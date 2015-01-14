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

<%@ include file="/html/portlet/image_gallery_display/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	Folder folder = DLAppServiceUtil.getFolder(folderId);

	repositoryId = folder.getRepositoryId();
}

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
%>

<liferay-ui:search-container
	curParam="cur1"
	headerNames="folder,num-of-folders,num-of-images"
	iteratorURL="<%= portletURL %>"
	total="<%= DLAppServiceUtil.getFoldersCount(repositoryId, folderId) %>"
>
	<liferay-ui:search-container-results
		results="<%= DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.repository.model.Folder"
		escapedModel="<%= true %>"
		keyProperty="folderId"
		modelVar="curFolder"
	>
		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="struts_action" value="/image_gallery_display/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="folder"
		>

			<%
			buffer.append("<a href=\"");
			buffer.append(rowURL);
			buffer.append("\">");
			buffer.append("<img alt=\"");
			buffer.append(LanguageUtil.get(pageContext, "folder"));
			buffer.append("\" class=\"label-icon\" src=\"");
			buffer.append(themeDisplay.getPathThemeImages());
			buffer.append("/common/folder.png\">");
			buffer.append("<strong>");
			buffer.append(curFolder.getName());
			buffer.append("</strong>");

			if (Validator.isNotNull(curFolder.getDescription())) {
				buffer.append("<br />");
				buffer.append(curFolder.getDescription());
			}

			buffer.append("</a>");

			List subfolders = DLAppServiceUtil.getFolders(repositoryId, curFolder.getFolderId(), 0, 5);

			if (!subfolders.isEmpty()) {
				int subfoldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, curFolder.getFolderId());

				buffer.append("<br /><u>");
				buffer.append(LanguageUtil.get(pageContext, "subfolders"));
				buffer.append("</u>: ");

				for (int j = 0; j < subfolders.size(); j++) {
					Folder subfolder = (Folder)subfolders.get(j);

					subfolder = subfolder.toEscapedModel();

					rowURL.setParameter("folderId", String.valueOf(subfolder.getFolderId()));

					buffer.append("<a href=\"");
					buffer.append(rowURL);
					buffer.append("\">");
					buffer.append(subfolder.getName());
					buffer.append("</a>");

					if ((j + 1) < subfolders.size()) {
						buffer.append(", ");
					}
				}

				if (subfoldersCount > subfolders.size()) {
					rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

					buffer.append(", <a href=\"");
					buffer.append(rowURL);
					buffer.append("\">");
					buffer.append(LanguageUtil.get(pageContext, "more"));
					buffer.append(" &raquo;");
					buffer.append("</a>");
				}

				rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
			}
			%>

		</liferay-ui:search-container-column-text>

		<%
		List subfolderIds = new ArrayList();

		subfolderIds.add(new Long(curFolder.getFolderId()));

		DLAppServiceUtil.getSubfolderIds(repositoryId, curFolder.getFolderId());

		int subFoldersCount = subfolderIds.size() - 1;
		int subEntriesCount = DLAppServiceUtil.getFoldersFileEntriesCount(repositoryId, subfolderIds, status);
		%>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="num-of-folders"
			value="<%= String.valueOf(subFoldersCount) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="num-of-entries"
			value="<%= String.valueOf(subEntriesCount) %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/document_library/folder_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>