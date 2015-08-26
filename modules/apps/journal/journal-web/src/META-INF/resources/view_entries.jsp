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
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

String currentFolder = ParamUtil.getString(request, "curFolder");
String deltaFolder = ParamUtil.getString(request, "deltaFolder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String ddmStructureName = LanguageUtil.get(request, "basic-web-content");

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("curFolder", currentFolder);
portletURL.setParameter("deltaFolder", deltaFolder);
portletURL.setParameter("folderId", String.valueOf(folderId));

ArticleSearch articleSearchContainer = new ArticleSearch(liferayPortletRequest, portletURL);

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNull(orderByCol)) {
	orderByCol = portalPreferences.getValue(JournalPortletKeys.JOURNAL, "order-by-col", "modified-date");
	orderByType = portalPreferences.getValue(JournalPortletKeys.JOURNAL, "order-by-type", "asc");
}
else {
	boolean saveOrderBy = ParamUtil.getBoolean(request, "saveOrderBy");

	if (saveOrderBy) {
		portalPreferences.setValue(JournalPortletKeys.JOURNAL, "order-by-col", orderByCol);
		portalPreferences.setValue(JournalPortletKeys.JOURNAL, "order-by-type", orderByType);
	}
}

OrderByComparator<JournalArticle> orderByComparator = JournalPortletUtil.getArticleOrderByComparator(orderByCol, orderByType);

articleSearchContainer.setOrderByCol(orderByCol);
articleSearchContainer.setOrderByComparator(orderByComparator);
articleSearchContainer.setOrderByType(orderByType);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("entry-selector");

articleSearchContainer.setRowChecker(entriesChecker);

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms)articleSearchContainer.getDisplayTerms();
%>

<c:if test="<%= Validator.isNotNull(displayTerms.getDDMStructureKey()) %>">
	<aui:input name="<%= ArticleDisplayTerms.DDM_STRUCTURE_KEY %>" type="hidden" value="<%= displayTerms.getDDMStructureKey() %>" />

	<%
	DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(JournalArticle.class), displayTerms.getDDMStructureKey(), true);

	if (ddmStructure != null) {
		ddmStructureName = ddmStructure.getName(locale);
	}
	%>

</c:if>

<c:if test="<%= Validator.isNotNull(displayTerms.getDDMTemplateKey()) %>">
	<aui:input name="<%= ArticleDisplayTerms.DDM_TEMPLATE_KEY %>" type="hidden" value="<%= displayTerms.getDDMTemplateKey() %>" />
</c:if>

<c:if test="<%= portletName.equals(JournalPortletKeys.JOURNAL) && !((themeDisplay.getScopeGroupId() == themeDisplay.getCompanyGroupId()) && (Validator.isNotNull(displayTerms.getDDMStructureKey()) || Validator.isNotNull(displayTerms.getDDMTemplateKey()))) %>">
	<aui:input name="groupId" type="hidden" />
</c:if>

<%
ArticleSearchTerms searchTerms = (ArticleSearchTerms)articleSearchContainer.getSearchTerms();

if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	List<Long> folderIds = new ArrayList<Long>(1);

	folderIds.add(folderId);

	searchTerms.setFolderIds(folderIds);
}
else {
	searchTerms.setFolderIds(new ArrayList<Long>());
}

if (Validator.isNotNull(displayTerms.getDDMStructureKey())) {
	searchTerms.setDDMStructureKey(displayTerms.getDDMStructureKey());
}

searchTerms.setVersion(-1);

if (displayTerms.isNavigationRecent()) {
	articleSearchContainer.setOrderByCol("create-date");
	articleSearchContainer.setOrderByType(orderByType);
}

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

List results = null;
int total = 0;
%>

<c:choose>
	<c:when test='<%= displayTerms.getNavigation().equals("mine") || displayTerms.isNavigationRecent() %>'>

		<%
		boolean includeOwner = true;

		if (displayTerms.getNavigation().equals("mine")) {
			includeOwner = false;

			status = WorkflowConstants.STATUS_ANY;
		}

		total = JournalArticleServiceUtil.getGroupArticlesCount(scopeGroupId, themeDisplay.getUserId(), folderId, status, includeOwner);

		articleSearchContainer.setTotal(total);

		results = JournalArticleServiceUtil.getGroupArticles(scopeGroupId, themeDisplay.getUserId(), folderId, status, includeOwner, articleSearchContainer.getStart(), articleSearchContainer.getEnd(), articleSearchContainer.getOrderByComparator());
		%>

	</c:when>
	<c:when test="<%= Validator.isNotNull(displayTerms.getDDMStructureKey()) %>">

		<%
		total = JournalArticleServiceUtil.getArticlesCountByStructureId(displayTerms.getGroupId(), searchTerms.getDDMStructureKey());

		articleSearchContainer.setTotal(total);

		results = JournalArticleServiceUtil.getArticlesByStructureId(displayTerms.getGroupId(), displayTerms.getDDMStructureKey(), articleSearchContainer.getStart(), articleSearchContainer.getEnd(), articleSearchContainer.getOrderByComparator());
		%>

	</c:when>
	<c:when test="<%= Validator.isNotNull(displayTerms.getDDMTemplateKey()) %>">

		<%
		total = JournalArticleServiceUtil.searchCount(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFolderIds(), JournalArticleConstants.CLASSNAME_ID_DEFAULT, searchTerms.getKeywords(), searchTerms.getVersionObj(), searchTerms.getDDMStructureKey(), searchTerms.getDDMTemplateKey(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), searchTerms.getStatus(), searchTerms.getReviewDate());

		articleSearchContainer.setTotal(total);

		results = JournalArticleServiceUtil.search(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFolderIds(), JournalArticleConstants.CLASSNAME_ID_DEFAULT, searchTerms.getKeywords(), searchTerms.getVersionObj(), searchTerms.getDDMStructureKey(), searchTerms.getDDMTemplateKey(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), searchTerms.getStatus(), searchTerms.getReviewDate(), articleSearchContainer.getStart(), articleSearchContainer.getEnd(), articleSearchContainer.getOrderByComparator());
		%>

	</c:when>
	<c:otherwise>

		<%
		total = JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, themeDisplay.getUserId(), folderId, status);

		articleSearchContainer.setTotal(total);

		results = JournalFolderServiceUtil.getFoldersAndArticles(scopeGroupId, themeDisplay.getUserId(), folderId, status, articleSearchContainer.getStart(), articleSearchContainer.getEnd(), articleSearchContainer.getOrderByComparator());
		%>

	</c:otherwise>
</c:choose>

<%
articleSearchContainer.setResults(results);

request.setAttribute("view.jsp-total", String.valueOf(total));

request.setAttribute("view_entries.jsp-entryStart", String.valueOf(articleSearchContainer.getStart()));
request.setAttribute("view_entries.jsp-entryEnd", String.valueOf(articleSearchContainer.getEnd()));
%>

<c:if test="<%= results.isEmpty() %>">
	<div class="alert alert-info entries-empty">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(displayTerms.getDDMStructureKey()) %>">
				<c:if test="<%= total == 0 %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(ddmStructureName) %>" key="there-is-no-web-content-with-structure-x" translateArguments="<%= false %>" />
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="<%= total == 0 %>">
					<liferay-ui:message key="no-web-content-was-found" />
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<%
String displayStyle = journalDisplayContext.getDisplayStyle();
%>

<liferay-ui:search-container
	searchContainer="<%= articleSearchContainer %>"
	totalVar="articleSearchContainerTotal"
>
	<liferay-ui:search-container-results
		results="<%= results %>"
		resultsVar="articleSearchContainerResults"
		total="<%= total %>"
	/>

	<liferay-ui:search-container-row
		className="Object"
		cssClass="entry-display-style selectable"
		modelVar="object"
	>

		<%
		JournalArticle curArticle = null;
		JournalFolder curFolder = null;

		Object result = row.getObject();

		if (result instanceof JournalFolder) {
			curFolder = (JournalFolder)result;
		}
		else {
			curArticle = (JournalArticle)result;
		}
		%>

		<c:choose>
			<c:when test="<%= curArticle != null %>">

				<%
				Map<String, Object> rowData = new HashMap<String, Object>();

				rowData.put("draggable", JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE));
				rowData.put("title", HtmlUtil.escape(curArticle.getTitle(locale)));

				row.setData(rowData);

				row.setPrimaryKey(HtmlUtil.escape(curArticle.getArticleId()));
				%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<%@ include file="/article_columns_descriptive.jspf" %>
					</c:when>
					<c:when test='<%= displayStyle.equals("icon") %>'>
						<%@ include file="/article_columns_icon.jspf" %>
					</c:when>
					<c:otherwise>
						<%@ include file="/article_columns_list.jspf" %>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="<%= curFolder != null %>">

				<%
				Map<String, Object> rowData = new HashMap<String, Object>();

				rowData.put("draggable", JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE));
				rowData.put("folder", true);
				rowData.put("folder-id", curFolder.getFolderId());
				rowData.put("title", HtmlUtil.escape(curFolder.getName()));

				row.setData(rowData);
				row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));
				%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<%@ include file="/folder_columns_descriptive.jspf" %>
					</c:when>
					<c:when test='<%= displayStyle.equals("icon") %>'>
						<%@ include file="/folder_columns_icon.jspf" %>
					</c:when>
					<c:otherwise>
						<%@ include file="/folder_columns_list.jspf" %>
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle='<%= displayStyle.equals("list") ? null : displayStyle %>' paginate="<%= false %>" searchContainer="<%= articleSearchContainer %>" />
</liferay-ui:search-container>

<div class="article-entries-pagination">
	<liferay-ui:search-paginator searchContainer="<%= articleSearchContainer %>" />
</div>