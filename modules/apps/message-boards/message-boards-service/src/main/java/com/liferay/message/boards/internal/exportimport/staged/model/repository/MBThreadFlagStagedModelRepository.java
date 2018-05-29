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
import com.liferay.message.boards.model.MBThreadFlag;
import com.liferay.message.boards.service.MBThreadFlagLocalService;
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
	property = "model.class.name=com.liferay.message.boards.model.MBThreadFlag",
	service = StagedModelRepository.class
)
public class MBThreadFlagStagedModelRepository
	implements StagedModelRepository<MBThreadFlag> {

	@Override
	public MBThreadFlag addStagedModel(
			PortletDataContext portletDataContext, MBThreadFlag mbThreadFlag)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(MBThreadFlag mbThreadFlag)
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
	public MBThreadFlag fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MBThreadFlag fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<MBThreadFlag> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public MBThreadFlag getStagedModel(long threadFlagId)
		throws PortalException {

		return _mbThreadFlagLocalService.getMBThreadFlag(threadFlagId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, MBThreadFlag mbThreadFlag)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MBThreadFlag saveStagedModel(MBThreadFlag mbThreadFlag)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MBThreadFlag updateStagedModel(
			PortletDataContext portletDataContext, MBThreadFlag mbThreadFlag)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private MBThreadFlagLocalService _mbThreadFlagLocalService;

}