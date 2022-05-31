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

package com.liferay.client.extension.service;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * Provides the remote service utility for ClientExtensionEntry. This utility wraps
 * <code>com.liferay.client.extension.service.impl.ClientExtensionEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryService
 * @generated
 */
public class ClientExtensionEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.client.extension.service.impl.ClientExtensionEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ClientExtensionEntry addCustomElementClientExtensionEntry(
			String externalReferenceCode, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, boolean instanceable,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addCustomElementClientExtensionEntry(
			externalReferenceCode, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	public static ClientExtensionEntry addIFrameClientExtensionEntry(
			String description, String friendlyURLMapping, String iFrameURL,
			boolean instanceable, Map<java.util.Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addIFrameClientExtensionEntry(
			description, friendlyURLMapping, iFrameURL, instanceable, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	public static ClientExtensionEntry addThemeCSSClientExtensionEntry(
			String clayURL, String description, String mainURL,
			Map<java.util.Locale, String> nameMap, String sourceCodeURL)
		throws PortalException {

		return getService().addThemeCSSClientExtensionEntry(
			clayURL, description, mainURL, nameMap, sourceCodeURL);
	}

	public static ClientExtensionEntry addThemeFaviconClientExtensionEntry(
			String description, Map<java.util.Locale, String> nameMap,
			String sourceCodeURL, String url)
		throws PortalException {

		return getService().addThemeFaviconClientExtensionEntry(
			description, nameMap, sourceCodeURL, url);
	}

	public static ClientExtensionEntry addThemeJSClientExtensionEntry(
			String description, Map<java.util.Locale, String> nameMap,
			String sourceCodeURL, String urls)
		throws PortalException {

		return getService().addThemeJSClientExtensionEntry(
			description, nameMap, sourceCodeURL, urls);
	}

	public static ClientExtensionEntry deleteClientExtensionEntry(
			long clientExtensionEntryId)
		throws PortalException {

		return getService().deleteClientExtensionEntry(clientExtensionEntryId);
	}

	public static ClientExtensionEntry getClientExtensionEntry(
			long clientExtensionEntryId)
		throws PortalException {

		return getService().getClientExtensionEntry(clientExtensionEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ClientExtensionEntry updateCustomElementClientExtensionEntry(
			long clientExtensionEntryId, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, Map<java.util.Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		return getService().updateCustomElementClientExtensionEntry(
			clientExtensionEntryId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	public static ClientExtensionEntry updateIFrameClientExtensionEntry(
			long clientExtensionEntryId, String description,
			String friendlyURLMapping, String iFrameURL,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().updateIFrameClientExtensionEntry(
			clientExtensionEntryId, description, friendlyURLMapping, iFrameURL,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	public static ClientExtensionEntry updateThemeCSSClientExtensionEntry(
			long clientExtensionEntryId, String clayURL, String description,
			String mainURL, Map<java.util.Locale, String> nameMap,
			String sourceCodeURL)
		throws PortalException {

		return getService().updateThemeCSSClientExtensionEntry(
			clientExtensionEntryId, clayURL, description, mainURL, nameMap,
			sourceCodeURL);
	}

	public static ClientExtensionEntry updateThemeFaviconClientExtensionEntry(
			long clientExtensionEntryId, String description,
			Map<java.util.Locale, String> nameMap, String sourceCodeURL,
			String url)
		throws PortalException {

		return getService().updateThemeFaviconClientExtensionEntry(
			clientExtensionEntryId, description, nameMap, sourceCodeURL, url);
	}

	public static ClientExtensionEntry updateThemeJSClientExtensionEntry(
			long clientExtensionEntryId, String description,
			Map<java.util.Locale, String> nameMap, String sourceCodeURL,
			String urls)
		throws PortalException {

		return getService().updateThemeJSClientExtensionEntry(
			clientExtensionEntryId, description, nameMap, sourceCodeURL, urls);
	}

	public static ClientExtensionEntryService getService() {
		return _service;
	}

	private static volatile ClientExtensionEntryService _service;

}