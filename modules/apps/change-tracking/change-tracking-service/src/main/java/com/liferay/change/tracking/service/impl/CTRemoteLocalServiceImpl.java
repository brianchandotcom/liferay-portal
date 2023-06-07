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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.service.base.CTRemoteLocalServiceBaseImpl;
import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTRemote",
	service = AopService.class
)
public class CTRemoteLocalServiceImpl extends CTRemoteLocalServiceBaseImpl {

	@Override
	public CTRemote addCTRemote(
			long userId, String name, String description, String url)
		throws PortalException {

		long ctRemoteId = counterLocalService.increment(
			CTRemote.class.getName());

		CTRemote ctRemote = ctRemotePersistence.create(ctRemoteId);

		User user = _userLocalService.getUser(userId);

		ctRemote.setCompanyId(user.getCompanyId());

		ctRemote.setUserId(userId);
		ctRemote.setName(name);
		ctRemote.setDescription(description);
		ctRemote.setUrl(url);

		ctRemote = ctRemotePersistence.update(ctRemote);

		_resourceLocalService.addResources(
			ctRemote.getCompanyId(), 0, ctRemote.getUserId(),
			CTRemote.class.getName(), ctRemote.getCtRemoteId(), false, false,
			false);

		return ctRemote;
	}

	@Override
	public CTRemote fetchCTRemote(long ctRemoteId) {
		return ctRemotePersistence.fetchByPrimaryKey(ctRemoteId);
	}

	@Override
	public List<CTRemote> getCTRemotes(long companyId, int start, int end) {
		return ctRemotePersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public CTRemote updateCTRemote(
			long ctRemoteId, String name, String description, String url)
		throws PortalException {

		CTRemote ctRemote = ctRemotePersistence.findByPrimaryKey(ctRemoteId);

		ctRemote.setName(name);
		ctRemote.setDescription(description);
		ctRemote.setUrl(url);

		return ctRemotePersistence.update(ctRemote);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private JSONStorageEntryLocalService _jsonStorageEntryLocalService;

	@Reference(target = "(resource.name=" + CTConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}