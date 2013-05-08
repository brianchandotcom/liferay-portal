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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectFolder");

long repositoryId = scopeGroupId;
String folderName = LanguageUtil.get(pageContext, "home");

if (folder != null) {
	repositoryId = folder.getRepositoryId();
	folderName = folder.getName();

	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}
%>

<aui:form method="post" name="selectFolderFm">
	<liferay-ui:header
		title="home"
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<aui:button-row>
		<c:if test="<%= showAddFolderButton && DLFolderPermission.contains(permissionChecker, repositoryId, folderId, ActionKeys.ADD_FOLDER) %>">
			<portlet:renderURL var="editFolderURL">
				<portlet:param name="struts_action" value="/document_library/edit_folder" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
				<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
			</portlet:renderURL>

			<aui:button href="<%= editFolderURL %>" value='<%= (folder == null) ? "add-folder" : "add-subfolder" %>' />
		</c:if>

		<%
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("folderid", folderId);
		data.put("folderissupportsmetadata", ((folder != null) ? folder.isSupportsMetadata() : Boolean.TRUE.toString()));
		data.put("folderissupportssocial", ((folder != null) ? folder.isSupportsSocial() : Boolean.TRUE.toString()));
		data.put("foldername", HtmlUtil.escapeAttribute(folderName));
		%>

		<aui:button cssClass="selector-button" data="<%= data %>" value="choose-this-folder" />
	</aui:button-row>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/document_library/select_folder");
	portletURL.setParameter("folderId", String.valueOf(folderId));
	%>

	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
	>

		<liferay-ui:search-container-results
			results="<%= DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
			total="<%= DLAppServiceUtil.getFoldersCount(repositoryId, folderId) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.Folder"
			keyProperty="folderId"
			modelVar="curFolder"
			rowVar="row"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="struts_action" value="/document_library/select_folder" />
				<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
			</liferay-portlet:renderURL>

			<%
			int foldersCount = 0;
			int fileEntriesCount = 0;

			try {
				List<Long> subfolderIds = DLAppServiceUtil.getSubfolderIds(curFolder.getRepositoryId(), curFolder.getFolderId(), false);

				foldersCount = subfolderIds.size();

				subfolderIds.clear();
				subfolderIds.add(curFolder.getFolderId());

				fileEntriesCount = DLAppServiceUtil.getFoldersFileEntriesCount(curFolder.getRepositoryId(), subfolderIds, WorkflowConstants.STATUS_APPROVED);
			}
			catch (com.liferay.portal.kernel.repository.RepositoryException re) {
				rowURL = null;
			}
			catch (com.liferay.portal.security.auth.PrincipalException pe) {
				rowURL = null;
			}

			String image = null;

			if (curFolder.isMountPoint()) {
				if (rowURL != null) {
					image = "drive";
				}
				else {
					image = "drive_error";
				}
			}
			else {
				if ((foldersCount + fileEntriesCount) > 0) {
					image = "folder_full_document";
				}
				else {
					image = "folder_empty";
				}
			}
			%>

			<liferay-ui:search-container-column-text
				name="folder"
			>
				<liferay-ui:icon image="<%= image %>" label="<%= true %>" message="<%= HtmlUtil.escape(curFolder.getName()) %>" url="<%= (rowURL != null) ? rowURL.toString() : StringPool.BLANK %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-folders"
				value="<%= String.valueOf(foldersCount) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-documents"
				value="<%= String.valueOf(fileEntriesCount) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= rowURL != null %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("folderid", curFolder.getFolderId());
					data.put("folderissupportsmetadata", curFolder.isSupportsMetadata());
					data.put("folderissupportssocial", curFolder.isSupportsSocial());
					data.put("foldername", HtmlUtil.escapeAttribute(curFolder.getName()));
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	A.one('#<portlet:namespace />selectFolderFm').delegate(
		'click',
		function(event) {
			var result = Util.getAttributes(event.currentTarget, 'data-');

			Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', result);

			Util.getWindow().hide();
		},
		'.selector-button'
	);
</aui:script>