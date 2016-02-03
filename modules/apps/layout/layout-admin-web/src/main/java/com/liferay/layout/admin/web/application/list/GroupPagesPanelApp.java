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

package com.liferay.layout.admin.web.application.list;

import com.liferay.application.list.BaseJSPPanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.constants.LayoutAdminWebKeys;
import com.liferay.portal.model.Portlet;

import java.io.IOException;

import javax.servlet.ServletContext;
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
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_PAGES,
		"service.ranking:Integer=200"
	},
	service = PanelApp.class
)
public class GroupPagesPanelApp extends BaseJSPPanelApp {

	@Override
	public String getJspPath() {
		return "/panel/app/layouts_tree.jsp";
	}

	@Override
	public String getPortletId() {
		return LayoutAdminPortletKeys.GROUP_PAGES;
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(LayoutAdminWebKeys.GROUP_PROVIDER, groupProvider);

		return super.include(request, response);
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.admin.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}