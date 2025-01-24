/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.service.impl;

import com.liferay.layout.seo.exception.NoSuchEntryException;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.base.LayoutSEOEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;

import java.util.Collections;
import java.util.Date;
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
	public LayoutSEOEntry copyLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout,
			long sourceLayoutId, boolean canonicalURLEnabled,
			Map<Locale, String> canonicalURLMap,
			boolean openGraphDescriptionEnabled,
			Map<Locale, String> openGraphDescriptionMap,
			Map<Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			Map<Locale, String> openGraphTitleMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOEntry layoutSEOEntry = layoutSEOEntryPersistence.fetchByG_P_L(
			groupId, privateLayout, sourceLayoutId);

		if (layoutSEOEntry == null) {
			return _addLayoutSEOEntry(
				userId, groupId, privateLayout, sourceLayoutId,
				canonicalURLEnabled, canonicalURLMap,
				openGraphDescriptionEnabled, openGraphDescriptionMap,
				openGraphImageAltMap, openGraphImageFileEntryId,
				openGraphTitleEnabled, openGraphTitleMap, serviceContext);
		}

		return updateLayoutSEOEntry(
			userId, groupId, privateLayout, sourceLayoutId, canonicalURLEnabled,
			canonicalURLMap, openGraphDescriptionEnabled,
			openGraphDescriptionMap, openGraphImageAltMap,
			openGraphImageFileEntryId, openGraphTitleEnabled, openGraphTitleMap,
			serviceContext);
	}

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
	public List<LayoutSEOEntry> getLayoutSEOEntriesByUuidAndCompanyId(
		String uuid, long companyId) {

		return layoutSEOEntryPersistence.findByUuid_C(uuid, companyId);
	}

	@Override
	public LayoutSEOEntry updateCustomMetaTags(
			long userId, long groupId, boolean privateLayout, long layoutId,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOEntry layoutSEOEntry = layoutSEOEntryPersistence.fetchByG_P_L(
			groupId, privateLayout, layoutId);

		if (layoutSEOEntry == null) {
			return _addLayoutSEOEntry(
				userId, groupId, privateLayout, layoutId, false,
				Collections.emptyMap(), false, Collections.emptyMap(),
				Collections.emptyMap(), 0, false, Collections.emptyMap(),
				serviceContext);
		}

		layoutSEOEntry.setModifiedDate(DateUtil.newDate());

		return layoutSEOEntryPersistence.update(layoutSEOEntry);
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
		layoutSEOEntry.setCanonicalURLMap(canonicalURLMap);
		layoutSEOEntry.setCanonicalURLEnabled(canonicalURLEnabled);
		layoutSEOEntry.setOpenGraphDescriptionMap(openGraphDescriptionMap);
		layoutSEOEntry.setOpenGraphDescriptionEnabled(
			openGraphDescriptionEnabled);

		if (openGraphImageFileEntryId != 0) {
			layoutSEOEntry.setOpenGraphImageAltMap(openGraphImageAltMap);
		}
		else {
			layoutSEOEntry.setOpenGraphImageAltMap(Collections.emptyMap());
		}

		layoutSEOEntry.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
		layoutSEOEntry.setOpenGraphTitleMap(openGraphTitleMap);
		layoutSEOEntry.setOpenGraphTitleEnabled(openGraphTitleEnabled);

		return layoutSEOEntryPersistence.update(layoutSEOEntry);
	}

	@Override
	public LayoutSEOEntry updateLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout, long layoutId,
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
				userId, groupId, privateLayout, layoutId, false,
				Collections.emptyMap(), openGraphDescriptionEnabled,
				openGraphDescriptionMap, openGraphImageAltMap,
				openGraphImageFileEntryId, openGraphTitleEnabled,
				openGraphTitleMap, serviceContext);
		}

		layoutSEOEntry.setModifiedDate(DateUtil.newDate());

		layoutSEOEntry.setOpenGraphDescriptionMap(openGraphDescriptionMap);
		layoutSEOEntry.setOpenGraphDescriptionEnabled(
			openGraphDescriptionEnabled);

		if (openGraphImageFileEntryId != 0) {
			layoutSEOEntry.setOpenGraphImageAltMap(openGraphImageAltMap);
		}
		else {
			layoutSEOEntry.setOpenGraphImageAltMap(Collections.emptyMap());
		}

		layoutSEOEntry.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
		layoutSEOEntry.setOpenGraphTitleMap(openGraphTitleMap);
		layoutSEOEntry.setOpenGraphTitleEnabled(openGraphTitleEnabled);

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
		layoutSEOEntry.setCanonicalURLMap(canonicalURLMap);
		layoutSEOEntry.setCanonicalURLEnabled(canonicalURLEnabled);

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

		Group group = _groupLocalService.getGroup(groupId);

		layoutSEOEntry.setCompanyId(group.getCompanyId());

		layoutSEOEntry.setUserId(userId);

		Date date = DateUtil.newDate();

		layoutSEOEntry.setCreateDate(date);
		layoutSEOEntry.setModifiedDate(date);

		layoutSEOEntry.setPrivateLayout(privateLayout);
		layoutSEOEntry.setLayoutId(layoutId);
		layoutSEOEntry.setCanonicalURLMap(canonicalURLMap);
		layoutSEOEntry.setCanonicalURLEnabled(canonicalURLEnabled);
		layoutSEOEntry.setOpenGraphDescriptionMap(openGraphDescriptionMap);
		layoutSEOEntry.setOpenGraphDescriptionEnabled(
			openGraphDescriptionEnabled);

		if (openGraphImageFileEntryId != 0) {
			layoutSEOEntry.setOpenGraphImageAltMap(openGraphImageAltMap);
		}

		layoutSEOEntry.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
		layoutSEOEntry.setOpenGraphTitleMap(openGraphTitleMap);
		layoutSEOEntry.setOpenGraphTitleEnabled(openGraphTitleEnabled);

		return layoutSEOEntryPersistence.update(layoutSEOEntry);
	}

	@Reference
	private GroupLocalService _groupLocalService;

}