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

import com.liferay.commerce.product.model.CommerceProductDefinition;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce product definition service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CommerceProductDefinitionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductDefinitionPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionUtil {
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
		CommerceProductDefinition commerceProductDefinition) {
		getPersistence().clearCache(commerceProductDefinition);
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
	public static List<CommerceProductDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceProductDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceProductDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceProductDefinition update(
		CommerceProductDefinition commerceProductDefinition) {
		return getPersistence().update(commerceProductDefinition);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceProductDefinition update(
		CommerceProductDefinition commerceProductDefinition,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceProductDefinition, serviceContext);
	}

	/**
	* Returns all the commerce product definitions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce product definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce product definitions before and after the current commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param commerceProductDefinitionId the primary key of the current commerce product definition
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public static CommerceProductDefinition[] findByUuid_PrevAndNext(
		long commerceProductDefinitionId, java.lang.String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceProductDefinitionId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce product definitions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce product definitions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce product definitions
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce product definition where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductDefinitionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce product definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByUUID_G(
		java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce product definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce product definition where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce product definition that was removed
	*/
	public static CommerceProductDefinition removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce product definitions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce product definitions
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce product definitions before and after the current commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceProductDefinitionId the primary key of the current commerce product definition
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public static CommerceProductDefinition[] findByUuid_C_PrevAndNext(
		long commerceProductDefinitionId, java.lang.String uuid,
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceProductDefinitionId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product definitions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce product definitions
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce product definitions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce product definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition findByGroupId_First(long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce product definitions before and after the current commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param commerceProductDefinitionId the primary key of the current commerce product definition
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public static CommerceProductDefinition[] findByGroupId_PrevAndNext(
		long commerceProductDefinitionId, long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceProductDefinitionId,
			groupId, orderByComparator);
	}

	/**
	* Returns all the commerce product definitions that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product definitions that the user has permission to view
	*/
	public static List<CommerceProductDefinition> filterFindByGroupId(
		long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce product definitions that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of matching commerce product definitions that the user has permission to view
	*/
	public static List<CommerceProductDefinition> filterFindByGroupId(
		long groupId, int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definitions that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definitions that the user has permission to view
	*/
	public static List<CommerceProductDefinition> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the commerce product definitions before and after the current commerce product definition in the ordered set of commerce product definitions that the user has permission to view where groupId = &#63;.
	*
	* @param commerceProductDefinitionId the primary key of the current commerce product definition
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public static CommerceProductDefinition[] filterFindByGroupId_PrevAndNext(
		long commerceProductDefinitionId, long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(commerceProductDefinitionId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce product definitions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce product definitions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product definitions
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of commerce product definitions that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product definitions that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the commerce product definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce product definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definitions
	*/
	public static List<CommerceProductDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition findByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition findByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public static CommerceProductDefinition fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce product definitions before and after the current commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param commerceProductDefinitionId the primary key of the current commerce product definition
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public static CommerceProductDefinition[] findByCompanyId_PrevAndNext(
		long commerceProductDefinitionId, long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commerceProductDefinitionId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product definitions where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce product definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product definitions
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Caches the commerce product definition in the entity cache if it is enabled.
	*
	* @param commerceProductDefinition the commerce product definition
	*/
	public static void cacheResult(
		CommerceProductDefinition commerceProductDefinition) {
		getPersistence().cacheResult(commerceProductDefinition);
	}

	/**
	* Caches the commerce product definitions in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitions the commerce product definitions
	*/
	public static void cacheResult(
		List<CommerceProductDefinition> commerceProductDefinitions) {
		getPersistence().cacheResult(commerceProductDefinitions);
	}

	/**
	* Creates a new commerce product definition with the primary key. Does not add the commerce product definition to the database.
	*
	* @param commerceProductDefinitionId the primary key for the new commerce product definition
	* @return the new commerce product definition
	*/
	public static CommerceProductDefinition create(
		long commerceProductDefinitionId) {
		return getPersistence().create(commerceProductDefinitionId);
	}

	/**
	* Removes the commerce product definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionId the primary key of the commerce product definition
	* @return the commerce product definition that was removed
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public static CommerceProductDefinition remove(
		long commerceProductDefinitionId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence().remove(commerceProductDefinitionId);
	}

	public static CommerceProductDefinition updateImpl(
		CommerceProductDefinition commerceProductDefinition) {
		return getPersistence().updateImpl(commerceProductDefinition);
	}

	/**
	* Returns the commerce product definition with the primary key or throws a {@link NoSuchProductDefinitionException} if it could not be found.
	*
	* @param commerceProductDefinitionId the primary key of the commerce product definition
	* @return the commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public static CommerceProductDefinition findByPrimaryKey(
		long commerceProductDefinitionId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionException {
		return getPersistence().findByPrimaryKey(commerceProductDefinitionId);
	}

	/**
	* Returns the commerce product definition with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductDefinitionId the primary key of the commerce product definition
	* @return the commerce product definition, or <code>null</code> if a commerce product definition with the primary key could not be found
	*/
	public static CommerceProductDefinition fetchByPrimaryKey(
		long commerceProductDefinitionId) {
		return getPersistence().fetchByPrimaryKey(commerceProductDefinitionId);
	}

	public static java.util.Map<java.io.Serializable, CommerceProductDefinition> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce product definitions.
	*
	* @return the commerce product definitions
	*/
	public static List<CommerceProductDefinition> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce product definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of commerce product definitions
	*/
	public static List<CommerceProductDefinition> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product definitions
	*/
	public static List<CommerceProductDefinition> findAll(int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product definitions
	*/
	public static List<CommerceProductDefinition> findAll(int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce product definitions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce product definitions.
	*
	* @return the number of commerce product definitions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceProductDefinitionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductDefinitionPersistence, CommerceProductDefinitionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductDefinitionPersistence.class);
}