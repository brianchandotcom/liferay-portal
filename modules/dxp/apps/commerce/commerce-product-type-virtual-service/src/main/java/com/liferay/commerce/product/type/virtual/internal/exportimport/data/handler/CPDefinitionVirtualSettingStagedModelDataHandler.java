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

package com.liferay.commerce.product.type.virtual.internal.exportimport.data.handler;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPDefinitionVirtualSettingStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPDefinitionVirtualSetting> {

	public static final String[] CLASS_NAMES =
		{CPDefinitionVirtualSetting.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionVirtualSetting,
			cpDefinitionVirtualSetting.getCPDefinition(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		FileEntry fileEntry = cpDefinitionVirtualSetting.getFileEntry();

		if (fileEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, cpDefinitionVirtualSetting, fileEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		FileEntry sampleFileEntry =
			cpDefinitionVirtualSetting.getSampleFileEntry();

		if (sampleFileEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, cpDefinitionVirtualSetting, sampleFileEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		JournalArticle journalArticle =
			cpDefinitionVirtualSetting.getTermsOfUseJournalArticle();

		if (journalArticle != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, cpDefinitionVirtualSetting, journalArticle,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		Element cpDefinitionVirtualSettingElement =
			portletDataContext.getExportDataElement(cpDefinitionVirtualSetting);

		portletDataContext.addClassedModel(
			cpDefinitionVirtualSettingElement,
			ExportImportPathUtil.getModelPath(cpDefinitionVirtualSetting),
			cpDefinitionVirtualSetting);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpDefinitionVirtualSettingId)
		throws Exception {

		CPDefinitionVirtualSetting existingCPDefinitionVirtualSetting =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpDefinitionVirtualSettingIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionVirtualSetting.class);

		cpDefinitionVirtualSettingIds.put(
			cpDefinitionVirtualSettingId,
			existingCPDefinitionVirtualSetting.
				getCPDefinitionVirtualSettingId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting)
		throws Exception {

		Map<Long, Long> cpDefinitionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinition.class);

		long cpDefinitionId = MapUtil.getLong(
			cpDefinitionIds, cpDefinitionVirtualSetting.getCPDefinitionId(),
			cpDefinitionVirtualSetting.getCPDefinitionId());

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		long fileEntryId = MapUtil.getLong(
			fileEntryIds, cpDefinitionVirtualSetting.getFileEntryId(),
			cpDefinitionVirtualSetting.getFileEntryId());
		long sampleFileEntryId = MapUtil.getLong(
			fileEntryIds, cpDefinitionVirtualSetting.getSampleFileEntryId(),
			cpDefinitionVirtualSetting.getSampleFileEntryId());

		Map<Long, Long> journalArticleResourcePrimaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class);

		long termsOfUseJournalArticleResourcePrimKey = MapUtil.getLong(
			journalArticleResourcePrimaryKeys,
			cpDefinitionVirtualSetting.
				getTermsOfUseJournalArticleResourcePrimKey(),
			cpDefinitionVirtualSetting.
				getTermsOfUseJournalArticleResourcePrimKey());

		CPDefinitionVirtualSetting importedCPDefinitionVirtualSetting =
			(CPDefinitionVirtualSetting)cpDefinitionVirtualSetting.clone();

		importedCPDefinitionVirtualSetting.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCPDefinitionVirtualSetting.setCPDefinitionId(cpDefinitionId);
		importedCPDefinitionVirtualSetting.setFileEntryId(fileEntryId);
		importedCPDefinitionVirtualSetting.setSampleFileEntryId(
			sampleFileEntryId);
		importedCPDefinitionVirtualSetting.
			setTermsOfUseJournalArticleResourcePrimKey(
				termsOfUseJournalArticleResourcePrimKey);

		CPDefinitionVirtualSetting existingCPDefinitionVirtualSetting =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpDefinitionVirtualSetting.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPDefinitionVirtualSetting == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPDefinitionVirtualSetting =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCPDefinitionVirtualSetting);
		}
		else {
			importedCPDefinitionVirtualSetting.setCPDefinitionVirtualSettingId(
				existingCPDefinitionVirtualSetting.
					getCPDefinitionVirtualSettingId());

			importedCPDefinitionVirtualSetting =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCPDefinitionVirtualSetting);
		}

		portletDataContext.importClassedModel(
			cpDefinitionVirtualSetting, importedCPDefinitionVirtualSetting);
	}

	@Override
	protected StagedModelRepository<CPDefinitionVirtualSetting>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPDefinitionVirtualSetting>
			stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPDefinitionVirtualSetting>
		_stagedModelRepository;

}