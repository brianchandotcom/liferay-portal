/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.service.persistence;

import com.liferay.expando.kernel.exception.NoSuchColumnException;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the expando column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnUtil
 * @generated
 */
@ProviderType
public interface ExpandoColumnPersistence
	extends BasePersistence<ExpandoColumn>, CTPersistence<ExpandoColumn> {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @return the range of matching expando columns
	 */
	public java.util.List<ExpandoColumn> findByTableId(
		long tableId, int start, int end);

	/**
	 * Returns an ordered range of all the expando columns where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando columns
	 */
	public java.util.List<ExpandoColumn> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the expando columns where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando columns
	 */
	public java.util.List<ExpandoColumn> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first expando column in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando column
	 * @throws NoSuchColumnException if a matching expando column could not be found
	 */
	public ExpandoColumn findByTableId_First(
			long tableId,
			com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
				orderByComparator)
		throws NoSuchColumnException;

	/**
	 * Returns the first expando column in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando column, or <code>null</code> if a matching expando column could not be found
	 */
	public ExpandoColumn fetchByTableId_First(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
			orderByComparator);

	/**
	 * Returns the last expando column in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando column
	 * @throws NoSuchColumnException if a matching expando column could not be found
	 */
	public ExpandoColumn findByTableId_Last(
			long tableId,
			com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
				orderByComparator)
		throws NoSuchColumnException;

	/**
	 * Returns the last expando column in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando column, or <code>null</code> if a matching expando column could not be found
	 */
	public ExpandoColumn fetchByTableId_Last(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
			orderByComparator);

	/**
	 * Returns the expando columns before and after the current expando column in the ordered set where tableId = &#63;.
	 *
	 * @param columnId the primary key of the current expando column
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando column
	 * @throws NoSuchColumnException if a expando column with the primary key could not be found
	 */
	public ExpandoColumn[] findByTableId_PrevAndNext(
			long columnId, long tableId,
			com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
				orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @return the range of matching expando columns that the user has permission to view
	 */
	public java.util.List<ExpandoColumn> filterFindByTableId(
		long tableId, int start, int end);

	/**
	 * Returns an ordered range of all the expando columns that the user has permissions to view where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando columns that the user has permission to view
	 */
	public java.util.List<ExpandoColumn> filterFindByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
			orderByComparator);

	/**
	 * Returns the expando columns before and after the current expando column in the ordered set of expando columns that the user has permission to view where tableId = &#63;.
	 *
	 * @param columnId the primary key of the current expando column
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando column
	 * @throws NoSuchColumnException if a expando column with the primary key could not be found
	 */
	public ExpandoColumn[] filterFindByTableId_PrevAndNext(
			long columnId, long tableId,
			com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
				orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @return the matching expando columns
	 */
	public java.util.List<ExpandoColumn> findByT_N(
		long tableId, String[] names);

	/**
	 * Returns a range of all the expando columns where tableId = &#63; and name = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @return the range of matching expando columns
	 */
	public java.util.List<ExpandoColumn> findByT_N(
		long tableId, String[] names, int start, int end);

	/**
	 * Returns an ordered range of all the expando columns where tableId = &#63; and name = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando columns
	 */
	public java.util.List<ExpandoColumn> findByT_N(
		long tableId, String[] names, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the expando columns where tableId = &#63; and name = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando columns
	 */
	public java.util.List<ExpandoColumn> findByT_N(
		long tableId, String[] names, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the expando column where tableId = &#63; and name = &#63; or throws a <code>NoSuchColumnException</code> if it could not be found.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the matching expando column
	 * @throws NoSuchColumnException if a matching expando column could not be found
	 */
	public ExpandoColumn findByT_N(long tableId, String name)
		throws NoSuchColumnException;

	/**
	 * Returns the expando column where tableId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the matching expando column, or <code>null</code> if a matching expando column could not be found
	 */
	public ExpandoColumn fetchByT_N(long tableId, String name);

	/**
	 * Returns the expando column where tableId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching expando column, or <code>null</code> if a matching expando column could not be found
	 */
	public ExpandoColumn fetchByT_N(
		long tableId, String name, boolean useFinderCache);

	/**
	 * Removes the expando column where tableId = &#63; and name = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the expando column that was removed
	 */
	public ExpandoColumn removeByT_N(long tableId, String name)
		throws NoSuchColumnException;

	/**
	 * Returns the number of expando columns where tableId = &#63; and name = &#63;.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the number of matching expando columns
	 */
	public int countByT_N(long tableId, String name);

	/**
	 * Returns the number of expando columns where tableId = &#63; and name = any &#63;.
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @return the number of matching expando columns
	 */
	public int countByT_N(long tableId, String[] names);

	/**
	 * Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the number of matching expando columns that the user has permission to view
	 */
	public int filterCountByT_N(long tableId, String name);

	/**
	 * Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @return the number of matching expando columns that the user has permission to view
	 */
	public int filterCountByT_N(long tableId, String[] names);

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
	 * Returns the expando column with the primary key or throws a <code>NoSuchColumnException</code> if it could not be found.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of expando columns
	 */
	public java.util.List<ExpandoColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the expando columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of expando columns
	 */
	public java.util.List<ExpandoColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoColumn>
			orderByComparator,
		boolean useFinderCache);

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

}