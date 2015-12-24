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
 * Provides a wrapper for {@link WikiNodeService}.
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodeService
 * @generated
 */
@ProviderType
public class WikiNodeServiceWrapper implements WikiNodeService,
	ServiceWrapper<WikiNodeService> {
	public WikiNodeServiceWrapper(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	@Override
	public com.liferay.wiki.model.WikiNode addNode(java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeService.addNode(name, description, serviceContext);
	}

	@Override
	public void deleteNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeService.deleteNode(nodeId);
	}

	@Override
	public com.liferay.wiki.model.WikiNode getNode(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeService.getNode(groupId, name);
	}

	@Override
	public com.liferay.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeService.getNode(nodeId);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeService.getNodes(groupId);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int start, int end) {
		return _wikiNodeService.getNodes(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeService.getNodes(groupId, status);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int status, int start, int end) {
		return _wikiNodeService.getNodes(groupId, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiNode> getNodes(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> obc) {
		return _wikiNodeService.getNodes(groupId, status, start, end, obc);
	}

	@Override
	public int getNodesCount(long groupId) {
		return _wikiNodeService.getNodesCount(groupId);
	}

	@Override
	public int getNodesCount(long groupId, int status) {
		return _wikiNodeService.getNodesCount(groupId, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _wikiNodeService.getOSGiServiceIdentifier();
	}

	@Override
	public void importPages(long nodeId, java.lang.String importer,
		java.io.InputStream[] inputStreams,
		java.util.Map<java.lang.String, java.lang.String[]> options)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeService.importPages(nodeId, importer, inputStreams, options);
	}

	@Override
	public com.liferay.wiki.model.WikiNode moveNodeToTrash(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeService.moveNodeToTrash(nodeId);
	}

	@Override
	public void restoreNodeFromTrash(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeService.restoreNodeFromTrash(nodeId);
	}

	@Override
	public void subscribeNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeService.subscribeNode(nodeId);
	}

	@Override
	public void unsubscribeNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiNodeService.unsubscribeNode(nodeId);
	}

	@Override
	public com.liferay.wiki.model.WikiNode updateNode(long nodeId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNodeService.updateNode(nodeId, name, description,
			serviceContext);
	}

	@Override
	public WikiNodeService getWrappedService() {
		return _wikiNodeService;
	}

	@Override
	public void setWrappedService(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	private WikiNodeService _wikiNodeService;
}