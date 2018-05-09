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
CommerceTierPriceEntryDisplayContext commerceTierPriceEntryDisplayContext = (CommerceTierPriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTierPriceEntry commerceTierPriceEntry = commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntry();

CommercePriceList commercePriceList = commerceTierPriceEntryDisplayContext.getCommercePriceList();

CommercePriceEntry commercePriceEntry = commerceTierPriceEntryDisplayContext.getCommercePriceEntry();

CommerceCurrency commerceCurrency = commerceTierPriceEntryDisplayContext.getCommercePriceListCurrency();

CPInstance cpInstance = commercePriceEntry.getCPInstance();
CPDefinition cpDefinition = cpInstance.getCPDefinition();

long commercePriceEntryId = commerceTierPriceEntryDisplayContext.getCommercePriceEntryId();
long commercePriceListId = commerceTierPriceEntryDisplayContext.getCommercePriceListId();
long commerceTierPriceEntryId = commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryId();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-tier-price-entries");

PortletURL editPriceListURL = renderResponse.createRenderURL();

editPriceListURL.setParameter("mvcRenderCommandName", "editCommercePriceList");
editPriceListURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));

PortletURL editPriceEntryURL = renderResponse.createRenderURL();

editPriceEntryURL.setParameter("mvcRenderCommandName", "editCommercePriceEntry");
editPriceEntryURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
editPriceEntryURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));

PortletURL tierPriceEntriesURL = renderResponse.createRenderURL();

tierPriceEntriesURL.setParameter("mvcRenderCommandName", "viewCommerceTierPriceEntries");
tierPriceEntriesURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
tierPriceEntriesURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));
tierPriceEntriesURL.setParameter("toolbarItem", toolbarItem);

String title = commerceTierPriceEntryDisplayContext.getContextTitle();

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "price-lists"), priceListsURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, commercePriceList.getName(), editPriceListURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, cpDefinition.getName(languageId), editPriceEntryURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "tier-price-entries"), tierPriceEntriesURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "price-lists"));
%>

<%@ include file="/price_list_navbar.jspf" %>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceTierPriceEntry" var="editCommerceTierPriceEntryActionURL" />

<aui:form action="<%= editCommerceTierPriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= tierPriceEntriesURL.toString() %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="commerceTierPriceEntryId" type="hidden" value="<%= commerceTierPriceEntryId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceTierPriceEntry %>" model="<%= CommerceTierPriceEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="price" suffix="<%= commerceCurrency.getCode() %>" type="text" value="<%= commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryPrice(commerceTierPriceEntry) %>" />

				<aui:input name="promoPrice" suffix="<%= commerceCurrency.getCode() %>" type="text" value="<%= commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryPromoPrice(commerceTierPriceEntry) %>" />

				<aui:input name="minQuantity" />
			</aui:fieldset>
		</aui:fieldset-group>

		<c:if test="<%= commerceTierPriceEntryDisplayContext.hasCustomAttributes() %>">
			<aui:fieldset>
				<liferay-expando:custom-attribute-list
					className="<%= CommerceTierPriceEntry.class.getName() %>"
					classPK="<%= (commerceTierPriceEntry != null) ? commerceTierPriceEntry.getCommerceTierPriceEntryId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</aui:fieldset>
		</c:if>

		<aui:button-row cssClass="tier-price-entry-button-row">
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= tierPriceEntriesURL.toString() %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>