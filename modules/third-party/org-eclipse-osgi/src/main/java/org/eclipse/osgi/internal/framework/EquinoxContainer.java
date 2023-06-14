/*******************************************************************************
 * Copyright (c) 2012, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.internal.framework;

import java.io.IOException;
import java.security.AccessController;
import java.util.*;
import java.util.concurrent.*;
import org.eclipse.osgi.framework.eventmgr.ListenerQueue;
import org.eclipse.osgi.framework.log.FrameworkLogEntry;
import org.eclipse.osgi.framework.util.SecureAction;
import org.eclipse.osgi.internal.framework.legacy.PackageAdminImpl;
import org.eclipse.osgi.internal.framework.legacy.StartLevelImpl;
import org.eclipse.osgi.internal.hookregistry.ClassLoaderHook;
import org.eclipse.osgi.internal.hookregistry.HookRegistry;
import org.eclipse.osgi.internal.location.EquinoxLocations;
import org.eclipse.osgi.internal.log.EquinoxLogServices;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.osgi.internal.serviceregistry.ServiceRegistry;
import org.eclipse.osgi.signedcontent.SignedContentFactory;
import org.eclipse.osgi.storage.Storage;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.*;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.service.startlevel.StartLevel;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings("deprecation")
public class EquinoxContainer implements ThreadFactory, Runnable {
	public static final String NAME = "org.eclipse.osgi"; //$NON-NLS-1$
	static final SecureAction secureAction = AccessController.doPrivileged(SecureAction.createSecureAction());

	private final EquinoxConfiguration equinoxConfig;
	private final EquinoxLogServices logServices;
	private final Storage storage;
	private final PackageAdmin packageAdmin;
	private final StartLevel startLevel;
	private final Set<String> bootDelegation;
	private final String[] bootDelegationStems;
	private final boolean bootDelegateAll;
	private final boolean isProcessClassRecursionSupportedByAll;
	private final EquinoxEventPublisher eventPublisher;

	private volatile ServiceRegistry serviceRegistry;
	private volatile ContextFinder contextFinder;

	private volatile ServiceTracker<SignedContentFactory, SignedContentFactory> signedContentFactory;

	private volatile ScheduledExecutorService executor;
	private volatile StorageSaver storageSaver;

	public EquinoxContainer(Map<String, ?> configuration) {
		this.equinoxConfig = new EquinoxConfiguration(configuration, new HookRegistry(this));
		this.logServices = new EquinoxLogServices(this.equinoxConfig);
		this.equinoxConfig.logMessages(this.logServices);
		this.equinoxConfig.getHookRegistry().initialize();
		try {
			this.storage = Storage.createStorage(this);
		} catch (IOException e) {
			throw new RuntimeException("Error initializing storage.", e); //$NON-NLS-1$
		} catch (BundleException e) {
			throw new RuntimeException("Error initializing storage.", e); //$NON-NLS-1$
		}
		this.packageAdmin = new PackageAdminImpl(storage.getModuleContainer());
		this.startLevel = new StartLevelImpl(storage.getModuleContainer());
		this.eventPublisher = new EquinoxEventPublisher(this);

		// set the boot delegation according to the osgi boot delegation property
		// TODO unfortunately this has to be done after constructing storage so the vm profile is loaded
		// TODO ideally this should be in equinox configuration or perhaps in storage
		String bootDelegationProp = equinoxConfig.getConfiguration(Constants.FRAMEWORK_BOOTDELEGATION);
		String[] bootPackages = ManifestElement.getArrayFromList(bootDelegationProp, ","); //$NON-NLS-1$
		HashSet<String> exactMatch = new HashSet<>(bootPackages.length);
		List<String> stemMatch = new ArrayList<>(bootPackages.length);
		boolean delegateAllValue = false;
		for (int i = 0; i < bootPackages.length; i++) {
			if (bootPackages[i].equals("*")) { //$NON-NLS-1$
				delegateAllValue = true;
				exactMatch.clear();
				stemMatch.clear();
				break;
			} else if (bootPackages[i].endsWith("*")) { //$NON-NLS-1$
				if (bootPackages[i].length() > 2 && bootPackages[i].endsWith(".*")) //$NON-NLS-1$
					stemMatch.add(bootPackages[i].substring(0, bootPackages[i].length() - 1));
			} else {
				exactMatch.add(bootPackages[i]);
			}
		}
		bootDelegateAll = delegateAllValue;
		bootDelegation = exactMatch;
		bootDelegationStems = stemMatch.isEmpty() ? null : stemMatch.toArray(new String[stemMatch.size()]);

		// Detect if all hooks can support recursive class processing
		boolean supportRecursion = true;
		for (ClassLoaderHook hook : equinoxConfig.getHookRegistry().getClassLoaderHooks()) {
			supportRecursion &= hook.isProcessClassRecursionSupported();
		}
		isProcessClassRecursionSupportedByAll = supportRecursion;
	}

	public Storage getStorage() {
		return storage;
	}

	public EquinoxConfiguration getConfiguration() {
		return equinoxConfig;
	}

	public EquinoxLocations getLocations() {
		return equinoxConfig.getEquinoxLocations();
	}

	public EquinoxLogServices getLogServices() {
		return logServices;
	}

	public PackageAdmin getPackageAdmin() {
		return packageAdmin;
	}

	public StartLevel getStartLevel() {
		return startLevel;
	}

	public SignedContentFactory getSignedContentFactory() {
		ServiceTracker<SignedContentFactory, SignedContentFactory> current = signedContentFactory;

		return current == null ? null : current.getService();
	}

	public boolean isBootDelegationPackage(String name) {
		if (bootDelegateAll)
			return true;
		if (bootDelegation.contains(name))
			return true;
		if (bootDelegationStems != null)
			for (int i = 0; i < bootDelegationStems.length; i++)
				if (name.startsWith(bootDelegationStems[i]))
					return true;
		return false;
	}

	public boolean isProcessClassRecursionSupportedByAll() {
		return isProcessClassRecursionSupportedByAll;
	}

	void init() {
		eventPublisher.init();

		serviceRegistry = new ServiceRegistry(this);
		initializeContextFinder();
		executor = Executors.newScheduledThreadPool(1, this);
		// be sure to initialize the executor threads
		executor.execute(this);
		storageSaver = new StorageSaver(this);
	}

	void close() {
		StorageSaver currentSaver;
		Storage currentStorage;
		ScheduledExecutorService currentExecutor;

		serviceRegistry = null;
		currentSaver = storageSaver;
		currentStorage = storage;
		currentExecutor = executor;

		// do this outside of the lock to avoid deadlock
		currentSaver.close();
		currentStorage.close();
		// Must be done last since it will result in termination of the 
		// framework active thread.
		currentExecutor.shutdown();
	}

	private void initializeContextFinder() {
		Thread current = Thread.currentThread();
		try {
			ClassLoader parent = null;
			// check property for specified parent
			String type = equinoxConfig.getConfiguration(EquinoxConfiguration.PROP_CONTEXTCLASSLOADER_PARENT);
			if (EquinoxConfiguration.CONTEXTCLASSLOADER_PARENT_APP.equals(type))
				parent = ClassLoader.getSystemClassLoader();
			else if (EquinoxConfiguration.CONTEXTCLASSLOADER_PARENT_BOOT.equals(type))
				parent = EquinoxContainerAdaptor.BOOT_CLASSLOADER;
			else if (EquinoxConfiguration.CONTEXTCLASSLOADER_PARENT_FWK.equals(type))
				parent = EquinoxContainer.class.getClassLoader();
			else if (EquinoxConfiguration.CONTEXTCLASSLOADER_PARENT_EXT.equals(type)) {
				ClassLoader appCL = ClassLoader.getSystemClassLoader();
				if (appCL != null)
					parent = appCL.getParent();
			} else { // default is ccl (null or any other value will use ccl)
				parent = current.getContextClassLoader();
			}
			contextFinder = new ContextFinder(parent);
			current.setContextClassLoader(contextFinder);
			return;
		} catch (Exception e) {
			logServices.log(NAME, FrameworkLogEntry.INFO, NLS.bind(Msg.CANNOT_SET_CONTEXTFINDER, null), e);
		}

	}

	public EquinoxEventPublisher getEventPublisher() {
		return eventPublisher;
	}

	ScheduledExecutorService getScheduledExecutor() {
		return executor;
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public ContextFinder getContextFinder() {
		return contextFinder;
	}

	public <K, V, E> ListenerQueue<K, V, E> newListenerQueue() {
		return eventPublisher.newListenerQueue();
	}

	void checkAdminPermission(Bundle bundle, String action) {
		if (bundle == null)
			return;
		SecurityManager sm = System.getSecurityManager();
		if (sm != null)
			sm.checkPermission(new AdminPermission(bundle, action));
	}

	public void handleRuntimeError(Throwable t) {
		// TODO need to call some hook here
	}

	void systemStart(BundleContext bc) {
		signedContentFactory = new ServiceTracker<>(bc, SignedContentFactory.class, null);

		signedContentFactory.open();
	}

	void systemStop(BundleContext bc) {
		if (signedContentFactory != null) {
			signedContentFactory.close();
		}
	}

	@Override
	public String toString() {
		String UUID = equinoxConfig == null ? null : equinoxConfig.getConfiguration(Constants.FRAMEWORK_UUID);
		return "Equinox Container: " + UUID; //$NON-NLS-1$
	}

	StorageSaver getStorageSaver() {
		return storageSaver;
	}

	@Override
	public Thread newThread(Runnable r) {
		String type = equinoxConfig.getConfiguration(EquinoxConfiguration.PROP_ACTIVE_THREAD_TYPE, EquinoxConfiguration.ACTIVE_THREAD_TYPE_NORMAL);
		Thread t = new Thread(r, "Active Thread: " + toString()); //$NON-NLS-1$
		if (EquinoxConfiguration.ACTIVE_THREAD_TYPE_NORMAL.equals(type)) {
			t.setDaemon(false);
		} else {
			t.setDaemon(true);
		}
		t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}

	@Override
	public void run() {
		// Do nothing; just used to ensure the active thread is created during init
	}

}
/* @generated */