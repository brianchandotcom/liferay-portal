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
CommercePaymentMethodGroupRelQualifiersDisplayContext commercePaymentMethodGroupRelQualifiersDisplayContext = (CommercePaymentMethodGroupRelQualifiersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePaymentMethodGroupRel commercePaymentMethodGroupRel = commercePaymentMethodGroupRelQualifiersDisplayContext.getCommercePaymentMethodGroupRel();
long commercePaymentMethodGroupRelId = commercePaymentMethodGroupRelQualifiersDisplayContext.getCommercePaymentMethodGroupRelId();

String orderTypeQualifiers = ParamUtil.getString(request, "orderTypeQualifiers", commercePaymentMethodGroupRelQualifiersDisplayContext.getActiveOrderTypeEligibility());
String termEntryQualifiers = ParamUtil.getString(request, "termEntryQualifiers", commercePaymentMethodGroupRelQualifiersDisplayContext.getActiveTermEntryEligibility());

boolean hasPermission = commercePaymentMethodGroupRelQualifiersDisplayContext.hasPermission(ActionKeys.UPDATE);
%>

<portlet:actionURL name="/commerce_payment_methods/edit_commerce_payment_method_group_rel_qualifiers" var="editCommercePaymentMethodGroupRelQualifiersActionURL" />

<aui:form action="<%= editCommercePaymentMethodGroupRelQualifiersActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commercePaymentMethodGroupRel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commercePaymentMethodGroupRelId" type="hidden" value="<%= commercePaymentMethodGroupRelId %>" />
	<aui:input name="orderTypeQualifiers" type="hidden" value="<%= orderTypeQualifiers %>" />
	<aui:input name="termEntryQualifiers" type="hidden" value="<%= termEntryQualifiers %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<liferay-ui:error exception="<%= DuplicateCommercePaymentMethodGroupRelQualifierException.class %>" message="the-qualifier-is-already-linked" />

	<aui:model-context bean="<%= commercePaymentMethodGroupRel %>" model="<%= CommercePaymentMethodGroupRel.class %>" />

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				collapsed="<%= false %>"
				collapsible="<%= false %>"
				title='<%= LanguageUtil.get(request, "order-type-eligibility") %>'
			>
				<div class="row">
					<aui:fieldset markupView="lexicon">
						<aui:input checked='<%= Objects.equals(orderTypeQualifiers, "all") %>' label="all-order-types" name="qualifiers--orderType--" type="radio" value="all" />
						<aui:input checked='<%= Objects.equals(orderTypeQualifiers, "orderTypes") %>' label="specific-order-types" name="qualifiers--orderType--" type="radio" value="orderTypes" />
					</aui:fieldset>
				</div>
			</commerce-ui:panel>
		</div>
	</div>

	<c:if test='<%= Objects.equals(orderTypeQualifiers, "orderTypes") %>'>
		<%@ include file="/commerce_payment_method/qualifier/commerce_order_types.jspf" %>
	</c:if>

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				collapsed="<%= false %>"
				collapsible="<%= false %>"
				title='<%= LanguageUtil.get(request, "payment-terms-eligibility") %>'
			>
				<div class="row">
					<aui:fieldset markupView="lexicon">
						<aui:input checked='<%= Objects.equals(termEntryQualifiers, "none") %>' label="no-payment-terms" name="qualifiers--termEntry--" type="radio" value="none" />
						<aui:input checked='<%= Objects.equals(termEntryQualifiers, "termEntries") %>' label="specific-payment-terms" name="qualifiers--termEntry--" type="radio" value="termEntries" />
					</aui:fieldset>
				</div>
			</commerce-ui:panel>
		</div>
	</div>

	<c:if test='<%= Objects.equals(termEntryQualifiers, "termEntries") %>'>
		<%@ include file="/commerce_payment_method/qualifier/commerce_term_entries.jspf" %>
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