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
	public static ClientExtensionEntry addClientExtensionEntry(
			String externalReferenceCode, String description,
			Map<java.util.Locale, String> nameMap, String properties,
			String sourceCodeURL, String type, String typeSettings)
		throws PortalException {

		return getService().addClientExtensionEntry(
			externalReferenceCode, description, nameMap, properties,
			sourceCodeURL, type, typeSettings);
	}

	public static ClientExtensionEntry deleteClientExtensionEntry(
			long clientExtensionEntryId)
		throws PortalException {

		return getService().deleteClientExtensionEntry(clientExtensionEntryId);
	}

	public static ClientExtensionEntry
			deleteClientExtensionEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().deleteClientExtensionEntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static ClientExtensionEntry
			fetchClientExtensionEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().fetchClientExtensionEntryByExternalReferenceCode(
			companyId, externalReferenceCode);
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

	public static ClientExtensionEntry updateClientExtensionEntry(
			long clientExtensionEntryId, String description,
			Map<java.util.Locale, String> nameMap, String properties,
			String sourceCodeURL, String typeSettings)
		throws PortalException {

		return getService().updateClientExtensionEntry(
			clientExtensionEntryId, description, nameMap, properties,
			sourceCodeURL, typeSettings);
	}

	public static ClientExtensionEntryService getService() {
		return _service;
	}

	public static void setService(ClientExtensionEntryService service) {
		_service = service;
	}

	private static volatile ClientExtensionEntryService _service;

}