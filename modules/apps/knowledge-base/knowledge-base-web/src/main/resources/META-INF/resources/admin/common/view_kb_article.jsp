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

	<%
	KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse);
	%>

	<div class="management-bar management-bar-light navbar navbar-expand-md">
		<clay:container-fluid>
			<ul class="navbar-nav navbar-nav-expand">
				<li class="m-auto nav-item">
					<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= kbArticle.getStatus() %>" version="<%= String.valueOf(kbArticle.getVersion()) %>" />
				</li>
				<li class="nav-item">
					<liferay-frontend:sidebar-toggler-button
						cssClass="btn btn-monospaced btn-sm btn-unstyled"
						icon="info-circle-open"
					/>
				</li>
				<li class="nav-item">
					<clay:dropdown-actions
						dropdownItems="<%= kbDropdownItemsProvider.getKBArticleDropdownItems(kbArticle) %>"
						propsTransformer="admin/js/KBDropdownPropsTransformer"
					/>
				</li>
			</ul>
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

		<div <%= portletTitleBasedNavigation ? "class=\"sheet\"" : StringPool.BLANK %>>
			<div class="kb-entity-body">
				<c:if test="<%= portletTitleBasedNavigation %>">
					<div class="kb-article-title">
						<%= HtmlUtil.escape(kbArticle.getTitle()) %>
					</div>
				</c:if>

				<div id="<portlet:namespace /><%= kbArticle.getResourcePrimKey() %>">
					<%= kbArticle.getContent() %>
				</div>

				<clay:content-row>
					<clay:content-col>

						<%
						KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse);
						%>

						<liferay-social-bookmarks:bookmarks
							className="<%= KBArticle.class.getName() %>"
							classPK="<%= kbArticle.getKbArticleId() %>"
							displayStyle="<%= socialBookmarksDisplayStyle %>"
							target="_blank"
							title="<%= kbArticle.getTitle() %>"
							types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(socialBookmarksTypes) %>"
							urlImpl="<%= kbArticleURLHelper.createViewURL(kbArticle) %>"
						/>
					</clay:content-col>

					<c:if test="<%= enableKBArticleRatings %>">
						<clay:content-col
							expand="<%= true %>"
						>
							<liferay-ratings:ratings
								className="<%= KBArticle.class.getName() %>"
								classPK="<%= kbArticle.getResourcePrimKey() %>"
								inTrash="<%= false %>"
							/>
						</clay:content-col>
					</c:if>
				</clay:content-row>

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

				<c:if test="<%= !portletTitleBasedNavigation && !rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE) %>">
					<liferay-util:include page="/admin/common/kb_article_siblings.jsp" servletContext="<%= application %>" />
				</c:if>
			</div>

			<%
			int status = WorkflowConstants.STATUS_APPROVED;

			if (portletTitleBasedNavigation) {
				status = WorkflowConstants.STATUS_ANY;
			}

			List<KBArticle> childKBArticles = KBArticleServiceUtil.getKBArticles(scopeGroupId, kbArticle.getResourcePrimKey(), status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new KBArticlePriorityComparator(true));
			%>

			<c:if test="<%= enableKBArticleSuggestions || !childKBArticles.isEmpty() %>">
				<c:choose>
					<c:when test="<%= portletTitleBasedNavigation %>">
						<liferay-ui:panel-container
							cssClass="mt-5 panel-group-flush panel-group-sm"
							extended="<%= true %>"
							markupView="lexicon"
							persistState="<%= true %>"
						>
							<c:if test="<%= enableKBArticleSuggestions %>">
								<liferay-ui:panel
									collapsible="<%= true %>"
									cssClass="panel-unstyled"
									extended="<%= true %>"
									markupView="lexicon"
									persistState="<%= true %>"
									title="suggestions"
								>
									<liferay-util:include page="/admin/common/kb_article_suggestions.jsp" servletContext="<%= application %>" />
								</liferay-ui:panel>
							</c:if>

							<c:if test="<%= !childKBArticles.isEmpty() %>">
								<liferay-ui:panel
									collapsible="<%= true %>"
									cssClass="panel-unstyled"
									extended="<%= true %>"
									markupView="lexicon"
									persistState="<%= true %>"
									title='<%= LanguageUtil.format(request, "child-articles-x", childKBArticles.size(), false) %>'
								>
									<liferay-util:include page="/admin/common/kb_article_child.jsp" servletContext="<%= application %>" />
								</liferay-ui:panel>
							</c:if>
						</liferay-ui:panel-container>
					</c:when>
					<c:otherwise>
						<c:if test="<%= enableKBArticleSuggestions %>">
							<liferay-util:include page="/admin/common/kb_article_suggestions.jsp" servletContext="<%= application %>" />
						</c:if>

						<c:if test="<%= !childKBArticles.isEmpty() %>">
							<liferay-util:include page="/admin/common/kb_article_child.jsp" servletContext="<%= application %>" />
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
</div>

<%
List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(KBArticle.class.getName(), kbArticle.getClassPK());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);
%>