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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
shoppingSettings = ShoppingSettings.getInstance(themeDisplay.getSiteGroupId(), request.getParameterMap());
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= ShoppingConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--ccTypes--" type="hidden" />

	<liferay-ui:tabs
		names="payment-settings,shipping-calculation,insurance-calculation,email-from,confirmation-email,shipping-email"
		refresh="<%= false %>"
	>
		<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
		<liferay-ui:error key="emailOrderShippingBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailOrderShippingSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailOrderConfirmationBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailOrderConfirmationSubject" message="please-enter-a-valid-subject" />

		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="enter-a-paypal-email-address-to-send-all-payments-to-paypal" />

				<liferay-ui:message arguments='<%= "<strong>" + themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/shopping/notify</strong>" %>' key="go-to-paypal-and-set-up-ipn-to-post-to-x" translateArguments="<%= false %>" />
			</div>

			<div class="alert alert-info">
				<liferay-ui:message key="enter-a-blank-paypal-email-address-to-disable-paypal" />
			</div>

			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="paypal-email-address" name="preferences--paypalEmailAddress--" type="text" value="<%= shoppingSettings.getPayPalEmailAddress() %>" />

				<aui:field-wrapper label="credit-cards">

					<%
					String[] ccTypes1 = ShoppingSettings.CC_TYPES;
					String[] ccTypes2 = shoppingSettings.getCcTypes();

					// Left list

					List leftList = new ArrayList();

					for (String ccType : ccTypes2) {
						leftList.add(new KeyValuePair(HtmlUtil.escapeAttribute(ccType), LanguageUtil.get(request, "cc_" + HtmlUtil.escape(ccType))));
					}

					// Right list

					List rightList = new ArrayList();

					for (String ccType : ccTypes1) {
						if (!ArrayUtil.contains(ccTypes2, ccType)) {
							rightList.add(new KeyValuePair(ccType, LanguageUtil.get(request, "cc_" + ccType)));
						}
					}
					%>

					<liferay-ui:input-move-boxes
						leftBoxName="current_cc_types"
						leftList="<%= leftList %>"
						leftReorder="true"
						leftTitle="current"
						rightBoxName="available_cc_types"
						rightList="<%= rightList %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>

				<aui:select label="currency" name="preferences--currencyId--">

					<%
					for (int i = 0; i < ShoppingSettings.CURRENCY_IDS.length; i++) {
					%>

						<aui:option label="<%= ShoppingSettings.CURRENCY_IDS[i] %>" selected="<%= shoppingSettings.getCurrencyId().equals(ShoppingSettings.CURRENCY_IDS[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="tax-state" name="preferences--taxState--">

					<%
					for (int i = 0; i < StateUtil.STATES.length; i++) {
					%>

						<aui:option label="<%= StateUtil.STATES[i].getName() %>" selected="<%= shoppingSettings.getTaxState().equals(StateUtil.STATES[i].getId()) %>" value="<%= StateUtil.STATES[i].getId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input maxlength="7" name="taxRate" size="7" type="text" value="<%= taxFormat.format(shoppingSettings.getTaxRate()) %>" />

				<aui:input label="minimum-order" maxlength="7" name="preferences--minOrder--" size="7" type="text" value="<%= doubleFormat.format(shoppingSettings.getMinOrder()) %>" />
			</aui:fieldset>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="calculate-a-flat-shipping-amount-based-on-the-total-amount-of-the-purchase" /> <span style="font-size: xx-small;">-- <%= StringUtil.toUpperCase(LanguageUtil.get(request, "or")) %> --</span> <liferay-ui:message key="calculate-the-shipping-based-on-a-percentage-of-the-total-amount-of-the-purchase" />
			</div>

			<aui:fieldset>
				<aui:select label="formula" name="preferences--shippingFormula--">
					<aui:option label="flat-amount" selected='<%= shoppingSettings.getShippingFormula().equals("flat") %>' value="flat" />
					<aui:option label="percentage" selected='<%= shoppingSettings.getShippingFormula().equals("percentage") %>' />
				</aui:select>

				<aui:field-wrapper label="values">

					<%
					int shippingRange = 0;

					for (int i = 0; i < 5; i++) {
						double shippingRangeA = ShoppingSettings.INSURANCE_RANGE[shippingRange++];
						double shippingRangeB = ShoppingSettings.INSURANCE_RANGE[shippingRange++];
					%>

					<%= currencyFormat.format(shippingRangeA) %>

					<c:if test="<%= !Double.isInfinite(shippingRangeB) %>">
						- <%= currencyFormat.format(shippingRangeB) %>
					</c:if>

					<c:if test="<%= Double.isInfinite(shippingRangeB) %>">
						and over
					</c:if>

					<aui:input label="" maxlength="6" name='<%= "shipping" + i %>' size="6" title="shipping" type="text" value="<%= GetterUtil.getString(shoppingSettings.getShipping()[i]) %>" />

					<%
					}
					%>

				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="calculate-a-flat-insurance-amount-based-on-the-total-amount-of-the-purchase" /> <span style="font-size: xx-small;">-- <%= StringUtil.toUpperCase(LanguageUtil.get(request, "or")) %> --</span> <liferay-ui:message key="calculate-the-insurance-based-on-a-percentage-of-the-total-amount-of-the-purchase" />
			</div>

			<aui:fieldset>
				<aui:select label="formula" name="preferences--insuranceFormula--">
					<aui:option label="flat-amount" selected='<%= shoppingSettings.getInsuranceFormula().equals("flat") %>' value="flat" />
					<aui:option label="percentage" selected='<%= shoppingSettings.getInsuranceFormula().equals("percentage") %>' />
				</aui:select>

				<aui:field-wrapper label="values">

					<%
					int insuranceRange = 0;

					for (int i = 0; i < 5; i++) {
						double insuranceRangeA = ShoppingSettings.INSURANCE_RANGE[insuranceRange++];
						double insuranceRangeB = ShoppingSettings.INSURANCE_RANGE[insuranceRange++];
					%>

					<%= currencyFormat.format(insuranceRangeA) %>

					<c:if test="<%= !Double.isInfinite(insuranceRangeB) %>">
						- <%= currencyFormat.format(insuranceRangeB) %>
					</c:if>

					<c:if test="<%= Double.isInfinite(insuranceRangeB) %>">
						and over
					</c:if>

					<aui:input label="" maxlength="6" name='<%= "insurance" + i %>' size="6" title="insurance" type="text" value="<%= GetterUtil.getString(shoppingSettings.getInsurance()[i]) %>" />

					<%
					}
					%>

				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" type="text" value="<%= shoppingSettings.getEmailFromName() %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" type="text" value="<%= shoppingSettings.getEmailFromAddress() %>" />
			</aui:fieldset>
		</liferay-ui:section>

		<%
		Map<String, String> emailDefinitionTerms = ShoppingUtil.getEmailDefinitionTerms(renderRequest, shoppingSettings.getEmailFromAddress(), shoppingSettings.getEmailFromName());
		%>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= shoppingSettings.getEmailOrderConfirmationBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled="<%= shoppingSettings.isEmailOrderConfirmationEnabled() %>"
				emailParam="emailOrderConfirmation"
				emailSubject="<%= shoppingSettings.getEmailOrderConfirmationSubjectXml() %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= shoppingSettings.getEmailOrderShippingBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled="<%= shoppingSettings.isEmailOrderShippingEnabled() %>"
				emailParam="emailOrderShipping"
				emailSubject="<%= shoppingSettings.getEmailOrderShippingSubjectXml() %>"
			/>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--ccTypes--'].value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />current_cc_types);

			<portlet:namespace />saveEmails();

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	function <portlet:namespace />saveEmails() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailOrderConfirmationBody--'].value = window['<portlet:namespace />emailOrderConfirmation'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailOrderShippingBody--'].value = window['<portlet:namespace />emailOrderShipping'].getHTML();
		}
		catch (e) {
		}
	}
</aui:script>