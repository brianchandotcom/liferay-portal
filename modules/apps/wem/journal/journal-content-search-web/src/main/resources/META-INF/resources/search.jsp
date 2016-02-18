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

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

String backURL = ParamUtil.getString(request, "backURL");

if (Validator.isNotNull(redirect)) {
	portletDisplay.setURLBack(redirect);
}

PortletURL portletURL = PortletURLUtil.getCurrent(renderRequest, renderResponse);

request.setAttribute("search.jsp-portletURL", portletURL);
request.setAttribute("search.jsp-returnToFullPageURL", portletDisplay.getURLBack());

String defaultKeywords = LanguageUtil.get(request, "search") + StringPool.TRIPLE_PERIOD;

String keywords = StringUtil.unquote(ParamUtil.getString(request, "keywords", defaultKeywords));

PortletURL renderURL = renderResponse.createRenderURL();

renderURL.setParameter("mvcPath", "/search.jsp");
renderURL.setParameter("keywords", keywords);

SearchContainer journalContentSearch = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, renderURL, null, LanguageUtil.format(request, "no-pages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>", false));

Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

SearchContext searchContext = SearchContextFactory.getInstance(request);

searchContext.setGroupIds(null);
searchContext.setKeywords(keywords);

Hits hits = indexer.search(searchContext);

String[] queryTerms = hits.getQueryTerms();

ContentHits contentHits = new ContentHits();

contentHits.setShowListed(journalContentSearchPortletInstanceConfiguration.showListed());

contentHits.recordHits(hits, layout.getGroupId(), layout.isPrivateLayout(), journalContentSearch.getStart(), journalContentSearch.getEnd());

journalContentSearch.setTotal(hits.getLength());

List documents = ListUtil.toList(hits.getDocs());

journalContentSearch.setResults(documents);
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	title="search"
/>

<portlet:renderURL var="searchURL">
	<portlet:param name="mvcPath" value="/search.jsp" />
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="targetPortletId" value="<%= journalContentSearchPortletInstanceConfiguration.targetPortletId() %>" />
</portlet:renderURL>

<aui:form action="<%= searchURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "search(); event.preventDefault();" %>'>
	<div class="form-search">
		<liferay-ui:input-search name="keywords" placeholder='<%= LanguageUtil.get(request, "keywords") %>' />
	</div>

	<div class="search-results">
		<liferay-ui:search-speed hits="<%= hits %>" searchContainer="<%= journalContentSearch %>" />
	</div>

	<liferay-ui:search-container
		searchContainer="<%= journalContentSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.search.Document"
			keyProperty="entryClassPK"
			modelVar="document"
		>

			<%
			Summary summary = indexer.getSummary(document, StringPool.BLANK, renderRequest, renderResponse);

			summary.setHighlight(PropsValues.INDEX_SEARCH_HIGHLIGHT_ENABLED);
			summary.setQueryTerms(queryTerms);
			%>

			<liferay-ui:search-container-column-icon
				icon="web-content"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5>
					<%= summary.getHighlightedTitle() %>
				</h5>

				<%
				Locale snippetLocale = summary.getLocale();

				String languageId = LocaleUtil.toLanguageId(snippetLocale);
				%>

				<c:if test="<%= languageId.length() > 0 %>">
					<h6 class="text-default">
						<liferay-ui:icon image='<%= "../language/" + languageId %>' message='<%= LanguageUtil.format(request, "this-result-comes-from-the-x-version-of-this-content", snippetLocale.getDisplayLanguage(locale), false) %>' />
					</h6>
				</c:if>

				<h6 class="text-default">
					<%@ include file="/article_content.jspf" %>
				</h6>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />search() {
		var keywords = document.<portlet:namespace />fm.<portlet:namespace />keywords.value;

		keywords = keywords.replace(/^\s+|\s+$/, '');

		if (keywords != '') {
			submitForm(document.<portlet:namespace />fm);
		}
	}
</aui:script>