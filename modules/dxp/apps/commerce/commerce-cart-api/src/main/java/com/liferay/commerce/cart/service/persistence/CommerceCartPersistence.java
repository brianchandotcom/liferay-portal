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

package com.liferay.commerce.cart.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.exception.NoSuchCartException;
import com.liferay.commerce.cart.model.CommerceCart;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce cart service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.cart.service.persistence.impl.CommerceCartPersistenceImpl
 * @see CommerceCartUtil
 * @generated
 */
@ProviderType
public interface CommerceCartPersistence extends BasePersistence<CommerceCart> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCartUtil} to access the commerce cart persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce carts where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_T(long groupId, int type);

	/**
	* Returns a range of all the commerce carts where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_T(long groupId, int type,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_T(long groupId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_T(long groupId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_T_First(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_T_First(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param CommerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByG_T_PrevAndNext(long CommerceCartId,
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public void removeByG_T(long groupId, int type);

	/**
	* Returns the number of commerce carts where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching commerce carts
	*/
	public int countByG_T(long groupId, int type);

	/**
	* Returns all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_T(long groupId, long userId,
		int type);

	/**
	* Returns a range of all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_T(long groupId, long userId,
		int type, int start, int end);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_T(long groupId, long userId,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_T(long groupId, long userId,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_U_T_First(long groupId, long userId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_U_T_First(long groupId, long userId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_U_T_Last(long groupId, long userId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_U_T_Last(long groupId, long userId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param CommerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByG_U_T_PrevAndNext(long CommerceCartId,
		long groupId, long userId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	*/
	public void removeByG_U_T(long groupId, long userId, int type);

	/**
	* Returns the number of commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @return the number of matching commerce carts
	*/
	public int countByG_U_T(long groupId, long userId, int type);

	/**
	* Returns all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_N_T(long groupId,
		long userId, java.lang.String name, int type);

	/**
	* Returns a range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_N_T(long groupId,
		long userId, java.lang.String name, int type, int start, int end);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_N_T(long groupId,
		long userId, java.lang.String name, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_N_T(long groupId,
		long userId, java.lang.String name, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_U_N_T_First(long groupId, long userId,
		java.lang.String name, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_U_N_T_First(long groupId, long userId,
		java.lang.String name, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_U_N_T_Last(long groupId, long userId,
		java.lang.String name, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_U_N_T_Last(long groupId, long userId,
		java.lang.String name, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* @param CommerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByG_U_N_T_PrevAndNext(long CommerceCartId,
		long groupId, long userId, java.lang.String name, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	*/
	public void removeByG_U_N_T(long groupId, long userId,
		java.lang.String name, int type);

	/**
	* Returns the number of commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param type the type
	* @return the number of matching commerce carts
	*/
	public int countByG_U_N_T(long groupId, long userId, java.lang.String name,
		int type);

	/**
	* Caches the commerce cart in the entity cache if it is enabled.
	*
	* @param commerceCart the commerce cart
	*/
	public void cacheResult(CommerceCart commerceCart);

	/**
	* Caches the commerce carts in the entity cache if it is enabled.
	*
	* @param commerceCarts the commerce carts
	*/
	public void cacheResult(java.util.List<CommerceCart> commerceCarts);

	/**
	* Creates a new commerce cart with the primary key. Does not add the commerce cart to the database.
	*
	* @param CommerceCartId the primary key for the new commerce cart
	* @return the new commerce cart
	*/
	public CommerceCart create(long CommerceCartId);

	/**
	* Removes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CommerceCartId the primary key of the commerce cart
	* @return the commerce cart that was removed
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart remove(long CommerceCartId) throws NoSuchCartException;

	public CommerceCart updateImpl(CommerceCart commerceCart);

	/**
	* Returns the commerce cart with the primary key or throws a {@link NoSuchCartException} if it could not be found.
	*
	* @param CommerceCartId the primary key of the commerce cart
	* @return the commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart findByPrimaryKey(long CommerceCartId)
		throws NoSuchCartException;

	/**
	* Returns the commerce cart with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CommerceCartId the primary key of the commerce cart
	* @return the commerce cart, or <code>null</code> if a commerce cart with the primary key could not be found
	*/
	public CommerceCart fetchByPrimaryKey(long CommerceCartId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceCart> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce carts.
	*
	* @return the commerce carts
	*/
	public java.util.List<CommerceCart> findAll();

	/**
	* Returns a range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of commerce carts
	*/
	public java.util.List<CommerceCart> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce carts
	*/
	public java.util.List<CommerceCart> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce carts
	*/
	public java.util.List<CommerceCart> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce carts from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce carts.
	*
	* @return the number of commerce carts
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}