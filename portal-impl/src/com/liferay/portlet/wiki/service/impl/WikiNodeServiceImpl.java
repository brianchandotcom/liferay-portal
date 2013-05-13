/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.base.WikiNodeServiceBaseImpl;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPermission;

import java.io.InputStream;

import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, importing,
 * subscription handling of, trash handling of, and updating wiki nodes. Its
 * methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 */
public class WikiNodeServiceImpl extends WikiNodeServiceBaseImpl {

	@Override
	public WikiNode addNode(
			String name, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_NODE);

		return wikiNodeLocalService.addNode(
			getUserId(), name, description, serviceContext);
	}

	@Override
	public void deleteNode(long nodeId)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.DELETE);

		wikiNodeLocalService.deleteNode(nodeId);
	}

	@Override
	public WikiNode getNode(long nodeId)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		return wikiNodeLocalService.getNode(nodeId);
	}

	@Override
	public WikiNode getNode(long groupId, String name)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), groupId, name, ActionKeys.VIEW);

		return wikiNodeLocalService.getNode(groupId, name);
	}

	@Override
	public void importPages(
			long nodeId, String importer, InputStream[] inputStreams,
			Map<String, String[]> options)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.IMPORT);

		wikiNodeLocalService.importPages(
			getUserId(), nodeId, importer, inputStreams, options);
	}

	@Override
	public WikiNode moveNodeToTrash(long nodeId)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.DELETE);

		return wikiNodeLocalService.moveNodeToTrash(getUserId(), nodeId);
	}

	@Override
	public void restoreNodeFromTrash(long nodeId)
		throws PortalException, SystemException {

		WikiNode node = wikiNodeLocalService.getNode(nodeId);

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.DELETE);

		wikiNodeLocalService.restoreNodeFromTrash(getUserId(), node);
	}

	@Override
	public void subscribeNode(long nodeId)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.SUBSCRIBE);

		wikiNodeLocalService.subscribeNode(getUserId(), nodeId);
	}

	@Override
	public void unsubscribeNode(long nodeId)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.SUBSCRIBE);

		wikiNodeLocalService.unsubscribeNode(getUserId(), nodeId);
	}

	@Override
	public WikiNode updateNode(
			long nodeId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.UPDATE);

		return wikiNodeLocalService.updateNode(
			nodeId, name, description, serviceContext);
	}

}