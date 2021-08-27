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

package com.liferay.layout.seo.service;

import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutSEOEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryService
 * @generated
 */
public class LayoutSEOEntryServiceWrapper
	implements LayoutSEOEntryService, ServiceWrapper<LayoutSEOEntryService> {

	public LayoutSEOEntryServiceWrapper(
		LayoutSEOEntryService layoutSEOEntryService) {

		_layoutSEOEntryService = layoutSEOEntryService;
	}

	@Override
	public LayoutSEOEntry copyLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled,
			java.util.Map<java.util.Locale, String> canonicalURLMap,
			long copyDDMStorageId, boolean openGraphDescriptionEnabled,
			java.util.Map<java.util.Locale, String> openGraphDescriptionMap,
			java.util.Map<java.util.Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			java.util.Map<java.util.Locale, String> openGraphTitleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOEntryService.copyLayoutSEOEntry(
			userId, groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, copyDDMStorageId, openGraphDescriptionEnabled,
			openGraphDescriptionMap, openGraphImageAltMap,
			openGraphImageFileEntryId, openGraphTitleEnabled, openGraphTitleMap,
			serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutSEOEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public LayoutSEOEntry updateCustomMetaTags(
			long groupId, boolean privateLayout, long layoutId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOEntryService.updateCustomMetaTags(
			groupId, privateLayout, layoutId, serviceContext);
	}

	@Override
	public LayoutSEOEntry updateLayoutSEOEntry(
			long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled,
			java.util.Map<java.util.Locale, String> canonicalURLMap,
			boolean openGraphDescriptionEnabled,
			java.util.Map<java.util.Locale, String> openGraphDescriptionMap,
			java.util.Map<java.util.Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			java.util.Map<java.util.Locale, String> openGraphTitleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOEntryService.updateLayoutSEOEntry(
			groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, openGraphDescriptionEnabled,
			openGraphDescriptionMap, openGraphImageAltMap,
			openGraphImageFileEntryId, openGraphTitleEnabled, openGraphTitleMap,
			serviceContext);
	}

	@Override
	public LayoutSEOEntry updateLayoutSEOEntry(
			long groupId, boolean privateLayout, long layoutId,
			boolean enabledCanonicalURLMap,
			java.util.Map<java.util.Locale, String> canonicalURLMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOEntryService.updateLayoutSEOEntry(
			groupId, privateLayout, layoutId, enabledCanonicalURLMap,
			canonicalURLMap, serviceContext);
	}

	@Override
	public LayoutSEOEntryService getWrappedService() {
		return _layoutSEOEntryService;
	}

	@Override
	public void setWrappedService(LayoutSEOEntryService layoutSEOEntryService) {
		_layoutSEOEntryService = layoutSEOEntryService;
	}

	private LayoutSEOEntryService _layoutSEOEntryService;

}