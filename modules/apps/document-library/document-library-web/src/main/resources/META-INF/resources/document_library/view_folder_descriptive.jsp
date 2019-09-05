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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Folder folder = (Folder)row.getObject();

folder = folder.toEscapedModel();

Date modifiedDate = folder.getLastPostDate();

String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);

PortletURL rowURL = liferayPortletResponse.createRenderURL();

rowURL.setParameter("mvcRenderCommandName", "/document_library/view_folder");
rowURL.setParameter("redirect", currentURL);
rowURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
%>

<h2 class="h5">
	<aui:a href="<%= rowURL.toString() %>">
		<%= folder.getName() %>
	</aui:a>
</h2>

<span>
	<c:choose>
		<c:when test="<%= Validator.isNull(folder.getUserName()) %>">
			<liferay-ui:message arguments="<%= new String[] {modifiedDateDescription} %>" key="modified-x-ago" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments="<%= new String[] {folder.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
		</c:otherwise>
	</c:choose>
</span>
<span>
	<%= DLUtil.getAbsolutePath(liferayPortletRequest, folder.getParentFolderId()).replace(StringPool.RAQUO_CHAR, StringPool.GREATER_THAN) %>
</span>