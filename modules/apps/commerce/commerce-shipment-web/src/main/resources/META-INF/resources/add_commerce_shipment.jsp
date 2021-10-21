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
CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment" var="editCommerceShipmentActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "create-new-shipment") %>'
>
	<aui:form action="<%= editCommerceShipmentActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="form">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />

		<aui:select id="commerceChannelGroupId" label="channel" name="commerceChannelGroupId" required="<%= true %>" showEmptyOption="<%= true %>">

			<%
			for (CommerceChannel commerceChannel : commerceShipmentDisplayContext.getCommerceChannels()) {
			%>

				<aui:option label="<%= commerceChannel.getName() %>" value="<%= commerceChannel.getGroupId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select id="commerceAccountId" label="account" name="commerceAccountId" required="<%= true %>" showEmptyOption="<%= true %>">

			<%
			for (CommerceAccount commerceAccount : commerceShipmentDisplayContext.getCommerceAccountsWithShippableOrders()) {
			%>

				<aui:option label="<%= commerceAccount.getName() %>" value="<%= commerceAccount.getCommerceAccountId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="address" name="commerceAddressId" required="<%= true %>" showEmptyOption="<%= true %>" />
	</aui:form>
</commerce-ui:modal-content>

<aui:script use="aui-base">
	var commerceAccount = <portlet:namespace />form.querySelector(
		'select[name=<portlet:namespace />commerceAccountId]'
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateAddressField',
		function <portlet:namespace />updateAddressField(commerceAccountId) {
			return Liferay.Util.fetch(
				'/o/headless-commerce-admin-account/v1.0/accounts/' +
					commerceAccountId +
					'/accountAddresses/',
				{
					headers: new Headers({
						'Accept': 'application/json',
						'Content-Type': 'application/json',
					}),
					method: 'GET',
				}
			)
				.then((response) => {
					return response.json();
				})
				.then((response) => {
					var select = A.one('#<portlet:namespace />commerceAddressId');

					response.items.forEach((item) => {
						var option = A.Node.create(
							'<option id="<portlet:namespace />commerceAddressId-' +
								item.id +
								'" value="' +
								item.id +
								'">' +
								item.street1 +
								' - ' +
								item.city +
								' - ' +
								item.regionISOCode +
								' - ' +
								item.countryISOCode +
								'</option>'
						);

						select.append(option);
					});

					select.show();
				});
		}
	);

	if (commerceAccount) {
		commerceAccount.addEventListener('change', () => {
			if (commerceAccount.value) {
				<portlet:namespace />updateAddressField(commerceAccount.value);
			}
		});
	}
</aui:script>