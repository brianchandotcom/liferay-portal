<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

KBArticle[] previousAndNextKBArticles = KBArticleServiceUtil.getPreviousAndNextKBArticles(kbArticle.getKbArticleId());

KBArticle previousKBArticle = previousAndNextKBArticles[0];
KBArticle nextKBArticle = previousAndNextKBArticles[2];

if (resourceClassNameId != kbFolderClassNameId) {
	if (resourcePrimKey == kbArticle.getResourcePrimKey()) {
		previousKBArticle = null;
	}

	if (nextKBArticle != null) {
		List<Long> ancestorResourcePrimaryKeys = nextKBArticle.getAncestorResourcePrimaryKeys();

		if (!ancestorResourcePrimaryKeys.contains(resourcePrimKey)) {
			nextKBArticle = null;
		}
	}
}

KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse);
%>

<div class="kb-article-siblings">
	<span class="kb-article-previous">
		<c:if test="<%= previousKBArticle != null %>">

			<%
			PortletURL previousKBArticleURL = kbArticleURLHelper.createViewURL(previousKBArticle);
			%>

			<aui:a cssClass="d-none d-sm-block" href="<%= previousKBArticleURL.toString() %>">
				<i class="icon icon-circle-arrow-left"></i> <span class="title"><%= HtmlUtil.escape(previousKBArticle.getTitle()) %></span>
			</aui:a>

			<aui:a cssClass="d-block d-sm-none" href="<%= previousKBArticleURL.toString() %>">
				<i class="icon icon-circle-arrow-left"></i> <span class="title"><liferay-ui:message key="previous" /></span>
			</aui:a>
		</c:if>
	</span>
	<span class="kb-article-next">
		<c:if test="<%= nextKBArticle != null %>">

			<%
			PortletURL nextKBArticleURL = kbArticleURLHelper.createViewURL(nextKBArticle);
			%>

			<aui:a cssClass="d-none d-sm-block next" href="<%= nextKBArticleURL.toString() %>">
				<span class="title"><%= HtmlUtil.escape(nextKBArticle.getTitle()) %></span> <i class="icon icon-circle-arrow-right"></i>
			</aui:a>

			<aui:a cssClass="d-block d-sm-none next" href="<%= nextKBArticleURL.toString() %>">
				<span class="title"><liferay-ui:message key="next" /></span> <i class="icon icon-circle-arrow-right"></i>
			</aui:a>
		</c:if>
	</span>
</div>