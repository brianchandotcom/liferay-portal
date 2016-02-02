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

import com.liferay.announcements.kernel.exception.NoSuchDeliveryException;
import com.liferay.announcements.kernel.model.AnnouncementsDelivery;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the announcements delivery service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.announcements.service.persistence.impl.AnnouncementsDeliveryPersistenceImpl
 * @see AnnouncementsDeliveryUtil
 * @generated
 */
@ProviderType
public interface AnnouncementsDeliveryPersistence extends BasePersistence<AnnouncementsDelivery> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnnouncementsDeliveryUtil} to access the announcements delivery persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the announcements deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching announcements deliveries
	*/
	public java.util.List<AnnouncementsDelivery> findByUserId(long userId);

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
	public java.util.List<AnnouncementsDelivery> findByUserId(long userId,
		int start, int end);

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
	public java.util.List<AnnouncementsDelivery> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsDelivery> orderByComparator);

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
	public java.util.List<AnnouncementsDelivery> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsDelivery> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first announcements delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements delivery
	* @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	*/
	public AnnouncementsDelivery findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws NoSuchDeliveryException;

	/**
	* Returns the first announcements delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	*/
	public AnnouncementsDelivery fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsDelivery> orderByComparator);

	/**
	* Returns the last announcements delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements delivery
	* @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	*/
	public AnnouncementsDelivery findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws NoSuchDeliveryException;

	/**
	* Returns the last announcements delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	*/
	public AnnouncementsDelivery fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsDelivery> orderByComparator);

	/**
	* Returns the announcements deliveries before and after the current announcements delivery in the ordered set where userId = &#63;.
	*
	* @param deliveryId the primary key of the current announcements delivery
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements delivery
	* @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	*/
	public AnnouncementsDelivery[] findByUserId_PrevAndNext(long deliveryId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws NoSuchDeliveryException;

	/**
	* Removes all the announcements deliveries where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of announcements deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching announcements deliveries
	*/
	public int countByUserId(long userId);

	/**
	* Returns the announcements delivery where userId = &#63; and type = &#63; or throws a {@link NoSuchDeliveryException} if it could not be found.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching announcements delivery
	* @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	*/
	public AnnouncementsDelivery findByU_T(long userId, java.lang.String type)
		throws NoSuchDeliveryException;

	/**
	* Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	*/
	public AnnouncementsDelivery fetchByU_T(long userId, java.lang.String type);

	/**
	* Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	*/
	public AnnouncementsDelivery fetchByU_T(long userId, java.lang.String type,
		boolean retrieveFromCache);

	/**
	* Removes the announcements delivery where userId = &#63; and type = &#63; from the database.
	*
	* @param userId the user ID
	* @param type the type
	* @return the announcements delivery that was removed
	*/
	public AnnouncementsDelivery removeByU_T(long userId, java.lang.String type)
		throws NoSuchDeliveryException;

	/**
	* Returns the number of announcements deliveries where userId = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @return the number of matching announcements deliveries
	*/
	public int countByU_T(long userId, java.lang.String type);

	/**
	* Caches the announcements delivery in the entity cache if it is enabled.
	*
	* @param announcementsDelivery the announcements delivery
	*/
	public void cacheResult(AnnouncementsDelivery announcementsDelivery);

	/**
	* Caches the announcements deliveries in the entity cache if it is enabled.
	*
	* @param announcementsDeliveries the announcements deliveries
	*/
	public void cacheResult(
		java.util.List<AnnouncementsDelivery> announcementsDeliveries);

	/**
	* Creates a new announcements delivery with the primary key. Does not add the announcements delivery to the database.
	*
	* @param deliveryId the primary key for the new announcements delivery
	* @return the new announcements delivery
	*/
	public AnnouncementsDelivery create(long deliveryId);

	/**
	* Removes the announcements delivery with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery that was removed
	* @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	*/
	public AnnouncementsDelivery remove(long deliveryId)
		throws NoSuchDeliveryException;

	public AnnouncementsDelivery updateImpl(
		AnnouncementsDelivery announcementsDelivery);

	/**
	* Returns the announcements delivery with the primary key or throws a {@link NoSuchDeliveryException} if it could not be found.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery
	* @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	*/
	public AnnouncementsDelivery findByPrimaryKey(long deliveryId)
		throws NoSuchDeliveryException;

	/**
	* Returns the announcements delivery with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery, or <code>null</code> if a announcements delivery with the primary key could not be found
	*/
	public AnnouncementsDelivery fetchByPrimaryKey(long deliveryId);

	@Override
	public java.util.Map<java.io.Serializable, AnnouncementsDelivery> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the announcements deliveries.
	*
	* @return the announcements deliveries
	*/
	public java.util.List<AnnouncementsDelivery> findAll();

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
	public java.util.List<AnnouncementsDelivery> findAll(int start, int end);

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
	public java.util.List<AnnouncementsDelivery> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsDelivery> orderByComparator);

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
	public java.util.List<AnnouncementsDelivery> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsDelivery> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the announcements deliveries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of announcements deliveries.
	*
	* @return the number of announcements deliveries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}