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
User selUser = userDisplayContext.getSelectedUser();
List<UserGroup> userGroups = userDisplayContext.getUserGroups();

currentURLObj.setParameter("historyKey", renderResponse.getNamespace() + "userGroups");
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="user-groups"
/>

<liferay-ui:membership-policy-error />

<h3 class="autofit-row sheet-subtitle">
	<span class="autofit-col autofit-col-expand">
		<span class="heading-text"><liferay-ui:message key="user-groups" /></span>
	</span>

	<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
		<span class="autofit-col">
			<span class="heading-end">
				<liferay-ui:icon
					cssClass="modify-link"
					id="openUserGroupsLink"
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message="select"
					url="javascript:;"
				/>
			</span>
		</span>
	</c:if>
</h3>

<liferay-util:buffer
	var="removeUserGroupIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="addUserGroupIds" type="hidden" />
<aui:input name="deleteUserGroupIds" type="hidden" />

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	cssClass="lfr-search-container-user-groups"
	curParam="userGroupsCur"
	emptyResultsMessage="this-user-does-not-belong-to-a-user-group"
	headerNames="name,null"
	iteratorURL="<%= currentURLObj %>"
	total="<%= userGroups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= userGroups.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="name"
			property="name"
		/>

		<c:if test="<%= !portletName.equals(myAccountPortletId) && !UserGroupMembershipPolicyUtil.isMembershipRequired((selUser != null) ? selUser.getUserId() : 0, userGroup.getUserGroupId()) %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= userGroup.getUserGroupId() %>" href="javascript:;"><%= removeUserGroupIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
	<aui:script use="escape,liferay-search-container">
		var Util = Liferay.Util;

		var searchContainer = Liferay.SearchContainer.get(
			'<portlet:namespace />userGroupsSearchContainer'
		);

		var searchContainerContentBox = searchContainer.get('contentBox');

		var addUserGroupIds = [];
		var deleteUserGroupIds = [];

		searchContainerContentBox.delegate(
			'click',
			function(event) {
				var link = event.currentTarget;

				var rowId = link.attr('data-rowId');

				var tr = link.ancestor('tr');

				var selectUserGroup = Util.getWindow(
					'<portlet:namespace />selectUserGroup'
				);

				if (selectUserGroup) {
					var selectButton = selectUserGroup.iframe.node
						.get('contentWindow.document')
						.one('.selector-button[data-usergroupid="' + rowId + '"]');

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, rowId);

				A.Array.removeItem(addUserGroupIds, rowId);

				deleteUserGroupIds.push(rowId);

				document.<portlet:namespace />fm.<portlet:namespace />addUserGroupIds.value = addUserGroupIds.join(
					','
				);
				document.<portlet:namespace />fm.<portlet:namespace />deleteUserGroupIds.value = deleteUserGroupIds.join(
					','
				);
			},
			'.modify-link'
		);

		Liferay.on('<portlet:namespace />enableRemovedUserGroups', function(event) {
			event.selectors.each(function(item, index, collection) {
				var userGroupId = item.attr('data-usergroupid');

				if (deleteUserGroupIds.indexOf(userGroupId) != -1) {
					Util.toggleDisabled(item, false);
				}
			});
		});

		A.one('#<portlet:namespace />openUserGroupsLink').on('click', function(event) {
			var searchContainerData = searchContainer.getData();

			if (!searchContainerData.length) {
				searchContainerData = [];
			}
			else {
				searchContainerData = searchContainerData.split(',');
			}

			Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},

					<%
					String eventName = liferayPortletResponse.getNamespace() + "selectUserGroup";
					%>

					id: '<%= eventName %>',

					selectedData: searchContainerData,
					title:
						'<liferay-ui:message arguments="user-group" key="select-x" />',

					<%
					PortletURL selectUserGroupURL = PortletProviderUtil.getPortletURL(request, UserGroup.class.getName(), PortletProvider.Action.BROWSE);

					selectUserGroupURL.setParameter("p_u_i_d", (selUser == null) ? "0" : String.valueOf(selUser.getUserId()));
					selectUserGroupURL.setParameter("eventName", eventName);
					selectUserGroupURL.setWindowState(LiferayWindowState.POP_UP);
					%>

					uri: '<%= selectUserGroupURL.toString() %>'
				},
				function(event) {
					var A = AUI();

					var entityId = event.entityid;

					var rowColumns = [];

					rowColumns.push(A.Escape.html(event.entityname));
					rowColumns.push(
						'<a class="modify-link" data-rowId="' +
							entityId +
							'" href="javascript:;"><%= UnicodeFormatter.toString(removeUserGroupIcon) %></a>'
					);

					searchContainer.addRow(rowColumns, entityId);

					searchContainer.updateDataStore();

					A.Array.removeItem(deleteUserGroupIds, entityId);

					addUserGroupIds.push(entityId);

					document.<portlet:namespace />fm.<portlet:namespace />addUserGroupIds.value = addUserGroupIds.join(
						','
					);
					document.<portlet:namespace />fm.<portlet:namespace />deleteUserGroupIds.value = deleteUserGroupIds.join(
						','
					);
				}
			);
		});
	</aui:script>
</c:if>