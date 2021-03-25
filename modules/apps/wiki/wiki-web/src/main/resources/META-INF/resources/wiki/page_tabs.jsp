<%--
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
--%>

<%@ include file="/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

String title = wikiPage.getTitle();

PortletURL viewPageURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/wiki/view"
).setParameter(
	"nodeName", node.getName()
).setParameter(
	"title", wikiPage.getTitle()
).build();

PortletURL editPageURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/wiki/edit_page"
).setRedirect(
	viewPageURL.toString()
).setParameter(
	"nodeId", String.valueOf(node.getNodeId())
).setParameter(
	"title", title
).build();

PortletURL viewPageDetailsURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/wiki/view_page_details"
).setRedirect(
	viewPageURL.toString()
).setParameter(
	"nodeId", String.valueOf(node.getNodeId())
).setParameter(
	"title", wikiPage.getTitle()
).build();

String[] tabs1Names = {"details", "history", "incoming-links", "outgoing-links", "attachments"};
String[] tabs1URLs = {
	viewPageDetailsURL.toString(),
	PortletURLBuilder.create(
		PortletURLUtil.clone(viewPageDetailsURL, renderResponse)
	).setMVCRenderCommandName(
		"/wiki/view_page_activities"
	).buildString(),
	PortletURLBuilder.create(
		PortletURLUtil.clone(viewPageDetailsURL, renderResponse)
	).setMVCRenderCommandName(
		"/wiki/view_page_incoming_links"
	).buildString(),
	PortletURLBuilder.create(
		PortletURLUtil.clone(viewPageDetailsURL, renderResponse)
	).setMVCRenderCommandName(
		"/wiki/view_page_outgoing_links"
	).buildString(),
	PortletURLBuilder.create(
		PortletURLUtil.clone(viewPageDetailsURL, renderResponse)
	).setMVCRenderCommandName(
		"/wiki/view_page_attachments"
	).buildString()
};

if (WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE)) {
	tabs1Names = ArrayUtil.append(new String[] {"content"}, tabs1Names);
	tabs1URLs = ArrayUtil.append(new String[] {editPageURL.toString()}, tabs1URLs);
}
%>

<%@ include file="/wiki/page_name.jspf" %>

<liferay-ui:tabs
	names="<%= StringUtil.merge(tabs1Names) %>"
	urls="<%= tabs1URLs %>"
/>