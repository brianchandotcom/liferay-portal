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

package com.liferay.portal.search.web.internal.low.level.search.options.portlet.shared.search;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.internal.low.level.search.options.constants.LowLevelSearchOptionsPortletKeys;
import com.liferay.portal.search.web.internal.low.level.search.options.portlet.preferences.LowLevelSearchOptionsPortletPreferences;
import com.liferay.portal.search.web.internal.low.level.search.options.portlet.preferences.LowLevelSearchOptionsPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.io.Serializable;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + LowLevelSearchOptionsPortletKeys.LOW_LEVEL_SEARCH_OPTIONS,
	service = PortletSharedSearchContributor.class
)
public class LowLevelSearchOptionsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		LowLevelSearchOptionsPortletPreferences
			lowLevelSearchOptionsPortletPreferences =
				new LowLevelSearchOptionsPortletPreferencesImpl(
					portletSharedSearchSettings.
						getPortletPreferencesOptional());

		Optional<String> connectionIdOptional =
			lowLevelSearchOptionsPortletPreferences.getConnectionIdOptional();

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				lowLevelSearchOptionsPortletPreferences.
					getFederatedSearchKeyOptional());

		searchRequestBuilder.connectionId(
			connectionIdOptional.orElse(null)
		).excludeContributors(
			SearchStringUtil.splitAndUnquote(
				lowLevelSearchOptionsPortletPreferences.
					getContributorsToExcludeOptional())
		).fields(
			SearchStringUtil.splitAndUnquote(
				lowLevelSearchOptionsPortletPreferences.
					getFieldsToReturnOptional())
		).includeContributors(
			SearchStringUtil.splitAndUnquote(
				lowLevelSearchOptionsPortletPreferences.
					getContributorsToIncludeOptional())
		).indexes(
			SearchStringUtil.splitAndUnquote(
				lowLevelSearchOptionsPortletPreferences.getIndexesOptional())
		).withSearchContext(
			searchContext -> {
				_applyAttributes(
					lowLevelSearchOptionsPortletPreferences, searchContext);

				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(
						portletSharedSearchSettings.getRenderRequest());

				searchContext.setAttribute(
					"search.experiences.ip.address",
					httpServletRequest.getRemoteAddr());
			}
		);
	}

	private void _applyAttributes(
		LowLevelSearchOptionsPortletPreferences
			lowLevelSearchOptionsPortletPreferences,
		SearchContext searchContext) {

		for (Object object :
				lowLevelSearchOptionsPortletPreferences.
					getAttributesJSONArray()) {

			JSONObject jsonObject = (JSONObject)object;

			searchContext.setAttribute(
				jsonObject.getString("key"),
				(Serializable)jsonObject.get("value"));
		}
	}

	@Reference
	private Portal _portal;

}