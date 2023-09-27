/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.service;

import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for DLTrash. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface DLTrashService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLTrashServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the dl trash remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link DLTrashServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Moves the file entry from a trashed folder to the new folder.
	 *
	 * @param fileEntryId the primary key of the file entry
	 * @param newFolderId the primary key of the new folder
	 * @param serviceContext the service context to be applied
	 * @return the file entry
	 */
	public FileEntry moveFileEntryFromTrash(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Moves the file entry with the primary key to the trash portlet.
	 *
	 * @param fileEntryId the primary key of the file entry
	 * @return the file entry
	 */
	public FileEntry moveFileEntryToTrash(long fileEntryId)
		throws PortalException;

	/**
	 * Moves the file shortcut from a trashed folder to the new folder.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 * @param newFolderId the primary key of the new folder
	 * @param serviceContext the service context to be applied
	 * @return the file shortcut
	 */
	public FileShortcut moveFileShortcutFromTrash(
			long fileShortcutId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Moves the file shortcut with the primary key to the trash portlet.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 * @return the file shortcut
	 */
	public FileShortcut moveFileShortcutToTrash(long fileShortcutId)
		throws PortalException;

	/**
	 * Moves the folder with the primary key from the trash portlet to the new
	 * parent folder with the primary key.
	 *
	 * @param folderId the primary key of the folder
	 * @param parentFolderId the primary key of the new parent folder
	 * @param serviceContext the service context to be applied
	 * @return the file entry
	 */
	public Folder moveFolderFromTrash(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Moves the folder with the primary key to the trash portlet.
	 *
	 * @param folderId the primary key of the folder
	 * @return the file entry
	 */
	public Folder moveFolderToTrash(long folderId) throws PortalException;

	/**
	 * Restores the file entry with the primary key from the trash portlet.
	 *
	 * @param fileEntryId the primary key of the file entry
	 */
	public void restoreFileEntryFromTrash(long fileEntryId)
		throws PortalException;

	/**
	 * Restores the file shortcut with the primary key from the trash portlet.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 */
	public void restoreFileShortcutFromTrash(long fileShortcutId)
		throws PortalException;

	/**
	 * Restores the folder with the primary key from the trash portlet.
	 *
	 * @param folderId the primary key of the folder
	 */
	public void restoreFolderFromTrash(long folderId) throws PortalException;

}