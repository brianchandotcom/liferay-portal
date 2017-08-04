<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-dimension-product-measurement-units");

CPMeasurementUnitsDisplayContext cpMeasurementUnitsDisplayContext = (CPMeasurementUnitsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CPMeasurementUnit> cpMeasurementUnitSearchContainer = cpMeasurementUnitsDisplayContext.getSearchContainer();

int type = cpMeasurementUnitsDisplayContext.getType();
%>

<%@ include file="/navbar.jspf" %>

<liferay-frontend:management-bar
	searchContainerId="cpMeasurementUnits"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= cpMeasurementUnitsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpMeasurementUnitsDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpMeasurementUnitsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= cpMeasurementUnitsDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<liferay-ui:search-container
	id="cpMeasurementUnits"
	searchContainer="<%= cpMeasurementUnitSearchContainer %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.commerce.product.model.CPMeasurementUnit"
		keyProperty="CPMeasurementUnitId"
		modelVar="cpMeasurementUnit"
	>

		<%
		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("mvcRenderCommandName", "editCPMeasurementUnit");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("cpMeasurementUnitId", String.valueOf(cpMeasurementUnit.getCPMeasurementUnitId()));
		rowURL.setParameter("type", String.valueOf(type));
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			href="<%= rowURL %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			property="key"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="ratio-to-primary"
			property="rate"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="primary"
			value='<%= LanguageUtil.get(request, cpMeasurementUnit.isPrimary() ? "yes" : "no") %>'
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			property="priority"
		/>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/measurement_unit_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" />
</liferay-ui:search-container>

<c:if test="<%= CPMeasurementUnitPermission.contains(permissionChecker, scopeGroupId, CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNIT) %>">
	<portlet:renderURL var="addCPMeasurementUnitURL">
		<portlet:param name="mvcRenderCommandName" value="editCPMeasurementUnit" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="type" value="<%= String.valueOf(type) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-measurement-unit") %>' url="<%= addCPMeasurementUnitURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>