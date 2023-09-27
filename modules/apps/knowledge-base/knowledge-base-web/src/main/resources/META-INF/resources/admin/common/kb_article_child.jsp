<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
ViewKBArticleDisplayContext viewKBArticleDisplayContext = new ViewKBArticleDisplayContext(liferayPortletRequest, liferayPortletResponse);
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
								<c:when test="<%= viewKBArticleDisplayContext.isKBArticleDescriptionEnabled() && Validator.isNotNull(childrenKBArticle.getDescription()) %>">
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