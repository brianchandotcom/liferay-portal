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
String p_u_i_d = ParamUtil.getString(request, "p_u_i_d");

long selOrganizationId = ParamUtil.getLong(request, "organizationId");
User selUser = PortalUtil.getSelectedUser(request);

SelectOrganizationManagementToolbarDisplayContext selectOrganizationManagementToolbarDisplayContext = new SelectOrganizationManagementToolbarDisplayContext(request, renderRequest, renderResponse);

PortletURL portletURL = selectOrganizationManagementToolbarDisplayContext.getPortletURL();

LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

if (filterManageableOrganizations) {
	organizationParams.put("organizationsTree", user.getOrganizations());
}

SearchContainer<Organization> searchContainer = selectOrganizationManagementToolbarDisplayContext.getSearchContainer(organizationParams);

renderResponse.setTitle(LanguageUtil.get(request, "organizations"));
%>

<clay:management-toolbar
	clearResultsURL="<%= selectOrganizationManagementToolbarDisplayContext.getClearResultsURL() %>"
	filterDropdownItems="<%= selectOrganizationManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	searchActionURL="<%= selectOrganizationManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= selectOrganizationManagementToolbarDisplayContext.getSortingURL() %>"
/>

<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="selectOrganizationFm">
	<liferay-ui:search-container
		searchContainer="<%= searchContainer %>"
		var="organizationSearchContainer"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="name"
				orderable="<%= true %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="parent-organization"
				value="<%= HtmlUtil.escape(organization.getParentOrganizationName()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				orderable="<%= true %>"
				value="<%= LanguageUtil.get(request, organization.getType()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="city"
				property="address.city"
			/>

			<liferay-ui:search-container-column-text
				name="region"
				property="address.region.name"
			/>

			<liferay-ui:search-container-column-text
				name="country"
				value="<%= UsersAdmin.ORGANIZATION_COUNTRY_NAME_ACCESSOR.get(organization) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= Validator.isNull(p_u_i_d) || OrganizationMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, organization.getOrganizationId()) %>">

					<%
					boolean disabled = false;

					if (selUser != null) {
						for (long curOrganizationId : selUser.getOrganizationIds()) {
							if (curOrganizationId == organization.getOrganizationId()) {
								disabled = true;

								break;
							}
						}
					}

					if (selOrganizationId == organization.getOrganizationId()) {
						disabled = true;
					}
					%>

					<clay:button
						aria-label='<%= LanguageUtil.format(request, "choose-x", organization.getName()) %>'
						cssClass="selector-button"
						data-entityid="<%= organization.getOrganizationId() %>"
						data-entityname="<%= organization.getName() %>"
						data-type="<%= LanguageUtil.get(request, organization.getType()) %>"
						disabled="<%= disabled %>"
						displayType="secondary"
						label='<%= LanguageUtil.get(request, "choose") %>'
					/>
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>