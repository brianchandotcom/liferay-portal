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

PortletURL editPricingClassPortletURL = commercePricingClassDisplayContext.getEditCommercePricingClassRenderURL();

Locale defaultLocale = LocaleUtil.getSiteDefault();

String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
%>

<portlet:actionURL name="/commerce_pricing_classes/edit_commerce_pricing_class" var="editCommercePricingClassActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-product-group") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<aui:input label="name" name="title" required="<%= true %>" />

		<aui:input name="description" type="textarea" />
	</aui:form>

	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
		var CommerceProductGroupsResource = ServiceProvider.default.AdminCatalogAPI(
			'v1'
		);

		Liferay.provide(
			window,
			'<portlet:namespace />apiSubmit',
			(form) => {
				var description = form.querySelector('#description').value;
				var title = form.querySelector('#title').value;

				var productGroupData = {
					description: {<%= defaultLanguageId %>: description},
					title: {<%= defaultLanguageId %>: title},
				};

				return CommerceProductGroupsResource.addProductGroup(productGroupData)
					.then((payload) => {
						var redirectURL = new Liferay.PortletURL.createURL(
							'<%= editPricingClassPortletURL.toString() %>'
						);

						redirectURL.setParameter('commercePricingClassId', payload.id);
						redirectURL.setParameter('p_auth', Liferay.authToken);

						window.parent.Liferay.fire(events.CLOSE_MODAL, {
							redirectURL: redirectURL.toString(),
							successNotification: {
								message:
									'<liferay-ui:message key="your-request-completed-successfully" />',
								showSuccessNotification: true,
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