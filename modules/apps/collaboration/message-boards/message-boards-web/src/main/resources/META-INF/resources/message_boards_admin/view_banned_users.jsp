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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/view_banned_users");
%>

<liferay-util:include page="/message_boards_admin/nav.jsp" servletContext="<%= application %>">
	<liferay-util:param name="navItemSelected" value="banned-users" />
</liferay-util:include>

<%
String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(MBPortletKeys.MESSAGE_BOARDS, "banned-users-display-style", "descriptive");
}
else {
	portalPreferences.setValue(MBPortletKeys.MESSAGE_BOARDS, "banned-users-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

int totalBannedUsers = MBBanLocalServiceUtil.getBansCount(scopeGroupId);
%>

<liferay-frontend:management-bar
	disabled="<%= totalBannedUsers == 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="mbBanUsers"
>

	<%
	PortletURL displayStyleURL = renderResponse.createRenderURL();

	displayStyleURL.setParameter("mvcRenderCommandName", "/message_boards/view_banned_users");
	%>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "unbanUser();" %>' icon="unlock" label="unban-user" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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
				className="com.liferay.message.boards.kernel.model.MBBan"
				keyProperty="banUserId"
				modelVar="ban"
			>
				<liferay-ui:search-container-column-user
					cssClass="user-icon-lg"
					showDetails="<%= false %>"
					userId="<%= ban.getBanUserId() %>"
				/>

				<liferay-ui:search-container-column-text colspan="<%= 2 %>">

					<%
					Date createDate = ban.getCreateDate();

					String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
					%>

					<h5 class="text-default">
						<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(PortalUtil.getUserName(ban.getUserId(), StringPool.BLANK)), modifiedDateDescription} %>" key="banned-by-x-x-ago" />
					</h5>

					<h4>
						<%= HtmlUtil.escape(PortalUtil.getUserName(ban.getBanUserId(), StringPool.BLANK)) %>
					</h4>

					<h5 class="text-default">
						<liferay-ui:message key="unban-date" />

						<%= dateFormatDateTime.format(MBUtil.getUnbanDate(ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL)) %>
					</h5>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					path="/message_boards/ban_user_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<%
PortalUtil.setPageSubtitle(LanguageUtil.get(request, "banned-users"), request);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format("banned-users", TextFormatter.O)), portletURL.toString());
%>

<aui:script>
	function <portlet:namespace />unbanUser() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');
		form.fm('<%= Constants.CMD %>').val('unban');

		submitForm(form, '<portlet:actionURL name="/message_boards/ban_user" />');
	}
</aui:script>