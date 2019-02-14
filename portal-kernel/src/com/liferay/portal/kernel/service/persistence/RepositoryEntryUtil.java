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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the repository entry service. This utility wraps <code>com.liferay.portal.service.persistence.impl.RepositoryEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntryPersistence
 * @generated
 */
@ProviderType
public class RepositoryEntryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(RepositoryEntry repositoryEntry) {
		getPersistence().clearCache(repositoryEntry);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, RepositoryEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RepositoryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RepositoryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RepositoryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RepositoryEntry update(RepositoryEntry repositoryEntry) {
		return getPersistence().update(repositoryEntry);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RepositoryEntry update(RepositoryEntry repositoryEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(repositoryEntry, serviceContext);
	}

	/**
	* Returns all the repository entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching repository entries
	*/
	public static List<RepositoryEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the repository entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of matching repository entries
	*/
	public static List<RepositoryEntry> findByUuid(String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository entries
	*/
	public static List<RepositoryEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching repository entries
	*/
	public static List<RepositoryEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<RepositoryEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public static RepositoryEntry findByUuid_First(String uuid,
		OrderByComparator<RepositoryEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByUuid_First(String uuid,
		OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public static RepositoryEntry findByUuid_Last(String uuid,
		OrderByComparator<RepositoryEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByUuid_Last(String uuid,
		OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the repository entries before and after the current repository entry in the ordered set where uuid = &#63;.
	*
	* @param repositoryEntryId the primary key of the current repository entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next repository entry
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public static RepositoryEntry[] findByUuid_PrevAndNext(
		long repositoryEntryId, String uuid,
		OrderByComparator<RepositoryEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(repositoryEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the repository entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of repository entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching repository entries
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the repository entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRepositoryEntryException</code> if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public static RepositoryEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the repository entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the repository entry that was removed
	*/
	public static RepositoryEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of repository entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching repository entries
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching repository entries
	*/
	public static List<RepositoryEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of matching repository entries
	*/
	public static List<RepositoryEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository entries
	*/
	public static List<RepositoryEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching repository entries
	*/
	public static List<RepositoryEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public static RepositoryEntry findByUuid_C_First(String uuid,
		long companyId, OrderByComparator<RepositoryEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByUuid_C_First(String uuid,
		long companyId, OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public static RepositoryEntry findByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<RepositoryEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the repository entries before and after the current repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param repositoryEntryId the primary key of the current repository entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next repository entry
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public static RepositoryEntry[] findByUuid_C_PrevAndNext(
		long repositoryEntryId, String uuid, long companyId,
		OrderByComparator<RepositoryEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(repositoryEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the repository entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of repository entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching repository entries
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the repository entries where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the matching repository entries
	*/
	public static List<RepositoryEntry> findByRepositoryId(long repositoryId) {
		return getPersistence().findByRepositoryId(repositoryId);
	}

	/**
	* Returns a range of all the repository entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of matching repository entries
	*/
	public static List<RepositoryEntry> findByRepositoryId(long repositoryId,
		int start, int end) {
		return getPersistence().findByRepositoryId(repositoryId, start, end);
	}

	/**
	* Returns an ordered range of all the repository entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository entries
	*/
	public static List<RepositoryEntry> findByRepositoryId(long repositoryId,
		int start, int end, OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence()
				   .findByRepositoryId(repositoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the repository entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching repository entries
	*/
	public static List<RepositoryEntry> findByRepositoryId(long repositoryId,
		int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByRepositoryId(repositoryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public static RepositoryEntry findByRepositoryId_First(long repositoryId,
		OrderByComparator<RepositoryEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence()
				   .findByRepositoryId_First(repositoryId, orderByComparator);
	}

	/**
	* Returns the first repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByRepositoryId_First(long repositoryId,
		OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence()
				   .fetchByRepositoryId_First(repositoryId, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public static RepositoryEntry findByRepositoryId_Last(long repositoryId,
		OrderByComparator<RepositoryEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence()
				   .findByRepositoryId_Last(repositoryId, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByRepositoryId_Last(long repositoryId,
		OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence()
				   .fetchByRepositoryId_Last(repositoryId, orderByComparator);
	}

	/**
	* Returns the repository entries before and after the current repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryEntryId the primary key of the current repository entry
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next repository entry
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public static RepositoryEntry[] findByRepositoryId_PrevAndNext(
		long repositoryEntryId, long repositoryId,
		OrderByComparator<RepositoryEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence()
				   .findByRepositoryId_PrevAndNext(repositoryEntryId,
			repositoryId, orderByComparator);
	}

	/**
	* Removes all the repository entries where repositoryId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	*/
	public static void removeByRepositoryId(long repositoryId) {
		getPersistence().removeByRepositoryId(repositoryId);
	}

	/**
	* Returns the number of repository entries where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the number of matching repository entries
	*/
	public static int countByRepositoryId(long repositoryId) {
		return getPersistence().countByRepositoryId(repositoryId);
	}

	/**
	* Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or throws a <code>NoSuchRepositoryEntryException</code> if it could not be found.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public static RepositoryEntry findByR_M(long repositoryId, String mappedId)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence().findByR_M(repositoryId, mappedId);
	}

	/**
	* Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByR_M(long repositoryId, String mappedId) {
		return getPersistence().fetchByR_M(repositoryId, mappedId);
	}

	/**
	* Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public static RepositoryEntry fetchByR_M(long repositoryId,
		String mappedId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByR_M(repositoryId, mappedId, retrieveFromCache);
	}

	/**
	* Removes the repository entry where repositoryId = &#63; and mappedId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the repository entry that was removed
	*/
	public static RepositoryEntry removeByR_M(long repositoryId, String mappedId)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence().removeByR_M(repositoryId, mappedId);
	}

	/**
	* Returns the number of repository entries where repositoryId = &#63; and mappedId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the number of matching repository entries
	*/
	public static int countByR_M(long repositoryId, String mappedId) {
		return getPersistence().countByR_M(repositoryId, mappedId);
	}

	/**
	* Caches the repository entry in the entity cache if it is enabled.
	*
	* @param repositoryEntry the repository entry
	*/
	public static void cacheResult(RepositoryEntry repositoryEntry) {
		getPersistence().cacheResult(repositoryEntry);
	}

	/**
	* Caches the repository entries in the entity cache if it is enabled.
	*
	* @param repositoryEntries the repository entries
	*/
	public static void cacheResult(List<RepositoryEntry> repositoryEntries) {
		getPersistence().cacheResult(repositoryEntries);
	}

	/**
	* Creates a new repository entry with the primary key. Does not add the repository entry to the database.
	*
	* @param repositoryEntryId the primary key for the new repository entry
	* @return the new repository entry
	*/
	public static RepositoryEntry create(long repositoryEntryId) {
		return getPersistence().create(repositoryEntryId);
	}

	/**
	* Removes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry that was removed
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public static RepositoryEntry remove(long repositoryEntryId)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence().remove(repositoryEntryId);
	}

	public static RepositoryEntry updateImpl(RepositoryEntry repositoryEntry) {
		return getPersistence().updateImpl(repositoryEntry);
	}

	/**
	* Returns the repository entry with the primary key or throws a <code>NoSuchRepositoryEntryException</code> if it could not be found.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public static RepositoryEntry findByPrimaryKey(long repositoryEntryId)
		throws com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException {
		return getPersistence().findByPrimaryKey(repositoryEntryId);
	}

	/**
	* Returns the repository entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry, or <code>null</code> if a repository entry with the primary key could not be found
	*/
	public static RepositoryEntry fetchByPrimaryKey(long repositoryEntryId) {
		return getPersistence().fetchByPrimaryKey(repositoryEntryId);
	}

	/**
	* Returns all the repository entries.
	*
	* @return the repository entries
	*/
	public static List<RepositoryEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of repository entries
	*/
	public static List<RepositoryEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of repository entries
	*/
	public static List<RepositoryEntry> findAll(int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of repository entries
	*/
	public static List<RepositoryEntry> findAll(int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the repository entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of repository entries.
	*
	* @return the number of repository entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RepositoryEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (RepositoryEntryPersistence)PortalBeanLocatorUtil.locate(RepositoryEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(RepositoryEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static RepositoryEntryPersistence _persistence;
}