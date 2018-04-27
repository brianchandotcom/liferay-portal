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

import com.liferay.commerce.exception.NoSuchPaymentMethodException;
import com.liferay.commerce.model.CommercePaymentMethod;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce payment method service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommercePaymentMethodPersistenceImpl
 * @see CommercePaymentMethodUtil
 * @generated
 */
@ProviderType
public interface CommercePaymentMethodPersistence extends BasePersistence<CommercePaymentMethod> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePaymentMethodUtil} to access the commerce payment method persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce payment methods where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce payment methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @return the range of matching commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce payment methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator);

	/**
	* Returns an ordered range of all the commerce payment methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException;

	/**
	* Returns the first commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator);

	/**
	* Returns the last commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException;

	/**
	* Returns the last commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator);

	/**
	* Returns the commerce payment methods before and after the current commerce payment method in the ordered set where groupId = &#63;.
	*
	* @param commercePaymentMethodId the primary key of the current commerce payment method
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce payment method
	* @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	*/
	public CommercePaymentMethod[] findByGroupId_PrevAndNext(
		long commercePaymentMethodId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException;

	/**
	* Removes all the commerce payment methods where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce payment methods where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce payment methods
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the commerce payment method where groupId = &#63; and engineKey = &#63; or throws a {@link NoSuchPaymentMethodException} if it could not be found.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod findByG_E(long groupId, String engineKey)
		throws NoSuchPaymentMethodException;

	/**
	* Returns the commerce payment method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod fetchByG_E(long groupId, String engineKey);

	/**
	* Returns the commerce payment method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod fetchByG_E(long groupId, String engineKey,
		boolean retrieveFromCache);

	/**
	* Removes the commerce payment method where groupId = &#63; and engineKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the commerce payment method that was removed
	*/
	public CommercePaymentMethod removeByG_E(long groupId, String engineKey)
		throws NoSuchPaymentMethodException;

	/**
	* Returns the number of commerce payment methods where groupId = &#63; and engineKey = &#63;.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the number of matching commerce payment methods
	*/
	public int countByG_E(long groupId, String engineKey);

	/**
	* Returns all the commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findByG_A(long groupId,
		boolean active);

	/**
	* Returns a range of all the commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @return the range of matching commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findByG_A(long groupId,
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator);

	/**
	* Returns an ordered range of all the commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod findByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException;

	/**
	* Returns the first commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod fetchByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator);

	/**
	* Returns the last commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce payment method
	* @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod findByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException;

	/**
	* Returns the last commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	*/
	public CommercePaymentMethod fetchByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator);

	/**
	* Returns the commerce payment methods before and after the current commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param commercePaymentMethodId the primary key of the current commerce payment method
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce payment method
	* @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	*/
	public CommercePaymentMethod[] findByG_A_PrevAndNext(
		long commercePaymentMethodId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException;

	/**
	* Removes all the commerce payment methods where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public void removeByG_A(long groupId, boolean active);

	/**
	* Returns the number of commerce payment methods where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce payment methods
	*/
	public int countByG_A(long groupId, boolean active);

	/**
	* Caches the commerce payment method in the entity cache if it is enabled.
	*
	* @param commercePaymentMethod the commerce payment method
	*/
	public void cacheResult(CommercePaymentMethod commercePaymentMethod);

	/**
	* Caches the commerce payment methods in the entity cache if it is enabled.
	*
	* @param commercePaymentMethods the commerce payment methods
	*/
	public void cacheResult(
		java.util.List<CommercePaymentMethod> commercePaymentMethods);

	/**
	* Creates a new commerce payment method with the primary key. Does not add the commerce payment method to the database.
	*
	* @param commercePaymentMethodId the primary key for the new commerce payment method
	* @return the new commerce payment method
	*/
	public CommercePaymentMethod create(long commercePaymentMethodId);

	/**
	* Removes the commerce payment method with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method that was removed
	* @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	*/
	public CommercePaymentMethod remove(long commercePaymentMethodId)
		throws NoSuchPaymentMethodException;

	public CommercePaymentMethod updateImpl(
		CommercePaymentMethod commercePaymentMethod);

	/**
	* Returns the commerce payment method with the primary key or throws a {@link NoSuchPaymentMethodException} if it could not be found.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method
	* @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	*/
	public CommercePaymentMethod findByPrimaryKey(long commercePaymentMethodId)
		throws NoSuchPaymentMethodException;

	/**
	* Returns the commerce payment method with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method, or <code>null</code> if a commerce payment method with the primary key could not be found
	*/
	public CommercePaymentMethod fetchByPrimaryKey(long commercePaymentMethodId);

	@Override
	public java.util.Map<java.io.Serializable, CommercePaymentMethod> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce payment methods.
	*
	* @return the commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findAll();

	/**
	* Returns a range of all the commerce payment methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @return the range of commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce payment methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator);

	/**
	* Returns an ordered range of all the commerce payment methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce payment methods
	*/
	public java.util.List<CommercePaymentMethod> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentMethod> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce payment methods from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce payment methods.
	*
	* @return the number of commerce payment methods
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}