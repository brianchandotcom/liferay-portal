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

<%@ include file="/html/portlet/mobile_device_rules/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MDRAction action = (MDRAction)row.getObject();
%>

<liferay-ui:icon-menu direction="down" extended="<%= false %>" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>" triggerCssClass="btn btn-default">
	<c:if test="<%= MDRRuleGroupInstancePermissionUtil.contains(permissionChecker, action.getRuleGroupInstanceId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editActionURL">
			<portlet:param name="struts_action" value="/mobile_device_rules/edit_action" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="actionId" value="<%= String.valueOf(action.getActionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editActionURL %>"
		/>

		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/mobile_device_rules/edit_action" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="actionId" value="<%= String.valueOf(action.getActionId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL.toString() %>" />
	</c:if>
</liferay-ui:icon-menu>