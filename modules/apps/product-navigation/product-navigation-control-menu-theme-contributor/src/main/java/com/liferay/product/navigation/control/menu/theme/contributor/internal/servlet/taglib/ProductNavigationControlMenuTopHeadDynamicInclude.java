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

package com.liferay.product.navigation.control.menu.theme.contributor.internal.servlet.taglib;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = DynamicInclude.class)
public class ProductNavigationControlMenuTopHeadDynamicInclude
	implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		StringBundler sb = new StringBundler(6);

		sb.append("<link data-senna-track=\"permanent\" href=\"");

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		sb.append(
			absolutePortalURLBuilder.forModule(
				_bundle,
				"/product_navigation_control_menu.css?languageId=" +
					themeDisplay.getLanguageId()
			).build());

		sb.append("\" rel=\"stylesheet\" type = \"text/css\" />\n");
		sb.append("<script data-senna-track=\"permanent\" src=\"");
		sb.append(
			absolutePortalURLBuilder.forModule(
				_bundle, "/product_navigation_control_menu.js"
			).build());
		sb.append("\" type=\"text/javascript\"></script>");

		printWriter.println(sb.toString());
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private Bundle _bundle;

}