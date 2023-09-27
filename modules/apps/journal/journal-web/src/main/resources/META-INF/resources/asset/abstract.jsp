<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset/init.jsp" %>

<%
int abstractLength = GetterUtil.getInteger(request.getAttribute(WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH), AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH);

JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);
%>

<div class="asset-summary">
	<c:if test="<%= articleDisplay.isSmallImage() %>">
		<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image mb-4" style="background-image: url(<%= articleDisplay.getArticleDisplayImageURL(themeDisplay) %>);"></div>
	</c:if>

	<%
	String summary = articleDisplay.getDescription();
	%>

	<c:choose>
		<c:when test="<%= Validator.isNull(summary) %>">
			<%= StringUtil.shorten(HtmlUtil.stripHtml(articleDisplay.getContent()), abstractLength) %>
		</c:when>
		<c:otherwise>
			<%= HtmlUtil.replaceNewLine(StringUtil.shorten(HtmlUtil.stripHtml(summary), abstractLength)) %>
		</c:otherwise>
	</c:choose>
</div>