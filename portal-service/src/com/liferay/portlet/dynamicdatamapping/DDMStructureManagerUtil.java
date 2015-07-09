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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.DDMForm;
import com.liferay.portlet.dynamicdatamapping.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMStructureManagerUtil {

	public static DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException {

		return _getDDMStructureManager().addStructure(
			userId, groupId, parentStructureKey, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
			serviceContext);
	}

	public void deleteDDMStructure(long structureId) throws PortalException {
		_getDDMStructureManager().deleteDDMStructure(structureId);
	}

	public DDMStructure fetchDDMStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		return _getDDMStructureManager().getDDMStructure(
			groupId, classNameId, structureKey, true);
	}

	public DDMStructure fetchDDMStructureByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return _getDDMStructureManager().getDDMStructureByUuidAndGroupId(
			uuid, groupId, true);
	}

	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {

		return _getDDMStructureManager().getClassStructures(
			companyId, classNameId, start, end);
	}

	public DDMStructure getDDMStructure(long structureId)
		throws PortalException {

		return _getDDMStructureManager().getDDMStructure(structureId);
	}

	public DDMStructure getDDMStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		return _getDDMStructureManager().getDDMStructure(
			groupId, classNameId, structureKey, false);
	}

	public DDMStructure getDDMStructureByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return _getDDMStructureManager().getDDMStructureByUuidAndGroupId(
			uuid, groupId, false);
	}

	public DDMStructure updateDDMStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		return _getDDMStructureManager().updateDDMStructure(
			userId, structureId, parentStructureId, nameMap, descriptionMap,
			ddmForm, ddmFormLayout, serviceContext);
	}

	private static DDMStructureManager _getDDMStructureManager() {
		return _instance._serviceTracker.getService();
	}

	private DDMStructureManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(DDMStructureManager.class);

		_serviceTracker.open();
	}

	private static final DDMStructureManagerUtil _instance =
		new DDMStructureManagerUtil();

	private final ServiceTracker<DDMStructureManager,
		DDMStructureManager> _serviceTracker;

}