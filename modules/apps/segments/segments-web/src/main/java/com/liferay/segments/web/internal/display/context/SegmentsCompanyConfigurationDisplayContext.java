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

package com.liferay.segments.web.internal.display.context;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;

import javax.portlet.PortletResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class SegmentsCompanyConfigurationDisplayContext {

	public SegmentsCompanyConfigurationDisplayContext(
		HttpServletRequest httpServletRequest, Portal portal,
		SegmentsConfigurationProvider segmentsConfigurationProvider) {

		_httpServletRequest = httpServletRequest;
		_portal = portal;
		_segmentsConfigurationProvider = segmentsConfigurationProvider;
	}

	public String getBindConfigurationActionURL() {
		return PortletURLBuilder.createActionURL(
			_portal.getLiferayPortletResponse(
				(PortletResponse)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE)),
			ConfigurationAdminPortletKeys.INSTANCE_SETTINGS
		).setActionName(
			"/instance_settings/bind_segments_company_configuration"
		).buildString();
	}

	public String getDeleteConfigurationActionURL() {
		return PortletURLBuilder.createActionURL(
			_portal.getLiferayPortletResponse(
				(PortletResponse)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE)),
			ConfigurationAdminPortletKeys.INSTANCE_SETTINGS
		).setActionName(
			"/instance_settings/delete_segments_company_configuration"
		).buildString();
	}

	public String getExportConfigurationActionURL() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest);

		ResourceURL resourceURL =
			(ResourceURL)requestBackedPortletURLFactory.createResourceURL(
				ConfigurationAdminPortletKeys.INSTANCE_SETTINGS);

		resourceURL.setParameters(
			HttpComponentsUtil.getParameterMap(
				_segmentsConfigurationProvider.getConfigurationURL(
					_httpServletRequest)));
		resourceURL.setResourceID("/configuration_admin/export_configuration");

		return resourceURL.toString();
	}

	public String getSegmentsCompanyConfigurationURL() {
		try {
			return _segmentsConfigurationProvider.getConfigurationURL(
				_httpServletRequest);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return null;
		}
	}

	public boolean isRoleSegmentationChecked() throws ConfigurationException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _segmentsConfigurationProvider.isRoleSegmentationEnabled(
			themeDisplay.getCompanyId());
	}

	public boolean isRoleSegmentationEnabled() throws ConfigurationException {
		return _segmentsConfigurationProvider.isRoleSegmentationEnabled();
	}

	public boolean isSegmentationChecked() throws ConfigurationException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _segmentsConfigurationProvider.isSegmentationEnabled(
			themeDisplay.getCompanyId());
	}

	public boolean isSegmentationEnabled() throws ConfigurationException {
		return _segmentsConfigurationProvider.isSegmentationEnabled();
	}

	public boolean isSegmentsCompanyConfigurationDefined()
		throws ConfigurationException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _segmentsConfigurationProvider.
			isSegmentsCompanyConfigurationDefined(themeDisplay.getCompanyId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsCompanyConfigurationDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;
	private final SegmentsConfigurationProvider _segmentsConfigurationProvider;

}