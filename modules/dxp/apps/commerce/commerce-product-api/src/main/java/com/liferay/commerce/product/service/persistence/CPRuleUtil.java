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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPRule;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp rule service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPRulePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPRulePersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPRulePersistenceImpl
 * @generated
 */
@ProviderType
public class CPRuleUtil {
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
	public static void clearCache(CPRule cpRule) {
		getPersistence().clearCache(cpRule);
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
	public static List<CPRule> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPRule> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPRule> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator<CPRule> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPRule update(CPRule cpRule) {
		return getPersistence().update(cpRule);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPRule update(CPRule cpRule, ServiceContext serviceContext) {
		return getPersistence().update(cpRule, serviceContext);
	}

	/**
	* Returns all the cp rules where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp rules
	*/
	public static List<CPRule> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp rules where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp rules
	* @param end the upper bound of the range of cp rules (not inclusive)
	* @return the range of matching cp rules
	*/
	public static List<CPRule> findByGroupId(long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp rules where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp rules
	* @param end the upper bound of the range of cp rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp rules
	*/
	public static List<CPRule> findByGroupId(long groupId, int start, int end,
		OrderByComparator<CPRule> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp rules where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp rules
	* @param end the upper bound of the range of cp rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp rules
	*/
	public static List<CPRule> findByGroupId(long groupId, int start, int end,
		OrderByComparator<CPRule> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp rule in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule
	* @throws NoSuchCPRuleException if a matching cp rule could not be found
	*/
	public static CPRule findByGroupId_First(long groupId,
		OrderByComparator<CPRule> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp rule in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule, or <code>null</code> if a matching cp rule could not be found
	*/
	public static CPRule fetchByGroupId_First(long groupId,
		OrderByComparator<CPRule> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp rule in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule
	* @throws NoSuchCPRuleException if a matching cp rule could not be found
	*/
	public static CPRule findByGroupId_Last(long groupId,
		OrderByComparator<CPRule> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp rule in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule, or <code>null</code> if a matching cp rule could not be found
	*/
	public static CPRule fetchByGroupId_Last(long groupId,
		OrderByComparator<CPRule> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp rules before and after the current cp rule in the ordered set where groupId = &#63;.
	*
	* @param CPRuleId the primary key of the current cp rule
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule
	* @throws NoSuchCPRuleException if a cp rule with the primary key could not be found
	*/
	public static CPRule[] findByGroupId_PrevAndNext(long CPRuleId,
		long groupId, OrderByComparator<CPRule> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPRuleId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the cp rules that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp rules that the user has permission to view
	*/
	public static List<CPRule> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp rules that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp rules
	* @param end the upper bound of the range of cp rules (not inclusive)
	* @return the range of matching cp rules that the user has permission to view
	*/
	public static List<CPRule> filterFindByGroupId(long groupId, int start,
		int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp rules that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp rules
	* @param end the upper bound of the range of cp rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp rules that the user has permission to view
	*/
	public static List<CPRule> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<CPRule> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the cp rules before and after the current cp rule in the ordered set of cp rules that the user has permission to view where groupId = &#63;.
	*
	* @param CPRuleId the primary key of the current cp rule
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule
	* @throws NoSuchCPRuleException if a cp rule with the primary key could not be found
	*/
	public static CPRule[] filterFindByGroupId_PrevAndNext(long CPRuleId,
		long groupId, OrderByComparator<CPRule> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(CPRuleId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the cp rules where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp rules where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp rules
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of cp rules that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp rules that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Caches the cp rule in the entity cache if it is enabled.
	*
	* @param cpRule the cp rule
	*/
	public static void cacheResult(CPRule cpRule) {
		getPersistence().cacheResult(cpRule);
	}

	/**
	* Caches the cp rules in the entity cache if it is enabled.
	*
	* @param cpRules the cp rules
	*/
	public static void cacheResult(List<CPRule> cpRules) {
		getPersistence().cacheResult(cpRules);
	}

	/**
	* Creates a new cp rule with the primary key. Does not add the cp rule to the database.
	*
	* @param CPRuleId the primary key for the new cp rule
	* @return the new cp rule
	*/
	public static CPRule create(long CPRuleId) {
		return getPersistence().create(CPRuleId);
	}

	/**
	* Removes the cp rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleId the primary key of the cp rule
	* @return the cp rule that was removed
	* @throws NoSuchCPRuleException if a cp rule with the primary key could not be found
	*/
	public static CPRule remove(long CPRuleId)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleException {
		return getPersistence().remove(CPRuleId);
	}

	public static CPRule updateImpl(CPRule cpRule) {
		return getPersistence().updateImpl(cpRule);
	}

	/**
	* Returns the cp rule with the primary key or throws a {@link NoSuchCPRuleException} if it could not be found.
	*
	* @param CPRuleId the primary key of the cp rule
	* @return the cp rule
	* @throws NoSuchCPRuleException if a cp rule with the primary key could not be found
	*/
	public static CPRule findByPrimaryKey(long CPRuleId)
		throws com.liferay.commerce.product.exception.NoSuchCPRuleException {
		return getPersistence().findByPrimaryKey(CPRuleId);
	}

	/**
	* Returns the cp rule with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPRuleId the primary key of the cp rule
	* @return the cp rule, or <code>null</code> if a cp rule with the primary key could not be found
	*/
	public static CPRule fetchByPrimaryKey(long CPRuleId) {
		return getPersistence().fetchByPrimaryKey(CPRuleId);
	}

	public static java.util.Map<java.io.Serializable, CPRule> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp rules.
	*
	* @return the cp rules
	*/
	public static List<CPRule> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rules
	* @param end the upper bound of the range of cp rules (not inclusive)
	* @return the range of cp rules
	*/
	public static List<CPRule> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rules
	* @param end the upper bound of the range of cp rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp rules
	*/
	public static List<CPRule> findAll(int start, int end,
		OrderByComparator<CPRule> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rules
	* @param end the upper bound of the range of cp rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp rules
	*/
	public static List<CPRule> findAll(int start, int end,
		OrderByComparator<CPRule> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp rules from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp rules.
	*
	* @return the number of cp rules
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPRulePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPRulePersistence, CPRulePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPRulePersistence.class);

		ServiceTracker<CPRulePersistence, CPRulePersistence> serviceTracker = new ServiceTracker<CPRulePersistence, CPRulePersistence>(bundle.getBundleContext(),
				CPRulePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}