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
CommerceCountryItemSelectorViewDisplayContext commerceCountryItemSelectorViewDisplayContext = (CommerceCountryItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String itemSelectedEventName = commerceCountryItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = commerceCountryItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceCountries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceCountryItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceCountryItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />commerceCountrySelectorWrapper">
	<liferay-ui:search-container
		id="commerceCountries"
		searchContainer="<%= commerceCountryItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceCountry"
			cssClass="commerce-country-row"
			keyProperty="commerceCountryId"
			modelVar="commerceCountry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="billing-allowed"
				value='<%= LanguageUtil.get(request, commerceCountry.isBillingAllowed() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="shipping-allowed"
				value='<%= LanguageUtil.get(request, commerceCountry.isShippingAllowed() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="two-letters-iso-code"
				property="twoLettersISOCode"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="priority"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchContainer="<%= commerceCountryItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>

		<liferay-ui:search-paginator
			searchContainer="<%= commerceCountryItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commerceCountrySelectorWrapper = A.one("#<portlet:namespace />commerceCountrySelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceCountries');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(commerceCountrySelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>