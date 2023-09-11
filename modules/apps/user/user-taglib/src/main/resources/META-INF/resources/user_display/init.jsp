<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/user" prefix="liferay-user" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-theme:defineObjects />

<%
boolean author = GetterUtil.getBoolean((String)request.getAttribute("liferay-user:user-display:author"));
String imageCssClass = (String)request.getAttribute("liferay-user:user-display:imageCssClass");
boolean showLink = GetterUtil.getBoolean((String)request.getAttribute("liferay-user:user-display:showLink"));
boolean showUserDetails = GetterUtil.getBoolean((String)request.getAttribute("liferay-user:user-display:showUserDetails"));
boolean showUserName = GetterUtil.getBoolean((String)request.getAttribute("liferay-user:user-display:showUserName"));
String url = (String)request.getAttribute("liferay-user:user-display:url");
User userDisplay = (User)request.getAttribute("liferay-user:user-display:user");
String userName = GetterUtil.getString((String)request.getAttribute("liferay-user:user-display:userName"));

if (author) {
	imageCssClass += " author";
}
%>