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

package com.liferay.wiki.web.wiki.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.wiki.constants.WikiPortletKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 * @author Jack Li
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/wiki/search",
		"mvc.command.name=/wiki/view_all_pages",
		"mvc.command.name=/wiki/view_categorized_pages",
		"mvc.command.name=/wiki/view_draft_pages",
		"mvc.command.name=/wiki/view_orphan_pages",
		"mvc.command.name=/wiki/view_recent_changes",
		"mvc.command.name=/wiki/view_tagged_pages"
	},
	service = MVCRenderCommand.class
)
public class ViewNodeMVCRenderCommand implements MVCRenderCommand {

	public ViewNodeMVCRenderCommand() {
		_jspPaths.put("/wiki/search", "/wiki/search.jsp");
		_jspPaths.put("/wiki/view_all_pages", "/wiki/view_all_pages.jsp");
		_jspPaths.put(
			"/wiki/view_categorized_pages", "/wiki/view_categorized_pages.jsp");
		_jspPaths.put("/wiki/view_draft_pages", "/wiki/view_draft_pages.jsp");
		_jspPaths.put("/wiki/view_orphan_pages", "/wiki/view_orphan_pages.jsp");
		_jspPaths.put(
			"/wiki/view_recent_changes", "/wiki/view_recent_changes.jsp");
		_jspPaths.put("/wiki/view_tagged_pages", "/wiki/view_tagged_pages.jsp");
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		return ActionUtil.viewNode(
			renderRequest,
			_jspPaths.get(
				ParamUtil.get(
					renderRequest, "mvcRenderCommandName", StringPool.BLANK)));
	}

	private final Map<String, String> _jspPaths = new HashMap<>();

}