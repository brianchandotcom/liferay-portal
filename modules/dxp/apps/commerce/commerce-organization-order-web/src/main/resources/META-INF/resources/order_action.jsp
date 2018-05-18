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
CommerceOrganizationOrderDisplayContext commerceOrganizationOrderDisplayContext = (CommerceOrganizationOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceOrder commerceOrder = (CommerceOrder)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
	triggerCssClass="component-action"
>
	<c:if test="<%= commerceOrder.isOpen() && !commerceOrganizationOrderDisplayContext.isCurrentCommerceOrder(commerceOrder) %>">
		<portlet:actionURL name="editCommerceOrder" var="setCurrentURL">
			<portlet:param name="<%= Constants.CMD %>" value="setCurrent" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="set-as-current"
			url="<%= setCurrentURL %>"
		/>
	</c:if>

	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="editCommerceOrder" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view"
		url="<%= editURL %>"
	/>

	<c:if test="<%= commerceOrganizationOrderDisplayContext.hasPermission(commerceOrder, ActionKeys.DELETE) %>">
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