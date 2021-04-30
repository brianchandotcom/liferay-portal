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

List<Organization> organizations = userDisplayContext.getOrganizations();

String organizationIdsString = ParamUtil.getString(request, "organizationsSearchContainerPrimaryKeys");

currentURLObj.setParameter("historyKey", liferayPortletResponse.getNamespace() + "organizations");
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="organizations"
/>

<liferay-ui:membership-policy-error />

<clay:content-row
	containerElement="h3"
	cssClass="sheet-subtitle"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<span class="heading-text"><liferay-ui:message key="organizations" /></span>
	</clay:content-col>

	<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
		<clay:content-col>
			<span class="heading-end">
				<liferay-ui:icon
					cssClass="modify-link"
					id="selectOrganizationLink"
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message="select"
					method="get"
					url="javascript:;"
				/>
			</span>
		</clay:content-col>
	</c:if>
</clay:content-row>

<liferay-util:buffer
	var="removeOrganizationIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="addOrganizationIds" type="hidden" value="<%= organizationIdsString %>" />
<aui:input name="deleteOrganizationIds" type="hidden" />

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	cssClass="lfr-search-container-organizations"
	curParam="organizationsCur"
	emptyResultsMessage="this-user-does-not-belong-to-an-organization"
	headerNames="name,type,roles,null"
	iteratorURL="<%= currentURLObj %>"
	total="<%= organizations.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= organizations.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Organization"
		escapedModel="<%= true %>"
		keyProperty="organizationId"
		modelVar="organization"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand"
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(request, organization.getType()) %>"
		/>

		<%
		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();
		int userGroupRolesCount = 0;

		if (selUser != null) {
			userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(selUser.getUserId(), organization.getGroupId(), 0, PropsValues.USERS_ADMIN_ROLE_COLUMN_LIMIT);
			userGroupRolesCount = UserGroupRoleLocalServiceUtil.getUserGroupRolesCount(selUser.getUserId(), organization.getGroupId());
		}
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand"
			name="roles"
			value="<%= HtmlUtil.escape(UsersAdminUtil.getUserColumnText(locale, userGroupRoles, UsersAdmin.USER_GROUP_ROLE_TITLE_ACCESSOR, userGroupRolesCount)) %>"
		/>

		<c:if test="<%= !portletName.equals(myAccountPortletId) && ((selUser == null) || !OrganizationMembershipPolicyUtil.isMembershipProtected(permissionChecker, selUser.getUserId(), organization.getOrganizationId())) %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= organization.getOrganizationId() %>" href="javascript:;"><%= removeOrganizationIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
	<aui:script use="liferay-search-container">
		var AArray = A.Array;
		var Util = Liferay.Util;

		var addOrganizationIds = [];

		var organizationValues =
			document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds
				.value;

		if (organizationValues) {
			addOrganizationIds.push(organizationValues);
		}

		var deleteOrganizationIds = [];

		var searchContainer = Liferay.SearchContainer.get(
			'<portlet:namespace />organizationsSearchContainer'
		);

		var searchContainerContentBox = searchContainer.get('contentBox');

		searchContainerContentBox.delegate(
			'click',
			(event) => {
				var link = event.currentTarget;

				var rowId = link.attr('data-rowId');

				var tr = link.ancestor('tr');

				var selectOrganization = Util.getWindow(
					'<portlet:namespace />selectOrganization'
				);

				if (selectOrganization) {
					var selectButton = selectOrganization.iframe.node
						.get('contentWindow.document')
						.one('.selector-button[data-entityid="' + rowId + '"]');

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, rowId);

				AArray.removeItem(addOrganizationIds, rowId);

				deleteOrganizationIds.push(rowId);

				document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value = addOrganizationIds.join(
					','
				);
				document.<portlet:namespace />fm.<portlet:namespace />deleteOrganizationIds.value = deleteOrganizationIds.join(
					','
				);
			},
			'.modify-link'
		);

		var selectOrganizationLink = A.one(
			'#<portlet:namespace />selectOrganizationLink'
		);

		if (selectOrganizationLink) {
			selectOrganizationLink.on('click', (event) => {
				Util.openSelectionModal({
					onSelect: (selectedItem) => {
						if (selectedItem) {
							const entityId = selectedItem.entityid;

							const rowColumns = [];

							rowColumns.push(selectedItem.entityname);
							rowColumns.push(selectedItem.type);
							rowColumns.push('');
							rowColumns.push(
								'<a class="modify-link" data-rowId="' +
									entityId +
									'" href="javascript:;"><%= UnicodeFormatter.toString(removeOrganizationIcon) %></a>'
							);

							searchContainer.addRow(rowColumns, entityId);

							searchContainer.updateDataStore();

							AArray.removeItem(deleteOrganizationIds, entityId);

							addOrganizationIds.push(entityId);

							document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value = addOrganizationIds.join(
								','
							);
							document.<portlet:namespace />fm.<portlet:namespace />deleteOrganizationIds.value = deleteOrganizationIds.join(
								','
							);
						}
					},
					selectEventName: '<portlet:namespace />selectOrganization',
					selectedData: [searchContainer.getData(true)],
					title:
						'<liferay-ui:message arguments="organization" key="select-x" />',
					url:
						'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_organization.jsp" /><portlet:param name="p_u_i_d" value='<%= (selUser == null) ? "0" : String.valueOf(selUser.getUserId()) %>' /></portlet:renderURL>',
				});
			});
		}
	</aui:script>
</c:if>