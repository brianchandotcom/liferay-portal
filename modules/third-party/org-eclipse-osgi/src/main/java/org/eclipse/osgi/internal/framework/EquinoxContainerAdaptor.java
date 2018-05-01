/*******************************************************************************
 * Copyright (c) 2012, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.internal.framework;

import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.osgi.container.*;
import org.eclipse.osgi.container.Module.Settings;
import org.eclipse.osgi.container.Module.State;
import org.eclipse.osgi.internal.hookregistry.ClassLoaderHook;
import org.eclipse.osgi.internal.loader.*;
import org.eclipse.osgi.internal.permadmin.BundlePermissions;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.storage.BundleInfo.Generation;
import org.eclipse.osgi.storage.Storage;
import org.osgi.framework.*;
import org.osgi.framework.hooks.resolver.ResolverHookFactory;
import org.osgi.framework.wiring.BundleRevision;

public class EquinoxContainerAdaptor extends ModuleContainerAdaptor {
	private static final ClassLoader BOOT_CLASSLOADER = new ClassLoader(Object.class.getClassLoader()) { /* boot class loader */};
	private final EquinoxContainer container;
	private final Storage storage;
	private final OSGiFrameworkHooks hooks;
	private final Map<Long, Generation> initial;
	// The ClassLoader parent to use when creating ModuleClassLoaders.
	private final ClassLoader moduleClassLoaderParent;
	private final AtomicLong lastSecurityAdminFlush;

	public EquinoxContainerAdaptor(EquinoxContainer container, Storage storage, Map<Long, Generation> initial) {
		this.container = container;
		this.storage = storage;
		this.hooks = new OSGiFrameworkHooks(container, storage);
		this.initial = initial;
		this.moduleClassLoaderParent = getModuleClassLoaderParent(container.getConfiguration());
		this.lastSecurityAdminFlush = new AtomicLong();
	}

	private static ClassLoader getModuleClassLoaderParent(EquinoxConfiguration configuration) {
		// allow hooks to determine the parent class loader
		for (ClassLoaderHook hook : configuration.getHookRegistry().getClassLoaderHooks()) {
			ClassLoader parent = hook.getModuleClassLoaderParent(configuration);
			if (parent != null) {
				// first one to return non-null wins.
				return parent;
			}
		}
		// DEFAULT behavior:
		// check property for specified parent
		// check the osgi defined property first
		String type = configuration.getConfiguration(Constants.FRAMEWORK_BUNDLE_PARENT);
		if (type == null) {
			type = configuration.getConfiguration(EquinoxConfiguration.PROP_PARENT_CLASSLOADER, Constants.FRAMEWORK_BUNDLE_PARENT_BOOT);
		}

		if (Constants.FRAMEWORK_BUNDLE_PARENT_FRAMEWORK.equalsIgnoreCase(type) || EquinoxConfiguration.PARENT_CLASSLOADER_FWK.equalsIgnoreCase(type)) {
			ClassLoader cl = EquinoxContainer.class.getClassLoader();
			return cl == null ? BOOT_CLASSLOADER : cl;
		}
		if (Constants.FRAMEWORK_BUNDLE_PARENT_APP.equalsIgnoreCase(type))
			return ClassLoader.getSystemClassLoader();
		if (Constants.FRAMEWORK_BUNDLE_PARENT_EXT.equalsIgnoreCase(type)) {
			ClassLoader appCL = ClassLoader.getSystemClassLoader();
			if (appCL != null)
				return appCL.getParent();
		}
		return BOOT_CLASSLOADER;

	}

	@Override
	public ModuleCollisionHook getModuleCollisionHook() {
		return hooks.getModuleCollisionHook();
	}

	@Override
	public ResolverHookFactory getResolverHookFactory() {
		return hooks.getResolverHookFactory();
	}

	@Override
	public void publishContainerEvent(ContainerEvent type, Module module, Throwable error, FrameworkListener... listeners) {
		EquinoxEventPublisher publisher = container.getEventPublisher();
		if (publisher != null) {
			publisher.publishFrameworkEvent(getType(type), module.getBundle(), error, listeners);
		}
	}

	@Override
	public void publishModuleEvent(ModuleEvent type, Module module, Module origin) {
		EquinoxEventPublisher publisher = container.getEventPublisher();
		if (publisher != null) {
			publisher.publishBundleEvent(getType(type), module.getBundle(), origin.getBundle());
		}
	}

	@Override
	public Module createModule(String location, long id, EnumSet<Settings> settings, int startlevel) {
		EquinoxBundle bundle = new EquinoxBundle(id, location, storage.getModuleContainer(), settings, startlevel, container);
		return bundle.getModule();
	}

	@Override
	public SystemModule createSystemModule() {
		return (SystemModule) new EquinoxBundle.SystemBundle(storage.getModuleContainer(), container).getModule();
	}

	@Override
	public String getProperty(String key) {
		return storage.getConfiguration().getConfiguration(key);
	}

	@Override
	public ModuleLoader createModuleLoader(ModuleWiring wiring) {
		if (wiring.getBundle().getBundleId() == 0) {
			ClassLoader cl = EquinoxContainer.class.getClassLoader();
			cl = cl == null ? BOOT_CLASSLOADER : cl;
			return new SystemBundleLoader(wiring, container, cl);
		}
		if ((wiring.getRevision().getTypes() & BundleRevision.TYPE_FRAGMENT) != 0) {
			return new FragmentLoader();
		}
		return new BundleLoader(wiring, container, moduleClassLoaderParent);
	}

	@Override
	public Generation getRevisionInfo(String location, long id) {
		return initial.remove(id);
	}

	@Override
	public void associateRevision(ModuleRevision revision, Object revisionInfo) {
		((Generation) revisionInfo).setRevision(revision);
	}

	@Override
	public void invalidateWiring(ModuleWiring moduleWiring, ModuleLoader current) {
		if (current instanceof BundleLoader) {
			BundleLoader bundleLoader = (BundleLoader) current;
			bundleLoader.close();
		}
		long updatedTimestamp = storage.getModuleDatabase().getRevisionsTimestamp();
		if (System.getSecurityManager() != null && updatedTimestamp != lastSecurityAdminFlush.getAndSet(updatedTimestamp)) {
			storage.getSecurityAdmin().clearCaches();
			List<Module> modules = storage.getModuleContainer().getModules();
			for (Module module : modules) {
				for (ModuleRevision revision : module.getRevisions().getModuleRevisions()) {
					Generation generation = (Generation) revision.getRevisionInfo();
					if (generation != null && generation.getContent().exists()) {
						ProtectionDomain domain = generation.getDomain();
						if (domain != null) {
							((BundlePermissions) domain.getPermissions()).clearPermissionCache();
						}
					}
				}
			}
		}
		clearManifestCache(moduleWiring);
	}

	private void clearManifestCache(ModuleWiring moduleWiring) {
		boolean frameworkActive = Module.ACTIVE_SET.contains(storage.getModuleContainer().getModule(0).getState());
		ModuleRevision revision = moduleWiring.getRevision();
		Module module = revision.getRevisions().getModule();
		boolean isUninstallingOrUninstalled = State.UNINSTALLED.equals(module.getState()) ^ module.holdsTransitionEventLock(ModuleEvent.UNINSTALLED);
		if (!frameworkActive || !isUninstallingOrUninstalled) {
			// only do this when the framework is not active or when the bundle is not uninstalled
			Generation generation = (Generation) moduleWiring.getRevision().getRevisionInfo();
			generation.clearManifestCache();
		}
	}

	static int getType(ContainerEvent type) {
		switch (type) {
			case ERROR :
				return FrameworkEvent.ERROR;
			case INFO :
				return FrameworkEvent.INFO;
			case WARNING :
				return FrameworkEvent.WARNING;
			case REFRESH :
				return FrameworkEvent.PACKAGES_REFRESHED;
			case START_LEVEL :
				return FrameworkEvent.STARTLEVEL_CHANGED;
			case STARTED :
				return FrameworkEvent.STARTED;
			case STOPPED :
				return FrameworkEvent.STOPPED;
			case STOPPED_REFRESH :
				return FrameworkEvent.STOPPED_BOOTCLASSPATH_MODIFIED;
			case STOPPED_UPDATE :
				return FrameworkEvent.STOPPED_UPDATE;
			case STOPPED_TIMEOUT :
				return FrameworkEvent.WAIT_TIMEDOUT;
			default :
				// default to error
				return FrameworkEvent.ERROR;
		}
	}

	private int getType(ModuleEvent type) {
		switch (type) {
			case INSTALLED :
				return BundleEvent.INSTALLED;
			case LAZY_ACTIVATION :
				return BundleEvent.LAZY_ACTIVATION;
			case RESOLVED :
				return BundleEvent.RESOLVED;
			case STARTED :
				return BundleEvent.STARTED;
			case STARTING :
				return BundleEvent.STARTING;
			case STOPPING :
				return BundleEvent.STOPPING;
			case STOPPED :
				return BundleEvent.STOPPED;
			case UNINSTALLED :
				return BundleEvent.UNINSTALLED;
			case UNRESOLVED :
				return BundleEvent.UNRESOLVED;
			case UPDATED :
				return BundleEvent.UPDATED;
			default :
				// TODO log error?
				return 0;
		}
	}

	@Override
	public void refreshedSystemModule() {
		storage.getConfiguration().setConfiguration(EquinoxConfiguration.PROP_FORCED_RESTART, "true"); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return container.toString();
	}

	@Override
	public void updatedDatabase() {
		StorageSaver saver = container.getStorageSaver();
		if (saver == null)
			return;
		saver.save();
	}

	@Override
	public void initBegin() {
		hooks.initBegin();
	}

	@Override
	public void initEnd() {
		hooks.initEnd();
	}

	@Override
	public DebugOptions getDebugOptions() {
		return container.getConfiguration().getDebugOptions();
	}
}