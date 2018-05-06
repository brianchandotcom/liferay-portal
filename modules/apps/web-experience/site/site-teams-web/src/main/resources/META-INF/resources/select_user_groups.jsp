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
SelectUserGroupsDisplayContext selectUserGroupsDisplayContext = new SelectUserGroupsDisplayContext(renderRequest, renderResponse, request);
%>

<clay:navigation-bar
	items="<%= selectUserGroupsDisplayContext.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	disabled="<%= selectUserGroupsDisplayContext.isDisabledManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="userGroups"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= selectUserGroupsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= selectUserGroupsDisplayContext.getOrderByCol() %>"
			orderByType="<%= selectUserGroupsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name", "description"} %>'
			portletURL="<%= selectUserGroupsDisplayContext.getPortletURL() %>"
		/>

		<c:if test="<%= selectUserGroupsDisplayContext.isShowSearch() %>">
			<li>
				<aui:form action="<%= selectUserGroupsDisplayContext.getPortletURL() %>" name="searchFm">
					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= selectUserGroupsDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280" name="selectUserGroupFm">
	<liferay-ui:search-container
		id="userGroups"
		searchContainer="<%= selectUserGroupsDisplayContext.getUserGroupSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

			userParams.put("usersUserGroups", Long.valueOf(userGroup.getUserGroupId()));

			int usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), StringPool.BLANK, WorkflowConstants.STATUS_ANY, userParams);
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(selectUserGroupsDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item selectable");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							cssClass="entry-display-style"
							icon="users"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							subtitle="<%= userGroup.getDescription() %>"
							title="<%= userGroup.getName() %>"
						>
							<liferay-frontend:vertical-card-footer>
								<liferay-ui:message arguments="<%= usersCount %>" key="x-users" />
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(selectUserGroupsDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="users"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5><%= userGroup.getName() %></h5>

						<h6 class="text-default">
							<span><%= userGroup.getDescription() %></span>
						</h6>

						<h6 class="text-default">
							<span><liferay-ui:message arguments="<%= usersCount %>" key="x-users" /></span>
						</h6>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						orderable="<%= true %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="description"
						orderable="<%= true %>"
						property="description"
					/>

					<liferay-ui:search-container-column-text
						name="users"
						value="<%= String.valueOf(usersCount) %>"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= selectUserGroupsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />userGroups');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(selectUserGroupsDisplayContext.getEventName()) %>',
				{
					data: event.elements.allSelectedElements.getDOMNodes()
				}
			);
		}
	);
</aui:script>