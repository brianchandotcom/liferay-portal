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
					String sampleUrl, boolean termsOfUseRequired,
					java.util.Map<java.util.Locale, String>
						termsOfUseContentMap,
					long termsOfUseJournalArticleResourcePrimKey,
					boolean override,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionVirtualSettingService.addCPDefinitionVirtualSetting(
			className, classPK, fileEntryId, url, activationStatus, duration,
			maxUsages, useSample, sampleFileEntryId, sampleUrl,
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
					String sampleUrl, boolean termsOfUseRequired,
					java.util.Map<java.util.Locale, String>
						termsOfUseContentMap,
					long termsOfUseJournalArticleResourcePrimKey,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionVirtualSettingService.addCPDefinitionVirtualSetting(
			className, classPK, fileEntryId, url, activationStatus, duration,
			maxUsages, useSample, sampleFileEntryId, sampleUrl,
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
					String sampleUrl, boolean termsOfUseRequired,
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
				sampleFileEntryId, sampleUrl, termsOfUseRequired,
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
					String sampleUrl, boolean termsOfUseRequired,
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
				sampleFileEntryId, sampleUrl, termsOfUseRequired,
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