/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = StagedModelDataHandler.class)
public class LayoutUtilityPageEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutUtilityPageEntry> {

	public static final String[] CLASS_NAMES = {
		LayoutUtilityPageEntry.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		return layoutUtilityPageEntry.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws Exception {

		if (layoutUtilityPageEntry.getPreviewFileEntryId() > 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				layoutUtilityPageEntry.getPreviewFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutUtilityPageEntry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		_exportReferenceLayout(layoutUtilityPageEntry, portletDataContext);

		Element entryElement = portletDataContext.getExportDataElement(
			layoutUtilityPageEntry);

		portletDataContext.addClassedModel(
			entryElement,
			ExportImportPathUtil.getModelPath(layoutUtilityPageEntry),
			layoutUtilityPageEntry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws Exception {

		Map<Long, Long> plids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		long plid = MapUtil.getLong(
			plids, layoutUtilityPageEntry.getPlid(),
			layoutUtilityPageEntry.getPlid());

		LayoutUtilityPageEntry importedLayoutUtilityPageEntry =
			(LayoutUtilityPageEntry)layoutUtilityPageEntry.clone();

		importedLayoutUtilityPageEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedLayoutUtilityPageEntry.setPlid(plid);

		if (portletDataContext.isDataStrategyMirror()) {
			LayoutUtilityPageEntry existingLayoutUtilityPageEntry =
				_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
					layoutUtilityPageEntry.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingLayoutUtilityPageEntry == null) {
				importedLayoutUtilityPageEntry = _addStagedModel(
					portletDataContext, importedLayoutUtilityPageEntry);
			}
			else {
				importedLayoutUtilityPageEntry.setMvccVersion(
					existingLayoutUtilityPageEntry.getMvccVersion());
				importedLayoutUtilityPageEntry.setLayoutUtilityPageEntryId(
					existingLayoutUtilityPageEntry.
						getLayoutUtilityPageEntryId());

				importedLayoutUtilityPageEntry =
					_stagedModelRepository.updateStagedModel(
						portletDataContext, importedLayoutUtilityPageEntry);
			}
		}
		else {
			importedLayoutUtilityPageEntry = _addStagedModel(
				portletDataContext, importedLayoutUtilityPageEntry);
		}

		if (layoutUtilityPageEntry.getPreviewFileEntryId() > 0) {
			Map<Long, Long> fileEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					FileEntry.class);

			long previewFileEntryId = MapUtil.getLong(
				fileEntryIds, layoutUtilityPageEntry.getPreviewFileEntryId(),
				0);

			importedLayoutUtilityPageEntry =
				_layoutUtilityPageEntryLocalService.
					updateLayoutUtilityPageEntry(
						importedLayoutUtilityPageEntry.
							getLayoutUtilityPageEntryId(),
						previewFileEntryId);
		}

		portletDataContext.importClassedModel(
			layoutUtilityPageEntry, importedLayoutUtilityPageEntry);
	}

	@Override
	protected StagedModelRepository<LayoutUtilityPageEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	private LayoutUtilityPageEntry _addStagedModel(
			PortletDataContext portletDataContext,
			LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws Exception {

		if (!ExportImportThreadLocal.isStagingInProcess() &&
			layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {

			LayoutUtilityPageEntry defaultLayoutUtilityPageEntry =
				_layoutUtilityPageEntryLocalService.
					fetchDefaultLayoutUtilityPageEntry(
						layoutUtilityPageEntry.getGroupId(),
						layoutUtilityPageEntry.getType());

			if (defaultLayoutUtilityPageEntry != null) {
				layoutUtilityPageEntry.setDefaultLayoutUtilityPageEntry(false);
			}
		}

		return _stagedModelRepository.addStagedModel(
			portletDataContext, layoutUtilityPageEntry);
	}

	private void _exportReferenceLayout(
			LayoutUtilityPageEntry layoutUtilityPageEntry,
			PortletDataContext portletDataContext)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayout(
			layoutUtilityPageEntry.getPlid());

		if (layout == null) {
			return;
		}

		Element layoutElement = portletDataContext.getReferenceElement(
			Layout.class.getName(), layout.getPlid());

		if ((layoutElement != null) &&
			Validator.isNotNull(
				layoutElement.attributeValue("master-layout-uuid"))) {

			return;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutUtilityPageEntry, draftLayout,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, layoutUtilityPageEntry, layout,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.layout.utility.page.model.LayoutUtilityPageEntry)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutUtilityPageEntry>
		_stagedModelRepository;

}