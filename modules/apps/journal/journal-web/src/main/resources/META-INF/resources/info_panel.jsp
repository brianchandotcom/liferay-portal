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
List<Long> folders = ListUtil.toList(ParamUtil.getLongValues(request, "rowIdsJournalFolder"));
List<String> entries = ListUtil.toList(ParamUtil.getStringValues(request, "rowIdsJournalArticle"));

if (ListUtil.isEmpty(folders) && ListUtil.isEmpty(entries)) {
	long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"), ParamUtil.getLong(request, "folderId"));

	folders = new ArrayList<Long>();

	JournalFolder folder = (JournalFolder)request.getAttribute("view.jsp-folder");

	if (folder != null) {
		folders.add(folder.getFolderId());
	}
	else if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		folders.add(folderId);
	}
	else {
		folders.add(null);
	}
}
%>

<c:choose>
	<c:when test="<%= (ListUtil.isEmpty(entries) && ListUtil.isNotEmpty(folders) && (folders.size() == 1)) %>">

		<%
		Long folderId = folders.get(0);

		if (folderId == null) {
			folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		JournalFolder folder = JournalFolderServiceUtil.fetchFolder(folderId);

		request.setAttribute("info_panel.jsp-folder", folder);
		%>

		<div class="sidebar-header">
			<c:if test="<%= journalDisplayContext.isShowEditActions() %>">
				<ul class="list-inline list-unstyled sidebar-header-actions">
					<li>
						<liferay-util:include page="/subscribe.jsp" servletContext="<%= application %>" />
					</li>

					<li>
						<liferay-util:include page="/folder_action.jsp" servletContext="<%= application %>" />
					</li>
				</ul>
			</c:if>

			<h4><%= (folder != null) ? folder.getName() : LanguageUtil.get(request, "home") %></h4>

			<div>
				<liferay-ui:message key="folder" />
			</div>
		</div>

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="num-of-items" /></h5>

			<p>
				<%= JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, folderId, journalDisplayContext.getStatus()) %>
			</p>

			<c:if test="<%= folder != null %>">
				<h5><liferay-ui:message key="created" /></h5>

				<p>
					<%= HtmlUtil.escape(folder.getUserName()) %>
				</p>
			</c:if>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isEmpty(folders) && ListUtil.isNotEmpty(entries) && (entries.size() == 1) %>">
		<%
		String articleId = entries.get(0);

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(scopeGroupId, articleId);

		long classPK = JournalArticleAssetRenderer.getClassPK(article);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), classPK);

		DDMStructure ddmStructure = article.getDDMStructure();

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(scopeGroupId, PortalUtil.getClassNameId(DDMStructure.class), article.getDDMTemplateKey(), true);
		%>

		<div class="sidebar-header">
			<h4><%= assetEntry.getTitle(locale) %></h4>

			<div>
				<liferay-ui:message key="entry" />
			</div>
		</div>

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= article.getArticleId() %>
			</p>

			<h5><liferay-ui:message key="version" /></h5>

			<p>
				<%= article.getVersion() %>
			</p>

			<h5><liferay-ui:message key="status" /></h5>

			<p>
				<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" />
			</p>

			<h5><liferay-ui:message key="title" /></h5>

			<p>
				<%= article.getTitle(locale) %>
			</p>

			<c:if test="<%= ddmStructure != null %>">
				<h5><liferay-ui:message key="structure" /></h5>

				<p>
					<%= ddmStructure.getName(locale) %>
				</p>
			</c:if>

			<c:if test="<%= ddmTemplate != null %>">
				<h5><liferay-ui:message key="template" /></h5>

				<p>
					<%= ddmTemplate.getName(locale) %>
				</p>
			</c:if>

			<div class="lfr-asset-tags">
				<liferay-ui:asset-tags-summary
				className="<%= JournalArticle.class.getName() %>"
				classPK="<%= classPK %>"
				message="tags"
				/>
			</div>

			<h5><liferay-ui:message key="priority" /></h5>

			<p>
				<%= assetEntry.getPriority() %>
			</p>

			<%
				Date expirationDate = article.getExpirationDate();

				Date reviewDate = article.getReviewDate();
			%>

			<h5><liferay-ui:message key="display-date" /></h5>

			<p>
				<%= dateFormatDateTime.format(article.getDisplayDate()) %>
			</p>

			<h5><liferay-ui:message key="expiration-date" /></h5>

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

			<h5><liferay-ui:message key="review-date" /></h5>

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
			<h4><liferay-ui:message arguments="<%= folders.size() + entries.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= folders.size() + entries.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:otherwise>
</c:choose>