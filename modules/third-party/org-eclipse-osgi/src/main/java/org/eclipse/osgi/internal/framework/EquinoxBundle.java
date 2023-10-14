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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.eclipse.osgi.container.Module;
import org.eclipse.osgi.container.Module.Settings;
import org.eclipse.osgi.container.Module.StartOptions;
import org.eclipse.osgi.container.Module.State;
import org.eclipse.osgi.container.Module.StopOptions;
import org.eclipse.osgi.container.ModuleContainer;
import org.eclipse.osgi.container.ModuleContainerAdaptor.ContainerEvent;
import org.eclipse.osgi.container.ModuleContainerAdaptor.ModuleEvent;
import org.eclipse.osgi.container.ModuleLoader;
import org.eclipse.osgi.container.ModuleRevision;
import org.eclipse.osgi.container.ModuleWire;
import org.eclipse.osgi.container.ModuleWiring;
import org.eclipse.osgi.container.SystemModule;
import org.eclipse.osgi.framework.log.FrameworkLogEntry;
import org.eclipse.osgi.internal.debug.Debug;
import org.eclipse.osgi.internal.loader.BundleLoader;
import org.eclipse.osgi.internal.loader.ModuleClassLoader;
import org.eclipse.osgi.internal.loader.classpath.ClasspathManager;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.osgi.internal.permadmin.EquinoxSecurityManager;
import org.eclipse.osgi.report.resolution.ResolutionReport;
import org.eclipse.osgi.signedcontent.SignedContent;
import org.eclipse.osgi.signedcontent.SignedContentFactory;
import org.eclipse.osgi.signedcontent.SignerInfo;
import org.eclipse.osgi.storage.BundleInfo.Generation;
import org.eclipse.osgi.storage.Storage;
import org.osgi.framework.AdaptPermission;
import org.osgi.framework.AdminPermission;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleReference;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.framework.dto.BundleDTO;
import org.osgi.framework.dto.FrameworkDTO;
import org.osgi.framework.dto.ServiceReferenceDTO;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.namespace.HostNamespace;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;
import org.osgi.framework.startlevel.dto.BundleStartLevelDTO;
import org.osgi.framework.startlevel.dto.FrameworkStartLevelDTO;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleRevisions;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.framework.wiring.dto.BundleRevisionDTO;
import org.osgi.framework.wiring.dto.BundleWiringDTO;
import org.osgi.framework.wiring.dto.FrameworkWiringDTO;

public class EquinoxBundle implements Bundle, BundleReference {

	static class SystemBundle extends EquinoxBundle implements Framework {
		class SystemBundleHeaders extends Dictionary<String, String> {
			private final Dictionary<String, String> headers;

			public SystemBundleHeaders(Dictionary<String, String> headers) {
				this.headers = headers;
			}

			public Enumeration<String> elements() {
				return headers.elements();
			}

			public String get(Object key) {
				if (!(key instanceof String))
					return null;

				String sKey = (String) key;
				if (Constants.EXPORT_PACKAGE.equalsIgnoreCase(sKey) || Constants.PROVIDE_CAPABILITY.equalsIgnoreCase(sKey)) {
					String systemProvideHeader = getEquinoxContainer().getConfiguration().getConfiguration(EquinoxConfiguration.PROP_SYSTEM_PROVIDE_HEADER, EquinoxConfiguration.SYSTEM_PROVIDE_HEADER_SYSTEM_EXTRA);
					boolean useSystemExtra = systemProvideHeader.equals(EquinoxConfiguration.SYSTEM_PROVIDE_HEADER_SYSTEM_EXTRA);
					boolean useSystem = systemProvideHeader.equals(EquinoxConfiguration.SYSTEM_PROVIDE_HEADER_SYSTEM) || useSystemExtra;
					String systemProp = useSystem ? (Constants.EXPORT_PACKAGE.equalsIgnoreCase(sKey) ? Constants.FRAMEWORK_SYSTEMPACKAGES : Constants.FRAMEWORK_SYSTEMCAPABILITIES) : null;
					String systemExtraProp = useSystemExtra ? (Constants.EXPORT_PACKAGE.equalsIgnoreCase(sKey) ? Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA : Constants.FRAMEWORK_SYSTEMCAPABILITIES_EXTRA) : null;
					return getExtra(sKey, systemProp, systemExtraProp);
				}

				return headers.get(key);
			}

			private String getExtra(String header, String systemProp, String systemExtraProp) {
				String systemValue = systemProp != null ? getEquinoxContainer().getConfiguration().getConfiguration(systemProp) : null;
				String systemExtraValue = systemExtraProp != null ? getEquinoxContainer().getConfiguration().getConfiguration(systemExtraProp) : null;
				if (systemValue == null)
					systemValue = systemExtraValue;
				else if (systemExtraValue != null && systemExtraValue.trim().length() > 0)
					systemValue += ", " + systemExtraValue; //$NON-NLS-1$
				String result = headers.get(header);
				if (systemValue != null && systemValue.trim().length() > 0) {
					if (result != null)
						result += ", " + systemValue; //$NON-NLS-1$
					else
						result = systemValue;
				}
				return result;
			}

			public boolean isEmpty() {
				return headers.isEmpty();
			}

			public Enumeration<String> keys() {
				return headers.keys();
			}

			public String put(String key, String value) {
				return headers.put(key, value);
			}

			public String remove(Object key) {
				return headers.remove(key);
			}

			public int size() {
				return headers.size();
			}

		}

		final List<FrameworkListener> initListeners = new ArrayList<>(0);

		class EquinoxSystemModule extends SystemModule {
			public EquinoxSystemModule(ModuleContainer container) {
				super(container);
			}

			@Override
			public Bundle getBundle() {
				return SystemBundle.this;
			}

			@Override
			protected void cleanup(ModuleRevision revision) {
				// Nothing to do
			}

			@Override
			protected void initWorker() throws BundleException {
				String initUUID = getEquinoxContainer().getConfiguration().setConfiguration(EquinoxConfiguration.PROP_INIT_UUID, Boolean.TRUE.toString());
				if (initUUID != null) {
					// this is not the first framework init, need to generate a new UUID
					getEquinoxContainer().getConfiguration().setConfiguration(Constants.FRAMEWORK_UUID, UUID.randomUUID().toString());
				}
				getEquinoxContainer().init();
				addInitFrameworkListeners();
				startWorker0();
			}

			@Override
			protected void stopWorker() throws BundleException {
				super.stopWorker();
				stopWorker0();
				getEquinoxContainer().close();
			}

			void asyncStop() throws BundleException {
				if (getEquinoxContainer().getConfiguration().getDebug().DEBUG_SYSTEM_BUNDLE) {
					Debug.printStackTrace(new Exception("Framework has been requested to stop.")); //$NON-NLS-1$
				}
				lockStateChange(ModuleEvent.STOPPED);
				try {
					if (Module.ACTIVE_SET.contains(getState())) {
						// TODO this still has a chance of a race condition:
						// multiple threads could get started if stop is called over and over
						Thread t = new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									stop();
								} catch (Throwable e) {
									SystemBundle.this.getEquinoxContainer().getLogServices().log(EquinoxContainer.NAME, FrameworkLogEntry.ERROR, "Error stopping the framework.", e); //$NON-NLS-1$
								}
							}
						}, "Framework stop"); //$NON-NLS-1$
						t.start();
					}
				} finally {
					unlockStateChange(ModuleEvent.STOPPED);
				}
			}

			void asyncUpdate() throws BundleException {
				if (getEquinoxContainer().getConfiguration().getDebug().DEBUG_SYSTEM_BUNDLE) {
					Debug.printStackTrace(new Exception("Framework has been requested to update (restart).")); //$NON-NLS-1$
				}
				lockStateChange(ModuleEvent.UPDATED);
				try {
					if (Module.ACTIVE_SET.contains(getState())) {
						Thread t = new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									update();
								} catch (Throwable e) {
									SystemBundle.this.getEquinoxContainer().getLogServices().log(EquinoxContainer.NAME, FrameworkLogEntry.ERROR, "Error updating the framework.", e); //$NON-NLS-1$
								}
							}
						}, "Framework update"); //$NON-NLS-1$
						t.start();
					}
				} finally {
					unlockStateChange(ModuleEvent.UPDATED);
				}
			}
		}

		SystemBundle(ModuleContainer moduleContainer, EquinoxContainer equinoxContainer) {
			super(moduleContainer, equinoxContainer);
		}

		@Override
		public void init() throws BundleException {
			this.init((FrameworkListener[]) null);
		}

		public void init(FrameworkListener... listeners) throws BundleException {
			if (listeners != null) {
				if (getEquinoxContainer().getConfiguration().getDebug().DEBUG_SYSTEM_BUNDLE) {
					Debug.println("Initializing framework with framework listeners: " + listeners); //$NON-NLS-1$
				}
				initListeners.addAll(Arrays.asList(listeners));
			} else {
				if (getEquinoxContainer().getConfiguration().getDebug().DEBUG_SYSTEM_BUNDLE) {
					Debug.println("Initializing framework with framework no listeners"); //$NON-NLS-1$
				}
			}
			try {
				((SystemModule) getModule()).init();
			} finally {
				if (!initListeners.isEmpty()) {
					getEquinoxContainer().getEventPublisher().flushFrameworkEvents();
					removeInitListeners();
				}
			}
		}

		void addInitFrameworkListeners() {
			BundleContext context = createBundleContext(false);
			for (FrameworkListener initListener : initListeners) {
				context.addFrameworkListener(initListener);
			}
		}

		void removeInitListeners() {
			BundleContext context = createBundleContext(false);
			for (FrameworkListener initListener : initListeners) {
				context.removeFrameworkListener(initListener);
			}
			initListeners.clear();
		}

		@Override
		public FrameworkEvent waitForStop(long timeout) throws InterruptedException {
			ContainerEvent event = ((SystemModule) getModule()).waitForStop(timeout);
			return new FrameworkEvent(EquinoxContainerAdaptor.getType(event), this, null);
		}

		@Override
		Module createSystemModule(ModuleContainer moduleContainer) {
			return new EquinoxSystemModule(moduleContainer);
		}

		@Override
		public void stop(final int options) throws BundleException {
			getEquinoxContainer().checkAdminPermission(this, AdminPermission.EXECUTE);
			((EquinoxSystemModule) getModule()).asyncStop();
		}

		@Override
		public void stop() throws BundleException {
			stop(0);
		}

		@Override
		public void update(InputStream input) throws BundleException {
			getEquinoxContainer().checkAdminPermission(this, AdminPermission.LIFECYCLE);
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
				// do nothing
			}
			((EquinoxSystemModule) getModule()).asyncUpdate();
		}

		@Override
		public void update() throws BundleException {
			update(null);
		}

		@Override
		public void uninstall() throws BundleException {
			getEquinoxContainer().checkAdminPermission(this, AdminPermission.LIFECYCLE);
			throw new BundleException(Msg.BUNDLE_SYSTEMBUNDLE_UNINSTALL_EXCEPTION, BundleException.INVALID_OPERATION);
		}

		@Override
		public Dictionary<String, String> getHeaders(String locale) {
			return new SystemBundleHeaders(super.getHeaders(locale));
		}
	}

	private final EquinoxContainer equinoxContainer;
	private final Module module;
	private final Object monitor = new Object();
	private BundleContextImpl context;
	private volatile SignerInfo[] signerInfos;

	private class EquinoxModule extends Module {

		@Override
		protected void startWorker() throws BundleException {
			startWorker0();
		}

		@Override
		protected void stopWorker() throws BundleException {
			stopWorker0();
		}

		public EquinoxModule(Long id, String location, ModuleContainer container, EnumSet<Settings> settings, int startlevel) {
			super(id, location, container, settings, startlevel);
		}

		@Override
		public Bundle getBundle() {
			return EquinoxBundle.this;
		}

		@Override
		protected void cleanup(ModuleRevision revision) {
			Generation generation = (Generation) revision.getRevisionInfo();
			generation.delete();
			if (revision.equals(getCurrentRevision())) {
				// uninstall case
				generation.getBundleInfo().delete();
			}
		}
	}

	EquinoxBundle(ModuleContainer moduleContainer, EquinoxContainer equinoxContainer) {
		this.equinoxContainer = equinoxContainer;
		this.module = createSystemModule(moduleContainer);
	}

	public EquinoxBundle(Long id, String location, ModuleContainer moduleContainer, EnumSet<Settings> settings, int startlevel, EquinoxContainer equinoxContainer) {
		this.equinoxContainer = equinoxContainer;
		this.module = new EquinoxModule(id, location, moduleContainer, settings, startlevel);
	}

	Module createSystemModule(ModuleContainer moduleContainer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int compareTo(Bundle bundle) {
		long idcomp = getBundleId() - bundle.getBundleId();
		return (idcomp < 0L) ? -1 : ((idcomp > 0L) ? 1 : 0);
	}

	@Override
	public int getState() {
		switch (module.getState()) {
			case INSTALLED :
				return Bundle.INSTALLED;
			case RESOLVED :
				return Bundle.RESOLVED;
			case STARTING :
			case LAZY_STARTING :
				return Bundle.STARTING;
			case ACTIVE :
				return Bundle.ACTIVE;
			case STOPPING :
				return Bundle.STOPPING;
			case UNINSTALLED :
				return Bundle.UNINSTALLED;
			default :
				throw new IllegalStateException("No valid bundle state for module state: " + module.getState()); //$NON-NLS-1$
		}
	}

	@Override
	public void start(int options) throws BundleException {
		if (options == 0 && equinoxContainer.getConfiguration().getDebug().MONITOR_ACTIVATION) {
			Debug.printStackTrace(new Exception("A persistent start has been called on bundle: " + this)); //$NON-NLS-1$
		}
		module.start(getStartOptions(options));
	}

	private static StartOptions[] getStartOptions(int options) {
		if (options == 0) {
			return new StartOptions[0];
		}
		Collection<StartOptions> result = new ArrayList<>(2);
		if ((options & Bundle.START_TRANSIENT) != 0) {
			result.add(StartOptions.TRANSIENT);
		}
		if ((options & Bundle.START_ACTIVATION_POLICY) != 0) {
			result.add(StartOptions.USE_ACTIVATION_POLICY);
		}
		return result.toArray(new StartOptions[result.size()]);
	}

	@Override
	public void start() throws BundleException {
		start(0);
	}

	@Override
	public void stop(int options) throws BundleException {
		if (options == 0 && equinoxContainer.getConfiguration().getDebug().MONITOR_ACTIVATION) {
			Debug.printStackTrace(new Exception("A persistent stop has been called on bundle: " + this)); //$NON-NLS-1$
		}
		module.stop(getStopOptions(options));
	}

	private StopOptions[] getStopOptions(int options) {
		if ((options & Bundle.STOP_TRANSIENT) == 0) {
			return new StopOptions[0];
		}
		return new StopOptions[] {StopOptions.TRANSIENT};
	}

	@Override
	public void stop() throws BundleException {
		stop(0);
	}

	@Override
	public void update(InputStream input) throws BundleException {
		try {
			Storage storage = equinoxContainer.getStorage();
			storage.update(module, storage.getContentConnection(module, null, input));
			signerInfos = null;
		} catch (IOException e) {
			throw new BundleException("Error reading bundle content.", e); //$NON-NLS-1$
		}
	}

	@Override
	public void update() throws BundleException {
		update(null);
	}

	@Override
	public void uninstall() throws BundleException {
		// be sure to prime the headers with default local; calling priv method to avoid permission check
		privGetHeaders(null);
		Storage storage = equinoxContainer.getStorage();
		storage.getModuleContainer().uninstall(module);
	}

	@Override
	public Dictionary<String, String> getHeaders() {
		return getHeaders(null);
	}

	@Override
	public Dictionary<String, String> getHeaders(String locale) {
		equinoxContainer.checkAdminPermission(this, AdminPermission.METADATA);
		return privGetHeaders(locale);
	}

	private Dictionary<String, String> privGetHeaders(String locale) {
		Generation current = (Generation) module.getCurrentRevision().getRevisionInfo();
		return current.getHeaders(locale);
	}

	@Override
	public long getBundleId() {
		return module.getId();
	}

	@Override
	public String getLocation() {
		equinoxContainer.checkAdminPermission(getBundle(), AdminPermission.METADATA);
		return module.getLocation();
	}

	@Override
	public ServiceReference<?>[] getRegisteredServices() {
		checkValid();
		BundleContextImpl current = getBundleContextImpl();
		return current == null ? null : equinoxContainer.getServiceRegistry().getRegisteredServices(current);
	}

	@Override
	public ServiceReference<?>[] getServicesInUse() {
		checkValid();
		BundleContextImpl current = getBundleContextImpl();
		return current == null ? null : equinoxContainer.getServiceRegistry().getServicesInUse(current);
	}

	@Override
	public boolean hasPermission(Object permission) {
		Generation current = (Generation) module.getCurrentRevision().getRevisionInfo();
		ProtectionDomain domain = current.getDomain();
		if (domain != null) {
			if (permission instanceof Permission) {
				SecurityManager sm = System.getSecurityManager();
				if (sm instanceof EquinoxSecurityManager) {
					/*
					 * If the FrameworkSecurityManager is active, we need to do checks the "right" way.
					 * We can exploit our knowledge that the security context of FrameworkSecurityManager
					 * is an AccessControlContext to invoke it properly with the ProtectionDomain.
					 */
					AccessControlContext acc = new AccessControlContext(new ProtectionDomain[] {domain});
					try {
						sm.checkPermission((Permission) permission, acc);
						return true;
					} catch (Exception e) {
						return false;
					}
				}
				return domain.implies((Permission) permission);
			}
			return false;
		}
		return true;
	}

	@Override
	public URL getResource(String name) {
		try {
			equinoxContainer.checkAdminPermission(this, AdminPermission.RESOURCE);
		} catch (SecurityException e) {
			return null;
		}
		checkValid();
		if (isFragment()) {
			return null;
		}

		ModuleClassLoader classLoader = getModuleClassLoader(false);
		if (classLoader != null) {
			return classLoader.getResource(name);
		}

		return new ClasspathManager((Generation) module.getCurrentRevision().getRevisionInfo(), null).findLocalResource(name);
	}

	@Override
	public String getSymbolicName() {
		return module.getCurrentRevision().getSymbolicName();
	}

	@Override
	public Version getVersion() {
		return module.getCurrentRevision().getVersion();
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try {
			equinoxContainer.checkAdminPermission(this, AdminPermission.CLASS);
		} catch (SecurityException e) {
			throw new ClassNotFoundException(name, e);
		}
		checkValid();
		if (isFragment()) {
			throw new ClassNotFoundException("Can not load a class from a fragment bundle: " + this); //$NON-NLS-1$
		}
		try {
			ModuleClassLoader classLoader = getModuleClassLoader(true);
			if (classLoader != null) {
				if (name.length() > 0 && name.charAt(0) == '[')
					return Class.forName(name, false, classLoader);
				return classLoader.loadClass(name);
			}
		} catch (ClassNotFoundException e) {
			// This is an equinox-ism, check compatibility flag
			boolean compatibilityLazyTrigger = equinoxContainer.getConfiguration().compatibilityLazyTriggerOnFailLoad;
			// On failure attempt to activate lazy activating bundles.
			if (compatibilityLazyTrigger && State.LAZY_STARTING.equals(module.getState())) {
				try {
					module.start(StartOptions.LAZY_TRIGGER);
				} catch (BundleException e1) {
					equinoxContainer.getLogServices().log(EquinoxContainer.NAME, FrameworkLogEntry.WARNING, e.getMessage(), e);
				}
			}
			throw e;
		}
		throw new ClassNotFoundException("No class loader available for the bundle: " + this); //$NON-NLS-1$
	}

	private ModuleClassLoader getModuleClassLoader(boolean logResolveError) {
		ResolutionReport report = resolve();
		if (logResolveError && !Module.RESOLVED_SET.contains(module.getState())) {
			String reportMessage = report.getResolutionReportMessage(module.getCurrentRevision());
			equinoxContainer.getEventPublisher().publishFrameworkEvent(FrameworkEvent.ERROR, this, new BundleException(reportMessage, BundleException.RESOLVE_ERROR));
		}
		return AccessController.doPrivileged(new PrivilegedAction<ModuleClassLoader>() {
			@Override
			public ModuleClassLoader run() {
				ModuleWiring wiring = getModule().getCurrentRevision().getWiring();
				if (wiring != null) {
					ModuleLoader moduleLoader = wiring.getModuleLoader();
					if (moduleLoader instanceof BundleLoader) {
						return ((BundleLoader) moduleLoader).getModuleClassLoader();
					}
				}
				return null;
			}
		});
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		try {
			equinoxContainer.checkAdminPermission(this, AdminPermission.RESOURCE);
		} catch (SecurityException e) {
			return null;
		}
		checkValid();
		if (isFragment()) {
			return null;
		}
		ModuleClassLoader classLoader = getModuleClassLoader(false);
		Enumeration<URL> result = null;
		if (classLoader != null) {
			result = classLoader.getResources(name);
		} else {
			result = new ClasspathManager((Generation) module.getCurrentRevision().getRevisionInfo(), null).findLocalResources(name);
		}
		return result != null && result.hasMoreElements() ? result : null;
	}

	@Override
	public Enumeration<String> getEntryPaths(String path) {
		try {
			equinoxContainer.checkAdminPermission(this, AdminPermission.RESOURCE);
		} catch (SecurityException e) {
			return null;
		}
		checkValid();
		Generation current = (Generation) getModule().getCurrentRevision().getRevisionInfo();
		return current.getBundleFile().getEntryPaths(path);
	}

	@Override
	public URL getEntry(String path) {
		try {
			equinoxContainer.checkAdminPermission(this, AdminPermission.RESOURCE);
		} catch (SecurityException e) {
			return null;
		}
		checkValid();
		Generation current = (Generation) getModule().getCurrentRevision().getRevisionInfo();
		return current.getEntry(path);
	}

	@Override
	public long getLastModified() {
		return module.getLastModified();
	}

	@Override
	public Enumeration<URL> findEntries(String path, String filePattern, boolean recurse) {
		try {
			equinoxContainer.checkAdminPermission(this, AdminPermission.RESOURCE);
		} catch (SecurityException e) {
			return null;
		}
		checkValid();
		resolve();
		return Storage.findEntries(getGenerations(), path, filePattern, recurse ? BundleWiring.FINDENTRIES_RECURSE : 0);
	}

	@Override
	public BundleContext getBundleContext() {
		equinoxContainer.checkAdminPermission(this, AdminPermission.CONTEXT);
		return createBundleContext(true);
	}

	BundleContextImpl createBundleContext(boolean checkPermission) {
		if (isFragment()) {
			// fragments cannot have contexts
			return null;
		}
		synchronized (this.monitor) {
			if (context == null) {
				// only create the context if we are starting, active or stopping
				// this is so that SCR can get the context for lazy-start bundles
				if (Module.ACTIVE_SET.contains(module.getState())) {
					context = new BundleContextImpl(this, equinoxContainer);
				}
			}
			return context;
		}
	}

	private BundleContextImpl getBundleContextImpl() {
		synchronized (this.monitor) {
			return context;
		}
	}

	@Override
	public Map<X509Certificate, List<X509Certificate>> getSignerCertificates(int signersType) {
		SignedContentFactory factory = equinoxContainer.getSignedContentFactory();
		if (factory == null) {
			return Collections.emptyMap();
		}

		try {
			SignerInfo[] infos = signerInfos;
			if (infos == null) {
				SignedContent signedContent = factory.getSignedContent(this);
				infos = signedContent.getSignerInfos();
				signerInfos = infos;
			}
			if (infos.length == 0)
				return Collections.emptyMap();
			Map<X509Certificate, List<X509Certificate>> results = new HashMap<>(infos.length);
			for (int i = 0; i < infos.length; i++) {
				if (signersType == SIGNERS_TRUSTED && !infos[i].isTrusted())
					continue;
				Certificate[] certs = infos[i].getCertificateChain();
				if (certs == null || certs.length == 0)
					continue;
				List<X509Certificate> certChain = new ArrayList<>();
				for (int j = 0; j < certs.length; j++)
					certChain.add((X509Certificate) certs[j]);
				results.put((X509Certificate) certs[0], certChain);
			}
			return results;
		} catch (Exception e) {
			return Collections.emptyMap();
		}
	}

	@Override
	public final <A> A adapt(Class<A> adapterType) {
		checkAdaptPermission(adapterType);
		return adapt0(adapterType);
	}

	private void readLock() {
		equinoxContainer.getStorage().getModuleDatabase().readLock();
	}

	private void readUnlock() {
		equinoxContainer.getStorage().getModuleDatabase().readUnlock();
	}

	@SuppressWarnings("unchecked")
	private <A> A adapt0(Class<A> adapterType) {
		if (AccessControlContext.class.equals(adapterType)) {
			Generation current = (Generation) module.getCurrentRevision().getRevisionInfo();
			ProtectionDomain domain = current.getDomain();
			return (A) (domain == null ? null : new AccessControlContext(new ProtectionDomain[] {domain}));
		}

		if (BundleContext.class.equals(adapterType)) {
			try {
				return (A) getBundleContext();
			} catch (SecurityException e) {
				return null;
			}
		}

		if (BundleRevision.class.equals(adapterType)) {
			if (module.getState().equals(State.UNINSTALLED)) {
				return null;
			}
			return (A) module.getCurrentRevision();
		}

		if (BundleRevisions.class.equals(adapterType)) {
			return (A) module.getRevisions();
		}

		if (BundleStartLevel.class.equals(adapterType)) {
			return (A) module;
		}

		if (BundleWiring.class.equals(adapterType)) {
			if (module.getState().equals(State.UNINSTALLED)) {
				return null;
			}
			ModuleRevision revision = module.getCurrentRevision();
			if (revision == null) {
				return null;
			}
			return (A) revision.getWiring();
		}

		if (BundleDTO.class.equals(adapterType)) {
			// Unfortunately we need to lock here to make sure the BSN and version
			// are consistent in case of updates
			readLock();
			try {
				return (A) DTOBuilder.newBundleDTO(this);
			} finally {
				readUnlock();
			}
		}

		if (BundleStartLevelDTO.class.equals(adapterType)) {
			if (module.getState().equals(State.UNINSTALLED)) {
				return null;
			}
			return (A) DTOBuilder.newBundleStartLevelDTO(this, module);
		}

		if (BundleRevisionDTO.class.equals(adapterType)) {
			if (module.getState().equals(State.UNINSTALLED)) {
				return null;
			}
			return (A) DTOBuilder.newBundleRevisionDTO(module.getCurrentRevision());
		}

		if (BundleRevisionDTO[].class.equals(adapterType)) {
			if (module.getState().equals(State.UNINSTALLED)) {
				return null;
			}
			// No need to lock the database here since the ModuleRevisions object does the
			// proper locking for us.
			return (A) DTOBuilder.newArrayBundleRevisionDTO(module.getRevisions());
		}

		if (BundleWiringDTO.class.equals(adapterType)) {
			if (module.getState().equals(State.UNINSTALLED)) {
				return null;
			}
			readLock();
			try {
				return (A) DTOBuilder.newBundleWiringDTO(module.getCurrentRevision());
			} finally {
				readUnlock();
			}
		}

		if (BundleWiringDTO[].class.equals(adapterType)) {
			if (module.getState().equals(State.UNINSTALLED)) {
				return null;
			}
			readLock();
			try {
				return (A) DTOBuilder.newArrayBundleWiringDTO(module.getRevisions());
			} finally {
				readUnlock();
			}
		}

		if (ServiceReferenceDTO[].class.equals(adapterType)) {
			if (module.getState().equals(State.UNINSTALLED)) {
				return null;
			}
			BundleContextImpl current = getBundleContextImpl();
			ServiceReference<?>[] references = (current == null) ? null : equinoxContainer.getServiceRegistry().getRegisteredServices(current);
			return (A) DTOBuilder.newArrayServiceReferenceDTO(references);
		}

		if (getBundleId() == 0) {
			if (Framework.class.equals(adapterType)) {
				return (A) this;
			}

			if (FrameworkStartLevel.class.equals(adapterType)) {
				return (A) module.getContainer().getFrameworkStartLevel();
			}

			if (FrameworkWiring.class.equals(adapterType)) {
				return (A) module.getContainer().getFrameworkWiring();
			}

			if (FrameworkDTO.class.equals(adapterType)) {
				BundleContextImpl current = getBundleContextImpl();
				Map<String, String> configuration = equinoxContainer.getConfiguration().getConfiguration();
				readLock();
				try {
					return (A) DTOBuilder.newFrameworkDTO(current, configuration);
				} finally {
					readUnlock();
				}
			}

			if (FrameworkStartLevelDTO.class.equals(adapterType)) {
				return (A) DTOBuilder.newFrameworkStartLevelDTO(module.getContainer().getFrameworkStartLevel());
			}

			if (FrameworkWiringDTO.class.equals(adapterType)) {
				readLock();
				try {
					Set<BundleWiring> allWirings = new HashSet<>();
					for (Module m : module.getContainer().getModules()) {
						for (BundleRevision revision : m.getRevisions().getRevisions()) {
							BundleWiring wiring = revision.getWiring();
							if (wiring != null) {
								allWirings.add(wiring);
							}
						}
					}
					for (ModuleRevision revision : module.getContainer().getRemovalPending()) {
						BundleWiring wiring = revision.getWiring();
						if (wiring != null) {
							allWirings.add(wiring);
						}
					}
					return (A) DTOBuilder.newFrameworkWiringDTO(allWirings);
				} finally {
					readUnlock();
				}
			}
		}

		// Equinox extras
		if (Module.class.equals(adapterType)) {
			return (A) module;
		}
		if (ProtectionDomain.class.equals(adapterType)) {
			Generation current = (Generation) module.getCurrentRevision().getRevisionInfo();
			return (A) current.getDomain();
		}
		return null;
	}

	/**
	 * Check for permission to get a service.
	 */
	private <A> void checkAdaptPermission(Class<A> adapterType) {
		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			return;
		}
		sm.checkPermission(new AdaptPermission(adapterType.getName(), this, AdaptPermission.ADAPT));
	}

	@Override
	public File getDataFile(String filename) {
		checkValid();
		Generation current = (Generation) module.getCurrentRevision().getRevisionInfo();
		return current.getBundleInfo().getDataFile(filename);
	}

	@Override
	public Bundle getBundle() {
		return this;
	}

	public Module getModule() {
		return module;
	}

	private final void checkValid() {
		if (module.getState().equals(State.UNINSTALLED))
			throw new IllegalStateException("Module has been uninstalled."); //$NON-NLS-1$
	}

	public boolean isFragment() {
		return (getModule().getCurrentRevision().getTypes() & BundleRevision.TYPE_FRAGMENT) != 0;
	}

	void startWorker0() throws BundleException {
		BundleContextImpl current = createBundleContext(false);
		if (current == null) {
			throw new BundleException("Unable to create bundle context!"); //$NON-NLS-1$
		}
		try {
			current.start();
		} catch (BundleException e) {
			current.close();
			synchronized (EquinoxBundle.this.monitor) {
				context = null;
			}
			throw e;
		}
	}

	void stopWorker0() throws BundleException {
		BundleContextImpl current = getBundleContextImpl();
		if (current != null) {
			try {
				current.stop();
			} finally {
				current.close();
			}
			synchronized (EquinoxBundle.this.monitor) {
				context = null;
			}
		}
	}

	ResolutionReport resolve() {
		if (!Module.RESOLVED_SET.contains(module.getState())) {
			return module.getContainer().resolve(Arrays.asList(module), true);
		}
		return null;
	}

	List<Generation> getGenerations() {
		List<Generation> result = new ArrayList<>();
		ModuleRevision current = getModule().getCurrentRevision();
		result.add((Generation) current.getRevisionInfo());
		ModuleWiring wiring = current.getWiring();
		if (wiring != null) {
			List<ModuleWire> hostWires = wiring.getProvidedModuleWires(HostNamespace.HOST_NAMESPACE);
			if (hostWires != null) {
				for (ModuleWire hostWire : hostWires) {
					result.add((Generation) hostWire.getRequirer().getRevisionInfo());
				}
			}
		}
		return result;
	}

	EquinoxContainer getEquinoxContainer() {
		return equinoxContainer;
	}

	public String toString() {
		String name = getSymbolicName();
		if (name == null)
			name = "unknown"; //$NON-NLS-1$
		return (name + '_' + getVersion() + " [" + getBundleId() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
