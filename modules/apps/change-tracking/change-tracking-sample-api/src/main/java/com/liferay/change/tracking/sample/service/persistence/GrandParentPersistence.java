/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence;

import com.liferay.change.tracking.sample.exception.NoSuchGrandParentException;
import com.liferay.change.tracking.sample.model.GrandParent;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the grand parent service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GrandParentUtil
 * @generated
 */
@ProviderType
public interface GrandParentPersistence extends BasePersistence<GrandParent> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link GrandParentUtil} to access the grand parent persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the grand parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching grand parents
	 */
	public java.util.List<GrandParent> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the grand parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @return the range of matching grand parents
	 */
	public java.util.List<GrandParent> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the grand parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching grand parents
	 */
	public java.util.List<GrandParent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GrandParent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the grand parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching grand parents
	 */
	public java.util.List<GrandParent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GrandParent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching grand parent
	 * @throws NoSuchGrandParentException if a matching grand parent could not be found
	 */
	public GrandParent findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<GrandParent>
				orderByComparator)
		throws NoSuchGrandParentException;

	/**
	 * Returns the first grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching grand parent, or <code>null</code> if a matching grand parent could not be found
	 */
	public GrandParent fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<GrandParent>
			orderByComparator);

	/**
	 * Returns the last grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching grand parent
	 * @throws NoSuchGrandParentException if a matching grand parent could not be found
	 */
	public GrandParent findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<GrandParent>
				orderByComparator)
		throws NoSuchGrandParentException;

	/**
	 * Returns the last grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching grand parent, or <code>null</code> if a matching grand parent could not be found
	 */
	public GrandParent fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<GrandParent>
			orderByComparator);

	/**
	 * Returns the grand parents before and after the current grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param grandParentId the primary key of the current grand parent
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next grand parent
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	public GrandParent[] findByCompanyId_PrevAndNext(
			long grandParentId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<GrandParent>
				orderByComparator)
		throws NoSuchGrandParentException;

	/**
	 * Removes all the grand parents where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of grand parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching grand parents
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the grand parent in the entity cache if it is enabled.
	 *
	 * @param grandParent the grand parent
	 */
	public void cacheResult(GrandParent grandParent);

	/**
	 * Caches the grand parents in the entity cache if it is enabled.
	 *
	 * @param grandParents the grand parents
	 */
	public void cacheResult(java.util.List<GrandParent> grandParents);

	/**
	 * Creates a new grand parent with the primary key. Does not add the grand parent to the database.
	 *
	 * @param grandParentId the primary key for the new grand parent
	 * @return the new grand parent
	 */
	public GrandParent create(long grandParentId);

	/**
	 * Removes the grand parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent that was removed
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	public GrandParent remove(long grandParentId)
		throws NoSuchGrandParentException;

	public GrandParent updateImpl(GrandParent grandParent);

	/**
	 * Returns the grand parent with the primary key or throws a <code>NoSuchGrandParentException</code> if it could not be found.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	public GrandParent findByPrimaryKey(long grandParentId)
		throws NoSuchGrandParentException;

	/**
	 * Returns the grand parent with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent, or <code>null</code> if a grand parent with the primary key could not be found
	 */
	public GrandParent fetchByPrimaryKey(long grandParentId);

	/**
	 * Returns all the grand parents.
	 *
	 * @return the grand parents
	 */
	public java.util.List<GrandParent> findAll();

	/**
	 * Returns a range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @return the range of grand parents
	 */
	public java.util.List<GrandParent> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of grand parents
	 */
	public java.util.List<GrandParent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GrandParent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of grand parents
	 */
	public java.util.List<GrandParent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GrandParent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the grand parents from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of grand parents.
	 *
	 * @return the number of grand parents
	 */
	public int countAll();

}