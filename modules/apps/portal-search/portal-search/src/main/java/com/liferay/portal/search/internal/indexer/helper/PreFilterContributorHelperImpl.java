/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer.helper;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.search.internal.indexer.IndexerProvidedClausesUtil;
import com.liferay.portal.search.internal.indexer.ModelPreFilterContributorsRegistry;
import com.liferay.portal.search.internal.indexer.ModelSearchSettingsImpl;
import com.liferay.portal.search.internal.indexer.QueryPreFilterContributorsRegistry;
import com.liferay.portal.search.internal.util.SearchStringUtil;
import com.liferay.portal.search.permission.SearchPermissionFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = PreFilterContributorHelper.class)
public class PreFilterContributorHelperImpl
	implements PreFilterContributorHelper {

	@Override
	public void contribute(
		BooleanFilter booleanFilter,
		Map<String, Indexer<?>> entryClassNameIndexerMap,
		SearchContext searchContext) {

		_addPreFilters(booleanFilter, searchContext);

		BooleanFilter preFilterBooleanFilter = new BooleanFilter();

		for (Map.Entry<String, Indexer<?>> entry :
				entryClassNameIndexerMap.entrySet()) {

			String entryClassName = entry.getKey();
			Indexer<?> indexer = entry.getValue();

			preFilterBooleanFilter.add(
				_createPreFilterForEntryClassName(
					entryClassName, indexer, searchContext),
				BooleanClauseOccur.SHOULD);
		}

		if (preFilterBooleanFilter.hasClauses()) {
			booleanFilter.add(preFilterBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_addModelProvidedPreFilters(
			booleanFilter, modelSearchSettings, searchContext);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, SearchPermissionFilterContributor.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	protected Collection<String> getStrings(
		String string, SearchContext searchContext) {

		return Arrays.asList(
			SearchStringUtil.splitAndUnquote(
				(String)searchContext.getAttribute(string)));
	}

	@Reference
	protected ModelPreFilterContributorsRegistry
		modelPreFilterContributorsRegistry;

	@Reference
	protected QueryPreFilterContributorsRegistry
		queryPreFilterContributorsRegistry;

	@Reference
	protected SearchPermissionChecker searchPermissionChecker;

	private void _addIndexerProvidedPreFilters(
		BooleanFilter booleanFilter, Indexer<?> indexer,
		SearchContext searchContext) {

		if (IndexerProvidedClausesUtil.shouldSuppress(searchContext)) {
			return;
		}

		try {
			indexer.postProcessContextBooleanFilter(
				booleanFilter, searchContext);

			for (IndexerPostProcessor indexerPostProcessor :
					indexer.getIndexerPostProcessors()) {

				indexerPostProcessor.postProcessContextBooleanFilter(
					booleanFilter, searchContext);
			}
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _addModelProvidedPreFilters(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		List<ModelPreFilterContributor> modelPreFilterContributors =
			modelPreFilterContributorsRegistry.filterModelPreFilterContributor(
				modelSearchSettings.getClassName(),
				getStrings(
					"search.full.query.clause.contributors.excludes",
					searchContext),
				getStrings(
					"search.full.query.clause.contributors.includes",
					searchContext),
				IndexerProvidedClausesUtil.shouldSuppress(searchContext));

		for (ModelPreFilterContributor modelPreFilterContributor :
				modelPreFilterContributors) {

			modelPreFilterContributor.contribute(
				booleanFilter, modelSearchSettings, searchContext);
		}
	}

	private void _addPermissionFilter(
		BooleanFilter booleanFilter, String entryClassName,
		SearchContext searchContext) {

		if (searchContext.getUserId() == 0) {
			return;
		}

		searchPermissionChecker.getPermissionBooleanFilter(
			searchContext.getCompanyId(), searchContext.getGroupIds(),
			searchContext.getUserId(), _getParentEntryClassName(entryClassName),
			booleanFilter, searchContext);
	}

	private void _addPreFilters(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		List<QueryPreFilterContributor> queryPreFilterContributors =
			queryPreFilterContributorsRegistry.filterQueryPreFilterContributor(
				getStrings(
					"search.full.query.clause.contributors.excludes",
					searchContext),
				getStrings(
					"search.full.query.clause.contributors.includes",
					searchContext));

		for (QueryPreFilterContributor queryPreFilterContributor :
				queryPreFilterContributors) {

			queryPreFilterContributor.contribute(booleanFilter, searchContext);
		}
	}

	private Filter _createPreFilterForEntryClassName(
		String entryClassName, Indexer<?> indexer,
		SearchContext searchContext) {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.addTerm(
			Field.ENTRY_CLASS_NAME, entryClassName, BooleanClauseOccur.MUST);

		_addPermissionFilter(booleanFilter, entryClassName, searchContext);

		_addIndexerProvidedPreFilters(booleanFilter, indexer, searchContext);

		_addModelProvidedPreFilters(
			booleanFilter, _getModelSearchSettings(indexer), searchContext);

		return booleanFilter;
	}

	private ModelSearchSettings _getModelSearchSettings(Indexer<?> indexer) {
		ModelSearchSettingsImpl modelSearchSettingsImpl =
			new ModelSearchSettingsImpl(indexer.getClassName());

		modelSearchSettingsImpl.setStagingAware(indexer.isStagingAware());

		return modelSearchSettingsImpl;
	}

	private String _getParentEntryClassName(String entryClassName) {
		for (SearchPermissionFilterContributor
				searchPermissionFilterContributor :
					_serviceTrackerList.toList()) {

			String parentEntryClassName =
				searchPermissionFilterContributor.getParentEntryClassName(
					entryClassName);

			if (parentEntryClassName != null) {
				return parentEntryClassName;
			}
		}

		return entryClassName;
	}

	private ServiceTrackerList<SearchPermissionFilterContributor>
		_serviceTrackerList;

}