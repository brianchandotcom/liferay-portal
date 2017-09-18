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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.modern.site.building.fragment.constants.MSBFragmentActionKeys" %><%@
page import="com.liferay.modern.site.building.fragment.exception.DuplicateMSBFragmentCollectionException" %><%@
page import="com.liferay.modern.site.building.fragment.exception.MSBFragmentCollectionNameException" %><%@
page import="com.liferay.modern.site.building.fragment.model.MSBFragmentCollection" %><%@
page import="com.liferay.modern.site.building.fragment.model.MSBFragmentEntry" %><%@
page import="com.liferay.modern.site.building.fragment.service.permission.MSBFragmentCollectionPermission" %><%@
page import="com.liferay.modern.site.building.fragment.service.permission.MSBFragmentEntryPermission" %><%@
page import="com.liferay.modern.site.building.fragment.web.internal.display.context.MSBFragmentDisplayContext" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
MSBFragmentDisplayContext msbFragmentDisplayContext = new MSBFragmentDisplayContext(renderRequest, renderResponse, request);
%>

<%@ include file="/init-ext.jsp" %>