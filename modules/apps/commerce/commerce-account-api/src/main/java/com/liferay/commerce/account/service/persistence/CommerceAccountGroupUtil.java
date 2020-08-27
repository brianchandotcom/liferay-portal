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

package com.liferay.commerce.account.service.persistence;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce account group service. This utility wraps <code>com.liferay.commerce.account.service.persistence.impl.CommerceAccountGroupPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroupPersistence
 * @generated
 */
public class CommerceAccountGroupUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CommerceAccountGroup commerceAccountGroup) {
		getPersistence().clearCache(commerceAccountGroup);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CommerceAccountGroup> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceAccountGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceAccountGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceAccountGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceAccountGroup update(
		CommerceAccountGroup commerceAccountGroup) {

		return getPersistence().update(commerceAccountGroup);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceAccountGroup update(
		CommerceAccountGroup commerceAccountGroup,
		ServiceContext serviceContext) {

		return getPersistence().update(commerceAccountGroup, serviceContext);
	}

	/**
	 * Returns all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId) {

		return getPersistence().findByCommerceAccountGroupIds(
			commerceAccountGroupId);
	}

	/**
	 * Returns a range of all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end) {

		return getPersistence().findByCommerceAccountGroupIds(
			commerceAccountGroupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().findByCommerceAccountGroupIds(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceAccountGroupIds(
			commerceAccountGroupId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup findByCommerceAccountGroupIds_First(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByCommerceAccountGroupIds_First(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the first commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup fetchByCommerceAccountGroupIds_First(
		long commerceAccountGroupId,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().fetchByCommerceAccountGroupIds_First(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup findByCommerceAccountGroupIds_Last(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByCommerceAccountGroupIds_Last(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup fetchByCommerceAccountGroupIds_Last(
		long commerceAccountGroupId,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().fetchByCommerceAccountGroupIds_Last(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns all the commerce account groups that the user has permission to view where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(long commerceAccountGroupId) {

		return getPersistence().filterFindByCommerceAccountGroupIds(
			commerceAccountGroupId);
	}

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(
			long commerceAccountGroupId, int start, int end) {

		return getPersistence().filterFindByCommerceAccountGroupIds(
			commerceAccountGroupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permissions to view where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().filterFindByCommerceAccountGroupIds(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	/**
	 * Returns all the commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(long[] commerceAccountGroupIds) {

		return getPersistence().filterFindByCommerceAccountGroupIds(
			commerceAccountGroupIds);
	}

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(
			long[] commerceAccountGroupIds, int start, int end) {

		return getPersistence().filterFindByCommerceAccountGroupIds(
			commerceAccountGroupIds, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(
			long[] commerceAccountGroupIds, int start, int end,
			OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().filterFindByCommerceAccountGroupIds(
			commerceAccountGroupIds, start, end, orderByComparator);
	}

	/**
	 * Returns all the commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds) {

		return getPersistence().findByCommerceAccountGroupIds(
			commerceAccountGroupIds);
	}

	/**
	 * Returns a range of all the commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end) {

		return getPersistence().findByCommerceAccountGroupIds(
			commerceAccountGroupIds, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().findByCommerceAccountGroupIds(
			commerceAccountGroupIds, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceAccountGroupIds(
			commerceAccountGroupIds, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the commerce account groups where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	public static void removeByCommerceAccountGroupIds(
		long commerceAccountGroupId) {

		getPersistence().removeByCommerceAccountGroupIds(
			commerceAccountGroupId);
	}

	/**
	 * Returns the number of commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account groups
	 */
	public static int countByCommerceAccountGroupIds(
		long commerceAccountGroupId) {

		return getPersistence().countByCommerceAccountGroupIds(
			commerceAccountGroupId);
	}

	/**
	 * Returns the number of commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the number of matching commerce account groups
	 */
	public static int countByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds) {

		return getPersistence().countByCommerceAccountGroupIds(
			commerceAccountGroupIds);
	}

	/**
	 * Returns the number of commerce account groups that the user has permission to view where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	public static int filterCountByCommerceAccountGroupIds(
		long commerceAccountGroupId) {

		return getPersistence().filterCountByCommerceAccountGroupIds(
			commerceAccountGroupId);
	}

	/**
	 * Returns the number of commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	public static int filterCountByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds) {

		return getPersistence().filterCountByCommerceAccountGroupIds(
			commerceAccountGroupIds);
	}

	/**
	 * Returns all the commerce account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public static CommerceAccountGroup[] findByCompanyId_PrevAndNext(
			long commerceAccountGroupId, long companyId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceAccountGroupId, companyId, orderByComparator);
	}

	/**
	 * Returns all the commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup> filterFindByCompanyId(
		long companyId) {

		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set of commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public static CommerceAccountGroup[] filterFindByCompanyId_PrevAndNext(
			long commerceAccountGroupId, long companyId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			commerceAccountGroupId, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce account groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce account groups
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByC_T(
		long companyId, int type) {

		return getPersistence().findByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByC_T(
		long companyId, int type, int start, int end) {

		return getPersistence().findByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	public static List<CommerceAccountGroup> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup findByC_T_First(
			long companyId, int type,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup fetchByC_T_First(
		long companyId, int type,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().fetchByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup findByC_T_Last(
			long companyId, int type,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup fetchByC_T_Last(
		long companyId, int type,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().fetchByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public static CommerceAccountGroup[] findByC_T_PrevAndNext(
			long commerceAccountGroupId, long companyId, int type,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByC_T_PrevAndNext(
			commerceAccountGroupId, companyId, type, orderByComparator);
	}

	/**
	 * Returns all the commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup> filterFindByC_T(
		long companyId, int type) {

		return getPersistence().filterFindByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup> filterFindByC_T(
		long companyId, int type, int start, int end) {

		return getPersistence().filterFindByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	public static List<CommerceAccountGroup> filterFindByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().filterFindByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set of commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public static CommerceAccountGroup[] filterFindByC_T_PrevAndNext(
			long commerceAccountGroupId, long companyId, int type,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().filterFindByC_T_PrevAndNext(
			commerceAccountGroupId, companyId, type, orderByComparator);
	}

	/**
	 * Removes all the commerce account groups where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public static void removeByC_T(long companyId, int type) {
		getPersistence().removeByC_T(companyId, type);
	}

	/**
	 * Returns the number of commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce account groups
	 */
	public static int countByC_T(long companyId, int type) {
		return getPersistence().countByC_T(companyId, type);
	}

	/**
	 * Returns the number of commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	public static int filterCountByC_T(long companyId, int type) {
		return getPersistence().filterCountByC_T(companyId, type);
	}

	/**
	 * Returns the commerce account group where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchAccountGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public static CommerceAccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce account group where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce account group that was removed
	 */
	public static CommerceAccountGroup removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce account groups where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce account groups
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the commerce account group in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroup the commerce account group
	 */
	public static void cacheResult(CommerceAccountGroup commerceAccountGroup) {
		getPersistence().cacheResult(commerceAccountGroup);
	}

	/**
	 * Caches the commerce account groups in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroups the commerce account groups
	 */
	public static void cacheResult(
		List<CommerceAccountGroup> commerceAccountGroups) {

		getPersistence().cacheResult(commerceAccountGroups);
	}

	/**
	 * Creates a new commerce account group with the primary key. Does not add the commerce account group to the database.
	 *
	 * @param commerceAccountGroupId the primary key for the new commerce account group
	 * @return the new commerce account group
	 */
	public static CommerceAccountGroup create(long commerceAccountGroupId) {
		return getPersistence().create(commerceAccountGroupId);
	}

	/**
	 * Removes the commerce account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group that was removed
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public static CommerceAccountGroup remove(long commerceAccountGroupId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().remove(commerceAccountGroupId);
	}

	public static CommerceAccountGroup updateImpl(
		CommerceAccountGroup commerceAccountGroup) {

		return getPersistence().updateImpl(commerceAccountGroup);
	}

	/**
	 * Returns the commerce account group with the primary key or throws a <code>NoSuchAccountGroupException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public static CommerceAccountGroup findByPrimaryKey(
			long commerceAccountGroupId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupException {

		return getPersistence().findByPrimaryKey(commerceAccountGroupId);
	}

	/**
	 * Returns the commerce account group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group, or <code>null</code> if a commerce account group with the primary key could not be found
	 */
	public static CommerceAccountGroup fetchByPrimaryKey(
		long commerceAccountGroupId) {

		return getPersistence().fetchByPrimaryKey(commerceAccountGroupId);
	}

	/**
	 * Returns all the commerce account groups.
	 *
	 * @return the commerce account groups
	 */
	public static List<CommerceAccountGroup> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of commerce account groups
	 */
	public static List<CommerceAccountGroup> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account groups
	 */
	public static List<CommerceAccountGroup> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account groups
	 */
	public static List<CommerceAccountGroup> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce account groups from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce account groups.
	 *
	 * @return the number of commerce account groups
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceAccountGroupPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountGroupPersistence, CommerceAccountGroupPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountGroupPersistence.class);

		ServiceTracker
			<CommerceAccountGroupPersistence, CommerceAccountGroupPersistence>
				serviceTracker =
					new ServiceTracker
						<CommerceAccountGroupPersistence,
						 CommerceAccountGroupPersistence>(
							 bundle.getBundleContext(),
							 CommerceAccountGroupPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}