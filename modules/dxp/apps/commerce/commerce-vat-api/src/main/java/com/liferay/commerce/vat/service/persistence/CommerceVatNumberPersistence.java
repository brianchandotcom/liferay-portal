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

package com.liferay.commerce.vat.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.vat.exception.NoSuchVatNumberException;
import com.liferay.commerce.vat.model.CommerceVatNumber;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce vat number service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.vat.service.persistence.impl.CommerceVatNumberPersistenceImpl
 * @see CommerceVatNumberUtil
 * @generated
 */
@ProviderType
public interface CommerceVatNumberPersistence extends BasePersistence<CommerceVatNumber> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceVatNumberUtil} to access the commerce vat number persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce vat numbers where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce vat numbers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @return the range of matching commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce vat numbers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator);

	/**
	* Returns an ordered range of all the commerce vat numbers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException;

	/**
	* Returns the first commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator);

	/**
	* Returns the last commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException;

	/**
	* Returns the last commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator);

	/**
	* Returns the commerce vat numbers before and after the current commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param commerceVatNumberId the primary key of the current commerce vat number
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce vat number
	* @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	*/
	public CommerceVatNumber[] findByGroupId_PrevAndNext(
		long commerceVatNumberId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException;

	/**
	* Removes all the commerce vat numbers where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce vat numbers where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce vat numbers
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findByC_C(long classNameId,
		long classPK);

	/**
	* Returns a range of all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @return the range of matching commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator);

	/**
	* Returns an ordered range of all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException;

	/**
	* Returns the first commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator);

	/**
	* Returns the last commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException;

	/**
	* Returns the last commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator);

	/**
	* Returns the commerce vat numbers before and after the current commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceVatNumberId the primary key of the current commerce vat number
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce vat number
	* @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	*/
	public CommerceVatNumber[] findByC_C_PrevAndNext(long commerceVatNumberId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException;

	/**
	* Removes all the commerce vat numbers where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce vat numbers
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchVatNumberException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber findByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchVatNumberException;

	/**
	* Returns the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber fetchByG_C_C(long groupId, long classNameId,
		long classPK);

	/**
	* Returns the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public CommerceVatNumber fetchByG_C_C(long groupId, long classNameId,
		long classPK, boolean retrieveFromCache);

	/**
	* Removes the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the commerce vat number that was removed
	*/
	public CommerceVatNumber removeByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchVatNumberException;

	/**
	* Returns the number of commerce vat numbers where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce vat numbers
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Caches the commerce vat number in the entity cache if it is enabled.
	*
	* @param commerceVatNumber the commerce vat number
	*/
	public void cacheResult(CommerceVatNumber commerceVatNumber);

	/**
	* Caches the commerce vat numbers in the entity cache if it is enabled.
	*
	* @param commerceVatNumbers the commerce vat numbers
	*/
	public void cacheResult(
		java.util.List<CommerceVatNumber> commerceVatNumbers);

	/**
	* Creates a new commerce vat number with the primary key. Does not add the commerce vat number to the database.
	*
	* @param commerceVatNumberId the primary key for the new commerce vat number
	* @return the new commerce vat number
	*/
	public CommerceVatNumber create(long commerceVatNumberId);

	/**
	* Removes the commerce vat number with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number that was removed
	* @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	*/
	public CommerceVatNumber remove(long commerceVatNumberId)
		throws NoSuchVatNumberException;

	public CommerceVatNumber updateImpl(CommerceVatNumber commerceVatNumber);

	/**
	* Returns the commerce vat number with the primary key or throws a {@link NoSuchVatNumberException} if it could not be found.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number
	* @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	*/
	public CommerceVatNumber findByPrimaryKey(long commerceVatNumberId)
		throws NoSuchVatNumberException;

	/**
	* Returns the commerce vat number with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number, or <code>null</code> if a commerce vat number with the primary key could not be found
	*/
	public CommerceVatNumber fetchByPrimaryKey(long commerceVatNumberId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceVatNumber> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce vat numbers.
	*
	* @return the commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findAll();

	/**
	* Returns a range of all the commerce vat numbers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @return the range of commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce vat numbers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator);

	/**
	* Returns an ordered range of all the commerce vat numbers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce vat numbers
	*/
	public java.util.List<CommerceVatNumber> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceVatNumber> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce vat numbers from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce vat numbers.
	*
	* @return the number of commerce vat numbers
	*/
	public int countAll();
}