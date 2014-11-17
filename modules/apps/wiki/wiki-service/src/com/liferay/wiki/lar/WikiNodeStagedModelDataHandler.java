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

package com.liferay.wiki.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class WikiNodeStagedModelDataHandler
	extends BaseStagedModelDataHandler<WikiNode> {

	public static final String[] CLASS_NAMES = {WikiNode.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		WikiNode wikiNode = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (wikiNode != null) {
			WikiNodeLocalServiceUtil.deleteNode(wikiNode);
		}
	}

	@Override
	public WikiNode fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<WikiNode> wikiNodes =
			WikiNodeLocalServiceUtil.getWikiNodesByUuidAndCompanyId(
				uuid, companyId);

		if (ListUtil.isEmpty(wikiNodes)) {
			return null;
		}

		return wikiNodes.get(0);
	}

	@Override
	public WikiNode fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return WikiNodeLocalServiceUtil.fetchWikiNodeByUuidAndGroupId(
			uuid, groupId);
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

		if (portletDataContext.isDataStrategyMirror()) {
			WikiNode existingNode = fetchStagedModelByUuidAndGroupId(
				node.getUuid(), portletDataContext.getScopeGroupId());

			String initialNodeName = PropsValues.WIKI_INITIAL_NODE_NAME;

			if ((existingNode == null) &&
				initialNodeName.equals(node.getName())) {

				WikiNode initialNode = WikiNodeLocalServiceUtil.fetchNode(
					portletDataContext.getScopeGroupId(), node.getName());

				if (initialNode != null) {
					WikiNodeLocalServiceUtil.deleteWikiNode(initialNode);
				}
			}

			if (existingNode == null) {
				serviceContext.setUuid(node.getUuid());

				importedNode = WikiNodeLocalServiceUtil.addNode(
					userId, node.getName(), node.getDescription(),
					serviceContext);
			}
			else {
				importedNode = WikiNodeLocalServiceUtil.updateNode(
					existingNode.getNodeId(), node.getName(),
					node.getDescription(), serviceContext);
			}
		}
		else {
			String initialNodeName = PropsValues.WIKI_INITIAL_NODE_NAME;

			if (initialNodeName.equals(node.getName())) {
				WikiNode initialNode = WikiNodeLocalServiceUtil.fetchNode(
					portletDataContext.getScopeGroupId(), node.getName());

				if (initialNode != null) {
					WikiNodeLocalServiceUtil.deleteWikiNode(initialNode);
				}
			}

			String nodeName = getNodeName(
				portletDataContext, node, node.getName(), 2);

			importedNode = WikiNodeLocalServiceUtil.addNode(
				userId, nodeName, node.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(node, importedNode);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, WikiNode node)
		throws Exception {

		long userId = portletDataContext.getUserId(node.getUserUuid());

		WikiNode existingNode = fetchStagedModelByUuidAndGroupId(
			node.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingNode == null) || !existingNode.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingNode.getTrashHandler();

		if (trashHandler.isRestorable(existingNode.getNodeId())) {
			trashHandler.restoreTrashEntry(userId, existingNode.getNodeId());
		}
	}

	protected String getNodeName(
			PortletDataContext portletDataContext, WikiNode node, String name,
			int count)
		throws Exception {

		WikiNode existingNode = WikiNodeLocalServiceUtil.fetchNode(
			portletDataContext.getScopeGroupId(), name);

		if (existingNode == null) {
			return name;
		}

		String nodeName = node.getName();

		return getNodeName(
			portletDataContext, node,
			nodeName.concat(StringPool.SPACE).concat(String.valueOf(count)),
			++count);
	}

}