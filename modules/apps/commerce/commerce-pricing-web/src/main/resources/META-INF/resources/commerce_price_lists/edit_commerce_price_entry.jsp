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

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((commercePriceEntry != null) && (commercePriceEntry.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<portlet:actionURL name="/commerce_price_list/edit_commerce_price_entry" var="editCommercePriceEntryActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "edit-price") %>'
>
	<aui:form action="<%= editCommercePriceEntryActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
		<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListId %>" />

		<aui:model-context bean="<%= commercePriceEntry %>" model="<%= CommercePriceEntry.class %>" />

		<div class="row">
			<div class="col-12">
				<%@ include file="/commerce_price_lists/commerce_price_entry/details.jspf" %>

				<%@ include file="/commerce_price_lists/commerce_price_entry/custom_fields.jspf" %>
			</div>

			<div class="col-12">
				<commerce-ui:panel
					bodyClasses="p-0"
					title='<%= LanguageUtil.get(request, "price-tiers") %>'
				>
					<div class="align-items-center d-flex justify-content-end p-3">
						<div class="mr-3">
							<aui:input checked="<%= commercePriceEntry.isBulkPricing() %>" label="bulk-pricing" name="bulkPricing" type="radio" value="<%= true %>" />
						</div>

						<div>
							<aui:input checked="<%= !commercePriceEntry.isBulkPricing() %>" label="tiered-pricing" name="bulkPricing" type="radio" value="<%= false %>" />
						</div>
					</div>

					<clay:data-set-display
						contextParams='<%=
							HashMapBuilder.<String, String>put(
								"commercePriceEntryId", String.valueOf(commercePriceEntryId)
							).build()
						%>'
						creationMenu="<%= commercePriceEntryDisplayContext.getCreationMenu() %>"
						dataProviderKey="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_TIER_PRICE_ENTRIES %>"
						formId="fm"
						id="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_TIER_PRICE_ENTRIES %>"
						itemsPerPage="<%= 10 %>"
						namespace="<%= liferayPortletResponse.getNamespace() %>"
						pageNumber="<%= 1 %>"
						portletURL="<%= currentURLObj %>"
						selectedItemsKey="tierPriceEntryId"
						showManagementBar="<%= true %>"
						showSearch="<%= false %>"
					/>
				</commerce-ui:panel>
			</div>
		</div>

		<aui:button-row cssClass="price-entry-button-row">
			<aui:button cssClass="btn-lg" type="submit" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>