<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

ViewKBArticleDisplayContext viewKBArticleDisplayContext = new ViewKBArticleDisplayContext(liferayPortletRequest, liferayPortletResponse);

if (Validator.isNotNull(backURL)) {
	portletDisplay.setURLBack(backURL);
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);
	portletDisplay.setURLBackTitle(portletDisplay.getTitle());

	renderResponse.setTitle(kbArticle.getTitle());
}
%>

<c:if test="<%= portletTitleBasedNavigation %>">

	<%
	KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse);
	%>

	<div class="management-bar management-bar-light navbar navbar-expand-md">
		<clay:container-fluid>
			<ul class="justify-content-end navbar-nav navbar-nav-expand">
				<li class="nav-item">
					<clay:link
						aria-label='<%= LanguageUtil.get(request, "edit") %>'
						cssClass="btn-monospaced btn-secondary btn-sm"
						href="<%= viewKBArticleDisplayContext.getEditArticleURL(kbArticle) %>"
						icon="pencil"
						title='<%= LanguageUtil.get(request, "edit") %>'
					/>
				</li>
				<li class="nav-item">
					<liferay-frontend:sidebar-toggler-button
						cssClass="btn btn-monospaced btn-secondary btn-sm btn-unstyled"
						icon="info-circle-open"
					/>
				</li>

				<c:if test="<%= viewKBArticleDisplayContext.isSubscriptionEnabled(kbArticle) %>">
					<li class="nav-item">
						<clay:link
							aria-label="<%= viewKBArticleDisplayContext.getSubscriptionLabel(kbArticle) %>"
							cssClass="btn-primary btn-sm"
							href="<%= viewKBArticleDisplayContext.getSubscriptionURL(kbArticle).toString() %>"
							label="<%= viewKBArticleDisplayContext.getSubscriptionLabel(kbArticle) %>"
						/>
					</li>
				</c:if>

				<li class="nav-item">
					<clay:dropdown-actions
						aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
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
			<div class="autofit-row">
				<div class="autofit-col autofit-col-expand">
					<h1><%= HtmlUtil.escape(kbArticle.getTitle()) %></h1>
				</div>

				<c:if test="<%= !rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN) %>">
					<div class="autofit-col">

						<%
						KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(kbGroupServiceConfiguration, liferayPortletRequest, liferayPortletResponse);
						%>

						<clay:dropdown-actions
							aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
							dropdownItems="<%= kbDropdownItemsProvider.getKBArticleDropdownItems(kbArticle) %>"
							propsTransformer="admin/js/KBDropdownPropsTransformer"
						/>
					</div>
				</c:if>
			</div>
		</c:if>

		<div class="kb-tools">
			<liferay-util:include page="/admin/common/kb_article_tools.jsp" servletContext="<%= application %>" />
		</div>

		<div <%= portletTitleBasedNavigation ? "class=\"sheet\"" : StringPool.BLANK %>>
			<div class="kb-entity-body mb-5">
				<c:if test="<%= portletTitleBasedNavigation %>">
					<div class="kb-article-title">
						<%= HtmlUtil.escape(kbArticle.getTitle()) %>
					</div>
				</c:if>

				<div class="mb-4" id="<portlet:namespace /><%= kbArticle.getResourcePrimKey() %>">
					<%= kbArticle.getContent() %>
				</div>

				<c:if test="<%= viewKBArticleDisplayContext.isKBArticleDescriptionEnabled() && Validator.isNotNull(kbArticle.getDescription()) %>">
					<div class="sheet-subtitle">
						<liferay-ui:message key="description" />
					</div>

					<div class="lfr-asset-description">
						<%= HtmlUtil.escape(kbArticle.getDescription()) %>
					</div>
				</c:if>

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
						<clay:panel-group>
							<c:if test="<%= enableKBArticleSuggestions %>">
								<clay:panel
									displayTitle='<%= LanguageUtil.get(request, "suggestions") %>'
									expanded="<%= true %>"
								>
									<liferay-util:include page="/admin/common/kb_article_suggestions.jsp" servletContext="<%= application %>" />
								</clay:panel>
							</c:if>

							<c:if test="<%= !childKBArticles.isEmpty() %>">
								<clay:panel
									displayTitle='<%= LanguageUtil.format(request, "child-articles-x", childKBArticles.size(), false) %>'
									expanded="<%= true %>"
								>
									<liferay-util:include page="/admin/common/kb_article_child.jsp" servletContext="<%= application %>" />
								</clay:panel>
							</c:if>
						</clay:panel-group>
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