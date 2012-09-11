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

<%@ include file="/html/taglib/init.jsp" %>

<%
String bulletStyle = ((String)request.getAttribute("liferay-ui:sites-directory:bulletStyle")).toLowerCase();
String displayStyle = (String)request.getAttribute("liferay-ui:sites-directory:displayStyle");

String headerType = null;
String rootGroupType = null;
int rootGroupLevel = 0;
String includedGroups = null;
boolean nestedChildren = true;

String[] displayStyleDefinition = _getDisplayStyleDefinition(displayStyle);

if ((displayStyleDefinition != null) && (displayStyleDefinition.length != 0)) {
	headerType = displayStyleDefinition[0];
	rootGroupType = displayStyleDefinition[1];
	rootGroupLevel = GetterUtil.getInteger(displayStyleDefinition[2]);
	includedGroups = displayStyleDefinition[3];

	if (displayStyleDefinition.length > 4) {
		nestedChildren = GetterUtil.getBoolean(displayStyleDefinition[4]);
	}
}
else {
	headerType = (String)request.getAttribute("liferay-ui:sites-directory:headerType");
	rootGroupType = (String)request.getAttribute("liferay-ui:sites-directory:rootGroupType");
	rootGroupLevel = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:sites-directory:rootGroupLevel"));
	includedGroups = (String)request.getAttribute("liferay-ui:sites-directory:includedGroups");
	nestedChildren = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:sites-directory:nestedChildren"));
}

Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);
%>

<%!
private String[] _getDisplayStyleDefinition(String displayStyle) {
	return PropsUtil.getArray("sites.directory.display.style", new Filter(displayStyle));
}
%>