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

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;
import com.liferay.product.navigation.product.menu.helper.ProductNavigationProductMenuHelper;
import com.liferay.taglib.aui.IconTag;

import java.io.IOException;
import java.io.Writer;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

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

		Map<String, String> values = HashMapBuilder.put(
			"cssClass",
			() -> {
				String productMenuState = SessionClicks.get(
					httpServletRequest,
					"com.liferay.product.navigation.product.menu." +
						"web_productMenuState",
					"closed");

				if (Objects.equals(productMenuState, "open")) {
					return "active";
				}

				return StringPool.BLANK;
			}
		).put(
			"dataURL",
			() -> {
				PortletURL portletURL = PortletURLBuilder.create(
					PortletURLFactoryUtil.create(
						httpServletRequest,
						ProductNavigationProductMenuPortletKeys.
							PRODUCT_NAVIGATION_PRODUCT_MENU,
						RenderRequest.RENDER_PHASE)
				).setMVCPath(
					"/portlet/product_menu.jsp"
				).setParameter(
					"selPpid",
					() -> {
						ThemeDisplay themeDisplay =
							(ThemeDisplay)httpServletRequest.getAttribute(
								WebKeys.THEME_DISPLAY);

						PortletDisplay portletDisplay =
							themeDisplay.getPortletDisplay();

						return portletDisplay.getId();
					}
				).buildPortletURL();

				try {
					portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
				}
				catch (WindowStateException windowStateException) {
					ReflectionUtil.throwException(windowStateException);
				}

				return "data-url='" + portletURL.toString() + "'";
			}
		).put(
			"portletNamespace",
			_portal.getPortletNamespace(
				ProductNavigationProductMenuPortletKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU)
		).put(
			"title",
			HtmlUtil.escape(LanguageUtil.get(httpServletRequest, "menu"))
		).build();

		try {
			IconTag iconTag = new IconTag();

			iconTag.setCssClass("icon-monospaced icon-product-menu-closed");
			iconTag.setImage("product-menu-closed");
			iconTag.setMarkupView("lexicon");

			values.put(
				"closedIcon",
				iconTag.doTagAsString(httpServletRequest, httpServletResponse));

			iconTag.setCssClass("icon-monospaced icon-product-menu-open");
			iconTag.setImage("product-menu-open");
			iconTag.setMarkupView("lexicon");

			values.put(
				"openIcon",
				iconTag.doTagAsString(httpServletRequest, httpServletResponse));
		}
		catch (JspException jspException) {
			ReflectionUtil.throwException(jspException);
		}

		Writer writer = httpServletResponse.getWriter();

		writer.write(StringUtil.replace(_TMPL_CONTENT, "${", "}", values));

		return true;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		if (_productNavigationProductMenuHelper.isShowProductMenu(
				httpServletRequest)) {

			return true;
		}

		return false;
	}

	private static final String _TMPL_CONTENT = StringUtil.read(
		ProductMenuProductNavigationControlMenuEntry.class, "icon.tmpl");

	@Reference
	private Portal _portal;

	@Reference
	private ProductNavigationProductMenuHelper
		_productNavigationProductMenuHelper;

}