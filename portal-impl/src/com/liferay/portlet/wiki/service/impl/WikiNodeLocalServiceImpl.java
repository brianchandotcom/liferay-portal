/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.wiki.DuplicateNodeNameException;
import com.liferay.portlet.wiki.NodeNameException;
import com.liferay.portlet.wiki.importers.WikiImporter;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.base.WikiNodeLocalServiceBaseImpl;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Raymond Augé
 */
public class WikiNodeLocalServiceImpl extends WikiNodeLocalServiceBaseImpl {

	public WikiNode addDefaultNode(long userId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addNode(
			userId, PropsValues.WIKI_INITIAL_NODE_NAME, StringPool.BLANK,
			serviceContext);
	}

	public WikiNode addNode(
			long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Node

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		Date now = new Date();

		validate(groupId, name);

		long nodeId = counterLocalService.increment();

		WikiNode node = wikiNodePersistence.create(nodeId);

		node.setUuid(serviceContext.getUuid());
		node.setGroupId(groupId);
		node.setCompanyId(user.getCompanyId());
		node.setUserId(user.getUserId());
		node.setUserName(user.getFullName());
		node.setCreateDate(serviceContext.getCreateDate(now));
		node.setModifiedDate(serviceContext.getModifiedDate(now));
		node.setName(name);
		node.setDescription(description);

		try {
			wikiNodePersistence.update(node, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {groupId=" + groupId + ", name=" +
						name + "}");
			}

			node = wikiNodePersistence.fetchByG_N(groupId, name, false);

			if (node == null) {
				throw se;
			}

			return node;
		}

		// Resources

		if (serviceContext.getAddGroupPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addNodeResources(
				node, serviceContext.getAddGroupPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addNodeResources(
				node, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return node;
	}

	public void addNodeResources(
			long nodeId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		addNodeResources(node, addGroupPermissions, addGuestPermissions);
	}

	public void addNodeResources(
			WikiNode node, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			node.getCompanyId(), node.getGroupId(), node.getUserId(),
			WikiNode.class.getName(), node.getNodeId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	public void addNodeResources(
			long nodeId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		addNodeResources(node, groupPermissions, guestPermissions);
	}

	public void addNodeResources(
			WikiNode node, String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			node.getCompanyId(), node.getGroupId(), node.getUserId(),
			WikiNode.class.getName(), node.getNodeId(), groupPermissions,
			guestPermissions);
	}

	public void deleteNode(long nodeId)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		deleteNode(node);
	}

	public void deleteNode(WikiNode node)
		throws PortalException, SystemException {

		// Indexer

		Indexer indexer = IndexerRegistryUtil.getIndexer(WikiPage.class);

		indexer.delete(node);

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			node.getCompanyId(), WikiNode.class.getName(), node.getNodeId());

		// Pages

		wikiPageLocalService.deletePages(node.getNodeId());

		// Resources

		resourceLocalService.deleteResource(
			node.getCompanyId(), WikiNode.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, node.getNodeId());

		// Node

		wikiNodePersistence.remove(node);
	}

	public void deleteNodes(long groupId)
		throws PortalException, SystemException {

		Iterator<WikiNode> itr = wikiNodePersistence.findByGroupId(
			groupId).iterator();

		while (itr.hasNext()) {
			WikiNode node = itr.next();

			deleteNode(node);
		}
	}

	public List<WikiNode> getCompanyNodes(long companyId, int start, int end)
		throws SystemException {

		return wikiNodePersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyNodesCount(long companyId) throws SystemException {
		return wikiNodePersistence.countByCompanyId(companyId);
	}

	public WikiNode getNode(long nodeId)
		throws PortalException, SystemException {

		return wikiNodePersistence.findByPrimaryKey(nodeId);
	}

	public WikiNode getNode(long groupId, String nodeName)
		throws PortalException, SystemException {

		return wikiNodePersistence.findByG_N(groupId, nodeName);
	}

	public List<WikiNode> getNodes(long groupId)
		throws PortalException, SystemException {

		List<WikiNode> nodes = wikiNodePersistence.findByGroupId(groupId);

		if (nodes.isEmpty()) {
			nodes = addDefaultNode(groupId);
		}

		return nodes;
	}

	public List<WikiNode> getNodes(long groupId, int start, int end)
		throws PortalException, SystemException {

		List<WikiNode> nodes = wikiNodePersistence.findByGroupId(
			groupId, start, end);

		if (nodes.isEmpty()) {
			nodes = addDefaultNode(groupId);
		}

		return nodes;
	}

	public int getNodesCount(long groupId) throws SystemException {
		return wikiNodePersistence.countByGroupId(groupId);
	}

	public void importPages(
			long userId, long nodeId, String importer,
			InputStream[] inputStreams, Map<String, String[]> options)
		throws PortalException, SystemException {

		WikiNode node = getNode(nodeId);

		WikiImporter wikiImporter = getWikiImporter(importer);

		wikiImporter.importPages(userId, node, inputStreams, options);
	}

	public void subscribeNode(long userId, long nodeId)
		throws PortalException, SystemException {

		WikiNode node = getNode(nodeId);

		subscriptionLocalService.addSubscription(
			userId, node.getGroupId(), WikiNode.class.getName(), nodeId);
	}

	public void unsubscribeNode(long userId, long nodeId)
		throws PortalException, SystemException {

		subscriptionLocalService.deleteSubscription(
			userId, WikiNode.class.getName(), nodeId);
	}

	public WikiNode updateNode(
			long nodeId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		validate(nodeId, node.getGroupId(), name);

		node.setModifiedDate(serviceContext.getModifiedDate(null));
		node.setName(name);
		node.setDescription(description);

		wikiNodePersistence.update(node, false);

		return node;
	}

	protected List<WikiNode> addDefaultNode(long groupId)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		WikiNode node = addDefaultNode(defaultUserId, serviceContext);

		List<WikiNode> nodes = new ArrayList<WikiNode>(1);

		nodes.add(node);

		return nodes;
	}

	protected WikiImporter getWikiImporter(String importer)
		throws SystemException {

		WikiImporter wikiImporter = _wikiImporters.get(importer);

		if (wikiImporter == null) {
			String importerClass = PropsUtil.get(
				PropsKeys.WIKI_IMPORTERS_CLASS, new Filter(importer));

			if (importerClass != null) {
				wikiImporter = (WikiImporter)InstancePool.get(importerClass);

				_wikiImporters.put(importer, wikiImporter);
			}

			if (importer == null) {
				throw new SystemException(
					"Unable to instantiate wiki importer class " +
						importerClass);
			}
		}

		return wikiImporter;
	}

	protected void validate(long groupId, String name)
		throws PortalException, SystemException {

		validate(0, groupId, name);
	}

	protected void validate(long nodeId, long groupId, String name)
		throws PortalException, SystemException {

		if (name.equalsIgnoreCase("tag")) {
			throw new NodeNameException(name + " is reserved");
		}

		if (!Validator.isAlphanumericName(name)) {
			throw new NodeNameException();
		}

		WikiNode node = wikiNodePersistence.fetchByG_N(groupId, name);

		if ((node != null) && (node.getNodeId() != nodeId)) {
			throw new DuplicateNodeNameException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		WikiNodeLocalServiceImpl.class);

	private Map<String, WikiImporter> _wikiImporters =
		new HashMap<String, WikiImporter>();

}