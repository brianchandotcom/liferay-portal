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
EditArticleDisplayPageDisplayContext editArticleDisplayPageDisplayContext = new EditArticleDisplayPageDisplayContext(request);

DDMStructure ddmStructure = editArticleDisplayPageDisplayContext.getDDMStructure(journalDisplayContext.getDDMStructureKey());

JournalArticle article = editArticleDisplayPageDisplayContext.getArticle();

if ((ddmStructure == null) && (article != null)) {
	ddmStructure = editArticleDisplayPageDisplayContext.getDDMStructure(article.getDDMStructureKey());
}

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);
%>

<liferay-asset:select-asset-display-page
	classNameId="<%= PortalUtil.getClassNameId(JournalArticle.class) %>"
	classPK="<%= (article != null) ? article.getResourcePrimKey() : 0 %>"
	classTypeId="<%= ddmStructure.getStructureId() %>"
	groupId="<%= groupId %>"
	showPortletLayouts="<%= true %>"
/>