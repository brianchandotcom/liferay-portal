<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
CopyDLObjectsDisplayContext copyDLObjectsDisplayContext = (CopyDLObjectsDisplayContext)request.getAttribute(CopyDLObjectsDisplayContext.class.getName());

copyDLObjectsDisplayContext.setViewAttributes();
%>

<div class="c-mt-3 sheet sheet-lg">
	<react:component
		module="{DLFolderSelector} from document-library-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"copyActionURL", copyDLObjectsDisplayContext.getActionURL()
			).put(
				"dlObjectIds", copyDLObjectsDisplayContext.getDLObjectIds()
			).put(
				"dlObjectName", copyDLObjectsDisplayContext.getDLObjectName()
			).put(
				"redirect", copyDLObjectsDisplayContext.getRedirect()
			).put(
				"selectionModalURL", copyDLObjectsDisplayContext.getSelectionModalURL()
			).put(
				"size", copyDLObjectsDisplayContext.getSize()
			).put(
				"sourceRepositoryId", copyDLObjectsDisplayContext.getSourceRepositoryId()
			).build()
		%>'
	/>
</div>