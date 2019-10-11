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

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddm form instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceUtil
 * @generated
 */
@ProviderType
public interface DDMFormInstancePersistence
	extends BasePersistence<DDMFormInstance> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMFormInstanceUtil} to access the ddm form instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ddm form instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByUuid(String uuid);

	/**
	 * Returns a range of all the ddm form instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @return the range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm form instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance
	 * @throws NoSuchFormInstanceException if a matching ddm form instance could not be found
	 */
	public DDMFormInstance findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Returns the first ddm form instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	 */
	public DDMFormInstance fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns the last ddm form instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance
	 * @throws NoSuchFormInstanceException if a matching ddm form instance could not be found
	 */
	public DDMFormInstance findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Returns the last ddm form instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	 */
	public DDMFormInstance fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns the ddm form instances before and after the current ddm form instance in the ordered set where uuid = &#63;.
	 *
	 * @param formInstanceId the primary key of the current ddm form instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance
	 * @throws NoSuchFormInstanceException if a ddm form instance with the primary key could not be found
	 */
	public DDMFormInstance[] findByUuid_PrevAndNext(
			long formInstanceId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Removes all the ddm form instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of ddm form instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddm form instances
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the ddm form instance where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFormInstanceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm form instance
	 * @throws NoSuchFormInstanceException if a matching ddm form instance could not be found
	 */
	public DDMFormInstance findByUUID_G(String uuid, long groupId)
		throws NoSuchFormInstanceException;

	/**
	 * Returns the ddm form instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	 */
	public DDMFormInstance fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the ddm form instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	 */
	public DDMFormInstance fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the ddm form instance where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddm form instance that was removed
	 */
	public DDMFormInstance removeByUUID_G(String uuid, long groupId)
		throws NoSuchFormInstanceException;

	/**
	 * Returns the number of ddm form instances where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddm form instances
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the ddm form instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the ddm form instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @return the range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm form instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance
	 * @throws NoSuchFormInstanceException if a matching ddm form instance could not be found
	 */
	public DDMFormInstance findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Returns the first ddm form instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	 */
	public DDMFormInstance fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns the last ddm form instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance
	 * @throws NoSuchFormInstanceException if a matching ddm form instance could not be found
	 */
	public DDMFormInstance findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Returns the last ddm form instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	 */
	public DDMFormInstance fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns the ddm form instances before and after the current ddm form instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param formInstanceId the primary key of the current ddm form instance
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance
	 * @throws NoSuchFormInstanceException if a ddm form instance with the primary key could not be found
	 */
	public DDMFormInstance[] findByUuid_C_PrevAndNext(
			long formInstanceId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Removes all the ddm form instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of ddm form instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddm form instances
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the ddm form instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByGroupId(long groupId);

	/**
	 * Returns a range of all the ddm form instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @return the range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm form instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance
	 * @throws NoSuchFormInstanceException if a matching ddm form instance could not be found
	 */
	public DDMFormInstance findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Returns the first ddm form instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	 */
	public DDMFormInstance fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns the last ddm form instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance
	 * @throws NoSuchFormInstanceException if a matching ddm form instance could not be found
	 */
	public DDMFormInstance findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Returns the last ddm form instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	 */
	public DDMFormInstance fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns the ddm form instances before and after the current ddm form instance in the ordered set where groupId = &#63;.
	 *
	 * @param formInstanceId the primary key of the current ddm form instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance
	 * @throws NoSuchFormInstanceException if a ddm form instance with the primary key could not be found
	 */
	public DDMFormInstance[] findByGroupId_PrevAndNext(
			long formInstanceId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Returns all the ddm form instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching ddm form instances that the user has permission to view
	 */
	public java.util.List<DDMFormInstance> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the ddm form instances that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @return the range of matching ddm form instances that the user has permission to view
	 */
	public java.util.List<DDMFormInstance> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instances that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instances that the user has permission to view
	 */
	public java.util.List<DDMFormInstance> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns the ddm form instances before and after the current ddm form instance in the ordered set of ddm form instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param formInstanceId the primary key of the current ddm form instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance
	 * @throws NoSuchFormInstanceException if a ddm form instance with the primary key could not be found
	 */
	public DDMFormInstance[] filterFindByGroupId_PrevAndNext(
			long formInstanceId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
				orderByComparator)
		throws NoSuchFormInstanceException;

	/**
	 * Returns all the ddm form instances that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching ddm form instances that the user has permission to view
	 */
	public java.util.List<DDMFormInstance> filterFindByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the ddm form instances that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @return the range of matching ddm form instances that the user has permission to view
	 */
	public java.util.List<DDMFormInstance> filterFindByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instances that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instances that the user has permission to view
	 */
	public java.util.List<DDMFormInstance> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns all the ddm form instances where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the ddm form instances where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @return the range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instances where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instances where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instances
	 */
	public java.util.List<DDMFormInstance> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm form instances where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of ddm form instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching ddm form instances
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of ddm form instances where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching ddm form instances
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns the number of ddm form instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching ddm form instances that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the number of ddm form instances that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching ddm form instances that the user has permission to view
	 */
	public int filterCountByGroupId(long[] groupIds);

	/**
	 * Caches the ddm form instance in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstance the ddm form instance
	 */
	public void cacheResult(DDMFormInstance ddmFormInstance);

	/**
	 * Caches the ddm form instances in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstances the ddm form instances
	 */
	public void cacheResult(java.util.List<DDMFormInstance> ddmFormInstances);

	/**
	 * Creates a new ddm form instance with the primary key. Does not add the ddm form instance to the database.
	 *
	 * @param formInstanceId the primary key for the new ddm form instance
	 * @return the new ddm form instance
	 */
	public DDMFormInstance create(long formInstanceId);

	/**
	 * Removes the ddm form instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceId the primary key of the ddm form instance
	 * @return the ddm form instance that was removed
	 * @throws NoSuchFormInstanceException if a ddm form instance with the primary key could not be found
	 */
	public DDMFormInstance remove(long formInstanceId)
		throws NoSuchFormInstanceException;

	public DDMFormInstance updateImpl(DDMFormInstance ddmFormInstance);

	/**
	 * Returns the ddm form instance with the primary key or throws a <code>NoSuchFormInstanceException</code> if it could not be found.
	 *
	 * @param formInstanceId the primary key of the ddm form instance
	 * @return the ddm form instance
	 * @throws NoSuchFormInstanceException if a ddm form instance with the primary key could not be found
	 */
	public DDMFormInstance findByPrimaryKey(long formInstanceId)
		throws NoSuchFormInstanceException;

	/**
	 * Returns the ddm form instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formInstanceId the primary key of the ddm form instance
	 * @return the ddm form instance, or <code>null</code> if a ddm form instance with the primary key could not be found
	 */
	public DDMFormInstance fetchByPrimaryKey(long formInstanceId);

	/**
	 * Returns all the ddm form instances.
	 *
	 * @return the ddm form instances
	 */
	public java.util.List<DDMFormInstance> findAll();

	/**
	 * Returns a range of all the ddm form instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @return the range of ddm form instances
	 */
	public java.util.List<DDMFormInstance> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instances
	 */
	public java.util.List<DDMFormInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm form instances
	 */
	public java.util.List<DDMFormInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm form instances from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddm form instances.
	 *
	 * @return the number of ddm form instances
	 */
	public int countAll();

}