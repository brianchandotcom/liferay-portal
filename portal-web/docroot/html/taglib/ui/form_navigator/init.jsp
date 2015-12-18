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

<portlet:defineObjects />

<%
String backURL = (String)request.getAttribute("liferay-ui:form-navigator:backURL");
String[][] categorySectionKeys = (String[][])request.getAttribute("liferay-ui:form-navigator:categorySectionKeys");
String[][] categorySectionLabels = (String[][])request.getAttribute("liferay-ui:form-navigator:categorySectionLabels");
String[] categoryKeys = (String[])request.getAttribute("liferay-ui:form-navigator:categoryKeys");
String[] categoryLabels = (String[])request.getAttribute("liferay-ui:form-navigator:categoryLabels");
String[] deprecatedCategorySections = (String[])request.getAttribute("liferay-ui:form-navigator:deprecatedCategorySections");
String displayStyle = (String)request.getAttribute("liferay-ui:form-navigator:displayStyle");
Object formModelBean = request.getAttribute("liferay-ui:form-navigator:formModelBean");
String formName = GetterUtil.getString((String)request.getAttribute("liferay-ui:form-navigator:formName"));
String htmlBottom = (String)request.getAttribute("liferay-ui:form-navigator:htmlBottom");
String htmlTop = (String)request.getAttribute("liferay-ui:form-navigator:htmlTop");
String id = (String)request.getAttribute("liferay-ui:form-navigator:id");
String jspPath = (String)request.getAttribute("liferay-ui:form-navigator:jspPath");
boolean showButtons = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:form-navigator:showButtons"));

if (Validator.isNull(backURL)) {
	String redirect = ParamUtil.getString(request, "redirect");

	backURL = redirect;
}

PortletURL portletURL = liferayPortletResponse.createRenderURL();

if (Validator.isNull(backURL)) {
	backURL = portletURL.toString();
}

String curSection = StringPool.BLANK;

if (categorySectionKeys[0].length > 0) {
	curSection = categorySectionKeys[0][0];
}

String historyKey = ParamUtil.getString(request, "historyKey");

if (Validator.isNotNull(historyKey)) {
	curSection = historyKey;
}
%>

<%!
private String _getSectionId(String name) {
	return TextFormatter.format(name, TextFormatter.M);
}

private String _getSectionJsp(String name) {
	return TextFormatter.format(name, TextFormatter.N);
}
%>