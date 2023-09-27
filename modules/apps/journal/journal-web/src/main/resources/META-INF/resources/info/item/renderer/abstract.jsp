<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info/item/renderer/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
AssetRenderer<?> assetRenderer = (AssetRenderer)request.getAttribute(WebKeys.ASSET_RENDERER);
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
			<c:if test="<%= article.isSmallImage() %>">
				<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image mb-4" style="background-image: url(<%= article.getArticleImageURL(themeDisplay) %>);"></div>
			</c:if>

			<%
			String summary = assetRenderer.getSummary(renderRequest, renderResponse);
			%>

			<c:choose>
				<c:when test="<%= Validator.isNull(summary) %>">

					<%
					assetRenderer.include(request, response, "abstract");
					%>

				</c:when>
				<c:otherwise>
					<%= summary %>
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>