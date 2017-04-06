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

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce product definition option value rel service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CommerceProductDefinitionOptionValueRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRelPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductDefinitionOptionValueRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelUtil {
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
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		getPersistence().clearCache(commerceProductDefinitionOptionValueRel);
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
	public static List<CommerceProductDefinitionOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceProductDefinitionOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceProductDefinitionOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceProductDefinitionOptionValueRel update(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		return getPersistence().update(commerceProductDefinitionOptionValueRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceProductDefinitionOptionValueRel update(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceProductDefinitionOptionValueRel,
			serviceContext);
	}

	/**
	* Returns all the commerce product definition option value rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce product definition option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of matching commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definition option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definition option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel findByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel findByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce product definition option value rels before and after the current commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the current commerce product definition option value rel
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel[] findByGroupId_PrevAndNext(
		long commerceProductDefinitionOptionValueRelId, long groupId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceProductDefinitionOptionValueRelId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce product definition option value rels where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce product definition option value rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product definition option value rels
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce product definition option value rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce product definition option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of matching commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definition option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definition option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel findByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel findByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce product definition option value rels before and after the current commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the current commerce product definition option value rel
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel[] findByCompanyId_PrevAndNext(
		long commerceProductDefinitionOptionValueRelId, long companyId,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commerceProductDefinitionOptionValueRelId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product definition option value rels where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce product definition option value rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product definition option value rels
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Caches the commerce product definition option value rel in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	*/
	public static void cacheResult(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		getPersistence().cacheResult(commerceProductDefinitionOptionValueRel);
	}

	/**
	* Caches the commerce product definition option value rels in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionOptionValueRels the commerce product definition option value rels
	*/
	public static void cacheResult(
		List<CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels) {
		getPersistence().cacheResult(commerceProductDefinitionOptionValueRels);
	}

	/**
	* Creates a new commerce product definition option value rel with the primary key. Does not add the commerce product definition option value rel to the database.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key for the new commerce product definition option value rel
	* @return the new commerce product definition option value rel
	*/
	public static CommerceProductDefinitionOptionValueRel create(
		long commerceProductDefinitionOptionValueRelId) {
		return getPersistence().create(commerceProductDefinitionOptionValueRelId);
	}

	/**
	* Removes the commerce product definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel that was removed
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel remove(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException {
		return getPersistence().remove(commerceProductDefinitionOptionValueRelId);
	}

	public static CommerceProductDefinitionOptionValueRel updateImpl(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		return getPersistence()
				   .updateImpl(commerceProductDefinitionOptionValueRel);
	}

	/**
	* Returns the commerce product definition option value rel with the primary key or throws a {@link NoSuchProductDefinitionOptionValueRelException} if it could not be found.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel findByPrimaryKey(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException {
		return getPersistence()
				   .findByPrimaryKey(commerceProductDefinitionOptionValueRelId);
	}

	/**
	* Returns the commerce product definition option value rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel, or <code>null</code> if a commerce product definition option value rel with the primary key could not be found
	*/
	public static CommerceProductDefinitionOptionValueRel fetchByPrimaryKey(
		long commerceProductDefinitionOptionValueRelId) {
		return getPersistence()
				   .fetchByPrimaryKey(commerceProductDefinitionOptionValueRelId);
	}

	public static java.util.Map<java.io.Serializable, CommerceProductDefinitionOptionValueRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce product definition option value rels.
	*
	* @return the commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce product definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findAll(
		int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product definition option value rels
	*/
	public static List<CommerceProductDefinitionOptionValueRel> findAll(
		int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce product definition option value rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce product definition option value rels.
	*
	* @return the number of commerce product definition option value rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceProductDefinitionOptionValueRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductDefinitionOptionValueRelPersistence, CommerceProductDefinitionOptionValueRelPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductDefinitionOptionValueRelPersistence.class);
}