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

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class LayoutPageTemplateStructureRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutPageTemplateStructureRel> {

	public static final String[] CLASS_NAMES = {
		LayoutPageTemplateStructureRel.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws Exception {

		Element layoutPageTemplateStructureRelElement =
			portletDataContext.getExportDataElement(
				layoutPageTemplateStructureRel);

		if (layoutPageTemplateStructureRel.getSegmentsExperienceId() !=
				SegmentsExperienceConstants.ID_DEFAULT) {

			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					layoutPageTemplateStructureRel.getSegmentsExperienceId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateStructureRel,
				segmentsExperience, PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		portletDataContext.addClassedModel(
			layoutPageTemplateStructureRelElement,
			ExportImportPathUtil.getModelPath(layoutPageTemplateStructureRel),
			layoutPageTemplateStructureRel);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws Exception {

		Map<Long, Long> layoutPageTemplateStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutPageTemplateStructure.class);

		long layoutPageTemplateStructureId = MapUtil.getLong(
			layoutPageTemplateStructureIds,
			layoutPageTemplateStructureRel.getLayoutPageTemplateStructureId(),
			layoutPageTemplateStructureRel.getLayoutPageTemplateStructureId());

		Map<Long, Long> segmentsExperienceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SegmentsExperience.class);

		long segmentsExperienceId = MapUtil.getLong(
			segmentsExperienceIds,
			layoutPageTemplateStructureRel.getSegmentsExperienceId(),
			layoutPageTemplateStructureRel.getSegmentsExperienceId());

		LayoutPageTemplateStructureRel importedLayoutPageTemplateStructureRel =
			(LayoutPageTemplateStructureRel)
				layoutPageTemplateStructureRel.clone();

		importedLayoutPageTemplateStructureRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedLayoutPageTemplateStructureRel.setLayoutPageTemplateStructureId(
			layoutPageTemplateStructureId);
		importedLayoutPageTemplateStructureRel.setSegmentsExperienceId(
			segmentsExperienceId);

		LayoutPageTemplateStructureRel existingLayoutPageTemplateStructureRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				layoutPageTemplateStructureRel.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingLayoutPageTemplateStructureRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedLayoutPageTemplateStructureRel =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedLayoutPageTemplateStructureRel);
		}
		else {
			importedLayoutPageTemplateStructureRel.setMvccVersion(
				existingLayoutPageTemplateStructureRel.getMvccVersion());
			importedLayoutPageTemplateStructureRel.
				setLayoutPageTemplateStructureRelId(
					existingLayoutPageTemplateStructureRel.
						getLayoutPageTemplateStructureRelId());

			importedLayoutPageTemplateStructureRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedLayoutPageTemplateStructureRel);
		}

		portletDataContext.importClassedModel(
			layoutPageTemplateStructureRel,
			importedLayoutPageTemplateStructureRel);
	}

	@Override
	protected StagedModelRepository<LayoutPageTemplateStructureRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutPageTemplateStructureRel>
		_stagedModelRepository;

}