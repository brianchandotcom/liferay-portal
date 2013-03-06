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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.announcements.model.AnnouncementsEntry;

import java.util.List;

/**
 * The persistence utility for the announcements entry service. This utility wraps {@link AnnouncementsEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryPersistence
 * @see AnnouncementsEntryPersistenceImpl
 * @generated
 */
public class AnnouncementsEntryUtil {
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
	public static void clearCache(AnnouncementsEntry announcementsEntry) {
		getPersistence().clearCache(announcementsEntry);
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
	public static List<AnnouncementsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnnouncementsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AnnouncementsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AnnouncementsEntry update(
		AnnouncementsEntry announcementsEntry) throws SystemException {
		return getPersistence().update(announcementsEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AnnouncementsEntry update(
		AnnouncementsEntry announcementsEntry, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(announcementsEntry, serviceContext);
	}

	/**
	* Returns all the announcements entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the announcements entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where uuid = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(entryId, uuid, orderByComparator);
	}

	/**
	* Returns all the announcements entries that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	* Returns a range of all the announcements entries that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where uuid = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] filterFindByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .filterFindByUuid_PrevAndNext(entryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the announcements entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of announcements entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of announcements entries that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	* Returns all the announcements entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the announcements entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] findByUuid_C_PrevAndNext(
		long entryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(entryId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Returns all the announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] filterFindByUuid_C_PrevAndNext(
		long entryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .filterFindByUuid_C_PrevAndNext(entryId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the announcements entries where uuid = &#63; and companyId = &#63; from the database.
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
	* Returns the number of announcements entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the announcements entries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the announcements entries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where userId = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] findByUserId_PrevAndNext(
		long entryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByUserId_PrevAndNext(entryId, userId, orderByComparator);
	}

	/**
	* Returns all the announcements entries that the user has permission to view where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUserId(userId);
	}

	/**
	* Returns a range of all the announcements entries that the user has permission to view where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where userId = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] filterFindByUserId_PrevAndNext(
		long entryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .filterFindByUserId_PrevAndNext(entryId, userId,
			orderByComparator);
	}

	/**
	* Removes all the announcements entries where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of announcements entries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of announcements entries that the user has permission to view where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByUserId(userId);
	}

	/**
	* Returns all the announcements entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the announcements entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] findByC_C_PrevAndNext(
		long entryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_PrevAndNext(entryId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] filterFindByC_C_PrevAndNext(
		long entryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .filterFindByC_C_PrevAndNext(entryId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the announcements entries where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of announcements entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByC_C(classNameId, classPK);
	}

	/**
	* Returns all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @return the matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C_A(
		long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C_A(classNameId, classPK, alert);
	}

	/**
	* Returns a range of all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C_A(
		long classNameId, long classPK, boolean alert, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_A(classNameId, classPK, alert, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C_A(
		long classNameId, long classPK, boolean alert, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_A(classNameId, classPK, alert, start, end,
			orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByC_C_A_First(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_A_First(classNameId, classPK, alert,
			orderByComparator);
	}

	/**
	* Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByC_C_A_First(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_A_First(classNameId, classPK, alert,
			orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByC_C_A_Last(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_A_Last(classNameId, classPK, alert,
			orderByComparator);
	}

	/**
	* Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByC_C_A_Last(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_A_Last(classNameId, classPK, alert,
			orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] findByC_C_A_PrevAndNext(
		long entryId, long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_A_PrevAndNext(entryId, classNameId, classPK,
			alert, orderByComparator);
	}

	/**
	* Returns all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @return the matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByC_C_A(
		long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByC_C_A(classNameId, classPK, alert);
	}

	/**
	* Returns a range of all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByC_C_A(
		long classNameId, long classPK, boolean alert, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByC_C_A(classNameId, classPK, alert, start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> filterFindByC_C_A(
		long classNameId, long classPK, boolean alert, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByC_C_A(classNameId, classPK, alert, start, end,
			orderByComparator);
	}

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry[] filterFindByC_C_A_PrevAndNext(
		long entryId, long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence()
				   .filterFindByC_C_A_PrevAndNext(entryId, classNameId,
			classPK, alert, orderByComparator);
	}

	/**
	* Removes all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C_A(long classNameId, long classPK,
		boolean alert)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C_A(classNameId, classPK, alert);
	}

	/**
	* Returns the number of announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @return the number of matching announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C_A(long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_A(classNameId, classPK, alert);
	}

	/**
	* Returns the number of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @return the number of matching announcements entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByC_C_A(long classNameId, long classPK,
		boolean alert)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByC_C_A(classNameId, classPK, alert);
	}

	/**
	* Caches the announcements entry in the entity cache if it is enabled.
	*
	* @param announcementsEntry the announcements entry
	*/
	public static void cacheResult(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry) {
		getPersistence().cacheResult(announcementsEntry);
	}

	/**
	* Caches the announcements entries in the entity cache if it is enabled.
	*
	* @param announcementsEntries the announcements entries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> announcementsEntries) {
		getPersistence().cacheResult(announcementsEntries);
	}

	/**
	* Creates a new announcements entry with the primary key. Does not add the announcements entry to the database.
	*
	* @param entryId the primary key for the new announcements entry
	* @return the new announcements entry
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the announcements entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry that was removed
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(announcementsEntry);
	}

	/**
	* Returns the announcements entry with the primary key or throws a {@link com.liferay.portlet.announcements.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry
	* @throws com.liferay.portlet.announcements.NoSuchEntryException if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Returns the announcements entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry, or <code>null</code> if a announcements entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	/**
	* Returns all the announcements entries.
	*
	* @return the announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the announcements entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the announcements entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the announcements entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of announcements entries.
	*
	* @return the number of announcements entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AnnouncementsEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AnnouncementsEntryPersistence)PortalBeanLocatorUtil.locate(AnnouncementsEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(AnnouncementsEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(AnnouncementsEntryPersistence persistence) {
	}

	private static AnnouncementsEntryPersistence _persistence;
}