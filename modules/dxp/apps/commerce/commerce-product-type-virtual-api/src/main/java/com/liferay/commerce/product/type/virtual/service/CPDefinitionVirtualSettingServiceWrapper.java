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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionVirtualSettingService}.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingService
 * @generated
 */
@ProviderType
public class CPDefinitionVirtualSettingServiceWrapper
	implements CPDefinitionVirtualSettingService,
		ServiceWrapper<CPDefinitionVirtualSettingService> {
	public CPDefinitionVirtualSettingServiceWrapper(
		CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService) {
		_cpDefinitionVirtualSettingService = cpDefinitionVirtualSettingService;
	}

	@Override
	public com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
		long cpDefinitionId, boolean useFileEntry, long fileEntryId,
		java.lang.String url, java.lang.String activationStatus, long duration,
		int maxUsages, boolean useSampleFileEntry, long sampleFileEntryId,
		java.lang.String sampleUrl, boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, java.lang.String> termsOfUseContentMap,
		long termsOfUseJournalArticleId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionVirtualSettingService.addCPDefinitionVirtualSetting(cpDefinitionId,
			useFileEntry, fileEntryId, url, activationStatus, duration,
			maxUsages, useSampleFileEntry, sampleFileEntryId, sampleUrl,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleId, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
		com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting cpDefinitionVirtualSetting)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionVirtualSettingService.deleteCPDefinitionVirtualSetting(cpDefinitionVirtualSetting);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting fetchCPDefinitionVirtualSetting(
		long cpDefinitionVirtualSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionVirtualSettingService.fetchCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
		long cpDefinitionVirtualSettingId, boolean useFileEntry,
		long fileEntryId, java.lang.String url,
		java.lang.String activationStatus, long duration, int maxUsages,
		boolean useSampleFileEntry, long sampleFileEntryId,
		java.lang.String sampleUrl, boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, java.lang.String> termsOfUseContentMap,
		long termsOfUseJournalArticleId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionVirtualSettingService.updateCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId,
			useFileEntry, fileEntryId, url, activationStatus, duration,
			maxUsages, useSampleFileEntry, sampleFileEntryId, sampleUrl,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleId, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionVirtualSettingService.getOSGiServiceIdentifier();
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

	private CPDefinitionVirtualSettingService _cpDefinitionVirtualSettingService;
}