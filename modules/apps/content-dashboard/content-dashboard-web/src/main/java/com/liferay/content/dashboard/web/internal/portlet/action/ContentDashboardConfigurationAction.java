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

import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.configuration.ContentDashboardAdminConfiguration;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardWebKeys;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardAdminConfigurationDisplayContext;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.BaseJSPSettingsConfigurationAction;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
	service = ConfigurationAction.class
)
public class ContentDashboardConfigurationAction
	extends BaseJSPSettingsConfigurationAction {

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ContentDashboardAdminConfiguration contentDashboardAdminConfiguration =
			_configurationProvider.getCompanyConfiguration(
				ContentDashboardAdminConfiguration.class,
				_portal.getCompanyId(httpServletRequest));

		httpServletRequest.setAttribute(
			ContentDashboardWebKeys.
				CONTENT_DASHBOARD_ADMIN_CONFIGURATION_DISPLAY_CONTEXT,
			new ContentDashboardAdminConfigurationDisplayContext(
				_assetVocabularyLocalService,
				contentDashboardAdminConfiguration, httpServletRequest));

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.content.dashboard.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected void updateMultiValuedKeys(ActionRequest actionRequest) {
		String assetVocabularyNames = getParameter(
			actionRequest, "assetVocabularyNames");

		setPreference(
			actionRequest, "assetVocabularyNames",
			StringUtil.split(assetVocabularyNames));
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

}