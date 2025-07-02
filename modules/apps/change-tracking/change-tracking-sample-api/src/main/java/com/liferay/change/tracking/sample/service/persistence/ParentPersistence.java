/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence;

import com.liferay.change.tracking.sample.exception.NoSuchParentException;
import com.liferay.change.tracking.sample.model.Parent;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the parent service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ParentUtil
 * @generated
 */
@ProviderType
public interface ParentPersistence
	extends BasePersistence<Parent>, CTPersistence<Parent> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ParentUtil} to access the parent persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching parents
	 */
	public java.util.List<Parent> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of matching parents
	 */
	public java.util.List<Parent> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching parents
	 */
	public java.util.List<Parent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching parents
	 */
	public java.util.List<Parent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	public Parent findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Parent>
				orderByComparator)
		throws NoSuchParentException;

	/**
	 * Returns the first parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent, or <code>null</code> if a matching parent could not be found
	 */
	public Parent fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator);

	/**
	 * Returns the last parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	public Parent findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Parent>
				orderByComparator)
		throws NoSuchParentException;

	/**
	 * Returns the last parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent, or <code>null</code> if a matching parent could not be found
	 */
	public Parent fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator);

	/**
	 * Returns the parents before and after the current parent in the ordered set where companyId = &#63;.
	 *
	 * @param parentId the primary key of the current parent
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	public Parent[] findByCompanyId_PrevAndNext(
			long parentId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Parent>
				orderByComparator)
		throws NoSuchParentException;

	/**
	 * Removes all the parents where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching parents
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the matching parents
	 */
	public java.util.List<Parent> findByC_G(long companyId, long grandParentId);

	/**
	 * Returns a range of all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of matching parents
	 */
	public java.util.List<Parent> findByC_G(
		long companyId, long grandParentId, int start, int end);

	/**
	 * Returns an ordered range of all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching parents
	 */
	public java.util.List<Parent> findByC_G(
		long companyId, long grandParentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching parents
	 */
	public java.util.List<Parent> findByC_G(
		long companyId, long grandParentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	public Parent findByC_G_First(
			long companyId, long grandParentId,
			com.liferay.portal.kernel.util.OrderByComparator<Parent>
				orderByComparator)
		throws NoSuchParentException;

	/**
	 * Returns the first parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent, or <code>null</code> if a matching parent could not be found
	 */
	public Parent fetchByC_G_First(
		long companyId, long grandParentId,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator);

	/**
	 * Returns the last parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	public Parent findByC_G_Last(
			long companyId, long grandParentId,
			com.liferay.portal.kernel.util.OrderByComparator<Parent>
				orderByComparator)
		throws NoSuchParentException;

	/**
	 * Returns the last parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent, or <code>null</code> if a matching parent could not be found
	 */
	public Parent fetchByC_G_Last(
		long companyId, long grandParentId,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator);

	/**
	 * Returns the parents before and after the current parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param parentId the primary key of the current parent
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	public Parent[] findByC_G_PrevAndNext(
			long parentId, long companyId, long grandParentId,
			com.liferay.portal.kernel.util.OrderByComparator<Parent>
				orderByComparator)
		throws NoSuchParentException;

	/**
	 * Removes all the parents where companyId = &#63; and grandParentId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 */
	public void removeByC_G(long companyId, long grandParentId);

	/**
	 * Returns the number of parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the number of matching parents
	 */
	public int countByC_G(long companyId, long grandParentId);

	/**
	 * Caches the parent in the entity cache if it is enabled.
	 *
	 * @param parent the parent
	 */
	public void cacheResult(Parent parent);

	/**
	 * Caches the parents in the entity cache if it is enabled.
	 *
	 * @param parents the parents
	 */
	public void cacheResult(java.util.List<Parent> parents);

	/**
	 * Creates a new parent with the primary key. Does not add the parent to the database.
	 *
	 * @param parentId the primary key for the new parent
	 * @return the new parent
	 */
	public Parent create(long parentId);

	/**
	 * Removes the parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent that was removed
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	public Parent remove(long parentId) throws NoSuchParentException;

	public Parent updateImpl(Parent parent);

	/**
	 * Returns the parent with the primary key or throws a <code>NoSuchParentException</code> if it could not be found.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	public Parent findByPrimaryKey(long parentId) throws NoSuchParentException;

	/**
	 * Returns the parent with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent, or <code>null</code> if a parent with the primary key could not be found
	 */
	public Parent fetchByPrimaryKey(long parentId);

	/**
	 * Returns all the parents.
	 *
	 * @return the parents
	 */
	public java.util.List<Parent> findAll();

	/**
	 * Returns a range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of parents
	 */
	public java.util.List<Parent> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of parents
	 */
	public java.util.List<Parent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of parents
	 */
	public java.util.List<Parent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Parent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the parents from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of parents.
	 *
	 * @return the number of parents
	 */
	public int countAll();

}