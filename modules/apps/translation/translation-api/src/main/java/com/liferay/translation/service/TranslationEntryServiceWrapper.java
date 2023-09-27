/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.translation.model.TranslationEntry;

/**
 * Provides a wrapper for {@link TranslationEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see TranslationEntryService
 * @generated
 */
public class TranslationEntryServiceWrapper
	implements ServiceWrapper<TranslationEntryService>,
			   TranslationEntryService {

	public TranslationEntryServiceWrapper() {
		this(null);
	}

	public TranslationEntryServiceWrapper(
		TranslationEntryService translationEntryService) {

		_translationEntryService = translationEntryService;
	}

	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId,
			com.liferay.info.item.InfoItemReference infoItemReference,
			String content, String contentType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryService.addOrUpdateTranslationEntry(
			groupId, infoItemReference, content, contentType, serviceContext);
	}

	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String languageId,
			com.liferay.info.item.InfoItemReference infoItemReference,
			com.liferay.info.item.InfoItemFieldValues infoItemFieldValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryService.addOrUpdateTranslationEntry(
			groupId, languageId, infoItemReference, infoItemFieldValues,
			serviceContext);
	}

	@Override
	public TranslationEntry deleteTranslationEntry(long translationEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryService.deleteTranslationEntry(
			translationEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _translationEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public TranslationEntryService getWrappedService() {
		return _translationEntryService;
	}

	@Override
	public void setWrappedService(
		TranslationEntryService translationEntryService) {

		_translationEntryService = translationEntryService;
	}

	private TranslationEntryService _translationEntryService;

}