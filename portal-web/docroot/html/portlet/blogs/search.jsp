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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/blogs/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="search"
	/>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/blogs/search");
	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("keywords", keywords);
	%>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(pageContext, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>") %>'
		iteratorURL="<%= portletURL %>"
	>

		<%
		Indexer indexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "regular");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setKeywords(keywords);
		searchContext.setStart(searchContainer.getStart());
		searchContext.setIncludeDiscussions(true);

		Hits hits = indexer.search(searchContext);
		%>

		<liferay-ui:search-container-results
			results="<%= BlogsUtil.getEntries(hits) %>"
			total="<%= hits.getLength() %>"
		/>

		<liferay-ui:search-container-row
			className="java.lang.Object"
			modelVar="obj"
		>

			<c:choose>
				<c:when test="<%= obj instanceof BlogsEntry %>">

					<%
					BlogsEntry entry = (BlogsEntry)obj;

					entry = entry.toEscapedModel();
					%>

					<portlet:renderURL var="rowURL">
						<portlet:param name="struts_action" value="/blogs/view_entry" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						name="title"
					>
						<liferay-ui:icon
							image="../blogs/blogs"
							label="<%= true %>"
							message="<%= entry.getTitle() %>"
							url="<%= rowURL %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value='<%= LanguageUtil.get(locale, "blog") %>'
					/>
				</c:when>
				<c:when test="<%= obj instanceof MBMessage %>">

					<%
					MBMessage message = (MBMessage)obj;

					BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(message.getClassPK());

					entry = entry.toEscapedModel();
					%>

					<portlet:renderURL var="rowURL">
						<portlet:param name="struts_action" value="/blogs/view_entry" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						name="title"
					>
						<liferay-ui:icon
							image="message"
							label="<%= true %>"
							message="<%= StringUtil.shorten(message.getBody(), 50) %>"
							url="<%= rowURL %>"
						/>

						<liferay-util:buffer var="rootEntryIcon">
							<liferay-ui:icon
								image="../blogs/blogs"
								label="<%= true %>"
								message="<%= entry.getTitle() %>"
								url="<%= rowURL %>"
							/>
						</liferay-util:buffer>

						<span class="search-root-entry">(<liferay-ui:message arguments="<%= rootEntryIcon %>" key="found-comment-in-blog-entry-x" />)</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value='<%= LanguageUtil.get(locale, "comment") %>'
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<span class="aui-search-bar">
			<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-entries" type="text" value="<%= keywords %>" />

			<aui:button type="submit" value="search" />
		</span>

		<br /><br />

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</aui:script>
</c:if>

<%
if (Validator.isNotNull(keywords)) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
}
%>