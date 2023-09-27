/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectStateFlowException;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object state flow service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectStateFlowUtil
 * @generated
 */
@ProviderType
public interface ObjectStateFlowPersistence
	extends BasePersistence<ObjectStateFlow> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectStateFlowUtil} to access the object state flow persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object state flows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object state flows
	 */
	public java.util.List<ObjectStateFlow> findByUuid(String uuid);

	/**
	 * Returns a range of all the object state flows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of matching object state flows
	 */
	public java.util.List<ObjectStateFlow> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state flows
	 */
	public java.util.List<ObjectStateFlow> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state flows
	 */
	public java.util.List<ObjectStateFlow> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public ObjectStateFlow findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
				orderByComparator)
		throws NoSuchObjectStateFlowException;

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public ObjectStateFlow fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator);

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public ObjectStateFlow findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
				orderByComparator)
		throws NoSuchObjectStateFlowException;

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public ObjectStateFlow fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator);

	/**
	 * Returns the object state flows before and after the current object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param objectStateFlowId the primary key of the current object state flow
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	public ObjectStateFlow[] findByUuid_PrevAndNext(
			long objectStateFlowId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
				orderByComparator)
		throws NoSuchObjectStateFlowException;

	/**
	 * Removes all the object state flows where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object state flows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object state flows
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object state flows
	 */
	public java.util.List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of matching object state flows
	 */
	public java.util.List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state flows
	 */
	public java.util.List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state flows
	 */
	public java.util.List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public ObjectStateFlow findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
				orderByComparator)
		throws NoSuchObjectStateFlowException;

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public ObjectStateFlow fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator);

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public ObjectStateFlow findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
				orderByComparator)
		throws NoSuchObjectStateFlowException;

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public ObjectStateFlow fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator);

	/**
	 * Returns the object state flows before and after the current object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectStateFlowId the primary key of the current object state flow
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	public ObjectStateFlow[] findByUuid_C_PrevAndNext(
			long objectStateFlowId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
				orderByComparator)
		throws NoSuchObjectStateFlowException;

	/**
	 * Removes all the object state flows where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object state flows
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the object state flow where objectFieldId = &#63; or throws a <code>NoSuchObjectStateFlowException</code> if it could not be found.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public ObjectStateFlow findByObjectFieldId(long objectFieldId)
		throws NoSuchObjectStateFlowException;

	/**
	 * Returns the object state flow where objectFieldId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public ObjectStateFlow fetchByObjectFieldId(long objectFieldId);

	/**
	 * Returns the object state flow where objectFieldId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public ObjectStateFlow fetchByObjectFieldId(
		long objectFieldId, boolean useFinderCache);

	/**
	 * Removes the object state flow where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 * @return the object state flow that was removed
	 */
	public ObjectStateFlow removeByObjectFieldId(long objectFieldId)
		throws NoSuchObjectStateFlowException;

	/**
	 * Returns the number of object state flows where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object state flows
	 */
	public int countByObjectFieldId(long objectFieldId);

	/**
	 * Caches the object state flow in the entity cache if it is enabled.
	 *
	 * @param objectStateFlow the object state flow
	 */
	public void cacheResult(ObjectStateFlow objectStateFlow);

	/**
	 * Caches the object state flows in the entity cache if it is enabled.
	 *
	 * @param objectStateFlows the object state flows
	 */
	public void cacheResult(java.util.List<ObjectStateFlow> objectStateFlows);

	/**
	 * Creates a new object state flow with the primary key. Does not add the object state flow to the database.
	 *
	 * @param objectStateFlowId the primary key for the new object state flow
	 * @return the new object state flow
	 */
	public ObjectStateFlow create(long objectStateFlowId);

	/**
	 * Removes the object state flow with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow that was removed
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	public ObjectStateFlow remove(long objectStateFlowId)
		throws NoSuchObjectStateFlowException;

	public ObjectStateFlow updateImpl(ObjectStateFlow objectStateFlow);

	/**
	 * Returns the object state flow with the primary key or throws a <code>NoSuchObjectStateFlowException</code> if it could not be found.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	public ObjectStateFlow findByPrimaryKey(long objectStateFlowId)
		throws NoSuchObjectStateFlowException;

	/**
	 * Returns the object state flow with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow, or <code>null</code> if a object state flow with the primary key could not be found
	 */
	public ObjectStateFlow fetchByPrimaryKey(long objectStateFlowId);

	/**
	 * Returns all the object state flows.
	 *
	 * @return the object state flows
	 */
	public java.util.List<ObjectStateFlow> findAll();

	/**
	 * Returns a range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of object state flows
	 */
	public java.util.List<ObjectStateFlow> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object state flows
	 */
	public java.util.List<ObjectStateFlow> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object state flows
	 */
	public java.util.List<ObjectStateFlow> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectStateFlow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object state flows from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object state flows.
	 *
	 * @return the number of object state flows
	 */
	public int countAll();

}