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
COREntryQualifiersDisplayContext corEntryQualifiersDisplayContext = (COREntryQualifiersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

COREntry corEntry = corEntryQualifiersDisplayContext.getCOREntry();
long corEntryId = corEntryQualifiersDisplayContext.getCOREntryId();

String accountQualifiers = ParamUtil.getString(request, "accountQualifiers", corEntryQualifiersDisplayContext.getActiveAccountEligibility());
String channelQualifiers = ParamUtil.getString(request, "channelQualifiers", corEntryQualifiersDisplayContext.getActiveChannelEligibility());
String orderTypeQualifiers = ParamUtil.getString(request, "orderTypeQualifiers", corEntryQualifiersDisplayContext.getActiveOrderTypeEligibility());

boolean hasPermission = corEntryQualifiersDisplayContext.hasPermission(ActionKeys.UPDATE);
%>

<portlet:actionURL name="/cor_entry/edit_cor_entry_qualifiers" var="editCOREntryQualifiersActionURL" />

<aui:form action="<%= editCOREntryQualifiersActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (corEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= corEntry.getExternalReferenceCode() %>" />
	<aui:input name="corEntryId" type="hidden" value="<%= corEntryId %>" />
	<aui:input name="accountQualifiers" type="hidden" value="<%= accountQualifiers %>" />
	<aui:input name="channelQualifiers" type="hidden" value="<%= channelQualifiers %>" />
	<aui:input name="orderTypeQualifiers" type="hidden" value="<%= orderTypeQualifiers %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<aui:model-context bean="<%= corEntry %>" model="<%= COREntry.class %>" />

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				collapsed="<%= false %>"
				collapsible="<%= false %>"
				title='<%= LanguageUtil.get(request, "account-eligibility") %>'
			>
				<aui:fieldset markupView="lexicon">
					<aui:input checked='<%= Objects.equals(accountQualifiers, "all") %>' label="all-accounts" name="qualifiers--account--" type="radio" value="all" />
					<aui:input checked='<%= Objects.equals(accountQualifiers, "accountGroups") %>' label="specific-account-groups" name="qualifiers--account--" type="radio" value="accountGroups" />
					<aui:input checked='<%= Objects.equals(accountQualifiers, "accounts") %>' label="specific-accounts" name="qualifiers--account--" type="radio" value="accounts" />
				</aui:fieldset>
			</commerce-ui:panel>
		</div>
	</div>

	<c:if test='<%= Objects.equals(accountQualifiers, "accounts") %>'>
		<%@ include file="/cor_entry/qualifier/account_entries.jspf" %>
	</c:if>

	<c:if test='<%= Objects.equals(accountQualifiers, "accountGroups") %>'>
		<%@ include file="/cor_entry/qualifier/account_groups.jspf" %>
	</c:if>

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				collapsed="<%= false %>"
				collapsible="<%= false %>"
				title='<%= LanguageUtil.get(request, "channel-eligibility") %>'
			>
				<div class="row">
					<aui:fieldset markupView="lexicon">
						<aui:input checked='<%= Objects.equals(channelQualifiers, "all") %>' label="all-channels" name="qualifiers--channel--" type="radio" value="all" />
						<aui:input checked='<%= Objects.equals(channelQualifiers, "channels") %>' label="specific-channels" name="qualifiers--channel--" type="radio" value="channels" />
					</aui:fieldset>
				</div>
			</commerce-ui:panel>
		</div>
	</div>

	<c:if test='<%= Objects.equals(channelQualifiers, "channels") %>'>
		<%@ include file="/cor_entry/qualifier/commerce_channels.jspf" %>
	</c:if>

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
		<%@ include file="/cor_entry/qualifier/commerce_order_types.jspf" %>
	</c:if>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"currentURL", currentURL
		).put(
			"searchParam", "accountQualifiers"
		).put(
			"selector", "qualifiers--account--"
		).build()
	%>'
	module="js/qualifiers"
/>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"currentURL", currentURL
		).put(
			"searchParam", "channelQualifiers"
		).put(
			"selector", "qualifiers--channel--"
		).build()
	%>'
	module="js/qualifiers"
/>

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