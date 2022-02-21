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

package com.liferay.commerce.shipping.engine.fixed.service.persistence;

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce shipping fixed option service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionUtil
 * @generated
 */
@ProviderType
public interface CommerceShippingFixedOptionPersistence
	extends BasePersistence<CommerceShippingFixedOption> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShippingFixedOptionUtil} to access the commerce shipping fixed option persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the matching commerce shipping fixed options
	 */
	public java.util.List<CommerceShippingFixedOption>
		findByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	 * Returns a range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @return the range of matching commerce shipping fixed options
	 */
	public java.util.List<CommerceShippingFixedOption>
		findByCommerceShippingMethodId(
			long commerceShippingMethodId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed options
	 */
	public java.util.List<CommerceShippingFixedOption>
		findByCommerceShippingMethodId(
			long commerceShippingMethodId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOption> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed options
	 */
	public java.util.List<CommerceShippingFixedOption>
		findByCommerceShippingMethodId(
			long commerceShippingMethodId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOption> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a matching commerce shipping fixed option could not be found
	 */
	public CommerceShippingFixedOption findByCommerceShippingMethodId_First(
			long commerceShippingMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOption> orderByComparator)
		throws NoSuchShippingFixedOptionException;

	/**
	 * Returns the first commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	 */
	public CommerceShippingFixedOption fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOption> orderByComparator);

	/**
	 * Returns the last commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a matching commerce shipping fixed option could not be found
	 */
	public CommerceShippingFixedOption findByCommerceShippingMethodId_Last(
			long commerceShippingMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOption> orderByComparator)
		throws NoSuchShippingFixedOptionException;

	/**
	 * Returns the last commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	 */
	public CommerceShippingFixedOption fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOption> orderByComparator);

	/**
	 * Returns the commerce shipping fixed options before and after the current commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the primary key of the current commerce shipping fixed option
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	 */
	public CommerceShippingFixedOption[]
			findByCommerceShippingMethodId_PrevAndNext(
				long commerceShippingFixedOptionId,
				long commerceShippingMethodId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingFixedOption> orderByComparator)
		throws NoSuchShippingFixedOptionException;

	/**
	 * Removes all the commerce shipping fixed options where commerceShippingMethodId = &#63; from the database.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 */
	public void removeByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	 * Returns the number of commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the number of matching commerce shipping fixed options
	 */
	public int countByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	 * Returns the commerce shipping fixed option where companyId = &#63; and key = &#63; or throws a <code>NoSuchShippingFixedOptionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a matching commerce shipping fixed option could not be found
	 */
	public CommerceShippingFixedOption findByC_K(long companyId, String key)
		throws NoSuchShippingFixedOptionException;

	/**
	 * Returns the commerce shipping fixed option where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	 */
	public CommerceShippingFixedOption fetchByC_K(long companyId, String key);

	/**
	 * Returns the commerce shipping fixed option where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	 */
	public CommerceShippingFixedOption fetchByC_K(
		long companyId, String key, boolean useFinderCache);

	/**
	 * Removes the commerce shipping fixed option where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the commerce shipping fixed option that was removed
	 */
	public CommerceShippingFixedOption removeByC_K(long companyId, String key)
		throws NoSuchShippingFixedOptionException;

	/**
	 * Returns the number of commerce shipping fixed options where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching commerce shipping fixed options
	 */
	public int countByC_K(long companyId, String key);

	/**
	 * Caches the commerce shipping fixed option in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOption the commerce shipping fixed option
	 */
	public void cacheResult(
		CommerceShippingFixedOption commerceShippingFixedOption);

	/**
	 * Caches the commerce shipping fixed options in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptions the commerce shipping fixed options
	 */
	public void cacheResult(
		java.util.List<CommerceShippingFixedOption>
			commerceShippingFixedOptions);

	/**
	 * Creates a new commerce shipping fixed option with the primary key. Does not add the commerce shipping fixed option to the database.
	 *
	 * @param commerceShippingFixedOptionId the primary key for the new commerce shipping fixed option
	 * @return the new commerce shipping fixed option
	 */
	public CommerceShippingFixedOption create(
		long commerceShippingFixedOptionId);

	/**
	 * Removes the commerce shipping fixed option with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option that was removed
	 * @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	 */
	public CommerceShippingFixedOption remove(
			long commerceShippingFixedOptionId)
		throws NoSuchShippingFixedOptionException;

	public CommerceShippingFixedOption updateImpl(
		CommerceShippingFixedOption commerceShippingFixedOption);

	/**
	 * Returns the commerce shipping fixed option with the primary key or throws a <code>NoSuchShippingFixedOptionException</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	 */
	public CommerceShippingFixedOption findByPrimaryKey(
			long commerceShippingFixedOptionId)
		throws NoSuchShippingFixedOptionException;

	/**
	 * Returns the commerce shipping fixed option with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option, or <code>null</code> if a commerce shipping fixed option with the primary key could not be found
	 */
	public CommerceShippingFixedOption fetchByPrimaryKey(
		long commerceShippingFixedOptionId);

	/**
	 * Returns all the commerce shipping fixed options.
	 *
	 * @return the commerce shipping fixed options
	 */
	public java.util.List<CommerceShippingFixedOption> findAll();

	/**
	 * Returns a range of all the commerce shipping fixed options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @return the range of commerce shipping fixed options
	 */
	public java.util.List<CommerceShippingFixedOption> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping fixed options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping fixed options
	 */
	public java.util.List<CommerceShippingFixedOption> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOption> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping fixed options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping fixed options
	 */
	public java.util.List<CommerceShippingFixedOption> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOption> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce shipping fixed options from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce shipping fixed options.
	 *
	 * @return the number of commerce shipping fixed options
	 */
	public int countAll();

}