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

package com.liferay.layout.seo.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.layout.seo.exception.NoSuchEntryException;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.base.LayoutSEOEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.layout.seo.model.LayoutSEOEntry",
	service = AopService.class
)
public class LayoutSEOEntryLocalServiceImpl
	extends LayoutSEOEntryLocalServiceBaseImpl {

	@Override
	public void deleteLayoutSEOEntry(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchEntryException {

		layoutSEOEntryPersistence.removeByG_P_L(
			groupId, privateLayout, layoutId);
	}

	@Override
	public void deleteLayoutSEOEntry(String uuid, long groupId)
		throws NoSuchEntryException {

		layoutSEOEntryPersistence.removeByUUID_G(uuid, groupId);
	}

	@Override
	public LayoutSEOEntry fetchLayoutSEOEntry(
		long groupId, boolean privateLayout, long layoutId) {

		return layoutSEOEntryPersistence.fetchByG_P_L(
			groupId, privateLayout, layoutId);
	}

	@Override
	public LayoutSEOEntry updateLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled, Map<Locale, String> canonicalURLMap,
			boolean openGraphDescriptionEnabled,
			Map<Locale, String> openGraphDescriptionMap,
			Map<Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			Map<Locale, String> openGraphTitleMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOEntry layoutSEOEntry = layoutSEOEntryPersistence.fetchByG_P_L(
			groupId, privateLayout, layoutId);

		if (layoutSEOEntry == null) {
			return _addLayoutSEOEntry(
				userId, groupId, privateLayout, layoutId, canonicalURLEnabled,
				canonicalURLMap, openGraphDescriptionEnabled,
				openGraphDescriptionMap, openGraphImageAltMap,
				openGraphImageFileEntryId, openGraphTitleEnabled,
				openGraphTitleMap, serviceContext);
		}

		layoutSEOEntry.setModifiedDate(DateUtil.newDate());
		layoutSEOEntry.setCanonicalURLEnabled(canonicalURLEnabled);
		layoutSEOEntry.setCanonicalURLMap(canonicalURLMap);

		DDMStructure ddmStructure = _getDDMStructure(
			_groupLocalService.getGroup(groupId));

		long ddmStorageId = _updateDDMStorage(
			layoutSEOEntry.getCompanyId(), layoutSEOEntry.getDDMStorageId(),
			ddmStructure.getStructureId(), serviceContext);

		layoutSEOEntry.setDDMStorageId(ddmStorageId);

		layoutSEOEntry.setOpenGraphDescriptionEnabled(
			openGraphDescriptionEnabled);
		layoutSEOEntry.setOpenGraphDescriptionMap(openGraphDescriptionMap);

		if (openGraphImageFileEntryId != 0) {
			layoutSEOEntry.setOpenGraphImageAltMap(openGraphImageAltMap);
		}
		else {
			layoutSEOEntry.setOpenGraphImageAltMap(Collections.emptyMap());
		}

		layoutSEOEntry.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
		layoutSEOEntry.setOpenGraphTitleEnabled(openGraphTitleEnabled);
		layoutSEOEntry.setOpenGraphTitleMap(openGraphTitleMap);

		return layoutSEOEntryPersistence.update(layoutSEOEntry);
	}

	@Override
	public LayoutSEOEntry updateLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled, Map<Locale, String> canonicalURLMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOEntry layoutSEOEntry = layoutSEOEntryPersistence.fetchByG_P_L(
			groupId, privateLayout, layoutId);

		if (layoutSEOEntry == null) {
			return _addLayoutSEOEntry(
				userId, groupId, privateLayout, layoutId, canonicalURLEnabled,
				canonicalURLMap, false, Collections.emptyMap(),
				Collections.emptyMap(), 0, false, Collections.emptyMap(),
				serviceContext);
		}

		layoutSEOEntry.setModifiedDate(DateUtil.newDate());
		layoutSEOEntry.setCanonicalURLEnabled(canonicalURLEnabled);
		layoutSEOEntry.setCanonicalURLMap(canonicalURLMap);

		return layoutSEOEntryPersistence.update(layoutSEOEntry);
	}

	private LayoutSEOEntry _addLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled, Map<Locale, String> canonicalURLMap,
			boolean openGraphDescriptionEnabled,
			Map<Locale, String> openGraphDescriptionMap,
			Map<Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			Map<Locale, String> openGraphTitleMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOEntry layoutSEOEntry = layoutSEOEntryPersistence.create(
			counterLocalService.increment());

		layoutSEOEntry.setUuid(serviceContext.getUuid());
		layoutSEOEntry.setGroupId(groupId);

		Group group = groupLocalService.getGroup(groupId);

		layoutSEOEntry.setCompanyId(group.getCompanyId());

		layoutSEOEntry.setUserId(userId);

		Date now = DateUtil.newDate();

		layoutSEOEntry.setCreateDate(now);
		layoutSEOEntry.setModifiedDate(now);

		layoutSEOEntry.setPrivateLayout(privateLayout);
		layoutSEOEntry.setLayoutId(layoutId);
		layoutSEOEntry.setCanonicalURLEnabled(canonicalURLEnabled);
		layoutSEOEntry.setCanonicalURLMap(canonicalURLMap);

		DDMStructure ddmStructure = _getDDMStructure(
			_groupLocalService.getGroup(groupId));

		long ddmStorageId = _updateDDMStorage(
			layoutSEOEntry.getCompanyId(), layoutSEOEntry.getDDMStorageId(),
			ddmStructure.getStructureId(), serviceContext);

		layoutSEOEntry.setDDMStorageId(ddmStorageId);

		layoutSEOEntry.setOpenGraphDescriptionEnabled(
			openGraphDescriptionEnabled);
		layoutSEOEntry.setOpenGraphDescriptionMap(openGraphDescriptionMap);

		if (openGraphImageFileEntryId != 0) {
			layoutSEOEntry.setOpenGraphImageAltMap(openGraphImageAltMap);
		}

		layoutSEOEntry.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
		layoutSEOEntry.setOpenGraphTitleEnabled(openGraphTitleEnabled);
		layoutSEOEntry.setOpenGraphTitleMap(openGraphTitleMap);

		return layoutSEOEntryPersistence.update(layoutSEOEntry);
	}

	private DDMStructure _getDDMStructure(Group group) throws PortalException {
		Group companyGroup = _groupLocalService.getCompanyGroup(
			group.getCompanyId());

		return _ddmStructureLocalService.getStructure(
			companyGroup.getGroupId(),
			_classNameLocalService.getClassNameId(
				LayoutSEOEntry.class.getName()),
			"custom-meta-tags");
	}

	private DDMFormValues _getDDMFormValues(
			long structureId, ServiceContext serviceContext)
		throws PortalException {

		DDMFormValues ddmFormValues = _ddm.getDDMFormValues(
			structureId, String.valueOf(structureId), serviceContext);

		Set<DDMFormFieldValue> notEmptyDDMFormFieldValues =
			new LinkedHashSet<>();

		HashSet<Locale> availableLocales = new HashSet<>();

		for (DDMFormFieldValue formFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			Value value = formFieldValue.getValue();

			if (value == null) {
				continue;
			}

			for (Locale locale : ddmFormValues.getAvailableLocales()) {
				String localizedValue = value.getString(locale);

				if (!Validator.isBlank(localizedValue)) {
					notEmptyDDMFormFieldValues.add(formFieldValue);

					availableLocales.add(locale);

					continue;
				}

				List<DDMFormFieldValue> nestedDDMFormFieldValues =
					formFieldValue.getNestedDDMFormFieldValues();

				Stream<DDMFormFieldValue> ddmFormFieldValueStream =
					nestedDDMFormFieldValues.stream();

				boolean hasNestedValues = ddmFormFieldValueStream.anyMatch(
					nested -> !Validator.isBlank(
						nested.getValue(
						).getString(
							locale
						)));

				if (hasNestedValues) {
					notEmptyDDMFormFieldValues.add(formFieldValue);
					availableLocales.add(locale);
				}
			}
		}

		ddmFormValues.setAvailableLocales(availableLocales);
		ddmFormValues.setDDMFormFieldValues(
			new ArrayList<>(notEmptyDDMFormFieldValues));

		return ddmFormValues;
	}

	private long _updateDDMStorage(
			long companyId, long ddmStorageId, long structureId,
			ServiceContext serviceContext)
		throws PortalException {

		DDMFormValues ddmFormValues = _getDDMFormValues(
			structureId, serviceContext);

		if (ListUtil.isEmpty(ddmFormValues.getDDMFormFieldValues())) {
			return ddmStorageId;
		}

		if (ddmStorageId == 0) {
			return _storageEngine.create(
				companyId, structureId, ddmFormValues, serviceContext);
		}

		_storageEngine.update(ddmStorageId, ddmFormValues, serviceContext);

		return ddmStorageId;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private StorageEngine _storageEngine;

}