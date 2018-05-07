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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AssetVocabulary vocabulary = (AssetVocabulary)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= assetCategoriesDisplayContext.hasPermission(vocabulary, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editVocabularyURL">
			<portlet:param name="mvcPath" value="/edit_vocabulary.jsp" />
			<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabulary.getVocabularyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editVocabularyURL %>"
		/>
	</c:if>

	<c:if test="<%= assetCategoriesDisplayContext.isShowCategoriesAddButton() %>">
		<portlet:renderURL var="addCategoryURL">
			<portlet:param name="mvcPath" value="/edit_category.jsp" />
			<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabulary.getVocabularyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="add-category"
			url="<%= addCategoryURL %>"
		/>
	</c:if>

	<c:if test="<%= assetCategoriesDisplayContext.hasPermission(vocabulary, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= AssetVocabulary.class.getName() %>"
			modelResourceDescription="<%= vocabulary.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(vocabulary.getVocabularyId()) %>"
			var="permissionsVocabularyURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsVocabularyURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= assetCategoriesDisplayContext.hasPermission(vocabulary, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteVocabulary" var="deleteVocabularyURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabulary.getVocabularyId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteVocabularyURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>