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

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.BundleStylesheetAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.CacheHelper;
import com.liferay.portal.url.builder.internal.util.URLUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class BundleStylesheetAbsolutePortalURLBuilderImpl
	extends AbstractBundleResourceAbsolutePortalURLBuilderImpl
		<BundleStylesheetAbsolutePortalURLBuilder>
	implements BundleStylesheetAbsolutePortalURLBuilder {

	public BundleStylesheetAbsolutePortalURLBuilderImpl(
		Bundle bundle, CacheHelper cacheHelper, String cdnHost,
		HttpServletRequest httpServletRequest, String pathModule,
		String pathProxy, String relativeURL) {

		super(
			bundle, cacheHelper, cdnHost, httpServletRequest, pathModule,
			pathProxy, relativeURL);
	}

	@Override
	protected void addSpecificParams(
		HttpServletRequest httpServletRequest, StringBundler sb) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isThemeCssFastLoad()) {
			URLUtil.appendParam(sb, "minifierType", "css");
		}

		Theme theme = themeDisplay.getTheme();

		URLUtil.appendParam(
			sb, "themeId", URLCodec.encodeURL(theme.getThemeId()));
	}

}