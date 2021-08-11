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

package com.liferay.users.admin.internal.portlet.filter;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.DynamicActionRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Drew Brokke
 */
@Component(
	configurationPid = "com.liferay.users.admin.configuration.UserFileUploadsConfiguration",
	immediate = true,
	property = "javax.portlet.name=com_liferay_image_uploader_web_portlet_ImageUploaderPortlet",
	service = PortletFilter.class
)
public class UploadUserPortraitPortletFilter implements ActionFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			ActionRequest actionRequest, ActionResponse actionResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		String currentLogoURL = actionRequest.getParameter("currentLogoURL");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String pathImage = themeDisplay.getPathImage();

		if (StringUtil.startsWith(
				currentLogoURL, pathImage + "/user_female_portrait") ||
			StringUtil.startsWith(
				currentLogoURL, pathImage + "/user_male_portrait") ||
			StringUtil.startsWith(
				currentLogoURL, pathImage + "/user_portrait")) {

			DynamicActionRequest dynamicActionRequest =
				new DynamicActionRequest(
					actionRequest, actionRequest.getParameterMap());

			dynamicActionRequest.setParameter(
				"maxFileSize", String.valueOf(_imageMaxSize));

			actionRequest = dynamicActionRequest;
		}

		filterChain.doFilter(actionRequest, actionResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		UserFileUploadsConfiguration userFileUploadsConfiguration =
			ConfigurableUtil.createConfigurable(
				UserFileUploadsConfiguration.class, properties);

		_imageMaxSize = userFileUploadsConfiguration.imageMaxSize();
	}

	private volatile long _imageMaxSize;

}