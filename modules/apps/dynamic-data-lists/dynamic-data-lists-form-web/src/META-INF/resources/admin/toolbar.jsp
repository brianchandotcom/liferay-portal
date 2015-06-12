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

<%@ include file="/admin/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute(WebKeys.SEARCH_CONTAINER);
%>

<aui:nav-bar id="toolbar">
	<aui:nav cssClass="navbar-nav" searchContainer="<%= searchContainer %>">
		<c:if test="<%= ddlFormAdminDisplayContext.isShowAddRecordSetButton() %>">
			<aui:nav-item dropdown="<%= true %>" id="addButtonContainer" label="add">
				<portlet:renderURL var="addRecordSetURL">
					<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
				</portlet:renderURL>

				<aui:nav-item cssClass="add-form" href="<%= addRecordSetURL %>" iconCssClass="icon-plus" label="new-form" />
			</aui:nav-item>
		</c:if>
	</aui:nav>

	<aui:nav-bar-search>
		<liferay-util:include page="/admin/record_set_search.jsp" servletContext="<%= application %>" />
	</aui:nav-bar-search>
</aui:nav-bar>