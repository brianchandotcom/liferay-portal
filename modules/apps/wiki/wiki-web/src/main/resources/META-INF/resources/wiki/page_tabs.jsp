<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
).buildPortletURL();

PortletURL editPageURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/wiki/edit_page"
).setRedirect(
	viewPageURL
).setParameter(
	"nodeId", node.getNodeId()
).setParameter(
	"title", title
).buildPortletURL();

PortletURL viewPageDetailsURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/wiki/view_page_details"
).setRedirect(
	viewPageURL
).setParameter(
	"nodeId", node.getNodeId()
).setParameter(
	"title", wikiPage.getTitle()
).buildPortletURL();

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