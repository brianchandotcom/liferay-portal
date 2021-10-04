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
CommerceOrderRuleEntryQualifiersDisplayContext commerceOrderRuleEntryQualifiersDisplayContext = (CommerceOrderRuleEntryQualifiersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderRuleEntry commerceOrderRuleEntry = commerceOrderRuleEntryQualifiersDisplayContext.getCommerceOrderRuleEntry();
long commerceOrderRuleEntryId = commerceOrderRuleEntryQualifiersDisplayContext.getCommerceOrderRuleEntryId();

String channelQualifiers = ParamUtil.getString(request, "channelQualifiers", commerceOrderRuleEntryQualifiersDisplayContext.getActiveChannelEligibility());

boolean hasPermission = commerceOrderRuleEntryQualifiersDisplayContext.hasPermission(ActionKeys.UPDATE);
%>

<portlet:actionURL name="/commerce_order_rule_entry/edit_commerce_order_rule_entry_qualifiers" var="editCommerceOrderRuleEntryQualifiersActionURL" />

<aui:form action="<%= editCommerceOrderRuleEntryQualifiersActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceOrderRuleEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= commerceOrderRuleEntry.getExternalReferenceCode() %>" />
	<aui:input name="commerceOrderRuleEntryId" type="hidden" value="<%= commerceOrderRuleEntryId %>" />
	<aui:input name="channelQualifiers" type="hidden" value="<%= channelQualifiers %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<aui:model-context bean="<%= commerceOrderRuleEntry %>" model="<%= CommerceOrderRuleEntry.class %>" />

	<commerce-ui:panel
		bodyClasses="flex-fill"
		collapsed="<%= false %>"
		collapsible="<%= false %>"
		title='<%= LanguageUtil.get(request, "channel-eligibility") %>'
	>
		<aui:fieldset markupView="lexicon">
			<aui:input checked='<%= Objects.equals(channelQualifiers, "all") %>' label="all-channels" name="chooseChannelQualifiers" type="radio" value="all" />
			<aui:input checked='<%= Objects.equals(channelQualifiers, "channels") %>' label="specific-channels" name="chooseChannelQualifiers" type="radio" value="channels" />
		</aui:fieldset>
	</commerce-ui:panel>

	<c:if test='<%= Objects.equals(channelQualifiers, "channels") %>'>
		<%@ include file="/commerce_order_rule_entry/qualifier/channels.jspf" %>
	</c:if>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"currentURL", currentURL
		).build()
	%>'
	module="js/qualifiers"
/>