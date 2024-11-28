<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryWarehouseQualifiersDisplayContext cIWarehouseQualifiersDisplayContext = (CommerceInventoryWarehouseQualifiersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouse commerceInventoryWarehouse = cIWarehouseQualifiersDisplayContext.getCommerceInventoryWarehouse();

PortletURL portletCommerceInventoryWarehouseURL = cIWarehouseQualifiersDisplayContext.getPortletCommerceInventoryWarehouseURL();

long commerceInventoryWarehouseId = cIWarehouseQualifiersDisplayContext.getCommerceInventoryWarehouseId();

String accountQualifiers = ParamUtil.getString(request, "accountQualifiers", cIWarehouseQualifiersDisplayContext.getActiveAccountEligibility());
String channelQualifiers = ParamUtil.getString(request, "channelQualifiers", cIWarehouseQualifiersDisplayContext.getActiveChannelEligibility());
%>

<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse_qualifiers" var="editCommerceInventoryWarehouseQualifiersActionURL" />

<aui:form action="<%= editCommerceInventoryWarehouseQualifiersActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="accountQualifiers" type="hidden" value="<%= accountQualifiers %>" />
	<aui:input name="channelQualifiers" type="hidden" value="<%= channelQualifiers %>" />
	<aui:input name="commerceInventoryWarehouseId" type="hidden" value="<%= commerceInventoryWarehouseId %>" />

	<aui:model-context bean="<%= commerceInventoryWarehouse %>" model="<%= CommerceInventoryWarehouse.class %>" />

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				collapsed="<%= false %>"
				collapsible="<%= false %>"
				title='<%= LanguageUtil.get(request, "account-eligibility") %>'
			>
				<div class="row">
					<aui:fieldset markupView="lexicon">
						<aui:input checked='<%= Objects.equals(accountQualifiers, "all") %>' label="all-accounts" name="qualifiers--account--" type="radio" value="all" />
						<aui:input checked='<%= Objects.equals(accountQualifiers, "accountGroups") %>' label="specific-account-groups" name="qualifiers--account--" type="radio" value="accountGroups" />
						<aui:input checked='<%= Objects.equals(accountQualifiers, "accounts") %>' label="specific-accounts" name="qualifiers--account--" type="radio" value="accounts" />
					</aui:fieldset>
				</div>
			</commerce-ui:panel>
		</div>
	</div>

	<c:if test='<%= Objects.equals(accountQualifiers, "accounts") %>'>
		<%@ include file="/commerce_inventory_warehouse/qualifier/accounts.jspf" %>
	</c:if>

	<c:if test='<%= Objects.equals(accountQualifiers, "accountGroups") %>'>
		<%@ include file="/commerce_inventory_warehouse/qualifier/account_groups.jspf" %>
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
						<aui:input checked='<%= Objects.equals(channelQualifiers, "none") %>' label="no-channel" name="qualifiers--channel--" type="radio" value="none" />
						<aui:input checked='<%= Objects.equals(channelQualifiers, "channels") %>' label="specific-channels" name="qualifiers--channel--" type="radio" value="channels" />
					</aui:fieldset>
				</div>
			</commerce-ui:panel>
		</div>
	</div>

	<c:if test='<%= Objects.equals(channelQualifiers, "channels") %>'>
		<%@ include file="/commerce_inventory_warehouse/qualifier/channels.jspf" %>
	</c:if>
</aui:form>

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
	module="{qualifiers} from commerce-warehouse-web"
/>

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
	module="{qualifiers} from commerce-warehouse-web"
/>