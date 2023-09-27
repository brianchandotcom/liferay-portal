<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commercePriceListId = commercePriceListDisplayContext.getCommercePriceListId();
%>

<c:if test="<%= commercePriceListDisplayContext.hasPermission(commercePriceListId, ActionKeys.UPDATE) %>">
	<div class="pt-4">
		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commercePriceListId", String.valueOf(commercePriceListId)
				).build()
			%>'
			creationMenu="<%= commercePriceListDisplayContext.getPriceModifiersCreationMenu() %>"
			dataProviderKey="<%= CommercePricingFDSNames.PRICE_MODIFIERS %>"
			formName="fm"
			id="<%= CommercePricingFDSNames.PRICE_MODIFIERS %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</div>
</c:if>