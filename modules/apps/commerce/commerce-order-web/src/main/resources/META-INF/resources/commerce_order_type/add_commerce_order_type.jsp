<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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