/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
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
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate",
	service = StagedModelRepository.class
)
public class DDMTemplateStagedModelRepository
	implements StagedModelRepository<DDMTemplate> {

	@Override
	public DDMTemplate addStagedModel(
			PortletDataContext portletDataContext, DDMTemplate ddmTemplate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(DDMTemplate ddmTemplate)
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
	public DDMTemplate fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DDMTemplate fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<DDMTemplate> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDMTemplate getStagedModel(long templateId) throws PortalException {
		return _ddmTemplateLocalService.getDDMTemplate(templateId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, DDMTemplate ddmTemplate)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDMTemplate saveStagedModel(DDMTemplate ddmTemplate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDMTemplate updateStagedModel(
			PortletDataContext portletDataContext, DDMTemplate ddmTemplate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

}