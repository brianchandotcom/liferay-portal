<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = GetterUtil.getString(request.getAttribute("view.jsp-backURL"));
Organization organization = (Organization)request.getAttribute("view.jsp-organization");
long organizationId = GetterUtil.getLong(request.getAttribute("view.jsp-organizationId"));
String toolbarItem = GetterUtil.getString(request.getAttribute("view.jsp-toolbarItem"));
String usersListView = GetterUtil.getString(request.getAttribute("view.jsp-usersListView"));

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", "list");
}
else {
	portalPreferences.setValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

List<Organization> organizations = new ArrayList<Organization>();

if (filterManageableOrganizations) {
	organizations = user.getOrganizations(true);
}

if (organizationId != OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {
	organizations.clear();

	organizations.add(OrganizationLocalServiceUtil.getOrganization(organizationId));
}

boolean showList = true;

if (filterManageableOrganizations && organizations.isEmpty()) {
	showList = false;
}

PortalUtil.addPortletBreadcrumbEntry(
	request, LanguageUtil.get(request, "users-and-organizations"),
	PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCPath(
		"/view.jsp"
	).setParameter(
		"toolbarItem", "view-all-organizations"
	).setParameter(
		"usersListView", UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS
	).buildString());

if (organization != null) {
	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
%>

<c:choose>
	<c:when test="<%= showList %>">

		<%
		ViewTreeManagementToolbarDisplayContext viewTreeManagementToolbarDisplayContext = new ViewTreeManagementToolbarDisplayContext(request, renderRequest, renderResponse, organization, displayStyle);

		SearchContainer<Object> searchContainer = viewTreeManagementToolbarDisplayContext.getSearchContainer();
		%>

		<clay:management-toolbar
			actionDropdownItems="<%= viewTreeManagementToolbarDisplayContext.getActionDropdownItems() %>"
			additionalProps='<%=
				HashMapBuilder.<String, Object>put(
					"basePortletURL", String.valueOf(renderResponse.createRenderURL())
				).build()
			%>'
			clearResultsURL="<%= viewTreeManagementToolbarDisplayContext.getClearResultsURL() %>"
			creationMenu="<%= viewTreeManagementToolbarDisplayContext.getCreationMenu() %>"
			filterDropdownItems="<%= viewTreeManagementToolbarDisplayContext.getFilterDropdownItems() %>"
			filterLabelItems="<%= viewTreeManagementToolbarDisplayContext.getFilterLabelItems() %>"
			itemsTotal="<%= searchContainer.getTotal() %>"
			orderDropdownItems="<%= viewTreeManagementToolbarDisplayContext.getFilterDropdownItems() %>"
			propsTransformer="js/ViewTreeManagementToolbarPropsTransformer"
			searchActionURL="<%= viewTreeManagementToolbarDisplayContext.getSearchActionURL() %>"
			searchContainerId="organizationUsers"
			searchFormName="searchFm"
			selectable="<%= true %>"
			showCreationMenu="<%= viewTreeManagementToolbarDisplayContext.showCreationMenu() %>"
			showSearch="<%= true %>"
			sortingOrder="<%= searchContainer.getOrderByType() %>"
			sortingURL="<%= viewTreeManagementToolbarDisplayContext.getSortingURL() %>"
			viewTypeItems="<%= viewTreeManagementToolbarDisplayContext.getViewTypeItems() %>"
		/>

		<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "search();" %>'>
			<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
			<aui:input name="redirect" type="hidden" value="<%= viewTreeManagementToolbarDisplayContext.getPortletURL().toString() %>" />
			<aui:input name="onErrorRedirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteOrganizationIds" type="hidden" />
			<aui:input name="deleteUserIds" type="hidden" />
			<aui:input name="removeOrganizationIds" type="hidden" />
			<aui:input name="removeUserIds" type="hidden" />

			<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />
			<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

			<c:if test="<%= organization != null %>">

				<%
				portletDisplay.setShowBackIcon(true);
				portletDisplay.setURLBack(Validator.isNotNull(backURL) ? backURL : UsersAdminPortletURLUtil.createParentOrganizationViewTreeURL(organizationId, renderResponse));

				renderResponse.setTitle(organization.getName());
				%>

			</c:if>

			<c:if test="<%= (portletName.equals(UsersAdminPortletKeys.USERS_ADMIN) && usersListView.equals(UserConstants.LIST_VIEW_TREE)) || portletName.equals(UsersAdminPortletKeys.MY_ORGANIZATIONS) %>">
				<div id="breadcrumb">
					<liferay-site-navigation:breadcrumb
						breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, false, false, false, true, true) %>"
					/>
				</div>
			</c:if>

			<liferay-ui:search-container
				id="organizationUsers"
				searchContainer="<%= searchContainer %>"
				var="organizationUserSearchContainer"
			>
				<liferay-ui:search-container-row
					className="Object"
					modelVar="result"
				>

					<%
					Organization curOrganization = null;
					OrganizationActionDropdownItems organizationActionDropdownItems = null;
					Map<String, Object> rowData = new HashMap<String, Object>();
					User user2 = null;

					if (result instanceof Organization) {
						curOrganization = (Organization)result;

						organizationActionDropdownItems = new OrganizationActionDropdownItems(curOrganization, renderRequest, renderResponse);

						rowData.put("actions", StringUtil.merge(viewTreeManagementToolbarDisplayContext.getAvailableActions(curOrganization)));
					}
					else {
						user2 = (User)result;

						rowData.put("actions", StringUtil.merge(viewTreeManagementToolbarDisplayContext.getAvailableActions(user2)));
					}

					row.setData(rowData);
					%>

					<%@ include file="/organization/organization_user_search_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= displayStyle %>"
					markupView="lexicon"
					resultRowSplitter="<%= new OrganizationResultRowSplitter() %>"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</c:when>
	<c:otherwise>
		<clay:alert
			message="you-do-not-belong-to-an-organization-and-are-not-allowed-to-view-other-organizations"
		/>
	</c:otherwise>
</c:choose>