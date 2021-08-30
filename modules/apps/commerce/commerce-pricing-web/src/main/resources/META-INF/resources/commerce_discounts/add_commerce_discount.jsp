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

PortletURL editDiscountPortletURL = commerceDiscountDisplayContext.getEditCommerceDiscountRenderURL();
%>

<portlet:actionURL name="/commerce_discount/edit_commerce_discount" var="editCommerceDiscountActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-discount") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<aui:input bean="<%= commerceDiscountDisplayContext.getCommerceDiscount() %>" label="name" model="<%= CommerceDiscount.class %>" name="title" required="<%= true %>" />

		<aui:select label="type" name="commerceDiscountType" required="<%= true %>">

			<%
			for (String commerceDiscountType : CommerceDiscountConstants.TYPES) {
			%>

				<aui:option label="<%= commerceDiscountType %>" value="<%= commerceDiscountDisplayContext.getUsePercentage(commerceDiscountType) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="apply-to" name="commerceDiscountTarget" required="<%= true %>">

			<%
			for (CommerceDiscountTarget commerceDiscountTarget : commerceDiscountDisplayContext.getCommerceDiscountTargets()) {
			%>

				<aui:option label="<%= commerceDiscountTarget.getLabel(locale) %>" value="<%= commerceDiscountTarget.getKey() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>

	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
		var CommerceDiscountResource = ServiceProvider.default.AdminPricingAPI('v2');

		Liferay.provide(
			window,
			'<portlet:namespace />apiSubmit',
			(form) => {
				var commerceDiscountTarget = form.querySelector(
					'#commerceDiscountTarget'
				).value;

				var title = form.querySelector('#title').value;

				var commerceDiscountType = form.querySelector('#commerceDiscountType')
					.value;

				var discountData = {
					level: '<%= CommerceDiscountConstants.LEVEL_L1 %>',
					limitationType:
						'<%= CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED %>',
					target: commerceDiscountTarget,
					title: title,
					usePercentage: commerceDiscountType,
				};

				return CommerceDiscountResource.addDiscount(discountData)
					.then((payload) => {
						var redirectURL = new Liferay.PortletURL.createURL(
							'<%= editDiscountPortletURL.toString() %>'
						);

						redirectURL.setParameter('commerceDiscountId', payload.id);
						redirectURL.setParameter(
							'usePercentage',
							payload.usePercentage
						);
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