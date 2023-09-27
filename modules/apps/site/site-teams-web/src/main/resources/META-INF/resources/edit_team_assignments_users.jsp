<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditSiteTeamAssignmentsUsersDisplayContext editSiteTeamAssignmentsUsersDisplayContext = new EditSiteTeamAssignmentsUsersDisplayContext(request, renderRequest, renderResponse);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= editSiteTeamAssignmentsUsersDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new EditSiteTeamAssignmentsUsersManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, editSiteTeamAssignmentsUsersDisplayContext) %>"
	propsTransformer="js/EditTeamAssignmentsUsersManagementToolbarPropsTransformer"
/>

<portlet:actionURL name="deleteTeamUsers" var="deleteTeamUsersURL" />

<aui:form action="<%= deleteTeamUsersURL %>" cssClass="container-fluid container-fluid-max-xl portlet-site-teams-users" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= editSiteTeamAssignmentsUsersDisplayContext.getTabs1() %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="teamId" type="hidden" value="<%= String.valueOf(editSiteTeamAssignmentsUsersDisplayContext.getTeamId()) %>" />

	<liferay-ui:search-container
		id="users"
		searchContainer="<%= editSiteTeamAssignmentsUsersDisplayContext.getUserSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<c:choose>
				<c:when test='<%= Objects.equals(editSiteTeamAssignmentsUsersDisplayContext.getDisplayStyle(), "icon") %>'>
					<liferay-ui:search-container-column-text>
						<clay:user-card
							propsTransformer="js/UserDropdownDefaultPropsTransformer"
							userCard="<%= new UserUserCard(user2, editSiteTeamAssignmentsUsersDisplayContext.getTeamId(), renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(editSiteTeamAssignmentsUsersDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-text>
						<liferay-user:user-portrait
							userId="<%= user2.getUserId() %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5><%= user2.getFullName() %></h5>

						<h6 class="text-default">
							<span><%= user2.getScreenName() %></span>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text>

						<%
						UserActionDropdownItemsProvider userActionDropdownItemsProvider = new UserActionDropdownItemsProvider(user2, editSiteTeamAssignmentsUsersDisplayContext.getTeamId(), renderRequest, renderResponse);
						%>

						<clay:dropdown-actions
							aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
							dropdownItems="<%= userActionDropdownItemsProvider.getActionDropdownItems() %>"
							propsTransformer="js/UserDropdownDefaultPropsTransformer"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="name"
						property="fullName"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="screen-name"
						property="screenName"
					/>

					<liferay-ui:search-container-column-text>

						<%
						UserActionDropdownItemsProvider userActionDropdownItemsProvider = new UserActionDropdownItemsProvider(user2, editSiteTeamAssignmentsUsersDisplayContext.getTeamId(), renderRequest, renderResponse);
						%>

						<clay:dropdown-actions
							aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
							dropdownItems="<%= userActionDropdownItemsProvider.getActionDropdownItems() %>"
							propsTransformer="js/UserDropdownDefaultPropsTransformer"
						/>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= editSiteTeamAssignmentsUsersDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="addTeamUsers" var="addTeamUsersURL" />

<aui:form action="<%= addTeamUsersURL %>" cssClass="hide" name="addTeamUsersFm">
	<aui:input name="tabs1" type="hidden" value="<%= editSiteTeamAssignmentsUsersDisplayContext.getTabs1() %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="teamId" type="hidden" value="<%= String.valueOf(editSiteTeamAssignmentsUsersDisplayContext.getTeamId()) %>" />
</aui:form>