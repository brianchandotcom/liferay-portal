<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPInstanceUnitOfMeasureDisplayContext cpInstanceUnitOfMeasureDisplayContext = (CPInstanceUnitOfMeasureDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long cpInstanceId = cpInstanceUnitOfMeasureDisplayContext.getCPInstanceId();
%>

<commerce-ui:panel
	title='<%= LanguageUtil.get(request, "uom") %>'
>
	<portlet:actionURL name="/cp_definitions/edit_cp_instance_unit_of_measure" var="editCPInstanceUnitOfMeasureActionURL" />

	<aui:form action="<%= editCPInstanceUnitOfMeasureActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<frontend-data-set:headless-display
			apiURL='<%= "/o/headless-commerce-admin-catalog/v1.0/skus/" + cpInstanceId + "/sku-unit-of-measures" %>'
			bulkActionDropdownItems="<%= cpInstanceUnitOfMeasureDisplayContext.getBulkActionDropdownItems() %>"
			creationMenu="<%= cpInstanceUnitOfMeasureDisplayContext.getCreationMenu() %>"
			fdsActionDropdownItems="<%= cpInstanceUnitOfMeasureDisplayContext.getFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CommerceProductFDSNames.PRODUCT_UNITS_OF_MEASURE %>"
			itemsPerPage="<%= 10 %>"
			selectedItemsKey="id"
			selectionType="multiple"
			style="stacked"
		/>
	</aui:form>
</commerce-ui:panel>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"datasetId", CommerceProductFDSNames.PRODUCT_UNITS_OF_MEASURE
		).build()
	%>'
	module="js/view_cp_instance_unit_of_measure"
/>