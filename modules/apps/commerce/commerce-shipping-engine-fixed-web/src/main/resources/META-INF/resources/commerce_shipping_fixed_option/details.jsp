<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long commerceShippingMethodId = ParamUtil.getLong(request, "commerceShippingMethodId");

CommerceShippingFixedOptionsDisplayContext commerceShippingFixedOptionsDisplayContext = (CommerceShippingFixedOptionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingFixedOption commerceShippingFixedOption = commerceShippingFixedOptionsDisplayContext.getCommerceShippingFixedOption();

long commerceShippingFixedOptionId = 0;

if (commerceShippingFixedOption != null) {
	commerceShippingFixedOptionId = commerceShippingFixedOption.getCommerceShippingFixedOptionId();
}
%>

<portlet:actionURL name="/commerce_shipping_methods/edit_commerce_shipping_fixed_option" var="editCommerceShippingFixedOptionActionURL" />

<aui:form action="<%= editCommerceShippingFixedOptionActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingFixedOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceShippingFixedOptionId" type="hidden" value="<%= commerceShippingFixedOptionId %>" />
	<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />

	<liferay-ui:error exception="<%= CommerceShippingFixedOptionKeyException.class %>" message="please-enter-a-valid-key" />

	<commerce-ui:panel>
		<aui:input bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" name="name" />

		<aui:input bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" name="description" />

		<c:if test="<%= commerceShippingFixedOptionsDisplayContext.isFixed() %>">
			<aui:input name="amount" suffix="<%= HtmlUtil.escape(commerceShippingFixedOptionsDisplayContext.getCommerceCurrencyCode()) %>" type="text" value="<%= (commerceShippingFixedOption == null) ? BigDecimal.ZERO : commerceShippingFixedOptionsDisplayContext.round(commerceShippingFixedOption.getAmount()) %>">
				<aui:validator name="number" />
			</aui:input>
		</c:if>

		<aui:input bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" name="priority" />

		<aui:input bean="<%= commerceShippingFixedOption %>" helpMessage="key-help" model="<%= CommerceShippingFixedOption.class %>" name="key" />
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>

<c:if test="<%= commerceShippingFixedOption == null %>">
	<aui:script require="frontend-js-web/index as frontendJsWeb">
		var {debounce} = frontendJsWeb;

		var form = document.getElementById('<portlet:namespace />fm');

		var keyInput = form.querySelector('#<portlet:namespace />key');
		var titleInput = form.querySelector('#<portlet:namespace />name');

		var handleOnTitleInput = function () {
			keyInput.value = titleInput.value;
		};

		titleInput.addEventListener('input', debounce(handleOnTitleInput, 200));
	</aui:script>
</c:if>