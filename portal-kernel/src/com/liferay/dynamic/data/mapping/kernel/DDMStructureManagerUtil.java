/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.ServiceContext;

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
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException {

		DDMStructureManager ddmStructureManager =
			_ddmStructureManagerSnapshot.get();

		return ddmStructureManager.addStructure(
			userId, groupId, parentStructureKey, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, storageType, type,
			serviceContext);
	}

	public static void deleteStructure(long structureId)
		throws PortalException {

		DDMStructureManager ddmStructureManager =
			_ddmStructureManagerSnapshot.get();

		ddmStructureManager.deleteStructure(structureId);
	}

	public static DDMStructure fetchStructure(long structureId) {
		DDMStructureManager ddmStructureManager =
			_ddmStructureManagerSnapshot.get();

		return ddmStructureManager.fetchStructure(structureId);
	}

	public static DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey) {

		DDMStructureManager ddmStructureManager =
			_ddmStructureManagerSnapshot.get();

		return ddmStructureManager.fetchStructure(
			groupId, classNameId, structureKey);
	}

	public static List<DDMStructure> getClassStructures(
		long companyId, long classNameId) {

		DDMStructureManager ddmStructureManager =
			_ddmStructureManagerSnapshot.get();

		return ddmStructureManager.getClassStructures(companyId, classNameId);
	}

	public static DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, ServiceContext serviceContext)
		throws PortalException {

		DDMStructureManager ddmStructureManager =
			_ddmStructureManagerSnapshot.get();

		return ddmStructureManager.updateStructure(
			userId, structureId, parentStructureId, nameMap, descriptionMap,
			ddmForm, serviceContext);
	}

	public static void updateStructureKey(long structureId, String structureKey)
		throws PortalException {

		DDMStructureManager ddmStructureManager =
			_ddmStructureManagerSnapshot.get();

		ddmStructureManager.updateStructureKey(structureId, structureKey);
	}

	private static final Snapshot<DDMStructureManager>
		_ddmStructureManagerSnapshot = new Snapshot<>(
			DDMStructureManagerUtil.class, DDMStructureManager.class);

}