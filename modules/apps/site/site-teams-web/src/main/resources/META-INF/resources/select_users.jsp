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
SelectUsersDisplayContext selectUsersDisplayContext = new SelectUsersDisplayContext(request, renderRequest, renderResponse);
%>

<clay:management-toolbar-v2
	displayContext="<%= new SelectUsersManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, selectUsersDisplayContext) %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl portlet-site-teams-select-users" name="selectUserFm">
	<liferay-ui:search-container
		id="users"
		searchContainer="<%= selectUsersDisplayContext.getUserSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<c:choose>
				<c:when test='<%= Objects.equals(selectUsersDisplayContext.getDisplayStyle(), "icon") %>'>
					<liferay-ui:search-container-column-text>
						<clay:user-card
							userCard="<%= new SelectUserUserCard(user2, renderRequest, searchContainer.getRowChecker()) %>"
							userColorClass='<%= "sticker-user-icon " + LexiconUtil.getUserColorCssClass(user2) %>'
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(selectUsersDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-text>
						<liferay-ui:user-portrait
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
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= selectUsersDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />users');

	searchContainer.on('rowToggled', function (event) {
		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(selectUsersDisplayContext.getEventName()) %>',
			{
				data: event.elements.allSelectedElements.getDOMNodes(),
			}
		);
	});
</aui:script>