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

import com.liferay.announcements.kernel.exception.NoSuchFlagException;
import com.liferay.announcements.kernel.model.AnnouncementsFlag;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the announcements flag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.announcements.service.persistence.impl.AnnouncementsFlagPersistenceImpl
 * @see AnnouncementsFlagUtil
 * @generated
 */
@ProviderType
public interface AnnouncementsFlagPersistence extends BasePersistence<AnnouncementsFlag> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnnouncementsFlagUtil} to access the announcements flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the announcements flags where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching announcements flags
	*/
	public java.util.List<AnnouncementsFlag> findByEntryId(long entryId);

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
	public java.util.List<AnnouncementsFlag> findByEntryId(long entryId,
		int start, int end);

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
	public java.util.List<AnnouncementsFlag> findByEntryId(long entryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsFlag> orderByComparator);

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
	public java.util.List<AnnouncementsFlag> findByEntryId(long entryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsFlag> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first announcements flag in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements flag
	* @throws NoSuchFlagException if a matching announcements flag could not be found
	*/
	public AnnouncementsFlag findByEntryId_First(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException;

	/**
	* Returns the first announcements flag in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	*/
	public AnnouncementsFlag fetchByEntryId_First(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsFlag> orderByComparator);

	/**
	* Returns the last announcements flag in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements flag
	* @throws NoSuchFlagException if a matching announcements flag could not be found
	*/
	public AnnouncementsFlag findByEntryId_Last(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException;

	/**
	* Returns the last announcements flag in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	*/
	public AnnouncementsFlag fetchByEntryId_Last(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsFlag> orderByComparator);

	/**
	* Returns the announcements flags before and after the current announcements flag in the ordered set where entryId = &#63;.
	*
	* @param flagId the primary key of the current announcements flag
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements flag
	* @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	*/
	public AnnouncementsFlag[] findByEntryId_PrevAndNext(long flagId,
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException;

	/**
	* Removes all the announcements flags where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	*/
	public void removeByEntryId(long entryId);

	/**
	* Returns the number of announcements flags where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching announcements flags
	*/
	public int countByEntryId(long entryId);

	/**
	* Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or throws a {@link NoSuchFlagException} if it could not be found.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @return the matching announcements flag
	* @throws NoSuchFlagException if a matching announcements flag could not be found
	*/
	public AnnouncementsFlag findByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException;

	/**
	* Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	*/
	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value);

	/**
	* Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	*/
	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value,
		boolean retrieveFromCache);

	/**
	* Removes the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; from the database.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @return the announcements flag that was removed
	*/
	public AnnouncementsFlag removeByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException;

	/**
	* Returns the number of announcements flags where userId = &#63; and entryId = &#63; and value = &#63;.
	*
	* @param userId the user ID
	* @param entryId the entry ID
	* @param value the value
	* @return the number of matching announcements flags
	*/
	public int countByU_E_V(long userId, long entryId, int value);

	/**
	* Caches the announcements flag in the entity cache if it is enabled.
	*
	* @param announcementsFlag the announcements flag
	*/
	public void cacheResult(AnnouncementsFlag announcementsFlag);

	/**
	* Caches the announcements flags in the entity cache if it is enabled.
	*
	* @param announcementsFlags the announcements flags
	*/
	public void cacheResult(
		java.util.List<AnnouncementsFlag> announcementsFlags);

	/**
	* Creates a new announcements flag with the primary key. Does not add the announcements flag to the database.
	*
	* @param flagId the primary key for the new announcements flag
	* @return the new announcements flag
	*/
	public AnnouncementsFlag create(long flagId);

	/**
	* Removes the announcements flag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param flagId the primary key of the announcements flag
	* @return the announcements flag that was removed
	* @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	*/
	public AnnouncementsFlag remove(long flagId) throws NoSuchFlagException;

	public AnnouncementsFlag updateImpl(AnnouncementsFlag announcementsFlag);

	/**
	* Returns the announcements flag with the primary key or throws a {@link NoSuchFlagException} if it could not be found.
	*
	* @param flagId the primary key of the announcements flag
	* @return the announcements flag
	* @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	*/
	public AnnouncementsFlag findByPrimaryKey(long flagId)
		throws NoSuchFlagException;

	/**
	* Returns the announcements flag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param flagId the primary key of the announcements flag
	* @return the announcements flag, or <code>null</code> if a announcements flag with the primary key could not be found
	*/
	public AnnouncementsFlag fetchByPrimaryKey(long flagId);

	@Override
	public java.util.Map<java.io.Serializable, AnnouncementsFlag> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the announcements flags.
	*
	* @return the announcements flags
	*/
	public java.util.List<AnnouncementsFlag> findAll();

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
	public java.util.List<AnnouncementsFlag> findAll(int start, int end);

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
	public java.util.List<AnnouncementsFlag> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsFlag> orderByComparator);

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
	public java.util.List<AnnouncementsFlag> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsFlag> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the announcements flags from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of announcements flags.
	*
	* @return the number of announcements flags
	*/
	public int countAll();
}