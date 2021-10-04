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
long commerceOrderRuleEntryId = commerceOrderRuleEntryDisplayContext.getCommerceOrderRuleEntryId();

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((commerceOrderRuleEntry != null) && (commerceOrderRuleEntry.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<portlet:actionURL name="/commerce_order_rule_entry/edit_commerce_order_rule_entry" var="editCommerceOrderRuleEntryActionURL" />

<aui:form action="<%= editCommerceOrderRuleEntryActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceOrderRuleEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= commerceOrderRuleEntry.getExternalReferenceCode() %>" />
	<aui:input name="commerceOrderRuleEntryId" type="hidden" value="<%= commerceOrderRuleEntryId %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<aui:model-context bean="<%= commerceOrderRuleEntry %>" model="<%= CommerceOrderRuleEntry.class %>" />

	<div class="row">
		<div class="col-12 col-xl-8">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				collapsed="<%= false %>"
				collapsible="<%= false %>"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<div class="row">
					<div class="col">
						<aui:input autoFocus="<%= true %>" label="name" name="name" required="<%= true %>" />
					</div>

					<div class="col-auto">
						<aui:input label='<%= HtmlUtil.escape("active") %>' name="active" type="toggle-switch" value="<%= commerceOrderRuleEntry.isActive() %>" />
					</div>
				</div>

				<div class="row">
					<div class="col">
						<aui:input name="description" type="textarea" value="<%= commerceOrderRuleEntry.getDescription() %>" />
					</div>
				</div>
			</commerce-ui:panel>
		</div>

		<div class="col-12 col-xl-4">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "schedule") %>'
			>
				<liferay-ui:error exception="<%= CommerceOrderRuleEntryExpirationDateException.class %>" message="please-select-a-valid-expiration-date" />

				<aui:input formName="fm" label="publish-date" name="displayDate" />

				<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>