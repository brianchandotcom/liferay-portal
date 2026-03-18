/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCPConfigurationListRelException;
import com.liferay.commerce.product.model.CPConfigurationListRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp configuration list rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPConfigurationListRelUtil
 * @generated
 */
@ProviderType
public interface CPConfigurationListRelPersistence
	extends BasePersistence<CPConfigurationListRel>,
			CTPersistence<CPConfigurationListRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPConfigurationListRelUtil} to access the cp configuration list rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId);

	/**
	 * Returns a range of all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @return the range of matching cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId, int start, int end);

	/**
	 * Returns an ordered range of all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel findByCPConfigurationListId_First(
			long CPConfigurationListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException;

	/**
	 * Returns the first cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel fetchByCPConfigurationListId_First(
		long CPConfigurationListId,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator);

	/**
	 * Returns the last cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel findByCPConfigurationListId_Last(
			long CPConfigurationListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException;

	/**
	 * Returns the last cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel fetchByCPConfigurationListId_Last(
		long CPConfigurationListId,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator);

	/**
	 * Returns the cp configuration list rels before and after the current cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListRelId the primary key of the current cp configuration list rel
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	public CPConfigurationListRel[] findByCPConfigurationListId_PrevAndNext(
			long CPConfigurationListRelId, long CPConfigurationListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException;

	/**
	 * Removes all the cp configuration list rels where CPConfigurationListId = &#63; from the database.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 */
	public void removeByCPConfigurationListId(long CPConfigurationListId);

	/**
	 * Returns the number of cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the number of matching cp configuration list rels
	 */
	public int countByCPConfigurationListId(long CPConfigurationListId);

	/**
	 * Returns all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId);

	/**
	 * Returns a range of all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @return the range of matching cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId, int start, int end);

	/**
	 * Returns an ordered range of all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel findByC_C_First(
			long classNameId, long CPConfigurationListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException;

	/**
	 * Returns the first cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel fetchByC_C_First(
		long classNameId, long CPConfigurationListId,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator);

	/**
	 * Returns the last cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel findByC_C_Last(
			long classNameId, long CPConfigurationListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException;

	/**
	 * Returns the last cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel fetchByC_C_Last(
		long classNameId, long CPConfigurationListId,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator);

	/**
	 * Returns the cp configuration list rels before and after the current cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListRelId the primary key of the current cp configuration list rel
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	public CPConfigurationListRel[] findByC_C_PrevAndNext(
			long CPConfigurationListRelId, long classNameId,
			long CPConfigurationListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException;

	/**
	 * Removes all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 */
	public void removeByC_C(long classNameId, long CPConfigurationListId);

	/**
	 * Returns the number of cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the number of matching cp configuration list rels
	 */
	public int countByC_C(long classNameId, long CPConfigurationListId);

	/**
	 * Returns the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; or throws a <code>NoSuchCPConfigurationListRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel findByC_C_C(
			long classNameId, long classPK, long CPConfigurationListId)
		throws NoSuchCPConfigurationListRelException;

	/**
	 * Returns the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel fetchByC_C_C(
		long classNameId, long classPK, long CPConfigurationListId);

	/**
	 * Returns the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	public CPConfigurationListRel fetchByC_C_C(
		long classNameId, long classPK, long CPConfigurationListId,
		boolean useFinderCache);

	/**
	 * Removes the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the cp configuration list rel that was removed
	 */
	public CPConfigurationListRel removeByC_C_C(
			long classNameId, long classPK, long CPConfigurationListId)
		throws NoSuchCPConfigurationListRelException;

	/**
	 * Returns the number of cp configuration list rels where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the number of matching cp configuration list rels
	 */
	public int countByC_C_C(
		long classNameId, long classPK, long CPConfigurationListId);

	/**
	 * Caches the cp configuration list rel in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationListRel the cp configuration list rel
	 */
	public void cacheResult(CPConfigurationListRel cpConfigurationListRel);

	/**
	 * Caches the cp configuration list rels in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationListRels the cp configuration list rels
	 */
	public void cacheResult(
		java.util.List<CPConfigurationListRel> cpConfigurationListRels);

	/**
	 * Creates a new cp configuration list rel with the primary key. Does not add the cp configuration list rel to the database.
	 *
	 * @param CPConfigurationListRelId the primary key for the new cp configuration list rel
	 * @return the new cp configuration list rel
	 */
	public CPConfigurationListRel create(long CPConfigurationListRelId);

	/**
	 * Removes the cp configuration list rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPConfigurationListRelId the primary key of the cp configuration list rel
	 * @return the cp configuration list rel that was removed
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	public CPConfigurationListRel remove(long CPConfigurationListRelId)
		throws NoSuchCPConfigurationListRelException;

	public CPConfigurationListRel updateImpl(
		CPConfigurationListRel cpConfigurationListRel);

	/**
	 * Returns the cp configuration list rel with the primary key or throws a <code>NoSuchCPConfigurationListRelException</code> if it could not be found.
	 *
	 * @param CPConfigurationListRelId the primary key of the cp configuration list rel
	 * @return the cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	public CPConfigurationListRel findByPrimaryKey(
			long CPConfigurationListRelId)
		throws NoSuchCPConfigurationListRelException;

	/**
	 * Returns the cp configuration list rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPConfigurationListRelId the primary key of the cp configuration list rel
	 * @return the cp configuration list rel, or <code>null</code> if a cp configuration list rel with the primary key could not be found
	 */
	public CPConfigurationListRel fetchByPrimaryKey(
		long CPConfigurationListRelId);

	/**
	 * Returns all the cp configuration list rels.
	 *
	 * @return the cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findAll();

	/**
	 * Returns a range of all the cp configuration list rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @return the range of cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cp configuration list rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp configuration list rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp configuration list rels
	 */
	public java.util.List<CPConfigurationListRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPConfigurationListRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp configuration list rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp configuration list rels.
	 *
	 * @return the number of cp configuration list rels
	 */
	public int countAll();

}
// LIFERAY-SERVICE-BUILDER-HASH:1497313906