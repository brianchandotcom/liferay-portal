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

package com.liferay.fragment.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class FragmentEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<FragmentEntry> {

	public static final String[] CLASS_NAMES = {FragmentEntry.class.getName()};

	@Override
	public void deleteStagedModel(FragmentEntry fragmentEntry)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(fragmentEntry);
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
	public String getDisplayName(FragmentEntry fragmentEntry) {
		return fragmentEntry.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, FragmentEntry fragmentEntry)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				fragmentEntry.getFragmentCollectionId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, fragmentEntry, fragmentCollection,
			PortletDataContext.REFERENCE_TYPE_PARENT);

		if (fragmentEntry.getPreviewFileEntryId() > 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				fragmentEntry.getPreviewFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fragmentEntry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		Element entryElement = portletDataContext.getExportDataElement(
			fragmentEntry);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(fragmentEntry),
			fragmentEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long fragmentEntryId)
		throws Exception {

		FragmentEntry existingFragmentEntry = fetchMissingReference(
			uuid, groupId);

		if (existingFragmentEntry == null) {
			return;
		}

		Map<Long, Long> fragmentEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentEntry.class);

		fragmentEntryIds.put(
			fragmentEntryId, existingFragmentEntry.getFragmentEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, FragmentEntry fragmentEntry)
		throws Exception {

		Map<Long, Long> fragmentCollectionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentCollection.class);

		long fragmentCollectionId = MapUtil.getLong(
			fragmentCollectionIds, fragmentEntry.getFragmentCollectionId(),
			fragmentEntry.getFragmentCollectionId());

		FragmentEntry importedFragmentEntry =
			(FragmentEntry)fragmentEntry.clone();

		importedFragmentEntry.setGroupId(portletDataContext.getScopeGroupId());
		importedFragmentEntry.setFragmentCollectionId(fragmentCollectionId);

		FragmentEntry existingFragmentEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				fragmentEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFragmentEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedFragmentEntry = _stagedModelRepository.addStagedModel(
				portletDataContext, importedFragmentEntry);
		}
		else {
			importedFragmentEntry.setMvccVersion(
				existingFragmentEntry.getMvccVersion());
			importedFragmentEntry.setFragmentEntryId(
				existingFragmentEntry.getFragmentEntryId());

			importedFragmentEntry = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedFragmentEntry);
		}

		if (fragmentEntry.getPreviewFileEntryId() > 0) {
			Map<Long, Long> fileEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					FileEntry.class);

			long previewFileEntryId = MapUtil.getLong(
				fileEntryIds, fragmentEntry.getPreviewFileEntryId(), 0);

			importedFragmentEntry.setPreviewFileEntryId(previewFileEntryId);

			importedFragmentEntry =
				_fragmentEntryLocalService.updateFragmentEntry(
					importedFragmentEntry);
		}

		portletDataContext.importClassedModel(
			fragmentEntry, importedFragmentEntry);
	}

	@Override
	protected StagedModelRepository<FragmentEntry> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.fragment.model.FragmentEntry)",
		unbind = "-"
	)
	private StagedModelRepository<FragmentEntry> _stagedModelRepository;

}