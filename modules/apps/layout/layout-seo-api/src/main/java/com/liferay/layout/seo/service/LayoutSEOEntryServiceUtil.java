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
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * Provides the remote service utility for LayoutSEOEntry. This utility wraps
 * <code>com.liferay.layout.seo.service.impl.LayoutSEOEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryService
 * @generated
 */
public class LayoutSEOEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.seo.service.impl.LayoutSEOEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static LayoutSEOEntry copyLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled,
			Map<java.util.Locale, String> canonicalURLMap,
			long copyDDMStorageId, boolean openGraphDescriptionEnabled,
			Map<java.util.Locale, String> openGraphDescriptionMap,
			Map<java.util.Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			Map<java.util.Locale, String> openGraphTitleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyLayoutSEOEntry(
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
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static LayoutSEOEntry updateCustomMetaTags(
			long groupId, boolean privateLayout, long layoutId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCustomMetaTags(
			groupId, privateLayout, layoutId, serviceContext);
	}

	public static LayoutSEOEntry updateLayoutSEOEntry(
			long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled,
			Map<java.util.Locale, String> canonicalURLMap,
			boolean openGraphDescriptionEnabled,
			Map<java.util.Locale, String> openGraphDescriptionMap,
			Map<java.util.Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			Map<java.util.Locale, String> openGraphTitleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateLayoutSEOEntry(
			groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, openGraphDescriptionEnabled,
			openGraphDescriptionMap, openGraphImageAltMap,
			openGraphImageFileEntryId, openGraphTitleEnabled, openGraphTitleMap,
			serviceContext);
	}

	public static LayoutSEOEntry updateLayoutSEOEntry(
			long groupId, boolean privateLayout, long layoutId,
			boolean enabledCanonicalURLMap,
			Map<java.util.Locale, String> canonicalURLMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateLayoutSEOEntry(
			groupId, privateLayout, layoutId, enabledCanonicalURLMap,
			canonicalURLMap, serviceContext);
	}

	public static LayoutSEOEntryService getService() {
		return _service;
	}

	private static volatile LayoutSEOEntryService _service;

}