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

package com.liferay.configuration.admin.web.portlet.action;

import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminWebKeys;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.search.FieldNames;
import com.liferay.configuration.admin.web.util.ConfigurationModelIterator;
import com.liferay.configuration.admin.web.util.ConfigurationModelRetriever;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN,
		"mvc.command.name=search"
	},
	service = MVCActionCommand.class
)
public class SearchMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Indexer indexer = _indexerRegistry.nullSafeGetIndexer(
			ConfigurationModel.class);

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(false);

		searchContext.setCompanyId(CompanyConstants.SYSTEM);

		String keywords = actionRequest.getParameter("keywords");

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(true);
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

				if (configurationModel != null) {
					searchResults.add(configurationModel);
				}
			}

			ConfigurationModelIterator configurationModelIterator =
				new ConfigurationModelIterator(searchResults);

			actionRequest.setAttribute(
				ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR,
				configurationModelIterator);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Reference(unbind = "-")
	protected void setConfigurationModelRetriever(
		ConfigurationModelRetriever configurationModelRetriever) {

		_configurationModelRetriever = configurationModelRetriever;
	}

	@Reference(unbind = "-")
	protected void setIndexerRegistry(IndexerRegistry indexerRegistry) {
		_indexerRegistry = indexerRegistry;
	}

	private volatile ConfigurationModelRetriever _configurationModelRetriever;
	private volatile IndexerRegistry _indexerRegistry;

}