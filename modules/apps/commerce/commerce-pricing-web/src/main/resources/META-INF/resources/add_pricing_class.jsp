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

<portlet:actionURL name="editCommercePricingClass" var="editCommercePricingClassActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-product-group") %>'
>
	<div class="col-12 lfr-form-content">
		<aui:form cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
			<aui:input name="title" required="<%= true %>" />

			<aui:input name="description" type="textarea" />
		</aui:form>

		<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils">
			var headers = new Headers({
				Accept: 'application/json',
				'Content-Type': 'application/json',
				'x-csrf-token': Liferay.authToken
			});

			Liferay.provide(
				window,
				'<portlet:namespace/>apiSubmit',
				function(form) {
					var API_URL = '/o/headless-commerce-admin-catalog/v1.0/product-groups';

					window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
						isLoading: true
					});

					var title = form.querySelector('#title').value;
					var description = form.querySelector('#description').value;

					return fetch(API_URL, {
						body: JSON.stringify({
							title: {'<%= defaultLanguageId %>': title},
							description: {'<%= defaultLanguageId %>': description}
						}),
						credentials: 'include',
						headers: headers,
						method: 'POST'
					})
						.then(function(response) {
							if (response.ok) {
								return response.json();
							}

							return response.json().then(function(data) {
								return Promise.reject(data.message);
							});
						})
						.then(function(payload) {
							var redirectURL = new Liferay.PortletURL.createURL(
								'<%= editPricingClassPortletURL.toString() %>'
							);

							redirectURL.setParameter('commercePricingClassId', payload.id);
							redirectURL.setParameter('p_auth', Liferay.authToken);

							window.parent.Liferay.fire(events.CLOSE_MODAL, {
								redirectURL: redirectURL.toString(),
								successNotification: {
									showSuccessNotification: true,
									message:
										'<liferay-ui:message key="your-request-completed-successfully" />'
								}
							});
						})
						.catch(function() {
							window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
								isLoading: false
							});

							new Liferay.Notification({
								closeable: true,
								delay: {
									hide: 5000,
									show: 0
								},
								duration: 500,
								message:
									'<liferay-ui:message key="an-unexpected-error-occurred" />',
								render: true,
								title: '<liferay-ui:message key="danger" />',
								type: 'danger'
							});
						});
				},
				['liferay-portlet-url']
			);
		</aui:script>
	</div>
</commerce-ui:modal-content>