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

PortletURL portletURL = PortletURLBuilder.createActionURL(
	renderResponse
).setActionName(
	"/wiki/view"
).setParameter(
	"nodeId", node.getNodeId()
).setParameter(
	"title", wikiPage.getTitle()
).buildPortletURL();

PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());

portletURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/view_page_attachments");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "attachments"), portletURL.toString());
%>

<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/wiki/page_tabs.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="attachments" />
</liferay-util:include>

<%
List<FileEntry> attachmentsFileEntries = wikiPage.getAttachmentsFileEntries();
int attachmentsFileEntriesCount = wikiPage.getAttachmentsFileEntriesCount();
String emptyResultsMessage = "this-page-does-not-have-file-attachments";

PortletURL iteratorURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/wiki/view_page_attachments"
).setRedirect(
	currentURL
).setParameter(
	"nodeId", node.getNodeId()
).setParameter(
	"title", wikiPage.getTitle()
).buildPortletURL();

boolean paginate = false;
boolean showPageAttachmentAction = false;
int status = WorkflowConstants.STATUS_APPROVED;
%>

<%@ include file="/wiki/attachments_list.jspf" %>