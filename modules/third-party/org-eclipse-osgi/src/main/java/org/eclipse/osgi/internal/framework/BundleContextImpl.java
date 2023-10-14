/*******************************************************************************
 * Copyright (c) 2003, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.osgi.internal.framework;

import java.io.*;
import java.net.URLConnection;
import java.security.*;
import java.util.*;
import org.eclipse.osgi.container.Module;
import org.eclipse.osgi.container.ModuleWiring;
import org.eclipse.osgi.container.namespaces.EquinoxModuleDataNamespace;
import org.eclipse.osgi.framework.eventmgr.EventDispatcher;
import org.eclipse.osgi.internal.debug.Debug;
import org.eclipse.osgi.internal.loader.BundleLoader;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.osgi.internal.serviceregistry.*;
import org.eclipse.osgi.storage.BundleInfo.Generation;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.*;
import org.osgi.framework.hooks.bundle.FindHook;
import org.osgi.resource.Capability;

/**
 * Bundle's execution context.
 *
 * This object is given out to bundles and provides the
 * implementation to the BundleContext for a host bundle.
 * It is destroyed when a bundle is stopped.
 */

public class BundleContextImpl implements BundleContext, EventDispatcher<Object, Object, Object> {
	static final String findHookName = FindHook.class.getName();
	/** true if the bundle context is still valid */
	private volatile boolean valid;

	/** Bundle object this context is associated with. */
	// This slot is accessed directly by the Framework instead of using
	// the getBundle() method because the Framework needs access to the bundle
	// even when the context is invalid while the close method is being called.
	final EquinoxBundle bundle;

	/** Internal equinox container object. */
	final EquinoxContainer container;
	final Debug debug;

	/** Services that bundle is using. Key is ServiceRegistrationImpl,
	 Value is ServiceUse */
	/* @GuardedBy("contextLock") */
	private HashMap<ServiceRegistrationImpl<?>, ServiceUse<?>> servicesInUse;

	/** The current instantiation of the activator. */
	private BundleActivator activator;

	/** private object for locking */
	private final Object contextLock = new Object();

	/**
	 * Construct a BundleContext which wrappers the framework for a
	 * bundle
	 *
	 * @param bundle The bundle we are wrapping.
	 */
	public BundleContextImpl(EquinoxBundle bundle, EquinoxContainer container) {
		this.bundle = bundle;
		this.container = container;
		this.debug = container.getConfiguration().getDebug();
		valid = true;
		synchronized (contextLock) {
			servicesInUse = null;
		}
		activator = null;
	}

	/**
	 * Destroy the wrapper. This is called when the bundle is stopped.
	 *
	 */
	protected void close() {
		valid = false; /* invalidate context */

		final ServiceRegistry registry = container.getServiceRegistry();

		registry.removeAllServiceListeners(this);
		container.getEventPublisher().removeAllListeners(this);

		/* service's registered by the bundle, if any, are unregistered. */
		registry.unregisterServices(this);

		/* service's used by the bundle, if any, are released. */
		registry.releaseServicesInUse(this);

		synchronized (contextLock) {
			servicesInUse = null;
		}
	}

	/**
	 * Retrieve the value of the named environment property.
	 *
	 * @param key The name of the requested property.
	 * @return The value of the requested property, or <code>null</code> if
	 * the property is undefined.
	 */
	public String getProperty(String key) {
		SecurityManager sm = System.getSecurityManager();

		if (sm != null) {
			sm.checkPropertyAccess(key);
		}

		return (container.getConfiguration().getProperty(key));
	}

	/**
	 * Retrieve the Bundle object for the context bundle.
	 *
	 * @return The context bundle's Bundle object.
	 */
	public Bundle getBundle() {
		checkValid();

		return getBundleImpl();
	}

	public EquinoxBundle getBundleImpl() {
		return bundle;
	}

	public Bundle installBundle(String location) throws BundleException {
		return installBundle(location, null);
	}

	public Bundle installBundle(String location, InputStream in) throws BundleException {
		checkValid();
		try {
			URLConnection content = container.getStorage().getContentConnection(null, location, in);
			Generation generation = container.getStorage().install(bundle.getModule(), location, content);
			return generation.getRevision().getBundle();
		} catch (IOException e) {
			throw new BundleException("Error reading bundle content.", e); //$NON-NLS-1$
		}

	}

	/**
	 * Retrieve the bundle that has the given unique identifier.
	 *
	 * @param id The identifier of the bundle to retrieve.
	 * @return A Bundle object, or <code>null</code>
	 * if the identifier doesn't match any installed bundle.
	 */
	public Bundle getBundle(long id) {
		Module m = container.getStorage().getModuleContainer().getModule(id);
		if (m == null) {
			return null;
		}

		List<Bundle> bundles = new ArrayList<>(1);
		bundles.add(m.getBundle());
		notifyFindHooks(this, bundles);
		if (bundles.isEmpty()) {
			return null;
		}
		return m.getBundle();
	}

	public Bundle getBundle(String location) {
		Module m = container.getStorage().getModuleContainer().getModule(location);
		return m == null ? null : m.getBundle();
	}

	/**
	 * Retrieve a list of all installed bundles.
	 * The list is valid at the time
	 * of the call to getBundles, but the framework is a very dynamic
	 * environment and bundles can be installed or uninstalled at anytime.
	 *
	 * @return An array of {@link Bundle} objects, one
	 * object per installed bundle.
	 */
	public Bundle[] getBundles() {
		List<Module> modules = container.getStorage().getModuleContainer().getModules();
		List<Bundle> bundles = new ArrayList<>(modules.size());
		for (Module module : modules) {
			bundles.add(module.getBundle());
		}

		notifyFindHooks(this, bundles);
		return bundles.toArray(new Bundle[bundles.size()]);
	}

	private void notifyFindHooks(final BundleContextImpl context, List<Bundle> allBundles) {
		if (context.getBundleImpl().getBundleId() == 0) {
			// Make a copy for the purposes of calling the hooks;
			// The the removals from the hooks are ignored
			allBundles = new ArrayList<>(allBundles);
		}
		final Collection<Bundle> shrinkable = new ShrinkableCollection<>(allBundles);
		if (System.getSecurityManager() == null) {
			notifyFindHooksPriviledged(context, shrinkable);
		} else {
			AccessController.doPrivileged(new PrivilegedAction<Void>() {
				public Void run() {
					notifyFindHooksPriviledged(context, shrinkable);
					return null;
				}
			});
		}
	}

	void notifyFindHooksPriviledged(final BundleContextImpl context, final Collection<Bundle> allBundles) {
		if (debug.DEBUG_HOOKS) {
			Debug.println("notifyBundleFindHooks(" + allBundles + ")"); //$NON-NLS-1$ //$NON-NLS-2$ 
		}
		container.getServiceRegistry().notifyHooksPrivileged(new HookContext() {
			public void call(Object hook, ServiceRegistration<?> hookRegistration) throws Exception {
				if (hook instanceof FindHook) {
					((FindHook) hook).find(context, allBundles);
				}
			}

			public String getHookClassName() {
				return findHookName;
			}

			public String getHookMethodName() {
				return "find"; //$NON-NLS-1$ 
			}

			@Override
			public boolean skipRegistration(ServiceRegistration<?> hookRegistration) {
				return false;
			}
		});
	}

	public void addServiceListener(ServiceListener listener, String filter) throws InvalidSyntaxException {
		checkValid();

		if (listener == null) {
			throw new IllegalArgumentException();
		}
		container.getServiceRegistry().addServiceListener(this, listener, filter);
	}

	/**
	 * Add a service listener.
	 *
	 * <p>This method is the same as calling
	 * {@link #addServiceListener(ServiceListener, String)}
	 * with filter set to <code>null</code>.
	 *
	 * @see #addServiceListener(ServiceListener, String)
	 */
	public void addServiceListener(ServiceListener listener) {
		try {
			addServiceListener(listener, null);
		} catch (InvalidSyntaxException e) {
			if (debug.DEBUG_GENERAL) {
				Debug.println("InvalidSyntaxException w/ null filter" + e.getMessage()); //$NON-NLS-1$
				Debug.printStackTrace(e);
			}
		}
	}

	/**
	 * Remove a service listener.
	 * The listener is removed from the context bundle's list of listeners.
	 * See {@link #getBundle() getBundle()}
	 * for a definition of context bundle.
	 *
	 * <p>If this method is called with a listener which is not registered,
	 * then this method does nothing.
	 *
	 * @param listener The service listener to remove.
	 * @exception java.lang.IllegalStateException
	 * If the bundle context has stopped.
	 */
	public void removeServiceListener(ServiceListener listener) {
		checkValid();

		if (listener == null) {
			throw new IllegalArgumentException();
		}
		container.getServiceRegistry().removeServiceListener(this, listener);
	}

	/**
	 * Add a bundle listener.
	 * {@link BundleListener}s are notified when a bundle has a lifecycle
	 * state change.
	 * The listener is added to the context bundle's list of listeners.
	 * See {@link #getBundle() getBundle()}
	 * for a definition of context bundle.
	 *
	 * @param listener The bundle listener to add.
	 * @exception java.lang.IllegalStateException
	 * If the bundle context has stopped.
	 * @see BundleEvent
	 * @see BundleListener
	 */
	public void addBundleListener(BundleListener listener) {
		checkValid();
		if (listener == null) {
			throw new IllegalArgumentException();
		}

		if (debug.DEBUG_EVENTS) {
			String listenerName = listener.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(listener)); //$NON-NLS-1$
			Debug.println("addBundleListener[" + bundle + "](" + listenerName + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		container.getEventPublisher().addBundleListener(listener, this);
	}

	/**
	 * Remove a bundle listener.
	 * The listener is removed from the context bundle's list of listeners.
	 * See {@link #getBundle() getBundle()}
	 * for a definition of context bundle.
	 *
	 * <p>If this method is called with a listener which is not registered,
	 * then this method does nothing.
	 *
	 * @param listener The bundle listener to remove.
	 * @exception java.lang.IllegalStateException
	 * If the bundle context has stopped.
	 */
	public void removeBundleListener(BundleListener listener) {
		checkValid();
		if (listener == null) {
			throw new IllegalArgumentException();
		}

		if (debug.DEBUG_EVENTS) {
			String listenerName = listener.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(listener)); //$NON-NLS-1$
			Debug.println("removeBundleListener[" + bundle + "](" + listenerName + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		container.getEventPublisher().removeBundleListener(listener, this);
	}

	/**
	 * Add a general framework listener.
	 * {@link FrameworkListener}s are notified of general framework events.
	 * The listener is added to the context bundle's list of listeners.
	 * See {@link #getBundle() getBundle()}
	 * for a definition of context bundle.
	 *
	 * @param listener The framework listener to add.
	 * @exception java.lang.IllegalStateException
	 * If the bundle context has stopped.
	 * @see FrameworkEvent
	 * @see FrameworkListener
	 */
	public void addFrameworkListener(FrameworkListener listener) {
		checkValid();
		if (listener == null) {
			throw new IllegalArgumentException();
		}

		if (debug.DEBUG_EVENTS) {
			String listenerName = listener.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(listener)); //$NON-NLS-1$
			Debug.println("addFrameworkListener[" + bundle + "](" + listenerName + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		container.getEventPublisher().addFrameworkListener(listener, this);
	}

	/**
	 * Remove a framework listener.
	 * The listener is removed from the context bundle's list of listeners.
	 * See {@link #getBundle() getBundle()}
	 * for a definition of context bundle.
	 *
	 * <p>If this method is called with a listener which is not registered,
	 * then this method does nothing.
	 *
	 * @param listener The framework listener to remove.
	 * @exception java.lang.IllegalStateException
	 * If the bundle context has stopped.
	 */
	public void removeFrameworkListener(FrameworkListener listener) {
		checkValid();
		if (listener == null) {
			throw new IllegalArgumentException();
		}

		if (debug.DEBUG_EVENTS) {
			String listenerName = listener.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(listener)); //$NON-NLS-1$
			Debug.println("removeFrameworkListener[" + bundle + "](" + listenerName + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		container.getEventPublisher().removeFrameworkListener(listener, this);
	}

	/**
	 * Register a service with multiple names.
	 * This method registers the given service object with the given properties
	 * under the given class names.
	 * A {@link ServiceRegistration} object is returned.
	 * The {@link ServiceRegistration} object is for the private use of the bundle
	 * registering the service and should not be shared with other bundles.
	 * The registering bundle is defined to be the context bundle.
	 * See {@link #getBundle()} for a definition of context bundle.
	 * Other bundles can locate the service by using either the
	 * {@link #getServiceReferences getServiceReferences} or
	 * {@link #getServiceReference getServiceReference} method.
	 *
	 * <p>A bundle can register a service object that implements the
	 * {@link ServiceFactory} interface to
	 * have more flexiblity in providing service objects to different
	 * bundles.
	 *
	 * <p>The following steps are followed to register a service:
	 * <ol>
	 * <li>If the service parameter is not a {@link ServiceFactory},
	 * an <code>IllegalArgumentException</code> is thrown if the
	 * service parameter is not an <code>instanceof</code>
	 * all the classes named.
	 * <li>The service is added to the framework's service registry
	 * and may now be used by other bundles.
	 * <li>A {@link ServiceEvent} of type {@link ServiceEvent#REGISTERED}
	 * is synchronously sent.
	 * <li>A {@link ServiceRegistration} object for this registration
	 * is returned.
	 * </ol>
	 *
	 * @param clazzes The class names under which the service can be located.
	 *                The class names in this array will be stored in the service's
	 *                properties under the key "objectClass".
	 * @param service The service object or a {@link ServiceFactory} object.
	 * @param properties The properties for this service.
	 *        The keys in the properties object must all be Strings.
	 *        Changes should not be made to this object after calling this method.
	 *        To update the service's properties call the
	 *        {@link ServiceRegistration#setProperties ServiceRegistration.setProperties}
	 *        method.
	 *        This parameter may be <code>null</code> if the service has no properties.
	 * @return A {@link ServiceRegistration} object for use by the bundle
	 *        registering the service to update the
	 *        service's properties or to unregister the service.
	 * @exception java.lang.IllegalArgumentException If one of the following is true:
	 * <ul>
	 * <li>The service parameter is null.
	 * <li>The service parameter is not a {@link ServiceFactory} and is not an
	 * <code>instanceof</code> all the named classes in the clazzes parameter.
	 * </ul>
	 * @exception java.lang.SecurityException If the caller does not have
	 * {@link ServicePermission} permission to "register" the service for
	 * all the named classes
	 * and the Java runtime environment supports permissions.
	 * @exception java.lang.IllegalStateException
	 * If the bundle context has stopped.
	 * @see ServiceRegistration
	 * @see ServiceFactory
	 */
	public ServiceRegistration<?> registerService(String[] clazzes, Object service, Dictionary<String, ?> properties) {
		checkValid();
		return container.getServiceRegistry().registerService(this, clazzes, service, properties);
	}

	/**
	 * Register a service with a single name.
	 * This method registers the given service object with the given properties
	 * under the given class name.
	 *
	 * <p>This method is otherwise identical to
	 * {@link #registerService(java.lang.String[], java.lang.Object, java.util.Dictionary)}
	 * and is provided as a convenience when the service parameter will only be registered
	 * under a single class name.
	 *
	 * @see #registerService(java.lang.String[], java.lang.Object, java.util.Dictionary)
	 */
	public ServiceRegistration<?> registerService(String clazz, Object service, Dictionary<String, ?> properties) {
		String[] clazzes = new String[] {clazz};

		return registerService(clazzes, service, properties);
	}

	/**
	 * Returns a list of <tt>ServiceReference</tt> objects. This method returns a list of
	 * <tt>ServiceReference</tt> objects for services which implement and were registered under
	 * the specified class and match the specified filter criteria.
	 *
	 * <p>The list is valid at the time of the call to this method, however as the Framework is
	 * a very dynamic environment, services can be modified or unregistered at anytime.
	 *
	 * <p><tt>filter</tt> is used to select the registered service whose
	 * properties objects contain keys and values which satisfy the filter.
	 * See {@link Filter}for a description of the filter string syntax.
	 *
	 * <p>If <tt>filter</tt> is <tt>null</tt>, all registered services
	 * are considered to match the filter.
	 * <p>If <tt>filter</tt> cannot be parsed, an {@link InvalidSyntaxException} will
	 * be thrown with a human readable message where the filter became unparsable.
	 *
	 * <p>The following steps are required to select a service:
	 * <ol>
	 * <li>If the Java Runtime Environment supports permissions, the caller is checked for the
	 * <tt>ServicePermission</tt> to get the service with the specified class.
	 * If the caller does not have the correct permission, <tt>null</tt> is returned.
	 * <li>If the filter string is not <tt>null</tt>, the filter string is
	 * parsed and the set of registered services which satisfy the filter is
	 * produced.
	 * If the filter string is <tt>null</tt>, then all registered services
	 * are considered to satisfy the filter.
	 * <li>If <code>clazz</code> is not <tt>null</tt>, the set is further reduced to
	 * those services which are an <tt>instanceof</tt> and were registered under the specified class.
	 * The complete list of classes of which a service is an instance and which
	 * were specified when the service was registered is available from the
	 * service's {@link Constants#OBJECTCLASS}property.
	 * <li>An array of <tt>ServiceReference</tt> to the selected services is returned.
	 * </ol>
	 *
	 * @param clazz The class name with which the service was registered, or
	 * <tt>null</tt> for all services.
	 * @param filter The filter criteria.
	 * @return An array of <tt>ServiceReference</tt> objects, or
	 * <tt>null</tt> if no services are registered which satisfy the search.
	 * @exception InvalidSyntaxException If <tt>filter</tt> contains
	 * an invalid filter string which cannot be parsed.
	 */
	public ServiceReference<?>[] getServiceReferences(String clazz, String filter) throws InvalidSyntaxException {
		checkValid();
		return container.getServiceRegistry().getServiceReferences(this, clazz, filter, false);
	}

	public ServiceReference<?>[] getAllServiceReferences(String clazz, String filter) throws InvalidSyntaxException {
		checkValid();
		return container.getServiceRegistry().getServiceReferences(this, clazz, filter, true);
	}

	/**
	 * Get a service reference.
	 * Retrieves a {@link ServiceReference} for a service
	 * which implements the named class.
	 *
	 * <p>This reference is valid at the time
	 * of the call to this method, but since the framework is a very dynamic
	 * environment, services can be modified or unregistered at anytime.
	 *
	 * <p>This method is provided as a convenience for when the caller is
	 * interested in any service which implements a named class. This method is
	 * the same as calling {@link #getServiceReferences getServiceReferences}
	 * with a <code>null</code> filter string but only a single {@link ServiceReference}
	 * is returned.
	 *
	 * @param clazz The class name which the service must implement.
	 * @return A {@link ServiceReference} object, or <code>null</code>
	 * if no services are registered which implement the named class.
	 * @see #getServiceReferences
	 */
	public ServiceReference<?> getServiceReference(String clazz) {
		checkValid();

		return container.getServiceRegistry().getServiceReference(this, clazz);
	}

	/**
	 * Get a service's service object.
	 * Retrieves the service object for a service.
	 * A bundle's use of a service is tracked by a
	 * use count. Each time a service's service object is returned by
	 * {@link #getService}, the context bundle's use count for the service
	 * is incremented by one. Each time the service is release by
	 * {@link #ungetService}, the context bundle's use count
	 * for the service is decremented by one.
	 * When a bundle's use count for a service
	 * drops to zero, the bundle should no longer use the service.
	 * See {@link #getBundle()} for a definition of context bundle.
	 *
	 * <p>This method will always return <code>null</code> when the
	 * service associated with this reference has been unregistered.
	 *
	 * <p>The following steps are followed to get the service object:
	 * <ol>
	 * <li>If the service has been unregistered,
	 * <code>null</code> is returned.
	 * <li>The context bundle's use count for this service is incremented by one.
	 * <li>If the context bundle's use count for the service is now one and
	 * the service was registered with a {@link ServiceFactory},
	 * the {@link ServiceFactory#getService ServiceFactory.getService} method
	 * is called to create a service object for the context bundle.
	 * This service object is cached by the framework.
	 * While the context bundle's use count for the service is greater than zero,
	 * subsequent calls to get the services's service object for the context bundle
	 * will return the cached service object.
	 * <br>If the service object returned by the {@link ServiceFactory}
	 * is not an <code>instanceof</code>
	 * all the classes named when the service was registered or
	 * the {@link ServiceFactory} throws an exception,
	 * <code>null</code> is returned and a
	 * {@link FrameworkEvent} of type {@link FrameworkEvent#ERROR} is broadcast.
	 * <li>The service object for the service is returned.
	 * </ol>
	 *
	 * @param reference A reference to the service whose service object is desired.
	 * @return A service object for the service associated with this
	 * reference, or <code>null</code> if the service is not registered.
	 * @exception java.lang.SecurityException If the caller does not have
	 * {@link ServicePermission} permission to "get" the service
	 * using at least one of the named classes the service was registered under
	 * and the Java runtime environment supports permissions.
	 * @exception java.lang.IllegalStateException
	 * If the bundle context has stopped.
	 * @see #ungetService
	 * @see ServiceFactory
	 */
	public <S> S getService(ServiceReference<S> reference) {
		checkValid();
		if (reference == null)
			throw new NullPointerException("A null service reference is not allowed."); //$NON-NLS-1$
		provisionServicesInUseMap();
		S service = container.getServiceRegistry().getService(this, (ServiceReferenceImpl<S>) reference);
		return service;
	}

	/**
	 * Unget a service's service object.
	 * Releases the service object for a service.
	 * If the context bundle's use count for the service is zero, this method
	 * returns <code>false</code>. Otherwise, the context bundle's use count for the
	 * service is decremented by one.
	 * See {@link #getBundle()} for a definition of context bundle.
	 *
	 * <p>The service's service object
	 * should no longer be used and all references to it should be destroyed
	 * when a bundle's use count for the service
	 * drops to zero.
	 *
	 * <p>The following steps are followed to unget the service object:
	 * <ol>
	 * <li>If the context bundle's use count for the service is zero or
	 * the service has been unregistered,
	 * <code>false</code> is returned.
	 * <li>The context bundle's use count for this service is decremented by one.
	 * <li>If the context bundle's use count for the service is now zero and
	 * the service was registered with a {@link ServiceFactory},
	 * the {@link ServiceFactory#ungetService ServiceFactory.ungetService} method
	 * is called to release the service object for the context bundle.
	 * <li><code>true</code> is returned.
	 * </ol>
	 *
	 * @param reference A reference to the service to be released.
	 * @return <code>false</code> if the context bundle's use count for the service
	 *         is zero or if the service has been unregistered,
	 *         otherwise <code>true</code>.
	 * @exception java.lang.IllegalStateException
	 * If the bundle context has stopped.
	 * @see #getService
	 * @see ServiceFactory
	 */
	public boolean ungetService(ServiceReference<?> reference) {
		checkValid();

		return container.getServiceRegistry().ungetService(this, (ServiceReferenceImpl<?>) reference);
	}

	/**
	 * Creates a <code>File</code> object for a file in the
	 * persistent storage area provided for the bundle by the framework.
	 * If the adaptor does not have file system support, this method will
	 * return <code>null</code>.
	 *
	 * <p>A <code>File</code> object for the base directory of the
	 * persistent storage area provided for the context bundle by the framework
	 * can be obtained by calling this method with the empty string ("")
	 * as the parameter.
	 * See {@link #getBundle()} for a definition of context bundle.
	 *
	 * <p>If the Java runtime environment supports permissions,
	 * the framework the will ensure that the bundle has
	 * <code>java.io.FilePermission</code> with actions
	 * "read","write","execute","delete" for all files (recursively) in the
	 * persistent storage area provided for the context bundle by the framework.
	 *
	 * @param filename A relative name to the file to be accessed.
	 * @return A <code>File</code> object that represents the requested file or
	 * <code>null</code> if the adaptor does not have file system support.
	 * @exception java.lang.IllegalStateException
	 * If the bundle context has stopped.
	 */
	public File getDataFile(String filename) {
		checkValid();

		Generation generation = (Generation) bundle.getModule().getCurrentRevision().getRevisionInfo();
		return generation.getBundleInfo().getDataFile(filename);
	}

	/**
	 * Call bundle's BundleActivator.start()
	 * This method is called by Bundle.startWorker to start the bundle.
	 *
	 * @exception BundleException if
	 *            the bundle has a class that implements the BundleActivator interface,
	 *            but Framework couldn't instantiate it, or the BundleActivator.start()
	 *            method failed
	 */
	protected void start() throws BundleException {
		long start = 0;
		try {
			if (debug.DEBUG_BUNDLE_TIME) {
				start = System.currentTimeMillis();
				Debug.println("Finding activator for " + bundle); //$NON-NLS-1$
			}
			activator = loadBundleActivator();
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new BundleException(Msg.BundleContextImpl_LoadActivatorError, BundleException.ACTIVATOR_ERROR, e);
		}

		if (activator != null) {
			if (debug.DEBUG_BUNDLE_TIME) {
				Debug.println("Starting " + bundle); //$NON-NLS-1$
			}
			try {
				startActivator(activator);
			} catch (BundleException be) {
				activator = null;
				throw be;
			} finally {
				if (debug.DEBUG_BUNDLE_TIME) {
					Debug.println("End starting " + bundle + " " + (System.currentTimeMillis() - start)); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}

		/* activator completed successfully. We must use this
		 same activator object when we stop this bundle. */
	}

	private BundleActivator loadBundleActivator() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ModuleWiring wiring = bundle.getModule().getCurrentRevision().getWiring();
		if (wiring == null) {
			return null;
		}
		BundleLoader loader = (BundleLoader) wiring.getModuleLoader();
		if (loader == null) {
			return null;
		}
		List<Capability> metadata = wiring.getRevision().getCapabilities(EquinoxModuleDataNamespace.MODULE_DATA_NAMESPACE);
		if (metadata.isEmpty()) {
			return null;
		}

		String activatorName = (String) metadata.get(0).getAttributes().get(EquinoxModuleDataNamespace.CAPABILITY_ACTIVATOR);
		if (activatorName == null) {
			return null;
		}
		Class<?> activatorClass = loader.findClass(activatorName);
		return (BundleActivator) activatorClass.newInstance();
	}

	/**
	 * Calls the start method of a BundleActivator.
	 * @param bundleActivator that activator to start
	 */
	private void startActivator(final BundleActivator bundleActivator) throws BundleException {
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				public Void run() throws Exception {
					if (bundleActivator != null) {
						// make sure the context class loader is set correctly
						Object previousTCCL = setContextFinder();
						/* Start the bundle synchronously */
						try {
							bundleActivator.start(BundleContextImpl.this);
						} finally {
							if (previousTCCL != Boolean.FALSE)
								Thread.currentThread().setContextClassLoader((ClassLoader) previousTCCL);
						}
					}
					return null;
				}
			});
		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				t = ((PrivilegedActionException) t).getException();
			}

			if (debug.DEBUG_GENERAL) {
				Debug.printStackTrace(t);
			}

			String clazz = null;
			clazz = bundleActivator.getClass().getName();

			throw new BundleException(NLS.bind(Msg.BUNDLE_ACTIVATOR_EXCEPTION, new Object[] {clazz, "start", bundle.getSymbolicName() == null ? "" + bundle.getBundleId() : bundle.getSymbolicName()}), BundleException.ACTIVATOR_ERROR, t); //$NON-NLS-1$ //$NON-NLS-2$ 
		}
	}

	Object setContextFinder() {
		if (!container.getConfiguration().BUNDLE_SET_TCCL)
			return Boolean.FALSE;
		Thread currentThread = Thread.currentThread();
		ClassLoader previousTCCL = currentThread.getContextClassLoader();
		ClassLoader contextFinder = container.getContextFinder();
		if (previousTCCL != contextFinder) {
			currentThread.setContextClassLoader(container.getContextFinder());
			return previousTCCL;
		}
		return Boolean.FALSE;
	}

	/**
	 * Call bundle's BundleActivator.stop()
	 * This method is called by Bundle.stopWorker to stop the bundle.
	 *
	 * @exception BundleException if
	 *            the bundle has a class that implements the BundleActivator interface,
	 *            and the BundleActivator.stop() method failed
	 */
	protected void stop() throws BundleException {
		try {
			final BundleActivator bundleActivator = activator;
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				public Void run() throws Exception {
					if (bundleActivator != null) {
						// make sure the context class loader is set correctly
						Object previousTCCL = setContextFinder();
						try {
							/* Stop the bundle synchronously */
							bundleActivator.stop(BundleContextImpl.this);
						} finally {
							if (previousTCCL != Boolean.FALSE)
								Thread.currentThread().setContextClassLoader((ClassLoader) previousTCCL);
						}
					}
					return null;
				}
			});
		} catch (Throwable t) {
			if (t instanceof PrivilegedActionException) {
				t = ((PrivilegedActionException) t).getException();
			}

			if (debug.DEBUG_GENERAL) {
				Debug.printStackTrace(t);
			}

			String clazz = (activator == null) ? "" : activator.getClass().getName(); //$NON-NLS-1$

			throw new BundleException(NLS.bind(Msg.BUNDLE_ACTIVATOR_EXCEPTION, new Object[] {clazz, "stop", bundle.getSymbolicName() == null ? "" + bundle.getBundleId() : bundle.getSymbolicName()}), BundleException.ACTIVATOR_ERROR, t); //$NON-NLS-1$ //$NON-NLS-2$ 
		} finally {
			activator = null;
		}
	}

	/** 
	 * Return the map of ServiceRegistrationImpl to ServiceUse for services being
	 * used by this context.
	 * @return A map of ServiceRegistrationImpl to ServiceUse for services in use by 
	 * this context.
	 */
	public Map<ServiceRegistrationImpl<?>, ServiceUse<?>> getServicesInUseMap() {
		synchronized (contextLock) {
			return servicesInUse;
		}
	}

	/**
	 * Provision the map of ServiceRegistrationImpl to ServiceUse for services being
	 * used by this context.
	 */
	public void provisionServicesInUseMap() {
		synchronized (contextLock) {
			if (servicesInUse == null)
				// Cannot predict how many services a bundle will use, start with a small table.
				servicesInUse = new HashMap<>(10);
		}
	}

	/**
	 * Bottom level event dispatcher for the BundleContext.
	 *
	 * @param originalListener listener object registered under.
	 * @param l listener to call (may be filtered).
	 * @param action Event class type
	 * @param object Event object
	 */
	public void dispatchEvent(Object originalListener, Object l, int action, Object object) {
		Object previousTCCL = setContextFinder();
		try {
			// if context still valid or the system bundle
			if (isValid() || bundle.getBundleId() == 0) {
				switch (action) {
					case EquinoxEventPublisher.BUNDLEEVENT :
					case EquinoxEventPublisher.BUNDLEEVENTSYNC : {
						BundleListener listener = (BundleListener) l;

						if (debug.DEBUG_EVENTS) {
							String listenerName = listener.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(listener)); //$NON-NLS-1$
							Debug.println("dispatchBundleEvent[" + bundle + "](" + listenerName + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						}

						listener.bundleChanged((BundleEvent) object);
						break;
					}

					case ServiceRegistry.SERVICEEVENT : {
						ServiceEvent event = (ServiceEvent) object;

						ServiceListener listener = (ServiceListener) l;
						if (debug.DEBUG_EVENTS) {
							String listenerName = listener.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(listener)); //$NON-NLS-1$
							Debug.println("dispatchServiceEvent[" + bundle + "](" + listenerName + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						}
						listener.serviceChanged(event);
						break;
					}

					case EquinoxEventPublisher.FRAMEWORKEVENT : {
						FrameworkListener listener = (FrameworkListener) l;

						if (debug.DEBUG_EVENTS) {
							String listenerName = listener.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(listener)); //$NON-NLS-1$
							Debug.println("dispatchFrameworkEvent[" + bundle + "](" + listenerName + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						}

						listener.frameworkEvent((FrameworkEvent) object);
						break;
					}
					default : {
						throw new InternalError();
					}
				}
			}
		} catch (Throwable t) {
			if (debug.DEBUG_GENERAL) {
				Debug.println("Exception in bottom level event dispatcher: " + t.getMessage()); //$NON-NLS-1$
				Debug.printStackTrace(t);
			}
			// allow the adaptor to handle this unexpected error
			container.handleRuntimeError(t);
			publisherror: {
				if (action == EquinoxEventPublisher.FRAMEWORKEVENT) {
					FrameworkEvent event = (FrameworkEvent) object;
					if (event.getType() == FrameworkEvent.ERROR) {
						break publisherror; // avoid infinite loop
					}
				}

				container.getEventPublisher().publishFrameworkEvent(FrameworkEvent.ERROR, bundle, t);
			}
		} finally {
			if (previousTCCL != Boolean.FALSE)
				Thread.currentThread().setContextClassLoader((ClassLoader) previousTCCL);
		}
	}

	/**
	 * Construct a Filter object. This filter object may be used
	 * to match a ServiceReference or a Dictionary.
	 * See Filter
	 * for a description of the filter string syntax.
	 *
	 * @param filter The filter string.
	 * @return A Filter object encapsulating the filter string.
	 * @exception InvalidSyntaxException If the filter parameter contains
	 * an invalid filter string which cannot be parsed.
	 */
	public Filter createFilter(String filter) throws InvalidSyntaxException {
		checkValid();

		return FilterImpl.newInstance(filter, container.getConfiguration().getDebug().DEBUG_FILTER);
	}

	/**
	 * This method checks that the context is still valid. If the context is
	 * no longer valid, an IllegalStateException is thrown.
	 *
	 * @exception java.lang.IllegalStateException
	 * If the context bundle has stopped.
	 */
	public void checkValid() {
		if (!isValid()) {
			throw new IllegalStateException(Msg.BUNDLE_CONTEXT_INVALID_EXCEPTION);
		}
	}

	/**
	 * This method checks that the context is still valid. 
	 *
	 * @return true if the context is still valid; false otherwise
	 */
	protected boolean isValid() {
		return valid;
	}

	public <S> ServiceRegistration<S> registerService(Class<S> clazz, S service, Dictionary<String, ?> properties) {
		@SuppressWarnings("unchecked")
		ServiceRegistration<S> registration = (ServiceRegistration<S>) registerService(clazz.getName(), service, properties);
		return registration;
	}

	public <S> ServiceRegistration<S> registerService(Class<S> clazz, ServiceFactory<S> factory, Dictionary<String, ?> properties) {
		@SuppressWarnings("unchecked")
		ServiceRegistration<S> registration = (ServiceRegistration<S>) registerService(clazz.getName(), factory, properties);
		return registration;
	}

	public <S> ServiceReference<S> getServiceReference(Class<S> clazz) {
		@SuppressWarnings("unchecked")
		ServiceReference<S> reference = (ServiceReference<S>) getServiceReference(clazz.getName());
		return reference;
	}

	public <S> Collection<ServiceReference<S>> getServiceReferences(Class<S> clazz, String filter) throws InvalidSyntaxException {
		@SuppressWarnings("unchecked")
		ServiceReference<S>[] refs = (ServiceReference<S>[]) getServiceReferences(clazz.getName(), filter);
		if (refs == null) {
			Collection<ServiceReference<S>> empty = Collections.<ServiceReference<S>> emptyList();
			return empty;
		}
		List<ServiceReference<S>> result = new ArrayList<>(refs.length);
		for (ServiceReference<S> b : refs) {
			result.add(b);
		}
		return result;
	}

	public EquinoxContainer getContainer() {
		return container;
	}

	@Override
	public <S> ServiceObjects<S> getServiceObjects(ServiceReference<S> reference) {
		checkValid();
		if (reference == null)
			throw new NullPointerException("A null service reference is not allowed."); //$NON-NLS-1$
		provisionServicesInUseMap();
		ServiceObjects<S> serviceObjects = container.getServiceRegistry().getServiceObjects(this, (ServiceReferenceImpl<S>) reference);
		return serviceObjects;
	}
}
