/*******************************************************************************
 * Copyright (c) 2003, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.osgi.internal.serviceregistry;

import java.security.AccessController;
import java.security.PrivilegedAction;
import org.eclipse.osgi.internal.debug.Debug;
import org.eclipse.osgi.internal.framework.BundleContextImpl;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.*;

/**
 * This class represents the use of a service by a bundle. One is created for each
 * service acquired by a bundle. 
 * 
 * <p>
 * This class manages a service factory.
 * 
 * @ThreadSafe
 */
public class ServiceFactoryUse<S> extends ServiceUse<S> {
	/** BundleContext associated with this service use */
	final BundleContextImpl context;
	/** ServiceFactory object  */
	final ServiceFactory<S> factory;
	final Debug debug;

	/** Service object returned by ServiceFactory.getService() */
	/* @GuardedBy("this") */
	private S cachedService;
	/** true if we are calling the factory getService method. Used to detect recursion. */
	/* @GuardedBy("this") */
	private boolean factoryInUse;

	/**
	 * Constructs a service use encapsulating the service factory.
	 *
	 * @param   context bundle getting the service
	 * @param   registration ServiceRegistration of the service
	 */
	ServiceFactoryUse(BundleContextImpl context, ServiceRegistrationImpl<S> registration) {
		super(context, registration);
		this.debug = context.getContainer().getConfiguration().getDebug();
		this.context = context;
		this.factoryInUse = false;
		this.cachedService = null;
		@SuppressWarnings("unchecked")
		ServiceFactory<S> f = (ServiceFactory<S>) registration.getServiceObject();
		this.factory = f;
	}

	/**
	 * Get a service's service object and increment the use count.
	 *
	 * <p>The following steps are followed to get the service object:
	 * <ol>
	 * <li>The use count is incremented by one.
	 * <li>If the use count is now one,
	 * the {@link ServiceFactory#getService(Bundle, ServiceRegistration)} method
	 * is called to create a service object for the context bundle.
	 * This service object is cached.
	 * While the use count is greater than zero,
	 * subsequent calls to get the service object 
	 * will return the cached service object.
	 * <br>If the service object returned by the {@link ServiceFactory}
	 * is not an <code>instanceof</code>
	 * all the classes named when the service was registered or
	 * the {@link ServiceFactory} throws an exception,
	 * <code>null</code> is returned and a
	 * {@link FrameworkEvent} of type {@link FrameworkEvent#ERROR} is broadcast.
	 * <li>The service object is returned.
	 * </ol>
	 *
	 * @return The service object.
	 */
	/* @GuardedBy("this") */
	@Override
	S getService() {
		assert Thread.holdsLock(this);
		if (inUse()) {
			incrementUse();
			return cachedService;
		}

		if (debug.DEBUG_SERVICES) {
			Debug.println("getService[factory=" + registration.getBundle() + "](" + context.getBundleImpl() + "," + registration + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		// check for recursive call on this thread
		if (factoryInUse) {
			if (debug.DEBUG_SERVICES) {
				Debug.println(factory + ".getService() recursively called."); //$NON-NLS-1$
			}

			ServiceException se = new ServiceException(NLS.bind(Msg.SERVICE_FACTORY_RECURSION, factory.getClass().getName(), "getService"), ServiceException.FACTORY_RECURSION); //$NON-NLS-1$
			context.getContainer().getEventPublisher().publishFrameworkEvent(FrameworkEvent.WARNING, registration.getBundle(), se);
			return null;
		}
		factoryInUse = true;
		final S service;
		try {
			service = factoryGetService();
			if (service == null) {
				return null;
			}
		} finally {
			factoryInUse = false;
		}

		this.cachedService = service;
		incrementUse();

		return service;
	}

	/**
	 * Unget a service's service object.
	 * 
	 * <p>
	 * Decrements the use count if the service was being used.
	 *
	 * <p>The following steps are followed to unget the service object:
	 * <ol>
	 * <li>If the use count is zero, return.
	 * <li>The use count is decremented by one.
	 * <li>If the use count is non zero, return.
	 * <li>The {@link ServiceFactory#ungetService(Bundle, ServiceRegistration, Object)} method
	 * is called to release the service object for the context bundle.
	 * </ol>
	 * @return true if the service was ungotten; otherwise false.
	 */
	/* @GuardedBy("this") */
	@Override
	boolean ungetService() {
		assert Thread.holdsLock(this);
		if (!inUse()) {
			return false;
		}

		decrementUse();
		if (inUse()) {
			return true;
		}

		final S service = cachedService;
		cachedService = null;

		if (debug.DEBUG_SERVICES) {
			Debug.println("ungetService[factory=" + registration.getBundle() + "](" + context.getBundleImpl() + "," + registration + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		factoryUngetService(service);
		return true;
	}

	/**
	 * Release all uses of the service and reset the use count to zero.
	 * 
	 * <ol>
	 * <li>The bundle's use count for this service is set to zero.
	 * <li>The {@link ServiceFactory#ungetService(Bundle, ServiceRegistration, Object)} method
	 * is called to release the service object for the bundle.
	 * </ol>
	 */
	/* @GuardedBy("this") */
	@Override
	void release() {
		super.release();

		final S service = cachedService;
		if (service == null) {
			return;
		}
		cachedService = null;

		if (debug.DEBUG_SERVICES) {
			Debug.println("releaseService[factory=" + registration.getBundle() + "](" + context.getBundleImpl() + "," + registration + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		factoryUngetService(service);
	}

	/**
	 * Return the service object for this service use.
	 *
	 * @return The service object.
	 */
	/* @GuardedBy("this") */
	@Override
	S getCachedService() {
		return cachedService;
	}

	/**
	 *  Call the service factory to get the service.
	 *  
	 * @return The service returned by the factory or null if there was an error.
	 */
	/* @GuardedBy("this") */
	S factoryGetService() {
		final S service;
		try {
			service = AccessController.doPrivileged(new PrivilegedAction<S>() {
				public S run() {
					return factory.getService(context.getBundleImpl(), registration);
				}
			});
		} catch (Throwable t) {
			if (debug.DEBUG_SERVICES) {
				Debug.println(factory + ".getService() exception: " + t.getMessage()); //$NON-NLS-1$
				Debug.printStackTrace(t);
			}
			// allow the adaptor to handle this unexpected error
			context.getContainer().handleRuntimeError(t);
			ServiceException se = new ServiceException(NLS.bind(Msg.SERVICE_FACTORY_EXCEPTION, factory.getClass().getName(), "getService"), ServiceException.FACTORY_EXCEPTION, t); //$NON-NLS-1$ 
			context.getContainer().getEventPublisher().publishFrameworkEvent(FrameworkEvent.ERROR, registration.getBundle(), se);
			return null;
		}

		if (service == null) {
			if (debug.DEBUG_SERVICES) {
				Debug.println(factory + ".getService() returned null."); //$NON-NLS-1$
			}

			ServiceException se = new ServiceException(NLS.bind(Msg.SERVICE_OBJECT_NULL_EXCEPTION, factory.getClass().getName()), ServiceException.FACTORY_ERROR);
			context.getContainer().getEventPublisher().publishFrameworkEvent(FrameworkEvent.WARNING, registration.getBundle(), se);
			return null;
		}

		String[] clazzes = registration.getClasses();
		String invalidService = ServiceRegistry.checkServiceClass(clazzes, service);
		if (invalidService != null) {
			if (debug.DEBUG_SERVICES) {
				Debug.println("Service object is not an instanceof " + invalidService); //$NON-NLS-1$
			}
			ServiceException se = new ServiceException(NLS.bind(Msg.SERVICE_FACTORY_NOT_INSTANCEOF_CLASS_EXCEPTION, factory.getClass().getName(), invalidService), ServiceException.FACTORY_ERROR);
			context.getContainer().getEventPublisher().publishFrameworkEvent(FrameworkEvent.ERROR, registration.getBundle(), se);
			return null;
		}
		return service;
	}

	/**
	 *  Call the service factory to unget the service.
	 *  
	 *  @param service The service object to pass to the factory.
	 */
	/* @GuardedBy("this") */
	void factoryUngetService(final S service) {
		try {
			AccessController.doPrivileged(new PrivilegedAction<Void>() {
				public Void run() {
					factory.ungetService(context.getBundleImpl(), registration, service);
					return null;
				}
			});
		} catch (Throwable t) {
			if (debug.DEBUG_SERVICES) {
				Debug.println(factory + ".ungetService() exception"); //$NON-NLS-1$
				Debug.printStackTrace(t);
			}

			ServiceException se = new ServiceException(NLS.bind(Msg.SERVICE_FACTORY_EXCEPTION, factory.getClass().getName(), "ungetService"), ServiceException.FACTORY_EXCEPTION, t); //$NON-NLS-1$ 
			context.getContainer().getEventPublisher().publishFrameworkEvent(FrameworkEvent.ERROR, registration.getBundle(), se);
		}
	}
}
