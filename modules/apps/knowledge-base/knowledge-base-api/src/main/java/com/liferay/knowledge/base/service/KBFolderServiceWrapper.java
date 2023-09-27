/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.service;

import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KBFolderService}.
 *
 * @author Brian Wing Shun Chan
 * @see KBFolderService
 * @generated
 */
public class KBFolderServiceWrapper
	implements KBFolderService, ServiceWrapper<KBFolderService> {

	public KBFolderServiceWrapper() {
		this(null);
	}

	public KBFolderServiceWrapper(KBFolderService kbFolderService) {
		_kbFolderService = kbFolderService;
	}

	@Override
	public KBFolder addKBFolder(
			String externalReferenceCode, long groupId,
			long parentResourceClassNameId, long parentResourcePrimKey,
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.addKBFolder(
			externalReferenceCode, groupId, parentResourceClassNameId,
			parentResourcePrimKey, name, description, serviceContext);
	}

	@Override
	public KBFolder deleteKBFolder(long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.deleteKBFolder(kbFolderId);
	}

	@Override
	public KBFolder fetchFirstChildKBFolder(long groupId, long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.fetchFirstChildKBFolder(groupId, kbFolderId);
	}

	@Override
	public KBFolder fetchFirstChildKBFolder(
			long groupId, long kbFolderId,
			com.liferay.portal.kernel.util.OrderByComparator<KBFolder>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.fetchFirstChildKBFolder(
			groupId, kbFolderId, orderByComparator);
	}

	@Override
	public KBFolder fetchKBFolder(long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.fetchKBFolder(kbFolderId);
	}

	@Override
	public KBFolder fetchKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.fetchKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);
	}

	@Override
	public KBFolder getKBFolder(long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.getKBFolder(kbFolderId);
	}

	@Override
	public KBFolder getKBFolderByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.getKBFolderByExternalReferenceCode(
			groupId, externalReferenceCode);
	}

	@Override
	public KBFolder getKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.getKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);
	}

	@Override
	public java.util.List<KBFolder> getKBFolders(
			long groupId, long parentKBFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.getKBFolders(
			groupId, parentKBFolderId, start, end);
	}

	@Override
	public java.util.List<Object> getKBFoldersAndKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> orderByComparator) {

		return _kbFolderService.getKBFoldersAndKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	@Override
	public int getKBFoldersAndKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return _kbFolderService.getKBFoldersAndKBArticlesCount(
			groupId, parentResourcePrimKey, status);
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
	public String getOSGiServiceIdentifier() {
		return _kbFolderService.getOSGiServiceIdentifier();
	}

	@Override
	public void moveKBFolder(long kbFolderId, long parentKBFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbFolderService.moveKBFolder(kbFolderId, parentKBFolderId);
	}

	@Override
	public KBFolder updateKBFolder(
			long parentResourceClassNameId, long parentResourcePrimKey,
			long kbFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderService.updateKBFolder(
			parentResourceClassNameId, parentResourcePrimKey, kbFolderId, name,
			description, serviceContext);
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