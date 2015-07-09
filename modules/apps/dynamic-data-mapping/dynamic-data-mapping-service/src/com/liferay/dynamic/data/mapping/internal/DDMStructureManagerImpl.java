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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.DDMForm;
import com.liferay.portlet.dynamicdatamapping.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureManager;

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
		long userId, long groupId, String parentStructureKey, long classNameId,
		String structureKey, Map<Locale, String> nameMap,
		Map<Locale, String> descriptionMap, DDMForm ddmForm,
		DDMFormLayout ddmFormLayout, String storageType, int type,
		ServiceContext serviceContext) throws PortalException {

		com.liferay.portlet.dynamicdatamapping.model.DDMForm form;
		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout formLayout;
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure;

		try {
			form = BeanPropertiesUtil.deepCopyProperties(
				ddmForm,
				com.liferay.portlet.dynamicdatamapping.model.DDMForm.class);

			formLayout = BeanPropertiesUtil.deepCopyProperties(
				ddmFormLayout,
			com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout.class);

			structure = _ddmStructureLocalService.addStructure(
				userId, groupId, parentStructureKey, classNameId, structureKey,
				nameMap, descriptionMap, form, formLayout, storageType, type,
				serviceContext);

			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (PortalException e) {
			throw e;
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@Override
	public void deleteDDMStructure(long structureId) throws PortalException {
		_ddmStructureLocalService.deleteDDMStructure(structureId);
	}

	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {

		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>
			structures;
		List<DDMStructure> ddmStructures = new ArrayList<>();

		structures = _ddmStructureLocalService.getClassStructures(
			companyId, classNameId, start, end);

		try {
			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure : structures) {

				ddmStructures.add(
					BeanPropertiesUtil.deepCopyProperties(
						structure, DDMStructureImpl.class));
			}
		} catch (Exception e) { }

		return ddmStructures;
	}

	@Override
	public DDMStructure getDDMStructure(long structureId)
		throws PortalException {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure =
			_ddmStructureLocalService.getDDMStructure(structureId);

		try {
			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@Override
	public DDMStructure getDDMStructure(
			long groupId, long classNameId, String structureKey, boolean fetch)
		throws PortalException {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure =
			_ddmStructureLocalService.getStructure(
				groupId, classNameId, structureKey);

		try {
			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@Override
	public DDMStructure getDDMStructureByUuidAndGroupId(
			String uuid, long groupId, boolean fetch)
		throws PortalException {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure =
			_ddmStructureLocalService.getDDMStructureByUuidAndGroupId(
				uuid, groupId);

		try {
			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@Override
	public DDMStructure updateDDMStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		com.liferay.portlet.dynamicdatamapping.model.DDMForm form;
		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout formLayout;
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure;

		try {
			form = BeanPropertiesUtil.deepCopyProperties(
				ddmForm,
				com.liferay.portlet.dynamicdatamapping.model.DDMForm.class);

			formLayout = BeanPropertiesUtil.deepCopyProperties(
				ddmFormLayout,
			com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout.class);

			structure = _ddmStructureLocalService.updateStructure(
				userId, parentStructureId, form, formLayout, serviceContext);

			return BeanPropertiesUtil.deepCopyProperties(
				structure, DDMStructureImpl.class);
		}
		catch (PortalException e) {
			throw e;
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@Reference
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	private DDMStructureLocalService _ddmStructureLocalService;

}