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
String layoutUuid = null;

JournalArticle article = journalDisplayContext.getArticle();

if (article != null) {
	layoutUuid = article.getLayoutUuid();
}

Layout articleLayout = null;

if (Validator.isNotNull(layoutUuid)) {
	articleLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, article.getGroupId(), false);

	if (articleLayout == null) {
		articleLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, article.getGroupId(), true);
	}
}

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);
%>

<p class="text-secondary"><liferay-ui:message key="changing-the-display-page-template-will-affect-all-web-content-article-versions-even-when-saving-it-as-a-draft" /></p>

<c:if test="<%= Validator.isNotNull(layoutUuid) && (articleLayout == null) %>">
	<div class="alert alert-warning">
		<liferay-ui:message arguments="<%= layoutUuid %>" key="this-article-is-configured-to-use-a-display-page-that-does-not-exist-on-the-current-site" />
	</div>
</c:if>

<liferay-asset:select-asset-display-page
	classNameId="<%= PortalUtil.getClassNameId(JournalArticle.class) %>"
	classPK="<%= (article != null) ? article.getResourcePrimKey() : 0 %>"
	classTypeId="<%= journalEditArticleDisplayContext.getDDMStructureId() %>"
	groupId="<%= journalEditArticleDisplayContext.getGroupId() %>"
	showPortletLayouts="<%= true %>"
	showViewInContextLink="<%= journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASS_NAME_ID_DEFAULT %>"
/>