<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/bookmark/init.jsp" %>

<%
String icon = PropsUtil.get(PropsKeys.SOCIAL_BOOKMARK_ICON, new Filter(type));
%>

<liferay-ui:icon
	image="<%= icon %>"
	label="<%= false %>"
	linkCssClass="btn btn-borderless btn-outline-borderless btn-outline-secondary btn-sm c-px-2"
	message="<%= socialBookmark.getName(locale) %>"
	method="get"
	src="<%= icon %>"
	url="<%= socialBookmark.getPostURL(title, url) %>"
/>