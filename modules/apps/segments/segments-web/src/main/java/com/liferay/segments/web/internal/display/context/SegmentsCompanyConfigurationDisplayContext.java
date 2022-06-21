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

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class SegmentsCompanyConfigurationDisplayContext {

	public SegmentsCompanyConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		SegmentsConfigurationProvider segmentsConfigurationProvider) {

		_httpServletRequest = httpServletRequest;
		_segmentsConfigurationProvider = segmentsConfigurationProvider;
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

	private final HttpServletRequest _httpServletRequest;
	private final SegmentsConfigurationProvider _segmentsConfigurationProvider;

}