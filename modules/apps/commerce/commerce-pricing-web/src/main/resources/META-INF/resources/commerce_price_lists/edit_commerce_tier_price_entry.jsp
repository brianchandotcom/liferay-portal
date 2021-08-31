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
CommerceTierCommercePriceEntryDisplayContext commerceTierPriceEntryDisplayContext = (CommerceTierCommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTierPriceEntry commerceTierPriceEntry = commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntry();
CommerceCurrency commerceCurrency = commerceTierPriceEntryDisplayContext.getCommercePriceListCurrency();
long commercePriceEntryId = commerceTierPriceEntryDisplayContext.getCommercePriceEntryId();
long commercePriceListId = commerceTierPriceEntryDisplayContext.getCommercePriceListId();
long commerceTierPriceEntryId = commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryId();

BigDecimal price = BigDecimal.ZERO;
BigDecimal promoPrice = BigDecimal.ZERO;

if (commerceTierPriceEntry != null) {
	price = commerceTierPriceEntry.getPrice();
	promoPrice = commerceTierPriceEntry.getPromoPrice();
}

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((commerceTierPriceEntry != null) && (commerceTierPriceEntry.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<portlet:actionURL name="/commerce_price_list/edit_commerce_tier_price_entry" var="editCommerceTierPriceEntryActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "edit-price-tier") %>'
>
	<aui:form action="<%= editCommerceTierPriceEntryActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
		<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListId %>" />
		<aui:input name="commerceTierPriceEntryId" type="hidden" value="<%= commerceTierPriceEntryId %>" />

		<liferay-ui:error exception="<%= DuplicateCommerceTierPriceEntryException.class %>" message="there-is-already-a-tier-price-entry-with-the-same-minimum-quantity" />

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<%@ include file="/commerce_price_lists/commerce_tier_price_entry/details.jspf" %>
		</commerce-ui:panel>

		<c:if test="<%= commerceTierPriceEntryDisplayContext.hasCustomAttributes() %>">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "custom-attributes") %>'
			>
				<liferay-expando:custom-attribute-list
					className="<%= CommerceTierPriceEntry.class.getName() %>"
					classPK="<%= (commerceTierPriceEntry != null) ? commerceTierPriceEntry.getCommerceTierPriceEntryId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</commerce-ui:panel>
		</c:if>

		<aui:button-row cssClass="tier-price-entry-button-row">
			<aui:button cssClass="btn-lg" type="submit" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>