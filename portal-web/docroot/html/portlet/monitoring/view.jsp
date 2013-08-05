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

<%@ include file="/html/portlet/monitoring/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/monitoring/view");
%>

<c:choose>
	<c:when test="<%= PropsValues.LIVE_USERS_ENABLED && PropsValues.SESSION_TRACKER_MEMORY_ENABLED %>">
		<liferay-ui:header
			title="live-sessions"
		/>

		<%
		Map<String, UserTracker> sessionUsers = LiveUsers.getSessionUsers(company.getCompanyId());

		List<UserTracker> userTrackers = new ArrayList<UserTracker>(sessionUsers.values());

		userTrackers = ListUtil.sort(userTrackers, new UserTrackerModifiedDateComparator());
		%>

		<liferay-ui:search-container
			emptyResultsMessage="there-are-no-live-sessions"
			headerNames="session-id,user-id,name,screen-name,last-request,num-of-hits"
			total="<%= userTrackers.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= ListUtil.subList(userTrackers, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.UserTracker"
				keyProperty="userTrackerId"
				modelVar="userTracker"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="struts_action" value="/monitoring/edit_session" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="sessionId" value="<%= userTracker.getSessionId() %>" />
				</portlet:renderURL>

				<%
				User user2 = null;

				try {
					user2 = UserLocalServiceUtil.getUserById(userTracker.getUserId());
				}
				catch (NoSuchUserException nsue) {
				}
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="session-id"
					property="sessionId"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="user-id"
					property="userId"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="user-id"
					value='<%= ((user2 != null) ? HtmlUtil.escape(user2.getFullName()) : LanguageUtil.get(pageContext, "not-available")) %>'
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="screen-name"
					value='<%= ((user2 != null) ? user2.getScreenName() : LanguageUtil.get(pageContext, "not-available")) %>'
				/>

				<liferay-ui:search-container-column-date
					href="<%= rowURL %>"
					name="last-request"
					value="<%= userTracker.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="num-of-hits"
					property="hits"
				/>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:when test="<%= !PropsValues.LIVE_USERS_ENABLED %>">
		<%= LanguageUtil.format(pageContext, "display-of-live-session-data-is-disabled", PropsKeys.LIVE_USERS_ENABLED) %>
	</c:when>
	<c:otherwise>
		<%= LanguageUtil.format(pageContext, "display-of-live-session-data-is-disabled", PropsKeys.SESSION_TRACKER_MEMORY_ENABLED) %>
	</c:otherwise>
</c:choose>