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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.CPFriendlyURLEntryException;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl;
import com.liferay.commerce.product.service.base.CPFriendlyURLEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPFriendlyURLEntryLocalServiceImpl
	extends CPFriendlyURLEntryLocalServiceBaseImpl {

	@Override
	public void addCPFriendlyURLEntries(
			long groupId, long companyId, Class<?> clazz, long classPK,
			Map<Locale, String> urlTitleMap)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		addCPFriendlyURLEntries(
			groupId, companyId, classNameId, classPK, urlTitleMap);
	}

	@Override
	public String buildUrlTitle(
		long groupId, long classNameId, long classPK, String languageId,
		String title) {

		int maxLength = ModelHintsUtil.getMaxLength(
			CPFriendlyURLEntry.class.getName(), "urlTitle");

		title = StringUtil.toLowerCase(title.trim());

		if (Validator.isNull(title) || Validator.isNumber(title)) {
			title = String.valueOf(classPK);
		}
		else if (title.length() > maxLength) {
			title = title.substring(0, maxLength);
		}
		else {
			title = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
				title);
		}

		String urlTitle = ModelHintsUtil.trimString(
			CPFriendlyURLEntry.class.getName(), "urlTitle", title);

		return getUniqueUrlTitle(
			groupId, classNameId, classPK, languageId, urlTitle);
	}

	@Override
	public void deleteCPFriendlyURLEntries(
		long groupId, Class<?> clazz, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		cpFriendlyURLEntryPersistence.removeByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long groupId, long classNameId, long classPK, String languageId,
		boolean main) {

		return cpFriendlyURLEntryPersistence.fetchByG_C_C_L_M(
			groupId, classNameId, classPK, languageId, main);
	}

	@Override
	public List<CPFriendlyURLEntry> getCPFriendlyURLEntries(
		long groupId, long classNameId, long classPK) {

		return cpFriendlyURLEntryPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public CPFriendlyURLEntry getCPFriendlyURLEntry(
			long groupId, long classNameId, String languageId, String urlTitle)
		throws PortalException {

		return cpFriendlyURLEntryPersistence.findByG_C_L_U(
			groupId, classNameId, languageId, urlTitle);
	}

	@Override
	public Map<String, String> getLanguageIdToUrlTitleMap(
		long groupId, long classNameId, long classPK) {

		Map<String, String> languageIdToUrlTitleMap = new HashMap<>();

		List<CPFriendlyURLEntry> cpFriendlyURLEntries =
			cpFriendlyURLEntryPersistence.findByG_C_C_M(
				groupId, classNameId, classPK, true);

		for (CPFriendlyURLEntry cpFriendlyURLEntry : cpFriendlyURLEntries) {
			languageIdToUrlTitleMap.put(
				cpFriendlyURLEntry.getLanguageId(),
				cpFriendlyURLEntry.getUrlTitle());
		}

		return languageIdToUrlTitleMap;
	}

	@Override
	public Map<Locale, String> getUrlTitleMap(
		long groupId, long classNameId, long classPK) {

		Map<Locale, String> urlTitleMap = new HashMap<>();

		List<CPFriendlyURLEntry> cpFriendlyURLEntries =
			cpFriendlyURLEntryPersistence.findByG_C_C_M(
				groupId, classNameId, classPK, true);

		for (CPFriendlyURLEntry cpFriendlyURLEntry : cpFriendlyURLEntries) {
			urlTitleMap.put(
				cpFriendlyURLEntry.getLocale(),
				cpFriendlyURLEntry.getUrlTitle());
		}

		return urlTitleMap;
	}

	@Override
	public String getUrlTitleMapAsXML(
		long groupId, long classNameId, long classPK,
		String defaultLanguageId) {

		Map<String, String> languageIdToUrlTitleMap =
			getLanguageIdToUrlTitleMap(groupId, classNameId, classPK);

		return LocalizationUtil.getXml(
			languageIdToUrlTitleMap, defaultLanguageId, "urlTitle");
	}

	protected void addCPFriendlyURLEntries(
			long groupId, long companyId, long classNameId, long classPK,
			Map<Locale, String> urlTitleMap)
		throws PortalException {

		for (Map.Entry<Locale, String> urlTitleEntry : urlTitleMap.entrySet()) {
			String languageId = LanguageUtil.getLanguageId(
				urlTitleEntry.getKey());

			if (Validator.isNull(urlTitleEntry.getValue())) {
				continue;
			}

			addCPFriendlyURLEntry(
				groupId, companyId, classNameId, classPK, languageId,
				urlTitleEntry.getValue());
		}
	}

	protected CPFriendlyURLEntry addCPFriendlyURLEntry(
			long groupId, long companyId, Class<?> clazz, long classPK,
			String languageId, String urlTitle)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return addCPFriendlyURLEntry(
			groupId, companyId, classNameId, classPK, languageId, urlTitle);
	}

	protected CPFriendlyURLEntry addCPFriendlyURLEntry(
			long groupId, long companyId, long classNameId, long classPK,
			String languageId, String urlTitle)
		throws PortalException {

		String normalizedUrlTitle =
			FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(urlTitle);

		validate(groupId, classNameId, classPK, languageId, normalizedUrlTitle);

		CPFriendlyURLEntry mainCPFriendlyURLEntry =
			cpFriendlyURLEntryPersistence.fetchByG_C_C_L_M(
				groupId, classNameId, classPK, languageId, true);

		if (mainCPFriendlyURLEntry != null) {
			mainCPFriendlyURLEntry.setMain(false);

			cpFriendlyURLEntryPersistence.update(mainCPFriendlyURLEntry);
		}

		CPFriendlyURLEntry oldCPFriendlyURLEntry =
			cpFriendlyURLEntryPersistence.fetchByG_C_C_L_U(
				groupId, classNameId, classPK, languageId, normalizedUrlTitle);

		if (oldCPFriendlyURLEntry != null) {
			oldCPFriendlyURLEntry.setMain(true);

			return cpFriendlyURLEntryPersistence.update(oldCPFriendlyURLEntry);
		}

		long cpFriendlyURLEntryId = counterLocalService.increment();

		CPFriendlyURLEntry cpFriendlyURLEntry = createCPFriendlyURLEntry(
			cpFriendlyURLEntryId);

		cpFriendlyURLEntry.setCompanyId(companyId);
		cpFriendlyURLEntry.setGroupId(groupId);
		cpFriendlyURLEntry.setClassNameId(classNameId);
		cpFriendlyURLEntry.setClassPK(classPK);
		cpFriendlyURLEntry.setLanguageId(languageId);
		cpFriendlyURLEntry.setUrlTitle(normalizedUrlTitle);
		cpFriendlyURLEntry.setMain(true);

		return cpFriendlyURLEntryPersistence.update(cpFriendlyURLEntry);
	}

	protected String getUniqueUrlTitle(
		long groupId, long classNameId, long classPK, String languageId,
		String urlTitle) {

		String normalizedUrlTitle =
			FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(urlTitle);

		int maxLength = ModelHintsUtil.getMaxLength(
			CPFriendlyURLEntry.class.getName(), "urlTitle");

		String curUrlTitle = normalizedUrlTitle.substring(
			0, Math.min(maxLength, normalizedUrlTitle.length()));

		for (int i = 1;; i++) {
			CPFriendlyURLEntry curCPFriendlyURLEntry =
				cpFriendlyURLEntryPersistence.fetchByG_C_L_U(
					groupId, classNameId, languageId, curUrlTitle);

			if ((curCPFriendlyURLEntry == null) ||
				(curCPFriendlyURLEntry.getClassPK() == classPK)) {

				break;
			}

			String suffix = StringPool.DASH + i;

			String prefix = normalizedUrlTitle.substring(
				0,
				Math.min(
					maxLength - suffix.length(), normalizedUrlTitle.length()));

			curUrlTitle = prefix + suffix;
		}

		return curUrlTitle;
	}

	protected Map<Locale, String> getUrlTitleMap(
		long groupId, Class<?> clazz, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return getUrlTitleMap(groupId, classNameId, classPK);
	}

	protected void validate(
			long groupId, long classNameId, long classPK, String languageId,
			String urlTitle)
		throws PortalException {

		int exceptionType = CPFriendlyURLEntryImpl.validate(urlTitle);

		if (exceptionType > 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"urlTitle: " + urlTitle + " is not valid " + exceptionType);
			}

			throw new CPFriendlyURLEntryException(exceptionType);
		}

		if (classPK > 0) {
			CPFriendlyURLEntry cpFriendlyURLEntry =
				cpFriendlyURLEntryPersistence.fetchByG_C_C_L_U(
					groupId, classNameId, classPK, languageId, urlTitle);

			if (cpFriendlyURLEntry != null) {
				return;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPFriendlyURLEntryLocalServiceImpl.class);

}