/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.service.persistence;

import com.liferay.expando.kernel.exception.NoSuchTableException;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the expando table service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTableUtil
 * @generated
 */
@ProviderType
public interface ExpandoTablePersistence
	extends BasePersistence<ExpandoTable>, CTPersistence<ExpandoTable> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoTableUtil} to access the expando table persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching expando tables
	 */
	public java.util.List<ExpandoTable> findByC_C(
		long companyId, long classNameId);

	/**
	 * Returns a range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @return the range of matching expando tables
	 */
	public java.util.List<ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando tables
	 */
	public java.util.List<ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable>
			orderByComparator);

	/**
	 * Returns an ordered range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando tables
	 */
	public java.util.List<ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando table
	 * @throws NoSuchTableException if a matching expando table could not be found
	 */
	public ExpandoTable findByC_C_First(
			long companyId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable>
				orderByComparator)
		throws NoSuchTableException;

	/**
	 * Returns the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando table, or <code>null</code> if a matching expando table could not be found
	 */
	public ExpandoTable fetchByC_C_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable>
			orderByComparator);

	/**
	 * Returns the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando table
	 * @throws NoSuchTableException if a matching expando table could not be found
	 */
	public ExpandoTable findByC_C_Last(
			long companyId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable>
				orderByComparator)
		throws NoSuchTableException;

	/**
	 * Returns the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando table, or <code>null</code> if a matching expando table could not be found
	 */
	public ExpandoTable fetchByC_C_Last(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable>
			orderByComparator);

	/**
	 * Returns the expando tables before and after the current expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param tableId the primary key of the current expando table
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando table
	 * @throws NoSuchTableException if a expando table with the primary key could not be found
	 */
	public ExpandoTable[] findByC_C_PrevAndNext(
			long tableId, long companyId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable>
				orderByComparator)
		throws NoSuchTableException;

	/**
	 * Removes all the expando tables where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 */
	public void removeByC_C(long companyId, long classNameId);

	/**
	 * Returns the number of expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching expando tables
	 */
	public int countByC_C(long companyId, long classNameId);

	/**
	 * Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or throws a <code>NoSuchTableException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @return the matching expando table
	 * @throws NoSuchTableException if a matching expando table could not be found
	 */
	public ExpandoTable findByC_C_N(
			long companyId, long classNameId, String name)
		throws NoSuchTableException;

	/**
	 * Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	 */
	public ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, String name);

	/**
	 * Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	 */
	public ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, String name, boolean useFinderCache);

	/**
	 * Removes the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @return the expando table that was removed
	 */
	public ExpandoTable removeByC_C_N(
			long companyId, long classNameId, String name)
		throws NoSuchTableException;

	/**
	 * Returns the number of expando tables where companyId = &#63; and classNameId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @return the number of matching expando tables
	 */
	public int countByC_C_N(long companyId, long classNameId, String name);

	/**
	 * Caches the expando table in the entity cache if it is enabled.
	 *
	 * @param expandoTable the expando table
	 */
	public void cacheResult(ExpandoTable expandoTable);

	/**
	 * Caches the expando tables in the entity cache if it is enabled.
	 *
	 * @param expandoTables the expando tables
	 */
	public void cacheResult(java.util.List<ExpandoTable> expandoTables);

	/**
	 * Creates a new expando table with the primary key. Does not add the expando table to the database.
	 *
	 * @param tableId the primary key for the new expando table
	 * @return the new expando table
	 */
	public ExpandoTable create(long tableId);

	/**
	 * Removes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param tableId the primary key of the expando table
	 * @return the expando table that was removed
	 * @throws NoSuchTableException if a expando table with the primary key could not be found
	 */
	public ExpandoTable remove(long tableId) throws NoSuchTableException;

	public ExpandoTable updateImpl(ExpandoTable expandoTable);

	/**
	 * Returns the expando table with the primary key or throws a <code>NoSuchTableException</code> if it could not be found.
	 *
	 * @param tableId the primary key of the expando table
	 * @return the expando table
	 * @throws NoSuchTableException if a expando table with the primary key could not be found
	 */
	public ExpandoTable findByPrimaryKey(long tableId)
		throws NoSuchTableException;

	/**
	 * Returns the expando table with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param tableId the primary key of the expando table
	 * @return the expando table, or <code>null</code> if a expando table with the primary key could not be found
	 */
	public ExpandoTable fetchByPrimaryKey(long tableId);

	/**
	 * Returns all the expando tables.
	 *
	 * @return the expando tables
	 */
	public java.util.List<ExpandoTable> findAll();

	/**
	 * Returns a range of all the expando tables.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @return the range of expando tables
	 */
	public java.util.List<ExpandoTable> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the expando tables.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of expando tables
	 */
	public java.util.List<ExpandoTable> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable>
			orderByComparator);

	/**
	 * Returns an ordered range of all the expando tables.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of expando tables
	 */
	public java.util.List<ExpandoTable> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the expando tables from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of expando tables.
	 *
	 * @return the number of expando tables
	 */
	public int countAll();

}