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
SearchContainer<Country> countrySearchContainer = CountrySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new CountriesManagementAdminManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, countrySearchContainer) %>"
	propsTransformer="js/CountriesManagementToolbarPropsTransformer"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="countryIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= countrySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Country"
				keyProperty="countryId"
				modelVar="country"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/address/edit_country" />
					<portlet:param name="countryId" value="<%= String.valueOf(country.getCountryId()) %>" />
				</portlet:renderURL>

				<%
				if (!PortalPermissionUtil.contains(permissionChecker, ActionKeys.MANAGE_COUNTRIES)) {
					rowURL = null;
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="font-weight-bold important table-cell-expand-smallest"
					href="<%= rowURL %>"
					name="name"
					value="<%= HtmlUtil.escape(country.getTitle(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					name="billing-allowed"
				>
					<liferay-ui:icon
						cssClass='<%= country.isBillingAllowed() ? "text-success" : "text-danger" %>'
						icon='<%= country.isBillingAllowed() ? "check" : "times" %>'
						markupView="lexicon"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="shipping-allowed"
				>
					<liferay-ui:icon
						cssClass='<%= country.isShippingAllowed() ? "text-success" : "text-danger" %>'
						icon='<%= country.isShippingAllowed() ? "check" : "times" %>'
						markupView="lexicon"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="two-letter-iso-code"
					property="a2"
				/>

				<liferay-ui:search-container-column-text
					name="active"
				>
					<liferay-ui:icon
						cssClass='<%= country.isActive() ? "text-success" : "text-danger" %>'
						icon='<%= country.isActive() ? "check" : "times" %>'
						markupView="lexicon"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="priority"
					property="position"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="table-column-text-end"
					path="/country_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>