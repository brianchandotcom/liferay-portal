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

package com.liferay.wiki.service.impl;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.exception.RestoreEntryException;
import com.liferay.trash.exception.TrashEntryException;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.exception.DuplicateNodeNameException;
import com.liferay.wiki.exception.NodeNameException;
import com.liferay.wiki.importer.WikiImporter;
import com.liferay.wiki.internal.util.WikiCacheThreadLocal;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.base.WikiNodeLocalServiceBaseImpl;

import java.io.InputStream;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the local service for accessing, adding, deleting, importing,
 * subscription handling of, trash handling of, and updating wiki nodes.
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Raymond Augé
 */
@Component(
	property = "model.class.name=com.liferay.wiki.model.WikiNode",
	service = AopService.class
)
public class WikiNodeLocalServiceImpl extends WikiNodeLocalServiceBaseImpl {

	@Activate
	public void activate(BundleContext bundleContext) {
		_wikiImporterServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, WikiImporter.class, "importer");

		_portalCache = _multiVMPool.getPortalCache(
			WikiPageDisplay.class.getName());
	}

	@Override
	public WikiNode addDefaultNode(long userId, ServiceContext serviceContext)
		throws PortalException {

		return addNode(
			userId, _wikiGroupServiceConfiguration.initialNodeName(),
			StringPool.BLANK, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public WikiNode addNode(
			long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Node

		User user = userLocalService.getUser(userId);

		long groupId = serviceContext.getScopeGroupId();

		validate(groupId, name);

		long nodeId = counterLocalService.increment();

		WikiNode node = wikiNodePersistence.create(nodeId);

		node.setUuid(serviceContext.getUuid());
		node.setGroupId(groupId);
		node.setCompanyId(user.getCompanyId());
		node.setUserId(user.getUserId());
		node.setUserName(user.getFullName());
		node.setName(name);
		node.setDescription(description);

		try {
			node = wikiNodePersistence.update(node);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Add failed, fetch {groupId=", groupId, ", name=", name,
						"}"));
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
			addNodeResources(node, serviceContext.getModelPermissions());
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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #addNodeResources(WikiNode, ModelPermissions)}
	 */
	@Deprecated
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
			WikiNode node, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			node.getCompanyId(), node.getGroupId(), node.getUserId(),
			WikiNode.class.getName(), node.getNodeId(), modelPermissions);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #addNodeResources(WikiNode, ModelPermissions)}
	 */
	@Deprecated
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
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteNode(WikiNode node) throws PortalException {

		// Pages

		boolean clearCache = WikiCacheThreadLocal.isClearCache();

		try {
			WikiCacheThreadLocal.setClearCache(false);

			_wikiPageLocalService.deletePages(node.getNodeId());
		}
		finally {
			WikiCacheThreadLocal.setClearCache(clearCache);

			_portalCache.removeAll();
		}

		// Node

		wikiNodePersistence.remove(node);

		// Resources

		resourceLocalService.deleteResource(
			node.getCompanyId(), WikiNode.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, node.getNodeId());

		// Attachments

		long folderId = node.getAttachmentsFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			_portletFileRepository.deletePortletFolder(folderId);
		}

		// Subscriptions

		_subscriptionLocalService.deleteSubscriptions(
			node.getCompanyId(), WikiNode.class.getName(), node.getNodeId());

		// Indexer

		Indexer<WikiNode> indexer = _indexerRegistry.nullSafeGetIndexer(
			WikiNode.class);

		indexer.delete(node);

		if (node.isInTrash()) {
			node.setName(_trashHelper.getOriginalTitle(node.getName()));

			// Trash

			_trashEntryLocalService.deleteEntry(
				WikiNode.class.getName(), node.getNodeId());
		}
	}

	@Override
	public void deleteNodes(long groupId) throws PortalException {
		List<WikiNode> nodes = wikiNodePersistence.findByGroupId(groupId);

		for (WikiNode node : nodes) {
			wikiNodeLocalService.deleteNode(node);
		}

		_portletFileRepository.deletePortletRepository(
			groupId, WikiConstants.SERVICE_NAME);
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

		WikiImporter wikiImporter = _wikiImporterServiceTrackerMap.getService(
			importer);

		if (wikiImporter == null) {
			throw new SystemException(
				"Unable to instantiate wiki importer with name " + importer);
		}

		boolean notificationsEnabled = NotificationThreadLocal.isEnabled();
		boolean clearCache = WikiCacheThreadLocal.isClearCache();

		try {
			NotificationThreadLocal.setEnabled(false);
			WikiCacheThreadLocal.setClearCache(false);

			wikiImporter.importPages(userId, node, inputStreams, options);
		}
		finally {
			NotificationThreadLocal.setEnabled(notificationsEnabled);
			WikiCacheThreadLocal.setClearCache(clearCache);

			_portalCache.removeAll();
		}
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

		if (node.isInTrash()) {
			throw new TrashEntryException();
		}

		int oldStatus = node.getStatus();

		node = updateStatus(
			userId, node, WorkflowConstants.STATUS_IN_TRASH,
			new ServiceContext());

		// Trash

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("title", node.getName());

		TrashEntry trashEntry = _trashEntryLocalService.addTrashEntry(
			userId, node.getGroupId(), WikiNode.class.getName(),
			node.getNodeId(), node.getUuid(), null, oldStatus, null,
			typeSettingsProperties);

		node.setName(_trashHelper.getTrashTitle(trashEntry.getEntryId()));

		node = wikiNodePersistence.update(node);

		// Pages

		moveDependentsToTrash(node.getNodeId(), trashEntry.getEntryId());

		return node;
	}

	@Override
	public void restoreNodeFromTrash(long userId, WikiNode node)
		throws PortalException {

		// Node

		if (!node.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		node.setName(_trashHelper.getOriginalTitle(node.getName()));

		node = wikiNodePersistence.update(node);

		TrashEntry trashEntry = _trashEntryLocalService.getEntry(
			WikiNode.class.getName(), node.getNodeId());

		updateStatus(
			userId, node, trashEntry.getStatus(), new ServiceContext());

		// Pages

		restoreDependentsFromTrash(userId, node.getNodeId());

		// Trash

		_trashEntryLocalService.deleteEntry(trashEntry);
	}

	@Override
	public void subscribeNode(long userId, long nodeId) throws PortalException {
		WikiNode node = getNode(nodeId);

		_subscriptionLocalService.addSubscription(
			userId, node.getGroupId(), WikiNode.class.getName(), nodeId);
	}

	@Override
	public void unsubscribeNode(long userId, long nodeId)
		throws PortalException {

		_subscriptionLocalService.deleteSubscription(
			userId, WikiNode.class.getName(), nodeId);
	}

	@Override
	public WikiNode updateNode(
			long nodeId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		validate(nodeId, node.getGroupId(), name);

		node.setName(name);
		node.setDescription(description);

		return wikiNodePersistence.update(node);
	}

	@Override
	public WikiNode updateStatus(
			long userId, WikiNode node, int status,
			ServiceContext serviceContext)
		throws PortalException {

		// Node

		User user = userLocalService.getUser(userId);

		node.setStatus(status);
		node.setStatusByUserId(userId);
		node.setStatusByUserName(user.getFullName());
		node.setStatusDate(new Date());

		node = wikiNodePersistence.update(node);

		// Indexer

		Indexer<WikiNode> indexer = _indexerRegistry.nullSafeGetIndexer(
			WikiNode.class);

		indexer.reindex(node);

		return node;
	}

	protected List<WikiNode> addDefaultNode(long groupId)
		throws PortalException {

		Group group = groupLocalService.getGroup(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		WikiNode node = wikiNodeLocalService.addDefaultNode(
			defaultUserId, serviceContext);

		return ListUtil.fromArray(node);
	}

	protected void moveDependentsToTrash(long nodeId, long trashEntryId)
		throws PortalException {

		boolean clearCache = WikiCacheThreadLocal.isClearCache();

		try {
			WikiCacheThreadLocal.setClearCache(false);

			List<WikiPage> pages = wikiPagePersistence.findByN_H(nodeId, true);

			for (WikiPage page : pages) {
				_wikiPageLocalService.moveDependentToTrash(page, trashEntryId);
			}
		}
		finally {
			WikiCacheThreadLocal.setClearCache(clearCache);

			_portalCache.removeAll();
		}
	}

	protected void restoreDependentsFromTrash(long userId, long nodeId)
		throws PortalException {

		List<WikiPage> pages = wikiPagePersistence.findByN_H(nodeId, true);

		for (WikiPage page : pages) {
			if (!page.isInTrashImplicitly()) {
				continue;
			}

			_wikiPageLocalService.restorePageFromTrash(userId, page);
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

		if (Validator.isNumber(name)) {
			throw new NodeNameException("Node name cannot be a number");
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

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<?, ?> _portalCache;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

	@Reference
	private TrashHelper _trashHelper;

	@Reference
	private WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

	private ServiceTrackerMap<String, WikiImporter>
		_wikiImporterServiceTrackerMap;

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

}