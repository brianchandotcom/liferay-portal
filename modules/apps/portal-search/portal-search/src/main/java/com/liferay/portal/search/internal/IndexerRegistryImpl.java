/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.dummy.DummyIndexer;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.search.configuration.IndexerRegistryConfiguration;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.search.internal.buffer.BufferedIndexerInvocationHandler;
import com.liferay.portal.search.internal.buffer.IndexerRequestBuffer;
import com.liferay.portal.search.internal.buffer.IndexerRequestBufferOverflowHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ClassUtils;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.IndexerRegistryConfiguration",
	service = IndexerRegistry.class
)
public class IndexerRegistryImpl implements IndexerRegistry {

	@Override
	public <T> Indexer<T> getIndexer(Class<T> clazz) {
		return getIndexer(clazz.getName());
	}

	@Override
	public <T> Indexer<T> getIndexer(String className) {
		ServiceTrackerMap<String, Indexer> indexerServiceTrackerMap =
			_getIndexerServiceTrackerMap();

		Indexer<T> indexer = indexerServiceTrackerMap.getService(className);

		return _proxyIndexer(indexer);
	}

	@Override
	public Set<Indexer<?>> getIndexers() {
		ServiceTrackerMap<String, Indexer> indexerServiceTrackerMap =
			_getIndexerServiceTrackerMap();

		return new HashSet<>((Collection)indexerServiceTrackerMap.values());
	}

	@Override
	public <T> Indexer<T> nullSafeGetIndexer(Class<T> clazz) {
		return nullSafeGetIndexer(clazz.getName());
	}

	@Override
	public <T> Indexer<T> nullSafeGetIndexer(String className) {
		Indexer<T> indexer = getIndexer(className);

		if (indexer != null) {
			return indexer;
		}

		if (_log.isInfoEnabled()) {
			_log.info("No indexer found for " + className);
		}

		return (Indexer<T>)_dummyIndexer;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		modified(properties);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(indexer.class.name=*)"
	)
	protected void addIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor,
		Map<String, Object> properties) {

		List<String> indexerClassNames = StringPlus.asList(
			properties.get("indexer.class.name"));

		for (String indexerClassName : indexerClassNames) {
			Indexer<?> indexer = getIndexer(indexerClassName);

			if (indexer != null) {
				indexer.registerIndexerPostProcessor(indexerPostProcessor);
			}
			else {
				synchronized (_queuedIndexerPostProcessors) {
					List<IndexerPostProcessor> indexerPostProcessors =
						_queuedIndexerPostProcessors.computeIfAbsent(
							indexerClassName, key -> new ArrayList<>());

					indexerPostProcessors.add(indexerPostProcessor);

					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Registration of indexer post processor for ",
								indexerClassName,
								" will be completed once the indexer becomes ",
								"available"));
					}
				}
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		ServiceTrackerMap<String, Indexer> indexerServiceTrackerMap =
			_indexerServiceTrackerMap;

		if (indexerServiceTrackerMap != null) {
			_indexerServiceTrackerMap.close();
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_indexerRegistryConfiguration = ConfigurableUtil.createConfigurable(
			IndexerRegistryConfiguration.class, properties);

		for (BufferedIndexerInvocationHandler bufferedIndexerInvocationHandler :
				_bufferedInvocationHandlers.values()) {

			bufferedIndexerInvocationHandler.setIndexerRegistryConfiguration(
				_indexerRegistryConfiguration);
		}
	}

	protected void removeIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor,
		Map<String, Object> properties) {

		List<String> indexerClassNames = StringPlus.asList(
			properties.get("indexer.class.name"));

		for (String indexerClassName : indexerClassNames) {
			Indexer<?> indexer = getIndexer(indexerClassName);

			if (indexer == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("No indexer exists for " + indexerClassName);
				}
			}
			else {
				indexer.unregisterIndexerPostProcessor(indexerPostProcessor);
			}

			synchronized (_queuedIndexerPostProcessors) {
				List<IndexerPostProcessor> indexerPostProcessors =
					_queuedIndexerPostProcessors.get(indexerClassName);

				if (indexerPostProcessors != null) {
					indexerPostProcessors.remove(indexerPostProcessor);

					if (indexerPostProcessors.isEmpty()) {
						_queuedIndexerPostProcessors.remove(indexerClassName);
					}
				}
			}
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setIndexerRequestBufferOverflowHandler(
		IndexerRequestBufferOverflowHandler
			indexerRequestBufferOverflowHandler) {

		_indexerRequestBufferOverflowHandler =
			indexerRequestBufferOverflowHandler;

		for (BufferedIndexerInvocationHandler bufferedIndexerInvocationHandler :
				_bufferedInvocationHandlers.values()) {

			bufferedIndexerInvocationHandler.
				setIndexerRequestBufferOverflowHandler(
					_indexerRequestBufferOverflowHandler);
		}
	}

	protected void unsetIndexerRequestBufferOverflowHandler(
		IndexerRequestBufferOverflowHandler
			indexerRequestBufferOverflowHandler) {

		_indexerRequestBufferOverflowHandler = null;

		for (BufferedIndexerInvocationHandler bufferedIndexerInvocationHandler :
				_bufferedInvocationHandlers.values()) {

			bufferedIndexerInvocationHandler.
				setIndexerRequestBufferOverflowHandler(
					_defaultIndexerRequestBufferOverflowHandler);
		}
	}

	private ServiceTrackerMap<String, Indexer> _getIndexerServiceTrackerMap() {
		ServiceTrackerMap<String, Indexer> indexerServiceTrackerMap =
			_indexerServiceTrackerMap;

		if (indexerServiceTrackerMap != null) {
			return indexerServiceTrackerMap;
		}

		synchronized (this) {
			if (_indexerServiceTrackerMap == null) {
				_indexerServiceTrackerMap =
					ServiceTrackerMapFactory.openSingleValueMap(
						_bundleContext, Indexer.class, null,
						(serviceReference, emitter) -> {
							Indexer<?> indexer = _bundleContext.getService(
								serviceReference);

							emitter.emit(indexer.getClassName());

							Class<?> clazz = indexer.getClass();

							emitter.emit(clazz.getName());

							_bundleContext.ungetService(serviceReference);
						},
						new ServiceTrackerCustomizer<Indexer, Indexer>() {

							@Override
							public Indexer addingService(
								ServiceReference<Indexer> serviceReference) {

								Indexer<?> indexer = _bundleContext.getService(
									serviceReference);

								Class<?> clazz = indexer.getClass();

								synchronized (_queuedIndexerPostProcessors) {
									List<IndexerPostProcessor>
										indexerPostProcessors =
											_queuedIndexerPostProcessors.remove(
												clazz.getName());

									if (indexerPostProcessors != null) {
										for (IndexerPostProcessor
												indexerPostProcessor :
													indexerPostProcessors) {

											indexer.
												registerIndexerPostProcessor(
													indexerPostProcessor);
										}
									}

									indexerPostProcessors =
										_queuedIndexerPostProcessors.remove(
											indexer.getClassName());

									if (indexerPostProcessors != null) {
										for (IndexerPostProcessor
												indexerPostProcessor :
													indexerPostProcessors) {

											indexer.
												registerIndexerPostProcessor(
													indexerPostProcessor);
										}
									}
								}

								return indexer;
							}

							@Override
							public void modifiedService(
								ServiceReference<Indexer> serviceReference,
								Indexer indexer) {
							}

							@Override
							public void removedService(
								ServiceReference<Indexer> serviceReference,
								Indexer indexer) {

								Class<?> clazz = indexer.getClass();

								_bufferedInvocationHandlers.remove(
									clazz.getName());
								_proxiedIndexers.remove(clazz.getName());

								_bufferedInvocationHandlers.remove(
									indexer.getClassName());
								_proxiedIndexers.remove(indexer.getClassName());
							}

						});
			}

			indexerServiceTrackerMap = _indexerServiceTrackerMap;
		}

		return indexerServiceTrackerMap;
	}

	private <T> Indexer<T> _proxyIndexer(Indexer<T> indexer) {
		if (indexer == null) {
			return null;
		}

		IndexerRequestBuffer indexerRequestBuffer = IndexerRequestBuffer.get();

		if ((indexerRequestBuffer == null) ||
			!_indexerRegistryConfiguration.buffered()) {

			return indexer;
		}

		Indexer<?> proxiedIndexer = _proxiedIndexers.get(
			indexer.getClassName());

		if (proxiedIndexer == null) {
			List<?> interfaces = ClassUtils.getAllInterfaces(
				indexer.getClass());

			BufferedIndexerInvocationHandler bufferedIndexerInvocationHandler =
				new BufferedIndexerInvocationHandler(
					indexer, _indexStatusManager, _indexerRegistryConfiguration,
					_persistedModelLocalServiceRegistry);

			if (_indexerRequestBufferOverflowHandler == null) {
				bufferedIndexerInvocationHandler.
					setIndexerRequestBufferOverflowHandler(
						_defaultIndexerRequestBufferOverflowHandler);
			}
			else {
				bufferedIndexerInvocationHandler.
					setIndexerRequestBufferOverflowHandler(
						_indexerRequestBufferOverflowHandler);
			}

			_bufferedInvocationHandlers.put(
				indexer.getClassName(), bufferedIndexerInvocationHandler);

			proxiedIndexer = (Indexer<?>)ProxyUtil.newProxyInstance(
				PortalClassLoaderUtil.getClassLoader(),
				interfaces.toArray(new Class<?>[0]),
				bufferedIndexerInvocationHandler);

			_proxiedIndexers.put(indexer.getClassName(), proxiedIndexer);
		}

		return (Indexer<T>)proxiedIndexer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRegistryImpl.class);

	private final Map<String, BufferedIndexerInvocationHandler>
		_bufferedInvocationHandlers = new ConcurrentHashMap<>();
	private BundleContext _bundleContext;

	@Reference(target = "(mode=DEFAULT)")
	private IndexerRequestBufferOverflowHandler
		_defaultIndexerRequestBufferOverflowHandler;

	private final Indexer<?> _dummyIndexer = new DummyIndexer();
	private volatile IndexerRegistryConfiguration _indexerRegistryConfiguration;
	private volatile IndexerRequestBufferOverflowHandler
		_indexerRequestBufferOverflowHandler;
	private volatile ServiceTrackerMap<String, Indexer>
		_indexerServiceTrackerMap;

	@Reference
	private IndexStatusManager _indexStatusManager;

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

	private final Map<String, Indexer<? extends Object>> _proxiedIndexers =
		new ConcurrentHashMap<>();
	private final Map<String, List<IndexerPostProcessor>>
		_queuedIndexerPostProcessors = new HashMap<>();

}