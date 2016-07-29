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
String eventName = ParamUtil.getString(request, "eventName", renderResponse.getNamespace() + "selectCategory");

long categoryId = ParamUtil.getLong(request, "categoryId");

AssetCategory category = AssetCategoryLocalServiceUtil.fetchCategory(categoryId);

AssetCategory parentCategory = category.getParentCategory();

long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

List<AssetVocabulary> vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(scopeGroupId);
%>

<aui:form cssClass="container-fluid-1280" name="fm">
	<aui:input name="categoryId" type="hidden" value="<%= categoryId %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:select label="vocabulary" name="vocabularyId">

				<%
				for (AssetVocabulary vocabulary : vocabularies) {
				%>

					<aui:option label="<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>" selected="<%= vocabulary.getVocabularyId() == vocabularyId %>" value="<%= vocabulary.getVocabularyId() %>" />

				<%
				}
				%>

			</aui:select>

			<label><liferay-ui:message key="parent-category" /></label>

			<div class="lfr-tags-selector-content">
				<aui:input name="parentCategoryId" type="hidden" />

				<%
				for (AssetVocabulary curVocabulary : vocabularies) {
				%>

					<div class="asset-category-selector <%= (curVocabulary.getVocabularyId() == vocabularyId) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />assetCategoriesSelector<%= curVocabulary.getVocabularyId() %>"></div>

					<aui:script use="liferay-asset-categories-selector">
						new Liferay.AssetCategoriesSelector(
							{
								contentBox: '#<portlet:namespace />assetCategoriesSelector<%= curVocabulary.getVocabularyId() %>',

								<c:if test="<%= (curVocabulary.getVocabularyId() == vocabularyId) && (parentCategory != null) %>">
									curEntries: '<%= parentCategory.getTitle(locale) %>',
									curEntryIds: '<%= parentCategory.getCategoryId() %>',
								</c:if>

								hiddenInput: '#<portlet:namespace />parentCategoryId',
								instanceVar: '<portlet:namespace />',
								label: '<%= UnicodeLanguageUtil.format(request, "select-x", curVocabulary.getTitle(locale), false) %>',
								singleSelect: <%= true %>,
								vocabularyGroupIds: '<%= scopeGroupId %>',
								vocabularyIds: '<%= curVocabulary.getVocabularyId() %>'
							}
						).render();
					</aui:script>

				<%
				}
				%>

			</div>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg btn-primary" id="move" value="move" />

		<aui:button cssClass="btn-lg" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="liferay-asset-categories-selector">
	var parentCategoryId = A.one('#<portlet:namespace />parentCategoryId');

	var previousSelector = A.one('#<portlet:namespace />assetCategoriesSelector<%= vocabularyId %>');

	A.one('#<portlet:namespace />vocabularyId').on(
		'change',
		function(event) {
			A.all('.asset-category-selector').hide();

			previousSelector.attr('data-selectedId', parentCategoryId.val());

			previousSelector = A.one('#<portlet:namespace />assetCategoriesSelector' + event.currentTarget.val());

			previousSelector.show();

			parentCategoryId.val(previousSelector.attr('data-selectedId') || '');
		}
	);

	A.one('#<portlet:namespace />move').on(
		'click',
		function(event) {
			if (parentCategoryId.val() != document.<portlet:namespace />fm.<portlet:namespace />categoryId.value) {
				Liferay.Util.getOpener().Liferay.fire(
					'<%= HtmlUtil.escapeJS(eventName) %>',
					{
						data: {
							categoryId: <%= categoryId %>,
							parentCategoryId: parentCategoryId.val(),
							vocabularyId: A.one('#<portlet:namespace />vocabularyId').val()
						}
					}
				);

				Liferay.Util.getWindow().hide();
			}
			else {
				alert('<liferay-ui:message arguments="<%= category.getTitle(locale) %>" key="the-category-x-and-parent-category-should-not-be-the-same" />');
			}
		}
	);
</aui:script>