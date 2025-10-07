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
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="updateCPConfigurationEntryIds" type="hidden" />

		<frontend-data-set:headless-display
			apiURL='<%= "/o/headless-commerce-admin-catalog/v1.0/product-configuration-lists/" + cpConfigurationListId + "/product-configurations?showDifferences=true" %>'
			bulkActionDropdownItems="<%= cpConfigurationListDisplayContext.getBulkActionDropdownItems() %>"
			fdsActionDropdownItems="<%= cpConfigurationListDisplayContext.getCPConfigurationEntryFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CPConfigurationFDSNames.PRODUCT_CONFIGURATIONS %>"
			propsTransformer="{CPConfigurationEntryFDSPropsTransformer} from commerce-product-definitions-web"
			selectedItemsKey="id"
			selectionType="multiple"
			style="fluid"
		/>
	</aui:form>
</commerce-ui:panel>