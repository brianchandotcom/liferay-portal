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
AssetRenderer<JournalArticle> assetRenderer = journalContentDisplayContext.getAssetRenderer();

String title = HtmlUtil.escape(assetRenderer.getTitle(locale));

if (article.getGroupId() != themeDisplay.getScopeGroupId()) {
	Group articleGroup = GroupLocalServiceUtil.getGroup(article.getGroupId());

	title = title + StringPool.SPACE + StringPool.OPEN_PARENTHESIS + HtmlUtil.escape(articleGroup.getDescriptiveName(locale)) + StringPool.CLOSE_PARENTHESIS;
}

String articleImageURL = HtmlUtil.escapeAttribute(assetRenderer.getThumbnailPath(liferayPortletRequest));
%>

<div class="card card-type-asset <%= Validator.isNotNull(articleImageURL) ? "image-card" : "file-card" %>">
	<div class="aspect-ratio card-item-first">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(articleImageURL) %>">
				<img alt="thumbnail" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= articleImageURL %>">
			</c:when>
			<c:otherwise>
				<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon">
					<svg aria-hidden="true" class="lexicon-icon lexicon-icon-web-content">
						<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#web-content"></use>
					</svg>
				</div>
			</c:otherwise>
		</c:choose>

		<liferay-ui:user-portrait
			cssClass="sticker sticker-bottom-left"
			userId="<%= assetRenderer.getUserId() %>"
		/>
	</div>

	<div class="card-body">
		<div class="card-row">
			<div class="autofit-col autofit-col-expand">
				<div class="card-title text-truncate" title="<%= title %>">
					<%= title %>
				</div>

				<div class="card-detail">
					<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" />
				</div>
			</div>
		</div>
	</div>
</div>