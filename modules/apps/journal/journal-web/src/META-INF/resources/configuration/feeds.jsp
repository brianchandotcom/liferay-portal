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

<%@ include file="/init.jsp" %>

<%
String taglibOnClick = renderResponse.getNamespace() + "openFeedsView()";
%>

<liferay-ui:icon
	iconCssClass="icon-rss"
	message="feeds"
	onClick="<%= taglibOnClick %>"
	url="javascript:;"
/>

<aui:script>
	function <portlet:namespace />openFeedsView() {
		Liferay.Util.openWindow(
			{
				id: '<portlet:namespace />openFeedsView',
				title: '<%= UnicodeLanguageUtil.get(request, "feeds") %>',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/view_feeds.jsp" /></liferay-portlet:renderURL>'
			}
		);
	}
</aui:script>