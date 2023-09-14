<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
DLCopyEntriesDisplayContext dlCopyEntriesDisplayContext = new DLCopyEntriesDisplayContext(request, liferayPortletResponse, themeDisplay);

dlCopyEntriesDisplayContext.setViewAttributes();
%>

<div class="c-mt-3 sheet sheet-lg">
	<react:component
		module="document_library/js/DLFolderSelector"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"copyActionURL", dlCopyEntriesDisplayContext.getActionURL()
			).put(
				"entryIds", dlCopyEntriesDisplayContext.getEntryIds()
			).put(
				"entryName", dlCopyEntriesDisplayContext.getEntryName()
			).put(
				"redirect", dlCopyEntriesDisplayContext.getRedirect()
			).put(
				"selectionModalURL", dlCopyEntriesDisplayContext.getSelectionModalURL()
			).put(
				"sourceRepositoryId", dlCopyEntriesDisplayContext.getSourceRepositoryId()
			).build()
		%>'
	/>
</div>