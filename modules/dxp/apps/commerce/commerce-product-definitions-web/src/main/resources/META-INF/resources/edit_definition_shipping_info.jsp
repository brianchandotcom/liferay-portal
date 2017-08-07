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

PortletURL portletURL = cpDefinitionShippingInfoDisplayContext.getPortletURL();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "edit-product-definition-shipping-info");

portletURL.setParameter("toolbarItem", toolbarItem);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(catalogURL);

renderResponse.setTitle(cpDefinition.getTitle(languageId));

request.setAttribute("view.jsp-cpDefinition", cpDefinition);
request.setAttribute("view.jsp-cpType", cpDefinitionShippingInfoDisplayContext.getCPType());
request.setAttribute("view.jsp-portletURL", portletURL);
request.setAttribute("view.jsp-toolbarItem", toolbarItem);
%>

<liferay-util:include page="/definition_navbar.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionShippingInfoActionURL" />

<aui:form action="<%= editProductDefinitionShippingInfoActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateShippingInfo" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

	<aui:fieldset>
		<aui:input name="width" suffix="<%= cpDefinitionShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION) %>" />

		<aui:input name="height" suffix="<%= cpDefinitionShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION) %>" />

		<aui:input name="depth" suffix="<%= cpDefinitionShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION) %>" />

		<aui:input name="weight" suffix="<%= cpDefinitionShippingInfoDisplayContext.getCPMeasurementUnitName(CPConstants.MEASUREMENT_UNIT_TYPE_WEIGHT) %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
	</aui:button-row>
</aui:form>