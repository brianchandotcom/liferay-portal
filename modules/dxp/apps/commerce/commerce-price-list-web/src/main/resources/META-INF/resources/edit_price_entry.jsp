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
CommercePriceEntryDisplayContext commercePriceEntryDisplayContext = (CommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceEntry commercePriceEntry = commercePriceEntryDisplayContext.getCommercePriceEntry();

CommercePriceList commercePriceList = commercePriceEntryDisplayContext.getCommercePriceList();

long commercePriceEntryId = commercePriceEntryDisplayContext.getCommercePriceEntryId();
long commercePriceListId = commercePriceEntryDisplayContext.getCommercePriceListId();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-price-entry-details");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "editCommercePriceEntry");
portletURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
portletURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));
portletURL.setParameter("toolbarItem", toolbarItem);

PortletURL priceEntriesURL = renderResponse.createRenderURL();

priceEntriesURL.setParameter("mvcRenderCommandName", "viewCommercePriceEntries");
priceEntriesURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(priceEntriesURL.toString());

renderResponse.setTitle((commercePriceList == null) ? LanguageUtil.get(request, "add-price-list") : commercePriceList.getName());
%>

<%@ include file="/price_entry_navbar.jspf" %>

<portlet:actionURL name="editCommercePriceEntry" var="editCommercePriceEntryActionURL" />

<aui:form action="<%= editCommercePriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListId %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= priceEntriesURL.toString() %>"
			formModelBean="<%= commercePriceEntry %>"
			id="<%= CommercePriceEntryFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRICE_ENTRY %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>