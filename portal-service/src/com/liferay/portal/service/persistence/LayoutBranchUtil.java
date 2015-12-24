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
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the layout branch service. This utility wraps {@link com.liferay.portal.service.persistence.impl.LayoutBranchPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchPersistence
 * @see com.liferay.portal.service.persistence.impl.LayoutBranchPersistenceImpl
 * @generated
 */
@ProviderType
public class LayoutBranchUtil {
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
	public static void clearCache(LayoutBranch layoutBranch) {
		getPersistence().clearCache(layoutBranch);
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
	public static List<LayoutBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static LayoutBranch update(LayoutBranch layoutBranch) {
		return getPersistence().update(layoutBranch);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static LayoutBranch update(LayoutBranch layoutBranch,
		ServiceContext serviceContext) {
		return getPersistence().update(layoutBranch, serviceContext);
	}

	/**
	* Returns all the layout branchs where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the matching layout branchs
	*/
	public static List<LayoutBranch> findByLayoutSetBranchId(
		long layoutSetBranchId) {
		return getPersistence().findByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns a range of all the layout branchs where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @return the range of matching layout branchs
	*/
	public static List<LayoutBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end) {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end);
	}

	/**
	* Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout branchs
	*/
	public static List<LayoutBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout branchs
	*/
	public static List<LayoutBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout branch
	* @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	*/
	public static LayoutBranch findByLayoutSetBranchId_First(
		long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence()
				   .findByLayoutSetBranchId_First(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the first layout branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	*/
	public static LayoutBranch fetchByLayoutSetBranchId_First(
		long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetBranchId_First(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the last layout branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout branch
	* @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	*/
	public static LayoutBranch findByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence()
				   .findByLayoutSetBranchId_Last(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the last layout branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout branch, or <code>null</code> if a matching layout branch could not be found
	*/
	public static LayoutBranch fetchByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetBranchId_Last(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the layout branchs before and after the current layout branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutBranchId the primary key of the current layout branch
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout branch
	* @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	*/
	public static LayoutBranch[] findByLayoutSetBranchId_PrevAndNext(
		long layoutBranchId, long layoutSetBranchId,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence()
				   .findByLayoutSetBranchId_PrevAndNext(layoutBranchId,
			layoutSetBranchId, orderByComparator);
	}

	/**
	* Removes all the layout branchs where layoutSetBranchId = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	*/
	public static void removeByLayoutSetBranchId(long layoutSetBranchId) {
		getPersistence().removeByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns the number of layout branchs where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the number of matching layout branchs
	*/
	public static int countByLayoutSetBranchId(long layoutSetBranchId) {
		return getPersistence().countByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching layout branchs
	*/
	public static List<LayoutBranch> findByL_P(long layoutSetBranchId, long plid) {
		return getPersistence().findByL_P(layoutSetBranchId, plid);
	}

	/**
	* Returns a range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @return the range of matching layout branchs
	*/
	public static List<LayoutBranch> findByL_P(long layoutSetBranchId,
		long plid, int start, int end) {
		return getPersistence().findByL_P(layoutSetBranchId, plid, start, end);
	}

	/**
	* Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout branchs
	*/
	public static List<LayoutBranch> findByL_P(long layoutSetBranchId,
		long plid, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .findByL_P(layoutSetBranchId, plid, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout branchs
	*/
	public static List<LayoutBranch> findByL_P(long layoutSetBranchId,
		long plid, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_P(layoutSetBranchId, plid, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout branch
	* @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	*/
	public static LayoutBranch findByL_P_First(long layoutSetBranchId,
		long plid, OrderByComparator<LayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence()
				   .findByL_P_First(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	*/
	public static LayoutBranch fetchByL_P_First(long layoutSetBranchId,
		long plid, OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_First(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Returns the last layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout branch
	* @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	*/
	public static LayoutBranch findByL_P_Last(long layoutSetBranchId,
		long plid, OrderByComparator<LayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence()
				   .findByL_P_Last(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Returns the last layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout branch, or <code>null</code> if a matching layout branch could not be found
	*/
	public static LayoutBranch fetchByL_P_Last(long layoutSetBranchId,
		long plid, OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_Last(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Returns the layout branchs before and after the current layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutBranchId the primary key of the current layout branch
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout branch
	* @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	*/
	public static LayoutBranch[] findByL_P_PrevAndNext(long layoutBranchId,
		long layoutSetBranchId, long plid,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence()
				   .findByL_P_PrevAndNext(layoutBranchId, layoutSetBranchId,
			plid, orderByComparator);
	}

	/**
	* Removes all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	*/
	public static void removeByL_P(long layoutSetBranchId, long plid) {
		getPersistence().removeByL_P(layoutSetBranchId, plid);
	}

	/**
	* Returns the number of layout branchs where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the number of matching layout branchs
	*/
	public static int countByL_P(long layoutSetBranchId, long plid) {
		return getPersistence().countByL_P(layoutSetBranchId, plid);
	}

	/**
	* Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; or throws a {@link NoSuchLayoutBranchException} if it could not be found.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param name the name
	* @return the matching layout branch
	* @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	*/
	public static LayoutBranch findByL_P_N(long layoutSetBranchId, long plid,
		java.lang.String name)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence().findByL_P_N(layoutSetBranchId, plid, name);
	}

	/**
	* Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param name the name
	* @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	*/
	public static LayoutBranch fetchByL_P_N(long layoutSetBranchId, long plid,
		java.lang.String name) {
		return getPersistence().fetchByL_P_N(layoutSetBranchId, plid, name);
	}

	/**
	* Returns the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	*/
	public static LayoutBranch fetchByL_P_N(long layoutSetBranchId, long plid,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByL_P_N(layoutSetBranchId, plid, name,
			retrieveFromCache);
	}

	/**
	* Removes the layout branch where layoutSetBranchId = &#63; and plid = &#63; and name = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param name the name
	* @return the layout branch that was removed
	*/
	public static LayoutBranch removeByL_P_N(long layoutSetBranchId, long plid,
		java.lang.String name)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence().removeByL_P_N(layoutSetBranchId, plid, name);
	}

	/**
	* Returns the number of layout branchs where layoutSetBranchId = &#63; and plid = &#63; and name = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param name the name
	* @return the number of matching layout branchs
	*/
	public static int countByL_P_N(long layoutSetBranchId, long plid,
		java.lang.String name) {
		return getPersistence().countByL_P_N(layoutSetBranchId, plid, name);
	}

	/**
	* Returns all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @return the matching layout branchs
	*/
	public static List<LayoutBranch> findByL_P_M(long layoutSetBranchId,
		long plid, boolean master) {
		return getPersistence().findByL_P_M(layoutSetBranchId, plid, master);
	}

	/**
	* Returns a range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @return the range of matching layout branchs
	*/
	public static List<LayoutBranch> findByL_P_M(long layoutSetBranchId,
		long plid, boolean master, int start, int end) {
		return getPersistence()
				   .findByL_P_M(layoutSetBranchId, plid, master, start, end);
	}

	/**
	* Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout branchs
	*/
	public static List<LayoutBranch> findByL_P_M(long layoutSetBranchId,
		long plid, boolean master, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .findByL_P_M(layoutSetBranchId, plid, master, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout branchs
	*/
	public static List<LayoutBranch> findByL_P_M(long layoutSetBranchId,
		long plid, boolean master, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_P_M(layoutSetBranchId, plid, master, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout branch
	* @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	*/
	public static LayoutBranch findByL_P_M_First(long layoutSetBranchId,
		long plid, boolean master,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence()
				   .findByL_P_M_First(layoutSetBranchId, plid, master,
			orderByComparator);
	}

	/**
	* Returns the first layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout branch, or <code>null</code> if a matching layout branch could not be found
	*/
	public static LayoutBranch fetchByL_P_M_First(long layoutSetBranchId,
		long plid, boolean master,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_M_First(layoutSetBranchId, plid, master,
			orderByComparator);
	}

	/**
	* Returns the last layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout branch
	* @throws NoSuchLayoutBranchException if a matching layout branch could not be found
	*/
	public static LayoutBranch findByL_P_M_Last(long layoutSetBranchId,
		long plid, boolean master,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence()
				   .findByL_P_M_Last(layoutSetBranchId, plid, master,
			orderByComparator);
	}

	/**
	* Returns the last layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout branch, or <code>null</code> if a matching layout branch could not be found
	*/
	public static LayoutBranch fetchByL_P_M_Last(long layoutSetBranchId,
		long plid, boolean master,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_M_Last(layoutSetBranchId, plid, master,
			orderByComparator);
	}

	/**
	* Returns the layout branchs before and after the current layout branch in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* @param layoutBranchId the primary key of the current layout branch
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout branch
	* @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	*/
	public static LayoutBranch[] findByL_P_M_PrevAndNext(long layoutBranchId,
		long layoutSetBranchId, long plid, boolean master,
		OrderByComparator<LayoutBranch> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence()
				   .findByL_P_M_PrevAndNext(layoutBranchId, layoutSetBranchId,
			plid, master, orderByComparator);
	}

	/**
	* Removes all the layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	*/
	public static void removeByL_P_M(long layoutSetBranchId, long plid,
		boolean master) {
		getPersistence().removeByL_P_M(layoutSetBranchId, plid, master);
	}

	/**
	* Returns the number of layout branchs where layoutSetBranchId = &#63; and plid = &#63; and master = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param master the master
	* @return the number of matching layout branchs
	*/
	public static int countByL_P_M(long layoutSetBranchId, long plid,
		boolean master) {
		return getPersistence().countByL_P_M(layoutSetBranchId, plid, master);
	}

	/**
	* Caches the layout branch in the entity cache if it is enabled.
	*
	* @param layoutBranch the layout branch
	*/
	public static void cacheResult(LayoutBranch layoutBranch) {
		getPersistence().cacheResult(layoutBranch);
	}

	/**
	* Caches the layout branchs in the entity cache if it is enabled.
	*
	* @param layoutBranchs the layout branchs
	*/
	public static void cacheResult(List<LayoutBranch> layoutBranchs) {
		getPersistence().cacheResult(layoutBranchs);
	}

	/**
	* Creates a new layout branch with the primary key. Does not add the layout branch to the database.
	*
	* @param layoutBranchId the primary key for the new layout branch
	* @return the new layout branch
	*/
	public static LayoutBranch create(long layoutBranchId) {
		return getPersistence().create(layoutBranchId);
	}

	/**
	* Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranchId the primary key of the layout branch
	* @return the layout branch that was removed
	* @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	*/
	public static LayoutBranch remove(long layoutBranchId)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence().remove(layoutBranchId);
	}

	public static LayoutBranch updateImpl(LayoutBranch layoutBranch) {
		return getPersistence().updateImpl(layoutBranch);
	}

	/**
	* Returns the layout branch with the primary key or throws a {@link NoSuchLayoutBranchException} if it could not be found.
	*
	* @param layoutBranchId the primary key of the layout branch
	* @return the layout branch
	* @throws NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	*/
	public static LayoutBranch findByPrimaryKey(long layoutBranchId)
		throws com.liferay.portal.NoSuchLayoutBranchException {
		return getPersistence().findByPrimaryKey(layoutBranchId);
	}

	/**
	* Returns the layout branch with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutBranchId the primary key of the layout branch
	* @return the layout branch, or <code>null</code> if a layout branch with the primary key could not be found
	*/
	public static LayoutBranch fetchByPrimaryKey(long layoutBranchId) {
		return getPersistence().fetchByPrimaryKey(layoutBranchId);
	}

	public static java.util.Map<java.io.Serializable, LayoutBranch> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the layout branchs.
	*
	* @return the layout branchs
	*/
	public static List<LayoutBranch> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @return the range of layout branchs
	*/
	public static List<LayoutBranch> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout branchs
	*/
	public static List<LayoutBranch> findAll(int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout branchs
	*/
	public static List<LayoutBranch> findAll(int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the layout branchs from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of layout branchs.
	*
	* @return the number of layout branchs
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutBranchPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutBranchPersistence)PortalBeanLocatorUtil.locate(LayoutBranchPersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutBranchUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static LayoutBranchPersistence _persistence;
}