/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.exportimport.data.handler;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.segments.model.SegmentsEntry;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(service = StagedModelDataHandler.class)
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
		importedSegmentsEntry.setCompanyId(portletDataContext.getCompanyId());
		importedSegmentsEntry.setCriteria(
			_segmentsEntryExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, segmentsEntry,
					segmentsEntry.getCriteria()));

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

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsEntry)"
	)
	private ExportImportContentProcessor<String>
		_segmentsEntryExportImportContentProcessor;

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsEntry)",
		unbind = "-"
	)
	private StagedModelRepository<SegmentsEntry> _stagedModelRepository;

}