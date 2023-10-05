<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
CopyDLObjectsDisplayContext copyDLObjectsDisplayContext = new CopyDLObjectsDisplayContext(request, liferayPortletResponse, themeDisplay);

copyDLObjectsDisplayContext.setViewAttributes();
%>

<div class="c-mt-3 sheet sheet-lg">
	<react:component
		module="document_library/js/DLFolderSelector"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"copyActionURL", copyDLObjectsDisplayContext.getActionURL()
			).put(
				"entryIds", copyDLObjectsDisplayContext.getEntryIds()
			).put(
				"entryName", copyDLObjectsDisplayContext.getEntryName()
			).put(
				"redirect", copyDLObjectsDisplayContext.getRedirect()
			).put(
				"selectionModalURL", copyDLObjectsDisplayContext.getSelectionModalURL()
			).put(
				"sourceRepositoryId", copyDLObjectsDisplayContext.getSourceRepositoryId()
			).build()
		%>'
	/>
</div>