<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPConfigurationListDisplayContext cpConfigurationListDisplayContext = (CPConfigurationListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div id="<portlet:namespace />productConfigurationListsContainer">
	<aui:form action="" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="" />

		<frontend-data-set:headless-display
			apiURL="/o/headless-commerce-admin-catalog/v1.0/product-configuration-lists"
			creationMenu="<%= cpConfigurationListDisplayContext.getCreationMenu() %>"
			fdsActionDropdownItems="<%= cpConfigurationListDisplayContext.getCPConfigurationListFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CPConfigurationFDSNames.PRODUCT_CONFIGURATION_LISTS %>"
			itemsPerPage="<%= 10 %>"
			selectedItemsKey="id"
			selectionType="multiple"
			style="fluid"
		/>

		<liferay-frontend:component
			context="<%= cpConfigurationListDisplayContext.getContext() %>"
			module="{addCpConfigurationList} from commerce-product-definitions-web"
		/>
	</aui:form>
</div>