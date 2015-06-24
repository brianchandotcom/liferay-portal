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

<%@ include file="/init.jsp" %>

<%
JournalArticle article = journalContentDisplayContext.getArticle();
JournalArticleDisplay articleDisplay = journalContentDisplayContext.getArticleDisplay();
AssetRenderer articleAssetRenderer = journalContentDisplayContext.getAssetRenderer();

String webContentTitle = HtmlUtil.escapeAttribute(articleDisplay.getTitle());

String webContentSummary = HtmlUtil.escape(articleDisplay.getDescription());
webContentSummary = HtmlUtil.replaceNewLine(webContentSummary);

if (Validator.isNull(webContentSummary)) {
	webContentSummary = HtmlUtil.stripHtml(articleDisplay.getContent());
}

webContentSummary = StringUtil.shorten(webContentSummary, 70);

String webContentImgURL = HtmlUtil.escapeAttribute(articleAssetRenderer.getThumbnailPath(liferayPortletRequest));

long webContentUserId = articleDisplay.getUserId();
%>

<div class="article-preview-content">
	<div class="card-horizontal">
		<div class="card-row">
			<div class="card-col-5">
				<div class="square-thumbnail" style="background-image: url('<%= webContentImgURL %>');"></div>
			</div>
			<div class="card-col-7 card-col-gutters">
				<h4><%= webContentTitle %></h4>
				<p><%= webContentSummary %></p>
				<liferay-ui:user-display
					displayStyle="4"
					userId="<%= webContentUserId %>"
				/>
			</div>
		</div>
	</div>
</div>