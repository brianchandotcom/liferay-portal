<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

PortletURL viewPageHistoryURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/wiki/view_page_history"
).setRedirect(
	PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCRenderCommandName(
		"/wiki/view"
	).setParameter(
		"nodeName", node.getName()
	).setParameter(
		"title", wikiPage.getTitle()
	).buildString()
).setParameter(
	"nodeId", node.getNodeId()
).setParameter(
	"title", wikiPage.getTitle()
).build();

PortletURL viewPageActivitiesURL = PortletURLBuilder.create(
	PortletURLUtil.clone(viewPageHistoryURL, renderResponse)
).setMVCRenderCommandName(
	"/wiki/view_page_activities"
).buildPortletURL();
%>

<liferay-ui:tabs
	names="activities,versions"
	param="tabs3"
	urls="<%= new String[] {viewPageActivitiesURL.toString(), viewPageHistoryURL.toString()} %>"
/>