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
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceDiscount commerceDiscount = (CommerceDiscount)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceDiscountDisplayContext.hasPermission(commerceDiscount.getCommerceDiscountId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="editCommerceDiscount" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceDiscountId" value="<%= String.valueOf(commerceDiscount.getCommerceDiscountId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= commerceDiscountDisplayContext.hasPermission(commerceDiscount.getCommerceDiscountId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="editCommerceDiscount" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceDiscountId" value="<%= String.valueOf(commerceDiscount.getCommerceDiscountId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>

	<c:if test="<%= commerceDiscountDisplayContext.hasPermission(commerceDiscount.getCommerceDiscountId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= CommerceDiscount.class.getName() %>"
			modelResourceDescription="<%= commerceDiscount.getTitle() %>"
			resourcePrimKey="<%= String.valueOf(commerceDiscount.getCommerceDiscountId()) %>"
			var="permissionsDiscountURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsDiscountURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>
</liferay-ui:icon-menu>