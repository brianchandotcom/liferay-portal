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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchMessageException;
import com.liferay.change.tracking.model.CTMessage;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTMessageUtil
 * @generated
 */
@ProviderType
public interface CTMessagePersistence extends BasePersistence<CTMessage> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTMessageUtil} to access the ct message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct messages where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct messages
	 */
	public java.util.List<CTMessage> findByCtCollectionId(long ctCollectionId);

	/**
	 * Returns a range of all the ct messages where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @return the range of matching ct messages
	 */
	public java.util.List<CTMessage> findByCtCollectionId(
		long ctCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the ct messages where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct messages
	 */
	public java.util.List<CTMessage> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct messages where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct messages
	 */
	public java.util.List<CTMessage> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct message
	 * @throws NoSuchMessageException if a matching ct message could not be found
	 */
	public CTMessage findByCtCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct message, or <code>null</code> if a matching ct message could not be found
	 */
	public CTMessage fetchByCtCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTMessage>
			orderByComparator);

	/**
	 * Returns the last ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct message
	 * @throws NoSuchMessageException if a matching ct message could not be found
	 */
	public CTMessage findByCtCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct message, or <code>null</code> if a matching ct message could not be found
	 */
	public CTMessage fetchByCtCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTMessage>
			orderByComparator);

	/**
	 * Returns the ct messages before and after the current ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctMessageId the primary key of the current ct message
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct message
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	public CTMessage[] findByCtCollectionId_PrevAndNext(
			long ctMessageId, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the ct messages where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCtCollectionId(long ctCollectionId);

	/**
	 * Returns the number of ct messages where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct messages
	 */
	public int countByCtCollectionId(long ctCollectionId);

	/**
	 * Caches the ct message in the entity cache if it is enabled.
	 *
	 * @param ctMessage the ct message
	 */
	public void cacheResult(CTMessage ctMessage);

	/**
	 * Caches the ct messages in the entity cache if it is enabled.
	 *
	 * @param ctMessages the ct messages
	 */
	public void cacheResult(java.util.List<CTMessage> ctMessages);

	/**
	 * Creates a new ct message with the primary key. Does not add the ct message to the database.
	 *
	 * @param ctMessageId the primary key for the new ct message
	 * @return the new ct message
	 */
	public CTMessage create(long ctMessageId);

	/**
	 * Removes the ct message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message that was removed
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	public CTMessage remove(long ctMessageId) throws NoSuchMessageException;

	public CTMessage updateImpl(CTMessage ctMessage);

	/**
	 * Returns the ct message with the primary key or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	public CTMessage findByPrimaryKey(long ctMessageId)
		throws NoSuchMessageException;

	/**
	 * Returns the ct message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message, or <code>null</code> if a ct message with the primary key could not be found
	 */
	public CTMessage fetchByPrimaryKey(long ctMessageId);

	/**
	 * Returns all the ct messages.
	 *
	 * @return the ct messages
	 */
	public java.util.List<CTMessage> findAll();

	/**
	 * Returns a range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @return the range of ct messages
	 */
	public java.util.List<CTMessage> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct messages
	 */
	public java.util.List<CTMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct messages
	 */
	public java.util.List<CTMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct messages from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct messages.
	 *
	 * @return the number of ct messages
	 */
	public int countAll();

}