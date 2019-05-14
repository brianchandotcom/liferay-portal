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

<%@ include file="/html/common/init.jsp" %>

<%
boolean author = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:user-display:author"));
int displayStyle = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:user-display:displayStyle"));
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