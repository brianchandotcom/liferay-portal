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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String keywords = ParamUtil.getString(request, "keywords");
String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");
int type = ParamUtil.getInteger(request, "type", 1);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("displayStyle", displayStyle);
portletURL.setParameter("orderByCol", orderByCol);
portletURL.setParameter("orderByType", orderByType);
portletURL.setParameter("type", String.valueOf(type));

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();

portletURL.setParameter("keywords", keywords);
%>

<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="you-cannot-delete-a-system-role" />

<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_ROLE) %>">
	<liferay-portlet:renderURL varImpl="addRoleURL">
		<portlet:param name="mvcPath" value="/edit_role.jsp" />
		<portlet:param name="redirect" value="<%= portletURLString %>" />
	</liferay-portlet:renderURL>

	<liferay-frontend:add-menu>

		<%
		addRoleURL.setParameter("type", String.valueOf(RoleConstants.TYPE_REGULAR));
		%>

		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "regular-role") %>' url="<%= addRoleURL.toString() %>" />

		<%
		addRoleURL.setParameter("type", String.valueOf(RoleConstants.TYPE_SITE));
		%>

		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "site-role") %>' url="<%= addRoleURL.toString() %>" />

		<%
		addRoleURL.setParameter("type", String.valueOf(RoleConstants.TYPE_ORGANIZATION));
		%>

		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "organization-role") %>' url="<%= addRoleURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		portletURL.setParameter("type", String.valueOf(RoleConstants.TYPE_REGULAR));
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="regular-roles" selected="<%= type == RoleConstants.TYPE_REGULAR %>" />

		<%
		portletURL.setParameter("type", String.valueOf(RoleConstants.TYPE_SITE));
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="site-roles" selected="<%= type == RoleConstants.TYPE_SITE %>" />

		<%
		portletURL.setParameter("type", String.valueOf(RoleConstants.TYPE_ORGANIZATION));
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="organization-roles" selected="<%= type == RoleConstants.TYPE_ORGANIZATION %>" />

		<%
		portletURL.setParameter("type", String.valueOf(type));
		%>

	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURLString %>" name="searchFm">
			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" placeholder='<%= LanguageUtil.get(request, "keywords") %>' />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

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
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="deleteRoles" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURLString %>" cssClass="container-fluid-1280" method="get" name="fm">
	<aui:input name="deleteRoleIds" type="hidden" />

	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<liferay-ui:search-container
		id="roleSearch"
		rowChecker="<%= new RoleChecker(renderResponse) %>"
		searchContainer="<%= new RoleSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-container-results>

			<%
			RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

			total = RoleServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getTypesObj(), new LinkedHashMap<String, Object>());

			searchContainer.setTotal(total);

			results = RoleServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getTypesObj(), new LinkedHashMap<String, Object>(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setResults(results);

			portletURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));
			%>

		</liferay-ui:search-container-results>

		<aui:input name="rolesRedirect" type="hidden" value="<%= portletURL.toString() %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Role"
			keyProperty="roleId"
			modelVar="role"
		>

			<%
			PortletURL rowURL = null;

			if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {
				rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/edit_role.jsp");
				rowURL.setParameter("redirect", searchContainer.getIteratorURL().toString());
				rowURL.setParameter("roleId", String.valueOf(role.getRoleId()));
			}
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="title"
				value="<%= role.getTitle(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="type"
				value="<%= LanguageUtil.get(request, role.getTypeLabel()) %>"
			/>

			<c:if test="<%= (PropsValues.ROLES_ORGANIZATION_SUBTYPES.length > 0) || (PropsValues.ROLES_REGULAR_SUBTYPES.length > 0) || (PropsValues.ROLES_SITE_SUBTYPES.length > 0) %>">
				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="subType"
					value="<%= LanguageUtil.get(request, role.getSubtype()) %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="description"
				value="<%= HtmlUtil.escape(role.getDescription(locale)) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/role_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	var deleteRoles = function(deleteRoleIds) {
		var form = $(document.<portlet:namespace />fm);

		form.attr('method', 'post');

		form.fm('deleteRoleIds').val(deleteRoleIds);

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