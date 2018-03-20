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
int type = ParamUtil.getInteger(request, "type", 1);
String keywords = ParamUtil.getString(request, "keywords");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(RolesAdminPortletKeys.ROLES_ADMIN, "displayStyle", "descriptive");
}
else {
	portalPreferences.setValue(RolesAdminPortletKeys.ROLES_ADMIN, "displayStyle", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("type", String.valueOf(type));
portletURL.setParameter("displayStyle", displayStyle);
portletURL.setParameter("orderByCol", orderByCol);
portletURL.setParameter("orderByType", orderByType);

String portletURLString = portletURL.toString();

portletURL.setParameter("keywords", keywords);

pageContext.setAttribute("portletURL", portletURL);
%>

<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="you-cannot-delete-a-system-role" />

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= roleDisplayContext.getViewRoleNavigationItems(portletURL) %>"
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="roleSearch"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"title"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<aui:form action="<%= portletURLString %>" name="searchFm">
				<liferay-ui:input-search
					autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive", "icon", "list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_ROLE) %>">
			<liferay-portlet:renderURL varImpl="addRoleURL">
				<portlet:param name="mvcPath" value="/edit_role.jsp" />
				<portlet:param name="redirect" value="<%= portletURLString %>" />
				<portlet:param name="tabs1" value="details" />
				<portlet:param name="type" value="<%= String.valueOf(type) %>" />
			</liferay-portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>

				<%
				String title = null;

				if (type == RoleConstants.TYPE_SITE) {
					title = "site-role";
				}
				else if (type == RoleConstants.TYPE_ORGANIZATION) {
					title = "organization-role";
				}
				else {
					title = "regular-role";
				}
				%>

				<liferay-frontend:add-menu-item
					title="<%= LanguageUtil.get(request, title) %>"
					url="<%= addRoleURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteRoles"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURLString %>" cssClass="container-fluid-1280" method="get" name="fm">
	<aui:input name="deleteRoleIds" type="hidden" />

	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<%
	SearchContainer searchContainer = new RoleSearch(renderRequest, portletURL);
	%>

	<liferay-ui:search-container
		id="roleSearch"
		rowChecker="<%= new RoleChecker(renderResponse) %>"
		searchContainer="<%= searchContainer %>"
		var="roleSearchContainer"
	>
		<liferay-ui:search-container-results>

			<%
			RoleSearchTerms searchTerms = (RoleSearchTerms)roleSearchContainer.getSearchTerms();

			total = RoleServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getTypesObj(), new LinkedHashMap<String, Object>());

			roleSearchContainer.setTotal(total);

			results = RoleServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getTypesObj(), new LinkedHashMap<String, Object>(), roleSearchContainer.getStart(), roleSearchContainer.getEnd(), roleSearchContainer.getOrderByComparator());

			roleSearchContainer.setResults(results);

			portletURL.setParameter(roleSearchContainer.getCurParam(), String.valueOf(roleSearchContainer.getCur()));
			%>

		</liferay-ui:search-container-results>

		<aui:input name="rolesRedirect" type="hidden" value="<%= portletURL.toString() %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Role"
			keyProperty="roleId"
			modelVar="role"
		>

			<%
			PortletURL rowURL = null;

			if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {
				rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/edit_role.jsp");
				rowURL.setParameter("tabs1", "details");
				rowURL.setParameter("redirect", roleSearchContainer.getIteratorURL().toString());
				rowURL.setParameter("roleId", String.valueOf(role.getRoleId()));
			}
			%>

			<%@ include file="/search_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	var deleteRoles = function(deleteRoleIds) {
		var form = $(document.<portlet:namespace />fm);

		form.attr('method', 'post');

		form.fm('deleteRoleIds').val(deleteRoleIds);

		document.<portlet:namespace />fm.p_p_lifecycle.value = '1';

		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			submitForm(form, '<portlet:actionURL name="deleteRoles"><portlet:param name="redirect" value="<%= portletURL.toString() %>" /></portlet:actionURL>');
		}
	};

	$('#<portlet:namespace />deleteRoles').on(
		'click',
		function() {
			deleteRoles(
				Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds')
			);
		}
	);
</aui:script>