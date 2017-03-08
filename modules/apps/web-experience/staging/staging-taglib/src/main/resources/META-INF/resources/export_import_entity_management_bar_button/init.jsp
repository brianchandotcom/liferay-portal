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

<%@ page import="com.liferay.exportimport.constants.ExportImportPortletKeys" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %>

<%
String cmd = GetterUtil.getString(request.getAttribute("liferay-staging:export-import-entity-management-bar-button:cmd"));
String searchContainerId = GetterUtil.getString(request.getAttribute("liferay-staging:export-import-entity-management-bar-button:searchContainerId"));
String searchContainerMappingId = GetterUtil.getString(request.getAttribute("liferay-staging:export-import-entity-management-bar-button:searchContainerMappingId"));
%>