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
CommerceInventoryWarehouseQualifiersDisplayContext cIWarehouseQualifiersDisplayContext = (CommerceInventoryWarehouseQualifiersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouse commerceInventoryWarehouse = cIWarehouseQualifiersDisplayContext.getCommerceInventoryWarehouse();

PortletURL portletCommerceInventoryWarehouseURL = cIWarehouseQualifiersDisplayContext.getPortletCommerceInventoryWarehouseURL();

long commerceInventoryWarehouseId = cIWarehouseQualifiersDisplayContext.getCommerceInventoryWarehouseId();

String channelQualifiers = ParamUtil.getString(request, "channelQualifiers", cIWarehouseQualifiersDisplayContext.getActiveChannelEligibility());

boolean hasPermission = cIWarehouseQualifiersDisplayContext.hasPermission(ActionKeys.UPDATE);
%>

<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse_qualifiers" var="editCommerceInventoryWarehouseQualifiersActionURL" />

<aui:form action="<%= editCommerceInventoryWarehouseQualifiersActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="channelQualifiers" type="hidden" value="<%= channelQualifiers %>" />
	<aui:input name="commerceInventoryWarehouseId" type="hidden" value="<%= commerceInventoryWarehouseId %>" />

	<aui:model-context bean="<%= commerceInventoryWarehouse %>" model="<%= CommerceInventoryWarehouse.class %>" />

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
	module="js/qualifiers"
/>