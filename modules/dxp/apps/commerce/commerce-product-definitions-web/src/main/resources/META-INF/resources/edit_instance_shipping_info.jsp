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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "edit-product-instance-shipping-info");

CPInstanceShippingInfoDisplayContext cpInstanceShippingInfoDisplayContext = (CPInstanceShippingInfoDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpInstanceShippingInfoDisplayContext.getCPDefinition();

CPInstance cpInstance = cpInstanceShippingInfoDisplayContext.getCPInstance();

long cpInstanceId = cpInstanceShippingInfoDisplayContext.getCPInstanceId();

PortletURL productSkusURL = renderResponse.createRenderURL();

productSkusURL.setParameter("mvcRenderCommandName", "viewProductInstances");
productSkusURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(productSkusURL.toString());

renderResponse.setTitle(cpDefinition.getTitle(languageId) + " - " + cpInstance.getSku());

request.setAttribute("view.jsp-cpInstance", cpInstance);
request.setAttribute("view.jsp-toolbarItem", toolbarItem);
%>

<liferay-util:include page="/instance_navbar.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="editProductInstance" var="editProductInstanceShippingInfoActionURL" />

<aui:form action="<%= editProductInstanceShippingInfoActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateShippingInfo" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />

	<aui:model-context bean="<%= cpInstance %>" model="<%= CPInstance.class %>" />

	<aui:fieldset>
		<aui:input name="width" suffix="<%= cpInstanceShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION) %>" />

		<aui:input name="height" suffix="<%= cpInstanceShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION) %>" />

		<aui:input name="depth" suffix="<%= cpInstanceShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION) %>" />

		<aui:input name="weight" suffix="<%= cpInstanceShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_WEIGHT) %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= productSkusURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>