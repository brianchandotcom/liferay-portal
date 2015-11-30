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

package com.liferay.product.navigation.user.application.list;

import com.liferay.application.list.BaseJSPPanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.USER,
		"service.ranking:Integer=100"
	},
	service = PanelCategory.class
)
public class MyAccountPanelCategory extends BaseJSPPanelCategory {

	@Override
	public String getIconCssClass() {
		return "icon-user";
	}

	@Override
	public String getJspPath() {
		return "/my_account.jsp";
	}

	@Override
	public String getKey() {
		return PanelCategoryKeys.USER_MY_ACCOUNT;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "my-account");
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(ApplicationListWebKeys.PANEL_CATEGORY, this);

		return super.include(request, response);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.navigation.user)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}