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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view_feeds");

String searchContainerId = StringPool.BLANK;
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />

	<aui:input name="groupId" type="hidden" />
	<aui:input name="deleteFeedIds" type="hidden" />

	<%
	FeedSearch searchContainer = new FeedSearch(renderRequest, portletURL);

	List headerNames = searchContainer.getHeaderNames();

	headerNames.add(StringPool.BLANK);

	searchContainer.setRowChecker(new RowChecker(renderResponse));
	%>

	<liferay-ui:search-form
		page="/html/portlet/journal/feed_search.jsp"
		searchContainer="<%= searchContainer %>"
	/>

	<%
	searchContainerId = searchContainer.getId(request, renderResponse.getNamespace());

	FeedSearchTerms searchTerms = (FeedSearchTerms)searchContainer.getSearchTerms();
	%>

	<%@ include file="/html/portlet/journal/feed_search_results.jspf" %>

	<div class="separator"><!-- --></div>

	<aui:button disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteFeeds();" %>' value="delete" />

	<br /><br />

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		JournalFeed feed = (JournalFeed)results.get(i);

		feed = feed.toEscapedModel();

		ResultRow row = new ResultRow(feed, feed.getFeedId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/journal/edit_feed");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("groupId", String.valueOf(feed.getGroupId()));
		rowURL.setParameter("feedId", feed.getFeedId());

		row.setParameter("rowHREF", rowURL.toString());

		// Feed id

		row.addText(feed.getFeedId(), rowURL);

		// Name and description

		if (Validator.isNotNull(feed.getDescription())) {
			row.addText(feed.getName().concat("<br />").concat(feed.getDescription()), rowURL);
		}
		else {
			row.addText(feed.getName(), rowURL);
		}

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/journal/feed_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerId %>', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

	Liferay.provide(
		window,
		'<portlet:namespace />deleteFeeds',
		function() {
			var deleteFeedIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			if (deleteFeedIds && confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-feeds") %>')) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
				document.<portlet:namespace />fm.<portlet:namespace />groupId.value = "<%= scopeGroupId %>";
				document.<portlet:namespace />fm.<portlet:namespace />deleteFeedIds.value = deleteFeedIds;

				submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_feed" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>