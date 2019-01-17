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

package com.liferay.wiki.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zsolt Berentey
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class WikiNodeStagedModelDataHandler
	extends BaseStagedModelDataHandler<WikiNode> {

	public static final String[] CLASS_NAMES = {WikiNode.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		WikiNode wikiNode = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (wikiNode != null) {
			deleteStagedModel(wikiNode);
		}
	}

	@Override
	public void deleteStagedModel(WikiNode node) throws PortalException {
		_wikiNodeLocalService.deleteNode(node);
	}

	@Override
	public WikiNode fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _wikiNodeLocalService.fetchWikiNodeByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<WikiNode> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _wikiNodeLocalService.getWikiNodesByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, WikiNode node)
		throws Exception {

		Element nodeElement = portletDataContext.getExportDataElement(node);

		portletDataContext.addClassedModel(
			nodeElement, ExportImportPathUtil.getModelPath(node), node);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long nodeId)
		throws Exception {

		WikiNode existingNode = fetchMissingReference(uuid, groupId);

		if (existingNode == null) {
			return;
		}

		Map<Long, Long> nodeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

		nodeIds.put(nodeId, existingNode.getNodeId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, WikiNode node)
		throws Exception {

		long userId = portletDataContext.getUserId(node.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			node);

		WikiNode importedNode = null;

		WikiNode existingNode = fetchStagedModelByUuidAndGroupId(
			node.getUuid(), portletDataContext.getScopeGroupId());

		String nodeName = node.getName();

		WikiNode nodeWithSameName = _wikiNodeLocalService.fetchNode(
			portletDataContext.getScopeGroupId(), nodeName);

		if (portletDataContext.isDataStrategyMirror()) {
			if (existingNode == null) {
				if (nodeWithSameName != null) {
					nodeName = getNodeName(
						portletDataContext, node, nodeName, 2);
				}

				serviceContext.setUuid(node.getUuid());

				importedNode = _wikiNodeLocalService.addNode(
					userId, nodeName, node.getDescription(), serviceContext);
			}
			else {
				String uuid = existingNode.getUuid();

				if ((nodeWithSameName != null) &&
					!uuid.equals(nodeWithSameName.getUuid())) {

					nodeName = getNodeName(
						portletDataContext, node, nodeName, 2);
				}

				importedNode = _wikiNodeLocalService.updateNode(
					existingNode.getNodeId(), nodeName, node.getDescription(),
					serviceContext);
			}
		}
		else {
			String initialNodeName =
				_wikiGroupServiceConfiguration.initialNodeName();

			if ((existingNode != null) &&
				initialNodeName.equals(existingNode.getName())) {

				importedNode = _wikiNodeLocalService.updateNode(
					existingNode.getNodeId(), nodeName, node.getDescription(),
					serviceContext);
			}
			else {
				nodeName = getNodeName(portletDataContext, node, nodeName, 2);

				importedNode = _wikiNodeLocalService.addNode(
					userId, nodeName, node.getDescription(), serviceContext);
			}
		}

		portletDataContext.importClassedModel(node, importedNode);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, WikiNode node)
		throws Exception {

		WikiNode existingNode = fetchStagedModelByUuidAndGroupId(
			node.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingNode == null) || !existingNode.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiNode.class.getName());

		if (trashHandler.isRestorable(existingNode.getNodeId())) {
			long userId = portletDataContext.getUserId(node.getUserUuid());

			trashHandler.restoreTrashEntry(userId, existingNode.getNodeId());
		}
	}

	protected String getNodeName(
			PortletDataContext portletDataContext, WikiNode node, String name,
			int count)
		throws Exception {

		WikiNode existingNode = _wikiNodeLocalService.fetchNode(
			portletDataContext.getScopeGroupId(), name);

		if (existingNode == null) {
			return name;
		}

		String nodeName = node.getName();

		return getNodeName(
			portletDataContext, node,
			nodeName.concat(
				StringPool.SPACE
			).concat(
				String.valueOf(count)
			),
			++count);
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	@Reference
	private WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

	private WikiNodeLocalService _wikiNodeLocalService;

}