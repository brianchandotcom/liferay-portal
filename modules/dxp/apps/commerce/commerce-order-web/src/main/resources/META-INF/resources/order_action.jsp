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

CommerceOrder commerceOrder = (CommerceOrder)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:choose>
		<c:when test="<%= CommerceOrderPermission.contains(permissionChecker, commerceOrder, ActionKeys.UPDATE) %>">
			<portlet:renderURL var="editURL">
				<portlet:param name="mvcRenderCommandName" value="editCommerceOrder" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				message="edit"
				url="<%= editURL %>"
			/>
		</c:when>
		<c:otherwise>
			<portlet:renderURL var="viewCommerceOrderDetailURL">
				<portlet:param name="mvcRenderCommandName" value="viewCommerceOrderDetail" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				message="view"
				url="<%= viewCommerceOrderDetailURL %>"
			/>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= CommerceOrderPermission.contains(permissionChecker, commerceOrder, ActionKeys.DELETE) %>">
		<portlet:actionURL name="editCommerceOrder" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>