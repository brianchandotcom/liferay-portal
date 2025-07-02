/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence;

import com.liferay.change.tracking.sample.exception.NoSuchChildException;
import com.liferay.change.tracking.sample.model.Child;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the child service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChildUtil
 * @generated
 */
@ProviderType
public interface ChildPersistence
	extends BasePersistence<Child>, CTPersistence<Child> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChildUtil} to access the child persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the childs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching childs
	 */
	public java.util.List<Child> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the childs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of matching childs
	 */
	public java.util.List<Child> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the childs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching childs
	 */
	public java.util.List<Child> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns an ordered range of all the childs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching childs
	 */
	public java.util.List<Child> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public Child findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Child>
				orderByComparator)
		throws NoSuchChildException;

	/**
	 * Returns the first child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child, or <code>null</code> if a matching child could not be found
	 */
	public Child fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns the last child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public Child findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Child>
				orderByComparator)
		throws NoSuchChildException;

	/**
	 * Returns the last child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child, or <code>null</code> if a matching child could not be found
	 */
	public Child fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns the childs before and after the current child in the ordered set where companyId = &#63;.
	 *
	 * @param childId the primary key of the current child
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public Child[] findByCompanyId_PrevAndNext(
			long childId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Child>
				orderByComparator)
		throws NoSuchChildException;

	/**
	 * Removes all the childs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of childs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching childs
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the matching childs
	 */
	public java.util.List<Child> findByC_G(long companyId, long grandParentId);

	/**
	 * Returns a range of all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of matching childs
	 */
	public java.util.List<Child> findByC_G(
		long companyId, long grandParentId, int start, int end);

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching childs
	 */
	public java.util.List<Child> findByC_G(
		long companyId, long grandParentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching childs
	 */
	public java.util.List<Child> findByC_G(
		long companyId, long grandParentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public Child findByC_G_First(
			long companyId, long grandParentId,
			com.liferay.portal.kernel.util.OrderByComparator<Child>
				orderByComparator)
		throws NoSuchChildException;

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child, or <code>null</code> if a matching child could not be found
	 */
	public Child fetchByC_G_First(
		long companyId, long grandParentId,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public Child findByC_G_Last(
			long companyId, long grandParentId,
			com.liferay.portal.kernel.util.OrderByComparator<Child>
				orderByComparator)
		throws NoSuchChildException;

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child, or <code>null</code> if a matching child could not be found
	 */
	public Child fetchByC_G_Last(
		long companyId, long grandParentId,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns the childs before and after the current child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param childId the primary key of the current child
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public Child[] findByC_G_PrevAndNext(
			long childId, long companyId, long grandParentId,
			com.liferay.portal.kernel.util.OrderByComparator<Child>
				orderByComparator)
		throws NoSuchChildException;

	/**
	 * Removes all the childs where companyId = &#63; and grandParentId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 */
	public void removeByC_G(long companyId, long grandParentId);

	/**
	 * Returns the number of childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the number of matching childs
	 */
	public int countByC_G(long companyId, long grandParentId);

	/**
	 * Returns all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @return the matching childs
	 */
	public java.util.List<Child> findByC_P(long companyId, long parentChildId);

	/**
	 * Returns a range of all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of matching childs
	 */
	public java.util.List<Child> findByC_P(
		long companyId, long parentChildId, int start, int end);

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching childs
	 */
	public java.util.List<Child> findByC_P(
		long companyId, long parentChildId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching childs
	 */
	public java.util.List<Child> findByC_P(
		long companyId, long parentChildId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public Child findByC_P_First(
			long companyId, long parentChildId,
			com.liferay.portal.kernel.util.OrderByComparator<Child>
				orderByComparator)
		throws NoSuchChildException;

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child, or <code>null</code> if a matching child could not be found
	 */
	public Child fetchByC_P_First(
		long companyId, long parentChildId,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public Child findByC_P_Last(
			long companyId, long parentChildId,
			com.liferay.portal.kernel.util.OrderByComparator<Child>
				orderByComparator)
		throws NoSuchChildException;

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child, or <code>null</code> if a matching child could not be found
	 */
	public Child fetchByC_P_Last(
		long companyId, long parentChildId,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns the childs before and after the current child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param childId the primary key of the current child
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public Child[] findByC_P_PrevAndNext(
			long childId, long companyId, long parentChildId,
			com.liferay.portal.kernel.util.OrderByComparator<Child>
				orderByComparator)
		throws NoSuchChildException;

	/**
	 * Removes all the childs where companyId = &#63; and parentChildId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 */
	public void removeByC_P(long companyId, long parentChildId);

	/**
	 * Returns the number of childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @return the number of matching childs
	 */
	public int countByC_P(long companyId, long parentChildId);

	/**
	 * Caches the child in the entity cache if it is enabled.
	 *
	 * @param child the child
	 */
	public void cacheResult(Child child);

	/**
	 * Caches the childs in the entity cache if it is enabled.
	 *
	 * @param childs the childs
	 */
	public void cacheResult(java.util.List<Child> childs);

	/**
	 * Creates a new child with the primary key. Does not add the child to the database.
	 *
	 * @param childId the primary key for the new child
	 * @return the new child
	 */
	public Child create(long childId);

	/**
	 * Removes the child with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param childId the primary key of the child
	 * @return the child that was removed
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public Child remove(long childId) throws NoSuchChildException;

	public Child updateImpl(Child child);

	/**
	 * Returns the child with the primary key or throws a <code>NoSuchChildException</code> if it could not be found.
	 *
	 * @param childId the primary key of the child
	 * @return the child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public Child findByPrimaryKey(long childId) throws NoSuchChildException;

	/**
	 * Returns the child with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param childId the primary key of the child
	 * @return the child, or <code>null</code> if a child with the primary key could not be found
	 */
	public Child fetchByPrimaryKey(long childId);

	/**
	 * Returns all the childs.
	 *
	 * @return the childs
	 */
	public java.util.List<Child> findAll();

	/**
	 * Returns a range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of childs
	 */
	public java.util.List<Child> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of childs
	 */
	public java.util.List<Child> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator);

	/**
	 * Returns an ordered range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of childs
	 */
	public java.util.List<Child> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Child>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the childs from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of childs.
	 *
	 * @return the number of childs
	 */
	public int countAll();

}