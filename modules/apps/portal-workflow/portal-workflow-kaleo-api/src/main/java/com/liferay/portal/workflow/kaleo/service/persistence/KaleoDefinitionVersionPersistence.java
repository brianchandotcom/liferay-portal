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

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionVersionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo definition version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinitionVersionUtil
 * @generated
 */
@ProviderType
public interface KaleoDefinitionVersionPersistence
	extends BasePersistence<KaleoDefinitionVersion> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoDefinitionVersionUtil} to access the kaleo definition version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the kaleo definition versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of matching kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException;

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator);

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException;

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator);

	/**
	 * Returns the kaleo definition versions before and after the current kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the current kaleo definition version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	public KaleoDefinitionVersion[] findByCompanyId_PrevAndNext(
			long kaleoDefinitionVersionId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException;

	/**
	 * Removes all the kaleo definition versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo definition versions
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name);

	/**
	 * Returns a range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of matching kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion findByC_N_First(
			long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException;

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion fetchByC_N_First(
		long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator);

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion findByC_N_Last(
			long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException;

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion fetchByC_N_Last(
		long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator);

	/**
	 * Returns the kaleo definition versions before and after the current kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the current kaleo definition version
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	public KaleoDefinitionVersion[] findByC_N_PrevAndNext(
			long kaleoDefinitionVersionId, long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException;

	/**
	 * Removes all the kaleo definition versions where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public void removeByC_N(long companyId, String name);

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching kaleo definition versions
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or throws a <code>NoSuchDefinitionVersionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion findByC_N_V(
			long companyId, String name, String version)
		throws NoSuchDefinitionVersionException;

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion fetchByC_N_V(
		long companyId, String name, String version);

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public KaleoDefinitionVersion fetchByC_N_V(
		long companyId, String name, String version, boolean useFinderCache);

	/**
	 * Removes the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the kaleo definition version that was removed
	 */
	public KaleoDefinitionVersion removeByC_N_V(
			long companyId, String name, String version)
		throws NoSuchDefinitionVersionException;

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching kaleo definition versions
	 */
	public int countByC_N_V(long companyId, String name, String version);

	/**
	 * Caches the kaleo definition version in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinitionVersion the kaleo definition version
	 */
	public void cacheResult(KaleoDefinitionVersion kaleoDefinitionVersion);

	/**
	 * Caches the kaleo definition versions in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinitionVersions the kaleo definition versions
	 */
	public void cacheResult(
		java.util.List<KaleoDefinitionVersion> kaleoDefinitionVersions);

	/**
	 * Creates a new kaleo definition version with the primary key. Does not add the kaleo definition version to the database.
	 *
	 * @param kaleoDefinitionVersionId the primary key for the new kaleo definition version
	 * @return the new kaleo definition version
	 */
	public KaleoDefinitionVersion create(long kaleoDefinitionVersionId);

	/**
	 * Removes the kaleo definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version that was removed
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	public KaleoDefinitionVersion remove(long kaleoDefinitionVersionId)
		throws NoSuchDefinitionVersionException;

	public KaleoDefinitionVersion updateImpl(
		KaleoDefinitionVersion kaleoDefinitionVersion);

	/**
	 * Returns the kaleo definition version with the primary key or throws a <code>NoSuchDefinitionVersionException</code> if it could not be found.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	public KaleoDefinitionVersion findByPrimaryKey(
			long kaleoDefinitionVersionId)
		throws NoSuchDefinitionVersionException;

	/**
	 * Returns the kaleo definition version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version, or <code>null</code> if a kaleo definition version with the primary key could not be found
	 */
	public KaleoDefinitionVersion fetchByPrimaryKey(
		long kaleoDefinitionVersionId);

	/**
	 * Returns all the kaleo definition versions.
	 *
	 * @return the kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findAll();

	/**
	 * Returns a range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo definition versions
	 */
	public java.util.List<KaleoDefinitionVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDefinitionVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the kaleo definition versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of kaleo definition versions.
	 *
	 * @return the number of kaleo definition versions
	 */
	public int countAll();

}