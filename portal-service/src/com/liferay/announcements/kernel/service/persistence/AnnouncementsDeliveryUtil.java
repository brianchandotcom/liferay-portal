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

package com.liferay.announcements.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.announcements.kernel.model.AnnouncementsDelivery;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the announcements delivery service. This utility wraps {@link com.liferay.portlet.announcements.service.persistence.impl.AnnouncementsDeliveryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDeliveryPersistence
 * @see com.liferay.portlet.announcements.service.persistence.impl.AnnouncementsDeliveryPersistenceImpl
 * @generated
 */
@ProviderType
public class AnnouncementsDeliveryUtil {
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
	public static void clearCache(AnnouncementsDelivery announcementsDelivery) {
		getPersistence().clearCache(announcementsDelivery);
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
	public static List<AnnouncementsDelivery> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnnouncementsDelivery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AnnouncementsDelivery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AnnouncementsDelivery update(
		AnnouncementsDelivery announcementsDelivery) {
		return getPersistence().update(announcementsDelivery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AnnouncementsDelivery update(
		AnnouncementsDelivery announcementsDelivery,
		ServiceContext serviceContext) {
		return getPersistence().update(announcementsDelivery, serviceContext);
	}

	/**
	* Returns all the announcements deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching announcements deliveries
	*/
	public static List<AnnouncementsDelivery> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the announcements deliveries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @return the range of matching announcements deliveries
	*/
	public static List<AnnouncementsDelivery> findByUserId(long userId,
		int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the announcements deliveries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements deliveries
	*/
	public static List<AnnouncementsDelivery> findByUserId(long userId,
		int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the announcements deliveries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching announcements deliveries
	*/
	public static List<AnnouncementsDelivery> findByUserId(long userId,
		int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first announcements delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements delivery
	* @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	*/
	public static AnnouncementsDelivery findByUserId_First(long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws com.liferay.announcements.kernel.exception.NoSuchDeliveryException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first announcements delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	*/
	public static AnnouncementsDelivery fetchByUserId_First(long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last announcements delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements delivery
	* @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	*/
	public static AnnouncementsDelivery findByUserId_Last(long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws com.liferay.announcements.kernel.exception.NoSuchDeliveryException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last announcements delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	*/
	public static AnnouncementsDelivery fetchByUserId_Last(long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the announcements deliveries before and after the current announcements delivery in the ordered set where userId = &#63;.
	*
	* @param deliveryId the primary key of the current announcements delivery
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements delivery
	* @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	*/
	public static AnnouncementsDelivery[] findByUserId_PrevAndNext(
		long deliveryId, long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws com.liferay.announcements.kernel.exception.NoSuchDeliveryException {
		return getPersistence()
				   .findByUserId_PrevAndNext(deliveryId, userId,
			orderByComparator);
	}

	/**
	* Removes all the announcements deliveries where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of announcements deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching announcements deliveries
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the announcements delivery where userId = &#63; and type = &#63; or throws a {@link NoSuchDeliveryException} if it could not be found.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching announcements delivery
	* @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	*/
	public static AnnouncementsDelivery findByU_T(long userId,
		java.lang.String type)
		throws com.liferay.announcements.kernel.exception.NoSuchDeliveryException {
		return getPersistence().findByU_T(userId, type);
	}

	/**
	* Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	*/
	public static AnnouncementsDelivery fetchByU_T(long userId,
		java.lang.String type) {
		return getPersistence().fetchByU_T(userId, type);
	}

	/**
	* Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	*/
	public static AnnouncementsDelivery fetchByU_T(long userId,
		java.lang.String type, boolean retrieveFromCache) {
		return getPersistence().fetchByU_T(userId, type, retrieveFromCache);
	}

	/**
	* Removes the announcements delivery where userId = &#63; and type = &#63; from the database.
	*
	* @param userId the user ID
	* @param type the type
	* @return the announcements delivery that was removed
	*/
	public static AnnouncementsDelivery removeByU_T(long userId,
		java.lang.String type)
		throws com.liferay.announcements.kernel.exception.NoSuchDeliveryException {
		return getPersistence().removeByU_T(userId, type);
	}

	/**
	* Returns the number of announcements deliveries where userId = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @return the number of matching announcements deliveries
	*/
	public static int countByU_T(long userId, java.lang.String type) {
		return getPersistence().countByU_T(userId, type);
	}

	/**
	* Caches the announcements delivery in the entity cache if it is enabled.
	*
	* @param announcementsDelivery the announcements delivery
	*/
	public static void cacheResult(AnnouncementsDelivery announcementsDelivery) {
		getPersistence().cacheResult(announcementsDelivery);
	}

	/**
	* Caches the announcements deliveries in the entity cache if it is enabled.
	*
	* @param announcementsDeliveries the announcements deliveries
	*/
	public static void cacheResult(
		List<AnnouncementsDelivery> announcementsDeliveries) {
		getPersistence().cacheResult(announcementsDeliveries);
	}

	/**
	* Creates a new announcements delivery with the primary key. Does not add the announcements delivery to the database.
	*
	* @param deliveryId the primary key for the new announcements delivery
	* @return the new announcements delivery
	*/
	public static AnnouncementsDelivery create(long deliveryId) {
		return getPersistence().create(deliveryId);
	}

	/**
	* Removes the announcements delivery with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery that was removed
	* @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	*/
	public static AnnouncementsDelivery remove(long deliveryId)
		throws com.liferay.announcements.kernel.exception.NoSuchDeliveryException {
		return getPersistence().remove(deliveryId);
	}

	public static AnnouncementsDelivery updateImpl(
		AnnouncementsDelivery announcementsDelivery) {
		return getPersistence().updateImpl(announcementsDelivery);
	}

	/**
	* Returns the announcements delivery with the primary key or throws a {@link NoSuchDeliveryException} if it could not be found.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery
	* @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	*/
	public static AnnouncementsDelivery findByPrimaryKey(long deliveryId)
		throws com.liferay.announcements.kernel.exception.NoSuchDeliveryException {
		return getPersistence().findByPrimaryKey(deliveryId);
	}

	/**
	* Returns the announcements delivery with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery, or <code>null</code> if a announcements delivery with the primary key could not be found
	*/
	public static AnnouncementsDelivery fetchByPrimaryKey(long deliveryId) {
		return getPersistence().fetchByPrimaryKey(deliveryId);
	}

	public static java.util.Map<java.io.Serializable, AnnouncementsDelivery> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the announcements deliveries.
	*
	* @return the announcements deliveries
	*/
	public static List<AnnouncementsDelivery> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the announcements deliveries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @return the range of announcements deliveries
	*/
	public static List<AnnouncementsDelivery> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the announcements deliveries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of announcements deliveries
	*/
	public static List<AnnouncementsDelivery> findAll(int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the announcements deliveries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of announcements deliveries
	*/
	public static List<AnnouncementsDelivery> findAll(int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the announcements deliveries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of announcements deliveries.
	*
	* @return the number of announcements deliveries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static AnnouncementsDeliveryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AnnouncementsDeliveryPersistence)PortalBeanLocatorUtil.locate(AnnouncementsDeliveryPersistence.class.getName());

			ReferenceRegistry.registerReference(AnnouncementsDeliveryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static AnnouncementsDeliveryPersistence _persistence;
}