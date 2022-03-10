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

<%@ include file="/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

long nodeId = BeanParamUtil.getLong(node, request, "nodeId");

long[] nodeIds = null;

if (node != null) {
	nodeIds = new long[] {nodeId};
}

String keywords = ParamUtil.getString(request, "keywords");

boolean createNewPage = WikiNodePermission.contains(permissionChecker, node, ActionKeys.ADD_PAGE);

WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);
%>

<aui:form action="<%= wikiURLHelper.getSearchURL() %>" method="get" name="fm">
	<liferay-portlet:renderURLParams portletURL="<%= wikiURLHelper.getSearchURL() %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= nodeId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="search"
	/>

	<div class="form-search">
		<liferay-ui:input-search
			autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
			placeholder='<%= LanguageUtil.get(request, "keywords") %>'
			title='<%= LanguageUtil.get(request, "search-pages") %>'
		/>
	</div>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(request, "no-pages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>", false) %>'
		iteratorURL='<%=
			PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCRenderCommandName(
				"/wiki/search"
			).setRedirect(
				redirect
			).setKeywords(
				keywords
			).setParameter(
				"nodeId", nodeId
			).buildPortletURL()
		%>'
	>

		<%
		Indexer<WikiPage> indexer = IndexerRegistryUtil.getIndexer(WikiPage.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(wikiPortletInstanceSettingsHelper.isEnableHighlighting());

		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setIncludeAttachments(true);
		searchContext.setIncludeDiscussions(true);
		searchContext.setKeywords(keywords);
		searchContext.setNodeIds(nodeIds);
		searchContext.setStart(searchContainer.getStart());

		Hits hits = indexer.search(searchContext);

		java.util.Locale searchLocale = locale;

		searchContainer.setResultsAndTotal(() -> SearchResultUtil.getSearchResults(hits, searchLocale), hits.getLength());
		%>

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

			PortletURL viewPageURL = renderResponse.createRenderURL();

			if (portletName.equals(WikiPortletKeys.WIKI_DISPLAY)) {
				viewPageURL.setParameter("mvcRenderCommandName", "/wiki/view_page");
			}
			else {
				viewPageURL.setParameter("mvcRenderCommandName", "/wiki/view");
			}

			viewPageURL.setParameter("nodeName", node.getName());
			viewPageURL.setParameter("title", wikiPage.getTitle());

			PortletURL editPageURL = PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCRenderCommandName(
				"/wiki/edit_page"
			).setRedirect(
				currentURL
			).setParameter(
				"nodeId", node.getNodeId()
			).setParameter(
				"title", title
			).buildPortletURL();

			WikiEngineRenderer wikiEngineRenderer = (WikiEngineRenderer)request.getAttribute(WikiWebKeys.WIKI_ENGINE_RENDERER);

			String formattedContent = null;

			try {
				formattedContent = WikiUtil.getFormattedContent(wikiEngineRenderer, renderRequest, renderResponse, wikiPage, viewPageURL, editPageURL, wikiPage.getTitle(), false);
			}
			catch (Exception e) {
				formattedContent = wikiPage.getContent();
			}
			%>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/wiki/view" />
				<portlet:param name="nodeName" value="<%= curNode.getName() %>" />
				<portlet:param name="title" value="<%= title %>" />
			</portlet:renderURL>

			<%@ include file="/wiki/app_view_search_entry.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-paginator
			searchContainer="<%= searchContainer %>"
			type="more"
		/>
	</liferay-ui:search-container>

	<c:if test="<%= createNewPage %>">
		<portlet:renderURL var="addPageURL">
			<portlet:param name="mvcRenderCommandName" value="/wiki/edit_page" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
			<portlet:param name="title" value="<%= keywords %>" />
			<portlet:param name="editTitle" value="1" />
		</portlet:renderURL>

		<strong><aui:a cssClass="new-page" href="<%= addPageURL.toString() %>" label="create-a-new-page-on-this-topic" /></strong>
	</c:if>
</aui:form>