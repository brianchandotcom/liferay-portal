<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
DLAdminDisplayContext dlAdminDisplayContext = (DLAdminDisplayContext)request.getAttribute(DLAdminDisplayContext.class.getName());

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Folder folder = (Folder)row.getObject();

folder = folder.toEscapedModel();

Date modifiedDate = folder.getModifiedDate();

String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
%>

<h2 class="h5">
	<aui:a
		href='<%=
			PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCRenderCommandName(
				"/document_library/view_folder"
			).setRedirect(
				currentURL
			).setParameter(
				"folderId", folder.getFolderId()
			).buildString()
		%>'
	>
		<%= folder.getName() %>
	</aui:a>
</h2>

<span>
	<c:choose>
		<c:when test="<%= Validator.isNull(folder.getUserName()) %>">
			<liferay-ui:message arguments="<%= modifiedDateDescription %>" key="modified-x-ago" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments="<%= new String[] {folder.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
		</c:otherwise>
	</c:choose>
</span>
<span>
	<%= DLUtil.getAbsolutePath(liferayPortletRequest, dlAdminDisplayContext.getRootFolderId(), folder.getParentFolderId()).replace(StringPool.RAQUO_CHAR, StringPool.GREATER_THAN) %>
</span>