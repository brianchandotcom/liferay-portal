/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.item.selector;

import com.liferay.asset.categories.admin.web.internal.display.context.AssetCategoriesDisplayContext;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Date;
import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class AssetVocabularyItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public AssetVocabularyItemDescriptor(
		AssetCategoriesDisplayContext assetCategoriesDisplayContext,
		AssetVocabulary assetVocabulary) {

		_assetCategoriesDisplayContext = assetCategoriesDisplayContext;
		_assetVocabulary = assetVocabulary;
	}

	@Override
	public String getIcon() {
		return "vocabulary";
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return _assetVocabulary.getModifiedDate();
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"name", _assetVocabulary.getName()
		).put(
			"vocabularyId", _assetVocabulary.getVocabularyId()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		StringBundler sb = new StringBundler(11);

		sb.append(LanguageUtil.get(locale, "number-of-categories"));
		sb.append(StringPool.COLON);
		sb.append(StringPool.SPACE);
		sb.append(_assetVocabulary.getCategoriesCount());
		sb.append(StringPool.SPACE);
		sb.append(StringPool.DASH);
		sb.append(StringPool.SPACE);
		sb.append(LanguageUtil.get(locale, "asset-type"));
		sb.append(StringPool.COLON);
		sb.append(StringPool.SPACE);
		sb.append(
			_assetCategoriesDisplayContext.getAssetType(_assetVocabulary));

		return sb.toString();
	}

	@Override
	public String getTitle(Locale locale) {
		return _assetVocabulary.getTitle(locale);
	}

	@Override
	public long getUserId() {
		return _assetVocabulary.getUserId();
	}

	@Override
	public String getUserName() {
		return _assetVocabulary.getUserName();
	}

	private final AssetCategoriesDisplayContext _assetCategoriesDisplayContext;
	private final AssetVocabulary _assetVocabulary;

}