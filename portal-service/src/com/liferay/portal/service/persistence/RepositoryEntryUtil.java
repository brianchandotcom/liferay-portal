/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the repository entry service. This utility wraps {@link RepositoryEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntryPersistence
 * @see RepositoryEntryPersistenceImpl
 * @generated
 */
public class RepositoryEntryUtil {
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
	public static void clearCache(RepositoryEntry repositoryEntry) {
		getPersistence().clearCache(repositoryEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RepositoryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RepositoryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RepositoryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static RepositoryEntry update(RepositoryEntry repositoryEntry)
		throws SystemException {
		return getPersistence().update(repositoryEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static RepositoryEntry update(RepositoryEntry repositoryEntry,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(repositoryEntry, serviceContext);
	}

	/**
	* Returns all the repository entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the repository entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the repository entries before and after the current repository entry in the ordered set where uuid = &#63;.
	*
	* @param repositoryEntryId the primary key of the current repository entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next repository entry
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry[] findByUuid_PrevAndNext(
		long repositoryEntryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(repositoryEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the repository entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of repository entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the repository entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portal.NoSuchRepositoryEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching repository entry
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the repository entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the repository entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of repository entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry[] findByUuid_C_PrevAndNext(
		long repositoryEntryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(repositoryEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the repository entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of repository entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the repository entries where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findByRepositoryId(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRepositoryId(repositoryId);
	}

	/**
	* Returns a range of all the repository entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findByRepositoryId(
		long repositoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRepositoryId(repositoryId, start, end);
	}

	/**
	* Returns an ordered range of all the repository entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findByRepositoryId(
		long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRepositoryId(repositoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry findByRepositoryId_First(
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRepositoryId_First(repositoryId, orderByComparator);
	}

	/**
	* Returns the first repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByRepositoryId_First(
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByRepositoryId_First(repositoryId, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry findByRepositoryId_Last(
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRepositoryId_Last(repositoryId, orderByComparator);
	}

	/**
	* Returns the last repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByRepositoryId_Last(
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry[] findByRepositoryId_PrevAndNext(
		long repositoryEntryId, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRepositoryId_PrevAndNext(repositoryEntryId,
			repositoryId, orderByComparator);
	}

	/**
	* Removes all the repository entries where repositoryId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByRepositoryId(long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRepositoryId(repositoryId);
	}

	/**
	* Returns the number of repository entries where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the number of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRepositoryId(long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRepositoryId(repositoryId);
	}

	/**
	* Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or throws a {@link com.liferay.portal.NoSuchRepositoryEntryException} if it could not be found.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the matching repository entry
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry findByR_M(
		long repositoryId, java.lang.String mappedId)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_M(repositoryId, mappedId);
	}

	/**
	* Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByR_M(
		long repositoryId, java.lang.String mappedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByR_M(repositoryId, mappedId);
	}

	/**
	* Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByR_M(
		long repositoryId, java.lang.String mappedId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByR_M(repositoryId, mappedId, retrieveFromCache);
	}

	/**
	* Removes the repository entry where repositoryId = &#63; and mappedId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the repository entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry removeByR_M(
		long repositoryId, java.lang.String mappedId)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByR_M(repositoryId, mappedId);
	}

	/**
	* Returns the number of repository entries where repositoryId = &#63; and mappedId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the number of matching repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_M(long repositoryId, java.lang.String mappedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_M(repositoryId, mappedId);
	}

	/**
	* Caches the repository entry in the entity cache if it is enabled.
	*
	* @param repositoryEntry the repository entry
	*/
	public static void cacheResult(
		com.liferay.portal.model.RepositoryEntry repositoryEntry) {
		getPersistence().cacheResult(repositoryEntry);
	}

	/**
	* Caches the repository entries in the entity cache if it is enabled.
	*
	* @param repositoryEntries the repository entries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.RepositoryEntry> repositoryEntries) {
		getPersistence().cacheResult(repositoryEntries);
	}

	/**
	* Creates a new repository entry with the primary key. Does not add the repository entry to the database.
	*
	* @param repositoryEntryId the primary key for the new repository entry
	* @return the new repository entry
	*/
	public static com.liferay.portal.model.RepositoryEntry create(
		long repositoryEntryId) {
		return getPersistence().create(repositoryEntryId);
	}

	/**
	* Removes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry that was removed
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry remove(
		long repositoryEntryId)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(repositoryEntryId);
	}

	public static com.liferay.portal.model.RepositoryEntry updateImpl(
		com.liferay.portal.model.RepositoryEntry repositoryEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(repositoryEntry);
	}

	/**
	* Returns the repository entry with the primary key or throws a {@link com.liferay.portal.NoSuchRepositoryEntryException} if it could not be found.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry
	* @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry findByPrimaryKey(
		long repositoryEntryId)
		throws com.liferay.portal.NoSuchRepositoryEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(repositoryEntryId);
	}

	/**
	* Returns the repository entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry, or <code>null</code> if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry fetchByPrimaryKey(
		long repositoryEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(repositoryEntryId);
	}

	/**
	* Returns all the repository entries.
	*
	* @return the repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the repository entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of repository entries.
	*
	* @return the number of repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
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

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(RepositoryEntryPersistence persistence) {
	}

	private static RepositoryEntryPersistence _persistence;
}