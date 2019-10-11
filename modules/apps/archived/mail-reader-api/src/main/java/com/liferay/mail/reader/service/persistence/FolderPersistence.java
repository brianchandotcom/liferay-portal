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

package com.liferay.mail.reader.service.persistence;

import com.liferay.mail.reader.exception.NoSuchFolderException;
import com.liferay.mail.reader.model.Folder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FolderUtil
 * @generated
 */
@ProviderType
public interface FolderPersistence extends BasePersistence<Folder> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FolderUtil} to access the folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the folders where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @return the matching folders
	 */
	public java.util.List<Folder> findByAccountId(long accountId);

	/**
	 * Returns a range of all the folders where accountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param accountId the account ID
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @return the range of matching folders
	 */
	public java.util.List<Folder> findByAccountId(
		long accountId, int start, int end);

	/**
	 * Returns an ordered range of all the folders where accountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param accountId the account ID
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching folders
	 */
	public java.util.List<Folder> findByAccountId(
		long accountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Folder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the folders where accountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param accountId the account ID
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching folders
	 */
	public java.util.List<Folder> findByAccountId(
		long accountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Folder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first folder in the ordered set where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching folder
	 * @throws NoSuchFolderException if a matching folder could not be found
	 */
	public Folder findByAccountId_First(
			long accountId,
			com.liferay.portal.kernel.util.OrderByComparator<Folder>
				orderByComparator)
		throws NoSuchFolderException;

	/**
	 * Returns the first folder in the ordered set where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching folder, or <code>null</code> if a matching folder could not be found
	 */
	public Folder fetchByAccountId_First(
		long accountId,
		com.liferay.portal.kernel.util.OrderByComparator<Folder>
			orderByComparator);

	/**
	 * Returns the last folder in the ordered set where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching folder
	 * @throws NoSuchFolderException if a matching folder could not be found
	 */
	public Folder findByAccountId_Last(
			long accountId,
			com.liferay.portal.kernel.util.OrderByComparator<Folder>
				orderByComparator)
		throws NoSuchFolderException;

	/**
	 * Returns the last folder in the ordered set where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching folder, or <code>null</code> if a matching folder could not be found
	 */
	public Folder fetchByAccountId_Last(
		long accountId,
		com.liferay.portal.kernel.util.OrderByComparator<Folder>
			orderByComparator);

	/**
	 * Returns the folders before and after the current folder in the ordered set where accountId = &#63;.
	 *
	 * @param folderId the primary key of the current folder
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next folder
	 * @throws NoSuchFolderException if a folder with the primary key could not be found
	 */
	public Folder[] findByAccountId_PrevAndNext(
			long folderId, long accountId,
			com.liferay.portal.kernel.util.OrderByComparator<Folder>
				orderByComparator)
		throws NoSuchFolderException;

	/**
	 * Removes all the folders where accountId = &#63; from the database.
	 *
	 * @param accountId the account ID
	 */
	public void removeByAccountId(long accountId);

	/**
	 * Returns the number of folders where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @return the number of matching folders
	 */
	public int countByAccountId(long accountId);

	/**
	 * Returns the folder where accountId = &#63; and fullName = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @return the matching folder
	 * @throws NoSuchFolderException if a matching folder could not be found
	 */
	public Folder findByA_F(long accountId, String fullName)
		throws NoSuchFolderException;

	/**
	 * Returns the folder where accountId = &#63; and fullName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @return the matching folder, or <code>null</code> if a matching folder could not be found
	 */
	public Folder fetchByA_F(long accountId, String fullName);

	/**
	 * Returns the folder where accountId = &#63; and fullName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching folder, or <code>null</code> if a matching folder could not be found
	 */
	public Folder fetchByA_F(
		long accountId, String fullName, boolean useFinderCache);

	/**
	 * Removes the folder where accountId = &#63; and fullName = &#63; from the database.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @return the folder that was removed
	 */
	public Folder removeByA_F(long accountId, String fullName)
		throws NoSuchFolderException;

	/**
	 * Returns the number of folders where accountId = &#63; and fullName = &#63;.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @return the number of matching folders
	 */
	public int countByA_F(long accountId, String fullName);

	/**
	 * Caches the folder in the entity cache if it is enabled.
	 *
	 * @param folder the folder
	 */
	public void cacheResult(Folder folder);

	/**
	 * Caches the folders in the entity cache if it is enabled.
	 *
	 * @param folders the folders
	 */
	public void cacheResult(java.util.List<Folder> folders);

	/**
	 * Creates a new folder with the primary key. Does not add the folder to the database.
	 *
	 * @param folderId the primary key for the new folder
	 * @return the new folder
	 */
	public Folder create(long folderId);

	/**
	 * Removes the folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the folder
	 * @return the folder that was removed
	 * @throws NoSuchFolderException if a folder with the primary key could not be found
	 */
	public Folder remove(long folderId) throws NoSuchFolderException;

	public Folder updateImpl(Folder folder);

	/**
	 * Returns the folder with the primary key or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param folderId the primary key of the folder
	 * @return the folder
	 * @throws NoSuchFolderException if a folder with the primary key could not be found
	 */
	public Folder findByPrimaryKey(long folderId) throws NoSuchFolderException;

	/**
	 * Returns the folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the folder
	 * @return the folder, or <code>null</code> if a folder with the primary key could not be found
	 */
	public Folder fetchByPrimaryKey(long folderId);

	/**
	 * Returns all the folders.
	 *
	 * @return the folders
	 */
	public java.util.List<Folder> findAll();

	/**
	 * Returns a range of all the folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @return the range of folders
	 */
	public java.util.List<Folder> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of folders
	 */
	public java.util.List<Folder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Folder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of folders
	 */
	public java.util.List<Folder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Folder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the folders from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of folders.
	 *
	 * @return the number of folders
	 */
	public int countAll();

}