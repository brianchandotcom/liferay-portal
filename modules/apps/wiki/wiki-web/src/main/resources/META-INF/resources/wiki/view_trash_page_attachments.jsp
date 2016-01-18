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
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

PortletURL portletURL = renderResponse.createActionURL();

portletURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
portletURL.setParameter("title", wikiPage.getTitle());

portletURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/view");

PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());

portletURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/view_page_attachments");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "attachments"), portletURL.toString());
%>

<portlet:actionURL name="/wiki/edit_page_attachment" var="undoTrashURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<div class="container-fluid-1280">
	<liferay-trash:undo portletURL="<%= undoTrashURL %>" />

	<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node.getNodeId(), ActionKeys.ADD_ATTACHMENT) %>">
		<portlet:actionURL name="/wiki/edit_page_attachment" var="emptyTrashURL">
			<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
			<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<liferay-trash:empty
			confirmMessage="are-you-sure-you-want-to-remove-the-attachments-for-this-page"
			emptyMessage="remove-the-attachments-for-this-page"
			infoMessage="attachments-that-have-been-removed-for-more-than-x-will-be-automatically-deleted"
			portletURL="<%= emptyTrashURL.toString() %>"
			totalEntries="<%= wikiPage.getDeletedAttachmentsFileEntriesCount() %>"
		/>
	</c:if>

	<%
	List<FileEntry> attachmentsFileEntries = wikiPage.getDeletedAttachmentsFileEntries();
	int attachmentsFileEntriesCount = wikiPage.getDeletedAttachmentsFileEntriesCount();
	String emptyResultsMessage = "this-page-does-not-have-file-attachments-in-the-recycle-bin";

	PortletURL iteratorURL = renderResponse.createRenderURL();

	iteratorURL.setParameter("mvcRenderCommandName", "/wiki/view_trash_page_attachments");
	iteratorURL.setParameter("redirect", currentURL);
	iteratorURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
	iteratorURL.setParameter("title", wikiPage.getTitle());
	iteratorURL.setWindowState(LiferayWindowState.POP_UP);

	boolean showPageAttachmentAction = true;
	int status = WorkflowConstants.STATUS_IN_TRASH;
	%>

	<%@ include file="/wiki/attachments_list.jspf" %>
</div>

<portlet:actionURL name="/wiki/edit_page_attachment" var="checkEntryURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECK %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:renderURL var="duplicateEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/wiki/restore_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<aui:script use="liferay-restore-entry">
	new Liferay.RestoreEntry(
		{
			checkEntryURL: '<%= checkEntryURL.toString() %>',
			duplicateEntryURL: '<%= duplicateEntryURL.toString() %>',
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>