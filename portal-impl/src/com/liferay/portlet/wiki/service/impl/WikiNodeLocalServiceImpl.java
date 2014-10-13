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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.util.TrashUtil;
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
import java.util.List;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, deleting, importing,
 * subscription handling of, trash handling of, and updating wiki nodes.
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Raymond Augé
 */
public class WikiNodeLocalServiceImpl extends WikiNodeLocalServiceBaseImpl {

	@Override
	public WikiNode addDefaultNode(long userId, ServiceContext serviceContext)
		throws PortalException {

		return addNode(
			userId, PropsValues.WIKI_INITIAL_NODE_NAME, StringPool.BLANK,
			serviceContext);
	}

	@Override
	public WikiNode addNode(
			long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

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
			wikiNodePersistence.update(node);
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

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addNodeResources(
				node, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addNodeResources(
				node, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return node;
	}

	@Override
	public void addNodeResources(
			long nodeId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		addNodeResources(node, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addNodeResources(
			long nodeId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		addNodeResources(node, groupPermissions, guestPermissions);
	}

	@Override
	public void addNodeResources(
			WikiNode node, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			node.getCompanyId(), node.getGroupId(), node.getUserId(),
			WikiNode.class.getName(), node.getNodeId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addNodeResources(
			WikiNode node, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			node.getCompanyId(), node.getGroupId(), node.getUserId(),
			WikiNode.class.getName(), node.getNodeId(), groupPermissions,
			guestPermissions);
	}

	@Override
	public void deleteNode(long nodeId) throws PortalException {
		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		wikiNodeLocalService.deleteNode(node);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public void deleteNode(WikiNode node) throws PortalException {

		// Pages

		wikiPageLocalService.deletePages(node.getNodeId());

		// Node

		wikiNodePersistence.remove(node);

		// Resources

		resourceLocalService.deleteResource(
			node.getCompanyId(), WikiNode.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, node.getNodeId());

		// Attachments

		long folderId = node.getAttachmentsFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			PortletFileRepositoryUtil.deletePortletFolder(folderId);
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			node.getCompanyId(), WikiNode.class.getName(), node.getNodeId());

		if (node.isInTrash()) {
			node.setName(TrashUtil.getOriginalTitle(node.getName()));

			// Trash

			trashEntryLocalService.deleteEntry(
				WikiNode.class.getName(), node.getNodeId());

			// Indexer

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				WikiNode.class);

			indexer.delete(node);
		}
	}

	@Override
	public void deleteNodes(long groupId) throws PortalException {
		List<WikiNode> nodes = wikiNodePersistence.findByGroupId(groupId);

		for (WikiNode node : nodes) {
			wikiNodeLocalService.deleteNode(node);
		}

		PortletFileRepositoryUtil.deletePortletRepository(
			groupId, PortletKeys.WIKI);
	}

	@Override
	public WikiNode fetchNode(long groupId, String name) {
		return wikiNodePersistence.fetchByG_N(groupId, name);
	}

	@Override
	public WikiNode fetchNodeByUuidAndGroupId(String uuid, long groupId) {
		return wikiNodePersistence.fetchByUUID_G(uuid, groupId);
	}

	@Override
	public List<WikiNode> getCompanyNodes(long companyId, int start, int end) {
		return wikiNodePersistence.findByC_S(
			companyId, WorkflowConstants.STATUS_APPROVED, start, end);
	}

	@Override
	public List<WikiNode> getCompanyNodes(
		long companyId, int status, int start, int end) {

		return wikiNodePersistence.findByC_S(companyId, status, start, end);
	}

	@Override
	public int getCompanyNodesCount(long companyId) {
		return wikiNodePersistence.countByC_S(
			companyId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getCompanyNodesCount(long companyId, int status) {
		return wikiNodePersistence.countByC_S(companyId, status);
	}

	@Override
	public WikiNode getNode(long nodeId) throws PortalException {
		return wikiNodePersistence.findByPrimaryKey(nodeId);
	}

	@Override
	public WikiNode getNode(long groupId, String nodeName)
		throws PortalException {

		return wikiNodePersistence.findByG_N(groupId, nodeName);
	}

	@Override
	public List<WikiNode> getNodes(long groupId) throws PortalException {
		return getNodes(groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<WikiNode> getNodes(long groupId, int status)
		throws PortalException {

		List<WikiNode> nodes = wikiNodePersistence.findByG_S(groupId, status);

		if (nodes.isEmpty()) {
			nodes = addDefaultNode(groupId);
		}

		return nodes;
	}

	@Override
	public List<WikiNode> getNodes(long groupId, int start, int end)
		throws PortalException {

		return getNodes(groupId, WorkflowConstants.STATUS_APPROVED, start, end);
	}

	@Override
	public List<WikiNode> getNodes(long groupId, int status, int start, int end)
		throws PortalException {

		List<WikiNode> nodes = wikiNodePersistence.findByG_S(
			groupId, status, start, end);

		if (nodes.isEmpty()) {
			nodes = addDefaultNode(groupId);
		}

		return nodes;
	}

	@Override
	public int getNodesCount(long groupId) {
		return wikiNodePersistence.countByG_S(
			groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getNodesCount(long groupId, int status) {
		return wikiNodePersistence.countByG_S(groupId, status);
	}

	@Override
	public void importPages(
			long userId, long nodeId, String importer,
			InputStream[] inputStreams, Map<String, String[]> options)
		throws PortalException {

		WikiNode node = getNode(nodeId);

		WikiImporter wikiImporter = getWikiImporter(importer);

		wikiImporter.importPages(userId, node, inputStreams, options);
	}

	@Override
	public WikiNode moveNodeToTrash(long userId, long nodeId)
		throws PortalException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		return moveNodeToTrash(userId, node);
	}

	@Override
	public WikiNode moveNodeToTrash(long userId, WikiNode node)
		throws PortalException {

		// Node

		int oldStatus = node.getStatus();

		node = updateStatus(
			userId, node, WorkflowConstants.STATUS_IN_TRASH,
			new ServiceContext());

		// Trash

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("title", node.getName());

		TrashEntry trashEntry = trashEntryLocalService.addTrashEntry(
			userId, node.getGroupId(), WikiNode.class.getName(),
			node.getNodeId(), node.getUuid(), null, oldStatus, null,
			typeSettingsProperties);

		node.setName(TrashUtil.getTrashTitle(trashEntry.getEntryId()));

		wikiNodePersistence.update(node);

		// Pages

		moveDependentsToTrash(node.getNodeId(), trashEntry.getEntryId());

		return node;
	}

	@Override
	public void restoreNodeFromTrash(long userId, WikiNode node)
		throws PortalException {

		// Node

		node.setName(TrashUtil.getOriginalTitle(node.getName()));

		wikiNodePersistence.update(node);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			WikiNode.class.getName(), node.getNodeId());

		updateStatus(
			userId, node, trashEntry.getStatus(), new ServiceContext());

		// Pages

		restoreDependentsFromTrash(userId, node.getNodeId());

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry);
	}

	@Override
	public void subscribeNode(long userId, long nodeId) throws PortalException {
		WikiNode node = getNode(nodeId);

		subscriptionLocalService.addSubscription(
			userId, node.getGroupId(), WikiNode.class.getName(), nodeId);
	}

	@Override
	public void unsubscribeNode(long userId, long nodeId)
		throws PortalException {

		subscriptionLocalService.deleteSubscription(
			userId, WikiNode.class.getName(), nodeId);
	}

	@Override
	public WikiNode updateNode(
			long nodeId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		validate(nodeId, node.getGroupId(), name);

		node.setModifiedDate(serviceContext.getModifiedDate(null));
		node.setName(name);
		node.setDescription(description);

		wikiNodePersistence.update(node);

		return node;
	}

	@Override
	public WikiNode updateStatus(
			long userId, WikiNode node, int status,
			ServiceContext serviceContext)
		throws PortalException {

		// Node

		User user = userPersistence.findByPrimaryKey(userId);

		Date now = new Date();

		node.setStatus(status);
		node.setStatusByUserId(userId);
		node.setStatusByUserName(user.getFullName());
		node.setStatusDate(now);

		wikiNodePersistence.update(node);

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			WikiNode.class);

		indexer.reindex(node);

		return node;
	}

	protected List<WikiNode> addDefaultNode(long groupId)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		WikiNode node = wikiNodeLocalService.addDefaultNode(
			defaultUserId, serviceContext);

		List<WikiNode> nodes = new ArrayList<WikiNode>(1);

		nodes.add(node);

		return nodes;
	}

	protected WikiImporter getWikiImporter(String importer) {
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

	protected void moveDependentsToTrash(long nodeId, long trashEntryId)
		throws PortalException {

		List<WikiPage> pages = wikiPagePersistence.findByNodeId(nodeId);

		for (WikiPage page : pages) {
			wikiPageLocalService.moveDependentToTrash(page, trashEntryId);
		}
	}

	protected void restoreDependentsFromTrash(long userId, long nodeId)
		throws PortalException {

		List<WikiPage> pages = wikiPagePersistence.findByN_H(nodeId, true);

		for (WikiPage page : pages) {
			if (!page.isInTrashImplicitly()) {
				continue;
			}

			wikiPageLocalService.restorePageFromTrash(userId, page);
		}
	}

	protected void validate(long nodeId, long groupId, String name)
		throws PortalException {

		if (StringUtil.equalsIgnoreCase(name, "tag")) {
			throw new NodeNameException(name + " is reserved");
		}

		if (Validator.isNull(name)) {
			throw new NodeNameException();
		}

		WikiNode node = wikiNodePersistence.fetchByG_N(groupId, name);

		if ((node != null) && (node.getNodeId() != nodeId)) {
			throw new DuplicateNodeNameException("{nodeId=" + nodeId + "}");
		}
	}

	protected void validate(long groupId, String name) throws PortalException {
		validate(0, groupId, name);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiNodeLocalServiceImpl.class);

	private final Map<String, WikiImporter> _wikiImporters =
		new HashMap<String, WikiImporter>();

}