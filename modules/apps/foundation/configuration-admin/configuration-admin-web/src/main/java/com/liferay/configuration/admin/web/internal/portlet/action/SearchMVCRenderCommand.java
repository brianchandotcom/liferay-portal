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

package com.liferay.configuration.admin.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.internal.constants.ConfigurationAdminWebKeys;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.search.FieldNames;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelIterator;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelRetriever;
import com.liferay.configuration.admin.web.internal.util.ResourceBundleLoaderProvider;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/search"
	},
	service = MVCRenderCommand.class
)
public class SearchMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		Indexer indexer = _indexerRegistry.nullSafeGetIndexer(
			ConfigurationModel.class);

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(false);
		searchContext.setCompanyId(CompanyConstants.SYSTEM);
		searchContext.setLocale(renderRequest.getLocale());

		String keywords = renderRequest.getParameter("keywords");

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(true);
		queryConfig.setLocale(renderRequest.getLocale());
		queryConfig.setScoreEnabled(true);

		try {
			Hits hits = indexer.search(searchContext);

			Document[] documents = hits.getDocs();

			Map<String, ConfigurationModel> configurationModels =
				_configurationModelRetriever.getConfigurationModels();

			List<ConfigurationModel> searchResults = new ArrayList<>(
				documents.length);

			for (Document document : documents) {
				String configurationModelId = document.get(
					FieldNames.CONFIGURATION_MODEL_ID);

				ConfigurationModel configurationModel = configurationModels.get(
					configurationModelId);

				if (configurationModel == null) {
					String configurationModelFactoryId = document.get(
						FieldNames.CONFIGURATION_MODEL_FACTORY_PID);

					configurationModel = configurationModels.get(
						configurationModelFactoryId);
				}

				if (configurationModel != null) {
					searchResults.add(configurationModel);
				}
			}

			ConfigurationModelIterator configurationModelIterator =
				new ConfigurationModelIterator(searchResults);

			renderRequest.setAttribute(
				ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR,
				configurationModelIterator);

			renderRequest.setAttribute(
				ConfigurationAdminWebKeys.CONFIGURATION_MODEL_RETRIEVER,
				_configurationModelRetriever);

			renderRequest.setAttribute(
				ConfigurationAdminWebKeys.RESOURCE_BUNDLE_LOADER_PROVIDER,
				_resourceBundleLoaderProvider);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return "/search_results.jsp";
	}

	@Reference
	private ConfigurationModelRetriever _configurationModelRetriever;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private ResourceBundleLoaderProvider _resourceBundleLoaderProvider;

}