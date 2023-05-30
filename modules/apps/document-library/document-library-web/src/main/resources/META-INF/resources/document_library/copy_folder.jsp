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
DLCopyFolderDisplayContext dlCopyFolderDisplayContext = new DLCopyFolderDisplayContext(request, liferayPortletResponse, themeDisplay);

dlCopyFolderDisplayContext.setViewAttributes(liferayPortletResponse);

Long sourceFolderId = dlCopyFolderDisplayContext.getSourceFolderId();

Long sourceRepositoryId = dlCopyFolderDisplayContext.getSourceRepositoryId();

String sourceFolderName = dlCopyFolderDisplayContext.getSourceFolderName();

String selectFolderURL = dlCopyFolderDisplayContext.getSelectFolderURL();

String redirect = dlCopyFolderDisplayContext.getRedirect();

String copyActionURL = dlCopyFolderDisplayContext.getActionURL();
%>

<div class="c-mt-3 sheet sheet-lg">
	<react:component
		module="document_library/js/DDMFolderSelector"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"copyActionURL", copyActionURL
			).put(
				"itemType", "folder"
			).put(
				"redirect", redirect
			).put(
				"selectionModalURL", selectFolderURL
			).put(
				"sourceFolderId", sourceFolderId
			).put(
				"sourceFolderName", sourceFolderName
			).put(
				"sourceRepositoryId", sourceRepositoryId
			).build()
		%>'
	/>
</div>