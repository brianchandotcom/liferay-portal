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

<%@ include file="/bookmarks/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsFolderId = ParamUtil.getLong(request, "breadcrumbsFolderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");

String keywords = ParamUtil.getString(request, "keywords");

if (searchFolderId > 0) {
	BookmarksUtil.addPortletBreadcrumbEntries(searchFolderId, request, renderResponse);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "search") + ": " + keywords, currentURL);

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

String headerTitle = LanguageUtil.get(request, "search");

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="mvcPath" value="/bookmarks/search.jsp" />
	</liferay-portlet:renderURL>

	<aui:form action="<%= searchURL %>" method="get" name="fm">
		<liferay-portlet:renderURLParams varImpl="searchURL" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= breadcrumbsFolderId %>" />
		<aui:input name="searchFolderId" type="hidden" value="<%= searchFolderId %>" />

		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				backURL="<%= redirect %>"
				title="<%= headerTitle %>"
			/>
		</c:if>

		<%
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/bookmarks/search.jsp");
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
		portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
		portletURL.setParameter("keywords", keywords);
		%>

		<liferay-ui:search-container
			emptyResultsMessage='<%= LanguageUtil.format(request, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>", false) %>'
			iteratorURL="<%= portletURL %>"
		>

			<%
			Indexer<?> indexer = BookmarksSearcher.getInstance();

			SearchContext searchContext = SearchContextFactory.getInstance(request);

			searchContext.setAttribute("paginationType", "more");
			searchContext.setEnd(searchContainer.getEnd());
			searchContext.setFolderIds(new long[] {searchFolderId});
			searchContext.setKeywords(keywords);
			searchContext.setStart(searchContainer.getStart());

			Hits hits = indexer.search(searchContext);

			searchContainer.setTotal(hits.getLength());
			%>

			<liferay-ui:search-container-results
				results="<%= BookmarksUtil.getEntries(hits) %>"
			/>

			<liferay-ui:search-container-row
				className="Object"
				modelVar="obj"
			>

				<c:choose>
					<c:when test="<%= obj instanceof BookmarksEntry %>">

						<%
						BookmarksEntry entry = (BookmarksEntry)obj;

						entry = entry.toEscapedModel();

						BookmarksFolder folder = entry.getFolder();

						String rowHREF = themeDisplay.getPathMain().concat("/bookmarks/open_entry?entryId=").concat(String.valueOf(entry.getEntryId()));
						%>

						<liferay-ui:search-container-column-text
							name="entry"
							title="<%= entry.getDescription() %>"
						>

							<%
							AssetRendererFactory<BookmarksEntry> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(BookmarksEntry.class);

							AssetRenderer<BookmarksEntry> assetRenderer = assetRendererFactory.getAssetRenderer(entry.getEntryId());
							%>

							<liferay-ui:icon
								iconCssClass="<%= assetRenderer.getIconCssClass() %>"
								label="<%= true %>"
								localizeMessage="<%= false %>"
								message="<%= entry.getName() %>"
								target="_blank"
								url="<%= rowHREF %>"
							/>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							href="<%= rowHREF %>"
							name="type"
							target="_blank"
							title="<%= entry.getDescription() %>"
							value='<%= LanguageUtil.get(request, "entry") %>'
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowHREF %>"
							name="folder"
							target="_blank"
							title="<%= entry.getDescription() %>"
							value="<%= folder.getName() %>"
						/>

						<c:if test='<%= ArrayUtil.contains(entryColumns, "action") %>'>
							<liferay-ui:search-container-column-jsp
								cssClass="entry-action"
								path="/bookmarks/entry_action.jsp"
							/>
						</c:if>
					</c:when>
					<c:when test="<%= obj instanceof BookmarksFolder %>">

						<%
						BookmarksFolder folder = (BookmarksFolder)obj;

						BookmarksFolder parentFolder = folder.getParentFolder();
						%>

						<liferay-portlet:renderURL var="rowURL">
							<portlet:param name="mvcRenderCommandName" value="/bookmarks/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:search-container-column-text
							name="entry"
							title="<%= folder.getDescription() %>"
						>

							<%
							AssetRendererFactory<BookmarksFolder> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(BookmarksFolder.class);

							AssetRenderer<BookmarksFolder> assetRenderer = assetRendererFactory.getAssetRenderer(folder.getFolderId());
							%>

							<liferay-ui:icon
								iconCssClass="<%= assetRenderer.getIconCssClass() %>"
								label="<%= true %>"
								localizeMessage="<%= false %>"
								message="<%= HtmlUtil.escape(folder.getName()) %>"
								url="<%= rowURL %>"
							/>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="type"
							title="<%= folder.getDescription() %>"
							value='<%= LanguageUtil.get(request, "folder") %>'
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="folder"
							title="<%= folder.getDescription() %>"
							value='<%= (parentFolder != null) ? parentFolder.getName() : LanguageUtil.get(request, "home") %>'
						/>

						<c:if test='<%= ArrayUtil.contains(folderColumns, "action") %>'>
							<liferay-ui:search-container-column-jsp
								cssClass="entry-action"
								path="/bookmarks/folder_action.jsp"
							/>
						</c:if>
					</c:when>
				</c:choose>
			</liferay-ui:search-container-row>

			<div class="form-search">
				<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" placeholder='<%= LanguageUtil.get(request, "keywords") %>' title='<%= LanguageUtil.get(request, "search-categories") %>' />
			</div>

			<liferay-ui:search-iterator type="more" />
		</liferay-ui:search-container>
	</aui:form>
</div>