<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String tabs2 = ParamUtil.getString(request, "tabs2", "payment-settings");
String tabs3 = ParamUtil.getString(request, "tabs3", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "emailFromName", shoppingPrefs.getEmailFromName(company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", shoppingPrefs.getEmailFromAddress(company.getCompanyId()));

String emailOrderConfirmationSubject = ParamUtil.getString(request, "emailOrderConfirmationSubject", shoppingPrefs.getEmailOrderConfirmationSubject());
String emailOrderConfirmationBody = ParamUtil.getString(request, "emailOrderConfirmationBody", shoppingPrefs.getEmailOrderConfirmationBody());

String emailOrderShippingSubject = ParamUtil.getString(request, "emailOrderShippingSubject", shoppingPrefs.getEmailOrderShippingSubject());
String emailOrderShippingBody = ParamUtil.getString(request, "emailOrderShippingBody", shoppingPrefs.getEmailOrderShippingBody());

String editorParam = StringPool.BLANK;
String editorContent = StringPool.BLANK;

if (tabs3.equals("confirmation-email")) {
	editorParam = "emailOrderConfirmationBody";
	editorContent = emailOrderConfirmationBody;
}
else if (tabs3.equals("shipping-email")) {
	editorParam = "emailOrderShippingBody";
	editorContent = emailOrderShippingBody;
}
%>

<liferay-portlet:renderURL portletConfiguration="true" var="portletURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="ccTypes" type="hidden" />

	<liferay-ui:tabs
		names="payment-settings,shipping-calculation,insurance-calculation,emails"
		param="tabs2"
		url="<%= portletURL %>"
	/>

	<c:choose>
		<c:when test='<%= tabs2.equals("payment-settings") %>'>
			<div class="alert alert-info">
				<liferay-ui:message key="enter-a-paypal-email-address-to-send-all-payments-to-paypal" />

				<liferay-ui:message arguments='<%= "<strong>" + themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/shopping/notify</strong>" %>' key="go-to-paypal-and-set-up-ipn-to-post-to-x" translateArguments="<%= false %>" />
			</div>

			<div class="alert alert-info">
				<liferay-ui:message key="enter-a-blank-paypal-email-address-to-disable-paypal" />
			</div>

			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="paypal-email-address" name="payPalEmailAddress" type="text" value="<%= shoppingPrefs.getPayPalEmailAddress() %>" />

				<aui:field-wrapper label="credit-cards">

					<%
					String[] ccTypes1 = ShoppingPreferences.CC_TYPES;
					String[] ccTypes2 = shoppingPrefs.getCcTypes();

					// Left list

					List leftList = new ArrayList();

					for (String ccType : ccTypes2) {
						leftList.add(new KeyValuePair(HtmlUtil.escapeAttribute(ccType), LanguageUtil.get(pageContext, "cc_" + HtmlUtil.escape(ccType))));
					}

					// Right list

					List rightList = new ArrayList();

					for (String ccType : ccTypes1) {
						if (!ArrayUtil.contains(ccTypes2, ccType)) {
							rightList.add(new KeyValuePair(ccType, LanguageUtil.get(pageContext, "cc_" + ccType)));
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

				<aui:select label="currency" name="currencyId">

					<%
					for (int i = 0; i < ShoppingPreferences.CURRENCY_IDS.length; i++) {
					%>

						<aui:option label="<%= ShoppingPreferences.CURRENCY_IDS[i] %>" selected="<%= shoppingPrefs.getCurrencyId().equals(ShoppingPreferences.CURRENCY_IDS[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select name="taxState">

					<%
					for (int i = 0; i < StateUtil.STATES.length; i++) {
					%>

						<aui:option label="<%= StateUtil.STATES[i].getName() %>" selected="<%= shoppingPrefs.getTaxState().equals(StateUtil.STATES[i].getId()) %>" value="<%= StateUtil.STATES[i].getId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input maxlength="7" name="taxRate" size="7" type="text" value="<%= taxFormat.format(shoppingPrefs.getTaxRate()) %>" />

				<aui:input label="minimum-order" maxlength="7" name="minOrder" size="7" type="text" value="<%= doubleFormat.format(shoppingPrefs.getMinOrder()) %>" />
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("shipping-calculation") %>'>
			<div class="alert alert-info">
				<liferay-ui:message key="calculate-a-flat-shipping-amount-based-on-the-total-amount-of-the-purchase" /> <span style="font-size: xx-small;">-- <%= StringUtil.toUpperCase(LanguageUtil.get(pageContext, "or")) %> --</span> <liferay-ui:message key="calculate-the-shipping-based-on-a-percentage-of-the-total-amount-of-the-purchase" />
			</div>

			<aui:fieldset>
				<aui:select label="formula" name="shippingFormula">
					<aui:option label="flat-amount" selected='<%= shoppingPrefs.getShippingFormula().equals("flat") %>' value="flat" />
					<aui:option label="percentage" selected='<%= shoppingPrefs.getShippingFormula().equals("percentage") %>' />
				</aui:select>

				<aui:field-wrapper label="values">

					<%
					int shippingRange = 0;

					for (int i = 0; i < 5; i++) {
						double shippingRangeA = ShoppingPreferences.INSURANCE_RANGE[shippingRange++];
						double shippingRangeB = ShoppingPreferences.INSURANCE_RANGE[shippingRange++];
					%>

					<%= currencyFormat.format(shippingRangeA) %>

					<c:if test="<%= !Double.isInfinite(shippingRangeB) %>">
						- <%= currencyFormat.format(shippingRangeB) %>
					</c:if>

					<c:if test="<%= Double.isInfinite(shippingRangeB) %>">
						and over
					</c:if>

					<aui:input label="" maxlength="6" name='<%= "shipping" + i %>' size="6" type="text" value="<%= GetterUtil.getString(shoppingPrefs.getShipping()[i]) %>" />

					<%
					}
					%>

				</aui:field-wrapper>
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("insurance-calculation") %>'>
			<div class="alert alert-info">
				<liferay-ui:message key="calculate-a-flat-insurance-amount-based-on-the-total-amount-of-the-purchase" /> <span style="font-size: xx-small;">-- <%= StringUtil.toUpperCase(LanguageUtil.get(pageContext, "or")) %> --</span> <liferay-ui:message key="calculate-the-insurance-based-on-a-percentage-of-the-total-amount-of-the-purchase" />
			</div>

			<aui:fieldset>
				<aui:select label="formula" name="insuranceFormula">
					<aui:option label="flat-amount" selected='<%= shoppingPrefs.getInsuranceFormula().equals("flat") %>' value="flat" />
					<aui:option label="percentage" selected='<%= shoppingPrefs.getInsuranceFormula().equals("percentage") %>' />
				</aui:select>

				<aui:field-wrapper label="values">

					<%
					int insuranceRange = 0;

					for (int i = 0; i < 5; i++) {
						double insuranceRangeA = ShoppingPreferences.INSURANCE_RANGE[insuranceRange++];
						double insuranceRangeB = ShoppingPreferences.INSURANCE_RANGE[insuranceRange++];
					%>

					<%= currencyFormat.format(insuranceRangeA) %>

					<c:if test="<%= !Double.isInfinite(insuranceRangeB) %>">
						- <%= currencyFormat.format(insuranceRangeB) %>
					</c:if>

					<c:if test="<%= Double.isInfinite(insuranceRangeB) %>">
						and over
					</c:if>

					<aui:input label="" maxlength="6" name='<%= "insurance" + i %>' size="6" type="text" value="<%= GetterUtil.getString(shoppingPrefs.getInsurance()[i]) %>" />

					<%
					}
					%>

				</aui:field-wrapper>
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("emails") %>'>
			<liferay-ui:tabs
				names="email-from,confirmation-email,shipping-email"
				param="tabs3"
				url="<%= portletURL.toString() %>"
			/>

			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
			<liferay-ui:error key="emailOrderShippingBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailOrderShippingSubject" message="please-enter-a-valid-subject" />
			<liferay-ui:error key="emailOrderConfirmationBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailOrderConfirmationSubject" message="please-enter-a-valid-subject" />

			<c:choose>
				<c:when test='<%= tabs3.endsWith("-email") %>'>
					<aui:fieldset>
						<c:choose>
							<c:when test='<%= tabs3.equals("confirmation-email") %>'>
								<aui:input label="enabled" name="emailOrderConfirmationEnabled" type="checkbox" value="<%= shoppingPrefs.getEmailOrderConfirmationEnabled() %>" />
							</c:when>
							<c:when test='<%= tabs3.equals("shipping-email") %>'>
								<aui:input label="enabled" name="emailOrderShippingEnabled" type="checkbox" value="<%= shoppingPrefs.getEmailOrderShippingEnabled() %>" />
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test='<%= tabs3.equals("confirmation-email") %>'>
								<aui:input cssClass="lfr-input-text-container" label="subject" name="emailOrderConfirmationSubject" type="text" value="<%= emailOrderConfirmationSubject %>" />
							</c:when>
							<c:when test='<%= tabs3.equals("shipping-email") %>'>
								<aui:input cssClass="lfr-input-text-container" label="subject" name="emailOrderShippingSubject" type="text" value="<%= emailOrderShippingSubject %>" />
							</c:when>
						</c:choose>

						<aui:field-wrapper label="body">
							<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

							<aui:input name="<%= editorParam %>" type="hidden" value="" />
						</aui:field-wrapper>

						<aui:fieldset cssClass="definition-of-terms">
							<legend>
								<liferay-ui:message key="definition-of-terms" />
							</legend>

							<dl>
								<dt>
									[$FROM_ADDRESS$]
								</dt>
								<dd>
									<%= HtmlUtil.escape(emailFromAddress) %>
								</dd>
								<dt>
									[$FROM_NAME$]
								</dt>
								<dd>
									<%= HtmlUtil.escape(emailFromName) %>
								</dd>
								<dt>
									[$ORDER_BILLING_ADDRESS$]
								</dt>
								<dd>
									<liferay-ui:message key="the-order-billing-address" />
								</dd>
								<dt>
									[$ORDER_CURRENCY$]
								</dt>
								<dd>
									<liferay-ui:message key="the-order-currency" />
								</dd>
								<dt>
									[$ORDER_NUMBER$]
								</dt>
								<dd>
									<liferay-ui:message key="the-order-id" />
								</dd>
								<dt>
									[$ORDER_SHIPPING_ADDRESS$]
								</dt>
								<dd>
									<liferay-ui:message key="the-order-shipping-address" />
								</dd>
								<dt>
									[$ORDER_TOTAL$]
								</dt>
								<dd>
									<liferay-ui:message key="the-order-total" />
								</dd>
								<dt>
									[$PORTAL_URL$]
								</dt>
								<dd>
									<%= company.getVirtualHostname() %>
								</dd>
								<dt>
									[$PORTLET_NAME$]
								</dt>
								<dd>
									<%= PortalUtil.getPortletTitle(renderResponse) %>
								</dd>
								<dt>
									[$TO_ADDRESS$]
								</dt>
								<dd>
									<liferay-ui:message key="the-address-of-the-email-recipient" />
								</dd>
								<dt>
									[$TO_NAME$]
								</dt>
								<dd>
									<liferay-ui:message key="the-name-of-the-email-recipient" />
								</dd>
							</dl>
						</aui:fieldset>
					</aui:fieldset>
				</c:when>
				<c:otherwise>
					<aui:fieldset>
						<aui:input cssClass="lfr-input-text-container" label="name" name="emailFromName" type="text" value="<%= emailFromName %>" />

						<aui:input cssClass="lfr-input-text-container" label="address" name="emailFromAddress" type="text" value="<%= emailFromAddress %>" />
					</aui:fieldset>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(editorContent) %>";
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			<c:if test='<%= tabs2.equals("payment-settings") %>'>
				document.<portlet:namespace />fm.<portlet:namespace />ccTypes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />current_cc_types);
			</c:if>

			<c:if test='<%= tabs3.endsWith("-email") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();
			</c:if>

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.shopping.configuration.jsp";
%>