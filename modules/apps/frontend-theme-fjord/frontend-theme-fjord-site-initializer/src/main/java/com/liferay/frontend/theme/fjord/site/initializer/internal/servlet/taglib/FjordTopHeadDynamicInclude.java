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

package com.liferay.frontend.theme.fjord.site.initializer.internal.servlet.taglib;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(immediate = true, service = DynamicInclude.class)
public class FjordTopHeadDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		LayoutSet publicLayoutSet = scopeGroup.getPublicLayoutSet();
		LayoutSet privateLayoutSet = scopeGroup.getPrivateLayoutSet();

		if (!Objects.equals(publicLayoutSet.getThemeId(), _THEME_ID) &&
			!Objects.equals(privateLayoutSet.getThemeId(), _THEME_ID)) {

			return;
		}

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			return;
		}

		if (!Objects.equals(
				themeDisplay.getPpid(), LayoutAdminPortletKeys.GROUP_PAGES)) {

			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write("<link data-senna-track=\"permanent\" href=\"");

		StringBundler sb = new StringBundler(4);

		sb.append(themeDisplay.getCDNBaseURL());
		sb.append(_portal.getPathProxy());

		Theme theme = _themeLocalService.getTheme(
			themeDisplay.getCompanyId(), _THEME_ID);

		sb.append(theme.getContextPath());

		sb.append("/css/fragments_editor.css");

		long themeLastModified = PortalWebResourcesUtil.getLastModified(
			PortalWebResourceConstants.RESOURCE_TYPE_THEME_CONTRIBUTOR);

		String staticResourceURL = _portal.getStaticResourceURL(
			httpServletRequest, sb.toString(), themeLastModified);

		printWriter.write(staticResourceURL);

		printWriter.write("\" rel=\"stylesheet\" type = \"text/css\" />\n");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	private static final String _THEME_ID = "fjord_WAR_fjordtheme";

	@Reference
	private Portal _portal;

	@Reference
	private ThemeLocalService _themeLocalService;

}