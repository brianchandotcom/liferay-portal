/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionVirtualSettingService}.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingService
 * @generated
 */
public class CPDefinitionVirtualSettingServiceWrapper
	implements CPDefinitionVirtualSettingService,
			   ServiceWrapper<CPDefinitionVirtualSettingService> {

	public CPDefinitionVirtualSettingServiceWrapper() {
		this(null);
	}

	public CPDefinitionVirtualSettingServiceWrapper(
		CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService) {

		_cpDefinitionVirtualSettingService = cpDefinitionVirtualSettingService;
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
					String className, long classPK, long fileEntryId,
					String url, int activationStatus, long duration,
					int maxUsages, boolean useSample, long sampleFileEntryId,
					String sampleURL, boolean termsOfUseRequired,
					java.util.Map<java.util.Locale, String>
						termsOfUseContentMap,
					long termsOfUseJournalArticleResourcePrimKey,
					boolean override,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionVirtualSettingService.addCPDefinitionVirtualSetting(
			className, classPK, fileEntryId, url, activationStatus, duration,
			maxUsages, useSample, sampleFileEntryId, sampleURL,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleResourcePrimKey, override, serviceContext);
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
					String className, long classPK, long fileEntryId,
					String url, int activationStatus, long duration,
					int maxUsages, boolean useSample, long sampleFileEntryId,
					String sampleURL, boolean termsOfUseRequired,
					java.util.Map<java.util.Locale, String>
						termsOfUseContentMap,
					long termsOfUseJournalArticleResourcePrimKey,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionVirtualSettingService.addCPDefinitionVirtualSetting(
			className, classPK, fileEntryId, url, activationStatus, duration,
			maxUsages, useSample, sampleFileEntryId, sampleURL,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleResourcePrimKey, serviceContext);
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
					String className, long classPK)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionVirtualSettingService.
			deleteCPDefinitionVirtualSetting(className, classPK);
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDefinitionVirtualSetting fetchCPDefinitionVirtualSetting(
					String className, long classPK)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionVirtualSettingService.
			fetchCPDefinitionVirtualSetting(className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionVirtualSettingService.getOSGiServiceIdentifier();
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
					long cpDefinitionVirtualSettingId, long fileEntryId,
					String url, int activationStatus, long duration,
					int maxUsages, boolean useSample, long sampleFileEntryId,
					String sampleURL, boolean termsOfUseRequired,
					java.util.Map<java.util.Locale, String>
						termsOfUseContentMap,
					long termsOfUseJournalArticleResourcePrimKey,
					boolean override,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionVirtualSettingService.
			updateCPDefinitionVirtualSetting(
				cpDefinitionVirtualSettingId, fileEntryId, url,
				activationStatus, duration, maxUsages, useSample,
				sampleFileEntryId, sampleURL, termsOfUseRequired,
				termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
				override, serviceContext);
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
					long cpDefinitionVirtualSettingId, long fileEntryId,
					String url, int activationStatus, long duration,
					int maxUsages, boolean useSample, long sampleFileEntryId,
					String sampleURL, boolean termsOfUseRequired,
					java.util.Map<java.util.Locale, String>
						termsOfUseContentMap,
					long termsOfUseJournalArticleResourcePrimKey,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionVirtualSettingService.
			updateCPDefinitionVirtualSetting(
				cpDefinitionVirtualSettingId, fileEntryId, url,
				activationStatus, duration, maxUsages, useSample,
				sampleFileEntryId, sampleURL, termsOfUseRequired,
				termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
				serviceContext);
	}

	@Override
	public CPDefinitionVirtualSettingService getWrappedService() {
		return _cpDefinitionVirtualSettingService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService) {

		_cpDefinitionVirtualSettingService = cpDefinitionVirtualSettingService;
	}

	private CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;

}