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
int abstractLength = GetterUtil.getInteger(request.getAttribute(WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH), AssetUtil.ASSET_ENTRY_ABSTRACT_LENGTH);
String viewURL = (String)request.getAttribute(WebKeys.ASSET_ENTRY_VIEW_URL);

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
JournalArticleResource articleResource = JournalArticleResourceLocalServiceUtil.getArticleResource(article.getResourcePrimKey());

String languageId = LanguageUtil.getLanguageId(request);

boolean workflowAssetPreview = GetterUtil.getBoolean((Boolean)request.getAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW));

JournalArticleDisplay articleDisplay = null;

if (!workflowAssetPreview && article.isApproved()) {
	articleDisplay = JournalContentUtil.getDisplay(articleResource.getGroupId(), articleResource.getArticleId(), article.getVersion(), null, null, languageId, 1, new PortletRequestModel(renderRequest, renderResponse), themeDisplay);
}
else {
	articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(article, null, null, languageId, 1, new PortletRequestModel(renderRequest, renderResponse), themeDisplay);
}
%>

<c:if test="<%= articleDisplay.isSmallImage() %>">
	<div class="asset-small-image">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(viewURL) %>">
				<a href="<%= viewURL %>">
					<img alt="<%= HtmlUtil.escapeAttribute(articleDisplay.getTitle()) %>" class="asset-small-image img-thumbnail" src="<%= HtmlUtil.escapeAttribute(articleDisplay.getArticleDisplayImageURL(themeDisplay)) %>" width="150" />
				</a>
			</c:when>
			<c:otherwise>
				<img alt="" class="asset-small-image img-thumbnail" src="<%= HtmlUtil.escapeAttribute(articleDisplay.getArticleDisplayImageURL(themeDisplay)) %>" width="150" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<%
String summary = HtmlUtil.escape(articleDisplay.getDescription());

summary = HtmlUtil.replaceNewLine(summary);

if (Validator.isNull(summary)) {
	summary = HtmlUtil.stripHtml(articleDisplay.getContent());
}
%>

<%= StringUtil.shorten(summary, abstractLength) %>