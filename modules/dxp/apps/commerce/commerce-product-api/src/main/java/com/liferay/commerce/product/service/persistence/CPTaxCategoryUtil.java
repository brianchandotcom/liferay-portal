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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPTaxCategory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp tax category service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPTaxCategoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPTaxCategoryPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPTaxCategoryPersistenceImpl
 * @generated
 */
@ProviderType
public class CPTaxCategoryUtil {
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
	public static void clearCache(CPTaxCategory cpTaxCategory) {
		getPersistence().clearCache(cpTaxCategory);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPTaxCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPTaxCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPTaxCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPTaxCategory> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPTaxCategory update(CPTaxCategory cpTaxCategory) {
		return getPersistence().update(cpTaxCategory);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPTaxCategory update(CPTaxCategory cpTaxCategory,
		ServiceContext serviceContext) {
		return getPersistence().update(cpTaxCategory, serviceContext);
	}

	/**
	* Returns all the cp tax categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp tax categories
	*/
	public static List<CPTaxCategory> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp tax categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp tax categories
	* @param end the upper bound of the range of cp tax categories (not inclusive)
	* @return the range of matching cp tax categories
	*/
	public static List<CPTaxCategory> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp tax categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp tax categories
	* @param end the upper bound of the range of cp tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp tax categories
	*/
	public static List<CPTaxCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPTaxCategory> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp tax categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp tax categories
	* @param end the upper bound of the range of cp tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp tax categories
	*/
	public static List<CPTaxCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPTaxCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp tax category
	* @throws NoSuchCPTaxCategoryException if a matching cp tax category could not be found
	*/
	public static CPTaxCategory findByGroupId_First(long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp tax category, or <code>null</code> if a matching cp tax category could not be found
	*/
	public static CPTaxCategory fetchByGroupId_First(long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp tax category
	* @throws NoSuchCPTaxCategoryException if a matching cp tax category could not be found
	*/
	public static CPTaxCategory findByGroupId_Last(long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp tax category, or <code>null</code> if a matching cp tax category could not be found
	*/
	public static CPTaxCategory fetchByGroupId_Last(long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp tax categories before and after the current cp tax category in the ordered set where groupId = &#63;.
	*
	* @param CPTaxCategoryId the primary key of the current cp tax category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp tax category
	* @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	*/
	public static CPTaxCategory[] findByGroupId_PrevAndNext(
		long CPTaxCategoryId, long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPTaxCategoryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the cp tax categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp tax categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp tax categories
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Caches the cp tax category in the entity cache if it is enabled.
	*
	* @param cpTaxCategory the cp tax category
	*/
	public static void cacheResult(CPTaxCategory cpTaxCategory) {
		getPersistence().cacheResult(cpTaxCategory);
	}

	/**
	* Caches the cp tax categories in the entity cache if it is enabled.
	*
	* @param cpTaxCategories the cp tax categories
	*/
	public static void cacheResult(List<CPTaxCategory> cpTaxCategories) {
		getPersistence().cacheResult(cpTaxCategories);
	}

	/**
	* Creates a new cp tax category with the primary key. Does not add the cp tax category to the database.
	*
	* @param CPTaxCategoryId the primary key for the new cp tax category
	* @return the new cp tax category
	*/
	public static CPTaxCategory create(long CPTaxCategoryId) {
		return getPersistence().create(CPTaxCategoryId);
	}

	/**
	* Removes the cp tax category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPTaxCategoryId the primary key of the cp tax category
	* @return the cp tax category that was removed
	* @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	*/
	public static CPTaxCategory remove(long CPTaxCategoryId)
		throws com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException {
		return getPersistence().remove(CPTaxCategoryId);
	}

	public static CPTaxCategory updateImpl(CPTaxCategory cpTaxCategory) {
		return getPersistence().updateImpl(cpTaxCategory);
	}

	/**
	* Returns the cp tax category with the primary key or throws a {@link NoSuchCPTaxCategoryException} if it could not be found.
	*
	* @param CPTaxCategoryId the primary key of the cp tax category
	* @return the cp tax category
	* @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	*/
	public static CPTaxCategory findByPrimaryKey(long CPTaxCategoryId)
		throws com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException {
		return getPersistence().findByPrimaryKey(CPTaxCategoryId);
	}

	/**
	* Returns the cp tax category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPTaxCategoryId the primary key of the cp tax category
	* @return the cp tax category, or <code>null</code> if a cp tax category with the primary key could not be found
	*/
	public static CPTaxCategory fetchByPrimaryKey(long CPTaxCategoryId) {
		return getPersistence().fetchByPrimaryKey(CPTaxCategoryId);
	}

	public static java.util.Map<java.io.Serializable, CPTaxCategory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp tax categories.
	*
	* @return the cp tax categories
	*/
	public static List<CPTaxCategory> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp tax categories
	* @param end the upper bound of the range of cp tax categories (not inclusive)
	* @return the range of cp tax categories
	*/
	public static List<CPTaxCategory> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp tax categories
	* @param end the upper bound of the range of cp tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp tax categories
	*/
	public static List<CPTaxCategory> findAll(int start, int end,
		OrderByComparator<CPTaxCategory> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp tax categories
	* @param end the upper bound of the range of cp tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp tax categories
	*/
	public static List<CPTaxCategory> findAll(int start, int end,
		OrderByComparator<CPTaxCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp tax categories from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp tax categories.
	*
	* @return the number of cp tax categories
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPTaxCategoryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPTaxCategoryPersistence, CPTaxCategoryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPTaxCategoryPersistence.class);

		ServiceTracker<CPTaxCategoryPersistence, CPTaxCategoryPersistence> serviceTracker =
			new ServiceTracker<CPTaxCategoryPersistence, CPTaxCategoryPersistence>(bundle.getBundleContext(),
				CPTaxCategoryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}