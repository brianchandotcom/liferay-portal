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

portletURL.setParameter("mvcPath", "/view_feeds.jsp");
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="deleteFeedIds" type="hidden" />

	<liferay-ui:search-container
		rowChecker="<%= new RowChecker(renderResponse) %>"
		searchContainer="<%= new FeedSearch(renderRequest, portletURL) %>"
	>

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<c:if test="<%= JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_FEED) %>">
					<portlet:renderURL var="editFeedURL">
						<portlet:param name="mvcPath" value="/edit_feed.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:renderURL>

					<aui:nav-item
						href="<%= editFeedURL %>"
						label="add-feed"
					/>
				</c:if>

				<c:if test="<%= JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.journal"
						modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
						resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
						var="permissionsURL"
						windowState="<%= LiferayWindowState.POP_UP.toString() %>"
					/>

					<aui:nav-item
						href="<%= permissionsURL %>"
						label="permissions"
					/>
				</c:if>
			</aui:nav>

			<aui:nav-bar-search searchContainer="<%= searchContainer %>">
				<%@ include file="/feed_search.jspf" %>
			</aui:nav-bar-search>
		</aui:nav-bar>

		<liferay-ui:search-container-results>
			<%@ include file="/feed_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<c:if test="<%= !results.isEmpty() %>">
			<div class="separator"><!-- --></div>

			<aui:button disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteFeeds();" %>' value="delete" />
		</c:if>

		<liferay-ui:search-container-row
			className="com.liferay.journal.model.JournalFeed"
			keyProperty="feedId"
			modelVar="feed"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("mvcPath", "/edit_feed.jsp");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("groupId", String.valueOf(feed.getGroupId()));
			rowURL.setParameter("feedId", feed.getFeedId());
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="id"
				property="feedId"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="description"
				property="description"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/feed_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

	function <portlet:namespace />deleteFeeds() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-feeds") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteFeedIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteFeeds"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}
</aui:script>