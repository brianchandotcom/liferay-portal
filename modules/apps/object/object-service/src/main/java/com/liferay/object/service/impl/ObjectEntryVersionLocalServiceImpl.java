/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.entry.util.ObjectEntryDTOConverterUtil;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryVersion;
import com.liferay.object.service.base.ObjectEntryVersionLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectEntryVersion",
	service = AopService.class
)
public class ObjectEntryVersionLocalServiceImpl
	extends ObjectEntryVersionLocalServiceBaseImpl {

	@Override
	public ObjectEntryVersion addObjectEntryVersion(ObjectEntry objectEntry)
		throws Exception {

		ObjectEntryVersion objectEntryVersion =
			objectEntryVersionPersistence.create(
				counterLocalService.increment());

		objectEntryVersion.setCompanyId(objectEntry.getCompanyId());

		User user = _userLocalService.getUser(objectEntry.getUserId());

		objectEntryVersion.setUserId(user.getUserId());
		objectEntryVersion.setUserName(user.getFullName());

		objectEntryVersion.setCreateDate(objectEntry.getCreateDate());
		objectEntryVersion.setModifiedDate(objectEntry.getModifiedDate());
		objectEntryVersion.setObjectEntryId(objectEntry.getObjectEntryId());

		objectEntryVersion.setContent(
			ObjectEntryDTOConverterUtil.toDTO(
				_dtoConverterRegistry, _jsonFactory, objectEntry, user));

		int count = objectEntryVersionPersistence.countByObjectEntryId(
			objectEntry.getObjectEntryId());

		objectEntryVersion.setVersion(++count);

		objectEntryVersion.setStatus(objectEntry.getStatus());

		return objectEntryVersionPersistence.update(objectEntryVersion);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserLocalService _userLocalService;

}