/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = StagedModelDataHandler.class)
public class StylebookEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<StyleBookEntry> {

	public static final String[] CLASS_NAMES = {StyleBookEntry.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public void deleteStagedModel(StyleBookEntry styleBookEntry)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(styleBookEntry);
	}

	@Override
	public List<StyleBookEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StyleBookEntry styleBookEntry)
		throws Exception {

		if (styleBookEntry.getPreviewFileEntryId() > 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				styleBookEntry.getPreviewFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, styleBookEntry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		Element entryElement = portletDataContext.getExportDataElement(
			styleBookEntry);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(styleBookEntry),
			styleBookEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long styleBookEntryId)
		throws Exception {

		StyleBookEntry existingStyleBookEntry = fetchMissingReference(
			uuid, groupId);

		if (existingStyleBookEntry == null) {
			return;
		}

		Map<Long, Long> styleBookEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				StyleBookEntry.class);

		styleBookEntryIds.put(
			styleBookEntryId, existingStyleBookEntry.getStyleBookEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StyleBookEntry styleBookEntry)
		throws Exception {

		StyleBookEntry importedStyleBookEntry =
			(StyleBookEntry)styleBookEntry.clone();

		importedStyleBookEntry.setGroupId(portletDataContext.getScopeGroupId());

		StyleBookEntry existingStyleBookEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				styleBookEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingStyleBookEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedStyleBookEntry = _stagedModelRepository.addStagedModel(
				portletDataContext, importedStyleBookEntry);
		}
		else {
			importedStyleBookEntry.setMvccVersion(
				existingStyleBookEntry.getMvccVersion());
			importedStyleBookEntry.setStyleBookEntryId(
				existingStyleBookEntry.getStyleBookEntryId());

			importedStyleBookEntry = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedStyleBookEntry);
		}

		if (styleBookEntry.getPreviewFileEntryId() > 0) {
			Map<Long, Long> fileEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					FileEntry.class);

			long previewFileEntryId = MapUtil.getLong(
				fileEntryIds, styleBookEntry.getPreviewFileEntryId(), 0);

			importedStyleBookEntry.setPreviewFileEntryId(previewFileEntryId);

			importedStyleBookEntry =
				_styleBookEntryLocalService.updatePreviewFileEntryId(
					importedStyleBookEntry.getStyleBookEntryId(),
					previewFileEntryId);
		}

		portletDataContext.importClassedModel(
			styleBookEntry, importedStyleBookEntry);
	}

	@Override
	protected StagedModelRepository<StyleBookEntry> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.style.book.model.StyleBookEntry)",
		unbind = "-"
	)
	private StagedModelRepository<StyleBookEntry> _stagedModelRepository;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}