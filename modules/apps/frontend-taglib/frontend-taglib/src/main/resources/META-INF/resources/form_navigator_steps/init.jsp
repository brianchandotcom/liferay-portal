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
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.util.PortalIncludeUtil" %>

<%@ page import="java.util.ArrayList" %>

<%
String backURL = (String)request.getAttribute("liferay-frontend:form-navigator-steps:backURL");
String[][] categorySectionKeys = (String[][])request.getAttribute("liferay-frontend:form-navigator-steps:categorySectionKeys");
String[][] categorySectionLabels = (String[][])request.getAttribute("liferay-frontend:form-navigator-steps:categorySectionLabels");
String[] categoryKeys = (String[])request.getAttribute("liferay-frontend:form-navigator-steps:categoryKeys");
String[] categoryLabels = (String[])request.getAttribute("liferay-frontend:form-navigator-steps:categoryLabels");
Object formModelBean = request.getAttribute("liferay-frontend:form-navigator-steps:formModelBean");
String formName = GetterUtil.getString((String)request.getAttribute("liferay-frontend:form-navigator-steps:formName"));
String htmlBottom = (String)request.getAttribute("liferay-frontend:form-navigator-steps:htmlBottom");
String htmlTop = (String)request.getAttribute("liferay-frontend:form-navigator-steps:htmlTop");
String id = (String)request.getAttribute("liferay-frontend:form-navigator-steps:id");
boolean showButtons = GetterUtil.getBoolean((String)request.getAttribute("liferay-frontend:form-navigator-steps:showButtons"));

if (Validator.isNull(backURL)) {
	backURL = ParamUtil.getString(request, "redirect");
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
%>