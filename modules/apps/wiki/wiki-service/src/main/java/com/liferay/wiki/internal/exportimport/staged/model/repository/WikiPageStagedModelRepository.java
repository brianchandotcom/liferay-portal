/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.wiki.model.WikiPage",
	service = StagedModelRepository.class
)
public class WikiPageStagedModelRepository
	implements StagedModelRepository<WikiPage> {

	@Override
	public WikiPage addStagedModel(
			PortletDataContext portletDataContext, WikiPage wikiPage)
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
	public void deleteStagedModel(WikiPage wikiPage) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WikiPage> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage getStagedModel(long pageId) throws PortalException {
		return _wikiPageLocalService.getWikiPage(pageId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, WikiPage wikiPage)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage saveStagedModel(WikiPage wikiPage) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage updateStagedModel(
			PortletDataContext portletDataContext, WikiPage wikiPage)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

}