<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
		String defaultKeywords = LanguageUtil.get(pageContext, "search") + "...";
		String unicodeDefaultKeywords = UnicodeFormatter.toString(defaultKeywords);

		String keywords = ParamUtil.getString(request, "keywords", defaultKeywords);
		%>

		<portlet:renderURL var="searchURL">
			<portlet:param name="struts_action" value="/journal_content_search/search" />
		</portlet:renderURL>

		<aui:form action="<%= searchURL %>" method="post" name="fm">

			<%
			PortletURL portletURL = renderResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/journal_content_search/search");
			portletURL.setParameter("keywords", keywords);

			List<String> headerNames = new ArrayList<String>();

			headerNames.add("#");
			headerNames.add("name");
			headerNames.add("content");
			headerNames.add("score");

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(pageContext, "no-pages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>"));

			try {
				Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

				SearchContext searchContext = SearchContextFactory.getInstance(request);

				searchContext.setAttribute("articleType", type);
				searchContext.setGroupIds(null);
				searchContext.setKeywords(keywords);

				Hits results = indexer.search(searchContext);

				String[] queryTerms = results.getQueryTerms();

				ContentHits contentHits = new ContentHits();

				contentHits.setShowListed(showListed);

				contentHits.recordHits(results, layout.getGroupId(), layout.isPrivateLayout(), searchContainer.getStart(), searchContainer.getEnd());

				int total = results.getLength();

				searchContainer.setTotal(total);

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.getDocs().length; i++) {
					Document doc = results.doc(i);

					String snippet = doc.get(locale, Field.CONTENT);

					ResultRow row = new ResultRow(new Object[] {queryTerms, doc, snippet}, i, i);

					// Position

					row.addText(searchContainer.getStart() + i + 1 + StringPool.PERIOD);

					// Title

					String title = HtmlUtil.escape(doc.get(locale, Field.TITLE));

					title = StringUtil.highlight(title, queryTerms);

					row.addText(title);

					// Content

					row.addJSP("/html/portlet/journal_content_search/article_content.jsp");

					// Score

					row.addScore(results.score(i));

					// Add result row

					resultRows.add(row);
				}
			%>

			<%
			String taglibOnBlur = "if (this.value == '') { this.value = '" + unicodeDefaultKeywords + "'; }";
			String taglibOnFocus = "if (this.value == '" + unicodeDefaultKeywords + "') { this.value = ''; }";
			%>

			<aui:input cssClass="lfr-search-keywords" inlineField="<%= true %>" label="" name="keywords" size="30" onBlur="<%= taglibOnBlur %>" onFocus="<%= taglibOnFocus %>" title="search-web-content" type="text" value="<%= HtmlUtil.escape(keywords) %>" />

			<aui:input align="absmiddle" alt='<%= LanguageUtil.get(pageContext, "search") %>' border="0" cssClass="lfr-search-button" inlineField="<%= true %>" label="" name="search" src='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' title="search" type="image" />

			<div class="search-results">
				<liferay-ui:search-speed searchContainer="<%= searchContainer %>" hits="<%= results %>" />

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
			</div>

			<%
			}
			catch (Exception e) {
				_log.error(e.getMessage());
			}
			%>

		</aui:form>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				if (document.<portlet:namespace />fm.<portlet:namespace />keywords) {
					Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
				}
			</aui:script>
		</c:if>
	</c:when>
	<c:otherwise>
		<liferay-ui:journal-content-search />
	</c:otherwise>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.journal_content_search.search_jsp");
%>