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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL);

String orderByType = ParamUtil.getString(request, "orderByType");

String reverseOrderByType = "asc";

if (orderByType.equals("asc")) {
	reverseOrderByType = "desc";
}
%>

<aui:nav-item dropdown="<%= true %>" id="sortButtonContainer" label="sort-by">

	<%
	String taglibURL = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'title','" + reverseOrderByType + "')";
	%>

	<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-calendar" label="title" />

	<%
	taglibURL = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'creationDate','" + reverseOrderByType + "')";
	%>

	<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-calendar" label="create-date" />

	<%
	taglibURL = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'modifiedDate','" + reverseOrderByType + "')";
	%>

	<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-calendar" label="modified-date" />

	<%
	taglibURL = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'downloads','" + reverseOrderByType + "')";
	%>

	<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-calendar" label="downloads" />

	<%
	taglibURL = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'size','" + reverseOrderByType + "')";
	%>

	<aui:nav-item href="<%= taglibURL %>" iconCssClass="icon-calendar" label="size" />
</aui:nav-item>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />sortEntries',
		function(folderId, orderByCol, reverseOrderByType) {
			Liferay.fire(
				'<portlet:namespace />dataRequest',
				{
					requestParams: {
						'<portlet:namespace />folderId': folderId,
						'<portlet:namespace />navigation': '<%= HtmlUtil.escape(navigation) %>',
						'<portlet:namespace />struts_action': '/document_library/view',
						'<portlet:namespace />fileEntryTypeId': <%= fileEntryTypeId %>,
						'<portlet:namespace />viewEntries': <%= Boolean.FALSE.toString() %>,
						'<portlet:namespace />viewEntriesPage': <%= Boolean.TRUE.toString() %>,
						'<portlet:namespace />viewFolders': <%= Boolean.FALSE.toString() %>,
						'<portlet:namespace />orderByCol': orderByCol,
						'<portlet:namespace />orderByType': reverseOrderByType,
						'<portlet:namespace />saveOrderBy': <%= Boolean.TRUE.toString() %>
					}
				}
			);
		},
		['aui-base']
	);
</aui:script>