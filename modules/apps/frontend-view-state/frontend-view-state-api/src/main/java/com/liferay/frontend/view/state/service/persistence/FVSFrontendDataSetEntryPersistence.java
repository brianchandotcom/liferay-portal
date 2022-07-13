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

package com.liferay.frontend.view.state.service.persistence;

import com.liferay.frontend.view.state.exception.NoSuchFrontendDataSetEntryException;
import com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fvs frontend data set entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSFrontendDataSetEntryUtil
 * @generated
 */
@ProviderType
public interface FVSFrontendDataSetEntryPersistence
	extends BasePersistence<FVSFrontendDataSetEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FVSFrontendDataSetEntryUtil} to access the fvs frontend data set entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns the fvs frontend data set entries before and after the current fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the current fvs frontend data set entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public FVSFrontendDataSetEntry[] findByUuid_PrevAndNext(
			long fvsFrontendDataSetEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Removes all the fvs frontend data set entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of fvs frontend data set entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs frontend data set entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns the fvs frontend data set entries before and after the current fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the current fvs frontend data set entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public FVSFrontendDataSetEntry[] findByUuid_C_PrevAndNext(
			long fvsFrontendDataSetEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Removes all the fvs frontend data set entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs frontend data set entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId);

	/**
	 * Returns a range of all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId, int start,
		int end);

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry findByU_F_P_P_First(
			long userId, String fdsName, long plid, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry fetchByU_F_P_P_First(
		long userId, String fdsName, long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry findByU_F_P_P_Last(
			long userId, String fdsName, long plid, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public FVSFrontendDataSetEntry fetchByU_F_P_P_Last(
		long userId, String fdsName, long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns the fvs frontend data set entries before and after the current fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the current fvs frontend data set entry
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public FVSFrontendDataSetEntry[] findByU_F_P_P_PrevAndNext(
			long fvsFrontendDataSetEntryId, long userId, String fdsName,
			long plid, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Removes all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	public void removeByU_F_P_P(
		long userId, String fdsName, long plid, String portletId);

	/**
	 * Returns the number of fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching fvs frontend data set entries
	 */
	public int countByU_F_P_P(
		long userId, String fdsName, long plid, String portletId);

	/**
	 * Caches the fvs frontend data set entry in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSetEntry the fvs frontend data set entry
	 */
	public void cacheResult(FVSFrontendDataSetEntry fvsFrontendDataSetEntry);

	/**
	 * Caches the fvs frontend data set entries in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSetEntries the fvs frontend data set entries
	 */
	public void cacheResult(
		java.util.List<FVSFrontendDataSetEntry> fvsFrontendDataSetEntries);

	/**
	 * Creates a new fvs frontend data set entry with the primary key. Does not add the fvs frontend data set entry to the database.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key for the new fvs frontend data set entry
	 * @return the new fvs frontend data set entry
	 */
	public FVSFrontendDataSetEntry create(long fvsFrontendDataSetEntryId);

	/**
	 * Removes the fvs frontend data set entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry that was removed
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public FVSFrontendDataSetEntry remove(long fvsFrontendDataSetEntryId)
		throws NoSuchFrontendDataSetEntryException;

	public FVSFrontendDataSetEntry updateImpl(
		FVSFrontendDataSetEntry fvsFrontendDataSetEntry);

	/**
	 * Returns the fvs frontend data set entry with the primary key or throws a <code>NoSuchFrontendDataSetEntryException</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public FVSFrontendDataSetEntry findByPrimaryKey(
			long fvsFrontendDataSetEntryId)
		throws NoSuchFrontendDataSetEntryException;

	/**
	 * Returns the fvs frontend data set entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry, or <code>null</code> if a fvs frontend data set entry with the primary key could not be found
	 */
	public FVSFrontendDataSetEntry fetchByPrimaryKey(
		long fvsFrontendDataSetEntryId);

	/**
	 * Returns all the fvs frontend data set entries.
	 *
	 * @return the fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findAll();

	/**
	 * Returns a range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs frontend data set entries
	 */
	public java.util.List<FVSFrontendDataSetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FVSFrontendDataSetEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fvs frontend data set entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fvs frontend data set entries.
	 *
	 * @return the number of fvs frontend data set entries
	 */
	public int countAll();

}