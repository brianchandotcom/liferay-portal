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

<%@ include file="/bookmark/init.jsp" %>

<%
Map<String, Object> data = new HashMap<>();

data.put("contentid", contentId);
%>

<liferay-ui:icon data="<%= data %>" label="<%= true %>" linkCssClass="social-bookmark" message="<%= socialBookmark.getName(locale) %>" method="get" url="<%= socialBookmark.getPostURL(title, url) %>" />