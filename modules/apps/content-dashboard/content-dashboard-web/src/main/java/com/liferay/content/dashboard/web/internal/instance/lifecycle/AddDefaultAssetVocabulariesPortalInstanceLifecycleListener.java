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

package com.liferay.content.dashboard.web.internal.instance.lifecycle;

import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddDefaultAssetVocabulariesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_addAssetVocabulary(
			company, PropsValues.ASSET_VOCABULARY_DEFAULT,
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC, null);
		_addAssetVocabulary(
			company, "audience",
			AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL,
			JournalArticle.class);
		_addAssetVocabulary(
			company, "stage", AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL,
			JournalArticle.class);
	}

	private void _addAssetVocabulary(
			Company company, String name, int visibilityType,
			Class<?> assetVocabularyTypeClass)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				company.getGroupId(), _getVocabularyName(name));

		if (assetVocabulary != null) {
			return;
		}

		User defaultUser = company.getDefaultUser();

		Map<Locale, String> titleMap = new HashMap<>();

		for (Locale locale :
				LanguageUtil.getCompanyAvailableLocales(
					company.getCompanyId())) {

			titleMap.put(
				locale,
				LanguageUtil.get(
					ResourceBundleUtil.getBundle(locale, getClass()), name));
		}

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		if (assetVocabularyTypeClass != null) {
			assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
				new long[] {_portal.getClassNameId(assetVocabularyTypeClass)},
				new long[] {AssetCategoryConstants.ALL_CLASS_TYPE_PK},
				new boolean[] {false});
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		_assetVocabularyLocalService.addVocabulary(
			defaultUser.getUserId(), company.getGroupId(), name,
			StringPool.BLANK, titleMap, Collections.emptyMap(),
			assetVocabularySettingsHelper.toString(), visibilityType,
			serviceContext);
	}

	private String _getVocabularyName(String vocabularyName) {
		if (vocabularyName != null) {
			vocabularyName = vocabularyName.trim();

			return StringUtil.toLowerCase(vocabularyName);
		}

		return StringPool.BLANK;
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private Portal _portal;

}