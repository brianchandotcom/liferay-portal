/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.template.model.TemplateEntry",
	service = StagedModelRepository.class
)
public class TemplateEntryStagedModelRepository
	implements StagedModelRepository<TemplateEntry> {

	@Override
	public TemplateEntry addStagedModel(
			PortletDataContext portletDataContext, TemplateEntry templateEntry)
		throws PortalException {

		long userId = portletDataContext.getUserId(templateEntry.getUserUuid());

		Map<Long, Long> ddmTemplateIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class);

		long ddmTemplateId = MapUtil.getLong(
			ddmTemplateIds, templateEntry.getDDMTemplateId(),
			templateEntry.getDDMTemplateId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			templateEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(templateEntry.getUuid());
		}

		return _templateEntryLocalService.addTemplateEntry(
			userId, templateEntry.getGroupId(), ddmTemplateId,
			templateEntry.getInfoItemClassName(),
			templateEntry.getInfoItemFormVariationKey(), serviceContext);
	}

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

		_ddmTemplateLocalService.deleteTemplate(
			templateEntry.getDDMTemplateId());

		_templateEntryLocalService.deleteTemplateEntry(templateEntry);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public TemplateEntry fetchMissingReference(String uuid, long groupId) {
		return (TemplateEntry)
			_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
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
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _templateEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public TemplateEntry getStagedModel(long templateEntryId)
		throws PortalException {

		return _templateEntryLocalService.getTemplateEntry(templateEntryId);
	}

	@Override
	public TemplateEntry saveStagedModel(TemplateEntry templateEntry)
		throws PortalException {

		return _templateEntryLocalService.updateTemplateEntry(templateEntry);
	}

	@Override
	public TemplateEntry updateStagedModel(
			PortletDataContext portletDataContext, TemplateEntry templateEntry)
		throws PortalException {

		return _templateEntryLocalService.updateTemplateEntry(
			templateEntry.getTemplateEntryId());
	}

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

}