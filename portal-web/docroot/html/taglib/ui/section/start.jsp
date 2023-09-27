<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:section:data");
String name = (String)request.getAttribute("liferay-ui:section:name");
boolean selected = (Boolean)request.getAttribute("liferay-ui:section:selected");
%>

<div class="<%= selected ? StringPool.BLANK : "hide" %>" <%= AUIUtil.buildData(data) %> id="<%= namespace %><%= (String)request.getAttribute("liferay-ui:section:param") %><%= StringUtil.toCharCode(name) %>TabsSection">