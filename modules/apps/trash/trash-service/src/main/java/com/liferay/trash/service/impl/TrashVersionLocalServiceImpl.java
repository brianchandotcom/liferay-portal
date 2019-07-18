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

package com.liferay.trash.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.service.base.TrashVersionLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Zsolt Berentey
 */
@Component(
	property = "model.class.name=com.liferay.trash.model.TrashVersion",
	service = AopService.class
)
public class TrashVersionLocalServiceImpl
	extends TrashVersionLocalServiceBaseImpl {

	@Override
	public TrashVersion addTrashVersion(
		long trashEntryId, String className, long classPK, int status,
		UnicodeProperties typeSettingsProperties) {

		long versionId = counterLocalService.increment();

		TrashVersion trashVersion = trashVersionPersistence.create(versionId);

		trashVersion.setEntryId(trashEntryId);
		trashVersion.setClassName(className);
		trashVersion.setClassPK(classPK);

		if (typeSettingsProperties != null) {
			trashVersion.setTypeSettingsProperties(typeSettingsProperties);
		}

		trashVersion.setStatus(status);

		return trashVersionPersistence.update(trashVersion);
	}

	@Override
	public TrashVersion deleteTrashVersion(String className, long classPK) {
		TrashVersion trashVersion = trashVersionPersistence.fetchByC_C(
			classNameLocalService.getClassNameId(className), classPK);

		if (trashVersion != null) {
			return deleteTrashVersion(trashVersion);
		}

		return null;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #fetchVersion(String, long)}
	 */
	@Deprecated
	@Override
	public TrashVersion fetchVersion(
		long entryId, String className, long classPK) {

		return fetchVersion(className, classPK);
	}

	@Override
	public TrashVersion fetchVersion(String className, long classPK) {
		return trashVersionPersistence.fetchByC_C(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public List<TrashVersion> getVersions(long entryId) {
		return trashVersionPersistence.findByEntryId(entryId);
	}

	@Override
	public List<TrashVersion> getVersions(long entryId, String className) {
		if (Validator.isNull(className)) {
			return trashVersionPersistence.findByEntryId(entryId);
		}

		return trashVersionPersistence.findByE_C(
			entryId, classNameLocalService.getClassNameId(className));
	}

}