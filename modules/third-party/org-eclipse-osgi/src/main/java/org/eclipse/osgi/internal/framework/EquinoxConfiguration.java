/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.internal.framework;

import static org.osgi.framework.Constants.FRAMEWORK_LANGUAGE;
import static org.osgi.framework.Constants.FRAMEWORK_OS_NAME;
import static org.osgi.framework.Constants.FRAMEWORK_OS_VERSION;
import static org.osgi.framework.Constants.FRAMEWORK_PROCESSOR;
import static org.osgi.framework.Constants.FRAMEWORK_STORAGE_CLEAN;
import static org.osgi.framework.Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT;
import static org.osgi.framework.Constants.FRAMEWORK_VENDOR;
import static org.osgi.framework.Constants.SUPPORTS_FRAMEWORK_EXTENSION;
import static org.osgi.framework.Constants.SUPPORTS_FRAMEWORK_FRAGMENT;
import static org.osgi.framework.Constants.SUPPORTS_FRAMEWORK_REQUIREBUNDLE;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.core.runtime.internal.adaptor.ConsoleManager;
import org.eclipse.osgi.internal.debug.Debug;
import org.eclipse.osgi.internal.debug.FrameworkDebugOptions;
import org.eclipse.osgi.internal.hookregistry.HookRegistry;
import org.eclipse.osgi.internal.location.EquinoxLocations;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.environment.Constants;
import org.eclipse.osgi.service.environment.EnvironmentInfo;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Version;

/**
 * Internal class.
 */
public class EquinoxConfiguration implements EnvironmentInfo {
	private static final String CONFIG_FILE = "config.ini"; //$NON-NLS-1$
	// While we recognize the SunOS operating system, we change
	// this internally to be Solaris.
	private static final String INTERNAL_OS_SUNOS = "SunOS"; //$NON-NLS-1$
	private static final String INTERNAL_OS_LINUX = "Linux"; //$NON-NLS-1$
	private static final String INTERNAL_OS_MACOSX = "Mac OS"; //$NON-NLS-1$
	private static final String INTERNAL_OS_AIX = "AIX"; //$NON-NLS-1$
	private static final String INTERNAL_OS_HPUX = "HP-UX"; //$NON-NLS-1$
	private static final String INTERNAL_OS_QNX = "QNX"; //$NON-NLS-1$
	private static final String INTERNAL_OS_OS400 = "OS/400"; //$NON-NLS-1$
	private static final String INTERNAL_OS_OS390 = "OS/390"; //$NON-NLS-1$
	private static final String INTERNAL_OS_ZOS = "z/OS"; //$NON-NLS-1$
	// While we recognize the i386 architecture, we change
	// this internally to be x86.
	private static final String INTERNAL_ARCH_I386 = "i386"; //$NON-NLS-1$
	// While we recognize the amd64 architecture, we change
	// this internally to be x86_64.
	private static final String INTERNAL_AMD64 = "amd64"; //$NON-NLS-1$

	public static final String VARIABLE_DELIM_STRING = "$"; //$NON-NLS-1$
	public static final char VARIABLE_DELIM_CHAR = '$';

	private final ConfigValues configValues;

	private final Debug debug;
	private final DebugOptions debugOptions;
	private final HookRegistry hookRegistry;
	private final AliasMapper aliasMapper = new AliasMapper();
	private final EquinoxLocations equinoxLocations;

	private volatile String[] allArgs;
	private volatile String[] frameworkArgs;
	private volatile String[] appArgs;

	// dev mode fields
	private final boolean inDevelopmentMode;
	private final File devLocation;
	private final Object devMonitor = new Object();
	private String[] devDefaultClasspath;
	private Dictionary<String, String> devProperties = null;
	// timestamp for the dev.properties file
	private long devLastModified = 0;

	public final boolean contextBootDelegation;
	public final boolean compatibilityBootDelegation;

	public final List<String> LIB_EXTENSIONS;
	public final List<String> ECLIPSE_LIB_VARIANTS;
	public final boolean COPY_NATIVES;
	public final List<String> ECLIPSE_NL_JAR_VARIANTS;
	public final boolean DEFINE_PACKAGE_ATTRIBUTES;
	public final boolean BUNDLE_SET_TCCL;

	public final int BSN_VERSION;
	public static final int BSN_VERSION_SINGLE = 1;
	public static final int BSN_VERSION_MULTIPLE = 2;
	public static final int BSN_VERSION_MANAGED = 3;

	public final boolean throwErrorOnFailedStart;

	public final boolean CLASS_CERTIFICATE;
	public final boolean PARALLEL_CAPABLE;

	// JVM os.arch property name
	public static final String PROP_JVM_OS_ARCH = "os.arch"; //$NON-NLS-1$
	// JVM os.name property name
	public static final String PROP_JVM_OS_NAME = "os.name"; //$NON-NLS-1$
	// JVM os.version property name
	public static final String PROP_JVM_OS_VERSION = "os.version"; //$NON-NLS-1$
	public static final String PROP_JVM_SPEC_VERSION = "java.specification.version"; //$NON-NLS-1$
	public static final String PROP_JVM_SPEC_NAME = "java.specification.name"; //$NON-NLS-1$
	// J2ME configuration property name
	public static final String PROP_J2ME_MICROEDITION_CONFIGURATION = "microedition.configuration"; //$NON-NLS-1$
	// J2ME profile property name
	public static final String PROP_J2ME_MICROEDITION_PROFILES = "microedition.profiles"; //$NON-NLS-1$

	public static final String PROP_SETPERMS_CMD = "osgi.filepermissions.command"; //$NON-NLS-1$
	public static final String PROP_DEBUG = "osgi.debug"; //$NON-NLS-1$
	public static final String PROP_DEBUG_VERBOSE = "osgi.debug.verbose"; //$NON-NLS-1$
	public static final String PROP_DEV = "osgi.dev"; //$NON-NLS-1$
	public static final String PROP_CLEAN = "osgi.clean"; //$NON-NLS-1$
	public static final String PROP_USE_SYSTEM_PROPERTIES = "osgi.framework.useSystemProperties"; //$NON-NLS-1$
	public static final String PROP_FRAMEWORK = "osgi.framework"; //$NON-NLS-1$

	public static final String ECLIPSE_FRAMEWORK_VENDOR = "Eclipse"; //$NON-NLS-1$

	public static final String PROP_OSGI_JAVA_PROFILE = "osgi.java.profile"; //$NON-NLS-1$
	public static final String PROP_OSGI_JAVA_PROFILE_NAME = "osgi.java.profile.name"; //$NON-NLS-1$ 
	// OSGi java profile bootdelegation; used to indicate how the org.osgi.framework.bootdelegation
	// property defined in the java profile should be processed, (ingnore, override, none). default is ignore
	public static final String PROP_OSGI_JAVA_PROFILE_BOOTDELEGATION = "osgi.java.profile.bootdelegation"; //$NON-NLS-1$
	// indicates that the org.osgi.framework.bootdelegation in the java profile should be ingored
	public static final String PROP_OSGI_BOOTDELEGATION_IGNORE = "ignore"; //$NON-NLS-1$
	// indicates that the org.osgi.framework.bootdelegation in the java profile should override the system property
	public static final String PROP_OSGI_BOOTDELEGATION_OVERRIDE = "override"; //$NON-NLS-1$
	// indicates that the org.osgi.framework.bootdelegation in the java profile AND the system properties should be ignored
	public static final String PROP_OSGI_BOOTDELEGATION_NONE = "none"; //$NON-NLS-1$

	public static final String PROP_CONTEXT_BOOTDELEGATION = "osgi.context.bootdelegation"; //$NON-NLS-1$
	public static final String PROP_COMPATIBILITY_BOOTDELEGATION = "osgi.compatibility.bootdelegation"; //$NON-NLS-1$
	public static final String PROP_COMPATIBILITY_ERROR_FAILED_START = "osgi.compatibility.errorOnFailedStart"; //$NON-NLS-1$
	public static final String PROP_COMPATIBILITY_START_LAZY = "osgi.compatibility.eagerStart.LazyActivation"; //$NON-NLS-1$

	public static final String PROP_OSGI_OS = "osgi.os"; //$NON-NLS-1$
	public static final String PROP_OSGI_WS = "osgi.ws"; //$NON-NLS-1$
	public static final String PROP_OSGI_ARCH = "osgi.arch"; //$NON-NLS-1$
	public static final String PROP_OSGI_NL = "osgi.nl"; //$NON-NLS-1$
	public static final String PROP_OSGI_NL_USER = "osgi.nl.user"; //$NON-NLS-1$

	public static final String PROP_ROOT_LOCALE = "equinox.root.locale"; //$NON-NLS-1$

	public static final String PROP_PARENT_CLASSLOADER = "osgi.parentClassloader"; //$NON-NLS-1$	
	// A parent classloader type that specifies the framework classlaoder
	public static final String PARENT_CLASSLOADER_FWK = "fwk"; //$NON-NLS-1$
	// System property used to set the context classloader parent classloader type (ccl is the default)
	public static final String PROP_CONTEXTCLASSLOADER_PARENT = "osgi.contextClassLoaderParent"; //$NON-NLS-1$
	public static final String CONTEXTCLASSLOADER_PARENT_APP = "app"; //$NON-NLS-1$
	public static final String CONTEXTCLASSLOADER_PARENT_EXT = "ext"; //$NON-NLS-1$
	public static final String CONTEXTCLASSLOADER_PARENT_BOOT = "boot"; //$NON-NLS-1$
	public static final String CONTEXTCLASSLOADER_PARENT_FWK = "fwk"; //$NON-NLS-1$

	public static final String PROP_FRAMEWORK_LIBRARY_EXTENSIONS = "osgi.framework.library.extensions"; //$NON-NLS-1$
	public static final String PROP_COPY_NATIVES = "osgi.classloader.copy.natives"; //$NON-NLS-1$
	public static final String PROP_DEFINE_PACKAGES = "osgi.classloader.define.packages"; //$NON-NLS-1$
	public static final String PROP_BUNDLE_SETTCCL = "eclipse.bundle.setTCCL"; //$NON-NLS-1$

	public static final String PROP_EQUINOX_SECURITY = "eclipse.security"; //$NON-NLS-1$
	public static final String PROP_FILE_LIMIT = "osgi.bundlefile.limit"; //$NON-NLS-1$

	public final static String PROP_CLASS_CERTIFICATE_SUPPORT = "osgi.support.class.certificate"; //$NON-NLS-1$
	public final static String PROP_CLASS_LOADER_TYPE = "osgi.classloader.type"; //$NON-NLS-1$
	public final static String CLASS_LOADER_TYPE_PARALLEL = "parallel"; //$NON-NLS-1$

	public static final String PROP_FORCED_RESTART = "osgi.forcedRestart"; //$NON-NLS-1$
	public static final String PROP_IGNORE_USER_CONFIGURATION = "eclipse.ignoreUserConfiguration"; //$NON-NLS-1$

	public static final String PROPERTY_STRICT_BUNDLE_ENTRY_PATH = "osgi.strictBundleEntryPath";//$NON-NLS-1$

	public static final String PROP_CHECK_CONFIGURATION = "osgi.checkConfiguration"; //$NON-NLS-1$
	private final boolean inCheckConfigurationMode;

	public static final String DEFAULT_STATE_SAVE_DELAY_INTERVAL = "30000"; //$NON-NLS-1$
	public static final String PROP_STATE_SAVE_DELAY_INTERVAL = "eclipse.stateSaveDelayInterval"; //$NON-NLS-1$

	public static final String PROP_MODULE_LOCK_TIMEOUT = "osgi.module.lock.timeout"; //$NON-NLS-1$
	public static final String PROP_ALLOW_RESTRICTED_PROVIDES = "osgi.equinox.allow.restricted.provides"; //$NON-NLS-1$
	public static final String PROP_LOG_HISTORY_MAX = "equinox.log.history.max"; //$NON-NLS-1$

	public static final String PROP_RESOLVER_REVISION_BATCH_SIZE = "equinox.resolver.revision.batch.size"; //$NON-NLS-1$

	public static final String PROP_OSGI_HOME = "osgi.home";

	public static final String PROP_DEFAULT_SUFFIX = ".default"; //$NON-NLS-1$
	public static final Collection<String> PROP_WITH_ECLIPSE_STARTER_DEFAULTS = Collections.singletonList(PROP_COMPATIBILITY_BOOTDELEGATION);

	public static final class ConfigValues {
		/**
		 * Value of {@link #configuration} properties that should be considered
		 * <code>null</code> and for which access should not fall back to
		 * {@link System#getProperty(String)}.
		 * The instance must be compared by identity (==, not equals) and must not
		 * be leaked outside this class.
		 */
		private final static String NULL_CONFIG = new String("org.eclipse.equinox.configuration.null.value"); //$NON-NLS-1$
		private final static Collection<String> populateInitConfig = Arrays.asList(PROP_OSGI_ARCH, PROP_OSGI_OS, PROP_OSGI_WS, PROP_OSGI_NL, FRAMEWORK_OS_NAME, FRAMEWORK_OS_VERSION, FRAMEWORK_PROCESSOR, FRAMEWORK_LANGUAGE);

		private final boolean useSystemProperties;
		private final Map<String, Object> initialConfig;
		private final Properties configuration;

		public ConfigValues(Map<String, ?> initialConfiguration) {
			this.initialConfig = initialConfiguration == null ? new HashMap<String, Object>(0) : new HashMap<String, Object>(initialConfiguration);
			Object useSystemPropsValue = initialConfig.get(PROP_USE_SYSTEM_PROPERTIES);
			this.useSystemProperties = useSystemPropsValue == null ? false : Boolean.parseBoolean(useSystemPropsValue.toString());
			this.configuration = useSystemProperties ? System.getProperties() : new Properties();
			// do this the hard way to handle null values
			for (Map.Entry<String, ?> initialEntry : this.initialConfig.entrySet()) {
				if (initialEntry.getValue() == null) {
					if (useSystemProperties) {
						this.configuration.remove(initialEntry.getKey());
					} else {
						this.configuration.put(initialEntry.getKey(), NULL_CONFIG);
					}
				} else {
					this.configuration.put(initialEntry.getKey(), initialEntry.getValue());
				}
			}
		}

		void loadConfigIni(URL configIni) {
			if (configIni != null) {
				mergeConfiguration(loadProperties(configIni));
			}
		}

		void finalizeValues() {
			// populate initConfig after loading the configIni
			for (String initialKey : populateInitConfig) {
				String value = getConfiguration(initialKey);
				if (value != null) {
					this.initialConfig.put(initialKey, value);
				}
			}

			// look for special case EclipseStarter defaults
			for (String keyWithEclipseStarterDefault : PROP_WITH_ECLIPSE_STARTER_DEFAULTS) {
				String currentValue = getConfiguration(keyWithEclipseStarterDefault);
				if (currentValue == null) {
					String defaultValue = getConfiguration(keyWithEclipseStarterDefault + PROP_DEFAULT_SUFFIX);
					if (defaultValue != null) {
						setConfiguration(keyWithEclipseStarterDefault, defaultValue);
					}
				}
			}

			// set other defaults
			if (FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT.equals(getConfiguration(FRAMEWORK_STORAGE_CLEAN))) {
				setConfiguration(PROP_CLEAN, "true"); //$NON-NLS-1$
			}

			if (getConfiguration(PROP_STATE_SAVE_DELAY_INTERVAL) == null)
				// Property not specified. Use the default.
				setConfiguration(PROP_STATE_SAVE_DELAY_INTERVAL, DEFAULT_STATE_SAVE_DELAY_INTERVAL);
			try {
				// Verify type compatibility.
				Long.parseLong(getConfiguration(PROP_STATE_SAVE_DELAY_INTERVAL));
			} catch (NumberFormatException e) {
				// TODO Consider logging here.
				// The specified value is not type compatible. Use the default.
				setConfiguration(PROP_STATE_SAVE_DELAY_INTERVAL, DEFAULT_STATE_SAVE_DELAY_INTERVAL);
			}

			String consoleProp = getConfiguration(ConsoleManager.PROP_CONSOLE);
			consoleProp = consoleProp == null ? null : consoleProp.trim();
			if (consoleProp == null || consoleProp.length() > 0) {
				// no -console was specified or it has specified none or a port for telnet;
				// need to make sure the gogo shell does not create an interactive console on standard in/out
				setConfiguration("gosh.args", "--nointeractive"); //$NON-NLS-1$//$NON-NLS-2$
			} else {
				// Need to make sure we don't shutdown the framework if no console is around (bug 362412)
				setConfiguration("gosh.args", "--noshutdown"); //$NON-NLS-1$//$NON-NLS-2$
			}
		}

		private void mergeConfiguration(Properties source) {
			for (Enumeration<?> e = source.keys(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				String value = source.getProperty(key);
				if (getConfiguration(key) == null) {
					setProperty(key, value);
					initialConfig.put(key, value);
				} else {
					initialConfig.put(key, getConfiguration(key));
				}
			}
		}

		private Properties loadProperties(URL location) {
			Properties result = new Properties();
			if (location == null)
				return result;
			try {
				InputStream in = location.openStream();
				try {
					result.load(in);
				} finally {
					in.close();
				}
			} catch (IOException e) {
				// its ok if there is no file.  We'll just use the defaults for everything
				// TODO but it might be nice to log something with gentle wording (i.e., it is not an error)
			}
			return substituteVars(result);
		}

		private Properties substituteVars(Properties result) {
			if (result == null) {
				//nothing todo.
				return null;
			}
			for (Enumeration<Object> eKeys = result.keys(); eKeys.hasMoreElements();) {
				Object key = eKeys.nextElement();
				if (key instanceof String) {
					String value = result.getProperty((String) key);
					if (value != null)
						result.put(key, substituteVars(value, true));
				}
			}
			return result;
		}

		public String substituteVars(String path, boolean preserveDelimiters) {
			StringBuffer buf = new StringBuffer(path.length());
			StringTokenizer st = new StringTokenizer(path, VARIABLE_DELIM_STRING, true);
			boolean varStarted = false; // indicates we are processing a var subtitute
			String var = null; // the current var key
			while (st.hasMoreElements()) {
				String tok = st.nextToken();
				if (VARIABLE_DELIM_STRING.equals(tok)) {
					if (!varStarted) {
						varStarted = true; // we found the start of a var
						var = ""; //$NON-NLS-1$
					} else {
						// we have found the end of a var
						String prop = null;
						// get the value of the var from system properties
						if (var != null && var.length() > 0)
							prop = getProperty(var);
						if (prop == null) {
							try {
								// try using the System.getenv method if it exists (bug 126921)
								Method getenv = System.class.getMethod("getenv", new Class[] {String.class}); //$NON-NLS-1$
								prop = (String) getenv.invoke(null, new Object[] {var});
							} catch (Throwable t) {
								// do nothing; 
								// on 1.4 VMs this throws an error
								// on J2ME this method does not exist
							}
						}
						if (prop != null) {
							// found a value; use it
							buf.append(prop);
						} else {
							// could not find a value append the var
							if (preserveDelimiters) {
								buf.append(VARIABLE_DELIM_CHAR);
							}
							buf.append(var == null ? "" : var); //$NON-NLS-1$
							if (preserveDelimiters) {
								buf.append(VARIABLE_DELIM_CHAR);
							}
						}
						varStarted = false;
						var = null;
					}
				} else {
					if (!varStarted)
						buf.append(tok); // the token is not part of a var
					else
						var = tok; // the token is the var key; save the key to process when we find the end token
				}
			}
			if (var != null)
				// found a case of $var at the end of the path with no trailing $; just append it as is.
				buf.append(VARIABLE_DELIM_CHAR).append(var);
			return buf.toString();
		}

		public String getProperty(String key) {
			// have to access configuration directly instead of using getConfiguration to get access to NULL_CONFIG values
			String result = configuration.getProperty(key);
			if (result == NULL_CONFIG) {
				return null;
			}
			return result == null ? System.getProperty(key) : result;
		}

		public String setProperty(String key, String value) {
			if (value == null) {
				return clearConfiguration(key);
			}
			return setConfiguration(key, value);
		}

		public String getConfiguration(String key) {
			String result = configuration.getProperty(key);
			return result == NULL_CONFIG ? null : result;
		}

		public String getConfiguration(String key, String defaultValue) {
			String result = getConfiguration(key);
			return result == null ? defaultValue : result;
		}

		public String setConfiguration(String key, String value) {
			Object result = configuration.put(key, value);
			return result instanceof String && result != NULL_CONFIG ? (String) result : null;
		}

		public String clearConfiguration(String key) {
			Object result = configuration.remove(key);
			if (!useSystemProperties) {
				configuration.put(key, NULL_CONFIG);
			}
			return result instanceof String && result != NULL_CONFIG ? (String) result : null;
		}

		public Map<String, String> getConfiguration() {
			// must sync on configuration to avoid concurrent modification exception
			synchronized (configuration) {
				Map<String, String> result = new HashMap<String, String>(configuration.size());
				for (Object key : configuration.keySet()) {
					if (key instanceof String) {
						String skey = (String) key;
						String sValue = configuration.getProperty(skey);
						if (sValue != NULL_CONFIG) {
							result.put(skey, sValue);
						}
					}
				}
				return result;
			}
		}

		public Map<String, Object> getInitialConfig() {
			return Collections.unmodifiableMap(initialConfig);
		}
	}

	EquinoxConfiguration(Map<String, ?> initialConfiguration, HookRegistry hookRegistry) {
		this.hookRegistry = hookRegistry;
		// Care must be taken to bootstrap of the config values properly
		// A separate class is used to hold the configuration maps so that we can pass them
		// to the EquionxLocations before the EquinoxConfiguration has been fully constructed
		this.configValues = new ConfigValues(initialConfiguration);

		// We need to initialize some properties always before constructing the EquinoxLocations
		initializeProperties();

		// At this point we do not know if we want to debug locations because we have not detemined if osgi.debug is set yet
		// We use an AttomicBoolean to hold the setting so we can set it after the config.ini has been loaded
		AtomicBoolean debugLocations = new AtomicBoolean();
		this.equinoxLocations = new EquinoxLocations(this.configValues, this.hookRegistry.getContainer(), debugLocations);
		this.configValues.loadConfigIni(getConfigIni(equinoxLocations));
		this.configValues.finalizeValues();

		this.debugOptions = new FrameworkDebugOptions(this);
		this.debug = new Debug(this.debugOptions);
		// finally we know if locations should be debugged.
		debugLocations.set(debug.DEBUG_LOCATION);

		// Finally we can set all our variables according to the configuration values

		String osgiDev = getConfiguration(PROP_DEV);
		File f = null;
		boolean devMode = false;
		if (osgiDev != null) {
			try {
				devMode = true;
				URL location = new URL(osgiDev);

				if ("file".equals(location.getProtocol())) { //$NON-NLS-1$
					f = new File(location.getFile());
					devLastModified = f.lastModified();
				}

				// Check the osgi.dev property to see if dev classpath entries have been defined.
				try {
					loadDevProperties(location.openStream());
					devMode = true;
				} catch (IOException e) {
					// TODO consider logging
				}

			} catch (MalformedURLException e) {
				devDefaultClasspath = getArrayFromList(osgiDev);
			}
		}
		inDevelopmentMode = devMode;
		devLocation = f;

		contextBootDelegation = "true".equals(getConfiguration(PROP_CONTEXT_BOOTDELEGATION, "true")); //$NON-NLS-1$ //$NON-NLS-2$
		compatibilityBootDelegation = "true".equals(getConfiguration(PROP_COMPATIBILITY_BOOTDELEGATION)); //$NON-NLS-1$

		COPY_NATIVES = Boolean.valueOf(getConfiguration(PROP_COPY_NATIVES)).booleanValue();
		String[] libExtensions = ManifestElement.getArrayFromList(getConfiguration(EquinoxConfiguration.PROP_FRAMEWORK_LIBRARY_EXTENSIONS, getConfiguration(org.osgi.framework.Constants.FRAMEWORK_LIBRARY_EXTENSIONS, getOSLibraryExtDefaults())), ","); //$NON-NLS-1$
		for (int i = 0; i < libExtensions.length; i++)
			if (libExtensions[i].length() > 0 && libExtensions[i].charAt(0) != '.')
				libExtensions[i] = '.' + libExtensions[i];
		LIB_EXTENSIONS = Collections.unmodifiableList(Arrays.asList(libExtensions));
		ECLIPSE_LIB_VARIANTS = buildEclipseLibraryVariants(getWS(), getOS(), getOSArch(), getNL());
		ECLIPSE_NL_JAR_VARIANTS = buildNLJarVariants(getNL());
		DEFINE_PACKAGE_ATTRIBUTES = !"noattributes".equals(getConfiguration(PROP_DEFINE_PACKAGES)); //$NON-NLS-1$

		String bsnVersion = getConfiguration(org.osgi.framework.Constants.FRAMEWORK_BSNVERSION);
		if (org.osgi.framework.Constants.FRAMEWORK_BSNVERSION_SINGLE.equals(bsnVersion)) {
			BSN_VERSION = BSN_VERSION_SINGLE;
		} else if (org.osgi.framework.Constants.FRAMEWORK_BSNVERSION_MULTIPLE.equals(bsnVersion)) {
			BSN_VERSION = BSN_VERSION_MULTIPLE;
		} else {
			BSN_VERSION = BSN_VERSION_MANAGED;
		}

		BUNDLE_SET_TCCL = "true".equals(getConfiguration(PROP_BUNDLE_SETTCCL, "true")); //$NON-NLS-1$ //$NON-NLS-2$

		throwErrorOnFailedStart = "true".equals(getConfiguration(PROP_COMPATIBILITY_ERROR_FAILED_START, "true")); //$NON-NLS-1$//$NON-NLS-2$

		CLASS_CERTIFICATE = Boolean.valueOf(getConfiguration(PROP_CLASS_CERTIFICATE_SUPPORT, "true")).booleanValue(); //$NON-NLS-1$
		PARALLEL_CAPABLE = CLASS_LOADER_TYPE_PARALLEL.equals(getConfiguration(PROP_CLASS_LOADER_TYPE));

		// A specified osgi.dev property but unspecified osgi.checkConfiguration
		// property implies osgi.checkConfiguration = true.
		inCheckConfigurationMode = Boolean.valueOf(getConfiguration(PROP_CHECK_CONFIGURATION, Boolean.toString(devMode)));
		// Must ensure the check configuration property is set if in osgi.dev mode (bug 443340)
		if (inCheckConfigurationMode && getConfiguration(PROP_CHECK_CONFIGURATION) == null) {
			setConfiguration(PROP_CHECK_CONFIGURATION, "true"); //$NON-NLS-1$
		}

		if (getConfiguration(PROP_OSGI_HOME) == null) {
			Location location = equinoxLocations.getInstallLocation();

			URL url = location.getURL();

			File file = new File(url.getPath());

			setConfiguration(PROP_OSGI_HOME, file.getParent());
		}
	}

	private URL getConfigIni(EquinoxLocations equinoxLocations2) {
		if (Boolean.TRUE.toString().equals(getConfiguration(EquinoxConfiguration.PROP_IGNORE_USER_CONFIGURATION)))
			return null;
		Location configArea = equinoxLocations.getConfigurationLocation();
		if (configArea == null)
			return null;
		try {
			return new URL(configArea.getURL().toExternalForm() + CONFIG_FILE);
		} catch (MalformedURLException e) {
			// its ok.  This should never happen
		}
		return null;
	}

	public Map<String, Object> getInitialConfig() {
		return this.configValues.getInitialConfig();
	}

	private static List<String> buildEclipseLibraryVariants(String ws, String os, String arch, String nl) {
		List<String> result = new ArrayList<String>();
		result.add("ws/" + ws + "/"); //$NON-NLS-1$ //$NON-NLS-2$
		result.add("os/" + os + "/" + arch + "/"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		result.add("os/" + os + "/"); //$NON-NLS-1$ //$NON-NLS-2$
		nl = nl.replace('_', '/');
		while (nl.length() > 0) {
			result.add("nl/" + nl + "/"); //$NON-NLS-1$ //$NON-NLS-2$
			int i = nl.lastIndexOf('/');
			nl = (i < 0) ? "" : nl.substring(0, i); //$NON-NLS-1$
		}
		result.add(""); //$NON-NLS-1$
		return Collections.unmodifiableList(result);
	}

	private static List<String> buildNLJarVariants(String nl) {
		List<String> result = new ArrayList<String>();
		nl = nl.replace('_', '/');
		while (nl.length() > 0) {
			result.add("nl/" + nl + "/"); //$NON-NLS-1$ //$NON-NLS-2$
			int i = nl.lastIndexOf('/');
			nl = (i < 0) ? "" : nl.substring(0, i); //$NON-NLS-1$
		}
		result.add(""); //$NON-NLS-1$
		return result;
	}

	private static String getOSLibraryExtDefaults() {
		// Some OSes have multiple library extensions
		// We should provide defaults to the known ones
		// For example Mac OS X uses dylib and jnilib (bug 380350)
		String os = System.getProperty(EquinoxConfiguration.PROP_JVM_OS_NAME);
		return os == null || !os.startsWith("Mac OS") ? null : "dylib,jnilib"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean inCheckConfigurationMode() {
		return inCheckConfigurationMode;
	}

	public boolean inDevelopmentMode() {
		return inDevelopmentMode;
	}

	public boolean inDebugMode() {
		return debugOptions.isDebugEnabled();
	}

	public String[] getCommandLineArgs() {
		return allArgs;
	}

	public String[] getFrameworkArgs() {
		return frameworkArgs;
	}

	public String[] getNonFrameworkArgs() {
		return appArgs;
	}

	public String getOSArch() {
		return getConfiguration(PROP_OSGI_ARCH);
	}

	public String getNL() {
		return getConfiguration(PROP_OSGI_NL);
	}

	public String getOS() {
		return getConfiguration(PROP_OSGI_OS);
	}

	public String getWS() {
		return getConfiguration(PROP_OSGI_WS);
	}

	public void setAllArgs(String[] allArgs) {
		// do not check if this is set already to allow arguments to change when multiple applications are launched
		this.allArgs = allArgs;
	}

	public void setAppArgs(String[] appArgs) {
		// do not check if this is set already to allow arguments to change when multiple applications are launched
		this.appArgs = appArgs;
	}

	public void setFrameworkArgs(String[] frameworkArgs) {
		// do not check if this is set already to allow arguments to change when multiple applications are launched
		this.frameworkArgs = frameworkArgs;
	}

	public static String guessWS(String osName) {
		// setup default values for known OSes if nothing was specified
		if (osName.equals(Constants.OS_WIN32))
			return Constants.WS_WIN32;
		if (osName.equals(Constants.OS_LINUX))
			return Constants.WS_GTK;
		if (osName.equals(Constants.OS_MACOSX))
			return Constants.WS_COCOA;
		if (osName.equals(Constants.OS_HPUX))
			return Constants.WS_MOTIF;
		if (osName.equals(Constants.OS_AIX))
			return Constants.WS_MOTIF;
		if (osName.equals(Constants.OS_SOLARIS))
			return Constants.WS_GTK;
		if (osName.equals(Constants.OS_QNX))
			return Constants.WS_PHOTON;
		return Constants.WS_UNKNOWN;
	}

	public static String guessOS(String osName) {
		// check to see if the OS name is "Windows 98" or some other
		// flavour which should be converted to win32.
		if (osName.regionMatches(true, 0, Constants.OS_WIN32, 0, 3))
			return Constants.OS_WIN32;
		// EXCEPTION: All mappings of SunOS convert to Solaris
		if (osName.equalsIgnoreCase(INTERNAL_OS_SUNOS))
			return Constants.OS_SOLARIS;
		if (osName.equalsIgnoreCase(INTERNAL_OS_LINUX))
			return Constants.OS_LINUX;
		if (osName.equalsIgnoreCase(INTERNAL_OS_QNX))
			return Constants.OS_QNX;
		if (osName.equalsIgnoreCase(INTERNAL_OS_AIX))
			return Constants.OS_AIX;
		if (osName.equalsIgnoreCase(INTERNAL_OS_HPUX))
			return Constants.OS_HPUX;
		if (osName.equalsIgnoreCase(INTERNAL_OS_OS400))
			return Constants.OS_OS400;
		if (osName.equalsIgnoreCase(INTERNAL_OS_OS390))
			return Constants.OS_OS390;
		if (osName.equalsIgnoreCase(INTERNAL_OS_ZOS))
			return Constants.OS_ZOS;
		// os.name on Mac OS can be either Mac OS or Mac OS X
		if (osName.regionMatches(true, 0, INTERNAL_OS_MACOSX, 0, INTERNAL_OS_MACOSX.length()))
			return Constants.OS_MACOSX;
		return Constants.OS_UNKNOWN;
	}

	public String getConfiguration(String key) {
		return this.configValues.getConfiguration(key);
	}

	public String getConfiguration(String key, String defaultValue) {
		return this.configValues.getConfiguration(key, defaultValue);
	}

	public String setConfiguration(String key, String value) {
		return this.configValues.setConfiguration(key, value);
	}

	public String clearConfiguration(String key) {
		return this.configValues.clearConfiguration(key);
	}

	public Map<String, String> getConfiguration() {
		return this.configValues.getConfiguration();
	}

	public Debug getDebug() {
		return this.debug;
	}

	public DebugOptions getDebugOptions() {
		return this.debugOptions;
	}

	public HookRegistry getHookRegistry() {
		return hookRegistry;
	}

	@Override
	public String getProperty(String key) {
		return this.configValues.getProperty(key);
	}

	@Override
	public String setProperty(String key, String value) {
		return this.configValues.setProperty(key, value);
	}

	public AliasMapper getAliasMapper() {
		return aliasMapper;
	}

	/*
	 * Updates the dev classpath if the file containing the entries have changed
	 */
	private void updateDevProperties() {
		if (devLocation == null)
			return;
		synchronized (devMonitor) {
			if (devLocation.lastModified() == devLastModified)
				return;

			try {
				loadDevProperties(new FileInputStream(devLocation));
			} catch (FileNotFoundException e) {
				return;
			}
			devLastModified = devLocation.lastModified();
		}
	}

	private static String[] getDevClassPath(String id, Dictionary<String, String> properties, String[] defaultClasspath) {
		String[] result = null;
		if (id != null && properties != null) {
			String entry = properties.get(id);
			if (entry != null)
				result = getArrayFromList(entry);
		}
		if (result == null)
			result = defaultClasspath;
		return result;
	}

	/**
	 * Returns a list of classpath elements for the specified bundle symbolic name.
	 * @param id a bundle symbolic name to get the development classpath for
	 * @param properties a Dictionary of properties to use or <code>null</code> if
	 * the default develoment classpath properties should be used
	 * @return a list of development classpath elements
	 */
	public String[] getDevClassPath(String id, Dictionary<String, String> properties) {
		if (properties == null) {
			synchronized (devMonitor) {
				updateDevProperties();
				return getDevClassPath(id, devProperties, devDefaultClasspath);
			}
		}
		return getDevClassPath(id, properties, getArrayFromList(properties.get("*"))); //$NON-NLS-1$
	}

	/**
	 * Returns a list of classpath elements for the specified bundle symbolic name.
	 * @param id a bundle symbolic name to get the development classpath for
	 * @return a list of development classpath elements
	 */
	public String[] getDevClassPath(String id) {
		return getDevClassPath(id, null);
	}

	private static String[] getArrayFromList(String prop) {
		return ManifestElement.getArrayFromList(prop, ","); //$NON-NLS-1$
	}

	/*
	 * Load the given input stream into a dictionary
	 */
	private void loadDevProperties(InputStream input) {
		Properties props = new Properties();
		try {
			props.load(input);
		} catch (IOException e) {
			// TODO consider logging here
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					// tried our best
				}
		}
		@SuppressWarnings({"unchecked", "rawtypes"})
		Dictionary<String, String> result = (Dictionary) props;
		synchronized (devMonitor) {
			devProperties = result;
			if (devProperties != null)
				devDefaultClasspath = getArrayFromList(devProperties.get("*")); //$NON-NLS-1$
		}
	}

	private void initializeProperties() {
		// initialize some framework properties that must always be set AND cannot be set with config.ini
		if (getConfiguration(PROP_FRAMEWORK) == null || getConfiguration(EquinoxLocations.PROP_INSTALL_AREA) == null) {
			ProtectionDomain pd = EquinoxConfiguration.class.getProtectionDomain();
			CodeSource cs = pd == null ? null : pd.getCodeSource();
			URL url = cs == null ? null : cs.getLocation();
			if (url == null) {
				IOException cause = null;
				// try to determine by loading a resource we know we have
				URL java6Profile = EquinoxConfiguration.class.getResource("/JavaSE-1.6.profile"); //$NON-NLS-1$
				if (java6Profile != null && "jar".equals(java6Profile.getProtocol())) { //$NON-NLS-1$
					try {
						url = ((JarURLConnection) java6Profile.openConnection()).getJarFileURL();
					} catch (IOException e) {
						cause = e;
					}
				}
				if (url == null) {
					throw new IllegalArgumentException(NLS.bind(Msg.ECLIPSE_STARTUP_PROPS_NOT_SET, PROP_FRAMEWORK + ", " + EquinoxLocations.PROP_INSTALL_AREA), cause); //$NON-NLS-1$
				}
			}

			// allow props to be preset
			if (getConfiguration(PROP_FRAMEWORK) == null) {
				String externalForm = getFrameworkPath(url.toExternalForm(), false);
				setConfiguration(PROP_FRAMEWORK, externalForm);
			}
			if (getConfiguration(EquinoxLocations.PROP_INSTALL_AREA) == null) {
				String filePart = getFrameworkPath(url.getFile(), true);
				setConfiguration(EquinoxLocations.PROP_INSTALL_AREA, filePart);
			}
		}
		// always decode these properties
		setConfiguration(PROP_FRAMEWORK, decode(getConfiguration(PROP_FRAMEWORK)));
		setConfiguration(EquinoxLocations.PROP_INSTALL_AREA, decode(getConfiguration(EquinoxLocations.PROP_INSTALL_AREA)));

		setConfiguration(FRAMEWORK_VENDOR, ECLIPSE_FRAMEWORK_VENDOR);
		String value = getConfiguration(FRAMEWORK_PROCESSOR);
		if (value == null) {
			value = System.getProperty(PROP_JVM_OS_ARCH);
			if (value != null) {
				setConfiguration(FRAMEWORK_PROCESSOR, aliasMapper.getCanonicalProcessor(value));
			}
		}

		value = getConfiguration(FRAMEWORK_OS_NAME);
		if (value == null) {
			value = System.getProperty(PROP_JVM_OS_NAME);
			if (value != null) {
				setConfiguration(FRAMEWORK_OS_NAME, aliasMapper.getCanonicalOSName(value));
			}
		}

		value = getConfiguration(FRAMEWORK_OS_VERSION);
		if (value == null) {
			value = System.getProperty(PROP_JVM_OS_VERSION);
			if (value != null) {
				// only use the value upto the first space
				int space = value.indexOf(' ');
				if (space > 0) {
					value = value.substring(0, space);
				}
				// fix up cases where the os version does not make a valid Version string.
				int major = 0, minor = 0, micro = 0;
				String qualifier = ""; //$NON-NLS-1$
				try {
					StringTokenizer st = new StringTokenizer(value, ".", true); //$NON-NLS-1$
					major = parseVersionInt(st.nextToken());

					if (st.hasMoreTokens()) {
						st.nextToken(); // consume delimiter
						minor = parseVersionInt(st.nextToken());

						if (st.hasMoreTokens()) {
							st.nextToken(); // consume delimiter
							micro = parseVersionInt(st.nextToken());

							if (st.hasMoreTokens()) {
								st.nextToken(); // consume delimiter
								qualifier = st.nextToken();
							}
						}
					}
				} catch (NoSuchElementException e) {
					// ignore, use the values parsed so far
				}
				try {
					value = new Version(major, minor, micro, qualifier).toString();
				} catch (IllegalArgumentException e) {
					// must be an invalid qualifier; just ignore it
					value = new Version(major, minor, micro).toString();
				}
				setConfiguration(FRAMEWORK_OS_VERSION, value);
			}
		}
		value = getConfiguration(FRAMEWORK_LANGUAGE);
		if (value == null)
			// set the value of the framework language property
			setConfiguration(FRAMEWORK_LANGUAGE, Locale.getDefault().getLanguage());
		// set the support properties for fragments and require-bundle (bug 173090)
		setConfiguration(SUPPORTS_FRAMEWORK_FRAGMENT, "true"); //$NON-NLS-1$
		setConfiguration(SUPPORTS_FRAMEWORK_REQUIREBUNDLE, "true"); //$NON-NLS-1$
		setConfiguration(SUPPORTS_FRAMEWORK_EXTENSION, "true"); //$NON-NLS-1$

		/*
		 * Initializes the execution context for this run of the platform.  The context
		 * includes information about the locale, operating system and window system.
		 * 
		 * NOTE: The OS, WS, and ARCH values should never be null. The executable should
		 * be setting these values and therefore this code path is obsolete for Eclipse
		 * when run from the executable.
		 */

		// if the user didn't set the locale with a command line argument then use the default.
		String nlValue = getConfiguration(PROP_OSGI_NL);
		if (nlValue != null) {
			Locale userLocale = toLocale(nlValue, Locale.getDefault());
			Locale.setDefault(userLocale);
			// TODO what the heck is this for?? why not just use osgi.nl
			setConfiguration(PROP_OSGI_NL_USER, nlValue);
		}
		nlValue = Locale.getDefault().toString();
		setConfiguration(PROP_OSGI_NL, nlValue);

		// if the user didn't set the operating system with a command line 
		// argument then use the default.
		String osValue = getConfiguration(PROP_OSGI_OS);
		if (osValue == null) {
			osValue = guessOS(System.getProperty(PROP_JVM_OS_NAME));
			setConfiguration(PROP_OSGI_OS, osValue);
		}

		// if the user didn't set the window system with a command line 
		// argument then use the default.
		String wsValue = getConfiguration(PROP_OSGI_WS);
		if (wsValue == null) {
			wsValue = guessWS(osValue);
			setConfiguration(PROP_OSGI_WS, wsValue);
		}

		// if the user didn't set the system architecture with a command line 
		// argument then use the default.
		String archValue = getConfiguration(PROP_OSGI_ARCH);
		if (archValue == null) {
			String name = System.getProperty(PROP_JVM_OS_ARCH);
			// Map i386 architecture to x86
			if (name.equalsIgnoreCase(INTERNAL_ARCH_I386))
				archValue = Constants.ARCH_X86;
			// Map amd64 architecture to x86_64
			else if (name.equalsIgnoreCase(INTERNAL_AMD64))
				archValue = Constants.ARCH_X86_64;
			else
				archValue = name;
			setConfiguration(PROP_OSGI_ARCH, archValue);
		}
	}

	private static String getFrameworkPath(String path, boolean parent) {
		if (File.separatorChar == '\\') {
			// in case on windows the \ is used
			path = path.replace('\\', '/');
		}
		// TODO is there a better way?
		// this is a hack to get the framework to launch from a self-hosted eclipse instance
		// we assume the code source will end in the path org.eclipse.osgi/bin/
		if (path.endsWith("org.eclipse.osgi/bin/")) { //$NON-NLS-1$
			path = path.substring(0, path.length() - "bin/".length()); //$NON-NLS-1$
		}
		if (parent) {
			int lastSlash = path.lastIndexOf('/');
			return lastSlash == -1 ? "/" : path.substring(0, lastSlash); //$NON-NLS-1$
		}
		return path;
	}

	private static int parseVersionInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			// try up to the first non-number char
			StringBuffer sb = new StringBuffer(value.length());
			char[] chars = value.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (!Character.isDigit(chars[i]))
					break;
				sb.append(chars[i]);
			}
			if (sb.length() > 0)
				return Integer.parseInt(sb.toString());
			return 0;
		}
	}

	public static String decode(String urlString) {
		//first encode '+' characters, because URLDecoder incorrectly converts 
		//them to spaces on certain class library implementations.
		if (urlString.indexOf('+') >= 0) {
			int len = urlString.length();
			StringBuffer buf = new StringBuffer(len);
			for (int i = 0; i < len; i++) {
				char c = urlString.charAt(i);
				if (c == '+')
					buf.append("%2B"); //$NON-NLS-1$
				else
					buf.append(c);
			}
			urlString = buf.toString();
		}
		try {
			return URLDecoder.decode(urlString, "UTF-8"); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			// Tried but failed
			// TODO should we throw runtime exception here?
			return urlString;
		}
	}

	public String substituteVars(String path) {
		return substituteVars(path, false);
	}

	public String substituteVars(String path, boolean preserveDelimiters) {
		return this.configValues.substituteVars(path, preserveDelimiters);
	}

	/**
	 * <p>
	 * Converts a String to a Locale.
	 * </p>
	 * 
	 * <p>
	 * This method takes the string format of a locale and creates the locale object from it.
	 * </p>
	 * 
	 * <p>
	 * This method validates the input strictly. The language code must be lowercase. The country
	 * code must be uppercase. The separator must be an underscore. The length must be correct.
	 * </p>
	 * 
	 * <p>
	 * This method is inspired by <code>org.apache.commons.lang.LocaleUtils.toLocale(String)</code>
	 * by fixing the parsing error for uncommon Locales like having a language and a variant code
	 * but no country code, or a Locale that only consists of a country code.
	 * </p>
	 * 
	 * @param str
	 *            the locale String to convert
	 * @param defaultLocale
	 *            the Locale that should be returned in case of an invalid Locale String
	 * @return a Locale that matches the specified locale String. If the given input String is
	 *         <code>null</code> or can not be parsed because of an invalid format, the given
	 *         default {@link Locale} will be returned.
	 */
	public static Locale toLocale(String str, Locale defaultLocale) {
		if (str == null) {
			System.err.println("Given locale String is null - Default Locale will be used instead."); //$NON-NLS-1$
			return defaultLocale;
		}

		String language = ""; //$NON-NLS-1$
		String country = ""; //$NON-NLS-1$
		String variant = ""; //$NON-NLS-1$

		String[] localeParts = str.split("_"); //$NON-NLS-1$
		if (localeParts.length == 0 || localeParts.length > 3 || (localeParts.length == 1 && localeParts[0].length() == 0)) {
			System.err.println(NLS.bind(Msg.error_badNL, str));
			return defaultLocale;
		}

		if (localeParts[0].length() > 0 && !localeParts[0].matches("[a-zA-Z]{2,8}")) { //$NON-NLS-1$
			System.err.println(NLS.bind(Msg.error_badNL, str));
			return defaultLocale;
		}

		language = localeParts[0];

		if (localeParts.length > 1) {
			if (localeParts[1].length() > 0 && !localeParts[1].matches("[a-zA-Z]{2}|[0-9]{3}")) { //$NON-NLS-1$
				if (language.length() > 0) {
					System.err.println(NLS.bind(Msg.error_badNL_language, str));
					return new Locale(language);
				}
				System.err.println(NLS.bind(Msg.error_badNL, str));
				return defaultLocale;
			}

			country = localeParts[1];
		}

		if (localeParts.length == 3) {
			if (localeParts[2].length() == 0) {
				System.err.println(NLS.bind(Msg.error_badNL_language_country, str));
				return new Locale(language, country);
			}
			variant = localeParts[2];
		}

		return new Locale(language, country, variant);
	}

	public EquinoxLocations getEquinoxLocations() {
		return equinoxLocations;
	}
}
