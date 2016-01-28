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

String title = assetRenderer.getTitle(locale);

if (article.getGroupId() != themeDisplay.getScopeGroupId()) {
	Group articleGroup = GroupLocalServiceUtil.getGroup(article.getGroupId());

	title = title + StringPool.SPACE + StringPool.OPEN_PARENTHESIS + articleGroup.getDescriptiveName(locale) + StringPool.CLOSE_PARENTHESIS;
}

String articleImageURL = HtmlUtil.escapeAttribute(assetRenderer.getThumbnailPath(liferayPortletRequest));
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(articleImageURL) %>">
		<liferay-frontend:vertical-card
			imageUrl="<%= articleImageURL %>"
			subtitle="<%= assetRenderer.getSummary(liferayPortletRequest, liferayPortletResponse) %>"
			title="<%= title %>"
		>
			<liferay-frontend:vertical-card-sticker-bottom>
				<liferay-ui:user-portrait
					cssClass="sticker sticker-bottom"
					imageCssClass="user-icon-lg"
					userId="<%= assetRenderer.getUserId() %>"
				/>
			</liferay-frontend:vertical-card-sticker-bottom>

			<liferay-frontend:vertical-card-footer>
				<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" />
			</liferay-frontend:vertical-card-footer>
		</liferay-frontend:vertical-card>
	</c:when>
	<c:otherwise>
		<liferay-frontend:icon-vertical-card
			icon="web-content"
			subtitle="<%= assetRenderer.getSummary(liferayPortletRequest, liferayPortletResponse) %>"
			title="<%= title %>"
		>
			<liferay-frontend:vertical-card-sticker-bottom>
				<liferay-ui:user-portrait
					cssClass="sticker sticker-bottom"
					imageCssClass="user-icon-lg"
					userId="<%= assetRenderer.getUserId() %>"
				/>
			</liferay-frontend:vertical-card-sticker-bottom>

			<liferay-frontend:vertical-card-footer>
				<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" />
			</liferay-frontend:vertical-card-footer>
		</liferay-frontend:icon-vertical-card>
	</c:otherwise>
</c:choose>