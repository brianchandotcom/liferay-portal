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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long ddmStructureId = 0;

String ddmStructureName = LanguageUtil.get(request, "basic-web-content");

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view");
portletURL.setParameter("folderId", String.valueOf(folderId));

ArticleSearch articleSearchContainer = new ArticleSearch(liferayPortletRequest, portletURL);

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNull(orderByCol)) {
	orderByCol = portalPreferences.getValue(PortletKeys.JOURNAL, "order-by-col", "modified-date");
	orderByType = portalPreferences.getValue(PortletKeys.JOURNAL, "order-by-type", "asc");
}
else {
	boolean saveOrderBy = ParamUtil.getBoolean(request, "saveOrderBy");

	if (saveOrderBy) {
		portalPreferences.setValue(PortletKeys.JOURNAL, "order-by-col", orderByCol);
		portalPreferences.setValue(PortletKeys.JOURNAL, "order-by-type", orderByType);
	}
}

OrderByComparator<JournalArticle> orderByComparator = JournalUtil.getArticleOrderByComparator(orderByCol, orderByType);

articleSearchContainer.setOrderByCol(orderByCol);
articleSearchContainer.setOrderByComparator(orderByComparator);
articleSearchContainer.setOrderByType(orderByType);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("entry-selector");

articleSearchContainer.setRowChecker(entriesChecker);

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms) articleSearchContainer.getDisplayTerms();
%>

<c:if test="<%= Validator.isNotNull(displayTerms.getDDMStructureKey()) %>">
	<aui:input name="<%= displayTerms.DDM_STRUCTURE_KEY %>" type="hidden" value="<%= displayTerms.getDDMStructureKey() %>" />

	<%
	DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(JournalArticle.class), displayTerms.getDDMStructureKey(), true);

	if (ddmStructure != null) {
		ddmStructureId = ddmStructure.getStructureId();

		ddmStructureName = ddmStructure.getName(locale);
	}
	%>

</c:if>

<c:if test="<%= Validator.isNotNull(displayTerms.getDDMTemplateKey()) %>">
	<aui:input name="<%= displayTerms.DDM_TEMPLATE_KEY %>" type="hidden" value="<%= displayTerms.getDDMTemplateKey() %>" />
</c:if>

<c:if test="<%= portletName.equals(PortletKeys.JOURNAL) && !((themeDisplay.getScopeGroupId() == themeDisplay.getCompanyGroupId()) && (Validator.isNotNull(displayTerms.getDDMStructureKey()) || Validator.isNotNull(displayTerms.getDDMTemplateKey()))) %>">
	<aui:input name="groupId" type="hidden" />
</c:if>

<%
ArticleSearchTerms searchTerms = (ArticleSearchTerms) articleSearchContainer.getSearchTerms();

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
		long userId = 0;

		if (displayTerms.getNavigation().equals("mine")) {
			userId = themeDisplay.getUserId();

			status = WorkflowConstants.STATUS_ANY;
		}

		total = JournalArticleServiceUtil.getGroupArticlesCount(scopeGroupId, userId, folderId, status);

		articleSearchContainer.setTotal(total);

		results = JournalArticleServiceUtil.getGroupArticles(scopeGroupId, userId, folderId, status, articleSearchContainer.getStart(), articleSearchContainer.getEnd(), articleSearchContainer.getOrderByComparator());
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
		total = JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, folderId, status);

		articleSearchContainer.setTotal(total);

		results = JournalFolderServiceUtil.getFoldersAndArticles(scopeGroupId, folderId, status, articleSearchContainer.getStart(), articleSearchContainer.getEnd(), articleSearchContainer.getOrderByComparator());
		%>

	</c:otherwise>
</c:choose>

<%
articleSearchContainer.setResults(results);

request.setAttribute("view.jsp-total", String.valueOf(total));

request.setAttribute("view_entries.jsp-entryStart", String.valueOf(articleSearchContainer.getStart()));
request.setAttribute("view_entries.jsp-entryEnd", String.valueOf(articleSearchContainer.getEnd()));
%>

<div class="subscribe-action">
	<c:if test="<%= JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.SUBSCRIBE) && JournalUtil.getEmailArticleAnyEventEnabled(portletPreferences) %>">

		<%
		boolean subscribed = false;
		boolean unsubscribable = true;

		if (Validator.isNull(displayTerms.getDDMStructureKey())) {
			subscribed = JournalUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId);

			if (subscribed) {
				if (!JournalUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId, false)) {
					unsubscribable = false;
				}
			}
		}
		else {
			subscribed = JournalUtil.isSubscribedToStructure(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), ddmStructureId);
		}
		%>

		<c:choose>
			<c:when test="<%= subscribed %>">
				<c:choose>
					<c:when test="<%= unsubscribable %>">
						<portlet:actionURL var="unsubscribeURL">
							<portlet:param name="struts_action" value='<%= Validator.isNull(displayTerms.getDDMStructureKey()) ? "/journal/edit_folder" : "/journal/edit_article" %>' />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />

							<c:choose>
								<c:when test="<%= Validator.isNull(displayTerms.getDDMStructureKey()) %>">
									<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
								</c:when>
								<c:otherwise>
									<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructureId) %>" />
								</c:otherwise>
							</c:choose>
						</portlet:actionURL>

						<liferay-ui:icon
							iconCssClass="icon-remove-sign"
							label="<%= true %>"
							message="unsubscribe"
							url="<%= unsubscribeURL %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:icon
							iconCssClass="icon-remove-sign"
							label="<%= true %>"
							message="subscribed-to-a-parent-folder"
						/>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<portlet:actionURL var="subscribeURL">
					<portlet:param name="struts_action" value='<%= Validator.isNull(displayTerms.getDDMStructureKey()) ? "/journal/edit_folder" : "/journal/edit_article" %>' />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />

					<c:choose>
						<c:when test="<%= Validator.isNull(displayTerms.getDDMStructureKey()) %>">
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						</c:when>
						<c:otherwise>
							<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructureId) %>" />
						</c:otherwise>
					</c:choose>
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-ok-sign"
					label="<%= true %>"
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>

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

<c:choose>
	<c:when test='<%= !displayStyle.equals("list") %>'>

		<%
		for (int i = 0; i < results.size(); i++) {
			Object result = results.get(i);
		%>

			<%@ include file="/html/portlet/journal/cast_result.jspf" %>

			<c:choose>
				<c:when test="<%= curArticle != null %>">

					<%
					PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

					tempRowURL.setParameter("struts_action", "/journal/edit_article");
					tempRowURL.setParameter("redirect", currentURL);
					tempRowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
					tempRowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
					tempRowURL.setParameter("articleId", curArticle.getArticleId());

					tempRowURL.setParameter("status", String.valueOf(status));

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
				<c:when test="<%= curFolder != null %>">

					<%
					String folderImage = "folder_empty_article";

					if (JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, curFolder.getFolderId()) > 0) {
						folderImage = "folder_full_article";
					}

					PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

					tempRowURL.setParameter("struts_action", "/journal/view");
					tempRowURL.setParameter("redirect", currentURL);
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
			</c:choose>

		<%
		}
		%>

	</c:when>
	<c:otherwise>
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
						row.setClassName("entry-display-style");

						Map<String, Object> rowData = new HashMap<String, Object>();

						rowData.put("draggable", JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE));
						rowData.put("title", HtmlUtil.escape(curArticle.getTitle(locale)));

						row.setData(rowData);

						row.setPrimaryKey(HtmlUtil.escape(curArticle.getArticleId()));
						%>

						<%@ include file="/html/portlet/journal/article_columns.jspf" %>
					</c:when>
					<c:when test="<%= curFolder != null %>">

						<%
						row.setClassName("entry-display-style");

						Map<String, Object> rowData = new HashMap<String, Object>();

						rowData.put("draggable", JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE));
						rowData.put("folder", true);
						rowData.put("folder-id", curFolder.getFolderId());
						rowData.put("title", HtmlUtil.escape(curFolder.getName()));

						row.setData(rowData);
						row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));
						%>

						<%@ include file="/html/portlet/journal/folder_columns.jspf" %>
					</c:when>
				</c:choose>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= articleSearchContainer %>" />
		</liferay-ui:search-container>
	</c:otherwise>
</c:choose>

<div class="article-entries-pagination">
	<liferay-ui:search-paginator searchContainer="<%= articleSearchContainer %>" />
</div>