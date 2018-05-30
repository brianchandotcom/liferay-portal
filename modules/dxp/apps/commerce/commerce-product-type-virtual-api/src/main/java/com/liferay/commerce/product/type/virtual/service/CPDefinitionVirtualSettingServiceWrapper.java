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
		long cpDefinitionId, long fileEntryId, String url,
		int activationStatus, long duration, int maxUsages, boolean useSample,
		long sampleFileEntryId, String sampleUrl, boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionVirtualSettingService.addCPDefinitionVirtualSetting(cpDefinitionId,
			fileEntryId, url, activationStatus, duration, maxUsages, useSample,
			sampleFileEntryId, sampleUrl, termsOfUseRequired,
			termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting fetchCPDefinitionVirtualSettingByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionVirtualSettingService.fetchCPDefinitionVirtualSettingByCPDefinitionId(cpDefinitionId);
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
	public com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
		long cpDefinitionVirtualSettingId, long fileEntryId, String url,
		int activationStatus, long duration, int maxUsages, boolean useSample,
		long sampleFileEntryId, String sampleUrl, boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionVirtualSettingService.updateCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId,
			fileEntryId, url, activationStatus, duration, maxUsages, useSample,
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

	private CPDefinitionVirtualSettingService _cpDefinitionVirtualSettingService;
}