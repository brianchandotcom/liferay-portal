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

import com.liferay.app.builder.exception.NoSuchAppException;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the app builder app service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppUtil
 * @generated
 */
@ProviderType
public interface AppBuilderAppPersistence
	extends BasePersistence<AppBuilderApp> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AppBuilderAppUtil} to access the app builder app persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the app builder apps where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByUuid(String uuid);

	/**
	 * Returns a range of all the app builder apps where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public AppBuilderApp[] findByUuid_PrevAndNext(
			long appBuilderAppId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Removes all the app builder apps where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of app builder apps where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching app builder apps
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the app builder app where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByUUID_G(String uuid, long groupId)
		throws NoSuchAppException;

	/**
	 * Returns the app builder app where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the app builder app where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the app builder app where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the app builder app that was removed
	 */
	public AppBuilderApp removeByUUID_G(String uuid, long groupId)
		throws NoSuchAppException;

	/**
	 * Returns the number of app builder apps where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching app builder apps
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public AppBuilderApp[] findByUuid_C_PrevAndNext(
			long appBuilderAppId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Removes all the app builder apps where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching app builder apps
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the app builder apps where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByGroupId(long groupId);

	/**
	 * Returns a range of all the app builder apps where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public AppBuilderApp[] findByGroupId_PrevAndNext(
			long appBuilderAppId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Removes all the app builder apps where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of app builder apps where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching app builder apps
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByC_S(long companyId, int status);

	/**
	 * Returns a range of all the app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByC_S(
		long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByC_S_First(
			long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the first app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByC_S_Last(
			long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public AppBuilderApp[] findByC_S_PrevAndNext(
			long appBuilderAppId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Removes all the app builder apps where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	public void removeByC_S(long companyId, int status);

	/**
	 * Returns the number of app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching app builder apps
	 */
	public int countByC_S(long companyId, int status);

	/**
	 * Returns all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId);

	/**
	 * Returns a range of all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public java.util.List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByG_C_D_First(
			long groupId, long companyId, long ddmStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByG_C_D_First(
		long groupId, long companyId, long ddmStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public AppBuilderApp findByG_C_D_Last(
			long groupId, long companyId, long ddmStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public AppBuilderApp fetchByG_C_D_Last(
		long groupId, long companyId, long ddmStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public AppBuilderApp[] findByG_C_D_PrevAndNext(
			long appBuilderAppId, long groupId, long companyId,
			long ddmStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
				orderByComparator)
		throws NoSuchAppException;

	/**
	 * Removes all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 */
	public void removeByG_C_D(
		long groupId, long companyId, long ddmStructureId);

	/**
	 * Returns the number of app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching app builder apps
	 */
	public int countByG_C_D(long groupId, long companyId, long ddmStructureId);

	/**
	 * Caches the app builder app in the entity cache if it is enabled.
	 *
	 * @param appBuilderApp the app builder app
	 */
	public void cacheResult(AppBuilderApp appBuilderApp);

	/**
	 * Caches the app builder apps in the entity cache if it is enabled.
	 *
	 * @param appBuilderApps the app builder apps
	 */
	public void cacheResult(java.util.List<AppBuilderApp> appBuilderApps);

	/**
	 * Creates a new app builder app with the primary key. Does not add the app builder app to the database.
	 *
	 * @param appBuilderAppId the primary key for the new app builder app
	 * @return the new app builder app
	 */
	public AppBuilderApp create(long appBuilderAppId);

	/**
	 * Removes the app builder app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app that was removed
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public AppBuilderApp remove(long appBuilderAppId) throws NoSuchAppException;

	public AppBuilderApp updateImpl(AppBuilderApp appBuilderApp);

	/**
	 * Returns the app builder app with the primary key or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public AppBuilderApp findByPrimaryKey(long appBuilderAppId)
		throws NoSuchAppException;

	/**
	 * Returns the app builder app with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app, or <code>null</code> if a app builder app with the primary key could not be found
	 */
	public AppBuilderApp fetchByPrimaryKey(long appBuilderAppId);

	/**
	 * Returns all the app builder apps.
	 *
	 * @return the app builder apps
	 */
	public java.util.List<AppBuilderApp> findAll();

	/**
	 * Returns a range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of app builder apps
	 */
	public java.util.List<AppBuilderApp> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder apps
	 */
	public java.util.List<AppBuilderApp> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator);

	/**
	 * Returns an ordered range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder apps
	 */
	public java.util.List<AppBuilderApp> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AppBuilderApp>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the app builder apps from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of app builder apps.
	 *
	 * @return the number of app builder apps
	 */
	public int countAll();

}