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
DLCopyEntryDisplayContext dlCopyEntryDisplayContext = new DLCopyEntryDisplayContext(request, liferayPortletResponse, themeDisplay);

dlCopyEntryDisplayContext.setViewAttributes();
%>

<div class="c-mt-3 sheet sheet-lg">
	<react:component
		module="document_library/js/DDMFolderSelector"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"copyActionURL", dlCopyEntryDisplayContext.getActionURL()
			).put(
				"fileShortcutId", dlCopyEntryDisplayContext.getFileShortcutId()
			).put(
				"itemType", "fileEntry"
			).put(
				"redirect", dlCopyEntryDisplayContext.getRedirect()
			).put(
				"selectionModalURL", dlCopyEntryDisplayContext.getSelectFolderURL()
			).put(
				"sourceFileEntryId", dlCopyEntryDisplayContext.getFileEntryId()
			).put(
				"sourceFileName", dlCopyEntryDisplayContext.getFileName()
			).build()
		%>'
	/>
</div>