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
long termsOfUseGroupId = PrefsPropsUtil.getLong(themeDisplay.getCompanyId(), JournalServiceConfigurationKeys.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID, JournalServiceConfigurationValues.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID);
String termsOfUseArticleId = PrefsPropsUtil.getString(themeDisplay.getCompanyId(), JournalServiceConfigurationKeys.TERMS_OF_USE_JOURNAL_ARTICLE_ID, JournalServiceConfigurationValues.TERMS_OF_USE_JOURNAL_ARTICLE_ID);

JournalArticle journalArticle = null;

if ((termsOfUseGroupId > 0) && Validator.isNotNull(termsOfUseArticleId)) {
	journalArticle = JournalArticleLocalServiceUtil.fetchArticle(termsOfUseGroupId, termsOfUseArticleId);
}
%>

<c:choose>
	<c:when test="<%= journalArticle != null %>">
		<liferay-asset:asset-display
			className="<%= JournalArticle.class.getName() %>"
			classPK="<%= journalArticle.getResourcePrimKey() %>"
			template="<%= AssetRenderer.TEMPLATE_FULL_CONTENT %>"
		/>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/terms_of_use_default.jsp" />
	</c:otherwise>
</c:choose>