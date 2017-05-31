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

<%@ include file="/item/selector/init.jsp" %>

<%
WikiPageItemSelectorViewDisplayContext wikiPageItemSelectorViewDisplayContext = (WikiPageItemSelectorViewDisplayContext)request.getAttribute(WikiPageItemSelectorView.WIKI_PAGE_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

WikiNode node = wikiPageItemSelectorViewDisplayContext.getNode();

String keywords = ParamUtil.getString(request, "keywords");

SearchContainer wikiPagesSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, wikiPageItemSelectorViewDisplayContext.getPortletURL(request, liferayPortletResponse), null, wikiPageItemSelectorViewDisplayContext.isSearch() ? LanguageUtil.format(locale, "no-pages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>", false) : "there-are-no-pages");

if (wikiPageItemSelectorViewDisplayContext.isSearch()) {
	Indexer<WikiPage> indexer = IndexerRegistryUtil.getIndexer(WikiPage.class);

	SearchContext searchContext = SearchContextFactory.getInstance(request);

	searchContext.setEnd(wikiPagesSearchContainer.getEnd());
	searchContext.setIncludeAttachments(false);
	searchContext.setIncludeDiscussions(false);
	searchContext.setNodeIds(new long[] {node.getNodeId()});
	searchContext.setStart(wikiPagesSearchContainer.getStart());

	Hits hits = indexer.search(searchContext);

	wikiPagesSearchContainer.setTotal(hits.getLength());

	List<SearchResult> searchResults = SearchResultUtil.getSearchResults(hits, themeDisplay.getLocale());

	List<WikiPage> results = new ArrayList<>();

	for (SearchResult searchResult : searchResults) {
		WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(searchResult.getClassPK());

		results.add(wikiPage);
	}

	wikiPagesSearchContainer.setResults(results);
}
else {
	wikiPagesSearchContainer.setTotal(WikiPageLocalServiceUtil.getPagesCount(node.getNodeId(), true, wikiPageItemSelectorViewDisplayContext.getStatus()));
	wikiPagesSearchContainer.setResults(WikiPageLocalServiceUtil.getPages(node.getNodeId(), true, wikiPageItemSelectorViewDisplayContext.getStatus(), wikiPagesSearchContainer.getStart(), wikiPagesSearchContainer.getEnd()));
}
%>

<div class="container-fluid-1280 lfr-item-viewer" id="<portlet:namespace />wikiPagesSelectorContainer">
	<liferay-ui:search-container
		id="wikiPagesSearchContainer"
		searchContainer="<%= wikiPagesSearchContainer %>"
		total="<%= wikiPagesSearchContainer.getTotal() %>"
	>
		<liferay-ui:search-container-results
			results="<%= wikiPagesSearchContainer.getResults() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.wiki.model.WikiPage"
			keyProperty="pageId"
			modelVar="curPage"
		>
			<liferay-ui:search-container-column-icon icon="wiki-page" />

			<liferay-ui:search-container-column-text colspan="<%= 2 %>">

				<%
				Date modifiedDate = curPage.getModifiedDate();

				String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
				%>

				<h5 class="text-default">
					<c:choose>
						<c:when test="<%= Validator.isNotNull(curPage.getUserName()) %>">
							<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(curPage.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments="<%= new String[] {modifiedDateDescription} %>" key="modified-x-ago" />
						</c:otherwise>
					</c:choose>
				</h5>

				<%
				WikiPageItemSelectorReturnTypeResolver wikiPageItemSelectorReturnTypeResolver = wikiPageItemSelectorViewDisplayContext.getWikiPageItemSelectorReturnTypeResolver();
				%>

				<h4>
					<a class="wiki-page" data-title="<%= wikiPageItemSelectorReturnTypeResolver.getTitle(curPage, themeDisplay) %>" data-value="<%= wikiPageItemSelectorReturnTypeResolver.getValue(curPage, themeDisplay) %>" href="javascript:;">
						<%= curPage.getTitle() %>
					</a>
				</h4>

				<h5 class="text-default">
					<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= curPage.getStatus() %>" />
				</h5>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" searchContainer="<%= wikiPagesSearchContainer %>" />
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />wikiPagesSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			Util.getOpener().Liferay.fire(
				'<%= wikiPageItemSelectorViewDisplayContext.getItemSelectedEventName() %>',
				{
					data: {
						title: event.currentTarget.attr('data-title'),
						value: event.currentTarget.attr('data-value')
					}
				}
			);
		},
		'.wiki-page'
	);
</aui:script>