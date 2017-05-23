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

package com.liferay.commerce.product.type.virtual.service.impl;

import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingFileEntryIdException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleFileEntryIdException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleUrlException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseRequiredException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingUrlException;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.base.CPDefinitionVirtualSettingLocalServiceBaseImpl;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionVirtualSettingLocalServiceImpl
	extends CPDefinitionVirtualSettingLocalServiceBaseImpl {

	@Override
	public CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
			long cpDefinitionId, boolean useFileEntry, long fileEntryId,
			String url, String activationStatus, long duration, int maxUsages,
			boolean useSample, boolean useSampleFileEntry,
			long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePK,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		if (useFileEntry) {
			url = null;
		}
		else {
			fileEntryId = 0;
		}

		if (useSample) {
			if (useSampleFileEntry) {
				sampleUrl = null;
			}
			else {
				sampleFileEntryId = 0;
			}
		}
		else {
			sampleUrl = null;
			sampleFileEntryId = 0;
		}

		if (termsOfUseRequired) {
			if (termsOfUseJournalArticleResourcePK > 0) {
				termsOfUseContentMap = null;
			}
			else {
				termsOfUseJournalArticleResourcePK = 0;
			}
		}

		validate(
			useFileEntry, fileEntryId, url, useSample, useSampleFileEntry,
			sampleFileEntryId, sampleUrl, termsOfUseRequired,
			termsOfUseContentMap, termsOfUseJournalArticleResourcePK);

		long cpDefinitionVirtualSettingId = counterLocalService.increment();

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingPersistence.create(
				cpDefinitionVirtualSettingId);

		cpDefinitionVirtualSetting.setGroupId(groupId);
		cpDefinitionVirtualSetting.setCompanyId(user.getCompanyId());
		cpDefinitionVirtualSetting.setUserId(user.getUserId());
		cpDefinitionVirtualSetting.setUserName(user.getFullName());
		cpDefinitionVirtualSetting.setCPDefinitionId(cpDefinitionId);
		cpDefinitionVirtualSetting.setFileEntryId(fileEntryId);
		cpDefinitionVirtualSetting.setUrl(url);
		cpDefinitionVirtualSetting.setActivationStatus(activationStatus);
		cpDefinitionVirtualSetting.setDuration(duration);
		cpDefinitionVirtualSetting.setMaxUsages(maxUsages);
		cpDefinitionVirtualSetting.setUseSample(useSample);
		cpDefinitionVirtualSetting.setSampleFileEntryId(sampleFileEntryId);
		cpDefinitionVirtualSetting.setSampleUrl(sampleUrl);
		cpDefinitionVirtualSetting.setTermsOfUseRequired(termsOfUseRequired);
		cpDefinitionVirtualSetting.setTermsOfUseContentMap(
			termsOfUseContentMap);
		cpDefinitionVirtualSetting.setTermsOfUseJournalArticleResourcePK(
			termsOfUseJournalArticleResourcePK);
		cpDefinitionVirtualSetting.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionVirtualSettingPersistence.update(
			cpDefinitionVirtualSetting);

		return cpDefinitionVirtualSetting;
	}

	@Override
	public CPDefinitionVirtualSetting getCPDefinitionVirtualSetting(
			long cpDefinitionId)
		throws PortalException {

		return cpDefinitionVirtualSettingPersistence.findByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
			long cpDefinitionVirtualSettingId, boolean useFileEntry,
			long fileEntryId, String url, String activationStatus,
			long duration, int maxUsages, boolean useSample,
			boolean useSampleFileEntry, long sampleFileEntryId,
			String sampleUrl, boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePK,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingPersistence.findByPrimaryKey(
				cpDefinitionVirtualSettingId);

		if (useFileEntry) {
			url = null;
		}
		else {
			fileEntryId = 0;
		}

		if (useSample) {
			if (useSampleFileEntry) {
				sampleUrl = null;
			}
			else {
				sampleFileEntryId = 0;
			}
		}
		else {
			sampleUrl = null;
			sampleFileEntryId = 0;
		}

		if (termsOfUseRequired) {
			if (termsOfUseJournalArticleResourcePK > 0) {
				termsOfUseContentMap = null;
			}
			else {
				termsOfUseJournalArticleResourcePK = 0;
			}
		}

		validate(
			useFileEntry, fileEntryId, url, useSample, useSampleFileEntry,
			sampleFileEntryId, sampleUrl, termsOfUseRequired,
			termsOfUseContentMap, termsOfUseJournalArticleResourcePK);

		cpDefinitionVirtualSetting.setFileEntryId(fileEntryId);
		cpDefinitionVirtualSetting.setUrl(url);
		cpDefinitionVirtualSetting.setActivationStatus(activationStatus);
		cpDefinitionVirtualSetting.setDuration(duration);
		cpDefinitionVirtualSetting.setMaxUsages(maxUsages);
		cpDefinitionVirtualSetting.setUseSample(useSample);
		cpDefinitionVirtualSetting.setSampleFileEntryId(sampleFileEntryId);
		cpDefinitionVirtualSetting.setSampleUrl(sampleUrl);
		cpDefinitionVirtualSetting.setTermsOfUseRequired(termsOfUseRequired);
		cpDefinitionVirtualSetting.setTermsOfUseContentMap(
			termsOfUseContentMap);
		cpDefinitionVirtualSetting.setTermsOfUseJournalArticleResourcePK(
			termsOfUseJournalArticleResourcePK);
		cpDefinitionVirtualSetting.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionVirtualSettingPersistence.update(
			cpDefinitionVirtualSetting);

		return cpDefinitionVirtualSetting;
	}

	protected void validate(
			boolean useFileEntry, long fileEntryId, String url,
			boolean useSample, boolean useSampleFileEntry,
			long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePK)
		throws PortalException {

		if (useFileEntry) {
			try {
				dlAppLocalService.getFileEntry(fileEntryId);
			}
			catch (NoSuchFileEntryException nsfee) {
				throw new CPDefinitionVirtualSettingFileEntryIdException(nsfee);
			}
		}
		else if (Validator.isNull(url)) {
			throw new CPDefinitionVirtualSettingUrlException();
		}

		if (useSample) {
			if (useSampleFileEntry) {
				try {
					dlAppLocalService.getFileEntry(sampleFileEntryId);
				}
				catch (NoSuchFileEntryException nsfee) {
					throw new
						CPDefinitionVirtualSettingSampleFileEntryIdException(
							nsfee);
				}
			}
			else if (Validator.isNull(sampleUrl)) {
				throw new CPDefinitionVirtualSettingSampleUrlException();
			}
		}

		if (termsOfUseRequired && MapUtil.isEmpty(termsOfUseContentMap)) {
			JournalArticle journalArticle =
				journalArticleLocalService.fetchLatestArticle(
					termsOfUseJournalArticleResourcePK);

			if (journalArticle == null) {
				throw new
					CPDefinitionVirtualSettingTermsOfUseRequiredException();
			}
		}
	}

}