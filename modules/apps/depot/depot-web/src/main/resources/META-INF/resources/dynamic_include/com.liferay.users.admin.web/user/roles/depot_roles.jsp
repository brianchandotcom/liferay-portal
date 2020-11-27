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

<clay:content-row
	containerElement="h3"
	cssClass="sheet-subtitle"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<span class="heading-text"><%= depotAdminRolesDisplayContext.getLabel() %></span>
	</clay:content-col>

	<c:if test="<%= depotAdminRolesDisplayContext.isSelectable() %>">
		<clay:content-col>
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
			</clay:content-col>
		</span>
	</c:if>
</clay:content-row>

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
			cssClass="table-cell-expand"
			name="title"
		>
			<liferay-ui:icon
				iconCssClass="<%= RolesAdminUtil.getIconCssClass(userGroupRole.getRole()) %>"
				label="<%= true %>"
				message="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand"
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

		searchContainer.updateDataStore(
			searchContainerContentBox
				.all('.modify-link')
				.getData()
				.map(function (data) {
					return data.groupid + '-' + data.rowid;
				})
		);

		searchContainerContentBox.delegate(
			'click',
			function (event) {
				var link = event.currentTarget;
				var tr = link.ancestor('tr');

				var groupId = link.getAttribute('data-groupId');
				var rowId = link.getAttribute('data-rowId');
				var id = groupId + '-' + rowId;

				var selectDepotRole = Util.getWindow(
					'<portlet:namespace />selectDepotRole'
				);

				if (selectDepotRole) {
					var selectButton = selectDepotRole.iframe.node
						.get('contentWindow.document')
						.one(
							'.selector-button[data-groupid="' +
								groupId +
								'"][data-entityid="' +
								rowId +
								'"]'
						);

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, id);

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

		A.one('#<portlet:namespace />selectDepotRoleLink').on('click', function (
			event
		) {
			Util.openSelectionModal({
				onSelect: function (event) {
					var A = AUI();
					var LString = A.Lang.String;

					var id = event.groupid + '-' + event.entityid;

					var rowColumns = [];

					rowColumns.push(
						'<i class="' +
							event.iconcssclass +
							'"></i> ' +
							Liferay.Util.escapeHTML(event.rolename)
					);

					rowColumns.push(event.groupdescriptivename);

					rowColumns.push(
						'<a class="modify-link" data-groupId="' +
							event.groupid +
							'" data-rowId="' +
							event.entityid +
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
								event.entityid
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
					<portlet:namespace />addDepotGroupRolesRoleIds.push(event.entityid);

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

					searchContainer.addRow(rowColumns, id);

					searchContainer.updateDataStore();
				},
				selectedData: searchContainer.getData(true),
				selectEventName:
					'<%= depotAdminRolesDisplayContext.getSelectDepotRolesEventName() %>',
				title: '<liferay-ui:message arguments="role" key="select-x" />',
				url: '<%= depotAdminRolesDisplayContext.getSelectDepotRolesURL() %>',
			});
		});
	</aui:script>
</c:if>