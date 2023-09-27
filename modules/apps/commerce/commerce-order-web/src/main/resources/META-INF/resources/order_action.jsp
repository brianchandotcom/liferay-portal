<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order" />
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
				<portlet:param name="mvcRenderCommandName" value="/commerce_order_content/view_commerce_order_details" />
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
		<portlet:actionURL name="/commerce_order/edit_commerce_order" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>