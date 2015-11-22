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

String ddmStructureName = LanguageUtil.get(request, "basic-web-content");

PortletURL portletURL = journalDisplayContext.getPortletURL();

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

EntriesMover entriesMover = new EntriesMover(scopeGroupId);

articleSearchContainer.setRowMover(entriesMover);

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

if (journalDisplayContext.getFolderId() != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	List<Long> folderIds = new ArrayList<Long>(1);

	folderIds.add(journalDisplayContext.getFolderId());

	searchTerms.setFolderIds(folderIds);
}
else {
	searchTerms.setFolderIds(new ArrayList<Long>());
}

if (Validator.isNotNull(displayTerms.getDDMStructureKey())) {
	searchTerms.setDDMStructureKey(displayTerms.getDDMStructureKey());
}

searchTerms.setVersion(-1);

if (journalDisplayContext.isNavigationRecent()) {
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
	<c:when test="<%= journalDisplayContext.isNavigationMine() || journalDisplayContext.isNavigationRecent() %>">

		<%
		boolean includeOwner = true;

		if (journalDisplayContext.isNavigationMine()) {
			includeOwner = false;
		}

		total = JournalArticleServiceUtil.getGroupArticlesCount(scopeGroupId, themeDisplay.getUserId(), journalDisplayContext.getFolderId(), journalDisplayContext.getStatus(), includeOwner);

		articleSearchContainer.setTotal(total);

		results = JournalArticleServiceUtil.getGroupArticles(scopeGroupId, themeDisplay.getUserId(), journalDisplayContext.getFolderId(), journalDisplayContext.getStatus(), includeOwner, articleSearchContainer.getStart(), articleSearchContainer.getEnd(), articleSearchContainer.getOrderByComparator());
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
		total = JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, themeDisplay.getUserId(), journalDisplayContext.getFolderId(), journalDisplayContext.getStatus());

		articleSearchContainer.setTotal(total);

		OrderByComparator<Object> folderOrderByComparator = null;

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		if (orderByCol.equals("display-date")) {
			folderOrderByComparator = new FolderArticleDisplayDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			folderOrderByComparator = new FolderArticleModifiedDateComparator(orderByAsc);
		}

		results = JournalFolderServiceUtil.getFoldersAndArticles(scopeGroupId, themeDisplay.getUserId(), journalDisplayContext.getFolderId(), journalDisplayContext.getStatus(), articleSearchContainer.getStart(), articleSearchContainer.getEnd(), folderOrderByComparator);
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
String searchContainerId = ParamUtil.getString(request, "searchContainerId");
%>

<liferay-ui:search-container
	id="<%= searchContainerId %>"
	searchContainer="<%= articleSearchContainer %>"
	total="<%= total %>"
	totalVar="articleSearchContainerTotal"
>
	<liferay-ui:search-container-results
		results="<%= results %>"
		resultsVar="articleSearchContainerResults"
	/>

	<liferay-ui:search-container-row
		className="Object"
		cssClass="entry-display-style"
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

		String displayStyle = journalDisplayContext.getDisplayStyle();
		%>

		<c:choose>
			<c:when test="<%= curArticle != null %>">

				<%
				Map<String, Object> rowData = new HashMap<String, Object>();

				if (journalDisplayContext.isShowEditActions()) {
					rowData.put("draggable", JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE));
				}

				rowData.put("title", HtmlUtil.escape(curArticle.getTitle(locale)));

				row.setData(rowData);

				row.setPrimaryKey(HtmlUtil.escape(curArticle.getArticleId()));

				PortletURL rowURL = null;

				if (journalDisplayContext.isShowEditActions()) {
					rowURL = journalDisplayContext.getPortletURL();

					rowURL.setParameter("mvcPath", "/edit_article.jsp");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("referringPortletResource", referringPortletResource);
					rowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
					rowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
					rowURL.setParameter("articleId", curArticle.getArticleId());
					rowURL.setParameter("version", String.valueOf(curArticle.getVersion()));
				}
				%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-image
							src='<%= Validator.isNotNull(curArticle.getArticleImageURL(themeDisplay)) ? curArticle.getArticleImageURL(themeDisplay) : themeDisplay.getPathThemeImages() + "/file_system/large/article.png" %>'
							toggleRowChecker="<%= journalDisplayContext.isShowEditActions() %>"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="2"
							href="<%= rowURL %>"
							path="/view_article_descriptive.jsp"
						/>

						<c:if test="<%= journalDisplayContext.isShowEditActions() %>">
							<liferay-ui:search-container-column-jsp
								path="/article_action.jsp"
							/>
						</c:if>
					</c:when>
					<c:when test='<%= displayStyle.equals("icon") %>'>

						<%
						row.setCssClass("col-md-2 col-sm-4 col-xs-6 " + row.getCssClass());
						%>

						<liferay-ui:search-container-column-text>

							<%
							String articleImageURL = curArticle.getArticleImageURL(themeDisplay);
							%>

							<liferay-frontend:vertical-card
								actionJsp='<%= journalDisplayContext.isShowEditActions() ? "/article_action.jsp" : null %>'
								actionJspServletContext="<%= application %>"
								imageUrl='<%= Validator.isNotNull(articleImageURL) ? articleImageURL : themeDisplay.getPathThemeImages() + "/file_system/large/article.png" %>'
								resultRow="<%= row %>"
								rowChecker="<%= entriesChecker %>"
								title="<%= curArticle.getTitle(locale) %>"
								url="<%= rowURL != null ? rowURL.toString() : null %>"
							>
								<liferay-frontend:vertical-card-sticker-bottom>
									<liferay-ui:user-portrait
										cssClass="sticker sticker-bottom"
										userId="<%= curArticle.getUserId() %>"
									/>
								</liferay-frontend:vertical-card-sticker-bottom>

								<liferay-frontend:vertical-card-header>
									<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - curArticle.getModifiedDate().getTime(), true), HtmlUtil.escape(curArticle.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
								</liferay-frontend:vertical-card-header>

								<liferay-frontend:vertical-card-footer>
									<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= curArticle.getStatus() %>" />
								</liferay-frontend:vertical-card-footer>
							</liferay-frontend:vertical-card>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							name="id"
							value="<%= HtmlUtil.escape(curArticle.getArticleId()) %>"
						/>

						<liferay-ui:search-container-column-jsp
							href="<%= rowURL %>"
							name="title"
							path="/article_title.jsp"
							truncate="<%= true %>"
						/>

						<liferay-ui:search-container-column-status
							name="status"
						/>

						<liferay-ui:search-container-column-date
							name="modified-date"
							value="<%= curArticle.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-date
							name="display-date"
							value="<%= curArticle.getDisplayDate() %>"
						/>

						<liferay-ui:search-container-column-text
							name="author"
							value="<%= PortalUtil.getUserName(curArticle) %>"
						/>

						<%
						DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(scopeGroupId, PortalUtil.getClassNameId(JournalArticle.class), curArticle.getDDMStructureKey(), true);
						%>

						<liferay-ui:search-container-column-text
							name="type"
							value="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
						/>

						<c:if test="<%= journalDisplayContext.isShowEditActions() %>">
							<liferay-ui:search-container-column-jsp
								align="right"
								cssClass="checkbox-cell entry-action"
								path="/article_action.jsp"
							/>
						</c:if>
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

				PortletURL rowURL = liferayPortletResponse.createRenderURL();

				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
				rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
				rowURL.setParameter("displayStyle", displayStyle);
				rowURL.setParameter("showEditActions", String.valueOf(journalDisplayContext.isShowEditActions()));
				%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							icon="icon-folder-close"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-text colspan="<%= 2 %>">
							<liferay-ui:app-view-entry
								actionJsp='<%= journalDisplayContext.isShowEditActions() ? "/folder_action.jsp" : null %>'
								actionJspServletContext="<%= application %>"
								author="<%= curFolder.getUserName() %>"
								createDate="<%= curFolder.getCreateDate() %>"
								description="<%= curFolder.getDescription() %>"
								displayStyle="descriptive"
								folder="<%= true %>"
								markupView="lexicon"
								modifiedDate="<%= curFolder.getModifiedDate() %>"
								rowCheckerId="<%= String.valueOf(curFolder.getFolderId()) %>"
								rowCheckerName="<%= JournalFolder.class.getSimpleName() %>"
								showCheckbox="<%= JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE) %>"
								title="<%= curFolder.getName() %>"
								url="<%= rowURL.toString() %>"
							/>
						</liferay-ui:search-container-column-text>

						<c:if test="<%= journalDisplayContext.isShowEditActions() %>">
							<liferay-ui:search-container-column-jsp
								path="/folder_action.jsp"
							/>
						</c:if>
					</c:when>
					<c:when test='<%= displayStyle.equals("icon") %>'>

						<%
						row.setCssClass("col-md-3 col-sm-4 col-xs-12 " + row.getCssClass());
						%>

						<liferay-ui:search-container-column-text colspan="<%= 2 %>">
							<liferay-frontend:horizontal-card
								actionJsp='<%= journalDisplayContext.isShowEditActions() ? "/folder_action.jsp" : null %>'
								actionJspServletContext="<%= application %>"
								icon="icon-folder-close-alt"
								imageCSSClass="icon-monospaced"
								resultRow="<%= row %>"
								rowChecker="<%= entriesChecker %>"
								text="<%= HtmlUtil.escape(curFolder.getName()) %>"
								url="<%= rowURL.toString() %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							name="id"
							value="<%= String.valueOf(curFolder.getFolderId()) %>"
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowURL.toString() %>"
							name="title"
							truncate="<%= true %>"
							value="<%= HtmlUtil.escape(curFolder.getName()) %>"
						/>

						<liferay-ui:search-container-column-text
							name="status"
							value="--"
						/>

						<liferay-ui:search-container-column-date
							name="modified-date"
							value="<%= curFolder.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-text
							name="display-date"
							value="--"
						/>

						<liferay-ui:search-container-column-text
							name="author"
							value="<%= PortalUtil.getUserName(curFolder) %>"
						/>

						<liferay-ui:search-container-column-text
							name="type"
							value='<%= LanguageUtil.get(request, "folder") %>'
						/>

						<c:if test="<%= journalDisplayContext.isShowEditActions() %>">
							<liferay-ui:search-container-column-jsp
								align="right"
								cssClass="checkbox-cell entry-action"
								path="/folder_action.jsp"
							/>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" resultRowSplitter="<%= new JournalResultRowSplitter() %>" searchContainer="<%= articleSearchContainer %>" />
</liferay-ui:search-container>