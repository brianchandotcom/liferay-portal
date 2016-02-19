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

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

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

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="feeds"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<%
		PortletURL iteratorURL = PortletURLUtil.clone(portletURL, renderResponse);

		iteratorURL.setParameter("displayStyle", displayStyle);
		%>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name", "id"} %>'
			portletURL="<%= iteratorURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteFeeds" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="deleteFeeds" var="deleteFeedsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteFeedsURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<liferay-ui:search-container
		id="feeds"
		rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
		searchContainer="<%= new FeedSearch(renderRequest, portletURL) %>"
	>

		<liferay-ui:search-container-results>

			<%
			FeedSearchTerms searchTerms = (FeedSearchTerms)searchContainer.getSearchTerms();

			total = JournalFeedLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFeedId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			results = JournalFeedLocalServiceUtil.search(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFeedId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.journal.model.JournalFeed"
			keyProperty="feedId"
			modelVar="feed"
		>
			<liferay-ui:search-container-column-text
				name="id"
				property="feedId"
			/>

			<liferay-ui:search-container-column-text
				name="name"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				property="description"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				path="/feed_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />deleteFeeds() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-feeds") %>')) {
			submitForm(form);
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