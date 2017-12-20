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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchAddressRestrictionException;
import com.liferay.commerce.model.CommerceAddressRestriction;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce address restriction service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceAddressRestrictionPersistenceImpl
 * @see CommerceAddressRestrictionUtil
 * @generated
 */
@ProviderType
public interface CommerceAddressRestrictionPersistence extends BasePersistence<CommerceAddressRestriction> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAddressRestrictionUtil} to access the commerce address restriction persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce address restrictions
	*/
	public java.util.List<CommerceAddressRestriction> findByC_C(
		long classNameId, long classPK);

	/**
	* Returns a range of all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @return the range of matching commerce address restrictions
	*/
	public java.util.List<CommerceAddressRestriction> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce address restrictions
	*/
	public java.util.List<CommerceAddressRestriction> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddressRestriction> orderByComparator);

	/**
	* Returns an ordered range of all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce address restrictions
	*/
	public java.util.List<CommerceAddressRestriction> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddressRestriction> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address restriction
	* @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	*/
	public CommerceAddressRestriction findByC_C_First(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws NoSuchAddressRestrictionException;

	/**
	* Returns the first commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public CommerceAddressRestriction fetchByC_C_First(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddressRestriction> orderByComparator);

	/**
	* Returns the last commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address restriction
	* @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	*/
	public CommerceAddressRestriction findByC_C_Last(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws NoSuchAddressRestrictionException;

	/**
	* Returns the last commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public CommerceAddressRestriction fetchByC_C_Last(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddressRestriction> orderByComparator);

	/**
	* Returns the commerce address restrictions before and after the current commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceAddressRestrictionId the primary key of the current commerce address restriction
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address restriction
	* @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	*/
	public CommerceAddressRestriction[] findByC_C_PrevAndNext(
		long commerceAddressRestrictionId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws NoSuchAddressRestrictionException;

	/**
	* Removes all the commerce address restrictions where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce address restrictions
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; or throws a {@link NoSuchAddressRestrictionException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce address restriction
	* @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	*/
	public CommerceAddressRestriction findByC_C_C(long classNameId,
		long classPK, long commerceCountryId)
		throws NoSuchAddressRestrictionException;

	/**
	* Returns the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public CommerceAddressRestriction fetchByC_C_C(long classNameId,
		long classPK, long commerceCountryId);

	/**
	* Returns the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public CommerceAddressRestriction fetchByC_C_C(long classNameId,
		long classPK, long commerceCountryId, boolean retrieveFromCache);

	/**
	* Removes the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @return the commerce address restriction that was removed
	*/
	public CommerceAddressRestriction removeByC_C_C(long classNameId,
		long classPK, long commerceCountryId)
		throws NoSuchAddressRestrictionException;

	/**
	* Returns the number of commerce address restrictions where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce address restrictions
	*/
	public int countByC_C_C(long classNameId, long classPK,
		long commerceCountryId);

	/**
	* Caches the commerce address restriction in the entity cache if it is enabled.
	*
	* @param commerceAddressRestriction the commerce address restriction
	*/
	public void cacheResult(
		CommerceAddressRestriction commerceAddressRestriction);

	/**
	* Caches the commerce address restrictions in the entity cache if it is enabled.
	*
	* @param commerceAddressRestrictions the commerce address restrictions
	*/
	public void cacheResult(
		java.util.List<CommerceAddressRestriction> commerceAddressRestrictions);

	/**
	* Creates a new commerce address restriction with the primary key. Does not add the commerce address restriction to the database.
	*
	* @param commerceAddressRestrictionId the primary key for the new commerce address restriction
	* @return the new commerce address restriction
	*/
	public CommerceAddressRestriction create(long commerceAddressRestrictionId);

	/**
	* Removes the commerce address restriction with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddressRestrictionId the primary key of the commerce address restriction
	* @return the commerce address restriction that was removed
	* @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	*/
	public CommerceAddressRestriction remove(long commerceAddressRestrictionId)
		throws NoSuchAddressRestrictionException;

	public CommerceAddressRestriction updateImpl(
		CommerceAddressRestriction commerceAddressRestriction);

	/**
	* Returns the commerce address restriction with the primary key or throws a {@link NoSuchAddressRestrictionException} if it could not be found.
	*
	* @param commerceAddressRestrictionId the primary key of the commerce address restriction
	* @return the commerce address restriction
	* @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	*/
	public CommerceAddressRestriction findByPrimaryKey(
		long commerceAddressRestrictionId)
		throws NoSuchAddressRestrictionException;

	/**
	* Returns the commerce address restriction with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceAddressRestrictionId the primary key of the commerce address restriction
	* @return the commerce address restriction, or <code>null</code> if a commerce address restriction with the primary key could not be found
	*/
	public CommerceAddressRestriction fetchByPrimaryKey(
		long commerceAddressRestrictionId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceAddressRestriction> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce address restrictions.
	*
	* @return the commerce address restrictions
	*/
	public java.util.List<CommerceAddressRestriction> findAll();

	/**
	* Returns a range of all the commerce address restrictions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @return the range of commerce address restrictions
	*/
	public java.util.List<CommerceAddressRestriction> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce address restrictions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce address restrictions
	*/
	public java.util.List<CommerceAddressRestriction> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddressRestriction> orderByComparator);

	/**
	* Returns an ordered range of all the commerce address restrictions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce address restrictions
	*/
	public java.util.List<CommerceAddressRestriction> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddressRestriction> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce address restrictions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce address restrictions.
	*
	* @return the number of commerce address restrictions
	*/
	public int countAll();
}