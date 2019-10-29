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

package com.liferay.layout.admin.web.internal.product.navigation.control.menu;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.taglib.aui.IconTag;
import com.liferay.taglib.servlet.PageContextFactoryUtil;
import com.liferay.taglib.ui.SuccessTag;

import java.io.IOException;
import java.io.Writer;

import java.util.Locale;
import java.util.Map;
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
	public String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.getClassNameId() == _portal.getClassNameId(Layout.class)) {
			layout = _layoutLocalService.fetchLayout(layout.getClassPK());
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		PortletURL editPageURL = _portal.getControlPanelPortletURL(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		editPageURL.setParameter("mvcRenderCommandName", "/layout/edit_layout");

		String currentURL = _portal.getCurrentURL(httpServletRequest);

		editPageURL.setParameter("redirect", currentURL);
		editPageURL.setParameter("backURL", currentURL);

		editPageURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		editPageURL.setParameter("selPlid", String.valueOf(layout.getPlid()));
		editPageURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		Map<String, String> values = HashMapBuilder.put(
			"configurePage",
			_html.escape(_language.get(resourceBundle, "configure-page"))
		).put(
			"editPageURL", editPageURL.toString()
		).build();

		try {
			IconTag iconTag = new IconTag();

			iconTag.setCssClass("icon-monospaced");
			iconTag.setImage("cog");
			iconTag.setMarkupView("lexicon");

			PageContext pageContext = PageContextFactoryUtil.create(
				httpServletRequest, httpServletResponse);

			values.put("iconCog", iconTag.doTagAsString(pageContext));

			SuccessTag successTag = new SuccessTag();

			successTag.setKey("layoutUpdated");
			successTag.setMessage(
				_language.get(
					resourceBundle, "the-page-was-updated-succesfully"));
			successTag.setTargetNode("#controlMenuAlertsContainer");

			values.put(
				"layoutUpdatedMessage", successTag.doTagAsString(pageContext));
		}
		catch (JspException je) {
			ReflectionUtil.throwException(je);
		}

		Writer writer = httpServletResponse.getWriter();

		writer.write(StringUtil.replace(_TMPL_CONTENT, "${", "}", values));

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

		if (isEmbeddedPersonalApplicationLayout(layout)) {
			return false;
		}

		if (!(themeDisplay.isShowLayoutTemplatesIcon() ||
			  themeDisplay.isShowPageSettingsIcon())) {

			return false;
		}

		return super.isShow(httpServletRequest);
	}

	private static final String _TMPL_CONTENT = StringUtil.read(
		ManageLayoutProductNavigationControlMenuEntry.class,
		"/META-INF/resources/control/menu/edit_layout_control_menu_entry_" +
			"icon.tmpl");

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}