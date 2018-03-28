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

import com.liferay.commerce.model.CommerceTaxMethod;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce tax method service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceTaxMethodPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxMethodPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceTaxMethodPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxMethodUtil {
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
	public static void clearCache(CommerceTaxMethod commerceTaxMethod) {
		getPersistence().clearCache(commerceTaxMethod);
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
	public static List<CommerceTaxMethod> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceTaxMethod> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceTaxMethod> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceTaxMethod update(CommerceTaxMethod commerceTaxMethod) {
		return getPersistence().update(commerceTaxMethod);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceTaxMethod update(
		CommerceTaxMethod commerceTaxMethod, ServiceContext serviceContext) {
		return getPersistence().update(commerceTaxMethod, serviceContext);
	}

	/**
	* Returns all the commerce tax methods where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce tax methods
	*/
	public static List<CommerceTaxMethod> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce tax methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @return the range of matching commerce tax methods
	*/
	public static List<CommerceTaxMethod> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tax methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tax methods
	*/
	public static List<CommerceTaxMethod> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tax methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tax methods
	*/
	public static List<CommerceTaxMethod> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce tax method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax method
	* @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod findByGroupId_First(long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce tax method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce tax method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax method
	* @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod findByGroupId_Last(long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce tax method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce tax methods before and after the current commerce tax method in the ordered set where groupId = &#63;.
	*
	* @param commerceTaxMethodId the primary key of the current commerce tax method
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tax method
	* @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	*/
	public static CommerceTaxMethod[] findByGroupId_PrevAndNext(
		long commerceTaxMethodId, long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceTaxMethodId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce tax methods where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce tax methods where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce tax methods
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the commerce tax method where groupId = &#63; and engineKey = &#63; or throws a {@link NoSuchTaxMethodException} if it could not be found.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the matching commerce tax method
	* @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod findByG_E(long groupId,
		java.lang.String engineKey)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence().findByG_E(groupId, engineKey);
	}

	/**
	* Returns the commerce tax method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod fetchByG_E(long groupId,
		java.lang.String engineKey) {
		return getPersistence().fetchByG_E(groupId, engineKey);
	}

	/**
	* Returns the commerce tax method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod fetchByG_E(long groupId,
		java.lang.String engineKey, boolean retrieveFromCache) {
		return getPersistence().fetchByG_E(groupId, engineKey, retrieveFromCache);
	}

	/**
	* Removes the commerce tax method where groupId = &#63; and engineKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the commerce tax method that was removed
	*/
	public static CommerceTaxMethod removeByG_E(long groupId,
		java.lang.String engineKey)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence().removeByG_E(groupId, engineKey);
	}

	/**
	* Returns the number of commerce tax methods where groupId = &#63; and engineKey = &#63;.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the number of matching commerce tax methods
	*/
	public static int countByG_E(long groupId, java.lang.String engineKey) {
		return getPersistence().countByG_E(groupId, engineKey);
	}

	/**
	* Returns all the commerce tax methods where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce tax methods
	*/
	public static List<CommerceTaxMethod> findByG_A(long groupId, boolean active) {
		return getPersistence().findByG_A(groupId, active);
	}

	/**
	* Returns a range of all the commerce tax methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @return the range of matching commerce tax methods
	*/
	public static List<CommerceTaxMethod> findByG_A(long groupId,
		boolean active, int start, int end) {
		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tax methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tax methods
	*/
	public static List<CommerceTaxMethod> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tax methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tax methods
	*/
	public static List<CommerceTaxMethod> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax method
	* @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod findByG_A_First(long groupId,
		boolean active, OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence()
				   .findByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the first commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod fetchByG_A_First(long groupId,
		boolean active, OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax method
	* @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod findByG_A_Last(long groupId,
		boolean active, OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence()
				   .findByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	*/
	public static CommerceTaxMethod fetchByG_A_Last(long groupId,
		boolean active, OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the commerce tax methods before and after the current commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param commerceTaxMethodId the primary key of the current commerce tax method
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tax method
	* @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	*/
	public static CommerceTaxMethod[] findByG_A_PrevAndNext(
		long commerceTaxMethodId, long groupId, boolean active,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence()
				   .findByG_A_PrevAndNext(commerceTaxMethodId, groupId, active,
			orderByComparator);
	}

	/**
	* Removes all the commerce tax methods where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public static void removeByG_A(long groupId, boolean active) {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	* Returns the number of commerce tax methods where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce tax methods
	*/
	public static int countByG_A(long groupId, boolean active) {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	* Caches the commerce tax method in the entity cache if it is enabled.
	*
	* @param commerceTaxMethod the commerce tax method
	*/
	public static void cacheResult(CommerceTaxMethod commerceTaxMethod) {
		getPersistence().cacheResult(commerceTaxMethod);
	}

	/**
	* Caches the commerce tax methods in the entity cache if it is enabled.
	*
	* @param commerceTaxMethods the commerce tax methods
	*/
	public static void cacheResult(List<CommerceTaxMethod> commerceTaxMethods) {
		getPersistence().cacheResult(commerceTaxMethods);
	}

	/**
	* Creates a new commerce tax method with the primary key. Does not add the commerce tax method to the database.
	*
	* @param commerceTaxMethodId the primary key for the new commerce tax method
	* @return the new commerce tax method
	*/
	public static CommerceTaxMethod create(long commerceTaxMethodId) {
		return getPersistence().create(commerceTaxMethodId);
	}

	/**
	* Removes the commerce tax method with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxMethodId the primary key of the commerce tax method
	* @return the commerce tax method that was removed
	* @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	*/
	public static CommerceTaxMethod remove(long commerceTaxMethodId)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence().remove(commerceTaxMethodId);
	}

	public static CommerceTaxMethod updateImpl(
		CommerceTaxMethod commerceTaxMethod) {
		return getPersistence().updateImpl(commerceTaxMethod);
	}

	/**
	* Returns the commerce tax method with the primary key or throws a {@link NoSuchTaxMethodException} if it could not be found.
	*
	* @param commerceTaxMethodId the primary key of the commerce tax method
	* @return the commerce tax method
	* @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	*/
	public static CommerceTaxMethod findByPrimaryKey(long commerceTaxMethodId)
		throws com.liferay.commerce.exception.NoSuchTaxMethodException {
		return getPersistence().findByPrimaryKey(commerceTaxMethodId);
	}

	/**
	* Returns the commerce tax method with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceTaxMethodId the primary key of the commerce tax method
	* @return the commerce tax method, or <code>null</code> if a commerce tax method with the primary key could not be found
	*/
	public static CommerceTaxMethod fetchByPrimaryKey(long commerceTaxMethodId) {
		return getPersistence().fetchByPrimaryKey(commerceTaxMethodId);
	}

	public static java.util.Map<java.io.Serializable, CommerceTaxMethod> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce tax methods.
	*
	* @return the commerce tax methods
	*/
	public static List<CommerceTaxMethod> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce tax methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @return the range of commerce tax methods
	*/
	public static List<CommerceTaxMethod> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce tax methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce tax methods
	*/
	public static List<CommerceTaxMethod> findAll(int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tax methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce tax methods
	*/
	public static List<CommerceTaxMethod> findAll(int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce tax methods from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce tax methods.
	*
	* @return the number of commerce tax methods
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceTaxMethodPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxMethodPersistence, CommerceTaxMethodPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxMethodPersistence.class);

		ServiceTracker<CommerceTaxMethodPersistence, CommerceTaxMethodPersistence> serviceTracker =
			new ServiceTracker<CommerceTaxMethodPersistence, CommerceTaxMethodPersistence>(bundle.getBundleContext(),
				CommerceTaxMethodPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}