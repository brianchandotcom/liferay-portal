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

PortletURL editCommerceOrderTypePortletURL = commerceOrderTypeDisplayContext.getEditCommerceOrderTypeRenderURL();

String defaultLanguageId = LocaleUtil.toLanguageId(locale);
%>

<portlet:actionURL name="/commerce_order_type/edit_commerce_order_type" var="editCommerceOrderTypeActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-order-type") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<aui:input bean="<%= commerceOrderTypeDisplayContext.getCommerceOrderType() %>" label="name" model="<%= CommerceOrderType.class %>" name="name" required="<%= true %>" />

		<aui:input name="description" type="textarea" />
	</aui:form>

	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
		var CommerceOrderTypeResource = ServiceProvider.default.AdminOrderAPI('v1');

		Liferay.provide(
			window,
			'<portlet:namespace />apiSubmit',
			(form) => {
				var description = form.querySelector('#description').value;
				var name = form.querySelector('#name').value;

				var orderTypeData = {
					description: {<%= defaultLanguageId %>: description},
					name: {<%= defaultLanguageId %>: name},
				};

				return CommerceOrderTypeResource.addOrderType(orderTypeData)
					.then((payload) => {
						var redirectURL = new Liferay.PortletURL.createURL(
							'<%= editCommerceOrderTypePortletURL.toString() %>'
						);

						redirectURL.setParameter('commerceOrderTypeId', payload.id);
						redirectURL.setParameter('p_auth', Liferay.authToken);

						window.parent.Liferay.fire(events.CLOSE_MODAL, {
							redirectURL: redirectURL.toString(),
							successNotification: {
								showSuccessNotification: true,
								message:
									'<liferay-ui:message key="your-request-completed-successfully" />',
							},
						});
					})
					.catch((error) => {
						return Promise.reject(error);
					});
			},
			['liferay-portlet-url']
		);
	</aui:script>
</commerce-ui:modal-content>