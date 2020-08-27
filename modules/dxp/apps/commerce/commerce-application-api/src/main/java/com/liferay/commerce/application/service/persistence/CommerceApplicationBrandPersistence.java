/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.application.service.persistence;

import com.liferay.commerce.application.exception.NoSuchApplicationBrandException;
import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce application brand service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrandUtil
 * @generated
 */
@ProviderType
public interface CommerceApplicationBrandPersistence
	extends BasePersistence<CommerceApplicationBrand> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceApplicationBrandUtil} to access the commerce application brand persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce application brands where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce application brands
	 */
	public java.util.List<CommerceApplicationBrand> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce application brands where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of matching commerce application brands
	 */
	public java.util.List<CommerceApplicationBrand> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce application brands where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application brands
	 */
	public java.util.List<CommerceApplicationBrand> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationBrand> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce application brands where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application brands
	 */
	public java.util.List<CommerceApplicationBrand> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationBrand> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application brand
	 * @throws NoSuchApplicationBrandException if a matching commerce application brand could not be found
	 */
	public CommerceApplicationBrand findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationBrand> orderByComparator)
		throws NoSuchApplicationBrandException;

	/**
	 * Returns the first commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application brand, or <code>null</code> if a matching commerce application brand could not be found
	 */
	public CommerceApplicationBrand fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationBrand> orderByComparator);

	/**
	 * Returns the last commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application brand
	 * @throws NoSuchApplicationBrandException if a matching commerce application brand could not be found
	 */
	public CommerceApplicationBrand findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationBrand> orderByComparator)
		throws NoSuchApplicationBrandException;

	/**
	 * Returns the last commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application brand, or <code>null</code> if a matching commerce application brand could not be found
	 */
	public CommerceApplicationBrand fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationBrand> orderByComparator);

	/**
	 * Returns the commerce application brands before and after the current commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param commerceApplicationBrandId the primary key of the current commerce application brand
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	public CommerceApplicationBrand[] findByCompanyId_PrevAndNext(
			long commerceApplicationBrandId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationBrand> orderByComparator)
		throws NoSuchApplicationBrandException;

	/**
	 * Returns all the commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce application brands that the user has permission to view
	 */
	public java.util.List<CommerceApplicationBrand> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of matching commerce application brands that the user has permission to view
	 */
	public java.util.List<CommerceApplicationBrand> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce application brands that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application brands that the user has permission to view
	 */
	public java.util.List<CommerceApplicationBrand> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationBrand> orderByComparator);

	/**
	 * Returns the commerce application brands before and after the current commerce application brand in the ordered set of commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceApplicationBrandId the primary key of the current commerce application brand
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	public CommerceApplicationBrand[] filterFindByCompanyId_PrevAndNext(
			long commerceApplicationBrandId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationBrand> orderByComparator)
		throws NoSuchApplicationBrandException;

	/**
	 * Removes all the commerce application brands where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce application brands where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce application brands
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce application brands that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Caches the commerce application brand in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationBrand the commerce application brand
	 */
	public void cacheResult(CommerceApplicationBrand commerceApplicationBrand);

	/**
	 * Caches the commerce application brands in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationBrands the commerce application brands
	 */
	public void cacheResult(
		java.util.List<CommerceApplicationBrand> commerceApplicationBrands);

	/**
	 * Creates a new commerce application brand with the primary key. Does not add the commerce application brand to the database.
	 *
	 * @param commerceApplicationBrandId the primary key for the new commerce application brand
	 * @return the new commerce application brand
	 */
	public CommerceApplicationBrand create(long commerceApplicationBrandId);

	/**
	 * Removes the commerce application brand with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand that was removed
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	public CommerceApplicationBrand remove(long commerceApplicationBrandId)
		throws NoSuchApplicationBrandException;

	public CommerceApplicationBrand updateImpl(
		CommerceApplicationBrand commerceApplicationBrand);

	/**
	 * Returns the commerce application brand with the primary key or throws a <code>NoSuchApplicationBrandException</code> if it could not be found.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	public CommerceApplicationBrand findByPrimaryKey(
			long commerceApplicationBrandId)
		throws NoSuchApplicationBrandException;

	/**
	 * Returns the commerce application brand with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand, or <code>null</code> if a commerce application brand with the primary key could not be found
	 */
	public CommerceApplicationBrand fetchByPrimaryKey(
		long commerceApplicationBrandId);

	/**
	 * Returns all the commerce application brands.
	 *
	 * @return the commerce application brands
	 */
	public java.util.List<CommerceApplicationBrand> findAll();

	/**
	 * Returns a range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of commerce application brands
	 */
	public java.util.List<CommerceApplicationBrand> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce application brands
	 */
	public java.util.List<CommerceApplicationBrand> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationBrand> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce application brands
	 */
	public java.util.List<CommerceApplicationBrand> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationBrand> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce application brands from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce application brands.
	 *
	 * @return the number of commerce application brands
	 */
	public int countAll();

}