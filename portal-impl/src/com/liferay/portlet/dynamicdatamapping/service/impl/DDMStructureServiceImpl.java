/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructurePermission;

import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class DDMStructureServiceImpl
	extends DDMStructureServiceBaseImpl {

	public DDMStructure addStructure(
			long groupId, long classNameId, String structureKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, String storageType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_STRUCTURE);

		return ddmStructureLocalService.addStructure(
			getUserId(), groupId, classNameId, structureKey, nameMap,
			descriptionMap, xsd, storageType, serviceContext);
	}

	public DDMStructure copyStructure(
			long structureId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_STRUCTURE);

		return ddmStructureLocalService.copyStructure(
			getUserId(), structureId, serviceContext);
	}

	public void deleteStructure(long structureId)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.DELETE);

		ddmStructureLocalService.deleteStructure(structureId);
	}

	public DDMStructure fetchStructure(long groupId, String structureKey)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = ddmStructurePersistence.fetchByG_S(
			groupId, structureKey);

		if (ddmStructure != null) {
			DDMStructurePermission.check(
				getPermissionChecker(), ddmStructure, ActionKeys.VIEW);
		}

		return ddmStructure;
	}

	public DDMStructure getStructure(long structureId)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.VIEW);

		return ddmStructurePersistence.findByPrimaryKey(structureId);
	}

	public DDMStructure updateStructure(
			long structureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsd,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.UPDATE);

		return ddmStructureLocalService.updateStructure(
			structureId, nameMap, descriptionMap, xsd, serviceContext);
	}

	public DDMStructure updateStructure(
			long groupId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsd,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.UPDATE);

		return ddmStructureLocalService.updateStructure(
			groupId, structureKey, nameMap, descriptionMap, xsd,
			serviceContext);
	}

}