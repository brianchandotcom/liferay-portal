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

package com.liferay.journal.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.service.JournalFolderService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for JournalFolder. This utility wraps
 * {@link com.liferay.journal.service.impl.JournalFolderServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=journal", "json.web.service.context.path=JournalFolder"}, service = JournalFolderJsonService.class)
@JSONWebService
@ProviderType
public class JournalFolderJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.journal.service.impl.JournalFolderServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.journal.model.JournalFolder addFolder(long groupId,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addFolder(groupId, parentFolderId, name, description,
			serviceContext);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteFolder(folderId);
	}

	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteFolder(folderId, includeTrashedEntries);
	}

	public com.liferay.journal.model.JournalFolder fetchFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.fetchFolder(folderId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long[] groupIds, long folderId, int restrictionType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getDDMStructures(groupIds, folderId, restrictionType);
	}

	public com.liferay.journal.model.JournalFolder getFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getFolder(folderId);
	}

	public java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getFolderIds(groupId, folderId);
	}

	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId) {
		return _service.getFolders(groupId);
	}

	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId) {
		return _service.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int start, int end) {
		return _service.getFolders(groupId, parentFolderId, start, end);
	}

	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int status) {
		return _service.getFolders(groupId, parentFolderId, status);
	}

	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end) {
		return _service.getFolders(groupId, parentFolderId, status, start, end);
	}

	public java.util.List<java.lang.Object> getFoldersAndArticles(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc) {
		return _service.getFoldersAndArticles(groupId, folderId, start, end, obc);
	}

	public java.util.List<java.lang.Object> getFoldersAndArticles(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc) {
		return _service.getFoldersAndArticles(groupId, folderId, status, start,
			end, obc);
	}

	public int getFoldersAndArticlesCount(long groupId, long folderId) {
		return _service.getFoldersAndArticlesCount(groupId, folderId);
	}

	public int getFoldersAndArticlesCount(long groupId, long folderId,
		int status) {
		return _service.getFoldersAndArticlesCount(groupId, folderId, status);
	}

	public int getFoldersAndArticlesCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status) {
		return _service.getFoldersAndArticlesCount(groupId, folderIds, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId) {
		return _service.getFoldersCount(groupId, parentFolderId);
	}

	public int getFoldersCount(long groupId, long parentFolderId, int status) {
		return _service.getFoldersCount(groupId, parentFolderId, status);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#getSubfolderIds(java.util.List, long, long, boolean)}
	*/
	@Deprecated
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId) {
		_service.getSubfolderIds(folderIds, groupId, folderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId, boolean recurse) {
		_service.getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	public java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse) {
		return _service.getSubfolderIds(groupId, folderId, recurse);
	}

	public com.liferay.journal.model.JournalFolder moveFolder(long folderId,
		long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveFolder(folderId, parentFolderId, serviceContext);
	}

	public com.liferay.journal.model.JournalFolder moveFolderFromTrash(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveFolderFromTrash(folderId, parentFolderId,
			serviceContext);
	}

	public com.liferay.journal.model.JournalFolder moveFolderToTrash(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveFolderToTrash(folderId);
	}

	public void restoreFolderFromTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.restoreFolderFromTrash(folderId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public void subscribe(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.subscribe(groupId, folderId);
	}

	public void unsubscribe(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.unsubscribe(groupId, folderId);
	}

	public com.liferay.journal.model.JournalFolder updateFolder(long groupId,
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long[] ddmStructureIds,
		int restrictionType, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateFolder(groupId, folderId, parentFolderId, name,
			description, ddmStructureIds, restrictionType,
			mergeWithParentFolder, serviceContext);
	}

	public com.liferay.journal.model.JournalFolder updateFolder(long groupId,
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateFolder(groupId, folderId, parentFolderId, name,
			description, mergeWithParentFolder, serviceContext);
	}

	@Reference
	protected void setService(JournalFolderService service) {
		_service = service;
	}

	private JournalFolderService _service;
}