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
import com.liferay.layout.seo.model.LayoutCanonicalURL;
import com.liferay.layout.seo.service.LayoutCanonicalURLLocalService;
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
public class LayoutCanonicalURLStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutCanonicalURL> {

	public static final String[] CLASS_NAMES = {
		LayoutCanonicalURL.class.getName()
	};

	@Override
	public void deleteStagedModel(LayoutCanonicalURL layoutCanonicalURL)
		throws PortalException {

		_layoutCanonicalURLLocalService.deleteLayoutCanonicalURL(
			layoutCanonicalURL);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_layoutCanonicalURLLocalService.deleteLayoutCanonicalURL(uuid, groupId);
	}

	@Override
	public List<LayoutCanonicalURL> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return Collections.singletonList(
			_layoutCanonicalURLLocalService.
				fetchLayoutCanonicalURLByUuidAndGroupId(uuid, companyId));
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutCanonicalURL layoutCanonicalURL)
		throws Exception {

		portletDataContext.addClassedModel(
			portletDataContext.getExportDataElement(layoutCanonicalURL),
			ExportImportPathUtil.getModelPath(layoutCanonicalURL),
			layoutCanonicalURL);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutCanonicalURL layoutCanonicalURL)
		throws Exception {

		LayoutCanonicalURL existingLayoutCanonicalURL =
			fetchStagedModelByUuidAndGroupId(
				layoutCanonicalURL.getUuid(), layoutCanonicalURL.getGroupId());

		if (existingLayoutCanonicalURL == null) {
			Map<Long, Layout> newPrimaryKeysMap =
				(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
					Layout.class + ".layout");

			Layout layout = newPrimaryKeysMap.get(
				layoutCanonicalURL.getLayoutId());

			_layoutCanonicalURLLocalService.updateLayoutCanonicalURL(
				layoutCanonicalURL.getUserId(), layout.getGroupId(),
				layout.isPrivateLayout(), layout.getLayoutId(),
				layoutCanonicalURL.isEnabled(),
				layoutCanonicalURL.getCanonicalURLMap(),
				portletDataContext.createServiceContext(layoutCanonicalURL));
		}
		else {
			_layoutCanonicalURLLocalService.updateLayoutCanonicalURL(
				existingLayoutCanonicalURL.getUserId(),
				portletDataContext.getScopeGroupId(),
				layoutCanonicalURL.isPrivateLayout(),
				existingLayoutCanonicalURL.getLayoutId(),
				layoutCanonicalURL.isEnabled(),
				layoutCanonicalURL.getCanonicalURLMap(),
				portletDataContext.createServiceContext(layoutCanonicalURL));
		}
	}

	@Reference
	private LayoutCanonicalURLLocalService _layoutCanonicalURLLocalService;

}