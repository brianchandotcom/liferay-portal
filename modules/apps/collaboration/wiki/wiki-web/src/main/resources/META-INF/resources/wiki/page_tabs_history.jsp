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
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

PortletURL viewPageURL = renderResponse.createRenderURL();

viewPageURL.setParameter("mvcRenderCommandName", "/wiki/view");
viewPageURL.setParameter("nodeName", node.getName());
viewPageURL.setParameter("title", wikiPage.getTitle());

PortletURL viewPageHistoryURL = renderResponse.createRenderURL();

viewPageHistoryURL.setParameter("mvcRenderCommandName", "/wiki/view_page_history");
viewPageHistoryURL.setParameter("redirect", viewPageURL.toString());
viewPageHistoryURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
viewPageHistoryURL.setParameter("title", wikiPage.getTitle());

PortletURL viewPageActivitiesURL = PortletURLUtil.clone(viewPageHistoryURL, renderResponse);

viewPageActivitiesURL.setParameter("mvcRenderCommandName", "/wiki/view_page_activities");
%>

<liferay-ui:tabs
	names="activities,versions"
	param="tabs3"
	urls="<%= new String[] {viewPageActivitiesURL.toString(), viewPageHistoryURL.toString()} %>"
/>