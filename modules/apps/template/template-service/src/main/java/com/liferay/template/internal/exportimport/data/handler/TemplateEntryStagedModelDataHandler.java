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

package com.liferay.template.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class TemplateEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<TemplateEntry> {

	public static final String[] CLASS_NAMES = {TemplateEntry.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		TemplateEntry templateEntry = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (templateEntry != null) {
			deleteStagedModel(templateEntry);
		}
	}

	@Override
	public void deleteStagedModel(TemplateEntry templateEntry)
		throws PortalException {

		_ddmTemplateLocalService.deleteDDMTemplate(
			templateEntry.getDDMTemplateId());

		_templateEntryLocalService.deleteTemplateEntry(
			templateEntry.getTemplateEntryId());
	}

	@Override
	public TemplateEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _templateEntryLocalService.fetchTemplateEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<TemplateEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _templateEntryLocalService.getTemplateEntriesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(TemplateEntry templateEntry) {
		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchDDMTemplate(
			templateEntry.getDDMTemplateId());

		return ddmTemplate.getNameCurrentValue();
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, DDMTemplate ddmTemplate) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(ddmTemplate.getCreateDate());
		serviceContext.setModifiedDate(ddmTemplate.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, TemplateEntry templateEntry) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(templateEntry.getCreateDate());
		serviceContext.setModifiedDate(templateEntry.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, TemplateEntry templateEntry)
		throws Exception {

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchDDMTemplate(
			templateEntry.getDDMTemplateId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, templateEntry, ddmTemplate,
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element templateEntryElement = portletDataContext.getExportDataElement(
			templateEntry);

		templateEntry.setUserUuid(templateEntry.getUserUuid());

		String templateEntryPath = ExportImportPathUtil.getModelPath(
			templateEntry);

		templateEntryElement.addAttribute("path", templateEntryPath);

		portletDataContext.addReferenceElement(
			templateEntry, templateEntryElement, templateEntry,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);

		portletDataContext.addZipEntry(templateEntryPath, templateEntry);
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

		long userId = portletDataContext.getUserId(templateEntry.getUserUuid());

		Map<Long, Long> templateEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				TemplateEntry.class);

		ServiceContext serviceContext = createServiceContext(
			portletDataContext, templateEntry);

		TemplateEntry importedTemplateEntry = null;

		TemplateEntry existingTemplateEntry = fetchStagedModelByUuidAndGroupId(
			templateEntry.getUuid(), portletDataContext.getScopeGroupId());

		if (existingTemplateEntry == null) {
			Map<Long, Long> ddmTemplateIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".templateId");

			long ddmTemplateId = MapUtil.getLong(
				ddmTemplateIds, templateEntry.getDDMTemplateId(),
				templateEntry.getDDMTemplateId());

			serviceContext.setUuid(templateEntry.getUuid());

			importedTemplateEntry = _templateEntryLocalService.addTemplateEntry(
				userId, portletDataContext.getScopeGroupId(), ddmTemplateId,
				templateEntry.getInfoItemClassName(),
				templateEntry.getInfoItemFormVariationKey());
		}
		else {
			importedTemplateEntry =
				_templateEntryLocalService.updateTemplateEntry(
					existingTemplateEntry.getTemplateEntryId());
		}

		templateEntryIds.put(
			templateEntry.getTemplateEntryId(),
			importedTemplateEntry.getTemplateEntryId());

		Map<String, String> templateEntryUuids =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				TemplateEntry.class + ".uuid");

		templateEntryUuids.put(
			templateEntry.getUuid(), importedTemplateEntry.getUuid());
	}

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

}