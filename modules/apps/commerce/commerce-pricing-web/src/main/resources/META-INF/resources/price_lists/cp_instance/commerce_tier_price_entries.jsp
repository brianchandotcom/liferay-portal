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
CPInstanceCommerceTierPriceEntryDisplayContext cpInstanceCommerceTierPriceEntryDisplayContext = (CPInstanceCommerceTierPriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commercePriceEntryId = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntryId();

PortletURL portletURL = cpInstanceCommerceTierPriceEntryDisplayContext.getPortletURL();

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<div class="tier-price-entries-container" id="<portlet:namespace />entriesContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<clay:data-set-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commercePriceEntryId", String.valueOf(commercePriceEntryId)
				).build()
			%>'
			creationMenu="<%= cpInstanceCommerceTierPriceEntryDisplayContext.getCreationMenu() %>"
			dataProviderKey="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_INSTANCE_TIER_PRICE_ENTRIES %>"
			formId="fm"
			id="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_INSTANCE_TIER_PRICE_ENTRIES %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= portletURL %>"
			style="stacked"
		/>
	</aui:form>
</div>