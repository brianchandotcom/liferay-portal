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
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL);

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

Map<String, String> orderColumns = new HashMap<String, String>();

orderColumns.put("downloads", "downloads");
orderColumns.put("creationDate", "create-date");
orderColumns.put("modifiedDate", "modified-date");
orderColumns.put("size", "size");
orderColumns.put("title", "title");

PortletURL sortURL = renderResponse.createRenderURL();

sortURL.setParameter("mvcRenderCommandName", "/document_library/view");
sortURL.setParameter("navigation", navigation);
sortURL.setParameter("folderId", String.valueOf(folderId));
sortURL.setParameter("fileEntryTypeId", String.valueOf(fileEntryTypeId));
%>

<liferay-frontend:management-bar-sort
	orderByCol="<%= orderByCol %>"
	orderByType="<%= orderByType %>"
	orderColumns="<%= orderColumns %>"
	portletURL="<%= sortURL %>"
/>