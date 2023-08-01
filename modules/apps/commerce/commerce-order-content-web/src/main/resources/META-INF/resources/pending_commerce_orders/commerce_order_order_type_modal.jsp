<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannel commerceChannel = commerceOrderContentDisplayContext.fetchCommerceChannel();
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "add-order") %>'
	title='<%= LanguageUtil.format(locale, "select-x", "order-type") %>'
>
	<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderURL" />

	<clay:alert
		dismissible="<%= true %>"
		displayType="info"
		message="commerce-order-type-info"
		title='<%= LanguageUtil.get(request, "info") %>'
	/>

	<aui:form action="<%= editCommerceOrderURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "addOrder();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

		<aui:select label="order-type" name="commerceOrderTypeId">

			<%
			for (CommerceOrderType orderType : commerceOrderContentDisplayContext.getCommerceOrderTypes()) {
			%>

				<aui:option label="<%= orderType.getName(locale) %>" value="<%= orderType.getCommerceOrderTypeId() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>
</commerce-ui:modal-content>

<portlet:renderURL var="editCommerceOrderRenderURL">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order" />
</portlet:renderURL>

<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
	Liferay.provide(window, '<portlet:namespace />addOrder', () => {
		window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
			isLoading: true,
		});

		var form = document.getElementById('<portlet:namespace />fm');

		var orderTypeId = form.querySelector(
			'#<portlet:namespace />commerceOrderTypeId'
		).value;

		if (<%= ParamUtil.getBoolean(request, "addToCart") %>) {
			window.parent.Liferay.fire(events.ADD_ITEM_TO_CART, {orderTypeId});

			window.parent.Liferay.fire(events.CLOSE_MODAL, {
				successNotification: {
					showSuccessNotification: true,
					message:
						'<liferay-ui:message key="your-request-completed-successfully" />',
				},
			});
		}
		else {
			var CartResource = ServiceProvider.default.DeliveryCartAPI('v1');

			CartResource.createCartByChannelId(
				'<%= commerceChannel.getCommerceChannelId() %>',
				{
					accountId:
						'<%= commerceOrderContentDisplayContext.getCommerceAccountId() %>',
					currencyCode:
						'<%= commerceChannel.getCommerceCurrencyCode() %>',
					orderTypeId: orderTypeId,
				}
			)
				.then((order) => {
					Liferay.fire(
						events.CURRENT_ORDER_UPDATED,
						Object.assign({}, order)
					);

					var redirectURL = new Liferay.Util.PortletURL.createPortletURL(
						'<%= editCommerceOrderRenderURL.toString() %>',
						{
							commerceOrderId: order.id,
							p_auth: Liferay.authToken,
							p_p_state:
								'<%= LiferayWindowState.MAXIMIZED.toString() %>',
						}
					);
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
		}
	});
</aui:script>