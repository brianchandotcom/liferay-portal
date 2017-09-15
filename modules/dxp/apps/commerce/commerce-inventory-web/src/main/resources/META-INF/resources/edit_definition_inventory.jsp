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
CommerceInventoryDisplayContext commerceInventoryDisplayContext = (CommerceInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventory commerceInventory = commerceInventoryDisplayContext.getCommerceInventory();

CAvailabilityRangeEntry cAvailabilityRangeEntry = commerceInventoryDisplayContext.getCAvailabilityRangeEntry();

CPDefinition cpDefinition = commerceInventoryDisplayContext.getCPDefinition();

long cpDefinitionId = commerceInventoryDisplayContext.getCPDefinitionId();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(catalogURL);

renderResponse.setTitle(cpDefinition.getTitle(languageId));
%>

<portlet:actionURL name="editProductDefinitionInventory" var="editProductDefinitionInventoryActionURL" />

<aui:form action="<%= editProductDefinitionInventoryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceInventory == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cAvailabilityRangeEntryId" type="hidden" value="<%= (cAvailabilityRangeEntry == null) ? StringPool.BLANK : cAvailabilityRangeEntry.getCAvailabilityRangeEntryId() %>" />
	<aui:input name="commerceInventoryId" type="hidden" value="<%= (commerceInventory == null) ? StringPool.BLANK : commerceInventory.getCommerceInventoryId() %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceInventory %>" model="<%= CommerceInventory.class %>" />

		<aui:fieldset>
			<aui:select label="inventory-engine" name="commerceInventoryEngine" showEmptyOption="<%= true %>">

				<%
				Map<String, CommerceInventoryEngine> commerceInventoryEngines = commerceInventoryDisplayContext.getCommerceInventoryEngines();

				for (Map.Entry<String, CommerceInventoryEngine> commerceInventoryEngineEntry : commerceInventoryEngines.entrySet()) {
					CommerceInventoryEngine commerceInventoryEngine = commerceInventoryEngineEntry.getValue();

					String commerceInventoryEngineName = commerceInventoryEngine.getName();
				%>

					<aui:option
						label="<%= commerceInventoryEngineName %>"
						selected="<%= (commerceInventory != null) && commerceInventoryEngineName.equals(commerceInventory.getCommerceInventoryEngine()) %>"
					/>

				<%
				}
				%>

			</aui:select>

			<aui:select name="lowStockActivity" showEmptyOption="<%= true %>">

				<%
				Map<String, CommerceLowStockActivity> commerceLowStockActivities = commerceInventoryDisplayContext.getCommerceLowStockActivities();

				for (Map.Entry<String, CommerceLowStockActivity> commerceLowStockActivityEntry : commerceLowStockActivities.entrySet()) {
					CommerceLowStockActivity commerceLowStockActivity = commerceLowStockActivityEntry.getValue();

					String commerceLowStockActivityName = commerceLowStockActivity.getName();
				%>

					<aui:option
						label="<%= commerceLowStockActivityName %>"
						selected="<%= (commerceInventory != null) && commerceLowStockActivityName.equals(commerceInventory.getLowStockActivity()) %>"
					/>

				<%
				}
				%>

			</aui:select>

			<aui:select label="availability-range" name="commerceAvailabilityRangeId" showEmptyOption="<%= true %>">

				<%
				List<CommerceAvailabilityRange> commerceAvailabilityRanges = commerceInventoryDisplayContext.getCommerceAvailabilityRanges();

				for (CommerceAvailabilityRange commerceAvailabilityRange : commerceAvailabilityRanges) {
				%>

					<aui:option
						label="<%= commerceAvailabilityRange.getTitle(languageId) %>"
						selected="<%= (cAvailabilityRangeEntry != null) && (commerceAvailabilityRange.getCommerceAvailabilityRangeId() == cAvailabilityRangeEntry.getCommerceAvailabilityRangeId()) %>"
						value="<%= commerceAvailabilityRange.getCommerceAvailabilityRangeId() %>"
					/>

				<%
				}
				%>

			</aui:select>

			<aui:input name="displayAvailability" />

			<aui:input name="displayStockQuantity" />

			<aui:input name="minStockQuantity" />

			<aui:input name="backOrders" />

			<aui:input name="minCartQuantity" />

			<aui:input name="maxCartQuantity" />

			<aui:input name="allowedCartQuantities" />

			<aui:input name="multipleCartQuantity" />
		</aui:fieldset>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>