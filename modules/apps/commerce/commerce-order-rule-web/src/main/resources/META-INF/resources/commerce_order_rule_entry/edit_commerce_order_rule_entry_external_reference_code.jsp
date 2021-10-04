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
CommerceOrderRuleEntryDisplayContext commerceOrderRuleEntryDisplayContext = (CommerceOrderRuleEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderRuleEntry commerceOrderRuleEntry = commerceOrderRuleEntryDisplayContext.getCommerceOrderRuleEntry();
%>

<portlet:actionURL name="/commerce_order_rule_entry/edit_commerce_order_rule_entry_external_reference_code" var="editCommerceOrderRuleEntryExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommerceOrderRuleEntryExternalReferenceCodeURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceOrderRuleEntryId" type="hidden" value="<%= commerceOrderRuleEntry.getCommerceOrderRuleEntryId() %>" />

		<aui:model-context bean="<%= commerceOrderRuleEntry %>" model="<%= CommerceOrderRuleEntry.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= commerceOrderRuleEntry.getExternalReferenceCode() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>