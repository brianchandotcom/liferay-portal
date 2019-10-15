<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

OAuthApplication oAuthApplication = (OAuthApplication)row.getObject();
%>

<liferay-ui:icon-menu
	icon="<%= StringPool.BLANK %>"
	message="<%= StringPool.BLANK %>"
>
	<liferay-portlet:renderURL var="viewURL">
		<portlet:param name="mvcPath" value="/admin/view_application.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="oAuthApplicationId" value="<%= String.valueOf(oAuthApplication.getOAuthApplicationId()) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		iconCssClass="icon-search"
		message="view"
		method="get"
		url="<%= viewURL %>"
	/>

	<c:if test="<%= OAuthApplicationPermission.contains(permissionChecker, oAuthApplication, OAuthActionKeys.UPDATE) %>">
		<liferay-portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/admin/edit_application.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="oAuthApplicationId" value="<%= String.valueOf(oAuthApplication.getOAuthApplicationId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			method="get"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= OAuthApplicationPermission.contains(permissionChecker, oAuthApplication, OAuthActionKeys.DELETE) %>">
		<liferay-portlet:actionURL name="deleteOAuthApplication" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="oAuthApplicationId" value="<%= String.valueOf(oAuthApplication.getOAuthApplicationId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>