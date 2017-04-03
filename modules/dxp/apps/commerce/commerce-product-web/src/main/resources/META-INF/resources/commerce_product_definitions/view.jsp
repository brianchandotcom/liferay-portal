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

<%@ include file="/commerce_product_definitions/init.jsp" %>

<%
String searchContainerId = ParamUtil.getString(request, "searchContainerId", "commerceProductDefinitions");

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-product-definitions");

SearchContainer productDefinitionSearchContainer = commerceProductDisplayContext.getSearchContainer();

PortletURL portletURL = commerceProductDisplayContext.getPortletURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", searchContainerId);
portletURL.setParameter("jspPage", "/commerce_product_definitions/view.jsp");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<%@ include file="/commerce_product_definitions/toolbar.jspf" %>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= productDefinitionSearchContainer.getOrderByCol() %>"
				orderByType="<%= productDefinitionSearchContainer.getOrderByType() %>"
				orderColumns='<%= new String[] {"create-date", "display-date"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<liferay-ui:search-container
		emptyResultsMessage="no-product-was-found"
		id="<%= searchContainerId %>"
		searchContainer="<%= productDefinitionSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CommerceProductDefinition"
			escapedModel="<%= true %>"
			keyProperty="commerceProductDefinitionId"
			modelVar="commerceProductDefinition"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="product-name"
			>
				<%= commerceProductDefinition.getTitle(themeDisplay.getLocale()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="SKU"
				property="baseSKU"
			/>

			<liferay-ui:search-container-column-status
				cssClass="table-cell-content"
				name="status"
				status="<%= commerceProductDisplayContext.getStatus() %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/commerce_product_definitions/product_definition_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<liferay-portlet:renderURL var="editProductDefinitionURL">
	<portlet:param name="mvcRenderCommandName" value="/commerce_product_definitions/edit_product_definition" />
	<portlet:param name="backURL" value="<%= PortalUtil.getCurrentCompleteURL(request) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title="add-product" url="<%= editProductDefinitionURL.toString() %>" />
</liferay-frontend:add-menu>