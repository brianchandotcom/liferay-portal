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
long commerceProductOptionId = ParamUtil.getLong(request, "commerceProductOptionId");

SearchContainer<CommerceProductOptionValue> commerceProductOptionValueSearchContainer = commerceProductOptionsDisplayContext.getCommerceProductOptionValueSearchContainer();

PortletURL portletURL = liferayPortletResponse.createRenderURL();

String orderByCol = ParamUtil.getString(request, "orderByCol", "priority");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

portletURL.setParameter("mvcRenderCommandName", "editProductOption");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("backURL", redirect);
portletURL.setParameter("commerceProductOptionId", String.valueOf(commerceProductOptionId));
portletURL.setParameter("orderByCol", orderByCol);
portletURL.setParameter("orderByType", orderByType);
portletURL.setParameter("searchContainerId", "commerceProductOptionValues");
%>

<liferay-frontend:management-bar
	searchContainerId="commerceProductOptionValues"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"priority", "title"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<div class="product-option-values-container" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="commerceProductOptionValues"
			searchContainer="<%= commerceProductOptionValueSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.product.model.CommerceProductOptionValue"
				cssClass="entry-display-style"
				keyProperty="commerceProductOptionValueId"
				modelVar="commerceProductOptionValue"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="title"
				>
					<%= commerceProductOptionValue.getTitle(locale) %>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="priority"
					property="priority"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/commerce_product_option_value_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</div>
</aui:form>

<liferay-portlet:renderURL var="addProductOptionValueURL">
	<portlet:param name="mvcRenderCommandName" value="editProductOptionValue" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="commerceProductOptionId" value="<%= String.valueOf(commerceProductOptionId) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-product-option-value") %>' url="<%= addProductOptionValueURL.toString() %>" />
</liferay-frontend:add-menu>