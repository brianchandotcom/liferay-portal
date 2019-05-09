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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.asset.kernel.model.AssetCategory" %><%@
page import="com.liferay.asset.kernel.model.AssetVocabulary" %><%@
page import="com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.AssetCategoryServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.AssetVocabularyServiceUtil" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %>

<%!
public long[] _filterCategoryIds(long vocabularyId, long[] categoryIds) {
	List<Long> filteredCategoryIds = new ArrayList<>();

	for (long categoryId : categoryIds) {
		AssetCategory category = AssetCategoryLocalServiceUtil.fetchCategory(categoryId);

		if (category == null) {
			continue;
		}

		if (category.getVocabularyId() == vocabularyId) {
			filteredCategoryIds.add(category.getCategoryId());
		}
	}

	return ArrayUtil.toArray(filteredCategoryIds.toArray(new Long[0]));
}

private String[] _getCategoryIdsTitles(String categoryIds, String categoryNames, long vocabularyId, ThemeDisplay themeDisplay) {
	if (Validator.isNotNull(categoryIds)) {
		long[] categoryIdsArray = GetterUtil.getLongValues(StringUtil.split(categoryIds));

		if (vocabularyId > 0) {
			categoryIdsArray = _filterCategoryIds(vocabularyId, categoryIdsArray);
		}

		categoryIds = StringPool.BLANK;
		categoryNames = StringPool.BLANK;

		if (categoryIdsArray.length > 0) {
			StringBundler categoryIdsSB = new StringBundler(categoryIdsArray.length * 2);
			StringBundler categoryNamesSB = new StringBundler(categoryIdsArray.length * 2);

			for (long categoryId : categoryIdsArray) {
				AssetCategory category = AssetCategoryLocalServiceUtil.fetchCategory(categoryId);

				if (category == null) {
					continue;
				}

				categoryIdsSB.append(categoryId);
				categoryIdsSB.append(StringPool.COMMA);

				categoryNamesSB.append(category.getTitle(themeDisplay.getLocale()));
				categoryNamesSB.append("_CATEGORY_");
			}

			if (categoryIdsSB.index() > 0) {
				categoryIdsSB.setIndex(categoryIdsSB.index() - 1);
				categoryNamesSB.setIndex(categoryNamesSB.index() - 1);

				categoryIds = categoryIdsSB.toString();
				categoryNames = categoryNamesSB.toString();
			}
		}
	}

	return new String[] {categoryIds, categoryNames};
}
%>