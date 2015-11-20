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

package com.liferay.wiki.navigation.web.portlet.action;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.navigation.web.constants.WikiNavigationPortletKeys;
import com.liferay.wiki.service.WikiNodeService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + WikiNavigationPortletKeys.PAGE_MENU},
	service = ConfigurationAction.class
)
public class WikiNavigationPageMenuConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest request) {
		return "/page_menu/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateNode(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.wiki.navigation.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference(unbind = "-")
	protected void setWikiNodeService(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	protected void validateNode(ActionRequest actionRequest) throws Exception {
		long selNodeId = GetterUtil.getLong(
			getParameter(actionRequest, "selNodeId"));

		try {
			_wikiNodeService.getNode(selNodeId);
		}
		catch (NoSuchNodeException nsne) {
			SessionErrors.add(actionRequest, nsne.getClass());
		}
	}

	private volatile WikiNodeService _wikiNodeService;

}