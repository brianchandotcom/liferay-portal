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

import com.liferay.commerce.discount.model.CommerceDiscountRule;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce discount rule service. This utility wraps {@link com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountRulePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountRulePersistence
 * @see com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountRulePersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceDiscountRuleUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CommerceDiscountRule commerceDiscountRule) {
		getPersistence().clearCache(commerceDiscountRule);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceDiscountRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceDiscountRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceDiscountRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceDiscountRule update(
		CommerceDiscountRule commerceDiscountRule) {
		return getPersistence().update(commerceDiscountRule);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceDiscountRule update(
		CommerceDiscountRule commerceDiscountRule, ServiceContext serviceContext) {
		return getPersistence().update(commerceDiscountRule, serviceContext);
	}

	/**
	* Returns all the commerce discount rules where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @return the matching commerce discount rules
	*/
	public static List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId) {
		return getPersistence().findByCommerceDiscountId(commerceDiscountId);
	}

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
	public static List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end) {
		return getPersistence()
				   .findByCommerceDiscountId(commerceDiscountId, start, end);
	}

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
	public static List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {
		return getPersistence()
				   .findByCommerceDiscountId(commerceDiscountId, start, end,
			orderByComparator);
	}

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
	public static List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceDiscountId(commerceDiscountId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount rule
	* @throws NoSuchDiscountRuleException if a matching commerce discount rule could not be found
	*/
	public static CommerceDiscountRule findByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountRuleException {
		return getPersistence()
				   .findByCommerceDiscountId_First(commerceDiscountId,
			orderByComparator);
	}

	/**
	* Returns the first commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount rule, or <code>null</code> if a matching commerce discount rule could not be found
	*/
	public static CommerceDiscountRule fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceDiscountId_First(commerceDiscountId,
			orderByComparator);
	}

	/**
	* Returns the last commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount rule
	* @throws NoSuchDiscountRuleException if a matching commerce discount rule could not be found
	*/
	public static CommerceDiscountRule findByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountRuleException {
		return getPersistence()
				   .findByCommerceDiscountId_Last(commerceDiscountId,
			orderByComparator);
	}

	/**
	* Returns the last commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount rule, or <code>null</code> if a matching commerce discount rule could not be found
	*/
	public static CommerceDiscountRule fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceDiscountId_Last(commerceDiscountId,
			orderByComparator);
	}

	/**
	* Returns the commerce discount rules before and after the current commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountRuleId the primary key of the current commerce discount rule
	* @param commerceDiscountId the commerce discount ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount rule
	* @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	*/
	public static CommerceDiscountRule[] findByCommerceDiscountId_PrevAndNext(
		long commerceDiscountRuleId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountRuleException {
		return getPersistence()
				   .findByCommerceDiscountId_PrevAndNext(commerceDiscountRuleId,
			commerceDiscountId, orderByComparator);
	}

	/**
	* Removes all the commerce discount rules where commerceDiscountId = &#63; from the database.
	*
	* @param commerceDiscountId the commerce discount ID
	*/
	public static void removeByCommerceDiscountId(long commerceDiscountId) {
		getPersistence().removeByCommerceDiscountId(commerceDiscountId);
	}

	/**
	* Returns the number of commerce discount rules where commerceDiscountId = &#63;.
	*
	* @param commerceDiscountId the commerce discount ID
	* @return the number of matching commerce discount rules
	*/
	public static int countByCommerceDiscountId(long commerceDiscountId) {
		return getPersistence().countByCommerceDiscountId(commerceDiscountId);
	}

	/**
	* Caches the commerce discount rule in the entity cache if it is enabled.
	*
	* @param commerceDiscountRule the commerce discount rule
	*/
	public static void cacheResult(CommerceDiscountRule commerceDiscountRule) {
		getPersistence().cacheResult(commerceDiscountRule);
	}

	/**
	* Caches the commerce discount rules in the entity cache if it is enabled.
	*
	* @param commerceDiscountRules the commerce discount rules
	*/
	public static void cacheResult(
		List<CommerceDiscountRule> commerceDiscountRules) {
		getPersistence().cacheResult(commerceDiscountRules);
	}

	/**
	* Creates a new commerce discount rule with the primary key. Does not add the commerce discount rule to the database.
	*
	* @param commerceDiscountRuleId the primary key for the new commerce discount rule
	* @return the new commerce discount rule
	*/
	public static CommerceDiscountRule create(long commerceDiscountRuleId) {
		return getPersistence().create(commerceDiscountRuleId);
	}

	/**
	* Removes the commerce discount rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountRuleId the primary key of the commerce discount rule
	* @return the commerce discount rule that was removed
	* @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	*/
	public static CommerceDiscountRule remove(long commerceDiscountRuleId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountRuleException {
		return getPersistence().remove(commerceDiscountRuleId);
	}

	public static CommerceDiscountRule updateImpl(
		CommerceDiscountRule commerceDiscountRule) {
		return getPersistence().updateImpl(commerceDiscountRule);
	}

	/**
	* Returns the commerce discount rule with the primary key or throws a {@link NoSuchDiscountRuleException} if it could not be found.
	*
	* @param commerceDiscountRuleId the primary key of the commerce discount rule
	* @return the commerce discount rule
	* @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	*/
	public static CommerceDiscountRule findByPrimaryKey(
		long commerceDiscountRuleId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountRuleException {
		return getPersistence().findByPrimaryKey(commerceDiscountRuleId);
	}

	/**
	* Returns the commerce discount rule with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceDiscountRuleId the primary key of the commerce discount rule
	* @return the commerce discount rule, or <code>null</code> if a commerce discount rule with the primary key could not be found
	*/
	public static CommerceDiscountRule fetchByPrimaryKey(
		long commerceDiscountRuleId) {
		return getPersistence().fetchByPrimaryKey(commerceDiscountRuleId);
	}

	public static java.util.Map<java.io.Serializable, CommerceDiscountRule> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce discount rules.
	*
	* @return the commerce discount rules
	*/
	public static List<CommerceDiscountRule> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CommerceDiscountRule> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CommerceDiscountRule> findAll(int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CommerceDiscountRule> findAll(int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce discount rules from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce discount rules.
	*
	* @return the number of commerce discount rules
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceDiscountRulePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceDiscountRulePersistence, CommerceDiscountRulePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceDiscountRulePersistence.class);

		ServiceTracker<CommerceDiscountRulePersistence, CommerceDiscountRulePersistence> serviceTracker =
			new ServiceTracker<CommerceDiscountRulePersistence, CommerceDiscountRulePersistence>(bundle.getBundleContext(),
				CommerceDiscountRulePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}