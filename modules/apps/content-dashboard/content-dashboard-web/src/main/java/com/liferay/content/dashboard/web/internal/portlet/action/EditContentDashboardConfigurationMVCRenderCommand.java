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

package com.liferay.content.dashboard.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardWebKeys;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardAdminConfigurationDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 + * @author David Arques
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"mvc.command.name=/content_dashboard/edit_content_dashboard_configuration"
	},
	service = MVCRenderCommand.class
)
public class EditContentDashboardConfigurationMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String[] assetVocabularyIdsString = portletPreferences.getValues(
			"assetVocabularyIds", new String[0]);

		if (assetVocabularyIdsString.length == 0) {
			assetVocabularyIdsString = _defaultValues(renderRequest);
		}

		Stream<String> streamAssetVocabularyIdsString = Arrays.stream(
			assetVocabularyIdsString);

		long[] assetVocabularyIds = streamAssetVocabularyIdsString.mapToLong(
			Long::parseLong
		).toArray();

		renderRequest.setAttribute(
			ContentDashboardWebKeys.
				CONTENT_DASHBOARD_ADMIN_CONFIGURATION_DISPLAY_CONTEXT,
			new ContentDashboardAdminConfigurationDisplayContext(
				_assetVocabularyLocalService, assetVocabularyIds,
				_groupLocalService,
				_portal.getHttpServletRequest(renderRequest), renderResponse));

		return "/edit_content_dashboard_configuration.jsp";
	}

	private String[] _defaultValues(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetVocabulary audience =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				themeDisplay.getCompanyGroupId(), "audience");

		AssetVocabulary stage =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				themeDisplay.getCompanyGroupId(), "stage");

		return new String[] {
			String.valueOf(audience.getVocabularyId()),
			String.valueOf(stage.getVocabularyId())
		};
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}