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
	immediate = true,
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