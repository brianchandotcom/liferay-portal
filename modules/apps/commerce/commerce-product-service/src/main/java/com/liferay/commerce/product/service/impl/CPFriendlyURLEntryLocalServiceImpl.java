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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl;
import com.liferay.commerce.product.service.base.CPFriendlyURLEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 * 					 com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalServiceImpl}
 */
@Deprecated
public class CPFriendlyURLEntryLocalServiceImpl
	extends CPFriendlyURLEntryLocalServiceBaseImpl {

	@Override
	public void addCPFriendlyURLEntries(
			long groupId, long companyId, Class<?> clazz, long classPK,
			Map<Locale, String> urlTitleMap)
		throws PortalException {

		addCPFriendlyURLEntries(
			groupId, companyId, classNameLocalService.getClassNameId(clazz),
			classPK, urlTitleMap);
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
			title = FriendlyURLNormalizerUtil.normalizeWithEncoding(title);
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

		if ((clazz == CPDefinition.class) &&
			cpDefinitionLocalService.isVersionable(classPK)) {

			try {
				CPDefinition newCPDefinition =
					cpDefinitionLocalService.copyCPDefinition(classPK);

				classPK = newCPDefinition.getCPDefinitionId();
			}
			catch (PortalException pe) {
				throw new SystemException(pe);
			}
		}

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
	public CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long groupId, long classNameId, String languageId, String urlTitle) {

		return cpFriendlyURLEntryPersistence.fetchByG_C_L_U(
			groupId, classNameId, languageId, urlTitle);
	}

	@Override
	public List<CPFriendlyURLEntry> getCPFriendlyURLEntries(
		long groupId, long classNameId, long classPK) {

		return cpFriendlyURLEntryPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public List<CPFriendlyURLEntry> getCPFriendlyURLEntries(
		long groupId, long classNameId, String urlTitle) {

		return cpFriendlyURLEntryPersistence.findByG_C_U(
			groupId, classNameId, urlTitle);
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
			if (Validator.isNull(urlTitleEntry.getValue())) {
				continue;
			}

			String languageId = LanguageUtil.getLanguageId(
				urlTitleEntry.getKey());

			addCPFriendlyURLEntry(
				groupId, companyId, classNameId, classPK, languageId,
				urlTitleEntry.getValue());
		}
	}

	protected CPFriendlyURLEntry addCPFriendlyURLEntry(
			long groupId, long companyId, Class<?> clazz, long classPK,
			String languageId, String urlTitle)
		throws PortalException {

		return addCPFriendlyURLEntry(
			groupId, companyId, classNameLocalService.getClassNameId(clazz),
			classPK, languageId, urlTitle);
	}

	protected CPFriendlyURLEntry addCPFriendlyURLEntry(
			long groupId, long companyId, long classNameId, long classPK,
			String languageId, String urlTitle)
		throws PortalException {

		String normalizedUrlTitle =
			FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

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

		cpFriendlyURLEntry.setGroupId(groupId);
		cpFriendlyURLEntry.setCompanyId(companyId);
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
			FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

		int maxLength = ModelHintsUtil.getMaxLength(
			CPFriendlyURLEntry.class.getName(), "urlTitle");

		String curUrlTitle = normalizedUrlTitle.substring(
			0, Math.min(maxLength, normalizedUrlTitle.length()));

		CPFriendlyURLEntry curCPFriendlyURLEntry =
			cpFriendlyURLEntryPersistence.fetchByG_C_L_U(
				groupId, classNameId, languageId, curUrlTitle);

		if ((curCPFriendlyURLEntry == null) ||
			(curCPFriendlyURLEntry.getClassPK() == classPK)) {

			return curUrlTitle;
		}

		return curUrlTitle + StringPool.DASH + PortalUUIDUtil.generate();
	}

	protected Map<Locale, String> getUrlTitleMap(
		long groupId, Class<?> clazz, long classPK) {

		return getUrlTitleMap(
			groupId, classNameLocalService.getClassNameId(clazz), classPK);
	}

	protected void validate(
			long groupId, long classNameId, long classPK, String languageId,
			String urlTitle)
		throws PortalException {

		int exceptionType = CPFriendlyURLEntryImpl.validate(urlTitle);

		if (exceptionType > 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"urlTitle: ", urlTitle, " is not valid ",
						exceptionType));
			}

			throw new CPFriendlyURLEntryException(exceptionType);
		}

		if (classPK > 0) {
			cpFriendlyURLEntryPersistence.fetchByG_C_C_L_U(
				groupId, classNameId, classPK, languageId, urlTitle);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPFriendlyURLEntryLocalServiceImpl.class);

}