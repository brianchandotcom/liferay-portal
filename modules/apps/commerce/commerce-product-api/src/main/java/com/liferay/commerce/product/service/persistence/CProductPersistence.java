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

import com.liferay.commerce.product.exception.NoSuchCProductException;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the c product service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CProductUtil
 * @generated
 */
@ProviderType
public interface CProductPersistence
	extends BasePersistence<CProduct>, CTPersistence<CProduct> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CProductUtil} to access the c product persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the c products where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching c products
	 */
	public java.util.List<CProduct> findByUuid(String uuid);

	/**
	 * Returns a range of all the c products where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @return the range of matching c products
	 */
	public java.util.List<CProduct> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the c products where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c products
	 */
	public java.util.List<CProduct> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns an ordered range of all the c products where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching c products
	 */
	public java.util.List<CProduct> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first c product in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c product
	 * @throws NoSuchCProductException if a matching c product could not be found
	 */
	public CProduct findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CProduct>
				orderByComparator)
		throws NoSuchCProductException;

	/**
	 * Returns the first c product in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns the last c product in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c product
	 * @throws NoSuchCProductException if a matching c product could not be found
	 */
	public CProduct findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CProduct>
				orderByComparator)
		throws NoSuchCProductException;

	/**
	 * Returns the last c product in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns the c products before and after the current c product in the ordered set where uuid = &#63;.
	 *
	 * @param CProductId the primary key of the current c product
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c product
	 * @throws NoSuchCProductException if a c product with the primary key could not be found
	 */
	public CProduct[] findByUuid_PrevAndNext(
			long CProductId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CProduct>
				orderByComparator)
		throws NoSuchCProductException;

	/**
	 * Removes all the c products where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of c products where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching c products
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the c product where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCProductException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching c product
	 * @throws NoSuchCProductException if a matching c product could not be found
	 */
	public CProduct findByUUID_G(String uuid, long groupId)
		throws NoSuchCProductException;

	/**
	 * Returns the c product where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the c product where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the c product where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the c product that was removed
	 */
	public CProduct removeByUUID_G(String uuid, long groupId)
		throws NoSuchCProductException;

	/**
	 * Returns the number of c products where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching c products
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the c products where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching c products
	 */
	public java.util.List<CProduct> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the c products where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @return the range of matching c products
	 */
	public java.util.List<CProduct> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the c products where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c products
	 */
	public java.util.List<CProduct> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns an ordered range of all the c products where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching c products
	 */
	public java.util.List<CProduct> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first c product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c product
	 * @throws NoSuchCProductException if a matching c product could not be found
	 */
	public CProduct findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CProduct>
				orderByComparator)
		throws NoSuchCProductException;

	/**
	 * Returns the first c product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns the last c product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c product
	 * @throws NoSuchCProductException if a matching c product could not be found
	 */
	public CProduct findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CProduct>
				orderByComparator)
		throws NoSuchCProductException;

	/**
	 * Returns the last c product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns the c products before and after the current c product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CProductId the primary key of the current c product
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c product
	 * @throws NoSuchCProductException if a c product with the primary key could not be found
	 */
	public CProduct[] findByUuid_C_PrevAndNext(
			long CProductId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CProduct>
				orderByComparator)
		throws NoSuchCProductException;

	/**
	 * Removes all the c products where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of c products where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching c products
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the c products where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching c products
	 */
	public java.util.List<CProduct> findByGroupId(long groupId);

	/**
	 * Returns a range of all the c products where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @return the range of matching c products
	 */
	public java.util.List<CProduct> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the c products where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c products
	 */
	public java.util.List<CProduct> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns an ordered range of all the c products where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching c products
	 */
	public java.util.List<CProduct> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first c product in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c product
	 * @throws NoSuchCProductException if a matching c product could not be found
	 */
	public CProduct findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CProduct>
				orderByComparator)
		throws NoSuchCProductException;

	/**
	 * Returns the first c product in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns the last c product in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c product
	 * @throws NoSuchCProductException if a matching c product could not be found
	 */
	public CProduct findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CProduct>
				orderByComparator)
		throws NoSuchCProductException;

	/**
	 * Returns the last c product in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns the c products before and after the current c product in the ordered set where groupId = &#63;.
	 *
	 * @param CProductId the primary key of the current c product
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c product
	 * @throws NoSuchCProductException if a c product with the primary key could not be found
	 */
	public CProduct[] findByGroupId_PrevAndNext(
			long CProductId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CProduct>
				orderByComparator)
		throws NoSuchCProductException;

	/**
	 * Removes all the c products where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of c products where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching c products
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the c product where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCProductException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching c product
	 * @throws NoSuchCProductException if a matching c product could not be found
	 */
	public CProduct findByC_ERC(long companyId, String externalReferenceCode)
		throws NoSuchCProductException;

	/**
	 * Returns the c product where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Returns the c product where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public CProduct fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the c product where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the c product that was removed
	 */
	public CProduct removeByC_ERC(long companyId, String externalReferenceCode)
		throws NoSuchCProductException;

	/**
	 * Returns the number of c products where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching c products
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the c product in the entity cache if it is enabled.
	 *
	 * @param cProduct the c product
	 */
	public void cacheResult(CProduct cProduct);

	/**
	 * Caches the c products in the entity cache if it is enabled.
	 *
	 * @param cProducts the c products
	 */
	public void cacheResult(java.util.List<CProduct> cProducts);

	/**
	 * Creates a new c product with the primary key. Does not add the c product to the database.
	 *
	 * @param CProductId the primary key for the new c product
	 * @return the new c product
	 */
	public CProduct create(long CProductId);

	/**
	 * Removes the c product with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CProductId the primary key of the c product
	 * @return the c product that was removed
	 * @throws NoSuchCProductException if a c product with the primary key could not be found
	 */
	public CProduct remove(long CProductId) throws NoSuchCProductException;

	public CProduct updateImpl(CProduct cProduct);

	/**
	 * Returns the c product with the primary key or throws a <code>NoSuchCProductException</code> if it could not be found.
	 *
	 * @param CProductId the primary key of the c product
	 * @return the c product
	 * @throws NoSuchCProductException if a c product with the primary key could not be found
	 */
	public CProduct findByPrimaryKey(long CProductId)
		throws NoSuchCProductException;

	/**
	 * Returns the c product with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CProductId the primary key of the c product
	 * @return the c product, or <code>null</code> if a c product with the primary key could not be found
	 */
	public CProduct fetchByPrimaryKey(long CProductId);

	/**
	 * Returns all the c products.
	 *
	 * @return the c products
	 */
	public java.util.List<CProduct> findAll();

	/**
	 * Returns a range of all the c products.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @return the range of c products
	 */
	public java.util.List<CProduct> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the c products.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of c products
	 */
	public java.util.List<CProduct> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator);

	/**
	 * Returns an ordered range of all the c products.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CProductModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of c products
	 */
	public java.util.List<CProduct> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CProduct>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the c products from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of c products.
	 *
	 * @return the number of c products
	 */
	public int countAll();

}