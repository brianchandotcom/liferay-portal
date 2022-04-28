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
public class ClientExtensionEntryServiceImpl extends ClientExtensionEntryServiceBaseImpl {

	@Override
	public ClientExtensionEntry addCustomElementClientExtensionEntry(
			String externalReferenceCode, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, boolean instanceable,
			Map<Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, ActionKeys.ADD_ENTRY);

		return clientExtensionEntryLocalService.addCustomElementClientExtensionEntry(
			externalReferenceCode, getUserId(), customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public ClientExtensionEntry addIFrameClientExtensionEntry(
			String description, String friendlyURLMapping, String iFrameURL,
			boolean instanceable, Map<Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, ActionKeys.ADD_ENTRY);

		return clientExtensionEntryLocalService.addIFrameClientExtensionEntry(
			getUserId(), description, friendlyURLMapping, iFrameURL,
			instanceable, nameMap, portletCategoryName, properties,
			sourceCodeURL);
	}

	@Override
	public ClientExtensionEntry deleteClientExtensionEntry(long clientExtensionEntryId)
		throws PortalException {

		_clientExtensionEntryModelResourcePermission.check(
			getPermissionChecker(), clientExtensionEntryId, ActionKeys.DELETE);

		return clientExtensionEntryLocalService.deleteClientExtensionEntry(
			clientExtensionEntryId);
	}

	@Override
	public ClientExtensionEntry getClientExtensionEntry(long clientExtensionEntryId)
		throws PortalException {

		_clientExtensionEntryModelResourcePermission.check(
			getPermissionChecker(), clientExtensionEntryId, ActionKeys.VIEW);

		return clientExtensionEntryLocalService.getClientExtensionEntry(clientExtensionEntryId);
	}

	@Override
	public ClientExtensionEntry updateCustomElementClientExtensionEntry(
			long clientExtensionEntryId, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, Map<Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		_clientExtensionEntryModelResourcePermission.check(
			getPermissionChecker(), clientExtensionEntryId, ActionKeys.UPDATE);

		return clientExtensionEntryLocalService.updateCustomElementClientExtensionEntry(
			getUserId(), clientExtensionEntryId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public ClientExtensionEntry updateIFrameClientExtensionEntry(
			long clientExtensionEntryId, String description,
			String friendlyURLMapping, String iFrameURL,
			Map<Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		_clientExtensionEntryModelResourcePermission.check(
			getPermissionChecker(), clientExtensionEntryId, ActionKeys.UPDATE);

		return clientExtensionEntryLocalService.updateIFrameClientExtensionEntry(
			getUserId(), clientExtensionEntryId, description, friendlyURLMapping,
			iFrameURL, nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	@Reference(
		target = "(resource.name=" + ClientExtensionConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.client.extension.model.ClientExtensionEntry)"
	)
	private ModelResourcePermission<ClientExtensionEntry>
		_clientExtensionEntryModelResourcePermission;

}