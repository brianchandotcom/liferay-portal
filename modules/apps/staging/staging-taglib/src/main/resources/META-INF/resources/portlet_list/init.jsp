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

<%@ include file="/init.jsp" %>

<%
boolean disableInputs = GetterUtil.getBoolean(request.getAttribute("liferay-staging:portlet-list:disableInputs"));
Map<String, String[]> parameterMap = (Map<String, String[]>)GetterUtil.getObject(request.getAttribute("liferay-staging:content:parameterMap"), Collections.emptyMap());
List<Portlet> portlets = (List<Portlet>)GetterUtil.getObject(request.getAttribute("liferay-staging:portlet-list:portlets"), Collections.emptyList());
String type = GetterUtil.getString(request.getAttribute("liferay-staging:portlet-list:type"));

Set<String> displayedControls = new HashSet<String>();
Set<String> portletDataHandlerClasses = new HashSet<String>();

String defaultRange = null;
long exportGroupId = groupId;

if (type.equals(Constants.EXPORT)) {
	defaultRange = ExportImportDateUtil.RANGE_ALL;

	if (liveGroupId > 0) {
		exportGroupId = liveGroupId;
	}
}
else {
	defaultRange = ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE;

	if (stagingGroupId > 0) {
		exportGroupId = stagingGroupId;
	}
}
%>