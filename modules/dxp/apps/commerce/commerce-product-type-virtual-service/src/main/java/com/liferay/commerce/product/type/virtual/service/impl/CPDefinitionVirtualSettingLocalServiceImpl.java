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
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseArticleResourcePKException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseContentException;
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

import java.util.Collections;
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
			long cpDefinitionId, boolean useUrl, long fileEntryId, String url,
			String activationStatus, long duration, int maxUsages,
			boolean useSample, boolean useSampleUrl, long sampleFileEntryId,
			String sampleUrl, boolean termsOfUseRequired,
			boolean useTermsOfUseJournal,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		if (useUrl) {
			fileEntryId = 0;
		}
		else {
			url = null;
		}

		if (useSample) {
			if (useSampleUrl) {
				sampleFileEntryId = 0;
			}
			else {
				sampleUrl = null;
			}
		}
		else {
			sampleUrl = null;
			sampleFileEntryId = 0;
		}

		if (termsOfUseRequired) {
			if (useTermsOfUseJournal) {
				termsOfUseContentMap = Collections.emptyMap();
			}
			else {
				termsOfUseJournalArticleResourcePrimKey = 0;
			}
		}
		else {
			termsOfUseContentMap = Collections.emptyMap();
			termsOfUseJournalArticleResourcePrimKey = 0;
		}

		validate(
			useUrl, fileEntryId, url, useSample, useSampleUrl,
			sampleFileEntryId, sampleUrl, termsOfUseRequired,
			useTermsOfUseJournal, termsOfUseContentMap,
			termsOfUseJournalArticleResourcePrimKey);

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
		cpDefinitionVirtualSetting.setTermsOfUseJournalArticleResourcePrimKey(
			termsOfUseJournalArticleResourcePrimKey);
		cpDefinitionVirtualSetting.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionVirtualSettingPersistence.update(
			cpDefinitionVirtualSetting);

		return cpDefinitionVirtualSetting;
	}

	@Override
	public CPDefinitionVirtualSetting
		deleteCPDefinitionVirtualSettingByCPDefinitionId(long cpDefinitionId) {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingPersistence.fetchByCPDefinitionId(
				cpDefinitionId);

		if (cpDefinitionVirtualSetting != null) {
			cpDefinitionVirtualSettingLocalService.
				deleteCPDefinitionVirtualSetting(cpDefinitionVirtualSetting);
		}

		return cpDefinitionVirtualSetting;
	}

	@Override
	public CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
			long cpDefinitionVirtualSettingId, boolean useUrl, long fileEntryId,
			String url, String activationStatus, long duration, int maxUsages,
			boolean useSample, boolean useSampleUrl, long sampleFileEntryId,
			String sampleUrl, boolean termsOfUseRequired,
			boolean useTermsOfUseJournal,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingPersistence.findByPrimaryKey(
				cpDefinitionVirtualSettingId);

		if (useUrl) {
			fileEntryId = 0;
		}
		else {
			url = null;
		}

		if (useSample) {
			if (useSampleUrl) {
				sampleFileEntryId = 0;
			}
			else {
				sampleUrl = null;
			}
		}
		else {
			sampleUrl = null;
			sampleFileEntryId = 0;
		}

		if (termsOfUseRequired) {
			if (useTermsOfUseJournal) {
				termsOfUseContentMap = Collections.emptyMap();
			}
			else {
				termsOfUseJournalArticleResourcePrimKey = 0;
			}
		}
		else {
			termsOfUseContentMap = Collections.emptyMap();
			termsOfUseJournalArticleResourcePrimKey = 0;
		}

		validate(
			useUrl, fileEntryId, url, useSample, useSampleUrl,
			sampleFileEntryId, sampleUrl, termsOfUseRequired,
			useTermsOfUseJournal, termsOfUseContentMap,
			termsOfUseJournalArticleResourcePrimKey);

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
		cpDefinitionVirtualSetting.setTermsOfUseJournalArticleResourcePrimKey(
			termsOfUseJournalArticleResourcePrimKey);
		cpDefinitionVirtualSetting.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionVirtualSettingPersistence.update(
			cpDefinitionVirtualSetting);

		return cpDefinitionVirtualSetting;
	}

	protected void validate(
			boolean useUrl, long fileEntryId, String url, boolean useSample,
			boolean useSampleUrl, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired, boolean useTermsOfUseJournal,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey)
		throws PortalException {

		if (!useUrl) {
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
			if (!useSampleUrl) {
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

		if (termsOfUseRequired) {
			if (useTermsOfUseJournal) {
				JournalArticle journalArticle =
					journalArticleLocalService.fetchLatestArticle(
						termsOfUseJournalArticleResourcePrimKey);

				if (journalArticle == null) {
					throw new
						CPDefinitionVirtualSettingTermsOfUseArticleResourcePKException();
				}
				else if (!useTermsOfUseJournal &&
						 MapUtil.isEmpty(termsOfUseContentMap)) {

					throw new
						CPDefinitionVirtualSettingTermsOfUseContentException();
				}
			}
		}
	}

}