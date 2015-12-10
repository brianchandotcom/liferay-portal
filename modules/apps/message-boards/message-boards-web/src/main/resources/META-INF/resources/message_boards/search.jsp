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

<%@ include file="/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsCategoryId = ParamUtil.getLong(request, "breadcrumbsCategoryId");
long breadcrumbsMessageId = ParamUtil.getLong(request, "breadcrumbsMessageId");

long searchCategoryId = ParamUtil.getLong(request, "searchCategoryId");

long[] categoryIdsArray = null;

List categoryIds = new ArrayList();

categoryIds.add(Long.valueOf(searchCategoryId));

MBCategoryServiceUtil.getSubcategoryIds(categoryIds, scopeGroupId, searchCategoryId);

categoryIdsArray = StringUtil.split(StringUtil.merge(categoryIds), 0L);

long threadId = ParamUtil.getLong(request, "threadId");
String keywords = ParamUtil.getString(request, "keywords");

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="mvcRenderCommandName" value="/message_boards/search" />
	</liferay-portlet:renderURL>

	<aui:form action="<%= searchURL %>" method="get" name="fm">
		<liferay-portlet:renderURLParams varImpl="searchURL" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= breadcrumbsCategoryId %>" />
		<aui:input name="breadcrumbsMessageId" type="hidden" value="<%= breadcrumbsMessageId %>" />
		<aui:input name="searchCategoryId" type="hidden" value="<%= searchCategoryId %>" />
		<aui:input name="threadId" type="hidden" value="<%= threadId %>" />

		<liferay-ui:header
			backURL="<%= redirect %>"
			title="search"
		/>

		<div class="form-search">
			<liferay-ui:input-search autoFocus="<%= (windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook()) %>" placeholder='<%= LanguageUtil.get(request, "keywords") %>' title='<%= LanguageUtil.get(request, "search-messages") %>' />
		</div>

		<%
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/message_boards/search");
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("breadcrumbsCategoryId", String.valueOf(breadcrumbsCategoryId));
		portletURL.setParameter("breadcrumbsMessageId", String.valueOf(breadcrumbsMessageId));
		portletURL.setParameter("searchCategoryId", String.valueOf(searchCategoryId));
		portletURL.setParameter("threadId", String.valueOf(threadId));
		portletURL.setParameter("keywords", keywords);
		%>

		<liferay-ui:search-container
			emptyResultsMessage='<%= LanguageUtil.format(request, "no-messages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>", false) %>'
			iteratorURL="<%= portletURL %>"
		>

			<%
			Hits hits = null;
			%>

			<liferay-ui:search-container-results>

				<%
				Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

				SearchContext searchContext = SearchContextFactory.getInstance(request);

				searchContext.setAttribute("paginationType", "more");
				searchContext.setCategoryIds(categoryIdsArray);
				searchContext.setEnd(searchContainer.getEnd());
				searchContext.setIncludeAttachments(true);
				searchContext.setKeywords(keywords);
				searchContext.setStart(searchContainer.getStart());

				hits = indexer.search(searchContext);

				searchContainer.setTotal(hits.getLength());

				results = SearchResultUtil.getSearchResults(hits, locale);

				searchContainer.setResults(results);
				%>

			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.search.SearchResult"
				modelVar="searchResult"
			>

				<%
				MBMessage message = MBMessageLocalServiceUtil.getMessage(searchResult.getClassPK());

				Summary summary = searchResult.getSummary();
				%>

				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
				</portlet:renderURL>

				<liferay-ui:app-view-search-entry
					containerName="<%= MBUtil.getAbsolutePath(renderRequest, message.getCategoryId()) %>"
					cssClass='<%= MathUtil.isEven(index) ? "search" : "search alt" %>'
					description="<%= (summary != null) ? summary.getContent() : StringPool.BLANK %>"
					fileEntryRelatedSearchResults="<%= searchResult.getFileEntryRelatedSearchResults() %>"
					queryTerms="<%= hits.getQueryTerms() %>"
					title="<%= (summary != null) ? summary.getTitle() : message.getSubject() %>"
					url="<%= rowURL %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="more" />
		</liferay-ui:search-container>

	</aui:form>
</div>

<%
if (breadcrumbsCategoryId > 0) {
	MBUtil.addPortletBreadcrumbEntries(breadcrumbsCategoryId, request, renderResponse);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "search") + ": " + keywords, currentURL);
%>