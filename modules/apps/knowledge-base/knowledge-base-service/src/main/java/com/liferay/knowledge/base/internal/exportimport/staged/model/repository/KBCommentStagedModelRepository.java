/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.service.KBCommentLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.knowledge.base.model.KBComment",
	service = StagedModelRepository.class
)
public class KBCommentStagedModelRepository
	implements StagedModelRepository<KBComment> {

	@Override
	public KBComment addStagedModel(
			PortletDataContext portletDataContext, KBComment kbComment)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(KBComment kbComment) throws PortalException {
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
	public KBComment fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KBComment fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<KBComment> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBComment getStagedModel(long kbCommentId) throws PortalException {
		return _kbCommentLocalService.getKBComment(kbCommentId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, KBComment kbComment)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBComment saveStagedModel(KBComment kbComment)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public KBComment updateStagedModel(
			PortletDataContext portletDataContext, KBComment kbComment)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private KBCommentLocalService _kbCommentLocalService;

}