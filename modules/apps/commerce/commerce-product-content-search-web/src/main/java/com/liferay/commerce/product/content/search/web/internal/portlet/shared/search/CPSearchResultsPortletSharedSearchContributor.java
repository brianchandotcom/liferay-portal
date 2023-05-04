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

package com.liferay.commerce.product.content.search.web.internal.portlet.shared.search;

import com.liferay.account.model.AccountEntry;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.configuration.CPSearchResultsPortletInstanceConfiguration;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "javax.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS,
	service = PortletSharedSearchContributor.class
)
public class CPSearchResultsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		try {
			_contribute(portletSharedSearchSettings);

			String paginationStartParameterName =
				portletSharedSearchSettings.getPaginationStartParameterName();

			if (paginationStartParameterName == null) {
				throw new NoSuchElementException(
					"Pagination start parameter name is null for portlet ID " +
						portletSharedSearchSettings.getPortletId());
			}

			SearchRequestBuilder searchRequestBuilder =
				portletSharedSearchSettings.getSearchRequestBuilder();

			searchRequestBuilder.paginationStartParameterName(
				paginationStartParameterName);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	private void _contribute(
			PortletSharedSearchSettings portletSharedSearchSettings)
		throws PortalException {

		RenderRequest renderRequest =
			portletSharedSearchSettings.getRenderRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				themeDisplay.getScopeGroupId());

		Optional<String> parameterValueOptional =
			portletSharedSearchSettings.getParameterOptional("q");

		portletSharedSearchSettings.setKeywords(
			parameterValueOptional.orElse(StringPool.BLANK));

		portletSharedSearchSettings.addCondition(
			new BooleanClauseImpl<Query>(
				new TermQueryImpl(
					Field.ENTRY_CLASS_NAME, CPDefinition.class.getName()),
				BooleanClauseOccur.MUST));

		AssetCategory assetCategory = (AssetCategory)renderRequest.getAttribute(
			WebKeys.ASSET_CATEGORY);

		if (assetCategory != null) {
			portletSharedSearchSettings.addCondition(
				new BooleanClauseImpl<Query>(
					new TermQueryImpl(
						Field.ASSET_CATEGORY_IDS,
						String.valueOf(assetCategory.getCategoryId())),
					BooleanClauseOccur.MUST));
		}

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		searchContext.setAttribute(CPField.PUBLISHED, Boolean.TRUE);
		searchContext.setEntryClassNames(
			new String[] {CPDefinition.class.getName()});

		if (commerceChannel != null) {
			searchContext.setAttribute(
				"commerceChannelGroupId", commerceChannel.getGroupId());

			AccountEntry accountEntry =
				_commerceAccountHelper.getCurrentAccountEntry(
					commerceChannel.getGroupId(),
					_portal.getHttpServletRequest(renderRequest));

			if (accountEntry != null) {
				searchContext.setAttribute(
					"commerceAccountGroupIds",
					_commerceAccountHelper.getCommerceAccountGroupIds(
						accountEntry.getAccountEntryId()));
			}
		}

		searchContext.setAttribute("secure", Boolean.TRUE);

		QueryConfig queryConfig = portletSharedSearchSettings.getQueryConfig();

		queryConfig.setHighlightEnabled(false);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		CPSearchResultsPortletInstanceConfiguration
			cpSearchResultsPortletInstanceConfiguration =
				portletDisplay.getPortletInstanceConfiguration(
					CPSearchResultsPortletInstanceConfiguration.class);

		_paginate(
			cpSearchResultsPortletInstanceConfiguration,
			portletSharedSearchSettings);
	}

	private void _paginate(
		CPSearchResultsPortletInstanceConfiguration
			cpSearchResultsPortletInstanceConfiguration,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String paginationStartParameterName = "start";

		portletSharedSearchSettings.setPaginationStartParameterName(
			paginationStartParameterName);

		Optional<String> paginationStartParameterValueOptional =
			portletSharedSearchSettings.getParameterOptional(
				paginationStartParameterName);

		Optional<Integer> paginationStartOptional =
			paginationStartParameterValueOptional.map(Integer::valueOf);

		paginationStartOptional.ifPresent(
			portletSharedSearchSettings::setPaginationStart);

		String paginationDeltaParameterName = "delta";

		Optional<String> paginationDeltaParameterValueOptional =
			portletSharedSearchSettings.getParameterOptional(
				paginationDeltaParameterName);

		Optional<Integer> paginationDeltaOptional =
			paginationDeltaParameterValueOptional.map(Integer::valueOf);

		int configurationPaginationDelta =
			cpSearchResultsPortletInstanceConfiguration.paginationDelta();

		Optional<PortletPreferences> portletPreferencesOptional =
			portletSharedSearchSettings.getPortletPreferencesOptional();

		if (portletPreferencesOptional.isPresent()) {
			PortletPreferences portletPreferences =
				portletPreferencesOptional.get();

			configurationPaginationDelta = GetterUtil.getInteger(
				portletPreferences.getValue("paginationDelta", null));
		}

		portletSharedSearchSettings.setPaginationDelta(
			paginationDeltaOptional.orElse(configurationPaginationDelta));
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private Portal _portal;

}