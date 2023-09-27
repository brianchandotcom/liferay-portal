<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
OrganizationsDisplayContext organizationsDisplayContext = new OrganizationsDisplayContext(request, renderRequest, renderResponse);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= siteMembershipsDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new OrganizationsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, organizationsDisplayContext) %>"
	propsTransformer="js/OrganizationsManagementToolbarPropsTransformer"
/>

<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/site_memberships/organizations_info_panel" var="sidebarPanelURL">
		<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	</liferay-portlet:resourceURL>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="organizations"
	>
		<liferay-util:include page="/organization_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<clay:container-fluid>
			<portlet:actionURL name="deleteGroupOrganizations" var="deleteGroupOrganizationsURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<aui:form action="<%= deleteGroupOrganizationsURL %>" method="post" name="fm">
				<aui:input name="tabs1" type="hidden" value="organizations" />
				<aui:input name="groupId" type="hidden" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />

				<liferay-site-navigation:breadcrumb
					breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, true, false, false, true, true) %>"
				/>

				<liferay-ui:search-container
					id="organizations"
					searchContainer="<%= organizationsDisplayContext.getOrganizationSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.model.Organization"
						escapedModel="<%= true %>"
						keyProperty="organizationId"
						modelVar="organization"
					>

						<%
						String displayStyle = organizationsDisplayContext.getDisplayStyle();
						%>

						<c:choose>
							<c:when test='<%= displayStyle.equals("icon") %>'>
								<liferay-ui:search-container-column-text>
									<clay:user-card
										propsTransformer="js/OrganizationCardPropsTransformer"
										userCard="<%= new OrganizationsUserCard(organization, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:when test='<%= displayStyle.equals("descriptive") %>'>
								<liferay-ui:search-container-column-icon
									icon="organizations"
									toggleRowChecker="<%= true %>"
								/>

								<liferay-ui:search-container-column-text
									colspan="<%= 2 %>"
								>
									<h5><%= organization.getName() %></h5>

									<h6 class="text-default">
										<span><%= HtmlUtil.escape(organization.getParentOrganizationName()) %></span>
									</h6>

									<h6 class="text-default">
										<span><liferay-ui:message key="<%= organization.getType() %>" /></span>
									</h6>

									<h6 class="text-default">
										<span><%= HtmlUtil.escape(organization.getAddress().getCity()) %></span>
										<span><%= UsersAdmin.ORGANIZATION_REGION_NAME_ACCESSOR.get(organization) %></span>
										<span><%= UsersAdmin.ORGANIZATION_COUNTRY_NAME_ACCESSOR.get(organization) %></span>
									</h6>
								</liferay-ui:search-container-column-text>

								<%
								OrganizationActionDropdownItemsProvider organizationActionDropdownItemsProvider = new OrganizationActionDropdownItemsProvider(organization, renderRequest, renderResponse);
								%>

								<liferay-ui:search-container-column-text>
									<clay:dropdown-actions
										aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
										dropdownItems="<%= organizationActionDropdownItemsProvider.getActionDropdownItems() %>"
										propsTransformer="js/OrganizationDropdownDefaultPropsTransformer"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:otherwise>
								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand table-cell-minw-200 table-title"
									name="name"
									orderable="<%= true %>"
									value="<%= organization.getName() %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand table-cell-minw-200"
									name="parent-organization"
									value="<%= HtmlUtil.escape(organization.getParentOrganizationName()) %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-smallest table-cell-minw-100"
									name="type"
									orderable="<%= true %>"
									value="<%= LanguageUtil.get(request, organization.getType()) %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-smallest table-cell-minw-150"
									name="city"
									value="<%= HtmlUtil.escape(organization.getAddress().getCity()) %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-smallest table-cell-minw-150"
									name="region"
									value="<%= UsersAdmin.ORGANIZATION_REGION_NAME_ACCESSOR.get(organization) %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-smallest table-cell-minw-150"
									name="country"
									value="<%= UsersAdmin.ORGANIZATION_COUNTRY_NAME_ACCESSOR.get(organization) %>"
								/>

								<%
								OrganizationActionDropdownItemsProvider organizationActionDropdownItemsProvider = new OrganizationActionDropdownItemsProvider(organization, renderRequest, renderResponse);
								%>

								<liferay-ui:search-container-column-text>
									<clay:dropdown-actions
										aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
										dropdownItems="<%= organizationActionDropdownItemsProvider.getActionDropdownItems() %>"
										propsTransformer="js/OrganizationDropdownDefaultPropsTransformer"
									/>
								</liferay-ui:search-container-column-text>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="<%= organizationsDisplayContext.getDisplayStyle() %>"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</aui:form>
		</clay:container-fluid>
	</div>
</div>

<portlet:actionURL name="addGroupOrganizations" var="addGroupOrganizationsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= addGroupOrganizationsURL %>" cssClass="hide" name="addGroupOrganizationsFm">
	<aui:input name="tabs1" type="hidden" value="organizations" />
</aui:form>