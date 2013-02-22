<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}

String keywords = ParamUtil.getString(request, "keywords");

Map<String, String> requestParams = new HashMap<String, String>();

requestParams.put("struts_action", Validator.isNull(keywords) ? "/document_library/view" : "/document_library/search");
requestParams.put("navigation", HtmlUtil.escapeJS(navigation));
requestParams.put("folderId", String.valueOf(folderId));
requestParams.put("saveDisplayStyle", Boolean.TRUE.toString());
requestParams.put("searchType", String.valueOf(DLSearchConstants.FRAGMENT));
requestParams.put("viewEntriesPage", Boolean.TRUE.toString());
requestParams.put("viewFolders", Boolean.FALSE.toString());

if (Validator.isNull(keywords)) {
	requestParams.put("viewEntries", Boolean.TRUE.toString());
}
else {
	requestParams.put("keywords", HtmlUtil.escapeJS(keywords));
	requestParams.put("searchFolderId", String.valueOf(folderId));
	requestParams.put("viewEntries", Boolean.FALSE.toString());
}

if (fileEntryTypeId != -1) {
	requestParams.put("fileEntryTypeId", String.valueOf(fileEntryTypeId));
}
%>

<liferay-ui:app-view-display-style
	displayStyle="<%= displayStyle %>"
	displayStyles="<%= displayViews %>"
	requestParams="<%= requestParams %>"
/>