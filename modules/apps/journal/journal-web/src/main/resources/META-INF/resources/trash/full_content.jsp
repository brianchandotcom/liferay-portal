<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AssetRendererFactory<?> assetRendererFactory = (AssetRendererFactory<?>)request.getAttribute(WebKeys.ASSET_RENDERER_FACTORY);

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
%>

<liferay-journal:journal-article-display
	articleDisplay="<%= (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY) %>"
	paginationURL='<%=
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/view_content.jsp"
		).setParameter(
			"classNameId", assetRendererFactory.getClassNameId()
		).setParameter(
			"classPK", JournalArticleAssetRenderer.getClassPK(article)
		).buildPortletURL()
	%>'
/>