/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.service.base.TrashVersionLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class TrashVersionLocalServiceImpl
	extends TrashVersionLocalServiceBaseImpl {

	@Override
	public void addTrashVersion(
			long trashEntryId, String className, long classPK, int status)
		throws SystemException {

		long versionId = counterLocalService.increment();

		TrashVersion trashVersion = trashVersionPersistence.create(versionId);

		trashVersion.setEntryId(trashEntryId);
		trashVersion.setClassName(className);
		trashVersion.setClassPK(classPK);
		trashVersion.setStatus(status);

		trashVersionPersistence.update(trashVersion);
	}

	@Override
	public TrashVersion fetchVersion(
			long entryId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		TrashVersion version = trashVersionPersistence.fetchByE_C_C(
			entryId, classNameId, classPK);

		return version;
	}

	@Override
	public List<TrashVersion> getVersions(long entryId) throws SystemException {
		return trashVersionPersistence.findByEntryId(entryId);
	}

	@Override
	public List<TrashVersion> getVersions(long entryId, String className)
		throws SystemException {

		if (Validator.isNull(className)) {
			return trashVersionPersistence.findByEntryId(entryId);
		}

		long classNameId = PortalUtil.getClassNameId(className);

		return trashVersionPersistence.findByE_C(entryId, classNameId);
	}

	/**
	 * Returns all the trash versions associated with the trash entry.
	 *
	 * @param  className the class name of the trash entity
	 * @param  classPK the primary key of the trash entity
	 * @return all the trash versions associated with the trash entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<TrashVersion> getVersions(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return trashVersionPersistence.findByC_C(classNameId, classPK);
	}

}