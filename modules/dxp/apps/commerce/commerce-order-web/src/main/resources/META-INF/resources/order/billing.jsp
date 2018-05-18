<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
long commerceOrderId = commerceOrderEditDisplayContext.getCommerceOrderId();

CommerceAddress billingAddress = null;

if (commerceOrder != null) {
	billingAddress = commerceOrder.getBillingAddress();
}

List<CommercePaymentMethod> commercePaymentMethods = commerceOrderEditDisplayContext.getCommercePaymentMethods();

long commerceCountryId = BeanParamUtil.getLong(billingAddress, request, "commerceCountryId");
long commerceRegionId = BeanParamUtil.getLong(billingAddress, request, "commerceRegionId");

int paymentStatus = BeanParamUtil.getInteger(commerceOrder, request, "paymentStatus");
long commercePaymentMethodId = BeanParamUtil.getLong(commerceOrder, request, "commercePaymentMethodId");
%>

<liferay-portlet:actionURL name="editCommerceOrder" var="editCommerceOrderURL" />

<aui:fieldset-group markupView="lexicon">
	<aui:container>
		<aui:row>
			<aui:col width="<%= 50 %>">
				<aui:form action="<%= editCommerceOrderURL %>" method="post" name="billingAddressFm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="billingAddress" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderId %>" />

					<aui:model-context bean="<%= billingAddress %>" model="<%= CommerceAddress.class %>" />

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
				<aui:form action="<%= editCommerceOrderURL %>" method="post" name="paymentFm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="payment" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderId %>" />

					<aui:model-context bean="<%= commerceOrder %>" model="<%= CommerceOrder.class %>" />

					<aui:fieldset disabled="">
						<aui:select label="status" name="paymentStatus">

							<%
							for (int curPaymentStatus : CommerceOrderConstants.PAYMENT_STATUSES) {
							%>

								<aui:option label="<%= CommerceOrderConstants.getPaymentStatusLabel(curPaymentStatus) %>" selected="<%= curPaymentStatus == paymentStatus %>" value="<%= curPaymentStatus %>" />

							<%
							}
							%>

						</aui:select>

						<aui:select label="payment-method" name="paymentMethodId">

							<%
							for (CommercePaymentMethod commercePaymentMethod : commercePaymentMethods) {
								long curCommercePaymentMethodId = commercePaymentMethod.getCommercePaymentMethodId();
							%>

								<aui:option label="<%= commerceOrderEditDisplayContext.getCommercePaymentMethodLabel(commercePaymentMethod) %>" localizeLabel="<%= false %>" selected="<%= curCommercePaymentMethodId == commercePaymentMethodId %>" value="<%= curCommercePaymentMethodId %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input name="purchaseOrderNumber" />

						<aui:input name="advanceStatus" />

						<aui:button-row>
							<aui:icon cssClass="edit-form-link" image="edit" label="edit-payment" url="javascript:;" />

							<div class="edit-form-buttons hide">
								<aui:button type="submit" />

								<aui:button cssClass="cancel-form-button" type="cancel" />
							</div>
						</aui:button-row>
					</aui:fieldset>
				</aui:form>
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
						'/commerce.commercecountry/get-billing-commerce-countries',
						{
							groupId: <%= scopeGroupId %>,
							billingAllowed: true,
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

	var form = A.one('#<portlet:namespace />billingAddressFm');

	form.on(
		'reset',
		function() {
			var commerceCountrySelect = A.one('#<portlet:namespace />commerceCountryId');

			commerceCountrySelect.val('<%= commerceCountryId %>');
			commerceCountrySelect.simulate('change');
		}
	);
</aui:script>