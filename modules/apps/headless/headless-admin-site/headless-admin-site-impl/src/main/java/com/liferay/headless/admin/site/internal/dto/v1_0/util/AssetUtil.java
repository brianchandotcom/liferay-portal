/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.ParentTaxonomyCategory;
import com.liferay.headless.admin.site.dto.v1_0.ParentTaxonomyVocabulary;
import com.liferay.headless.admin.site.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetUtil {

	public static String[] getKeywords(String className, long classPK) {
		List<String> keywords = TransformUtil.transform(
			AssetTagLocalServiceUtil.getTags(className, classPK),
			AssetTag::getName);

		Collections.sort(keywords);

		return keywords.toArray(new String[0]);
	}

	public static TaxonomyCategoryBrief[] getTaxonomyCategoryBriefs(
		String className, long classPK, long groupId) {

		return TransformUtil.unsafeTransformToArray(
			AssetCategoryLocalServiceUtil.getCategories(className, classPK),
			assetCategory -> new TaxonomyCategoryBrief() {
				{
					setParentTaxonomyCategory(
						() -> {
							AssetCategory parentCategory =
								assetCategory.getParentCategory();

							if (parentCategory == null) {
								return null;
							}

							return new ParentTaxonomyCategory() {
								{
									setExternalReferenceCode(
										parentCategory::
											getExternalReferenceCode);
								}
							};
						});
					setParentTaxonomyVocabulary(
						() -> {
							AssetVocabulary assetVocabulary =
								AssetVocabularyLocalServiceUtil.
									getAssetVocabulary(
										assetCategory.getVocabularyId());

							if (assetVocabulary == null) {
								return null;
							}

							return new ParentTaxonomyVocabulary() {
								{
									setExternalReferenceCode(
										assetVocabulary::
											getExternalReferenceCode);
								}
							};
						});
					setScope(
						() -> ItemScopeUtil.getItemScope(
							assetCategory.getGroupId(), groupId));
					setTaxonomyCategoryExternalReferenceCode(
						assetCategory::getExternalReferenceCode);
				}
			},
			TaxonomyCategoryBrief.class);
	}

}