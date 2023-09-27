<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SearchContainer<?> searchContainer = (SearchContainer)request.getAttribute("liferay-export-import-changeset:export-import-entity-search-container-mapping:searchContainer");
String searchContainerMappingId = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:export-import-entity-search-container-mapping:searchContainerMappingId"));
%>