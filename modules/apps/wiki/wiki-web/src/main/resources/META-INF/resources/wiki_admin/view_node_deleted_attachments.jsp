<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

List<FileEntry> attachmentsFileEntries = node.getDeletedAttachmentsFiles();

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/wiki/view_pages"
).setRedirect(
	currentURL
).setParameter(
	"nodeId", node.getNodeId()
).buildPortletURL();

PortalUtil.addPortletBreadcrumbEntry(request, node.getName(), portletURL.toString());

portletURL.setParameter("mvcRenderCommandName", "/wiki/view_node_deleted_attachments");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "attachments-recycle-bin"), portletURL.toString());

WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);

PortletURL backToNodeURL = wikiURLHelper.getBackToNodeURL(node);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backToNodeURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "removed-attachments"));
%>

<clay:container-fluid>
	<portlet:actionURL name="/wiki/edit_node_attachment" var="emptyTrashURL">
		<portlet:param name="nodeId" value="<%= String.valueOf(node.getPrimaryKey()) %>" />
	</portlet:actionURL>

	<%
	String trashEntriesMaxAgeTimeDescription = LanguageUtil.getTimeDescription(locale, trashHelper.getMaxAge(themeDisplay.getScopeGroup()) * Time.MINUTE, true);
	%>

	<liferay-trash:empty
		confirmMessage="are-you-sure-you-want-to-remove-the-attachments-for-this-wiki-node"
		emptyMessage="remove-the-attachments-for-this-wiki-node"
		infoMessage='<%= LanguageUtil.format(request, "attachments-that-have-been-removed-for-more-than-x-will-be-automatically-deleted", trashEntriesMaxAgeTimeDescription, false) %>'
		portletURL="<%= emptyTrashURL.toString() %>"
		totalEntries="<%= attachmentsFileEntries.size() %>"
	/>

	<%
	int attachmentsFileEntriesCount = attachmentsFileEntries.size();
	String emptyResultsMessage = "this-wiki-node-does-not-have-file-attachments-in-the-recycle-bin";

	PortletURL iteratorURL = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCRenderCommandName(
		"/wiki/view_node_deleted_attachments"
	).setRedirect(
		currentURL
	).setParameter(
		"nodeId", node.getNodeId()
	).setParameter(
		"viewTrashAttachments", true
	).buildPortletURL();

	boolean paginate = true;
	boolean showPageAttachmentAction = true;
	int status = WorkflowConstants.STATUS_IN_TRASH;
	%>

	<%@ include file="/wiki/attachments_list.jspf" %>
</clay:container-fluid>