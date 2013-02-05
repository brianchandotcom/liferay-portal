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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLSync;

import java.util.List;

/**
 * The persistence utility for the d l sync service. This utility wraps {@link DLSyncPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncPersistence
 * @see DLSyncPersistenceImpl
 * @generated
 */
public class DLSyncUtil {
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
	public static void clearCache(DLSync dlSync) {
		getPersistence().clearCache(dlSync);
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
	public static List<DLSync> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLSync> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLSync> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DLSync update(DLSync dlSync) throws SystemException {
		return getPersistence().update(dlSync);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DLSync update(DLSync dlSync, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(dlSync, serviceContext);
	}

	/**
	* Returns the d l sync where fileId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchSyncException} if it could not be found.
	*
	* @param fileId the file ID
	* @return the matching d l sync
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a matching d l sync could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync findByFileId(
		long fileId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncException {
		return getPersistence().findByFileId(fileId);
	}

	/**
	* Returns the d l sync where fileId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileId the file ID
	* @return the matching d l sync, or <code>null</code> if a matching d l sync could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync fetchByFileId(
		long fileId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByFileId(fileId);
	}

	/**
	* Returns the d l sync where fileId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileId the file ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d l sync, or <code>null</code> if a matching d l sync could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync fetchByFileId(
		long fileId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByFileId(fileId, retrieveFromCache);
	}

	/**
	* Removes the d l sync where fileId = &#63; from the database.
	*
	* @param fileId the file ID
	* @return the d l sync that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync removeByFileId(
		long fileId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncException {
		return getPersistence().removeByFileId(fileId);
	}

	/**
	* Returns the number of d l syncs where fileId = &#63;.
	*
	* @param fileId the file ID
	* @return the number of matching d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFileId(long fileId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFileId(fileId);
	}

	/**
	* Returns all the d l syncs where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @return the matching d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSync> findByC_M_R(
		long companyId, long modifiedDate, long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_M_R(companyId, modifiedDate, repositoryId);
	}

	/**
	* Returns a range of all the d l syncs where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of d l syncs
	* @param end the upper bound of the range of d l syncs (not inclusive)
	* @return the range of matching d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSync> findByC_M_R(
		long companyId, long modifiedDate, long repositoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_M_R(companyId, modifiedDate, repositoryId, start,
			end);
	}

	/**
	* Returns an ordered range of all the d l syncs where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of d l syncs
	* @param end the upper bound of the range of d l syncs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSync> findByC_M_R(
		long companyId, long modifiedDate, long repositoryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_M_R(companyId, modifiedDate, repositoryId, start,
			end, orderByComparator);
	}

	/**
	* Returns the first d l sync in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a matching d l sync could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync findByC_M_R_First(
		long companyId, long modifiedDate, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncException {
		return getPersistence()
				   .findByC_M_R_First(companyId, modifiedDate, repositoryId,
			orderByComparator);
	}

	/**
	* Returns the first d l sync in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync, or <code>null</code> if a matching d l sync could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync fetchByC_M_R_First(
		long companyId, long modifiedDate, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_M_R_First(companyId, modifiedDate, repositoryId,
			orderByComparator);
	}

	/**
	* Returns the last d l sync in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a matching d l sync could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync findByC_M_R_Last(
		long companyId, long modifiedDate, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncException {
		return getPersistence()
				   .findByC_M_R_Last(companyId, modifiedDate, repositoryId,
			orderByComparator);
	}

	/**
	* Returns the last d l sync in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync, or <code>null</code> if a matching d l sync could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync fetchByC_M_R_Last(
		long companyId, long modifiedDate, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_M_R_Last(companyId, modifiedDate, repositoryId,
			orderByComparator);
	}

	/**
	* Returns the d l syncs before and after the current d l sync in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63;.
	*
	* @param syncId the primary key of the current d l sync
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l sync
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a d l sync with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync[] findByC_M_R_PrevAndNext(
		long syncId, long companyId, long modifiedDate, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncException {
		return getPersistence()
				   .findByC_M_R_PrevAndNext(syncId, companyId, modifiedDate,
			repositoryId, orderByComparator);
	}

	/**
	* Removes all the d l syncs where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_M_R(long companyId, long modifiedDate,
		long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_M_R(companyId, modifiedDate, repositoryId);
	}

	/**
	* Returns the number of d l syncs where companyId = &#63; and modifiedDate &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param repositoryId the repository ID
	* @return the number of matching d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_M_R(long companyId, long modifiedDate,
		long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_M_R(companyId, modifiedDate, repositoryId);
	}

	/**
	* Caches the d l sync in the entity cache if it is enabled.
	*
	* @param dlSync the d l sync
	*/
	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync) {
		getPersistence().cacheResult(dlSync);
	}

	/**
	* Caches the d l syncs in the entity cache if it is enabled.
	*
	* @param dlSyncs the d l syncs
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLSync> dlSyncs) {
		getPersistence().cacheResult(dlSyncs);
	}

	/**
	* Creates a new d l sync with the primary key. Does not add the d l sync to the database.
	*
	* @param syncId the primary key for the new d l sync
	* @return the new d l sync
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync create(
		long syncId) {
		return getPersistence().create(syncId);
	}

	/**
	* Removes the d l sync with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param syncId the primary key of the d l sync
	* @return the d l sync that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a d l sync with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync remove(
		long syncId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncException {
		return getPersistence().remove(syncId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLSync updateImpl(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlSync);
	}

	/**
	* Returns the d l sync with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchSyncException} if it could not be found.
	*
	* @param syncId the primary key of the d l sync
	* @return the d l sync
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a d l sync with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync findByPrimaryKey(
		long syncId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncException {
		return getPersistence().findByPrimaryKey(syncId);
	}

	/**
	* Returns the d l sync with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param syncId the primary key of the d l sync
	* @return the d l sync, or <code>null</code> if a d l sync with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync fetchByPrimaryKey(
		long syncId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(syncId);
	}

	/**
	* Returns all the d l syncs.
	*
	* @return the d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSync> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d l syncs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d l syncs
	* @param end the upper bound of the range of d l syncs (not inclusive)
	* @return the range of d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSync> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d l syncs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d l syncs
	* @param end the upper bound of the range of d l syncs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSync> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d l syncs from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d l syncs.
	*
	* @return the number of d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DLSyncPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLSyncPersistence)PortalBeanLocatorUtil.locate(DLSyncPersistence.class.getName());

			ReferenceRegistry.registerReference(DLSyncUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(DLSyncPersistence persistence) {
	}

	private static DLSyncPersistence _persistence;
}