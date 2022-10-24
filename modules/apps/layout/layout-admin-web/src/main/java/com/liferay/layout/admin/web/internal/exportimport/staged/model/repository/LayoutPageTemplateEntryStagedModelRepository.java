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

package com.liferay.layout.admin.web.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry",
	service = StagedModelRepository.class
)
public class LayoutPageTemplateEntryStagedModelRepository
	implements StagedModelRepository<LayoutPageTemplateEntry> {

	@Override
	public LayoutPageTemplateEntry addStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			layoutPageTemplateEntry.getUserUuid());

		long plid = layoutPageTemplateEntry.getPlid();

		if (layoutPageTemplateEntry.getLayoutPrototypeId() > 0) {
			plid = 0;
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutPageTemplateEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(layoutPageTemplateEntry.getUuid());
		}

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			userId, layoutPageTemplateEntry.getGroupId(),
			layoutPageTemplateEntry.getLayoutPageTemplateCollectionId(),
			layoutPageTemplateEntry.getClassNameId(),
			layoutPageTemplateEntry.getClassTypeId(),
			layoutPageTemplateEntry.getName(),
			layoutPageTemplateEntry.getType(),
			layoutPageTemplateEntry.getPreviewFileEntryId(),
			layoutPageTemplateEntry.isDefaultTemplate(),
			layoutPageTemplateEntry.getLayoutPrototypeId(), plid, 0,
			layoutPageTemplateEntry.getStatus(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			layoutPageTemplateEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (layoutPageTemplateEntry != null) {
			deleteStagedModel(layoutPageTemplateEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public LayoutPageTemplateEntry fetchMissingReference(
		String uuid, long groupId) {

		return (LayoutPageTemplateEntry)
			_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
	}

	@Override
	public LayoutPageTemplateEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<LayoutPageTemplateEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _layoutPageTemplateEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public LayoutPageTemplateEntry getStagedModel(long classPK) {
		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntry(classPK);
	}

	@Override
	public LayoutPageTemplateEntry saveStagedModel(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public LayoutPageTemplateEntry updateStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		existingLayoutPageTemplateEntry.setName(
			layoutPageTemplateEntry.getName());
		existingLayoutPageTemplateEntry.setType(
			layoutPageTemplateEntry.getType());
		existingLayoutPageTemplateEntry.setPreviewFileEntryId(
			layoutPageTemplateEntry.getPreviewFileEntryId());
		existingLayoutPageTemplateEntry.setDefaultTemplate(
			layoutPageTemplateEntry.isDefaultTemplate());
		existingLayoutPageTemplateEntry.setLayoutPrototypeId(
			layoutPageTemplateEntry.getLayoutPrototypeId());
		existingLayoutPageTemplateEntry.setPlid(
			layoutPageTemplateEntry.getPlid());
		existingLayoutPageTemplateEntry.setStatus(
			layoutPageTemplateEntry.getStatus());

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(existingLayoutPageTemplateEntry);
	}

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}