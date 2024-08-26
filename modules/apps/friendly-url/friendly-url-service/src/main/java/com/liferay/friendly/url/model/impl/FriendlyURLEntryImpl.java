/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.model.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Pavel Savinov
 * @author Roberto Díaz
 */
public class FriendlyURLEntryImpl extends FriendlyURLEntryBaseImpl {

	@Override
	public String getCategorizedUrlTitle(String languageId) {
		if (!FeatureFlagManagerUtil.isEnabled(getCompanyId(), "LPD-11147")) {
			return getUrlTitle();
		}

		String urlTitle = super.getUrlTitle(languageId, false);

		if (Validator.isNull(urlTitle)) {
			urlTitle = super.getUrlTitle(languageId, true);

			if (Validator.isNull(urlTitle)) {
				return StringPool.BLANK;
			}
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			FriendlyURLEntry.class.getName(), getFriendlyURLEntryId());

		if (assetEntry == null) {
			return urlTitle;
		}

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getEntryCategoriesByCategoryId(
				assetEntry.getEntryId(), false);

		if (assetCategories.isEmpty()) {
			return urlTitle;
		}

		StringBundler sb = new StringBundler(assetCategories.size() * 2);

		for (AssetCategory assetCategory : assetCategories) {
			sb.append(assetCategory.getTitle(languageId));
			sb.append(StringPool.SLASH);
		}

		sb.append(urlTitle);

		return sb.toString();
	}

	@Override
	public String getUrlTitle() {
		String urlTitle = super.getUrlTitle();

		if (!Validator.isBlank(urlTitle)) {
			return urlTitle;
		}

		Map<String, String> languageIdToUrlTitleMap =
			getLanguageIdToUrlTitleMap();

		if (languageIdToUrlTitleMap.isEmpty()) {
			return StringPool.BLANK;
		}

		Collection<String> urlTitles = languageIdToUrlTitleMap.values();

		Iterator<String> iterator = urlTitles.iterator();

		if (iterator.hasNext()) {
			return iterator.next();
		}

		return StringPool.BLANK;
	}

	@Override
	public String getUrlTitleMapAsXML() {
		return LocalizationUtil.getXml(
			getLanguageIdToUrlTitleMap(), getDefaultLanguageId(), "UrlTitle",
			true);
	}

	@Override
	public boolean isMain() throws PortalException {
		FriendlyURLEntry friendlyURLEntry =
			FriendlyURLEntryLocalServiceUtil.getMainFriendlyURLEntry(
				getClassNameId(), getClassPK());

		if (friendlyURLEntry.getPrimaryKey() == getPrimaryKey()) {
			return true;
		}

		return false;
	}

}