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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the resource block service. This utility wraps {@link com.liferay.portal.service.persistence.impl.ResourceBlockPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPersistence
 * @see com.liferay.portal.service.persistence.impl.ResourceBlockPersistenceImpl
 * @generated
 */
@ProviderType
public class ResourceBlockUtil {
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
	public static void clearCache(ResourceBlock resourceBlock) {
		getPersistence().clearCache(resourceBlock);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ResourceBlock> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ResourceBlock> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ResourceBlock> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ResourceBlock update(ResourceBlock resourceBlock) {
		return getPersistence().update(resourceBlock);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ResourceBlock update(ResourceBlock resourceBlock,
		ServiceContext serviceContext) {
		return getPersistence().update(resourceBlock, serviceContext);
	}

	/**
	* Returns all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching resource blocks
	*/
	public static List<ResourceBlock> findByC_N(long companyId,
		java.lang.String name) {
		return getPersistence().findByC_N(companyId, name);
	}

	/**
	* Returns a range of all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of matching resource blocks
	*/
	public static List<ResourceBlock> findByC_N(long companyId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByC_N(companyId, name, start, end);
	}

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource blocks
	*/
	public static List<ResourceBlock> findByC_N(long companyId,
		java.lang.String name, int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return getPersistence()
				   .findByC_N(companyId, name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource blocks
	*/
	public static List<ResourceBlock> findByC_N(long companyId,
		java.lang.String name, int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_N(companyId, name, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public static ResourceBlock findByC_N_First(long companyId,
		java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence()
				   .findByC_N_First(companyId, name, orderByComparator);
	}

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public static ResourceBlock fetchByC_N_First(long companyId,
		java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return getPersistence()
				   .fetchByC_N_First(companyId, name, orderByComparator);
	}

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public static ResourceBlock findByC_N_Last(long companyId,
		java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence()
				   .findByC_N_Last(companyId, name, orderByComparator);
	}

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public static ResourceBlock fetchByC_N_Last(long companyId,
		java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return getPersistence()
				   .fetchByC_N_Last(companyId, name, orderByComparator);
	}

	/**
	* Returns the resource blocks before and after the current resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param resourceBlockId the primary key of the current resource block
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block
	* @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	*/
	public static ResourceBlock[] findByC_N_PrevAndNext(long resourceBlockId,
		long companyId, java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence()
				   .findByC_N_PrevAndNext(resourceBlockId, companyId, name,
			orderByComparator);
	}

	/**
	* Removes all the resource blocks where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	*/
	public static void removeByC_N(long companyId, java.lang.String name) {
		getPersistence().removeByC_N(companyId, name);
	}

	/**
	* Returns the number of resource blocks where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching resource blocks
	*/
	public static int countByC_N(long companyId, java.lang.String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	* Returns all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @return the matching resource blocks
	*/
	public static List<ResourceBlock> findByC_G_N(long companyId, long groupId,
		java.lang.String name) {
		return getPersistence().findByC_G_N(companyId, groupId, name);
	}

	/**
	* Returns a range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of matching resource blocks
	*/
	public static List<ResourceBlock> findByC_G_N(long companyId, long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByC_G_N(companyId, groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource blocks
	*/
	public static List<ResourceBlock> findByC_G_N(long companyId, long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return getPersistence()
				   .findByC_G_N(companyId, groupId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource blocks
	*/
	public static List<ResourceBlock> findByC_G_N(long companyId, long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_G_N(companyId, groupId, name, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public static ResourceBlock findByC_G_N_First(long companyId, long groupId,
		java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence()
				   .findByC_G_N_First(companyId, groupId, name,
			orderByComparator);
	}

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public static ResourceBlock fetchByC_G_N_First(long companyId,
		long groupId, java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return getPersistence()
				   .fetchByC_G_N_First(companyId, groupId, name,
			orderByComparator);
	}

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public static ResourceBlock findByC_G_N_Last(long companyId, long groupId,
		java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence()
				   .findByC_G_N_Last(companyId, groupId, name, orderByComparator);
	}

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public static ResourceBlock fetchByC_G_N_Last(long companyId, long groupId,
		java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return getPersistence()
				   .fetchByC_G_N_Last(companyId, groupId, name,
			orderByComparator);
	}

	/**
	* Returns the resource blocks before and after the current resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param resourceBlockId the primary key of the current resource block
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block
	* @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	*/
	public static ResourceBlock[] findByC_G_N_PrevAndNext(
		long resourceBlockId, long companyId, long groupId,
		java.lang.String name,
		OrderByComparator<ResourceBlock> orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence()
				   .findByC_G_N_PrevAndNext(resourceBlockId, companyId,
			groupId, name, orderByComparator);
	}

	/**
	* Removes all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	*/
	public static void removeByC_G_N(long companyId, long groupId,
		java.lang.String name) {
		getPersistence().removeByC_G_N(companyId, groupId, name);
	}

	/**
	* Returns the number of resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching resource blocks
	*/
	public static int countByC_G_N(long companyId, long groupId,
		java.lang.String name) {
		return getPersistence().countByC_G_N(companyId, groupId, name);
	}

	/**
	* Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or throws a {@link NoSuchResourceBlockException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public static ResourceBlock findByC_G_N_P(long companyId, long groupId,
		java.lang.String name, java.lang.String permissionsHash)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence()
				   .findByC_G_N_P(companyId, groupId, name, permissionsHash);
	}

	/**
	* Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public static ResourceBlock fetchByC_G_N_P(long companyId, long groupId,
		java.lang.String name, java.lang.String permissionsHash) {
		return getPersistence()
				   .fetchByC_G_N_P(companyId, groupId, name, permissionsHash);
	}

	/**
	* Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public static ResourceBlock fetchByC_G_N_P(long companyId, long groupId,
		java.lang.String name, java.lang.String permissionsHash,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_G_N_P(companyId, groupId, name, permissionsHash,
			retrieveFromCache);
	}

	/**
	* Removes the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the resource block that was removed
	*/
	public static ResourceBlock removeByC_G_N_P(long companyId, long groupId,
		java.lang.String name, java.lang.String permissionsHash)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence()
				   .removeByC_G_N_P(companyId, groupId, name, permissionsHash);
	}

	/**
	* Returns the number of resource blocks where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the number of matching resource blocks
	*/
	public static int countByC_G_N_P(long companyId, long groupId,
		java.lang.String name, java.lang.String permissionsHash) {
		return getPersistence()
				   .countByC_G_N_P(companyId, groupId, name, permissionsHash);
	}

	/**
	* Caches the resource block in the entity cache if it is enabled.
	*
	* @param resourceBlock the resource block
	*/
	public static void cacheResult(ResourceBlock resourceBlock) {
		getPersistence().cacheResult(resourceBlock);
	}

	/**
	* Caches the resource blocks in the entity cache if it is enabled.
	*
	* @param resourceBlocks the resource blocks
	*/
	public static void cacheResult(List<ResourceBlock> resourceBlocks) {
		getPersistence().cacheResult(resourceBlocks);
	}

	/**
	* Creates a new resource block with the primary key. Does not add the resource block to the database.
	*
	* @param resourceBlockId the primary key for the new resource block
	* @return the new resource block
	*/
	public static ResourceBlock create(long resourceBlockId) {
		return getPersistence().create(resourceBlockId);
	}

	/**
	* Removes the resource block with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block that was removed
	* @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	*/
	public static ResourceBlock remove(long resourceBlockId)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence().remove(resourceBlockId);
	}

	public static ResourceBlock updateImpl(ResourceBlock resourceBlock) {
		return getPersistence().updateImpl(resourceBlock);
	}

	/**
	* Returns the resource block with the primary key or throws a {@link NoSuchResourceBlockException} if it could not be found.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block
	* @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	*/
	public static ResourceBlock findByPrimaryKey(long resourceBlockId)
		throws com.liferay.portal.NoSuchResourceBlockException {
		return getPersistence().findByPrimaryKey(resourceBlockId);
	}

	/**
	* Returns the resource block with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block, or <code>null</code> if a resource block with the primary key could not be found
	*/
	public static ResourceBlock fetchByPrimaryKey(long resourceBlockId) {
		return getPersistence().fetchByPrimaryKey(resourceBlockId);
	}

	public static java.util.Map<java.io.Serializable, ResourceBlock> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the resource blocks.
	*
	* @return the resource blocks
	*/
	public static List<ResourceBlock> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of resource blocks
	*/
	public static List<ResourceBlock> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource blocks
	*/
	public static List<ResourceBlock> findAll(int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of resource blocks
	*/
	public static List<ResourceBlock> findAll(int start, int end,
		OrderByComparator<ResourceBlock> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the resource blocks from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of resource blocks.
	*
	* @return the number of resource blocks
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ResourceBlockPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ResourceBlockPersistence)PortalBeanLocatorUtil.locate(ResourceBlockPersistence.class.getName());

			ReferenceRegistry.registerReference(ResourceBlockUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static ResourceBlockPersistence _persistence;
}