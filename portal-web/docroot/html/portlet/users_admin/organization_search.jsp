<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
OrganizationSearch searchContainer = (OrganizationSearch)request.getAttribute("liferay-ui:search:searchContainer");

OrganizationDisplayTerms displayTerms = (OrganizationDisplayTerms)searchContainer.getDisplayTerms();

String type = displayTerms.getType();

Organization organization = null;

if (displayTerms.getParentOrganizationId() > 0) {
	try {
		organization = OrganizationLocalServiceUtil.getOrganization(displayTerms.getParentOrganizationId());
	}
	catch (NoSuchOrganizationException nsoe) {
	}
}
%>

<liferay-ui:search-toggle
	autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_users_admin_organization_search"
>
	<aui:fieldset>
		<aui:input name="<%= displayTerms.NAME %>" size="20" value="<%= displayTerms.getName() %>" />

		<aui:input name="<%= displayTerms.STREET %>" size="20" value="<%= displayTerms.getStreet() %>" />

		<aui:input name="<%= displayTerms.CITY %>" size="20" value="<%= displayTerms.getCity() %>" />

		<aui:select name="<%= displayTerms.TYPE %>">
			<aui:option value=""></aui:option>

			<%
			for (String curType : PropsValues.ORGANIZATIONS_TYPES) {
			%>

				<aui:option label="<%= curType %>" selected="<%= type.equals(curType) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="country" name="<%= displayTerms.COUNTRY_ID %>"></aui:select>

		<aui:select label="region" name="<%= displayTerms.REGION_ID %>"></aui:select>

		<aui:input label="postal-code" name="<%= displayTerms.ZIP %>" size="20" value="<%= displayTerms.getZip() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace /><%= displayTerms.COUNTRY_ID %>',
				selectData: Liferay.Address.getCountries,
				selectDesc: 'nameCurrentValue',
				selectSort: '<%= true %>',
				selectId: 'countryId',
				selectVal: '<%= displayTerms.getCountryId() %>'
			},
			{
				select: '<portlet:namespace /><%= displayTerms.REGION_ID %>',
				selectData: Liferay.Address.getRegions,
				selectDesc: 'name',
				selectId: 'regionId',
				selectVal: '<%= displayTerms.getRegionId() %>'
			}
		]
	);
</aui:script>