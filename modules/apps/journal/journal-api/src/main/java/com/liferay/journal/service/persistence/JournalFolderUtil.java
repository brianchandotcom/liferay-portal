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

package com.liferay.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.model.JournalFolder;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the journal folder service. This utility wraps {@link com.liferay.journal.service.persistence.impl.JournalFolderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderPersistence
 * @see com.liferay.journal.service.persistence.impl.JournalFolderPersistenceImpl
 * @generated
 */
@ProviderType
public class JournalFolderUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(JournalFolder journalFolder) {
		getPersistence().clearCache(journalFolder);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<JournalFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static JournalFolder update(JournalFolder journalFolder) {
		return getPersistence().update(journalFolder);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static JournalFolder update(JournalFolder journalFolder,
		ServiceContext serviceContext) {
		return getPersistence().update(journalFolder, serviceContext);
	}

	/**
	* Returns all the journal folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal folders
	*/
	public static List<JournalFolder> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<JournalFolder> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<JournalFolder> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal folders
	*/
	public static List<JournalFolder> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByUuid_First(java.lang.String uuid,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByUuid_Last(java.lang.String uuid,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where uuid = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] findByUuid_PrevAndNext(long folderId,
		java.lang.String uuid,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByUuid_PrevAndNext(folderId, uuid, orderByComparator);
	}

	/**
	* Removes all the journal folders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of journal folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal folders
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.journal.NoSuchFolderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the journal folder where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal folder that was removed
	*/
	public static JournalFolder removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of journal folders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal folders
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the journal folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching journal folders
	*/
	public static List<JournalFolder> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<JournalFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<JournalFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal folders
	*/
	public static List<JournalFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] findByUuid_C_PrevAndNext(long folderId,
		java.lang.String uuid, long companyId,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(folderId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the journal folders where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of journal folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching journal folders
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the journal folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal folders
	*/
	public static List<JournalFolder> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static List<JournalFolder> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static List<JournalFolder> findByGroupId(long groupId, int start,
		int end, OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal folders
	*/
	public static List<JournalFolder> findByGroupId(long groupId, int start,
		int end, OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByGroupId_First(long groupId,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByGroupId_First(long groupId,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByGroupId_Last(long groupId,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByGroupId_Last(long groupId,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] findByGroupId_PrevAndNext(long folderId,
		long groupId, OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(folderId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal folders that the user has permission to view
	*/
	public static List<JournalFolder> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

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
	public static List<JournalFolder> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

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
	public static List<JournalFolder> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] filterFindByGroupId_PrevAndNext(
		long folderId, long groupId,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(folderId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the journal folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of journal folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal folders
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal folders that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the journal folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching journal folders
	*/
	public static List<JournalFolder> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

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
	public static List<JournalFolder> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

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
	public static List<JournalFolder> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal folders
	*/
	public static List<JournalFolder> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByCompanyId_First(long companyId,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByCompanyId_First(long companyId,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByCompanyId_Last(long companyId,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByCompanyId_Last(long companyId,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where companyId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId, OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(folderId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the journal folders where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of journal folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching journal folders
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching journal folders
	*/
	public static List<JournalFolder> findByG_P(long groupId,
		long parentFolderId) {
		return getPersistence().findByG_P(groupId, parentFolderId);
	}

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
	public static List<JournalFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end) {
		return getPersistence().findByG_P(groupId, parentFolderId, start, end);
	}

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
	public static List<JournalFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .findByG_P(groupId, parentFolderId, start, end,
			orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal folders
	*/
	public static List<JournalFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P(groupId, parentFolderId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByG_P_First(long groupId,
		long parentFolderId, OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_First(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_P_First(long groupId,
		long parentFolderId, OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_First(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByG_P_Last(long groupId,
		long parentFolderId, OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_Last(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_P_Last(long groupId,
		long parentFolderId, OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_Last(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] findByG_P_PrevAndNext(long folderId,
		long groupId, long parentFolderId,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_PrevAndNext(folderId, groupId, parentFolderId,
			orderByComparator);
	}

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching journal folders that the user has permission to view
	*/
	public static List<JournalFolder> filterFindByG_P(long groupId,
		long parentFolderId) {
		return getPersistence().filterFindByG_P(groupId, parentFolderId);
	}

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
	public static List<JournalFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end) {
		return getPersistence()
				   .filterFindByG_P(groupId, parentFolderId, start, end);
	}

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
	public static List<JournalFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P(groupId, parentFolderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] filterFindByG_P_PrevAndNext(long folderId,
		long groupId, long parentFolderId,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(folderId, groupId,
			parentFolderId, orderByComparator);
	}

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	*/
	public static void removeByG_P(long groupId, long parentFolderId) {
		getPersistence().removeByG_P(groupId, parentFolderId);
	}

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching journal folders
	*/
	public static int countByG_P(long groupId, long parentFolderId) {
		return getPersistence().countByG_P(groupId, parentFolderId);
	}

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching journal folders that the user has permission to view
	*/
	public static int filterCountByG_P(long groupId, long parentFolderId) {
		return getPersistence().filterCountByG_P(groupId, parentFolderId);
	}

	/**
	* Returns the journal folder where groupId = &#63; and name = &#63; or throws a {@link com.liferay.journal.NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByG_N(long groupId, java.lang.String name)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_N(long groupId, java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_N(long groupId, java.lang.String name,
		boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the journal folder where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the journal folder that was removed
	*/
	public static JournalFolder removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of journal folders where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching journal folders
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Returns all the journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching journal folders
	*/
	public static List<JournalFolder> findByC_NotS(long companyId, int status) {
		return getPersistence().findByC_NotS(companyId, status);
	}

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
	public static List<JournalFolder> findByC_NotS(long companyId, int status,
		int start, int end) {
		return getPersistence().findByC_NotS(companyId, status, start, end);
	}

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
	public static List<JournalFolder> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .findByC_NotS(companyId, status, start, end,
			orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal folders
	*/
	public static List<JournalFolder> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_NotS(companyId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByC_NotS_First(long companyId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByC_NotS_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByC_NotS_First(long companyId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByC_NotS_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByC_NotS_Last(long companyId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByC_NotS_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByC_NotS_Last(long companyId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByC_NotS_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] findByC_NotS_PrevAndNext(long folderId,
		long companyId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByC_NotS_PrevAndNext(folderId, companyId, status,
			orderByComparator);
	}

	/**
	* Removes all the journal folders where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public static void removeByC_NotS(long companyId, int status) {
		getPersistence().removeByC_NotS(companyId, status);
	}

	/**
	* Returns the number of journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public static int countByC_NotS(long companyId, int status) {
		return getPersistence().countByC_NotS(companyId, status);
	}

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link com.liferay.journal.NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByG_P_N(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().findByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_P_N(long groupId, long parentFolderId,
		java.lang.String name) {
		return getPersistence().fetchByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_P_N(long groupId, long parentFolderId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_P_N(groupId, parentFolderId, name,
			retrieveFromCache);
	}

	/**
	* Removes the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the journal folder that was removed
	*/
	public static JournalFolder removeByG_P_N(long groupId,
		long parentFolderId, java.lang.String name)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().removeByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the number of matching journal folders
	*/
	public static int countByG_P_N(long groupId, long parentFolderId,
		java.lang.String name) {
		return getPersistence().countByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders
	*/
	public static List<JournalFolder> findByG_P_S(long groupId,
		long parentFolderId, int status) {
		return getPersistence().findByG_P_S(groupId, parentFolderId, status);
	}

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
	public static List<JournalFolder> findByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end) {
		return getPersistence()
				   .findByG_P_S(groupId, parentFolderId, status, start, end);
	}

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
	public static List<JournalFolder> findByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .findByG_P_S(groupId, parentFolderId, status, start, end,
			orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal folders
	*/
	public static List<JournalFolder> findByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_S(groupId, parentFolderId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByG_P_S_First(long groupId,
		long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_S_First(groupId, parentFolderId, status,
			orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_P_S_First(long groupId,
		long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_S_First(groupId, parentFolderId, status,
			orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByG_P_S_Last(long groupId,
		long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_S_Last(groupId, parentFolderId, status,
			orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_P_S_Last(long groupId,
		long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_S_Last(groupId, parentFolderId, status,
			orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] findByG_P_S_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_S_PrevAndNext(folderId, groupId, parentFolderId,
			status, orderByComparator);
	}

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders that the user has permission to view
	*/
	public static List<JournalFolder> filterFindByG_P_S(long groupId,
		long parentFolderId, int status) {
		return getPersistence()
				   .filterFindByG_P_S(groupId, parentFolderId, status);
	}

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
	public static List<JournalFolder> filterFindByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_P_S(groupId, parentFolderId, status, start,
			end);
	}

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
	public static List<JournalFolder> filterFindByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P_S(groupId, parentFolderId, status, start,
			end, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] filterFindByG_P_S_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .filterFindByG_P_S_PrevAndNext(folderId, groupId,
			parentFolderId, status, orderByComparator);
	}

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public static void removeByG_P_S(long groupId, long parentFolderId,
		int status) {
		getPersistence().removeByG_P_S(groupId, parentFolderId, status);
	}

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public static int countByG_P_S(long groupId, long parentFolderId, int status) {
		return getPersistence().countByG_P_S(groupId, parentFolderId, status);
	}

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders that the user has permission to view
	*/
	public static int filterCountByG_P_S(long groupId, long parentFolderId,
		int status) {
		return getPersistence()
				   .filterCountByG_P_S(groupId, parentFolderId, status);
	}

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders
	*/
	public static List<JournalFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status) {
		return getPersistence().findByG_P_NotS(groupId, parentFolderId, status);
	}

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
	public static List<JournalFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end) {
		return getPersistence()
				   .findByG_P_NotS(groupId, parentFolderId, status, start, end);
	}

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
	public static List<JournalFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .findByG_P_NotS(groupId, parentFolderId, status, start, end,
			orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal folders
	*/
	public static List<JournalFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_NotS(groupId, parentFolderId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByG_P_NotS_First(long groupId,
		long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_NotS_First(groupId, parentFolderId, status,
			orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_P_NotS_First(long groupId,
		long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_NotS_First(groupId, parentFolderId, status,
			orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByG_P_NotS_Last(long groupId,
		long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_NotS_Last(groupId, parentFolderId, status,
			orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public static JournalFolder fetchByG_P_NotS_Last(long groupId,
		long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_NotS_Last(groupId, parentFolderId, status,
			orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] findByG_P_NotS_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_NotS_PrevAndNext(folderId, groupId,
			parentFolderId, status, orderByComparator);
	}

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders that the user has permission to view
	*/
	public static List<JournalFolder> filterFindByG_P_NotS(long groupId,
		long parentFolderId, int status) {
		return getPersistence()
				   .filterFindByG_P_NotS(groupId, parentFolderId, status);
	}

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
	public static List<JournalFolder> filterFindByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_P_NotS(groupId, parentFolderId, status,
			start, end);
	}

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
	public static List<JournalFolder> filterFindByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P_NotS(groupId, parentFolderId, status,
			start, end, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder[] filterFindByG_P_NotS_PrevAndNext(
		long folderId, long groupId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .filterFindByG_P_NotS_PrevAndNext(folderId, groupId,
			parentFolderId, status, orderByComparator);
	}

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public static void removeByG_P_NotS(long groupId, long parentFolderId,
		int status) {
		getPersistence().removeByG_P_NotS(groupId, parentFolderId, status);
	}

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public static int countByG_P_NotS(long groupId, long parentFolderId,
		int status) {
		return getPersistence().countByG_P_NotS(groupId, parentFolderId, status);
	}

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders that the user has permission to view
	*/
	public static int filterCountByG_P_NotS(long groupId, long parentFolderId,
		int status) {
		return getPersistence()
				   .filterCountByG_P_NotS(groupId, parentFolderId, status);
	}

	/**
	* Returns all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders
	*/
	public static List<JournalFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status) {
		return getPersistence()
				   .findByF_C_P_NotS(folderId, companyId, parentFolderId, status);
	}

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
	public static List<JournalFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end) {
		return getPersistence()
				   .findByF_C_P_NotS(folderId, companyId, parentFolderId,
			status, start, end);
	}

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
	public static List<JournalFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .findByF_C_P_NotS(folderId, companyId, parentFolderId,
			status, start, end, orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal folders
	*/
	public static List<JournalFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByF_C_P_NotS(folderId, companyId, parentFolderId,
			status, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByF_C_P_NotS_First(long folderId,
		long companyId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByF_C_P_NotS_First(folderId, companyId, parentFolderId,
			status, orderByComparator);
	}

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
	public static JournalFolder fetchByF_C_P_NotS_First(long folderId,
		long companyId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByF_C_P_NotS_First(folderId, companyId,
			parentFolderId, status, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public static JournalFolder findByF_C_P_NotS_Last(long folderId,
		long companyId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence()
				   .findByF_C_P_NotS_Last(folderId, companyId, parentFolderId,
			status, orderByComparator);
	}

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
	public static JournalFolder fetchByF_C_P_NotS_Last(long folderId,
		long companyId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence()
				   .fetchByF_C_P_NotS_Last(folderId, companyId, parentFolderId,
			status, orderByComparator);
	}

	/**
	* Removes all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public static void removeByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status) {
		getPersistence()
			.removeByF_C_P_NotS(folderId, companyId, parentFolderId, status);
	}

	/**
	* Returns the number of journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public static int countByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status) {
		return getPersistence()
				   .countByF_C_P_NotS(folderId, companyId, parentFolderId,
			status);
	}

	/**
	* Caches the journal folder in the entity cache if it is enabled.
	*
	* @param journalFolder the journal folder
	*/
	public static void cacheResult(JournalFolder journalFolder) {
		getPersistence().cacheResult(journalFolder);
	}

	/**
	* Caches the journal folders in the entity cache if it is enabled.
	*
	* @param journalFolders the journal folders
	*/
	public static void cacheResult(List<JournalFolder> journalFolders) {
		getPersistence().cacheResult(journalFolders);
	}

	/**
	* Creates a new journal folder with the primary key. Does not add the journal folder to the database.
	*
	* @param folderId the primary key for the new journal folder
	* @return the new journal folder
	*/
	public static JournalFolder create(long folderId) {
		return getPersistence().create(folderId);
	}

	/**
	* Removes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder that was removed
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder remove(long folderId)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().remove(folderId);
	}

	public static JournalFolder updateImpl(JournalFolder journalFolder) {
		return getPersistence().updateImpl(journalFolder);
	}

	/**
	* Returns the journal folder with the primary key or throws a {@link com.liferay.journal.NoSuchFolderException} if it could not be found.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder
	* @throws com.liferay.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public static JournalFolder findByPrimaryKey(long folderId)
		throws com.liferay.journal.exception.NoSuchFolderException {
		return getPersistence().findByPrimaryKey(folderId);
	}

	/**
	* Returns the journal folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder, or <code>null</code> if a journal folder with the primary key could not be found
	*/
	public static JournalFolder fetchByPrimaryKey(long folderId) {
		return getPersistence().fetchByPrimaryKey(folderId);
	}

	public static java.util.Map<java.io.Serializable, JournalFolder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the journal folders.
	*
	* @return the journal folders
	*/
	public static List<JournalFolder> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<JournalFolder> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<JournalFolder> findAll(int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of journal folders
	*/
	public static List<JournalFolder> findAll(int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the journal folders from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of journal folders.
	*
	* @return the number of journal folders
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static JournalFolderPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<JournalFolderPersistence, JournalFolderPersistence> _serviceTracker =
		ServiceTrackerFactory.open(JournalFolderPersistence.class);
}