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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineConfigurator;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.queue.QueuingSearchEngine;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.configuration.SearchEngineHelperConfiguration;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.SearchEngineHelperConfiguration",
	immediate = true, service = SearchEngineHelper.class
)
public class SearchEngineHelperImpl implements SearchEngineHelper {

	@Override
	public void flushQueuedSearchEngine() {
		synchronized (_queuingSearchEngines) {
			for (QueuingSearchEngine queuingSearchEngine :
					_queuingSearchEngines.values()) {

				queuingSearchEngine.flush();
			}

			_queuingSearchEngines.clear();
		}
	}

	@Override
	public void flushQueuedSearchEngine(String searchEngineId) {
		QueuingSearchEngine queuingSearchEngine = null;

		synchronized (_queuingSearchEngines) {
			queuingSearchEngine = _queuingSearchEngines.remove(searchEngineId);
		}

		if (queuingSearchEngine != null) {
			queuingSearchEngine.flush();
		}
	}

	@Override
	public Collection<Long> getCompanyIds() {
		return _companyIds.keySet();
	}

	@Override
	public String getDefaultSearchEngineId() {
		if (_defaultSearchEngineId == null) {
			return SYSTEM_ENGINE_ID;
		}

		return _defaultSearchEngineId;
	}

	@Override
	public String[] getEntryClassNames() {
		Set<String> assetEntryClassNames = new HashSet<>();

		for (Indexer<?> indexer : IndexerRegistryUtil.getIndexers()) {
			for (String className : indexer.getSearchClassNames()) {
				if (!_excludedEntryClassNames.contains(className)) {
					assetEntryClassNames.add(className);
				}
			}
		}

		return assetEntryClassNames.toArray(new String[0]);
	}

	@Override
	public SearchEngine getSearchEngine(String searchEngineId) {
		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		if (searchEngine != null) {
			return searchEngine;
		}

		synchronized (_queuingSearchEngines) {
			searchEngine = _queuingSearchEngines.get(searchEngineId);

			if (searchEngine == null) {
				QueuingSearchEngine queuingSearchEngine =
					new QueuingSearchEngine(_queueCapacity);

				_queuingSearchEngines.put(searchEngineId, queuingSearchEngine);

				searchEngine = queuingSearchEngine;
			}

			if (_log.isInfoEnabled()) {
				_log.info("Returning queuing search engine");
			}

			return searchEngine;
		}
	}

	@Override
	public String getSearchEngineId(Collection<Document> documents) {
		if (!documents.isEmpty()) {
			Iterator<Document> iterator = documents.iterator();

			Document document = iterator.next();

			return getSearchEngineId(document);
		}

		return getDefaultSearchEngineId();
	}

	@Override
	public String getSearchEngineId(Document document) {
		String entryClassName = document.get("entryClassName");

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(entryClassName);

		String searchEngineId = indexer.getSearchEngineId();

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Search engine ID ", searchEngineId, " is associated with ",
					ClassUtil.getClassName(indexer)));
		}

		return searchEngineId;
	}

	@Override
	public Set<String> getSearchEngineIds() {
		return _searchEngines.keySet();
	}

	@Override
	public Collection<SearchEngine> getSearchEngines() {
		return Collections.unmodifiableCollection(_searchEngines.values());
	}

	@Override
	public SearchEngine getSearchEngineSilent(String searchEngineId) {
		return _searchEngines.get(searchEngineId);
	}

	@Override
	public String getSearchReaderDestinationName(String searchEngineId) {
		return StringBundler.concat(
			DestinationNames.SEARCH_READER, StringPool.SLASH, searchEngineId);
	}

	@Override
	public String getSearchWriterDestinationName(String searchEngineId) {
		return StringBundler.concat(
			DestinationNames.SEARCH_WRITER, StringPool.SLASH, searchEngineId);
	}

	@Override
	public synchronized void initialize(long companyId) {
		if (_companyIds.containsKey(companyId)) {
			return;
		}

		_companyIds.put(companyId, companyId);

		for (SearchEngine searchEngine : _searchEngines.values()) {
			searchEngine.initialize(companyId);
		}
	}

	@Override
	public synchronized void removeCompany(long companyId) {
		if (!_companyIds.containsKey(companyId)) {
			return;
		}

		for (SearchEngine searchEngine : _searchEngines.values()) {
			searchEngine.removeCompany(companyId);
		}

		_companyIds.remove(companyId);
	}

	@Override
	public SearchEngine removeSearchEngine(String searchEngineId) {
		return _searchEngines.remove(searchEngineId);
	}

	@Override
	public void setDefaultSearchEngineId(String defaultSearchEngineId) {
		_defaultSearchEngineId = defaultSearchEngineId;
	}

	@Override
	public void setQueueCapacity(int queueCapacity) {
		_queueCapacity = queueCapacity;
	}

	@Override
	public void setSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		_searchEngines.put(searchEngineId, searchEngine);

		for (Long companyId : _companyIds.keySet()) {
			searchEngine.initialize(companyId);
		}

		synchronized (_queuingSearchEngines) {
			QueuingSearchEngine queuingSearchEngine = _queuingSearchEngines.get(
				searchEngineId);

			if (queuingSearchEngine != null) {
				try {
					queuingSearchEngine.invokeQueued(
						searchEngine.getIndexWriter());
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to execute pending write events for " +
								"engine: " + searchEngineId,
							exception);
					}
				}
			}
		}
	}

	@Activate
	@Modified
	protected synchronized void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		SearchEngineHelperConfiguration searchEngineHelperConfiguration =
			ConfigurableUtil.createConfigurable(
				SearchEngineHelperConfiguration.class, properties);

		_excludedEntryClassNames.clear();

		Collections.addAll(
			_excludedEntryClassNames,
			searchEngineHelperConfiguration.excludedEntryClassNames());

		_serviceTracker = new ServiceTracker<>(
			bundleContext, SearchEngineConfigurator.class,
			new ServiceTrackerCustomizer
				<SearchEngineConfigurator, SearchEngineConfigurator>() {

				@Override
				public SearchEngineConfigurator addingService(
					ServiceReference<SearchEngineConfigurator>
						serviceReference) {

					if (GetterUtil.getBoolean(
							serviceReference.getProperty("original.bean"))) {

						return null;
					}

					SearchEngineConfigurator searchEngineConfigurator =
						bundleContext.getService(serviceReference);

					searchEngineConfigurator.afterPropertiesSet();

					return searchEngineConfigurator;
				}

				@Override
				public void modifiedService(
					ServiceReference<SearchEngineConfigurator> serviceReference,
					SearchEngineConfigurator searchEngineConfigurator) {
				}

				@Override
				public void removedService(
					ServiceReference<SearchEngineConfigurator> serviceReference,
					SearchEngineConfigurator searchEngineConfigurator) {

					searchEngineConfigurator.destroy();

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchEngineHelperImpl.class);

	private final Map<Long, Long> _companyIds = new ConcurrentHashMap<>();
	private String _defaultSearchEngineId;
	private final Set<String> _excludedEntryClassNames = new HashSet<>();
	private int _queueCapacity = 200;
	private final Map<String, QueuingSearchEngine> _queuingSearchEngines =
		new HashMap<>();
	private final Map<String, SearchEngine> _searchEngines =
		new ConcurrentHashMap<>();
	private ServiceTracker<SearchEngineConfigurator, SearchEngineConfigurator>
		_serviceTracker;

}