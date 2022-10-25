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

Map<String, Object> componentContext = journalDisplayContext.getComponentContext();
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(articles) && ListUtil.isNotEmpty(folders) && (folders.size() == 1) %>">

		<%
		JournalFolder folder = folders.get(0);

		request.setAttribute("info_panel.jsp-folder", folder);
		%>

		<div class="sidebar-header">
			<clay:content-row
				cssClass="sidebar-section"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h1 class="component-title"><%= (folder != null) ? HtmlUtil.escape(folder.getName()) : LanguageUtil.get(request, "home") %></h1>

					<h2 class="component-subtitle">
						<liferay-ui:message key="folder" />
					</h2>
				</clay:content-col>

				<clay:content-col>
					<ul class="autofit-padded-no-gutters autofit-row">
						<li class="autofit-col">
							<liferay-util:include page="/subscribe.jsp" servletContext="<%= application %>" />
						</li>
						<li class="autofit-col">
							<clay:dropdown-actions
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
								dropdownItems="<%= journalDisplayContext.getFolderInfoPanelDropdownItems(folder) %>"
								propsTransformer="js/ElementsDefaultPropsTransformer"
							/>
						</li>
					</ul>
				</clay:content-col>
			</clay:content-row>
		</div>

		<clay:navigation-bar
			navigationItems="<%= journalDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<p class="sidebar-dt"><liferay-ui:message key="num-of-items" /></p>

			<%
			long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			if (folder != null) {
				folderId = folder.getFolderId();
			}
			%>

			<p class="sidebar-dd">
				<%= JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, folderId, journalDisplayContext.getStatus()) %>
			</p>

			<c:if test="<%= folder != null %>">
				<p class="sidebar-dt"><liferay-ui:message key="created" /></p>

				<p class="sidebar-dd">
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
			<clay:content-row
				cssClass="sidebar-section"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h1 class="component-title"><%= HtmlUtil.escape(article.getTitle(locale)) %></h1>

					<%
					DDMStructure ddmStructure = article.getDDMStructure();
					%>

					<h2 class="component-subtitle">
						<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>
					</h2>
				</clay:content-col>

				<clay:content-col>
					<ul class="autofit-padded-no-gutters autofit-row">
						<li class="autofit-col">
							<liferay-util:include page="/subscribe.jsp" servletContext="<%= application %>" />
						</li>
						<li class="autofit-col">
							<clay:dropdown-actions
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
								dropdownItems="<%= journalDisplayContext.getArticleInfoPanelDropdownItems(article) %>"
								propsTransformer="js/ElementsDefaultPropsTransformer"
							/>
						</li>
					</ul>
				</clay:content-col>
			</clay:content-row>
		</div>

		<clay:navigation-bar
			navigationItems="<%= journalDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<p class="sidebar-dt"><liferay-ui:message key="id" /></p>

			<p class="sidebar-dd">
				<%= HtmlUtil.escape(article.getArticleId()) %>
			</p>

			<p class="sidebar-dt"><liferay-ui:message key="version" /></p>

			<p class="sidebar-dd">
				<%= article.getVersion() %>
			</p>

			<p class="sidebar-dt"><liferay-ui:message key="status" /></p>

			<p class="sidebar-dd">
				<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" />
			</p>

			<p class="sidebar-dt"><liferay-ui:message key="title" /></p>

			<p class="sidebar-dd">
				<%= HtmlUtil.escape(article.getTitle(locale)) %>
			</p>

			<%
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(scopeGroupId, PortalUtil.getClassNameId(DDMStructure.class), article.getDDMTemplateKey(), true);
			%>

			<p class="sidebar-dt"><liferay-ui:message key="template" /></p>

			<p class="sidebar-dd">
				<c:choose>
					<c:when test="<%= ddmTemplate != null %>">
						<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="no-template" />
					</c:otherwise>
				</c:choose>
			</p>

			<div class="lfr-asset-tags sidebar-dd">
				<liferay-asset:asset-tags-summary
					className="<%= JournalArticle.class.getName() %>"
					classPK="<%= JournalArticleAssetRenderer.getClassPK(article) %>"
					message="tags"
				/>
			</div>

			<p class="sidebar-dt"><liferay-ui:message key="original-author" /></p>

			<p class="sidebar-dd">
				<%= HtmlUtil.escape(journalDisplayContext.getOriginalAuthorUserName(article)) %>
			</p>

			<p class="sidebar-dt"><liferay-ui:message key="priority" /></p>

			<%
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), JournalArticleAssetRenderer.getClassPK(article));
			%>

			<p class="sidebar-dd">
				<%= assetEntry.getPriority() %>
			</p>

			<c:if test="<%= article.getDisplayDate() != null %>">
				<p class="sidebar-dt"><liferay-ui:message key="display-date" /></p>

				<p class="sidebar-dd">
					<%= dateFormatDateTime.format(article.getDisplayDate()) %>
				</p>
			</c:if>

			<p class="sidebar-dt"><liferay-ui:message key="expiration-date" /></p>

			<%
			Date expirationDate = article.getExpirationDate();
			%>

			<p class="sidebar-dd">
				<c:choose>
					<c:when test="<%= expirationDate != null %>">
						<%= dateFormatDateTime.format(expirationDate) %>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="never-expire" />
					</c:otherwise>
				</c:choose>
			</p>

			<p class="sidebar-dt"><liferay-ui:message key="review-date" /></p>

			<%
			Date reviewDate = article.getReviewDate();
			%>

			<p class="sidebar-dd">
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
			<clay:content-row
				cssClass="sidebar-section"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h1 class="component-title"><liferay-ui:message arguments="<%= folders.size() + articles.size() %>" key="x-items-are-selected" /></h1>
				</clay:content-col>
			</clay:content-row>
		</div>

		<clay:navigation-bar
			navigationItems="<%= journalDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<p class="sidebar-dt"><liferay-ui:message arguments="<%= folders.size() + articles.size() %>" key="x-items-are-selected" /></p>
		</div>
	</c:otherwise>
</c:choose>