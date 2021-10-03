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

package com.liferay.template.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.template.model.TemplateEntry",
	service = StagedModelRepository.class
)
public class TemplateEntryStagedModelRepository
	implements StagedModelRepository<TemplateEntry> {

	@Override
	public TemplateEntry addStagedModel(
			PortletDataContext portletDataContext, TemplateEntry templateEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
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

		_templateEntryLocalService.deleteTemplateEntry(templateEntry);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public TemplateEntry fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
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
	public void restoreStagedModel(
			PortletDataContext portletDataContext, TemplateEntry templateEntry)
		throws PortletDataException {

		throw new UnsupportedOperationException();
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

		throw new UnsupportedOperationException();
	}

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

}