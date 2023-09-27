<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderTypeDisplayContext commerceOrderTypeDisplayContext = (CommerceOrderTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderType commerceOrderType = commerceOrderTypeDisplayContext.getCommerceOrderType();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCommerceOrderTypeExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order_type/edit_commerce_order_type_external_reference_code" />
	<portlet:param name="commerceOrderTypeId" value="<%= String.valueOf(commerceOrderType.getCommerceOrderTypeId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceOrderTypeDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceOrderType %>"
	beanIdLabel="id"
	externalReferenceCode="<%= commerceOrderType.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommerceOrderTypeExternalReferenceCodeURL %>"
	model="<%= CommerceOrderType.class %>"
	title="<%= commerceOrderType.getName(locale) %>"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= CommerceOrderTypeScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_TYPE_GENERAL %>"
	modelBean="<%= commerceOrderType %>"
	portletURL="<%= currentURLObj %>"
/>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"workflowAction", WorkflowConstants.ACTION_PUBLISH
		).build()
	%>'
	module="js/editCommerceOrderType"
/>