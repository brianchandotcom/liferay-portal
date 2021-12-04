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

package com.liferay.style.book.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.style.book.model.StyleBookEntry;

/**
 * Provides a wrapper for {@link StyleBookEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookEntryService
 * @generated
 */
public class StyleBookEntryServiceWrapper
	implements ServiceWrapper<StyleBookEntryService>, StyleBookEntryService {

	public StyleBookEntryServiceWrapper() {
		this(null);
	}

	public StyleBookEntryServiceWrapper(
		StyleBookEntryService styleBookEntryService) {

		_styleBookEntryService = styleBookEntryService;
	}

	@Override
	public StyleBookEntry addStyleBookEntry(
			long groupId, String name, String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.addStyleBookEntry(
			groupId, name, styleBookEntryKey, serviceContext);
	}

	@Override
	public StyleBookEntry addStyleBookEntry(
			long groupId, String frontendTokensValues, String name,
			String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.addStyleBookEntry(
			groupId, frontendTokensValues, name, styleBookEntryKey,
			serviceContext);
	}

	@Override
	public StyleBookEntry copyStyleBookEntry(
			long groupId, long styleBookEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.copyStyleBookEntry(
			groupId, styleBookEntryId, serviceContext);
	}

	@Override
	public StyleBookEntry deleteStyleBookEntry(long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.deleteStyleBookEntry(styleBookEntryId);
	}

	@Override
	public StyleBookEntry deleteStyleBookEntry(StyleBookEntry styleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.deleteStyleBookEntry(styleBookEntry);
	}

	@Override
	public StyleBookEntry discardDraftStyleBookEntry(long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.discardDraftStyleBookEntry(
			styleBookEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _styleBookEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public StyleBookEntry publishDraft(long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.publishDraft(styleBookEntryId);
	}

	@Override
	public StyleBookEntry updateDefaultStyleBookEntry(
			long styleBookEntryId, boolean defaultStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.updateDefaultStyleBookEntry(
			styleBookEntryId, defaultStyleBookEntry);
	}

	@Override
	public StyleBookEntry updateFrontendTokensValues(
			long styleBookEntryId, String frontendTokensValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.updateFrontendTokensValues(
			styleBookEntryId, frontendTokensValues);
	}

	@Override
	public StyleBookEntry updateName(long styleBookEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.updateName(styleBookEntryId, name);
	}

	@Override
	public StyleBookEntry updatePreviewFileEntryId(
			long styleBookEntryId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.updatePreviewFileEntryId(
			styleBookEntryId, previewFileEntryId);
	}

	@Override
	public StyleBookEntry updateStyleBookEntry(
			long styleBookEntryId, String frontendTokensValues, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryService.updateStyleBookEntry(
			styleBookEntryId, frontendTokensValues, name);
	}

	@Override
	public StyleBookEntryService getWrappedService() {
		return _styleBookEntryService;
	}

	@Override
	public void setWrappedService(StyleBookEntryService styleBookEntryService) {
		_styleBookEntryService = styleBookEntryService;
	}

	private StyleBookEntryService _styleBookEntryService;

}