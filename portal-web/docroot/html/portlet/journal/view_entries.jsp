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
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.JOURNAL, "display-style", PropsValues.JOURNAL_DEFAULT_DISPLAY_VIEW);
}
else {
	boolean saveDisplayStyle = ParamUtil.getBoolean(request, "saveDisplayStyle");

	if (saveDisplayStyle && ArrayUtil.contains(displayViews, displayStyle)) {
		portalPreferences.setValue(PortletKeys.JOURNAL, "display-style", displayStyle);
	}
}

if (!ArrayUtil.contains(displayViews, displayStyle)) {
	displayStyle = displayViews[0];
}

ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

List headerNames = searchContainer.getHeaderNames();

headerNames.add(2, "status");
headerNames.add(StringPool.BLANK);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("article-selector");

searchContainer.setRowChecker(entriesChecker);

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms)searchContainer.getDisplayTerms();

boolean showAddArticleButton = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ARTICLE);
%>

<c:if test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">
	<aui:input name="<%= displayTerms.STRUCTURE_ID %>" type="hidden" value="<%= displayTerms.getStructureId() %>" />

	<c:if test="<%= showAddArticleButton %>">
		<div class="portlet-msg-info">

			<%
			JournalStructure structure = JournalStructureLocalServiceUtil.getStructure(scopeGroupId, displayTerms.getStructureId());
			%>

			<liferay-portlet:renderURL varImpl="addArticlesURL" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
				<portlet:param name="structureId" value="<%= displayTerms.getStructureId() %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:message arguments="<%= structure.getName(locale) %>" key="showing-content-filtered-by-structure-x" /> (<a href="<%= addArticlesURL.toString() %>"><liferay-ui:message arguments="<%= structure.getName(locale) %>" key="add-new-x" /></a>)
		</div>
	</c:if>
</c:if>

<c:if test="<%= portletName.equals(PortletKeys.JOURNAL) && !((themeDisplay.getScopeGroupId() == themeDisplay.getCompanyGroupId()) && (Validator.isNotNull(displayTerms.getStructureId()) || Validator.isNotNull(displayTerms.getTemplateId()))) %>">
	<aui:input name="groupId" type="hidden" />
</c:if>

<%
ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

searchTerms.setFolderId(-1);
searchTerms.setVersion(-1);

if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	searchTerms.setFolderId(folderId);
}

boolean advancedSearch = ParamUtil.getBoolean(request, displayTerms.ADVANCED_SEARCH, false);

String keywords = ParamUtil.getString(request, "keywords");

List results = null;
int total = 0;
%>

<c:choose>
	<c:when test="<%= (Validator.isNotNull(keywords) || advancedSearch) %>">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_ARTICLES_SEARCH_WITH_INDEX %>">
				<%@ include file="/html/portlet/journal/article_search_results_index.jspf" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/journal/article_search_results_database.jspf" %>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test='<%= displayTerms.getNavigation().equals("mine") %>'>

		<%
		results = JournalArticleServiceUtil.getArticlesByUserId(scopeGroupId, themeDisplay.getUserId(), searchContainer.getStart(), searchContainer.getEnd(), null);
		total = JournalArticleServiceUtil.getArticlesCountByUserId(scopeGroupId, themeDisplay.getUserId());

		searchContainer.setResults(results);
		searchContainer.setTotal(total);
		%>

	</c:when>
	<c:when test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">

		<%
		results = JournalArticleServiceUtil.getArticlesByStructureId(scopeGroupId, displayTerms.getStructureId(), searchContainer.getStart(), searchContainer.getEnd(), null);
		total = JournalArticleServiceUtil.getArticlesCountByStructureId(scopeGroupId, displayTerms.getStructureId());

		searchContainer.setResults(results);
		searchContainer.setTotal(total);
		%>

	</c:when>
	<c:otherwise>

		<%
		results = JournalFolderServiceUtil.getFoldersAndArticles(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd(), null);
		total = JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, folderId);

		searchContainer.setResults(results);
		searchContainer.setTotal(total);
		%>

	</c:otherwise>
</c:choose>

<c:if test="<%= results.isEmpty() %>">
	<div class="entries-empty portlet-msg-info">
		<liferay-ui:message key="no-web-content-were-found" />
	</div>
</c:if>

<div class="results-grid">

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Object result = results.get(i);
	%>

		<%@ include file="/html/portlet/journal/cast_result.jspf" %>

		<c:choose>
			<c:when test="<%= curArticle != null %>">
				<c:choose>
					<c:when test='<%= !displayStyle.equals("list") %>'>

						<%
						PortletURL tempRowURL = renderResponse.createRenderURL();

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

						<%
						ResultRow row = new ResultRow(curArticle, curArticle.getArticleId(), i);

						PortletURL rowURL = renderResponse.createRenderURL();

						rowURL.setParameter("struts_action", "/journal/edit_article");
						rowURL.setParameter("redirect", currentURL);
						rowURL.setParameter("originalRedirect", currentURL);
						rowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
						rowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
						rowURL.setParameter("articleId", curArticle.getArticleId());
						%>

						<%@ include file="/html/portlet/journal/article_columns.jspf" %>

						<%

						// Add result row

						resultRows.add(row);
						%>

					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="<%= curFolder != null %>">

				<%
				int foldersCount = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, curFolder.getFolderId());
				int articlesCount = JournalArticleServiceUtil.getArticlesCount(scopeGroupId, curFolder.getFolderId());

				String folderImage = "folder_empty";

				if ((foldersCount + articlesCount) > 0) {
					folderImage = "folder_full_document";
				}
				%>

				<c:choose>
					<c:when test='<%= !displayStyle.equals("list") %>'>

						<%
						PortletURL tempRowURL = renderResponse.createRenderURL();

						tempRowURL.setParameter("struts_action", "/journal/view");
						tempRowURL.setParameter("redirect", currentURL);
						tempRowURL.setParameter("originalRedirect", currentURL);
						tempRowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
						tempRowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

						request.setAttribute("view_entries.jsp-folder", curFolder);

						request.setAttribute("view_entries.jsp-folderImage", folderImage);

						request.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
						%>

						<c:choose>
							<c:when test='<%= displayStyle.equals("icon") %>'>
								<liferay-util:include page="/html/portlet/journal/view_folder_icon.jsp" />
							</c:when>
							<c:otherwise>
								<liferay-util:include page="/html/portlet/journal/view_folder_descriptive.jsp" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>

						<%
						ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

						PortletURL rowURL = renderResponse.createRenderURL();

						rowURL.setParameter("struts_action", "/journal/view");
						rowURL.setParameter("redirect", currentURL);
						rowURL.setParameter("originalRedirect", currentURL);
						rowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
						rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
						%>

						<%@ include file="/html/portlet/journal/folder_columns.jspf" %>

						<%

						// Add result row

						resultRows.add(row);
						%>

					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>

	<%
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</div>

<aui:script>
	var journalContainer = A.one('#<portlet:namespace />journalContainer');

	journalContainer.delegate(
		'change',
		<portlet:namespace />onJournalContainerChange,
		'.article-selector'
	);

	function <portlet:namespace />onJournalContainerChange(event) {

		<%
		if (!displayStyle.equals("list")) {
		%>

			event.currentTarget.ancestor('.article-display-style').toggleClass('selected');

		<%
		}
		%>

	}

	var selectAllCheckbox = journalContainer.one('#<portlet:namespace />allRowIdsCheckbox');

	selectAllCheckbox.on('click', <portlet:namespace />onSelectAllCheckboxChange);

	function <portlet:namespace />onSelectAllCheckboxChange(event) {
		<portlet:namespace />_toggleEntriesSelection();

		debugger;

		var buttons = A.all('.delete-articles-button, .expire-articles-button');

		if (event.currentTarget.get('checked')) {
			buttons.show();
		}
		else {
			buttons.hide();
		}
	}

	function <portlet:namespace />_toggleEntriesSelection() {
		var journalContainer = A.one('#<portlet:namespace />journalContainer');

		var selectAllCheckbox = journalContainer.one('#<portlet:namespace />allRowIdsCheckbox');

		Liferay.Util.checkAll(journalContainer, '<portlet:namespace />rowIdsJournalFolderCheckbox', selectAllCheckbox, '.results-row');
		Liferay.Util.checkAll(journalContainer, '<portlet:namespace />rowIdsJournalArticleCheckbox', selectAllCheckbox, '.results-row');

		var documentDisplayStyle = A.all('.article-display-style.selectable');

		documentDisplayStyle.toggleClass('selected', selectAllCheckbox.attr('checked'));
	}

	Liferay.provide(
		window,
		'<portlet:namespace />deleteArticles',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-web-content") %>')) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
				document.<portlet:namespace />fm.<portlet:namespace />groupId.value = "<%= scopeGroupId %>";
				document.<portlet:namespace />fm.<portlet:namespace />deleteArticleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />expireArticles',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-expire-the-selected-web-content") %>')) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EXPIRE %>";
				document.<portlet:namespace />fm.<portlet:namespace />groupId.value = "<%= scopeGroupId %>";
				document.<portlet:namespace />fm.<portlet:namespace />expireArticleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>