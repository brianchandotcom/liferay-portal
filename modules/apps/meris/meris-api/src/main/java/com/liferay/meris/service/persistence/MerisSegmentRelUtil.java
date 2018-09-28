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

package com.liferay.meris.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.meris.model.MerisSegmentRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the meris segment rel service. This utility wraps {@link com.liferay.meris.service.persistence.impl.MerisSegmentRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see MerisSegmentRelPersistence
 * @see com.liferay.meris.service.persistence.impl.MerisSegmentRelPersistenceImpl
 * @generated
 */
@ProviderType
public class MerisSegmentRelUtil {
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
	public static void clearCache(MerisSegmentRel merisSegmentRel) {
		getPersistence().clearCache(merisSegmentRel);
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
	public static List<MerisSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MerisSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MerisSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MerisSegmentRel update(MerisSegmentRel merisSegmentRel) {
		return getPersistence().update(merisSegmentRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MerisSegmentRel update(MerisSegmentRel merisSegmentRel,
		ServiceContext serviceContext) {
		return getPersistence().update(merisSegmentRel, serviceContext);
	}

	/**
	* Returns all the meris segment rels where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @return the matching meris segment rels
	*/
	public static List<MerisSegmentRel> findByMerisSegmentId(
		long merisSegmentId) {
		return getPersistence().findByMerisSegmentId(merisSegmentId);
	}

	/**
	* Returns a range of all the meris segment rels where merisSegmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param merisSegmentId the meris segment ID
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @return the range of matching meris segment rels
	*/
	public static List<MerisSegmentRel> findByMerisSegmentId(
		long merisSegmentId, int start, int end) {
		return getPersistence().findByMerisSegmentId(merisSegmentId, start, end);
	}

	/**
	* Returns an ordered range of all the meris segment rels where merisSegmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param merisSegmentId the meris segment ID
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching meris segment rels
	*/
	public static List<MerisSegmentRel> findByMerisSegmentId(
		long merisSegmentId, int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		return getPersistence()
				   .findByMerisSegmentId(merisSegmentId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the meris segment rels where merisSegmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param merisSegmentId the meris segment ID
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching meris segment rels
	*/
	public static List<MerisSegmentRel> findByMerisSegmentId(
		long merisSegmentId, int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByMerisSegmentId(merisSegmentId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment rel
	* @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	*/
	public static MerisSegmentRel findByMerisSegmentId_First(
		long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentRelException {
		return getPersistence()
				   .findByMerisSegmentId_First(merisSegmentId, orderByComparator);
	}

	/**
	* Returns the first meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	*/
	public static MerisSegmentRel fetchByMerisSegmentId_First(
		long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByMerisSegmentId_First(merisSegmentId,
			orderByComparator);
	}

	/**
	* Returns the last meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment rel
	* @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	*/
	public static MerisSegmentRel findByMerisSegmentId_Last(
		long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentRelException {
		return getPersistence()
				   .findByMerisSegmentId_Last(merisSegmentId, orderByComparator);
	}

	/**
	* Returns the last meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	*/
	public static MerisSegmentRel fetchByMerisSegmentId_Last(
		long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByMerisSegmentId_Last(merisSegmentId, orderByComparator);
	}

	/**
	* Returns the meris segment rels before and after the current meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentRelId the primary key of the current meris segment rel
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next meris segment rel
	* @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	*/
	public static MerisSegmentRel[] findByMerisSegmentId_PrevAndNext(
		long merisSegmentRelId, long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentRelException {
		return getPersistence()
				   .findByMerisSegmentId_PrevAndNext(merisSegmentRelId,
			merisSegmentId, orderByComparator);
	}

	/**
	* Removes all the meris segment rels where merisSegmentId = &#63; from the database.
	*
	* @param merisSegmentId the meris segment ID
	*/
	public static void removeByMerisSegmentId(long merisSegmentId) {
		getPersistence().removeByMerisSegmentId(merisSegmentId);
	}

	/**
	* Returns the number of meris segment rels where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @return the number of matching meris segment rels
	*/
	public static int countByMerisSegmentId(long merisSegmentId) {
		return getPersistence().countByMerisSegmentId(merisSegmentId);
	}

	/**
	* Returns all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching meris segment rels
	*/
	public static List<MerisSegmentRel> findByCN_CPK(long classNameId,
		long classPK) {
		return getPersistence().findByCN_CPK(classNameId, classPK);
	}

	/**
	* Returns a range of all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @return the range of matching meris segment rels
	*/
	public static List<MerisSegmentRel> findByCN_CPK(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByCN_CPK(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching meris segment rels
	*/
	public static List<MerisSegmentRel> findByCN_CPK(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		return getPersistence()
				   .findByCN_CPK(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching meris segment rels
	*/
	public static List<MerisSegmentRel> findByCN_CPK(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCN_CPK(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment rel
	* @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	*/
	public static MerisSegmentRel findByCN_CPK_First(long classNameId,
		long classPK, OrderByComparator<MerisSegmentRel> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentRelException {
		return getPersistence()
				   .findByCN_CPK_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	*/
	public static MerisSegmentRel fetchByCN_CPK_First(long classNameId,
		long classPK, OrderByComparator<MerisSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCN_CPK_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment rel
	* @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	*/
	public static MerisSegmentRel findByCN_CPK_Last(long classNameId,
		long classPK, OrderByComparator<MerisSegmentRel> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentRelException {
		return getPersistence()
				   .findByCN_CPK_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	*/
	public static MerisSegmentRel fetchByCN_CPK_Last(long classNameId,
		long classPK, OrderByComparator<MerisSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCN_CPK_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the meris segment rels before and after the current meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param merisSegmentRelId the primary key of the current meris segment rel
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next meris segment rel
	* @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	*/
	public static MerisSegmentRel[] findByCN_CPK_PrevAndNext(
		long merisSegmentRelId, long classNameId, long classPK,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentRelException {
		return getPersistence()
				   .findByCN_CPK_PrevAndNext(merisSegmentRelId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the meris segment rels where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByCN_CPK(long classNameId, long classPK) {
		getPersistence().removeByCN_CPK(classNameId, classPK);
	}

	/**
	* Returns the number of meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching meris segment rels
	*/
	public static int countByCN_CPK(long classNameId, long classPK) {
		return getPersistence().countByCN_CPK(classNameId, classPK);
	}

	/**
	* Caches the meris segment rel in the entity cache if it is enabled.
	*
	* @param merisSegmentRel the meris segment rel
	*/
	public static void cacheResult(MerisSegmentRel merisSegmentRel) {
		getPersistence().cacheResult(merisSegmentRel);
	}

	/**
	* Caches the meris segment rels in the entity cache if it is enabled.
	*
	* @param merisSegmentRels the meris segment rels
	*/
	public static void cacheResult(List<MerisSegmentRel> merisSegmentRels) {
		getPersistence().cacheResult(merisSegmentRels);
	}

	/**
	* Creates a new meris segment rel with the primary key. Does not add the meris segment rel to the database.
	*
	* @param merisSegmentRelId the primary key for the new meris segment rel
	* @return the new meris segment rel
	*/
	public static MerisSegmentRel create(long merisSegmentRelId) {
		return getPersistence().create(merisSegmentRelId);
	}

	/**
	* Removes the meris segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param merisSegmentRelId the primary key of the meris segment rel
	* @return the meris segment rel that was removed
	* @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	*/
	public static MerisSegmentRel remove(long merisSegmentRelId)
		throws com.liferay.meris.exception.NoSuchSegmentRelException {
		return getPersistence().remove(merisSegmentRelId);
	}

	public static MerisSegmentRel updateImpl(MerisSegmentRel merisSegmentRel) {
		return getPersistence().updateImpl(merisSegmentRel);
	}

	/**
	* Returns the meris segment rel with the primary key or throws a {@link NoSuchSegmentRelException} if it could not be found.
	*
	* @param merisSegmentRelId the primary key of the meris segment rel
	* @return the meris segment rel
	* @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	*/
	public static MerisSegmentRel findByPrimaryKey(long merisSegmentRelId)
		throws com.liferay.meris.exception.NoSuchSegmentRelException {
		return getPersistence().findByPrimaryKey(merisSegmentRelId);
	}

	/**
	* Returns the meris segment rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param merisSegmentRelId the primary key of the meris segment rel
	* @return the meris segment rel, or <code>null</code> if a meris segment rel with the primary key could not be found
	*/
	public static MerisSegmentRel fetchByPrimaryKey(long merisSegmentRelId) {
		return getPersistence().fetchByPrimaryKey(merisSegmentRelId);
	}

	public static java.util.Map<java.io.Serializable, MerisSegmentRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the meris segment rels.
	*
	* @return the meris segment rels
	*/
	public static List<MerisSegmentRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the meris segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @return the range of meris segment rels
	*/
	public static List<MerisSegmentRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the meris segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of meris segment rels
	*/
	public static List<MerisSegmentRel> findAll(int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the meris segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of meris segment rels
	*/
	public static List<MerisSegmentRel> findAll(int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the meris segment rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of meris segment rels.
	*
	* @return the number of meris segment rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MerisSegmentRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MerisSegmentRelPersistence, MerisSegmentRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MerisSegmentRelPersistence.class);

		ServiceTracker<MerisSegmentRelPersistence, MerisSegmentRelPersistence> serviceTracker =
			new ServiceTracker<MerisSegmentRelPersistence, MerisSegmentRelPersistence>(bundle.getBundleContext(),
				MerisSegmentRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}