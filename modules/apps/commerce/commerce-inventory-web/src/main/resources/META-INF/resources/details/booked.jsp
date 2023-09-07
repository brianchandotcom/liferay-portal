<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryDisplayContext commerceInventoryDisplayContext = (CommerceInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:panel
	bodyClasses="p-0"
	title='<%= LanguageUtil.get(request, "details") %>'
>
	<frontend-data-set:classic-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"sku", commerceInventoryDisplayContext.getSku()
			).put(
				"unitOfMeasureKey", commerceInventoryDisplayContext.getUnitOfMeasureKey()
			).build()
		%>'
		dataProviderKey="<%= CommerceInventoryFDSNames.INVENTORY_BOOKED %>"
		id="<%= CommerceInventoryFDSNames.INVENTORY_BOOKED %>"
		itemsPerPage="<%= 10 %>"
	/>
</commerce-ui:panel>