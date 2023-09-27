/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.permission;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterSearcher;
import com.liferay.portal.kernel.search.facet.FacetPostProcessor;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.search.configuration.DefaultSearchResultPermissionFilterConfiguration;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Yan
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.DefaultSearchResultPermissionFilterConfiguration",
	service = SearchResultPermissionFilterFactory.class
)
public class SearchResultPermissionFilterFactoryImpl
	implements SearchResultPermissionFilterFactory {

	@Override
	public SearchResultPermissionFilter create(
		SearchResultPermissionFilterSearcher
			searchResultPermissionFilterSearcher,
		PermissionChecker permissionChecker) {

		return new DefaultSearchResultPermissionFilter(
			facetPostProcessor, indexerRegistry, permissionChecker, props,
			relatedEntryIndexerRegistry,
			searchContext -> _search(
				searchResultPermissionFilterSearcher, searchContext),
			searchRequestBuilderFactory,
			_defaultSearchResultPermissionFilterConfiguration);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_defaultSearchResultPermissionFilterConfiguration =
			ConfigurableUtil.createConfigurable(
				DefaultSearchResultPermissionFilterConfiguration.class,
				properties);
	}

	@Reference
	protected FacetPostProcessor facetPostProcessor;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected Props props;

	@Reference
	protected RelatedEntryIndexerRegistry relatedEntryIndexerRegistry;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private Hits _search(
		SearchResultPermissionFilterSearcher
			searchResultPermissionFilterSearcher,
		SearchContext searchContext) {

		try {
			return searchResultPermissionFilterSearcher.search(searchContext);
		}
		catch (SearchException searchException) {
			throw new RuntimeException(searchException);
		}
	}

	private volatile DefaultSearchResultPermissionFilterConfiguration
		_defaultSearchResultPermissionFilterConfiguration;

}