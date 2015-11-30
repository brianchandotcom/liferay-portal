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

package com.liferay.wiki.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeService;
import com.liferay.wiki.service.WikiPageService;
import com.liferay.wiki.web.util.WikiWebComponentProvider;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/", "mvc.command.name=/wiki_display/view"
	},
	service = MVCRenderCommand.class
)
public class WikiDisplayViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			PortletPreferences portletPreferences =
				renderRequest.getPreferences();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			WikiWebComponentProvider wikiWebComponentProvider =
				WikiWebComponentProvider.getWikiWebComponentProvider();

			WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
				wikiWebComponentProvider.getWikiGroupServiceConfiguration();

			String title = ParamUtil.getString(
				renderRequest, "title",
				portletPreferences.getValue(
					"title", wikiGroupServiceConfiguration.frontPageName()));
			double version = ParamUtil.getDouble(renderRequest, "version");

			WikiNode node = getNode(renderRequest);

			if (node.getGroupId() != themeDisplay.getScopeGroupId()) {
				throw new NoSuchNodeException(
					"{nodeId=" + node.getNodeId() + "}");
			}

			WikiPage page = _wikiPageService.fetchPage(
				node.getNodeId(), title, version);

			if ((page == null) || page.isInTrash()) {
				page = _wikiPageService.getPage(
					node.getNodeId(),
					wikiGroupServiceConfiguration.frontPageName());
			}

			renderRequest.setAttribute(WikiWebKeys.WIKI_NODE, node);
			renderRequest.setAttribute(WikiWebKeys.WIKI_PAGE, page);

			return "/wiki_display/view.jsp";
		}
		catch (NoSuchNodeException nsne) {
			return "/wiki_display/portlet_not_setup.jsp";
		}
		catch (NoSuchPageException nspe) {
			return "/wiki_display/portlet_not_setup.jsp";
		}
		catch (PortalException pe) {
			SessionErrors.add(renderRequest, pe.getClass());

			return "/wiki_display/error.jsp";
		}
	}

	protected WikiNode getNode(RenderRequest renderRequest)
		throws PortalException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String nodeName = ParamUtil.getString(renderRequest, "nodeName");

		if (Validator.isNotNull(nodeName)) {
			return _wikiNodeService.getNode(
				themeDisplay.getScopeGroupId(), nodeName);
		}
		else {
			long nodeId = GetterUtil.getLong(
				portletPreferences.getValue("nodeId", StringPool.BLANK));

			return _wikiNodeService.getNode(nodeId);
		}
	}

	@Reference(unbind = "-")
	protected void setWikiNodeService(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	private volatile WikiNodeService _wikiNodeService;
	private volatile WikiPageService _wikiPageService;

}