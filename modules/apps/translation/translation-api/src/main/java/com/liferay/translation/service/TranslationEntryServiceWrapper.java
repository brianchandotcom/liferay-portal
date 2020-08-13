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

package com.liferay.translation.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

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

	public TranslationEntryServiceWrapper(
		TranslationEntryService translationEntryService) {

		_translationEntryService = translationEntryService;
	}

	@Override
	public com.liferay.translation.model.TranslationEntry
			addOrUpdateTranslationEntry(
				long groupId, String languageId,
				com.liferay.info.item.InfoItemReference infoItemReference,
				com.liferay.info.item.InfoItemFieldValues infoItemFieldValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryService.addOrUpdateTranslationEntry(
			groupId, languageId, infoItemReference, infoItemFieldValues,
			serviceContext);
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