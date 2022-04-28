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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ClientExtensionEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryService
 * @generated
 */
public class ClientExtensionEntryServiceWrapper
	implements ClientExtensionEntryService, ServiceWrapper<ClientExtensionEntryService> {

	public ClientExtensionEntryServiceWrapper() {
		this(null);
	}

	public ClientExtensionEntryServiceWrapper(
		ClientExtensionEntryService clientExtensionEntryService) {

		_clientExtensionEntryService = clientExtensionEntryService;
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			addCustomElementClientExtensionEntry(
				String externalReferenceCode, String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean customElementUseESM, String description,
				String friendlyURLMapping, boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.addCustomElementClientExtensionEntry(
			externalReferenceCode, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			addIFrameClientExtensionEntry(
				String description, String friendlyURLMapping, String iFrameURL,
				boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.addIFrameClientExtensionEntry(
			description, friendlyURLMapping, iFrameURL, instanceable, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			deleteClientExtensionEntry(long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.deleteClientExtensionEntry(clientExtensionEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _clientExtensionEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry getClientExtensionEntry(
			long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.getClientExtensionEntry(clientExtensionEntryId);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			updateCustomElementClientExtensionEntry(
				long clientExtensionEntryId, String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean customElementUseESM, String description,
				String friendlyURLMapping,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.updateCustomElementClientExtensionEntry(
			clientExtensionEntryId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			updateIFrameClientExtensionEntry(
				long clientExtensionEntryId, String description,
				String friendlyURLMapping, String iFrameURL,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.updateIFrameClientExtensionEntry(
			clientExtensionEntryId, description, friendlyURLMapping, iFrameURL,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public ClientExtensionEntryService getWrappedService() {
		return _clientExtensionEntryService;
	}

	@Override
	public void setWrappedService(ClientExtensionEntryService clientExtensionEntryService) {
		_clientExtensionEntryService = clientExtensionEntryService;
	}

	private ClientExtensionEntryService _clientExtensionEntryService;

}