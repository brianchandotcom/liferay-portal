<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
int step = ParamUtil.getInteger(request, "step");
long userId = ParamUtil.getLong(request, "userId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/roles_admin/select_site_role");
portletURL.setParameter("userId", String.valueOf(userId));

User selUser = null;
long uniqueGroupId = 0;

List<Group> groups = null;

if (step == 1) {
	selUser = UserServiceUtil.getUserById(userId);

	groups = selUser.getGroups();

	if (filterManageableGroups) {
		groups = UsersAdminUtil.filterGroups(permissionChecker, groups);
	}

	if (groups.size() == 1) {
		step = 2;

		uniqueGroupId = groups.get(0).getGroupId();
	}
}
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<c:choose>
		<c:when test="<%= step == 1 %>">
			<aui:input name="groupId" type="hidden" />

			<liferay-ui:header
				title="site-roles"
			/>

			<div class="portlet-msg-info">
				<liferay-ui:message key="please-select-a-site-to-which-you-will-assign-a-site-role" />
			</div>

			<%
			portletURL.setParameter("step", "1");
			%>

			<liferay-ui:search-container
				searchContainer="<%= new GroupSearch(renderRequest, portletURL) %>"
			>
				<liferay-ui:search-container-results>

					<%
					total = groups.size();
					results = ListUtil.subList(groups, searchContainer.getStart(), searchContainer.getEnd());

					pageContext.setAttribute("results", results);
					pageContext.setAttribute("total", total);
					%>

				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Group"
					escapedModel="<%= true %>"
					keyProperty="groupId"
					modelVar="group"
					rowIdProperty="friendlyURL"
				>

					<%
					StringBundler sb = new StringBundler(5);

					sb.append("javascript:");
					sb.append(renderResponse.getNamespace());
					sb.append("selectGroup('");
					sb.append(group.getGroupId());
					sb.append("');");

					String rowHREF = sb.toString();
					%>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="name"
						value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="type"
						value="<%= LanguageUtil.get(pageContext, group.getTypeLabel()) %>"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			<aui:script>
				function <portlet:namespace />selectGroup(groupId) {
					document.<portlet:namespace />fm.<portlet:namespace />groupId.value = groupId;

					<%
					portletURL.setParameter("resetCur", Boolean.TRUE.toString());
					portletURL.setParameter("step", "2");
					%>

					submitForm(document.<portlet:namespace />fm, "<%= portletURL.toString() %>");
				}
			</aui:script>
		</c:when>

		<c:when test="<%= step == 2 %>">

			<%
			long groupId = ParamUtil.getLong(request, "groupId", uniqueGroupId);
			%>

			<aui:input name="step" type="hidden" value="2" />
			<aui:input name="groupId" type="hidden" value="<%= String.valueOf(groupId) %>" />

			<liferay-ui:header
				title="site-roles"
			/>

			<%
			Group group = GroupServiceUtil.getGroup(groupId);

			portletURL.setParameter("step", "1");

			String breadcrumbs = "<a href=\"" + portletURL.toString() + "\">" + LanguageUtil.get(pageContext, "sites") + "</a> &raquo; " + HtmlUtil.escape(group.getDescriptiveName(locale));
			%>

			<div class="breadcrumbs">
				<%= breadcrumbs %>
			</div>

			<%
			portletURL.setParameter("step", "2");
			portletURL.setParameter("groupId", String.valueOf(groupId));
			%>

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
						List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_SITE}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

						roles = UsersAdminUtil.filterGroupRoles(permissionChecker, groupId, roles);

						total = roles.size();
						results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());
					}
					else {
						results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_SITE}, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
						total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_SITE});
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

					<%
					StringBundler sb = new StringBundler(14);

					sb.append("javascript:opener.");
					sb.append(renderResponse.getNamespace());
					sb.append("selectRole('");
					sb.append(role.getRoleId());
					sb.append("', '");
					sb.append(UnicodeFormatter.toString(role.getTitle(locale)));
					sb.append("', '");
					sb.append("communityRoles");
					sb.append("', '");
					sb.append(UnicodeFormatter.toString(group.getDescriptiveName(locale)));
					sb.append("', '");
					sb.append(group.getGroupId());
					sb.append("');");
					sb.append("window.close();");

					String rowHREF = sb.toString();
					%>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="title"
						value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
			</aui:script>
		</c:when>
	</c:choose>
</aui:form>