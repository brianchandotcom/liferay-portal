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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long folderId = ParamUtil.getLong(request, "folderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId", folderId);

JournalFolder folder = null;

if (searchFolderId > 0) {
	folder = JournalFolderServiceUtil.getFolder(searchFolderId);
}

String keywords = ParamUtil.getString(request, "keywords");

int total = 0;

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("redirect", redirect);
portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
portletURL.setParameter("keywords", keywords);

ArticleSearch searchContainer = new ArticleSearch(liferayPortletRequest, portletURL);
%>

<div class="search-info">
	<span class="keywords">
		<c:choose>
			<c:when test="<%= folder != null %>">
				<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(keywords)} %>" key="searched-for-x-in-x" translateArguments="<%= false %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message arguments="<%= HtmlUtil.escape(keywords) %>" key="searched-for-x-everywhere" translateArguments="<%= false %>" />
			</c:otherwise>
		</c:choose>
	</span>

	<c:if test="<%= folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
		<span class="change-search-folder">
			<portlet:renderURL var="changeSearchFolderURL">
				<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				<portlet:param name="searchFolderId" value="<%= (folder != null) ? String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) : String.valueOf(folderId) %>" />
				<portlet:param name="keywords" value="<%= keywords %>" />
				<portlet:param name="displayStyle" value="<%= journalDisplayContext.getDisplayStyle() %>" />
			</portlet:renderURL>

			<aui:button href="<%= changeSearchFolderURL %>" value='<%= (folder != null) ? "search-everywhere" : "search-in-the-current-folder" %>' />
		</span>
	</c:if>

	<portlet:renderURL var="closeSearchURL">
		<portlet:param name="displayStyle" value="<%= journalDisplayContext.getDisplayStyle() %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		cssClass="close-search"
		iconCssClass="icon-remove"
		message="remove"
		url="<%= closeSearchURL %>"
	/>
</div>

<liferay-portlet:renderURL varImpl="searchURL" />

<aui:form action="<%= searchURL %>" method="get" name="fm2">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="searchFolderId" type="hidden" value="<%= searchFolderId %>" />

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
			<c:when test="<%= journalWebConfiguration.journalArticlesSearchWithIndex() %>">

				<%
				SearchContext searchContext = SearchContextFactory.getInstance(request);

				Indexer<?> indexer = null;

				indexer = JournalSearcher.getInstance();

				searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);

				if (Validator.isNotNull(keywords)) {
					searchContext.setAttribute(Field.ARTICLE_ID, keywords);
					searchContext.setAttribute(Field.CONTENT, keywords);
					searchContext.setAttribute(Field.DESCRIPTION, keywords);
					searchContext.setAttribute(Field.TITLE, keywords);
					searchContext.setKeywords(keywords);
				}
				else {
					searchContext.setAndSearch(true);
				}

				searchContext.setIncludeDiscussions(true);

				LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

				params.put("expandoAttributes", searchTerms.getKeywords());

				searchContext.setAttribute("head", Boolean.FALSE.toString());
				searchContext.setAttribute("params", params);
				searchContext.setEnd(searchContainer.getEnd());
				searchContext.setFolderIds(searchTerms.getFolderIds());
				searchContext.setStart(searchContainer.getStart());

				Hits hits = indexer.search(searchContext);

				total = hits.getLength();

				searchContainer.setTotal(total);

				List<SearchResult> searchResultsList = SearchResultUtil.getSearchResults(hits, locale, liferayPortletRequest, liferayPortletResponse);

				emptySearchResults = searchResultsList.isEmpty();

				for (int i = 0; i < searchResultsList.size(); i++) {
					SearchResult searchResult = searchResultsList.get(i);

					Summary summary = searchResult.getSummary();

					summary.setQueryTerms(hits.getQueryTerms());

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

							rowURL.setParameter("mvcPath", "/edit_article.jsp");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("groupId", String.valueOf(article.getGroupId()));
							rowURL.setParameter("folderId", String.valueOf(article.getFolderId()));
							rowURL.setParameter("articleId", article.getArticleId());

							List<String> versions = searchResult.getVersions();

							Collections.sort(versions);

							request.setAttribute("view_entries.jsp-article", article);
							%>

							<liferay-ui:app-view-search-entry
								actionJsp="/article_action.jsp"
								actionJspServletContext="<%= application %>"
								commentRelatedSearchResults="<%= searchResult.getCommentRelatedSearchResults() %>"
								containerName="<%= JournalUtil.getAbsolutePath(liferayPortletRequest, article.getFolderId()) %>"
								cssClass='<%= MathUtil.isEven(i) ? "alt" : StringPool.BLANK %>'
								description="<%= ((summary != null) && Validator.isNotNull(summary.getContent())) ? summary.getContent() : article.getDescription(locale) %>"
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

							if (JournalServiceConfigurationValues.JOURNAL_FOLDER_ICON_CHECK_COUNT && (JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, curFolder.getFolderId()) > 0)) {
								folderImage = "folder_full_article";
							}

							PortletURL rowURL = liferayPortletResponse.createRenderURL();

							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
							rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

							request.setAttribute("view_entries.jsp-folder", curFolder);
							%>

							<liferay-ui:app-view-search-entry
								actionJsp="/folder_action.jsp"
								actionJspServletContext="<%= application %>"
								containerName="<%= JournalUtil.getAbsolutePath(liferayPortletRequest, curFolder.getParentFolderId()) %>"
								cssClass='<%= MathUtil.isEven(i) ? "alt" : StringPool.BLANK %>'
								description="<%= ((summary != null) && Validator.isNotNull(summary.getContent())) ? summary.getContent() : curFolder.getDescription() %>"
								queryTerms="<%= hits.getQueryTerms() %>"
								rowCheckerId="<%= String.valueOf(curFolder.getFolderId()) %>"
								rowCheckerName="<%= JournalFolder.class.getSimpleName() %>"
								showCheckbox="<%= JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE) %>"
								thumbnailSrc='<%= themeDisplay.getPathThemeImages() + "/file_system/large/" + folderImage + ".png" %>'
								title="<%= ((summary != null) && Validator.isNotNull(summary.getTitle())) ? summary.getTitle() : curFolder.getName() %>"
								url="<%= rowURL.toString() %>"
							/>
						</c:when>

						<c:otherwise>
							<div style="float: left; margin: 100px 10px 0px;">
								<i class="icon-ban-circle"></i>
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

				<%@ include file="/article_search_results_database.jspf" %>

				<%
				emptySearchResults = results.isEmpty();

				String[] queryTerms = StringUtil.split(keywords);

				for (int i = 0; i < results.size(); i++) {
					Object result = results.get(i);

					JournalArticle curArticle = null;
					JournalFolder curFolder = null;

					if (result instanceof JournalFolder) {
						curFolder = (JournalFolder)result;
					}
					else {
						curArticle = (JournalArticle)result;
					}
				%>

					<c:choose>
						<c:when test="<%= (curArticle != null) && JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.VIEW) %>">

							<%
							String articleImageURL = curArticle.getArticleImageURL(themeDisplay);

							PortletURL rowURL = liferayPortletResponse.createRenderURL();

							rowURL.setParameter("mvcPath", "/edit_article.jsp");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
							rowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
							rowURL.setParameter("articleId", curArticle.getArticleId());

							request.setAttribute("view_entries.jsp-article", curArticle);
							%>

							<liferay-ui:app-view-search-entry
								actionJsp="/article_action.jsp"
								actionJspServletContext="<%= application %>"
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
								<i class="icon-ban-circle"></i>
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
				<liferay-ui:message arguments='<%= "<strong>" + HtmlUtil.escape(keywords) + "</strong>" %>' key="no-web-content-was-found-that-matched-the-keywords-x" translateArguments="<%= false %>" />
			</div>
		</c:if>

	<%
	}
	catch (Exception e) {
		_log.error(e, e);
	}
	%>

</aui:form>

<div class="article-entries-pagination">
	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
</div>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_journal_web.search_resources_jsp");
%>