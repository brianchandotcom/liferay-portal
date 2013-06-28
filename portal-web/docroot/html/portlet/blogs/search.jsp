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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/blogs/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="search"
	/>

	<span class="form-search">
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" inlineField="<%= true %>" label="" name="keywords" size="30" title="search-entries" type="text" value="<%= keywords %>" />

		<aui:button type="submit" value="search" />
	</span>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/blogs/search");
	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("keywords", keywords);
	%>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(pageContext, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>") %>'
		iteratorURL="<%= portletURL %>"
	>

		<%
		Indexer indexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "regular");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setIncludeDiscussions(true);
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(true);

		searchContext.setQueryConfig(queryConfig);

		searchContext.setStart(searchContainer.getStart());

		Hits hits = indexer.search(searchContext);

		searchContainer.setTotal(hits.getLength());

		if (searchContainer.isRecalculateCur()) {
			searchContext.setEnd(searchContainer.getEnd());
			searchContext.setStart(searchContainer.getStart());

			hits = indexer.search(searchContext);
		}

		PortletURL hitURL = renderResponse.createRenderURL();

		hitURL.setParameter("struts_action", "/blogs/view_entry");
		hitURL.setParameter("redirect", currentURL);
		%>

		<liferay-ui:search-container-results
			results="<%= SearchResultUtil.getSearchResults(hits, locale, hitURL) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.search.SearchResult"
			modelVar="searchResult"
		>

			<%
			BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(searchResult.getClassPK());

			entry = entry.toEscapedModel();

			Summary summary = searchResult.getSummary();
			%>

			<portlet:renderURL var="rowURL">
				<portlet:param name="struts_action" value="/blogs/view_entry" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
			</portlet:renderURL>

			<liferay-ui:app-view-search-entry
				cssClass='<%= MathUtil.isEven(index) ? "search" : "search alt" %>'
				description="<%= (summary != null) ? HtmlUtil.escape(summary.getContent()) : entry.getDescription() %>"
				mbMessages="<%= searchResult.getMBMessages() %>"
				queryTerms="<%= hits.getQueryTerms() %>"
				thumbnailSrc="<%= Validator.isNotNull(entry.getEntryImageURL(themeDisplay)) ? entry.getEntryImageURL(themeDisplay) : StringPool.BLANK %>"
				title="<%= (summary != null) ? HtmlUtil.escape(summary.getTitle()) : entry.getTitle() %>"
				url="<%= rowURL %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="more" />
	</liferay-ui:search-container>
</aui:form>

<%
if (Validator.isNotNull(keywords)) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
}
%>