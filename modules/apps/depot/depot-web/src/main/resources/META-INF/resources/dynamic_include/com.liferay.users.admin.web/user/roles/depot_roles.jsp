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
DepotAdminRolesDisplayContext depotAdminRolesDisplayContext = (DepotAdminRolesDisplayContext)request.getAttribute(DepotAdminRolesDisplayContext.class.getName());
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/depot/update_roles" />

<h3 class="autofit-row sheet-subtitle">
	<span class="autofit-col autofit-col-expand">
		<span class="heading-text"><%= depotAdminRolesDisplayContext.getLabel() %></span>
	</span>

	<c:if test="<%= depotAdminRolesDisplayContext.isSelectable() %>">
		<span class="autofit-col">
			<span class="heading-end">
				<liferay-ui:icon
					cssClass="modify-link"
					id="selectDepotRoleLink"
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message="select"
					method="get"
					url="javascript:;"
				/>
			</span>
		</span>
	</c:if>
</h3>

<liferay-util:buffer
	var="removeDepotRoleIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="addDepotGroupRolesGroupIds" type="hidden" />
<aui:input name="addDepotGroupRolesRoleIds" type="hidden" />
<aui:input name="deleteDepotGroupRolesGroupIds" type="hidden" />
<aui:input name="deleteDepotGroupRolesRoleIds" type="hidden" />

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	cssClass="lfr-search-container-depot-roles"
	curParam="depotRolesCur"
	emptyResultsMessage="this-user-is-not-assigned-any-asset-library-roles"
	headerNames="title,asset-library,null"
	id="depotRolesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= depotAdminRolesDisplayContext.getUserGroupRolesCount() %>"
>
	<liferay-ui:search-container-results
		results="<%= depotAdminRolesDisplayContext.getUserGroupRoles(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.UserGroupRole"
		escapedModel="<%= true %>"
		keyProperty="roleId"
		modelVar="userGroupRole"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="title"
		>
			<liferay-ui:icon
				iconCssClass="<%= RolesAdminUtil.getIconCssClass(userGroupRole.getRole()) %>"
				label="<%= true %>"
				message="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="<%= depotAdminRolesDisplayContext.getAssetLibraryLabel() %>"
		>
			<liferay-staging:descriptive-name
				group="<%= userGroupRole.getGroup() %>"
			/>
		</liferay-ui:search-container-column-text>

		<c:if test="<%= depotAdminRolesDisplayContext.isDeletable() %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-groupId="<%= userGroupRole.getGroupId() %>" data-rowId="<%= userGroupRole.getRoleId() %>" href="javascript:;"><%= removeDepotRoleIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<c:if test="<%= depotAdminRolesDisplayContext.isSelectable() %>">
	<aui:script use="liferay-search-container">
		var Util = Liferay.Util;

		var searchContainer = Liferay.SearchContainer.get(
			'<portlet:namespace />depotRolesSearchContainer'
		);

		var searchContainerContentBox = searchContainer.get('contentBox');

		searchContainerContentBox.delegate(
			'click',
			function(event) {
				var link = event.currentTarget;
				var tr = link.ancestor('tr');

				var groupId = link.getAttribute('data-groupId');
				var rowId = link.getAttribute('data-rowId');

				var selectDepotRole = Util.getWindow(
					'<portlet:namespace />selectDepotRole'
				);

				if (selectDepotRole) {
					var selectButton = selectDepotRole.iframe.node
						.get('contentWindow.document')
						.one(
							'.selector-button[data-groupid="' +
								groupId +
								'"][data-roleid="' +
								rowId +
								'"]'
						);

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, rowId);

				<portlet:namespace />deleteDepotGroupRole(rowId, groupId);
			},
			'.modify-link'
		);

		var <portlet:namespace />addDepotGroupRolesGroupIds = [];
		var <portlet:namespace />addDepotGroupRolesRoleIds = [];
		var <portlet:namespace />deleteDepotGroupRolesGroupIds = [];
		var <portlet:namespace />deleteDepotGroupRolesRoleIds = [];

		function <portlet:namespace />deleteDepotGroupRole(roleId, groupId) {
			for (
				var i = 0;
				i < <portlet:namespace />addDepotGroupRolesRoleIds.length;
				i++
			) {
				if (
					<portlet:namespace />addDepotGroupRolesGroupIds[i] == groupId &&
					<portlet:namespace />addDepotGroupRolesRoleIds[i] == roleId
				) {
					<portlet:namespace />addDepotGroupRolesGroupIds.splice(i, 1);
					<portlet:namespace />addDepotGroupRolesRoleIds.splice(i, 1);

					break;
				}
			}

			<portlet:namespace />deleteDepotGroupRolesGroupIds.push(groupId);
			<portlet:namespace />deleteDepotGroupRolesRoleIds.push(roleId);

			document.<portlet:namespace />fm.<portlet:namespace />addDepotGroupRolesGroupIds.value = <portlet:namespace />addDepotGroupRolesGroupIds.join(
				','
			);
			document.<portlet:namespace />fm.<portlet:namespace />addDepotGroupRolesRoleIds.value = <portlet:namespace />addDepotGroupRolesRoleIds.join(
				','
			);
			document.<portlet:namespace />fm.<portlet:namespace />deleteDepotGroupRolesGroupIds.value = <portlet:namespace />deleteDepotGroupRolesGroupIds.join(
				','
			);
			document.<portlet:namespace />fm.<portlet:namespace />deleteDepotGroupRolesRoleIds.value = <portlet:namespace />deleteDepotGroupRolesRoleIds.join(
				','
			);
		}

		Liferay.on(
			'<%= depotAdminRolesDisplayContext.getDepotRoleSyncEntitiesEventName() %>',
			function(event) {
				event.selectors.each(function(item, index, collection) {
					var groupId = item.attr('data-groupid');
					var roleId = item.attr('data-roleid');

					for (
						var k = 0;
						k < <portlet:namespace />addDepotGroupRolesGroupIds.length;
						k++
					) {
						if (
							<portlet:namespace />addDepotGroupRolesGroupIds[k] ==
								groupId &&
							<portlet:namespace />addDepotGroupRolesRoleIds[k] == roleId
						) {
							Util.toggleDisabled(item, true);

							break;
						}
					}

					for (
						var n = 0;
						n < <portlet:namespace />deleteDepotGroupRolesGroupIds.length;
						n++
					) {
						if (
							<portlet:namespace />deleteDepotGroupRolesGroupIds[n] ==
								groupId &&
							<portlet:namespace />deleteDepotGroupRolesRoleIds[n] ==
								roleId
						) {
							Util.toggleDisabled(item, false);

							break;
						}
					}
				});
			}
		);

		A.one('#<portlet:namespace />selectDepotRoleLink').on('click', function(event) {
			Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
					},

					id:
						'<%= depotAdminRolesDisplayContext.getSelectDepotRolesEventName() %>',
					selectedData: [],
					title: '<liferay-ui:message arguments="role" key="select-x" />',
					uri:
						'<%= depotAdminRolesDisplayContext.getSelectDepotRolesURL() %>',
				},
				function(event) {
					var A = AUI();
					var LString = A.Lang.String;

					var rowColumns = [];

					rowColumns.push(
						'<i class="' +
							event.iconcssclass +
							'"></i> ' +
							LString.escapeHTML(event.rolename)
					);

					rowColumns.push(event.groupdescriptivename);

					rowColumns.push(
						'<a class="modify-link" data-groupId="' +
							event.groupid +
							'" data-rowId="' +
							event.roleid +
							'" href="javascript:;"><%= UnicodeFormatter.toString(removeDepotRoleIcon) %></a>'
					);

					for (
						var i = 0;
						i < <portlet:namespace />deleteDepotGroupRolesRoleIds.length;
						i++
					) {
						if (
							<portlet:namespace />deleteDepotGroupRolesGroupIds[i] ==
								event.groupid &&
							<portlet:namespace />deleteDepotGroupRolesRoleIds[i] ==
								event.roleid
						) {
							<portlet:namespace />deleteDepotGroupRolesGroupIds.splice(
								i,
								1
							);
							<portlet:namespace />deleteDepotGroupRolesRoleIds.splice(
								i,
								1
							);

							break;
						}
					}

					<portlet:namespace />addDepotGroupRolesGroupIds.push(event.groupid);
					<portlet:namespace />addDepotGroupRolesRoleIds.push(event.roleid);

					document.<portlet:namespace />fm.<portlet:namespace />addDepotGroupRolesGroupIds.value = <portlet:namespace />addDepotGroupRolesGroupIds.join(
						','
					);
					document.<portlet:namespace />fm.<portlet:namespace />addDepotGroupRolesRoleIds.value = <portlet:namespace />addDepotGroupRolesRoleIds.join(
						','
					);
					document.<portlet:namespace />fm.<portlet:namespace />deleteDepotGroupRolesGroupIds.value = <portlet:namespace />deleteDepotGroupRolesGroupIds.join(
						','
					);
					document.<portlet:namespace />fm.<portlet:namespace />deleteDepotGroupRolesRoleIds.value = <portlet:namespace />deleteDepotGroupRolesRoleIds.join(
						','
					);

					searchContainer.addRow(rowColumns, event.roleid);

					searchContainer.updateDataStore();
				}
			);
		});
	</aui:script>
</c:if>