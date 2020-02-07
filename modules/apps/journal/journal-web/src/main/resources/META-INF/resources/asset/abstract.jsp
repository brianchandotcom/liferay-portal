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