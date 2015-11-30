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

import com.liferay.dynamic.data.mapping.exception.StructureDuplicateElementException;
import com.liferay.dynamic.data.mapping.exception.StructureNameException;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.dynamic.data.mapping.util.DDMIndexerUtil;
import com.liferay.dynamic.data.mapping.util.comparator.StructureIdComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureStructureKeyComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.DDMForm;
import com.liferay.portlet.dynamicdatamapping.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.DDMStructureManager;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDefinitionException;

import java.io.Serializable;

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
	public void addAttributes(
			long structureId, Document document, DDMFormValues ddmFormValues)
		throws PortalException {

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.getStructure(structureId);

		DDMIndexerUtil.addAttributes(
			document, ddmStructure,
			_ddmBeanTranslator.translate(ddmFormValues));
	}

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMForm translatedDDMForm =
				_ddmBeanTranslator.translate(ddmForm);

			com.liferay.dynamic.data.mapping.model.DDMStructure
				ddmStructure = _ddmStructureLocalService.addStructure(
					userId, groupId, parentStructureKey, classNameId,
					structureKey, nameMap, descriptionMap, translatedDDMForm,
					_ddm.getDefaultDDMFormLayout(translatedDDMForm),
					storageType, type, serviceContext);

			return new DDMStructureImpl(ddmStructure);
		}
		catch (PortalException pe) {
			throw translate(pe);
		}
	}

	@Override
	public void deleteStructure(long structureId) throws PortalException {
		try {
			_ddmStructureLocalService.deleteStructure(structureId);
		}
		catch (PortalException pe) {
			throw translate(pe);
		}
	}

	@Override
	public String extractAttributes(
			long structureId, DDMFormValues ddmFormValues, Locale locale)
		throws PortalException {

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.getStructure(structureId);

		return DDMIndexerUtil.extractAttributes(
			ddmStructure, _ddmBeanTranslator.translate(ddmFormValues), locale);
	}

	@Override
	public DDMStructure fetchStructure(long structureId) {
		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.fetchDDMStructure(structureId);

		if (ddmStructure == null) {
			return null;
		}

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey) {

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
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

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.fetchDDMStructureByUuidAndGroupId(
				uuid, groupId);

		if (ddmStructure == null) {
			return null;
		}

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId) {

		List<DDMStructure> ddmStructures = new ArrayList<>();

		List<com.liferay.dynamic.data.mapping.model.DDMStructure>
			structures = _ddmStructureLocalService.getClassStructures(
				companyId, classNameId);

		for (com.liferay.dynamic.data.mapping.model.DDMStructure
				structure : structures) {

			ddmStructures.add(new DDMStructureImpl(structure));
		}

		return ddmStructures;
	}

	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int structureComparator) {

		List<DDMStructure> ddmStructures = new ArrayList<>();

		for (com.liferay.dynamic.data.mapping.model.DDMStructure
				ddmStructure :
					_ddmStructureLocalService.getClassStructures(
						companyId, classNameId,
						getStructureOrderByComparator(structureComparator))) {

			ddmStructures.add(new DDMStructureImpl(ddmStructure));
		}

		return ddmStructures;
	}

	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {

		List<DDMStructure> ddmStructures = new ArrayList<>();

		for (com.liferay.dynamic.data.mapping.model.DDMStructure
				ddmStructure :
					_ddmStructureLocalService.getClassStructures(
						companyId, classNameId, start, end)) {

			ddmStructures.add(new DDMStructureImpl(ddmStructure));
		}

		return ddmStructures;
	}

	@Override
	public JSONArray getDDMFormFieldsJSONArray(long structureId, String script)
		throws PortalException {

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.fetchDDMStructure(structureId);

		return _ddm.getDDMFormFieldsJSONArray(ddmStructure, script);
	}

	@Override
	public Class<?> getDDMStructureModelClass() {
		return com.liferay.dynamic.data.mapping.model.DDMStructure.class;
	}

	@Override
	public Serializable getIndexedFieldValue(
			Serializable fieldValue, String fieldType)
		throws Exception {

		return _ddm.getIndexedFieldValue(fieldValue, fieldType);
	}

	@Override
	public DDMStructure getStructure(long structureId) throws PortalException {
		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
				_ddmStructureLocalService.getStructure(structureId);

			return new DDMStructureImpl(ddmStructure);
		}
		catch (PortalException pe) {
			throw translate(pe);
		}
	}

	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure structure =
				_ddmStructureLocalService.getStructure(
					groupId, classNameId, structureKey);

			return new DDMStructureImpl(structure);
		}
		catch (PortalException pe) {
			throw translate(pe);
		}
	}

	@Override
	public DDMStructure getStructureByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructureByUuidAndGroupId(
					uuid, groupId);

			return new DDMStructureImpl(ddmStructure);
		}
		catch (PortalException pe) {
			throw translate(pe);
		}
	}

	@Override
	public List<DDMStructure> getStructures(long[] groupIds, long classNameId) {
		List<DDMStructure> ddmStructures = new ArrayList<>();

		for (com.liferay.dynamic.data.mapping.model.DDMStructure
				ddmStructure :
					_ddmStructureLocalService.getStructures(
						groupIds, classNameId)) {

			ddmStructures.add(new DDMStructureImpl(ddmStructure));
		}

		return ddmStructures;
	}

	@Override
	public int getStructureStorageLinksCount(long structureId) {
		return _ddmStorageLinkLocalService.getStructureStorageLinksCount(
			structureId);
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, ServiceContext serviceContext)
		throws PortalException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMForm copyDDMForm =
				_ddmBeanTranslator.translate(ddmForm);

			com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
				_ddmStructureLocalService.updateStructure(
					userId, structureId, parentStructureId, nameMap,
					descriptionMap, copyDDMForm,
					_ddm.getDefaultDDMFormLayout(copyDDMForm), serviceContext);

			return new DDMStructureImpl(ddmStructure);
		}
		catch (PortalException pe) {
			throw translate(pe);
		}
	}

	@Override
	public void updateStructureDefinition(long structureId, String definition)
		throws PortalException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(structureId);

			ddmStructure.setDefinition(definition);

			_ddmStructureLocalService.updateDDMStructure(ddmStructure);
		}
		catch (PortalException pe) {
			throw translate(pe);
		}
	}

	@Override
	public void updateStructureKey(long structureId, String structureKey)
		throws PortalException {

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.getDDMStructure(structureId);

		ddmStructure.setStructureKey(structureKey);

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);
	}

	protected OrderByComparator
		<com.liferay.dynamic.data.mapping.model.DDMStructure>
		getStructureOrderByComparator(
			int structureComparator) {

		if (structureComparator ==
				DDMStructureManager.STRUCTURE_COMPARATOR_STRUCTURE_KEY) {

			return new StructureStructureKeyComparator();
		}

		return new StructureIdComparator();
	}

	@Reference(unbind = "-")
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(unbind = "-")
	protected void setDDMBeanTranslator(DDMBeanTranslator ddmBeanTranslator) {
		_ddmBeanTranslator = ddmBeanTranslator;
	}

	@Reference(unbind = "-")
	protected void setDDMStorageLinkLocalService(
		DDMStorageLinkLocalService ddmStorageLinkLocalService) {

		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	protected PortalException translate(PortalException portalException) {
		if (portalException instanceof
				com.liferay.dynamic.data.mapping.exception.
					NoSuchStructureException) {

			return new NoSuchStructureException(
				portalException.getMessage(), portalException.getCause());
		}
		else if (portalException instanceof
					com.liferay.dynamic.data.mapping.exception.
						RequiredStructureException) {

			return new RequiredStructureException(
				portalException.getMessage(), portalException.getCause());
		}
		else if (portalException instanceof
					com.liferay.dynamic.data.mapping.exception.
						StructureDefinitionException) {

			return new StructureDefinitionException(
				portalException.getMessage(), portalException.getCause());
		}
		else if (portalException instanceof
					com.liferay.dynamic.data.mapping.exception.
						StructureDuplicateElementException) {

			return new StructureDuplicateElementException(
				portalException.getMessage(), portalException.getCause());
		}
		else if (portalException instanceof
					com.liferay.dynamic.data.mapping.exception.
						StructureNameException) {

			return new StructureNameException(
				portalException.getMessage(), portalException.getCause());
		}

		return portalException;
	}

	private volatile DDM _ddm;
	private volatile DDMBeanTranslator _ddmBeanTranslator;
	private volatile DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private volatile DDMStructureLocalService _ddmStructureLocalService;

}