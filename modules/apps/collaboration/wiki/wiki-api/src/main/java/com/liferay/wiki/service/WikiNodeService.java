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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.wiki.model.WikiNode;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service interface for WikiNode. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodeServiceUtil
 * @see com.liferay.wiki.service.base.WikiNodeServiceBaseImpl
 * @see com.liferay.wiki.service.impl.WikiNodeServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=wiki", "json.web.service.context.path=WikiNode"}, service = WikiNodeService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface WikiNodeService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiNodeServiceUtil} to access the wiki node remote service. Add custom service methods to {@link com.liferay.wiki.service.impl.WikiNodeServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public WikiNode addNode(java.lang.String name,
		java.lang.String description, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiNode getNode(long groupId, java.lang.String name)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiNode getNode(long nodeId) throws PortalException;

	public WikiNode moveNodeToTrash(long nodeId) throws PortalException;

	public WikiNode updateNode(long nodeId, java.lang.String name,
		java.lang.String description, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getNodesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getNodesCount(long groupId, int status);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiNode> getNodes(long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiNode> getNodes(long groupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiNode> getNodes(long groupId, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiNode> getNodes(long groupId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiNode> getNodes(long groupId, int status, int start,
		int end, OrderByComparator<WikiNode> obc);

	public void deleteNode(long nodeId) throws PortalException;

	public void importPages(long nodeId, java.lang.String importer,
		InputStream[] inputStreams,
		Map<java.lang.String, java.lang.String[]> options)
		throws PortalException;

	public void restoreNodeFromTrash(long nodeId) throws PortalException;

	public void subscribeNode(long nodeId) throws PortalException;

	public void unsubscribeNode(long nodeId) throws PortalException;
}