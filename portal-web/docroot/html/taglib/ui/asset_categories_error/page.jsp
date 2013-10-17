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

<%@ include file="/html/taglib/ui/asset_categories_error/init.jsp" %>

<liferay-ui:error exception="<%= AssetCategoryException.class %>">

	<%
	AssetCategoryException ace = (AssetCategoryException)errorException;

	AssetVocabulary vocabulary = ace.getVocabulary();

	String vocabularyTitle = StringPool.BLANK;

	if (vocabulary != null) {
		vocabularyTitle = vocabulary.getTitle(locale);
	}
	%>

	<c:choose>
		<c:when test="<%= ace.getType() == AssetCategoryException.AT_LEAST_ONE_CATEGORY %>">
			<liferay-ui:message key='<%= LanguageUtil.format(pageContext, "please-select-at-least-one-category-for-x", vocabularyTitle, false) %>' />
		</c:when>
		<c:when test="<%= ace.getType() == AssetCategoryException.TOO_MANY_CATEGORIES %>">
			<liferay-ui:message key='<%= LanguageUtil.format(pageContext, "you-cannot-select-more-than-one-category-for-x", vocabularyTitle, false) %>' />
		</c:when>
	</c:choose>
</liferay-ui:error>