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

ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

List headerNames = searchContainer.getHeaderNames();

headerNames.add(2, "status");
headerNames.add(StringPool.BLANK);

searchContainer.setRowChecker(new RowChecker(renderResponse));

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

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	Object result = results.get(i);
%>

	<%@ include file="/html/portlet/journal/cast_result.jspf" %>

	<c:choose>
		<c:when test="<%= curArticle != null %>">

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

		</c:when>
		<c:when test="<%= curFolder != null %>">

			<%
			int foldersCount = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, curFolder.getFolderId());
			int articlesCount = JournalArticleServiceUtil.getArticlesCount(scopeGroupId, curFolder.getFolderId());

			String folderImage = "folder_empty";

			if ((foldersCount + articlesCount) > 0) {
				folderImage = "folder_full_document";
			}

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

		</c:when>
	</c:choose>

<%
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<aui:script>
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