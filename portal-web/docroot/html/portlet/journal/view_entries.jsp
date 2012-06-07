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
long folderId = GetterUtil.getLong((String)liferayPortletRequest.getAttribute("view.jsp-folderId"));

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view");

String displayStyle = ParamUtil.getString(liferayPortletRequest, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.JOURNAL, "display-style", PropsValues.JOURNAL_DEFAULT_DISPLAY_VIEW);
}
else {
	boolean saveDisplayStyle = ParamUtil.getBoolean(liferayPortletRequest, "saveDisplayStyle");

	if (saveDisplayStyle && ArrayUtil.contains(displayViews, displayStyle)) {
		portalPreferences.setValue(PortletKeys.JOURNAL, "display-style", displayStyle);
	}
}

if (!ArrayUtil.contains(displayViews, displayStyle)) {
	displayStyle = displayViews[0];
}

ArticleSearch searchContainer = new ArticleSearch(liferayPortletRequest, portletURL);

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

<c:if test="<%= Validator.isNotNull(displayTerms.getTemplateId()) %>">
	<aui:input name="<%= displayTerms.TEMPLATE_ID %>" type="hidden" value="<%= displayTerms.getTemplateId() %>" />

	<c:if test="<%= showAddArticleButton %>">
		<div class="portlet-msg-info">

			<%
			JournalTemplate template = JournalTemplateLocalServiceUtil.getTemplate(scopeGroupId, displayTerms.getTemplateId());
			JournalStructure structure = JournalStructureLocalServiceUtil.getStructure(scopeGroupId, template.getStructureId());
			%>

			<liferay-portlet:renderURL varImpl="addArticlesURL" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
				<portlet:param name="structureId" value="<%= template.getStructureId() %>" />
				<portlet:param name="templateId" value="<%= displayTerms.getTemplateId() %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:message arguments="<%= template.getName(locale) %>" key="showing-content-filtered-by-template-x" /> (<a href="<%= addArticlesURL.toString() %>"><liferay-ui:message arguments="<%= structure.getName(locale) %>" key="add-new-x" /></a>)
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

boolean advancedSearch = ParamUtil.getBoolean(liferayPortletRequest, displayTerms.ADVANCED_SEARCH, false);

String keywords = ParamUtil.getString(liferayPortletRequest, "keywords");

int entryStart = ParamUtil.getInteger(request, "entryStart", searchContainer.getStart());
int entryEnd = ParamUtil.getInteger(request, "entryEnd", searchContainer.getEnd());

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
		results = JournalArticleServiceUtil.getArticlesByUserId(scopeGroupId, themeDisplay.getUserId(), entryStart, entryEnd, null);
		total = JournalArticleServiceUtil.getArticlesCountByUserId(scopeGroupId, themeDisplay.getUserId());

		searchContainer.setResults(results);
		searchContainer.setTotal(total);
		%>

	</c:when>
	<c:when test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">

		<%
		results = JournalArticleServiceUtil.search(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFolderId(), 0, searchTerms.getKeywords(), searchTerms.getVersionObj(), null, searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), searchTerms.getStatusCode(), searchTerms.getReviewDate(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
		total = JournalArticleServiceUtil.searchCount(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFolderId(), 0, searchTerms.getKeywords(), searchTerms.getVersionObj(), null, searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), searchTerms.getStatusCode(), searchTerms.getReviewDate());

		searchContainer.setResults(results);
		searchContainer.setTotal(total);
		%>

	</c:when>
	<c:otherwise>

		<%
		results = JournalFolderServiceUtil.getFoldersAndArticles(scopeGroupId, folderId, entryStart, entryEnd, null);
		total = JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, folderId);

		searchContainer.setResults(results);
		searchContainer.setTotal(total);
		%>

	</c:otherwise>
</c:choose>

<%
liferayPortletRequest.setAttribute("view.jsp-total", String.valueOf(total));
%>

<c:if test="<%= results.isEmpty() %>">
	<div class="entries-empty portlet-msg-info">
		<liferay-ui:message key="no-web-content-were-found" />
	</div>
</c:if>

<div class="results-grid">

	<%
	for (int i = 0; i < results.size(); i++) {
		Object result = results.get(i);
	%>

		<%@ include file="/html/portlet/journal/cast_result.jspf" %>

		<c:choose>
			<c:when test="<%= curArticle != null %>">
				<c:choose>
					<c:when test='<%= !displayStyle.equals("list") %>'>

						<%
						PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

						tempRowURL.setParameter("struts_action", "/journal/edit_article");
						tempRowURL.setParameter("redirect", currentURL);
						tempRowURL.setParameter("originalRedirect", currentURL);
						tempRowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
						tempRowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
						tempRowURL.setParameter("articleId", curArticle.getArticleId());

						liferayPortletRequest.setAttribute("view_entries.jsp-article", curArticle);

						liferayPortletRequest.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
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
						<liferay-util:buffer var="articleTitle">

							<%
							PortletURL rowURL = liferayPortletResponse.createRenderURL();

							rowURL.setParameter("struts_action", "/journal/edit_article");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("originalRedirect", currentURL);
							rowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
							rowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
							rowURL.setParameter("articleId", curArticle.getArticleId());
							%>

							<liferay-ui:icon
								cssClass="document-display-style selectable"
								image="../file_system/small/html"
								label="<%= true %>"
								message="<%= curArticle.getTitle(locale) %>"
								method="get"
								url="<%= rowURL.toString() %>"
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

						<%
						List resultRows = searchContainer.getResultRows();

						ResultRow row = new ResultRow(curArticle, curArticle.getArticleId(), i);

						row.setClassName("article-display-style");

						Map<String, Object> data = new HashMap<String, Object>();

						data.put("draggable", JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE));
						data.put("title", curArticle.getTitle(locale));

						row.setData(data);
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
						PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

						tempRowURL.setParameter("struts_action", "/journal/view");
						tempRowURL.setParameter("redirect", currentURL);
						tempRowURL.setParameter("originalRedirect", currentURL);
						tempRowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
						tempRowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

						liferayPortletRequest.setAttribute("view_entries.jsp-folder", curFolder);

						liferayPortletRequest.setAttribute("view_entries.jsp-folderImage", folderImage);

						liferayPortletRequest.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
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
						<liferay-util:buffer var="folderTitle">

							<%
							Map<String, Object> data = new HashMap<String, Object>();

							data.put("folder", true);
							data.put("folder-id", curFolder.getFolderId());

							PortletURL rowURL = liferayPortletResponse.createRenderURL();

							rowURL.setParameter("struts_action", "/journal/view");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("originalRedirect", currentURL);
							rowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
							rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
							%>

							<liferay-ui:icon
								data="<%= data %>"
								image="<%= folderImage %>"
								label="<%= true %>"
								message="<%= curFolder.getName() %>"
								method="get"
								url="<%= rowURL.toString() %>"
							/>
						</liferay-util:buffer>

						<%
						List resultRows = searchContainer.getResultRows();

						ResultRow row = new ResultRow(curFolder, curFolder.getPrimaryKey(), i);

						row.setClassName("article-display-style");

						Map<String, Object> data = new HashMap<String, Object>();

						data.put("draggable", JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE));
						data.put("folder", true);
						data.put("folder-id", curFolder.getFolderId());
						data.put("title", curFolder.getName());

						row.setData(data);
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

	<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />
</div>

<aui:script>
	Liferay.fire(
		'<portlet:namespace />pageLoaded',
		{
			paginator: {
				name: 'entryPaginator',
				state: {
					page: <%= (total == 0) ? 0 : (entryEnd / (entryEnd - entryStart)) %>,
					rowsPerPage: <%= (entryEnd - entryStart) %>,
					total: <%= total %>
				}
			}
		}
	);
</aui:script>