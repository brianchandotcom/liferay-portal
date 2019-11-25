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

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.layout.seo.exception.NoSuchEntryException;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.base.LayoutSEOEntryLocalServiceBaseImpl;
import com.liferay.layout.seo.service.constants.LayoutSEOCustomTagsConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	public void getCustomTagsDDMStructure(Company company)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			company.getGroupId(),
			_classNameLocalService.getClassNameId(LayoutSEOEntry.class),
			LayoutSEOCustomTagsConstants.SEO_CUSTOM_TAG_LAYOUT);

		if (ddmStructure == null) {
			_addCustomTagsDDMStructure(company);
		}
	}

	@Override
	public LayoutSEOEntry updateLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled, Map<Locale, String> canonicalURLMap,
			boolean openGraphDescriptionEnabled,
			Map<Locale, String> openGraphDescriptionMap,
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
				openGraphDescriptionMap, openGraphImageFileEntryId,
				openGraphTitleEnabled, openGraphTitleMap, serviceContext);
		}

		layoutSEOEntry.setModifiedDate(DateUtil.newDate());
		layoutSEOEntry.setCanonicalURLEnabled(canonicalURLEnabled);
		layoutSEOEntry.setCanonicalURLMap(canonicalURLMap);
		layoutSEOEntry.setOpenGraphDescriptionEnabled(
			openGraphDescriptionEnabled);
		layoutSEOEntry.setOpenGraphDescriptionMap(openGraphDescriptionMap);
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
				canonicalURLMap, false, Collections.emptyMap(), 0, false,
				Collections.emptyMap(), serviceContext);
		}

		layoutSEOEntry.setModifiedDate(DateUtil.newDate());
		layoutSEOEntry.setCanonicalURLEnabled(canonicalURLEnabled);
		layoutSEOEntry.setCanonicalURLMap(canonicalURLMap);

		return layoutSEOEntryPersistence.update(layoutSEOEntry);
	}

	private DDMStructure _addCustomTagsDDMStructure(Company company)
		throws PortalException {

		long defaultUserId = _userLocalService.getDefaultUserId(
			company.getCompanyId());

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		for (Locale curLocale :
				LanguageUtil.getAvailableLocales(company.getCompanyId())) {

			nameMap.put(curLocale, "Custom Tags Metadata");
			descriptionMap.put(curLocale, "Custom Tags Metadata");
		}

		String definition = null;

		try {
			definition = _readFile(
				"dependencies/ddm_structure_custom_meta_tags.json");
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}

		Locale locale = company.getLocale();

		definition = StringUtil.replace(
			definition, "[$LOCALE_DEFAULT$]", locale.toString());

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				definition);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		DDMForm ddmForm = ddmFormDeserializerDeserializeResponse.getDDMForm();

		DDMFormLayout ddmFormLayout = _ddm.getDefaultDDMFormLayout(ddmForm);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(company.getGroupId());
		serviceContext.setUserId(defaultUserId);

		return _ddmStructureLocalService.addStructure(
			defaultUserId, company.getGroupId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			_classNameLocalService.getClassNameId(LayoutSEOEntry.class),
			LayoutSEOCustomTagsConstants.SEO_CUSTOM_TAG_KEY, nameMap,
			descriptionMap, ddmForm, ddmFormLayout, StorageType.JSON.toString(),
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	private LayoutSEOEntry _addLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled, Map<Locale, String> canonicalURLMap,
			boolean openGraphDescriptionEnabled,
			Map<Locale, String> openGraphDescriptionMap,
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
		layoutSEOEntry.setOpenGraphDescriptionEnabled(
			openGraphDescriptionEnabled);
		layoutSEOEntry.setOpenGraphDescriptionMap(openGraphDescriptionMap);
		layoutSEOEntry.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
		layoutSEOEntry.setOpenGraphTitleEnabled(openGraphTitleEnabled);
		layoutSEOEntry.setOpenGraphTitleMap(openGraphTitleMap);

		return layoutSEOEntryPersistence.update(layoutSEOEntry);
	}

	private String _readFile(String resourceName) throws IOException {
		List<String> lines = new ArrayList<>();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		StringUtil.readLines(
			classLoader.getResourceAsStream(resourceName), lines);

		return StringUtil.merge(lines, StringPool.SPACE);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

	@Reference
	private UserLocalService _userLocalService;

}