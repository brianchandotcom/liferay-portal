/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.template.model.TemplateEntry;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = StagedModelDataHandler.class)
public class TemplateEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<TemplateEntry> {

	public static final String[] CLASS_NAMES = {TemplateEntry.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public void deleteStagedModel(TemplateEntry templateEntry)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(templateEntry);
	}

	@Override
	public TemplateEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _stagedModelRepository.fetchStagedModelByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<TemplateEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(TemplateEntry templateEntry) {
		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchDDMTemplate(
			templateEntry.getDDMTemplateId());

		if (ddmTemplate != null) {
			return ddmTemplate.getNameCurrentValue();
		}

		return StringPool.BLANK;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, TemplateEntry templateEntry)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(
			templateEntry);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(templateEntry),
			templateEntry);

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchDDMTemplate(
			templateEntry.getDDMTemplateId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, templateEntry, ddmTemplate,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long templateEntryId)
		throws Exception {

		TemplateEntry existingTemplateEntry = fetchMissingReference(
			uuid, groupId);

		if (existingTemplateEntry == null) {
			return;
		}

		Map<Long, Long> templateEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				TemplateEntry.class);

		templateEntryIds.put(
			templateEntryId, existingTemplateEntry.getTemplateEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, TemplateEntry templateEntry)
		throws Exception {

		TemplateEntry importedTemplateEntry =
			(TemplateEntry)templateEntry.clone();

		importedTemplateEntry.setGroupId(portletDataContext.getScopeGroupId());

		TemplateEntry existingTemplateEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				templateEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingTemplateEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedTemplateEntry = _stagedModelRepository.addStagedModel(
				portletDataContext, importedTemplateEntry);
		}
		else {
			importedTemplateEntry.setMvccVersion(
				existingTemplateEntry.getMvccVersion());
			importedTemplateEntry.setTemplateEntryId(
				existingTemplateEntry.getTemplateEntryId());

			importedTemplateEntry = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedTemplateEntry);
		}

		portletDataContext.importClassedModel(
			templateEntry, importedTemplateEntry);
	}

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.template.model.TemplateEntry)"
	)
	private StagedModelRepository<TemplateEntry> _stagedModelRepository;

}