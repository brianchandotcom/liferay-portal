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

<frontend-data-set:classic-display
	creationMenu="<%= commerceInventoryDisplayContext.getInventoryItemCreationMenu() %>"
	dataProviderKey="<%= CommerceInventoryFDSNames.INVENTORY_ITEMS %>"
	id="<%= CommerceInventoryFDSNames.INVENTORY_ITEMS %>"
	itemsPerPage="<%= 10 %>"
	style="fluid"
/>