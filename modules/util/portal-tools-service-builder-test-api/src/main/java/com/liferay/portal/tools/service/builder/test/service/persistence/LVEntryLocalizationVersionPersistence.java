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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationVersionException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationVersion;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the lv entry localization version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationVersionUtil
 * @generated
 */
@ProviderType
public interface LVEntryLocalizationVersionPersistence
	extends BasePersistence<LVEntryLocalizationVersion> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LVEntryLocalizationVersionUtil} to access the lv entry localization version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @return the matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion>
		findByLvEntryLocalizationId(long lvEntryLocalizationId);

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion>
		findByLvEntryLocalizationId(
			long lvEntryLocalizationId, int start, int end);

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion>
		findByLvEntryLocalizationId(
			long lvEntryLocalizationId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion>
		findByLvEntryLocalizationId(
			long lvEntryLocalizationId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryLocalizationId_First(
			long lvEntryLocalizationId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_First(
		long lvEntryLocalizationId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryLocalizationId_Last(
			long lvEntryLocalizationId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Last(
		long lvEntryLocalizationId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public LVEntryLocalizationVersion[] findByLvEntryLocalizationId_PrevAndNext(
			long lvEntryLocalizationVersionId, long lvEntryLocalizationId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Removes all the lv entry localization versions where lvEntryLocalizationId = &#63; from the database.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 */
	public void removeByLvEntryLocalizationId(long lvEntryLocalizationId);

	/**
	 * Returns the number of lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @return the number of matching lv entry localization versions
	 */
	public int countByLvEntryLocalizationId(long lvEntryLocalizationId);

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryLocalizationVersionException</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryLocalizationId_Version(
			long lvEntryLocalizationId, int version)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version);

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version, boolean useFinderCache);

	/**
	 * Removes the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the lv entry localization version that was removed
	 */
	public LVEntryLocalizationVersion removeByLvEntryLocalizationId_Version(
			long lvEntryLocalizationId, int version)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the number of lv entry localization versions where lvEntryLocalizationId = &#63; and version = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	public int countByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version);

	/**
	 * Returns all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId);

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryId_First(
			long lvEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryId_First(
		long lvEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryId_Last(
			long lvEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryId_Last(
		long lvEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public LVEntryLocalizationVersion[] findByLvEntryId_PrevAndNext(
			long lvEntryLocalizationVersionId, long lvEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Removes all the lv entry localization versions where lvEntryId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 */
	public void removeByLvEntryId(long lvEntryId);

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the number of matching lv entry localization versions
	 */
	public int countByLvEntryId(long lvEntryId);

	/**
	 * Returns all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version);

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version, int start, int end);

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryId_Version_First(
			long lvEntryId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryId_Version_First(
		long lvEntryId, int version,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryId_Version_Last(
			long lvEntryId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryId_Version_Last(
		long lvEntryId, int version,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public LVEntryLocalizationVersion[] findByLvEntryId_Version_PrevAndNext(
			long lvEntryLocalizationVersionId, long lvEntryId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Removes all the lv entry localization versions where lvEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 */
	public void removeByLvEntryId_Version(long lvEntryId, int version);

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	public int countByLvEntryId_Version(long lvEntryId, int version);

	/**
	 * Returns all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion>
		findByLvEntryId_LanguageId(long lvEntryId, String languageId);

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion>
		findByLvEntryId_LanguageId(
			long lvEntryId, String languageId, int start, int end);

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion>
		findByLvEntryId_LanguageId(
			long lvEntryId, String languageId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion>
		findByLvEntryId_LanguageId(
			long lvEntryId, String languageId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryId_LanguageId_First(
			long lvEntryId, String languageId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_First(
		long lvEntryId, String languageId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryId_LanguageId_Last(
			long lvEntryId, String languageId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_Last(
		long lvEntryId, String languageId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public LVEntryLocalizationVersion[] findByLvEntryId_LanguageId_PrevAndNext(
			long lvEntryLocalizationVersionId, long lvEntryId,
			String languageId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Removes all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 */
	public void removeByLvEntryId_LanguageId(long lvEntryId, String languageId);

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the number of matching lv entry localization versions
	 */
	public int countByLvEntryId_LanguageId(long lvEntryId, String languageId);

	/**
	 * Returns the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryLocalizationVersionException</code> if it could not be found.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion findByLvEntryId_LanguageId_Version(
			long lvEntryId, String languageId, int version)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_Version(
		long lvEntryId, String languageId, int version);

	/**
	 * Returns the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_Version(
		long lvEntryId, String languageId, int version, boolean useFinderCache);

	/**
	 * Removes the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the lv entry localization version that was removed
	 */
	public LVEntryLocalizationVersion removeByLvEntryId_LanguageId_Version(
			long lvEntryId, String languageId, int version)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63; and languageId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	public int countByLvEntryId_LanguageId_Version(
		long lvEntryId, String languageId, int version);

	/**
	 * Caches the lv entry localization version in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizationVersion the lv entry localization version
	 */
	public void cacheResult(
		LVEntryLocalizationVersion lvEntryLocalizationVersion);

	/**
	 * Caches the lv entry localization versions in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizationVersions the lv entry localization versions
	 */
	public void cacheResult(
		java.util.List<LVEntryLocalizationVersion> lvEntryLocalizationVersions);

	/**
	 * Creates a new lv entry localization version with the primary key. Does not add the lv entry localization version to the database.
	 *
	 * @param lvEntryLocalizationVersionId the primary key for the new lv entry localization version
	 * @return the new lv entry localization version
	 */
	public LVEntryLocalizationVersion create(long lvEntryLocalizationVersionId);

	/**
	 * Removes the lv entry localization version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version that was removed
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public LVEntryLocalizationVersion remove(long lvEntryLocalizationVersionId)
		throws NoSuchLVEntryLocalizationVersionException;

	public LVEntryLocalizationVersion updateImpl(
		LVEntryLocalizationVersion lvEntryLocalizationVersion);

	/**
	 * Returns the lv entry localization version with the primary key or throws a <code>NoSuchLVEntryLocalizationVersionException</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public LVEntryLocalizationVersion findByPrimaryKey(
			long lvEntryLocalizationVersionId)
		throws NoSuchLVEntryLocalizationVersionException;

	/**
	 * Returns the lv entry localization version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version, or <code>null</code> if a lv entry localization version with the primary key could not be found
	 */
	public LVEntryLocalizationVersion fetchByPrimaryKey(
		long lvEntryLocalizationVersionId);

	/**
	 * Returns all the lv entry localization versions.
	 *
	 * @return the lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findAll();

	/**
	 * Returns a range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lv entry localization versions
	 */
	public java.util.List<LVEntryLocalizationVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the lv entry localization versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of lv entry localization versions.
	 *
	 * @return the number of lv entry localization versions
	 */
	public int countAll();

}