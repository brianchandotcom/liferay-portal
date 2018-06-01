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
String icon = PropsUtil.get(PropsKeys.SOCIAL_BOOKMARK_ICON, new Filter(type));
%>

<liferay-ui:icon
	image="<%= icon %>"
	label="<%= false %>"
	linkCssClass="btn btn-borderless btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
	message="<%= socialBookmark.getName(locale) %>"
	method="get"
	src="<%= icon %>"
	url="<%= socialBookmark.getPostURL(title, url) %>"
/>