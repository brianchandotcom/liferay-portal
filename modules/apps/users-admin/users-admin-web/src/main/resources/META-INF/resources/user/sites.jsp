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
User selUser = (User)request.getAttribute("user.selUser");
List<Group> groups = (List<Group>)request.getAttribute("user.groups");
List<Group> inheritedSites = (List<Group>)request.getAttribute("user.inheritedSites");

currentURLObj.setParameter("historyKey", renderResponse.getNamespace() + "sites");
%>

<liferay-ui:error-marker key="errorSection" value="sites" />

<liferay-ui:membership-policy-error />

<liferay-util:buffer var="removeGroupIcon">
	<liferay-ui:icon
		iconCssClass="icon-remove"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="addGroupIds" type="hidden" />
<aui:input name="deleteGroupIds" type="hidden" />

<h3><liferay-ui:message key="sites" /></h3>

<liferay-ui:search-container
	curParam="sitesCur"
	headerNames="name,roles,null"
	iteratorURL="<%= currentURLObj %>"
	total="<%= groups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= groups.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
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

		<%
		List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(selUser.getUserId(), group.getGroupId(), 0, PropsValues.USERS_ADMIN_ROLE_COLUMN_LIMIT);
		int userGroupRolesCount = UserGroupRoleLocalServiceUtil.getUserGroupRolesCount(selUser.getUserId(), group.getGroupId());
		%>

		<liferay-ui:search-container-column-text
			name="roles"
			value="<%= HtmlUtil.escape(UsersAdminUtil.getUserColumnText(locale, userGroupRoles, UsersAdmin.USER_GROUP_ROLE_TITLE_ACCESSOR, userGroupRolesCount)) %>"
		/>

		<c:if test="<%= !portletName.equals(myAccountPortletId) && !SiteMembershipPolicyUtil.isMembershipRequired(selUser.getUserId(), group.getGroupId()) && !SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, selUser.getUserId(), group.getGroupId()) %>">
			<liferay-ui:search-container-column-text>
				<c:if test="<%= group.isManualMembership() %>">
					<a class="modify-link" data-rowId="<%= group.getGroupId() %>" href="javascript:;"><%= removeGroupIcon %></a>
				</c:if>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		iconCssClass="icon-search"
		id="selectSiteLink"
		label="<%= true %>"
		linkCssClass="btn btn-default"
		message="select"
		url="javascript:;"
	/>

	<aui:script use="liferay-search-container">
		var AArray = A.Array;
		var Util = Liferay.Util;

		var addGroupIds = [];
		var deleteGroupIds = [];

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupsSearchContainer');

		var searchContainerContentBox = searchContainer.get('contentBox');

		var handleOnSelect = A.one('#<portlet:namespace />selectSiteLink').on(
			'click',
			function(event) {
				Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true
						},

						<%
						String eventName = liferayPortletResponse.getNamespace() + "selectSite";
						%>

						id: '<%= eventName %>',

						title: '<liferay-ui:message arguments="site" key="select-x" />',

						<%
						PortletURL groupSelectorURL = PortletProviderUtil.getPortletURL(request, Group.class.getName(), PortletProvider.Action.BROWSE);

						groupSelectorURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
						groupSelectorURL.setParameter("includeCurrentGroup", Boolean.FALSE.toString());
						groupSelectorURL.setParameter("manualMembership", Boolean.TRUE.toString());
						groupSelectorURL.setParameter("eventName", eventName);
						groupSelectorURL.setWindowState(LiferayWindowState.POP_UP);
						%>

						uri: '<%= groupSelectorURL.toString() %>'
					},
					function(event) {
						var rowColumns = [];

						rowColumns.push(event.groupdescriptivename);
						rowColumns.push('');
						rowColumns.push('<a class="modify-link" data-rowId="' + event.groupid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeGroupIcon) %></a>');

						searchContainer.addRow(rowColumns, event.groupid);

						searchContainer.updateDataStore();

						addGroupIds.push(event.groupid);

						AArray.removeItem(deleteGroupIds, event.groupid);

						document.<portlet:namespace />fm.<portlet:namespace />addGroupIds.value = addGroupIds.join(',');
						document.<portlet:namespace />fm.<portlet:namespace />deleteGroupIds.value = deleteGroupIds.join(',');
					}
				);
			}
		);

		var handleOnModifyLink = searchContainerContentBox.delegate(
			'click',
			function(event) {
				var link = event.currentTarget;

				var rowId = link.attr('data-rowId');
				var tr = link.ancestor('tr');

				var selectGroup = Util.getWindow('<portlet:namespace />selectGroup');

				if (selectGroup) {
					var selectButton = selectGroup.iframe.node.get('contentWindow.document').one('.selector-button[data-groupid="' + rowId + '"]');

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, rowId);

				AArray.removeItem(addGroupIds, event.rowId);

				deleteGroupIds.push(rowId);

				document.<portlet:namespace />fm.<portlet:namespace />addGroupIds.value = addGroupIds.join(',');
				document.<portlet:namespace />fm.<portlet:namespace />deleteGroupIds.value = deleteGroupIds.join(',');
			},
			'.modify-link'
		);

		var handleEnableRemoveSite = Liferay.on(
			'<portlet:namespace />enableRemovedSites',
			function(event) {
				event.selectors.each(
					function(item, index, collection) {
						var groupId = item.attr('data-groupid');

						if (deleteGroupIds.indexOf(groupId) != -1) {
							Util.toggleDisabled(item, false);
						}
					}
				);
			}
		);

		var onDestroyPortlet = function(event) {
			if (event.portletId === '<%= portletDisplay.getId() %>') {
				Liferay.detach(handleOnSelect);
				Liferay.detach(handleOnModifyLink);
				Liferay.detach(handleEnableRemoveSite);

				Liferay.detach('destroyPortlet', onDestroyPortlet);
			}
		};

		Liferay.on('destroyPortlet', onDestroyPortlet);
	</aui:script>
</c:if>

<h3><liferay-ui:message key="inherited-sites" /></h3>

<c:if test="<%= inheritedSites.isEmpty() %>">
	<liferay-ui:message key="this-user-does-not-have-any-inherited-sites" />
</c:if>

<liferay-ui:search-container
	curParam="inheritedSitesCur"
	headerNames="name,roles"
	iteratorURL="<%= currentURLObj %>"
	total="<%= inheritedSites.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= inheritedSites.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
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

		<%
		List<Role> inheritedRoles = RoleLocalServiceUtil.getUserGroupGroupRoles(selUser.getUserId(), inheritedSite.getGroupId(), 0, 50);
		int inheritedRolesCount = RoleLocalServiceUtil.getUserGroupGroupRolesCount(selUser.getUserId(), inheritedSite.getGroupId());
		%>

		<liferay-ui:search-container-column-text
			name="roles"
			value="<%= UsersAdminUtil.getUserColumnText(locale, inheritedRoles, Role.TITLE_ACCESSOR, inheritedRolesCount) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>