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

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

if (enableKBArticleViewCountIncrement && kbArticle.isApproved()) {
	KBArticle latestKBArticle = KBArticleLocalServiceUtil.getLatestKBArticle(kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_APPROVED);

	KBArticleLocalServiceUtil.updateViewCount(themeDisplay.getUserId(), kbArticle.getResourcePrimKey(), latestKBArticle.getViewCount() + 1);

	AssetEntryServiceUtil.incrementViewCounter(KBArticle.class.getName(), latestKBArticle.getClassPK());
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(kbArticle.getTitle());

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));
%>

<c:if test="<%= portletTitleBasedNavigation %>">
	<liferay-frontend:info-bar>
		<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= kbArticle.getStatus() %>" version="<%= String.valueOf(kbArticle.getVersion()) %>" />

		<liferay-frontend:info-bar-buttons>
			<liferay-frontend:info-bar-sidenav-toggler-button
					icon="info-circle"
					label="info"
			/>
		</liferay-frontend:info-bar-buttons>
	</liferay-frontend:info-bar>
</c:if>

<div <%= portletTitleBasedNavigation ? "class=\"closed container-fluid-1280 kb-article sidenav-container sidenav-right\" id=\"" + liferayPortletResponse.getNamespace() + "infoPanelId\"" : StringPool.BLANK %>>
	<c:if test="<%= portletTitleBasedNavigation %>">
		<liferay-frontend:sidebar-panel>
			<liferay-util:include page="/admin/common/article_info_panel.jsp" servletContext="<%= application %>" />
		</liferay-frontend:sidebar-panel>
	</c:if>

	<div class="sidenav-content">
		<c:if test='<%= enableSocialBookmarks && socialBookmarksDisplayPosition.equals("top") %>'>
			<liferay-util:include page="/admin/common/article_social_bookmarks.jsp" servletContext="<%= application %>" />
		</c:if>

		<div class="kb-tools">
			<liferay-util:include page="/admin/common/article_tools.jsp" servletContext="<%= application %>" />
		</div>

		<div class="main-content-card panel">
			<div class="panel-body text-default">
				<h1>
					<%= HtmlUtil.escape(kbArticle.getTitle()) %>
				</h1>

				<%
				request.setAttribute("article_icons.jsp-kb_article", kbArticle);
				%>

				<c:if test="<%= !rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN) %>">
					<liferay-util:include page="/admin/common/article_icons.jsp" servletContext="<%= application %>" />
				</c:if>

				<div id="<portlet:namespace /><%= kbArticle.getResourcePrimKey() %>">
					<%= kbArticle.getContent() %>
				</div>

				<c:if test='<%= enableSocialBookmarks && socialBookmarksDisplayPosition.equals("bottom") %>'>
					<liferay-util:include page="/admin/common/article_social_bookmarks.jsp" servletContext="<%= application %>" />
				</c:if>

				<liferay-util:include page="/admin/common/article_assets.jsp" servletContext="<%= application %>" />

				<c:if test="<%= showKBArticleAttachments %>">
					<liferay-util:include page="/admin/common/article_attachments.jsp" servletContext="<%= application %>" />
				</c:if>

				<liferay-util:include page="/admin/common/article_asset_links.jsp" servletContext="<%= application %>" />

				<c:if test="<%= !portletTitleBasedNavigation %>">
					<liferay-util:include page="/admin/common/article_asset_entries.jsp" servletContext="<%= application %>" />
				</c:if>

				<c:if test="<%= enableKBArticleRatings %>">
					<liferay-util:include page="/admin/common/article_ratings.jsp" servletContext="<%= application %>" />
				</c:if>

				<c:if test="<%= !portletTitleBasedNavigation && !rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE) %>">
					<liferay-util:include page="/admin/common/article_siblings.jsp" servletContext="<%= application %>" />
				</c:if>
			</div>
		</div>

		<liferay-util:include page="/admin/common/article_child.jsp" servletContext="<%= application %>" />
	</div>
</div>