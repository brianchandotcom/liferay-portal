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

package com.liferay.wiki.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import com.liferay.wiki.service.WikiNodeService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for WikiNode. This utility wraps
 * {@link com.liferay.wiki.service.impl.WikiNodeServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodeService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=wiki", "json.web.service.context.path=WikiNode"}, service = WikiNodeJsonService.class)
@JSONWebService
@ProviderType
public class WikiNodeJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.wiki.service.impl.WikiNodeServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.wiki.model.WikiNode addNode(java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addNode(name, description, serviceContext);
	}

	public void deleteNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteNode(nodeId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public com.liferay.wiki.model.WikiNode getNode(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getNode(groupId, name);
	}

	public com.liferay.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getNode(nodeId);
	}

	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getNodes(groupId);
	}

	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int start, int end) {
		return _service.getNodes(groupId, start, end);
	}

	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getNodes(groupId, status);
	}

	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int status, int start, int end) {
		return _service.getNodes(groupId, status, start, end);
	}

	public int getNodesCount(long groupId) {
		return _service.getNodesCount(groupId);
	}

	public int getNodesCount(long groupId, int status) {
		return _service.getNodesCount(groupId, status);
	}

	public void importPages(long nodeId, java.lang.String importer,
		java.io.InputStream[] inputStreams,
		java.util.Map<java.lang.String, java.lang.String[]> options)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.importPages(nodeId, importer, inputStreams, options);
	}

	public com.liferay.wiki.model.WikiNode moveNodeToTrash(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveNodeToTrash(nodeId);
	}

	public void restoreNodeFromTrash(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.restoreNodeFromTrash(nodeId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public void subscribeNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.subscribeNode(nodeId);
	}

	public void unsubscribeNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.unsubscribeNode(nodeId);
	}

	public com.liferay.wiki.model.WikiNode updateNode(long nodeId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateNode(nodeId, name, description, serviceContext);
	}

	@Reference
	protected void setService(WikiNodeService service) {
		_service = service;
	}

	private WikiNodeService _service;
}