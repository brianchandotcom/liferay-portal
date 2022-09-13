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

package com.liferay.client.extension.service.impl;

import com.liferay.client.extension.constants.ClientExtensionConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.base.ClientExtensionEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=remoteapp",
		"json.web.service.context.path=ClientExtensionEntry"
	},
	service = AopService.class
)
public class ClientExtensionEntryServiceImpl
	extends ClientExtensionEntryServiceBaseImpl {

	@Override
	public ClientExtensionEntry addClientExtensionEntry(
			String externalReferenceCode, String description,
			Map<Locale, String> nameMap, String properties,
			String sourceCodeURL, String type, String typeSettings)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, ActionKeys.ADD_ENTRY);

		return clientExtensionEntryLocalService.addClientExtensionEntry(
			externalReferenceCode, getUserId(), description, nameMap,
			properties, sourceCodeURL, type, typeSettings);
	}

	@Override
	public ClientExtensionEntry deleteClientExtensionEntry(
			long clientExtensionEntryId)
		throws PortalException {

		_clientExtensionEntryModelResourcePermission.check(
			getPermissionChecker(), clientExtensionEntryId, ActionKeys.DELETE);

		return clientExtensionEntryLocalService.deleteClientExtensionEntry(
			clientExtensionEntryId);
	}

	@Override
	public ClientExtensionEntry
			deleteClientExtensionEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		ClientExtensionEntry clientExtensionEntry =
			clientExtensionEntryPersistence.findByERC_C(
				externalReferenceCode, companyId);

		return deleteClientExtensionEntry(
			clientExtensionEntry.getClientExtensionEntryId());
	}

	@Override
	public ClientExtensionEntry
			fetchClientExtensionEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		ClientExtensionEntry clientExtensionEntry =
			clientExtensionEntryPersistence.fetchByERC_C(
				externalReferenceCode, companyId);

		if (clientExtensionEntry != null) {
			_clientExtensionEntryModelResourcePermission.check(
				getPermissionChecker(),
				clientExtensionEntry.getClientExtensionEntryId(),
				ActionKeys.VIEW);
		}

		return clientExtensionEntry;
	}

	@Override
	public ClientExtensionEntry getClientExtensionEntry(
			long clientExtensionEntryId)
		throws PortalException {

		_clientExtensionEntryModelResourcePermission.check(
			getPermissionChecker(), clientExtensionEntryId, ActionKeys.VIEW);

		return clientExtensionEntryLocalService.getClientExtensionEntry(
			clientExtensionEntryId);
	}

	@Override
	public ClientExtensionEntry updateClientExtensionEntry(
			long clientExtensionEntryId, String description,
			Map<Locale, String> nameMap, String properties,
			String sourceCodeURL, String typeSettings)
		throws PortalException {

		_clientExtensionEntryModelResourcePermission.check(
			getPermissionChecker(), clientExtensionEntryId, ActionKeys.UPDATE);

		return clientExtensionEntryLocalService.updateClientExtensionEntry(
			getUserId(), clientExtensionEntryId, description, nameMap,
			properties, sourceCodeURL, typeSettings);
	}

	@Reference(
		target = "(model.class.name=com.liferay.client.extension.model.ClientExtensionEntry)"
	)
	private ModelResourcePermission<ClientExtensionEntry>
		_clientExtensionEntryModelResourcePermission;

	@Reference(
		target = "(resource.name=" + ClientExtensionConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}