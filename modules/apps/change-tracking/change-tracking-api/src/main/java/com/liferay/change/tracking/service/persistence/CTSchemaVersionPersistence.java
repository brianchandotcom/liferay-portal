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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchSchemaVersionException;
import com.liferay.change.tracking.model.CTSchemaVersion;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct schema version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTSchemaVersionUtil
 * @generated
 */
@ProviderType
public interface CTSchemaVersionPersistence
	extends BasePersistence<CTSchemaVersion> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTSchemaVersionUtil} to access the ct schema version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct schema versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct schema versions
	 */
	public java.util.List<CTSchemaVersion> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the ct schema versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @return the range of matching ct schema versions
	 */
	public java.util.List<CTSchemaVersion> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ct schema versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct schema versions
	 */
	public java.util.List<CTSchemaVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSchemaVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct schema versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct schema versions
	 */
	public java.util.List<CTSchemaVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSchemaVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct schema version
	 * @throws NoSuchSchemaVersionException if a matching ct schema version could not be found
	 */
	public CTSchemaVersion findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTSchemaVersion>
				orderByComparator)
		throws NoSuchSchemaVersionException;

	/**
	 * Returns the first ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct schema version, or <code>null</code> if a matching ct schema version could not be found
	 */
	public CTSchemaVersion fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTSchemaVersion>
			orderByComparator);

	/**
	 * Returns the last ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct schema version
	 * @throws NoSuchSchemaVersionException if a matching ct schema version could not be found
	 */
	public CTSchemaVersion findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTSchemaVersion>
				orderByComparator)
		throws NoSuchSchemaVersionException;

	/**
	 * Returns the last ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct schema version, or <code>null</code> if a matching ct schema version could not be found
	 */
	public CTSchemaVersion fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTSchemaVersion>
			orderByComparator);

	/**
	 * Returns the ct schema versions before and after the current ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param schemaVersionId the primary key of the current ct schema version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct schema version
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	public CTSchemaVersion[] findByCompanyId_PrevAndNext(
			long schemaVersionId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTSchemaVersion>
				orderByComparator)
		throws NoSuchSchemaVersionException;

	/**
	 * Removes all the ct schema versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of ct schema versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct schema versions
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the ct schema version in the entity cache if it is enabled.
	 *
	 * @param ctSchemaVersion the ct schema version
	 */
	public void cacheResult(CTSchemaVersion ctSchemaVersion);

	/**
	 * Caches the ct schema versions in the entity cache if it is enabled.
	 *
	 * @param ctSchemaVersions the ct schema versions
	 */
	public void cacheResult(java.util.List<CTSchemaVersion> ctSchemaVersions);

	/**
	 * Creates a new ct schema version with the primary key. Does not add the ct schema version to the database.
	 *
	 * @param schemaVersionId the primary key for the new ct schema version
	 * @return the new ct schema version
	 */
	public CTSchemaVersion create(long schemaVersionId);

	/**
	 * Removes the ct schema version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version that was removed
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	public CTSchemaVersion remove(long schemaVersionId)
		throws NoSuchSchemaVersionException;

	public CTSchemaVersion updateImpl(CTSchemaVersion ctSchemaVersion);

	/**
	 * Returns the ct schema version with the primary key or throws a <code>NoSuchSchemaVersionException</code> if it could not be found.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	public CTSchemaVersion findByPrimaryKey(long schemaVersionId)
		throws NoSuchSchemaVersionException;

	/**
	 * Returns the ct schema version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version, or <code>null</code> if a ct schema version with the primary key could not be found
	 */
	public CTSchemaVersion fetchByPrimaryKey(long schemaVersionId);

	/**
	 * Returns all the ct schema versions.
	 *
	 * @return the ct schema versions
	 */
	public java.util.List<CTSchemaVersion> findAll();

	/**
	 * Returns a range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @return the range of ct schema versions
	 */
	public java.util.List<CTSchemaVersion> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct schema versions
	 */
	public java.util.List<CTSchemaVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSchemaVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct schema versions
	 */
	public java.util.List<CTSchemaVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSchemaVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct schema versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct schema versions.
	 *
	 * @return the number of ct schema versions
	 */
	public int countAll();

}