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

<%@ page import="com.liferay.frontend.taglib.internal.constants.FormNavigatorWebKeys" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryUtil" %><%@
page import="com.liferay.portal.kernel.util.SessionClicks" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.util.PortalIncludeUtil" %>

<%@ page import="java.io.IOException" %>

<%@ page import="java.util.Objects" %>

<%
String backURL = (String)request.getAttribute("liferay-frontend:form-navigator:backURL");
String[] categoryKeys = (String[])request.getAttribute("liferay-frontend:form-navigator:categoryKeys");
String fieldSetCssClass = (String)request.getAttribute("liferay-frontend:form-navigator:fieldSetCssClass");
Object formModelBean = request.getAttribute("liferay-frontend:form-navigator:formModelBean");
String id = (String)request.getAttribute("liferay-frontend:form-navigator:id");
boolean showButtons = GetterUtil.getBoolean((String)request.getAttribute("liferay-frontend:form-navigator:showButtons"));
%>

<%!
private String _getSectionId(String name) {
	return TextFormatter.format(name, TextFormatter.M);
}
%>