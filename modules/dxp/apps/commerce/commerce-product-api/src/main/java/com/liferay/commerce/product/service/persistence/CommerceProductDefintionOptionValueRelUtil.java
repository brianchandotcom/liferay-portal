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

import com.liferay.commerce.product.model.CommerceProductDefintionOptionValueRel;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce product defintion option value rel service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CommerceProductDefintionOptionValueRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefintionOptionValueRelPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductDefintionOptionValueRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionValueRelUtil {
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
	public static void clearCache(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		getPersistence().clearCache(commerceProductDefintionOptionValueRel);
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
	public static List<CommerceProductDefintionOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceProductDefintionOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceProductDefintionOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceProductDefintionOptionValueRel update(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		return getPersistence().update(commerceProductDefintionOptionValueRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceProductDefintionOptionValueRel update(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceProductDefintionOptionValueRel,
			serviceContext);
	}

	/**
	* Returns all the commerce product defintion option value rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findByGroupId(
		long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce product defintion option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product defintion option value rels
	* @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	* @return the range of matching commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findByGroupId(
		long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product defintion option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product defintion option value rels
	* @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product defintion option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product defintion option value rels
	* @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product defintion option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product defintion option value rel
	* @throws NoSuchProductDefintionOptionValueRelException if a matching commerce product defintion option value rel could not be found
	*/
	public static CommerceProductDefintionOptionValueRel findByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce product defintion option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product defintion option value rel, or <code>null</code> if a matching commerce product defintion option value rel could not be found
	*/
	public static CommerceProductDefintionOptionValueRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product defintion option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product defintion option value rel
	* @throws NoSuchProductDefintionOptionValueRelException if a matching commerce product defintion option value rel could not be found
	*/
	public static CommerceProductDefintionOptionValueRel findByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product defintion option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product defintion option value rel, or <code>null</code> if a matching commerce product defintion option value rel could not be found
	*/
	public static CommerceProductDefintionOptionValueRel fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce product defintion option value rels before and after the current commerce product defintion option value rel in the ordered set where groupId = &#63;.
	*
	* @param commerceProductDefintionOptionValueRelId the primary key of the current commerce product defintion option value rel
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product defintion option value rel
	* @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	*/
	public static CommerceProductDefintionOptionValueRel[] findByGroupId_PrevAndNext(
		long commerceProductDefintionOptionValueRelId, long groupId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceProductDefintionOptionValueRelId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce product defintion option value rels where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce product defintion option value rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product defintion option value rels
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce product defintion option value rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce product defintion option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product defintion option value rels
	* @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	* @return the range of matching commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product defintion option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product defintion option value rels
	* @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product defintion option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product defintion option value rels
	* @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product defintion option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product defintion option value rel
	* @throws NoSuchProductDefintionOptionValueRelException if a matching commerce product defintion option value rel could not be found
	*/
	public static CommerceProductDefintionOptionValueRel findByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product defintion option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product defintion option value rel, or <code>null</code> if a matching commerce product defintion option value rel could not be found
	*/
	public static CommerceProductDefintionOptionValueRel fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product defintion option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product defintion option value rel
	* @throws NoSuchProductDefintionOptionValueRelException if a matching commerce product defintion option value rel could not be found
	*/
	public static CommerceProductDefintionOptionValueRel findByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product defintion option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product defintion option value rel, or <code>null</code> if a matching commerce product defintion option value rel could not be found
	*/
	public static CommerceProductDefintionOptionValueRel fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce product defintion option value rels before and after the current commerce product defintion option value rel in the ordered set where companyId = &#63;.
	*
	* @param commerceProductDefintionOptionValueRelId the primary key of the current commerce product defintion option value rel
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product defintion option value rel
	* @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	*/
	public static CommerceProductDefintionOptionValueRel[] findByCompanyId_PrevAndNext(
		long commerceProductDefintionOptionValueRelId, long companyId,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commerceProductDefintionOptionValueRelId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product defintion option value rels where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce product defintion option value rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product defintion option value rels
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Caches the commerce product defintion option value rel in the entity cache if it is enabled.
	*
	* @param commerceProductDefintionOptionValueRel the commerce product defintion option value rel
	*/
	public static void cacheResult(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		getPersistence().cacheResult(commerceProductDefintionOptionValueRel);
	}

	/**
	* Caches the commerce product defintion option value rels in the entity cache if it is enabled.
	*
	* @param commerceProductDefintionOptionValueRels the commerce product defintion option value rels
	*/
	public static void cacheResult(
		List<CommerceProductDefintionOptionValueRel> commerceProductDefintionOptionValueRels) {
		getPersistence().cacheResult(commerceProductDefintionOptionValueRels);
	}

	/**
	* Creates a new commerce product defintion option value rel with the primary key. Does not add the commerce product defintion option value rel to the database.
	*
	* @param commerceProductDefintionOptionValueRelId the primary key for the new commerce product defintion option value rel
	* @return the new commerce product defintion option value rel
	*/
	public static CommerceProductDefintionOptionValueRel create(
		long commerceProductDefintionOptionValueRelId) {
		return getPersistence().create(commerceProductDefintionOptionValueRelId);
	}

	/**
	* Removes the commerce product defintion option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefintionOptionValueRelId the primary key of the commerce product defintion option value rel
	* @return the commerce product defintion option value rel that was removed
	* @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	*/
	public static CommerceProductDefintionOptionValueRel remove(
		long commerceProductDefintionOptionValueRelId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException {
		return getPersistence().remove(commerceProductDefintionOptionValueRelId);
	}

	public static CommerceProductDefintionOptionValueRel updateImpl(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		return getPersistence()
				   .updateImpl(commerceProductDefintionOptionValueRel);
	}

	/**
	* Returns the commerce product defintion option value rel with the primary key or throws a {@link NoSuchProductDefintionOptionValueRelException} if it could not be found.
	*
	* @param commerceProductDefintionOptionValueRelId the primary key of the commerce product defintion option value rel
	* @return the commerce product defintion option value rel
	* @throws NoSuchProductDefintionOptionValueRelException if a commerce product defintion option value rel with the primary key could not be found
	*/
	public static CommerceProductDefintionOptionValueRel findByPrimaryKey(
		long commerceProductDefintionOptionValueRelId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException {
		return getPersistence()
				   .findByPrimaryKey(commerceProductDefintionOptionValueRelId);
	}

	/**
	* Returns the commerce product defintion option value rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductDefintionOptionValueRelId the primary key of the commerce product defintion option value rel
	* @return the commerce product defintion option value rel, or <code>null</code> if a commerce product defintion option value rel with the primary key could not be found
	*/
	public static CommerceProductDefintionOptionValueRel fetchByPrimaryKey(
		long commerceProductDefintionOptionValueRelId) {
		return getPersistence()
				   .fetchByPrimaryKey(commerceProductDefintionOptionValueRelId);
	}

	public static java.util.Map<java.io.Serializable, CommerceProductDefintionOptionValueRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce product defintion option value rels.
	*
	* @return the commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce product defintion option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product defintion option value rels
	* @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	* @return the range of commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce product defintion option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product defintion option value rels
	* @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findAll(
		int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product defintion option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefintionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product defintion option value rels
	* @param end the upper bound of the range of commerce product defintion option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product defintion option value rels
	*/
	public static List<CommerceProductDefintionOptionValueRel> findAll(
		int start, int end,
		OrderByComparator<CommerceProductDefintionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce product defintion option value rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce product defintion option value rels.
	*
	* @return the number of commerce product defintion option value rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceProductDefintionOptionValueRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductDefintionOptionValueRelPersistence, CommerceProductDefintionOptionValueRelPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductDefintionOptionValueRelPersistence.class);
}