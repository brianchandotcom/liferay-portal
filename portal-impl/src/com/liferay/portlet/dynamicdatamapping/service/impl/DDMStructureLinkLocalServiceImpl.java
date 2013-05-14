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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureLinkLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class DDMStructureLinkLocalServiceImpl
	extends DDMStructureLinkLocalServiceBaseImpl {

	@Override
	public DDMStructureLink addStructureLink(
			long classNameId, long classPK, long structureId,
			ServiceContext serviceContext)
		throws SystemException {

		long structureLinkId = counterLocalService.increment();

		DDMStructureLink structureLink = ddmStructureLinkPersistence.create(
			structureLinkId);

		structureLink.setClassNameId(classNameId);
		structureLink.setClassPK(classPK);
		structureLink.setStructureId(structureId);

		ddmStructureLinkPersistence.update(structureLink);

		return structureLink;
	}

	@Override
	public void deleteClassStructureLink(long classPK)
		throws PortalException, SystemException {

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.findByClassPK(classPK);

		deleteStructureLink(structureLink);
	}

	@Override
	public void deleteStructureLink(DDMStructureLink structureLink)
		throws SystemException {

		ddmStructureLinkPersistence.remove(structureLink);
	}

	@Override
	public void deleteStructureLink(long structureLinkId)
		throws PortalException, SystemException {

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.findByPrimaryKey(structureLinkId);

		deleteStructureLink(structureLink);
	}

	@Override
	public void deleteStructureStructureLinks(long structureId)
		throws SystemException {

		List<DDMStructureLink> structureLinks =
			ddmStructureLinkPersistence.findByStructureId(structureId);

		for (DDMStructureLink structureLink : structureLinks) {
			deleteStructureLink(structureLink);
		}
	}

	@Override
	public DDMStructureLink getClassStructureLink(long classPK)
		throws PortalException, SystemException {

		return ddmStructureLinkPersistence.findByClassPK(classPK);
	}

	@Override
	public List<DDMStructureLink> getClassStructureLinks(long classNameId)
		throws SystemException {

		return ddmStructureLinkPersistence.findByStructureId(classNameId);
	}

	@Override
	public DDMStructureLink getStructureLink(long structureLinkId)
		throws PortalException, SystemException {

		return ddmStructureLinkPersistence.findByPrimaryKey(structureLinkId);
	}

	@Override
	public List<DDMStructureLink> getStructureLinks(
			long structureId, int start, int end)
		throws SystemException {

		return ddmStructureLinkPersistence.findByStructureId(
			structureId, start, end);
	}

	@Override
	public DDMStructureLink updateStructureLink(
			long structureLinkId, long classNameId, long classPK,
			long structureId)
		throws PortalException, SystemException {

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.findByPrimaryKey(structureLinkId);

		structureLink.setClassNameId(classNameId);
		structureLink.setClassPK(classPK);
		structureLink.setStructureId(structureId);

		ddmStructureLinkPersistence.update(structureLink);

		return structureLink;
	}

}