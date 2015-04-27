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

<%@ include file="/html/portlet/site_memberships/init.jsp" %>

<%
OrganizationSearch searchContainer = (OrganizationSearch)request.getAttribute("liferay-ui:search:searchContainer");

OrganizationDisplayTerms displayTerms = (OrganizationDisplayTerms)searchContainer.getDisplayTerms();

String type = displayTerms.getType();
%>

<liferay-ui:search-toggle
	autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_site_memberships_organization_search"
>
	<aui:fieldset>
		<aui:input inlineField="<%= true %>" name="<%= OrganizationDisplayTerms.NAME %>" size="20" value="<%= displayTerms.getName() %>" />

		<aui:input inlineField="<%= true %>" name="<%= OrganizationDisplayTerms.STREET %>" size="20" value="<%= displayTerms.getStreet() %>" />

		<aui:input inlineField="<%= true %>" name="<%= OrganizationDisplayTerms.CITY %>" size="20" value="<%= displayTerms.getCity() %>" />
	</aui:fieldset>

	<aui:fieldset>
		<aui:select inlineField="<%= true %>" name="<%= OrganizationDisplayTerms.TYPE %>">
			<aui:option value=""></aui:option>

			<%
			for (String curType : PropsValues.ORGANIZATIONS_TYPES) {
			%>

				<aui:option label="<%= curType %>" selected="<%= type.equals(curType) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select inlineField="<%= true %>" label="country" name="<%= OrganizationDisplayTerms.COUNTRY_ID %>"></aui:select>

		<aui:select inlineField="<%= true %>" label="region" name="<%= OrganizationDisplayTerms.REGION_ID %>"></aui:select>
	</aui:fieldset>

	<aui:fieldset>
		<aui:input inlineField="<%= true %>" label="postal-code" name="<%= OrganizationDisplayTerms.ZIP %>" size="20" value="<%= displayTerms.getZip() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace /><%= OrganizationDisplayTerms.COUNTRY_ID %>',
				selectData: Liferay.Address.getCountries,
				selectDesc: 'nameCurrentValue',
				selectId: 'countryId',
				selectSort: '<%= true %>',
				selectVal: '<%= displayTerms.getCountryId() %>'
			},
			{
				select: '<portlet:namespace /><%= OrganizationDisplayTerms.REGION_ID %>',
				selectData: Liferay.Address.getRegions,
				selectDesc: 'name',
				selectId: 'regionId',
				selectVal: '<%= displayTerms.getRegionId() %>'
			}
		]
	);
</aui:script>