<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
String displayType = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-renderer:displayType"));
%>

<%@ include file="/data_layout_renderer/init-ext.jspf" %>