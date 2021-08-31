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
CommercePricingClassDisplayContext commercePricingClassDisplayContext = (CommercePricingClassDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePricingClass commercePricingClass = commercePricingClassDisplayContext.getCommercePricingClass();

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}

portletDisplay.setShowBackIcon(true);
%>

<liferay-portlet:renderURL var="editCommercePricingClassExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_pricing_classes/edit_commerce_pricing_class_external_reference_code" />
	<portlet:param name="commercePricingClassId" value="<%= String.valueOf(commercePricingClass.getCommercePricingClassId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commercePricingClassDisplayContext.getHeaderActionModels() %>"
	bean="<%= commercePricingClass %>"
	beanIdLabel="id"
	externalReferenceCode="<%= commercePricingClass.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommercePricingClassExternalReferenceCodeURL %>"
	model="<%= CommercePricingClass.class %>"
	title="<%= commercePricingClass.getTitle(locale) %>"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= CommercePricingClassScreenNavigationConstants.SCREEN_NAVIGATION_KEY_PRICING_CLASS_GENERAL %>"
	modelBean="<%= commercePricingClass %>"
	portletURL="<%= currentURLObj %>"
/>