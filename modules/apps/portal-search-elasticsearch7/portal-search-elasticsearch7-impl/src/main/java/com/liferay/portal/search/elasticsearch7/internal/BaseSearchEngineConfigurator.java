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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineConfigurator;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchEngineProxyWrapper;
import com.liferay.portal.kernel.search.messaging.BaseSearchEngineMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchReaderMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchWriterMessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Michael C. Han
 */
public abstract class BaseSearchEngineConfigurator
	implements SearchEngineConfigurator {

	@Override
	public void afterPropertiesSet() {
	}

	@Override
	public void destroy() {
		for (SearchEngineRegistration searchEngineRegistration :
				_searchEngineRegistrations) {

			_destroySearchEngine(searchEngineRegistration);
		}

		_searchEngineRegistrations.clear();

		if (Validator.isNotNull(_originalSearchEngineId)) {
			SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

			searchEngineHelper.setDefaultSearchEngineId(
				_originalSearchEngineId);

			_originalSearchEngineId = null;
		}

		for (DestinationServiceRegistrar destinationServiceRegistrar :
				_destinationServiceRegistrars.values()) {

			destinationServiceRegistrar.destroy();
		}

		_destinationServiceRegistrars.clear();
	}

	@Override
	public void setSearchEngines(Map<String, SearchEngine> searchEngines) {
		_searchEngines = searchEngines;
	}

	public interface DestinationServiceRegistrarHelper {

		public Destination getDestination(
			ServiceRegistration<Destination> serviceRegistration);

		public ServiceRegistration<Destination> registerDestination(
			Destination destination);

	}

	public interface SearchDestinationHelper {

		public Destination createSearchReaderDestination(
			String searchReaderDestinationName);

		public Destination createSearchWriterDestination(
			String searchWriterDestinationName);

	}

	protected Destination createSearchReaderDestination(
		String searchReaderDestinationName) {

		DestinationConfiguration destinationConfiguration =
			DestinationConfiguration.createSynchronousDestinationConfiguration(
				searchReaderDestinationName);

		DestinationFactory destinationFactory = getDestinationFactory();

		return destinationFactory.createDestination(destinationConfiguration);
	}

	protected Destination createSearchWriterDestination(
		String searchWriterDestinationName) {

		DestinationConfiguration destinationConfiguration = null;

		if (PortalRunMode.isTestMode()) {
			destinationConfiguration =
				DestinationConfiguration.
					createSynchronousDestinationConfiguration(
						searchWriterDestinationName);
		}
		else {
			destinationConfiguration =
				DestinationConfiguration.createParallelDestinationConfiguration(
					searchWriterDestinationName);
		}

		if (_INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE > 0) {
			destinationConfiguration.setMaximumQueueSize(
				_INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE);

			RejectedExecutionHandler rejectedExecutionHandler =
				new ThreadPoolExecutor.CallerRunsPolicy() {

					@Override
					public void rejectedExecution(
						Runnable runnable,
						ThreadPoolExecutor threadPoolExecutor) {

						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"The search index writer's task queue is ",
									"at its maximum capacity. The current ",
									"thread will handle the request."));
						}

						super.rejectedExecution(runnable, threadPoolExecutor);
					}

				};

			destinationConfiguration.setRejectedExecutionHandler(
				rejectedExecutionHandler);
		}

		DestinationFactory destinationFactory = getDestinationFactory();

		return destinationFactory.createDestination(destinationConfiguration);
	}

	protected abstract BundleContext getBundleContext();

	protected abstract String getDefaultSearchEngineId();

	protected Destination getDestination(
		ServiceRegistration<Destination> serviceRegistration) {

		BundleContext bundleContext = getBundleContext();

		return bundleContext.getService(serviceRegistration.getReference());
	}

	protected abstract DestinationFactory getDestinationFactory();

	protected abstract IndexSearcher getIndexSearcher();

	protected abstract IndexWriter getIndexWriter();

	protected abstract MessageBus getMessageBus();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getOperatingClassLoader()}
	 */
	@Deprecated
	protected ClassLoader getOperatingClassloader() {
		return getOperatingClassLoader();
	}

	protected abstract ClassLoader getOperatingClassLoader();

	protected abstract SearchEngineHelper getSearchEngineHelper();

	protected void initialize() {
		Set<Map.Entry<String, SearchEngine>> entrySet =
			_searchEngines.entrySet();

		for (Map.Entry<String, SearchEngine> entry : entrySet) {
			_initSearchEngine(entry.getKey(), entry.getValue());
		}

		String defaultSearchEngineId = getDefaultSearchEngineId();

		if (Validator.isNotNull(defaultSearchEngineId)) {
			SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

			_originalSearchEngineId =
				searchEngineHelper.getDefaultSearchEngineId();

			searchEngineHelper.setDefaultSearchEngineId(defaultSearchEngineId);
		}

		_searchEngines.clear();
	}

	protected ServiceRegistration<Destination> registerDestination(
		Destination destination) {

		BundleContext bundleContext = getBundleContext();

		return bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	protected void setDestinationServiceRegistrarHelper(
		DestinationServiceRegistrarHelper destinationServiceRegistrarHelper) {

		_destinationServiceRegistrarHelper = destinationServiceRegistrarHelper;
	}

	protected void setSearchDestinationHelper(
		SearchDestinationHelper searchDestinationHelper) {

		_searchDestinationHelper = searchDestinationHelper;
	}

	protected void setSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		searchEngineHelper.setSearchEngine(searchEngineId, searchEngine);

		searchEngine.initialize(CompanyConstants.SYSTEM);
	}

	private void _createSearchEngineListeners(
		String searchEngineId, SearchEngine searchEngine,
		Destination searchReaderDestination,
		Destination searchWriterDestination) {

		_registerSearchEngineMessageListener(
			searchEngineId, searchEngine, searchReaderDestination,
			new SearchReaderMessageListener(), searchEngine.getIndexSearcher());

		_registerSearchEngineMessageListener(
			searchEngineId, searchEngine, searchWriterDestination,
			new SearchWriterMessageListener(), searchEngine.getIndexWriter());
	}

	private void _destroySearchEngine(
		SearchEngineRegistration searchEngineRegistration) {

		MessageBus messageBus = getMessageBus();

		Destination searchReaderDestination = _getSearchReaderDestination(
			messageBus, searchEngineRegistration.getSearchEngineId(), false);

		if (searchReaderDestination != null) {
			searchReaderDestination.unregisterMessageListeners();
		}

		Destination searchWriterDestination = _getSearchWriterDestination(
			messageBus, searchEngineRegistration.getSearchEngineId(), false);

		if (searchWriterDestination != null) {
			searchWriterDestination.unregisterMessageListeners();
		}

		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		searchEngineHelper.removeSearchEngine(
			searchEngineRegistration.getSearchEngineId());

		if (!searchEngineRegistration.isOverride()) {
			DestinationServiceRegistrar destinationServiceRegistrar =
				_destinationServiceRegistrars.remove(
					searchEngineRegistration.getSearchEngineId());

			if (destinationServiceRegistrar != null) {
				destinationServiceRegistrar.destroy();
			}

			return;
		}

		SearchEngineProxyWrapper originalSearchEngineProxy =
			searchEngineRegistration.getOriginalSearchEngineProxyWrapper();

		if (searchReaderDestination != null) {
			_registerInvokerMessageListener(
				searchReaderDestination,
				searchEngineRegistration.
					getOriginalSearchReaderMessageListeners());
		}

		if (searchWriterDestination != null) {
			_registerInvokerMessageListener(
				searchWriterDestination,
				searchEngineRegistration.
					getOriginalSearchWriterMessageListeners());
		}

		setSearchEngine(
			searchEngineRegistration.getSearchEngineId(),
			originalSearchEngineProxy);
	}

	private Destination _getSearchReaderDestination(
		MessageBus messageBus, String searchEngineId, boolean createIfAbsent) {

		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		String searchReaderDestinationName =
			searchEngineHelper.getSearchReaderDestinationName(searchEngineId);

		Destination searchReaderDestination = messageBus.getDestination(
			searchReaderDestinationName);

		if (createIfAbsent && (searchReaderDestination == null)) {
			searchReaderDestination =
				_searchDestinationHelper.createSearchReaderDestination(
					searchReaderDestinationName);

			_registerSearchEngineDestination(
				searchEngineId, searchReaderDestination);
		}

		return searchReaderDestination;
	}

	private Destination _getSearchWriterDestination(
		MessageBus messageBus, String searchEngineId, boolean createIfAbsent) {

		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		String searchWriterDestinationName =
			searchEngineHelper.getSearchWriterDestinationName(searchEngineId);

		Destination searchWriterDestination = messageBus.getDestination(
			searchWriterDestinationName);

		if (createIfAbsent && (searchWriterDestination == null)) {
			searchWriterDestination =
				_searchDestinationHelper.createSearchWriterDestination(
					searchWriterDestinationName);

			_registerSearchEngineDestination(
				searchEngineId, searchWriterDestination);
		}

		return searchWriterDestination;
	}

	private void _initSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		SearchEngineRegistration searchEngineRegistration =
			new SearchEngineRegistration(searchEngineId);

		_searchEngineRegistrations.add(searchEngineRegistration);

		MessageBus messageBus = getMessageBus();

		Destination searchReaderDestination = _getSearchReaderDestination(
			messageBus, searchEngineId, true);

		searchEngineRegistration.setSearchReaderDestinationName(
			searchReaderDestination.getName());

		Destination searchWriterDestination = _getSearchWriterDestination(
			messageBus, searchEngineId, true);

		searchEngineRegistration.setSearchWriterDestinationName(
			searchWriterDestination.getName());

		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		SearchEngine originalSearchEngine =
			searchEngineHelper.getSearchEngineSilent(searchEngineId);

		if (originalSearchEngine != null) {
			searchEngineRegistration.setOverride(true);

			searchEngineRegistration.setOriginalSearchEngineProxyWrapper(
				(SearchEngineProxyWrapper)originalSearchEngine);

			_savePreviousSearchEngineListeners(
				searchReaderDestination, searchWriterDestination,
				searchEngineRegistration);

			searchReaderDestination.unregisterMessageListeners();
			searchWriterDestination.unregisterMessageListeners();
		}

		_createSearchEngineListeners(
			searchEngineId, searchEngine, searchReaderDestination,
			searchWriterDestination);

		SearchEngineProxyWrapper searchEngineProxyWrapper =
			new SearchEngineProxyWrapper(
				searchEngine, getIndexSearcher(), getIndexWriter());

		setSearchEngine(searchEngineId, searchEngineProxyWrapper);
	}

	private void _registerInvokerMessageListener(
		Destination destination,
		List<InvokerMessageListener> invokerMessageListeners) {

		for (InvokerMessageListener invokerMessageListener :
				invokerMessageListeners) {

			destination.register(
				invokerMessageListener.getMessageListener(),
				invokerMessageListener.getClassLoader());
		}
	}

	private void _registerSearchEngineDestination(
		String searchEngineId, Destination destination) {

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"destination.name", destination.getName()
		).build();

		DestinationServiceRegistrar destinationServiceRegistrar =
			_destinationServiceRegistrars.get(searchEngineId);

		if (destinationServiceRegistrar == null) {
			destinationServiceRegistrar = new DestinationServiceRegistrar(
				_destinationServiceRegistrarHelper);

			_destinationServiceRegistrars.put(
				searchEngineId, destinationServiceRegistrar);
		}

		destinationServiceRegistrar.registerService(
			Destination.class, destination, properties);
	}

	private void _registerSearchEngineMessageListener(
		String searchEngineId, SearchEngine searchEngine,
		Destination destination,
		BaseSearchEngineMessageListener baseSearchEngineMessageListener,
		Object manager) {

		baseSearchEngineMessageListener.setManager(manager);
		baseSearchEngineMessageListener.setMessageBus(getMessageBus());
		baseSearchEngineMessageListener.setSearchEngine(searchEngine);
		baseSearchEngineMessageListener.setSearchEngineId(searchEngineId);

		destination.register(
			baseSearchEngineMessageListener, getOperatingClassLoader());
	}

	private void _savePreviousSearchEngineListeners(
		Destination searchReaderDestination,
		Destination searchWriterDestination,
		SearchEngineRegistration searchEngineRegistration) {

		Set<MessageListener> searchReaderMessageListeners =
			searchReaderDestination.getMessageListeners();

		for (MessageListener searchReaderMessageListener :
				searchReaderMessageListeners) {

			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)searchReaderMessageListener;

			searchEngineRegistration.addOriginalSearchReaderMessageListener(
				invokerMessageListener);
		}

		Set<MessageListener> searchWriterMessageListeners =
			searchWriterDestination.getMessageListeners();

		for (MessageListener searchWriterMessageListener :
				searchWriterMessageListeners) {

			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)searchWriterMessageListener;

			searchEngineRegistration.addOriginalSearchWriterMessageListener(
				invokerMessageListener);
		}
	}

	private static final int _INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE));

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSearchEngineConfigurator.class);

	private DestinationServiceRegistrarHelper
		_destinationServiceRegistrarHelper =
			new DestinationServiceRegistrarHelperImpl(this);
	private final Map<String, DestinationServiceRegistrar>
		_destinationServiceRegistrars = new ConcurrentHashMap<>();
	private String _originalSearchEngineId;
	private SearchDestinationHelper _searchDestinationHelper =
		new SearchDestinationHelperImpl(this);
	private final List<SearchEngineRegistration> _searchEngineRegistrations =
		new ArrayList<>();
	private Map<String, SearchEngine> _searchEngines;

	private static class DestinationServiceRegistrar {

		public synchronized void destroy() {
			for (ServiceRegistration<Destination> serviceRegistration :
					_serviceRegistrations) {

				Destination destination =
					_destinationServiceRegistrarHelper.getDestination(
						serviceRegistration);

				serviceRegistration.unregister();

				destination.destroy();
			}

			_serviceRegistrations.clear();
		}

		public synchronized void registerService(
			Class<Destination> clazz, Destination destination,
			Map<String, Object> properties) {

			_serviceRegistrations.add(
				_destinationServiceRegistrarHelper.registerDestination(
					destination));
		}

		private DestinationServiceRegistrar(
			DestinationServiceRegistrarHelper
				destinationServiceRegistrarHelper) {

			_destinationServiceRegistrarHelper =
				destinationServiceRegistrarHelper;
		}

		private final DestinationServiceRegistrarHelper
			_destinationServiceRegistrarHelper;
		private final Set<ServiceRegistration<Destination>>
			_serviceRegistrations = new HashSet<>();

	}

	private static class DestinationServiceRegistrarHelperImpl
		implements DestinationServiceRegistrarHelper {

		@Override
		public Destination getDestination(
			ServiceRegistration<Destination> serviceRegistration) {

			return _baseSearchEngineConfigurator.getDestination(
				serviceRegistration);
		}

		@Override
		public ServiceRegistration<Destination> registerDestination(
			Destination destination) {

			return _baseSearchEngineConfigurator.registerDestination(
				destination);
		}

		private DestinationServiceRegistrarHelperImpl(
			BaseSearchEngineConfigurator baseSearchEngineConfigurator) {

			_baseSearchEngineConfigurator = baseSearchEngineConfigurator;
		}

		private final BaseSearchEngineConfigurator
			_baseSearchEngineConfigurator;

	}

	private static class SearchDestinationHelperImpl
		implements SearchDestinationHelper {

		@Override
		public Destination createSearchReaderDestination(
			String searchReaderDestinationName) {

			return _baseSearchEngineConfigurator.createSearchReaderDestination(
				searchReaderDestinationName);
		}

		@Override
		public Destination createSearchWriterDestination(
			String searchWriterDestinationName) {

			return _baseSearchEngineConfigurator.createSearchWriterDestination(
				searchWriterDestinationName);
		}

		private SearchDestinationHelperImpl(
			BaseSearchEngineConfigurator baseSearchEngineConfigurator) {

			_baseSearchEngineConfigurator = baseSearchEngineConfigurator;
		}

		private final BaseSearchEngineConfigurator
			_baseSearchEngineConfigurator;

	}

	private static class SearchEngineRegistration {

		public void addOriginalSearchReaderMessageListener(
			InvokerMessageListener messageListener) {

			_originalSearchReaderMessageListeners.add(messageListener);
		}

		public void addOriginalSearchWriterMessageListener(
			InvokerMessageListener messageListener) {

			_originalSearchWriterMessageListeners.add(messageListener);
		}

		public SearchEngineProxyWrapper getOriginalSearchEngineProxyWrapper() {
			return _originalSearchEngineProxyWrapper;
		}

		public List<InvokerMessageListener>
			getOriginalSearchReaderMessageListeners() {

			return _originalSearchReaderMessageListeners;
		}

		public List<InvokerMessageListener>
			getOriginalSearchWriterMessageListeners() {

			return _originalSearchWriterMessageListeners;
		}

		public String getSearchEngineId() {
			return _searchEngineId;
		}

		public String getSearchReaderDestinationName() {
			return _searchReaderDestinationName;
		}

		public String getSearchWriterDestinationName() {
			return _searchWriterDestinationName;
		}

		public boolean isOverride() {
			return _override;
		}

		public void setOriginalSearchEngineProxyWrapper(
			SearchEngineProxyWrapper searchEngineProxyWrapper) {

			_originalSearchEngineProxyWrapper = searchEngineProxyWrapper;
		}

		public void setOverride(boolean override) {
			_override = override;
		}

		public void setSearchReaderDestinationName(
			String searchReaderDestinationName) {

			_searchReaderDestinationName = searchReaderDestinationName;
		}

		public void setSearchWriterDestinationName(
			String searchWriterDestinationName) {

			_searchWriterDestinationName = searchWriterDestinationName;
		}

		private SearchEngineRegistration(String searchEngineId) {
			_searchEngineId = searchEngineId;
		}

		private SearchEngineProxyWrapper _originalSearchEngineProxyWrapper;
		private final List<InvokerMessageListener>
			_originalSearchReaderMessageListeners = new ArrayList<>();
		private final List<InvokerMessageListener>
			_originalSearchWriterMessageListeners = new ArrayList<>();
		private boolean _override;
		private final String _searchEngineId;
		private String _searchReaderDestinationName;
		private String _searchWriterDestinationName;

	}

}