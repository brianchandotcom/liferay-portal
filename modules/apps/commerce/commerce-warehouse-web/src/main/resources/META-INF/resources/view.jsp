<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryWarehousesDisplayContext commerceInventoryWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = commerceInventoryWarehousesDisplayContext.getPortletURL();
%>

<c:if test="<%= commerceInventoryWarehousesDisplayContext.hasManageCommerceInventoryWarehousePermission() %>">
	<div class="pt-4">
		<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

			<frontend-data-set:headless-display
				apiURL="/o/headless-commerce-admin-inventory/v1.0/warehouses?sort=name:asc"
				creationMenu="<%= commerceInventoryWarehousesDisplayContext.getWarehouseCreationMenu() %>"
				fdsActionDropdownItems="<%= commerceInventoryWarehousesDisplayContext.getWarehouseFDSActionDropdownItems() %>"
				formName="fm"
				id="<%= CommerceInventoryWarehouseFDSNames.WAREHOUSES %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= portletURL %>"
				style="stacked"
			/>
		</aui:form>
	</div>
</c:if>