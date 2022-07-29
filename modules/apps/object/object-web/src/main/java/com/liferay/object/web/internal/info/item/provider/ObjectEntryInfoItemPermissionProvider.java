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

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.web.internal.util.ObjectDefinitionPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Lourdes Fernández Besada
 */
public class ObjectEntryInfoItemPermissionProvider
	implements InfoItemPermissionProvider<ObjectEntry> {

	public ObjectEntryInfoItemPermissionProvider(
		ObjectDefinition objectDefinition,
		ObjectEntryService objectEntryService) {

		_objectDefinition = objectDefinition;
		_objectEntryService = objectEntryService;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return _hasPermission(actionId, classPKInfoItemIdentifier.getClassPK());
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, ObjectEntry objectEntry,
			String actionId)
		throws InfoItemPermissionException {

		return _hasPermission(actionId, objectEntry.getObjectEntryId());
	}

	private boolean _hasPermission(String actionId, long objectEntryId) {
		try {
			return ObjectDefinitionPermissionUtil.hasModelResourcePermission(
				_objectDefinition, objectEntryId, _objectEntryService,
				actionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemPermissionProvider.class);

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryService _objectEntryService;

}