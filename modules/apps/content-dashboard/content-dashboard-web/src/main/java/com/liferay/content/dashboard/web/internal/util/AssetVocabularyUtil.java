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

package com.liferay.content.dashboard.web.internal.util;

import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Cristina González
 */
public class AssetVocabularyUtil {

	public static AssetVocabulary addAssetVocabulary(
			AssetVocabularyLocalService assetVocabularyLocalService,
			List<Long> classNameIdsList, Company company,
			String assetVocabularyName, int visibilityType)
		throws PortalException {

		if (assetVocabularyName == null) {
			throw new IllegalArgumentException(
				"assetVocabularyName should not be null");
		}

		AssetVocabulary assetVocabulary =
			assetVocabularyLocalService.fetchGroupVocabulary(
				company.getGroupId(),
				StringUtil.toLowerCase(assetVocabularyName));

		if (assetVocabulary == null) {
			return _addAssetVocabulary(
				assetVocabularyLocalService, assetVocabularyName,
				classNameIdsList, company, visibilityType);
		}

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			_getAssetVocabularySettingsHelper(
				assetVocabulary, classNameIdsList);

		return assetVocabularyLocalService.updateVocabulary(
			assetVocabulary.getVocabularyId(), assetVocabulary.getTitleMap(),
			assetVocabulary.getDescriptionMap(),
			assetVocabularySettingsHelper.toString(), visibilityType);
	}

	private static AssetVocabulary _addAssetVocabulary(
			AssetVocabularyLocalService assetVocabularyLocalService,
			String assetVocabularyName, List<Long> classNameIdsList,
			Company company, int visibilityType)
		throws PortalException {

		User user = company.getDefaultUser();

		Map<Locale, String> titleMap = new HashMap<>();

		for (Locale locale :
				LanguageUtil.getCompanyAvailableLocales(
					company.getCompanyId())) {

			titleMap.put(locale, LanguageUtil.get(locale, assetVocabularyName));
		}

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		if (classNameIdsList != null) {
			long[] classNameIds = new long[classNameIdsList.size()];
			long[] classTypePKs = new long[classNameIdsList.size()];
			boolean[] requireds = new boolean[classNameIdsList.size()];

			for (int i = 0; i < classNameIdsList.size(); i++) {
				classNameIds[i] = classNameIdsList.get(i);
				classTypePKs[i] = AssetCategoryConstants.ALL_CLASS_TYPE_PK;
				requireds[i] = false;
			}

			assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
				classNameIds, classTypePKs, requireds);
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return assetVocabularyLocalService.addVocabulary(
			null, user.getUserId(), company.getGroupId(), assetVocabularyName,
			StringPool.BLANK, titleMap, Collections.emptyMap(),
			assetVocabularySettingsHelper.toString(), visibilityType,
			serviceContext);
	}

	private static AssetVocabularySettingsHelper
		_getAssetVocabularySettingsHelper(
			AssetVocabulary assetVocabulary, List<Long> classNameIdsList) {

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		Set<Long> classNameIds = SetUtil.fromArray(
			assetVocabulary.getSelectedClassNameIds());

		classNameIds.addAll(classNameIdsList);

		long[] selectedClassNameIds = ArrayUtil.toArray(
			classNameIds.toArray(new Long[0]));

		long[] classTypePKs = new long[selectedClassNameIds.length];
		boolean[] requireds = new boolean[selectedClassNameIds.length];

		for (int i = 0; i < selectedClassNameIds.length; i++) {
			classTypePKs[i] = AssetCategoryConstants.ALL_CLASS_TYPE_PK;
			requireds[i] = false;
		}

		assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			selectedClassNameIds, classTypePKs, requireds);

		return assetVocabularySettingsHelper;
	}

}