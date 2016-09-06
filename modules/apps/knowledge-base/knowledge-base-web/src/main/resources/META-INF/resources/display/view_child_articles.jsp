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
KBArticle curKBArticle = (KBArticle)request.getAttribute("curKBArticle");

List<Long> ancestorResourcePrimaryKeys = (List<Long>)request.getAttribute("ancestorResourcePrimaryKeys");
KBArticleURLHelper kbArticleURLHelper = (KBArticleURLHelper)request.getAttribute("kbArticleURLHelper");

int level = GetterUtil.getInteger(request.getAttribute("level"));

List<KBArticle> childKBArticles = KBArticleServiceUtil.getKBArticles(themeDisplay.getScopeGroupId(), curKBArticle.getResourcePrimKey(), WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new KBArticlePriorityComparator(true));

for (KBArticle childKBArticle : childKBArticles) {
	PortletURL viewChildURL = kbArticleURLHelper.createViewURL(childKBArticle);
%>

	<ul>
		<li>

			<%
			boolean childKBArticleSelected = false;

			if (childKBArticle.getResourcePrimKey() == kbArticle.getResourcePrimKey()) {
				childKBArticleSelected = true;
			}

			boolean childKBArticleExpanded = false;

			if ((ancestorResourcePrimaryKeys.size() > 1) && (childKBArticle.getResourcePrimKey() == ancestorResourcePrimaryKeys.get(level))) {
				childKBArticleExpanded = true;
			}

			String childKBArticleClass = StringPool.BLANK;

			if (childKBArticle.getResourcePrimKey() == kbArticle.getResourcePrimKey()) {
				childKBArticleClass = "kbarticle-selected";
			}
			else if (childKBArticleExpanded) {
				childKBArticleClass = "kbarticle-expanded";
			}
			%>

			<a class="<%= childKBArticleClass %>" href="<%= viewChildURL %>"><%= HtmlUtil.escape(childKBArticle.getTitle()) %></a>

			<c:choose>
				<c:when test="<%= childKBArticleSelected %>">

					<%
					List<KBArticle> descendantKBArticles = KBArticleServiceUtil.getKBArticles(themeDisplay.getScopeGroupId(), childKBArticle.getResourcePrimKey(), WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new KBArticlePriorityComparator(true));

					for (KBArticle descendantKBArticle : descendantKBArticles) {
						PortletURL viewCurKBArticleURL = kbArticleURLHelper.createViewURL(descendantKBArticle);
					%>

						<ul>
							<li>
								<a href="<%= viewCurKBArticleURL %>"><%= HtmlUtil.escape(descendantKBArticle.getTitle()) %></a>
							</li>
						</ul>

					<%
					}
					%>

				</c:when>
				<c:otherwise>

					<%
					if (childKBArticleExpanded) {
						request.setAttribute("kbArticle", KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);
						request.setAttribute("curKBArticle", childKBArticle);
						request.setAttribute("ancestorResourcePrimaryKeys", ancestorResourcePrimaryKeys);
						request.setAttribute("kbArticleURLHelper", kbArticleURLHelper);
						request.setAttribute("level", level + 1);
					%>

					<liferay-util:include page="/display/view_child_articles.jsp" servletContext="<%= application %>" />

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</li>
	</ul>

<%
}
%>