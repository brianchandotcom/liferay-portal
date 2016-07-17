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

<div class="container-fluid-1280">
	<aui:fieldset-group>
		<aui:fieldset markupView="lexicon">
			<div class="lfr-categories-selector-list lfr-tags-selector-list" id="<portlet:namespace />listCategories">
			</div>
		</aui:fieldset>
	</aui:fieldset-group>
</div>

<portlet:resourceURL id="getCategories" var="resourceURL">
	<portlet:param name="vocabularyId" value="<%= String.valueOf(assetCategoriesSelectorDisplayContext.getVocabularyId()) %>" />
</portlet:resourceURL>

<aui:script use="liferay-asset-portlet-category-selector">
	var instanceCategorySelector = new Liferay.AssetPortletCategorySelector(
		{
			boundingBox: '#<portlet:namespace />listCategories',
			entries: {},
			entryIds: '<%= request.getParameter("selectedCategories") %>',
			eventName: '<%= HtmlUtil.escapeJS(assetCategoriesSelectorDisplayContext.getEventName()) %>',
			namespace: '<portlet:namespace />',
			singleSelect: '<%= request.getParameter("singleSelect") %>',
			url: '<%= resourceURL %>',
			vocabularyRootNode: {
				alwaysShowHitArea: true,
				id: 'vocabulary<%= assetCategoriesSelectorDisplayContext.getVocabularyId() %>',
				label: '<%= assetCategoriesSelectorDisplayContext.getVocabularyTitle() %>',
				leaf: false,
				type: 'io',
				expanded: true
			}
		}
	).render();
</aui:script>