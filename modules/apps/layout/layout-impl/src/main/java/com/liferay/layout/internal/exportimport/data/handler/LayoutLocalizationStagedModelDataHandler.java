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

package com.liferay.layout.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.layout.service.LayoutLocalizationLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	service = {
		LayoutLocalizationStagedModelDataHandler.class,
		StagedModelDataHandler.class
	}
)
public class LayoutLocalizationStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutLocalization> {

	public static final String[] CLASS_NAMES = {
		LayoutLocalization.class.getName()
	};

	@Override
	public void deleteStagedModel(LayoutLocalization layoutLocalization)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(layoutLocalization);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutLocalization layoutLocalization)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			layoutLocalization);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(layoutLocalization),
			layoutLocalization);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long layoutLocalizationId)
		throws Exception {

		LayoutLocalization existingLayoutLocalization = fetchMissingReference(
			uuid, groupId);

		if (existingLayoutLocalization == null) {
			return;
		}

		Map<Long, Long> layoutLocalizationIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutLocalization.class);

		layoutLocalizationIds.put(
			layoutLocalizationId,
			existingLayoutLocalization.getLayoutLocalizationId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutLocalization layoutLocalization)
		throws Exception {

		LayoutLocalization importedLayoutLocalization =
			(LayoutLocalization)layoutLocalization.clone();

		importedLayoutLocalization.setGroupId(
			portletDataContext.getScopeGroupId());
		importedLayoutLocalization.setCompanyId(
			portletDataContext.getCompanyId());

		Map<Long, Long> plids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		long plid = MapUtil.getLong(
			plids, layoutLocalization.getPlid(), layoutLocalization.getPlid());

		importedLayoutLocalization.setPlid(plid);

		LayoutLocalization existingLayoutLocalization =
			_layoutLocalizationLocalService.fetchLayoutLocalization(
				portletDataContext.getGroupId(),
				layoutLocalization.getLanguageId(), plid);

		if (existingLayoutLocalization == null) {
			existingLayoutLocalization =
				_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
					layoutLocalization.getUuid(),
					portletDataContext.getScopeGroupId());
		}

		if ((existingLayoutLocalization == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedLayoutLocalization = _stagedModelRepository.addStagedModel(
				portletDataContext, importedLayoutLocalization);
		}
		else {
			importedLayoutLocalization.setMvccVersion(
				existingLayoutLocalization.getMvccVersion());
			importedLayoutLocalization.setLayoutLocalizationId(
				existingLayoutLocalization.getLayoutLocalizationId());

			importedLayoutLocalization =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedLayoutLocalization);
		}

		portletDataContext.importClassedModel(
			layoutLocalization, importedLayoutLocalization);
	}

	@Override
	protected StagedModelRepository<LayoutLocalization>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference
	private LayoutLocalizationLocalService _layoutLocalizationLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.layout.model.LayoutLocalization)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutLocalization> _stagedModelRepository;

}