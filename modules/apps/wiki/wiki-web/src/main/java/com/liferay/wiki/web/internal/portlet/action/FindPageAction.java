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

package com.liferay.wiki.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.FindStrutsAction;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageResourceLocalService;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Kong
 */
@Component(property = "path=/wiki/find_page", service = StrutsAction.class)
public class FindPageAction extends FindStrutsAction {

	@Override
	protected void addRequiredParameters(
		HttpServletRequest httpServletRequest, String portletId,
		PortletURL portletURL) {

		portletURL.setParameter("struts_action", _getStrutsAction(portletId));
	}

	@Override
	protected long getGroupId(long primaryKey) throws Exception {
		WikiPageResource pageResource =
			_wikiPageResourceLocalService.getPageResource(primaryKey);

		WikiNode node = _wikiNodeLocalService.getNode(pageResource.getNodeId());

		return node.getGroupId();
	}

	@Override
	protected PortletLayoutFinder getPortletLayoutFinder() {
		return _portletLayoutFinder;
	}

	@Override
	protected String getPrimaryKeyParameterName() {
		return "pageResourcePrimKey";
	}

	@Override
	protected PortletURL processPortletURL(
			HttpServletRequest httpServletRequest, PortletURL portletURL)
		throws Exception {

		long pageResourcePrimKey = ParamUtil.getLong(
			httpServletRequest, getPrimaryKeyParameterName());

		WikiPageResource pageResource =
			_wikiPageResourceLocalService.getPageResource(pageResourcePrimKey);

		WikiNode node = _wikiNodeLocalService.getNode(pageResource.getNodeId());

		portletURL.setParameter("nodeName", node.getName());

		portletURL.setParameter("title", pageResource.getTitle());

		return portletURL;
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageResourceLocalService(
		WikiPageResourceLocalService wikiPageResourceLocalService) {

		_wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	private String _getStrutsAction(String portletId) {
		if (portletId.equals(WikiPortletKeys.WIKI) ||
			portletId.equals(WikiPortletKeys.WIKI_ADMIN)) {

			return "/wiki/view";
		}

		return "/wiki_display/view";
	}

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiPage)")
	private PortletLayoutFinder _portletLayoutFinder;

	private WikiNodeLocalService _wikiNodeLocalService;
	private WikiPageResourceLocalService _wikiPageResourceLocalService;

}