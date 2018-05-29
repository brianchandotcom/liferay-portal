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

package com.liferay.message.boards.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.message.boards.model.MBCategory",
	service = StagedModelRepository.class
)
public class MBCategoryStagedModelRepository
	implements StagedModelRepository<MBCategory> {

	@Override
	public MBCategory addStagedModel(
			PortletDataContext portletDataContext, MBCategory mbCategory)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(MBCategory mbCategory)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MBCategory fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MBCategory fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<MBCategory> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public MBCategory getStagedModel(long categoryId) throws PortalException {
		return _mbCategoryLocalService.getMBCategory(categoryId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, MBCategory mbCategory)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MBCategory saveStagedModel(MBCategory mbCategory)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MBCategory updateStagedModel(
			PortletDataContext portletDataContext, MBCategory mbCategory)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

}