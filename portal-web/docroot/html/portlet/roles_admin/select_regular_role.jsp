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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
String p_u_i_d = ParamUtil.getString(request, "p_u_i_d");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectRegularRole");

User selUser = PortalUtil.getSelectedUser(request);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/roles_admin/select_regular_role");

if (selUser != null) {
	portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
}

portletURL.setParameter("eventName", eventName);
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectRegularRoleFm">
	<liferay-ui:header
		title="roles"
	/>

	<liferay-ui:search-container
		headerNames="name"
		searchContainer="<%= new RoleSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-form
			page="/html/portlet/roles_admin/role_search.jsp"
		/>

		<%
		RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();
		%>

		<liferay-ui:search-container-results>

			<%
			if (filterManageableRoles) {
				List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_REGULAR}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

				roles = UsersAdminUtil.filterRoles(permissionChecker, roles);

				total = roles.size();
				results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());
			}
			else {
				results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_REGULAR}, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
				total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_REGULAR});
			}

			pageContext.setAttribute("results", results);
			pageContext.setAttribute("total", total);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Role"
			keyProperty="roleId"
			modelVar="role"
		>
			<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(role) %>" />
			<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(role) %>" />

			<liferay-ui:search-container-column-text
				name="title"
				value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= Validator.isNull(p_u_i_d)|| RoleMembershipPolicyUtil.isRoleAllowed((selUser != null) ? selUser.getUserId() : 0, role.getRoleId()) %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("roleid", role.getRoleId());
					data.put("roletitle", HtmlUtil.escapeAttribute(role.getTitle(locale)));
					data.put("searchcontainername", "roles");
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />selectRegularRoleFm.<portlet:namespace />name);
</aui:script>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	A.one('#<portlet:namespace />selectRegularRoleFm').delegate(
		'click',
		function(event) {
			var result = Util.getAttributes(event.currentTarget, 'data-');

			Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', result);

			Util.getWindow().close();
		},
		'.selector-button input'
	);
</aui:script>