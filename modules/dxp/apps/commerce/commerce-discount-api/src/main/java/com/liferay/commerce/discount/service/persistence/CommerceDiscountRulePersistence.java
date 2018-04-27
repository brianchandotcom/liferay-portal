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

package com.liferay.commerce.discount.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.exception.NoSuchDiscountRuleException;
import com.liferay.commerce.discount.model.CommerceDiscountRule;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce discount rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountRulePersistenceImpl
 * @see CommerceDiscountRuleUtil
 * @generated
 */
@ProviderType
public interface CommerceDiscountRulePersistence extends BasePersistence<CommerceDiscountRule> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDiscountRuleUtil} to access the commerce discount rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce discount rules where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @return the matching commerce discount rules
	*/
	public java.util.List<CommerceDiscountRule> findBycommerceDiscountId(
		long commerceDiscountId);

	/**
	* Returns a range of all the commerce discount rules where commerceDiscountId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceDiscountId the commerce discount ID
	* @param start the lower bound of the range of commerce discount rules
	* @param end the upper bound of the range of commerce discount rules (not inclusive)
	* @return the range of matching commerce discount rules
	*/
	public java.util.List<CommerceDiscountRule> findBycommerceDiscountId(
		long commerceDiscountId, int start, int end);

	/**
	* Returns an ordered range of all the commerce discount rules where commerceDiscountId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceDiscountId the commerce discount ID
	* @param start the lower bound of the range of commerce discount rules
	* @param end the upper bound of the range of commerce discount rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discount rules
	*/
	public java.util.List<CommerceDiscountRule> findBycommerceDiscountId(
		long commerceDiscountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountRule> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discount rules where commerceDiscountId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceDiscountId the commerce discount ID
	* @param start the lower bound of the range of commerce discount rules
	* @param end the upper bound of the range of commerce discount rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discount rules
	*/
	public java.util.List<CommerceDiscountRule> findBycommerceDiscountId(
		long commerceDiscountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountRule> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount rule
	* @throws NoSuchDiscountRuleException if a matching commerce discount rule could not be found
	*/
	public CommerceDiscountRule findBycommerceDiscountId_First(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws NoSuchDiscountRuleException;

	/**
	* Returns the first commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount rule, or <code>null</code> if a matching commerce discount rule could not be found
	*/
	public CommerceDiscountRule fetchBycommerceDiscountId_First(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountRule> orderByComparator);

	/**
	* Returns the last commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount rule
	* @throws NoSuchDiscountRuleException if a matching commerce discount rule could not be found
	*/
	public CommerceDiscountRule findBycommerceDiscountId_Last(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws NoSuchDiscountRuleException;

	/**
	* Returns the last commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount rule, or <code>null</code> if a matching commerce discount rule could not be found
	*/
	public CommerceDiscountRule fetchBycommerceDiscountId_Last(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountRule> orderByComparator);

	/**
	* Returns the commerce discount rules before and after the current commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountRuleId the primary key of the current commerce discount rule
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount rule
	* @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	*/
	public CommerceDiscountRule[] findBycommerceDiscountId_PrevAndNext(
		long commerceDiscountRuleId, long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws NoSuchDiscountRuleException;

	/**
	* Removes all the commerce discount rules where commerceDiscountId = &#63; from the database.
	*
	* @param commerceDiscountId the commerce discount ID
	*/
	public void removeBycommerceDiscountId(long commerceDiscountId);

	/**
	* Returns the number of commerce discount rules where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @return the number of matching commerce discount rules
	*/
	public int countBycommerceDiscountId(long commerceDiscountId);

	/**
	* Caches the commerce discount rule in the entity cache if it is enabled.
	*
	* @param commerceDiscountRule the commerce discount rule
	*/
	public void cacheResult(CommerceDiscountRule commerceDiscountRule);

	/**
	* Caches the commerce discount rules in the entity cache if it is enabled.
	*
	* @param commerceDiscountRules the commerce discount rules
	*/
	public void cacheResult(
		java.util.List<CommerceDiscountRule> commerceDiscountRules);

	/**
	* Creates a new commerce discount rule with the primary key. Does not add the commerce discount rule to the database.
	*
	* @param commerceDiscountRuleId the primary key for the new commerce discount rule
	* @return the new commerce discount rule
	*/
	public CommerceDiscountRule create(long commerceDiscountRuleId);

	/**
	* Removes the commerce discount rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountRuleId the primary key of the commerce discount rule
	* @return the commerce discount rule that was removed
	* @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	*/
	public CommerceDiscountRule remove(long commerceDiscountRuleId)
		throws NoSuchDiscountRuleException;

	public CommerceDiscountRule updateImpl(
		CommerceDiscountRule commerceDiscountRule);

	/**
	* Returns the commerce discount rule with the primary key or throws a {@link NoSuchDiscountRuleException} if it could not be found.
	*
	* @param commerceDiscountRuleId the primary key of the commerce discount rule
	* @return the commerce discount rule
	* @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	*/
	public CommerceDiscountRule findByPrimaryKey(long commerceDiscountRuleId)
		throws NoSuchDiscountRuleException;

	/**
	* Returns the commerce discount rule with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceDiscountRuleId the primary key of the commerce discount rule
	* @return the commerce discount rule, or <code>null</code> if a commerce discount rule with the primary key could not be found
	*/
	public CommerceDiscountRule fetchByPrimaryKey(long commerceDiscountRuleId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceDiscountRule> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce discount rules.
	*
	* @return the commerce discount rules
	*/
	public java.util.List<CommerceDiscountRule> findAll();

	/**
	* Returns a range of all the commerce discount rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount rules
	* @param end the upper bound of the range of commerce discount rules (not inclusive)
	* @return the range of commerce discount rules
	*/
	public java.util.List<CommerceDiscountRule> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce discount rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount rules
	* @param end the upper bound of the range of commerce discount rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce discount rules
	*/
	public java.util.List<CommerceDiscountRule> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountRule> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discount rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount rules
	* @param end the upper bound of the range of commerce discount rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce discount rules
	*/
	public java.util.List<CommerceDiscountRule> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountRule> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce discount rules from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce discount rules.
	*
	* @return the number of commerce discount rules
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}