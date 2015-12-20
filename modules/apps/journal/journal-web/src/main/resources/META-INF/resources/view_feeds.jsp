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
String redirect = ParamUtil.getString(request, "redirect");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_feeds.jsp");
portletURL.setParameter("redirect", redirect);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "feeds"));
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="feeds" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" method="post" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="deleteFeedIds" type="hidden" />

	<liferay-ui:search-container
		rowChecker="<%= new RowChecker(renderResponse) %>"
		searchContainer="<%= new FeedSearch(renderRequest, portletURL) %>"
	>

		<liferay-ui:search-container-results>

			<%
			FeedSearchTerms searchTerms = (FeedSearchTerms)searchContainer.getSearchTerms();

			total = JournalFeedLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFeedId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			results = JournalFeedLocalServiceUtil.search(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFeedId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), null);

			searchContainer.setResults(results);
			%>

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

<c:if test="<%= JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_FEED) %>">
	<portlet:renderURL var="editFeedURL">
		<portlet:param name="mvcPath" value="/edit_feed.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-feed") %>' url="<%= editFeedURL %>" />
	</liferay-frontend:add-menu>
</c:if>