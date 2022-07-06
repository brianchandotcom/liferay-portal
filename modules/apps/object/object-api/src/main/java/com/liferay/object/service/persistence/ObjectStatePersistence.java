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

import com.liferay.object.exception.NoSuchObjectStateException;
import com.liferay.object.model.ObjectState;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object state service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectStateUtil
 * @generated
 */
@ProviderType
public interface ObjectStatePersistence extends BasePersistence<ObjectState> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectStateUtil} to access the object state persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object states where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object states
	 */
	public java.util.List<ObjectState> findByUuid(String uuid);

	/**
	 * Returns a range of all the object states where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	public java.util.List<ObjectState> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object states where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	public java.util.List<ObjectState> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object states where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	public java.util.List<ObjectState> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public ObjectState findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Returns the first object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns the last object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public ObjectState findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Returns the last object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns the object states before and after the current object state in the ordered set where uuid = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public ObjectState[] findByUuid_PrevAndNext(
			long objectStateId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Removes all the object states where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object states where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object states
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object states
	 */
	public java.util.List<ObjectState> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	public java.util.List<ObjectState> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	public java.util.List<ObjectState> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	public java.util.List<ObjectState> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public ObjectState findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Returns the first object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns the last object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public ObjectState findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Returns the last object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns the object states before and after the current object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public ObjectState[] findByUuid_C_PrevAndNext(
			long objectStateId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Removes all the object states where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object states where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object states
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object states where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the matching object states
	 */
	public java.util.List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId);

	/**
	 * Returns a range of all the object states where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	public java.util.List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the object states where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	public java.util.List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object states where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	public java.util.List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public ObjectState findByListTypeEntryId_First(
			long listTypeEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Returns the first object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByListTypeEntryId_First(
		long listTypeEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns the last object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public ObjectState findByListTypeEntryId_Last(
			long listTypeEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Returns the last object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByListTypeEntryId_Last(
		long listTypeEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns the object states before and after the current object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public ObjectState[] findByListTypeEntryId_PrevAndNext(
			long objectStateId, long listTypeEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Removes all the object states where listTypeEntryId = &#63; from the database.
	 *
	 * @param listTypeEntryId the list type entry ID
	 */
	public void removeByListTypeEntryId(long listTypeEntryId);

	/**
	 * Returns the number of object states where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the number of matching object states
	 */
	public int countByListTypeEntryId(long listTypeEntryId);

	/**
	 * Returns all the object states where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object states
	 */
	public java.util.List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId);

	/**
	 * Returns a range of all the object states where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	public java.util.List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end);

	/**
	 * Returns an ordered range of all the object states where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	public java.util.List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object states where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	public java.util.List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public ObjectState findByObjectStateFlowId_First(
			long objectStateFlowId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Returns the first object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByObjectStateFlowId_First(
		long objectStateFlowId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns the last object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public ObjectState findByObjectStateFlowId_Last(
			long objectStateFlowId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Returns the last object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByObjectStateFlowId_Last(
		long objectStateFlowId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns the object states before and after the current object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public ObjectState[] findByObjectStateFlowId_PrevAndNext(
			long objectStateId, long objectStateFlowId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
				orderByComparator)
		throws NoSuchObjectStateException;

	/**
	 * Removes all the object states where objectStateFlowId = &#63; from the database.
	 *
	 * @param objectStateFlowId the object state flow ID
	 */
	public void removeByObjectStateFlowId(long objectStateFlowId);

	/**
	 * Returns the number of object states where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the number of matching object states
	 */
	public int countByObjectStateFlowId(long objectStateFlowId);

	/**
	 * Returns the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; or throws a <code>NoSuchObjectStateException</code> if it could not be found.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public ObjectState findByLTEI_OSFI(
			long listTypeEntryId, long objectStateFlowId)
		throws NoSuchObjectStateException;

	/**
	 * Returns the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByLTEI_OSFI(
		long listTypeEntryId, long objectStateFlowId);

	/**
	 * Returns the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public ObjectState fetchByLTEI_OSFI(
		long listTypeEntryId, long objectStateFlowId, boolean useFinderCache);

	/**
	 * Removes the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; from the database.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the object state that was removed
	 */
	public ObjectState removeByLTEI_OSFI(
			long listTypeEntryId, long objectStateFlowId)
		throws NoSuchObjectStateException;

	/**
	 * Returns the number of object states where listTypeEntryId = &#63; and objectStateFlowId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the number of matching object states
	 */
	public int countByLTEI_OSFI(long listTypeEntryId, long objectStateFlowId);

	/**
	 * Caches the object state in the entity cache if it is enabled.
	 *
	 * @param objectState the object state
	 */
	public void cacheResult(ObjectState objectState);

	/**
	 * Caches the object states in the entity cache if it is enabled.
	 *
	 * @param objectStates the object states
	 */
	public void cacheResult(java.util.List<ObjectState> objectStates);

	/**
	 * Creates a new object state with the primary key. Does not add the object state to the database.
	 *
	 * @param objectStateId the primary key for the new object state
	 * @return the new object state
	 */
	public ObjectState create(long objectStateId);

	/**
	 * Removes the object state with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state that was removed
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public ObjectState remove(long objectStateId)
		throws NoSuchObjectStateException;

	public ObjectState updateImpl(ObjectState objectState);

	/**
	 * Returns the object state with the primary key or throws a <code>NoSuchObjectStateException</code> if it could not be found.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public ObjectState findByPrimaryKey(long objectStateId)
		throws NoSuchObjectStateException;

	/**
	 * Returns the object state with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state, or <code>null</code> if a object state with the primary key could not be found
	 */
	public ObjectState fetchByPrimaryKey(long objectStateId);

	/**
	 * Returns all the object states.
	 *
	 * @return the object states
	 */
	public java.util.List<ObjectState> findAll();

	/**
	 * Returns a range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of object states
	 */
	public java.util.List<ObjectState> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object states
	 */
	public java.util.List<ObjectState> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object states
	 */
	public java.util.List<ObjectState> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectState>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object states from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object states.
	 *
	 * @return the number of object states
	 */
	public int countAll();

}