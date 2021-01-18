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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.layout.seo.model.LayoutSEOEntry
			updateCustomMetaTags(
				long groupId, boolean privateLayout, long layoutId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCustomMetaTags(
			groupId, privateLayout, layoutId, serviceContext);
	}

	public static com.liferay.layout.seo.model.LayoutSEOEntry
			updateLayoutSEOEntry(
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

		return getService().updateLayoutSEOEntry(
			groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, openGraphDescriptionEnabled,
			openGraphDescriptionMap, openGraphImageAltMap,
			openGraphImageFileEntryId, openGraphTitleEnabled, openGraphTitleMap,
			serviceContext);
	}

	public static com.liferay.layout.seo.model.LayoutSEOEntry
			updateLayoutSEOEntry(
				long groupId, boolean privateLayout, long layoutId,
				boolean enabledCanonicalURLMap,
				java.util.Map<java.util.Locale, String> canonicalURLMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutSEOEntry(
			groupId, privateLayout, layoutId, enabledCanonicalURLMap,
			canonicalURLMap, serviceContext);
	}

	public static LayoutSEOEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LayoutSEOEntryService, LayoutSEOEntryService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(LayoutSEOEntryService.class);

		ServiceTracker<LayoutSEOEntryService, LayoutSEOEntryService>
			serviceTracker =
				new ServiceTracker
					<LayoutSEOEntryService, LayoutSEOEntryService>(
						bundle.getBundleContext(), LayoutSEOEntryService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}