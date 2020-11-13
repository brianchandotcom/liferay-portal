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
CommerceChannelDisplayContext commerceChannelDisplayContext = (CommerceChannelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceChannel commerceChannel = commerceChannelDisplayContext.getCommerceChannel();
long commerceChannelId = commerceChannelDisplayContext.getCommerceChannelId();
List<CommerceChannelType> commerceChannelTypes = commerceChannelDisplayContext.getCommerceChannelTypes();
List<CommerceCurrency> commerceCurrencies = commerceChannelDisplayContext.getCommerceCurrencies();

String name = BeanParamUtil.getString(commerceChannel, request, "name");
String commerceCurrencyCode = BeanParamUtil.getString(commerceChannel, request, "commerceCurrencyCode");
String type = BeanParamUtil.getString(commerceChannel, request, "type");

boolean isViewOnly = false;

if (commerceChannel != null) {
	isViewOnly = !commerceChannelDisplayContext.hasPermission(commerceChannelId, ActionKeys.UPDATE);
}

PortletURL editCommerceChannelRenderURL = commerceChannelDisplayContext.getEditCommerceChannelRenderURL();
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "add") %>'
	title='<%= LanguageUtil.get(request, "add-channel") %>'
>
	<portlet:actionURL name="editCommerceChannel" var="editCommerceChannelActionURL" />

	<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<div class="lfr-form-content">
			<aui:model-context bean="<%= commerceChannel %>" model="<%= CommerceChannel.class %>" />

			<aui:input autoFocus="<%= true %>" disabled="<%= isViewOnly %>" name="name" value="<%= name %>" />

			<aui:select label="currency" name="currencyCode" required="<%= true %>" title="currency">

				<%
				for (CommerceCurrency commerceCurrency : commerceCurrencies) {
				%>

					<aui:option label="<%= commerceCurrency.getName(locale) %>" selected="<%= (commerceChannel == null) ? commerceCurrency.isPrimary() : commerceCurrencyCode.equals(commerceCurrency.getCode()) %>" value="<%= commerceCurrency.getCode() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select disabled="<%= isViewOnly %>" name="type" showEmptyOption="<%= true %>">

				<%
				for (CommerceChannelType commerceChannelType : commerceChannelTypes) {
					String commerceChannelTypeKey = commerceChannelType.getKey();
				%>

					<aui:option label="<%= commerceChannelType.getLabel(locale) %>" selected="<%= (commerceChannel != null) && commerceChannelTypeKey.equals(type) %>" value="<%= commerceChannelTypeKey %>" />

				<%
				}
				%>

			</aui:select>
		</div>
	</aui:form>

	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils">
		Liferay.provide(
			window,
			'<portlet:namespace />apiSubmit',
			function (form) {
				var API_URL = '/o/headless-commerce-admin-channel/v1.0/channels';

				window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
					isLoading: true,
				});

				FormUtils.apiSubmit(form, API_URL)
					.then(function (payload) {
						var redirectURL = new Liferay.PortletURL.createURL(
							'<%= editCommerceChannelRenderURL.toString() %>'
						);

						redirectURL.setParameter('commerceChannelId', payload.id);
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
					.catch(function () {
						window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
							isLoading: false,
						});

						new Liferay.Notification({
							closeable: true,
							delay: {
								hide: 5000,
								show: 0,
							},
							duration: 500,
							message:
								'<liferay-ui:message key="an-unexpected-error-occurred" />',
							render: true,
							title: '<liferay-ui:message key="danger" />',
							type: 'danger',
						});
					});
			},
			['liferay-portlet-url']
		);
	</aui:script>
</commerce-ui:modal-content>