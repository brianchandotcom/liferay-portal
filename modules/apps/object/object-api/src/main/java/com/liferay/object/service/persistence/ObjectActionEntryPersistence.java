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

import com.liferay.object.exception.NoSuchObjectActionEntryException;
import com.liferay.object.model.ObjectActionEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object action entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectActionEntryUtil
 * @generated
 */
@ProviderType
public interface ObjectActionEntryPersistence
	extends BasePersistence<ObjectActionEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectActionEntryUtil} to access the object action entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object action entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the object action entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @return the range of matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object action entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object action entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	public ObjectActionEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
				orderByComparator)
		throws NoSuchObjectActionEntryException;

	/**
	 * Returns the first object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	public ObjectActionEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns the last object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	public ObjectActionEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
				orderByComparator)
		throws NoSuchObjectActionEntryException;

	/**
	 * Returns the last object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	public ObjectActionEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns the object action entries before and after the current object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param objectActionEntryId the primary key of the current object action entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object action entry
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	public ObjectActionEntry[] findByUuid_PrevAndNext(
			long objectActionEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
				orderByComparator)
		throws NoSuchObjectActionEntryException;

	/**
	 * Removes all the object action entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object action entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object action entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @return the range of matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	public ObjectActionEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
				orderByComparator)
		throws NoSuchObjectActionEntryException;

	/**
	 * Returns the first object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	public ObjectActionEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns the last object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	public ObjectActionEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
				orderByComparator)
		throws NoSuchObjectActionEntryException;

	/**
	 * Returns the last object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	public ObjectActionEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns the object action entries before and after the current object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectActionEntryId the primary key of the current object action entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object action entry
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	public ObjectActionEntry[] findByUuid_C_PrevAndNext(
			long objectActionEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
				orderByComparator)
		throws NoSuchObjectActionEntryException;

	/**
	 * Removes all the object action entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object action entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @return the matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByO_A_T(
		long objectDefinitionId, boolean active, String triggerName);

	/**
	 * Returns a range of all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @return the range of matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByO_A_T(
		long objectDefinitionId, boolean active, String triggerName, int start,
		int end);

	/**
	 * Returns an ordered range of all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByO_A_T(
		long objectDefinitionId, boolean active, String triggerName, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object action entries
	 */
	public java.util.List<ObjectActionEntry> findByO_A_T(
		long objectDefinitionId, boolean active, String triggerName, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	public ObjectActionEntry findByO_A_T_First(
			long objectDefinitionId, boolean active, String triggerName,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
				orderByComparator)
		throws NoSuchObjectActionEntryException;

	/**
	 * Returns the first object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	public ObjectActionEntry fetchByO_A_T_First(
		long objectDefinitionId, boolean active, String triggerName,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns the last object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	public ObjectActionEntry findByO_A_T_Last(
			long objectDefinitionId, boolean active, String triggerName,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
				orderByComparator)
		throws NoSuchObjectActionEntryException;

	/**
	 * Returns the last object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	public ObjectActionEntry fetchByO_A_T_Last(
		long objectDefinitionId, boolean active, String triggerName,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns the object action entries before and after the current object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectActionEntryId the primary key of the current object action entry
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object action entry
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	public ObjectActionEntry[] findByO_A_T_PrevAndNext(
			long objectActionEntryId, long objectDefinitionId, boolean active,
			String triggerName,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
				orderByComparator)
		throws NoSuchObjectActionEntryException;

	/**
	 * Removes all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 */
	public void removeByO_A_T(
		long objectDefinitionId, boolean active, String triggerName);

	/**
	 * Returns the number of object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @return the number of matching object action entries
	 */
	public int countByO_A_T(
		long objectDefinitionId, boolean active, String triggerName);

	/**
	 * Caches the object action entry in the entity cache if it is enabled.
	 *
	 * @param objectActionEntry the object action entry
	 */
	public void cacheResult(ObjectActionEntry objectActionEntry);

	/**
	 * Caches the object action entries in the entity cache if it is enabled.
	 *
	 * @param objectActionEntries the object action entries
	 */
	public void cacheResult(
		java.util.List<ObjectActionEntry> objectActionEntries);

	/**
	 * Creates a new object action entry with the primary key. Does not add the object action entry to the database.
	 *
	 * @param objectActionEntryId the primary key for the new object action entry
	 * @return the new object action entry
	 */
	public ObjectActionEntry create(long objectActionEntryId);

	/**
	 * Removes the object action entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectActionEntryId the primary key of the object action entry
	 * @return the object action entry that was removed
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	public ObjectActionEntry remove(long objectActionEntryId)
		throws NoSuchObjectActionEntryException;

	public ObjectActionEntry updateImpl(ObjectActionEntry objectActionEntry);

	/**
	 * Returns the object action entry with the primary key or throws a <code>NoSuchObjectActionEntryException</code> if it could not be found.
	 *
	 * @param objectActionEntryId the primary key of the object action entry
	 * @return the object action entry
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	public ObjectActionEntry findByPrimaryKey(long objectActionEntryId)
		throws NoSuchObjectActionEntryException;

	/**
	 * Returns the object action entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectActionEntryId the primary key of the object action entry
	 * @return the object action entry, or <code>null</code> if a object action entry with the primary key could not be found
	 */
	public ObjectActionEntry fetchByPrimaryKey(long objectActionEntryId);

	/**
	 * Returns all the object action entries.
	 *
	 * @return the object action entries
	 */
	public java.util.List<ObjectActionEntry> findAll();

	/**
	 * Returns a range of all the object action entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @return the range of object action entries
	 */
	public java.util.List<ObjectActionEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object action entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object action entries
	 */
	public java.util.List<ObjectActionEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object action entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object action entries
	 */
	public java.util.List<ObjectActionEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectActionEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object action entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object action entries.
	 *
	 * @return the number of object action entries
	 */
	public int countAll();

}