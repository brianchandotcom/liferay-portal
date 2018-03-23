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

<%@ page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.SessionClicks" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.util.PortalIncludeUtil" %>

<%@ page import="java.io.IOException" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Objects" %>

<portlet:defineObjects />

<%
String backURL = (String)request.getAttribute("liferay-frontend:form-navigator:backURL");
String[][] categorySectionKeys = (String[][])request.getAttribute("liferay-frontend:form-navigator:categorySectionKeys");
String[][] categorySectionLabels = (String[][])request.getAttribute("liferay-frontend:form-navigator:categorySectionLabels");
String[] categoryKeys = (String[])request.getAttribute("liferay-frontend:form-navigator:categoryKeys");
String[] categoryLabels = (String[])request.getAttribute("liferay-frontend:form-navigator:categoryLabels");
String[] deprecatedCategorySections = (String[])request.getAttribute("liferay-frontend:form-navigator:deprecatedCategorySections");
Object formModelBean = request.getAttribute("liferay-frontend:form-navigator:formModelBean");
String id = (String)request.getAttribute("liferay-frontend:form-navigator:id");
String jspPath = (String)request.getAttribute("liferay-frontend:form-navigator:jspPath");
boolean showButtons = GetterUtil.getBoolean((String)request.getAttribute("liferay-frontend:form-navigator:showButtons"));

if (Validator.isNull(backURL)) {
	String redirect = ParamUtil.getString(request, "redirect");

	backURL = redirect;
}

PortletURL portletURL = liferayPortletResponse.createRenderURL();

if (Validator.isNull(backURL)) {
	backURL = portletURL.toString();
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