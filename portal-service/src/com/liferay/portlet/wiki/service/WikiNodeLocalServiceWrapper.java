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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link WikiNodeLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiNodeLocalService
 * @generated
 */
public class WikiNodeLocalServiceWrapper implements WikiNodeLocalService,
	ServiceWrapper<WikiNodeLocalService> {
	public WikiNodeLocalServiceWrapper(
		WikiNodeLocalService wikiNodeLocalService) {
		_wikiNodeLocalService = wikiNodeLocalService;
	}

	/**
	* Adds the wiki node to the database. Also notifies the appropriate model listeners.
	*
	* @param wikiNode the wiki node
	* @return the wiki node that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode addWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.addWikiNode(wikiNode);
	}

	/**
	* Creates a new wiki node with the primary key. Does not add the wiki node to the database.
	*
	* @param nodeId the primary key for the new wiki node
	* @return the new wiki node
	*/
	public com.liferay.portlet.wiki.model.WikiNode createWikiNode(long nodeId) {
		return _wikiNodeLocalService.createWikiNode(nodeId);
	}

	/**
	* Deletes the wiki node with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param nodeId the primary key of the wiki node
	* @throws PortalException if a wiki node with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteWikiNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteWikiNode(nodeId);
	}

	/**
	* Deletes the wiki node from the database. Also notifies the appropriate model listeners.
	*
	* @param wikiNode the wiki node
	* @throws SystemException if a system exception occurred
	*/
	public void deleteWikiNode(com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteWikiNode(wikiNode);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the wiki node with the primary key.
	*
	* @param nodeId the primary key of the wiki node
	* @return the wiki node
	* @throws PortalException if a wiki node with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode getWikiNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getWikiNode(nodeId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the wiki node with the UUID in the group.
	*
	* @param uuid the UUID of wiki node
	* @param groupId the group id of the wiki node
	* @return the wiki node
	* @throws PortalException if a wiki node with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode getWikiNodeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getWikiNodeByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the wiki nodes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getWikiNodes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getWikiNodes(start, end);
	}

	/**
	* Returns the number of wiki nodes.
	*
	* @return the number of wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public int getWikiNodesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getWikiNodesCount();
	}

	/**
	* Updates the wiki node in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wikiNode the wiki node
	* @return the wiki node that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode updateWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.updateWikiNode(wikiNode);
	}

	/**
	* Updates the wiki node in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wikiNode the wiki node
	* @param merge whether to merge the wiki node with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the wiki node that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode updateWikiNode(
		com.liferay.portlet.wiki.model.WikiNode wikiNode, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.updateWikiNode(wikiNode, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _wikiNodeLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_wikiNodeLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.wiki.model.WikiNode addDefaultNode(long userId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.addDefaultNode(userId, serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(long userId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.addNode(userId, name, description,
			serviceContext);
	}

	public void addNodeResources(long nodeId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.addNodeResources(nodeId, addGroupPermissions,
			addGuestPermissions);
	}

	public void addNodeResources(com.liferay.portlet.wiki.model.WikiNode node,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.addNodeResources(node, addGroupPermissions,
			addGuestPermissions);
	}

	public void addNodeResources(long nodeId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.addNodeResources(nodeId, groupPermissions,
			guestPermissions);
	}

	public void addNodeResources(com.liferay.portlet.wiki.model.WikiNode node,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.addNodeResources(node, groupPermissions,
			guestPermissions);
	}

	public void deleteNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteNode(nodeId);
	}

	public void deleteNode(com.liferay.portlet.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteNode(node);
	}

	public void deleteNodes(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.deleteNodes(groupId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getCompanyNodes(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getCompanyNodes(companyId, start, end);
	}

	public int getCompanyNodesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getCompanyNodesCount(companyId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNode(nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(long groupId,
		java.lang.String nodeName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNode(groupId, nodeName);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getNodes(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNodes(groupId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> getNodes(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNodes(groupId, start, end);
	}

	public int getNodesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.getNodesCount(groupId);
	}

	public void importPages(long userId, long nodeId,
		java.lang.String importer, java.io.InputStream[] inputStreams,
		java.util.Map<java.lang.String, java.lang.String[]> options)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.importPages(userId, nodeId, importer,
			inputStreams, options);
	}

	public void subscribeNode(long userId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.subscribeNode(userId, nodeId);
	}

	public void unsubscribeNode(long userId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeLocalService.unsubscribeNode(userId, nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode updateNode(long nodeId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeLocalService.updateNode(nodeId, name, description,
			serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public WikiNodeLocalService getWrappedWikiNodeLocalService() {
		return _wikiNodeLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {
		_wikiNodeLocalService = wikiNodeLocalService;
	}

	public WikiNodeLocalService getWrappedService() {
		return _wikiNodeLocalService;
	}

	public void setWrappedService(WikiNodeLocalService wikiNodeLocalService) {
		_wikiNodeLocalService = wikiNodeLocalService;
	}

	private WikiNodeLocalService _wikiNodeLocalService;
}