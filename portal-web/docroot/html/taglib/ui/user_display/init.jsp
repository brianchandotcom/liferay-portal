<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/common/init.jsp" %>

<%
boolean author = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:user-display:author"));
String imageCssClass = (String)request.getAttribute("liferay-ui:user-display:imageCssClass");
boolean showLink = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:user-display:showLink"));
boolean showUserDetails = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:user-display:showUserDetails"));
boolean showUserName = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:user-display:showUserName"));
String url = (String)request.getAttribute("liferay-ui:user-display:url");
User userDisplay = (User)request.getAttribute("liferay-ui:user-display:user");
String userName = GetterUtil.getString((String)request.getAttribute("liferay-ui:user-display:userName"));

if (author) {
	imageCssClass += " author";
}
%>