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
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(kbArticles) && ListUtil.isNotEmpty(kbFolders) && (kbFolders.size() == 1) %>">

		<%
		KBFolder kbFolder = kbFolders.get(0);

		request.setAttribute("info_panel.jsp-kbFolder", kbFolder);
		%>

		<div class="sidebar-header">
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title"><%= (kbFolder != null) ? HtmlUtil.escape(kbFolder.getName()) : LanguageUtil.get(request, "home") %></h4>

					<h5 class="component-subtitle">
						<liferay-ui:message key="folder" />
					</h5>
				</div>

				<div class="autofit-col">
					<ul class="autofit-padded-no-gutters autofit-row">
						<li class="autofit-col">

							<%
							KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse);
							%>

							<clay:dropdown-actions
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
								dropdownItems="<%= kbDropdownItemsProvider.getKBFolderDropdownItems(kbFolder) %>"
								propsTransformer="admin/js/KBDropdownPropsTransformer"
							/>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<%
		KBAdminNavigationDisplayContext kbAdminNavigationDisplayContext = new KBAdminNavigationDisplayContext(request, renderRequest, renderResponse);
		%>

		<clay:navigation-bar
			navigationItems="<%= kbAdminNavigationDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<dl class="sidebar-dl sidebar-section">
				<dt class="sidebar-dt">
					<liferay-ui:message key="num-of-items" />
				</dt>

				<%
				long folderId = KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;

				if (kbFolder != null) {
					folderId = kbFolder.getKbFolderId();
				}
				%>

				<dd class="sidebar-dd">
					<%= KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(scopeGroupId, folderId, WorkflowConstants.STATUS_APPROVED) %>
				</dd>

				<c:if test="<%= kbFolder != null %>">
					<dt class="sidebar-dt">
						<liferay-ui:message key="created" />
					</dt>
					<dd class="sidebar-dd">
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
			<clay:content-row
				cssClass="sidebar-section"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<clay:content-section>
						<h4 class="component-title"><%= HtmlUtil.escape(kbArticle.getTitle()) %></h4>

						<clay:label
							displayType="info"
							label='<%= LanguageUtil.get(request, "version") + StringPool.SPACE + kbArticle.getVersion() %>'
						/>

						<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= kbArticle.getStatus() %>" />
					</clay:content-section>
				</clay:content-col>

				<clay:content-col>
					<c:if test='<%= ParamUtil.getBoolean(request, "showSidebarHeader", GetterUtil.getBoolean(request.getAttribute(KBWebKeys.SHOW_SIDEBAR_HEADER))) %>'>
						<ul class="autofit-padded-no-gutters autofit-row">
							<li class="autofit-col">
								<liferay-util:include page="/admin/subscribe.jsp" servletContext="<%= application %>" />
							</li>
							<li class="autofit-col">

								<%
								KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse);
								%>

								<clay:dropdown-actions
									aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
									dropdownItems="<%= kbDropdownItemsProvider.getKBArticleDropdownItems(kbArticle) %>"
									propsTransformer="admin/js/KBDropdownPropsTransformer"
								/>
							</li>
						</ul>
					</c:if>
				</clay:content-col>
			</clay:content-row>
		</div>

		<div class="sidebar-body">
			<liferay-ui:tabs
				cssClass="navbar-no-collapse"
				names="details,versions"
				refresh="<%= false %>"
			>
				<liferay-ui:section>
					<dl class="sidebar-dl sidebar-section">
						<dt class="sidebar-dt">
							<liferay-ui:message key="title" />
						</dt>
						<dd class="sidebar-dd">
							<%= HtmlUtil.escape(kbArticle.getTitle()) %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="author" />
						</dt>
						<dd class="sidebar-dd">
							<%= HtmlUtil.escape(kbArticle.getUserName()) %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="status" />
						</dt>
						<dd class="sidebar-dd">
							<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(kbArticle.getStatus()) %>" />
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="priority" />
						</dt>
						<dd class="sidebar-dd">
							<%= kbArticle.getPriority() %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="create-date" />
						</dt>
						<dd class="sidebar-dd">
							<%= dateFormatDateTime.format(kbArticle.getCreateDate()) %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="modified-date" />
						</dt>
						<dd class="sidebar-dd">
							<%= dateFormatDateTime.format(kbArticle.getModifiedDate()) %>
						</dd>
						<dt class="sidebar-dt">
							<liferay-ui:message key="views" />
						</dt>
						<dd class="sidebar-dd">
							<%= kbArticle.getViewCount() %>
						</dd>
					</dl>
				</liferay-ui:section>

				<liferay-ui:section>
					<liferay-util:include page="/admin/common/kb_article_history.jsp" servletContext="<%= application %>" />
				</liferay-ui:section>
			</liferay-ui:tabs>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title"><liferay-ui:message arguments="<%= kbFolders.size() + kbArticles.size() %>" key="x-items-are-selected" /></h4>
				</div>
			</div>
		</div>

		<%
		KBAdminNavigationDisplayContext kbAdminNavigationDisplayContext = new KBAdminNavigationDisplayContext(request, renderRequest, renderResponse);
		%>

		<clay:navigation-bar
			navigationItems="<%= kbAdminNavigationDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= kbFolders.size() + kbArticles.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:otherwise>
</c:choose>