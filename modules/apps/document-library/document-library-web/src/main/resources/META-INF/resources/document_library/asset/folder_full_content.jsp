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

<%@ include file="/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);
%>

<c:if test="<%= folder != null %>">
	<div class="aspect-ratio aspect-ratio-8-to-3 bg-light mb-4">
		<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon">
			<div class="text-secondary">
				<svg aria-hidden="true" class="lexicon-icon lexicon-icon-folder reference-mark user-icon-xl">
					<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#folder" />
				</svg>
			</div>
		</div>
	</div>

	<c:if test="<%= Validator.isNotNull(folder.getDescription()) %>">
		<p>
			<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(folder.getDescription())) %>
		</p>
	</c:if>

	<%
	int foldersCount = DLAppServiceUtil.getFoldersCount(folder.getRepositoryId(), folder.getFolderId());
	%>

	<div class="small">
		<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "folder" : "folders" %>' />
	</div>

	<%
	int status = WorkflowConstants.STATUS_APPROVED;

	if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		status = WorkflowConstants.STATUS_ANY;
	}

	int fileEntriesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(folder.getRepositoryId(), folder.getFolderId(), status);
	%>

	<div class="small">
		<%= fileEntriesCount %> <liferay-ui:message key='<%= (fileEntriesCount == 1) ? "document" : "documents" %>' />
	</div>

	<liferay-expando:custom-attributes-available
		className="<%= DLFolderConstants.getClassName() %>"
	>
		<liferay-expando:custom-attribute-list
			className="<%= DLFolderConstants.getClassName() %>"
			classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
			editable="<%= false %>"
			label="<%= true %>"
		/>
	</liferay-expando:custom-attributes-available>
</c:if>