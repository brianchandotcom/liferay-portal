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
CommerceOrderTypeDisplayContext commerceOrderTypeDisplayContext = (CommerceOrderTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_order_type/edit_commerce_order_type" var="editCommerceOrderTypeActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-order-type") %>'
>
	<aui:form method="post" name="fm">
		<aui:input bean="<%= commerceOrderTypeDisplayContext.getCommerceOrderType() %>" label="name" model="<%= CommerceOrderType.class %>" name="name" required="<%= true %>" />

		<aui:input name="description" type="textarea" />
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLanguageId", themeDisplay.getLanguageId()
			).put(
				"editCommerceOrderTypePortletURL", String.valueOf(commerceOrderTypeDisplayContext.getEditCommerceOrderTypeRenderURL())
			).build()
		%>'
		module="js/addCommerceOrderType"
	/>
</commerce-ui:modal-content>