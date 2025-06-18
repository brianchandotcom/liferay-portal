/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetUtil {

	public static String[] getKeywords(String className, long classPK) {
		return TransformUtil.unsafeTransformToArray(
			AssetTagLocalServiceUtil.getTags(className, classPK),
			AssetTag::getName, String.class);
	}

	public static ItemExternalReference[]
		getTaxonomyCategoryItemExternalReferences(
			String className, long classPK, long groupId) {

		return TransformUtil.unsafeTransformToArray(
			AssetCategoryLocalServiceUtil.getCategories(className, classPK),
			assetCategory -> new ItemExternalReference() {
				{
					setClassName(() -> AssetCategory.class.getName());
					setExternalReferenceCode(
						assetCategory::getExternalReferenceCode);
					setScope(
						() -> _getScope(groupId, assetCategory.getGroupId()));
				}
			},
			ItemExternalReference.class);
	}

	private static Scope _getScope(long groupId, long scopeGroupId)
		throws Exception {

		if (groupId == scopeGroupId) {
			return null;
		}

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		return new Scope() {
			{
				setExternalReferenceCode(group::getExternalReferenceCode);
				setType(
					() -> {
						if (group.getType() == GroupConstants.TYPE_DEPOT) {
							return Scope.Type.ASSET_LIBRARY;
						}

						return Scope.Type.SITE;
					});
			}
		};
	}

}