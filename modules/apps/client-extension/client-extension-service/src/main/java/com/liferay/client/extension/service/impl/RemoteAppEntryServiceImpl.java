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

package com.liferay.remote.app.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.base.RemoteAppEntryServiceBaseImpl;

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
		"json.web.service.context.path=RemoteAppEntry"
	},
	service = AopService.class
)
public class RemoteAppEntryServiceImpl extends RemoteAppEntryServiceBaseImpl {

	@Override
	public RemoteAppEntry addCustomElementRemoteAppEntry(
			String externalReferenceCode, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, boolean instanceable,
			Map<Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, ActionKeys.ADD_ENTRY);

		return remoteAppEntryLocalService.addCustomElementRemoteAppEntry(
			externalReferenceCode, getUserId(), customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public RemoteAppEntry addIFrameRemoteAppEntry(
			String description, String friendlyURLMapping, String iFrameURL,
			boolean instanceable, Map<Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, ActionKeys.ADD_ENTRY);

		return remoteAppEntryLocalService.addIFrameRemoteAppEntry(
			getUserId(), description, friendlyURLMapping, iFrameURL,
			instanceable, nameMap, portletCategoryName, properties,
			sourceCodeURL);
	}

	@Override
	public RemoteAppEntry deleteRemoteAppEntry(long remoteAppEntryId)
		throws PortalException {

		_remoteAppEntryModelResourcePermission.check(
			getPermissionChecker(), remoteAppEntryId, ActionKeys.DELETE);

		return remoteAppEntryLocalService.deleteRemoteAppEntry(
			remoteAppEntryId);
	}

	@Override
	public RemoteAppEntry getRemoteAppEntry(long remoteAppEntryId)
		throws PortalException {

		_remoteAppEntryModelResourcePermission.check(
			getPermissionChecker(), remoteAppEntryId, ActionKeys.VIEW);

		return remoteAppEntryLocalService.getRemoteAppEntry(remoteAppEntryId);
	}

	@Override
	public RemoteAppEntry updateCustomElementRemoteAppEntry(
			long remoteAppEntryId, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, Map<Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		_remoteAppEntryModelResourcePermission.check(
			getPermissionChecker(), remoteAppEntryId, ActionKeys.UPDATE);

		return remoteAppEntryLocalService.updateCustomElementRemoteAppEntry(
			getUserId(), remoteAppEntryId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public RemoteAppEntry updateIFrameRemoteAppEntry(
			long remoteAppEntryId, String description,
			String friendlyURLMapping, String iFrameURL,
			Map<Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		_remoteAppEntryModelResourcePermission.check(
			getPermissionChecker(), remoteAppEntryId, ActionKeys.UPDATE);

		return remoteAppEntryLocalService.updateIFrameRemoteAppEntry(
			getUserId(), remoteAppEntryId, description, friendlyURLMapping,
			iFrameURL, nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	@Reference(
		target = "(resource.name=" + RemoteAppConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.remote.app.model.RemoteAppEntry)"
	)
	private ModelResourcePermission<RemoteAppEntry>
		_remoteAppEntryModelResourcePermission;

}