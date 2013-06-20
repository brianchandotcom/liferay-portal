<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute(WebKeys.ASSET_RENDERER);
int abstractLength = (Integer)request.getAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH);
String viewURL = (String)request.getAttribute(WebKeys.ASSET_PUBLISHER_VIEW_URL);

String xmlRequest = PortletRequestUtil.toXML(renderRequest, renderResponse);

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
JournalArticleResource articleResource = JournalArticleResourceLocalServiceUtil.getArticleResource(article.getResourcePrimKey());

String languageId = LanguageUtil.getLanguageId(request);

boolean workflowAssetPreview = GetterUtil.getBoolean((Boolean)request.getAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW));

JournalArticleDisplay articleDisplay = null;

if (!workflowAssetPreview && article.isApproved()) {
	articleDisplay = JournalContentUtil.getDisplay(articleResource.getGroupId(), articleResource.getArticleId(), null, null, languageId, themeDisplay, 1, xmlRequest);
}
else {
	articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(article, null, null, languageId, 1, null, themeDisplay);
}
%>

<c:if test="<%= articleDisplay.isSmallImage() %>">

	<%
	String src = StringPool.BLANK;

	if (Validator.isNotNull(articleDisplay.getSmallImageURL())) {
		src = articleDisplay.getSmallImageURL();
	}
	else {
		src = themeDisplay.getPathImage() + "/journal/article?img_id=" + articleDisplay.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(articleDisplay.getSmallImageId()) ;
	}
	%>

	<div class="asset-small-image">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(viewURL) %>">
				<a href="<%= viewURL %>">
					<img alt="<%= articleDisplay.getTitle() %>" class="asset-small-image" src="<%= HtmlUtil.escape(src) %>" width="150" />
				</a>
			</c:when>
			<c:otherwise>
				<img alt="" class="asset-small-image" src="<%= HtmlUtil.escape(src) %>" width="150" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<%
String summary = HtmlUtil.escape(articleDisplay.getDescription());

if (Validator.isNull(summary)) {
	summary = HtmlUtil.stripHtml(articleDisplay.getContent());
}
%>

<%= StringUtil.shorten(summary, abstractLength) %>