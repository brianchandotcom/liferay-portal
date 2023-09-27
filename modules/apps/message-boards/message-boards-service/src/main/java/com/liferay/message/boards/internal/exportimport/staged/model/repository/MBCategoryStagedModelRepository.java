/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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