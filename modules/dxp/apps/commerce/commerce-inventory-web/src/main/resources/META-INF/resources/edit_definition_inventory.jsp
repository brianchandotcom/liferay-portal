<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
				<aui:select label="inventory-engine" name="CPDefinitionInventoryEngine">

					<%
					List<CPDefinitionInventoryEngine> cpDefinitionInventoryEngines = cpDefinitionInventoryDisplayContext.getCPDefinitionInventoryEngines();

					for (CPDefinitionInventoryEngine cpDefinitionInventoryEngine : cpDefinitionInventoryEngines) {
						String cpDefinitionInventoryEngineName = cpDefinitionInventoryEngine.getKey();
					%>

						<aui:option label="<%= cpDefinitionInventoryEngine.getLabel(locale) %>" selected="<%= (cpDefinitionInventory != null) && cpDefinitionInventoryEngineName.equals(cpDefinitionInventory.getCPDefinitionInventoryEngine()) %>" value="<%= cpDefinitionInventoryEngineName %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="low-stock-action" name="lowStockActivity">

					<%
					List<CommerceLowStockActivity> commerceLowStockActivities = cpDefinitionInventoryDisplayContext.getCommerceLowStockActivities();

					for (CommerceLowStockActivity commerceLowStockActivity : commerceLowStockActivities) {
						String commerceLowStockActivityName = commerceLowStockActivity.getKey();
					%>

						<aui:option label="<%= commerceLowStockActivity.getLabel(locale) %>" selected="<%= (cpDefinitionInventory != null) && commerceLowStockActivityName.equals(cpDefinitionInventory.getLowStockActivity()) %>" value="<%= commerceLowStockActivityName %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="availability-estimate" name="commerceAvailabilityRangeId" showEmptyOption="<%= true %>">

					<%
					List<CommerceAvailabilityRange> commerceAvailabilityRanges = cpDefinitionInventoryDisplayContext.getCommerceAvailabilityRanges();

					for (CommerceAvailabilityRange commerceAvailabilityRange : commerceAvailabilityRanges) {
					%>

						<aui:option label="<%= commerceAvailabilityRange.getTitle(languageId) %>" selected="<%= (cpDefinitionAvailabilityRange != null) && (commerceAvailabilityRange.getCommerceAvailabilityRangeId() == cpDefinitionAvailabilityRange.getCommerceAvailabilityRangeId()) %>" value="<%= commerceAvailabilityRange.getCommerceAvailabilityRangeId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input name="displayAvailability" />

				<aui:input name="displayStockQuantity" />

				<aui:input name="minStockQuantity" />

				<aui:input label="allow-back-orders" name="backOrders" />

				<aui:input name="minOrderQuantity" />

				<aui:input name="maxOrderQuantity" />

				<aui:input name="allowedOrderQuantities" />

				<aui:input name="multipleOrderQuantity" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>