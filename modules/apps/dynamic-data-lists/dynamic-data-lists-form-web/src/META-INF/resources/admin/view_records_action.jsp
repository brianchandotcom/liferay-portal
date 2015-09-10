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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLRecord record = (DDLRecord)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>" view="lexicon">
	<portlet:renderURL var="viewURL">
		<portlet:param name="mvcPath" value="/" />
		<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view"
		url="<%= viewURL %>"
	/>

	<c:if test="<%= ddlFormAdminDisplayContext.isShowDeleteRecordSetIcon(ddlFormAdminDisplayContext.getRecordSet()) %>">
		<portlet:actionURL name="deleteRecord" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="delete"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>