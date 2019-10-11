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

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddm data provider instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceUtil
 * @generated
 */
@ProviderType
public interface DDMDataProviderInstancePersistence
	extends BasePersistence<DDMDataProviderInstance> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMDataProviderInstanceUtil} to access the ddm data provider instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ddm data provider instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByUuid(String uuid);

	/**
	 * Returns a range of all the ddm data provider instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @return the range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the ddm data provider instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm data provider instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the first ddm data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns the last ddm data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the last ddm data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns the ddm data provider instances before and after the current ddm data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current ddm data provider instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a ddm data provider instance with the primary key could not be found
	 */
	public DDMDataProviderInstance[] findByUuid_PrevAndNext(
			long dataProviderInstanceId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Removes all the ddm data provider instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of ddm data provider instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddm data provider instances
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the ddm data provider instance where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDataProviderInstanceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance findByUUID_G(String uuid, long groupId)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the ddm data provider instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the ddm data provider instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the ddm data provider instance where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddm data provider instance that was removed
	 */
	public DDMDataProviderInstance removeByUUID_G(String uuid, long groupId)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the number of ddm data provider instances where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddm data provider instances
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the ddm data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the ddm data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @return the range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the first ddm data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns the last ddm data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the last ddm data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns the ddm data provider instances before and after the current ddm data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current ddm data provider instance
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a ddm data provider instance with the primary key could not be found
	 */
	public DDMDataProviderInstance[] findByUuid_C_PrevAndNext(
			long dataProviderInstanceId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Removes all the ddm data provider instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of ddm data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddm data provider instances
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the ddm data provider instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByGroupId(long groupId);

	/**
	 * Returns a range of all the ddm data provider instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @return the range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm data provider instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm data provider instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the first ddm data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns the last ddm data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the last ddm data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns the ddm data provider instances before and after the current ddm data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current ddm data provider instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a ddm data provider instance with the primary key could not be found
	 */
	public DDMDataProviderInstance[] findByGroupId_PrevAndNext(
			long dataProviderInstanceId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns all the ddm data provider instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching ddm data provider instances that the user has permission to view
	 */
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long groupId);

	/**
	 * Returns a range of all the ddm data provider instances that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @return the range of matching ddm data provider instances that the user has permission to view
	 */
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm data provider instances that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instances that the user has permission to view
	 */
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns the ddm data provider instances before and after the current ddm data provider instance in the ordered set of ddm data provider instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current ddm data provider instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a ddm data provider instance with the primary key could not be found
	 */
	public DDMDataProviderInstance[] filterFindByGroupId_PrevAndNext(
			long dataProviderInstanceId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns all the ddm data provider instances that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching ddm data provider instances that the user has permission to view
	 */
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long[] groupIds);

	/**
	 * Returns a range of all the ddm data provider instances that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @return the range of matching ddm data provider instances that the user has permission to view
	 */
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the ddm data provider instances that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instances that the user has permission to view
	 */
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns all the ddm data provider instances where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long[] groupIds);

	/**
	 * Returns a range of all the ddm data provider instances where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @return the range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the ddm data provider instances where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm data provider instances where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm data provider instances where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of ddm data provider instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching ddm data provider instances
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of ddm data provider instances where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching ddm data provider instances
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns the number of ddm data provider instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching ddm data provider instances that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the number of ddm data provider instances that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching ddm data provider instances that the user has permission to view
	 */
	public int filterCountByGroupId(long[] groupIds);

	/**
	 * Returns all the ddm data provider instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the ddm data provider instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @return the range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm data provider instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm data provider instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the first ddm data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns the last ddm data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the last ddm data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance, or <code>null</code> if a matching ddm data provider instance could not be found
	 */
	public DDMDataProviderInstance fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns the ddm data provider instances before and after the current ddm data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current ddm data provider instance
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a ddm data provider instance with the primary key could not be found
	 */
	public DDMDataProviderInstance[] findByCompanyId_PrevAndNext(
			long dataProviderInstanceId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Removes all the ddm data provider instances where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of ddm data provider instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ddm data provider instances
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the ddm data provider instance in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstance the ddm data provider instance
	 */
	public void cacheResult(DDMDataProviderInstance ddmDataProviderInstance);

	/**
	 * Caches the ddm data provider instances in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstances the ddm data provider instances
	 */
	public void cacheResult(
		java.util.List<DDMDataProviderInstance> ddmDataProviderInstances);

	/**
	 * Creates a new ddm data provider instance with the primary key. Does not add the ddm data provider instance to the database.
	 *
	 * @param dataProviderInstanceId the primary key for the new ddm data provider instance
	 * @return the new ddm data provider instance
	 */
	public DDMDataProviderInstance create(long dataProviderInstanceId);

	/**
	 * Removes the ddm data provider instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dataProviderInstanceId the primary key of the ddm data provider instance
	 * @return the ddm data provider instance that was removed
	 * @throws NoSuchDataProviderInstanceException if a ddm data provider instance with the primary key could not be found
	 */
	public DDMDataProviderInstance remove(long dataProviderInstanceId)
		throws NoSuchDataProviderInstanceException;

	public DDMDataProviderInstance updateImpl(
		DDMDataProviderInstance ddmDataProviderInstance);

	/**
	 * Returns the ddm data provider instance with the primary key or throws a <code>NoSuchDataProviderInstanceException</code> if it could not be found.
	 *
	 * @param dataProviderInstanceId the primary key of the ddm data provider instance
	 * @return the ddm data provider instance
	 * @throws NoSuchDataProviderInstanceException if a ddm data provider instance with the primary key could not be found
	 */
	public DDMDataProviderInstance findByPrimaryKey(long dataProviderInstanceId)
		throws NoSuchDataProviderInstanceException;

	/**
	 * Returns the ddm data provider instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dataProviderInstanceId the primary key of the ddm data provider instance
	 * @return the ddm data provider instance, or <code>null</code> if a ddm data provider instance with the primary key could not be found
	 */
	public DDMDataProviderInstance fetchByPrimaryKey(
		long dataProviderInstanceId);

	/**
	 * Returns all the ddm data provider instances.
	 *
	 * @return the ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findAll();

	/**
	 * Returns a range of all the ddm data provider instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @return the range of ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ddm data provider instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm data provider instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instances
	 * @param end the upper bound of the range of ddm data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm data provider instances
	 */
	public java.util.List<DDMDataProviderInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm data provider instances from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddm data provider instances.
	 *
	 * @return the number of ddm data provider instances
	 */
	public int countAll();

}