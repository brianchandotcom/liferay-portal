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
CommerceOrderRuleEntryDisplayContext commerceOrderRuleEntryDisplayContext = (CommerceOrderRuleEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderRuleEntry commerceOrderRuleEntry = commerceOrderRuleEntryDisplayContext.getCommerceOrderRuleEntry();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCommerceOrderRuleEntryExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order_rule_entry/edit_commerce_order_rule_entry_external_reference_code" />
	<portlet:param name="commerceOrderRuleEntryId" value="<%= String.valueOf(commerceOrderRuleEntry.getCommerceOrderRuleEntryId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceOrderRuleEntryDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceOrderRuleEntry %>"
	beanIdLabel="id"
	externalReferenceCode="<%= commerceOrderRuleEntry.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommerceOrderRuleEntryExternalReferenceCodeURL %>"
	model="<%= CommerceOrderRuleEntry.class %>"
	title="<%= commerceOrderRuleEntry.getName() %>"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= CommerceOrderRuleEntryScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_RULE_ENTRY_GENERAL %>"
	modelBean="<%= commerceOrderRuleEntry %>"
	portletURL="<%= currentURLObj %>"
/>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"workflowAction", WorkflowConstants.ACTION_PUBLISH
		).build()
	%>'
	module="js/editCommerceOrderRuleEntry"
/>