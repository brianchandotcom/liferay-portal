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

package com.liferay.layout.admin.web.internal.control.menu;

import com.liferay.layout.admin.web.internal.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.taglib.aui.IconTag;
import com.liferay.taglib.servlet.PageContextFactoryUtil;
import com.liferay.taglib.ui.SuccessTag;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.io.IOException;
import java.io.Writer;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=100"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class ManageLayoutProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest request) {
		return null;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		Writer writer = response.getWriter();

		writer.write("<li class=\"control-menu-nav-item\">");
		writer.write(
			"<a class=\"control-menu-icon lfr-portal-tooltip\" data-qa-id=" +
				"\"editLayout\" data-title=\"");

		ResourceBundle resourceBundle = TagResourceBundleUtil.getResourceBundle(
			request, _portal.getLocale(request));

		writer.write(
			_html.escape(_language.get(resourceBundle, "configure-page")));

		writer.write("\" href=\"");

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = LayoutAdminPortletKeys.GROUP_PAGES;

		Group group = themeDisplay.getScopeGroup();

		if (group.isLayoutPrototype()) {
			portletId = LayoutAdminPortletKeys.LAYOUT_PROTOTYPE_PAGE;
		}

		PortletURL editPageURL = _portal.getControlPanelPortletURL(
			request, portletId, PortletRequest.RENDER_PHASE);

		Layout layout = themeDisplay.getLayout();

		editPageURL.setParameter("backURL", _portal.getCurrentURL(request));
		editPageURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		editPageURL.setParameter("selPlid", String.valueOf(layout.getPlid()));
		editPageURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		editPageURL.write(writer);

		writer.write("\">");

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("icon-monospaced");
		iconTag.setImage("cog");
		iconTag.setMarkupView("lexicon");

		PageContext pageContext = PageContextFactoryUtil.create(
			request, response);

		try {
			iconTag.doTag(pageContext);
		}
		catch (JspException je) {
			throw new IOException(je);
		}

		writer.write("</a></li>");

		SuccessTag successTag = new SuccessTag();

		successTag.setKey("layoutUpdated");
		successTag.setMessage(
			_language.get(resourceBundle, "the-page-was-updated-succesfully"));
		successTag.setTargetNode("#controlMenuAlertsContainer");

		try {
			successTag.doTag(pageContext);
		}
		catch (JspException je) {
			throw new IOException(je);
		}

		return true;
	}

	@Override
	public boolean isShow(HttpServletRequest request) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return false;
		}

		if (!(themeDisplay.isShowLayoutTemplatesIcon() ||
			  themeDisplay.isShowPageSettingsIcon())) {

			return false;
		}

		return super.isShow(request);
	}

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}