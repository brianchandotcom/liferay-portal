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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
long commerceOrderId = commerceOrderEditDisplayContext.getCommerceOrderId();

CommerceAddress shippingAddress = null;

if (commerceOrder != null) {
	shippingAddress = commerceOrder.getShippingAddress();
}

long commerceCountryId = BeanParamUtil.getLong(shippingAddress, request, "commerceCountryId");
long commerceRegionId = BeanParamUtil.getLong(shippingAddress, request, "commerceRegionId");
%>

<liferay-portlet:actionURL name="editCommerceOrder" var="editCommerceOrderURL" />

<aui:fieldset-group markupView="lexicon">
	<aui:container>
		<aui:row>
			<aui:col width="<%= 50 %>">
				<aui:form action="<%= editCommerceOrderURL %>" method="post" name="shippingAddressFm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="shippingAddress" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderId %>" />

					<aui:model-context bean="<%= shippingAddress %>" model="<%= CommerceAddress.class %>" />

					<aui:fieldset disabled="">
						<aui:input name="name" />

						<aui:input name="street1" />

						<aui:input name="street2" />

						<aui:input name="street3" />

						<aui:input name="city" />

						<aui:select label="region" name="commerceRegionId" />

						<aui:input label="postal-code" name="zip" />

						<aui:select label="country" name="commerceCountryId" />

						<aui:input name="description" />

						<aui:button-row>
							<aui:icon cssClass="edit-form-link" image="edit" label="edit-address" url="javascript:;" />

							<div class="edit-form-buttons hide">
								<aui:button type="submit" />

								<aui:button cssClass="cancel-form-button" type="cancel" />
							</div>
						</aui:button-row>
					</aui:fieldset>
				</aui:form>
			</aui:col>

			<aui:col width="<%= 50 %>">
			</aui:col>
		</aui:row>
	</aui:container>
</aui:fieldset-group>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace />commerceCountryId',
				selectData: function(callback) {
					Liferay.Service(
						'/commerce.commercecountry/get-shipping-commerce-countries',
						{
							groupId: <%= scopeGroupId %>,
							shippingAllowed: true,
							active: true
						},
						callback
					);
				},
				selectDesc: 'nameCurrentValue',
				selectId: 'commerceCountryId',
				selectSort: '<%= true %>',
				selectVal: '<%= commerceCountryId %>'
			},
			{
				select: '<portlet:namespace />commerceRegionId',
				selectData: function(callback, selectKey) {
					Liferay.Service(
						'/commerce.commerceregion/get-commerce-regions',
						{
							commerceCountryId: Number(selectKey),
							active: true
						},
						callback
					);
				},
				selectDesc: 'name',
				selectId: 'commerceRegionId',
				selectVal: '<%= commerceRegionId %>'
			}
		]
	);

	var form = A.one('#<portlet:namespace />shippingAddressFm');

	form.on(
		'reset',
		function() {
			var commerceCountrySelect = A.one('#<portlet:namespace />commerceCountryId');

			commerceCountrySelect.val('<%= commerceCountryId %>');
			commerceCountrySelect.simulate('change');
		}
	);
</aui:script>