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

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(DLPortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}

String keywords = ParamUtil.getString(request, "keywords");

int deltaEntry = ParamUtil.getInteger(request, "deltaEntry");

PortletURL displayStyleURL = renderResponse.createRenderURL();

displayStyleURL.setParameter("mvcRenderCommandName", Validator.isNull(keywords) ? "/document_library/view" : "/document_library/search");
displayStyleURL.setParameter("navigation", HtmlUtil.escapeJS(navigation));
displayStyleURL.setParameter("folderId", String.valueOf(folderId));

if (fileEntryTypeId != -1) {
	displayStyleURL.setParameter("fileEntryTypeId", String.valueOf(fileEntryTypeId));
}

if (deltaEntry > 0) {
	displayStyleURL.setParameter("deltaEntry", String.valueOf(deltaEntry));
}
%>

<liferay-ui:app-view-display-style
	displayStyle="<%= displayStyle %>"
	displayStyles="<%= dlPortletInstanceSettings.getDisplayViews() %>"
	displayStyleURL="<%= displayStyleURL %>"
/>