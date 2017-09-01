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
CPDefinitionPricingInfoDisplayContext cpDefinitionPricingInfoDisplayContext = (CPDefinitionPricingInfoDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionPricingInfoDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionPricingInfoDisplayContext.getCPDefinitionId();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(catalogURL);

renderResponse.setTitle(cpDefinition.getTitle(languageId));
%>

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionPricingInfoActionURL" />

<aui:form action="<%= editProductDefinitionPricingInfoActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updatePricingInfo" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

	<aui:fieldset>
		<aui:input name="cost" suffix="<%= cpDefinitionPricingInfoDisplayContext.getCommerceCurrencyCode() %>" />

		<aui:input name="price" suffix="<%= cpDefinitionPricingInfoDisplayContext.getCommerceCurrencyCode() %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
	</aui:button-row>
</aui:form>