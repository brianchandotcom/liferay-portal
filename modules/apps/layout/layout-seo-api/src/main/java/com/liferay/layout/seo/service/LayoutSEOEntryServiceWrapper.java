/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public LayoutSEOEntryServiceWrapper() {
		this(null);
	}

	public LayoutSEOEntryServiceWrapper(
		LayoutSEOEntryService layoutSEOEntryService) {

		_layoutSEOEntryService = layoutSEOEntryService;
	}

	@Override
	public LayoutSEOEntry copyLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout,
			long sourceLayoutId, boolean canonicalURLEnabled,
			java.util.Map<java.util.Locale, String> canonicalURLMap,
			long copyDDMStorageId, boolean openGraphDescriptionEnabled,
			java.util.Map<java.util.Locale, String> openGraphDescriptionMap,
			java.util.Map<java.util.Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			java.util.Map<java.util.Locale, String> openGraphTitleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOEntryService.copyLayoutSEOEntry(
			userId, groupId, privateLayout, sourceLayoutId, canonicalURLEnabled,
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