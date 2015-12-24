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

package com.liferay.portlet.announcements.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.announcements.model.AnnouncementsFlag;

import java.util.List;

/**
 * The persistence utility for the announcements flag service. This utility wraps {@link com.liferay.portlet.announcements.service.persistence.impl.AnnouncementsFlagPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlagPersistence
 * @see com.liferay.portlet.announcements.service.persistence.impl.AnnouncementsFlagPersistenceImpl
 * @generated
 */
@ProviderType
public class AnnouncementsFlagUtil {
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
	public static void clearCache(AnnouncementsFlag announcementsFlag) {
		getPersistence().clearCache(announcementsFlag);
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
	public static List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AnnouncementsFlag update(AnnouncementsFlag announcementsFlag) {
		return getPersistence().update(announcementsFlag);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AnnouncementsFlag update(
		AnnouncementsFlag announcementsFlag, ServiceContext serviceContext) {
		return getPersistence().update(announcementsFlag, serviceContext);
	}

	/**
	* Returns all the announcements flags where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching announcements flags
	*/
	public static List<AnnouncementsFlag> findByEntryId(long entryId) {
		return getPersistence().findByEntryId(entryId);
	}

	/**
	* Returns a range of all the announcements flags where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of announcements flags
	* @param end the upper bound of the range of announcements flags (not inclusive)
	* @return the range of matching announcements flags
	*/
	public static List<AnnouncementsFlag> findByEntryId(long entryId,
		int start, int end) {
		return getPersistence().findByEntryId(entryId, start, end);
	}

	/**
	* Returns an ordered range of all the announcements flags where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of announcements flags
	* @param end the upper bound of the range of announcements flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements flags
	*/
	public static List<AnnouncementsFlag> findByEntryId(long entryId,
		int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the announcements flags where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of announcements flags
	* @param end the upper bound of the range of announcements flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching announcements flags
	*/
	public static List<AnnouncementsFlag> findByEntryId(long entryId,
		int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first announcements flag in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements flag
	* @throws NoSuchFlagException if a matching announcements flag could not be found
	*/
	public static AnnouncementsFlag findByEntryId_First(long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the first announcements flag in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	*/
	public static AnnouncementsFlag fetchByEntryId_First(long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {
		return getPersistence().fetchByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the last announcements flag in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements flag
	* @throws NoSuchFlagException if a matching announcements flag could not be found
	*/
	public static AnnouncementsFlag findByEntryId_Last(long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the last announcements flag in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	*/
	public static AnnouncementsFlag fetchByEntryId_Last(long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {
		return getPersistence().fetchByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the announcements flags before and after the current announcements flag in the ordered set where entryId = &#63;.
	*
	* @param flagId the primary key of the current announcements flag
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements flag
	* @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	*/
	public static AnnouncementsFlag[] findByEntryId_PrevAndNext(long flagId,
		long entryId, OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence()
				   .findByEntryId_PrevAndNext(flagId, entryId, orderByComparator);
	}

	/**
	* Removes all the announcements flags where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	*/
	public static void removeByEntryId(long entryId) {
		getPersistence().removeByEntryId(entryId);
	}

	/**
	* Returns the number of announcements flags where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching announcements flags
	*/
	public static int countByEntryId(long entryId) {
		return getPersistence().countByEntryId(entryId);
	}

	/**
	* Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or throws a {@link NoSuchFlagException} if it could not be found.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @return the matching announcements flag
	* @throws NoSuchFlagException if a matching announcements flag could not be found
	*/
	public static AnnouncementsFlag findByU_E_V(long userId, long entryId,
		int value) throws com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByU_E_V(userId, entryId, value);
	}

	/**
	* Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	*/
	public static AnnouncementsFlag fetchByU_E_V(long userId, long entryId,
		int value) {
		return getPersistence().fetchByU_E_V(userId, entryId, value);
	}

	/**
	* Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	*/
	public static AnnouncementsFlag fetchByU_E_V(long userId, long entryId,
		int value, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByU_E_V(userId, entryId, value, retrieveFromCache);
	}

	/**
	* Removes the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; from the database.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @return the announcements flag that was removed
	*/
	public static AnnouncementsFlag removeByU_E_V(long userId, long entryId,
		int value) throws com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().removeByU_E_V(userId, entryId, value);
	}

	/**
	* Returns the number of announcements flags where userId = &#63; and entryId = &#63; and value = &#63;.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @return the number of matching announcements flags
	*/
	public static int countByU_E_V(long userId, long entryId, int value) {
		return getPersistence().countByU_E_V(userId, entryId, value);
	}

	/**
	* Caches the announcements flag in the entity cache if it is enabled.
	*
	* @param announcementsFlag the announcements flag
	*/
	public static void cacheResult(AnnouncementsFlag announcementsFlag) {
		getPersistence().cacheResult(announcementsFlag);
	}

	/**
	* Caches the announcements flags in the entity cache if it is enabled.
	*
	* @param announcementsFlags the announcements flags
	*/
	public static void cacheResult(List<AnnouncementsFlag> announcementsFlags) {
		getPersistence().cacheResult(announcementsFlags);
	}

	/**
	* Creates a new announcements flag with the primary key. Does not add the announcements flag to the database.
	*
	* @param flagId the primary key for the new announcements flag
	* @return the new announcements flag
	*/
	public static AnnouncementsFlag create(long flagId) {
		return getPersistence().create(flagId);
	}

	/**
	* Removes the announcements flag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param flagId the primary key of the announcements flag
	* @return the announcements flag that was removed
	* @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	*/
	public static AnnouncementsFlag remove(long flagId)
		throws com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().remove(flagId);
	}

	public static AnnouncementsFlag updateImpl(
		AnnouncementsFlag announcementsFlag) {
		return getPersistence().updateImpl(announcementsFlag);
	}

	/**
	* Returns the announcements flag with the primary key or throws a {@link NoSuchFlagException} if it could not be found.
	*
	* @param flagId the primary key of the announcements flag
	* @return the announcements flag
	* @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	*/
	public static AnnouncementsFlag findByPrimaryKey(long flagId)
		throws com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByPrimaryKey(flagId);
	}

	/**
	* Returns the announcements flag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param flagId the primary key of the announcements flag
	* @return the announcements flag, or <code>null</code> if a announcements flag with the primary key could not be found
	*/
	public static AnnouncementsFlag fetchByPrimaryKey(long flagId) {
		return getPersistence().fetchByPrimaryKey(flagId);
	}

	public static java.util.Map<java.io.Serializable, AnnouncementsFlag> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the announcements flags.
	*
	* @return the announcements flags
	*/
	public static List<AnnouncementsFlag> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the announcements flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements flags
	* @param end the upper bound of the range of announcements flags (not inclusive)
	* @return the range of announcements flags
	*/
	public static List<AnnouncementsFlag> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the announcements flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements flags
	* @param end the upper bound of the range of announcements flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of announcements flags
	*/
	public static List<AnnouncementsFlag> findAll(int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the announcements flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements flags
	* @param end the upper bound of the range of announcements flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of announcements flags
	*/
	public static List<AnnouncementsFlag> findAll(int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the announcements flags from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of announcements flags.
	*
	* @return the number of announcements flags
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AnnouncementsFlagPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AnnouncementsFlagPersistence)PortalBeanLocatorUtil.locate(AnnouncementsFlagPersistence.class.getName());

			ReferenceRegistry.registerReference(AnnouncementsFlagUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static AnnouncementsFlagPersistence _persistence;
}