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

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectViewSortColumnException;
import com.liferay.object.model.ObjectViewSortColumn;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object view sort column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewSortColumnUtil
 * @generated
 */
@ProviderType
public interface ObjectViewSortColumnPersistence
	extends BasePersistence<ObjectViewSortColumn> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectViewSortColumnUtil} to access the object view sort column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object view sort columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByUuid(String uuid);

	/**
	 * Returns a range of all the object view sort columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public ObjectViewSortColumn[] findByUuid_PrevAndNext(
			long objectViewSortColumnId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Removes all the object view sort columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object view sort columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view sort columns
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public ObjectViewSortColumn[] findByUuid_C_PrevAndNext(
			long objectViewSortColumnId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Removes all the object view sort columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view sort columns
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object view sort columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId);

	/**
	 * Returns a range of all the object view sort columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId, int start, int end);

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn findByObjectViewId_First(
			long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn fetchByObjectViewId_First(
		long objectViewId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn findByObjectViewId_Last(
			long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn fetchByObjectViewId_Last(
		long objectViewId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public ObjectViewSortColumn[] findByObjectViewId_PrevAndNext(
			long objectViewSortColumnId, long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Removes all the object view sort columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	public void removeByObjectViewId(long objectViewId);

	/**
	 * Returns the number of object view sort columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view sort columns
	 */
	public int countByObjectViewId(long objectViewId);

	/**
	 * Returns all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName);

	/**
	 * Returns a range of all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end);

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn findByOVI_OFN_First(
			long objectViewId, String objectFieldName,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn fetchByOVI_OFN_First(
		long objectViewId, String objectFieldName,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn findByOVI_OFN_Last(
			long objectViewId, String objectFieldName,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public ObjectViewSortColumn fetchByOVI_OFN_Last(
		long objectViewId, String objectFieldName,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public ObjectViewSortColumn[] findByOVI_OFN_PrevAndNext(
			long objectViewSortColumnId, long objectViewId,
			String objectFieldName,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Removes all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 */
	public void removeByOVI_OFN(long objectViewId, String objectFieldName);

	/**
	 * Returns the number of object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the number of matching object view sort columns
	 */
	public int countByOVI_OFN(long objectViewId, String objectFieldName);

	/**
	 * Caches the object view sort column in the entity cache if it is enabled.
	 *
	 * @param objectViewSortColumn the object view sort column
	 */
	public void cacheResult(ObjectViewSortColumn objectViewSortColumn);

	/**
	 * Caches the object view sort columns in the entity cache if it is enabled.
	 *
	 * @param objectViewSortColumns the object view sort columns
	 */
	public void cacheResult(
		java.util.List<ObjectViewSortColumn> objectViewSortColumns);

	/**
	 * Creates a new object view sort column with the primary key. Does not add the object view sort column to the database.
	 *
	 * @param objectViewSortColumnId the primary key for the new object view sort column
	 * @return the new object view sort column
	 */
	public ObjectViewSortColumn create(long objectViewSortColumnId);

	/**
	 * Removes the object view sort column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewSortColumnId the primary key of the object view sort column
	 * @return the object view sort column that was removed
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public ObjectViewSortColumn remove(long objectViewSortColumnId)
		throws NoSuchObjectViewSortColumnException;

	public ObjectViewSortColumn updateImpl(
		ObjectViewSortColumn objectViewSortColumn);

	/**
	 * Returns the object view sort column with the primary key or throws a <code>NoSuchObjectViewSortColumnException</code> if it could not be found.
	 *
	 * @param objectViewSortColumnId the primary key of the object view sort column
	 * @return the object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public ObjectViewSortColumn findByPrimaryKey(long objectViewSortColumnId)
		throws NoSuchObjectViewSortColumnException;

	/**
	 * Returns the object view sort column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewSortColumnId the primary key of the object view sort column
	 * @return the object view sort column, or <code>null</code> if a object view sort column with the primary key could not be found
	 */
	public ObjectViewSortColumn fetchByPrimaryKey(long objectViewSortColumnId);

	/**
	 * Returns all the object view sort columns.
	 *
	 * @return the object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findAll();

	/**
	 * Returns a range of all the object view sort columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object view sort columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view sort columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view sort columns
	 */
	public java.util.List<ObjectViewSortColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewSortColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object view sort columns from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object view sort columns.
	 *
	 * @return the number of object view sort columns
	 */
	public int countAll();

}