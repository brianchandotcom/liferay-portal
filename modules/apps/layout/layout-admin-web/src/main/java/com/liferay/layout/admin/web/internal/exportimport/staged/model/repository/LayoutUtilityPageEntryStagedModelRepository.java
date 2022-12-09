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
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.layout.utility.page.model.LayoutUtilityPageEntry",
	service = StagedModelRepository.class
)
public class LayoutUtilityPageEntryStagedModelRepository
	implements StagedModelRepository<LayoutUtilityPageEntry> {

	@Override
	public LayoutUtilityPageEntry addStagedModel(
			PortletDataContext portletDataContext,
			LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			layoutUtilityPageEntry.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutUtilityPageEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(layoutUtilityPageEntry.getUuid());
		}

		return _layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
			layoutUtilityPageEntry.getExternalReferenceCode(), userId,
			layoutUtilityPageEntry.getGroupId(),
			layoutUtilityPageEntry.getPlid(),
			layoutUtilityPageEntry.getPreviewFileEntryId(),
			layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry(),
			layoutUtilityPageEntry.getName(), layoutUtilityPageEntry.getType(),
			0);
	}

	@Override
	public void deleteStagedModel(LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws PortalException {

		_layoutUtilityPageEntryLocalService.deleteLayoutUtilityPageEntry(
			layoutUtilityPageEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (layoutUtilityPageEntry != null) {
			deleteStagedModel(layoutUtilityPageEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public LayoutUtilityPageEntry fetchMissingReference(
		String uuid, long groupId) {

		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public LayoutUtilityPageEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _layoutUtilityPageEntryLocalService.
			fetchLayoutUtilityPageEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<LayoutUtilityPageEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutUtilityPageEntryLocalService.
			getLayoutUtilityPageEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _layoutUtilityPageEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public LayoutUtilityPageEntry getStagedModel(long classPK) {
		return _layoutUtilityPageEntryLocalService.fetchLayoutUtilityPageEntry(
			classPK);
	}

	@Override
	public LayoutUtilityPageEntry saveStagedModel(
			LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws PortalException {

		return _layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
			layoutUtilityPageEntry);
	}

	@Override
	public LayoutUtilityPageEntry updateStagedModel(
			PortletDataContext portletDataContext,
			LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws PortalException {

		LayoutUtilityPageEntry existingLayoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.getLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());

		existingLayoutUtilityPageEntry.setPlid(
			layoutUtilityPageEntry.getPlid());
		existingLayoutUtilityPageEntry.setPreviewFileEntryId(
			layoutUtilityPageEntry.getPreviewFileEntryId());
		existingLayoutUtilityPageEntry.setDefaultLayoutUtilityPageEntry(
			layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry());
		existingLayoutUtilityPageEntry.setName(
			layoutUtilityPageEntry.getName());
		existingLayoutUtilityPageEntry.setType(
			layoutUtilityPageEntry.getType());

		return _layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
			existingLayoutUtilityPageEntry);
	}

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}