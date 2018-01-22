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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UADEntityTypeComposite uadEntityTypeComposite = (UADEntityTypeComposite)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:actionURL name="/users_admin/auto_anonymize_user_associated_data" var="autoAnonymizeURL">
		<portlet:param name="mvcActionCommand" value="/users_admin/auto_anonymize_user_associated_data" />
		<portlet:param name="key" value="<%= uadEntityTypeComposite.getKey() %>" />
		<portlet:param name="userId" value="<%= String.valueOf(uadEntityTypeComposite.getUserId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="auto-anonymize-all"
		url="<%= autoAnonymizeURL %>"
	/>

	<portlet:actionURL name="/users_admin/delete_user_associated_data" var="deleteURL">
		<portlet:param name="mvcActionCommand" value="/users_admin/delete_user_associated_data" />
		<portlet:param name="key" value="<%= uadEntityTypeComposite.getKey() %>" />
		<portlet:param name="userId" value="<%= String.valueOf(uadEntityTypeComposite.getUserId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="delete-all"
		url="<%= deleteURL %>"
	/>

	<portlet:actionURL name="/users_admin/export_user_associated_data" var="exportURL">
		<portlet:param name="mvcActionCommand" value="/users_admin/export_user_associated_data" />
		<portlet:param name="key" value="<%= uadEntityTypeComposite.getKey() %>" />
		<portlet:param name="userId" value="<%= String.valueOf(uadEntityTypeComposite.getUserId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="export-all"
		url="<%= exportURL %>"
	/>
</liferay-ui:icon-menu>