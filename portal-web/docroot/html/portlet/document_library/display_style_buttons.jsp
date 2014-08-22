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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

String folderId = (String)request.getAttribute("view.jsp-folderId");

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}

String keywords = ParamUtil.getString(request, "keywords");

PortletURL displayStyleUrl = renderResponse.createRenderURL();

displayStyleUrl.setParameter("struts_action", Validator.isNull(keywords) ? "/document_library/view" : "/document_library/search");
displayStyleUrl.setParameter("navigation", HtmlUtil.escapeJS(navigation));
displayStyleUrl.setParameter("folderId", folderId);

if(fileEntryTypeId != -1){
	displayStyleUrl.setParameter("fileEntryTypeId", String.valueOf(fileEntryTypeId));
}

%>

<liferay-ui:app-view-display-style
	displayStyle="<%= displayStyle %>"
	displayStyles="<%= dlPortletInstanceSettings.getDisplayViews() %>"
	displayStyleUrl="<%= displayStyleUrl %>"
/>