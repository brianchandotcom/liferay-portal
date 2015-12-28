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

package com.liferay.wiki.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link WikiNodeLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodeLocalService
 * @generated
 */
@ProviderType
public class WikiNodeLocalServiceWrapper implements WikiNodeLocalService,
	ServiceWrapper<WikiNodeLocalService> {
	public WikiNodeLocalServiceWrapper(
		WikiNodeLocalService wikiNodeLocalService) {
		_wikiNodeLocalService = wikiNodeLocalService;
	}

	@Override
	public com.liferay.wiki.model.WikiNode addDefaultNode(long userId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.addDefaultNode(userId, serviceContext);
	}

	@Override
	public com.liferay.wiki.model.WikiNode addNode(long userId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.addNode(userId, name, description,
			serviceContext);
	}

	@Override
	public void addNodeResources(com.liferay.wiki.model.WikiNode node,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.addNodeResources(node, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addNodeResources(com.liferay.wiki.model.WikiNode node,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.addNodeResources(node, groupPermissions,
			guestPermissions);
	}

	@Override
	public void addNodeResources(long nodeId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.addNodeResources(nodeId, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addNodeResources(long nodeId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.addNodeResources(nodeId, groupPermissions,
			guestPermissions);
	}

	/**
	* Adds the wiki node to the database. Also notifies the appropriate model listeners.
	*
	* @param wikiNode the wiki node
	* @return the wiki node that was added
	*/
	@Override
	public com.liferay.wiki.model.WikiNode addWikiNode(
		com.liferay.wiki.model.WikiNode wikiNode) {
		return _wikiNodeLocalService.addWikiNode(wikiNode);
	}

	/**
	* Creates a new wiki node with the primary key. Does not add the wiki node to the database.
	*
	* @param nodeId the primary key for the new wiki node
	* @return the new wiki node
	*/
	@Override
	public com.liferay.wiki.model.WikiNode createWikiNode(long nodeId) {
		return _wikiNodeLocalService.createWikiNode(nodeId);
	}

	@Override
	public void deleteNode(com.liferay.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.deleteNode(node);
	}

	@Override
	public void deleteNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.deleteNode(nodeId);
	}

	@Override
	public void deleteNodes(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.deleteNodes(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the wiki node with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param nodeId the primary key of the wiki node
	* @return the wiki node that was removed
	* @throws PortalException if a wiki node with the primary key could not be found
	*/
	@Override
	public com.liferay.wiki.model.WikiNode deleteWikiNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.deleteWikiNode(nodeId);
	}

	/**
	* Deletes the wiki node from the database. Also notifies the appropriate model listeners.
	*
	* @param wikiNode the wiki node
	* @return the wiki node that was removed
	*/
	@Override
	public com.liferay.wiki.model.WikiNode deleteWikiNode(
		com.liferay.wiki.model.WikiNode wikiNode) {
		return _wikiNodeLocalService.deleteWikiNode(wikiNode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _wikiNodeLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _wikiNodeLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _wikiNodeLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.wiki.model.WikiNode fetchNode(long groupId,
		java.lang.String name) {
		return _wikiNodeLocalService.fetchNode(groupId, name);
	}

	@Override
	public com.liferay.wiki.model.WikiNode fetchNodeByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _wikiNodeLocalService.fetchNodeByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.wiki.model.WikiNode fetchWikiNode(long nodeId) {
		return _wikiNodeLocalService.fetchWikiNode(nodeId);
	}

	/**
	* Returns the wiki node matching the UUID and group.
	*
	* @param uuid the wiki node's UUID
	* @param groupId the primary key of the group
	* @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	@Override
	public com.liferay.wiki.model.WikiNode fetchWikiNodeByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _wikiNodeLocalService.fetchWikiNodeByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _wikiNodeLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getCompanyNodes(
		long companyId, int start, int end) {
		return _wikiNodeLocalService.getCompanyNodes(companyId, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getCompanyNodes(
		long companyId, int status, int start, int end) {
		return _wikiNodeLocalService.getCompanyNodes(companyId, status, start,
			end);
	}

	@Override
	public int getCompanyNodesCount(long companyId) {
		return _wikiNodeLocalService.getCompanyNodesCount(companyId);
	}

	@Override
	public int getCompanyNodesCount(long companyId, int status) {
		return _wikiNodeLocalService.getCompanyNodesCount(companyId, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _wikiNodeLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _wikiNodeLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.wiki.model.WikiNode getNode(long groupId,
		java.lang.String nodeName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.getNode(groupId, nodeName);
	}

	@Override
	public com.liferay.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.getNode(nodeId);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.getNodes(groupId);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.getNodes(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.getNodes(groupId, status);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.getNodes(groupId, status, start, end);
	}

	@Override
	public int getNodesCount(long groupId) {
		return _wikiNodeLocalService.getNodesCount(groupId);
	}

	@Override
	public int getNodesCount(long groupId, int status) {
		return _wikiNodeLocalService.getNodesCount(groupId, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _wikiNodeLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the wiki node with the primary key.
	*
	* @param nodeId the primary key of the wiki node
	* @return the wiki node
	* @throws PortalException if a wiki node with the primary key could not be found
	*/
	@Override
	public com.liferay.wiki.model.WikiNode getWikiNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.getWikiNode(nodeId);
	}

	/**
	* Returns the wiki node matching the UUID and group.
	*
	* @param uuid the wiki node's UUID
	* @param groupId the primary key of the group
	* @return the matching wiki node
	* @throws PortalException if a matching wiki node could not be found
	*/
	@Override
	public com.liferay.wiki.model.WikiNode getWikiNodeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.getWikiNodeByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the wiki nodes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of wiki nodes
	*/
	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getWikiNodes(
		int start, int end) {
		return _wikiNodeLocalService.getWikiNodes(start, end);
	}

	/**
	* Returns all the wiki nodes matching the UUID and company.
	*
	* @param uuid the UUID of the wiki nodes
	* @param companyId the primary key of the company
	* @return the matching wiki nodes, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getWikiNodesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _wikiNodeLocalService.getWikiNodesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of wiki nodes matching the UUID and company.
	*
	* @param uuid the UUID of the wiki nodes
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching wiki nodes, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getWikiNodesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return _wikiNodeLocalService.getWikiNodesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of wiki nodes.
	*
	* @return the number of wiki nodes
	*/
	@Override
	public int getWikiNodesCount() {
		return _wikiNodeLocalService.getWikiNodesCount();
	}

	@Override
	public void importPages(long userId, long nodeId,
		java.lang.String importer, java.io.InputStream[] inputStreams,
		java.util.Map<java.lang.String, java.lang.String[]> options)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.importPages(userId, nodeId, importer,
			inputStreams, options);
	}

	@Override
	public com.liferay.wiki.model.WikiNode moveNodeToTrash(long userId,
		com.liferay.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.moveNodeToTrash(userId, node);
	}

	@Override
	public com.liferay.wiki.model.WikiNode moveNodeToTrash(long userId,
		long nodeId) throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.moveNodeToTrash(userId, nodeId);
	}

	@Override
	public void restoreNodeFromTrash(long userId,
		com.liferay.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.restoreNodeFromTrash(userId, node);
	}

	@Override
	public void subscribeNode(long userId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.subscribeNode(userId, nodeId);
	}

	@Override
	public void unsubscribeNode(long userId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeLocalService.unsubscribeNode(userId, nodeId);
	}

	@Override
	public com.liferay.wiki.model.WikiNode updateNode(long nodeId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.updateNode(nodeId, name, description,
			serviceContext);
	}

	@Override
	public com.liferay.wiki.model.WikiNode updateStatus(long userId,
		com.liferay.wiki.model.WikiNode node, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeLocalService.updateStatus(userId, node, status,
			serviceContext);
	}

	/**
	* Updates the wiki node in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wikiNode the wiki node
	* @return the wiki node that was updated
	*/
	@Override
	public com.liferay.wiki.model.WikiNode updateWikiNode(
		com.liferay.wiki.model.WikiNode wikiNode) {
		return _wikiNodeLocalService.updateWikiNode(wikiNode);
	}

	@Override
	public WikiNodeLocalService getWrappedService() {
		return _wikiNodeLocalService;
	}

	@Override
	public void setWrappedService(WikiNodeLocalService wikiNodeLocalService) {
		_wikiNodeLocalService = wikiNodeLocalService;
	}

	private WikiNodeLocalService _wikiNodeLocalService;
}