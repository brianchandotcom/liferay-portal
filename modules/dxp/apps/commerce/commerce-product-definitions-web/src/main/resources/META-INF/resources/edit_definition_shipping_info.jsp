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
CPDefinitionShippingInfoDisplayContext cpDefinitionShippingInfoDisplayContext = (CPDefinitionShippingInfoDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionShippingInfoDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionShippingInfoDisplayContext.getCPDefinitionId();

boolean shippable = BeanParamUtil.getBoolean(cpDefinition, request, "shippable", true);
boolean freeShipping = BeanParamUtil.getBoolean(cpDefinition, request, "freeShipping", false);
boolean shipSeparately = BeanParamUtil.getBoolean(cpDefinition, request, "shipSeparately", false);
%>

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionShippingInfoActionURL" />

<aui:form action="<%= editProductDefinitionShippingInfoActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateShippingInfo" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input checked="<%= shippable %>" name="shippable" type="toggle-switch" value="<%= shippable %>" />

			<div class="<%= shippable ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />shippableOptions">
				<aui:input checked="<%= freeShipping %>" name="freeShipping" type="toggle-switch" />

				<aui:input checked="<%= shipSeparately %>" name="shipSeparately" type="toggle-switch" />

				<aui:input name="shippingExtraPrice" />
			</div>

			<aui:input name="width" suffix="<%= cpDefinitionShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION) %>" />

			<aui:input name="height" suffix="<%= cpDefinitionShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION) %>" />

			<aui:input name="depth" suffix="<%= cpDefinitionShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION) %>" />

			<aui:input name="weight" suffix="<%= cpDefinitionShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_WEIGHT) %>" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />shippable', '<portlet:namespace />shippableOptions');
</aui:script>