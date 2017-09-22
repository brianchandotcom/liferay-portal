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
CPInstanceCommercePriceEntryDisplayContext cpInstanceCommercePriceEntryDisplayContext = (CPInstanceCommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpInstanceCommercePriceEntryDisplayContext.getCPDefinition();

CPInstance cpInstance = cpInstanceCommercePriceEntryDisplayContext.getCPInstance();

CommercePriceEntry commercePriceEntry = cpInstanceCommercePriceEntryDisplayContext.getCommercePriceEntry();

CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

long commercePriceEntryId = commercePriceEntry.getCommercePriceEntryId();
long cpDefinitionId = cpInstanceCommercePriceEntryDisplayContext.getCPDefinitionId();
long cpInstanceId = cpInstanceCommercePriceEntryDisplayContext.getCPInstanceId();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-price-entry-details");

PortletURL instancePriceListsURL = renderResponse.createRenderURL();

instancePriceListsURL.setParameter("mvcRenderCommandName", "editProductInstance");
instancePriceListsURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionId));
instancePriceListsURL.setParameter("cpInstanceId", String.valueOf(cpInstanceId));
instancePriceListsURL.setParameter("screenNavigationCategoryKey", cpInstanceCommercePriceEntryDisplayContext.getScreenNavigationCategoryKey());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(instancePriceListsURL.toString());

renderResponse.setTitle(cpDefinition.getTitle(languageId) + " - " + cpInstance.getSku() + " - " + commercePriceList.getName());
%>

<%@ include file="/instance_price_entry_navbar.jspf" %>

<portlet:actionURL name="editCPInstanceCommercePriceEntry" var="editCommercePriceEntryActionURL" />

<aui:form action="<%= editCommercePriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= instancePriceListsURL.toString() %>"
			formModelBean="<%= commercePriceEntry %>"
			id="<%= CommercePriceEntryFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRICE_ENTRY %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>