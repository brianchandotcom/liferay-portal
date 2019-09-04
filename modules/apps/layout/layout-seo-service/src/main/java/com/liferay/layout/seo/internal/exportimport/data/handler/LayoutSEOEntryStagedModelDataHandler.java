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

package com.liferay.layout.seo.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = StagedModelDataHandler.class)
public class LayoutSEOEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutSEOEntry> {

	public static final String[] CLASS_NAMES = {LayoutSEOEntry.class.getName()};

	@Override
	public void deleteStagedModel(LayoutSEOEntry layoutSEOEntry)
		throws PortalException {

		_layoutSEOEntryLocalService.deleteLayoutSEOEntry(layoutSEOEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_layoutSEOEntryLocalService.deleteLayoutSEOEntry(uuid, groupId);
	}

	@Override
	public List<LayoutSEOEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return Collections.singletonList(
			_layoutSEOEntryLocalService.fetchLayoutSEOEntryByUuidAndGroupId(
				uuid, companyId));
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSEOEntry layoutSEOEntry)
		throws Exception {

		portletDataContext.addClassedModel(
			portletDataContext.getExportDataElement(layoutSEOEntry),
			ExportImportPathUtil.getModelPath(layoutSEOEntry), layoutSEOEntry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSEOEntry layoutSEOEntry)
		throws Exception {

		LayoutSEOEntry existingLayoutSEOEntry =
			fetchStagedModelByUuidAndGroupId(
				layoutSEOEntry.getUuid(), layoutSEOEntry.getGroupId());

		if (existingLayoutSEOEntry == null) {
			Map<Long, Layout> newPrimaryKeysMap =
				(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
					Layout.class + ".layout");

			Layout layout = newPrimaryKeysMap.get(layoutSEOEntry.getLayoutId());

			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				layoutSEOEntry.getUserId(), layout.getGroupId(),
				layout.isPrivateLayout(), layout.getLayoutId(),
				layoutSEOEntry.isEnabled(), layoutSEOEntry.getCanonicalURLMap(),
				portletDataContext.createServiceContext(layoutSEOEntry));
		}
		else {
			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				existingLayoutSEOEntry.getUserId(),
				portletDataContext.getScopeGroupId(),
				layoutSEOEntry.isPrivateLayout(),
				existingLayoutSEOEntry.getLayoutId(),
				layoutSEOEntry.isEnabled(), layoutSEOEntry.getCanonicalURLMap(),
				portletDataContext.createServiceContext(layoutSEOEntry));
		}
	}

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

}