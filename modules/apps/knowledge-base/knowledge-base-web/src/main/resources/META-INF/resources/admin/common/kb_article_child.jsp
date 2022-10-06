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

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

int status = WorkflowConstants.STATUS_APPROVED;

if (portletTitleBasedNavigation) {
	status = WorkflowConstants.STATUS_ANY;
}

List<KBArticle> childKBArticles = KBArticleServiceUtil.getKBArticles(scopeGroupId, kbArticle.getResourcePrimKey(), status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new KBArticlePriorityComparator(true));

KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse);
%>

<c:if test="<%= !portletTitleBasedNavigation %>">
	<div class="h4 text-default">
		<liferay-ui:message arguments="<%= childKBArticles.size() %>" key="child-articles-x" translateArguments="<%= false %>" />
	</div>
</c:if>

<div class="panel">
	<ul class="list-group">

		<%
		for (KBArticle childrenKBArticle : childKBArticles) {
		%>

			<li class="list-group-item">
				<div class="list-group-title">

					<%
					PortletURL viewKBArticleURL = null;

					if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN) || rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_SEARCH) || rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_SECTION)) {
						viewKBArticleURL = kbArticleURLHelper.createViewWithRedirectURL(childrenKBArticle, currentURL);
					}
					else {
						viewKBArticleURL = kbArticleURLHelper.createViewURL(childrenKBArticle);
					}
					%>

					<aui:a href="<%= viewKBArticleURL.toString() %>"><%= HtmlUtil.escape(childrenKBArticle.getTitle()) %></aui:a>
				</div>

				<p class="list-group-subtext">
					<span class="text-truncate-inline">
						<span class="text-truncate">
							<c:choose>
								<c:when test="<%= Validator.isNotNull(childrenKBArticle.getDescription()) %>">
									<%= HtmlUtil.escape(childrenKBArticle.getDescription()) %>
								</c:when>
								<c:otherwise>
									<%= HtmlUtil.escape(StringUtil.shorten(HtmlParserUtil.extractText(childrenKBArticle.getContent()), 200)) %>
								</c:otherwise>
							</c:choose>
						</span>
					</span>
				</p>
			</li>

		<%
		}
		%>

	</ul>
</div>