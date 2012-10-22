/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStorageLinkLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDMStorageLinkLocalServiceImpl
	extends DDMStorageLinkLocalServiceBaseImpl {

	public DDMStorageLink addStorageLink(
			long classNameId, long classPK, long structureId,
			ServiceContext serviceContext)
		throws SystemException {

		long storageLinkId = counterLocalService.increment();

		DDMStorageLink storageLink = ddmStorageLinkPersistence.create(
			storageLinkId);

		storageLink.setClassNameId(classNameId);
		storageLink.setClassPK(classPK);
		storageLink.setStructureId(structureId);

		ddmStorageLinkPersistence.update(storageLink);

		return storageLink;
	}

	public void deleteClassStorageLink(long classPK)
		throws PortalException, SystemException {

		DDMStorageLink storageLink = ddmStorageLinkPersistence.findByClassPK(
			classPK);

		deleteStorageLink(storageLink);
	}

	public void deleteStorageLink(DDMStorageLink storageLink)
		throws SystemException {

		ddmStorageLinkPersistence.remove(storageLink);
	}

	public void deleteStorageLink(long storageLinkId)
		throws PortalException, SystemException {

		DDMStorageLink storageLink = ddmStorageLinkPersistence.findByPrimaryKey(
			storageLinkId);

		deleteStorageLink(storageLink);
	}

	public void deleteStructureStorageLinks(long structureId)
		throws SystemException {

		List<DDMStorageLink> storageLinks =
			ddmStorageLinkPersistence.findByStructureId(structureId);

		for (DDMStorageLink storageLink : storageLinks) {
			deleteStorageLink(storageLink);
		}
	}

	public DDMStorageLink getClassStorageLink(long classPK)
		throws PortalException, SystemException {

		return ddmStorageLinkPersistence.findByClassPK(classPK);
	}

	public DDMStorageLink getStorageLink(long storageLinkId)
		throws PortalException, SystemException {

		return ddmStorageLinkPersistence.findByPrimaryKey(storageLinkId);
	}

	public List<DDMStorageLink> getStructureStorageLinks(long structureId)
		throws SystemException {

		return ddmStorageLinkPersistence.findByStructureId(structureId);
	}

	public int getStructureStorageLinksCount(long structureId)
		throws SystemException {

		return ddmStorageLinkPersistence.countByStructureId(structureId);
	}

	public DDMStorageLink updateStorageLink(
			long storageLinkId, long classNameId, long classPK)
		throws PortalException, SystemException {

		DDMStorageLink storageLink = ddmStorageLinkPersistence.findByPrimaryKey(
			storageLinkId);

		storageLink.setClassNameId(classNameId);
		storageLink.setClassPK(classPK);

		ddmStorageLinkPersistence.update(storageLink);

		return storageLink;
	}

}