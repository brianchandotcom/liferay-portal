/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionListTypeDefinitionRelException;
import com.liferay.commerce.product.model.CPSpecificationOptionListTypeDefinitionRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp specification option list type definition rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPSpecificationOptionListTypeDefinitionRelUtil
 * @generated
 */
@ProviderType
public interface CPSpecificationOptionListTypeDefinitionRelPersistence
	extends BasePersistence<CPSpecificationOptionListTypeDefinitionRel>,
			CTPersistence<CPSpecificationOptionListTypeDefinitionRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPSpecificationOptionListTypeDefinitionRelUtil} to access the cp specification option list type definition rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the matching cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(long CPSpecificationOptionId);

	/**
	 * Returns a range of all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @return the range of matching cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end);

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSpecificationOptionListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSpecificationOptionListTypeDefinitionRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel
			findByCPSpecificationOptionId_First(
				long CPSpecificationOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPSpecificationOptionListTypeDefinitionRel>
						orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel
		fetchByCPSpecificationOptionId_First(
			long CPSpecificationOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSpecificationOptionListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel
			findByCPSpecificationOptionId_Last(
				long CPSpecificationOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPSpecificationOptionListTypeDefinitionRel>
						orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel
		fetchByCPSpecificationOptionId_Last(
			long CPSpecificationOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSpecificationOptionListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns the cp specification option list type definition rels before and after the current cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the current cp specification option list type definition rel
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel[]
			findByCPSpecificationOptionId_PrevAndNext(
				long CPSpecificationOptionListTypeDefinitionRelId,
				long CPSpecificationOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPSpecificationOptionListTypeDefinitionRel>
						orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	/**
	 * Removes all the cp specification option list type definition rels where CPSpecificationOptionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 */
	public void removeByCPSpecificationOptionId(long CPSpecificationOptionId);

	/**
	 * Returns the number of cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the number of matching cp specification option list type definition rels
	 */
	public int countByCPSpecificationOptionId(long CPSpecificationOptionId);

	/**
	 * Returns all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns a range of all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @return the range of matching cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(
			long listTypeDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(
			long listTypeDefinitionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSpecificationOptionListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(
			long listTypeDefinitionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSpecificationOptionListTypeDefinitionRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel
			findByListTypeDefinitionId_First(
				long listTypeDefinitionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPSpecificationOptionListTypeDefinitionRel>
						orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel
		fetchByListTypeDefinitionId_First(
			long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSpecificationOptionListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel
			findByListTypeDefinitionId_Last(
				long listTypeDefinitionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPSpecificationOptionListTypeDefinitionRel>
						orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel
		fetchByListTypeDefinitionId_Last(
			long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSpecificationOptionListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns the cp specification option list type definition rels before and after the current cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the current cp specification option list type definition rel
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel[]
			findByListTypeDefinitionId_PrevAndNext(
				long CPSpecificationOptionListTypeDefinitionRelId,
				long listTypeDefinitionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPSpecificationOptionListTypeDefinitionRel>
						orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	/**
	 * Removes all the cp specification option list type definition rels where listTypeDefinitionId = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 */
	public void removeByListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns the number of cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cp specification option list type definition rels
	 */
	public int countByListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or throws a <code>NoSuchCPSpecificationOptionListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel findByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	/**
	 * Returns the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId);

	/**
	 * Returns the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId,
		boolean useFinderCache);

	/**
	 * Removes the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the cp specification option list type definition rel that was removed
	 */
	public CPSpecificationOptionListTypeDefinitionRel removeByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	/**
	 * Returns the number of cp specification option list type definition rels where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cp specification option list type definition rels
	 */
	public int countByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId);

	/**
	 * Caches the cp specification option list type definition rel in the entity cache if it is enabled.
	 *
	 * @param cpSpecificationOptionListTypeDefinitionRel the cp specification option list type definition rel
	 */
	public void cacheResult(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel);

	/**
	 * Caches the cp specification option list type definition rels in the entity cache if it is enabled.
	 *
	 * @param cpSpecificationOptionListTypeDefinitionRels the cp specification option list type definition rels
	 */
	public void cacheResult(
		java.util.List<CPSpecificationOptionListTypeDefinitionRel>
			cpSpecificationOptionListTypeDefinitionRels);

	/**
	 * Creates a new cp specification option list type definition rel with the primary key. Does not add the cp specification option list type definition rel to the database.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key for the new cp specification option list type definition rel
	 * @return the new cp specification option list type definition rel
	 */
	public CPSpecificationOptionListTypeDefinitionRel create(
		long CPSpecificationOptionListTypeDefinitionRelId);

	/**
	 * Removes the cp specification option list type definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel that was removed
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel remove(
			long CPSpecificationOptionListTypeDefinitionRelId)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	public CPSpecificationOptionListTypeDefinitionRel updateImpl(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel);

	/**
	 * Returns the cp specification option list type definition rel with the primary key or throws a <code>NoSuchCPSpecificationOptionListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel findByPrimaryKey(
			long CPSpecificationOptionListTypeDefinitionRelId)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException;

	/**
	 * Returns the cp specification option list type definition rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel, or <code>null</code> if a cp specification option list type definition rel with the primary key could not be found
	 */
	public CPSpecificationOptionListTypeDefinitionRel fetchByPrimaryKey(
		long CPSpecificationOptionListTypeDefinitionRelId);

	/**
	 * Returns all the cp specification option list type definition rels.
	 *
	 * @return the cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel> findAll();

	/**
	 * Returns a range of all the cp specification option list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @return the range of cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSpecificationOptionListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp specification option list type definition rels
	 */
	public java.util.List<CPSpecificationOptionListTypeDefinitionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSpecificationOptionListTypeDefinitionRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp specification option list type definition rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp specification option list type definition rels.
	 *
	 * @return the number of cp specification option list type definition rels
	 */
	public int countAll();

}