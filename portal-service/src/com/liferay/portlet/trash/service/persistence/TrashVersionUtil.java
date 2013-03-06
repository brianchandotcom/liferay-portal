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

package com.liferay.portlet.trash.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.trash.model.TrashVersion;

import java.util.List;

/**
 * The persistence utility for the trash version service. This utility wraps {@link TrashVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashVersionPersistence
 * @see TrashVersionPersistenceImpl
 * @generated
 */
public class TrashVersionUtil {
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
	public static void clearCache(TrashVersion trashVersion) {
		getPersistence().clearCache(trashVersion);
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
	public static List<TrashVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<TrashVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<TrashVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static TrashVersion update(TrashVersion trashVersion)
		throws SystemException {
		return getPersistence().update(trashVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static TrashVersion update(TrashVersion trashVersion,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(trashVersion, serviceContext);
	}

	/**
	* Returns all the trash versions where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByEntryId(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEntryId(entryId);
	}

	/**
	* Returns a range of all the trash versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByEntryId(
		long entryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEntryId(entryId, start, end);
	}

	/**
	* Returns an ordered range of all the trash versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByEntryId(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator);
	}

	/**
	* Returns the first trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion findByEntryId_First(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException {
		return getPersistence().findByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the first trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion fetchByEntryId_First(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the last trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion findByEntryId_Last(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException {
		return getPersistence().findByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the last trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion fetchByEntryId_Last(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the trash versions before and after the current trash version in the ordered set where entryId = &#63;.
	*
	* @param versionId the primary key of the current trash version
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion[] findByEntryId_PrevAndNext(
		long versionId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException {
		return getPersistence()
				   .findByEntryId_PrevAndNext(versionId, entryId,
			orderByComparator);
	}

	/**
	* Removes all the trash versions where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByEntryId(entryId);
	}

	/**
	* Returns the number of trash versions where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByEntryId(entryId);
	}

	/**
	* Returns all the trash versions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the trash versions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the trash versions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the first trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the trash versions before and after the current trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param versionId the primary key of the current trash version
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion[] findByC_C_PrevAndNext(
		long versionId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException {
		return getPersistence()
				   .findByC_C_PrevAndNext(versionId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the trash versions where classNameId = &#63; and classPK = &#63; from the database.
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
	* Returns the number of trash versions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Caches the trash version in the entity cache if it is enabled.
	*
	* @param trashVersion the trash version
	*/
	public static void cacheResult(
		com.liferay.portlet.trash.model.TrashVersion trashVersion) {
		getPersistence().cacheResult(trashVersion);
	}

	/**
	* Caches the trash versions in the entity cache if it is enabled.
	*
	* @param trashVersions the trash versions
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.trash.model.TrashVersion> trashVersions) {
		getPersistence().cacheResult(trashVersions);
	}

	/**
	* Creates a new trash version with the primary key. Does not add the trash version to the database.
	*
	* @param versionId the primary key for the new trash version
	* @return the new trash version
	*/
	public static com.liferay.portlet.trash.model.TrashVersion create(
		long versionId) {
		return getPersistence().create(versionId);
	}

	/**
	* Removes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version that was removed
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion remove(
		long versionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException {
		return getPersistence().remove(versionId);
	}

	public static com.liferay.portlet.trash.model.TrashVersion updateImpl(
		com.liferay.portlet.trash.model.TrashVersion trashVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(trashVersion);
	}

	/**
	* Returns the trash version with the primary key or throws a {@link com.liferay.portlet.trash.NoSuchVersionException} if it could not be found.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion findByPrimaryKey(
		long versionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException {
		return getPersistence().findByPrimaryKey(versionId);
	}

	/**
	* Returns the trash version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version, or <code>null</code> if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashVersion fetchByPrimaryKey(
		long versionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(versionId);
	}

	/**
	* Returns all the trash versions.
	*
	* @return the trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the trash versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the trash versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the trash versions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of trash versions.
	*
	* @return the number of trash versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static TrashVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (TrashVersionPersistence)PortalBeanLocatorUtil.locate(TrashVersionPersistence.class.getName());

			ReferenceRegistry.registerReference(TrashVersionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(TrashVersionPersistence persistence) {
	}

	private static TrashVersionPersistence _persistence;
}