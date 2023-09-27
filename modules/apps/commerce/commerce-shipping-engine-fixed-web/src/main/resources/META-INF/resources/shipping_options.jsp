<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceShippingFixedOptionsDisplayContext commerceShippingFixedOptionsDisplayContext = (CommerceShippingFixedOptionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:panel
	bodyClasses="p-0"
>
	<frontend-data-set:classic-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceShippingMethodId", String.valueOf(commerceShippingFixedOptionsDisplayContext.getCommerceShippingMethodId())
			).build()
		%>'
		creationMenu="<%= commerceShippingFixedOptionsDisplayContext.getCreationMenu() %>"
		dataProviderKey="<%= CommerceShippingFixedOptionFDSNames.SHIPPING_FIXED_OPTIONS %>"
		fdsSortItemList="<%= commerceShippingFixedOptionsDisplayContext.getFDSSortItemList() %>"
		id="<%= CommerceShippingFixedOptionFDSNames.SHIPPING_FIXED_OPTIONS %>"
		itemsPerPage="<%= 10 %>"
		selectedItemsKey="shippingFixedOptionId"
		showManagementBar="<%= true %>"
		style="fluid"
	/>
</commerce-ui:panel>