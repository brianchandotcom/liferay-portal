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

package com.liferay.portlet.journal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link JournalFolderService}.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderService
 * @generated
 */
@ProviderType
public class JournalFolderServiceWrapper implements JournalFolderService,
	ServiceWrapper<JournalFolderService> {
	public JournalFolderServiceWrapper(
		JournalFolderService journalFolderService) {
		_journalFolderService = journalFolderService;
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder addFolder(
		long groupId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.addFolder(groupId, parentFolderId, name,
			description, serviceContext);
	}

	@Override
	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_journalFolderService.deleteFolder(folderId);
	}

	@Override
	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		_journalFolderService.deleteFolder(folderId, includeTrashedEntries);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder fetchFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.fetchFolder(folderId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _journalFolderService.getBeanIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long[] groupIds, long folderId, int restrictionType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.getDDMStructures(groupIds, folderId,
			restrictionType);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.getFolder(folderId);
	}

	@Override
	public java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.getFolderIds(groupId, folderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId) {
		return _journalFolderService.getFolders(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId) {
		return _journalFolderService.getFolders(groupId, parentFolderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int start, int end) {
		return _journalFolderService.getFolders(groupId, parentFolderId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int status) {
		return _journalFolderService.getFolders(groupId, parentFolderId, status);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end) {
		return _journalFolderService.getFolders(groupId, parentFolderId,
			status, start, end);
	}

	@Override
	public java.util.List<java.lang.Object> getFoldersAndArticles(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc) {
		return _journalFolderService.getFoldersAndArticles(groupId, folderId,
			start, end, obc);
	}

	@Override
	public java.util.List<java.lang.Object> getFoldersAndArticles(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc) {
		return _journalFolderService.getFoldersAndArticles(groupId, folderId,
			status, start, end, obc);
	}

	@Override
	public int getFoldersAndArticlesCount(long groupId, long folderId) {
		return _journalFolderService.getFoldersAndArticlesCount(groupId,
			folderId);
	}

	@Override
	public int getFoldersAndArticlesCount(long groupId, long folderId,
		int status) {
		return _journalFolderService.getFoldersAndArticlesCount(groupId,
			folderId, status);
	}

	@Override
	public int getFoldersAndArticlesCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status) {
		return _journalFolderService.getFoldersAndArticlesCount(groupId,
			folderIds, status);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId) {
		return _journalFolderService.getFoldersCount(groupId, parentFolderId);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId, int status) {
		return _journalFolderService.getFoldersCount(groupId, parentFolderId,
			status);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getSubfolderIds(List, long,
	long, boolean)}
	*/
	@Deprecated
	@Override
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId) {
		_journalFolderService.getSubfolderIds(folderIds, groupId, folderId);
	}

	@Override
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId, boolean recurse) {
		_journalFolderService.getSubfolderIds(folderIds, groupId, folderId,
			recurse);
	}

	@Override
	public java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse) {
		return _journalFolderService.getSubfolderIds(groupId, folderId, recurse);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.moveFolder(folderId, parentFolderId,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder moveFolderFromTrash(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.moveFolderFromTrash(folderId,
			parentFolderId, serviceContext);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder moveFolderToTrash(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.moveFolderToTrash(folderId);
	}

	@Override
	public void restoreFolderFromTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_journalFolderService.restoreFolderFromTrash(folderId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_journalFolderService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void subscribe(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_journalFolderService.subscribe(groupId, folderId);
	}

	@Override
	public void unsubscribe(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_journalFolderService.unsubscribe(groupId, folderId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder updateFolder(
		long groupId, long folderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		long[] ddmStructureIds, int restrictionType,
		boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.updateFolder(groupId, folderId,
			parentFolderId, name, description, ddmStructureIds,
			restrictionType, mergeWithParentFolder, serviceContext);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder updateFolder(
		long groupId, long folderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFolderService.updateFolder(groupId, folderId,
			parentFolderId, name, description, mergeWithParentFolder,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public JournalFolderService getWrappedJournalFolderService() {
		return _journalFolderService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedJournalFolderService(
		JournalFolderService journalFolderService) {
		_journalFolderService = journalFolderService;
	}

	@Override
	public JournalFolderService getWrappedService() {
		return _journalFolderService;
	}

	@Override
	public void setWrappedService(JournalFolderService journalFolderService) {
		_journalFolderService = journalFolderService;
	}

	private JournalFolderService _journalFolderService;
}