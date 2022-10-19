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

package com.liferay.segments.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(service = StagedModelDataHandler.class)
public class SegmentsExperienceStagedModelDataHandler
	extends BaseStagedModelDataHandler<SegmentsExperience> {

	public static final String[] CLASS_NAMES = {
		SegmentsExperience.class.getName()
	};

	@Override
	public void deleteStagedModel(SegmentsExperience segmentsExperience)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(segmentsExperience);
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
	public String getDisplayName(SegmentsExperience segmentsExperience) {
		return segmentsExperience.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			SegmentsExperience segmentsExperience)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			segmentsExperience.getGroupId());

		if (ExportImportThreadLocal.isStagingInProcess() &&
			group.isStagedPortlet(SegmentsPortletKeys.SEGMENTS)) {

			SegmentsEntry segmentsEntry =
				_segmentsEntryLocalService.fetchSegmentsEntry(
					segmentsExperience.getSegmentsEntryId());

			if (segmentsEntry != null) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, segmentsExperience, segmentsEntry,
					PortletDataContext.REFERENCE_TYPE_PARENT);
			}
		}

		Element segmentsExperienceElement =
			portletDataContext.getExportDataElement(segmentsExperience);

		portletDataContext.addClassedModel(
			segmentsExperienceElement,
			ExportImportPathUtil.getModelPath(segmentsExperience),
			segmentsExperience);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long segmentsExperienceId)
		throws Exception {

		SegmentsExperience existingSegmentsExperience = fetchMissingReference(
			uuid, groupId);

		if (existingSegmentsExperience == null) {
			return;
		}

		Map<Long, Long> segmentsExperienceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SegmentsExperience.class);

		segmentsExperienceIds.put(
			segmentsExperienceId,
			existingSegmentsExperience.getSegmentsExperienceId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			SegmentsExperience segmentsExperience)
		throws Exception {

		Map<Long, Long> segmentsEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SegmentsEntry.class);

		long segmentsEntryId = MapUtil.getLong(
			segmentsEntryIds, segmentsExperience.getSegmentsEntryId(),
			SegmentsEntryConstants.ID_DEFAULT);

		Map<Long, Long> referenceClassPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				segmentsExperience.getClassName());

		long referenceClassPK = MapUtil.getLong(
			referenceClassPKs, segmentsExperience.getClassPK(),
			segmentsExperience.getClassPK());

		SegmentsExperience importedSegmentsExperience =
			(SegmentsExperience)segmentsExperience.clone();

		importedSegmentsExperience.setGroupId(
			portletDataContext.getScopeGroupId());
		importedSegmentsExperience.setCompanyId(
			portletDataContext.getCompanyId());
		importedSegmentsExperience.setSegmentsEntryId(segmentsEntryId);
		importedSegmentsExperience.setClassPK(referenceClassPK);

		SegmentsExperience existingSegmentsExperience =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				segmentsExperience.getUuid(),
				portletDataContext.getScopeGroupId());

		if (existingSegmentsExperience == null) {
			existingSegmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					portletDataContext.getScopeGroupId(),
					importedSegmentsExperience.getSegmentsExperienceKey(),
					importedSegmentsExperience.getClassNameId(),
					importedSegmentsExperience.getClassPK());
		}

		if ((existingSegmentsExperience == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedSegmentsExperience = _stagedModelRepository.addStagedModel(
				portletDataContext, importedSegmentsExperience);
		}
		else {
			importedSegmentsExperience.setMvccVersion(
				existingSegmentsExperience.getMvccVersion());
			importedSegmentsExperience.setSegmentsExperienceId(
				existingSegmentsExperience.getSegmentsExperienceId());
			importedSegmentsExperience.setSegmentsExperienceKey(
				existingSegmentsExperience.getSegmentsExperienceKey());

			importedSegmentsExperience =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedSegmentsExperience);
		}

		portletDataContext.importClassedModel(
			segmentsExperience, importedSegmentsExperience);
	}

	@Override
	protected StagedModelRepository<SegmentsExperience>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsExperience)",
		unbind = "-"
	)
	private StagedModelRepository<SegmentsExperience> _stagedModelRepository;

}