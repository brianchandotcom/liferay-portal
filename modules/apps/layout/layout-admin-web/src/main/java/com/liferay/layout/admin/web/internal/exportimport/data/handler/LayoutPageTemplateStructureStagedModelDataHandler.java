/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = StagedModelDataHandler.class)
public class LayoutPageTemplateStructureStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutPageTemplateStructure> {

	public static final String[] CLASS_NAMES = {
		LayoutPageTemplateStructure.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws Exception {

		Element layoutPageTemplateStructureElement =
			portletDataContext.getExportDataElement(
				layoutPageTemplateStructure);

		portletDataContext.addClassedModel(
			layoutPageTemplateStructureElement,
			ExportImportPathUtil.getModelPath(layoutPageTemplateStructure),
			layoutPageTemplateStructure);

		_exportLayoutPageTemplateStructureRels(
			portletDataContext, layoutPageTemplateStructure);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws Exception {

		LayoutPageTemplateStructure importedLayoutPageTemplateStructure =
			(LayoutPageTemplateStructure)layoutPageTemplateStructure.clone();

		importedLayoutPageTemplateStructure.setGroupId(
			portletDataContext.getScopeGroupId());
		importedLayoutPageTemplateStructure.setCompanyId(
			portletDataContext.getCompanyId());

		Element element = portletDataContext.getImportDataElement(
			importedLayoutPageTemplateStructure);

		importedLayoutPageTemplateStructure.setPlid(
			GetterUtil.getLong(element.attributeValue("classPK")));

		LayoutPageTemplateStructure existingLayoutPageTemplateStructure =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				layoutPageTemplateStructure.getUuid(),
				portletDataContext.getScopeGroupId());

		if (existingLayoutPageTemplateStructure == null) {
			existingLayoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						portletDataContext.getScopeGroupId(),
						importedLayoutPageTemplateStructure.getPlid());
		}

		if ((existingLayoutPageTemplateStructure == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedLayoutPageTemplateStructure =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedLayoutPageTemplateStructure);
		}
		else {
			importedLayoutPageTemplateStructure.setMvccVersion(
				existingLayoutPageTemplateStructure.getMvccVersion());
			importedLayoutPageTemplateStructure.
				setLayoutPageTemplateStructureId(
					existingLayoutPageTemplateStructure.
						getLayoutPageTemplateStructureId());

			importedLayoutPageTemplateStructure =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedLayoutPageTemplateStructure);
		}

		portletDataContext.importClassedModel(
			layoutPageTemplateStructure, importedLayoutPageTemplateStructure);

		_importLayoutPageTemplateStructureRels(
			portletDataContext, layoutPageTemplateStructure);
	}

	@Override
	protected StagedModelRepository<LayoutPageTemplateStructure>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Override
	protected boolean isSkipImportReferenceStagedModels() {
		return true;
	}

	private void _exportLayoutPageTemplateStructureRels(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws Exception {

		List<LayoutPageTemplateStructureRel> layoutPageTemplateStructureRels =
			_layoutPageTemplateStructureRelLocalService.
				getLayoutPageTemplateStructureRels(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId());

		for (LayoutPageTemplateStructureRel layoutPageTemplateStructureRel :
				layoutPageTemplateStructureRels) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateStructure,
				layoutPageTemplateStructureRel,
				PortletDataContext.REFERENCE_TYPE_CHILD);
		}
	}

	private void _importLayoutPageTemplateStructureRels(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws Exception {

		Map<Long, Long> layoutPageTemplateStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutPageTemplateStructure.class);

		long layoutPageTemplateStructureId = MapUtil.getLong(
			layoutPageTemplateStructureIds,
			layoutPageTemplateStructure.getLayoutPageTemplateStructureId(),
			layoutPageTemplateStructure.getLayoutPageTemplateStructureId());

		_layoutPageTemplateStructureRelLocalService.
			deleteLayoutPageTemplateStructureRels(
				layoutPageTemplateStructureId);

		LayoutPageTemplateStructure referrerLayoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(layoutPageTemplateStructureId);

		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				portletDataContext.getScopeGroupId(),
				referrerLayoutPageTemplateStructure.getPlid());

		List<Element> layoutPageTemplateStructureRelElements =
			portletDataContext.getReferenceDataElements(
				layoutPageTemplateStructure,
				LayoutPageTemplateStructureRel.class,
				PortletDataContext.REFERENCE_TYPE_CHILD);

		for (Element layoutPageTemplateStructureRelElement :
				layoutPageTemplateStructureRelElements) {

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutPageTemplateStructureRelElement);
		}
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateStructure)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutPageTemplateStructure>
		_stagedModelRepository;

}