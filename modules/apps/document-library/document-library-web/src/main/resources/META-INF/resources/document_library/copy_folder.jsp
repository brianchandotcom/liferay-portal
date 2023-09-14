<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
DLCopyFolderDisplayContext dlCopyFolderDisplayContext = new DLCopyFolderDisplayContext(request, liferayPortletResponse, themeDisplay);

dlCopyFolderDisplayContext.setViewAttributes();
%>

<div class="c-mt-3 sheet sheet-lg">
	<react:component
		module="document_library/js/DLFolderSelector"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"copyActionURL", dlCopyFolderDisplayContext.getActionURL()
			).put(
				"entryIds", dlCopyFolderDisplayContext.getEntryIds()
			).put(
				"entryName", dlCopyFolderDisplayContext.getEntryName()
			).put(
				"redirect", dlCopyFolderDisplayContext.getRedirect()
			).put(
				"selectionModalURL", dlCopyFolderDisplayContext.getSelectionModalURL()
			).put(
				"sourceRepositoryId", dlCopyFolderDisplayContext.getSourceRepositoryId()
			).build()
		%>'
	/>
</div>