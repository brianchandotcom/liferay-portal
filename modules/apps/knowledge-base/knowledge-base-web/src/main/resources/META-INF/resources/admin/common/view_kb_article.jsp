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
String backURL = ParamUtil.getString(request, "backURL");

KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

if (enableKBArticleViewCountIncrement && kbArticle.isApproved()) {
	KBArticle latestKBArticle = KBArticleLocalServiceUtil.getLatestKBArticle(kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_APPROVED);

	KBArticleLocalServiceUtil.incrementViewCount(themeDisplay.getUserId(), kbArticle.getResourcePrimKey(), 1);

	AssetEntryServiceUtil.incrementViewCounter(latestKBArticle.getCompanyId(), KBArticle.class.getName(), latestKBArticle.getClassPK());
}

boolean enableKBArticleSuggestions = enableKBArticleRatings && kbArticle.isApproved();

if (enableKBArticleRatings && kbArticle.isDraft()) {
	KBArticle latestKBArticle = KBArticleServiceUtil.fetchLatestKBArticle(kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_APPROVED);

	if (latestKBArticle != null) {
		enableKBArticleSuggestions = true;
	}
}

if (Validator.isNotNull(backURL)) {
	portletDisplay.setURLBack(backURL);
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);
	renderResponse.setTitle(kbArticle.getTitle());
}
%>

<c:if test="<%= portletTitleBasedNavigation %>">
	<div class="management-bar management-bar-light navbar navbar-expand-md">
		<clay:container-fluid>
			<ul class="navbar-nav">
				<li class="nav-item">
					<liferay-frontend:info-bar-sidenav-toggler-button
						icon="info-circle-open"
						label="info"
					/>
				</li>
			</ul>

			<ul class="middle navbar-nav">
				<li class="nav-item">
					<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= kbArticle.getStatus() %>" version="<%= String.valueOf(kbArticle.getVersion()) %>" />
				</li>
			</ul>

			<ul class="end m-auto navbar-nav"></ul>
		</clay:container-fluid>
	</div>
</c:if>

<div <%= portletTitleBasedNavigation ? "class=\"closed kb-article sidenav-container sidenav-right\" id=\"" + liferayPortletResponse.getNamespace() + "infoPanelId\"" : StringPool.BLANK %>>
	<c:if test="<%= portletTitleBasedNavigation %>">
		<liferay-frontend:sidebar-panel>

			<%
			request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLES, ListUtil.fromArray(kbArticle));
			%>

			<liferay-util:include page="/admin/info_panel.jsp" servletContext="<%= application %>" />
		</liferay-frontend:sidebar-panel>
	</c:if>

	<div class="sidenav-content <%= portletTitleBasedNavigation ? "container-fluid container-fluid-max-xl container-form-lg" : StringPool.BLANK %>">
		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				title="<%= kbArticle.getTitle() %>"
			/>
		</c:if>

		<div class="kb-tools">
			<liferay-util:include page="/admin/common/kb_article_tools.jsp" servletContext="<%= application %>" />
		</div>

		<div <%= portletTitleBasedNavigation ? "class=\"panel\"" : StringPool.BLANK %>>
			<div class="kb-entity-body <%= portletTitleBasedNavigation ? "panel-body" : StringPool.BLANK %>">
				<c:if test="<%= portletTitleBasedNavigation %>">
					<h1>
						<%= HtmlUtil.escape(kbArticle.getTitle()) %>
					</h1>
				</c:if>

				<div id="<portlet:namespace /><%= kbArticle.getResourcePrimKey() %>">
					<%= kbArticle.getContent() %>
				</div>

				<liferay-util:include page="/admin/common/kb_article_social_bookmarks.jsp" servletContext="<%= application %>" />

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

				<liferay-util:include page="/admin/common/kb_article_assets.jsp" servletContext="<%= application %>" />

				<c:if test="<%= showKBArticleAttachments %>">
					<liferay-util:include page="/admin/common/kb_article_attachments.jsp" servletContext="<%= application %>" />
				</c:if>

				<liferay-util:include page="/admin/common/kb_article_asset_links.jsp" servletContext="<%= application %>" />

				<c:if test="<%= !portletTitleBasedNavigation %>">
					<liferay-util:include page="/admin/common/kb_article_asset_entries.jsp" servletContext="<%= application %>" />
				</c:if>

				<c:if test="<%= enableKBArticleRatings %>">
					<div class="kb-article-ratings">
						<liferay-ratings:ratings
							className="<%= KBArticle.class.getName() %>"
							classPK="<%= kbArticle.getResourcePrimKey() %>"
							inTrash="<%= false %>"
						/>
					</div>
				</c:if>

				<c:if test="<%= !portletTitleBasedNavigation && !rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE) %>">
					<liferay-util:include page="/admin/common/kb_article_siblings.jsp" servletContext="<%= application %>" />
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
								<liferay-util:include page="/admin/common/kb_article_suggestions.jsp" servletContext="<%= application %>" />
							</liferay-ui:panel>
						</liferay-ui:panel-container>
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/admin/common/kb_article_suggestions.jsp" servletContext="<%= application %>" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>

		<liferay-util:include page="/admin/common/kb_article_child.jsp" servletContext="<%= application %>" />
	</div>
</div>

<%
List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(KBArticle.class.getName(), kbArticle.getClassPK());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);
%>