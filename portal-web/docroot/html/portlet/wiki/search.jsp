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
		searchContext.setIncludeAttachments(true);
		searchContext.setIncludeDiscussions(true);
		searchContext.setKeywords(keywords);
		searchContext.setNodeIds(nodeIds);
		searchContext.setStart(searchContainer.getStart());

		Hits hits = indexer.search(searchContext);
		%>

		<liferay-ui:search-container-results
			results="<%= WikiUtil.getEntries(hits) %>"
			total="<%= hits.getLength() %>"
		/>

		<liferay-ui:search-container-row
			className="Object"
			modelVar="obj"
		>

			<c:choose>
				<c:when test="<%= obj instanceof DLFileEntry %>">

					<%
					DLFileEntry dlFileEntry = (DLFileEntry)obj;

					WikiPage wikiPage = WikiPageAttachmentsUtil.getPage(dlFileEntry.getFileEntryId());

					WikiNode curNode = wikiPage.getNode();
					%>

					<portlet:actionURL var="rowURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
						<portlet:param name="struts_action" value="/wiki/get_page_attachment" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
						<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
						<portlet:param name="fileName" value="<%= dlFileEntry.getTitle() %>" />
					</portlet:actionURL>

					<portlet:renderURL var="wikiPageURL">
						<portlet:param name="struts_action" value="/wiki/view" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="nodeName" value="<%= curNode.getName() %>" />
						<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						name="title"
					>
						<liferay-ui:icon
							image='<%= "../file_system/small/" + DLUtil.getFileIcon(dlFileEntry.getExtension()) %>'
							label="<%= true %>"
							message="<%= dlFileEntry.getTitle() %>"
							url="<%= rowURL %>"
						/>

						<liferay-util:buffer var="rootEntryIcon">
							<liferay-ui:icon
								image="page"
								label="<%= true %>"
								message="<%= wikiPage.getTitle() %>"
								url="<%= wikiPageURL %>"
							/>
						</liferay-util:buffer>

						<span class="search-root-entry">(<liferay-ui:message arguments="<%= rootEntryIcon %>" key="attachment-found-in-wiki-page-x" />)</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value='<%= LanguageUtil.get(locale, "attachment") %>'
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="wiki"
						value="<%= curNode.getName() %>"
					/>
				</c:when>
				<c:when test="<%= obj instanceof MBMessage %>">

					<%
					MBMessage message = (MBMessage)obj;

					WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(message.getClassPK());

					WikiNode curNode = wikiPage.getNode();
					%>

					<portlet:renderURL var="rowURL">
						<portlet:param name="struts_action" value="/wiki/view" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="nodeName" value="<%= curNode.getName() %>" />
						<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						name="title"
					>
						<liferay-ui:icon
							image="message"
							label="<%= true %>"
							message="<%= StringUtil.shorten(message.getBody()) %>"
							url="<%= rowURL %>"
						/>

						<liferay-util:buffer var="rootEntryIcon">
							<liferay-ui:icon
								image="page"
								label="<%= true %>"
								message="<%= wikiPage.getTitle() %>"
								url="<%= rowURL %>"
							/>
						</liferay-util:buffer>

						<span class="search-root-entry">(<liferay-ui:message arguments="<%= rootEntryIcon %>" key="comment-found-in-wiki-page-x" />)</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value='<%= LanguageUtil.get(locale, "comment") %>'
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="wiki"
						value="<%= curNode.getName() %>"
					/>
				</c:when>
				<c:when test="<%= obj instanceof WikiPage %>">

					<%
					WikiPage wikiPage = (WikiPage)obj;

					String title = wikiPage.getTitle();

					if (title.equalsIgnoreCase(keywords)) {
						createNewPage = false;
					}

					WikiNode curNode = wikiPage.getNode();
					%>

					<portlet:renderURL var="rowURL">
						<portlet:param name="struts_action" value="/wiki/view" />
						<portlet:param name="nodeName" value="<%= curNode.getName() %>" />
						<portlet:param name="title" value="<%= title %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						name="title"
					>
						<liferay-ui:icon
							image="page"
							label="<%= true %>"
							message="<%= title %>"
							url="<%= rowURL %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value='<%= LanguageUtil.get(locale, "page") %>'
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="wiki"
						value="<%= curNode.getName() %>"
					/>
				</c:when>
			</c:choose>
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