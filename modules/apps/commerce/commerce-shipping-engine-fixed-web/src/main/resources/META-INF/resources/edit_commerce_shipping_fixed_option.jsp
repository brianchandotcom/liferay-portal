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
long commerceShippingMethodId = ParamUtil.getLong(request, "commerceShippingMethodId");

CommerceShippingFixedOptionsDisplayContext commerceShippingFixedOptionsDisplayContext = (CommerceShippingFixedOptionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingFixedOption commerceShippingFixedOption = commerceShippingFixedOptionsDisplayContext.getCommerceShippingFixedOption();

long commerceShippingFixedOptionId = 0;

if (commerceShippingFixedOption != null) {
	commerceShippingFixedOptionId = commerceShippingFixedOption.getCommerceShippingFixedOptionId();
}
%>

<portlet:actionURL name="/commerce_shipping_methods/edit_commerce_shipping_fixed_option" var="editCommerceShippingFixedOptionActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(resourceBundle, "shipping-option") %>'
>
	<aui:form action="<%= editCommerceShippingFixedOptionActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingFixedOption == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="commerceShippingFixedOptionId" type="hidden" value="<%= commerceShippingFixedOptionId %>" />
		<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<commerce-ui:panel>
			<aui:input autoFocus="<%= true %>" bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" name="name" />

			<aui:input bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" name="description" />

			<c:if test="<%= commerceShippingFixedOptionsDisplayContext.isFixed() %>">
				<aui:input name="amount" suffix="<%= HtmlUtil.escape(commerceShippingFixedOptionsDisplayContext.getCommerceCurrencyCode()) %>" type="text" value="<%= (commerceShippingFixedOption == null) ? BigDecimal.ZERO : commerceShippingFixedOptionsDisplayContext.round(commerceShippingFixedOption.getAmount()) %>">
					<aui:validator name="number" />
				</aui:input>
			</c:if>

			<aui:input bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" name="priority" />
		</commerce-ui:panel>

		<aui:button-row>
			<aui:button type="submit" value="save" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>