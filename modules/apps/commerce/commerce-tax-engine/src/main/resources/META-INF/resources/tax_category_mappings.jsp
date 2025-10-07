<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceTaxCategoryMappingsDisplayContext commerceTaxCategoryMappingsDisplayContext = (CommerceTaxCategoryMappingsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:panel
	bodyClasses="p-0"
>
	<frontend-data-set:classic-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceChannelId", String.valueOf(commerceTaxCategoryMappingsDisplayContext.getCommerceChannelId())
			).put(
				"commerceTaxMethodId", String.valueOf(commerceTaxCategoryMappingsDisplayContext.getCommerceTaxMethodId())
			).build()
		%>'
		creationMenu="<%= commerceTaxCategoryMappingsDisplayContext.getCreationMenu() %>"
		dataProviderKey="<%= FunctionCommerceTaxEngineFDSNames.FUNCTION_COMMERCE_TAX_ENGINE_TAX_CATEGORY_MAPPINGS %>"
		id="<%= FunctionCommerceTaxEngineFDSNames.FUNCTION_COMMERCE_TAX_ENGINE_TAX_CATEGORY_MAPPINGS %>"
		showSearch="<%= false %>"
	/>
</commerce-ui:panel>