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

import com.liferay.commerce.forecast.exception.NoSuchForecastValueException;
import com.liferay.commerce.forecast.model.CommerceForecastValue;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the commerce forecast value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.forecast.service.persistence.impl.CommerceForecastValuePersistenceImpl
 * @see CommerceForecastValueUtil
 * @generated
 */
@ProviderType
public interface CommerceForecastValuePersistence extends BasePersistence<CommerceForecastValue> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceForecastValueUtil} to access the commerce forecast value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @return the matching commerce forecast values
	*/
	public java.util.List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId);

	/**
	* Returns a range of all the commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @return the range of matching commerce forecast values
	*/
	public java.util.List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId, int start, int end);

	/**
	* Returns an ordered range of all the commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce forecast values
	*/
	public java.util.List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastValue> orderByComparator);

	/**
	* Returns an ordered range of all the commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce forecast values
	*/
	public java.util.List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce forecast value
	* @throws NoSuchForecastValueException if a matching commerce forecast value could not be found
	*/
	public CommerceForecastValue findByCommerceForecastEntryId_First(
		long commerceForecastEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastValue> orderByComparator)
		throws NoSuchForecastValueException;

	/**
	* Returns the first commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	*/
	public CommerceForecastValue fetchByCommerceForecastEntryId_First(
		long commerceForecastEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastValue> orderByComparator);

	/**
	* Returns the last commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce forecast value
	* @throws NoSuchForecastValueException if a matching commerce forecast value could not be found
	*/
	public CommerceForecastValue findByCommerceForecastEntryId_Last(
		long commerceForecastEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastValue> orderByComparator)
		throws NoSuchForecastValueException;

	/**
	* Returns the last commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	*/
	public CommerceForecastValue fetchByCommerceForecastEntryId_Last(
		long commerceForecastEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastValue> orderByComparator);

	/**
	* Returns the commerce forecast values before and after the current commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastValueId the primary key of the current commerce forecast value
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce forecast value
	* @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	*/
	public CommerceForecastValue[] findByCommerceForecastEntryId_PrevAndNext(
		long commerceForecastValueId, long commerceForecastEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastValue> orderByComparator)
		throws NoSuchForecastValueException;

	/**
	* Removes all the commerce forecast values where commerceForecastEntryId = &#63; from the database.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	*/
	public void removeByCommerceForecastEntryId(long commerceForecastEntryId);

	/**
	* Returns the number of commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @return the number of matching commerce forecast values
	*/
	public int countByCommerceForecastEntryId(long commerceForecastEntryId);

	/**
	* Returns the commerce forecast value where commerceForecastEntryId = &#63; and date = &#63; or throws a {@link NoSuchForecastValueException} if it could not be found.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param date the date
	* @return the matching commerce forecast value
	* @throws NoSuchForecastValueException if a matching commerce forecast value could not be found
	*/
	public CommerceForecastValue findByC_D(long commerceForecastEntryId,
		Date date) throws NoSuchForecastValueException;

	/**
	* Returns the commerce forecast value where commerceForecastEntryId = &#63; and date = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param date the date
	* @return the matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	*/
	public CommerceForecastValue fetchByC_D(long commerceForecastEntryId,
		Date date);

	/**
	* Returns the commerce forecast value where commerceForecastEntryId = &#63; and date = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param date the date
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	*/
	public CommerceForecastValue fetchByC_D(long commerceForecastEntryId,
		Date date, boolean retrieveFromCache);

	/**
	* Removes the commerce forecast value where commerceForecastEntryId = &#63; and date = &#63; from the database.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param date the date
	* @return the commerce forecast value that was removed
	*/
	public CommerceForecastValue removeByC_D(long commerceForecastEntryId,
		Date date) throws NoSuchForecastValueException;

	/**
	* Returns the number of commerce forecast values where commerceForecastEntryId = &#63; and date = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param date the date
	* @return the number of matching commerce forecast values
	*/
	public int countByC_D(long commerceForecastEntryId, Date date);

	/**
	* Caches the commerce forecast value in the entity cache if it is enabled.
	*
	* @param commerceForecastValue the commerce forecast value
	*/
	public void cacheResult(CommerceForecastValue commerceForecastValue);

	/**
	* Caches the commerce forecast values in the entity cache if it is enabled.
	*
	* @param commerceForecastValues the commerce forecast values
	*/
	public void cacheResult(
		java.util.List<CommerceForecastValue> commerceForecastValues);

	/**
	* Creates a new commerce forecast value with the primary key. Does not add the commerce forecast value to the database.
	*
	* @param commerceForecastValueId the primary key for the new commerce forecast value
	* @return the new commerce forecast value
	*/
	public CommerceForecastValue create(long commerceForecastValueId);

	/**
	* Removes the commerce forecast value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value that was removed
	* @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	*/
	public CommerceForecastValue remove(long commerceForecastValueId)
		throws NoSuchForecastValueException;

	public CommerceForecastValue updateImpl(
		CommerceForecastValue commerceForecastValue);

	/**
	* Returns the commerce forecast value with the primary key or throws a {@link NoSuchForecastValueException} if it could not be found.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value
	* @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	*/
	public CommerceForecastValue findByPrimaryKey(long commerceForecastValueId)
		throws NoSuchForecastValueException;

	/**
	* Returns the commerce forecast value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value, or <code>null</code> if a commerce forecast value with the primary key could not be found
	*/
	public CommerceForecastValue fetchByPrimaryKey(long commerceForecastValueId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceForecastValue> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce forecast values.
	*
	* @return the commerce forecast values
	*/
	public java.util.List<CommerceForecastValue> findAll();

	/**
	* Returns a range of all the commerce forecast values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @return the range of commerce forecast values
	*/
	public java.util.List<CommerceForecastValue> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce forecast values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce forecast values
	*/
	public java.util.List<CommerceForecastValue> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastValue> orderByComparator);

	/**
	* Returns an ordered range of all the commerce forecast values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce forecast values
	*/
	public java.util.List<CommerceForecastValue> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceForecastValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce forecast values from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce forecast values.
	*
	* @return the number of commerce forecast values
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}