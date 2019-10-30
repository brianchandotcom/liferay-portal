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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for WikiNode. This utility wraps
 * <code>com.liferay.wiki.service.impl.WikiNodeLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodeLocalService
 * @generated
 */
public class WikiNodeLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.wiki.service.impl.WikiNodeLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiNodeLocalServiceUtil} to access the wiki node local service. Add custom service methods to <code>com.liferay.wiki.service.impl.WikiNodeLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.wiki.model.WikiNode addDefaultNode(
			long userId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDefaultNode(userId, serviceContext);
	}

	public static com.liferay.wiki.model.WikiNode addNode(
			long userId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addNode(userId, name, description, serviceContext);
	}

	public static void addNodeResources(
			long nodeId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addNodeResources(
			nodeId, addGroupPermissions, addGuestPermissions);
	}

	public static void addNodeResources(
			com.liferay.wiki.model.WikiNode node, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addNodeResources(
			node, addGroupPermissions, addGuestPermissions);
	}

	public static void addNodeResources(
			com.liferay.wiki.model.WikiNode node,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addNodeResources(node, modelPermissions);
	}

	/**
	 * Adds the wiki node to the database. Also notifies the appropriate model listeners.
	 *
	 * @param wikiNode the wiki node
	 * @return the wiki node that was added
	 */
	public static com.liferay.wiki.model.WikiNode addWikiNode(
		com.liferay.wiki.model.WikiNode wikiNode) {

		return getService().addWikiNode(wikiNode);
	}

	/**
	 * Creates a new wiki node with the primary key. Does not add the wiki node to the database.
	 *
	 * @param nodeId the primary key for the new wiki node
	 * @return the new wiki node
	 */
	public static com.liferay.wiki.model.WikiNode createWikiNode(long nodeId) {
		return getService().createWikiNode(nodeId);
	}

	public static void deleteNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteNode(nodeId);
	}

	public static void deleteNode(com.liferay.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteNode(node);
	}

	public static void deleteNodes(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteNodes(groupId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the wiki node with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param nodeId the primary key of the wiki node
	 * @return the wiki node that was removed
	 * @throws PortalException if a wiki node with the primary key could not be found
	 */
	public static com.liferay.wiki.model.WikiNode deleteWikiNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteWikiNode(nodeId);
	}

	/**
	 * Deletes the wiki node from the database. Also notifies the appropriate model listeners.
	 *
	 * @param wikiNode the wiki node
	 * @return the wiki node that was removed
	 */
	public static com.liferay.wiki.model.WikiNode deleteWikiNode(
		com.liferay.wiki.model.WikiNode wikiNode) {

		return getService().deleteWikiNode(wikiNode);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.wiki.model.impl.WikiNodeModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.wiki.model.impl.WikiNodeModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.wiki.model.WikiNode fetchNode(
		long groupId, String name) {

		return getService().fetchNode(groupId, name);
	}

	public static com.liferay.wiki.model.WikiNode fetchNodeByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchNodeByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.wiki.model.WikiNode fetchWikiNode(long nodeId) {
		return getService().fetchWikiNode(nodeId);
	}

	/**
	 * Returns the wiki node matching the UUID and group.
	 *
	 * @param uuid the wiki node's UUID
	 * @param groupId the primary key of the group
	 * @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	 */
	public static com.liferay.wiki.model.WikiNode fetchWikiNodeByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchWikiNodeByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode>
		getCompanyNodes(long companyId, int start, int end) {

		return getService().getCompanyNodes(companyId, start, end);
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode>
		getCompanyNodes(long companyId, int status, int start, int end) {

		return getService().getCompanyNodes(companyId, status, start, end);
	}

	public static int getCompanyNodesCount(long companyId) {
		return getService().getCompanyNodesCount(companyId);
	}

	public static int getCompanyNodesCount(long companyId, int status) {
		return getService().getCompanyNodesCount(companyId, status);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static com.liferay.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNode(nodeId);
	}

	public static com.liferay.wiki.model.WikiNode getNode(
			long groupId, String nodeName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNode(groupId, nodeName);
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
			long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNodes(groupId);
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
			long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNodes(groupId, status);
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
			long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNodes(groupId, start, end);
	}

	public static java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
			long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNodes(groupId, status, start, end);
	}

	public static int getNodesCount(long groupId) {
		return getService().getNodesCount(groupId);
	}

	public static int getNodesCount(long groupId, int status) {
		return getService().getNodesCount(groupId, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the wiki node with the primary key.
	 *
	 * @param nodeId the primary key of the wiki node
	 * @return the wiki node
	 * @throws PortalException if a wiki node with the primary key could not be found
	 */
	public static com.liferay.wiki.model.WikiNode getWikiNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWikiNode(nodeId);
	}

	/**
	 * Returns the wiki node matching the UUID and group.
	 *
	 * @param uuid the wiki node's UUID
	 * @param groupId the primary key of the group
	 * @return the matching wiki node
	 * @throws PortalException if a matching wiki node could not be found
	 */
	public static com.liferay.wiki.model.WikiNode getWikiNodeByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWikiNodeByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the wiki nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.wiki.model.impl.WikiNodeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki nodes
	 * @param end the upper bound of the range of wiki nodes (not inclusive)
	 * @return the range of wiki nodes
	 */
	public static java.util.List<com.liferay.wiki.model.WikiNode> getWikiNodes(
		int start, int end) {

		return getService().getWikiNodes(start, end);
	}

	/**
	 * Returns all the wiki nodes matching the UUID and company.
	 *
	 * @param uuid the UUID of the wiki nodes
	 * @param companyId the primary key of the company
	 * @return the matching wiki nodes, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.wiki.model.WikiNode>
		getWikiNodesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getWikiNodesByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.wiki.model.WikiNode>
		getWikiNodesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.wiki.model.WikiNode> orderByComparator) {

		return getService().getWikiNodesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of wiki nodes.
	 *
	 * @return the number of wiki nodes
	 */
	public static int getWikiNodesCount() {
		return getService().getWikiNodesCount();
	}

	public static void importPages(
			long userId, long nodeId, String importer,
			java.io.InputStream[] inputStreams,
			java.util.Map<String, String[]> options)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().importPages(
			userId, nodeId, importer, inputStreams, options);
	}

	public static com.liferay.wiki.model.WikiNode moveNodeToTrash(
			long userId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveNodeToTrash(userId, nodeId);
	}

	public static com.liferay.wiki.model.WikiNode moveNodeToTrash(
			long userId, com.liferay.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveNodeToTrash(userId, node);
	}

	public static void restoreNodeFromTrash(
			long userId, com.liferay.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().restoreNodeFromTrash(userId, node);
	}

	public static void subscribeNode(long userId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().subscribeNode(userId, nodeId);
	}

	public static void unsubscribeNode(long userId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsubscribeNode(userId, nodeId);
	}

	public static com.liferay.wiki.model.WikiNode updateNode(
			long nodeId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateNode(
			nodeId, name, description, serviceContext);
	}

	public static com.liferay.wiki.model.WikiNode updateStatus(
			long userId, com.liferay.wiki.model.WikiNode node, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(userId, node, status, serviceContext);
	}

	/**
	 * Updates the wiki node in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param wikiNode the wiki node
	 * @return the wiki node that was updated
	 */
	public static com.liferay.wiki.model.WikiNode updateWikiNode(
		com.liferay.wiki.model.WikiNode wikiNode) {

		return getService().updateWikiNode(wikiNode);
	}

	public static WikiNodeLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<WikiNodeLocalService, WikiNodeLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(WikiNodeLocalService.class);

		ServiceTracker<WikiNodeLocalService, WikiNodeLocalService>
			serviceTracker =
				new ServiceTracker<WikiNodeLocalService, WikiNodeLocalService>(
					bundle.getBundleContext(), WikiNodeLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}