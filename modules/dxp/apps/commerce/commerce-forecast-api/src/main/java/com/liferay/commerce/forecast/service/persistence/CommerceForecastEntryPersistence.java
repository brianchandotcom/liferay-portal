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

package com.liferay.commerce.forecast.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.forecast.exception.NoSuchForecastEntryException;
import com.liferay.commerce.forecast.model.CommerceForecastEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce forecast entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.forecast.service.persistence.impl.CommerceForecastEntryPersistenceImpl
 * @see CommerceForecastEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceForecastEntryPersistence extends BasePersistence<CommerceForecastEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceForecastEntryUtil} to access the commerce forecast entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce forecast entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce forecast entries
	*/
	public java.util.List<CommerceForecastEntry> findByCompanyId(long companyId);

	/**
	* Returns a range of all the commerce forecast entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @return the range of matching commerce forecast entries
	*/
	public java.util.List<CommerceForecastEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce forecast entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce forecast entries
	*/
	public java.util.List<CommerceForecastEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce forecast entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce forecast entries
	*/
	public java.util.List<CommerceForecastEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce forecast entry
	* @throws NoSuchForecastEntryException if a matching commerce forecast entry could not be found
	*/
	public CommerceForecastEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastEntry> orderByComparator)
		throws NoSuchForecastEntryException;

	/**
	* Returns the first commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	*/
	public CommerceForecastEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastEntry> orderByComparator);

	/**
	* Returns the last commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce forecast entry
	* @throws NoSuchForecastEntryException if a matching commerce forecast entry could not be found
	*/
	public CommerceForecastEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastEntry> orderByComparator)
		throws NoSuchForecastEntryException;

	/**
	* Returns the last commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	*/
	public CommerceForecastEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastEntry> orderByComparator);

	/**
	* Returns the commerce forecast entries before and after the current commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param commerceForecastEntryId the primary key of the current commerce forecast entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce forecast entry
	* @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	*/
	public CommerceForecastEntry[] findByCompanyId_PrevAndNext(
		long commerceForecastEntryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastEntry> orderByComparator)
		throws NoSuchForecastEntryException;

	/**
	* Removes all the commerce forecast entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce forecast entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce forecast entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; or throws a {@link NoSuchForecastEntryException} if it could not be found.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @return the matching commerce forecast entry
	* @throws NoSuchForecastEntryException if a matching commerce forecast entry could not be found
	*/
	public CommerceForecastEntry findByC_P_T_C_S(long companyId, int period,
		int target, long customerId, String sku)
		throws NoSuchForecastEntryException;

	/**
	* Returns the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @return the matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	*/
	public CommerceForecastEntry fetchByC_P_T_C_S(long companyId, int period,
		int target, long customerId, String sku);

	/**
	* Returns the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	*/
	public CommerceForecastEntry fetchByC_P_T_C_S(long companyId, int period,
		int target, long customerId, String sku, boolean retrieveFromCache);

	/**
	* Removes the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; from the database.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @return the commerce forecast entry that was removed
	*/
	public CommerceForecastEntry removeByC_P_T_C_S(long companyId, int period,
		int target, long customerId, String sku)
		throws NoSuchForecastEntryException;

	/**
	* Returns the number of commerce forecast entries where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63;.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @return the number of matching commerce forecast entries
	*/
	public int countByC_P_T_C_S(long companyId, int period, int target,
		long customerId, String sku);

	/**
	* Caches the commerce forecast entry in the entity cache if it is enabled.
	*
	* @param commerceForecastEntry the commerce forecast entry
	*/
	public void cacheResult(CommerceForecastEntry commerceForecastEntry);

	/**
	* Caches the commerce forecast entries in the entity cache if it is enabled.
	*
	* @param commerceForecastEntries the commerce forecast entries
	*/
	public void cacheResult(
		java.util.List<CommerceForecastEntry> commerceForecastEntries);

	/**
	* Creates a new commerce forecast entry with the primary key. Does not add the commerce forecast entry to the database.
	*
	* @param commerceForecastEntryId the primary key for the new commerce forecast entry
	* @return the new commerce forecast entry
	*/
	public CommerceForecastEntry create(long commerceForecastEntryId);

	/**
	* Removes the commerce forecast entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry that was removed
	* @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	*/
	public CommerceForecastEntry remove(long commerceForecastEntryId)
		throws NoSuchForecastEntryException;

	public CommerceForecastEntry updateImpl(
		CommerceForecastEntry commerceForecastEntry);

	/**
	* Returns the commerce forecast entry with the primary key or throws a {@link NoSuchForecastEntryException} if it could not be found.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry
	* @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	*/
	public CommerceForecastEntry findByPrimaryKey(long commerceForecastEntryId)
		throws NoSuchForecastEntryException;

	/**
	* Returns the commerce forecast entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry, or <code>null</code> if a commerce forecast entry with the primary key could not be found
	*/
	public CommerceForecastEntry fetchByPrimaryKey(long commerceForecastEntryId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceForecastEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce forecast entries.
	*
	* @return the commerce forecast entries
	*/
	public java.util.List<CommerceForecastEntry> findAll();

	/**
	* Returns a range of all the commerce forecast entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @return the range of commerce forecast entries
	*/
	public java.util.List<CommerceForecastEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce forecast entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce forecast entries
	*/
	public java.util.List<CommerceForecastEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce forecast entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce forecast entries
	*/
	public java.util.List<CommerceForecastEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce forecast entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce forecast entries.
	*
	* @return the number of commerce forecast entries
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}