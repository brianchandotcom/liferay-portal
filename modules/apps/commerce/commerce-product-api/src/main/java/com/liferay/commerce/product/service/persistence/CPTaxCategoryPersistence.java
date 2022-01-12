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

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp tax category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPTaxCategoryUtil
 * @generated
 */
@ProviderType
public interface CPTaxCategoryPersistence
	extends BasePersistence<CPTaxCategory>, CTPersistence<CPTaxCategory> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPTaxCategoryUtil} to access the cp tax category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cp tax categories where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp tax categories
	 */
	public java.util.List<CPTaxCategory> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the cp tax categories where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPTaxCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @return the range of matching cp tax categories
	 */
	public java.util.List<CPTaxCategory> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cp tax categories where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPTaxCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp tax categories
	 */
	public java.util.List<CPTaxCategory> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTaxCategory>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp tax categories where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPTaxCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp tax categories
	 */
	public java.util.List<CPTaxCategory> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTaxCategory>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp tax category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp tax category
	 * @throws NoSuchCPTaxCategoryException if a matching cp tax category could not be found
	 */
	public CPTaxCategory findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPTaxCategory>
				orderByComparator)
		throws NoSuchCPTaxCategoryException;

	/**
	 * Returns the first cp tax category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp tax category, or <code>null</code> if a matching cp tax category could not be found
	 */
	public CPTaxCategory fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPTaxCategory>
			orderByComparator);

	/**
	 * Returns the last cp tax category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp tax category
	 * @throws NoSuchCPTaxCategoryException if a matching cp tax category could not be found
	 */
	public CPTaxCategory findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPTaxCategory>
				orderByComparator)
		throws NoSuchCPTaxCategoryException;

	/**
	 * Returns the last cp tax category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp tax category, or <code>null</code> if a matching cp tax category could not be found
	 */
	public CPTaxCategory fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPTaxCategory>
			orderByComparator);

	/**
	 * Returns the cp tax categories before and after the current cp tax category in the ordered set where companyId = &#63;.
	 *
	 * @param CPTaxCategoryId the primary key of the current cp tax category
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp tax category
	 * @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	 */
	public CPTaxCategory[] findByCompanyId_PrevAndNext(
			long CPTaxCategoryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPTaxCategory>
				orderByComparator)
		throws NoSuchCPTaxCategoryException;

	/**
	 * Removes all the cp tax categories where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of cp tax categories where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp tax categories
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the cp tax category where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCPTaxCategoryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp tax category
	 * @throws NoSuchCPTaxCategoryException if a matching cp tax category could not be found
	 */
	public CPTaxCategory findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCPTaxCategoryException;

	/**
	 * Returns the cp tax category where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp tax category, or <code>null</code> if a matching cp tax category could not be found
	 */
	public CPTaxCategory fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the cp tax category where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp tax category, or <code>null</code> if a matching cp tax category could not be found
	 */
	public CPTaxCategory fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the cp tax category where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the cp tax category that was removed
	 */
	public CPTaxCategory removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCPTaxCategoryException;

	/**
	 * Returns the number of cp tax categories where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching cp tax categories
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the cp tax category in the entity cache if it is enabled.
	 *
	 * @param cpTaxCategory the cp tax category
	 */
	public void cacheResult(CPTaxCategory cpTaxCategory);

	/**
	 * Caches the cp tax categories in the entity cache if it is enabled.
	 *
	 * @param cpTaxCategories the cp tax categories
	 */
	public void cacheResult(java.util.List<CPTaxCategory> cpTaxCategories);

	/**
	 * Creates a new cp tax category with the primary key. Does not add the cp tax category to the database.
	 *
	 * @param CPTaxCategoryId the primary key for the new cp tax category
	 * @return the new cp tax category
	 */
	public CPTaxCategory create(long CPTaxCategoryId);

	/**
	 * Removes the cp tax category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPTaxCategoryId the primary key of the cp tax category
	 * @return the cp tax category that was removed
	 * @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	 */
	public CPTaxCategory remove(long CPTaxCategoryId)
		throws NoSuchCPTaxCategoryException;

	public CPTaxCategory updateImpl(CPTaxCategory cpTaxCategory);

	/**
	 * Returns the cp tax category with the primary key or throws a <code>NoSuchCPTaxCategoryException</code> if it could not be found.
	 *
	 * @param CPTaxCategoryId the primary key of the cp tax category
	 * @return the cp tax category
	 * @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	 */
	public CPTaxCategory findByPrimaryKey(long CPTaxCategoryId)
		throws NoSuchCPTaxCategoryException;

	/**
	 * Returns the cp tax category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPTaxCategoryId the primary key of the cp tax category
	 * @return the cp tax category, or <code>null</code> if a cp tax category with the primary key could not be found
	 */
	public CPTaxCategory fetchByPrimaryKey(long CPTaxCategoryId);

	/**
	 * Returns all the cp tax categories.
	 *
	 * @return the cp tax categories
	 */
	public java.util.List<CPTaxCategory> findAll();

	/**
	 * Returns a range of all the cp tax categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPTaxCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @return the range of cp tax categories
	 */
	public java.util.List<CPTaxCategory> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cp tax categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPTaxCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp tax categories
	 */
	public java.util.List<CPTaxCategory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTaxCategory>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp tax categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPTaxCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp tax categories
	 */
	public java.util.List<CPTaxCategory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTaxCategory>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp tax categories from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp tax categories.
	 *
	 * @return the number of cp tax categories
	 */
	public int countAll();

}