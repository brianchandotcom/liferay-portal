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

package com.liferay.search.experiences.web.internal.blueprint.options.portlet.shared.search;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.web.internal.blueprint.options.portlet.preferences.SXPBlueprintOptionsPortletPreferencesUtil;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	enabled = false, immediate = true,
	property = "javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT_OPTIONS,
	service = PortletSharedSearchContributor.class
)
public class SXPBlueprintOptionsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String federatedSearchKey =
			SXPBlueprintOptionsPortletPreferencesUtil.getValue(
				portletSharedSearchSettings.getPortletPreferencesOptional(),
				"federatedSearchKey");

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				Optional.of(federatedSearchKey));

		searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"federatedSearchKey", federatedSearchKey);

				String sxpBlueprintId = GetterUtil.getString(
					searchContext.getAttribute(
						"search.experiences.blueprint.id"));

				if (Validator.isBlank(sxpBlueprintId)) {
					searchContext.setAttribute(
						"search.experiences.blueprint.id",
						SXPBlueprintOptionsPortletPreferencesUtil.getValue(
							portletSharedSearchSettings.
								getPortletPreferencesOptional(),
							"sxpBlueprintId"));
				}

				ThemeDisplay themeDisplay =
					portletSharedSearchSettings.getThemeDisplay();

				searchContext.setAttribute(
					"search.experiences.scope.group.id",
					themeDisplay.getScopeGroupId());

				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(
						portletSharedSearchSettings.getRenderRequest());

				searchContext.setAttribute(
					"search.experiences.ip.address",
					httpServletRequest.getRemoteAddr());
			});
	}

	@Reference
	private Portal _portal;

}