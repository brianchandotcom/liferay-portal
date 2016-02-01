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

package com.liferay.portlet.expando.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.expando.exception.NoSuchColumnException;
import com.liferay.portlet.expando.model.ExpandoColumn;

/**
 * The persistence interface for the expando column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.expando.service.persistence.impl.ExpandoColumnPersistenceImpl
 * @see ExpandoColumnUtil
 * @generated
 */
@ProviderType
public interface ExpandoColumnPersistence extends BasePersistence<ExpandoColumn> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoColumnUtil} to access the expando column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the expando columns where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando columns
	*/
	public java.util.List<ExpandoColumn> findByTableId(long tableId);

	/**
	* Returns a range of all the expando columns where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns
	*/
	public java.util.List<ExpandoColumn> findByTableId(long tableId, int start,
		int end);

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns
	*/
	public java.util.List<ExpandoColumn> findByTableId(long tableId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator);

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando columns
	*/
	public java.util.List<ExpandoColumn> findByTableId(long tableId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando column in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando column
	* @throws NoSuchColumnException if a matching expando column could not be found
	*/
	public ExpandoColumn findByTableId_First(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator)
		throws NoSuchColumnException;

	/**
	* Returns the first expando column in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando column, or <code>null</code> if a matching expando column could not be found
	*/
	public ExpandoColumn fetchByTableId_First(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator);

	/**
	* Returns the last expando column in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando column
	* @throws NoSuchColumnException if a matching expando column could not be found
	*/
	public ExpandoColumn findByTableId_Last(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator)
		throws NoSuchColumnException;

	/**
	* Returns the last expando column in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando column, or <code>null</code> if a matching expando column could not be found
	*/
	public ExpandoColumn fetchByTableId_Last(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator);

	/**
	* Returns the expando columns before and after the current expando column in the ordered set where tableId = &#63;.
	*
	* @param columnId the primary key of the current expando column
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando column
	* @throws NoSuchColumnException if a expando column with the primary key could not be found
	*/
	public ExpandoColumn[] findByTableId_PrevAndNext(long columnId,
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator)
		throws NoSuchColumnException;

	/**
	* Returns all the expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando columns that the user has permission to view
	*/
	public java.util.List<ExpandoColumn> filterFindByTableId(long tableId);

	/**
	* Returns a range of all the expando columns that the user has permission to view where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns that the user has permission to view
	*/
	public java.util.List<ExpandoColumn> filterFindByTableId(long tableId,
		int start, int end);

	/**
	* Returns an ordered range of all the expando columns that the user has permissions to view where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns that the user has permission to view
	*/
	public java.util.List<ExpandoColumn> filterFindByTableId(long tableId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator);

	/**
	* Returns the expando columns before and after the current expando column in the ordered set of expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param columnId the primary key of the current expando column
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando column
	* @throws NoSuchColumnException if a expando column with the primary key could not be found
	*/
	public ExpandoColumn[] filterFindByTableId_PrevAndNext(long columnId,
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator)
		throws NoSuchColumnException;

	/**
	* Removes all the expando columns where tableId = &#63; from the database.
	*
	* @param tableId the table ID
	*/
	public void removeByTableId(long tableId);

	/**
	* Returns the number of expando columns where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando columns
	*/
	public int countByTableId(long tableId);

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando columns that the user has permission to view
	*/
	public int filterCountByTableId(long tableId);

	/**
	* Returns all the expando columns where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @return the matching expando columns
	*/
	public java.util.List<ExpandoColumn> findByT_N(long tableId,
		java.lang.String[] names);

	/**
	* Returns a range of all the expando columns where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns
	*/
	public java.util.List<ExpandoColumn> findByT_N(long tableId,
		java.lang.String[] names, int start, int end);

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns
	*/
	public java.util.List<ExpandoColumn> findByT_N(long tableId,
		java.lang.String[] names, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator);

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63; and name = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param name the name
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando columns
	*/
	public java.util.List<ExpandoColumn> findByT_N(long tableId,
		java.lang.String[] names, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the expando column where tableId = &#63; and name = &#63; or throws a {@link NoSuchColumnException} if it could not be found.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the matching expando column
	* @throws NoSuchColumnException if a matching expando column could not be found
	*/
	public ExpandoColumn findByT_N(long tableId, java.lang.String name)
		throws NoSuchColumnException;

	/**
	* Returns the expando column where tableId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the matching expando column, or <code>null</code> if a matching expando column could not be found
	*/
	public ExpandoColumn fetchByT_N(long tableId, java.lang.String name);

	/**
	* Returns the expando column where tableId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tableId the table ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching expando column, or <code>null</code> if a matching expando column could not be found
	*/
	public ExpandoColumn fetchByT_N(long tableId, java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the expando column where tableId = &#63; and name = &#63; from the database.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the expando column that was removed
	*/
	public ExpandoColumn removeByT_N(long tableId, java.lang.String name)
		throws NoSuchColumnException;

	/**
	* Returns the number of expando columns where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the number of matching expando columns
	*/
	public int countByT_N(long tableId, java.lang.String name);

	/**
	* Returns the number of expando columns where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the number of matching expando columns
	*/
	public int countByT_N(long tableId, java.lang.String[] names);

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the number of matching expando columns that the user has permission to view
	*/
	public int filterCountByT_N(long tableId, java.lang.String name);

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the number of matching expando columns that the user has permission to view
	*/
	public int filterCountByT_N(long tableId, java.lang.String[] names);

	/**
	* Caches the expando column in the entity cache if it is enabled.
	*
	* @param expandoColumn the expando column
	*/
	public void cacheResult(ExpandoColumn expandoColumn);

	/**
	* Caches the expando columns in the entity cache if it is enabled.
	*
	* @param expandoColumns the expando columns
	*/
	public void cacheResult(java.util.List<ExpandoColumn> expandoColumns);

	/**
	* Creates a new expando column with the primary key. Does not add the expando column to the database.
	*
	* @param columnId the primary key for the new expando column
	* @return the new expando column
	*/
	public ExpandoColumn create(long columnId);

	/**
	* Removes the expando column with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column that was removed
	* @throws NoSuchColumnException if a expando column with the primary key could not be found
	*/
	public ExpandoColumn remove(long columnId) throws NoSuchColumnException;

	public ExpandoColumn updateImpl(ExpandoColumn expandoColumn);

	/**
	* Returns the expando column with the primary key or throws a {@link NoSuchColumnException} if it could not be found.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column
	* @throws NoSuchColumnException if a expando column with the primary key could not be found
	*/
	public ExpandoColumn findByPrimaryKey(long columnId)
		throws NoSuchColumnException;

	/**
	* Returns the expando column with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column, or <code>null</code> if a expando column with the primary key could not be found
	*/
	public ExpandoColumn fetchByPrimaryKey(long columnId);

	@Override
	public java.util.Map<java.io.Serializable, ExpandoColumn> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the expando columns.
	*
	* @return the expando columns
	*/
	public java.util.List<ExpandoColumn> findAll();

	/**
	* Returns a range of all the expando columns.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of expando columns
	*/
	public java.util.List<ExpandoColumn> findAll(int start, int end);

	/**
	* Returns an ordered range of all the expando columns.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando columns
	*/
	public java.util.List<ExpandoColumn> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator);

	/**
	* Returns an ordered range of all the expando columns.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of expando columns
	*/
	public java.util.List<ExpandoColumn> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the expando columns from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of expando columns.
	*
	* @return the number of expando columns
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}