/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

import java.util.List;

/**
 * The persistence utility for the s c product entry service. This utility wraps {@link SCProductEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductEntryPersistence
 * @see SCProductEntryPersistenceImpl
 * @generated
 */
public class SCProductEntryUtil {
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
	public static void clearCache(SCProductEntry scProductEntry) {
		getPersistence().clearCache(scProductEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SCProductEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SCProductEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SCProductEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SCProductEntry update(SCProductEntry scProductEntry)
		throws SystemException {
		return getPersistence().update(scProductEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SCProductEntry update(SCProductEntry scProductEntry,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(scProductEntry, serviceContext);
	}

	/**
	* Returns all the s c product entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the s c product entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the s c product entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first s c product entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first s c product entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last s c product entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last s c product entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set where groupId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry[] findByGroupId_PrevAndNext(
		long productEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(productEntryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the s c product entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c product entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the s c product entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the s c product entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set of s c product entries that the user has permission to view where groupId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry[] filterFindByGroupId_PrevAndNext(
		long productEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(productEntryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the s c product entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of s c product entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of s c product entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c product entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the s c product entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the s c product entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the s c product entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first s c product entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first s c product entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last s c product entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last s c product entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set where companyId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry[] findByCompanyId_PrevAndNext(
		long productEntryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(productEntryId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the s c product entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of s c product entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the s c product entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	* Returns a range of all the s c product entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the s c product entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U(groupId, userId, start, end, orderByComparator);
	}

	/**
	* Returns the first s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence()
				   .findByG_U_First(groupId, userId, orderByComparator);
	}

	/**
	* Returns the first s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_U_First(groupId, userId, orderByComparator);
	}

	/**
	* Returns the last s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence()
				   .findByG_U_Last(groupId, userId, orderByComparator);
	}

	/**
	* Returns the last s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_U_Last(groupId, userId, orderByComparator);
	}

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry[] findByG_U_PrevAndNext(
		long productEntryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence()
				   .findByG_U_PrevAndNext(productEntryId, groupId, userId,
			orderByComparator);
	}

	/**
	* Returns all the s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching s c product entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> filterFindByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_U(groupId, userId);
	}

	/**
	* Returns a range of all the s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> filterFindByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_U(groupId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the s c product entries that the user has permissions to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> filterFindByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_U(groupId, userId, start, end,
			orderByComparator);
	}

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set of s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry[] filterFindByG_U_PrevAndNext(
		long productEntryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence()
				   .filterFindByG_U_PrevAndNext(productEntryId, groupId,
			userId, orderByComparator);
	}

	/**
	* Removes all the s c product entries where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	* Returns the number of s c product entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	* Returns the number of s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching s c product entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_U(groupId, userId);
	}

	/**
	* Returns the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductEntryException} if it could not be found.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @return the matching s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByRG_RA(
		java.lang.String repoGroupId, java.lang.String repoArtifactId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByRG_RA(repoGroupId, repoArtifactId);
	}

	/**
	* Returns the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @return the matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByRG_RA(
		java.lang.String repoGroupId, java.lang.String repoArtifactId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByRG_RA(repoGroupId, repoArtifactId);
	}

	/**
	* Returns the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByRG_RA(
		java.lang.String repoGroupId, java.lang.String repoArtifactId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByRG_RA(repoGroupId, repoArtifactId, retrieveFromCache);
	}

	/**
	* Removes the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; from the database.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @return the s c product entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry removeByRG_RA(
		java.lang.String repoGroupId, java.lang.String repoArtifactId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().removeByRG_RA(repoGroupId, repoArtifactId);
	}

	/**
	* Returns the number of s c product entries where repoGroupId = &#63; and repoArtifactId = &#63;.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @return the number of matching s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRG_RA(java.lang.String repoGroupId,
		java.lang.String repoArtifactId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRG_RA(repoGroupId, repoArtifactId);
	}

	/**
	* Caches the s c product entry in the entity cache if it is enabled.
	*
	* @param scProductEntry the s c product entry
	*/
	public static void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		getPersistence().cacheResult(scProductEntry);
	}

	/**
	* Caches the s c product entries in the entity cache if it is enabled.
	*
	* @param scProductEntries the s c product entries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries) {
		getPersistence().cacheResult(scProductEntries);
	}

	/**
	* Creates a new s c product entry with the primary key. Does not add the s c product entry to the database.
	*
	* @param productEntryId the primary key for the new s c product entry
	* @return the new s c product entry
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry create(
		long productEntryId) {
		return getPersistence().create(productEntryId);
	}

	/**
	* Removes the s c product entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry that was removed
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry remove(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().remove(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(scProductEntry);
	}

	/**
	* Returns the s c product entry with the primary key or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductEntryException} if it could not be found.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry
	* @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry findByPrimaryKey(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException {
		return getPersistence().findByPrimaryKey(productEntryId);
	}

	/**
	* Returns the s c product entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry, or <code>null</code> if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByPrimaryKey(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(productEntryId);
	}

	/**
	* Returns all the s c product entries.
	*
	* @return the s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the s c product entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the s c product entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the s c product entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of s c product entries.
	*
	* @return the number of s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	/**
	* Returns all the s c licenses associated with the s c product entry.
	*
	* @param pk the primary key of the s c product entry
	* @return the s c licenses associated with the s c product entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCLicenses(pk);
	}

	/**
	* Returns a range of all the s c licenses associated with the s c product entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c product entry
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of s c licenses associated with the s c product entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCLicenses(pk, start, end);
	}

	/**
	* Returns an ordered range of all the s c licenses associated with the s c product entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c product entry
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c licenses associated with the s c product entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCLicenses(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of s c licenses associated with the s c product entry.
	*
	* @param pk the primary key of the s c product entry
	* @return the number of s c licenses associated with the s c product entry
	* @throws SystemException if a system exception occurred
	*/
	public static int getSCLicensesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCLicensesSize(pk);
	}

	/**
	* Returns <code>true</code> if the s c license is associated with the s c product entry.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePK the primary key of the s c license
	* @return <code>true</code> if the s c license is associated with the s c product entry; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsSCLicense(long pk, long scLicensePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsSCLicense(pk, scLicensePK);
	}

	/**
	* Returns <code>true</code> if the s c product entry has any s c licenses associated with it.
	*
	* @param pk the primary key of the s c product entry to check for associations with s c licenses
	* @return <code>true</code> if the s c product entry has any s c licenses associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsSCLicenses(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsSCLicenses(pk);
	}

	/**
	* Adds an association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePK the primary key of the s c license
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCLicense(long pk, long scLicensePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCLicense(pk, scLicensePK);
	}

	/**
	* Adds an association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicense the s c license
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCLicense(pk, scLicense);
	}

	/**
	* Adds an association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePKs the primary keys of the s c licenses
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCLicenses(long pk, long[] scLicensePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCLicenses(pk, scLicensePKs);
	}

	/**
	* Adds an association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicenses the s c licenses
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCLicenses(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCLicenses(pk, scLicenses);
	}

	/**
	* Clears all associations between the s c product entry and its s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry to clear the associated s c licenses from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearSCLicenses(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearSCLicenses(pk);
	}

	/**
	* Removes the association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePK the primary key of the s c license
	* @throws SystemException if a system exception occurred
	*/
	public static void removeSCLicense(long pk, long scLicensePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCLicense(pk, scLicensePK);
	}

	/**
	* Removes the association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicense the s c license
	* @throws SystemException if a system exception occurred
	*/
	public static void removeSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCLicense(pk, scLicense);
	}

	/**
	* Removes the association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePKs the primary keys of the s c licenses
	* @throws SystemException if a system exception occurred
	*/
	public static void removeSCLicenses(long pk, long[] scLicensePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCLicenses(pk, scLicensePKs);
	}

	/**
	* Removes the association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicenses the s c licenses
	* @throws SystemException if a system exception occurred
	*/
	public static void removeSCLicenses(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCLicenses(pk, scLicenses);
	}

	/**
	* Sets the s c licenses associated with the s c product entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePKs the primary keys of the s c licenses to be associated with the s c product entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setSCLicenses(long pk, long[] scLicensePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setSCLicenses(pk, scLicensePKs);
	}

	/**
	* Sets the s c licenses associated with the s c product entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicenses the s c licenses to be associated with the s c product entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setSCLicenses(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setSCLicenses(pk, scLicenses);
	}

	public static SCProductEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SCProductEntryPersistence)PortalBeanLocatorUtil.locate(SCProductEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(SCProductEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(SCProductEntryPersistence persistence) {
	}

	private static SCProductEntryPersistence _persistence;
}