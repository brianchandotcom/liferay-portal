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

<%@ include file="/html/taglib/ui/asset_categories_selector/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_asset_categories_selector_page") + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:asset-categories-selector:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-categories-selector:classPK"));
long classTypePK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-categories-selector:classTypePK"));
String hiddenInput = (String)request.getAttribute("liferay-ui:asset-categories-selector:hiddenInput");
String curCategoryIds = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-categories-selector:curCategoryIds"), "");
String curCategoryNames = StringPool.BLANK;
int maxEntries = GetterUtil.getInteger(PropsUtil.get(PropsKeys.ASSET_CATEGORIES_SELECTOR_MAX_ENTRIES));

long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

List<AssetVocabulary> vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(groupIds);

if (Validator.isNotNull(className)) {
	vocabularies = AssetUtil.filterVocabularies(vocabularies, className, classTypePK);

	long classNameId = PortalUtil.getClassNameId(className);

	for (AssetVocabulary vocabulary : vocabularies) {
		vocabulary = vocabulary.toEscapedModel();

		if (AssetCategoryServiceUtil.getVocabularyCategoriesCount(vocabulary.getGroupId(), vocabulary.getVocabularyId()) == 0) {
			continue;
		}

		if (Validator.isNotNull(className) && (classPK > 0)) {
			List<AssetCategory> categories = AssetCategoryServiceUtil.getCategories(className, classPK);

			curCategoryIds = ListUtil.toString(categories, AssetCategory.CATEGORY_ID_ACCESSOR);
			curCategoryNames = ListUtil.toString(categories, AssetCategory.NAME_ACCESSOR);
		}

		String curCategoryIdsParam = request.getParameter(hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId());

		if (Validator.isNotNull(curCategoryIdsParam)) {
			curCategoryIds = curCategoryIdsParam;
			curCategoryNames = StringPool.BLANK;
		}

		String[] categoryIdsTitles = AssetCategoryUtil.getCategoryIdsTitles(curCategoryIds, curCategoryNames, vocabulary.getVocabularyId(), themeDisplay);
	%>

		<span class="field-content">
			<label id="<%= namespace %>assetCategoriesLabel_<%= vocabulary.getVocabularyId() %>">
				<%= vocabulary.getTitle(locale) %>

				<c:if test="<%= vocabulary.getGroupId() != themeDisplay.getSiteGroupId() %>">

					<%
					Group vocabularyGroup = GroupLocalServiceUtil.getGroup(vocabulary.getGroupId());
					%>

					(<%= vocabularyGroup.getDescriptiveName(locale) %>)
				</c:if>

				<c:if test="<%= vocabulary.isRequired(classNameId, classTypePK) %>">
					<span class="label-required"><liferay-ui:message key="required" /></span>
				</c:if>
			</label>

			<div class="lfr-tags-selector-content" id="<%= namespace + randomNamespace %>assetCategoriesSelector_<%= vocabulary.getVocabularyId() %>">
				<aui:input name="<%= hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId() %>" type="hidden" />
			</div>
		</span>

		<aui:script use="liferay-asset-categories-selector">
			new Liferay.AssetCategoriesSelector(
				{
					className: '<%= className %>',
					contentBox: '#<%= namespace + randomNamespace %>assetCategoriesSelector_<%= vocabulary.getVocabularyId() %>',
					curEntries: '<%= HtmlUtil.escapeJS(categoryIdsTitles[1]) %>',
					curEntryIds: '<%= categoryIdsTitles[0] %>',
					hiddenInput: '#<%= namespace + hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId() %>',
					instanceVar: '<%= namespace + randomNamespace %>',
					labelNode: '#<%= namespace %>assetCategoriesLabel_<%= vocabulary.getVocabularyId() %>',
					maxEntries: <%= maxEntries %>,
					moreResultsLabel: '<%= UnicodeLanguageUtil.get(pageContext, "load-more-results") %>',
					portalModelResource: <%= Validator.isNotNull(className) && (ResourceActionsUtil.isPortalModelResource(className) || className.equals(Group.class.getName())) %>,
					singleSelect: <%= !vocabulary.isMultiValued() %>,
					title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", vocabulary.getTitle(locale), false) %>',
					vocabularyGroupIds: '<%= StringUtil.merge(groupIds) %>',
					vocabularyIds: '<%= String.valueOf(vocabulary.getVocabularyId()) %>'
				}
			).render();
		</aui:script>

	<%
	}
}
else {
	String curCategoryIdsParam = request.getParameter(hiddenInput);

	if (curCategoryIdsParam != null) {
		curCategoryIds = curCategoryIdsParam;
	}

	String[] categoryIdsTitles = AssetCategoryUtil.getCategoryIdsTitles(curCategoryIds, curCategoryNames, 0, themeDisplay);
%>

	<div class="lfr-tags-selector-content" id="<%= namespace + randomNamespace %>assetCategoriesSelector">
		<aui:input name="<%= hiddenInput %>" type="hidden" />
	</div>

	<aui:script use="liferay-asset-categories-selector">
		new Liferay.AssetCategoriesSelector(
			{
				className: '<%= className %>',
				contentBox: '#<%= namespace + randomNamespace %>assetCategoriesSelector',
				curEntries: '<%= HtmlUtil.escapeJS(categoryIdsTitles[1]) %>',
				curEntryIds: '<%= categoryIdsTitles[0] %>',
				hiddenInput: '#<%= namespace + hiddenInput %>',
				instanceVar: '<%= namespace + randomNamespace %>',
				maxEntries: <%= maxEntries %>,
				moreResultsLabel: '<%= UnicodeLanguageUtil.get(pageContext, "load-more-results") %>',
				portalModelResource: <%= Validator.isNotNull(className) && (ResourceActionsUtil.isPortalModelResource(className) || className.equals(Group.class.getName())) %>,
				vocabularyGroupIds: '<%= StringUtil.merge(groupIds) %>',
				vocabularyIds: '<%= ListUtil.toString(vocabularies, "vocabularyId") %>'
			}
		).render();
	</aui:script>

<%
}
%>