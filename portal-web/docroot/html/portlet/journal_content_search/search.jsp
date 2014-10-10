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

<%@ include file="/html/portlet/journal_content_search/init.jsp" %>

<c:choose>
	<c:when test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<style type="text/css">
			.portlet-journal-content-search .search-results {
				margin-top: 1em;
			}
		</style>

		<%
		String defaultKeywords = LanguageUtil.get(request, "search") + StringPool.TRIPLE_PERIOD;

		String keywords = ParamUtil.getString(request, "keywords", defaultKeywords);
		%>

		<portlet:renderURL var="searchURL">
			<portlet:param name="struts_action" value="/journal_content_search/search" />
			<portlet:param name="showListed" value="<%= String.valueOf(showListed) %>" />
			<portlet:param name="targetPortletId" value="<%= targetPortletId %>" />
		</portlet:renderURL>

		<aui:form action="<%= searchURL %>" method="post" name="fm">

			<%
			PortletURL portletURL = renderResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/journal_content_search/search");
			portletURL.setParameter("keywords", keywords);

			List<String> headerNames = new ArrayList<String>();

			headerNames.add("#");
			headerNames.add("language");
			headerNames.add("name");
			headerNames.add("content");

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(request, "no-pages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>", false));

			try {
				Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

				SearchContext searchContext = SearchContextFactory.getInstance(request);

				searchContext.setGroupIds(null);
				searchContext.setKeywords(keywords);

				Hits hits = indexer.search(searchContext);

				String[] queryTerms = hits.getQueryTerms();

				ContentHits contentHits = new ContentHits();

				contentHits.setShowListed(showListed);

				contentHits.recordHits(hits, layout.getGroupId(), layout.isPrivateLayout(), searchContainer.getStart(), searchContainer.getEnd());

				int total = hits.getLength();

				searchContainer.setTotal(total);

				List<Document> results = ListUtil.toList(hits.getDocs());

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					Document doc = results.get(i);

					PortletURL summaryURL = PortletURLUtil.clone(portletURL, renderResponse);

					Summary summary = indexer.getSummary(doc, StringPool.BLANK, summaryURL, renderRequest, renderResponse);

					summary.setHighlight(PropsValues.INDEX_SEARCH_HIGHLIGHT_ENABLED);
					summary.setQueryTerms(queryTerms);

					ResultRow row = new ResultRow(new Object[] {doc, summary}, i, i);

					// Position

					row.addText(searchContainer.getStart() + i + 1 + StringPool.PERIOD);

					row.addJSP("/html/portlet/journal_content_search/article_language.jsp");

					// Title

					String title = summary.getHighlightedTitle();

					row.addText(title);

					// Content

					row.addJSP("/html/portlet/journal_content_search/article_content.jsp");

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
	</c:when>
	<c:otherwise>
		<liferay-ui:journal-content-search
			showListed="<%= showListed %>"
			targetPortletId="<%= targetPortletId %>"
		/>
	</c:otherwise>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.journal_content_search.search_jsp");
%>