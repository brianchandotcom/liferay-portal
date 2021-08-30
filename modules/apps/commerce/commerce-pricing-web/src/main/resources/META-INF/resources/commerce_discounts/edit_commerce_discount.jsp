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

CommerceDiscount commerceDiscount = commerceDiscountDisplayContext.getCommerceDiscount();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCommerceDiscountExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_discount/edit_commerce_discount_external_reference_code" />
	<portlet:param name="commerceDiscountId" value="<%= String.valueOf(commerceDiscount.getCommerceDiscountId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceDiscountDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceDiscount %>"
	beanIdLabel="id"
	externalReferenceCode="<%= commerceDiscount.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommerceDiscountExternalReferenceCodeURL %>"
	model="<%= CommerceDiscount.class %>"
	title="<%= commerceDiscount.getTitle() %>"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= CommerceDiscountScreenNavigationConstants.SCREEN_NAVIGATION_KEY_DISCOUNT_GENERAL %>"
	modelBean="<%= commerceDiscount %>"
	portletURL="<%= currentURLObj %>"
/>

<aui:script>
	document
		.getElementById('<portlet:namespace />publishButton')
		.addEventListener('click', (e) => {
			e.preventDefault();

			var form = document.getElementById('<portlet:namespace />fm');

			if (!form) {
				throw new Error('Form with id: <portlet:namespace />fm not found!');
			}

			var workflowActionInput = document.getElementById(
				'<portlet:namespace />workflowAction'
			);

			if (workflowActionInput) {
				workflowActionInput.value =
					'<%= WorkflowConstants.ACTION_PUBLISH %>';
			}

			submitForm(form);
		});
</aui:script>