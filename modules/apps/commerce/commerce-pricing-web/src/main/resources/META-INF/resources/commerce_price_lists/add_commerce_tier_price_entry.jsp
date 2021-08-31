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

CommerceCurrency commerceCurrency = commerceTierPriceEntryDisplayContext.getCommercePriceListCurrency();
long commercePriceEntryId = commerceTierPriceEntryDisplayContext.getCommercePriceEntryId();
long commercePriceListId = commerceTierPriceEntryDisplayContext.getCommercePriceListId();
CommerceTierPriceEntry commerceTierPriceEntry = commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntry();

BigDecimal price = BigDecimal.ZERO;
boolean neverExpire = true;
%>

<portlet:actionURL name="/commerce_price_list/edit_commerce_tier_price_entry" var="editCommerceTierPriceEntryActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-new-price-tier") %>'
>
	<aui:form action="<%= editCommerceTierPriceEntryActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
		<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListId %>" />

		<liferay-ui:error exception="<%= DuplicateCommerceTierPriceEntryException.class %>" message="there-is-already-a-tier-price-entry-with-the-same-minimum-quantity" />

		<%@ include file="/commerce_price_lists/commerce_tier_price_entry/details.jspf" %>

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
	</aui:form>
</commerce-ui:modal-content>