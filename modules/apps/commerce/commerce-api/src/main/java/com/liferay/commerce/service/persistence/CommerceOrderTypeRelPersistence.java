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

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.exception.NoSuchOrderTypeRelException;
import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce order type rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRelUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderTypeRelPersistence
	extends BasePersistence<CommerceOrderTypeRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderTypeRelUtil} to access the commerce order type rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId);

	/**
	 * Returns a range of all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of matching commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel findByCommerceOrderTypeId_First(
			long commerceOrderTypeId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException;

	/**
	 * Returns the first commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel fetchByCommerceOrderTypeId_First(
		long commerceOrderTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator);

	/**
	 * Returns the last commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel findByCommerceOrderTypeId_Last(
			long commerceOrderTypeId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException;

	/**
	 * Returns the last commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel fetchByCommerceOrderTypeId_Last(
		long commerceOrderTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator);

	/**
	 * Returns the commerce order type rels before and after the current commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeRelId the primary key of the current commerce order type rel
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	public CommerceOrderTypeRel[] findByCommerceOrderTypeId_PrevAndNext(
			long commerceOrderTypeRelId, long commerceOrderTypeId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException;

	/**
	 * Removes all the commerce order type rels where commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 */
	public void removeByCommerceOrderTypeId(long commerceOrderTypeId);

	/**
	 * Returns the number of commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce order type rels
	 */
	public int countByCommerceOrderTypeId(long commerceOrderTypeId);

	/**
	 * Returns all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId);

	/**
	 * Returns a range of all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of matching commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel findByC_C_First(
			long classNameId, long commerceOrderTypeId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException;

	/**
	 * Returns the first commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel fetchByC_C_First(
		long classNameId, long commerceOrderTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator);

	/**
	 * Returns the last commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel findByC_C_Last(
			long classNameId, long commerceOrderTypeId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException;

	/**
	 * Returns the last commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel fetchByC_C_Last(
		long classNameId, long commerceOrderTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator);

	/**
	 * Returns the commerce order type rels before and after the current commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeRelId the primary key of the current commerce order type rel
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	public CommerceOrderTypeRel[] findByC_C_PrevAndNext(
			long commerceOrderTypeRelId, long classNameId,
			long commerceOrderTypeId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException;

	/**
	 * Removes all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 */
	public void removeByC_C(long classNameId, long commerceOrderTypeId);

	/**
	 * Returns the number of commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce order type rels
	 */
	public int countByC_C(long classNameId, long commerceOrderTypeId);

	/**
	 * Returns the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; or throws a <code>NoSuchOrderTypeRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel findByC_C_C(
			long classNameId, long classPK, long commerceOrderTypeId)
		throws NoSuchOrderTypeRelException;

	/**
	 * Returns the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderTypeId);

	/**
	 * Returns the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderTypeId,
		boolean useFinderCache);

	/**
	 * Removes the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the commerce order type rel that was removed
	 */
	public CommerceOrderTypeRel removeByC_C_C(
			long classNameId, long classPK, long commerceOrderTypeId)
		throws NoSuchOrderTypeRelException;

	/**
	 * Returns the number of commerce order type rels where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce order type rels
	 */
	public int countByC_C_C(
		long classNameId, long classPK, long commerceOrderTypeId);

	/**
	 * Returns the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrderTypeRelException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderTypeRelException;

	/**
	 * Returns the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	public CommerceOrderTypeRel fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce order type rel that was removed
	 */
	public CommerceOrderTypeRel removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderTypeRelException;

	/**
	 * Returns the number of commerce order type rels where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce order type rels
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the commerce order type rel in the entity cache if it is enabled.
	 *
	 * @param commerceOrderTypeRel the commerce order type rel
	 */
	public void cacheResult(CommerceOrderTypeRel commerceOrderTypeRel);

	/**
	 * Caches the commerce order type rels in the entity cache if it is enabled.
	 *
	 * @param commerceOrderTypeRels the commerce order type rels
	 */
	public void cacheResult(
		java.util.List<CommerceOrderTypeRel> commerceOrderTypeRels);

	/**
	 * Creates a new commerce order type rel with the primary key. Does not add the commerce order type rel to the database.
	 *
	 * @param commerceOrderTypeRelId the primary key for the new commerce order type rel
	 * @return the new commerce order type rel
	 */
	public CommerceOrderTypeRel create(long commerceOrderTypeRelId);

	/**
	 * Removes the commerce order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel that was removed
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	public CommerceOrderTypeRel remove(long commerceOrderTypeRelId)
		throws NoSuchOrderTypeRelException;

	public CommerceOrderTypeRel updateImpl(
		CommerceOrderTypeRel commerceOrderTypeRel);

	/**
	 * Returns the commerce order type rel with the primary key or throws a <code>NoSuchOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	public CommerceOrderTypeRel findByPrimaryKey(long commerceOrderTypeRelId)
		throws NoSuchOrderTypeRelException;

	/**
	 * Returns the commerce order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel, or <code>null</code> if a commerce order type rel with the primary key could not be found
	 */
	public CommerceOrderTypeRel fetchByPrimaryKey(long commerceOrderTypeRelId);

	/**
	 * Returns all the commerce order type rels.
	 *
	 * @return the commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findAll();

	/**
	 * Returns a range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order type rels
	 */
	public java.util.List<CommerceOrderTypeRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderTypeRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce order type rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce order type rels.
	 *
	 * @return the number of commerce order type rels
	 */
	public int countAll();

}