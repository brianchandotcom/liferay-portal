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

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.JOURNAL, "display-style", PropsValues.JOURNAL_DEFAULT_DISPLAY_VIEW);
}

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", SearchContainer.DEFAULT_DELTA);

boolean ajaxRequest = ParamUtil.getBoolean(request, "ajax");

boolean showSearchInfo = ParamUtil.getBoolean(request, "showSearchInfo");

if (searchType == JournalSearchConstants.FRAGMENT) {
	if (ajaxRequest) {
		showSearchInfo = false;
	}
	else {
		searchType = JournalSearchConstants.SINGLE;

		showSearchInfo = true;
	}
}
else if ((searchType == JournalSearchConstants.SINGLE) && !ajaxRequest) {
	showSearchInfo = true;
}

int total = 0;

boolean advancedSearch = ParamUtil.getBoolean(liferayPortletRequest, ArticleDisplayTerms.ADVANCED_SEARCH);
%>

<c:if test="<%= showSearchInfo %>">
	<liferay-util:buffer var="searchInfo">
		<div class="search-info">
			<span class="keywords">

				<%
				String message = StringPool.BLANK;

				if (advancedSearch) {
					if (folder != null) {
						message = LanguageUtil.format(pageContext, "advanced-search-in-x", new Object[] {folder.getName()});
					}
					else {
						message = LanguageUtil.get(pageContext, "advanced-search-everywhere");
					}
				}
				else {
					if (folder != null) {
						message = LanguageUtil.format(pageContext, "searched-for-x-in-x", new Object[] {HtmlUtil.escape(keywords), folder.getName()});
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

			<liferay-ui:icon cssClass="close-search" id="closeSearch" image="../aui/closethick" url="javascript:;" />
		</div>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
			</aui:script>
		</c:if>

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
		<aui:form action="<%= searchURL %>" method="get" name="fm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= breadcrumbsFolderId %>" />
			<aui:input name="searchFolderId" type="hidden" value="<%= searchFolderId %>" />
			<aui:input name="searchFolderIds" type="hidden" value="<%= searchFolderIds %>" />

			<%
			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/journal/search");
			portletURL.setParameter("redirect", redirect);
			portletURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
			portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
			portletURL.setParameter("searchFolderIds", String.valueOf(searchFolderIds));
			portletURL.setParameter("keywords", keywords);

			SearchContainer searchContainer = new ArticleSearch(liferayPortletRequest, portletURL);

			String orderByCol = ParamUtil.getString(request, "orderByCol");

			searchContainer.setOrderByCol(orderByCol);

			String orderByType = ParamUtil.getString(request, "orderByType");

			searchContainer.setOrderByType(orderByType);

			OrderByComparator orderByComparator = JournalUtil.getArticleOrderByComparator(orderByCol, orderByType);

			searchContainer.setOrderByComparator(orderByComparator);

			searchContainer.setRowChecker(new EntriesChecker(liferayPortletRequest, liferayPortletResponse));

			ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

			List<Long> folderIds = new ArrayList<Long>(1);

			folderIds.add(searchFolderId);

			searchTerms.setFolderIds(folderIds);

			if (searchFolderId <= 0) {
				searchTerms.setFolderIds(new ArrayList<Long>());
			}

			try {
				List results = null;
			%>

				<c:choose>
					<c:when test="<%= PropsValues.JOURNAL_ARTICLES_SEARCH_WITH_INDEX %>">
						<%@ include file="/html/portlet/journal/article_search_results_index.jspf" %>
					</c:when>
					<c:otherwise>
						<%@ include file="/html/portlet/journal/article_search_results_database.jspf" %>
					</c:otherwise>
				</c:choose>

				<%
				searchContainer.setResults(results);
				searchContainer.setTotal(total);

				request.setAttribute("view.jsp-total", String.valueOf(total));

				for (int i = 0; i < results.size(); i++) {
					Object result = results.get(i);
				%>

					<%@ include file="/html/portlet/journal/cast_result.jspf" %>

					<c:choose>
						<c:when test='<%= !displayStyle.equals("list") %>'>
							<c:choose>
								<c:when test="<%= JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.VIEW) %>">

									<%
									PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

									tempRowURL.setParameter("struts_action", "/journal/edit_article");
									tempRowURL.setParameter("redirect", currentURL);
									tempRowURL.setParameter("originalRedirect", currentURL);
									tempRowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
									tempRowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
									tempRowURL.setParameter("articleId", curArticle.getArticleId());

									request.setAttribute("view_entries.jsp-article", curArticle);
									request.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
									%>

									<c:choose>
										<c:when test='<%= displayStyle.equals("icon") %>'>
											<liferay-util:include page="/html/portlet/journal/view_article_icon.jsp" />
										</c:when>

										<c:otherwise>
											<liferay-util:include page="/html/portlet/journal/view_article_descriptive.jsp" />
										</c:otherwise>
									</c:choose>
								</c:when>

								<c:otherwise>
									<div style="float: left; margin: 100px 10px 0px;">
										<img alt="<liferay-ui:message key="image" />" border="no" src="<%= themeDisplay.getPathThemeImages() %>/application/forbidden_action.png" />
									</div>
								</c:otherwise>
							</c:choose>
						</c:when>

						<c:otherwise>

							<%
							List resultRows = searchContainer.getResultRows();

							ResultRow row = new ResultRow(curArticle, curArticle.getArticleId(), i);

							// Position

							PortletURL rowURL = liferayPortletResponse.createRenderURL();

							rowURL.setParameter("struts_action", "/journal/edit_article");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("originalRedirect", currentURL);
							rowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
							rowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
							rowURL.setParameter("articleId", curArticle.getArticleId());
							%>

							<liferay-util:buffer var="articleTitle">

								<%
								PortletURL editURL = liferayPortletResponse.createRenderURL();

								editURL.setParameter("struts_action", "/journal/edit_article");
								editURL.setParameter("redirect", currentURL);
								editURL.setParameter("originalRedirect", currentURL);
								editURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
								editURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
								editURL.setParameter("articleId", curArticle.getArticleId());
								%>

								<liferay-ui:icon
									cssClass="entry-display-style selectable"
									image="../file_system/small/html"
									label="<%= true %>"
									message="<%= curArticle.getTitle(locale) %>"
									method="get"
									url="<%= editURL.toString() %>"
								/>

								<c:if test="<%= curArticle.isDraft() || curArticle.isPending() %>">

									<%
									String statusLabel = WorkflowConstants.toLabel(curArticle.getStatus());
									%>

									<span class="workflow-status-<%= statusLabel %>">
										(<liferay-ui:message key="<%= statusLabel %>" />)
									</span>
								</c:if>
							</liferay-util:buffer>

							<%@ include file="/html/portlet/journal/article_columns.jspf" %>

							<%

							// Add result row

							resultRows.add(row);
							%>

						</c:otherwise>
					</c:choose>

				<%
				}
				%>

				<c:if test="<%= results.isEmpty() %>">
					<div class="portlet-msg-info">

						<%
						String msgInfo = LanguageUtil.get(pageContext, "no-articles-were-found-that-matched-the-specified-filters");

						if (!advancedSearch) {
							msgInfo = LanguageUtil.format(pageContext, "no-articles-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>");
						}
						%>

						<%= msgInfo %>
					</div>
				</c:if>

				<c:if test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" type="more" />
				</c:if>

			<%
			}
			catch (Exception e) {
				_log.error(e, e);
			}
			%>

		</aui:form>
	</div>

	<aui:script>
		Liferay.fire(
			'<portlet:namespace />pageLoaded',
			{
				paginator: {
					name: 'entryPaginator',
					state: {
						page: <%= (total == 0) ? 0 : entryEnd / (entryEnd - entryStart) %>,
						rowsPerPage: <%= (entryEnd - entryStart) %>,
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
	<liferay-util:include page="/html/portlet/journal/display_style_buttons.jsp" />
</span>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.journal.search_resources_jsp");
%>