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

package com.liferay.layout.admin.web.internal.asset;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Garcia
 */
public class LayoutAssetRenderer extends BaseJSPAssetRenderer<Layout> {

	public LayoutAssetRenderer(Layout layout) {
		_layout = layout;
	}

	@Override
	public Layout getAssetObject() {
		return _layout;
	}

	@Override
	public String getClassName() {
		return Layout.class.getName();
	}

	@Override
	public long getClassPK() {
		return _layout.getLayoutId();
	}

	@Override
	public long getGroupId() {
		return _layout.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			return "/asset/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Locale locale = getLocale(portletRequest);

		StringBundler sb = new StringBundler(4);

		sb.append("<strong>");
		sb.append(LanguageUtil.get(locale, "page"));
		sb.append(":</strong> ");
		sb.append(_layout.getHTMLTitle(locale));

		return sb.toString();
	}

	@Override
	public String getTitle(Locale locale) {
		return _layout.getHTMLTitle(locale);
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Layout layout = LayoutLocalServiceUtil.getLayout(_layout.getPlid());

			return PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	@Override
	public long getUserId() {
		return _layout.getUserId();
	}

	@Override
	public String getUserName() {
		return _layout.getUserName();
	}

	@Override
	public String getUuid() {
		return null;
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(WebKeys.LAYOUT, _layout);

		return super.include(request, response, template);
	}

	private final Layout _layout;

}