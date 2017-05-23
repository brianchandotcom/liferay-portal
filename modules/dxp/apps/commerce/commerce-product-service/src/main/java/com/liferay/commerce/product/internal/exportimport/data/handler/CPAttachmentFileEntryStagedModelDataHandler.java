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

package com.liferay.commerce.product.internal.exportimport.data.handler;

import com.liferay.commerce.product.internal.exportimport.content.processor.CPInstanceExportImportContentProcessor;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPAttachmentFileEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPAttachmentFileEntry> {

	public static final String[] CLASS_NAMES =
		{CPAttachmentFileEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		if (cpAttachmentFileEntry.getClassNameId() ==
				_portal.getClassNameId(CPDefinition.class)) {

			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(
					cpAttachmentFileEntry.getClassPK());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, cpAttachmentFileEntry, cpDefinition,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpAttachmentFileEntry,
			cpAttachmentFileEntry.getFileEntry(),
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY);

		Element cpAttachmentFileEntryElement =
			portletDataContext.getExportDataElement(cpAttachmentFileEntry);

		String json =
			_cpInstanceExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, cpAttachmentFileEntry,
					cpAttachmentFileEntry.getJson(), true, false);

		cpAttachmentFileEntry.setJson(json);

		portletDataContext.addClassedModel(
			cpAttachmentFileEntryElement,
			ExportImportPathUtil.getModelPath(cpAttachmentFileEntry),
			cpAttachmentFileEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpAttachmentFileEntryId)
		throws Exception {

		CPAttachmentFileEntry existingCPAttachmentFileEntry =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpAttachmentFileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPAttachmentFileEntry.class);

		cpAttachmentFileEntryIds.put(
			cpAttachmentFileEntryId,
			existingCPAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		Map<Long, Long> newClassPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				cpAttachmentFileEntry.getClassName());

		long classPK = MapUtil.getLong(
			newClassPKs, cpAttachmentFileEntry.getClassPK(),
			cpAttachmentFileEntry.getClassPK());

		Map<Long, Long> newFileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class);

		long fileEntryId = MapUtil.getLong(
			newFileEntryIds, cpAttachmentFileEntry.getFileEntryId(),
			cpAttachmentFileEntry.getFileEntryId());

		CPAttachmentFileEntry importedCPAttachmentFileEntry =
			(CPAttachmentFileEntry)cpAttachmentFileEntry.clone();

		importedCPAttachmentFileEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCPAttachmentFileEntry.setClassPK(classPK);
		importedCPAttachmentFileEntry.setFileEntryId(fileEntryId);

		String json =
			_cpInstanceExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, cpAttachmentFileEntry,
					cpAttachmentFileEntry.getJson());

		importedCPAttachmentFileEntry.setJson(json);

		CPAttachmentFileEntry existingCPAttachmentFileEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpAttachmentFileEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPAttachmentFileEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPAttachmentFileEntry =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCPAttachmentFileEntry);
		}
		else {
			importedCPAttachmentFileEntry.setCPAttachmentFileEntryId(
				existingCPAttachmentFileEntry.getCPAttachmentFileEntryId());

			importedCPAttachmentFileEntry =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCPAttachmentFileEntry);
		}

		portletDataContext.importClassedModel(
			cpAttachmentFileEntry, importedCPAttachmentFileEntry);
	}

	@Override
	protected StagedModelRepository<CPAttachmentFileEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPAttachmentFileEntry)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPAttachmentFileEntry> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceExportImportContentProcessor
		_cpInstanceExportImportContentProcessor;

	@Reference
	private Portal _portal;

	private StagedModelRepository<CPAttachmentFileEntry> _stagedModelRepository;

}