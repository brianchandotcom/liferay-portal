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
CommerceAccountAdminDisplayContext commerceAccountAdminDisplayContext = (CommerceAccountAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceAccount commerceAccount = (CommerceAccount)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceAccountAdminDisplayContext.hasPermission(commerceAccount.getCommerceAccountId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="editCommerceAccount" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceAccountId" value="<%= String.valueOf(commerceAccount.getCommerceAccountId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:actionURL name="editCommerceAccount" var="setActiveURL">
			<portlet:param name="<%= Constants.CMD %>" value="setActive" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceAccountId" value="<%= String.valueOf(commerceAccount.getCommerceAccountId()) %>" />
			<portlet:param name="active" value="<%= String.valueOf(!commerceAccount.isActive()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= commerceAccount.isActive() ? "deactivate" : "activate" %>'
			url="<%= setActiveURL %>"
		/>
	</c:if>

	<c:if test="<%= commerceAccountAdminDisplayContext.hasPermission(commerceAccount.getCommerceAccountId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="editCommerceAccount" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceAccountId" value="<%= String.valueOf(commerceAccount.getCommerceAccountId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>