/*******************************************************************************
 * Copyright (c) 2010, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.internal.weaving;

import java.util.*;
import org.eclipse.osgi.internal.framework.EquinoxContainer;
import org.eclipse.osgi.internal.hookregistry.ClassLoaderHook;
import org.eclipse.osgi.internal.loader.BundleLoader;
import org.eclipse.osgi.internal.loader.ModuleClassLoader;
import org.eclipse.osgi.internal.loader.classpath.ClasspathEntry;
import org.eclipse.osgi.internal.loader.classpath.ClasspathManager;
import org.eclipse.osgi.internal.serviceregistry.ServiceRegistry;
import org.eclipse.osgi.storage.bundlefile.BundleEntry;
import org.osgi.framework.*;

public class WeavingHookConfigurator extends ClassLoaderHook {
	static class WovenClassContext {
		List<WovenClassImpl> wovenClassStack = new ArrayList<>(6);
		List<String> processClassNameStack = new ArrayList<>(6);
	}

	// holds the map of black listed hooks.  Use weak map to avoid pinning and simplify cleanup.
	private final Map<ServiceRegistration<?>, Boolean> blackList = Collections.synchronizedMap(new WeakHashMap<ServiceRegistration<?>, Boolean>());
	// holds the stack of WovenClass objects currently being used to define classes
	private final ThreadLocal<WovenClassContext> wovenClassContext = new ThreadLocal<>();

	private final EquinoxContainer container;

	public WeavingHookConfigurator(EquinoxContainer container) {
		this.container = container;
	}

	private ServiceRegistry getRegistry() {
		return container.getServiceRegistry();
	}

	public byte[] processClass(String name, byte[] classbytes, ClasspathEntry classpathEntry, BundleEntry entry, ClasspathManager manager) {
		ServiceRegistry registry = getRegistry();
		if (registry == null)
			return null; // no registry somehow we are loading classes before the registry has been created
		ModuleClassLoader classLoader = manager.getClassLoader();
		BundleLoader loader = classLoader.getBundleLoader();
		boolean threadLocalSet = false;
		try {
			// create a woven class object and add it to the thread local stack
			WovenClassImpl wovenClass = new WovenClassImpl(name, classbytes, entry, classpathEntry, loader, container, blackList);
			WovenClassContext context = wovenClassContext.get();
			if (context == null) {
				context = new WovenClassContext();
				wovenClassContext.set(context);
				threadLocalSet = true;
			}
			context.wovenClassStack.add(wovenClass);
			// If we detect recursion for the same class name then we will not call the
			// weaving hooks for the second request to load.
			// Note that this means the actual bytes that get used to define the class
			// will not have been woven at all.
			if (!context.processClassNameStack.contains(name)) {
				context.processClassNameStack.add(name);
				// call the weaving hooks
				try {
					return wovenClass.callHooks();
				} catch (Throwable t) {
					ServiceRegistration<?> errorHook = wovenClass.getErrorHook();
					Bundle errorBundle = errorHook != null ? errorHook.getReference().getBundle() : manager.getGeneration().getRevision().getBundle();
					container.getEventPublisher().publishFrameworkEvent(FrameworkEvent.ERROR, errorBundle, t);
					// fail hard with a class loading error
					ClassFormatError error = new ClassFormatError("Unexpected error from weaving hook."); //$NON-NLS-1$
					error.initCause(t);
					throw error;
				} finally {
					context.processClassNameStack.remove(name);
				}
			}
		} finally {
			if (threadLocalSet) {
				wovenClassContext.remove();
			}
		}
		return null;
	}

	public void recordClassDefine(String name, Class<?> clazz, byte[] classbytes, ClasspathEntry classpathEntry, BundleEntry entry, ClasspathManager manager) {
		// here we assume the stack contans a woven class with the same name as the class we are defining.
		WovenClassContext context = wovenClassContext.get();
		if (context == null || context.wovenClassStack.size() == 0)
			return;
		WovenClassImpl wovenClass = context.wovenClassStack.remove(context.wovenClassStack.size() - 1);
		// inform the woven class about the class that was defined.
		wovenClass.setWeavingCompleted(clazz);
	}

	@Override
	public boolean isProcessClassRecursionSupported() {
		return true;
	}
}
/* @generated */
