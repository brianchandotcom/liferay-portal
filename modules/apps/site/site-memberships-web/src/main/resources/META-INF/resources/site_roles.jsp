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
long groupId = ParamUtil.getLong(request, "groupId");
int roleType = ParamUtil.getInteger(request, "roleType", RoleConstants.TYPE_SITE);

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String eventName = ParamUtil.getString(request, "eventName", renderResponse.getNamespace() + "selectSiteRole");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/site_roles.jsp");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("roleType", String.valueOf(roleType));
portletURL.setParameter("displayStyle", displayStyle);
portletURL.setParameter("eventName", eventName);

RoleSearch roleSearch = new RoleSearch(renderRequest, portletURL);

RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

rowChecker.setRowIds("rowIdsRole");

roleSearch.setRowChecker(rowChecker);

RoleSearchTerms searchTerms = (RoleSearchTerms)roleSearch.getSearchTerms();

List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {roleType}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, roleSearch.getOrderByComparator());

roles = UsersAdminUtil.filterGroupRoles(permissionChecker, groupId, roles);

int rolesCount = roles.size();

roleSearch.setTotal(rolesCount);

roleSearch.setResults(ListUtil.subList(roles, roleSearch.getStart(), roleSearch.getEnd()));
%>

<clay:navigation-bar
	items="<%= siteMembershipsDisplayContext.getSiteRolesNavigationItems() %>"
/>

<liferay-frontend:management-bar
	disabled="<%= (rolesCount <= 0) && Validator.isNull(searchTerms.getKeywords()) %>"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= roleSearch.getOrderByCol() %>"
			orderByType="<%= roleSearch.getOrderByType() %>"
			orderColumns='<%= new String[] {"title"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<c:if test="<%= (rolesCount > 0) || Validator.isNotNull(searchTerms.getKeywords()) %>">
			<li>
				<aui:form action="<%= portletURL.toString() %>" name="searchFm">
					<liferay-ui:input-search
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280 portlet-site-memberships-assign-site-roles" name="fm">
	<liferay-ui:search-container
		id="siteRoles"
		searchContainer="<%= roleSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Role"
			escapedModel="<%= true %>"
			keyProperty="roleId"
			modelVar="role"
		>
			<%@ include file="/role_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />siteRoles');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(eventName) %>',
				{
					data: event.elements.allSelectedElements.getDOMNodes()
				}
			);
		}
	);
</aui:script>