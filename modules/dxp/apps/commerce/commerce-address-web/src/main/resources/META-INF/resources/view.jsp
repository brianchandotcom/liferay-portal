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
CommerceCountriesDisplayContext commerceCountriesDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceCountry> commerceCountrySearchContainer = commerceCountriesDisplayContext.getSearchContainer();

boolean hasManageCommerceCountriesPermission = CommerceAddressPermission.contains(permissionChecker, scopeGroupId, CommerceAddressActionKeys.MANAGE_COMMERCE_COUNTRIES);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceCountries"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
			portletURL="<%= commerceCountriesDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceCountriesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceCountriesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= commerceCountriesDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceCountriesDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCommerceCountriesPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceCountries();" %>' icon="trash" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<portlet:actionURL name="editCommerceCountry" var="editCommerceCountryActionURL" />

<aui:form action="<%= editCommerceCountryActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCommerceCountryIds" type="hidden" />

	<liferay-ui:search-container
		id="commerceCountries"
		searchContainer="<%= commerceCountrySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.address.model.CommerceCountry"
			keyProperty="commerceCountryId"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="billing-allowed"
				property="billingAllowed"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="shipping-allowed"
				property="shippingAllowed"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="two-letters-iso-code"
				property="twoLettersISOCode"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="subject-to-vat"
				property="subjectToVAT"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="active"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="priority"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/country_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= hasManageCommerceCountriesPermission %>">
	<portlet:renderURL var="addCommerceCountryURL">
		<portlet:param name="mvcRenderCommandName" value="editCommerceCountry" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-country") %>' url="<%= addCommerceCountryURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script>
	function <portlet:namespace />deleteCommerceCountries() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-countries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceCountryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>