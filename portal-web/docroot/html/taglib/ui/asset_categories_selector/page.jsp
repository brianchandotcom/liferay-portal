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

<%@ include file="/html/taglib/ui/asset_categories_selector/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_asset_categories_selector_page") + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:asset-categories-selector:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-categories-selector:classPK"));
String hiddenInput = (String)request.getAttribute("liferay-ui:asset-categories-selector:hiddenInput");
String curCategoryIds = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-categories-selector:curCategoryIds"), "");
String curCategoryNames = StringPool.BLANK;

if (Validator.isNotNull(className)) {
	long classNameId = PortalUtil.getClassNameId(className);

	List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>();

	Group group = themeDisplay.getScopeGroup();

	if (group.isLayout()) {
		vocabularies.addAll(AssetVocabularyLocalServiceUtil.getGroupVocabularies(group.getParentGroupId(), false));
	}
	else {
		vocabularies.addAll(AssetVocabularyLocalServiceUtil.getGroupVocabularies(scopeGroupId, false));
	}

	if (scopeGroupId != themeDisplay.getCompanyGroupId()) {
		vocabularies.addAll(AssetVocabularyLocalServiceUtil.getGroupVocabularies(themeDisplay.getCompanyGroupId(), false));
	}

	for (AssetVocabulary vocabulary : vocabularies) {
		int vocabularyCategoriesCount = AssetCategoryLocalServiceUtil.getVocabularyCategoriesCount(vocabulary.getVocabularyId());

		if (vocabularyCategoriesCount == 0) {
			continue;
		}

		UnicodeProperties settingsProperties = vocabulary.getSettingsProperties();

		long[] selectedClassNameIds = StringUtil.split(settingsProperties.getProperty("selectedClassNameIds"), 0L);

		if ((selectedClassNameIds.length > 0) && (selectedClassNameIds[0] != AssetCategoryConstants.ALL_CLASS_NAME_IDS) && !ArrayUtil.contains(selectedClassNameIds, classNameId)) {
			continue;
		}

		if (Validator.isNotNull(className) && (classPK > 0)) {
			List<AssetCategory> categories = AssetCategoryLocalServiceUtil.getCategories(className, classPK);

			curCategoryIds = ListUtil.toString(categories, AssetCategory.CATEGORY_ID_ACCESSOR);
			curCategoryNames = ListUtil.toString(categories, AssetCategory.NAME_ACCESSOR);
		}

		String curCategoryIdsParam = request.getParameter(hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId());

		if (curCategoryIdsParam != null) {
			curCategoryIds = curCategoryIdsParam;
			curCategoryNames = StringPool.BLANK;
		}

		String[] categoryIdsNames = _getCategoryIdsNames(curCategoryIds, curCategoryNames, vocabulary.getVocabularyId());
	%>

		<span class="aui-field-content">
			<label class="aui-field-label" id="<%= namespace %>assetCategoriesLabel_<%= vocabulary.getVocabularyId() %>">
				<%= vocabulary.getTitle(locale) %>

				<c:if test="<%= vocabulary.getGroupId() == themeDisplay.getCompanyGroupId() %>">
					(<liferay-ui:message key="global" />)
				</c:if>

				<c:if test="<%= vocabulary.isRequired(classNameId) %>">
					<span class="aui-label-required">(<liferay-ui:message key="required" />)</span>
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
					curEntries: '<%= HtmlUtil.escapeJS(categoryIdsNames[1]) %>',
					curEntryIds: '<%= categoryIdsNames[0] %>',
					hiddenInput: '#<%= namespace + hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId() %>',
					instanceVar: '<%= namespace + randomNamespace %>',
					labelNode: '#<%= namespace %>assetCategoriesLabel_<%= vocabulary.getVocabularyId() %>',
					portalModelResource: <%= Validator.isNotNull(className) && (ResourceActionsUtil.isPortalModelResource(className) || className.equals(Group.class.getName())) %>,
					singleSelect: <%= !vocabulary.isMultiValued() %>,
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

	String[] categoryIdsNames = _getCategoryIdsNames(curCategoryIds, curCategoryNames, 0);
%>

	<div class="lfr-tags-selector-content" id="<%= namespace + randomNamespace %>assetCategoriesSelector">
		<aui:input name="<%= hiddenInput %>" type="hidden" />
	</div>

	<aui:script use="liferay-asset-categories-selector">
		new Liferay.AssetCategoriesSelector(
			{
				className: '<%= className %>',
				contentBox: '#<%= namespace + randomNamespace %>assetCategoriesSelector',
				curEntries: '<%= HtmlUtil.escapeJS(categoryIdsNames[1]) %>',
				curEntryIds: '<%= categoryIdsNames[0] %>',
				hiddenInput: '#<%= namespace + hiddenInput %>',
				instanceVar: '<%= namespace + randomNamespace %>',
				portalModelResource: <%= Validator.isNotNull(className) && (ResourceActionsUtil.isPortalModelResource(className) || className.equals(Group.class.getName())) %>
			}
		).render();
	</aui:script>

<%
}
%>

<%!
private long[] _filterCategoryIds(long vocabularyId, long[] categoryIds) throws PortalException, SystemException {
	List<Long> filteredCategoryIds = new ArrayList<Long>();

	for (long categoryId : categoryIds) {
		AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(categoryId);

		if (category.getVocabularyId() == vocabularyId) {
			filteredCategoryIds.add(category.getCategoryId());
		}
	}

	return ArrayUtil.toArray(filteredCategoryIds.toArray(new Long[filteredCategoryIds.size()]));
}

private String[] _getCategoryIdsNames(String categoryIds, String categoryNames, long vocabularyId) throws PortalException, SystemException {
	if (Validator.isNotNull(categoryIds)) {
		long[] categoryIdsArray = GetterUtil.getLongValues(StringUtil.split(categoryIds));

		if (vocabularyId > 0) {
			categoryIdsArray = _filterCategoryIds(vocabularyId, categoryIdsArray);
		}

		if (categoryIdsArray.length == 0) {
			categoryIds = StringPool.BLANK;
			categoryNames = StringPool.BLANK;
		}
		else {
			StringBundler sb = new StringBundler(categoryIdsArray.length * 2);

			for (long categoryId : categoryIdsArray) {
				AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(categoryId);

				category = category.toEscapedModel();

				sb.append(category.getName());
				sb.append(_CATEGORY_SEPARATOR);
			}

			sb.setIndex(sb.index() - 1);

			categoryIds = StringUtil.merge(categoryIdsArray);
			categoryNames = sb.toString();
		}
	}

	return new String[] {categoryIds, categoryNames};
}

private static final String _CATEGORY_SEPARATOR = "_CATEGORY_";
%>