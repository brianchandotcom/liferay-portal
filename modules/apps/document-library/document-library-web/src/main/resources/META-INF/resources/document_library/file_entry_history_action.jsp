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

FileVersion fileVersion = null;

if (row != null) {
	fileVersion = (FileVersion)row.getObject();
}
else {
	fileVersion = (FileVersion)request.getAttribute("info_panel.jsp-fileVersion");
}

DLViewFileEntryHistoryDisplayContext dlViewFileEntryHistoryDisplayContext = dlDisplayContextProvider.getDLViewFileEntryHistoryDisplayContext(request, response, fileVersion);
%>

<liferay-ui:menu
	menu="<%= dlViewFileEntryHistoryDisplayContext.getMenu() %>"
/>