/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetCategoryImpl extends AssetCategoryBaseImpl {

	@Override
	public List<AssetCategory> getAncestors() throws PortalException {
		List<AssetCategory> categories = new ArrayList<>();

		AssetCategory category = this;

		while (!category.isRootCategory()) {
			category = AssetCategoryLocalServiceUtil.getAssetCategory(
				category.getParentCategoryId());

			categories.add(category);
		}

		return categories;
	}

	@Override
	public AssetCategory getParentCategory() {
		return AssetCategoryLocalServiceUtil.fetchCategory(
			getParentCategoryId());
	}

	@Override
	public String getPath(Locale locale) throws PortalException {
		return getPath(locale, false);
	}

	@Override
	public String getPath(Locale locale, boolean reverse)
		throws PortalException {

		List<AssetCategory> categories = getAncestors();

		StringBundler sb = new StringBundler((categories.size() * 4) + 1);

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.getVocabulary(getVocabularyId());

		sb.append(vocabulary.getTitle(locale));

		if (reverse) {
			Collections.reverse(categories);
		}

		for (AssetCategory category : categories) {
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
			sb.append(category.getTitle(locale));
		}

		return sb.toString();
	}

	@Override
	public String getTitle(String languageId) {
		String value = super.getTitle(languageId);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		String value = super.getTitle(languageId, useDefault);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	@Override
	public boolean isRootCategory() {
		if (getParentCategoryId() == 0) {
			return true;
		}

		return false;
	}

}