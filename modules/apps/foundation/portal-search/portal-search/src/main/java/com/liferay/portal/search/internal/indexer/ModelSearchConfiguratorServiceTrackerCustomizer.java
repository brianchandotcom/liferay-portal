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

package com.liferay.portal.search.internal.indexer;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.IndexSearcherHelper;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.search.hits.HitsProcessorRegistry;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.search.index.UpdateDocumentIndexWriter;
import com.liferay.portal.search.indexer.BaseModelDocumentFactory;
import com.liferay.portal.search.indexer.BaseModelRetriever;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerPermissionPostFilter;
import com.liferay.portal.search.indexer.IndexerQueryBuilder;
import com.liferay.portal.search.indexer.IndexerSearcher;
import com.liferay.portal.search.indexer.IndexerSummaryBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;
import com.liferay.portal.search.permission.SearchPermissionIndexWriter;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryConfigContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.SearchContextContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	service = ModelSearchConfiguratorServiceTrackerCustomizer.class
)
public class ModelSearchConfiguratorServiceTrackerCustomizer
	<T extends BaseModel<?>>
		implements ServiceTrackerCustomizer
			<ModelSearchConfigurator, ModelSearchConfigurator> {

	@Override
	@SuppressWarnings(value = "unchecked")
	public ModelSearchConfigurator addingService(
		ServiceReference<ModelSearchConfigurator> serviceReference) {

		int serviceRanking = GetterUtil.getInteger(
			serviceReference.getProperty(Constants.SERVICE_RANKING));

		final ModelSearchConfigurator<T> modelSearchConfigurator =
			_bundleContext.getService(serviceReference);

		ServiceRegistrationHolder serviceRegistrationHolder =
			_serviceRegistrationHolders.get(
				modelSearchConfigurator.getClassName());

		if ((serviceRegistrationHolder != null) &&
			(serviceRegistrationHolder._serviceRanking > serviceRanking)) {

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(5);

				sb.append(ClassUtil.getClassName(serviceRegistrationHolder));
				sb.append(" is already registered with a higher ranking of ");
				sb.append(serviceRegistrationHolder._serviceRanking);
				sb.append(" for: ");
				sb.append(modelSearchConfigurator.getClassName());

				_log.warn(sb.toString());
			}

			return modelSearchConfigurator;
		}

		serviceRegistrationHolder = new ServiceRegistrationHolder(
			modelSearchConfigurator, serviceRanking);

		Dictionary<String, ?> serviceProperties = new Hashtable<>(
			Collections.singletonMap(
				"indexer.class.name", modelSearchConfigurator.getClassName()));

		Indexer<?> defaultIndexer = buildIndexer(
			modelSearchConfigurator, serviceRegistrationHolder,
			serviceProperties);

		ServiceRegistration<Indexer> indexerServiceRegistration =
			_bundleContext.registerService(
				Indexer.class, defaultIndexer, serviceProperties);

		serviceRegistrationHolder.setIndexerServiceRegistration(
			indexerServiceRegistration);

		_serviceRegistrationHolders.put(
			modelSearchConfigurator.getClassName(), serviceRegistrationHolder);

		return modelSearchConfigurator;
	}

	@Override
	public void modifiedService(
		ServiceReference<ModelSearchConfigurator> serviceReference,
		ModelSearchConfigurator modelSearchConfigurator) {

		removedService(serviceReference, modelSearchConfigurator);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<ModelSearchConfigurator> serviceReference,
		ModelSearchConfigurator modelSearchConfigurator) {

		ServiceRegistrationHolder serviceRegistrationHolder =
			_serviceRegistrationHolders.remove(
				modelSearchConfigurator.getClassName());

		if (serviceRegistrationHolder != null) {
			serviceRegistrationHolder.close();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_documentContributors = ServiceTrackerListFactory.open(
			_bundleContext, DocumentContributor.class,
			"(!(indexer.class.name=*))");

		_keywordQueryContributors = ServiceTrackerListFactory.open(
			_bundleContext, KeywordQueryContributor.class,
			"(!(indexer.class.name=*))");

		_modelResourcePermissionServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ModelResourcePermission.class,
				"model.class.name");

		_queryConfigContributors = ServiceTrackerListFactory.open(
			_bundleContext, QueryConfigContributor.class,
			"(!(indexer.class.name=*))");

		_queryPreFilterContributors = ServiceTrackerListFactory.open(
			_bundleContext, QueryPreFilterContributor.class,
			"(!(indexer.class.name=*))");

		_searchContextContributors = ServiceTrackerListFactory.open(
			_bundleContext, SearchContextContributor.class,
			"(!(indexer.class.name=*))");

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, ModelSearchConfigurator.class, this);
	}

	protected Indexer<?> buildIndexer(
		ModelSearchConfigurator<T> modelSearchConfigurator,
		ServiceRegistrationHolder serviceRegistrationHolder,
		Dictionary<String, ?> serviceProperties) {

		Iterable<ModelDocumentContributor> modelDocumentContributors =
			modelSearchConfigurator.getModelDocumentContributors();

		Iterable<DocumentContributor> documentContributors =
			_documentContributors;

		IndexerPostProcessorsHolder indexerPostProcessorsHolder =
			new IndexerPostProcessorsHolder();

		IndexerDocumentBuilder indexerDocumentBuilder =
			new IndexerDocumentBuilderImpl(
				baseModelDocumentFactory, modelDocumentContributors,
				documentContributors, indexerPostProcessorsHolder);

		ServiceRegistration<IndexerDocumentBuilder>
			indexerDocumentBuilderServiceRegistration =
				_bundleContext.registerService(
					IndexerDocumentBuilder.class, indexerDocumentBuilder,
					serviceProperties);

		serviceRegistrationHolder.setIndexerDocumentBuilderServiceRegistration(
			indexerDocumentBuilderServiceRegistration);

		IndexerQueryBuilder indexerQueryBuilder = new IndexerQueryBuilderImpl<>(
			modelSearchConfigurator.getModelSearchSettings(),
			modelSearchConfigurator.getKeywordQueryContributors(),
			modelSearchConfigurator.getQueryPreFilterContributors(),
			modelSearchConfigurator.getSearchContextContributors(),
			_keywordQueryContributors, _queryPreFilterContributors,
			_searchContextContributors, indexerPostProcessorsHolder,
			_relatedEntryIndexerRegistry);

		ServiceRegistration<IndexerQueryBuilder>
			indexerQueryBuilderServiceRegistration =
				_bundleContext.registerService(
					IndexerQueryBuilder.class, indexerQueryBuilder,
					serviceProperties);

		serviceRegistrationHolder.setIndexerQueryBuilderServiceRegistration(
			indexerQueryBuilderServiceRegistration);

		IndexerPermissionPostFilter indexerPermissionPostFilter =
			new IndexerPermissionPostFilterImpl(
				() -> Optional.ofNullable(
					_modelResourcePermissionServiceTrackerMap.getService(
						modelSearchConfigurator.getClassName())),
				() -> Optional.ofNullable(
					modelSearchConfigurator.getModelVisibilityContributor()));

		ServiceRegistration<IndexerPermissionPostFilter>
			indexerPermissionPostFilterServiceRegistration =
				_bundleContext.registerService(
					IndexerPermissionPostFilter.class,
					indexerPermissionPostFilter, serviceProperties);

		serviceRegistrationHolder.
			setIndexerPermissionPostFilterServiceRegistration(
				indexerPermissionPostFilterServiceRegistration);

		IndexerSearcher indexerSearcher = new IndexerSearcherImpl<>(
			modelSearchConfigurator.getModelSearchSettings(),
			modelSearchConfigurator.getQueryConfigContributors(),
			indexerPermissionPostFilter, indexerQueryBuilder,
			hitsProcessorRegistry, indexSearcherHelper,
			_queryConfigContributors);

		ServiceRegistration<IndexerSearcher>
			indexerSearcherServiceRegistration = _bundleContext.registerService(
				IndexerSearcher.class, indexerSearcher, serviceProperties);

		serviceRegistrationHolder.setIndexerSearcherServiceRegistration(
			indexerSearcherServiceRegistration);

		IndexerWriter<?> indexerWriter = new IndexerWriterImpl<>(
			modelSearchConfigurator.getModelSearchSettings(),
			baseModelRetriever,
			modelSearchConfigurator.getModelIndexerWriterContributor(),
			indexerDocumentBuilder, searchPermissionIndexWriter,
			updateDocumentIndexWriter, indexStatusManager, indexWriterHelper,
			props);

		ServiceRegistration<IndexerWriter> indexerWriterServiceRegistration =
			_bundleContext.registerService(
				IndexerWriter.class, indexerWriter, serviceProperties);

		serviceRegistrationHolder.setIndexerWriterServiceRegistration(
			indexerWriterServiceRegistration);

		IndexerSummaryBuilder indexerSummaryBuilder =
			new IndexerSummaryBuilderImpl(
				modelSearchConfigurator.getModelSummaryBuilder(),
				indexerPostProcessorsHolder);

		ServiceRegistration<IndexerSummaryBuilder>
			indexerSummaryBuilderServiceRegistration =
				_bundleContext.registerService(
					IndexerSummaryBuilder.class, indexerSummaryBuilder,
					serviceProperties);

		serviceRegistrationHolder.setIndexerSummaryBuilderServiceRegistration(
			indexerSummaryBuilderServiceRegistration);

		return new DefaultIndexer<>(
			modelSearchConfigurator.getModelSearchSettings(),
			indexerDocumentBuilder, indexerSearcher, indexerWriter,
			indexerPermissionPostFilter, indexerQueryBuilder,
			indexerSummaryBuilder, indexerPostProcessorsHolder);
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;

		_serviceTracker.close();
		_documentContributors.close();
		_keywordQueryContributors.close();
		_queryPreFilterContributors.close();
		_queryConfigContributors.close();
		_searchContextContributors.close();

		_serviceRegistrationHolders.forEach(
			(key, serviceRegistrationHolder) ->
				serviceRegistrationHolder.close());
	}

	@Reference
	protected BaseModelDocumentFactory baseModelDocumentFactory;

	@Reference
	protected BaseModelRetriever baseModelRetriever;

	@Reference
	protected HitsProcessorRegistry hitsProcessorRegistry;

	@Reference
	protected IndexSearcherHelper indexSearcherHelper;

	@Reference
	protected IndexStatusManager indexStatusManager;

	@Reference
	protected IndexWriterHelper indexWriterHelper;

	@Reference
	protected Props props;

	@Reference
	protected SearchPermissionIndexWriter searchPermissionIndexWriter;

	@Reference
	protected UpdateDocumentIndexWriter updateDocumentIndexWriter;

	private static final Log _log = LogFactoryUtil.getLog(
		ModelSearchConfiguratorServiceTrackerCustomizer.class);

	private BundleContext _bundleContext;
	private ServiceTrackerList<DocumentContributor, DocumentContributor>
		_documentContributors;
	private ServiceTrackerList<KeywordQueryContributor, KeywordQueryContributor>
		_keywordQueryContributors;
	private ServiceTrackerMap<String, ModelResourcePermission>
		_modelResourcePermissionServiceTrackerMap;
	private ServiceTrackerList<QueryConfigContributor, QueryConfigContributor>
		_queryConfigContributors;
	private ServiceTrackerList
		<QueryPreFilterContributor, QueryPreFilterContributor>
			_queryPreFilterContributors;

	@Reference
	private RelatedEntryIndexerRegistry _relatedEntryIndexerRegistry;

	private ServiceTrackerList
		<SearchContextContributor, SearchContextContributor>
			_searchContextContributors;
	private final Map<String, ServiceRegistrationHolder>
		_serviceRegistrationHolders = new Hashtable<>();
	private ServiceTracker
		<ModelSearchConfigurator, ModelSearchConfigurator> _serviceTracker;

	private class ServiceRegistrationHolder {

		public ServiceRegistrationHolder(
			ModelSearchConfigurator modelSearchConfigurator,
			int serviceRanking) {

			_modelSearchConfigurator = modelSearchConfigurator;
			_serviceRanking = serviceRanking;
		}

		public void close() {
			_modelSearchConfigurator.close();

			if (_indexerDocumentBuilderServiceRegistration != null) {
				_indexerDocumentBuilderServiceRegistration.unregister();
			}

			if (_indexerPermissionPostFilterServiceRegistration != null) {
				_indexerPermissionPostFilterServiceRegistration.unregister();
			}

			if (_indexerQueryBuilderServiceRegistration != null) {
				_indexerQueryBuilderServiceRegistration.unregister();
			}

			if (_indexerSearcherServiceRegistration != null) {
				_indexerSearcherServiceRegistration.unregister();
			}

			if (_indexerServiceRegistration != null) {
				_indexerServiceRegistration.unregister();
			}

			if (_indexerSummaryBuilderServiceRegistration != null) {
				_indexerSummaryBuilderServiceRegistration.unregister();
			}

			if (_indexerWriterServiceRegistration != null) {
				_indexerWriterServiceRegistration.unregister();
			}
		}

		public void setIndexerDocumentBuilderServiceRegistration(
			ServiceRegistration<IndexerDocumentBuilder>
				indexerDocumentBuilderServiceRegistration) {

			_indexerDocumentBuilderServiceRegistration =
				indexerDocumentBuilderServiceRegistration;
		}

		public void setIndexerPermissionPostFilterServiceRegistration(
			ServiceRegistration<IndexerPermissionPostFilter>
				indexerPermissionPostFilterServiceRegistration) {

			_indexerPermissionPostFilterServiceRegistration =
				indexerPermissionPostFilterServiceRegistration;
		}

		public void setIndexerQueryBuilderServiceRegistration(
			ServiceRegistration<IndexerQueryBuilder>
				indexerQueryBuilderServiceRegistration) {

			_indexerQueryBuilderServiceRegistration =
				indexerQueryBuilderServiceRegistration;
		}

		public void setIndexerSearcherServiceRegistration(
			ServiceRegistration<IndexerSearcher>
				indexerSearcherServiceRegistration) {

			_indexerSearcherServiceRegistration =
				indexerSearcherServiceRegistration;
		}

		public void setIndexerServiceRegistration(
			ServiceRegistration<Indexer> indexerServiceRegistration) {

			_indexerServiceRegistration = indexerServiceRegistration;
		}

		public void setIndexerSummaryBuilderServiceRegistration(
			ServiceRegistration<IndexerSummaryBuilder>
				indexerSummaryBuilderServiceRegistration) {

			_indexerSummaryBuilderServiceRegistration =
				indexerSummaryBuilderServiceRegistration;
		}

		public void setIndexerWriterServiceRegistration(
			ServiceRegistration<IndexerWriter>
				indexerWriterServiceRegistration) {

			_indexerWriterServiceRegistration =
				indexerWriterServiceRegistration;
		}

		private ServiceRegistration<IndexerDocumentBuilder>
			_indexerDocumentBuilderServiceRegistration;
		private ServiceRegistration<IndexerPermissionPostFilter>
			_indexerPermissionPostFilterServiceRegistration;
		private ServiceRegistration<IndexerQueryBuilder>
			_indexerQueryBuilderServiceRegistration;
		private ServiceRegistration<IndexerSearcher>
			_indexerSearcherServiceRegistration;
		private ServiceRegistration<Indexer> _indexerServiceRegistration;
		private ServiceRegistration<IndexerSummaryBuilder>
			_indexerSummaryBuilderServiceRegistration;
		private ServiceRegistration<IndexerWriter>
			_indexerWriterServiceRegistration;
		private final ModelSearchConfigurator<?> _modelSearchConfigurator;
		private final int _serviceRanking;

	}

}