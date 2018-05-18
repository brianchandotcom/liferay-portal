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
			<aui:input name="price" suffix="<%= cpInstancePricingInfoDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= cpInstancePricingInfoDisplayContext.format(cpInstance.getPrice()) %>" />

			<aui:input name="promoPrice" suffix="<%= cpInstancePricingInfoDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= cpInstancePricingInfoDisplayContext.format(cpInstance.getPromoPrice()) %>" />

			<aui:input name="cost" suffix="<%= cpInstancePricingInfoDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= cpInstancePricingInfoDisplayContext.format(cpInstance.getCost()) %>" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= productSkusURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>