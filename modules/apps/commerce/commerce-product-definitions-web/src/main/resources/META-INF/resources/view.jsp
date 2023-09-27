<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String catalogNavigationItem = ParamUtil.getString(request, "catalogNavigationItem", "view-all-product-definitions");

CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

request.setAttribute("view.jsp-portletURL", cpDefinitionsDisplayContext.getPortletURL());
%>

<%@ include file="/navbar_definitions.jspf" %>

<div id="<portlet:namespace />productDefinitionsContainer">
	<aui:form action="<%= cpDefinitionsDisplayContext.getPortletURL() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= String.valueOf(cpDefinitionsDisplayContext.getPortletURL()) %>" />
		<aui:input name="deleteCPDefinitionIds" type="hidden" />

		<frontend-data-set:headless-display
			apiURL="/o/headless-commerce-admin-catalog/v1.0/products?nestedFields=skus,catalog"
			bulkActionDropdownItems="<%= cpDefinitionsDisplayContext.getBulkActionDropdownItems() %>"
			creationMenu="<%= cpDefinitionsDisplayContext.getCreationMenu() %>"
			fdsActionDropdownItems="<%= cpDefinitionsDisplayContext.getFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CommerceProductFDSNames.PRODUCT_DEFINITIONS %>"
			itemsPerPage="<%= 10 %>"
			selectedItemsKey="id"
			selectionType="multiple"
			style="fluid"
		/>
	</aui:form>
</div>