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

<%@ include file="/display/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

KBNavigationDisplayContext kbNavigationDisplayContext = (KBNavigationDisplayContext)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_NAVIGATION_DISPLAY_CONTEXT);

List<Long> ancestorResourcePrimaryKeys = kbNavigationDisplayContext.getAncestorResourcePrimaryKeys();

long rootResourcePrimKey = kbNavigationDisplayContext.getRootResourcePrimKey();

String pageTitle = kbNavigationDisplayContext.getPageTitle();

if (Validator.isNotNull(pageTitle)) {
	PortalUtil.setPageTitle(pageTitle, request);
}

KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse, templatePath);
%>

<div class="kbarticle-navigation">
	<c:if test="<%= resourceClassNameId == kbFolderClassNameId %>">
		<liferay-util:include page="/display/content_root_selector.jsp" servletContext="<%= application %>" />
	</c:if>

	<%
	List<KBArticle> kbArticles = KBArticleServiceUtil.getKBArticles(themeDisplay.getScopeGroupId(), rootResourcePrimKey, WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new KBArticlePriorityComparator(true));

	for (KBArticle curKBArticle : kbArticles) {
		PortletURL viewURL = kbArticleURLHelper.createViewURL(curKBArticle);
	%>

		<ul>
			<li>

				<%
				boolean kbArticleExpanded = false;

				if ((ancestorResourcePrimaryKeys.size() > 0) && (curKBArticle.getResourcePrimKey() == ancestorResourcePrimaryKeys.get(0))) {
					kbArticleExpanded = true;
				}

				String kbArticleClass = StringPool.BLANK;

				if (curKBArticle.getResourcePrimKey() == kbArticle.getResourcePrimKey()) {
					kbArticleClass = "kbarticle-selected";
				}
				else if (kbArticleExpanded) {
					kbArticleClass = "kbarticle-expanded";
				}
				%>

				<a class="<%= kbArticleClass %>" href="<%= viewURL %>"><%= HtmlUtil.escape(curKBArticle.getTitle()) %></a>

				<c:if test="<%= kbArticleExpanded %>">

					<%
					request.setAttribute("ancestorResourcePrimaryKeys", ancestorResourcePrimaryKeys);
					request.setAttribute("curKBArticle", curKBArticle);
					request.setAttribute("kbArticleURLHelper", kbArticleURLHelper);
					request.setAttribute("level", 1);
					%>

					<liferay-util:include page="/display/view_child_articles.jsp" servletContext="<%= application %>" />
				</c:if>
			</li>
		</ul>

	<%
	}
	%>

</div>