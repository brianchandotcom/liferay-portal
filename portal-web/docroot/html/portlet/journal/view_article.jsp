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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);
String articleId = ParamUtil.getString(request, "articleId");
String languageId = LanguageUtil.getLanguageId(request);
int articlePage = ParamUtil.getInteger(renderRequest, "page", 1);

JournalArticleDisplay articleDisplay = JournalContentUtil.getDisplay(groupId, articleId, null, null, languageId, articlePage, new PortletRequestModel(renderRequest, renderResponse), themeDisplay);

try {
	article = JournalArticleLocalServiceUtil.getLatestArticle(groupId, articleId, WorkflowConstants.STATUS_ANY);

	boolean expired = article.isExpired();

	if (!expired) {
		Date expirationDate = article.getExpirationDate();

		if ((expirationDate != null) && expirationDate.before(new Date())) {
			expired = true;
		}
	}
%>

	<c:choose>
		<c:when test="<%= (articleDisplay != null) && !expired %>">

			<div class="journal-content-article">
				<%= RuntimePageUtil.processXML(request, response, articleDisplay.getContent()) %>
			</div>

		</c:when>
		<c:otherwise>
			<div class="alert alert-danger">
				<liferay-ui:message key="this-content-has-expired-or-you-do-not-have-the-required-permissions-to-access-it" />
			</div>
		</c:otherwise>
	</c:choose>

<%
} catch (NoSuchArticleException nsae) {
%>

	<div class="alert alert-danger">
		<%= LanguageUtil.get(pageContext, "the-selected-web-content-no-longer-exists") %>
	</div>

<%
}
%>