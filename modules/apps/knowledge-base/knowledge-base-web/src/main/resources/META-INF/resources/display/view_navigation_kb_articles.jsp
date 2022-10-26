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

<ul>

	<%
	KBNavigationDisplayContext kbNavigationDisplayContext = (KBNavigationDisplayContext)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_NAVIGATION_DISPLAY_CONTEXT);

	KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

	long parentResourcePrimKey = (long)request.getAttribute("view_navigation_kb_articles.jsp-parentResourcePrimKey");
	int level = GetterUtil.getInteger(request.getAttribute("view_navigation_kb_articles.jsp-level"));

	KBArticleURLHelper kbArticleURLHelper = (KBArticleURLHelper)request.getAttribute("view_navigation_kb_articles.jsp-kbArticleURLHelper");

	for (KBArticle childKBArticle : kbNavigationDisplayContext.getChildKBArticles(themeDisplay.getScopeGroupId(), parentResourcePrimKey, level)) {
		boolean childKBArticleExpanded = kbNavigationDisplayContext.isChildKBArticleExpanded(childKBArticle, level);

		String cssClass = StringPool.BLANK;

		if (childKBArticle.getResourcePrimKey() == kbArticle.getResourcePrimKey()) {
			cssClass = "kbarticle-selected";
		}
		else if (childKBArticleExpanded && !kbNavigationDisplayContext.isMaxNestingLevelReached(level)) {
			cssClass = "kbarticle-expanded";
		}
	%>

		<li class="<%= cssClass %>">
			<a aria-label="<%= HtmlUtil.escape(childKBArticle.getTitle()) %>" href="<%= kbArticleURLHelper.createViewURL(childKBArticle) %>">
				<clay:icon
					cssClass="mr-1"
					symbol="document-text"
				/>
				<%= HtmlUtil.escape(childKBArticle.getTitle()) %>
			</a>

			<c:if test="<%= kbNavigationDisplayContext.isFurtherExpansionRequired(parentResourcePrimKey, childKBArticle, level) %>">

				<%
				request.setAttribute("view_navigation_kb_articles.jsp-level", level + 1);
				request.setAttribute("view_navigation_kb_articles.jsp-parentResourcePrimKey", childKBArticle.getResourcePrimKey());
				%>

				<liferay-util:include page="/display/view_navigation_kb_articles.jsp" servletContext="<%= application %>" />
			</c:if>
		</li>

	<%
	}
	%>

</ul>