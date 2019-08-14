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

package com.liferay.layout.internal.asset.validator;

import com.liferay.asset.kernel.validator.AssetEntryValidatorExclusionRule;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.sites.kernel.util.Sites;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.model.Layout",
	service = AssetEntryValidatorExclusionRule.class
)
public class LayoutAssetEntryValidatorExclusionRule
	implements AssetEntryValidatorExclusionRule {

	@Override
	public boolean isValidationExcluded(
		long groupId, String className, long classPK, long classTypePK,
		long[] categoryIds, String[] tagNames) {

		if (MergeLayoutPrototypesThreadLocal.isInProgress()) {
			return true;
		}

		Layout layout = _layoutLocalService.fetchLayout(classPK);

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		boolean layoutUpdateable = GetterUtil.getBoolean(
			typeSettingsProperties.get(Sites.LAYOUT_UPDATEABLE));

		if (!layoutUpdateable) {
			return true;
		}

		return false;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}