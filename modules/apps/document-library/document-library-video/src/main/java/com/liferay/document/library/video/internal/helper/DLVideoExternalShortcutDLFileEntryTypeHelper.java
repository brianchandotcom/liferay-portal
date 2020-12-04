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

package com.liferay.document.library.video.internal.helper;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.video.internal.constants.DLVideoConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Iván Zaera
 * @author Alejandro Tardín
 */
public class DLVideoExternalShortcutDLFileEntryTypeHelper {

	public DLVideoExternalShortcutDLFileEntryTypeHelper(
		Company company, DefaultDDMStructureHelper defaultDDMStructureHelper,
		long dlFileEntryMetadataClassNameId,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		UserLocalService userLocalService) {

		_company = company;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_dlFileEntryMetadataClassNameId = dlFileEntryMetadataClassNameId;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_userLocalService = userLocalService;
	}

	public void addDLVideoExternalShortcutDLFileEntryType() throws Exception {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			DLVideoConstants.DDM_STRUCTURE_KEY_DL_VIDEO_EXTERNAL_SHORTCUT);

		if (ddmStructure == null) {
			ddmStructure = _addDLVideoExternalShortcutDDMStructure();
		}

		List<DLFileEntryType> dlFileEntryTypes =
			_dlFileEntryTypeLocalService.getFileEntryTypes(
				ddmStructure.getStructureId());

		if (dlFileEntryTypes.isEmpty()) {
			_addDLVideoExternalShortcutDLFileEntryType(
				ddmStructure.getStructureId());
		}
	}

	private DDMStructure _addDLVideoExternalShortcutDDMStructure()
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		serviceContext.setUserId(defaultUserId);

		Class<?> clazz = getClass();

		_defaultDDMStructureHelper.addDDMStructures(
			defaultUserId, _company.getGroupId(),
			_dlFileEntryMetadataClassNameId, clazz.getClassLoader(),
			"com/liferay/document/library/video/internal/util/dependencies" +
				"/dl-video-external-shortcut-metadata-structure.xml",
			serviceContext);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			DLVideoConstants.DL_FILE_ENTRY_TYPE_KEY);

		ddmStructure.setNameMap(_updateNameMap(ddmStructure.getNameMap()));
		ddmStructure.setDescriptionMap(
			_updateDescriptionMap(ddmStructure.getDescriptionMap()));

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);

		return ddmStructure;
	}

	private void _addDLVideoExternalShortcutDLFileEntryType(long ddmStructureId)
		throws Exception {

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), DLVideoConstants.DL_FILE_ENTRY_TYPE_NAME
		).build();

		Map<Locale, String> descriptionMap = new HashMap<>();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());
		serviceContext.setUserId(defaultUserId);

		_dlFileEntryTypeLocalService.addFileEntryType(
			defaultUserId, _company.getGroupId(),
			DLVideoConstants.DL_FILE_ENTRY_TYPE_KEY, nameMap, descriptionMap,
			new long[] {ddmStructureId}, serviceContext);
	}

	private Map<Locale, String> _updateDescriptionMap(
		Map<Locale, String> descriptionMap) {

		Map<Locale, String> updatedDescriptionMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				entry.getKey(),
				DLVideoExternalShortcutDLFileEntryTypeHelper.class);

			updatedDescriptionMap.put(
				entry.getKey(),
				LanguageUtil.get(resourceBundle, entry.getValue()));
		}

		return updatedDescriptionMap;
	}

	private Map<Locale, String> _updateNameMap(Map<Locale, String> nameMap) {
		Map<Locale, String> updatedNameMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				entry.getKey(),
				DLVideoExternalShortcutDLFileEntryTypeHelper.class);

			updatedNameMap.put(
				entry.getKey(),
				LanguageUtil.get(
					resourceBundle, "dl-video-external-shortcut-metadata"));
		}

		return updatedNameMap;
	}

	private final Company _company;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final long _dlFileEntryMetadataClassNameId;
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final UserLocalService _userLocalService;

}