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

package com.liferay.app.builder.service.persistence;

import com.liferay.app.builder.exception.NoSuchAppVersionException;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the app builder app version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppVersionUtil
 * @generated
 */
@ProviderType
public interface AppBuilderAppVersionPersistence
	extends BasePersistence<AppBuilderAppVersion> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AppBuilderAppVersionUtil} to access the app builder app version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the app builder app versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByUuid(String uuid);

	/**
	 * Returns a range of all the app builder app versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public AppBuilderAppVersion[] findByUuid_PrevAndNext(
			long appBuilderAppVersionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Removes all the app builder app versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of app builder app versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching app builder app versions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the app builder app version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAppVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByUUID_G(String uuid, long groupId)
		throws NoSuchAppVersionException;

	/**
	 * Returns the app builder app version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the app builder app version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the app builder app version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the app builder app version that was removed
	 */
	public AppBuilderAppVersion removeByUUID_G(String uuid, long groupId)
		throws NoSuchAppVersionException;

	/**
	 * Returns the number of app builder app versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching app builder app versions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public AppBuilderAppVersion[] findByUuid_C_PrevAndNext(
			long appBuilderAppVersionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Removes all the app builder app versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching app builder app versions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the app builder app versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByGroupId(long groupId);

	/**
	 * Returns a range of all the app builder app versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder app versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the first app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the last app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the last app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public AppBuilderAppVersion[] findByGroupId_PrevAndNext(
			long appBuilderAppVersionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Removes all the app builder app versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of app builder app versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching app builder app versions
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the app builder app versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the app builder app versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder app versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the first app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the last app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the last app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public AppBuilderAppVersion[] findByCompanyId_PrevAndNext(
			long appBuilderAppVersionId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Removes all the app builder app versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of app builder app versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching app builder app versions
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId);

	/**
	 * Returns a range of all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByAppBuilderAppId_First(
			long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the first app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the last app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByAppBuilderAppId_Last(
			long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Returns the last app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public AppBuilderAppVersion[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderAppVersionId, long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppVersion> orderByComparator)
		throws NoSuchAppVersionException;

	/**
	 * Removes all the app builder app versions where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	public void removeByAppBuilderAppId(long appBuilderAppId);

	/**
	 * Returns the number of app builder app versions where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder app versions
	 */
	public int countByAppBuilderAppId(long appBuilderAppId);

	/**
	 * Returns the app builder app version where appBuilderAppId = &#63; and version = &#63; or throws a <code>NoSuchAppVersionException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion findByA_V(long appBuilderAppId, String version)
		throws NoSuchAppVersionException;

	/**
	 * Returns the app builder app version where appBuilderAppId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByA_V(
		long appBuilderAppId, String version);

	/**
	 * Returns the app builder app version where appBuilderAppId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public AppBuilderAppVersion fetchByA_V(
		long appBuilderAppId, String version, boolean useFinderCache);

	/**
	 * Removes the app builder app version where appBuilderAppId = &#63; and version = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the app builder app version that was removed
	 */
	public AppBuilderAppVersion removeByA_V(
			long appBuilderAppId, String version)
		throws NoSuchAppVersionException;

	/**
	 * Returns the number of app builder app versions where appBuilderAppId = &#63; and version = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the number of matching app builder app versions
	 */
	public int countByA_V(long appBuilderAppId, String version);

	/**
	 * Caches the app builder app version in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppVersion the app builder app version
	 */
	public void cacheResult(AppBuilderAppVersion appBuilderAppVersion);

	/**
	 * Caches the app builder app versions in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppVersions the app builder app versions
	 */
	public void cacheResult(
		java.util.List<AppBuilderAppVersion> appBuilderAppVersions);

	/**
	 * Creates a new app builder app version with the primary key. Does not add the app builder app version to the database.
	 *
	 * @param appBuilderAppVersionId the primary key for the new app builder app version
	 * @return the new app builder app version
	 */
	public AppBuilderAppVersion create(long appBuilderAppVersionId);

	/**
	 * Removes the app builder app version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version that was removed
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public AppBuilderAppVersion remove(long appBuilderAppVersionId)
		throws NoSuchAppVersionException;

	public AppBuilderAppVersion updateImpl(
		AppBuilderAppVersion appBuilderAppVersion);

	/**
	 * Returns the app builder app version with the primary key or throws a <code>NoSuchAppVersionException</code> if it could not be found.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public AppBuilderAppVersion findByPrimaryKey(long appBuilderAppVersionId)
		throws NoSuchAppVersionException;

	/**
	 * Returns the app builder app version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version, or <code>null</code> if a app builder app version with the primary key could not be found
	 */
	public AppBuilderAppVersion fetchByPrimaryKey(long appBuilderAppVersionId);

	/**
	 * Returns all the app builder app versions.
	 *
	 * @return the app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findAll();

	/**
	 * Returns a range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder app versions
	 */
	public java.util.List<AppBuilderAppVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderAppVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the app builder app versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of app builder app versions.
	 *
	 * @return the number of app builder app versions
	 */
	public int countAll();

}