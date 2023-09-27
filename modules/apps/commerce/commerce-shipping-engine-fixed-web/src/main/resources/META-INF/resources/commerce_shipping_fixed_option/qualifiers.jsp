<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceShippingFixedOptionQualifiersDisplayContext commerceShippingFixedOptionQualifiersDisplayContext = (CommerceShippingFixedOptionQualifiersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingFixedOption commerceShippingFixedOption = commerceShippingFixedOptionQualifiersDisplayContext.getCommerceShippingFixedOption();
long commerceShippingFixedOptionId = commerceShippingFixedOptionQualifiersDisplayContext.getCommerceShippingFixedOptionId();

String orderTypeQualifiers = ParamUtil.getString(request, "orderTypeQualifiers", commerceShippingFixedOptionQualifiersDisplayContext.getActiveOrderTypeEligibility());
String termEntryQualifiers = ParamUtil.getString(request, "termEntryQualifiers", commerceShippingFixedOptionQualifiersDisplayContext.getActiveTermEntryEligibility());

boolean hasPermission = commerceShippingFixedOptionQualifiersDisplayContext.hasPermission(ActionKeys.UPDATE);
%>

<portlet:actionURL name="/commerce_shipping_methods/edit_commerce_shipping_fixed_option_qualifiers" var="editCommerceShippingFixedOptionQualifiersActionURL" />

<aui:form action="<%= editCommerceShippingFixedOptionQualifiersActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingFixedOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceShippingFixedOptionId" type="hidden" value="<%= commerceShippingFixedOptionId %>" />
	<aui:input name="orderTypeQualifiers" type="hidden" value="<%= orderTypeQualifiers %>" />
	<aui:input name="termEntryQualifiers" type="hidden" value="<%= termEntryQualifiers %>" />

	<liferay-ui:error exception="<%= DuplicateCommerceShippingFixedOptionQualifierException.class %>" message="the-qualifier-is-already-linked" />

	<aui:model-context bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" />

	<commerce-ui:panel
		bodyClasses="flex-fill"
		collapsed="<%= false %>"
		collapsible="<%= false %>"
		title='<%= LanguageUtil.get(request, "order-type-eligibility") %>'
	>
		<aui:fieldset markupView="lexicon">
			<aui:input checked='<%= Objects.equals(orderTypeQualifiers, "all") %>' label="all-order-types" name="qualifiers--orderType--" type="radio" value="all" />
			<aui:input checked='<%= Objects.equals(orderTypeQualifiers, "orderTypes") %>' label="specific-order-types" name="qualifiers--orderType--" type="radio" value="orderTypes" />
		</aui:fieldset>
	</commerce-ui:panel>

	<c:if test='<%= Objects.equals(orderTypeQualifiers, "orderTypes") %>'>
		<%@ include file="/commerce_shipping_fixed_option/qualifier/commerce_order_types.jspf" %>
	</c:if>

	<commerce-ui:panel
		bodyClasses="flex-fill"
		collapsed="<%= false %>"
		collapsible="<%= false %>"
		title='<%= LanguageUtil.get(request, "delivery-terms-eligibility") %>'
	>
		<aui:fieldset markupView="lexicon">
			<aui:input checked='<%= Objects.equals(termEntryQualifiers, "none") %>' label="no-delivery-terms" name="qualifiers--termEntry--" type="radio" value="none" />
			<aui:input checked='<%= Objects.equals(termEntryQualifiers, "termEntries") %>' label="specific-delivery-terms" name="qualifiers--termEntry--" type="radio" value="termEntries" />
		</aui:fieldset>
	</commerce-ui:panel>

	<c:if test='<%= Objects.equals(termEntryQualifiers, "termEntries") %>'>
		<%@ include file="/commerce_shipping_fixed_option/qualifier/commerce_term_entries.jspf" %>
	</c:if>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" value="save" />

		<aui:button cssClass="btn-lg" type="cancel" />
	</aui:button-row>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"currentURL", currentURL
		).put(
			"searchParam", "orderTypeQualifiers"
		).put(
			"selector", "qualifiers--orderType--"
		).build()
	%>'
	module="js/qualifiers"
/>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"currentURL", currentURL
		).put(
			"searchParam", "termEntryQualifiers"
		).put(
			"selector", "qualifiers--termEntry--"
		).build()
	%>'
	module="js/qualifiers"
/>