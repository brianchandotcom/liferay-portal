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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryConfigContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.SearchContextContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.framework.BundleContext;

/**
 * @author Michael C. Han
 */
public class ModelSearchConfiguratorImpl<T extends BaseModel<?>>
	implements ModelSearchConfigurator<T> {

	public ModelSearchConfiguratorImpl(
		BundleContext bundleContext,
		ModelIndexerWriterContributor<T> modelIndexerWriterContributor,
		ModelVisibilityContributor modelVisibilityContributor,
		ModelSearchSettings modelSearchSettings,
		ModelSummaryContributor modelSummaryContributor) {

		_modelIndexerWriterContributor = modelIndexerWriterContributor;
		_modelVisibilityContributor = modelVisibilityContributor;
		_modelSearchSettings = modelSearchSettings;
		_modelSummaryContributor = modelSummaryContributor;

		String className = _modelSearchSettings.getClassName();

		_keywordQueryContributors = ServiceTrackerListFactory.open(
			bundleContext, KeywordQueryContributor.class,
			"(indexer.class.name=" + className + ")");

		_queryConfigContributors = ServiceTrackerListFactory.open(
			bundleContext, QueryConfigContributor.class,
			"(indexer.class.name=" + className + ")");

		_queryPreFilterContributors = ServiceTrackerListFactory.open(
			bundleContext, QueryPreFilterContributor.class,
			"(indexer.class.name=" + className + ")");

		_searchContextContributors = ServiceTrackerListFactory.open(
			bundleContext, SearchContextContributor.class,
			"(indexer.class.name=" + className + ")");

		_modelDocumentContributors = ServiceTrackerListFactory.open(
			bundleContext, ModelDocumentContributor.class,
			"(indexer.class.name=" + modelSearchSettings.getClassName() + ")");
	}

	@Override
	public void close() {
		_modelDocumentContributors.close();
		_keywordQueryContributors.close();
		_queryConfigContributors.close();
		_queryPreFilterContributors.close();
		_searchContextContributors.close();
	}

	@Override
	public String getClassName() {
		return _modelSearchSettings.getClassName();
	}

	@Override
	public Iterable<KeywordQueryContributor> getKeywordQueryContributors() {
		return _keywordQueryContributors;
	}

	@Override
	public Iterable<ModelDocumentContributor> getModelDocumentContributors() {
		return _modelDocumentContributors;
	}

	@Override
	public ModelIndexerWriterContributor<T> getModelIndexerWriterContributor() {
		return _modelIndexerWriterContributor;
	}

	@Override
	public ModelSearchSettings getModelSearchSettings() {
		return _modelSearchSettings;
	}

	@Override
	public ModelSummaryContributor getModelSummaryBuilder() {
		return _modelSummaryContributor;
	}

	@Override
	public ModelVisibilityContributor getModelVisibilityContributor() {
		return _modelVisibilityContributor;
	}

	@Override
	public Iterable<QueryConfigContributor> getQueryConfigContributors() {
		return _queryConfigContributors;
	}

	@Override
	public Iterable<QueryPreFilterContributor> getQueryPreFilterContributors() {
		return _queryPreFilterContributors;
	}

	@Override
	public Iterable<SearchContextContributor> getSearchContextContributors() {
		return _searchContextContributors;
	}

	private final ServiceTrackerList
		<KeywordQueryContributor, KeywordQueryContributor>
			_keywordQueryContributors;
	private final ServiceTrackerList
		<ModelDocumentContributor, ModelDocumentContributor>
			_modelDocumentContributors;
	private final ModelIndexerWriterContributor<T>
		_modelIndexerWriterContributor;
	private final ModelSearchSettings _modelSearchSettings;
	private final ModelSummaryContributor _modelSummaryContributor;
	private final ModelVisibilityContributor _modelVisibilityContributor;
	private final ServiceTrackerList
		<QueryConfigContributor, QueryConfigContributor>
			_queryConfigContributors;
	private final ServiceTrackerList
		<QueryPreFilterContributor, QueryPreFilterContributor>
			_queryPreFilterContributors;
	private final ServiceTrackerList
		<SearchContextContributor, SearchContextContributor>
			_searchContextContributors;

}