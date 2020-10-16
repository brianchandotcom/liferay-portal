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
CPDefinitionConfigurationDisplayContext cpDefinitionConfigurationDisplayContext = (CPDefinitionConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDAvailabilityEstimate cpdAvailabilityEstimate = cpDefinitionConfigurationDisplayContext.getCPDAvailabilityEstimate();
CPDefinition cpDefinition = cpDefinitionConfigurationDisplayContext.getCPDefinition();
long cpDefinitionId = cpDefinitionConfigurationDisplayContext.getCPDefinitionId();
CPDefinitionInventory cpDefinitionInventory = cpDefinitionConfigurationDisplayContext.getCPDefinitionInventory();
List<CPTaxCategory> cpTaxCategories = cpDefinitionConfigurationDisplayContext.getCPTaxCategories();

boolean shippable = BeanParamUtil.getBoolean(cpDefinition, request, "shippable", true);
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionConfigurationActionURL" />

<aui:form action="<%= editProductDefinitionConfigurationActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateConfiguration" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionInventoryId" type="hidden" value="<%= (cpDefinitionInventory == null) ? StringPool.BLANK : cpDefinitionInventory.getCPDefinitionInventoryId() %>" />
	<aui:input name="cpdAvailabilityEstimateId" type="hidden" value="<%= (cpdAvailabilityEstimate == null) ? StringPool.BLANK : cpdAvailabilityEstimate.getCPDAvailabilityEstimateId() %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

	<div class="row">
		<div class="col-8">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "tax-category") %>'
			>
				<div class="row">
					<div class="col-6">
						<aui:select label="tax-category" name="cpTaxCategoryId" showEmptyOption="<%= true %>">

							<%
							for (CPTaxCategory cpTaxCategory : cpTaxCategories) {
							%>

								<aui:option label="<%= HtmlUtil.escape(cpTaxCategory.getName(locale)) %>" selected="<%= (cpDefinition != null) && (cpDefinition.getCPTaxCategoryId() == cpTaxCategory.getCPTaxCategoryId()) %>" value="<%= cpTaxCategory.getCPTaxCategoryId() %>" />

							<%
							}
							%>

						</aui:select>
					</div>

					<div class="col-6">
						<aui:input checked="<%= (cpDefinition == null) ? false : cpDefinition.isTaxExempt() %>" name="taxExempt" type="toggle-switch" />
					</div>
				</div>
			</commerce-ui:panel>

			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "inventory") %>'
			>
				<div class="row">
					<aui:model-context bean="<%= cpDefinitionInventory %>" model="<%= CPDefinitionInventory.class %>" />

					<div class="col-6">
						<aui:select label="inventory-engine" name="CPDefinitionInventoryEngine">

							<%
							for (CPDefinitionInventoryEngine cpDefinitionInventoryEngine : cpDefinitionConfigurationDisplayContext.getCPDefinitionInventoryEngines()) {
								String cpDefinitionInventoryEngineName = cpDefinitionInventoryEngine.getKey();
							%>

								<aui:option label="<%= HtmlUtil.escape(cpDefinitionInventoryEngine.getLabel(locale)) %>" selected="<%= (cpDefinitionInventory != null) && cpDefinitionInventoryEngineName.equals(cpDefinitionInventory.getCPDefinitionInventoryEngine()) %>" value="<%= HtmlUtil.escape(cpDefinitionInventoryEngineName) %>" />

							<%
							}
							%>

						</aui:select>

						<aui:select label="availability-estimate" name="commerceAvailabilityEstimateId" showEmptyOption="<%= true %>">

							<%
							for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : cpDefinitionConfigurationDisplayContext.getCommerceAvailabilityEstimates()) {
							%>

								<aui:option label="<%= HtmlUtil.escape(commerceAvailabilityEstimate.getTitle(languageId)) %>" selected="<%= (cpdAvailabilityEstimate != null) && (commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId() == cpdAvailabilityEstimate.getCommerceAvailabilityEstimateId()) %>" value="<%= commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId() %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input checked="<%= (cpDefinitionInventory == null) ? false : cpDefinitionInventory.getDisplayAvailability() %>" inlineField="<%= true %>" name="displayAvailability" type="toggle-switch" />

						<aui:input checked="<%= (cpDefinitionInventory == null) ? false : cpDefinitionInventory.isDisplayStockQuantity() %>" inlineField="<%= true %>" name="displayStockQuantity" type="toggle-switch" />

						<aui:input name="minOrderQuantity" value="<%= (cpDefinitionInventory == null) ? String.valueOf(CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY) : String.valueOf(cpDefinitionInventory.getMinOrderQuantity()) %>">
							<aui:validator name="digits" />
							<aui:validator name="min">1</aui:validator>
						</aui:input>

						<aui:input helpMessage="separate-values-with-a-comma-period-or-space" name="allowedOrderQuantities" />
					</div>

					<div class="col-6">
						<aui:select label="low-stock-action" name="lowStockActivity">
							<aui:option selected="<%= cpDefinitionInventory == null %>" value="" />

							<%
							for (CommerceLowStockActivity commerceLowStockActivity : cpDefinitionConfigurationDisplayContext.getCommerceLowStockActivities()) {
								String commerceLowStockActivityName = commerceLowStockActivity.getKey();
							%>

								<aui:option label="<%= HtmlUtil.escape(commerceLowStockActivity.getLabel(locale)) %>" selected="<%= (cpDefinitionInventory != null) && commerceLowStockActivityName.equals(cpDefinitionInventory.getLowStockActivity()) %>" value="<%= HtmlUtil.escape(commerceLowStockActivityName) %>" />

							<%
							}
							%>

						</aui:select>

						<liferay-ui:error exception="<%= NumberFormatException.class %>" message="there-was-an-error-processing-one-or-more-of-the-quantities-entered" />

						<aui:input label="low-stock-threshold" name="minStockQuantity">
							<aui:validator name="digits" />
						</aui:input>

						<aui:input checked="<%= (cpDefinitionInventory == null) ? false : cpDefinitionInventory.getBackOrders() %>" label="allow-back-orders" name="backOrders" type="toggle-switch" />

						<aui:input name="maxOrderQuantity" value="<%= (cpDefinitionInventory == null) ? String.valueOf(CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY) : String.valueOf(cpDefinitionInventory.getMaxOrderQuantity()) %>">
							<aui:validator name="digits" />
							<aui:validator name="min">1</aui:validator>
						</aui:input>

						<aui:input name="multipleOrderQuantity" value="<%= (cpDefinitionInventory == null) ? String.valueOf(CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY) : String.valueOf(cpDefinitionInventory.getMultipleOrderQuantity()) %>">
							<aui:validator name="digits" />
							<aui:validator name="min">1</aui:validator>
						</aui:input>
					</div>
				</div>
			</commerce-ui:panel>
		</div>

		<div class="col-4">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "shipping") %>'
			>
				<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

				<aui:input checked="<%= shippable %>" name="shippable" type="toggle-switch" value="<%= shippable %>" />

				<div class="<%= shippable ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />shippableOptions">
					<aui:input checked='<%= BeanParamUtil.getBoolean(cpDefinition, request, "freeShipping", false) %>' inlineField="<%= true %>" name="freeShipping" type="toggle-switch" />

					<aui:input checked='<%= BeanParamUtil.getBoolean(cpDefinition, request, "shipSeparately", false) %>' inlineField="<%= true %>" label="always-ship-separately" name="shipSeparately" type="toggle-switch" />

					<aui:input name="shippingExtraPrice" suffix="<%= HtmlUtil.escape(cpDefinitionConfigurationDisplayContext.getCommerceCurrencyCode()) %>" />
				</div>

				<aui:input name="width" suffix="<%= HtmlUtil.escape(cpDefinitionConfigurationDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_DIMENSION)) %>" />

				<aui:input name="height" suffix="<%= HtmlUtil.escape(cpDefinitionConfigurationDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_DIMENSION)) %>" />

				<aui:input name="depth" suffix="<%= HtmlUtil.escape(cpDefinitionConfigurationDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_DIMENSION)) %>" />

				<aui:input name="weight" suffix="<%= HtmlUtil.escape(cpDefinitionConfigurationDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_WEIGHT)) %>" />
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />shippable',
		'<portlet:namespace />shippableOptions'
	);
</aui:script>