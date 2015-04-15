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

if (Validator.isNotNull(redirect)) {
	portletDisplay.setURLBack(redirect);
}

PortletURL portletURL = PortletURLUtil.getCurrent(renderRequest, renderResponse);

request.setAttribute("search.jsp-portletURL", portletURL);
request.setAttribute("search.jsp-returnToFullPageURL", portletDisplay.getURLBack());

String defaultKeywords = LanguageUtil.get(request, "search") + StringPool.TRIPLE_PERIOD;

String keywords = ParamUtil.getString(request, "keywords", defaultKeywords);
%>

<portlet:renderURL var="searchURL">
	<portlet:param name="mvcPath" value="/search.jsp" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="showListed" value="<%= String.valueOf(showListed) %>" />
	<portlet:param name="targetPortletId" value="<%= targetPortletId %>" />
</portlet:renderURL>

<aui:form action="<%= searchURL %>" method="post" name="fm">

	<%
	PortletURL renderURL = renderResponse.createRenderURL();

	renderURL.setParameter("mvcPath", "/search.jsp");
	renderURL.setParameter("keywords", keywords);
	renderURL.setParameter("redirect", redirect);

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("#");
	headerNames.add("language");
	headerNames.add("name");
	headerNames.add("content");

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, renderURL, headerNames, LanguageUtil.format(request, "no-pages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>", false));

	try {
		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setGroupIds(null);
		searchContext.setKeywords(keywords);

		Hits hits = indexer.search(searchContext);

		String[] queryTerms = hits.getQueryTerms();

		ContentHits contentHits = new ContentHits();

		contentHits.setShowListed(showListed);

		contentHits.recordHits(hits, layout.getGroupId(), searchContainer.getStart(), searchContainer.getEnd());

		int total = hits.getLength();

		searchContainer.setTotal(total);

		List<Document> results = ListUtil.toList(hits.getDocs());

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Document doc = results.get(i);

			Summary summary = indexer.getSummary(doc, StringPool.BLANK, renderRequest, renderResponse);

			summary.setHighlight(PropsValues.INDEX_SEARCH_HIGHLIGHT_ENABLED);
			summary.setQueryTerms(queryTerms);

			ResultRow row = new ResultRow(new Object[] {doc, summary}, i, i);

			// Position

			row.addText(searchContainer.getStart() + i + 1 + StringPool.PERIOD);

			row.addJSP("/article_language.jsp", application, request, response);

			// Title

			String title = summary.getHighlightedTitle();

			row.addText(title);

			// Content

			row.addJSP("/article_content.jsp", application, request, response);

			// Add result row

			resultRows.add(row);
		}
	%>

		<div class="form-search">
			<liferay-ui:input-search name="keywords" placeholder='<%= LanguageUtil.get(locale, "keywords") %>' />
		</div>

		<div class="search-results">
			<liferay-ui:search-speed hits="<%= hits %>" searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</div>

	<%
	}
	catch (Exception e) {
		_log.error(e.getMessage());
	}
	%>

</aui:form>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.journal_content_search.search_jsp");
%>