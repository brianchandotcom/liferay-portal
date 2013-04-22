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
int step = ParamUtil.getInteger(request, "step");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectSiteRole");

User selUser = PortalUtil.getSelectedUser(request);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/roles_admin/select_site_role");

if (selUser != null) {
	portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
}

portletURL.setParameter("eventName", eventName);

long uniqueGroupId = 0;

List<Group> groups = null;

if (step == 1) {
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

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectSiteRoleFm">
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

					<liferay-ui:search-container-column-text
						name="name"
						value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= LanguageUtil.get(pageContext, group.getTypeLabel()) %>"
					/>

					<liferay-ui:search-container-column-text>

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						data.put("groupid", group.getGroupId());
						%>

						<aui:button cssClass="group-selector-button" data="<%= data %>" value="choose" />
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			<aui:script use="aui-base">
				A.one('#<portlet:namespace />selectSiteRoleFm').delegate(
					'click',
					function(event) {
						var groupId = event.currentTarget.attr('data-groupid');

						document.<portlet:namespace />selectSiteRoleFm.<portlet:namespace />groupId.value = groupId;

						<%
						portletURL.setParameter("resetCur", Boolean.TRUE.toString());
						portletURL.setParameter("step", "2");
						%>

						submitForm(document.<portlet:namespace />selectSiteRoleFm, "<%= portletURL.toString() %>");
					},
					'.group-selector-button input'
				);
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

				<div class="separator"><!-- --></div>

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

					<liferay-ui:search-container-column-text
						name="title"
						value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
					/>

					<liferay-ui:search-container-column-text>
						<c:if test="<%= Validator.isNull(p_u_i_d) || SiteMembershipPolicyUtil.isRoleAllowed((selUser != null) ? selUser.getUserId() : 0, group.getGroupId(), role.getRoleId()) %>">

							<%
							Map<String, Object> data = new HashMap<String, Object>();

							data.put("groupid", group.getGroupId());
							data.put("groupname", HtmlUtil.escapeAttribute(group.getDescriptiveName(locale)));
							data.put("roleid", role.getRoleId());
							data.put("roletitle", HtmlUtil.escapeAttribute(role.getTitle(locale)));
							data.put("searchcontainername", "siteRoles");
							%>

							<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
						</c:if>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />selectSiteRoleFm.<portlet:namespace />name);
			</aui:script>

			<aui:script use="aui-base">
				var Util = Liferay.Util;

				A.one('#<portlet:namespace />selectSiteRoleFm').delegate(
					'click',
					function(event) {
						var result = Util.getAttributes(event.currentTarget, 'data-');

						Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', result);

						Util.getWindow().close();
					},
					'.selector-button input'
				);
			</aui:script>
		</c:when>
	</c:choose>
</aui:form>