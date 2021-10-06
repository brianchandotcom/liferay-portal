/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.options.portlet.shared.search;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import com.liferay.search.experiences.blueprint.constants.SearchContextAttributeKeys;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.web.internal.options.portlet.preferences.SXPBlueprintOptionsPortletPreferences;
import com.liferay.search.experiences.web.internal.options.portlet.preferences.SXPBlueprintOptionsPortletPreferencesImpl;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT_OPTIONS,
	service = PortletSharedSearchContributor.class
)
public class SXPBlueprintOptionsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SXPBlueprintOptionsPortletPreferences
			sxpBlueprintsOptionsPortletPreferences =
				new SXPBlueprintOptionsPortletPreferencesImpl(
					portletSharedSearchSettings.
						getPortletPreferencesOptional());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				sxpBlueprintsOptionsPortletPreferences.
					getFederatedSearchKeyOptional());

		searchRequestBuilder.withSearchContext(
			searchContext -> {
				String sxpBlueprintId = GetterUtil.getString(
					searchContext.getAttribute(
						SearchContextAttributeKeys.SXP_BLUEPRINT_ID));

				if (Validator.isBlank(sxpBlueprintId)) {
					searchContext.setAttribute(
						SearchContextAttributeKeys.SXP_BLUEPRINT_ID,
						sxpBlueprintsOptionsPortletPreferences.
							getSXPBlueprintIdString());
				}

				searchContext.setAttribute(
					SearchContextAttributeKeys.FEDERATED_SEARCH_KEY,
					sxpBlueprintsOptionsPortletPreferences.
						getFederatedSearchKeyString());

				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(
						portletSharedSearchSettings.getRenderRequest());

				searchContext.setAttribute(
					SearchContextAttributeKeys.IP_ADDRESS,
					httpServletRequest.getRemoteAddr());
			});
	}

	@Reference
	private Portal _portal;

}