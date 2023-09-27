/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure",
	service = StagedModelRepository.class
)
public class DDMStructureStagedModelRepository
	implements StagedModelRepository<DDMStructure> {

	@Override
	public DDMStructure addStagedModel(
			PortletDataContext portletDataContext, DDMStructure ddmStructure)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(DDMStructure ddmStructure)
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
	public DDMStructure fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DDMStructure fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<DDMStructure> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDMStructure getStagedModel(long structureId)
		throws PortalException {

		return _ddmStructureLocalService.getDDMStructure(structureId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, DDMStructure ddmStructure)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDMStructure saveStagedModel(DDMStructure ddmStructure)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDMStructure updateStagedModel(
			PortletDataContext portletDataContext, DDMStructure ddmStructure)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}