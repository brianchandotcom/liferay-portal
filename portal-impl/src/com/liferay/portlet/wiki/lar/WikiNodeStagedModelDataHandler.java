/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.lar;

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;

/**
 * @author Zsolt Berentey
 */
public class WikiNodeStagedModelDataHandler
	extends BaseStagedModelDataHandler<WikiNode> {

	public static final String[] CLASS_NAMES = {WikiNode.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, WikiNode node)
		throws Exception {

		if (!portletDataContext.hasDateRange()) {
			return;
		}

		WikiNode node = WikiNodeUtil.findByPrimaryKey(nodeId);

		String path = getNodePath(portletDataContext, node);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element nodeElement = nodesElement.addElement("node");

		portletDataContext.addClassedModel(nodeElement, path, node, NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, WikiNode node)
		throws Exception {

		long userId = portletDataContext.getUserId(node.getUserUuid());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(node.getCreateDate());
		serviceContext.setModifiedDate(node.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		WikiNode importedNode = null;

		if (portletDataContext.isDataStrategyMirror()) {
			WikiNode existingNode = WikiNodeUtil.fetchByUUID_G(
				node.getUuid(), portletDataContext.getScopeGroupId());

			String initialNodeName = PropsValues.WIKI_INITIAL_NODE_NAME;

			if ((existingNode == null) &&
				initialNodeName.equals(node.getName())) {

				try {
					WikiNodeUtil.removeByG_N(
						portletDataContext.getScopeGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
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
				try {
					WikiNodeUtil.removeByG_N(
						portletDataContext.getScopeGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			String nodeName = getNodeName(
				portletDataContext, node, node.getName(), 2);

			importedNode = WikiNodeLocalServiceUtil.addNode(
				userId, nodeName, node.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(node, importedNode, NAMESPACE);
	}

	protected static String getNodeName(
			PortletDataContext portletDataContext, WikiNode node, String name,
			int count)
		throws Exception {

		WikiNode existingNode = WikiNodeUtil.fetchByG_N(
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

	protected static String getNodePath(
		PortletDataContext portletDataContext, WikiNode node) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.WIKI));
		sb.append("/nodes/");
		sb.append(node.getNodeId());
		sb.append(".xml");

		return sb.toString();
	}

}
