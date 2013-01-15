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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsCategoryId = ParamUtil.getLong(request, "breadcrumbsCategoryId");
long breadcrumbsMessageId = ParamUtil.getLong(request, "breadcrumbsMessageId");

long searchCategoryId = ParamUtil.getLong(request, "searchCategoryId");

long[] categoryIdsArray = null;

List categoryIds = new ArrayList();

categoryIds.add(new Long(searchCategoryId));

MBCategoryServiceUtil.getSubcategoryIds(categoryIds, scopeGroupId, searchCategoryId);

categoryIdsArray = StringUtil.split(StringUtil.merge(categoryIds), 0L);

long threadId = ParamUtil.getLong(request, "threadId");
String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/message_boards/search" />
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

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/message_boards/search");
	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("breadcrumbsCategoryId", String.valueOf(breadcrumbsCategoryId));
	portletURL.setParameter("breadcrumbsMessageId", String.valueOf(breadcrumbsMessageId));
	portletURL.setParameter("searchCategoryId", String.valueOf(searchCategoryId));
	portletURL.setParameter("threadId", String.valueOf(threadId));
	portletURL.setParameter("keywords", keywords);
	%>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(pageContext, "no-messages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>") %>'
		iteratorURL="<%= portletURL %>"
	>

		<%
		Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setCategoryIds(categoryIdsArray);
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setKeywords(keywords);
		searchContext.setStart(searchContainer.getStart());

		Hits hits = indexer.search(searchContext);

		List<MBMessage> messages = MBUtil.getEntries(hits);
		%>

		<liferay-ui:search-container-results
			results="<%= messages %>"
			total="<%= hits.getLength() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.messageboards.model.MBMessage"
			modelVar="message"
		>

			<%
			MBCategory category = message.getCategory();

			PortletURL categoryUrl = renderResponse.createRenderURL();

			categoryUrl.setParameter("struts_action", "/message_boards/view");
			categoryUrl.setParameter("redirect", currentURL);
			categoryUrl.setParameter("mbCategoryId", String.valueOf(category.getCategoryId()));

			// Thread and message

			MBThread thread = message.getThread();

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/message_boards/view_message");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("messageId", String.valueOf(message.getMessageId()));
			%>

			<liferay-ui:search-container-column-text
				name="#"
				value="<%= (index + 1) + StringPool.PERIOD %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= categoryUrl %>"
				name="category"
				value="<%= HtmlUtil.escape(category.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="message"
				value="<%= HtmlUtil.escape(message.getSubject()) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="thread-posts"
				value="<%= String.valueOf(thread.getMessageCount()) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="thread-views"
				value="<%= String.valueOf(thread.getViewCount()) %>"
			/>

		</liferay-ui:search-container-row>

		<span class="aui-search-bar">
			<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-messages" type="text" value="<%= keywords %>" />

			<aui:button type="submit" value="search" />
		</span>

		<br /><br />

		<liferay-ui:search-iterator type="more" />
	</liferay-ui:search-container>

</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook() %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</aui:script>
</c:if>

<%
if (breadcrumbsCategoryId > 0) {
	MBUtil.addPortletBreadcrumbEntries(breadcrumbsCategoryId, request, renderResponse);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
%>