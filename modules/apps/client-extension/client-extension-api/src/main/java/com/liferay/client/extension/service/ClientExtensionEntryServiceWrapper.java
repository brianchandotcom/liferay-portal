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
	implements ClientExtensionEntryService,
			   ServiceWrapper<ClientExtensionEntryService> {

	public ClientExtensionEntryServiceWrapper() {
		this(null);
	}

	public ClientExtensionEntryServiceWrapper(
		ClientExtensionEntryService clientExtensionEntryService) {

		_clientExtensionEntryService = clientExtensionEntryService;
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			addClientExtensionEntry(
				String externalReferenceCode, String baseURL,
				String description,
				java.util.Map<java.util.Locale, String> nameMap,
				String properties, String sourceCodeURL, String type,
				String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.addClientExtensionEntry(
			externalReferenceCode, baseURL, description, nameMap, properties,
			sourceCodeURL, type, typeSettings);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			deleteClientExtensionEntry(long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.deleteClientExtensionEntry(
			clientExtensionEntryId);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			getClientExtensionEntry(long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.getClientExtensionEntry(
			clientExtensionEntryId);
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
	public com.liferay.client.extension.model.ClientExtensionEntry
			updateClientExtensionEntry(
				long clientExtensionEntryId, String baseURL, String description,
				java.util.Map<java.util.Locale, String> nameMap,
				String properties, String sourceCodeURL, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.updateClientExtensionEntry(
			clientExtensionEntryId, baseURL, description, nameMap, properties,
			sourceCodeURL, typeSettings);
	}

	@Override
	public ClientExtensionEntryService getWrappedService() {
		return _clientExtensionEntryService;
	}

	@Override
	public void setWrappedService(
		ClientExtensionEntryService clientExtensionEntryService) {

		_clientExtensionEntryService = clientExtensionEntryService;
	}

	private ClientExtensionEntryService _clientExtensionEntryService;

}