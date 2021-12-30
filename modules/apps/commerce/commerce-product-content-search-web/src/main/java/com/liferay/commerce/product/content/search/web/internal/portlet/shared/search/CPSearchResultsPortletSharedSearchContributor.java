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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.account.model.CommerceAccount;
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

import java.util.Optional;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	enabled = false,
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

			SearchRequestBuilder searchRequestBuilder =
				portletSharedSearchSettings.getSearchRequestBuilder();

			Optional<String> paginationStartParameterNameOptional =
				portletSharedSearchSettings.getPaginationStartParameterName();

			searchRequestBuilder.paginationStartParameterName(
				paginationStartParameterNameOptional.get());
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
			portletSharedSearchSettings.getParameter71("q");

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

		searchContext.setEntryClassNames(
			new String[] {CPDefinition.class.getName()});

		searchContext.setAttribute(CPField.PUBLISHED, Boolean.TRUE);

		if (commerceChannel != null) {
			searchContext.setAttribute(
				"commerceChannelGroupId", commerceChannel.getGroupId());

			CommerceAccount commerceAccount =
				_commerceAccountHelper.getCurrentCommerceAccount(
					commerceChannel.getGroupId(),
					_portal.getHttpServletRequest(renderRequest));

			if (commerceAccount != null) {
				long[] commerceAccountGroupIds =
					_commerceAccountHelper.getCommerceAccountGroupIds(
						commerceAccount.getCommerceAccountId());

				searchContext.setAttribute(
					"commerceAccountGroupIds", commerceAccountGroupIds);
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
			portletSharedSearchSettings.getParameter71(
				paginationStartParameterName);

		Optional<Integer> paginationStartOptional =
			paginationStartParameterValueOptional.map(Integer::valueOf);

		paginationStartOptional.ifPresent(
			portletSharedSearchSettings::setPaginationStart);

		String paginationDeltaParameterName = "delta";

		Optional<String> paginationDeltaParameterValueOptional =
			portletSharedSearchSettings.getParameter71(
				paginationDeltaParameterName);

		Optional<Integer> paginationDeltaOptional =
			paginationDeltaParameterValueOptional.map(Integer::valueOf);

		int configurationPaginationDelta =
			cpSearchResultsPortletInstanceConfiguration.paginationDelta();

		Optional<PortletPreferences> portletPreferencesOptional =
			portletSharedSearchSettings.getPortletPreferences71();

		if (portletPreferencesOptional.isPresent()) {
			PortletPreferences portletPreferences =
				portletPreferencesOptional.get();

			configurationPaginationDelta = GetterUtil.getInteger(
				portletPreferences.getValue("paginationDelta", null));
		}

		int paginationDelta = paginationDeltaOptional.orElse(
			configurationPaginationDelta);

		portletSharedSearchSettings.setPaginationDelta(paginationDelta);
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private Portal _portal;

}