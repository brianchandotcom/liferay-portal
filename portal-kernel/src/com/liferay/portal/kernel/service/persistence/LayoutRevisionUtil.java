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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.util.List;

/**
 * The persistence utility for the layout revision service. This utility wraps {@link com.liferay.portal.service.persistence.impl.LayoutRevisionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionPersistence
 * @see com.liferay.portal.service.persistence.impl.LayoutRevisionPersistenceImpl
 * @generated
 */
@ProviderType
public class LayoutRevisionUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(LayoutRevision layoutRevision) {
		getPersistence().clearCache(layoutRevision);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutRevision> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutRevision> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutRevision> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutRevision update(LayoutRevision layoutRevision) {
		return getPersistence().update(layoutRevision);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutRevision update(LayoutRevision layoutRevision,
		ServiceContext serviceContext) {
		return getPersistence().update(layoutRevision, serviceContext);
	}

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId) {
		return getPersistence().findByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end) {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByLayoutSetBranchId_First(
		long layoutSetBranchId,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByLayoutSetBranchId_First(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByLayoutSetBranchId_First(
		long layoutSetBranchId,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetBranchId_First(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByLayoutSetBranchId_Last(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetBranchId_Last(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByLayoutSetBranchId_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByLayoutSetBranchId_PrevAndNext(layoutRevisionId,
			layoutSetBranchId, orderByComparator);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	*/
	public static void removeByLayoutSetBranchId(long layoutSetBranchId) {
		getPersistence().removeByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the number of matching layout revisions
	*/
	public static int countByLayoutSetBranchId(long layoutSetBranchId) {
		return getPersistence().countByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns all the layout revisions where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByPlid(long plid) {
		return getPersistence().findByPlid(plid);
	}

	/**
	* Returns a range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByPlid(long plid, int start, int end) {
		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByPlid(long plid, int start,
		int end, OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByPlid(long plid, int start,
		int end, OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByPlid(plid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByPlid_First(long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByPlid_First(long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByPlid_Last(long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByPlid_Last(long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByPlid_PrevAndNext(
		long layoutRevisionId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByPlid_PrevAndNext(layoutRevisionId, plid,
			orderByComparator);
	}

	/**
	* Removes all the layout revisions where plid = &#63; from the database.
	*
	* @param plid the plid
	*/
	public static void removeByPlid(long plid) {
		getPersistence().removeByPlid(plid);
	}

	/**
	* Returns the number of layout revisions where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public static int countByPlid(long plid) {
		return getPersistence().countByPlid(plid);
	}

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H(long layoutSetBranchId,
		boolean head) {
		return getPersistence().findByL_H(layoutSetBranchId, head);
	}

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H(long layoutSetBranchId,
		boolean head, int start, int end) {
		return getPersistence().findByL_H(layoutSetBranchId, head, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H(long layoutSetBranchId,
		boolean head, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByL_H(layoutSetBranchId, head, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H(long layoutSetBranchId,
		boolean head, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_H(layoutSetBranchId, head, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_H_First(long layoutSetBranchId,
		boolean head, OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_H_First(layoutSetBranchId, head, orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_H_First(long layoutSetBranchId,
		boolean head, OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_H_First(layoutSetBranchId, head, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_H_Last(long layoutSetBranchId,
		boolean head, OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_H_Last(layoutSetBranchId, head, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_H_Last(long layoutSetBranchId,
		boolean head, OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_H_Last(layoutSetBranchId, head, orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByL_H_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, boolean head,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_H_PrevAndNext(layoutRevisionId, layoutSetBranchId,
			head, orderByComparator);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	*/
	public static void removeByL_H(long layoutSetBranchId, boolean head) {
		getPersistence().removeByL_H(layoutSetBranchId, head);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @return the number of matching layout revisions
	*/
	public static int countByL_H(long layoutSetBranchId, boolean head) {
		return getPersistence().countByL_H(layoutSetBranchId, head);
	}

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P(long layoutSetBranchId,
		long plid) {
		return getPersistence().findByL_P(layoutSetBranchId, plid);
	}

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P(long layoutSetBranchId,
		long plid, int start, int end) {
		return getPersistence().findByL_P(layoutSetBranchId, plid, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P(long layoutSetBranchId,
		long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByL_P(layoutSetBranchId, plid, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P(long layoutSetBranchId,
		long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_P(layoutSetBranchId, plid, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_P_First(long layoutSetBranchId,
		long plid, OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_P_First(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_P_First(long layoutSetBranchId,
		long plid, OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_First(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_P_Last(long layoutSetBranchId,
		long plid, OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_P_Last(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_P_Last(long layoutSetBranchId,
		long plid, OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_Last(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByL_P_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_P_PrevAndNext(layoutRevisionId, layoutSetBranchId,
			plid, orderByComparator);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	*/
	public static void removeByL_P(long layoutSetBranchId, long plid) {
		getPersistence().removeByL_P(layoutSetBranchId, plid);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public static int countByL_P(long layoutSetBranchId, long plid) {
		return getPersistence().countByL_P(layoutSetBranchId, plid);
	}

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByL_S(long layoutSetBranchId,
		int status) {
		return getPersistence().findByL_S(layoutSetBranchId, status);
	}

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_S(long layoutSetBranchId,
		int status, int start, int end) {
		return getPersistence().findByL_S(layoutSetBranchId, status, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_S(long layoutSetBranchId,
		int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByL_S(layoutSetBranchId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_S(long layoutSetBranchId,
		int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_S(layoutSetBranchId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_S_First(long layoutSetBranchId,
		int status, OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_S_First(layoutSetBranchId, status, orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_S_First(long layoutSetBranchId,
		int status, OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_S_First(layoutSetBranchId, status,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_S_Last(long layoutSetBranchId,
		int status, OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_S_Last(layoutSetBranchId, status, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_S_Last(long layoutSetBranchId,
		int status, OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_S_Last(layoutSetBranchId, status, orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByL_S_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, int status,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_S_PrevAndNext(layoutRevisionId, layoutSetBranchId,
			status, orderByComparator);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and status = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	*/
	public static void removeByL_S(long layoutSetBranchId, int status) {
		getPersistence().removeByL_S(layoutSetBranchId, status);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @return the number of matching layout revisions
	*/
	public static int countByL_S(long layoutSetBranchId, int status) {
		return getPersistence().countByL_S(layoutSetBranchId, status);
	}

	/**
	* Returns all the layout revisions where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByH_P(boolean head, long plid) {
		return getPersistence().findByH_P(head, plid);
	}

	/**
	* Returns a range of all the layout revisions where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByH_P(boolean head, long plid,
		int start, int end) {
		return getPersistence().findByH_P(head, plid, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByH_P(boolean head, long plid,
		int start, int end, OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByH_P(head, plid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByH_P(boolean head, long plid,
		int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByH_P(head, plid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByH_P_First(boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence().findByH_P_First(head, plid, orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByH_P_First(boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence().fetchByH_P_First(head, plid, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByH_P_Last(boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence().findByH_P_Last(head, plid, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByH_P_Last(boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence().fetchByH_P_Last(head, plid, orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByH_P_PrevAndNext(
		long layoutRevisionId, boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByH_P_PrevAndNext(layoutRevisionId, head, plid,
			orderByComparator);
	}

	/**
	* Removes all the layout revisions where head = &#63; and plid = &#63; from the database.
	*
	* @param head the head
	* @param plid the plid
	*/
	public static void removeByH_P(boolean head, long plid) {
		getPersistence().removeByH_P(head, plid);
	}

	/**
	* Returns the number of layout revisions where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public static int countByH_P(boolean head, long plid) {
		return getPersistence().countByH_P(head, plid);
	}

	/**
	* Returns all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByP_NotS(long plid, int status) {
		return getPersistence().findByP_NotS(plid, status);
	}

	/**
	* Returns a range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByP_NotS(long plid, int status,
		int start, int end) {
		return getPersistence().findByP_NotS(plid, status, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByP_NotS(long plid, int status,
		int start, int end, OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByP_NotS(plid, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByP_NotS(long plid, int status,
		int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByP_NotS(plid, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByP_NotS_First(long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByP_NotS_First(plid, status, orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByP_NotS_First(long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByP_NotS_First(plid, status, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByP_NotS_Last(long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByP_NotS_Last(plid, status, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByP_NotS_Last(long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByP_NotS_Last(plid, status, orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByP_NotS_PrevAndNext(
		long layoutRevisionId, long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByP_NotS_PrevAndNext(layoutRevisionId, plid, status,
			orderByComparator);
	}

	/**
	* Removes all the layout revisions where plid = &#63; and status &ne; &#63; from the database.
	*
	* @param plid the plid
	* @param status the status
	*/
	public static void removeByP_NotS(long plid, int status) {
		getPersistence().removeByP_NotS(plid, status);
	}

	/**
	* Returns the number of layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @return the number of matching layout revisions
	*/
	public static int countByP_NotS(long plid, int status) {
		return getPersistence().countByP_NotS(plid, status);
	}

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid) {
		return getPersistence()
				   .findByL_L_P(layoutSetBranchId, layoutBranchId, plid);
	}

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid, int start, int end) {
		return getPersistence()
				   .findByL_L_P(layoutSetBranchId, layoutBranchId, plid, start,
			end);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByL_L_P(layoutSetBranchId, layoutBranchId, plid, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_L_P(layoutSetBranchId, layoutBranchId, plid, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_L_P_First(long layoutSetBranchId,
		long layoutBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_L_P_First(layoutSetBranchId, layoutBranchId, plid,
			orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_L_P_First(long layoutSetBranchId,
		long layoutBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_L_P_First(layoutSetBranchId, layoutBranchId, plid,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_L_P_Last(long layoutSetBranchId,
		long layoutBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_L_P_Last(layoutSetBranchId, layoutBranchId, plid,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_L_P_Last(long layoutSetBranchId,
		long layoutBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_L_P_Last(layoutSetBranchId, layoutBranchId, plid,
			orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByL_L_P_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, long layoutBranchId,
		long plid, OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_L_P_PrevAndNext(layoutRevisionId,
			layoutSetBranchId, layoutBranchId, plid, orderByComparator);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	*/
	public static void removeByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid) {
		getPersistence().removeByL_L_P(layoutSetBranchId, layoutBranchId, plid);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public static int countByL_L_P(long layoutSetBranchId, long layoutBranchId,
		long plid) {
		return getPersistence()
				   .countByL_L_P(layoutSetBranchId, layoutBranchId, plid);
	}

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid) {
		return getPersistence()
				   .findByL_P_P(layoutSetBranchId, parentLayoutRevisionId, plid);
	}

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid, int start, int end) {
		return getPersistence()
				   .findByL_P_P(layoutSetBranchId, parentLayoutRevisionId,
			plid, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByL_P_P(layoutSetBranchId, parentLayoutRevisionId,
			plid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_P_P(layoutSetBranchId, parentLayoutRevisionId,
			plid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_P_P_First(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_P_P_First(layoutSetBranchId,
			parentLayoutRevisionId, plid, orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_P_P_First(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_P_First(layoutSetBranchId,
			parentLayoutRevisionId, plid, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_P_P_Last(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_P_P_Last(layoutSetBranchId, parentLayoutRevisionId,
			plid, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_P_P_Last(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_P_Last(layoutSetBranchId,
			parentLayoutRevisionId, plid, orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByL_P_P_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_P_P_PrevAndNext(layoutRevisionId,
			layoutSetBranchId, parentLayoutRevisionId, plid, orderByComparator);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	*/
	public static void removeByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid) {
		getPersistence()
			.removeByL_P_P(layoutSetBranchId, parentLayoutRevisionId, plid);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public static int countByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid) {
		return getPersistence()
				   .countByL_P_P(layoutSetBranchId, parentLayoutRevisionId, plid);
	}

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or throws a {@link NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_H_P(long layoutSetBranchId,
		boolean head, long plid)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence().findByL_H_P(layoutSetBranchId, head, plid);
	}

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_H_P(long layoutSetBranchId,
		boolean head, long plid) {
		return getPersistence().fetchByL_H_P(layoutSetBranchId, head, plid);
	}

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_H_P(long layoutSetBranchId,
		boolean head, long plid, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByL_H_P(layoutSetBranchId, head, plid,
			retrieveFromCache);
	}

	/**
	* Removes the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the layout revision that was removed
	*/
	public static LayoutRevision removeByL_H_P(long layoutSetBranchId,
		boolean head, long plid)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence().removeByL_H_P(layoutSetBranchId, head, plid);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public static int countByL_H_P(long layoutSetBranchId, boolean head,
		long plid) {
		return getPersistence().countByL_H_P(layoutSetBranchId, head, plid);
	}

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid) {
		return getPersistence()
				   .findByL_H_P_Collection(layoutSetBranchId, head, plid);
	}

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid, int start, int end) {
		return getPersistence()
				   .findByL_H_P_Collection(layoutSetBranchId, head, plid,
			start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByL_H_P_Collection(layoutSetBranchId, head, plid,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_H_P_Collection(layoutSetBranchId, head, plid,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_H_P_Collection_First(
		long layoutSetBranchId, boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_H_P_Collection_First(layoutSetBranchId, head, plid,
			orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_H_P_Collection_First(
		long layoutSetBranchId, boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_H_P_Collection_First(layoutSetBranchId, head,
			plid, orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_H_P_Collection_Last(
		long layoutSetBranchId, boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_H_P_Collection_Last(layoutSetBranchId, head, plid,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_H_P_Collection_Last(
		long layoutSetBranchId, boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_H_P_Collection_Last(layoutSetBranchId, head, plid,
			orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByL_H_P_Collection_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_H_P_Collection_PrevAndNext(layoutRevisionId,
			layoutSetBranchId, head, plid, orderByComparator);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	*/
	public static void removeByL_H_P_Collection(long layoutSetBranchId,
		boolean head, long plid) {
		getPersistence().removeByL_H_P_Collection(layoutSetBranchId, head, plid);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public static int countByL_H_P_Collection(long layoutSetBranchId,
		boolean head, long plid) {
		return getPersistence()
				   .countByL_H_P_Collection(layoutSetBranchId, head, plid);
	}

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H_S(long layoutSetBranchId,
		boolean head, int status) {
		return getPersistence().findByL_H_S(layoutSetBranchId, head, status);
	}

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H_S(long layoutSetBranchId,
		boolean head, int status, int start, int end) {
		return getPersistence()
				   .findByL_H_S(layoutSetBranchId, head, status, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H_S(long layoutSetBranchId,
		boolean head, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByL_H_S(layoutSetBranchId, head, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_H_S(long layoutSetBranchId,
		boolean head, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_H_S(layoutSetBranchId, head, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_H_S_First(long layoutSetBranchId,
		boolean head, int status,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_H_S_First(layoutSetBranchId, head, status,
			orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_H_S_First(long layoutSetBranchId,
		boolean head, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_H_S_First(layoutSetBranchId, head, status,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_H_S_Last(long layoutSetBranchId,
		boolean head, int status,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_H_S_Last(layoutSetBranchId, head, status,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_H_S_Last(long layoutSetBranchId,
		boolean head, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_H_S_Last(layoutSetBranchId, head, status,
			orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByL_H_S_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, boolean head,
		int status, OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_H_S_PrevAndNext(layoutRevisionId,
			layoutSetBranchId, head, status, orderByComparator);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	*/
	public static void removeByL_H_S(long layoutSetBranchId, boolean head,
		int status) {
		getPersistence().removeByL_H_S(layoutSetBranchId, head, status);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @return the number of matching layout revisions
	*/
	public static int countByL_H_S(long layoutSetBranchId, boolean head,
		int status) {
		return getPersistence().countByL_H_S(layoutSetBranchId, head, status);
	}

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @return the matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P_S(long layoutSetBranchId,
		long plid, int status) {
		return getPersistence().findByL_P_S(layoutSetBranchId, plid, status);
	}

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P_S(long layoutSetBranchId,
		long plid, int status, int start, int end) {
		return getPersistence()
				   .findByL_P_S(layoutSetBranchId, plid, status, start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P_S(long layoutSetBranchId,
		long plid, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .findByL_P_S(layoutSetBranchId, plid, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public static List<LayoutRevision> findByL_P_S(long layoutSetBranchId,
		long plid, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByL_P_S(layoutSetBranchId, plid, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_P_S_First(long layoutSetBranchId,
		long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_P_S_First(layoutSetBranchId, plid, status,
			orderByComparator);
	}

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_P_S_First(long layoutSetBranchId,
		long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_S_First(layoutSetBranchId, plid, status,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_P_S_Last(long layoutSetBranchId,
		long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_P_S_Last(layoutSetBranchId, plid, status,
			orderByComparator);
	}

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_P_S_Last(long layoutSetBranchId,
		long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence()
				   .fetchByL_P_S_Last(layoutSetBranchId, plid, status,
			orderByComparator);
	}

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision[] findByL_P_S_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_P_S_PrevAndNext(layoutRevisionId,
			layoutSetBranchId, plid, status, orderByComparator);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	*/
	public static void removeByL_P_S(long layoutSetBranchId, long plid,
		int status) {
		getPersistence().removeByL_P_S(layoutSetBranchId, plid, status);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @return the number of matching layout revisions
	*/
	public static int countByL_P_S(long layoutSetBranchId, long plid, int status) {
		return getPersistence().countByL_P_S(layoutSetBranchId, plid, status);
	}

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; or throws a {@link NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public static LayoutRevision findByL_L_H_P(long layoutSetBranchId,
		long layoutBranchId, boolean head, long plid)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .findByL_L_H_P(layoutSetBranchId, layoutBranchId, head, plid);
	}

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_L_H_P(long layoutSetBranchId,
		long layoutBranchId, boolean head, long plid) {
		return getPersistence()
				   .fetchByL_L_H_P(layoutSetBranchId, layoutBranchId, head, plid);
	}

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public static LayoutRevision fetchByL_L_H_P(long layoutSetBranchId,
		long layoutBranchId, boolean head, long plid, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByL_L_H_P(layoutSetBranchId, layoutBranchId, head,
			plid, retrieveFromCache);
	}

	/**
	* Removes the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @return the layout revision that was removed
	*/
	public static LayoutRevision removeByL_L_H_P(long layoutSetBranchId,
		long layoutBranchId, boolean head, long plid)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence()
				   .removeByL_L_H_P(layoutSetBranchId, layoutBranchId, head,
			plid);
	}

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public static int countByL_L_H_P(long layoutSetBranchId,
		long layoutBranchId, boolean head, long plid) {
		return getPersistence()
				   .countByL_L_H_P(layoutSetBranchId, layoutBranchId, head, plid);
	}

	/**
	* Caches the layout revision in the entity cache if it is enabled.
	*
	* @param layoutRevision the layout revision
	*/
	public static void cacheResult(LayoutRevision layoutRevision) {
		getPersistence().cacheResult(layoutRevision);
	}

	/**
	* Caches the layout revisions in the entity cache if it is enabled.
	*
	* @param layoutRevisions the layout revisions
	*/
	public static void cacheResult(List<LayoutRevision> layoutRevisions) {
		getPersistence().cacheResult(layoutRevisions);
	}

	/**
	* Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	*
	* @param layoutRevisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	public static LayoutRevision create(long layoutRevisionId) {
		return getPersistence().create(layoutRevisionId);
	}

	/**
	* Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision that was removed
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision remove(long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence().remove(layoutRevisionId);
	}

	public static LayoutRevision updateImpl(LayoutRevision layoutRevision) {
		return getPersistence().updateImpl(layoutRevision);
	}

	/**
	* Returns the layout revision with the primary key or throws a {@link NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision findByPrimaryKey(long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException {
		return getPersistence().findByPrimaryKey(layoutRevisionId);
	}

	/**
	* Returns the layout revision with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	*/
	public static LayoutRevision fetchByPrimaryKey(long layoutRevisionId) {
		return getPersistence().fetchByPrimaryKey(layoutRevisionId);
	}

	public static java.util.Map<java.io.Serializable, LayoutRevision> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the layout revisions.
	*
	* @return the layout revisions
	*/
	public static List<LayoutRevision> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of layout revisions
	*/
	public static List<LayoutRevision> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout revisions
	*/
	public static List<LayoutRevision> findAll(int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout revisions
	*/
	public static List<LayoutRevision> findAll(int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the layout revisions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of layout revisions.
	*
	* @return the number of layout revisions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutRevisionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutRevisionPersistence)PortalBeanLocatorUtil.locate(LayoutRevisionPersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutRevisionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static LayoutRevisionPersistence _persistence;
}