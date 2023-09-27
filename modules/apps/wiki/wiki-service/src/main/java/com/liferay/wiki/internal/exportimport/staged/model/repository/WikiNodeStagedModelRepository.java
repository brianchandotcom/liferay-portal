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
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.wiki.model.WikiNode",
	service = StagedModelRepository.class
)
public class WikiNodeStagedModelRepository
	implements StagedModelRepository<WikiNode> {

	@Override
	public WikiNode addStagedModel(
			PortletDataContext portletDataContext, WikiNode wikiNode)
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
	public void deleteStagedModel(WikiNode wikiNode) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public WikiNode fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiNode fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WikiNode> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WikiNode getStagedModel(long nodeId) throws PortalException {
		return _wikiNodeLocalService.getWikiNode(nodeId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, WikiNode wikiNode)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public WikiNode saveStagedModel(WikiNode wikiNode) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiNode updateStagedModel(
			PortletDataContext portletDataContext, WikiNode wikiNode)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private WikiNodeLocalService _wikiNodeLocalService;

}