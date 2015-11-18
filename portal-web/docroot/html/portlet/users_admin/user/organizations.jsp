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

List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");

String organizationIdsString = ParamUtil.getString(request, "organizationsSearchContainerPrimaryKeys");

currentURLObj.setParameter("historyKey", renderResponse.getNamespace() + "organizations");
%>

<liferay-ui:error-marker key="errorSection" value="organizations" />

<liferay-ui:membership-policy-error />

<liferay-util:buffer var="removeOrganizationIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="addOrganizationIds" type="hidden" value="<%= organizationIdsString %>" />
<aui:input name="deleteOrganizationIds" type="hidden" />

<h3><liferay-ui:message key="organizations" /></h3>

<liferay-ui:search-container
	curParam="organizationsCur"
	headerNames="name,type,roles,null"
	iteratorURL="<%= currentURLObj %>"
	total="<%= organizations.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= organizations.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Organization"
		escapedModel="<%= true %>"
		keyProperty="organizationId"
		modelVar="organization"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="roles"
		>

			<%
			if (selUser != null) {
				List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(selUser.getUserId(), organization.getGroup().getGroupId());

				for (UserGroupRole userGroupRole : userGroupRoles) {
					Role role = RoleLocalServiceUtil.getRole(userGroupRole.getRoleId());

					buffer.append(HtmlUtil.escape(role.getTitle(locale)));
					buffer.append(StringPool.COMMA_AND_SPACE);
				}

				if (!userGroupRoles.isEmpty()) {
					buffer.setIndex(buffer.index() - 1);
				}
			}
			%>

		</liferay-ui:search-container-column-text>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && ((selUser == null) || !OrganizationMembershipPolicyUtil.isMembershipProtected(permissionChecker, selUser.getUserId(), organization.getOrganizationId())) %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= organization.getOrganizationId() %>" href="javascript:;"><%= removeOrganizationIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<br />

	<liferay-ui:icon
		cssClass="modify-link"
		iconCssClass="icon-search"
		id="selectOrganizationLink"
		label="<%= true %>"
		linkCssClass="btn"
		message="select"
		method="get"
		url="javascript:;"
	/>

	<aui:script use="liferay-search-container">
		var AArray = A.Array;
		var Util = Liferay.Util;

		var addOrganizationIds = [];

		var organizationValues = document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value;

		if (organizationValues) {
			addOrganizationIds.push(organizationValues);
		}

		var deleteOrganizationIds = [];

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />organizationsSearchContainer');

		var searchContainerContentBox = searchContainer.get('contentBox');

		searchContainerContentBox.delegate(
			'click',
			function(event) {
				var link = event.currentTarget;

				var rowId = link.attr('data-rowId');

				var tr = link.ancestor('tr');

				var selectOrganization = Util.getWindow('<portlet:namespace />selectOrganization');

				if (selectOrganization) {
					var selectButton = selectOrganization.iframe.node.get('contentWindow.document').one('.selector-button[data-organizationid="' + rowId + '"]');

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, rowId);

				AArray.removeItem(addOrganizationIds, rowId);

				deleteOrganizationIds.push(rowId);

				document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value = addOrganizationIds.join(',');
				document.<portlet:namespace />fm.<portlet:namespace />deleteOrganizationIds.value = deleteOrganizationIds.join(',');
			},
			'.modify-link'
		);

		Liferay.on(
			'<portlet:namespace />enableRemovedOrganizations',
			function(event) {
				event.selectors.each(
					function(item, index, collection) {
						var organizationId = item.attr('data-organizationid');

						if (AArray.indexOf(deleteOrganizationIds, organizationId) != -1) {
							Util.toggleDisabled(item, false);
						}
					}
				);
			}
		);

		var selectOrganizationLink = A.one('#<portlet:namespace />selectOrganizationLink');

		if (selectOrganizationLink) {
			selectOrganizationLink.on(
				'click',
				function(event) {
					Util.selectEntity(
						{
							dialog: {
								constrain: true,
								modal: true
							},
							id: '<portlet:namespace />selectOrganization',
							title: '<liferay-ui:message arguments="organization" key="select-x" />',
							uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/users_admin/select_organization" /><portlet:param name="p_u_i_d" value='<%= selUser == null ? "0" : String.valueOf(selUser.getUserId()) %>' /></portlet:renderURL>'
						},
						function(event) {
							var rowColumns = [];

							rowColumns.push(event.name);
							rowColumns.push(event.type);
							rowColumns.push('');
							rowColumns.push('<a class="modify-link" data-rowId="' + event.organizationid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeOrganizationIcon) %></a>');

							searchContainer.addRow(rowColumns, event.organizationid);

							searchContainer.updateDataStore();

							AArray.removeItem(deleteOrganizationIds, event.organizationid);

							addOrganizationIds.push(event.organizationid);

							document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value = addOrganizationIds.join(',');
							document.<portlet:namespace />fm.<portlet:namespace />deleteOrganizationIds.value = deleteOrganizationIds.join(',');
						}
					);
				}
			);
		}
	</aui:script>
</c:if>
