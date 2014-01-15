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
User selUser = (User)request.getAttribute("user.selUser");
List<Group> groups = (List<Group>)request.getAttribute("user.groups");
List<Group> inheritedSites = (List<Group>)request.getAttribute("user.inheritedSites");
%>

<liferay-ui:error-marker key="errorSection" value="sites" />

<liferay-ui:membership-policy-error />

<liferay-util:buffer var="removeGroupIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<h3><liferay-ui:message key="sites" /></h3>

<liferay-ui:search-container
	headerNames="name,roles,null"
>
	<liferay-ui:search-container-results
		results="<%= groups %>"
		total="<%= groups.size() %>"
	/>

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
			buffer="buffer"
			name="roles"
		>

			<%
			List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(selUser.getUserId(), group.getGroupId());

			for (UserGroupRole userGroupRole : userGroupRoles) {
				Role role = RoleLocalServiceUtil.getRole(userGroupRole.getRoleId());

				buffer.append(HtmlUtil.escape(role.getTitle(locale)));
				buffer.append(StringPool.COMMA_AND_SPACE);
			}

			if (!userGroupRoles.isEmpty()) {
				buffer.setIndex(buffer.index() - 1);
			}
			%>

		</liferay-ui:search-container-column-text>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !SiteMembershipPolicyUtil.isMembershipRequired(selUser.getUserId(), group.getGroupId()) && !SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, selUser.getUserId(), group.getGroupId()) %>">
			<liferay-ui:search-container-column-text>
				<c:if test="<%= group.isManualMembership() %>">
					<a class="modify-link" data-rowId="<%= group.getGroupId() %>" href="javascript:;"><%= removeGroupIcon %></a>
				</c:if>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		iconCssClass="icon-search"
		id="selectSiteLink"
		label="<%= true %>"
		linkCssClass="btn"
		message="select"
		url="javascript:;"
	/>

	<portlet:renderURL var="groupSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="struts_action" value="/users_admin/select_site" />
		<portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" />
	</portlet:renderURL>

	<aui:script use="liferay-search-container">
		A.one('#<portlet:namespace />selectSiteLink').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true,
							width: 600
						},
						id: '<portlet:namespace />selectGroup',
						title: '<liferay-ui:message arguments="site" key="select-x" />',
						uri: '<%= groupSelectorURL.toString() %>'
					},
					function(event) {
						var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupsSearchContainer');

						var rowColumns = [];

						rowColumns.push(event.groupdescriptivename);
						rowColumns.push('');
						rowColumns.push('<a class="modify-link" data-rowId="' + event.groupid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeGroupIcon) %></a>');

						searchContainer.addRow(rowColumns, event.groupid);
						searchContainer.updateDataStore();
					}
				);
			}
		);

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupsSearchContainer');

		searchContainer.get('contentBox').delegate(
			'click',
			function(event) {
				var link = event.currentTarget;
				var tr = link.ancestor('tr');

				searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
			},
			'.modify-link'
		);
	</aui:script>
</c:if>

<h3><liferay-ui:message key="inherited-sites" /></h3>

<c:if test="<%= inheritedSites.isEmpty() %>">
	<liferay-ui:message key="this-user-does-not-have-any-inherited-sites" />
</c:if>

<liferay-ui:search-container
	headerNames="name,roles"
>
	<liferay-ui:search-container-results
		results="<%= inheritedSites %>"
		total="<%= inheritedSites.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="inheritedSite"
		rowIdProperty="friendlyURL"
	>
		<liferay-ui:search-container-column-text
			name="name"
			value="<%= HtmlUtil.escape(inheritedSite.getDescriptiveName(locale)) %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="roles"
		>

			<%
			long userId = selUser.getUserId();
			long groupId = inheritedSite.getGroupId();

			List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(userId, groupId);

			for (UserGroupRole userGroupRole : userGroupRoles) {
				Role role = RoleLocalServiceUtil.getRole(userGroupRole.getRoleId());

				buffer.append(HtmlUtil.escape(role.getTitle(locale)));
				buffer.append(StringPool.COMMA_AND_SPACE);
			}

			List<Role> inheritedRoles = RoleLocalServiceUtil.getUserGroupGroupRoles(userId, groupId);

			for (Role role : inheritedRoles) {
				buffer.append(HtmlUtil.escape(role.getTitle(locale)));
				buffer.append(StringPool.COMMA_AND_SPACE);
			}

			if (!inheritedRoles.isEmpty() || !userGroupRoles.isEmpty()) {
				buffer.setIndex(buffer.index() - 1);
			}
			%>

		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>