/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBTemplateLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.knowledge.base.model.KBTemplate",
	service = StagedModelRepository.class
)
public class KBTemplateStagedModelRepository
	implements StagedModelRepository<KBTemplate> {

	@Override
	public KBTemplate addStagedModel(
			PortletDataContext portletDataContext, KBTemplate kbTemplate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(KBTemplate kbTemplate)
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
	public KBTemplate fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KBTemplate fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<KBTemplate> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBTemplate getStagedModel(long kbTemplateId) throws PortalException {
		return _kbTemplateLocalService.getKBTemplate(kbTemplateId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, KBTemplate kbTemplate)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBTemplate saveStagedModel(KBTemplate kbTemplate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBTemplate updateStagedModel(
			PortletDataContext portletDataContext, KBTemplate kbTemplate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private KBTemplateLocalService _kbTemplateLocalService;

}