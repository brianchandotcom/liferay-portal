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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

long nodeId = BeanParamUtil.getLong(node, request, "nodeId");

long[] nodeIds = null;

if (node != null) {
	nodeIds = new long[] {nodeId};
}

String keywords = ParamUtil.getString(request, "keywords");

boolean createNewPage = true;

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/wiki/search");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("nodeId", String.valueOf(nodeId));
portletURL.setParameter("keywords", keywords);
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/wiki/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= nodeId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="search"
	/>

	<div class="form-search">
		<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" placeholder='<%= LanguageUtil.get(locale, "keywords") %>' title='<%= LanguageUtil.get(locale, "search-pages") %>' />
	</div>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(pageContext, "no-pages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>") %>'
		iteratorURL="<%= portletURL %>"
	>

		<%
		Indexer indexer = IndexerRegistryUtil.getIndexer(WikiPage.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setIncludeAttachments(true);
		searchContext.setIncludeDiscussions(true);
		searchContext.setKeywords(keywords);
		searchContext.setNodeIds(nodeIds);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(true);

		searchContext.setQueryConfig(queryConfig);

		searchContext.setStart(searchContainer.getStart());

		Hits hits = indexer.search(searchContext);

		searchContainer.setTotal(hits.getLength());

		PortletURL hitURL = renderResponse.createRenderURL();

		hitURL.setParameter("struts_action", "/wiki/view");
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
			WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(searchResult.getClassPK());

			String title = wikiPage.getTitle();

			if (StringUtil.equalsIgnoreCase(title, keywords)) {
				createNewPage = false;
			}

			WikiNode curNode = wikiPage.getNode();

			Summary summary = searchResult.getSummary();
			%>

			<portlet:renderURL var="rowURL">
				<portlet:param name="struts_action" value="/wiki/view" />
				<portlet:param name="nodeName" value="<%= curNode.getName() %>" />
				<portlet:param name="title" value="<%= title %>" />
			</portlet:renderURL>

			<liferay-ui:app-view-search-entry
				containerIcon="all_pages"
				containerName="<%= curNode.getName() %>"
				containerType='<%= LanguageUtil.get(locale, "wiki-node") %>'
				cssClass='<%= MathUtil.isEven(index) ? "search" : "search alt" %>'
				description="<%= (summary != null) ? summary.getContent() : wikiPage.getSummary() %>"
				fileEntryTuples="<%= searchResult.getFileEntryTuples() %>"
				mbMessages="<%= searchResult.getMBMessages() %>"
				queryTerms="<%= hits.getQueryTerms() %>"
				title="<%= (summary != null) ? summary.getTitle() : wikiPage.getTitle() %>"
				url="<%= rowURL %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="more" />
	</liferay-ui:search-container>

	<c:if test="<%= createNewPage %>">
		<portlet:actionURL var="addPageURL">
			<portlet:param name="struts_action" value="/wiki/edit_page" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
			<portlet:param name="title" value="<%= keywords %>" />
			<portlet:param name="editTitle" value="1" />
		</portlet:actionURL>

		<strong><aui:a cssClass="new-page" href="<%= addPageURL.toString() %>" label="create-a-new-page-on-this-topic" /></strong>
	</c:if>
</aui:form>