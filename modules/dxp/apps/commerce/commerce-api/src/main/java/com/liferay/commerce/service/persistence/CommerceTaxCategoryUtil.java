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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceTaxCategory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce tax category service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceTaxCategoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceTaxCategoryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryUtil {
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
	public static void clearCache(CommerceTaxCategory commerceTaxCategory) {
		getPersistence().clearCache(commerceTaxCategory);
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
	public static List<CommerceTaxCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceTaxCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceTaxCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceTaxCategory update(
		CommerceTaxCategory commerceTaxCategory) {
		return getPersistence().update(commerceTaxCategory);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceTaxCategory update(
		CommerceTaxCategory commerceTaxCategory, ServiceContext serviceContext) {
		return getPersistence().update(commerceTaxCategory, serviceContext);
	}

	/**
	* Returns all the commerce tax categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce tax categories
	*/
	public static List<CommerceTaxCategory> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce tax categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @return the range of matching commerce tax categories
	*/
	public static List<CommerceTaxCategory> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tax categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tax categories
	*/
	public static List<CommerceTaxCategory> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tax categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tax categories
	*/
	public static List<CommerceTaxCategory> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceTaxCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category
	* @throws NoSuchTaxCategoryException if a matching commerce tax category could not be found
	*/
	public static CommerceTaxCategory findByGroupId_First(long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category, or <code>null</code> if a matching commerce tax category could not be found
	*/
	public static CommerceTaxCategory fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category
	* @throws NoSuchTaxCategoryException if a matching commerce tax category could not be found
	*/
	public static CommerceTaxCategory findByGroupId_Last(long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category, or <code>null</code> if a matching commerce tax category could not be found
	*/
	public static CommerceTaxCategory fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce tax categories before and after the current commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param commerceTaxCategoryId the primary key of the current commerce tax category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tax category
	* @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	*/
	public static CommerceTaxCategory[] findByGroupId_PrevAndNext(
		long commerceTaxCategoryId, long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceTaxCategoryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce tax categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce tax categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce tax categories
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Caches the commerce tax category in the entity cache if it is enabled.
	*
	* @param commerceTaxCategory the commerce tax category
	*/
	public static void cacheResult(CommerceTaxCategory commerceTaxCategory) {
		getPersistence().cacheResult(commerceTaxCategory);
	}

	/**
	* Caches the commerce tax categories in the entity cache if it is enabled.
	*
	* @param commerceTaxCategories the commerce tax categories
	*/
	public static void cacheResult(
		List<CommerceTaxCategory> commerceTaxCategories) {
		getPersistence().cacheResult(commerceTaxCategories);
	}

	/**
	* Creates a new commerce tax category with the primary key. Does not add the commerce tax category to the database.
	*
	* @param commerceTaxCategoryId the primary key for the new commerce tax category
	* @return the new commerce tax category
	*/
	public static CommerceTaxCategory create(long commerceTaxCategoryId) {
		return getPersistence().create(commerceTaxCategoryId);
	}

	/**
	* Removes the commerce tax category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category that was removed
	* @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	*/
	public static CommerceTaxCategory remove(long commerceTaxCategoryId)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryException {
		return getPersistence().remove(commerceTaxCategoryId);
	}

	public static CommerceTaxCategory updateImpl(
		CommerceTaxCategory commerceTaxCategory) {
		return getPersistence().updateImpl(commerceTaxCategory);
	}

	/**
	* Returns the commerce tax category with the primary key or throws a {@link NoSuchTaxCategoryException} if it could not be found.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category
	* @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	*/
	public static CommerceTaxCategory findByPrimaryKey(
		long commerceTaxCategoryId)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryException {
		return getPersistence().findByPrimaryKey(commerceTaxCategoryId);
	}

	/**
	* Returns the commerce tax category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category, or <code>null</code> if a commerce tax category with the primary key could not be found
	*/
	public static CommerceTaxCategory fetchByPrimaryKey(
		long commerceTaxCategoryId) {
		return getPersistence().fetchByPrimaryKey(commerceTaxCategoryId);
	}

	public static java.util.Map<java.io.Serializable, CommerceTaxCategory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce tax categories.
	*
	* @return the commerce tax categories
	*/
	public static List<CommerceTaxCategory> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @return the range of commerce tax categories
	*/
	public static List<CommerceTaxCategory> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce tax categories
	*/
	public static List<CommerceTaxCategory> findAll(int start, int end,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce tax categories
	*/
	public static List<CommerceTaxCategory> findAll(int start, int end,
		OrderByComparator<CommerceTaxCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce tax categories from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce tax categories.
	*
	* @return the number of commerce tax categories
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceTaxCategoryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxCategoryPersistence, CommerceTaxCategoryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxCategoryPersistence.class);

		ServiceTracker<CommerceTaxCategoryPersistence, CommerceTaxCategoryPersistence> serviceTracker =
			new ServiceTracker<CommerceTaxCategoryPersistence, CommerceTaxCategoryPersistence>(bundle.getBundleContext(),
				CommerceTaxCategoryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}