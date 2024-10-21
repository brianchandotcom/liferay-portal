/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCPSOListTypeDefinitionRelException;
import com.liferay.commerce.product.model.CPSOListTypeDefinitionRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cpso list type definition rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPSOListTypeDefinitionRelUtil
 * @generated
 */
@ProviderType
public interface CPSOListTypeDefinitionRelPersistence
	extends BasePersistence<CPSOListTypeDefinitionRel>,
			CTPersistence<CPSOListTypeDefinitionRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPSOListTypeDefinitionRelUtil} to access the cpso list type definition rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the matching cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel>
		findByCPSpecificationOptionId(long CPSpecificationOptionId);

	/**
	 * Returns a range of all the cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @return the range of matching cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end);

	/**
	 * Returns an ordered range of all the cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSOListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns an ordered range of all the cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSOListTypeDefinitionRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel findByCPSpecificationOptionId_First(
			long CPSpecificationOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSOListTypeDefinitionRel> orderByComparator)
		throws NoSuchCPSOListTypeDefinitionRelException;

	/**
	 * Returns the first cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel fetchByCPSpecificationOptionId_First(
		long CPSpecificationOptionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSOListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns the last cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel findByCPSpecificationOptionId_Last(
			long CPSpecificationOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSOListTypeDefinitionRel> orderByComparator)
		throws NoSuchCPSOListTypeDefinitionRelException;

	/**
	 * Returns the last cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel fetchByCPSpecificationOptionId_Last(
		long CPSpecificationOptionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSOListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns the cpso list type definition rels before and after the current cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the current cpso list type definition rel
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a cpso list type definition rel with the primary key could not be found
	 */
	public CPSOListTypeDefinitionRel[]
			findByCPSpecificationOptionId_PrevAndNext(
				long CPSOListTypeDefinitionRelId, long CPSpecificationOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPSOListTypeDefinitionRel> orderByComparator)
		throws NoSuchCPSOListTypeDefinitionRelException;

	/**
	 * Removes all the cpso list type definition rels where CPSpecificationOptionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 */
	public void removeByCPSpecificationOptionId(long CPSpecificationOptionId);

	/**
	 * Returns the number of cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the number of matching cpso list type definition rels
	 */
	public int countByCPSpecificationOptionId(long CPSpecificationOptionId);

	/**
	 * Returns all the cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel> findByListTypeDefinitionId(
		long listTypeDefinitionId);

	/**
	 * Returns a range of all the cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @return the range of matching cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSOListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns an ordered range of all the cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSOListTypeDefinitionRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel findByListTypeDefinitionId_First(
			long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSOListTypeDefinitionRel> orderByComparator)
		throws NoSuchCPSOListTypeDefinitionRelException;

	/**
	 * Returns the first cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel fetchByListTypeDefinitionId_First(
		long listTypeDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSOListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns the last cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel findByListTypeDefinitionId_Last(
			long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSOListTypeDefinitionRel> orderByComparator)
		throws NoSuchCPSOListTypeDefinitionRelException;

	/**
	 * Returns the last cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel fetchByListTypeDefinitionId_Last(
		long listTypeDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSOListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns the cpso list type definition rels before and after the current cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the current cpso list type definition rel
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a cpso list type definition rel with the primary key could not be found
	 */
	public CPSOListTypeDefinitionRel[] findByListTypeDefinitionId_PrevAndNext(
			long CPSOListTypeDefinitionRelId, long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPSOListTypeDefinitionRel> orderByComparator)
		throws NoSuchCPSOListTypeDefinitionRelException;

	/**
	 * Removes all the cpso list type definition rels where listTypeDefinitionId = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 */
	public void removeByListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns the number of cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cpso list type definition rels
	 */
	public int countByListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns the cpso list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or throws a <code>NoSuchCPSOListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel findByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws NoSuchCPSOListTypeDefinitionRelException;

	/**
	 * Returns the cpso list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId);

	/**
	 * Returns the cpso list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public CPSOListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId,
		boolean useFinderCache);

	/**
	 * Removes the cpso list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the cpso list type definition rel that was removed
	 */
	public CPSOListTypeDefinitionRel removeByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws NoSuchCPSOListTypeDefinitionRelException;

	/**
	 * Returns the number of cpso list type definition rels where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cpso list type definition rels
	 */
	public int countByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId);

	/**
	 * Caches the cpso list type definition rel in the entity cache if it is enabled.
	 *
	 * @param cpsoListTypeDefinitionRel the cpso list type definition rel
	 */
	public void cacheResult(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel);

	/**
	 * Caches the cpso list type definition rels in the entity cache if it is enabled.
	 *
	 * @param cpsoListTypeDefinitionRels the cpso list type definition rels
	 */
	public void cacheResult(
		java.util.List<CPSOListTypeDefinitionRel> cpsoListTypeDefinitionRels);

	/**
	 * Creates a new cpso list type definition rel with the primary key. Does not add the cpso list type definition rel to the database.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key for the new cpso list type definition rel
	 * @return the new cpso list type definition rel
	 */
	public CPSOListTypeDefinitionRel create(long CPSOListTypeDefinitionRelId);

	/**
	 * Removes the cpso list type definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the cpso list type definition rel
	 * @return the cpso list type definition rel that was removed
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a cpso list type definition rel with the primary key could not be found
	 */
	public CPSOListTypeDefinitionRel remove(long CPSOListTypeDefinitionRelId)
		throws NoSuchCPSOListTypeDefinitionRelException;

	public CPSOListTypeDefinitionRel updateImpl(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel);

	/**
	 * Returns the cpso list type definition rel with the primary key or throws a <code>NoSuchCPSOListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the cpso list type definition rel
	 * @return the cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a cpso list type definition rel with the primary key could not be found
	 */
	public CPSOListTypeDefinitionRel findByPrimaryKey(
			long CPSOListTypeDefinitionRelId)
		throws NoSuchCPSOListTypeDefinitionRelException;

	/**
	 * Returns the cpso list type definition rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the cpso list type definition rel
	 * @return the cpso list type definition rel, or <code>null</code> if a cpso list type definition rel with the primary key could not be found
	 */
	public CPSOListTypeDefinitionRel fetchByPrimaryKey(
		long CPSOListTypeDefinitionRelId);

	/**
	 * Returns all the cpso list type definition rels.
	 *
	 * @return the cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel> findAll();

	/**
	 * Returns a range of all the cpso list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @return the range of cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the cpso list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSOListTypeDefinitionRel> orderByComparator);

	/**
	 * Returns an ordered range of all the cpso list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cpso list type definition rels
	 */
	public java.util.List<CPSOListTypeDefinitionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPSOListTypeDefinitionRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cpso list type definition rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cpso list type definition rels.
	 *
	 * @return the number of cpso list type definition rels
	 */
	public int countAll();

}