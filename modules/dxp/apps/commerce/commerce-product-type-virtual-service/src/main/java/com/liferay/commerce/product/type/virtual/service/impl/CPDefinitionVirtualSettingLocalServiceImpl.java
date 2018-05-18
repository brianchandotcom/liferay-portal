/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.type.virtual.service.impl;

import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingFileEntryIdException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleFileEntryIdException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleUrlException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseArticleResourcePKException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseContentException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseException;
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
			long cpDefinitionId, long fileEntryId, String url,
			String activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		if (Validator.isNotNull(url)) {
			fileEntryId = 0;
		}
		else {
			url = null;
		}

		if (useSample) {
			if (Validator.isNotNull(sampleUrl)) {
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
			if (termsOfUseJournalArticleResourcePrimKey > 0) {
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
			fileEntryId, url, useSample, sampleFileEntryId, sampleUrl,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleResourcePrimKey);

		long cpDefinitionVirtualSettingId = counterLocalService.increment();

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingPersistence.create(
				cpDefinitionVirtualSettingId);

		cpDefinitionVirtualSetting.setUuid(serviceContext.getUuid());
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
	public CPDefinitionVirtualSetting
		fetchCPDefinitionVirtualSettingByCPDefinitionId(long cpDefinitionId) {

		return cpDefinitionVirtualSettingPersistence.fetchByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
			long cpDefinitionVirtualSettingId, long fileEntryId, String url,
			String activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingPersistence.findByPrimaryKey(
				cpDefinitionVirtualSettingId);

		if (Validator.isNotNull(url)) {
			fileEntryId = 0;
		}
		else {
			url = null;
		}

		if (useSample) {
			if (Validator.isNotNull(sampleUrl)) {
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
			if (termsOfUseJournalArticleResourcePrimKey > 0) {
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
			fileEntryId, url, useSample, sampleFileEntryId, sampleUrl,
			termsOfUseRequired, termsOfUseContentMap,
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
			long fileEntryId, String url, boolean useSample,
			long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey)
		throws PortalException {

		if (fileEntryId > 0) {
			try {
				dlAppLocalService.getFileEntry(fileEntryId);
			}
			catch (NoSuchFileEntryException nsfee) {
				throw new CPDefinitionVirtualSettingFileEntryIdException(nsfee);
			}
		}
		else if ((fileEntryId <= 0) && Validator.isNull(url)) {
			throw new CPDefinitionVirtualSettingException();
		}
		else if (Validator.isNull(url)) {
			throw new CPDefinitionVirtualSettingUrlException();
		}

		if (useSample) {
			if (sampleFileEntryId > 0) {
				try {
					dlAppLocalService.getFileEntry(sampleFileEntryId);
				}
				catch (NoSuchFileEntryException nsfee) {
					throw new
						CPDefinitionVirtualSettingSampleFileEntryIdException(
							nsfee);
				}
			}
			else if ((sampleFileEntryId <= 0) && Validator.isNull(sampleUrl)) {
				throw new CPDefinitionVirtualSettingSampleException();
			}
			else if (Validator.isNull(sampleUrl)) {
				throw new CPDefinitionVirtualSettingSampleUrlException();
			}
		}

		if (termsOfUseRequired) {
			if (termsOfUseJournalArticleResourcePrimKey > 0) {
				JournalArticle journalArticle =
					journalArticleLocalService.fetchLatestArticle(
						termsOfUseJournalArticleResourcePrimKey);

				if (journalArticle == null) {
					throw new
						CPDefinitionVirtualSettingTermsOfUseArticleResourcePKException();
				}
			}
			else if ((termsOfUseJournalArticleResourcePrimKey <= 0) &&
					 MapUtil.isEmpty(termsOfUseContentMap)) {

				throw new CPDefinitionVirtualSettingTermsOfUseException();
			}
			else if (MapUtil.isEmpty(termsOfUseContentMap)) {
				throw new
					CPDefinitionVirtualSettingTermsOfUseContentException();
			}
		}
	}

}