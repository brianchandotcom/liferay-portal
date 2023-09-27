<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

boolean viewOnly = false;

if (commerceChannel != null) {
	viewOnly = !commerceChannelDisplayContext.hasPermission(commerceChannelId, ActionKeys.UPDATE);
}

PortletURL editCommerceChannelRenderURL = commerceChannelDisplayContext.getEditCommerceChannelRenderURL();
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "add") %>'
	title='<%= LanguageUtil.get(request, "add-channel") %>'
>
	<portlet:actionURL name="/commerce_channels/edit_commerce_channel" var="editCommerceChannelActionURL" />

	<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<div class="lfr-form-content">
			<aui:model-context bean="<%= commerceChannel %>" model="<%= CommerceChannel.class %>" />

			<aui:input disabled="<%= viewOnly %>" name="name" value="<%= name %>" />

			<aui:select label="currency" name="currencyCode" required="<%= true %>" title="currency">

				<%
				for (CommerceCurrency commerceCurrency : commerceCurrencies) {
				%>

					<aui:option label="<%= commerceCurrency.getName(locale) %>" selected="<%= (commerceChannel == null) ? commerceCurrency.isPrimary() : commerceCurrencyCode.equals(commerceCurrency.getCode()) %>" value="<%= commerceCurrency.getCode() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select disabled="<%= viewOnly %>" name="type" showEmptyOption="<%= true %>">

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
			(form) => {
				var API_URL = '/o/headless-commerce-admin-channel/v1.0/channels';

				window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
					isLoading: true,
				});

				FormUtils.apiSubmit(form, API_URL)
					.then((payload) => {
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
					.catch(() => {
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