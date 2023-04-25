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

package com.liferay.feature.flag.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.feature.flag.web.internal.configuration.admin.category.FeatureFlagConfigurationCategory;
import com.liferay.feature.flag.web.internal.constants.FeatureFlagConstants;
import com.liferay.feature.flag.web.internal.display.FeatureFlagsDisplayContextFactory;
import com.liferay.feature.flag.web.internal.model.FeatureFlagType;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Drew Brokke
 */
public class FeatureFlagConfigurationScreen implements ConfigurationScreen {

	public FeatureFlagConfigurationScreen(
		FeatureFlagManager featureFlagManager, FeatureFlagType featureFlagType,
		FeatureFlagsDisplayContextFactory featureFlagsDisplayContextFactory,
		ServletContext servletContext) {

		_featureFlagManager = featureFlagManager;
		_featureFlagType = featureFlagType;
		_featureFlagsDisplayContextFactory = featureFlagsDisplayContextFactory;
		_servletContext = servletContext;
	}

	@Override
	public String getCategoryKey() {
		return FeatureFlagConfigurationCategory.CATEGORY_KEY;
	}

	@Override
	public String getKey() {
		return FeatureFlagConstants.getKey(_featureFlagType.toString());
	}

	@Override
	public String getName(Locale locale) {
		return _featureFlagType.getTitle(locale);
	}

	@Override
	public String getScope() {
		return "company";
	}

	@Override
	public boolean isVisible() {
		if (_featureFlagType.isUIEnabled()) {
			return true;
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			_featureFlagsDisplayContextFactory.create(
				_featureFlagType, httpServletRequest));

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/feature_flags.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render feature_flags.jsp", exception);
		}
	}

	private final FeatureFlagManager _featureFlagManager;
	private final FeatureFlagsDisplayContextFactory
		_featureFlagsDisplayContextFactory;
	private final FeatureFlagType _featureFlagType;
	private final ServletContext _servletContext;

}