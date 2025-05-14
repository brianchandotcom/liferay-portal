<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
Map<String, Object> contextObjects = (Map<String, Object>)request.getAttribute("liferay-ddm:template-renderer:contextObjects");
List<?> entries = (List<?>)request.getAttribute("liferay-ddm:template-renderer:entries");
%>

<%@ include file="/template_renderer/init-ext.jspf" %>