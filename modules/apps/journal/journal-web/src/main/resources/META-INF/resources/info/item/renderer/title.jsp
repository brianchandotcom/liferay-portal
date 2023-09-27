<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info/item/renderer/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
%>

<c:choose>
	<c:when test="<%= (article != null) && article.isExpired() %>">
		<clay:alert
			displayType="warning"
			message='<%= LanguageUtil.format(request, "x-is-expired", HtmlUtil.escape(article.getTitle(locale))) %>'
		/>
	</c:when>
	<c:otherwise>
		<div class="asset-summary">
			<%= HtmlUtil.escape(article.getTitle(locale)) %>
		</div>
	</c:otherwise>
</c:choose>