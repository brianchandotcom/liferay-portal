<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String bulletStyle = ((String)request.getAttribute("liferay-ui:navigation:bulletStyle")).toLowerCase();
String displayStyle = (String)request.getAttribute("liferay-ui:navigation:displayStyle");
boolean preview = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:navigation:preview"), false);

String headerType = null;
String includedLayouts = null;
boolean nestedChildren = true;
int rootLayoutLevel = 0;
String rootLayoutType = null;

String[] displayStyleDefinition = _getDisplayStyleDefinition(displayStyle);

if ((displayStyleDefinition != null) && (displayStyleDefinition.length != 0)) {
	headerType = displayStyleDefinition[0];
	includedLayouts = displayStyleDefinition[3];

	if (displayStyleDefinition.length > 4) {
		nestedChildren = GetterUtil.getBoolean(displayStyleDefinition[4]);
	}

	rootLayoutLevel = GetterUtil.getInteger(displayStyleDefinition[2]);
	rootLayoutType = displayStyleDefinition[1];
}
else {
	headerType = (String)request.getAttribute("liferay-ui:navigation:headerType");
	includedLayouts = (String)request.getAttribute("liferay-ui:navigation:includedLayouts");
	nestedChildren = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:navigation:nestedChildren"));
	rootLayoutLevel = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:navigation:rootLayoutLevel"));
	rootLayoutType = (String)request.getAttribute("liferay-ui:navigation:rootLayoutType");
}
%>

<%!
private String[] _getDisplayStyleDefinition(String displayStyle) {
	return PropsUtil.getArray("navigation.display.style", new Filter(displayStyle));
}
%>