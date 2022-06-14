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

import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.base.ClientExtensionEntryRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.client.extension.model.ClientExtensionEntryRel",
	service = AopService.class
)
public class ClientExtensionEntryRelLocalServiceImpl
	extends ClientExtensionEntryRelLocalServiceBaseImpl {

	@Override
	public ClientExtensionEntryRel addClientExtensionEntryRel(
			long userId, long classNameId, long classPK,
			String cetExternalReferenceCode, String type)
		throws PortalException {

		ClientExtensionEntryRel clientExtensionEntryRel =
			clientExtensionEntryRelPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		clientExtensionEntryRel.setCompanyId(user.getCompanyId());
		clientExtensionEntryRel.setUserId(user.getUserId());
		clientExtensionEntryRel.setUserName(user.getFullName());

		clientExtensionEntryRel.setClassNameId(classNameId);
		clientExtensionEntryRel.setClassPK(classPK);
		clientExtensionEntryRel.setCETExternalReferenceCode(
			cetExternalReferenceCode);
		clientExtensionEntryRel.setType(type);

		return clientExtensionEntryRelPersistence.update(
			clientExtensionEntryRel);
	}

	@Override
	public void deleteClientExtensionEntryRels(long classNameId, long classPK) {
		clientExtensionEntryRelPersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public void deleteClientExtensionEntryRels(
		long classNameId, long classPK, String type) {

		clientExtensionEntryRelPersistence.removeByC_C_T(
			classNameId, classPK, type);
	}

	@Override
	public ClientExtensionEntryRel fetchClientExtensionEntryRel(
		long classNameId, long classPK, String type) {

		return clientExtensionEntryRelPersistence.fetchByC_C_T_First(
			classNameId, classPK, type, null);
	}

	@Override
	public List<ClientExtensionEntryRel> getClientExtensionEntryRels(
		long classNameId, long classPK) {

		return clientExtensionEntryRelPersistence.findByC_C(
			classNameId, classPK);
	}

	@Override
	public List<ClientExtensionEntryRel> getClientExtensionEntryRels(
		long classNameId, long classPK, String type, int start, int end) {

		return clientExtensionEntryRelPersistence.findByC_C_T(
			classNameId, classPK, type, start, end);
	}

	@Override
	public int getClientExtensionEntryRelsCount(
		long classNameId, long classPK, String type) {

		return clientExtensionEntryRelPersistence.countByC_C_T(
			classNameId, classPK, type);
	}

	@Reference
	private UserLocalService _userLocalService;

}