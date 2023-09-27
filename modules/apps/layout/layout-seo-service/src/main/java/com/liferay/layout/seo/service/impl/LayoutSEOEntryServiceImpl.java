/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.service.impl;

import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.base.LayoutSEOEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"json.web.service.context.name=layout",
		"json.web.service.context.path=LayoutSEOEntry"
	},
	service = AopService.class
)
public class LayoutSEOEntryServiceImpl extends LayoutSEOEntryServiceBaseImpl {

	@Override
	public LayoutSEOEntry copyLayoutSEOEntry(
			long userId, long groupId, boolean privateLayout,
			long sourceLayoutId, boolean canonicalURLEnabled,
			Map<Locale, String> canonicalURLMap, long copyDDMStorageId,
			boolean openGraphDescriptionEnabled,
			Map<Locale, String> openGraphDescriptionMap,
			Map<Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			Map<Locale, String> openGraphTitleMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			getPermissionChecker(),
			_layoutLocalService.getLayout(
				groupId, privateLayout, sourceLayoutId));

		return layoutSEOEntryLocalService.copyLayoutSEOEntry(
			userId, groupId, privateLayout, sourceLayoutId, canonicalURLEnabled,
			canonicalURLMap, copyDDMStorageId, openGraphDescriptionEnabled,
			openGraphDescriptionMap, openGraphImageAltMap,
			openGraphImageFileEntryId, openGraphTitleEnabled, openGraphTitleMap,
			serviceContext);
	}

	@Override
	public LayoutSEOEntry updateCustomMetaTags(
			long groupId, boolean privateLayout, long layoutId,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			getPermissionChecker(),
			_layoutLocalService.getLayout(groupId, privateLayout, layoutId));

		return layoutSEOEntryLocalService.updateCustomMetaTags(
			getUserId(), groupId, privateLayout, layoutId, serviceContext);
	}

	@Override
	public LayoutSEOEntry updateLayoutSEOEntry(
			long groupId, boolean privateLayout, long layoutId,
			boolean canonicalURLEnabled, Map<Locale, String> canonicalURLMap,
			boolean openGraphDescriptionEnabled,
			Map<Locale, String> openGraphDescriptionMap,
			Map<Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
			Map<Locale, String> openGraphTitleMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			getPermissionChecker(),
			_layoutLocalService.getLayout(groupId, privateLayout, layoutId));

		return layoutSEOEntryLocalService.updateLayoutSEOEntry(
			getUserId(), groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, openGraphDescriptionEnabled,
			openGraphDescriptionMap, openGraphImageAltMap,
			openGraphImageFileEntryId, openGraphTitleEnabled, openGraphTitleMap,
			serviceContext);
	}

	@Override
	public LayoutSEOEntry updateLayoutSEOEntry(
			long groupId, boolean privateLayout, long layoutId,
			boolean enabledCanonicalURLMap, Map<Locale, String> canonicalURLMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			getPermissionChecker(),
			_layoutLocalService.getLayout(groupId, privateLayout, layoutId));

		return layoutSEOEntryLocalService.updateLayoutSEOEntry(
			getUserId(), groupId, privateLayout, layoutId,
			enabledCanonicalURLMap, canonicalURLMap, serviceContext);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}