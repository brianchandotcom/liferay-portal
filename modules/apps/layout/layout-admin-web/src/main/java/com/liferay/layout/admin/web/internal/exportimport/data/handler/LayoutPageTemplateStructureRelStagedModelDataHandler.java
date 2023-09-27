/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(service = StagedModelDataHandler.class)
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

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				layoutPageTemplateStructureRel.getSegmentsExperienceId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, layoutPageTemplateStructureRel,
			segmentsExperience, PortletDataContext.REFERENCE_TYPE_STRONG);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateStructureRel.
						getLayoutPageTemplateStructureId());

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					segmentsExperience.getGroupId(),
					segmentsExperience.getSegmentsExperienceId(),
					layoutPageTemplateStructure.getPlid());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateStructureRel,
				fragmentEntryLink,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		String data =
			_dlReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, layoutPageTemplateStructureRel,
					layoutPageTemplateStructureRel.getData(), true, false);

		data =
			_layoutPageTemplateStructureRelReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, layoutPageTemplateStructureRel, data,
					true, false);

		layoutPageTemplateStructureRel.setData(data);

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
		importedLayoutPageTemplateStructureRel.setCompanyId(
			portletDataContext.getCompanyId());
		importedLayoutPageTemplateStructureRel.setLayoutPageTemplateStructureId(
			layoutPageTemplateStructureId);
		importedLayoutPageTemplateStructureRel.setSegmentsExperienceId(
			segmentsExperienceId);

		String data = layoutPageTemplateStructureRel.getData();

		if (Validator.isNotNull(data)) {
			data =
				_dlReferencesExportImportContentProcessor.
					replaceImportContentReferences(
						portletDataContext, layoutPageTemplateStructureRel,
						data);

			data =
				_layoutPageTemplateStructureRelReferencesExportImportContentProcessor.
					replaceImportContentReferences(
						portletDataContext, layoutPageTemplateStructureRel,
						data);

			LayoutStructure layoutStructure = LayoutStructure.of(data);

			_processImportFormLayoutStructureItemsData(
				layoutStructure, portletDataContext);

			_processImportFragmentLayoutStructureItemsData(
				layoutStructure, portletDataContext);

			data = layoutStructure.toString();
		}

		importedLayoutPageTemplateStructureRel.setData(data);

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

	private void _processImportFormLayoutStructureItemsData(
		LayoutStructure layoutStructure,
		PortletDataContext portletDataContext) {

		List<FormStyledLayoutStructureItem> formStyledLayoutStructureItems =
			layoutStructure.getFormStyledLayoutStructureItems();

		for (FormStyledLayoutStructureItem formStyledLayoutStructureItem :
				formStyledLayoutStructureItems) {

			JSONObject successMessageJSONObject =
				formStyledLayoutStructureItem.getSuccessMessageJSONObject();

			if (successMessageJSONObject == null) {
				continue;
			}

			JSONObject layoutJSONObject =
				successMessageJSONObject.getJSONObject("layout");

			if ((layoutJSONObject == null) ||
				(layoutJSONObject.length() == 0)) {

				continue;
			}

			layoutJSONObject.put(
				"groupId", portletDataContext.getScopeGroupId());
		}
	}

	private void _processImportFragmentLayoutStructureItemsData(
		LayoutStructure layoutStructure,
		PortletDataContext portletDataContext) {

		Map<Long, Long> fragmentEntryLinkIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentEntryLink.class);

		Map<Long, LayoutStructureItem> fragmentLayoutStructureItems =
			layoutStructure.getFragmentLayoutStructureItems();

		for (Map.Entry<Long, LayoutStructureItem> fragmentLayoutStructureItem :
				fragmentLayoutStructureItems.entrySet()) {

			long fragmentEntryLinkId = MapUtil.getLong(
				fragmentEntryLinkIds, fragmentLayoutStructureItem.getKey(),
				fragmentLayoutStructureItem.getKey());

			if (fragmentEntryLinkId <= 0) {
				continue;
			}

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)
						fragmentLayoutStructureItem.getValue();

			fragmentStyledLayoutStructureItem.setFragmentEntryLinkId(
				fragmentEntryLinkId);
		}
	}

	@Reference(target = "(content.processor.type=DLReferences)")
	private ExportImportContentProcessor<String>
		_dlReferencesExportImportContentProcessor;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference(
		target = "(content.processor.type=LayoutPageTemplateStructureRelReferences)"
	)
	private ExportImportContentProcessor<String>
		_layoutPageTemplateStructureRelReferencesExportImportContentProcessor;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutPageTemplateStructureRel>
		_stagedModelRepository;

}