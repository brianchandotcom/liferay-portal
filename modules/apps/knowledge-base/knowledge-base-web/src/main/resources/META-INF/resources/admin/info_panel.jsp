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

<%@ include file="/admin/init.jsp" %>

<%
long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long parentResourceClassNameId = ParamUtil.getLong(request, "parentResourceClassNameId", kbFolderClassNameId);
long parentResourcePrimKey = ParamUtil.getLong(request, "parentResourcePrimKey", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

List<KBFolder> kbFolders = (List<KBFolder>)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_FOLDERS);
List<KBArticle> kbArticles = (List<KBArticle>)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLES);

if (ListUtil.isEmpty(kbFolders) && ListUtil.isEmpty(kbArticles)) {
	if (parentResourceClassNameId == kbFolderClassNameId) {
		kbFolders = new ArrayList<KBFolder>();

		if (parentResourcePrimKey != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			kbFolders.add(KBFolderServiceUtil.getKBFolder(parentResourcePrimKey));
		}
		else {
			kbFolders.add(null);
		}
	}
	else {
		kbArticles = new ArrayList<KBArticle>();

		kbArticles.add(KBArticleServiceUtil.getLatestKBArticle(parentResourcePrimKey, WorkflowConstants.STATUS_ANY));
	}
}

boolean showSidebarHeader = ParamUtil.getBoolean(request, "showSidebarHeader", GetterUtil.getBoolean(request.getAttribute(KBWebKeys.SHOW_SIDEBAR_HEADER)));
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(kbArticles) && ListUtil.isNotEmpty(kbFolders) && (kbFolders.size() == 1) %>">

		<%
		KBFolder kbFolder = kbFolders.get(0);

		request.setAttribute("info_panel.jsp-kbFolder", kbFolder);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-actions">
				<li>
					<liferay-util:include page="/admin/folder_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4 class="sidebar-title"><%= (kbFolder != null) ? HtmlUtil.escape(kbFolder.getName()) : LanguageUtil.get(request, "home") %></h4>

			<h5>
				<liferay-ui:message key="folder" />
			</h5>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<dl class="sidebar-block">
				<dt class="h5">
					<liferay-ui:message key="num-of-items" />
				</dt>

				<%
				long folderId = KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;

				if (kbFolder != null) {
					folderId = kbFolder.getKbFolderId();
				}
				%>

				<dd class="h6 sidebar-caption">
					<%= KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(scopeGroupId, folderId, WorkflowConstants.STATUS_APPROVED) %>
				</dd>

				<c:if test="<%= kbFolder != null %>">
					<dt class="h5">
						<liferay-ui:message key="created" />
					</dt>
					<dd class="h6 sidebar-caption">
						<%= HtmlUtil.escape(kbFolder.getUserName()) %>
					</dd>
				</c:if>
			</dl>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isEmpty(kbFolders) && ListUtil.isNotEmpty(kbArticles) && (kbArticles.size() == 1) %>">

		<%
		KBArticle kbArticle = kbArticles.get(0);

		request.setAttribute("info_panel.jsp-kbArticle", kbArticle);
		%>

		<div class="sidebar-header">
			<c:if test="<%= showSidebarHeader %>">
				<ul class="sidebar-actions">
					<li>
						<liferay-util:include page="/admin/subscribe.jsp" servletContext="<%= application %>" />
					</li>
					<li>
						<liferay-util:include page="/admin/article_action.jsp" servletContext="<%= application %>" />
					</li>
				</ul>
			</c:if>

			<h4 class="sidebar-title"><%= HtmlUtil.escape(kbArticle.getTitle()) %></h4>

			<h5>
				<liferay-ui:message key="entry" />
			</h5>
		</div>

		<liferay-ui:tabs cssClass="navbar-no-collapse" names="details,versions" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">
					<dl class="sidebar-block">
						<dt class="h5">
							<liferay-ui:message key="title" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(kbArticle.getTitle()) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="author" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(kbArticle.getUserName()) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="status" />
						</dt>
						<dd class="h6 sidebar-caption">
							<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(kbArticle.getStatus()) %>" />
						</dd>
						<dt class="h5">
							<liferay-ui:message key="priority" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= kbArticle.getPriority() %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="create-date" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= dateFormatDateTime.format(kbArticle.getCreateDate()) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="modified-date" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= dateFormatDateTime.format(kbArticle.getModifiedDate()) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="views" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= kbArticle.getViewCount() %>
						</dd>
					</dl>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="sidebar-body">
					<liferay-util:include page="/admin/common/article_history.jsp" servletContext="<%= application %>" />
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4 class="sidebar-title"><liferay-ui:message arguments="<%= kbFolders.size() + kbArticles.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= kbFolders.size() + kbArticles.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:otherwise>
</c:choose>