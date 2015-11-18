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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsFolderId = ParamUtil.getLong(request, "breadcrumbsFolderId");

long folderId = ParamUtil.getLong(request, "folderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");
long searchFolderIds = ParamUtil.getLong(request, "searchFolderIds");

long[] folderIdsArray = null;

JournalFolder folder = null;

if (searchFolderId > 0) {
	folderIdsArray = new long[] {searchFolderId};

	folder = JournalFolderServiceUtil.getFolder(searchFolderId);
}
else {
	long defaultFolderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

	List<Long> folderIds = JournalFolderServiceUtil.getSubfolderIds(scopeGroupId, searchFolderIds, true);

	folderIds.add(0, defaultFolderId);

	folderIdsArray = StringUtil.split(StringUtil.merge(folderIds), 0L);
}

String keywords = ParamUtil.getString(request, "keywords");

int searchType = ParamUtil.getInteger(request, "searchType");

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", SearchContainer.DEFAULT_DELTA);

boolean ajax = ParamUtil.getBoolean(request, "ajax");

boolean showSearchInfo = ParamUtil.getBoolean(request, "showSearchInfo");

if (searchType == JournalSearchConstants.FRAGMENT) {
	if (ajax) {
		showSearchInfo = false;
	}
	else {
		searchType = JournalSearchConstants.SINGLE;

		showSearchInfo = true;
	}
}
else if ((searchType == JournalSearchConstants.SINGLE) && !ajax) {
	showSearchInfo = true;
}

int total = 0;

boolean advancedSearch = ParamUtil.getBoolean(liferayPortletRequest, ArticleDisplayTerms.ADVANCED_SEARCH);

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/search");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
portletURL.setParameter("searchFolderIds", String.valueOf(searchFolderIds));
portletURL.setParameter("keywords", keywords);

ArticleSearch searchContainer = new ArticleSearch(liferayPortletRequest, entryEnd / (entryEnd - entryStart), entryEnd - entryStart, portletURL);
%>

<c:if test="<%= showSearchInfo %>">
	<liferay-util:buffer var="searchInfo">
		<div class="search-info">
			<span class="keywords">

				<%
				String message = StringPool.BLANK;

				if (advancedSearch) {
					if (folder != null) {
						message = LanguageUtil.format(pageContext, "advanced-search-in-x", new Object[] {HtmlUtil.escape(folder.getName())});
					}
					else {
						message = LanguageUtil.get(pageContext, "advanced-search-everywhere");
					}
				}
				else {
					if (folder != null) {
						message = LanguageUtil.format(pageContext, "searched-for-x-in-x", new Object[] {HtmlUtil.escape(keywords), HtmlUtil.escape(folder.getName())});
					}
					else {
						message = LanguageUtil.format(pageContext, "searched-for-x-everywhere", HtmlUtil.escape(keywords));
					}
				}
				%>

				<%= message %>
			</span>

			<c:if test="<%= folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
				<span class="change-search-folder">

					<%
					String taglibOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "changeSearchFolder', {searchEverywhere: " + (folder != null) + "});";
					%>

					<aui:button onClick="<%= taglibOnClick %>" value='<%= (folder != null) ? "search-everywhere" : "search-in-the-current-folder" %>' />
				</span>
			</c:if>

			<liferay-ui:icon cssClass="close-search" id="closeSearch" image="../aui/remove" url="javascript:;" />
		</div>

		<aui:script use="aui-base">
			A.one('#<portlet:namespace />closeSearch').on(
				'click',
				function(event) {
					Liferay.fire(
						'<portlet:namespace />dataRequest',
						{
							requestParams: {
								'<portlet:namespace />struts_action': '/journal/view',
								'<portlet:namespace />folderId': '<%= String.valueOf(folderId) %>',
								'<portlet:namespace />viewEntries': <%= Boolean.TRUE.toString() %>
							},
							resetPagination: true,
							src: Liferay.JOURNAL_SEARCH_END
						}
					);
				}
			);
		</aui:script>
	</liferay-util:buffer>

	<div id="<portlet:namespace />searchInfo">
		<%= searchInfo %>
	</div>
</c:if>

<liferay-util:buffer var="searchResults">
	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="struts_action" value="/journal/search" />
	</liferay-portlet:renderURL>

	<div class="journal-container" id="<portlet:namespace />entriesContainer">
		<aui:form action="<%= searchURL %>" method="get" name="fm2">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= breadcrumbsFolderId %>" />
			<aui:input name="searchFolderId" type="hidden" value="<%= searchFolderId %>" />
			<aui:input name="searchFolderIds" type="hidden" value="<%= searchFolderIds %>" />

			<%
			searchContainer.setRowChecker(new EntriesChecker(liferayPortletRequest, liferayPortletResponse));

			ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

			List<Long> folderIds = new ArrayList<Long>(1);

			folderIds.add(searchFolderId);

			searchTerms.setFolderIds(folderIds);

			if (searchFolderId <= 0) {
				searchTerms.setFolderIds(new ArrayList<Long>());
			}

			try {
				boolean emptySearchResults = false;
			%>

				<c:choose>
					<c:when test="<%= PropsValues.JOURNAL_ARTICLES_SEARCH_WITH_INDEX %>">

						<%
						SearchContext searchContext = SearchContextFactory.getInstance(request);

						Indexer indexer = null;

						if (searchTerms.isAdvancedSearch()) {
							indexer = IndexerRegistryUtil.nullSafeGetIndexer(JournalArticle.class);

							searchContext.setAndSearch(searchTerms.isAndOperator());
							searchContext.setAttribute(Field.CONTENT, searchTerms.getContent());
							searchContext.setAttribute(Field.DESCRIPTION, searchTerms.getDescription());
							searchContext.setAttribute(Field.STATUS, searchTerms.getStatusCode());
							searchContext.setAttribute(Field.TITLE, searchTerms.getTitle());
							searchContext.setAttribute(Field.TYPE, searchTerms.getType());
							searchContext.setAttribute("articleId", searchTerms.getArticleId());
						}
						else {
							indexer = JournalSearcher.getInstance();

							searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);

							if (Validator.isNotNull(keywords)) {
								searchContext.setAttribute(Field.CONTENT, keywords);
								searchContext.setAttribute(Field.DESCRIPTION, keywords);
								searchContext.setAttribute(Field.TITLE, keywords);
								searchContext.setAttribute("articleId", keywords);
								searchContext.setKeywords(keywords);
							}
							else {
								searchContext.setAndSearch(true);
							}

							searchContext.setIncludeDiscussions(true);
						}

						LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

						params.put("expandoAttributes", searchTerms.getKeywords());

						searchContext.setAttribute("head", Boolean.FALSE.toString());
						searchContext.setAttribute("params", params);
						searchContext.setEnd(searchContainer.getEnd());
						searchContext.setFolderIds(searchTerms.getFolderIds());

						QueryConfig queryConfig = new QueryConfig();

						queryConfig.setHighlightEnabled(true);

						searchContext.setQueryConfig(queryConfig);

						searchContext.setStart(searchContainer.getStart());

						Hits hits = indexer.search(searchContext);

						total = hits.getLength();

						searchContainer.setTotal(total);

						PortletURL hitURL = liferayPortletResponse.createRenderURL();

						List<SearchResult> searchResultsList = SearchResultUtil.getSearchResults(hits, locale, hitURL);

						emptySearchResults = searchResultsList.isEmpty();

						for (int i = 0; i < searchResultsList.size(); i++) {
							SearchResult searchResult = searchResultsList.get(i);

							Summary summary = searchResult.getSummary();

							JournalArticle article = null;
							JournalFolder curFolder = null;

							String className = searchResult.getClassName();

							if (className.equals(JournalArticle.class.getName())) {
								article = JournalArticleLocalServiceUtil.fetchLatestArticle(searchResult.getClassPK(), WorkflowConstants.STATUS_ANY, false);
							}
							else if (className.equals(JournalFolder.class.getName())) {
								curFolder = JournalFolderLocalServiceUtil.getFolder(searchResult.getClassPK());
							}
						%>

							<c:choose>
								<c:when test="<%= (article != null) && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.VIEW) %>">

									<%
									PortletURL rowURL = liferayPortletResponse.createRenderURL();

									rowURL.setParameter("struts_action", "/journal/edit_article");
									rowURL.setParameter("redirect", currentURL);
									rowURL.setParameter("groupId", String.valueOf(article.getGroupId()));
									rowURL.setParameter("folderId", String.valueOf(article.getFolderId()));
									rowURL.setParameter("articleId", article.getArticleId());

									List<String> versions = searchResult.getVersions();

									Collections.sort(versions);

									request.setAttribute("view_entries.jsp-article", article);
									%>

									<liferay-ui:app-view-search-entry
										actionJsp="/html/portlet/journal/article_action.jsp"
										containerName="<%= JournalUtil.getAbsolutePath(liferayPortletRequest, article.getFolderId()) %>"
										cssClass='<%= MathUtil.isEven(i) ? "alt" : StringPool.BLANK %>'
										description="<%= LocalizationUtil.getLocalization(article.getContent(), themeDisplay.getLanguageId()) %>"
										mbMessages="<%= searchResult.getMBMessages() %>"
										queryTerms="<%= hits.getQueryTerms() %>"
										rowCheckerId="<%= HtmlUtil.escape(article.getArticleId()) %>"
										rowCheckerName="<%= JournalArticle.class.getSimpleName() %>"
										showCheckbox="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>"
										status="<%= article.getStatus() %>"
										thumbnailSrc='<%= Validator.isNotNull(article.getArticleImageURL(themeDisplay)) ? article.getArticleImageURL(themeDisplay) : themeDisplay.getPathThemeImages() + "/file_system/large/article.png" %>'
										title="<%= article.getTitle(locale) %>"
										url="<%= rowURL.toString() %>"
										versions="<%= versions %>"
									/>
								</c:when>

								<c:when test="<%= curFolder != null %>">

									<%
									String folderImage = "folder_empty_article";

									if (JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, curFolder.getFolderId()) > 0) {
										folderImage = "folder_full_article";
									}

									PortletURL rowURL = liferayPortletResponse.createRenderURL();

									rowURL.setParameter("struts_action", "/journal/view");
									rowURL.setParameter("redirect", currentURL);
									rowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
									rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

									request.setAttribute("view_entries.jsp-folder", curFolder);
									%>

									<liferay-ui:app-view-search-entry
										actionJsp="/html/portlet/journal/folder_action.jsp"
										containerName="<%= JournalUtil.getAbsolutePath(liferayPortletRequest, curFolder.getParentFolderId()) %>"
										cssClass='<%= MathUtil.isEven(i) ? "alt" : StringPool.BLANK %>'
										description="<%= (summary != null) ? summary.getContent() : curFolder.getDescription() %>"
										queryTerms="<%= hits.getQueryTerms() %>"
										rowCheckerId="<%= String.valueOf(curFolder.getFolderId()) %>"
										rowCheckerName="<%= JournalFolder.class.getSimpleName() %>"
										showCheckbox="<%= JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE) %>"
										thumbnailSrc='<%= themeDisplay.getPathThemeImages() + "/file_system/large/" + folderImage + ".png" %>'
										title="<%= (summary != null) ? summary.getTitle() : curFolder.getName() %>"
										url="<%= rowURL.toString() %>"
									/>
								</c:when>

								<c:otherwise>
									<div style="float: left; margin: 100px 10px 0px;">
										<img alt="<liferay-ui:message key="image" />" border="no" src="<%= themeDisplay.getPathThemeImages() %>/application/forbidden_action.png" />
									</div>
								</c:otherwise>
							</c:choose>

						<%
						}
						%>

					</c:when>
					<c:otherwise>

						<%
						List results = null;
						%>

						<%@ include file="/html/portlet/journal/article_search_results_database.jspf" %>

						<%
						emptySearchResults = results.isEmpty();

						String[] queryTerms = StringUtil.split(keywords);

						for (int i = 0; i < results.size(); i++) {
							Object result = results.get(i);
						%>

							<%@ include file="/html/portlet/journal/cast_result.jspf" %>

							<c:choose>
								<c:when test="<%= (curArticle != null) && JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.VIEW) %>">

									<%
									String articleImageURL = curArticle.getArticleImageURL(themeDisplay);

									PortletURL rowURL = liferayPortletResponse.createRenderURL();

									rowURL.setParameter("struts_action", "/journal/edit_article");
									rowURL.setParameter("redirect", currentURL);
									rowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
									rowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
									rowURL.setParameter("articleId", curArticle.getArticleId());

									request.setAttribute("view_entries.jsp-article", curArticle);
									%>

									<liferay-ui:app-view-search-entry
										actionJsp="/html/portlet/journal/article_action.jsp"
										containerName="<%= JournalUtil.getAbsolutePath(liferayPortletRequest, curArticle.getFolderId()) %>"
										cssClass='<%= MathUtil.isEven(i) ? "alt" : StringPool.BLANK %>'
										description="<%= LocalizationUtil.getLocalization(curArticle.getContent(), themeDisplay.getLanguageId()) %>"
										queryTerms="<%= queryTerms %>"
										rowCheckerId="<%= HtmlUtil.escape(curArticle.getArticleId()) %>"
										rowCheckerName="<%= JournalArticle.class.getSimpleName() %>"
										showCheckbox="<%= JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE) %>"
										status="<%= curArticle.getStatus() %>"
										thumbnailSrc='<%= Validator.isNotNull(articleImageURL) ? articleImageURL : themeDisplay.getPathThemeImages() + "/file_system/large/article.png" %>'
										title="<%= curArticle.getTitle(locale) %>"
										url="<%= rowURL.toString() %>"
									/>
								</c:when>

								<c:otherwise>
									<div style="float: left; margin: 100px 10px 0px;">
										<img alt="<liferay-ui:message key="image" />" border="no" src="<%= themeDisplay.getPathThemeImages() %>/application/forbidden_action.png" />
									</div>
								</c:otherwise>
							</c:choose>

						<%
						}
						%>

					</c:otherwise>
				</c:choose>

				<c:if test="<%= emptySearchResults %>">
					<div class="alert alert-info">

						<%
						String message = LanguageUtil.get(pageContext, "no-web-content-was-found-that-matched-the-specified-filters");

						if (!advancedSearch) {
							message = LanguageUtil.format(pageContext, "no-web-content-was-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>");
						}
						%>

						<%= message %>
					</div>
				</c:if>

			<%
			}
			catch (Exception e) {
				_log.error(e, e);
			}
			%>

		</aui:form>
	</div>

	<%
	request.setAttribute("view.jsp-total", String.valueOf(total));

	request.setAttribute("view_entries.jsp-entryStart", String.valueOf(searchContainer.getStart()));
	request.setAttribute("view_entries.jsp-entryEnd", String.valueOf(searchContainer.getEnd()));
	%>

	<aui:script>
		Liferay.fire(
			'<portlet:namespace />pageLoaded',
			{
				pagination: {
					name: 'entryPagination',
					state: {
						page: <%= searchContainer.getCur() %>,
						rowsPerPage: <%= searchContainer.getDelta() %>,
						total: <%= total %>
					}
				},
				src: Liferay.JOURNAL_SEARCH
			}
		);
	</aui:script>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= searchType == JournalSearchConstants.SINGLE %>">
		<div class="search-results-container" id="<portlet:namespace />searchResultsContainer">

			<%= searchResults %>

		</div>
	</c:when>
	<c:when test="<%= searchType == JournalSearchConstants.FRAGMENT %>">
		<div class="aa">
		<div id="<portlet:namespace />fragmentSearchResults">
			<%= searchResults %>
		</div>
		</div>
	</c:when>
</c:choose>

<%
request.setAttribute("view.jsp-folderId", String.valueOf(folderId));
%>

<span id="<portlet:namespace />displayStyleButtons">
</span>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.journal.search_resources_jsp");
%>