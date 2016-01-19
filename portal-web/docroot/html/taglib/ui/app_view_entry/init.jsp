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

<%@ include file="/html/taglib/init.jsp" %>

<%
String actionJsp = (String)request.getAttribute("liferay-ui:app-view-entry:actionJsp");
ServletContext actionJspServletContext = (ServletContext)request.getAttribute("liferay-ui:app-view-entry:actionJspServletContext");
String assetCategoryClassName = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:assetCategoryClassName"));
long assetCategoryClassPK = GetterUtil.getLong(request.getAttribute("liferay-ui:app-view-entry:assetCategoryClassPK"));
String assetTagClassName = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:assetTagClassName"));
long assetTagClassPK = GetterUtil.getLong(request.getAttribute("liferay-ui:app-view-entry:assetTagClassPK"));
String author = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:author"));
String classTypeName = (String)request.getAttribute("liferay-ui:app-view-entry:classTypeName");
Date createDate = GetterUtil.getDate(request.getAttribute("liferay-ui:app-view-entry:createDate"), DateFormatFactoryUtil.getDate(locale), null);
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:app-view-entry:cssClass"));
Date expirationDate = GetterUtil.getDate(request.getAttribute("liferay-ui:app-view-entry:expirationDate"), DateFormatFactoryUtil.getDate(locale), null);
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:app-view-entry:data");
String description = (String)request.getAttribute("liferay-ui:app-view-entry:description");
Date displayDate = GetterUtil.getDate(request.getAttribute("liferay-ui:app-view-entry:displayDate"), DateFormatFactoryUtil.getDate(locale), null);
String displayStyle = (String)request.getAttribute("liferay-ui:app-view-entry:displayStyle");
boolean folder = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:folder"));
long groupId = GetterUtil.getLong(request.getAttribute("liferay-ui:app-view-entry:groupId"));
String iconCssClass = (String)request.getAttribute("liferay-ui:app-view-entry:iconCssClass");
String latestApprovedVersion = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:latestApprovedVersion"));
String latestApprovedVersionAuthor = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:latestApprovedVersionAuthor"));
boolean locked = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:locked"));
Date modifiedDate = GetterUtil.getDate(request.getAttribute("liferay-ui:app-view-entry:modifiedDate"), DateFormatFactoryUtil.getDate(locale), null);
Date reviewDate = GetterUtil.getDate(request.getAttribute("liferay-ui:app-view-entry:reviewDate"), DateFormatFactoryUtil.getDate(locale), null);
String rowCheckerId = (String)request.getAttribute("liferay-ui:app-view-entry:rowCheckerId");
String rowCheckerName = (String)request.getAttribute("liferay-ui:app-view-entry:rowCheckerName");
boolean shortcut = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:shortcut"));
boolean showCheckbox = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:showCheckbox"));
boolean showLinkTitle = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-entry:showLinkTitle"));
int status = GetterUtil.getInteger(request.getAttribute("liferay-ui:app-view-entry:status"), WorkflowConstants.STATUS_ANY);
String thumbnailDivStyle = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailDivStyle");
String thumbnailSrc = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailSrc");
String thumbnailStyle = (String)request.getAttribute("liferay-ui:app-view-entry:thumbnailStyle");
String title = (String)request.getAttribute("liferay-ui:app-view-entry:title");
String url = (String)request.getAttribute("liferay-ui:app-view-entry:url");
String version = GetterUtil.getString(request.getAttribute("liferay-ui:app-view-entry:version"));

String shortTitle = StringUtil.shorten(title, 60);

String linkTitle = StringPool.BLANK;

if (showLinkTitle) {
	linkTitle = HtmlUtil.escapeAttribute(title);

	if (Validator.isNotNull(description)) {
		linkTitle += HtmlUtil.escapeAttribute(" - " + description);
	}
}
%>