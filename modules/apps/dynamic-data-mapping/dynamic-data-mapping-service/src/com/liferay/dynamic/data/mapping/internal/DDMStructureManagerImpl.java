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

package com.liferay.dynamic.data.mapping.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.DDMStructureManager;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true)
public class DDMStructureManagerImpl implements DDMStructureManager {

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.addStructure(
				userId, groupId, parentStructureKey, classNameId, structureKey,
				nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType,
				type, serviceContext);

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public void deleteStructure(long structureId) throws PortalException {
		_ddmStructureLocalService.deleteStructure(structureId);
	}

	@Override
	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey) {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.fetchStructure(
				groupId, classNameId, structureKey);

		if (ddmStructure == null) {
			return null;
		}

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public DDMStructure fetchStructureByUuidAndGroupId(
		String uuid, long groupId) {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.fetchDDMStructureByUuidAndGroupId(
				uuid, groupId);

		if (ddmStructure == null) {
			return null;
		}

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {

		List<DDMStructure> ddmStructures = new ArrayList<>();

		for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				ddmStructure :
					_ddmStructureLocalService.getClassStructures(
						companyId, classNameId, start, end)) {

			ddmStructures.add(new DDMStructureImpl(ddmStructure));
		}

		return ddmStructures;
	}

	@Override
	public DDMStructure getStructure(long structureId) throws PortalException {
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.getStructure(structureId);

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure =
			_ddmStructureLocalService.getStructure(
				groupId, classNameId, structureKey);

		return new DDMStructureImpl(structure);
	}

	@Override
	public DDMStructure getStructureByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.getDDMStructureByUuidAndGroupId(
				uuid, groupId);

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.updateStructure(
				userId, structureId, parentStructureId, nameMap, descriptionMap,
				ddmForm, ddmFormLayout, serviceContext);

		return new DDMStructureImpl(ddmStructure);
	}

	@Reference
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	private DDMStructureLocalService _ddmStructureLocalService;

}