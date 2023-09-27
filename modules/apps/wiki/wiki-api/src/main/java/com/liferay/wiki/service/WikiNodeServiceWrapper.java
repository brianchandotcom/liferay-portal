/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.wiki.model.WikiNode;

/**
 * Provides a wrapper for {@link WikiNodeService}.
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodeService
 * @generated
 */
public class WikiNodeServiceWrapper
	implements ServiceWrapper<WikiNodeService>, WikiNodeService {

	public WikiNodeServiceWrapper() {
		this(null);
	}

	public WikiNodeServiceWrapper(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #addNode(String,
	 String, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public WikiNode addNode(
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiNodeService.addNode(name, description, serviceContext);
	}

	@Override
	public WikiNode addNode(
			String externalReferenceCode, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiNodeService.addNode(
			externalReferenceCode, name, description, serviceContext);
	}

	@Override
	public void deleteNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiNodeService.deleteNode(nodeId);
	}

	@Override
	public WikiNode getNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiNodeService.getNode(nodeId);
	}

	@Override
	public WikiNode getNode(long groupId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiNodeService.getNode(groupId, name);
	}

	@Override
	public java.util.List<WikiNode> getNodes(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiNodeService.getNodes(groupId);
	}

	@Override
	public java.util.List<WikiNode> getNodes(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiNodeService.getNodes(groupId, status);
	}

	@Override
	public java.util.List<WikiNode> getNodes(long groupId, int start, int end) {
		return _wikiNodeService.getNodes(groupId, start, end);
	}

	@Override
	public java.util.List<WikiNode> getNodes(
		long groupId, int status, int start, int end) {

		return _wikiNodeService.getNodes(groupId, status, start, end);
	}

	@Override
	public java.util.List<WikiNode> getNodes(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiNode>
			orderByComparator) {

		return _wikiNodeService.getNodes(
			groupId, status, start, end, orderByComparator);
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
	public String getOSGiServiceIdentifier() {
		return _wikiNodeService.getOSGiServiceIdentifier();
	}

	@Override
	public WikiNode getWikiNodeByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiNodeService.getWikiNodeByExternalReferenceCode(
			groupId, externalReferenceCode);
	}

	@Override
	public void importPages(
			long nodeId, java.io.InputStream[] inputStreams,
			java.util.Map<String, String[]> options)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiNodeService.importPages(nodeId, inputStreams, options);
	}

	@Override
	public WikiNode moveNodeToTrash(long nodeId)
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
	public WikiNode updateNode(
			long nodeId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiNodeService.updateNode(
			nodeId, name, description, serviceContext);
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