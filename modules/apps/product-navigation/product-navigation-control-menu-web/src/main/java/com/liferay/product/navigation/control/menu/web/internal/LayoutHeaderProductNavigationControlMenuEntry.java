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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import java.io.IOException;
import java.io.Writer;

import java.util.Locale;
import java.util.Map;
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

		StringBundler sb = new StringBundler(14);

		sb.append("<li class=\"");
		sb.append(_getCssClass(httpServletRequest));
		sb.append("\">");
		sb.append("<span class=\"control-menu-level-1-heading text-truncate\"");
		sb.append("data-qa-id=\"headerTitle\">");
		sb.append(_getHeaderTitle(httpServletRequest));

		if (_hasDraftLayout(httpServletRequest)) {
			sb.append("<sup class=\"small\">*</sup>");
		}

		sb.append("</span>");

		if (_isDraftLayout(httpServletRequest)) {
			sb.append("<span class=\"bg-transparent label ");
			sb.append("label-inverse-secondary ml-2 mr-0\">");
			sb.append("<span class=\"label-item label-item-expand\">");
			sb.append(LanguageUtil.get(httpServletRequest, "draft"));
			sb.append("</span>");
			sb.append("</span>");
		}

		writer.write(sb.toString());

		return true;
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

		String cssClass = "control-menu-nav-item";

		if (!Objects.equals(
				layout.getType(), LayoutConstants.TYPE_COLLECTION)) {

			cssClass += " control-menu-nav-item-content";
		}

		return cssClass;
	}

	private String _getHeaderTitle(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String headerTitle = HtmlUtil.escape(
			layout.getName(themeDisplay.getLocale()));

		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		if (Validator.isNotNull(portletId) && layout.isSystem() &&
			!layout.isTypeControlPanel() &&
			Objects.equals(
				layout.getFriendlyURL(),
				PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL)) {

			headerTitle = _portal.getPortletTitle(
				portletId, themeDisplay.getLocale());
		}

		return headerTitle;
	}

	private boolean _hasDraftLayout(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if ((Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT) ||
			 Objects.equals(
				 layout.getType(), LayoutConstants.TYPE_COLLECTION))) {

			Layout draftLayout = layout.fetchDraftLayout();

			if (draftLayout != null) {
				boolean published = GetterUtil.getBoolean(
					draftLayout.getTypeSettingsProperty("published"));

				if ((draftLayout.getStatus() ==
						WorkflowConstants.STATUS_DRAFT) ||
					!published) {

					return true;
				}
			}
			else {
				boolean published = GetterUtil.getBoolean(
					layout.getTypeSettingsProperty("published"));

				if ((layout.getStatus() == WorkflowConstants.STATUS_DRAFT) ||
					!published) {

					return true;
				}
			}
		}

		return false;
	}

	private boolean _isDraftLayout(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if ((Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT) ||
			 Objects.equals(
				 layout.getType(), LayoutConstants.TYPE_COLLECTION))) {

			Layout draftLayout = layout.fetchDraftLayout();

			if (draftLayout == null) {
				boolean published = GetterUtil.getBoolean(
					layout.getTypeSettingsProperty("published"));

				if ((layout.getStatus() == WorkflowConstants.STATUS_DRAFT) ||
					!published) {

					String mode = ParamUtil.getString(
						httpServletRequest, "p_l_mode");

					if (!Objects.equals(mode, Constants.EDIT)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Reference
	private Portal _portal;

}