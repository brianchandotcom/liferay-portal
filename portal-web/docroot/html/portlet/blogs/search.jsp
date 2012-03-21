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

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("#");
	headerNames.add("entry");

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(pageContext, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>"));

	try {
		Indexer indexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "regular");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setKeywords(keywords);
		searchContext.setStart(searchContainer.getStart());

		Hits results = indexer.search(searchContext);

		int total = results.getLength();

		searchContainer.setTotal(total);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.getDocs().length; i++) {
			Document doc = results.doc(i);

			ResultRow row = new ResultRow(doc, i, i);

			// Position

			row.addText(searchContainer.getStart() + i + 1 + StringPool.PERIOD);

			// Entry

			long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			BlogsEntry entry = null;

			try {
				entry = BlogsEntryLocalServiceUtil.getEntry(entryId);

				entry = entry.toEscapedModel();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Blogs search index is stale and contains entry " + entryId);
				}

				continue;
			}

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/blogs/view_entry");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("urlTitle", entry.getUrlTitle());

			row.addText(entry.getTitle(), rowURL);

			// Add result row

			resultRows.add(row);
		}
	%>

		<span class="aui-search-bar">
			<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-entries" type="text" value="<%= keywords %>" />

			<aui:button type="submit" value="search" />
		</span>

		<br /><br />

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<%
	}
	catch (Exception e) {
		_log.error(e.getMessage());
	}
	%>
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

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.blogs.search_jsp");
%>