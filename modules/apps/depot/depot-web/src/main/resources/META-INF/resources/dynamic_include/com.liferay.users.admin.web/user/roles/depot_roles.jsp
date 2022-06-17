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
					url="javascript:void(0);"
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
				<a class="modify-link" data-entityId="<%= userGroupRole.getGroupId() %>-<%= userGroupRole.getRoleId() %>" href="javascript:void(0);"><%= removeDepotRoleIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<c:if test="<%= depotAdminRolesDisplayContext.isSelectable() %>">
	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"portletNamespace", liferayPortletResponse.getNamespace()
			).put(
				"removeDepotRoleIcon", removeDepotRoleIcon
			).put(
				"searchContainerId", "depotRolesSearchContainer"
			).put(
				"selectDepotRolesURL", depotAdminRolesDisplayContext.getSelectDepotRolesURL()
			).put(
				"selectEventName", depotAdminRolesDisplayContext.getSelectDepotRolesEventName()
			).build()
		%>'
		module="js/DepotRoles"
	/>
</c:if>