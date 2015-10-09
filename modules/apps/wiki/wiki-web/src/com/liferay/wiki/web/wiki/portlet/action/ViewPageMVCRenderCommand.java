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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/", "mvc.command.name=/wiki/select_version",
		"mvc.command.name=/wiki/view",
		"mvc.command.name=/wiki/view_page",
		"mvc.command.name=/wiki/view_page_activities",
		"mvc.command.name=/wiki/view_page_attachments",
		"mvc.command.name=/wiki/view_page_details",
		"mvc.command.name=/wiki/view_page_history",
		"mvc.command.name=/wiki/view_page_incoming_links",
		"mvc.command.name=/wiki/view_page_outgoing_links",
		"mvc.command.name=/wiki_admin/view"
	},
	service = MVCRenderCommand.class
)
public class ViewPageMVCRenderCommand implements MVCRenderCommand {

	public ViewPageMVCRenderCommand() {
		_jspPaths.put("/", "/wiki/view.jsp");
		_jspPaths.put("/wiki/select_version", "/wiki/select_version.jsp");
		_jspPaths.put("/wiki/view", "/wiki/view.jsp");
		_jspPaths.put("/wiki/view_page", "/wiki_display/view.jsp");
		_jspPaths.put(
			"/wiki/view_page_activities", "/wiki/view_page_activities.jsp");
		_jspPaths.put(
			"/wiki/view_page_attachments", "/wiki/view_page_attachments.jsp");
		_jspPaths.put("/wiki/view_page_details", "/wiki/view_page_details.jsp");
		_jspPaths.put("/wiki/view_page_history", "/wiki/view_page_history.jsp");
		_jspPaths.put(
			"/wiki/view_page_incoming_links",
			"/wiki/view_page_incoming_links.jsp");
		_jspPaths.put(
			"/wiki/view_page_outgoing_links",
			"/wiki/view_page_outgoing_links.jsp");
		_jspPaths.put("/wiki_admin/view", "/wiki/view.jsp");
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long categoryId = ParamUtil.getLong(renderRequest, "categoryId");

		if (categoryId > 0) {
			return ActionUtil.viewNode(
				renderRequest, "/wiki/view_categorized_pages.jsp");
		}

		String tag = ParamUtil.getString(renderRequest, "tag");

		if (Validator.isNotNull(tag)) {
			return ActionUtil.viewNode(
				renderRequest, "/wiki/view_tagged_pages.jsp");
		}

		try {
			ActionUtil.getNode(renderRequest);
			ActionUtil.getPage(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/wiki/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return _jspPaths.get(
			ParamUtil.get(
				renderRequest, "mvcRenderCommandName", StringPool.BLANK));
	}

	protected void getNode(RenderRequest renderRequest) throws Exception {
		ActionUtil.getNode(renderRequest);

		WikiNode node = (WikiNode)renderRequest.getAttribute(
			WikiWebKeys.WIKI_NODE);

		if (node != null) {
			return;
		}

		node = ActionUtil.getFirstVisibleNode(renderRequest);

		renderRequest.setAttribute(WikiWebKeys.WIKI_NODE, node);
	}

	private final Map<String, String> _jspPaths = new HashMap<>();

}