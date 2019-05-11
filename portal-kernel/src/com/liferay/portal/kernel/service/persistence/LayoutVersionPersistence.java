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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchLayoutVersionException;
import com.liferay.portal.kernel.model.LayoutVersion;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutVersionUtil
 * @generated
 */
@ProviderType
public interface LayoutVersionPersistence
	extends BasePersistence<LayoutVersion> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutVersionUtil} to access the layout version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout versions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByPlid(long plid);

	/**
	 * Returns a range of all the layout versions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByPlid(
		long plid, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByPlid_First(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByPlid_Last(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where plid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByPlid_PrevAndNext(
			long layoutVersionId, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public void removeByPlid(long plid);

	/**
	 * Returns the number of layout versions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout versions
	 */
	public int countByPlid(long plid);

	/**
	 * Returns the layout version where plid = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByPlid_Version(long plid, int version)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the layout version where plid = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByPlid_Version(long plid, int version);

	/**
	 * Returns the layout version where plid = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByPlid_Version(
		long plid, int version, boolean retrieveFromCache);

	/**
	 * Removes the layout version where plid = &#63; and version = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the layout version that was removed
	 */
	public LayoutVersion removeByPlid_Version(long plid, int version)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the number of layout versions where plid = &#63; and version = &#63;.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByPlid_Version(long plid, int version);

	/**
	 * Returns all the layout versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByUuid_PrevAndNext(
			long layoutVersionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout versions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_Version(
		String uuid, int version);

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_Version(
		String uuid, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUuid_Version_First(
			String uuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUuid_Version_First(
		String uuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUuid_Version_Last(
			String uuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUuid_Version_Last(
		String uuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByUuid_Version_PrevAndNext(
			long layoutVersionId, String uuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	public void removeByUuid_Version(String uuid, int version);

	/**
	 * Returns the number of layout versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByUuid_Version(String uuid, int version);

	/**
	 * Returns all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout);

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUUID_G_P_First(
			String uuid, long groupId, boolean privateLayout,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUUID_G_P_First(
		String uuid, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUUID_G_P_Last(
			String uuid, long groupId, boolean privateLayout,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUUID_G_P_Last(
		String uuid, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByUUID_G_P_PrevAndNext(
			long layoutVersionId, String uuid, long groupId,
			boolean privateLayout,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	public void removeByUUID_G_P(
		String uuid, long groupId, boolean privateLayout);

	/**
	 * Returns the number of layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout versions
	 */
	public int countByUUID_G_P(
		String uuid, long groupId, boolean privateLayout);

	/**
	 * Returns the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUUID_G_P_Version(
			String uuid, long groupId, boolean privateLayout, int version)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUUID_G_P_Version(
		String uuid, long groupId, boolean privateLayout, int version);

	/**
	 * Returns the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUUID_G_P_Version(
		String uuid, long groupId, boolean privateLayout, int version,
		boolean retrieveFromCache);

	/**
	 * Removes the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the layout version that was removed
	 */
	public LayoutVersion removeByUUID_G_P_Version(
			String uuid, long groupId, boolean privateLayout, int version)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the number of layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByUUID_G_P_Version(
		String uuid, long groupId, boolean privateLayout, int version);

	/**
	 * Returns all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByUuid_C_PrevAndNext(
			long layoutVersionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout versions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version);

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByUuid_C_Version_Last(
			String uuid, long companyId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByUuid_C_Version_Last(
		String uuid, long companyId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByUuid_C_Version_PrevAndNext(
			long layoutVersionId, String uuid, long companyId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	public void removeByUuid_C_Version(
		String uuid, long companyId, int version);

	/**
	 * Returns the number of layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByUuid_C_Version(String uuid, long companyId, int version);

	/**
	 * Returns all the layout versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByGroupId(long groupId);

	/**
	 * Returns a range of all the layout versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByGroupId_PrevAndNext(
			long layoutVersionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of layout versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout versions
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByGroupId_Version(
		long groupId, int version);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByGroupId_Version(
		long groupId, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByGroupId_Version_First(
			long groupId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByGroupId_Version_First(
		long groupId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByGroupId_Version_Last(
			long groupId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByGroupId_Version_PrevAndNext(
			long layoutVersionId, long groupId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	public void removeByGroupId_Version(long groupId, int version);

	/**
	 * Returns the number of layout versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByGroupId_Version(long groupId, int version);

	/**
	 * Returns all the layout versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the layout versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByCompanyId_PrevAndNext(
			long layoutVersionId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of layout versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching layout versions
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version);

	/**
	 * Returns a range of all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByCompanyId_Version_First(
			long companyId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByCompanyId_Version_First(
		long companyId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByCompanyId_Version_Last(
			long companyId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByCompanyId_Version_Last(
		long companyId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByCompanyId_Version_PrevAndNext(
			long layoutVersionId, long companyId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where companyId = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 */
	public void removeByCompanyId_Version(long companyId, int version);

	/**
	 * Returns the number of layout versions where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByCompanyId_Version(long companyId, int version);

	/**
	 * Returns all the layout versions where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByParentPlid(long parentPlid);

	/**
	 * Returns a range of all the layout versions where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByParentPlid(
		long parentPlid, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByParentPlid(
		long parentPlid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByParentPlid(
		long parentPlid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByParentPlid_First(
			long parentPlid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByParentPlid_First(
		long parentPlid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByParentPlid_Last(
			long parentPlid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByParentPlid_Last(
		long parentPlid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByParentPlid_PrevAndNext(
			long layoutVersionId, long parentPlid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where parentPlid = &#63; from the database.
	 *
	 * @param parentPlid the parent plid
	 */
	public void removeByParentPlid(long parentPlid);

	/**
	 * Returns the number of layout versions where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @return the number of matching layout versions
	 */
	public int countByParentPlid(long parentPlid);

	/**
	 * Returns all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version);

	/**
	 * Returns a range of all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByParentPlid_Version_First(
			long parentPlid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByParentPlid_Version_First(
		long parentPlid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByParentPlid_Version_Last(
			long parentPlid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByParentPlid_Version_Last(
		long parentPlid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByParentPlid_Version_PrevAndNext(
			long layoutVersionId, long parentPlid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where parentPlid = &#63; and version = &#63; from the database.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 */
	public void removeByParentPlid_Version(long parentPlid, int version);

	/**
	 * Returns the number of layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByParentPlid_Version(long parentPlid, int version);

	/**
	 * Returns all the layout versions where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByIconImageId(long iconImageId);

	/**
	 * Returns a range of all the layout versions where iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByIconImageId(
		long iconImageId, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByIconImageId(
		long iconImageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByIconImageId(
		long iconImageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByIconImageId_First(
			long iconImageId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByIconImageId_First(
		long iconImageId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByIconImageId_Last(
			long iconImageId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByIconImageId_Last(
		long iconImageId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByIconImageId_PrevAndNext(
			long layoutVersionId, long iconImageId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where iconImageId = &#63; from the database.
	 *
	 * @param iconImageId the icon image ID
	 */
	public void removeByIconImageId(long iconImageId);

	/**
	 * Returns the number of layout versions where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @return the number of matching layout versions
	 */
	public int countByIconImageId(long iconImageId);

	/**
	 * Returns all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version);

	/**
	 * Returns a range of all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByIconImageId_Version_First(
			long iconImageId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByIconImageId_Version_First(
		long iconImageId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByIconImageId_Version_Last(
			long iconImageId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByIconImageId_Version_Last(
		long iconImageId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByIconImageId_Version_PrevAndNext(
			long layoutVersionId, long iconImageId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where iconImageId = &#63; and version = &#63; from the database.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 */
	public void removeByIconImageId_Version(long iconImageId, int version);

	/**
	 * Returns the number of layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByIconImageId_Version(long iconImageId, int version);

	/**
	 * Returns all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid);

	/**
	 * Returns a range of all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByLayoutPrototypeUuid_First(
			String layoutPrototypeUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByLayoutPrototypeUuid_First(
		String layoutPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByLayoutPrototypeUuid_Last(
			String layoutPrototypeUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByLayoutPrototypeUuid_Last(
		String layoutPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByLayoutPrototypeUuid_PrevAndNext(
			long layoutVersionId, String layoutPrototypeUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where layoutPrototypeUuid = &#63; from the database.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 */
	public void removeByLayoutPrototypeUuid(String layoutPrototypeUuid);

	/**
	 * Returns the number of layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the number of matching layout versions
	 */
	public int countByLayoutPrototypeUuid(String layoutPrototypeUuid);

	/**
	 * Returns all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version);

	/**
	 * Returns a range of all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByLayoutPrototypeUuid_Version_First(
			String layoutPrototypeUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByLayoutPrototypeUuid_Version_First(
		String layoutPrototypeUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByLayoutPrototypeUuid_Version_Last(
			String layoutPrototypeUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByLayoutPrototypeUuid_Version_Last(
		String layoutPrototypeUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByLayoutPrototypeUuid_Version_PrevAndNext(
			long layoutVersionId, String layoutPrototypeUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where layoutPrototypeUuid = &#63; and version = &#63; from the database.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 */
	public void removeByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version);

	/**
	 * Returns the number of layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version);

	/**
	 * Returns all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid);

	/**
	 * Returns a range of all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findBySourcePrototypeLayoutUuid_First(
			String sourcePrototypeLayoutUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchBySourcePrototypeLayoutUuid_First(
		String sourcePrototypeLayoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findBySourcePrototypeLayoutUuid_Last(
			String sourcePrototypeLayoutUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchBySourcePrototypeLayoutUuid_Last(
		String sourcePrototypeLayoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findBySourcePrototypeLayoutUuid_PrevAndNext(
			long layoutVersionId, String sourcePrototypeLayoutUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where sourcePrototypeLayoutUuid = &#63; from the database.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 */
	public void removeBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid);

	/**
	 * Returns the number of layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the number of matching layout versions
	 */
	public int countBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid);

	/**
	 * Returns all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion>
		findBySourcePrototypeLayoutUuid_Version(
			String sourcePrototypeLayoutUuid, int version);

	/**
	 * Returns a range of all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion>
		findBySourcePrototypeLayoutUuid_Version(
			String sourcePrototypeLayoutUuid, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion>
		findBySourcePrototypeLayoutUuid_Version(
			String sourcePrototypeLayoutUuid, int version, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion>
		findBySourcePrototypeLayoutUuid_Version(
			String sourcePrototypeLayoutUuid, int version, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator,
			boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findBySourcePrototypeLayoutUuid_Version_First(
			String sourcePrototypeLayoutUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchBySourcePrototypeLayoutUuid_Version_First(
		String sourcePrototypeLayoutUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findBySourcePrototypeLayoutUuid_Version_Last(
			String sourcePrototypeLayoutUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchBySourcePrototypeLayoutUuid_Version_Last(
		String sourcePrototypeLayoutUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findBySourcePrototypeLayoutUuid_Version_PrevAndNext(
			long layoutVersionId, String sourcePrototypeLayoutUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63; from the database.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 */
	public void removeBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version);

	/**
	 * Returns the number of layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_First(
			long groupId, boolean privateLayout,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_Last(
			long groupId, boolean privateLayout,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_Last(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	public void removeByG_P(long groupId, boolean privateLayout);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout versions
	 */
	public int countByG_P(long groupId, boolean privateLayout);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_Version_First(
			long groupId, boolean privateLayout, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_Version_First(
		long groupId, boolean privateLayout, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_Version_Last(
			long groupId, boolean privateLayout, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_Version_Last(
		long groupId, boolean privateLayout, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 */
	public void removeByG_P_Version(
		long groupId, boolean privateLayout, int version);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByG_P_Version(
		long groupId, boolean privateLayout, int version);

	/**
	 * Returns all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_T(long groupId, String type);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_T(
		long groupId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_T(
		long groupId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_T(
		long groupId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_T_First(
			long groupId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_T_First(
		long groupId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_T_Last(
			long groupId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_T_Last(
		long groupId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_T_PrevAndNext(
			long layoutVersionId, long groupId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public void removeByG_T(long groupId, String type);

	/**
	 * Returns the number of layout versions where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout versions
	 */
	public int countByG_T(long groupId, String type);

	/**
	 * Returns all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_T_Version_First(
			long groupId, String type, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_T_Version_First(
		long groupId, String type, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_T_Version_Last(
			long groupId, String type, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_T_Version_Last(
		long groupId, String type, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_T_Version_PrevAndNext(
			long layoutVersionId, long groupId, String type, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and type = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 */
	public void removeByG_T_Version(long groupId, String type, int version);

	/**
	 * Returns the number of layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByG_T_Version(long groupId, String type, int version);

	/**
	 * Returns all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid);

	/**
	 * Returns a range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByC_L_First(
			long companyId, String layoutPrototypeUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByC_L_First(
		long companyId, String layoutPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByC_L_Last(
			long companyId, String layoutPrototypeUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByC_L_Last(
		long companyId, String layoutPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByC_L_PrevAndNext(
			long layoutVersionId, long companyId, String layoutPrototypeUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 */
	public void removeByC_L(long companyId, String layoutPrototypeUuid);

	/**
	 * Returns the number of layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the number of matching layout versions
	 */
	public int countByC_L(long companyId, String layoutPrototypeUuid);

	/**
	 * Returns all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version);

	/**
	 * Returns a range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version, int start,
		int end);

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByC_L_Version_First(
			long companyId, String layoutPrototypeUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByC_L_Version_First(
		long companyId, String layoutPrototypeUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByC_L_Version_Last(
			long companyId, String layoutPrototypeUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByC_L_Version_Last(
		long companyId, String layoutPrototypeUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByC_L_Version_PrevAndNext(
			long layoutVersionId, long companyId, String layoutPrototypeUuid,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 */
	public void removeByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version);

	/**
	 * Returns the number of layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version);

	/**
	 * Returns all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId);

	/**
	 * Returns a range of all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByP_I_First(
			boolean privateLayout, long iconImageId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByP_I_First(
		boolean privateLayout, long iconImageId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByP_I_Last(
			boolean privateLayout, long iconImageId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByP_I_Last(
		boolean privateLayout, long iconImageId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByP_I_PrevAndNext(
			long layoutVersionId, boolean privateLayout, long iconImageId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where privateLayout = &#63; and iconImageId = &#63; from the database.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 */
	public void removeByP_I(boolean privateLayout, long iconImageId);

	/**
	 * Returns the number of layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the number of matching layout versions
	 */
	public int countByP_I(boolean privateLayout, long iconImageId);

	/**
	 * Returns all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version);

	/**
	 * Returns a range of all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version, int start,
		int end);

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByP_I_Version_First(
			boolean privateLayout, long iconImageId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByP_I_Version_First(
		boolean privateLayout, long iconImageId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByP_I_Version_Last(
			boolean privateLayout, long iconImageId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByP_I_Version_Last(
		boolean privateLayout, long iconImageId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByP_I_Version_PrevAndNext(
			long layoutVersionId, boolean privateLayout, long iconImageId,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63; from the database.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 */
	public void removeByP_I_Version(
		boolean privateLayout, long iconImageId, int version);

	/**
	 * Returns the number of layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByP_I_Version(
		boolean privateLayout, long iconImageId, int version);

	/**
	 * Returns all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_C(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByC_C_PrevAndNext(
			long layoutVersionId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching layout versions
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version);

	/**
	 * Returns a range of all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByC_C_Version_First(
			long classNameId, long classPK, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByC_C_Version_First(
		long classNameId, long classPK, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByC_C_Version_Last(
			long classNameId, long classPK, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByC_C_Version_Last(
		long classNameId, long classPK, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByC_C_Version_PrevAndNext(
			long layoutVersionId, long classNameId, long classPK, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 */
	public void removeByC_C_Version(
		long classNameId, long classPK, int version);

	/**
	 * Returns the number of layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByC_C_Version(long classNameId, long classPK, int version);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_L_First(
			long groupId, boolean privateLayout, long layoutId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_L_First(
		long groupId, boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_L_Last(
			long groupId, boolean privateLayout, long layoutId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_L_Last(
		long groupId, boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_L_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long layoutId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 */
	public void removeByG_P_L(
		long groupId, boolean privateLayout, long layoutId);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout versions
	 */
	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId);

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_L_Version(
			long groupId, boolean privateLayout, long layoutId, int version)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_L_Version(
		long groupId, boolean privateLayout, long layoutId, int version);

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_L_Version(
		long groupId, boolean privateLayout, long layoutId, int version,
		boolean retrieveFromCache);

	/**
	 * Removes the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the layout version that was removed
	 */
	public LayoutVersion removeByG_P_L_Version(
			long groupId, boolean privateLayout, long layoutId, int version)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByG_P_L_Version(
		long groupId, boolean privateLayout, long layoutId, int version);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_P_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 */
	public void removeByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the number of matching layout versions
	 */
	public int countByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_Version_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_Version_First(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_Version_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_Version_Last(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_P_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 */
	public void removeByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_T_First(
			long groupId, boolean privateLayout, String type,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_T_First(
		long groupId, boolean privateLayout, String type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_T_Last(
			long groupId, boolean privateLayout, String type,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_T_Last(
		long groupId, boolean privateLayout, String type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_T_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String type,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 */
	public void removeByG_P_T(long groupId, boolean privateLayout, String type);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the number of matching layout versions
	 */
	public int countByG_P_T(long groupId, boolean privateLayout, String type);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version,
		int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_T_Version_First(
			long groupId, boolean privateLayout, String type, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_T_Version_First(
		long groupId, boolean privateLayout, String type, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_T_Version_Last(
			long groupId, boolean privateLayout, String type, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_T_Version_Last(
		long groupId, boolean privateLayout, String type, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_T_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String type, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 */
	public void removeByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL, int start,
		int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_F_First(
			long groupId, boolean privateLayout, String friendlyURL,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_F_First(
		long groupId, boolean privateLayout, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_F_Last(
			long groupId, boolean privateLayout, String friendlyURL,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_F_Last(
		long groupId, boolean privateLayout, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_F_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String friendlyURL,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 */
	public void removeByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @return the number of matching layout versions
	 */
	public int countByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL);

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_F_Version(
			long groupId, boolean privateLayout, String friendlyURL,
			int version)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_F_Version(
		long groupId, boolean privateLayout, String friendlyURL, int version);

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_F_Version(
		long groupId, boolean privateLayout, String friendlyURL, int version,
		boolean retrieveFromCache);

	/**
	 * Removes the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the layout version that was removed
	 */
	public LayoutVersion removeByG_P_F_Version(
			long groupId, boolean privateLayout, String friendlyURL,
			int version)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByG_P_F_Version(
		long groupId, boolean privateLayout, String friendlyURL, int version);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_SPLU_First(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_SPLU_First(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_SPLU_Last(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_SPLU_Last(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_SPLU_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 */
	public void removeByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the number of matching layout versions
	 */
	public int countByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_SPLU_Version_First(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_SPLU_Version_First(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_SPLU_Version_Last(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_SPLU_Version_Last(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_SPLU_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 */
	public void removeByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_H_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_H_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_H_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_H_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_P_H_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, boolean hidden,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 */
	public void removeByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @return the number of matching layout versions
	 */
	public int countByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_H_Version_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_H_Version_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_H_Version_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_H_Version_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_P_H_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, boolean hidden, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 */
	public void removeByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_LtP_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_LtP_First(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_LtP_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_LtP_Last(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_P_LtP_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, int priority,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 */
	public void removeByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the number of matching layout versions
	 */
	public int countByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority);

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @return the matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version);

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, int start, int end);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	public java.util.List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_LtP_Version_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_LtP_Version_First(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	public LayoutVersion findByG_P_P_LtP_Version_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	public LayoutVersion fetchByG_P_P_LtP_Version_Last(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion[] findByG_P_P_LtP_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, int priority, int version,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
				orderByComparator)
		throws NoSuchLayoutVersionException;

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 */
	public void removeByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version);

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	public int countByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version);

	/**
	 * Caches the layout version in the entity cache if it is enabled.
	 *
	 * @param layoutVersion the layout version
	 */
	public void cacheResult(LayoutVersion layoutVersion);

	/**
	 * Caches the layout versions in the entity cache if it is enabled.
	 *
	 * @param layoutVersions the layout versions
	 */
	public void cacheResult(java.util.List<LayoutVersion> layoutVersions);

	/**
	 * Creates a new layout version with the primary key. Does not add the layout version to the database.
	 *
	 * @param layoutVersionId the primary key for the new layout version
	 * @return the new layout version
	 */
	public LayoutVersion create(long layoutVersionId);

	/**
	 * Removes the layout version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutVersionId the primary key of the layout version
	 * @return the layout version that was removed
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion remove(long layoutVersionId)
		throws NoSuchLayoutVersionException;

	public LayoutVersion updateImpl(LayoutVersion layoutVersion);

	/**
	 * Returns the layout version with the primary key or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param layoutVersionId the primary key of the layout version
	 * @return the layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	public LayoutVersion findByPrimaryKey(long layoutVersionId)
		throws NoSuchLayoutVersionException;

	/**
	 * Returns the layout version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutVersionId the primary key of the layout version
	 * @return the layout version, or <code>null</code> if a layout version with the primary key could not be found
	 */
	public LayoutVersion fetchByPrimaryKey(long layoutVersionId);

	/**
	 * Returns all the layout versions.
	 *
	 * @return the layout versions
	 */
	public java.util.List<LayoutVersion> findAll();

	/**
	 * Returns a range of all the layout versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of layout versions
	 */
	public java.util.List<LayoutVersion> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the layout versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout versions
	 */
	public java.util.List<LayoutVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout versions
	 */
	public java.util.List<LayoutVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutVersion>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the layout versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout versions.
	 *
	 * @return the number of layout versions
	 */
	public int countAll();

}