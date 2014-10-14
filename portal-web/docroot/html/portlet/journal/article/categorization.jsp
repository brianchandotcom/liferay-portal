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
String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

DDMStructure ddmStructure = (DDMStructure)request.getAttribute("edit_article.jsp-structure");
%>

<liferay-ui:error-marker key="errorSection" value="categorization" />

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<h3><liferay-ui:message key="categorization" /></h3>

<liferay-ui:asset-categories-error />

<liferay-ui:asset-tags-error />

<aui:fieldset>

	<%
	long classPK = 0;

	if (article != null) {
		classPK = article.getResourcePrimKey();

		if (!article.isApproved() && (article.getVersion() != JournalArticleConstants.VERSION_DEFAULT)) {
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), article.getPrimaryKey());

			if (assetEntry != null) {
				classPK = article.getPrimaryKey();
			}
		}
	}
	%>

	<aui:input classPK="<%= classPK %>" classTypePK="<%= ddmStructure.getStructureId() %>" name="categories" type="assetCategories" />

	<aui:input classPK="<%= classPK %>" name="tags" type="assetTags" />
</aui:fieldset>

<aui:script>
	function <portlet:namespace />getSuggestionsContent() {
		return document.<portlet:namespace />fm1.<portlet:namespace />title_<%= defaultLanguageId %>.value;
	}
</aui:script>