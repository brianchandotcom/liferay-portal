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

import com.liferay.data.engine.exception.NoSuchDataRecordQueryException;
import com.liferay.data.engine.model.DEDataRecordQuery;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the de data record query service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEDataRecordQueryUtil
 * @generated
 */
@ProviderType
public interface DEDataRecordQueryPersistence
	extends BasePersistence<DEDataRecordQuery> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DEDataRecordQueryUtil} to access the de data record query persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the de data record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de data record queries
	 */
	public java.util.List<DEDataRecordQuery> findByUuid(String uuid);

	/**
	 * Returns a range of all the de data record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @return the range of matching de data record queries
	 */
	public java.util.List<DEDataRecordQuery> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the de data record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data record queries
	 */
	public java.util.List<DEDataRecordQuery> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataRecordQuery>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de data record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de data record queries
	 */
	public java.util.List<DEDataRecordQuery> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataRecordQuery>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data record query
	 * @throws NoSuchDataRecordQueryException if a matching de data record query could not be found
	 */
	public DEDataRecordQuery findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataRecordQuery>
				orderByComparator)
		throws NoSuchDataRecordQueryException;

	/**
	 * Returns the first de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data record query, or <code>null</code> if a matching de data record query could not be found
	 */
	public DEDataRecordQuery fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataRecordQuery>
			orderByComparator);

	/**
	 * Returns the last de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data record query
	 * @throws NoSuchDataRecordQueryException if a matching de data record query could not be found
	 */
	public DEDataRecordQuery findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataRecordQuery>
				orderByComparator)
		throws NoSuchDataRecordQueryException;

	/**
	 * Returns the last de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data record query, or <code>null</code> if a matching de data record query could not be found
	 */
	public DEDataRecordQuery fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataRecordQuery>
			orderByComparator);

	/**
	 * Returns the de data record queries before and after the current de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param deDataRecordQueryId the primary key of the current de data record query
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data record query
	 * @throws NoSuchDataRecordQueryException if a de data record query with the primary key could not be found
	 */
	public DEDataRecordQuery[] findByUuid_PrevAndNext(
			long deDataRecordQueryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataRecordQuery>
				orderByComparator)
		throws NoSuchDataRecordQueryException;

	/**
	 * Removes all the de data record queries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of de data record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de data record queries
	 */
	public int countByUuid(String uuid);

	/**
	 * Caches the de data record query in the entity cache if it is enabled.
	 *
	 * @param deDataRecordQuery the de data record query
	 */
	public void cacheResult(DEDataRecordQuery deDataRecordQuery);

	/**
	 * Caches the de data record queries in the entity cache if it is enabled.
	 *
	 * @param deDataRecordQueries the de data record queries
	 */
	public void cacheResult(
		java.util.List<DEDataRecordQuery> deDataRecordQueries);

	/**
	 * Creates a new de data record query with the primary key. Does not add the de data record query to the database.
	 *
	 * @param deDataRecordQueryId the primary key for the new de data record query
	 * @return the new de data record query
	 */
	public DEDataRecordQuery create(long deDataRecordQueryId);

	/**
	 * Removes the de data record query with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataRecordQueryId the primary key of the de data record query
	 * @return the de data record query that was removed
	 * @throws NoSuchDataRecordQueryException if a de data record query with the primary key could not be found
	 */
	public DEDataRecordQuery remove(long deDataRecordQueryId)
		throws NoSuchDataRecordQueryException;

	public DEDataRecordQuery updateImpl(DEDataRecordQuery deDataRecordQuery);

	/**
	 * Returns the de data record query with the primary key or throws a <code>NoSuchDataRecordQueryException</code> if it could not be found.
	 *
	 * @param deDataRecordQueryId the primary key of the de data record query
	 * @return the de data record query
	 * @throws NoSuchDataRecordQueryException if a de data record query with the primary key could not be found
	 */
	public DEDataRecordQuery findByPrimaryKey(long deDataRecordQueryId)
		throws NoSuchDataRecordQueryException;

	/**
	 * Returns the de data record query with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deDataRecordQueryId the primary key of the de data record query
	 * @return the de data record query, or <code>null</code> if a de data record query with the primary key could not be found
	 */
	public DEDataRecordQuery fetchByPrimaryKey(long deDataRecordQueryId);

	/**
	 * Returns all the de data record queries.
	 *
	 * @return the de data record queries
	 */
	public java.util.List<DEDataRecordQuery> findAll();

	/**
	 * Returns a range of all the de data record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @return the range of de data record queries
	 */
	public java.util.List<DEDataRecordQuery> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the de data record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de data record queries
	 */
	public java.util.List<DEDataRecordQuery> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataRecordQuery>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de data record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of de data record queries
	 */
	public java.util.List<DEDataRecordQuery> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataRecordQuery>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the de data record queries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of de data record queries.
	 *
	 * @return the number of de data record queries
	 */
	public int countAll();

}