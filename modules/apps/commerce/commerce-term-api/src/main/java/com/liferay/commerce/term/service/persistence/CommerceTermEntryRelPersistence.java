/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.service.persistence;

import com.liferay.commerce.term.exception.NoSuchTermEntryRelException;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce term entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryRelUtil
 * @generated
 */
@ProviderType
public interface CommerceTermEntryRelPersistence
	extends BasePersistence<CommerceTermEntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTermEntryRelUtil} to access the commerce term entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId);

	/**
	 * Returns a range of all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @return the range of matching commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel findByCommerceTermEntryId_First(
			long commerceTermEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException;

	/**
	 * Returns the first commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel fetchByCommerceTermEntryId_First(
		long commerceTermEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator);

	/**
	 * Returns the last commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel findByCommerceTermEntryId_Last(
			long commerceTermEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException;

	/**
	 * Returns the last commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel fetchByCommerceTermEntryId_Last(
		long commerceTermEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator);

	/**
	 * Returns the commerce term entry rels before and after the current commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryRelId the primary key of the current commerce term entry rel
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	public CommerceTermEntryRel[] findByCommerceTermEntryId_PrevAndNext(
			long commerceTermEntryRelId, long commerceTermEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException;

	/**
	 * Removes all the commerce term entry rels where commerceTermEntryId = &#63; from the database.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 */
	public void removeByCommerceTermEntryId(long commerceTermEntryId);

	/**
	 * Returns the number of commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching commerce term entry rels
	 */
	public int countByCommerceTermEntryId(long commerceTermEntryId);

	/**
	 * Returns all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId);

	/**
	 * Returns a range of all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @return the range of matching commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel findByC_C_First(
			long classNameId, long commerceTermEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException;

	/**
	 * Returns the first commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel fetchByC_C_First(
		long classNameId, long commerceTermEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator);

	/**
	 * Returns the last commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel findByC_C_Last(
			long classNameId, long commerceTermEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException;

	/**
	 * Returns the last commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel fetchByC_C_Last(
		long classNameId, long commerceTermEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator);

	/**
	 * Returns the commerce term entry rels before and after the current commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryRelId the primary key of the current commerce term entry rel
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	public CommerceTermEntryRel[] findByC_C_PrevAndNext(
			long commerceTermEntryRelId, long classNameId,
			long commerceTermEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException;

	/**
	 * Removes all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 */
	public void removeByC_C(long classNameId, long commerceTermEntryId);

	/**
	 * Returns the number of commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching commerce term entry rels
	 */
	public int countByC_C(long classNameId, long commerceTermEntryId);

	/**
	 * Returns the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; or throws a <code>NoSuchTermEntryRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel findByC_C_C(
			long classNameId, long classPK, long commerceTermEntryId)
		throws NoSuchTermEntryRelException;

	/**
	 * Returns the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceTermEntryId);

	/**
	 * Returns the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	public CommerceTermEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceTermEntryId,
		boolean useFinderCache);

	/**
	 * Removes the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the commerce term entry rel that was removed
	 */
	public CommerceTermEntryRel removeByC_C_C(
			long classNameId, long classPK, long commerceTermEntryId)
		throws NoSuchTermEntryRelException;

	/**
	 * Returns the number of commerce term entry rels where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching commerce term entry rels
	 */
	public int countByC_C_C(
		long classNameId, long classPK, long commerceTermEntryId);

	/**
	 * Caches the commerce term entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntryRel the commerce term entry rel
	 */
	public void cacheResult(CommerceTermEntryRel commerceTermEntryRel);

	/**
	 * Caches the commerce term entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntryRels the commerce term entry rels
	 */
	public void cacheResult(
		java.util.List<CommerceTermEntryRel> commerceTermEntryRels);

	/**
	 * Creates a new commerce term entry rel with the primary key. Does not add the commerce term entry rel to the database.
	 *
	 * @param commerceTermEntryRelId the primary key for the new commerce term entry rel
	 * @return the new commerce term entry rel
	 */
	public CommerceTermEntryRel create(long commerceTermEntryRelId);

	/**
	 * Removes the commerce term entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTermEntryRelId the primary key of the commerce term entry rel
	 * @return the commerce term entry rel that was removed
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	public CommerceTermEntryRel remove(long commerceTermEntryRelId)
		throws NoSuchTermEntryRelException;

	public CommerceTermEntryRel updateImpl(
		CommerceTermEntryRel commerceTermEntryRel);

	/**
	 * Returns the commerce term entry rel with the primary key or throws a <code>NoSuchTermEntryRelException</code> if it could not be found.
	 *
	 * @param commerceTermEntryRelId the primary key of the commerce term entry rel
	 * @return the commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	public CommerceTermEntryRel findByPrimaryKey(long commerceTermEntryRelId)
		throws NoSuchTermEntryRelException;

	/**
	 * Returns the commerce term entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTermEntryRelId the primary key of the commerce term entry rel
	 * @return the commerce term entry rel, or <code>null</code> if a commerce term entry rel with the primary key could not be found
	 */
	public CommerceTermEntryRel fetchByPrimaryKey(long commerceTermEntryRelId);

	/**
	 * Returns all the commerce term entry rels.
	 *
	 * @return the commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findAll();

	/**
	 * Returns a range of all the commerce term entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @return the range of commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce term entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce term entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce term entry rels
	 */
	public java.util.List<CommerceTermEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTermEntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce term entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce term entry rels.
	 *
	 * @return the number of commerce term entry rels
	 */
	public int countAll();

}