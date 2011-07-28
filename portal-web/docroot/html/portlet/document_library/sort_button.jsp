<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");
%>

<liferay-ui:icon-menu align="left" direction="down" icon="" message="sort-by" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">

	<%
	String taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('title')";
	%>

	<liferay-ui:icon
		image="folder"
		message="title"
		url="<%= taglibUrl %>"
	/>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('creationDate')";
	%>

	<liferay-ui:icon
		image="folder"
		message="create-date"
		url="<%= taglibUrl %>"
	/>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('modifiedDate')";
	%>

	<liferay-ui:icon
		image="folder"
		message="modified-date"
		url="<%= taglibUrl %>"
	/>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('readCount')";
	%>

	<liferay-ui:icon
		image="folder"
		message="read-count"
		url="<%= taglibUrl %>"
	/>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('size')";
	%>

	<liferay-ui:icon
		image="folder"
		message="size"
		url="<%= taglibUrl %>"
	/>
</liferay-ui:icon-menu>

<aui:script use="aui-base">
	var documentLibraryContainer = A.one('#<portlet:namespace />documentLibraryContainer');

	Liferay.provide(
		window,
		'<portlet:namespace />sortEntries',
		function (orderByCol) {
			var config = {
				'<portlet:namespace />struts_action': '/document_library/view',
				'<portlet:namespace />viewEntries': <%= Boolean.TRUE.toString() %>,
				'<portlet:namespace />viewSortButton': <%= Boolean.TRUE.toString() %>,
				'<portlet:namespace />orderByCol': orderByCol,
				'<portlet:namespace />orderByType': '<%= (orderByCol.equals("title") && orderByType.equals("asc")) ? "desc" : "asc" %>'
			};

			Liferay.fire(
				'<portlet:namespace />dataRequest',
				{
					requestParams: config
				}
			);
		},
		['aui-base']
	);
</aui:script>