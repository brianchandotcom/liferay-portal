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
String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");

DDMStructure ddmStructure = (DDMStructure)request.getAttribute("edit_article.jsp-structure");

boolean changeStructure = GetterUtil.getBoolean(request.getAttribute("edit_article.jsp-changeStructure"));
%>

<liferay-ui:error-marker key="errorSection" value="categorization" />

<aui:model-context bean="<%= journalDisplayContext.getArticle() %>" model="<%= JournalArticle.class %>" />

<liferay-ui:asset-categories-error />

<liferay-ui:asset-tags-error />

<%
long classPK = 0;
double priority = 0;

JournalArticle article = journalDisplayContext.getArticle();

if (article != null) {
	classPK = article.getResourcePrimKey();

	if (!article.isApproved() && (article.getVersion() != JournalArticleConstants.VERSION_DEFAULT)) {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), article.getPrimaryKey());

		if (assetEntry != null) {
			classPK = article.getPrimaryKey();
			priority = assetEntry.getPriority();
		}
	}
	else {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), article.getResourcePrimKey());

		if (assetEntry != null) {
			priority = assetEntry.getPriority();
		}
	}
}
%>

<aui:input classPK="<%= classPK %>" classTypePK="<%= ddmStructure.getStructureId() %>" ignoreRequestValue="<%= changeStructure %>" name="categories" type="assetCategories" />

<aui:input classPK="<%= classPK %>" ignoreRequestValue="<%= changeStructure %>" name="tags" type="assetTags" />

<aui:input label="priority" name="assetPriority" type="text" value="<%= priority %>">
	<aui:validator name="number" />

	<aui:validator name="min">[0]</aui:validator>
</aui:input>

<aui:script>
	function <portlet:namespace />getSuggestionsContent() {
		return document.<portlet:namespace />fm1.<portlet:namespace />title_<%= defaultLanguageId %>.value;
	}
</aui:script>