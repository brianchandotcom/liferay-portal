<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(pageContext, "no-pages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>") %>'
		iteratorURL="<%= portletURL %>"
	>

		<%
		Indexer indexer = IndexerRegistryUtil.getIndexer(WikiPage.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setKeywords(keywords);
		searchContext.setNodeIds(nodeIds);
		searchContext.setStart(searchContainer.getStart());

		Hits hits = indexer.search(searchContext);

		List<WikiPage> pages = WikiUtil.getEntries(hits);
		%>

		<liferay-ui:search-container-results
			results="<%= pages %>"
			total="<%= hits.getLength() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.wiki.model.WikiPage"
			modelVar="wikiPage"
		>

			<%
			WikiNode curNode = wikiPage.getNode();
			%>

			<liferay-ui:search-container-column-text
				name="#"
				value="<%= (index + 1) + StringPool.PERIOD %>"
			/>

			<portlet:actionURL var="rowURL">
				<portlet:param name="struts_action" value="/wiki/view" />
				<portlet:param name="nodeName" value="<%= node.getName() %>" />
				<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
			</portlet:actionURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="wiki"
				value="<%= curNode.getName() %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="page"
				value="<%= wikiPage.getTitle() %>"
			/>
		</liferay-ui:search-container-row>

		<span class="aui-search-bar">
			<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-pages" type="text" value="<%= keywords %>" />

			<aui:button type="submit" value="search" />
		</span>

		<br /><br />

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

		<liferay-ui:search-iterator type="more" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</aui:script>
</c:if>