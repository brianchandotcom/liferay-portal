<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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