<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPConfigurationListDisplayContext cpConfigurationListDisplayContext = (CPConfigurationListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long cpConfigurationListId = cpConfigurationListDisplayContext.getCPConfigurationListId();
%>

<commerce-ui:panel
	bodyClasses="p-0"
	elementClasses="mt-4"
	title='<%= LanguageUtil.get(request, "products") %>'
>
	<frontend-data-set:headless-display
		apiURL='<%= "/o/headless-commerce-admin-catalog/v1.0/product-configuration-lists/" + cpConfigurationListId + "/product-configurations" %>'
		fdsActionDropdownItems="<%= cpConfigurationListDisplayContext.getCPConfigurationEntryFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= CPConfigurationFDSNames.PRODUCT_CONFIGURATIONS %>"
		itemsPerPage="<%= 10 %>"
		selectedItemsKey="id"
		selectionType="multiple"
		style="fluid"
	/>
</commerce-ui:panel>