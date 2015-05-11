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

package com.liferay.portlet.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalFolder;

/**
 * The persistence interface for the journal folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.journal.service.persistence.impl.JournalFolderPersistenceImpl
 * @see JournalFolderUtil
 * @generated
 */
@ProviderType
public interface JournalFolderPersistence extends BasePersistence<JournalFolder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalFolderUtil} to access the journal folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the journal folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal folders
	*/
	public java.util.List<JournalFolder> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the journal folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the journal folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where uuid = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] findByUuid_PrevAndNext(long folderId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of journal folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal folders
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the journal folder where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal folder that was removed
	*/
	public JournalFolder removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the number of journal folders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal folders
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the journal folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching journal folders
	*/
	public java.util.List<JournalFolder> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the journal folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] findByUuid_C_PrevAndNext(long folderId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of journal folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching journal folders
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the journal folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal folders
	*/
	public java.util.List<JournalFolder> findByGroupId(long groupId);

	/**
	* Returns a range of all the journal folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] findByGroupId_PrevAndNext(long folderId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] filterFindByGroupId_PrevAndNext(long folderId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of journal folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal folders
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal folders that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the journal folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching journal folders
	*/
	public java.util.List<JournalFolder> findByCompanyId(long companyId);

	/**
	* Returns a range of all the journal folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the journal folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where companyId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of journal folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching journal folders
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching journal folders
	*/
	public java.util.List<JournalFolder> findByG_P(long groupId,
		long parentFolderId);

	/**
	* Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByG_P_First(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_P_First(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByG_P_Last(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_P_Last(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByG_P(long groupId,
		long parentFolderId);

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] filterFindByG_P_PrevAndNext(long folderId,
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	*/
	public void removeByG_P(long groupId, long parentFolderId);

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching journal folders
	*/
	public int countByG_P(long groupId, long parentFolderId);

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching journal folders that the user has permission to view
	*/
	public int filterCountByG_P(long groupId, long parentFolderId);

	/**
	* Returns the journal folder where groupId = &#63; and name = &#63; or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByG_N(long groupId, java.lang.String name)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_N(long groupId, java.lang.String name);

	/**
	* Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_N(long groupId, java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the journal folder where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the journal folder that was removed
	*/
	public JournalFolder removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the number of journal folders where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching journal folders
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching journal folders
	*/
	public java.util.List<JournalFolder> findByC_NotS(long companyId, int status);

	/**
	* Returns a range of all the journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByC_NotS(long companyId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByC_NotS(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] findByC_NotS_PrevAndNext(long folderId,
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_NotS(long companyId, int status);

	/**
	* Returns the number of journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public int countByC_NotS(long companyId, int status);

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByG_P_N(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_P_N(long groupId, long parentFolderId,
		java.lang.String name);

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_P_N(long groupId, long parentFolderId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the journal folder that was removed
	*/
	public JournalFolder removeByG_P_N(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the number of matching journal folders
	*/
	public int countByG_P_N(long groupId, long parentFolderId,
		java.lang.String name);

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders
	*/
	public java.util.List<JournalFolder> findByG_P_S(long groupId,
		long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByG_P_S_First(long groupId, long parentFolderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_P_S_First(long groupId, long parentFolderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByG_P_S_Last(long groupId, long parentFolderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_P_S_Last(long groupId, long parentFolderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] findByG_P_S_PrevAndNext(long folderId, long groupId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByG_P_S(long groupId,
		long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] filterFindByG_P_S_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByG_P_S(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public int countByG_P_S(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders that the user has permission to view
	*/
	public int filterCountByG_P_S(long groupId, long parentFolderId, int status);

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders
	*/
	public java.util.List<JournalFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByG_P_NotS_First(long groupId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_P_NotS_First(long groupId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByG_P_NotS_Last(long groupId, long parentFolderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByG_P_NotS_Last(long groupId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] findByG_P_NotS_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByG_P_NotS(long groupId,
		long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	*/
	public java.util.List<JournalFolder> filterFindByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder[] filterFindByG_P_NotS_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByG_P_NotS(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public int countByG_P_NotS(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders that the user has permission to view
	*/
	public int filterCountByG_P_NotS(long groupId, long parentFolderId,
		int status);

	/**
	* Returns all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders
	*/
	public java.util.List<JournalFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<JournalFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByF_C_P_NotS_First(long folderId, long companyId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByF_C_P_NotS_First(long folderId, long companyId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws NoSuchFolderException if a matching journal folder could not be found
	*/
	public JournalFolder findByF_C_P_NotS_Last(long folderId, long companyId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public JournalFolder fetchByF_C_P_NotS_Last(long folderId, long companyId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Removes all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status);

	/**
	* Returns the number of journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public int countByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status);

	/**
	* Caches the journal folder in the entity cache if it is enabled.
	*
	* @param journalFolder the journal folder
	*/
	public void cacheResult(JournalFolder journalFolder);

	/**
	* Caches the journal folders in the entity cache if it is enabled.
	*
	* @param journalFolders the journal folders
	*/
	public void cacheResult(java.util.List<JournalFolder> journalFolders);

	/**
	* Creates a new journal folder with the primary key. Does not add the journal folder to the database.
	*
	* @param folderId the primary key for the new journal folder
	* @return the new journal folder
	*/
	public JournalFolder create(long folderId);

	/**
	* Removes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder that was removed
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder remove(long folderId)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	public JournalFolder updateImpl(JournalFolder journalFolder);

	/**
	* Returns the journal folder with the primary key or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder
	* @throws NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public JournalFolder findByPrimaryKey(long folderId)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the journal folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder, or <code>null</code> if a journal folder with the primary key could not be found
	*/
	public JournalFolder fetchByPrimaryKey(long folderId);

	@Override
	public java.util.Map<java.io.Serializable, JournalFolder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the journal folders.
	*
	* @return the journal folders
	*/
	public java.util.List<JournalFolder> findAll();

	/**
	* Returns a range of all the journal folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of journal folders
	*/
	public java.util.List<JournalFolder> findAll(int start, int end);

	/**
	* Returns an ordered range of all the journal folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal folders
	*/
	public java.util.List<JournalFolder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFolder> orderByComparator);

	/**
	* Removes all the journal folders from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of journal folders.
	*
	* @return the number of journal folders
	*/
	public int countAll();
}