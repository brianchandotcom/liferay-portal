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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.asset.model.AssetCategoryConstants;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * This class is intended to handle setting properties for asset vocabulary
 * model.
 * </p>
 *
 * <p>
 * It handles specific setting keys for this model, so avoid to set your custom
 * keys using UnicodeProperties methods.
 * </p>
 *
 * @author José Manuel Navarro
 */
public class AssetVocabularySettingsProperties extends UnicodeProperties {

	public AssetVocabularySettingsProperties() {
		super(true);
	}

	public void addAssociatedAssets(
		List<Long> assetClassNameIds, List<Long> assetClassTypeIds,
		List<Boolean> areRequired) {

		Set<String> selectedClassNameIds = new LinkedHashSet<String>();
		Set<String> requiredClassNameIds = new LinkedHashSet<String>();

		for (int i = 0; i < assetClassNameIds.size(); ++i) {
			long classNameId = assetClassNameIds.get(i);
			long classTypeId = assetClassTypeIds.get(i);
			boolean required = areRequired.get(i);

			StringBundler sb = new StringBundler(3);
			sb.append(classNameId);
			sb.append(StringPool.COLON);
			sb.append(classTypeId);

			String classNameAndType = sb.toString();

			if (classNameId == AssetCategoryConstants.ALL_CLASS_NAME_IDS) {
				selectedClassNameIds.clear();
				selectedClassNameIds.add(classNameAndType);

				if (required) {
					requiredClassNameIds.clear();
					requiredClassNameIds.add(classNameAndType);
				}

				break;
			}
			else {
				selectedClassNameIds.add(classNameAndType);

				if (required) {
					requiredClassNameIds.add(classNameAndType);
				}
			}
		}

		setProperty(
			KEY_SELECTED_CLASSNAMES, StringUtil.merge(selectedClassNameIds));
		setProperty(
			KEY_REQUIRED_CLASSNAMES, StringUtil.merge(requiredClassNameIds));
	}

	public void setMultiValued(boolean multiValued) {
		setProperty(KEY_MULTIVALUED, String.valueOf(multiValued));
	}

	private static final String KEY_MULTIVALUED = "multiValued";

	private static final String KEY_REQUIRED_CLASSNAMES =
		"requiredClassNameIds";

	private static final String KEY_SELECTED_CLASSNAMES =
		"selectedClassNameIds";

}