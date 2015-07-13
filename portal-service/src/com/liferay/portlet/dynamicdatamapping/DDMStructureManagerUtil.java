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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
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

		DDMStructureManager ddmStructureManager = _getDDMStructureManager();

		return ddmStructureManager.addStructure(
			userId, groupId, parentStructureKey, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
			serviceContext);
	}

	public static void deleteStructure(long structureId)
		throws PortalException {

		DDMStructureManager ddmStructureManager = _getDDMStructureManager();

		ddmStructureManager.deleteStructure(structureId);
	}

	public static DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey) {

		DDMStructureManager ddmStructureManager = _getDDMStructureManager();

		return ddmStructureManager.fetchStructure(
			groupId, classNameId, structureKey);
	}

	public static DDMStructure fetchStructureByUuidAndGroupId(
		String uuid, long groupId) {

		DDMStructureManager ddmStructureManager = _getDDMStructureManager();

		return ddmStructureManager.fetchStructureByUuidAndGroupId(
			uuid, groupId);
	}

	public static List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {

		DDMStructureManager ddmStructureManager = _getDDMStructureManager();

		return ddmStructureManager.getClassStructures(
			companyId, classNameId, start, end);
	}

	public static DDMStructure getStructure(long structureId)
		throws PortalException {

		DDMStructureManager ddmStructureManager = _getDDMStructureManager();

		return ddmStructureManager.getStructure(structureId);
	}

	public static DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		DDMStructureManager ddmStructureManager = _getDDMStructureManager();

		return ddmStructureManager.getStructure(
			groupId, classNameId, structureKey);
	}

	public static DDMStructure getStructureByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		DDMStructureManager ddmStructureManager = _getDDMStructureManager();

		return ddmStructureManager.getStructureByUuidAndGroupId(uuid, groupId);
	}

	public static DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructureManager ddmStructureManager = _getDDMStructureManager();

		return ddmStructureManager.updateStructure(
			userId, structureId, parentStructureId, nameMap, descriptionMap,
			ddmForm, ddmFormLayout, serviceContext);
	}

	private static DDMStructureManager _getDDMStructureManager() {
		DDMStructureManager ddmStructureManager =
			_instance._serviceTracker.getService();

		if (ddmStructureManager == null) {
			return _dummyDDMStructureManagerImpl;
		}

		return ddmStructureManager;
	}

	private DDMStructureManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(DDMStructureManager.class);

		_serviceTracker.open();
	}

	private static final DDMStructureManagerUtil _instance =
		new DDMStructureManagerUtil();

	private static final DummyDDMStructureManagerImpl
		_dummyDDMStructureManagerImpl = new DummyDDMStructureManagerImpl();

	private final ServiceTracker<DDMStructureManager, DDMStructureManager>
		_serviceTracker;

}