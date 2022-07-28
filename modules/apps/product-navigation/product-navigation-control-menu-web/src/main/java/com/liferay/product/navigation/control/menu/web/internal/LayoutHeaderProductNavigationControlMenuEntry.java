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

package com.liferay.product.navigation.control.menu.web.internal;

import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import java.io.IOException;
import java.io.Writer;

import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.TOOLS,
		"product.navigation.control.menu.entry.order:Integer=100"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class LayoutHeaderProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Writer writer = httpServletResponse.getWriter();

		StringBundler sb = new StringBundler(17);

		sb.append("<li class=\"");
		sb.append(_getCssClass(httpServletRequest));
		sb.append("\"><span class=\"align-items-center ");
		sb.append("control-menu-level-1-heading d-flex mr-1\" ");
		sb.append("data-qa-id=\"headerTitle\"><span class=\"");
		sb.append("lfr-portal-tooltip text-truncate\" title=\"");
		sb.append(
			HtmlUtil.escapeAttribute(_getHeaderTitle(httpServletRequest)));
		sb.append("\">");
		sb.append(_getHeaderTitle(httpServletRequest));
		sb.append("</span>");

		if (_hasDraftLayout(httpServletRequest) &&
			_hasEditPermission(httpServletRequest)) {

			sb.append("<sup class=\"flex-shrink-0 small\">*</sup>");
		}

		sb.append("</span>");

		if (_isDraftLayout(httpServletRequest)) {
			sb.append("<span class=\"bg-transparent flex-shrink-0 label ");
			sb.append("label-inverse-secondary ml-2 mr-0\">");
			sb.append("<span class=\"label-item label-item-expand\">");
			sb.append(_language.get(httpServletRequest, "draft"));
			sb.append("</span></span>");
		}

		writer.write(sb.toString());

		return true;
	}

	@Override
	public boolean isRelevant(HttpServletRequest httpServletRequest) {
		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return false;
		}

		return super.isShow(httpServletRequest);
	}

	private String _getCssClass(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!Objects.equals(
				layout.getType(), LayoutConstants.TYPE_COLLECTION)) {

			return "control-menu-nav-item control-menu-nav-item-content";
		}

		return "control-menu-nav-item";
	}

	private String _getHeaderTitle(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		Layout layout = themeDisplay.getLayout();

		if (Validator.isNotNull(portletId) && layout.isSystem() &&
			!layout.isTypeControlPanel() &&
			Objects.equals(
				layout.getFriendlyURL(),
				PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL)) {

			return _portal.getPortletTitle(portletId, themeDisplay.getLocale());
		}

		return HtmlUtil.escape(layout.getName(themeDisplay.getLocale()));
	}

	private boolean _hasDraftLayout(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeContent()) {
			return false;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout != null) {
			layout = draftLayout;
		}

		if (!layout.isDraft() && _isLayoutPublished(layout)) {
			return false;
		}

		return true;
	}

	private boolean _hasEditPermission(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		try {
			if (_layoutContentModelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), layout.getPlid(),
					ActionKeys.UPDATE) ||
				_layoutPermission.containsLayoutUpdatePermission(
					themeDisplay.getPermissionChecker(), layout)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	private boolean _isDraftLayout(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeContent()) {
			return false;
		}

		if ((layout.fetchDraftLayout() != null) ||
			(!layout.isDraft() && _isLayoutPublished(layout))) {

			return false;
		}

		String mode = ParamUtil.getString(httpServletRequest, "p_l_mode");

		if (Objects.equals(mode, Constants.EDIT)) {
			return false;
		}

		return true;
	}

	private boolean _isLayoutPublished(Layout layout) {
		boolean published = GetterUtil.getBoolean(
			layout.getTypeSettingsProperty("published"));

		if (published) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutHeaderProductNavigationControlMenuEntry.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutContentModelResourcePermission
		_layoutContentModelResourcePermission;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private Portal _portal;

}