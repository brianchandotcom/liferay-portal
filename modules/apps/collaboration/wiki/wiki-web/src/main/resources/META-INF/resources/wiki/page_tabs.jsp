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

PortletURL viewPageURL = renderResponse.createRenderURL();

viewPageURL.setParameter("mvcRenderCommandName", "/wiki/view");
viewPageURL.setParameter("nodeName", node.getName());
viewPageURL.setParameter("title", wikiPage.getTitle());

PortletURL editPageURL = renderResponse.createRenderURL();

editPageURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
editPageURL.setParameter("redirect", viewPageURL.toString());
editPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
editPageURL.setParameter("title", title);

PortletURL viewPageDetailsURL = renderResponse.createRenderURL();

viewPageDetailsURL.setParameter("mvcRenderCommandName", "/wiki/view_page_details");
viewPageDetailsURL.setParameter("redirect", viewPageURL.toString());
viewPageDetailsURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
viewPageDetailsURL.setParameter("title", wikiPage.getTitle());

PortletURL viewPageHistoryURL = PortletURLUtil.clone(viewPageDetailsURL, renderResponse);

viewPageHistoryURL.setParameter("mvcRenderCommandName", "/wiki/view_page_activities");

PortletURL viewPageIncomingLinksURL = PortletURLUtil.clone(viewPageDetailsURL, renderResponse);

viewPageIncomingLinksURL.setParameter("mvcRenderCommandName", "/wiki/view_page_incoming_links");

PortletURL viewPageOutgoingLinksURL = PortletURLUtil.clone(viewPageDetailsURL, renderResponse);

viewPageOutgoingLinksURL.setParameter("mvcRenderCommandName", "/wiki/view_page_outgoing_links");

PortletURL viewPageAttachmentsURL = PortletURLUtil.clone(viewPageDetailsURL, renderResponse);

viewPageAttachmentsURL.setParameter("mvcRenderCommandName", "/wiki/view_page_attachments");

PortletURL viewPageActivitiesURL = PortletURLUtil.clone(viewPageDetailsURL, renderResponse);

viewPageActivitiesURL.setParameter("mvcRenderCommandName", "/wiki/view_page_activities");

String[] tabs1Names = {"details", "history", "incoming-links", "outgoing-links", "attachments"};
String[] tabs1URLs = {viewPageDetailsURL.toString(), viewPageHistoryURL.toString(), viewPageIncomingLinksURL.toString(), viewPageOutgoingLinksURL.toString(), viewPageAttachmentsURL.toString()};

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