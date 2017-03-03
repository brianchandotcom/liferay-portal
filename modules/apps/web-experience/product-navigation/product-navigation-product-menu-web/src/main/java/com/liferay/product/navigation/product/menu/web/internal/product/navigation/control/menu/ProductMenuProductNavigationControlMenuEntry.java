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

package com.liferay.product.navigation.product.menu.web.internal.product.navigation.control.menu;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.product.navigation.product.menu.web.constants.ProductNavigationProductMenuPortletKeys;
import com.liferay.product.navigation.product.menu.web.constants.ProductNavigationProductMenuWebKeys;
import com.liferay.taglib.portletext.RuntimeTag;
import com.liferay.taglib.util.BodyBottomTag;

import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.WindowStateException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.SITES,
		"product.navigation.control.menu.entry.order:Integer=100"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class ProductMenuProductNavigationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry {

	@Override
	public String getBodyJspPath() {
		return "/portlet/control_menu/product_menu_control_menu_entry_body.jsp";
	}

	@Override
	public String getIconJspPath() {
		return null;
	}

	@Override
	public boolean includeBody(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		BodyBottomTag bodyBottomTag = new BodyBottomTag();

		bodyBottomTag.setOutputKey("productMenu");

		try {
			bodyBottomTag.doBodyTag(
				request, response, this::_processBodyBottomContent);
		}
		catch (JspException je) {
			throw new IOException(je);
		}

		return true;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		Map<String, String> values = new HashMap<>();

		String portletNamespace = PortalUtil.getPortletNamespace(
			ProductNavigationProductMenuPortletKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU);

		values.put("portletNamespace", portletNamespace);

		values.put("title", HtmlUtil.escape(LanguageUtil.get(request, "menu")));

		String productMenuState = SessionClicks.get(
			request,
			ProductNavigationProductMenuWebKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU_STATE,
			"closed");

		if (Objects.equals(productMenuState, "open")) {
			values.put("cssClass", "active");
			values.put("dataURL", StringPool.BLANK);
		}
		else {
			values.put("cssClass", StringPool.BLANK);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			PortletURL portletURL = PortletURLFactoryUtil.create(
				request,
				ProductNavigationProductMenuPortletKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU,
				RenderRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/portlet/product_menu.jsp");
			portletURL.setParameter("selPpid", portletDisplay.getId());

			try {
				portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
			}
			catch (WindowStateException wse) {
				ReflectionUtil.throwException(wse);
			}

			values.put("dataURL", "data-url='" + portletURL.toString() + "'");
		}

		Writer writer = response.getWriter();

		writer.write(StringUtil.replace(_TMPL_CONTENT, "${", "}", values));

		return true;
	}

	@Override
	public boolean isShow(HttpServletRequest request) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isImpersonated()) {
			return true;
		}

		User user = themeDisplay.getUser();

		if (themeDisplay.isSignedIn() && user.isSetupComplete()) {
			return true;
		}

		return false;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.navigation.product.menu.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private void _processBodyBottomContent(PageContext pageContext) {
		try {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write("<div class=\"");

			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			String productMenuState = SessionClicks.get(
				request,
				ProductNavigationProductMenuWebKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU_STATE,
				"closed");

			jspWriter.write(productMenuState);

			jspWriter.write(
				" hidden-print lfr-product-menu-panel sidenav-fixed " +
					"sidenav-menu-slider\" id=\"");

			String portletNamespace = PortalUtil.getPortletNamespace(
				ProductNavigationProductMenuPortletKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU);

			jspWriter.write(portletNamespace);

			jspWriter.write("sidenavSliderId\">");
			jspWriter.write(
				"<div class=\"product-menu sidebar sidenav-menu\">");

			RuntimeTag runtimeTag = new RuntimeTag();

			runtimeTag.setPortletName(
				ProductNavigationProductMenuPortletKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU);

			runtimeTag.doTag(pageContext);

			jspWriter.write("</div></div>");
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
	}

	private static final String _TMPL_CONTENT = StringUtil.read(
		ProductMenuProductNavigationControlMenuEntry.class, "icon.tmpl");

}