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

<aui:script use="liferay-asset-portlet-category-selector">

	<portlet:resourceURL id="getCategories" var="resourceURL" />

	new Liferay.AssetPortletCategorySelector(
		{
			boundingBox: '#<portlet:namespace />listCategories',
			entries: {},
			entryIds: '<%= request.getParameter("selectedCategories") %>',
			eventName: '<%= HtmlUtil.escapeJS(assetCategoriesSelectorDisplayContext.getEventName()) %>',
			namespace: '<portlet:namespace />',
			singleSelect: '<%= request.getParameter("singleSelect") %>',
			url: '<%= resourceURL %>',
			vocabularyRootNode: [

				<%
				long vocabularyIds[] = assetCategoriesSelectorDisplayContext.getVocabularyIds();

				for (int i = 0; i < vocabularyIds.length; i++) {
					long vocabularyId = vocabularyIds[i];
				%>

					{
						alwaysShowHitArea: true,
						expanded: true,
						id: 'vocabulary<%= vocabularyId %>',
						label: '<%= assetCategoriesSelectorDisplayContext.getVocabularyTitle(vocabularyId) %>',
						leaf: false,
						type: 'io'
					}

					<c:if test="<%= (i + 1) < vocabularyIds.length %>">
						,
					</c:if>

				<%
				}
				%>

			]
		}
	).render();
</aui:script>