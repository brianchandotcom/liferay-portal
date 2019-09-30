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
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.segments.internal.exportimport.content.processor.SegmentsEntryExportImportContentProcessor;
import com.liferay.segments.model.SegmentsEntry;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class SegmentsEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<SegmentsEntry> {

	public static final String[] CLASS_NAMES = {SegmentsEntry.class.getName()};

	@Override
	public void deleteStagedModel(SegmentsEntry segmentsEntry)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(segmentsEntry);
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
	public String getDisplayName(SegmentsEntry segmentsEntry) {
		return segmentsEntry.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, SegmentsEntry segmentsEntry)
		throws Exception {

		String criteria =
			_segmentsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, segmentsEntry,
					segmentsEntry.getCriteria(), false, false);

		segmentsEntry.setCriteria(criteria);

		Element segmentsEntryElement = portletDataContext.getExportDataElement(
			segmentsEntry);

		portletDataContext.addClassedModel(
			segmentsEntryElement,
			ExportImportPathUtil.getModelPath(segmentsEntry), segmentsEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long segmentsEntryId)
		throws Exception {

		SegmentsEntry existingSegmentsEntry = fetchMissingReference(
			uuid, groupId);

		if (existingSegmentsEntry == null) {
			return;
		}

		Map<Long, Long> segmentsEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SegmentsEntry.class);

		segmentsEntryIds.put(
			segmentsEntryId, existingSegmentsEntry.getSegmentsEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, SegmentsEntry segmentsEntry)
		throws Exception {

		SegmentsEntry importedSegmentsEntry =
			(SegmentsEntry)segmentsEntry.clone();

		importedSegmentsEntry.setGroupId(portletDataContext.getScopeGroupId());

		String criteria =
			_segmentsEntryExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, segmentsEntry,
					segmentsEntry.getCriteria());

		importedSegmentsEntry.setCriteria(criteria);

		SegmentsEntry existingSegmentsEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				segmentsEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingSegmentsEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedSegmentsEntry = _stagedModelRepository.addStagedModel(
				portletDataContext, importedSegmentsEntry);
		}
		else {
			importedSegmentsEntry.setMvccVersion(
				existingSegmentsEntry.getMvccVersion());
			importedSegmentsEntry.setSegmentsEntryId(
				existingSegmentsEntry.getSegmentsEntryId());

			importedSegmentsEntry = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedSegmentsEntry);
		}

		portletDataContext.importClassedModel(
			segmentsEntry, importedSegmentsEntry);
	}

	@Override
	protected StagedModelRepository<SegmentsEntry> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference
	private SegmentsEntryExportImportContentProcessor
		_segmentsEntryExportImportContentProcessor;

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsEntry)",
		unbind = "-"
	)
	private StagedModelRepository<SegmentsEntry> _stagedModelRepository;

}