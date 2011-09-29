<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
long categoryId = GetterUtil.getLong(ParamUtil.getString(request, "categoryId"));

String categoryTitle = null;
String vocabularyTitle = null;

if (categoryId != 0) {
	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(categoryId);

	assetCategory = assetCategory.toEscapedModel();

	categoryTitle = assetCategory.getTitle(locale);

	AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

	assetVocabulary = assetVocabulary.toEscapedModel();

	vocabularyTitle = assetVocabulary.getTitle(locale);
}
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-ui:header
	escapeXml="<%= false %>"
	localizeTitle="<%= false %>"
	title='<%= LanguageUtil.format(pageContext, "pages-with-x-x", new String[] {vocabularyTitle, categoryTitle}) %>'
/>

<liferay-util:include page="/html/portlet/wiki/page_iterator.jsp">
	<liferay-util:param name="type" value="categorized_pages" />
</liferay-util:include>