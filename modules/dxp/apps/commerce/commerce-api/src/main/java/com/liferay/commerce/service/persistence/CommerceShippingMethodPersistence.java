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

import com.liferay.commerce.exception.NoSuchShippingMethodException;
import com.liferay.commerce.model.CommerceShippingMethod;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce shipping method service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceShippingMethodPersistenceImpl
 * @see CommerceShippingMethodUtil
 * @generated
 */
@ProviderType
public interface CommerceShippingMethodPersistence extends BasePersistence<CommerceShippingMethod> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShippingMethodUtil} to access the commerce shipping method persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce shipping methods where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce shipping methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @return the range of matching commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce shipping methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipping methods where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce shipping method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping method
	* @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException;

	/**
	* Returns the first commerce shipping method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator);

	/**
	* Returns the last commerce shipping method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping method
	* @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException;

	/**
	* Returns the last commerce shipping method in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator);

	/**
	* Returns the commerce shipping methods before and after the current commerce shipping method in the ordered set where groupId = &#63;.
	*
	* @param commerceShippingMethodId the primary key of the current commerce shipping method
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipping method
	* @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	*/
	public CommerceShippingMethod[] findByGroupId_PrevAndNext(
		long commerceShippingMethodId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException;

	/**
	* Removes all the commerce shipping methods where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce shipping methods where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce shipping methods
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the commerce shipping method where groupId = &#63; and engineKey = &#63; or throws a {@link NoSuchShippingMethodException} if it could not be found.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the matching commerce shipping method
	* @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod findByG_E(long groupId, String engineKey)
		throws NoSuchShippingMethodException;

	/**
	* Returns the commerce shipping method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod fetchByG_E(long groupId, String engineKey);

	/**
	* Returns the commerce shipping method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod fetchByG_E(long groupId, String engineKey,
		boolean retrieveFromCache);

	/**
	* Removes the commerce shipping method where groupId = &#63; and engineKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the commerce shipping method that was removed
	*/
	public CommerceShippingMethod removeByG_E(long groupId, String engineKey)
		throws NoSuchShippingMethodException;

	/**
	* Returns the number of commerce shipping methods where groupId = &#63; and engineKey = &#63;.
	*
	* @param groupId the group ID
	* @param engineKey the engine key
	* @return the number of matching commerce shipping methods
	*/
	public int countByG_E(long groupId, String engineKey);

	/**
	* Returns all the commerce shipping methods where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findByG_A(long groupId,
		boolean active);

	/**
	* Returns a range of all the commerce shipping methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @return the range of matching commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findByG_A(long groupId,
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the commerce shipping methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipping methods where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping method
	* @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod findByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException;

	/**
	* Returns the first commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod fetchByG_A_First(long groupId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator);

	/**
	* Returns the last commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping method
	* @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod findByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException;

	/**
	* Returns the last commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	*/
	public CommerceShippingMethod fetchByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator);

	/**
	* Returns the commerce shipping methods before and after the current commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param commerceShippingMethodId the primary key of the current commerce shipping method
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipping method
	* @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	*/
	public CommerceShippingMethod[] findByG_A_PrevAndNext(
		long commerceShippingMethodId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException;

	/**
	* Removes all the commerce shipping methods where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public void removeByG_A(long groupId, boolean active);

	/**
	* Returns the number of commerce shipping methods where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce shipping methods
	*/
	public int countByG_A(long groupId, boolean active);

	/**
	* Caches the commerce shipping method in the entity cache if it is enabled.
	*
	* @param commerceShippingMethod the commerce shipping method
	*/
	public void cacheResult(CommerceShippingMethod commerceShippingMethod);

	/**
	* Caches the commerce shipping methods in the entity cache if it is enabled.
	*
	* @param commerceShippingMethods the commerce shipping methods
	*/
	public void cacheResult(
		java.util.List<CommerceShippingMethod> commerceShippingMethods);

	/**
	* Creates a new commerce shipping method with the primary key. Does not add the commerce shipping method to the database.
	*
	* @param commerceShippingMethodId the primary key for the new commerce shipping method
	* @return the new commerce shipping method
	*/
	public CommerceShippingMethod create(long commerceShippingMethodId);

	/**
	* Removes the commerce shipping method with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingMethodId the primary key of the commerce shipping method
	* @return the commerce shipping method that was removed
	* @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	*/
	public CommerceShippingMethod remove(long commerceShippingMethodId)
		throws NoSuchShippingMethodException;

	public CommerceShippingMethod updateImpl(
		CommerceShippingMethod commerceShippingMethod);

	/**
	* Returns the commerce shipping method with the primary key or throws a {@link NoSuchShippingMethodException} if it could not be found.
	*
	* @param commerceShippingMethodId the primary key of the commerce shipping method
	* @return the commerce shipping method
	* @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	*/
	public CommerceShippingMethod findByPrimaryKey(
		long commerceShippingMethodId) throws NoSuchShippingMethodException;

	/**
	* Returns the commerce shipping method with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceShippingMethodId the primary key of the commerce shipping method
	* @return the commerce shipping method, or <code>null</code> if a commerce shipping method with the primary key could not be found
	*/
	public CommerceShippingMethod fetchByPrimaryKey(
		long commerceShippingMethodId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceShippingMethod> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce shipping methods.
	*
	* @return the commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findAll();

	/**
	* Returns a range of all the commerce shipping methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @return the range of commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce shipping methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipping methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce shipping methods
	*/
	public java.util.List<CommerceShippingMethod> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShippingMethod> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce shipping methods from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce shipping methods.
	*
	* @return the number of commerce shipping methods
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}