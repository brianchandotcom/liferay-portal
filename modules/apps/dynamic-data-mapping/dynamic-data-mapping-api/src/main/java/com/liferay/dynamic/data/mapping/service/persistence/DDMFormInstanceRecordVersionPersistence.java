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

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceRecordVersionException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddm form instance record version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordVersionUtil
 * @generated
 */
@ProviderType
public interface DDMFormInstanceRecordVersionPersistence
	extends BasePersistence<DDMFormInstanceRecordVersion> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMFormInstanceRecordVersionUtil} to access the ddm form instance record version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion>
		findByFormInstanceRecordId(long formInstanceRecordId);

	/**
	 * Returns a range of all the ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion>
		findByFormInstanceRecordId(
			long formInstanceRecordId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion>
		findByFormInstanceRecordId(
			long formInstanceRecordId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion>
		findByFormInstanceRecordId(
			long formInstanceRecordId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion findByFormInstanceRecordId_First(
			long formInstanceRecordId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the first ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByFormInstanceRecordId_First(
		long formInstanceRecordId,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion findByFormInstanceRecordId_Last(
			long formInstanceRecordId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByFormInstanceRecordId_Last(
		long formInstanceRecordId,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns the ddm form instance record versions before and after the current ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordVersionId the primary key of the current ddm form instance record version
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	public DDMFormInstanceRecordVersion[]
			findByFormInstanceRecordId_PrevAndNext(
				long formInstanceRecordVersionId, long formInstanceRecordId,
				com.liferay.portal.kernel.util.OrderByComparator
					<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Removes all the ddm form instance record versions where formInstanceRecordId = &#63; from the database.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 */
	public void removeByFormInstanceRecordId(long formInstanceRecordId);

	/**
	 * Returns the number of ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the number of matching ddm form instance record versions
	 */
	public int countByFormInstanceRecordId(long formInstanceRecordId);

	/**
	 * Returns all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @return the matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByF_F(
		long formInstanceId, String formInstanceVersion);

	/**
	 * Returns a range of all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion findByF_F_First(
			long formInstanceId, String formInstanceVersion,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the first ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByF_F_First(
		long formInstanceId, String formInstanceVersion,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion findByF_F_Last(
			long formInstanceId, String formInstanceVersion,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByF_F_Last(
		long formInstanceId, String formInstanceVersion,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns the ddm form instance record versions before and after the current ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceRecordVersionId the primary key of the current ddm form instance record version
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	public DDMFormInstanceRecordVersion[] findByF_F_PrevAndNext(
			long formInstanceRecordVersionId, long formInstanceId,
			String formInstanceVersion,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Removes all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 */
	public void removeByF_F(long formInstanceId, String formInstanceVersion);

	/**
	 * Returns the number of ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @return the number of matching ddm form instance record versions
	 */
	public int countByF_F(long formInstanceId, String formInstanceVersion);

	/**
	 * Returns the ddm form instance record version where formInstanceRecordId = &#63; and version = &#63; or throws a <code>NoSuchFormInstanceRecordVersionException</code> if it could not be found.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @return the matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion findByF_V(
			long formInstanceRecordId, String version)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the ddm form instance record version where formInstanceRecordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @return the matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByF_V(
		long formInstanceRecordId, String version);

	/**
	 * Returns the ddm form instance record version where formInstanceRecordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByF_V(
		long formInstanceRecordId, String version, boolean useFinderCache);

	/**
	 * Removes the ddm form instance record version where formInstanceRecordId = &#63; and version = &#63; from the database.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @return the ddm form instance record version that was removed
	 */
	public DDMFormInstanceRecordVersion removeByF_V(
			long formInstanceRecordId, String version)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the number of ddm form instance record versions where formInstanceRecordId = &#63; and version = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @return the number of matching ddm form instance record versions
	 */
	public int countByF_V(long formInstanceRecordId, String version);

	/**
	 * Returns all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @return the matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByF_S(
		long formInstanceRecordId, int status);

	/**
	 * Returns a range of all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByF_S(
		long formInstanceRecordId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByF_S(
		long formInstanceRecordId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByF_S(
		long formInstanceRecordId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion findByF_S_First(
			long formInstanceRecordId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the first ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByF_S_First(
		long formInstanceRecordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion findByF_S_Last(
			long formInstanceRecordId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByF_S_Last(
		long formInstanceRecordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns the ddm form instance record versions before and after the current ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordVersionId the primary key of the current ddm form instance record version
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	public DDMFormInstanceRecordVersion[] findByF_S_PrevAndNext(
			long formInstanceRecordVersionId, long formInstanceRecordId,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Removes all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63; from the database.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 */
	public void removeByF_S(long formInstanceRecordId, int status);

	/**
	 * Returns the number of ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @return the number of matching ddm form instance record versions
	 */
	public int countByF_S(long formInstanceRecordId, int status);

	/**
	 * Returns all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @return the matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status);

	/**
	 * Returns a range of all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status, int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion findByU_F_F_S_First(
			long userId, long formInstanceId, String formInstanceVersion,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the first ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByU_F_F_S_First(
		long userId, long formInstanceId, String formInstanceVersion,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns the last ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion findByU_F_F_S_Last(
			long userId, long formInstanceId, String formInstanceVersion,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the last ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByU_F_F_S_Last(
		long userId, long formInstanceId, String formInstanceVersion,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns the ddm form instance record versions before and after the current ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordVersionId the primary key of the current ddm form instance record version
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	public DDMFormInstanceRecordVersion[] findByU_F_F_S_PrevAndNext(
			long formInstanceRecordVersionId, long userId, long formInstanceId,
			String formInstanceVersion, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Removes all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 */
	public void removeByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status);

	/**
	 * Returns the number of ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @return the number of matching ddm form instance record versions
	 */
	public int countByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status);

	/**
	 * Caches the ddm form instance record version in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecordVersion the ddm form instance record version
	 */
	public void cacheResult(
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion);

	/**
	 * Caches the ddm form instance record versions in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecordVersions the ddm form instance record versions
	 */
	public void cacheResult(
		java.util.List<DDMFormInstanceRecordVersion>
			ddmFormInstanceRecordVersions);

	/**
	 * Creates a new ddm form instance record version with the primary key. Does not add the ddm form instance record version to the database.
	 *
	 * @param formInstanceRecordVersionId the primary key for the new ddm form instance record version
	 * @return the new ddm form instance record version
	 */
	public DDMFormInstanceRecordVersion create(
		long formInstanceRecordVersionId);

	/**
	 * Removes the ddm form instance record version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceRecordVersionId the primary key of the ddm form instance record version
	 * @return the ddm form instance record version that was removed
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	public DDMFormInstanceRecordVersion remove(long formInstanceRecordVersionId)
		throws NoSuchFormInstanceRecordVersionException;

	public DDMFormInstanceRecordVersion updateImpl(
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion);

	/**
	 * Returns the ddm form instance record version with the primary key or throws a <code>NoSuchFormInstanceRecordVersionException</code> if it could not be found.
	 *
	 * @param formInstanceRecordVersionId the primary key of the ddm form instance record version
	 * @return the ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	public DDMFormInstanceRecordVersion findByPrimaryKey(
			long formInstanceRecordVersionId)
		throws NoSuchFormInstanceRecordVersionException;

	/**
	 * Returns the ddm form instance record version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formInstanceRecordVersionId the primary key of the ddm form instance record version
	 * @return the ddm form instance record version, or <code>null</code> if a ddm form instance record version with the primary key could not be found
	 */
	public DDMFormInstanceRecordVersion fetchByPrimaryKey(
		long formInstanceRecordVersionId);

	/**
	 * Returns all the ddm form instance record versions.
	 *
	 * @return the ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findAll();

	/**
	 * Returns a range of all the ddm form instance record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instance record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instance record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm form instance record versions
	 */
	public java.util.List<DDMFormInstanceRecordVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMFormInstanceRecordVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm form instance record versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddm form instance record versions.
	 *
	 * @return the number of ddm form instance record versions
	 */
	public int countAll();

}