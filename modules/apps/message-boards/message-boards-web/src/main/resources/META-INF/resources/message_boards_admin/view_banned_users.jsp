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

<%@ include file="/message_boards/init.jsp" %>

<%
String navigation = "banned-users";

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/view_banned_users");
%>

<%@ include file="/message_boards_admin/nav.jspf" %>

<%
MBBannedUsersManagementToolbarDisplayContext mbBannedUsersManagementToolbarDisplayContext = new MBBannedUsersManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request);

int totalBannedUsers = MBBanLocalServiceUtil.getBansCount(scopeGroupId);
%>

<clay:management-toolbar
	actionDropdownItems="<%= mbBannedUsersManagementToolbarDisplayContext.getActionDropdownItems() %>"
	componentId="mbBannedUsersManagementToolbar"
	disabled="<%= totalBannedUsers == 0 %>"
	itemsTotal="<%= totalBannedUsers %>"
	searchContainerId="mbBanUsers"
	showCreationMenu="<%= false %>"
	showInfoButton="<%= false %>"
	showSearch="<%= false %>"
/>

<div class="container-fluid-1280">
	<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			emptyResultsMessage="there-are-no-banned-users"
			headerNames="banned-user,banned-by,ban-date"
			id="mbBanUsers"
			iteratorURL="<%= portletURL %>"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			total="<%= totalBannedUsers %>"
		>
			<liferay-ui:search-container-results
				results="<%= MBBanLocalServiceUtil.getBans(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.message.boards.model.MBBan"
				keyProperty="banUserId"
				modelVar="ban"
			>

				<%
				Map<String, Object> rowData = new HashMap<String, Object>();

				rowData.put("actions", StringUtil.merge(mbBannedUsersManagementToolbarDisplayContext.getAvailableActions(ban)));

				row.setData(rowData);
				%>

				<liferay-ui:search-container-column-user
					showDetails="<%= false %>"
					userId="<%= ban.getBanUserId() %>"
				/>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>

					<%
					Date createDate = ban.getCreateDate();

					String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
					%>

					<span class="text-default">
						<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(PortalUtil.getUserName(ban.getUserId(), StringPool.BLANK)), modifiedDateDescription} %>" key="banned-by-x-x-ago" />
					</span>

					<h2 class="h5">

						<%
						User bannedUser = UserLocalServiceUtil.fetchUser(ban.getBanUserId());
						%>

						<c:choose>
							<c:when test="<%= (bannedUser != null) && bannedUser.isActive() %>">
								<aui:a href="<%= bannedUser.getDisplayURL(themeDisplay) %>">
									<%= HtmlUtil.escape(PortalUtil.getUserName(ban.getBanUserId(), StringPool.BLANK)) %>
								</aui:a>
							</c:when>
							<c:otherwise>
								<%= HtmlUtil.escape(PortalUtil.getUserName(ban.getBanUserId(), StringPool.BLANK)) %>
							</c:otherwise>
						</c:choose>
					</h2>

					<span class="text-default">
						<liferay-ui:message key="unban-date" />

						<%= dateFormatDateTime.format(com.liferay.message.boards.util.MBUtil.getUnbanDate(ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL)) %>
					</span>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					path="/message_boards/ban_user_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= mbBannedUsersManagementToolbarDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format("banned-users", TextFormatter.O)), portletURL.toString());

PortalUtil.setPageSubtitle(LanguageUtil.get(request, "banned-users"), request);
%>

<aui:script>
	var unbanUser = function() {
		Liferay.Util.postForm(document.<portlet:namespace />fm, {
			data: {
				<%= Constants.CMD %>: 'unban'
			},
			url: '<portlet:actionURL name="/message_boards/ban_user" />'
		});
	};

	var ACTIONS = {
		unbanUser: unbanUser
	};

	Liferay.componentReady('mbBannedUsersManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>