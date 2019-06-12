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
List<JournalFolder> folders = (List<JournalFolder>)request.getAttribute(JournalWebKeys.JOURNAL_FOLDERS);
List<JournalArticle> articles = (List<JournalArticle>)request.getAttribute(JournalWebKeys.JOURNAL_ARTICLES);

if (ListUtil.isEmpty(folders) && ListUtil.isEmpty(articles)) {
	long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"), ParamUtil.getLong(request, "folderId"));

	folders = new ArrayList<JournalFolder>();

	JournalFolder folder = (JournalFolder)request.getAttribute("view.jsp-folder");

	if (folder != null) {
		folders.add(folder);
	}
	else if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		folders.add(JournalFolderLocalServiceUtil.fetchFolder(folderId));
	}
	else {
		folders.add(null);
	}
}
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(articles) && ListUtil.isNotEmpty(folders) && (folders.size() == 1) %>">

		<%
		JournalFolder folder = folders.get(0);

		request.setAttribute("info_panel.jsp-folder", folder);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/subscribe.jsp" servletContext="<%= application %>" />
				</li>
				<li>
					<clay:dropdown-actions
						defaultEventHandler="<%= JournalWebConstants.JOURNAL_INFO_PANEL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
						dropdownItems="<%= journalDisplayContext.getFolderInfoPanelDropdownItems(folder) %>"
					/>
				</li>
			</ul>

			<p class="h4 pt-2"><%= (folder != null) ? HtmlUtil.escape(folder.getName()) : LanguageUtil.get(request, "home") %></p>

			<p class="h6 text-default">
				<liferay-ui:message key="folder" />
			</p>
		</div>

		<clay:navigation-bar
			navigationItems="<%= journalDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<p class="h5"><liferay-ui:message key="num-of-items" /></p>

			<%
			long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			if (folder != null) {
				folderId = folder.getFolderId();
			}
			%>

			<p>
				<%= JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, folderId, journalDisplayContext.getStatus()) %>
			</p>

			<c:if test="<%= folder != null %>">
				<p class="h5"><liferay-ui:message key="created" /></p>

				<p>
					<%= HtmlUtil.escape(folder.getUserName()) %>
				</p>
			</c:if>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isEmpty(folders) && ListUtil.isNotEmpty(articles) && (articles.size() == 1) %>">

		<%
		JournalArticle article = articles.get(0);

		request.setAttribute("info_panel.jsp-entry", article);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/subscribe.jsp" servletContext="<%= application %>" />
				</li>
				<li>
					<clay:dropdown-actions
						defaultEventHandler="<%= JournalWebConstants.JOURNAL_INFO_PANEL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
						dropdownItems="<%= journalDisplayContext.getArticleInfoPanelDropdownItems(article) %>"
					/>
				</li>
			</ul>

			<p class="h4 pt-2"><%= HtmlUtil.escape(article.getTitle(locale)) %></p>

			<%
			DDMStructure ddmStructure = article.getDDMStructure();
			%>

			<p class="h6 text-default">
				<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>
			</p>
		</div>

		<clay:navigation-bar
			navigationItems="<%= journalDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<p class="h5"><liferay-ui:message key="id" /></p>

			<p>
				<%= HtmlUtil.escape(article.getArticleId()) %>
			</p>

			<p class="h5"><liferay-ui:message key="version" /></p>

			<p>
				<%= article.getVersion() %>
			</p>

			<p class="h5"><liferay-ui:message key="status" /></p>

			<p>
				<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" />
			</p>

			<p class="h5"><liferay-ui:message key="title" /></p>

			<p>
				<%= HtmlUtil.escape(article.getTitle(locale)) %>
			</p>

			<%
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(scopeGroupId, PortalUtil.getClassNameId(DDMStructure.class), article.getDDMTemplateKey(), true);
			%>

			<p class="h5"><liferay-ui:message key="template" /></p>

			<p>
				<c:choose>
					<c:when test="<%= ddmTemplate != null %>">
						<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="no-template" />
					</c:otherwise>
				</c:choose>
			</p>

			<div class="lfr-asset-tags">
				<liferay-asset:asset-tags-summary
					className="<%= JournalArticle.class.getName() %>"
					classPK="<%= JournalArticleAssetRenderer.getClassPK(article) %>"
					message="tags"
				/>
			</div>

			<p class="h5"><liferay-ui:message key="original-author" /></p>

			<p>
				<%= HtmlUtil.escape(journalDisplayContext.getOriginalAuthor(article)) %>
			</p>

			<p class="h5"><liferay-ui:message key="priority" /></p>

			<%
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), JournalArticleAssetRenderer.getClassPK(article));
			%>

			<p>
				<%= assetEntry.getPriority() %>
			</p>

			<c:if test="<%= article.getDisplayDate() != null %>">
				<p class="h5"><liferay-ui:message key="display-date" /></p>

				<p>
					<%= dateFormatDateTime.format(article.getDisplayDate()) %>
				</p>
			</c:if>

			<p class="h5"><liferay-ui:message key="expiration-date" /></p>

			<%
			Date expirationDate = article.getExpirationDate();
			%>

			<p>
				<c:choose>
					<c:when test="<%= expirationDate != null %>">
						<%= dateFormatDateTime.format(expirationDate) %>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="never-expire" />
					</c:otherwise>
				</c:choose>
			</p>

			<p class="h5"><liferay-ui:message key="review-date" /></p>

			<%
			Date reviewDate = article.getReviewDate();
			%>

			<p>
				<c:choose>
					<c:when test="<%= reviewDate != null %>">
						<%= dateFormatDateTime.format(reviewDate) %>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="never-review" />
					</c:otherwise>
				</c:choose>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<p class="h4 pt-2"><liferay-ui:message arguments="<%= folders.size() + articles.size() %>" key="x-items-are-selected" /></p>
		</div>

		<clay:navigation-bar
			navigationItems="<%= journalDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<p class="h5"><liferay-ui:message arguments="<%= folders.size() + articles.size() %>" key="x-items-are-selected" /></p>
		</div>
	</c:otherwise>
</c:choose>

<liferay-frontend:component
	componentId="<%= JournalWebConstants.JOURNAL_INFO_PANEL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
	context="<%= journalDisplayContext.getComponentContext() %>"
	module="js/ElementsDefaultEventHandler.es"
/>