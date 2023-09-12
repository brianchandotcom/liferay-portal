<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/user" prefix="liferay-user" %>

<%@ page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-theme:defineObjects />

<%
String url = (String)request.getAttribute("liferay-user:user-display:url");
User userDisplay = (User)request.getAttribute("liferay-user:user-display:user");
String userName = GetterUtil.getString((String)request.getAttribute("liferay-user:user-display:userName"));
%>