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
import com.liferay.layout.seo.model.LayoutSEOCanonicalURL;
import com.liferay.layout.seo.service.LayoutSEOCanonicalURLLocalService;
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
public class LayoutSEOCanonicalURLStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutSEOCanonicalURL> {

	public static final String[] CLASS_NAMES = {
		LayoutSEOCanonicalURL.class.getName()
	};

	@Override
	public void deleteStagedModel(LayoutSEOCanonicalURL layoutSEOCanonicalURL)
		throws PortalException {

		_layoutSEOCanonicalURLLocalService.deleteLayoutSEOCanonicalURL(
			layoutSEOCanonicalURL);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_layoutSEOCanonicalURLLocalService.deleteLayoutSEOCanonicalURL(
			uuid, groupId);
	}

	@Override
	public List<LayoutSEOCanonicalURL> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return Collections.singletonList(
			_layoutSEOCanonicalURLLocalService.
				fetchLayoutSEOCanonicalURLByUuidAndGroupId(uuid, companyId));
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSEOCanonicalURL layoutSEOCanonicalURL)
		throws Exception {

		portletDataContext.addClassedModel(
			portletDataContext.getExportDataElement(layoutSEOCanonicalURL),
			ExportImportPathUtil.getModelPath(layoutSEOCanonicalURL),
			layoutSEOCanonicalURL);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSEOCanonicalURL layoutSEOCanonicalURL)
		throws Exception {

		LayoutSEOCanonicalURL existingLayoutSEOCanonicalURL =
			fetchStagedModelByUuidAndGroupId(
				layoutSEOCanonicalURL.getUuid(),
				layoutSEOCanonicalURL.getGroupId());

		if (existingLayoutSEOCanonicalURL == null) {
			Map<Long, Layout> newPrimaryKeysMap =
				(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
					Layout.class + ".layout");

			Layout layout = newPrimaryKeysMap.get(
				layoutSEOCanonicalURL.getLayoutId());

			_layoutSEOCanonicalURLLocalService.updateLayoutSEOCanonicalURL(
				layoutSEOCanonicalURL.getUserId(), layout.getGroupId(),
				layout.isPrivateLayout(), layout.getLayoutId(),
				layoutSEOCanonicalURL.isEnabled(),
				layoutSEOCanonicalURL.getCanonicalURLMap(),
				portletDataContext.createServiceContext(layoutSEOCanonicalURL));
		}
		else {
			_layoutSEOCanonicalURLLocalService.updateLayoutSEOCanonicalURL(
				existingLayoutSEOCanonicalURL.getUserId(),
				portletDataContext.getScopeGroupId(),
				layoutSEOCanonicalURL.isPrivateLayout(),
				existingLayoutSEOCanonicalURL.getLayoutId(),
				layoutSEOCanonicalURL.isEnabled(),
				layoutSEOCanonicalURL.getCanonicalURLMap(),
				portletDataContext.createServiceContext(layoutSEOCanonicalURL));
		}
	}

	@Reference
	private LayoutSEOCanonicalURLLocalService
		_layoutSEOCanonicalURLLocalService;

}