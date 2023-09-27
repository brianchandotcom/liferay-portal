<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String cmd = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:export-import-entity-management-bar-button:cmd"));
String searchContainerId = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:export-import-entity-management-bar-button:searchContainerId"));
String searchContainerMappingId = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:export-import-entity-management-bar-button:searchContainerMappingId"));
%>