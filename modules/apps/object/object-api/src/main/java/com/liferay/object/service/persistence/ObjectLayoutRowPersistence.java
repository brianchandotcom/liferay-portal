/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectLayoutRowException;
import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object layout row service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutRowUtil
 * @generated
 */
@ProviderType
public interface ObjectLayoutRowPersistence
	extends BasePersistence<ObjectLayoutRow> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectLayoutRowUtil} to access the object layout row persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object layout rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByUuid(String uuid);

	/**
	 * Returns a range of all the object layout rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public ObjectLayoutRow findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
				orderByComparator)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public ObjectLayoutRow fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public ObjectLayoutRow findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
				orderByComparator)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public ObjectLayoutRow fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns the object layout rows before and after the current object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutRowId the primary key of the current object layout row
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public ObjectLayoutRow[] findByUuid_PrevAndNext(
			long objectLayoutRowId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
				orderByComparator)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Removes all the object layout rows where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object layout rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout rows
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public ObjectLayoutRow findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
				orderByComparator)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public ObjectLayoutRow fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public ObjectLayoutRow findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
				orderByComparator)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public ObjectLayoutRow fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns the object layout rows before and after the current object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutRowId the primary key of the current object layout row
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public ObjectLayoutRow[] findByUuid_C_PrevAndNext(
			long objectLayoutRowId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
				orderByComparator)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Removes all the object layout rows where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout rows
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @return the matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId);

	/**
	 * Returns a range of all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public ObjectLayoutRow findByObjectLayoutBoxId_First(
			long objectLayoutBoxId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
				orderByComparator)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Returns the first object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public ObjectLayoutRow fetchByObjectLayoutBoxId_First(
		long objectLayoutBoxId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns the last object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public ObjectLayoutRow findByObjectLayoutBoxId_Last(
			long objectLayoutBoxId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
				orderByComparator)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Returns the last object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public ObjectLayoutRow fetchByObjectLayoutBoxId_Last(
		long objectLayoutBoxId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns the object layout rows before and after the current object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutRowId the primary key of the current object layout row
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public ObjectLayoutRow[] findByObjectLayoutBoxId_PrevAndNext(
			long objectLayoutRowId, long objectLayoutBoxId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
				orderByComparator)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Removes all the object layout rows where objectLayoutBoxId = &#63; from the database.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 */
	public void removeByObjectLayoutBoxId(long objectLayoutBoxId);

	/**
	 * Returns the number of object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @return the number of matching object layout rows
	 */
	public int countByObjectLayoutBoxId(long objectLayoutBoxId);

	/**
	 * Caches the object layout row in the entity cache if it is enabled.
	 *
	 * @param objectLayoutRow the object layout row
	 */
	public void cacheResult(ObjectLayoutRow objectLayoutRow);

	/**
	 * Caches the object layout rows in the entity cache if it is enabled.
	 *
	 * @param objectLayoutRows the object layout rows
	 */
	public void cacheResult(java.util.List<ObjectLayoutRow> objectLayoutRows);

	/**
	 * Creates a new object layout row with the primary key. Does not add the object layout row to the database.
	 *
	 * @param objectLayoutRowId the primary key for the new object layout row
	 * @return the new object layout row
	 */
	public ObjectLayoutRow create(long objectLayoutRowId);

	/**
	 * Removes the object layout row with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutRowId the primary key of the object layout row
	 * @return the object layout row that was removed
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public ObjectLayoutRow remove(long objectLayoutRowId)
		throws NoSuchObjectLayoutRowException;

	public ObjectLayoutRow updateImpl(ObjectLayoutRow objectLayoutRow);

	/**
	 * Returns the object layout row with the primary key or throws a <code>NoSuchObjectLayoutRowException</code> if it could not be found.
	 *
	 * @param objectLayoutRowId the primary key of the object layout row
	 * @return the object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public ObjectLayoutRow findByPrimaryKey(long objectLayoutRowId)
		throws NoSuchObjectLayoutRowException;

	/**
	 * Returns the object layout row with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutRowId the primary key of the object layout row
	 * @return the object layout row, or <code>null</code> if a object layout row with the primary key could not be found
	 */
	public ObjectLayoutRow fetchByPrimaryKey(long objectLayoutRowId);

	/**
	 * Returns all the object layout rows.
	 *
	 * @return the object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findAll();

	/**
	 * Returns a range of all the object layout rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object layout rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout rows
	 */
	public java.util.List<ObjectLayoutRow> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutRow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object layout rows from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object layout rows.
	 *
	 * @return the number of object layout rows
	 */
	public int countAll();

}