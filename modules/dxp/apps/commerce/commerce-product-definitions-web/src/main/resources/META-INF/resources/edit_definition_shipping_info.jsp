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

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(catalogURL);

renderResponse.setTitle(cpDefinition.getTitle(languageId));
%>

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionShippingInfoActionURL" />

<aui:form action="<%= editProductDefinitionShippingInfoActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateShippingInfo" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="shippable" value="<%= shippable %>" />

			<div class="<%= shippable ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />shippableOptions">
				<aui:input name="freeShipping" />

				<aui:input name="shipSeparately" />

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