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

CommerceNotificationTemplate commerceNotificationTemplate = (CommerceNotificationTemplate)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CommerceNotificationTemplatePermission.contains(permissionChecker, commerceNotificationTemplate, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="editCommerceNotificationTemplate" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceNotificationTemplateId" value="<%= String.valueOf(commerceNotificationTemplate.getCommerceNotificationTemplateId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= CommerceNotificationTemplatePermission.contains(permissionChecker, commerceNotificationTemplate, ActionKeys.DELETE) %>">
		<portlet:actionURL name="editCommerceNotificationTemplate" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceNotificationTemplateId" value="<%= String.valueOf(commerceNotificationTemplate.getCommerceNotificationTemplateId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>