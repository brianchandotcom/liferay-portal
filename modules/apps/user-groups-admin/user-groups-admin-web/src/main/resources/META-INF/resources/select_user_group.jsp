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
User selUser = PortalUtil.getSelectedUser(request);

SelectUserGroupManagementToolbarDisplayContext selectUserGroupManagementToolbarDisplayContext = new SelectUserGroupManagementToolbarDisplayContext(request, renderRequest, renderResponse);

PortletURL portletURL = selectUserGroupManagementToolbarDisplayContext.getPortletURL();

SearchContainer<UserGroup> userGroupSearch = selectUserGroupManagementToolbarDisplayContext.getSearchContainer(filterManageableUserGroups);

renderResponse.setTitle(LanguageUtil.get(request, "user-groups"));
%>

<clay:management-toolbar-v2
	clearResultsURL="<%= selectUserGroupManagementToolbarDisplayContext.getClearResultsURL() %>"
	itemsTotal="<%= userGroupSearch.getTotal() %>"
	searchActionURL="<%= selectUserGroupManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	viewTypeItems="<%= selectUserGroupManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="selectUserGroupFm">
	<liferay-ui:search-container
		searchContainer="<%= userGroupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			escapedModel="<%= false %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="name"
				value="<%= HtmlUtil.escape(userGroup.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="description"
				value="<%= HtmlUtil.escape(userGroup.getDescription()) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= UserGroupMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, userGroup.getUserGroupId()) %>">
					<aui:button
						cssClass="selector-button"
						data='<%=
							HashMapBuilder.<String, Object>put(
								"entityid", userGroup.getUserGroupId()
							).put(
								"entityname", userGroup.getName()
							).build()
						%>'
						value="choose"
					/>
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>