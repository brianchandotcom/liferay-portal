/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.service;

import com.liferay.client.extension.model.ClientExtensionEntry;
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
	public ClientExtensionEntry addClientExtensionEntry(
			String externalReferenceCode, String description,
			java.util.Map<java.util.Locale, String> nameMap, String properties,
			String sourceCodeURL, String type, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.addClientExtensionEntry(
			externalReferenceCode, description, nameMap, properties,
			sourceCodeURL, type, typeSettings);
	}

	@Override
	public ClientExtensionEntry deleteClientExtensionEntry(
			long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.deleteClientExtensionEntry(
			clientExtensionEntryId);
	}

	@Override
	public ClientExtensionEntry
			deleteClientExtensionEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.
			deleteClientExtensionEntryByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public ClientExtensionEntry
			fetchClientExtensionEntryByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.
			fetchClientExtensionEntryByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public ClientExtensionEntry getClientExtensionEntry(
			long clientExtensionEntryId)
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
	public ClientExtensionEntry updateClientExtensionEntry(
			long clientExtensionEntryId, String description,
			java.util.Map<java.util.Locale, String> nameMap, String properties,
			String sourceCodeURL, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryService.updateClientExtensionEntry(
			clientExtensionEntryId, description, nameMap, properties,
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