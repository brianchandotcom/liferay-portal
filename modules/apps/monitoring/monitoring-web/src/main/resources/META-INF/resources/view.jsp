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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/monitoring/view");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="live-sessions" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test="<%= PropsValues.LIVE_USERS_ENABLED && PropsValues.SESSION_TRACKER_MEMORY_ENABLED %>">

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

					<%
					User user2 = null;

					try {
						user2 = UserLocalServiceUtil.getUserById(userTracker.getUserId());
					}
					catch (NoSuchUserException nsue) {
					}
					%>

					<portlet:renderURL var="rowURL">
						<portlet:param name="mvcRenderCommandName" value="/monitoring/edit_session" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="sessionId" value="<%= userTracker.getSessionId() %>" />
					</portlet:renderURL>

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
						value='<%= ((user2 != null) ? HtmlUtil.escape(user2.getFullName()) : LanguageUtil.get(request, "not-available")) %>'
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="screen-name"
						value='<%= ((user2 != null) ? user2.getScreenName() : LanguageUtil.get(request, "not-available")) %>'
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

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
			</liferay-ui:search-container>
		</c:when>
		<c:when test="<%= !PropsValues.LIVE_USERS_ENABLED %>">
			<liferay-ui:message arguments="<%= PropsKeys.LIVE_USERS_ENABLED %>" key="display-of-live-session-data-is-disabled" translateArguments="<%= false %>" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments="<%= PropsKeys.SESSION_TRACKER_MEMORY_ENABLED %>" key="display-of-live-session-data-is-disabled" translateArguments="<%= false %>" />
		</c:otherwise>
	</c:choose>
</div>