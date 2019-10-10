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

	AssetEntryServiceUtil.incrementViewCounter(latestKBArticle.getCompanyId(), KBArticle.class.getName(), latestKBArticle.getClassPK());
}

boolean enableKBArticleSuggestions = enableKBArticleRatings && kbArticle.isApproved();

if (enableKBArticleRatings && kbArticle.isDraft()) {
	KBArticle latestKBArticle = KBArticleServiceUtil.fetchLatestKBArticle(kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_APPROVED);

	if (latestKBArticle != null) {
		enableKBArticleSuggestions = true;
	}
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);
	renderResponse.setTitle(kbArticle.getTitle());
}
%>

<c:if test="<%= portletTitleBasedNavigation %>">
	<liferay-frontend:info-bar>
		<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= kbArticle.getStatus() %>" version="<%= String.valueOf(kbArticle.getVersion()) %>" />

		<liferay-frontend:info-bar-buttons>
			<liferay-frontend:info-bar-sidenav-toggler-button
				icon="info-circle-open"
				label="info"
			/>
		</liferay-frontend:info-bar-buttons>
	</liferay-frontend:info-bar>
</c:if>

<div <%= portletTitleBasedNavigation ? "class=\"closed container-fluid-1280 kb-article sidenav-container sidenav-right\" id=\"" + liferayPortletResponse.getNamespace() + "infoPanelId\"" : StringPool.BLANK %>>
	<c:if test="<%= portletTitleBasedNavigation %>">
		<liferay-frontend:sidebar-panel>

			<%
			List<KBArticle> kbArticles = new ArrayList<KBArticle>();

			kbArticles.add(kbArticle);

			request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLES, kbArticles);
			%>

			<liferay-util:include page="/admin/info_panel.jsp" servletContext="<%= application %>" />
		</liferay-frontend:sidebar-panel>
	</c:if>

	<div class="sidenav-content">
		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				title="<%= kbArticle.getTitle() %>"
			/>
		</c:if>

		<div class="kb-tools">
			<liferay-util:include page="/admin/common/article_tools.jsp" servletContext="<%= application %>" />
		</div>

		<div <%= portletTitleBasedNavigation ? "class=\"main-content-card panel\"" : StringPool.BLANK %>>
			<div class="kb-entity-body <%= portletTitleBasedNavigation ? "panel-body" : StringPool.BLANK %>">
				<c:if test="<%= portletTitleBasedNavigation %>">
					<h1>
						<%= HtmlUtil.escape(kbArticle.getTitle()) %>
					</h1>
				</c:if>

				<div id="<portlet:namespace /><%= kbArticle.getResourcePrimKey() %>">
					<%= kbArticle.getContent() %>
				</div>

				<liferay-util:include page="/admin/common/article_social_bookmarks.jsp" servletContext="<%= application %>" />

				<liferay-expando:custom-attributes-available
					className="<%= KBArticle.class.getName() %>"
				>
					<liferay-expando:custom-attribute-list
						className="<%= KBArticle.class.getName() %>"
						classPK="<%= kbArticle.getKbArticleId() %>"
						editable="<%= false %>"
						label="<%= true %>"
					/>
				</liferay-expando:custom-attributes-available>

				<liferay-util:include page="/admin/common/article_assets.jsp" servletContext="<%= application %>" />

				<c:if test="<%= showKBArticleAttachments %>">
					<liferay-util:include page="/admin/common/article_attachments.jsp" servletContext="<%= application %>" />
				</c:if>

				<liferay-util:include page="/admin/common/article_asset_links.jsp" servletContext="<%= application %>" />

				<c:if test="<%= !portletTitleBasedNavigation %>">
					<liferay-util:include page="/admin/common/article_asset_entries.jsp" servletContext="<%= application %>" />
				</c:if>

				<c:if test="<%= enableKBArticleRatings %>">
					<div class="kb-article-ratings">
						<liferay-ui:ratings
							className="<%= KBArticle.class.getName() %>"
							classPK="<%= kbArticle.getResourcePrimKey() %>"
							inTrash="<%= false %>"
						/>
					</div>
				</c:if>

				<c:if test="<%= !portletTitleBasedNavigation && !rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE) %>">
					<liferay-util:include page="/admin/common/article_siblings.jsp" servletContext="<%= application %>" />
				</c:if>
			</div>

			<c:if test="<%= enableKBArticleSuggestions %>">
				<c:choose>
					<c:when test="<%= portletTitleBasedNavigation %>">
						<liferay-ui:panel-container
							extended="<%= false %>"
							markupView="lexicon"
							persistState="<%= true %>"
						>
							<liferay-ui:panel
								collapsible="<%= true %>"
								extended="<%= false %>"
								markupView="lexicon"
								persistState="<%= true %>"
								title="suggestions"
							>
								<liferay-util:include page="/admin/common/article_suggestions.jsp" servletContext="<%= application %>" />
							</liferay-ui:panel>
						</liferay-ui:panel-container>
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/admin/common/article_suggestions.jsp" servletContext="<%= application %>" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>

		<liferay-util:include page="/admin/common/article_child.jsp" servletContext="<%= application %>" />
	</div>
</div>

<%
List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(KBArticle.class.getName(), kbArticle.getClassPK());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);
%>