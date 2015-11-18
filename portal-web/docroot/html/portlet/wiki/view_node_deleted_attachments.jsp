<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

List<FileEntry> attachmentsFileEntries = node.getDeletedAttachmentsFiles();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/wiki/view_all_pages");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

PortalUtil.addPortletBreadcrumbEntry(request, node.getName(), portletURL.toString());

portletURL.setParameter("struts_action", "/wiki/view_node_deleted_attachments");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "attachments-recycle-bin"), portletURL.toString());

PortletURL iteratorURL = renderResponse.createRenderURL();

iteratorURL.setParameter("struts_action", "/wiki/view_node_deleted_attachments");
iteratorURL.setParameter("redirect", currentURL);
iteratorURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
iteratorURL.setParameter("viewTrashAttachments", Boolean.TRUE.toString());
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title="removed-attachments"
/>

<portlet:actionURL var="emptyTrashURL">
	<portlet:param name="struts_action" value="/wiki/edit_node_attachment" />
	<portlet:param name="nodeId" value="<%= String.valueOf(node.getPrimaryKey()) %>" />
</portlet:actionURL>

<liferay-ui:trash-empty
	confirmMessage="are-you-sure-you-want-to-remove-the-attachments-for-this-wiki-node"
	emptyMessage="remove-the-attachments-for-this-wiki-node"
	infoMessage="attachments-that-have-been-removed-for-more-than-x-will-be-automatically-deleted"
	portletURL="<%= emptyTrashURL.toString() %>"
	totalEntries="<%= attachmentsFileEntries.size() %>"
/>

<liferay-ui:search-container
	emptyResultsMessage="this-wiki-node-does-not-have-file-attachments-in-the-recycle-bin"
	iteratorURL="<%= iteratorURL %>"
	total="<%= attachmentsFileEntries.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(attachmentsFileEntries, searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.repository.model.FileEntry"
		modelVar="fileEntry"
	>

		<%
		WikiPage wikiPage = WikiPageAttachmentsUtil.getPage(fileEntry.getFileEntryId());

		String rowHREF = PortletFileRepositoryUtil.getPortletFileEntryURL(themeDisplay, fileEntry, "status=" + WorkflowConstants.STATUS_IN_TRASH);

		rowHREF = HttpUtil.addParameter(rowHREF, "download", true);
		%>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="file-name"
		>
			<img align="left" alt="" border="0" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= DLUtil.getFileIcon(fileEntry.getExtension()) %>.png"> <%= TrashUtil.getOriginalTitle(fileEntry.getTitle()) %>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="size"
			value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/wiki/page_attachment_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<liferay-ui:restore-entry
	duplicateEntryAction="/wiki/restore_entry"
	overrideMessage="overwrite-the-existing-attachment-with-the-removed-one"
	renameMessage="keep-both-attachments-and-rename-the-removed-attachment-as"
	restoreEntryAction="/wiki/restore_page_attachment"
/>