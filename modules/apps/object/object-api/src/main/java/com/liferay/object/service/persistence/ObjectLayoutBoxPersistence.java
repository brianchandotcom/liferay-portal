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

import com.liferay.object.exception.NoSuchObjectLayoutBoxException;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object layout box service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxUtil
 * @generated
 */
@ProviderType
public interface ObjectLayoutBoxPersistence
	extends BasePersistence<ObjectLayoutBox> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectLayoutBoxUtil} to access the object layout box persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object layout boxes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid(String uuid);

	/**
	 * Returns a range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public ObjectLayoutBox findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public ObjectLayoutBox fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public ObjectLayoutBox findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public ObjectLayoutBox fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns the object layout boxes before and after the current object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutBoxId the primary key of the current object layout box
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public ObjectLayoutBox[] findByUuid_PrevAndNext(
			long objectLayoutBoxId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Removes all the object layout boxes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object layout boxes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout boxes
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public ObjectLayoutBox findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public ObjectLayoutBox fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public ObjectLayoutBox findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public ObjectLayoutBox fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns the object layout boxes before and after the current object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutBoxId the primary key of the current object layout box
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public ObjectLayoutBox[] findByUuid_C_PrevAndNext(
			long objectLayoutBoxId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Removes all the object layout boxes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout boxes
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @return the matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId);

	/**
	 * Returns a range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public ObjectLayoutBox findByObjectLayoutTabId_First(
			long objectLayoutTabId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the first object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public ObjectLayoutBox fetchByObjectLayoutTabId_First(
		long objectLayoutTabId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns the last object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public ObjectLayoutBox findByObjectLayoutTabId_Last(
			long objectLayoutTabId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the last object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public ObjectLayoutBox fetchByObjectLayoutTabId_Last(
		long objectLayoutTabId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns the object layout boxes before and after the current object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutBoxId the primary key of the current object layout box
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public ObjectLayoutBox[] findByObjectLayoutTabId_PrevAndNext(
			long objectLayoutBoxId, long objectLayoutTabId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Removes all the object layout boxes where objectLayoutTabId = &#63; from the database.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 */
	public void removeByObjectLayoutTabId(long objectLayoutTabId);

	/**
	 * Returns the number of object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @return the number of matching object layout boxes
	 */
	public int countByObjectLayoutTabId(long objectLayoutTabId);

	/**
	 * Caches the object layout box in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBox the object layout box
	 */
	public void cacheResult(ObjectLayoutBox objectLayoutBox);

	/**
	 * Caches the object layout boxes in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxes the object layout boxes
	 */
	public void cacheResult(java.util.List<ObjectLayoutBox> objectLayoutBoxes);

	/**
	 * Creates a new object layout box with the primary key. Does not add the object layout box to the database.
	 *
	 * @param objectLayoutBoxId the primary key for the new object layout box
	 * @return the new object layout box
	 */
	public ObjectLayoutBox create(long objectLayoutBoxId);

	/**
	 * Removes the object layout box with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box that was removed
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public ObjectLayoutBox remove(long objectLayoutBoxId)
		throws NoSuchObjectLayoutBoxException;

	public ObjectLayoutBox updateImpl(ObjectLayoutBox objectLayoutBox);

	/**
	 * Returns the object layout box with the primary key or throws a <code>NoSuchObjectLayoutBoxException</code> if it could not be found.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public ObjectLayoutBox findByPrimaryKey(long objectLayoutBoxId)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the object layout box with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box, or <code>null</code> if a object layout box with the primary key could not be found
	 */
	public ObjectLayoutBox fetchByPrimaryKey(long objectLayoutBoxId);

	/**
	 * Returns all the object layout boxes.
	 *
	 * @return the object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findAll();

	/**
	 * Returns a range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object layout boxes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object layout boxes.
	 *
	 * @return the number of object layout boxes
	 */
	public int countAll();

}