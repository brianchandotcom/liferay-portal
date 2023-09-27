<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/designer/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)row.getObject();

String userName = kaleoDesignerDisplayContext.getUserNameOrBlank(kaleoDefinitionVersion);
%>

<div class="list-group-title"><%= dateFormatTime.format(kaleoDefinitionVersion.getModifiedDate()) %></div>
<div class="list-group-subtitle"><%= HtmlUtil.escape(userName) %></div>