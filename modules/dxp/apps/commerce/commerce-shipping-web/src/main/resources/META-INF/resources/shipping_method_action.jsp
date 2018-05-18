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

CommerceShippingMethod commerceShippingMethod = (CommerceShippingMethod)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CommercePermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS) %>">
		<portlet:actionURL name="editCommerceShippingMethod" var="editURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceShippingMethodId" value="<%= String.valueOf(commerceShippingMethod.getCommerceShippingMethodId()) %>" />
			<portlet:param name="engineKey" value="<%= commerceShippingMethod.getEngineKey() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:actionURL name="editCommerceShippingMethod" var="setActiveURL">
			<portlet:param name="<%= Constants.CMD %>" value="setActive" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceShippingMethodId" value="<%= String.valueOf(commerceShippingMethod.getCommerceShippingMethodId()) %>" />
			<portlet:param name="active" value="<%= String.valueOf(!commerceShippingMethod.getActive()) %>" />
			<portlet:param name="engineKey" value="<%= commerceShippingMethod.getEngineKey() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= (commerceShippingMethod.getActive()) ? LanguageUtil.get(request, "unset-as-active") : LanguageUtil.get(request, "set-as-active") %>'
			url="<%= setActiveURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>