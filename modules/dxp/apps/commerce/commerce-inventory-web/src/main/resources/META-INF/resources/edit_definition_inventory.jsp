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
CPDefinitionInventoryDisplayContext cpDefinitionInventoryDisplayContext = (CPDefinitionInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionInventory cpDefinitionInventory = cpDefinitionInventoryDisplayContext.getCPDefinitionInventory();

CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = cpDefinitionInventoryDisplayContext.getCPDefinitionAvailabilityRange();

CPDefinition cpDefinition = cpDefinitionInventoryDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionInventoryDisplayContext.getCPDefinitionId();
%>

<portlet:actionURL name="editProductDefinitionInventory" var="editProductDefinitionInventoryActionURL" />

<aui:form action="<%= editProductDefinitionInventoryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDefinitionInventory == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionInventoryId" type="hidden" value="<%= (cpDefinitionInventory == null) ? StringPool.BLANK : cpDefinitionInventory.getCPDefinitionInventoryId() %>" />
	<aui:input name="cpDefinitionAvailabilityRangeId" type="hidden" value="<%= (cpDefinitionAvailabilityRange == null) ? StringPool.BLANK : cpDefinitionAvailabilityRange.getCPDefinitionAvailabilityRangeId() %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= cpDefinitionInventory %>" model="<%= CPDefinitionInventory.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:select label="inventory-engine" name="CPDefinitionInventoryEngine" showEmptyOption="<%= true %>">

					<%
					List<CPDefinitionInventoryEngine> cpDefinitionInventoryEngines = cpDefinitionInventoryDisplayContext.getCPDefinitionInventoryEngines();

					for (CPDefinitionInventoryEngine cpDefinitionInventoryEngine : cpDefinitionInventoryEngines) {
						String cpDefinitionInventoryEngineName = cpDefinitionInventoryEngine.getKey();
					%>

						<aui:option
							label="<%= cpDefinitionInventoryEngine.getLabel(locale) %>"
							selected="<%= (cpDefinitionInventory != null) && cpDefinitionInventoryEngineName.equals(cpDefinitionInventory.getCPDefinitionInventoryEngine()) %>"
							value="<%= cpDefinitionInventoryEngineName %>"
						/>

					<%
					}
					%>

				</aui:select>

				<aui:select name="lowStockActivity" showEmptyOption="<%= true %>">

					<%
					List<CommerceLowStockActivity> commerceLowStockActivities = cpDefinitionInventoryDisplayContext.getCommerceLowStockActivities();

					for (CommerceLowStockActivity commerceLowStockActivity : commerceLowStockActivities) {
						String commerceLowStockActivityName = commerceLowStockActivity.getKey();
					%>

						<aui:option
							label="<%= commerceLowStockActivity.getLabel(locale) %>"
							selected="<%= (cpDefinitionInventory != null) && commerceLowStockActivityName.equals(cpDefinitionInventory.getLowStockActivity()) %>"
							value="<%= commerceLowStockActivityName %>"
						/>

					<%
					}
					%>

				</aui:select>

				<aui:select label="availability-range" name="commerceAvailabilityRangeId" showEmptyOption="<%= true %>">

					<%
					List<CommerceAvailabilityRange> commerceAvailabilityRanges = cpDefinitionInventoryDisplayContext.getCommerceAvailabilityRanges();

					for (CommerceAvailabilityRange commerceAvailabilityRange : commerceAvailabilityRanges) {
					%>

						<aui:option
							label="<%= commerceAvailabilityRange.getTitle(languageId) %>"
							selected="<%= (cpDefinitionAvailabilityRange != null) && (commerceAvailabilityRange.getCommerceAvailabilityRangeId() == cpDefinitionAvailabilityRange.getCommerceAvailabilityRangeId()) %>"
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
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>