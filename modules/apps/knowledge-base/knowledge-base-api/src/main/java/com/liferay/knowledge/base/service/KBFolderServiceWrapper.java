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

package com.liferay.knowledge.base.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KBFolderService}.
 *
 * @author Brian Wing Shun Chan
 * @see KBFolderService
 * @generated
 */
@ProviderType
public class KBFolderServiceWrapper implements KBFolderService,
	ServiceWrapper<KBFolderService> {
	public KBFolderServiceWrapper(KBFolderService kbFolderService) {
		_kbFolderService = kbFolderService;
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder addKBFolder(long groupId,
		long parentResourceClassNameId, long parentResourcePrimKey,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.addKBFolder(groupId, parentResourceClassNameId,
			parentResourcePrimKey, name, description, serviceContext);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder deleteKBFolder(
		long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.deleteKBFolder(kbFolderId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder fetchFirstChildKBFolder(
		long groupId, long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.fetchFirstChildKBFolder(groupId, kbFolderId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder fetchFirstChildKBFolder(
		long groupId, long kbFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.knowledge.base.model.KBFolder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.fetchFirstChildKBFolder(groupId, kbFolderId, obc);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder fetchKBFolder(
		long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.fetchKBFolder(kbFolderId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder fetchKBFolderByUrlTitle(
		long groupId, long parentKbFolderId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.fetchKBFolderByUrlTitle(groupId,
			parentKbFolderId, urlTitle);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder getKBFolder(
		long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.getKBFolder(kbFolderId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder getKBFolderByUrlTitle(
		long groupId, long parentKbFolderId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.getKBFolderByUrlTitle(groupId,
			parentKbFolderId, urlTitle);
	}

	/**
	* @deprecated As of 1.1.0, replaced by {@link
	#updateKBFolder(long, long, long, String, String,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.knowledge.base.model.KBFolder updateKBFolder(
		long parentResourceClassNameId, long parentResourcePrimKey,
		long kbFolderId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.updateKBFolder(parentResourceClassNameId,
			parentResourcePrimKey, kbFolderId, name, description);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder updateKBFolder(
		long parentResourceClassNameId, long parentResourcePrimKey,
		long kbFolderId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.updateKBFolder(parentResourceClassNameId,
			parentResourcePrimKey, kbFolderId, name, description, serviceContext);
	}

	@Override
	public int getKBFoldersAndKBArticlesCount(long groupId,
		long parentResourcePrimKey, int status) {
		return _kbFolderService.getKBFoldersAndKBArticlesCount(groupId,
			parentResourcePrimKey, status);
	}

	@Override
	public int getKBFoldersCount(long groupId, long parentKBFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.getKBFoldersCount(groupId, parentKBFolderId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _kbFolderService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBFolder> getKBFolders(
		long groupId, long parentKBFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbFolderService.getKBFolders(groupId, parentKBFolderId, start,
			end);
	}

	@Override
	public java.util.List<java.lang.Object> getKBFoldersAndKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> orderByComparator) {
		return _kbFolderService.getKBFoldersAndKBArticles(groupId,
			parentResourcePrimKey, status, start, end, orderByComparator);
	}

	@Override
	public void moveKBFolder(long kbFolderId, long parentKBFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbFolderService.moveKBFolder(kbFolderId, parentKBFolderId);
	}

	@Override
	public KBFolderService getWrappedService() {
		return _kbFolderService;
	}

	@Override
	public void setWrappedService(KBFolderService kbFolderService) {
		_kbFolderService = kbFolderService;
	}

	private KBFolderService _kbFolderService;
}