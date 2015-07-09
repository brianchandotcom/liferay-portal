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

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.DDMForm;
import com.liferay.portlet.dynamicdatamapping.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.DDMStructureManager;
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

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.addStructure(
					userId, groupId, parentStructureKey, classNameId,
					structureKey, nameMap, descriptionMap, translate(ddmForm),
					translate(ddmFormLayout), storageType, type,
					serviceContext);

			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public void deleteStructure(long structureId) throws PortalException {
		_ddmStructureLocalService.deleteStructure(structureId);
	}

	@Override
	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey) {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.fetchStructure(
					groupId, classNameId, structureKey);

			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure fetchStructureByUuidAndGroupId(
		String uuid, long groupId) {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure =
					_ddmStructureLocalService.fetchDDMStructureByUuidAndGroupId(
						uuid, groupId);

			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {

		try {
			List<DDMStructure> ddmStructures = new ArrayList<>();

			List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>
				structures = _ddmStructureLocalService.getClassStructures(
					companyId, classNameId, start, end);

			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure
					structure : structures) {

				ddmStructures.add(
					BeanPropertiesUtil.deepCopyProperties(
						structure, DDMStructureImpl.class));
			}

			return ddmStructures;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure getStructure(long structureId) throws PortalException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.getStructure(structureId);

			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.getStructure(
					groupId, classNameId, structureKey);

			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure getStructureByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure =
					_ddmStructureLocalService.getDDMStructureByUuidAndGroupId(
						uuid, groupId);

			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.updateStructure(
					userId, parentStructureId, translate(ddmForm),
					translate(ddmFormLayout), serviceContext);

			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Reference
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	protected com.liferay.portlet.dynamicdatamapping.model.DDMForm translate(
			DDMForm ddmForm)
		throws Exception {

		return BeanPropertiesUtil.deepCopyProperties(
			ddmForm,
			com.liferay.portlet.dynamicdatamapping.model.DDMForm.class);
	}

	protected com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout translate(
			DDMFormLayout ddmFormLayout)
		throws Exception {

		return BeanPropertiesUtil.deepCopyProperties(
			ddmFormLayout,
			com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureManagerImpl.class);

	private DDMStructureLocalService _ddmStructureLocalService;

}