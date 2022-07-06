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

import com.liferay.object.exception.NoSuchObjectStateTransitionException;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object state transition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectStateTransitionUtil
 * @generated
 */
@ProviderType
public interface ObjectStateTransitionPersistence
	extends BasePersistence<ObjectStateTransition> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectStateTransitionUtil} to access the object state transition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object state transitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByUuid(String uuid);

	/**
	 * Returns a range of all the object state transitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public ObjectStateTransition[] findByUuid_PrevAndNext(
			long objectStateTransitionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Removes all the object state transitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object state transitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object state transitions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public ObjectStateTransition[] findByUuid_C_PrevAndNext(
			long objectStateTransitionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Removes all the object state transitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object state transitions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId);

	/**
	 * Returns a range of all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end);

	/**
	 * Returns an ordered range of all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findByObjectStateFlowId_First(
			long objectStateFlowId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the first object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchByObjectStateFlowId_First(
		long objectStateFlowId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the last object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findByObjectStateFlowId_Last(
			long objectStateFlowId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the last object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchByObjectStateFlowId_Last(
		long objectStateFlowId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public ObjectStateTransition[] findByObjectStateFlowId_PrevAndNext(
			long objectStateTransitionId, long objectStateFlowId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Removes all the object state transitions where objectStateFlowId = &#63; from the database.
	 *
	 * @param objectStateFlowId the object state flow ID
	 */
	public void removeByObjectStateFlowId(long objectStateFlowId);

	/**
	 * Returns the number of object state transitions where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the number of matching object state transitions
	 */
	public int countByObjectStateFlowId(long objectStateFlowId);

	/**
	 * Returns all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @return the matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId);

	/**
	 * Returns a range of all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId, int start, int end);

	/**
	 * Returns an ordered range of all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findBySourceObjectStateId_First(
			long sourceObjectStateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the first object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchBySourceObjectStateId_First(
		long sourceObjectStateId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the last object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findBySourceObjectStateId_Last(
			long sourceObjectStateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the last object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchBySourceObjectStateId_Last(
		long sourceObjectStateId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public ObjectStateTransition[] findBySourceObjectStateId_PrevAndNext(
			long objectStateTransitionId, long sourceObjectStateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Removes all the object state transitions where sourceObjectStateId = &#63; from the database.
	 *
	 * @param sourceObjectStateId the source object state ID
	 */
	public void removeBySourceObjectStateId(long sourceObjectStateId);

	/**
	 * Returns the number of object state transitions where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @return the number of matching object state transitions
	 */
	public int countBySourceObjectStateId(long sourceObjectStateId);

	/**
	 * Returns all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @return the matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId);

	/**
	 * Returns a range of all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId, int start, int end);

	/**
	 * Returns an ordered range of all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public java.util.List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findByTargetObjectStateId_First(
			long targetObjectStateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the first object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchByTargetObjectStateId_First(
		long targetObjectStateId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the last object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public ObjectStateTransition findByTargetObjectStateId_Last(
			long targetObjectStateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the last object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public ObjectStateTransition fetchByTargetObjectStateId_Last(
		long targetObjectStateId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public ObjectStateTransition[] findByTargetObjectStateId_PrevAndNext(
			long objectStateTransitionId, long targetObjectStateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Removes all the object state transitions where targetObjectStateId = &#63; from the database.
	 *
	 * @param targetObjectStateId the target object state ID
	 */
	public void removeByTargetObjectStateId(long targetObjectStateId);

	/**
	 * Returns the number of object state transitions where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @return the number of matching object state transitions
	 */
	public int countByTargetObjectStateId(long targetObjectStateId);

	/**
	 * Caches the object state transition in the entity cache if it is enabled.
	 *
	 * @param objectStateTransition the object state transition
	 */
	public void cacheResult(ObjectStateTransition objectStateTransition);

	/**
	 * Caches the object state transitions in the entity cache if it is enabled.
	 *
	 * @param objectStateTransitions the object state transitions
	 */
	public void cacheResult(
		java.util.List<ObjectStateTransition> objectStateTransitions);

	/**
	 * Creates a new object state transition with the primary key. Does not add the object state transition to the database.
	 *
	 * @param objectStateTransitionId the primary key for the new object state transition
	 * @return the new object state transition
	 */
	public ObjectStateTransition create(long objectStateTransitionId);

	/**
	 * Removes the object state transition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectStateTransitionId the primary key of the object state transition
	 * @return the object state transition that was removed
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public ObjectStateTransition remove(long objectStateTransitionId)
		throws NoSuchObjectStateTransitionException;

	public ObjectStateTransition updateImpl(
		ObjectStateTransition objectStateTransition);

	/**
	 * Returns the object state transition with the primary key or throws a <code>NoSuchObjectStateTransitionException</code> if it could not be found.
	 *
	 * @param objectStateTransitionId the primary key of the object state transition
	 * @return the object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public ObjectStateTransition findByPrimaryKey(long objectStateTransitionId)
		throws NoSuchObjectStateTransitionException;

	/**
	 * Returns the object state transition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectStateTransitionId the primary key of the object state transition
	 * @return the object state transition, or <code>null</code> if a object state transition with the primary key could not be found
	 */
	public ObjectStateTransition fetchByPrimaryKey(
		long objectStateTransitionId);

	/**
	 * Returns all the object state transitions.
	 *
	 * @return the object state transitions
	 */
	public java.util.List<ObjectStateTransition> findAll();

	/**
	 * Returns a range of all the object state transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of object state transitions
	 */
	public java.util.List<ObjectStateTransition> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object state transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object state transitions
	 */
	public java.util.List<ObjectStateTransition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object state transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object state transitions
	 */
	public java.util.List<ObjectStateTransition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateTransition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object state transitions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object state transitions.
	 *
	 * @return the number of object state transitions
	 */
	public int countAll();

}