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
long parentResourcePrimKey = (long)request.getAttribute("parentResourcePrimKey");

List<Long> ancestorResourcePrimaryKeys = (List<Long>)request.getAttribute("ancestorResourcePrimaryKeys");
KBArticleURLHelper kbArticleURLHelper = (KBArticleURLHelper)request.getAttribute("kbArticleURLHelper");

int level = GetterUtil.getInteger(request.getAttribute("level"));
int maxNestingLevel = kbDisplayPortletInstanceConfiguration.maxNestingLevel();

boolean maxNestingLevelReached = false;

if ((maxNestingLevel - level) <= 1) {
	maxNestingLevelReached = true;
}

List<KBArticle> childKBArticles = null;

if (maxNestingLevelReached) {
	childKBArticles = KBArticleServiceUtil.getAllDescendantKBArticles(parentResourcePrimKey, WorkflowConstants.STATUS_APPROVED, new KBArticlePriorityComparator(true));
}
else {
	childKBArticles = KBArticleServiceUtil.getKBArticles(themeDisplay.getScopeGroupId(), parentResourcePrimKey, WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new KBArticlePriorityComparator(true));
}

for (KBArticle childKBArticle : childKBArticles) {
	PortletURL viewChildURL = kbArticleURLHelper.createViewURL(childKBArticle);
%>

	<ul>
		<li>

			<%
			boolean childKBArticleExpanded = false;

			if ((ancestorResourcePrimaryKeys.size() > 1) && (level < ancestorResourcePrimaryKeys.size()) && (childKBArticle.getResourcePrimKey() == ancestorResourcePrimaryKeys.get(level))) {
				childKBArticleExpanded = true;
			}

			String childKBArticleClass = StringPool.BLANK;

			if (childKBArticle.getResourcePrimKey() == kbArticle.getResourcePrimKey()) {
				childKBArticleClass = "kbarticle-selected";
			}
			else if (childKBArticleExpanded && !maxNestingLevelReached) {
				childKBArticleClass = "kbarticle-expanded";
			}
			%>

			<a class="<%= childKBArticleClass %>" href="<%= viewChildURL %>"><%= HtmlUtil.escape(childKBArticle.getTitle()) %></a>

			<c:if test="<%= (parentResourcePrimKey != kbArticle.getResourcePrimKey()) && !maxNestingLevelReached %>">

				<%
				request.setAttribute("level", level + 1);
				request.setAttribute("parentResourcePrimKey", childKBArticle.getResourcePrimKey());
				%>

				<liferay-util:include page="/display/view_child_articles.jsp" servletContext="<%= application %>" />
			</c:if>
		</li>
	</ul>

<%
}
%>