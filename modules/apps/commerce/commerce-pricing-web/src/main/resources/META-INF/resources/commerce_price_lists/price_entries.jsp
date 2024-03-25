<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceEntryDisplayContext commercePriceEntryDisplayContext = (CommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commercePriceEntryDisplayContext.getCommercePriceList();
long commercePriceListId = commercePriceEntryDisplayContext.getCommercePriceListId();

String dataSetId = CommercePricingFDSNames.PRICE_LIST_ENTRIES;

if (CommercePriceListConstants.TYPE_PROMOTION.equals(commercePriceEntryDisplayContext.getCommercePriceListType(portletName))) {
	dataSetId = CommercePricingFDSNames.PROMOTION_ENTRIES;
}
%>

<c:if test="<%= commercePriceEntryDisplayContext.hasPermission(commercePriceListId, ActionKeys.UPDATE) %>">
	<div class="pt-4 row">
		<div class="col-12">
			<div id="item-finder-root"></div>

			<liferay-frontend:component
				context='<%=
					HashMapBuilder.<String, Object>put(
						"commerceCatalogId", commercePriceEntryDisplayContext.getCommerceCatalogId()
					).put(
						"commercePriceListId", commercePriceListId
					).put(
						"dataSetId", dataSetId
					).put(
						"discountExternalReferenceCode", commercePriceList.getExternalReferenceCode()
					).put(
						"portletId", portletDisplay.getRootPortletId()
					).put(
						"spritemap", themeDisplay.getPathThemeSpritemap()
					).build()
				%>'
				module="{pricingEntries} from commerce-pricing-web"
			/>
		</div>

		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="p-0"
				title='<%= LanguageUtil.get(request, "entries") %>'
			>
				<frontend-data-set:headless-display
					apiURL="<%= commercePriceEntryDisplayContext.getPriceEntryApiURL() %>"
					fdsActionDropdownItems="<%= commercePriceEntryDisplayContext.getPriceEntriesFDSActionDropdownItems() %>"
					formName="fm"
					id="<%= dataSetId %>"
					itemsPerPage="<%= 10 %>"
					selectedItemsKey="priceEntryId"
				/>
			</commerce-ui:panel>
		</div>
	</div>
</c:if>