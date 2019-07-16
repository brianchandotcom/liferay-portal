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

package com.liferay.data.engine.service.persistence;

import com.liferay.data.engine.exception.NoSuchRecordQueryException;
import com.liferay.data.engine.model.DERecordQuery;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the de record query service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DERecordQueryUtil
 * @generated
 */
@ProviderType
public interface DERecordQueryPersistence
	extends BasePersistence<DERecordQuery> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DERecordQueryUtil} to access the de record query persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the de record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de record queries
	 */
	public java.util.List<DERecordQuery> findByUuid(String uuid);

	/**
	 * Returns a range of all the de record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @return the range of matching de record queries
	 */
	public java.util.List<DERecordQuery> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the de record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de record queries
	 */
	public java.util.List<DERecordQuery> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DERecordQuery>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de record queries
	 */
	public java.util.List<DERecordQuery> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DERecordQuery>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de record query
	 * @throws NoSuchRecordQueryException if a matching de record query could not be found
	 */
	public DERecordQuery findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DERecordQuery>
				orderByComparator)
		throws NoSuchRecordQueryException;

	/**
	 * Returns the first de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de record query, or <code>null</code> if a matching de record query could not be found
	 */
	public DERecordQuery fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DERecordQuery>
			orderByComparator);

	/**
	 * Returns the last de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de record query
	 * @throws NoSuchRecordQueryException if a matching de record query could not be found
	 */
	public DERecordQuery findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DERecordQuery>
				orderByComparator)
		throws NoSuchRecordQueryException;

	/**
	 * Returns the last de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de record query, or <code>null</code> if a matching de record query could not be found
	 */
	public DERecordQuery fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DERecordQuery>
			orderByComparator);

	/**
	 * Returns the de record queries before and after the current de record query in the ordered set where uuid = &#63;.
	 *
	 * @param deRecordQueryId the primary key of the current de record query
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de record query
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	public DERecordQuery[] findByUuid_PrevAndNext(
			long deRecordQueryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DERecordQuery>
				orderByComparator)
		throws NoSuchRecordQueryException;

	/**
	 * Removes all the de record queries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of de record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de record queries
	 */
	public int countByUuid(String uuid);

	/**
	 * Caches the de record query in the entity cache if it is enabled.
	 *
	 * @param deRecordQuery the de record query
	 */
	public void cacheResult(DERecordQuery deRecordQuery);

	/**
	 * Caches the de record queries in the entity cache if it is enabled.
	 *
	 * @param deRecordQueries the de record queries
	 */
	public void cacheResult(java.util.List<DERecordQuery> deRecordQueries);

	/**
	 * Creates a new de record query with the primary key. Does not add the de record query to the database.
	 *
	 * @param deRecordQueryId the primary key for the new de record query
	 * @return the new de record query
	 */
	public DERecordQuery create(long deRecordQueryId);

	/**
	 * Removes the de record query with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deRecordQueryId the primary key of the de record query
	 * @return the de record query that was removed
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	public DERecordQuery remove(long deRecordQueryId)
		throws NoSuchRecordQueryException;

	public DERecordQuery updateImpl(DERecordQuery deRecordQuery);

	/**
	 * Returns the de record query with the primary key or throws a <code>NoSuchRecordQueryException</code> if it could not be found.
	 *
	 * @param deRecordQueryId the primary key of the de record query
	 * @return the de record query
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	public DERecordQuery findByPrimaryKey(long deRecordQueryId)
		throws NoSuchRecordQueryException;

	/**
	 * Returns the de record query with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deRecordQueryId the primary key of the de record query
	 * @return the de record query, or <code>null</code> if a de record query with the primary key could not be found
	 */
	public DERecordQuery fetchByPrimaryKey(long deRecordQueryId);

	/**
	 * Returns all the de record queries.
	 *
	 * @return the de record queries
	 */
	public java.util.List<DERecordQuery> findAll();

	/**
	 * Returns a range of all the de record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @return the range of de record queries
	 */
	public java.util.List<DERecordQuery> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the de record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de record queries
	 */
	public java.util.List<DERecordQuery> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DERecordQuery>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of de record queries
	 */
	public java.util.List<DERecordQuery> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DERecordQuery>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the de record queries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of de record queries.
	 *
	 * @return the number of de record queries
	 */
	public int countAll();

}