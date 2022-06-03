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

package com.liferay.frontend.icons.web.internal.servlet.taglib;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.frontend.icons.FrontendIconsUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryce Osterhaus
 */
@Component(immediate = true, service = DynamicInclude.class)
public class FrontendIconsSpritemapTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		StringBundler sb = new StringBundler(5);

		sb.append("<script data-senna-track=\"temporary\">");
		sb.append("var Liferay = window.Liferay || {};");
		sb.append("Liferay.Icons = Liferay.Icons || {};");
		sb.append(
			StringBundler.concat(
				"Liferay.Icons.basePath = '", FrontendIconsUtil.getBasePath(),
				"';"));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		sb.append(
			StringBundler.concat(
				"Liferay.Icons.spritemap = '",
				FrontendIconsUtil.getSpritemap(themeDisplay), "';"));

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-145112"))) {
			sb.append(
			StringBundler.concat(
				"Liferay.Icons.systemSpritemap = '",
				FrontendIconsUtil.getSystemSpritemap(), "';"));
		} else {
			sb.append(
				StringBundler.concat(
					"Liferay.Icons.systemSpritemap = '",
					FrontendIconsUtil.getSpritemap(themeDisplay), "';"));
		}

		sb.append("</script>");

		printWriter.println(sb);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

}