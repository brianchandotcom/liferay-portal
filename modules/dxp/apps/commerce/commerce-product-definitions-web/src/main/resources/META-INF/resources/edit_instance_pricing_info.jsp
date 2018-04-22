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
CPInstancePricingInfoDisplayContext cpInstancePricingInfoDisplayContext = (CPInstancePricingInfoDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpInstancePricingInfoDisplayContext.getCPDefinition();

CPInstance cpInstance = cpInstancePricingInfoDisplayContext.getCPInstance();

long cpInstanceId = cpInstancePricingInfoDisplayContext.getCPInstanceId();

PortletURL productSkusURL = renderResponse.createRenderURL();

productSkusURL.setParameter("mvcRenderCommandName", "editProductDefinition");
productSkusURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
productSkusURL.setParameter("screenNavigationCategoryKey", cpInstancePricingInfoDisplayContext.getScreenNavigationCategoryKey());
%>

<portlet:actionURL name="editProductInstance" var="editProductInstancePricingInfoActionURL" />

<aui:form action="<%= editProductInstancePricingInfoActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updatePricingInfo" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />

	<aui:model-context bean="<%= cpInstance %>" model="<%= CPInstance.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="cost" suffix="<%= cpInstancePricingInfoDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= cpInstance.getCost().toPlainString() %>" />

			<aui:input name="price" suffix="<%= cpInstancePricingInfoDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= cpInstance.getPrice().toPlainString() %>" />

			<aui:input name="promoPrice" suffix="<%= cpInstancePricingInfoDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= cpInstance.getPromoPrice().toPlainString() %>" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= productSkusURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>