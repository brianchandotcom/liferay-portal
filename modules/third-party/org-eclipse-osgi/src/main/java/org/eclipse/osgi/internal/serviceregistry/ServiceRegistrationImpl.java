/*******************************************************************************
 * Copyright (c) 2003, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.osgi.internal.serviceregistry;

import java.util.*;
import org.eclipse.osgi.internal.debug.Debug;
import org.eclipse.osgi.internal.framework.BundleContextImpl;
import org.eclipse.osgi.internal.loader.sources.PackageSource;
import org.eclipse.osgi.internal.messages.Msg;
import org.osgi.framework.*;

/**
 * A registered service.
 *
 * The framework returns a ServiceRegistration object when a
 * {@link BundleContextImpl#registerService(String, Object, Dictionary) BundleContext.registerService}
 * method is successful. This object is for the private use of
 * the registering bundle and should not be shared with other bundles.
 * <p>The ServiceRegistration object may be used to update the properties
 * for the service or to unregister the service.
 *
 * <p>If the ServiceRegistration is garbage collected the framework may remove
 * the service. This implies that if a
 * bundle wants to keep its service registered, it should keep the
 * ServiceRegistration object referenced.
 * 
 * @ThreadSafe
 */
public class ServiceRegistrationImpl<S> implements ServiceRegistration<S>, Comparable<ServiceRegistrationImpl<?>> {
	private final ServiceRegistry registry;

	/** context which registered this service. */
	private final BundleContextImpl context;

	/** bundle which registered this service. */
	private final Bundle bundle;

	/** service classes for this registration. */
	private final String[] clazzes;

	/** service object for this registration. */
	private final S service;

	/** Reference to this registration. */
	/* @GuardedBy("registrationLock") */
	private ServiceReferenceImpl<S> reference;

	/** List of contexts using the service. 
	 * List&lt;BundleContextImpl&gt;.
	 * */
	/* @GuardedBy("registrationLock") */
	private final List<BundleContextImpl> contextsUsing;

	/** properties for this registration. */
	/* @GuardedBy("registrationLock") */
	private ServiceProperties properties;

	/** service id. */
	private final long serviceid;

	/** service ranking. */
	/* @GuardedBy("registrationLock") */
	private int serviceranking;

	/* internal object to use for synchronization */
	private final Object registrationLock = new Object();

	/** The registration state */
	/* @GuardedBy("registrationLock") */
	private int state;
	private static final int REGISTERED = 0x00;
	private static final int UNREGISTERING = 0x01;
	private static final int UNREGISTERED = 0x02;

	/**
	 * Construct a ServiceRegistration and register the service
	 * in the framework's service registry.
	 *
	 */
	ServiceRegistrationImpl(ServiceRegistry registry, BundleContextImpl context, String[] clazzes, S service) {
		this.registry = registry;
		this.context = context;
		this.bundle = context.getBundleImpl();
		this.clazzes = clazzes; /* must be set before calling createProperties. */
		this.service = service; /* must be set before calling createProperties. */
		this.serviceid = registry.getNextServiceId(); /* must be set before calling createProperties. */
		this.contextsUsing = new ArrayList<BundleContextImpl>(10);

		synchronized (registrationLock) {
			this.state = REGISTERED;
			/* We leak this from the constructor here, but it is ok
			 * because the ServiceReferenceImpl constructor only
			 * stores the value in a final field without
			 * otherwise using it.
			 */
			this.reference = new ServiceReferenceImpl<S>(this);
		}
	}

	/**
	 * Call after constructing this object to complete the registration.
	 */
	void register(Dictionary<String, ?> props) {
		final ServiceReferenceImpl<S> ref;
		synchronized (registry) {
			context.checkValid();
			synchronized (registrationLock) {
				ref = reference; /* used to publish event outside sync */
				this.properties = createProperties(props); /* must be valid after unregister is called. */
			}
			if (registry.debug.DEBUG_SERVICES) {
				Debug.println("registerService[" + bundle + "](" + this + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			registry.addServiceRegistration(context, this);
		}

		/* must not hold the registrations lock when this event is published */
		registry.publishServiceEvent(new ServiceEvent(ServiceEvent.REGISTERED, ref));
	}

	/**
	 * Update the properties associated with this service.
	 *
	 * <p>The key "objectClass" cannot be modified by this method. It's
	 * value is set when the service is registered.
	 *
	 * <p>The following steps are followed to modify a service's properties:
	 * <ol>
	 * <li>The service's properties are replaced with the provided properties.
	 * <li>A {@link ServiceEvent} of type {@link ServiceEvent#MODIFIED}
	 * is synchronously sent.
	 * </ol>
	 *
	 * @param props The properties for this service.
	 *        Changes should not be made to this object after calling this method.
	 *        To update the service's properties this method should be called again.
	 * @exception java.lang.IllegalStateException If
	 * this ServiceRegistration has already been unregistered.
	 *
	 * @exception IllegalArgumentException If the <tt>properties</tt>
	 * parameter contains case variants of the same key name.
	 */
	public void setProperties(Dictionary<String, ?> props) {
		final ServiceReferenceImpl<S> ref;
		final ServiceProperties previousProperties;
		synchronized (registry) {
			synchronized (registrationLock) {
				if (state != REGISTERED) { /* in the process of unregisterING */
					throw new IllegalStateException(Msg.SERVICE_ALREADY_UNREGISTERED_EXCEPTION);
				}

				ref = reference; /* used to publish event outside sync */
				previousProperties = this.properties;
				this.properties = createProperties(props);
			}
			registry.modifyServiceRegistration(context, this);
		}
		/* must not hold the registrationLock when this event is published */
		registry.publishServiceEvent(new ModifiedServiceEvent(ref, previousProperties));
	}

	/**
	 * Unregister the service.
	 * Remove a service registration from the framework's service
	 * registry.
	 * All {@link ServiceReferenceImpl} objects for this registration
	 * can no longer be used to interact with the service.
	 *
	 * <p>The following steps are followed to unregister a service:
	 * <ol>
	 * <li>The service is removed from the framework's service
	 * registry so that it may no longer be used.
	 * {@link ServiceReferenceImpl}s for the service may no longer be used
	 * to get a service object for the service.
	 * <li>A {@link ServiceEvent} of type {@link ServiceEvent#UNREGISTERING}
	 * is synchronously sent so that bundles using this service
	 * may release their use of the service.
	 * <li>For each bundle whose use count for this service is greater
	 * than zero:
	 * <ol>
	 * <li>The bundle's use count for this service is set to zero.
	 * <li>If the service was registered with a {@link ServiceFactory},
	 * the {@link ServiceFactory#ungetService ServiceFactory.ungetService} method
	 * is called to release the service object for the bundle.
	 * </ol>
	 * </ol>
	 *
	 * @exception java.lang.IllegalStateException If
	 * this ServiceRegistration has already been unregistered.
	 * @see BundleContextImpl#ungetService
	 */
	public void unregister() {
		final ServiceReferenceImpl<S> ref;
		synchronized (registry) {
			synchronized (registrationLock) {
				if (state != REGISTERED) { /* in the process of unregisterING */
					throw new IllegalStateException(Msg.SERVICE_ALREADY_UNREGISTERED_EXCEPTION);
				}

				/* remove this object from the service registry */
				if (registry.debug.DEBUG_SERVICES) {
					Debug.println("unregisterService[" + bundle + "](" + this + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}

				registry.removeServiceRegistration(context, this);

				state = UNREGISTERING; /* mark unregisterING */
				ref = reference; /* used to publish event outside sync */
			}
		}

		/* must not hold the registrationLock when this event is published */
		registry.publishServiceEvent(new ServiceEvent(ServiceEvent.UNREGISTERING, ref));

		int size = 0;
		BundleContextImpl[] users = null;

		synchronized (registrationLock) {
			/* we have published the ServiceEvent, now mark the service fully unregistered */
			state = UNREGISTERED;

			size = contextsUsing.size();
			if (size > 0) {
				if (registry.debug.DEBUG_SERVICES) {
					Debug.println("unregisterService: releasing users"); //$NON-NLS-1$
				}
				users = contextsUsing.toArray(new BundleContextImpl[size]);
			}
		}

		/* must not hold the registrationLock while releasing services */
		for (int i = 0; i < size; i++) {
			releaseService(users[i]);
		}

		synchronized (registrationLock) {
			contextsUsing.clear();

			reference = null; /* mark registration dead */
		}

		/* The properties field must remain valid after unregister completes. */
	}

	/**
	 * Is this registration unregistered?
	 * 
	 * @return true if unregistered; otherwise false.
	 */
	boolean isUnregistered() {
		synchronized (registrationLock) {
			return state == UNREGISTERED;
		}
	}

	/**
	 * Returns a {@link ServiceReferenceImpl} object for this registration.
	 * The {@link ServiceReferenceImpl} object may be shared with other bundles.
	 *
	 * @exception java.lang.IllegalStateException If
	 * this ServiceRegistration has already been unregistered.
	 * @return A {@link ServiceReferenceImpl} object.
	 */
	public ServiceReference<S> getReference() {
		return getReferenceImpl();
	}

	ServiceReferenceImpl<S> getReferenceImpl() {
		/* use reference instead of unregistered so that ServiceFactorys, called
		 * by releaseService after the registration is unregistered, can
		 * get the ServiceReference. Note this technically may violate the spec
		 * but makes more sense.
		 */
		synchronized (registrationLock) {
			if (reference == null) {
				throw new IllegalStateException(Msg.SERVICE_ALREADY_UNREGISTERED_EXCEPTION + " " + properties);
			}

			return reference;
		}
	}

	/**
	 * Construct a properties object from the dictionary for this
	 * ServiceRegistration.
	 *
	 * @param p The properties for this service.
	 * @return A Properties object for this ServiceRegistration.
	 */
	/* @GuardedBy("registrationLock") */
	private ServiceProperties createProperties(Dictionary<String, ?> p) {
		assert Thread.holdsLock(registrationLock);
		ServiceProperties props = new ServiceProperties(p);

		props.set(Constants.OBJECTCLASS, clazzes, true);
		props.set(Constants.SERVICE_ID, Long.valueOf(serviceid), true);
		props.set(Constants.SERVICE_BUNDLEID, Long.valueOf(bundle.getBundleId()), true);
		final String scope;
		if (service instanceof ServiceFactory) {
			if (service instanceof PrototypeServiceFactory) {
				scope = Constants.SCOPE_PROTOTYPE;
			} else {
				scope = Constants.SCOPE_BUNDLE;
			}
		} else {
			scope = Constants.SCOPE_SINGLETON;
		}
		props.set(Constants.SERVICE_SCOPE, scope, true);
		props.setReadOnly();

		Object ranking = props.getProperty(Constants.SERVICE_RANKING);
		if (ranking instanceof Integer) {
			serviceranking = ((Integer) ranking).intValue();
		} else {
			serviceranking = 0;
			if (ranking != null) {
				registry.getContainer().getEventPublisher().publishFrameworkEvent(FrameworkEvent.WARNING, getBundle(), new ServiceException("Invalid ranking type: " + ranking.getClass(), ServiceException.UNSPECIFIED)); //$NON-NLS-1$
			}
		}

		return props;
	}

	/**
	 * Return the properties object. This is for framework internal use only.
	 * @return The service registration's properties.
	 */
	public ServiceProperties getProperties() {
		synchronized (registrationLock) {
			return properties;
		}
	}

	/**
	 * Get the value of a service's property.
	 *
	 * <p>This method will continue to return property values after the
	 * service has been unregistered. This is so that references to
	 * unregistered service can be interrogated.
	 * (For example: ServiceReference objects stored in the log.)
	 *
	 * @param key Name of the property.
	 * @return Value of the property or <code>null</code> if there is
	 * no property by that name.
	 */
	Object getProperty(String key) {
		synchronized (registrationLock) {
			return properties.getProperty(key);
		}
	}

	/**
	 * Get the list of key names for the service's properties.
	 *
	 * <p>This method will continue to return the keys after the
	 * service has been unregistered. This is so that references to
	 * unregistered service can be interrogated.
	 * (For example: ServiceReference objects stored in the log.)
	 *
	 * @return The list of property key names.
	 */
	String[] getPropertyKeys() {
		synchronized (registrationLock) {
			return properties.getPropertyKeys();
		}
	}

	/**
	 * Return the service id for this service.
	 * @return The service id for this service.
	 */
	long getId() {
		return serviceid;
	}

	/**
	 * Return the service ranking for this service.
	 * @return The service ranking for this service.
	 */
	int getRanking() {
		synchronized (registrationLock) {
			return serviceranking;
		}
	}

	String[] getClasses() {
		return clazzes;
	}

	S getServiceObject() {
		return service;
	}

	/**
	 * Return the bundle which registered the service.
	 *
	 * <p>This method will always return <code>null</code> when the
	 * service has been unregistered. This can be used to
	 * determine if the service has been unregistered.
	 *
	 * @return The bundle which registered the service.
	 */
	Bundle getBundle() {
		synchronized (registrationLock) {
			if (reference == null) {
				return null;
			}

			return bundle;
		}
	}

	/**
	 * This method returns the bundle which registered the 
	 * service regardless of the registration status of this
	 * service registration.  This is not an OSGi specified
	 * method.
	 * @return The bundle which registered the service.
	 */
	public Bundle getRegisteringBundle() {
		return bundle;
	}

	S getSafeService(BundleContextImpl user, ServiceConsumer consumer) {
		try {
			return getService(user, consumer);
		} catch (IllegalStateException e) {
			// can happen if the user is stopped on another thread
			return null;
		}
	}

	/**
	 * Get a service object for the using BundleContext.
	 *
	 * @param user BundleContext using service.
	 * @param consumer The closure for the consumer type.
	 * @return Service object
	 */
	S getService(BundleContextImpl user, ServiceConsumer consumer) {
		if (isUnregistered()) { /* service unregistered */
			return null;
		}
		Map<ServiceRegistrationImpl<?>, ServiceUse<?>> servicesInUse = user.getServicesInUseMap();
		if (servicesInUse == null) { /* user is closed */
			user.checkValid(); /* throw exception */
		}

		if (registry.debug.DEBUG_SERVICES) {
			Debug.println("getService[" + user.getBundleImpl() + "](" + this + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		/* Use a while loop to support retry if a call to a ServiceFactory fails */
		while (true) {
			ServiceUse<S> use;
			boolean added = false;
			/* Obtain the ServiceUse object for this service by bundle user */
			synchronized (servicesInUse) {
				user.checkValid();
				@SuppressWarnings("unchecked")
				ServiceUse<S> u = (ServiceUse<S>) servicesInUse.get(this);
				use = u;
				if (use == null) {
					/* if this is the first use of the service
					 * optimistically record this service is being used. */
					use = newServiceUse(user);
					added = true;
					synchronized (registrationLock) {
						if (state == UNREGISTERED) { /* service unregistered */
							return null;
						}
						servicesInUse.put(this, use);
						contextsUsing.add(user);
					}
				}
			}

			/* Obtain and return the service object */
			synchronized (use) {
				/* if another thread removed the ServiceUse, then
				 * go back to the top and start again */
				synchronized (servicesInUse) {
					user.checkValid();
					if (servicesInUse.get(this) != use) {
						continue;
					}
				}
				S serviceObject = consumer.getService(use);
				/* if the service factory failed to return an object and
				 * we created the service use, then remove the 
				 * optimistically added ServiceUse. */
				if ((serviceObject == null) && added) {
					synchronized (servicesInUse) {
						synchronized (registrationLock) {
							servicesInUse.remove(this);
							contextsUsing.remove(user);
						}
					}
				}
				return serviceObject;
			}
		}
	}

	/**
	 * Create a new ServiceObjects for the requesting bundle.
	 * 
	 * @param user The requesting bundle.
	 * @return A new ServiceObjects for this service and the requesting bundle.
	 */
	ServiceObjectsImpl<S> getServiceObjects(BundleContextImpl user) {
		if (isUnregistered()) { /* service unregistered */
			return null;
		}
		if (registry.debug.DEBUG_SERVICES) {
			Debug.println("getServiceObjects[" + user.getBundleImpl() + "](" + this + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		return new ServiceObjectsImpl<S>(user, this);
	}

	/**
	 * Create a new ServiceUse object for this service and user.
	 * 
	 * @param user The bundle using this service.
	 * @return The ServiceUse object for the bundle using this service.
	 */
	private ServiceUse<S> newServiceUse(BundleContextImpl user) {
		if (service instanceof ServiceFactory) {
			if (service instanceof PrototypeServiceFactory) {
				return new PrototypeServiceFactoryUse<S>(user, this);
			}
			return new ServiceFactoryUse<S>(user, this);
		}
		return new ServiceUse<S>(user, this);
	}

	/**
	 * Unget a service for the using BundleContext.
	 *
	 * @param user BundleContext using service.
	 * @param consumer The closure for the consumer type.
	 * @param serviceObject The service object to release for prototype consumers.
	 * @return <code>false</code> if the context bundle's use count for the service
	 *         is zero or if the service has been unregistered,
	 *         otherwise <code>true</code>.
	 */
	boolean ungetService(BundleContextImpl user, ServiceConsumer consumer, S serviceObject) {
		if (isUnregistered()) {
			return false;
		}
		Map<ServiceRegistrationImpl<?>, ServiceUse<?>> servicesInUse = user.getServicesInUseMap();
		if (servicesInUse == null) {
			return false;
		}

		if (registry.debug.DEBUG_SERVICES) {
			Debug.println("ungetService[" + user.getBundleImpl() + "](" + this + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		ServiceUse<S> use;
		synchronized (servicesInUse) {
			@SuppressWarnings("unchecked")
			ServiceUse<S> u = (ServiceUse<S>) servicesInUse.get(this);
			use = u;
			if (use == null) {
				return false;
			}
		}

		boolean result;
		synchronized (use) {
			result = consumer.ungetService(use, serviceObject);
			if (use.isEmpty()) { /* service use can be discarded */
				synchronized (servicesInUse) {
					synchronized (registrationLock) {
						servicesInUse.remove(this);
						contextsUsing.remove(user);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Release the service for the using BundleContext.
	 *
	 * @param user BundleContext using service.
	 */
	void releaseService(BundleContextImpl user) {
		synchronized (registrationLock) {
			if (reference == null) { /* registration dead */
				return;
			}
		}

		if (registry.debug.DEBUG_SERVICES) {
			Debug.println("releaseService[" + user.getBundleImpl() + "](" + this + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		Map<ServiceRegistrationImpl<?>, ServiceUse<?>> servicesInUse = user.getServicesInUseMap();
		if (servicesInUse == null) {
			return;
		}
		ServiceUse<S> use;
		synchronized (servicesInUse) {
			synchronized (registrationLock) {
				@SuppressWarnings("unchecked")
				ServiceUse<S> u = (ServiceUse<S>) servicesInUse.remove(this);
				use = u;
				if (use == null) {
					return;
				}
				contextsUsing.remove(user);
			}
		}
		synchronized (use) {
			use.release();
		}
	}

	/**
	 * Return the list of bundle which are using this service.
	 *
	 * @return Array of Bundles using this service.
	 */
	Bundle[] getUsingBundles() {
		synchronized (registrationLock) {
			if (state == UNREGISTERED) /* service unregistered */
				return null;

			int size = contextsUsing.size();
			if (size == 0)
				return null;

			/* Copy list of BundleContext into an array of Bundle. */
			Bundle[] bundles = new Bundle[size];
			for (int i = 0; i < size; i++)
				bundles[i] = contextsUsing.get(i).getBundleImpl();

			return bundles;
		}
	}

	boolean isAssignableTo(Bundle client, String className) {
		return PackageSource.isServiceAssignableTo(bundle, client, className, service.getClass(), context.getContainer());
	}

	/**
	 * Return a String representation of this object.
	 *
	 * @return String representation of this object.
	 */
	public String toString() {
		int size = clazzes.length;
		StringBuffer sb = new StringBuffer(50 * size);

		sb.append('{');

		for (int i = 0; i < size; i++) {
			if (i > 0) {
				sb.append(", "); //$NON-NLS-1$
			}
			sb.append(clazzes[i]);
		}

		sb.append("}="); //$NON-NLS-1$
		sb.append(getProperties().toString());

		return sb.toString();
	}

	/**
	 * Compares this <code>ServiceRegistrationImpl</code> with the specified
	 * <code>ServiceRegistrationImpl</code> for order.
	 * 
	 * <p>
	 * This does a reverse comparison so that the highest item is sorted to the left.
	 * We keep ServiceRegistationImpls in sorted lists such that the highest
	 * ranked service is at element 0 for quick retrieval.
	 * 
	 * @param other The <code>ServiceRegistrationImpl</code> to be compared.
	 * @return Returns a negative integer, zero, or a positive integer if this
	 *         <code>ServiceRegistrationImpl</code> is greater than, equal to, or
	 *         less than the specified <code>ServiceRegistrationImpl</code>.
	 */
	public int compareTo(ServiceRegistrationImpl<?> other) {
		final int thisRanking = this.getRanking();
		final int otherRanking = other.getRanking();
		if (thisRanking != otherRanking) {
			if (thisRanking < otherRanking) {
				return 1;
			}
			return -1;
		}
		final long thisId = this.getId();
		final long otherId = other.getId();
		if (thisId == otherId) {
			return 0;
		}
		if (thisId < otherId) {
			return -1;
		}
		return 1;
	}
}
