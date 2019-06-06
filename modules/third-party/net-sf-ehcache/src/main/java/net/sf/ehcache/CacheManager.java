/**
 *  Copyright Terracotta, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.sf.ehcache;

import net.sf.ehcache.cluster.CacheCluster;
import net.sf.ehcache.cluster.ClusterScheme;
import net.sf.ehcache.cluster.ClusterSchemeNotAvailableException;
import net.sf.ehcache.cluster.NoopCacheCluster;
import net.sf.ehcache.concurrent.ConcurrencyUtil;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.ConfigurationHelper;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.InvalidConfigurationException;
import net.sf.ehcache.config.ManagementRESTServiceConfiguration;
import net.sf.ehcache.config.SizeOfPolicyConfiguration;
import net.sf.ehcache.config.generator.ConfigurationSource;
import net.sf.ehcache.config.generator.ConfigurationUtil;
import net.sf.ehcache.distribution.CacheManagerPeerListener;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.event.CacheManagerEventListenerRegistry;
import net.sf.ehcache.management.event.DelegatingManagementEventSink;
import net.sf.ehcache.management.event.ManagementEventSink;
import net.sf.ehcache.management.ManagementServerLoader;
import net.sf.ehcache.management.provider.MBeanRegistrationProvider;
import net.sf.ehcache.management.provider.MBeanRegistrationProviderException;
import net.sf.ehcache.management.provider.MBeanRegistrationProviderFactory;
import net.sf.ehcache.management.provider.MBeanRegistrationProviderFactoryImpl;
import net.sf.ehcache.pool.Pool;
import net.sf.ehcache.pool.PoolEvictor;
import net.sf.ehcache.pool.SizeOfEngine;
import net.sf.ehcache.pool.SizeOfEngineLoader;
import net.sf.ehcache.pool.impl.BalancedAccessEvictor;
import net.sf.ehcache.pool.impl.BoundedPool;
import net.sf.ehcache.store.Store;
import net.sf.ehcache.terracotta.ClusteredInstanceFactory;
import net.sf.ehcache.terracotta.TerracottaClient;
import net.sf.ehcache.transaction.DelegatingTransactionIDFactory;
import net.sf.ehcache.transaction.ReadCommittedSoftLockFactory;
import net.sf.ehcache.transaction.SoftLockFactory;
import net.sf.ehcache.transaction.SoftLockManager;
import net.sf.ehcache.transaction.SoftLockManagerImpl;
import net.sf.ehcache.transaction.TransactionIDFactory;
import net.sf.ehcache.transaction.manager.TransactionManagerLookup;
import net.sf.ehcache.transaction.xa.processor.XARequestProcessor;
import net.sf.ehcache.util.FailSafeTimer;
import net.sf.ehcache.util.PropertyUtil;
import net.sf.ehcache.writer.writebehind.WriteBehind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.statistics.StatisticsManager;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A container for {@link Ehcache}s that maintain all aspects of their lifecycle.
 * <p>
 * CacheManager may be either be a singleton if created with factory methods, or multiple instances may exist, in which case resources
 * required by each must be unique.
 * <p>
 * A CacheManager holds references to Caches and Ehcaches and manages their creation and lifecycle.
 *
 * @author Greg Luck
 * @version $Id: CacheManager.java 10789 2018-04-26 02:08:13Z adahanne $
 */
public class CacheManager {

    /**
     * Default name if not specified in the configuration/
     */
    public static final String DEFAULT_NAME = "__DEFAULT__";

    /**
     * Threshold, in percent of the available heap, above which the CacheManager will warn if the configured memory
     */
    public static final double ON_HEAP_THRESHOLD = 0.8;

    /**
     * Keeps track of all known CacheManagers. Used to check on conflicts.
     * CacheManagers should remove themselves from this list during shut down.
     */
    public static final List<CacheManager> ALL_CACHE_MANAGERS = new CopyOnWriteArrayList<CacheManager>();

    /**
     * System property to enable creation of a shutdown hook for CacheManager.
     */
    public static final String ENABLE_SHUTDOWN_HOOK_PROPERTY = "net.sf.ehcache.enableShutdownHook";
    

    private static final Logger LOG = LoggerFactory.getLogger(CacheManager.class);

   /**
     * Update check interval - one week in milliseconds
     */
    private static final long EVERY_WEEK = 7 * 24 * 60 * 60 * 1000;

    /**
     * delay period before doing update check
     */
    private static final long DELAY_UPDATE_CHECK = 1000;

    /**
     * Default timeout in seconds to wait for stats thread pool to shutdown
     */
    private static final int POOL_SHUTDOWN_TIMEOUT_SECS = 60;

   /**
     * The Singleton Instance.
     */
    private static volatile CacheManager singleton;

    /**
     * The factory to use for creating MBeanRegistrationProvider's
     */
    private static final MBeanRegistrationProviderFactory MBEAN_REGISTRATION_PROVIDER_FACTORY = new MBeanRegistrationProviderFactoryImpl();

    private static final String NO_DEFAULT_CACHE_ERROR_MSG = "Caches cannot be added by name when default cache config is not specified"
            + " in the config. Please add a default cache config in the configuration.";

    private static final Map<String, CacheManager> CACHE_MANAGERS_MAP = new HashMap<String, CacheManager>();

    private static final IdentityHashMap<CacheManager, String> CACHE_MANAGERS_REVERSE_MAP = new IdentityHashMap<CacheManager, String>();

    private static final Map<String, CacheManager> INITIALIZING_CACHE_MANAGERS_MAP = new ConcurrentHashMap<String, CacheManager>();

    private static final long LOCAL_TX_RECOVERY_THREAD_JOIN_TIMEOUT = 1000L;
    
    static final String LOCAL_CACHE_NAME_PREFIX = "local_shadow_cache_for_";

    /**
     * Status of the Cache Manager
     */
    protected volatile Status status;

    /**
     * The map of providers
     */
    protected final Map<String, CacheManagerPeerProvider> cacheManagerPeerProviders = new ConcurrentHashMap<String, CacheManagerPeerProvider>();

    /**
     * The map of listeners
     */
    protected final Map<String, CacheManagerPeerListener> cacheManagerPeerListeners = new ConcurrentHashMap<String, CacheManagerPeerListener>();

    /**
     * The listener registry
     */
    protected final CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry = new CacheManagerEventListenerRegistry();

    /**
     * The shutdown hook thread for CacheManager. This ensures that the CacheManager and Caches are left in a
     * consistent state on a CTRL-C or kill.
     * <p>
     * This thread must be unregistered as a shutdown hook, when the CacheManager is disposed. Otherwise the CacheManager is not GC-able.
     * <p>
     * Of course kill -9 or abrupt termination will not run the shutdown hook. In this case, various sanity checks are made at start up.
     */
    protected Thread shutdownHook;

    /**
     * Ehcaches managed by this manager.
     */
    private final ConcurrentMap<String, Ehcache> ehcaches = new ConcurrentHashMap<String, Ehcache>();

    private final Map<String, Ehcache> initializingCaches = new ConcurrentHashMap<String, Ehcache>();


    /**
     * Default cache cache.
     */
    private Ehcache defaultCache;

    /**
     * The path for the directory in which disk caches are created.
     */
    private DiskStorePathManager diskStorePathManager;

    private volatile FeaturesManager featuresManager;

    private MBeanRegistrationProvider mbeanRegistrationProvider;

    private volatile TerracottaClient terracottaClient;

    private volatile TransactionManagerLookup transactionManagerLookup;

    private volatile TransactionController transactionController;

    private final ConcurrentMap<String, SoftLockManager> softLockManagers = new ConcurrentHashMap<String, SoftLockManager>();

    private volatile Pool onHeapPool;

    private volatile Pool onDiskPool;

    private volatile Configuration.RuntimeCfg runtimeCfg;

    private volatile DelegatingTransactionIDFactory transactionIDFactory;

    private volatile ManagementEventSink managementEventSink;

    private String registeredMgmtSvrBind;

    /**
     * Statistics thread pool.
     */
    private ScheduledExecutorService statisticsExecutor;

   /**
     * An constructor for CacheManager, which takes a configuration object, rather than one created by parsing
     * an ehcache.xml file. This constructor gives complete control over the creation of the CacheManager.
     * <p>
     * Care should be taken to ensure that, if multiple CacheManages are created, they do now overwrite each others disk store files, as
     * would happend if two were created which used the same diskStore path.
     * <p>
     * This method does not act as a singleton. Callers must maintain their own reference to it.
     * <p>
     * Note that if one of the {@link #create()} methods are called, a new singleton instance will be created, separate from any instances
     * created in this method.
     *
     * Since 2.5, every newly created CacheManager is registered with its name (uses a default name if unnamed), and trying to create
     * multiple CacheManager with same names (or multiple unnamed CacheManagers) is not allowed and throws an exception. It is recommended
     * to use one of the {@link #newInstance()} methods to instantiate new CacheManagers as those methods return the same instance of
     * CacheManager for same names (or unnamed). Shutting down the CacheManager will deregister it and new ones can be created again.
     *
     * @param configuration
     * @throws CacheException
     */
    public CacheManager(Configuration configuration) throws CacheException {
        status = Status.STATUS_UNINITIALISED;
        init(configuration, null, null, null);
    }

    /**
     * An ordinary constructor for CacheManager.
     * This method does not act as a singleton. Callers must maintain a reference to it.
     * Note that if one of the {@link #create()} methods are called, a new singleton will be created,
     * separate from any instances created in this method.
     *
     * Since 2.5, every newly created CacheManager is registered with its name (uses a default name if unnamed), and trying to create
     * multiple
     * CacheManager with same names (or multiple unnamed CacheManagers) is not allowed and throws an exception. Using any of the
     * {@link #newInstance()} methods also registers the CacheManager with its name.
     * It is recommended to use one of the {@link #newInstance()} methods to instantiate new CacheManagers as those methods return the same
     * instance
     * of CacheManager for same names (or unnamed). Shutting down the CacheManager will deregister it and new ones can be created again.
     *
     * @param configurationFileName
     *            an xml configuration file available through a file name. The configuration {@link File} is created
     *            using new <code>File(configurationFileName)</code>
     * @throws CacheException
     * @see #newInstance(String)
     */
    public CacheManager(String configurationFileName) throws CacheException {
        status = Status.STATUS_UNINITIALISED;
        init(null, configurationFileName, null, null);
    }

    /**
     * An ordinary constructor for CacheManager.
     * This method does not act as a singleton. Callers must maintain a reference to it.
     * Note that if one of the {@link #create()} methods are called, a new singleton will be created,
     * separate from any instances created in this method.
     *
     * Since 2.5, every newly created CacheManager is registered with its name (uses a default name if unnamed), and trying to create
     * multiple
     * CacheManager with same names (or multiple unnamed CacheManagers) is not allowed and throws an exception. Using any of the
     * {@link #newInstance()} methods also registers the CacheManager with its name.
     * It is recommended to use one of the {@link #newInstance()} methods to instantiate new CacheManagers as those methods return the same
     * instance
     * of CacheManager for same names (or unnamed). Shutting down the CacheManager will deregister it and new ones can be created again.
     *
     * <p>
     * This method can be used to specify a configuration resource in the classpath other than the default of \"/ehcache.xml\":
     *
     * <pre>
     * URL url = this.getClass().getResource(&quot;/ehcache-2.xml&quot;);
     * </pre>
     *
     * Note that {@link Class#getResource(String)} will look for resources in the same package unless a leading "/" is used, in which case
     * it will look in the root of the classpath.
     * <p>
     * You can also load a resource using other class loaders. e.g. {@link Thread#getContextClassLoader()}
     *
     * @param configurationURL
     *            an xml configuration available through a URL.
     * @throws CacheException
     * @see #newInstance(java.net.URL)
     * @since 1.2
     */
    public CacheManager(URL configurationURL) throws CacheException {
        status = Status.STATUS_UNINITIALISED;
        init(null, null, configurationURL, null);
    }

    /**
     * An ordinary constructor for CacheManager.
     * This method does not act as a singleton. Callers must maintain a reference to it.
     * Note that if one of the {@link #create()} methods are called, a new singleton will be created,
     * separate from any instances created in this method.
     *
     * Since 2.5, every newly created CacheManager is registered with its name (uses a default name if unnamed), and trying to create
     * multiple
     * CacheManager with same names (or multiple unnamed CacheManagers) is not allowed and throws an exception. Using any of the
     * {@link #newInstance()} methods also registers the CacheManager with its name.
     * It is recommended to use one of the {@link #newInstance()} methods to instantiate new CacheManagers as those methods return the same
     * instance
     * of CacheManager for same names (or unnamed). Shutting down the CacheManager will deregister it and new ones can be created again.
     *
     * @param configurationInputStream
     *            an xml configuration file available through an inputstream
     * @throws CacheException
     * @see #newInstance(java.io.InputStream)
     */
    public CacheManager(InputStream configurationInputStream) throws CacheException {
        status = Status.STATUS_UNINITIALISED;
        init(null, null, null, configurationInputStream);
    }

    /**
     * Constructor.
     *
     * Since 2.5, every newly created CacheManager is registered with its name (uses a default name if unnamed), and trying to create
     * multiple
     * CacheManager with same names (or multiple unnamed CacheManagers) is not allowed and throws an exception. Using any of the {@link
     * #newInstance()} methods also registers the CacheManager with its name.
     * It is recommended to use one of the {@link #newInstance()} methods to instantiate new CacheManagers as those methods return the same
     * instance
     * of CacheManager for same names (or unnamed). Shutting down the CacheManager will deregister it and new ones can be created again.
     *
     * @throws CacheException
     */
    public CacheManager() throws CacheException {
        // default config will be done
        status = Status.STATUS_UNINITIALISED;
        init(null, null, null, null);
    }

    /**
     * initialises the CacheManager
     * @param initialConfiguration  the initial configuration
     * @param configurationFileName the name of the configuration file
     * @param configurationInputStream the configuration input stream
     * @param configurationURL  the configuration URL
     */
    protected synchronized void init(Configuration initialConfiguration, String configurationFileName, URL configurationURL,
            InputStream configurationInputStream) {
        Configuration configuration;

        if (initialConfiguration == null) {
            configuration = parseConfiguration(configurationFileName, configurationURL, configurationInputStream);
        } else {
            configuration = initialConfiguration;
        }

        assertManagementRESTServiceConfigurationIsCorrect(configuration);
        assertNoCacheManagerExistsWithSameName(configuration);

        try {
            doInit(configuration);
        } catch (Throwable t) {
            if (terracottaClient != null) {
                terracottaClient.shutdown();
            }

            if (statisticsExecutor != null) {
                statisticsExecutor.shutdown();
            }

            if (featuresManager != null) {
                featuresManager.dispose();
            }

            if (diskStorePathManager != null) {
                diskStorePathManager.releaseLock();
            }

			synchronized (CacheManager.class) {
                final String name = CACHE_MANAGERS_REVERSE_MAP.remove(this);
                CACHE_MANAGERS_MAP.remove(name);
            }
            ALL_CACHE_MANAGERS.remove(this);
            if (t instanceof CacheException) {
                throw (CacheException) t;
            } else {
                throw new CacheException(t);
            }
        }
    }

    private void doInit(Configuration configuration) {
        if (configuration.getTerracottaConfiguration() != null) {
            // TODO this shouldn't be done!
            configuration.getTerracottaConfiguration().freezeConfig();
        }
        runtimeCfg = configuration.setupFor(this, DEFAULT_NAME);

        statisticsExecutor = Executors.newScheduledThreadPool(
          Integer.getInteger("net.sf.ehcache.CacheManager.statisticsExecutor.poolSize", 1) ,

          new ThreadFactory() {
             private AtomicInteger cnt = new AtomicInteger(0);
             @Override
             public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "Statistics Thread-" + getName() + "-" + cnt.incrementAndGet());
                t.setDaemon(true);
                return t;
             }
          });

       if (configuration.isMaxBytesLocalHeapSet()) {
            PoolEvictor evictor = new BalancedAccessEvictor();
            SizeOfEngine sizeOfEngine = createSizeOfEngine(null);
            this.onHeapPool = new BoundedPool(configuration.getMaxBytesLocalHeap(), evictor, sizeOfEngine);
        }
        if (configuration.isMaxBytesLocalDiskSet()) {
            PoolEvictor evictor = new BalancedAccessEvictor();
            this.onDiskPool = new BoundedPool(configuration.getMaxBytesLocalDisk(), evictor, null);
        }

        boolean clustered = false;
        terracottaClient = new TerracottaClient(this, configuration.getTerracottaConfiguration());
        if (terracottaClient.createClusteredInstanceFactory()) {
            clustered = true;
            if (configuration.getCacheConfigurations().isEmpty()) {
                terracottaClient.getClusteredInstanceFactory().linkClusteredCacheManager(getName(), configuration);
            }
        }

        ConfigurationHelper configurationHelper = new ConfigurationHelper(this, configuration);
        configure(configurationHelper);

        this.transactionController = new TransactionController(getOrCreateTransactionIDFactory(),
                configuration.getDefaultTransactionTimeoutInSeconds());

        status = Status.STATUS_ALIVE;

        for (CacheManagerPeerProvider cacheManagerPeerProvider : cacheManagerPeerProviders.values()) {
            cacheManagerPeerProvider.init();
        }

        cacheManagerEventListenerRegistry.init();
        addShutdownHookIfRequired();

        mbeanRegistrationProvider = MBEAN_REGISTRATION_PROVIDER_FACTORY.createMBeanRegistrationProvider(configuration);
        
        //Wait for the Orchestrator if required. This should be done before creating the caches
        if (configuration.getTerracottaConfiguration() != null) {
            if (configuration.getTerracottaConfiguration().isWanEnabledTSA()) {
                terracottaClient.waitForOrchestrator(getName());
            }
        }

        // do this last
        INITIALIZING_CACHE_MANAGERS_MAP.put(runtimeCfg.getCacheManagerName(), this);
        try {
            addConfiguredCaches(configurationHelper);
        } finally {
            INITIALIZING_CACHE_MANAGERS_MAP.remove(runtimeCfg.getCacheManagerName());
        }

        try {
            mbeanRegistrationProvider.initialize(CacheManager.this, terracottaClient.getClusteredInstanceFactory());
        } catch (MBeanRegistrationProviderException e) {
            LOG.warn("Failed to initialize the MBeanRegistrationProvider - " + mbeanRegistrationProvider.getClass().getName(), e);
        }

        ManagementRESTServiceConfiguration managementRESTService = configuration.getManagementRESTService();
        if (managementRESTService == null && clustered && ManagementServerLoader.isManagementAvailable()) {
            managementRESTService = getDefaultClusteredManagementRESTServiceConfiguration(configuration);
        }
        if (managementRESTService != null && managementRESTService.isEnabled()) {
            initializeManagementService(managementRESTService);
        }

        if (featuresManager != null) {
            featuresManager.startup();
        }

        // init XA recovery
        transactionManagerLookup.init();
    }


    private void initializeManagementService(ManagementRESTServiceConfiguration managementRESTService) {
        /**
         * ManagementServer will only be instantiated and started if one isn't already running on the configured port for this class
         * loader space.
         */
        synchronized (CacheManager.class) {
            ClusteredInstanceFactory clusteredInstanceFactory = terracottaClient.getClusteredInstanceFactory();
            String clientUUID = clusteredInstanceFactory == null ? null : clusteredInstanceFactory.getUUID();
            ManagementServerLoader.register(this, clientUUID, managementRESTService);
            registeredMgmtSvrBind = managementRESTService.getBind();
        }
    }

    private ManagementRESTServiceConfiguration getDefaultClusteredManagementRESTServiceConfiguration(Configuration configuration) {
        ManagementRESTServiceConfiguration managementRESTService;
        managementRESTService = new ManagementRESTServiceConfiguration();

        String url = configuration.getTerracottaConfiguration().getUrl();
        if (url != null && url.contains("@")) {
            managementRESTService.setSslEnabled(true);
        }

        managementRESTService.setEnabled(true);
        managementRESTService.setBind(ManagementRESTServiceConfiguration.NO_BIND);
        managementRESTService.setSecurityServiceLocation(ManagementRESTServiceConfiguration.AUTO_LOCATION);
        return managementRESTService;
    }

    private void assertManagementRESTServiceConfigurationIsCorrect(Configuration configuration) {
        ManagementRESTServiceConfiguration managementRESTService = configuration.getManagementRESTService();
        if (managementRESTService == null || !managementRESTService.isEnabled()) {
            return;
        }

        String url = configuration.getTerracottaConfiguration() != null ? configuration.getTerracottaConfiguration().getUrl() : null;
        boolean connectingToSecureCluster = url != null && url.contains("@");

        if (connectingToSecureCluster && !managementRESTService.isSslEnabled()) {
            throw new InvalidConfigurationException("The REST agent cannot be bound to a port when SSL is disabled and" +
                                                    " connecting to a secure cluster. Change your configuration to" +
                                                    " <ManagementRESTServiceConfiguration sslEnabled=\"true\" .../>" +
                                                    " or remove the ManagementRESTServiceConfiguration element.");
        }

        if (connectingToSecureCluster && managementRESTService.getSecurityServiceLocation() == null) {
            managementRESTService.setSecurityServiceLocation(ManagementRESTServiceConfiguration.AUTO_LOCATION);
            LOG.warn("The REST agent must have a non-null Security Service Location when SSL is enabled." +
                     " Using ManagementRESTServiceConfiguration.AUTO_LOCATION as a connection to a secure cluster" +
                     " is configured.");
        }

        if (managementRESTService.isSslEnabled() && managementRESTService.getSecurityServiceLocation() == null) {
            throw new InvalidConfigurationException("The REST agent must have a non-null Security Service Location when" +
                                                    " SSL is enabled. Change your configuration to" +
                                                    " <ManagementRESTServiceConfiguration securityServiceLocation=\"...\" .../>.");
        }
    }

    private void assertNoCacheManagerExistsWithSameName(Configuration configuration) {
        synchronized (CacheManager.class) {
            final String name;
            final boolean isNamed;
            if (configuration.getName() != null) {
                name = configuration.getName();
                isNamed = true;
            } else {
                name = DEFAULT_NAME;
                isNamed = false;
            }
            CacheManager cacheManager = CACHE_MANAGERS_MAP.get(name);
            if (cacheManager == null) {
                CACHE_MANAGERS_MAP.put(name, this);
                CACHE_MANAGERS_REVERSE_MAP.put(this, name);
            } else {
                ConfigurationSource configurationSource = cacheManager.getConfiguration().getConfigurationSource();
                final String msg = "Another "
                        + (isNamed ? "CacheManager with same name '" + name + "'" : "unnamed CacheManager")
                        + " already exists in the same VM. Please provide unique names for each CacheManager in the config or do one of following:\n"
                        + "1. Use one of the CacheManager.create() static factory methods to reuse same CacheManager with same name"
                        + " or create one if necessary\n"
                        + "2. Shutdown the earlier cacheManager before creating new one with same name.\n"
                        + "The source of the existing CacheManager is: "
                        + (configurationSource == null ? "[Programmatically configured]" : configurationSource);
                throw new CacheException(msg);
            }
        }
    }

    /**
     * Return this cache manager's statistics executor
     *
     * @return this cache manager's statistics executor
     */
    ScheduledExecutorService getStatisticsExecutor() {
        return statisticsExecutor;
    }

    /**
     * Return this cache manager's shared on-heap pool
     *
     * @return this cache manager's shared on-heap pool
     */
    public Pool getOnHeapPool() {
        return onHeapPool;
    }

    /**
     * Return this cache manager's shared on-disk pool
     *
     * @return this cache manager's shared on-disk pool
     */
    public Pool getOnDiskPool() {
        return onDiskPool;
    }

    /**
     * Returns unique cluster-wide id for this cache-manager. Only applicable when running in "cluster" mode, e.g. when this cache-manager
     * contains caches clustered with Terracotta. Otherwise returns blank string.
     *
     * @return Returns unique cluster-wide id for this cache-manager when it contains clustered caches (e.g. Terracotta clustered caches).
     *         Otherwise returns blank string.
     */
    public String getClusterUUID() {
        if (terracottaClient.getClusteredInstanceFactory() != null) {
            return getClientUUID(terracottaClient.getClusteredInstanceFactory());
        } else {
            return "";
        }
    }

    private static String getClientUUID(ClusteredInstanceFactory clusteredInstanceFactory) {
        return clusteredInstanceFactory.getUUID();
    }

    /**
     * Create/access the appropriate terracotta clustered store for the given cache
     *
     * @param cache The cache for which the Store should be created
     * @return a new (or existing) clustered store
     */
    public Store createTerracottaStore(Ehcache cache) {
        return getClusteredInstanceFactory().createStore(cache);
    }

    /**
     * Create/access the appropriate clustered write behind queue for the given cache
     *
     * @param cache The cache for which the write behind queue should be created
     * @return a new (or existing) write behind queue
     */
    public WriteBehind createTerracottaWriteBehind(Ehcache cache) {
        return getClusteredInstanceFactory().createWriteBehind(cache);
    }

    /**
     * Create/access the appropriate clustered cache event replicator for the given cache
     *
     * @param cache The cache for which the clustered event replicator should be created
     * @return a new cache event replicator
     */
    public CacheEventListener createTerracottaEventReplicator(Ehcache cache) {
      return getClusteredInstanceFactory().createEventReplicator(cache);
    }

    /**
     * Return the clustered instance factory for a cache of this cache manager.
     *
     * @return the clustered instance factory
     */
    protected ClusteredInstanceFactory getClusteredInstanceFactory() {
        return terracottaClient.getClusteredInstanceFactory();
    }

    /**
     * Loads configuration, either from the supplied {@link ConfigurationHelper} or by creating a new Configuration instance
     * from the configuration file referred to by file, inputstream or URL.
     * <p>
     * Should only be called once.
     *
     * @param configurationFileName
     *            the file name to parse, or null
     * @param configurationURL
     *            the URL to pass, or null
     * @param configurationInputStream
     *            , the InputStream to parse, or null
     * @return the loaded configuration
     * @throws CacheException
     *             if the configuration cannot be parsed
     */
    private synchronized Configuration parseConfiguration(String configurationFileName, URL configurationURL,
            InputStream configurationInputStream) throws CacheException {
        reinitialisationCheck();
        Configuration parsedConfig;
        if (configurationFileName != null) {

            LOG.debug("Configuring CacheManager from {}", configurationFileName);
            parsedConfig = ConfigurationFactory.parseConfiguration(new File(configurationFileName));
        } else if (configurationURL != null) {
            parsedConfig = ConfigurationFactory.parseConfiguration(configurationURL);
        } else if (configurationInputStream != null) {
            parsedConfig = ConfigurationFactory.parseConfiguration(configurationInputStream);
        } else {
            LOG.debug("Configuring ehcache from classpath.");
            parsedConfig = ConfigurationFactory.parseConfiguration();
        }
        return parsedConfig;

    }

    private void configure(ConfigurationHelper configurationHelper) {

        String diskStorePath = configurationHelper.getDiskStorePath();

        if (diskStorePath == null) {
            diskStorePathManager = new DiskStorePathManager();
            if (configurationHelper.numberOfCachesThatUseDiskStorage() > 0) {
                LOG.warn("One or more caches require a DiskStore but there is no diskStore element configured."
                        + " Using the default disk store path of " + DiskStoreConfiguration.getDefaultPath()
                        + ". Please explicitly configure the diskStore element in ehcache.xml.");
            }
        } else {
            diskStorePathManager = new DiskStorePathManager(diskStorePath);
        }

        this.featuresManager = retrieveFeaturesManager();

        this.transactionManagerLookup = runtimeCfg.getTransactionManagerLookup();

        cacheManagerEventListenerRegistry.registerListener(configurationHelper.createCacheManagerEventListener(this));

        cacheManagerPeerListeners.putAll(configurationHelper.createCachePeerListeners());
        for (CacheManagerPeerListener cacheManagerPeerListener : cacheManagerPeerListeners.values()) {
            cacheManagerEventListenerRegistry.registerListener(cacheManagerPeerListener);
        }

        detectAndFixCacheManagerPeerListenerConflict(configurationHelper);

        ALL_CACHE_MANAGERS.add(this);

        cacheManagerPeerProviders.putAll(configurationHelper.createCachePeerProviders());
        defaultCache = configurationHelper.createDefaultCache();
    }

    private void detectAndFixCacheManagerPeerListenerConflict(ConfigurationHelper configurationHelper) {
        if (cacheManagerPeerListeners == null) {
            return;
        }
        for (CacheManagerPeerListener cacheManagerPeerListener : cacheManagerPeerListeners.values()) {
            String uniqueResourceIdentifier = cacheManagerPeerListener.getUniqueResourceIdentifier();
            for (CacheManager cacheManager : ALL_CACHE_MANAGERS) {
                for (CacheManagerPeerListener otherCacheManagerPeerListener : cacheManager.cacheManagerPeerListeners.values()) {
                    if (otherCacheManagerPeerListener == null) {
                        continue;
                    }
                    String otherUniqueResourceIdentifier = otherCacheManagerPeerListener.getUniqueResourceIdentifier();
                    if (uniqueResourceIdentifier.equals(otherUniqueResourceIdentifier)) {
                        LOG.warn("Creating a new instance of CacheManager with a CacheManagerPeerListener which "
                                + "has a conflict on a resource that must be unique.\n" + "The resource is " + uniqueResourceIdentifier
                                + ".\n" + "Attempting automatic resolution. The source of the configuration was "
                                + configurationHelper.getConfigurationBean().getConfigurationSource() + ".\n"
                                + "To avoid this warning consider using the CacheManager factory methods to create a "
                                + "singleton CacheManager "
                                + "or specifying a separate ehcache configuration (ehcache.xml) for each CacheManager instance.");
                        cacheManagerPeerListener.attemptResolutionOfUniqueResourceConflict();
                        break;
                    }
                }

            }
        }
    }

    private void addConfiguredCaches(ConfigurationHelper configurationHelper) {
        Set unitialisedCaches = configurationHelper.createCaches();
        for (Iterator iterator = unitialisedCaches.iterator(); iterator.hasNext();) {
            Ehcache unitialisedCache = (Ehcache) iterator.next();
            addCacheNoCheck(unitialisedCache, true);

            // add the cache decorators for the cache, if any
            List<Ehcache> cacheDecorators = configurationHelper.createCacheDecorators(unitialisedCache);
            for (Ehcache decoratedCache : cacheDecorators) {
                addOrReplaceDecoratedCache(unitialisedCache, decoratedCache);
            }
        }
    }

    private void addOrReplaceDecoratedCache(final Ehcache underlyingCache, final Ehcache decoratedCache) {
        if (decoratedCache.getName().equals(underlyingCache.getName())) {
            this.replaceCacheWithDecoratedCache(underlyingCache, decoratedCache);
        } else {
            addDecoratedCache(decoratedCache);
        }
    }

    private void reinitialisationCheck() throws IllegalStateException {
        if (diskStorePathManager != null || ehcaches.size() != 0 || status.equals(Status.STATUS_SHUTDOWN)) {
            throw new IllegalStateException("Attempt to reinitialise the CacheManager");
        }
    }

    /**
     * A factory method to create a singleton CacheManager with default config, or return it if it exists.
     * <p>
     * The configuration will be read, {@link Ehcache}s created and required stores initialized. When the {@link CacheManager} is no longer
     * required, call shutdown to free resources.
     *
     * @return the singleton CacheManager
     * @throws CacheException
     *             if the CacheManager cannot be created
     */
    public static CacheManager create() throws CacheException {
        if (singleton != null) {
            LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            return singleton;
        }
        synchronized (CacheManager.class) {
            if (singleton == null) {
                singleton = newInstance();
            } else {
                LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            }
            return singleton;
        }
    }

    /**
     * A factory method to create a CacheManager with default config, or return it if it exists.
     * <p>
     * The configuration will be read, {@link Ehcache}s created and required stores initialized. When the {@link CacheManager} is no longer
     * required, call shutdown to free resources.
     *
     * @return the singleton CacheManager
     * @throws CacheException
     *             if the CacheManager cannot be created
     */
    public static CacheManager newInstance() throws CacheException {
        return newInstance(ConfigurationFactory.parseConfiguration(), "Creating new CacheManager with default config");
    }

    /**
     * A factory method to create a singleton CacheManager with default config, or return it if it exists.
     * <p>
     * This has the same effect as {@link CacheManager#create}
     * <p>
     * Same as {@link #create()}
     *
     * @return the singleton CacheManager
     * @throws CacheException
     *             if the CacheManager cannot be created
     */
    public static CacheManager getInstance() throws CacheException {
        return CacheManager.create();
    }

    /**
     * A factory method to create a singleton CacheManager with a specified configuration.
     *
     * @param configurationFileName
     *            an xml file compliant with the ehcache.xsd schema
     *            <p>
     *            The configuration will be read, {@link Ehcache}s created and required stores initialized. When the {@link CacheManager} is
     *            no longer required, call shutdown to free resources.
     */
    public static CacheManager create(String configurationFileName) throws CacheException {
        if (singleton != null) {
            LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            return singleton;
        }
        synchronized (CacheManager.class) {
            if (singleton == null) {
                singleton = newInstance(configurationFileName);
            } else {
                LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            }
            return singleton;
        }
    }

    /**
     * A factory method to create a CacheManager with a specified configuration.
     * <p>
     * If the specified configuration has different names for the CacheManager, it will return a new one for each unique name or return
     * already created one.
     *
     * @param configurationFileName
     *            an xml file compliant with the ehcache.xsd schema
     *            <p>
     *            The configuration will be read, {@link Ehcache}s created and required stores initialized. When the {@link CacheManager} is
     *            no longer required, call shutdown to free resources.
     */
    public static CacheManager newInstance(String configurationFileName) throws CacheException {
        return newInstance(ConfigurationFactory.parseConfiguration(new File(configurationFileName)),
                "Creating new CacheManager with config file: " + configurationFileName);
    }

    /**
     * A factory method to create a singleton CacheManager from an URL.
     * <p>
     * This method can be used to specify a configuration resource in the classpath other than the default of \"/ehcache.xml\": This method
     * can be used to specify a configuration resource in the classpath other than the default of \"/ehcache.xml\":
     *
     * <pre>
     * URL url = this.getClass().getResource(&quot;/ehcache-2.xml&quot;);
     * </pre>
     *
     * Note that {@link Class#getResource(String)} will look for resources in the same package unless a leading "/" is used, in which case
     * it will look in the root of the classpath.
     * <p>
     * You can also load a resource using other class loaders. e.g. {@link Thread#getContextClassLoader()}
     *
     * @param configurationFileURL
     *            an URL to an xml file compliant with the ehcache.xsd schema
     *            <p>
     *            The configuration will be read, {@link Ehcache}s created and required stores initialized. When the {@link CacheManager} is
     *            no longer required, call shutdown to free resources.
     */
    public static CacheManager create(URL configurationFileURL) throws CacheException {
        if (singleton != null) {
            LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            return singleton;
        }
        synchronized (CacheManager.class) {
            if (singleton == null) {
                singleton = newInstance(configurationFileURL);
            } else {
                LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            }
            return singleton;
        }
    }

    /**
     * A factory method to create a CacheManager from an URL.
     * <p>
     * This method can be used to specify a configuration resource in the classpath other than the default of \"/ehcache.xml\": This method
     * can be used to specify a configuration resource in the classpath other than the default of \"/ehcache.xml\":
     *
     * <pre>
     * URL url = this.getClass().getResource(&quot;/ehcache-2.xml&quot;);
     * </pre>
     *
     * Note that {@link Class#getResource(String)} will look for resources in the same package unless a leading "/" is used, in which case
     * it will look in the root of the classpath.
     * <p>
     * You can also load a resource using other class loaders. e.g. {@link Thread#getContextClassLoader()}
     *
     * If the specified configuration has different names for the CacheManager, it will return a new one for each unique name or return
     * already created one.
     *
     * @param configurationFileURL
     *            an URL to an xml file compliant with the ehcache.xsd schema
     *            <p>
     *            The configuration will be read, {@link Ehcache}s created and required stores initialized. When the {@link CacheManager} is
     *            no longer required, call shutdown to free resources.
     */
    public static CacheManager newInstance(URL configurationFileURL) throws CacheException {
        return newInstance(ConfigurationFactory.parseConfiguration(configurationFileURL), "Creating new CacheManager with config URL: "
                + configurationFileURL);
    }

    /**
     * A factory method to create a singleton CacheManager from a java.io.InputStream.
     * <p>
     * This method makes it possible to use an inputstream for configuration. Note: it is the clients responsibility to close the
     * inputstream.
     *
     * @param inputStream
     *            InputStream of xml compliant with the ehcache.xsd schema
     *            <p>
     *            The configuration will be read, {@link Ehcache}s created and required stores initialized. When the {@link CacheManager} is
     *            no longer required, call shutdown to free resources.
     */
    public static CacheManager create(InputStream inputStream) throws CacheException {
        if (singleton != null) {
            LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            return singleton;
        }
        synchronized (CacheManager.class) {
            if (singleton == null) {
                singleton = newInstance(inputStream);
            } else {
                LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            }
            return singleton;
        }
    }

    /**
     * A factory method to create a CacheManager from a java.io.InputStream.
     * <p>
     * This method makes it possible to use an inputstream for configuration. Note: it is the clients responsibility to close the
     * inputstream.
     * <p>
     *
     * If the specified configuration has different names for the CacheManager, it will return a new one for each unique name or return
     * already created one.
     *
     * @param inputStream
     *            InputStream of xml compliant with the ehcache.xsd schema
     *            <p>
     *            The configuration will be read, {@link Ehcache}s created and required stores initialized. When the {@link CacheManager} is
     *            no longer required, call shutdown to free resources.
     */
    public static CacheManager newInstance(InputStream inputStream) throws CacheException {
        return newInstance(ConfigurationFactory.parseConfiguration(inputStream), "Creating new CacheManager with InputStream");
    }

    /**
     * A factory method to create a singleton CacheManager from a net.sf.ehcache.config.Configuration.
     *
     * @param config
     */
    public static CacheManager create(Configuration config) throws CacheException {
        if (singleton != null) {
            LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            return singleton;
        }
        synchronized (CacheManager.class) {
            if (singleton == null) {
                singleton = newInstance(config);
            } else {
                LOG.debug("Attempting to create an existing singleton. Existing singleton returned.");
            }
            return singleton;
        }
    }

    /**
     * A factory method to create a CacheManager from a net.sf.ehcache.config.Configuration.
     * <p>
     * If the specified configuration has different names for the CacheManager, it will return a new one for each unique name or return
     * already created one.
     *
     * @param config
     */
    public static CacheManager newInstance(Configuration config) {
        return newInstance(config, "Creating new CacheManager with Configuration Object");
    }

    /**
     * Returns a new cacheManager or returns already created one.
     * If another cacheManager with same name already exists in the VM, returns it. Otherwise creates a new one and returns the new
     * cacheManager.
     * Subsequent calls with config having same name of the cacheManager will return same instance until it has been shut down.
     * There can be only one unnamed CacheManager in the VM
     *
     * @param configuration the configuration object
     * @param msg Message printed when creating new cacheManager
     * @return a new cacheManager or an already existing one in the VM with same name
     * @since 2.5
     */
    private static CacheManager newInstance(Configuration configuration, String msg) throws CacheException {
        synchronized (CacheManager.class) {
            String name = configuration.getName();
            if (name == null) {
                name = DEFAULT_NAME;
            }
            CacheManager cacheManager = CACHE_MANAGERS_MAP.get(name);
            if (cacheManager == null) {
                LOG.debug(msg);
                cacheManager = new CacheManager(configuration);
            }
            return cacheManager;
        }
    }

    /**
     * Checks if a cacheManager already exists for a given name and gets it.
     *
     * @param name the cacheManager name.
     * @return if a cacheManager exists with given name returns the CacheManager, otherwise returns null. If <code>name</code> is null,
     *         returns the default unnamed cacheManager if it has been created
     *         already otherwise returns null
     */
    public static CacheManager getCacheManager(String name) {
        synchronized (CacheManager.class) {
            if (name == null) {
                name = DEFAULT_NAME;
            }
            return CACHE_MANAGERS_MAP.get(name);
        }
    }

    /**
     * Returns a concrete implementation of Cache, it it is available in the CacheManager.
     * Consider using getEhcache(String name) instead, which will return decorated caches that are registered.
     * <p>
     * If a decorated ehcache is registered in CacheManager, an undecorated Cache with the same name may also exist.
     *
     * Since version ehcache-core-2.1.0, when an {@link Ehcache} decorator is present in the CacheManager, its not necessary that a
     * {@link Cache} instance is also present for the same name. Decorators can have different names other than the name of the cache its
     * decorating.
     *
     * @return a Cache, if an object of that type exists by that name, else null
     * @throws IllegalStateException
     *             if the cache is not {@link Status#STATUS_ALIVE}
     * @see #getEhcache(String)
     */
    public Cache getCache(String name) throws IllegalStateException, ClassCastException {
        checkStatus();
        Ehcache ehcache = ehcaches.get(name);
        return ehcache instanceof Cache ? (Cache) ehcache : null;
    }

    /**
     * Gets an Ehcache
     * <p>
     *
     * @return a Cache, if an object of type Cache exists by that name, else null
     * @throws IllegalStateException
     *             if the cache is not {@link Status#STATUS_ALIVE}
     */
    public Ehcache getEhcache(String name) throws IllegalStateException {
        checkStatus();
        return ehcaches.get(name);
    }

    /**
     * Some caches might be persistent, so we want to add a shutdown hook if that is the
     * case, so that the data and index can be written to disk.
     */
    private void addShutdownHookIfRequired() {

        String shutdownHookProperty = System.getProperty(ENABLE_SHUTDOWN_HOOK_PROPERTY);
        boolean enabled = PropertyUtil.parseBoolean(shutdownHookProperty);
        if (!enabled) {
            return;
        } else {
            LOG.info("The CacheManager shutdown hook is enabled because {} is set to true.", ENABLE_SHUTDOWN_HOOK_PROPERTY);

            Thread localShutdownHook = new Thread() {
                @Override
                public void run() {
                    synchronized (this) {
                        if (status.equals(Status.STATUS_ALIVE)) {
                            // clear shutdown hook reference to prevent
                            // removeShutdownHook to remove it during shutdown
                            shutdownHook = null;
                            LOG.info("VM shutting down with the CacheManager still active. Calling shutdown.");
                            shutdown();
                        }
                    }
                }
            };

            Runtime.getRuntime().addShutdownHook(localShutdownHook);
            shutdownHook = localShutdownHook;
        }
    }

    /**
     * Remove the shutdown hook to prevent leaving orphaned CacheManagers around. This
     * is called by {@link #shutdown()} AFTER the status has been set to shutdown.
     */
    private void removeShutdownHook() {
        if (shutdownHook != null) {
            // remove shutdown hook
            try {
                Runtime.getRuntime().removeShutdownHook(shutdownHook);
            } catch (IllegalStateException e) {
                // This will be thrown if the VM is shutting down. In this case
                // we do not need to worry about leaving references to CacheManagers lying
                // around and the call is ok to fail.
                LOG.debug("IllegalStateException due to attempt to remove a shutdown" + "hook while the VM is actually shutting down.", e);
            }
            shutdownHook = null;
        }
    }

    /**
     * Adds a {@link Ehcache} based on the defaultCache with the given name.
     * <p>
     * Memory and Disk stores will be configured for it and it will be added to the map of caches.
     * <p>
     * Also notifies the CacheManagerEventListener after the cache was initialised and added.
     * <p>
     * It will be created with the defaultCache attributes specified in ehcache.xml
     *
     * @param cacheName
     *            the name for the cache
     * @throws ObjectExistsException
     *             if the cache already exists
     * @throws CacheException
     *             if there was an error creating the cache.
     */
    public synchronized void addCache(String cacheName) throws IllegalStateException, ObjectExistsException, CacheException {
        checkStatus();

        // NPE guard
        if (cacheName == null || cacheName.length() == 0) {
            return;
        }

        if (ehcaches.get(cacheName) != null) {
            throw new ObjectExistsException("Cache " + cacheName + " already exists");
        }
        Ehcache clonedDefaultCache = cloneDefaultCache(cacheName);
        if (clonedDefaultCache == null) {
            throw new CacheException(NO_DEFAULT_CACHE_ERROR_MSG);
        }
        addCache(clonedDefaultCache);
        for (Ehcache ehcache : createDefaultCacheDecorators(clonedDefaultCache)) {
            addOrReplaceDecoratedCache(clonedDefaultCache, ehcache);
        }
    }

    /**
     * Adds a {@link Cache} to the CacheManager.
     * <p>
     * Memory and Disk stores will be configured for it and it will be added to the map of caches. Also notifies the
     * CacheManagerEventListener after the cache was initialised and added.
     *
     * @param cache
     * @throws IllegalStateException
     *             if the cache is not {@link Status#STATUS_UNINITIALISED} before this method is called.
     * @throws ObjectExistsException
     *             if the cache already exists in the CacheManager
     * @throws CacheException
     *             if there was an error adding the cache to the CacheManager
     */
    public void addCache(Cache cache) throws IllegalStateException, ObjectExistsException, CacheException {
        checkStatus();
        if (cache == null) {
            return;
        }
        addCache((Ehcache) cache);
    }

    /**
     * Adds an {@link Ehcache} to the CacheManager.
     * <p>
     * Memory and Disk stores will be configured for it and it will be added to the map of caches. Also notifies the
     * CacheManagerEventListener after the cache was initialised and added.
     *
     * @param cache
     * @throws IllegalStateException
     *             if the cache is not {@link Status#STATUS_UNINITIALISED} before this method is called.
     * @throws ObjectExistsException
     *             if the cache already exists in the CacheManager
     * @throws CacheException
     *             if there was an error adding the cache to the CacheManager
     */
    public synchronized void addCache(Ehcache cache) throws IllegalStateException, ObjectExistsException, CacheException {
        checkStatus();
        if (cache == null) {
            return;
        }
        final CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
        final boolean verifyOffHeapUsage = runtimeCfg.hasOffHeapPool()
                && ((!cacheConfiguration.isOverflowToDisk() && !cacheConfiguration.isOverflowToOffHeapSet()) || cacheConfiguration
                        .isOverflowToOffHeap());

        if (verifyOffHeapUsage
                && (cacheConfiguration.isMaxBytesLocalOffHeapPercentageSet() || cacheConfiguration.getMaxBytesLocalOffHeap() > 0)) {
            throw new CacheException("CacheManager uses OffHeap settings, you can't add cache using offHeap dynamically!");
        }
        addCacheNoCheck(cache, true);
    }

    /**
     * Adds a decorated {@link Ehcache} to the CacheManager. This method neither creates the memory/disk store
     * nor initializes the cache. It only adds the cache reference to the map of caches held by this
     * cacheManager.
     * <p>
     * It is generally required that a decorated cache, once constructed, is made available to other execution threads. The simplest way of
     * doing this is to either add it to the cacheManager with a different name or substitute the original cache with the decorated one.
     * <p>
     * This method adds the decorated cache assuming it has a different name. If another cache (decorated or not) with the same name already
     * exists, it will throw {@link ObjectExistsException}. For replacing existing cache with another decorated cache having same name,
     * please use {@link #replaceCacheWithDecoratedCache(Ehcache, Ehcache)}
     * <p>
     * Note that any overridden Ehcache methods by the decorator will take on new behaviours without casting. Casting is only required for
     * new methods that the decorator introduces. For more information see the well known Gang of Four Decorator pattern.
     *
     * @param decoratedCache
     * @throws ObjectExistsException
     *             if another cache with the same name already exists.
     */
    public synchronized void addDecoratedCache(Ehcache decoratedCache) throws ObjectExistsException {
        internalAddDecoratedCache(decoratedCache, true);
    }

    /**
     * Same as {@link #addDecoratedCache(Ehcache)} but does not throw exception if another cache with same name already exists.
     *
     * @param decoratedCache
     * @throws ObjectExistsException
     */
    public synchronized void addDecoratedCacheIfAbsent(Ehcache decoratedCache) throws ObjectExistsException {
        internalAddDecoratedCache(decoratedCache, false);
    }

    private void internalAddDecoratedCache(final Ehcache decoratedCache, final boolean strict) {
        Ehcache old = ehcaches.putIfAbsent(decoratedCache.getName(), decoratedCache);
        if (strict && old != null) {
            throw new ObjectExistsException("Cache " + decoratedCache.getName() + " already exists in the CacheManager");
        }
    }

    /**
     * Initialize the given {@link Ehcache} without adding it to the {@link CacheManager}.
     *
     * @param cache
     * @param registerCacheConfig
     */
    void initializeEhcache(final Ehcache cache, final boolean registerCacheConfig) {
        if (!registerCacheConfig) {
            cache.getCacheConfiguration().setupFor(this, registerCacheConfig, getParentCacheName(cache));
        } else {
            cache.getCacheConfiguration().setupFor(this);
        }
        cache.setCacheManager(this);
        cache.setTransactionManagerLookup(transactionManagerLookup);

        cache.initialise();

        if (!runtimeCfg.allowsDynamicCacheConfig()) {
            cache.disableDynamicFeatures();
        }

        if (!registerCacheConfig) {
            associateShadowCache(cache);
        }

        try {
            cache.bootstrap();
        } catch (CacheException e) {
            LOG.warn("Cache " + cache.getName() + "requested bootstrap but a CacheException occured. " + e.getMessage(), e);
        }
    }

    private void associateShadowCache(Ehcache shadow) {
        String parentCacheName = getParentCacheName(shadow);
        if (parentCacheName == null) {
            return;
        }
        Ehcache parent = initializingCaches.get(parentCacheName);
        if (parent == null) {
            parent = ehcaches.get(parentCacheName);
        }
        if (parent != null) {
            StatisticsManager.associate(shadow).withParent(parent);
        }
    }


    private String getParentCacheName(Ehcache shadow) {
        String shadowPrefix = LOCAL_CACHE_NAME_PREFIX + getName() + "___tc_clustered-ehcache|" + getName() + "|";
        if (shadow.getName().startsWith(shadowPrefix)) {
            return shadow.getName().substring(shadowPrefix.length());
        } else {
            return null;
        }
    }

    private Ehcache addCacheNoCheck(final Ehcache cache, final boolean strict) throws IllegalStateException, ObjectExistsException,
            CacheException {

        if (cache.getStatus() != Status.STATUS_UNINITIALISED) {
            throw new CacheException("Trying to add an already initialized cache." + " If you are adding a decorated cache, "
                    + "use CacheManager.addDecoratedCache" + "(Ehcache decoratedCache) instead.");
        }

        if (cache.getCacheConfiguration().isTerracottaClustered() && terracottaClient.getClusteredInstanceFactory() == null) {
            throw new CacheException(String.format("Trying to add terracotta cache %s but no <terracottaConfig> element was " +
                                                   "used to specify the Terracotta configuration on the CacheManager %s.",
                                                    cache.getName(), getName()));
        }

        Ehcache ehcache = ehcaches.get(cache.getName());
        if (ehcache != null) {
            if (strict) {
                throw new ObjectExistsException("Cache " + cache.getName() + " already exists");
            } else {
                return ehcache;
            }
        }

        initializingCaches.put(cache.getName(), cache);
        try {
            initializeEhcache(cache, true);

            ehcache = ehcaches.putIfAbsent(cache.getName(), cache);
            if (ehcache != null) {
                throw new AssertionError();
            }
        } finally {
            initializingCaches.remove(cache.getName());
        }

        // Don't notify initial config. The init method of each listener should take care of this.
        if (status.equals(Status.STATUS_ALIVE)) {
            cacheManagerEventListenerRegistry.notifyCacheAdded(cache.getName());
        }

        return cache;
    }

    /**
     * Checks whether a cache of type ehcache exists.
     *
     * @param cacheName
     *            the cache name to check for
     * @return true if it exists
     * @throws IllegalStateException
     *             if the cache is not {@link Status#STATUS_ALIVE}
     */
    public boolean cacheExists(String cacheName) throws IllegalStateException {
        checkStatus();
        return (ehcaches.get(cacheName) != null);
    }

    /**
     * Removes all caches using {@link #removeCache(String)} for each cache.
     */
    public void removeAllCaches() {
        String[] cacheNames = getCacheNames();
        for (String cacheName : cacheNames) {
            removeCache(cacheName);
        }
    }

    /**
     * Removes all caches using {@link #removeCache(String)} for each cache.
     * @deprecated use {@link #removeAllCaches} instead
     */
    @Deprecated
    public void removalAll() {
        removeAllCaches();
    }

    /**
     * Remove a cache from the CacheManager. The cache is disposed of.
     *
     * @param cacheName
     *            the cache name
     * @throws IllegalStateException
     *             if the cache is not {@link Status#STATUS_ALIVE}
     */
    public synchronized void removeCache(String cacheName) throws IllegalStateException {
        checkStatus();

        // NPE guard
        if (cacheName == null || cacheName.length() == 0) {
            return;
        }
        Ehcache cache = ehcaches.remove(cacheName);
        if (cache != null && cache.getStatus().equals(Status.STATUS_ALIVE)) {
            cache.dispose();
            runtimeCfg.removeCache(cache.getCacheConfiguration());
            cacheManagerEventListenerRegistry.notifyCacheRemoved(cache.getName());
        }
    }

    /**
     * Shuts down the CacheManager.
     * <p>
     * If the shutdown occurs on the singleton, then the singleton is removed, so that if a singleton access method is called, a new
     * singleton will be created.
     * <p>
     * By default there is no shutdown hook (ehcache-1.3-beta2 and higher).
     * <p>
     * Set the system property net.sf.ehcache.enableShutdownHook=true to turn it on.
     */
    public void shutdown() {
        synchronized (this) {
            if (status.equals(Status.STATUS_SHUTDOWN)) {
                LOG.debug("CacheManager already shutdown");
                return;
            }

            if (registeredMgmtSvrBind != null) {
                ManagementServerLoader.unregister(registeredMgmtSvrBind, this);
                registeredMgmtSvrBind = null;
            }

            for (CacheManagerPeerProvider cacheManagerPeerProvider : cacheManagerPeerProviders.values()) {
                if (cacheManagerPeerProvider != null) {
                    cacheManagerPeerProvider.dispose();
                }
            }
	
            cacheManagerEventListenerRegistry.dispose();

            ALL_CACHE_MANAGERS.remove(this);

            for (Ehcache cache : ehcaches.values()) {
                if (cache != null) {
                    cache.dispose();
                }
            }
            if (defaultCache != null) {
                defaultCache.dispose();
            }
            status = Status.STATUS_SHUTDOWN;
            XARequestProcessor.shutdown();

            // only delete singleton if the singleton is shutting down.
            if (this == singleton) {
                singleton = null;
            }
            terracottaClient.shutdown();
            transactionController = null;
            removeShutdownHook();

            if (featuresManager != null) {
                featuresManager.dispose();
            }

            // release file lock on diskstore path
            if (diskStorePathManager != null) {
                diskStorePathManager.releaseLock();
            }

            try {
                ConcurrencyUtil.shutdownAndWaitForTermination(statisticsExecutor, POOL_SHUTDOWN_TIMEOUT_SECS);
            } catch (TimeoutException e) {
                LOG.warn(e.getMessage(), e);
            }

            getConfiguration().cleanup();

            synchronized (CacheManager.class) {
                final String name = CACHE_MANAGERS_REVERSE_MAP.remove(this);
                CACHE_MANAGERS_MAP.remove(name);
            }
        }
    }


    /**
     * Returns a list of the current cache names.
     *
     * @return an array of {@link String}s
     * @throws IllegalStateException
     *             if the cache is not {@link Status#STATUS_ALIVE}
     */
    public String[] getCacheNames() throws IllegalStateException {
        checkStatus();
        return ehcaches.keySet().toArray(new String[0]);
    }

    /**
     * Checks the state of the CacheManager for legal operation
     */
    protected void checkStatus() {
        if (!(status.equals(Status.STATUS_ALIVE))) {
            if (status.equals(Status.STATUS_UNINITIALISED)) {
                throw new IllegalStateException("The CacheManager has not yet been initialised. It cannot be used yet.");
            } else if (status.equals(Status.STATUS_SHUTDOWN)) {
                throw new IllegalStateException("The CacheManager has been shut down. It can no longer be used.");
            }
        }
    }

    /**
     * Gets the status attribute of the Ehcache
     *
     * @return The status value from the Status enum class
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Clears the contents of all caches in the CacheManager, but without
     * removing any caches.
     * <p>
     * This method is not synchronized. It only guarantees to clear those elements in a cache at the time that the
     * {@link Ehcache#removeAll()} mehod on each cache is called.
     */
    public void clearAll() throws CacheException {
        String[] cacheNames = getCacheNames();

        LOG.debug("Clearing all caches");
        for (String cacheName : cacheNames) {
            Ehcache cache = getEhcache(cacheName);
            cache.removeAll();
        }
    }

    /**
     * Clears the contents of all caches in the CacheManager with a name starting with the prefix,
     * but without removing them.
     * <p>
     * This method is not synchronized. It only guarantees to clear those elements in a cache at the time that the
     * {@link Ehcache#removeAll()} method on each cache is called.
     *
     * @param prefix
     *            The prefix the cache name should start with
     * @throws CacheException
     * @since 1.7.2
     */
    public void clearAllStartingWith(String prefix) throws CacheException {
        // NPE guard
        if (prefix == null || prefix.length() == 0) {
            return;
        }

        for (Object o : ehcaches.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String cacheName = (String) entry.getKey();
            if (cacheName.startsWith(prefix)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Clearing cache named '" + cacheName + "' (matches '" + prefix + "' prefix");
                }
                ((Ehcache) entry.getValue()).removeAll();
            }
        }
    }

    /**
     * Gets the <code>CacheManagerPeerProvider</code>, matching the given scheme
     * For distributed caches, the peer provider finds other cache managers and their caches in the same cluster
     *
     * @param scheme
     *            the replication scheme to use. Schemes shipped with ehcache are RMI, JGROUPS, JMS
     * @return the provider, or null if one does not exist
     */
    public CacheManagerPeerProvider getCacheManagerPeerProvider(String scheme) {
        return cacheManagerPeerProviders.get(scheme);
    }

    /**
     * @return Read-only map of the registered {@link CacheManagerPeerProvider}s keyed by scheme.
     */
    public Map<String, CacheManagerPeerProvider> getCacheManagerPeerProviders() {
        return Collections.unmodifiableMap(this.cacheManagerPeerProviders);
    }

    /**
     * When CacheManage is configured as part of a cluster, a CacheManagerPeerListener will
     * be registered in it. Use this to access the individual cache listeners
     *
     * @param scheme
     *            the replication scheme to use. Schemes shipped with ehcache are RMI, JGROUPS, JMS
     * @return the listener, or null if one does not exist
     */
    public CacheManagerPeerListener getCachePeerListener(String scheme) {
        return cacheManagerPeerListeners.get(scheme);
    }

    /**
     * Returns the composite listener. A notification sent to this listener will notify all registered
     * listeners.
     *
     * @return null if none
     * @see "getCacheManagerEventListenerRegistry"
     */
    public CacheManagerEventListener getCacheManagerEventListener() {
        return cacheManagerEventListenerRegistry;
    }

    /**
     * Same as getCacheManagerEventListenerRegistry().registerListener(cacheManagerEventListener);
     * Left for backward compatiblity
     *
     * @param cacheManagerEventListener
     *            the listener to set.
     * @see "getCacheManagerEventListenerRegistry"
     */
    public void setCacheManagerEventListener(CacheManagerEventListener cacheManagerEventListener) {
        getCacheManagerEventListenerRegistry().registerListener(cacheManagerEventListener);
    }

    /**
     * Gets the CacheManagerEventListenerRegistry. Add and remove listeners here.
     */
    public CacheManagerEventListenerRegistry getCacheManagerEventListenerRegistry() {
        return cacheManagerEventListenerRegistry;
    }

    /**
     * Replaces in the map of Caches managed by this CacheManager an Ehcache with a decorated version of the same
     * Ehcache. CacheManager can operate fully with a decorated Ehcache.
     * <p>
     * Ehcache Decorators can be used to obtain different behaviour from an Ehcache in a very flexible way. Some examples in ehcache are:
     * <ol>
     * <li>{@link net.sf.ehcache.constructs.blocking.BlockingCache} - A cache that blocks other threads from getting a null element until
     * the first thread has placed a value in it.
     * <li>{@link net.sf.ehcache.constructs.blocking.SelfPopulatingCache} - A BlockingCache that has the additional property of knowing how
     * to load its own entries.
     * </ol>
     * Many other kinds are possible.
     * <p>
     * It is generally required that a decorated cache, once constructed, is made available to other execution threads. The simplest way of
     * doing this is to substitute the original cache for the decorated one here.
     * <p>
     * Note that any overwritten Ehcache methods will take on new behaviours without casting. Casting is only required for new methods that
     * the decorator introduces. For more information see the well known Gang of Four Decorator pattern.
     *
     * @param ehcache
     * @param decoratedCache
     *            An implementation of Ehcache that wraps the original cache.
     * @throws CacheException
     *             if the two caches do not equal each other.
     */
    public synchronized void replaceCacheWithDecoratedCache(Ehcache ehcache, Ehcache decoratedCache) throws CacheException {
        if (!ehcache.equals(decoratedCache)) {
            throw new CacheException("Cannot replace " + decoratedCache.getName() + " It does not equal the incumbent cache.");
        }

        String cacheName = ehcache.getName();
        if (!ehcaches.replace(cacheName, ehcache, decoratedCache)) {
            if (cacheExists(cacheName)) {
                throw new CacheException("Cache '" + ehcache.getName() + "' managed with this CacheManager doesn't match!");
            } else {
                throw new CacheException("Cache '" + cacheName + "' isn't associated with this manager (anymore?)");
            }
        }
    }

    /**
     * Gets the name of the CacheManager. This is useful for distinguishing multiple CacheManagers
     *
     * @return the name, or the output of toString() if it is not set.
     * @see #toString() which uses either the name or Object.toString()
     */
    public String getName() {
        if (runtimeCfg.getCacheManagerName() != null) {
            return runtimeCfg.getCacheManagerName();
        } else {
            return super.toString();
        }
    }

    /**
     * Indicate whether the CacheManager is named or not.
     *
     * @return True if named
     */
    public boolean isNamed() {
        return runtimeCfg.isNamed();
    }

    /**
     * @return either the name of this CacheManager, or if unset, Object.toString()
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Returns the disk store path manager. This may be null if no caches need a DiskStore and none was configured.
     * The path cannot be changed after creation of the CacheManager. All caches take the disk store path
     * from this manager.
     *
     * @return the disk store path manager.
     */
    public DiskStorePathManager getDiskStorePathManager() {
        return diskStorePathManager;
    }

    /**
     * Returns a {@link FailSafeTimer} associated with this {@link CacheManager}
     *
     * @return The {@link FailSafeTimer} associated with this cache manager
     * @since 1.7
     */
    public FailSafeTimer getTimer() {
        return null;
    }

    /**
     * Returns access to information about the cache cluster.
     *
     * @param scheme The clustering scheme to retrieve information about (such as "Terracotta")
     * @return Cluster API (never null, but possibly a simple single node implementation)
     * @throws ClusterSchemeNotAvailableException If the CacheCluster specified by scheme is not available.
     * @see ClusterScheme
     * @since 2.0
     */
    public CacheCluster getCluster(ClusterScheme scheme) throws ClusterSchemeNotAvailableException {
        switch (scheme) {
            case TERRACOTTA:
                if (null == terracottaClient.getClusteredInstanceFactory()) {
                    throw new ClusterSchemeNotAvailableException(ClusterScheme.TERRACOTTA, "Terracotta cluster scheme is not available");
                }
                return terracottaClient.getCacheCluster();
            default:
                return NoopCacheCluster.INSTANCE;
        }
    }

    /**
     * Returns the original configuration text for this {@link CacheManager}
     *
     * @return Returns the original configuration text for this {@link CacheManager}
     */
    public String getOriginalConfigurationText() {
        if (runtimeCfg.getConfiguration().getConfigurationSource() == null) {
            return "Originally configured programmatically. No original configuration source text.";
        } else {
            Configuration originalConfiguration = runtimeCfg.getConfiguration().getConfigurationSource().createConfiguration();
            return ConfigurationUtil.generateCacheManagerConfigurationText(originalConfiguration);
        }
    }

    /**
     * Returns the active configuration text for this {@link CacheManager}
     *
     * @return Returns the active configuration text for this {@link CacheManager}
     */
    public String getActiveConfigurationText() {
        return ConfigurationUtil.generateCacheManagerConfigurationText(this);
    }

    /**
     * Returns the original configuration text for the input cacheName
     *
     * @param cacheName
     * @return Returns the original configuration text for the input cacheName
     * @throws CacheException if the cache with <code>cacheName</code> does not exist in the original config
     */
    public String getOriginalConfigurationText(String cacheName) throws CacheException {
        if (runtimeCfg.getConfiguration().getConfigurationSource() == null) {
            return "Originally configured programmatically. No original configuration source text.";
        } else {
            Configuration originalConfiguration = runtimeCfg.getConfiguration().getConfigurationSource().createConfiguration();
            CacheConfiguration cacheConfiguration = originalConfiguration.getCacheConfigurations().get(cacheName);
            if (cacheConfiguration == null) {
                throw new CacheException("Cache with name '" + cacheName + "' does not exist in the original configuration");
            }
            return ConfigurationUtil.generateCacheConfigurationText(runtimeCfg.getConfiguration(), cacheConfiguration);
        }
    }

    /**
     * Returns the active configuration text for the input cacheName
     *
     * @param cacheName
     * @return Returns the active configuration text for the input cacheName
     * @throws CacheException if the cache with <code>cacheName</code> does not exist
     */
    public String getActiveConfigurationText(String cacheName) throws CacheException {
        boolean decoratedCache = false;
        Ehcache cache = getCache(cacheName);
        if (cache == null) {
            cache = getEhcache(cacheName);
            decoratedCache = true;
        }
        CacheConfiguration actualConfig = cache != null ? cache.getCacheConfiguration() : null;
        if (actualConfig == null) {
            throw new CacheException("Cache with name '" + cacheName + "' does not exist");
        }
        CacheConfiguration config = decoratedCache ? actualConfig.clone().name(cacheName) : actualConfig;
        return ConfigurationUtil.generateCacheConfigurationText(runtimeCfg.getConfiguration(), config);
    }

    /**
     * Get the CacheManager configuration
     *
     * @return the configuration
     */
    public Configuration getConfiguration() {
        return runtimeCfg.getConfiguration();
    }

    /**
     * Only adds the cache to the CacheManager should not one with the same name already be present
     *
     * @param cache The Ehcache to be added
     * @return the instance registered with the CacheManager, the cache instance passed in if it was added; or null if Ehcache is null
     */
    public synchronized Ehcache addCacheIfAbsent(final Ehcache cache) {
        checkStatus();
        return cache == null ? null : addCacheNoCheck(cache, false);
    }

    /**
     * Only creates and adds the cache to the CacheManager should not one with the same name already be present
     *
     * @param cacheName the name of the Cache to be created
     * @return the Ehcache instance created and registered; null if cacheName was null or of length 0
     */
    public synchronized Ehcache addCacheIfAbsent(final String cacheName) {
        checkStatus();

        // NPE guard
        if (cacheName == null || cacheName.length() == 0) {
            return null;
        }

        Ehcache ehcache = ehcaches.get(cacheName);
        if (ehcache == null) {
            Ehcache clonedDefaultCache = cloneDefaultCache(cacheName);
            if (clonedDefaultCache == null) {
                throw new CacheException(NO_DEFAULT_CACHE_ERROR_MSG);
            }
            addCacheIfAbsent(clonedDefaultCache);
            for (Ehcache createdCache : createDefaultCacheDecorators(clonedDefaultCache)) {
                addOrReplaceDecoratedCache(clonedDefaultCache, createdCache);
            }
        }
        return ehcaches.get(cacheName);
    }

    private Ehcache cloneDefaultCache(final String cacheName) {
        if (defaultCache == null) {
            return null;
        }
        Ehcache cache;
        try {
            cache = (Ehcache) defaultCache.clone();
        } catch (CloneNotSupportedException e) {
            throw new CacheException("Failure cloning default cache. Initial cause was " + e.getMessage(), e);
        }
        if (cache != null) {
            cache.setName(cacheName);
        }
        return cache;
    }

    private List<Ehcache> createDefaultCacheDecorators(Ehcache underlyingCache) {
        return ConfigurationHelper.createDefaultCacheDecorators(underlyingCache, runtimeCfg.getConfiguration()
                .getDefaultCacheConfiguration(), getClassLoader());
    }

    private ClassLoader getClassLoader() {
        return this.runtimeCfg.getConfiguration().getClassLoader();
    }

    /**
     * Get the TransactionController
     *
     * @return the TransactionController
     */
    public TransactionController getTransactionController() {
        return transactionController;
    }

    /**
     * Get or create a TransactionIDFactory
     *
     * @return a TransactionIDFactory
     */
    public TransactionIDFactory getOrCreateTransactionIDFactory() {
        if (transactionIDFactory == null) {
            transactionIDFactory = new DelegatingTransactionIDFactory(featuresManager, terracottaClient, getName());
        }
        return transactionIDFactory;
    }

    /**
     * Create a soft lock manager for a specific cache
     *
     * @param cache the cache to create the soft lock manager for
     * @return a SoftLockManager
     */
    SoftLockManager createSoftLockManager(Ehcache cache) {
        SoftLockManager softLockManager;
        if (cache.getCacheConfiguration().isTerracottaClustered()) {
            softLockManager = getClusteredInstanceFactory().getOrCreateSoftLockManager(cache);
        } else {
            SoftLockFactory lockFactory = new ReadCommittedSoftLockFactory();
            softLockManager = softLockManagers.get(cache.getName());
            if (softLockManager == null) {
                if (featuresManager == null) {
                    softLockManager = new SoftLockManagerImpl(cache.getName(), lockFactory);
                } else {
                    softLockManager = featuresManager.createSoftLockManager(cache, lockFactory);
                }
                SoftLockManager old = softLockManagers.putIfAbsent(cache.getName(), softLockManager);
                if (old != null) {
                    softLockManager = old;
                }
            }
        }
        return softLockManager;
    }

    /**
     * Creates a SizeOfEngine for a cache.
     * It will check for a System property on what class to instantiate.
     *
     * @param cache The cache to be sized by the engine
     * @return a SizeOfEngine instance
     */
    SizeOfEngine createSizeOfEngine(final Cache cache) {
        String prop = "net.sf.ehcache.sizeofengine";

        if (isNamed()) {
            prop += "." + getName();
        } else {
            prop += ".default";
        }

        if (cache != null) {
            prop += "." + cache.getName();
        }

        String className = System.getProperty(prop);

        if (className != null) {
            try {
                Class<? extends SizeOfEngine> aClass = (Class<? extends SizeOfEngine>) Class.forName(className);
                return aClass.newInstance();
            } catch (Exception exception) {
                throw new RuntimeException("Couldn't load and instantiate custom "
                        + (cache != null ? "SizeOfEngine for cache '" + cache.getName() + "'" : "default SizeOfEngine"), exception);
            }
        } else {
            SizeOfPolicyConfiguration sizeOfPolicyConfiguration = null;
            if (cache != null) {
                sizeOfPolicyConfiguration = cache.getCacheConfiguration().getSizeOfPolicyConfiguration();
            }
            if (sizeOfPolicyConfiguration == null) {
                sizeOfPolicyConfiguration = getConfiguration().getSizeOfPolicyConfiguration();
            }
            return SizeOfEngineLoader.newSizeOfEngine(sizeOfPolicyConfiguration.getMaxDepth(),
                sizeOfPolicyConfiguration.getMaxDepthExceededBehavior().isAbort(), false);
        }
    }

    /**
     * Get the features manager.
     *
     * @return the features manager
     */
    public FeaturesManager getFeaturesManager() {
        return featuresManager;
    }

    private FeaturesManager retrieveFeaturesManager() {
        try {
            Class<? extends FeaturesManager> featuresManagerClass = (Class<? extends FeaturesManager>) Class.forName(FeaturesManager.ENTERPRISE_FM_CLASSNAME);

            try {
                return featuresManagerClass.getConstructor(CacheManager.class).newInstance(this);
            } catch (NoSuchMethodException e) {
                throw new CacheException("Cannot find Enterprise features manager");
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof CacheException) {
                    throw (CacheException) cause;
                } else {
                    throw new CacheException("Cannot instantiate enterprise features manager", cause);
                }
            } catch (IllegalAccessException e) {
                throw new CacheException("Cannot instantiate enterprise features manager", e);
            } catch (InstantiationException e) {
                throw new CacheException("Cannot instantiate enterprise features manager", e);
            }
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Get a currently initializing {@link CacheManager}.
     *
     * @param name name of the {@link CacheManager}; can be null
     * @return the {@link CacheManager} if it exists.
     */
    static CacheManager getInitializingCacheManager(String name) {
        return INITIALIZING_CACHE_MANAGERS_MAP.get(name);
    }    

    /**
     * Send a management event to the cluster
     * @param event, the event (most probably an EventEntity)
     * @param type, the type of the event
     */
    public void sendManagementEvent(Serializable event, String type) {
      getOrCreateEventSink().sendManagementEvent(event, type);
    }

    private ManagementEventSink getOrCreateEventSink() {
        if (managementEventSink == null) {
            managementEventSink = new DelegatingManagementEventSink(terracottaClient);
        }
        return managementEventSink;
    }

}
/* @generated */