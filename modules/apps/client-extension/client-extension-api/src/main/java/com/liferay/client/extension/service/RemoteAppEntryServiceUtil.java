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

package com.liferay.remote.app.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.remote.app.model.RemoteAppEntry;

import java.util.Map;

/**
 * Provides the remote service utility for RemoteAppEntry. This utility wraps
 * <code>com.liferay.remote.app.service.impl.RemoteAppEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryService
 * @generated
 */
public class RemoteAppEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.remote.app.service.impl.RemoteAppEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static RemoteAppEntry addCustomElementRemoteAppEntry(
			String externalReferenceCode, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, boolean instanceable,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addCustomElementRemoteAppEntry(
			externalReferenceCode, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	public static RemoteAppEntry addIFrameRemoteAppEntry(
			String description, String friendlyURLMapping, String iFrameURL,
			boolean instanceable, Map<java.util.Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addIFrameRemoteAppEntry(
			description, friendlyURLMapping, iFrameURL, instanceable, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	public static RemoteAppEntry deleteRemoteAppEntry(long remoteAppEntryId)
		throws PortalException {

		return getService().deleteRemoteAppEntry(remoteAppEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static RemoteAppEntry getRemoteAppEntry(long remoteAppEntryId)
		throws PortalException {

		return getService().getRemoteAppEntry(remoteAppEntryId);
	}

	public static RemoteAppEntry updateCustomElementRemoteAppEntry(
			long remoteAppEntryId, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, Map<java.util.Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		return getService().updateCustomElementRemoteAppEntry(
			remoteAppEntryId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	public static RemoteAppEntry updateIFrameRemoteAppEntry(
			long remoteAppEntryId, String description,
			String friendlyURLMapping, String iFrameURL,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().updateIFrameRemoteAppEntry(
			remoteAppEntryId, description, friendlyURLMapping, iFrameURL,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	public static RemoteAppEntryService getService() {
		return _service;
	}

	private static volatile RemoteAppEntryService _service;

}