/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.entry.util.ObjectEntryDTOConverterUtil;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryVersion;
import com.liferay.object.service.base.ObjectEntryVersionLocalServiceBaseImpl;
import com.liferay.object.util.comparator.ObjectEntryVersionVersionComparator;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.List;

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
		throws PortalException {

		return _updateObjectEntryVersion(
			objectEntry,
			objectEntryVersionPersistence.create(
				counterLocalService.increment()),
			objectEntry.getVersion() + 1);
	}

	@Override
	public void deleteObjectEntryVersions(long objectEntryId) {
		objectEntryVersionPersistence.removeByObjectEntryId(objectEntryId);
	}

	@Override
	public ObjectEntryVersion getObjectEntryVersion(
			long objectEntryId, int version)
		throws PortalException {

		return objectEntryVersionPersistence.findByOEI_V(
			objectEntryId, version);
	}

	@Override
	public List<ObjectEntryVersion> getObjectEntryVersions(long objectEntryId) {
		return objectEntryVersionPersistence.findByObjectEntryId(objectEntryId);
	}

	@Override
	public List<ObjectEntryVersion> getObjectEntryVersions(
		long objectEntryId, int start, int end) {

		return objectEntryVersionPersistence.findByObjectEntryId(
			objectEntryId, start, end);
	}

	@Override
	public int getObjectEntryVersionsCount(long objectEntryId) {
		return objectEntryVersionPersistence.countByObjectEntryId(
			objectEntryId);
	}

	@Override
	public ObjectEntryVersion updateLatestObjectEntryVersion(
			ObjectEntry objectEntry)
		throws PortalException {

		return _updateObjectEntryVersion(
			objectEntry,
			objectEntryVersionPersistence.fetchByObjectEntryId_First(
				objectEntry.getObjectEntryId(),
				ObjectEntryVersionVersionComparator.getInstance(false)),
			objectEntry.getVersion());
	}

	private ObjectEntryVersion _updateObjectEntryVersion(
			ObjectEntry objectEntry, ObjectEntryVersion objectEntryVersion,
			int version)
		throws PortalException {

		User user = _userLocalService.getUser(objectEntry.getUserId());

		objectEntryVersion.setUserId(user.getUserId());
		objectEntryVersion.setUserName(user.getFullName());

		objectEntryVersion.setCreateDate(objectEntry.getCreateDate());
		objectEntryVersion.setModifiedDate(objectEntry.getModifiedDate());
		objectEntryVersion.setObjectEntryId(objectEntry.getObjectEntryId());

		try {
			objectEntryVersion.setContent(
				ObjectEntryDTOConverterUtil.toDTO(
					_dtoConverterRegistry, _jsonFactory, objectEntry, user));
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		objectEntryVersion.setVersion(version);
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