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

CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

long commercePriceEntryId = commercePriceEntryDisplayContext.getCommercePriceEntryId();
long commercePriceListId = commercePriceEntryDisplayContext.getCommercePriceListId();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-price-entry-details");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "editCommercePriceEntry");
portletURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
portletURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));
portletURL.setParameter("toolbarItem", toolbarItem);

PortletURL editPriceListURL = renderResponse.createRenderURL();

editPriceListURL.setParameter("mvcRenderCommandName", "editCommercePriceList");
editPriceListURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));

editPriceListURL.setParameter("screenNavigationCategoryKey", CommercePriceListScreenNavigationConstants.CATEGORY_KEY_ENTRIES);

renderResponse.setTitle(LanguageUtil.get(request, "price-lists"));
%>

<%@ include file="/price_entry_navbar.jspf" %>

<portlet:actionURL name="editCommercePriceEntry" var="editCommercePriceEntryActionURL" />

<aui:form action="<%= editCommercePriceEntryActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListId %>" />

	<div class="row">
		<div class="col-12">
			<%@ include file="/price_entry/details.jspf" %>

			<%@ include file="/price_entry/custom_fields.jspf" %>
		</div>
	</div>

	<aui:button-row cssClass="price-entry-button-row">
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= editPriceListURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>