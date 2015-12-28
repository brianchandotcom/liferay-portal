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

package com.liferay.mobile.device.rules.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.mobile.device.rules.model.MDRRule;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the m d r rule service. This utility wraps {@link com.liferay.mobile.device.rules.service.persistence.impl.MDRRulePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRulePersistence
 * @see com.liferay.mobile.device.rules.service.persistence.impl.MDRRulePersistenceImpl
 * @generated
 */
@ProviderType
public class MDRRuleUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(MDRRule mdrRule) {
		getPersistence().clearCache(mdrRule);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MDRRule> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MDRRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MDRRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MDRRule update(MDRRule mdrRule) {
		return getPersistence().update(mdrRule);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MDRRule update(MDRRule mdrRule, ServiceContext serviceContext) {
		return getPersistence().update(mdrRule, serviceContext);
	}

	/**
	* Returns all the m d r rules where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching m d r rules
	*/
	public static List<MDRRule> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the m d r rules where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @return the range of matching m d r rules
	*/
	public static List<MDRRule> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rules where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rules
	*/
	public static List<MDRRule> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rules where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r rules
	*/
	public static List<MDRRule> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<MDRRule> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first m d r rule in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	*/
	public static MDRRule findByUuid_First(java.lang.String uuid,
		OrderByComparator<MDRRule> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first m d r rule in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	*/
	public static MDRRule fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r rule in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	*/
	public static MDRRule findByUuid_Last(java.lang.String uuid,
		OrderByComparator<MDRRule> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r rule in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	*/
	public static MDRRule fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the m d r rules before and after the current m d r rule in the ordered set where uuid = &#63;.
	*
	* @param ruleId the primary key of the current m d r rule
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	*/
	public static MDRRule[] findByUuid_PrevAndNext(long ruleId,
		java.lang.String uuid, OrderByComparator<MDRRule> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence()
				   .findByUuid_PrevAndNext(ruleId, uuid, orderByComparator);
	}

	/**
	* Removes all the m d r rules where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of m d r rules where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching m d r rules
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the m d r rule where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.mobile.device.rules.NoSuchRuleException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	*/
	public static MDRRule findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	*/
	public static MDRRule fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	*/
	public static MDRRule fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the m d r rule where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the m d r rule that was removed
	*/
	public static MDRRule removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of m d r rules where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching m d r rules
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the m d r rules where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching m d r rules
	*/
	public static List<MDRRule> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the m d r rules where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @return the range of matching m d r rules
	*/
	public static List<MDRRule> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rules where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rules
	*/
	public static List<MDRRule> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rules where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r rules
	*/
	public static List<MDRRule> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<MDRRule> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	*/
	public static MDRRule findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<MDRRule> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	*/
	public static MDRRule fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	*/
	public static MDRRule findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<MDRRule> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	*/
	public static MDRRule fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the m d r rules before and after the current m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param ruleId the primary key of the current m d r rule
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	*/
	public static MDRRule[] findByUuid_C_PrevAndNext(long ruleId,
		java.lang.String uuid, long companyId,
		OrderByComparator<MDRRule> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(ruleId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the m d r rules where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of m d r rules where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching m d r rules
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the m d r rules where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rules
	*/
	public static List<MDRRule> findByRuleGroupId(long ruleGroupId) {
		return getPersistence().findByRuleGroupId(ruleGroupId);
	}

	/**
	* Returns a range of all the m d r rules where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @return the range of matching m d r rules
	*/
	public static List<MDRRule> findByRuleGroupId(long ruleGroupId, int start,
		int end) {
		return getPersistence().findByRuleGroupId(ruleGroupId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rules where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rules
	*/
	public static List<MDRRule> findByRuleGroupId(long ruleGroupId, int start,
		int end, OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence()
				   .findByRuleGroupId(ruleGroupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rules where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r rules
	*/
	public static List<MDRRule> findByRuleGroupId(long ruleGroupId, int start,
		int end, OrderByComparator<MDRRule> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByRuleGroupId(ruleGroupId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first m d r rule in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	*/
	public static MDRRule findByRuleGroupId_First(long ruleGroupId,
		OrderByComparator<MDRRule> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence()
				   .findByRuleGroupId_First(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the first m d r rule in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	*/
	public static MDRRule fetchByRuleGroupId_First(long ruleGroupId,
		OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence()
				   .fetchByRuleGroupId_First(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	*/
	public static MDRRule findByRuleGroupId_Last(long ruleGroupId,
		OrderByComparator<MDRRule> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence()
				   .findByRuleGroupId_Last(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	*/
	public static MDRRule fetchByRuleGroupId_Last(long ruleGroupId,
		OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence()
				   .fetchByRuleGroupId_Last(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the m d r rules before and after the current m d r rule in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleId the primary key of the current m d r rule
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	*/
	public static MDRRule[] findByRuleGroupId_PrevAndNext(long ruleId,
		long ruleGroupId, OrderByComparator<MDRRule> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence()
				   .findByRuleGroupId_PrevAndNext(ruleId, ruleGroupId,
			orderByComparator);
	}

	/**
	* Removes all the m d r rules where ruleGroupId = &#63; from the database.
	*
	* @param ruleGroupId the rule group ID
	*/
	public static void removeByRuleGroupId(long ruleGroupId) {
		getPersistence().removeByRuleGroupId(ruleGroupId);
	}

	/**
	* Returns the number of m d r rules where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the number of matching m d r rules
	*/
	public static int countByRuleGroupId(long ruleGroupId) {
		return getPersistence().countByRuleGroupId(ruleGroupId);
	}

	/**
	* Caches the m d r rule in the entity cache if it is enabled.
	*
	* @param mdrRule the m d r rule
	*/
	public static void cacheResult(MDRRule mdrRule) {
		getPersistence().cacheResult(mdrRule);
	}

	/**
	* Caches the m d r rules in the entity cache if it is enabled.
	*
	* @param mdrRules the m d r rules
	*/
	public static void cacheResult(List<MDRRule> mdrRules) {
		getPersistence().cacheResult(mdrRules);
	}

	/**
	* Creates a new m d r rule with the primary key. Does not add the m d r rule to the database.
	*
	* @param ruleId the primary key for the new m d r rule
	* @return the new m d r rule
	*/
	public static MDRRule create(long ruleId) {
		return getPersistence().create(ruleId);
	}

	/**
	* Removes the m d r rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleId the primary key of the m d r rule
	* @return the m d r rule that was removed
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	*/
	public static MDRRule remove(long ruleId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence().remove(ruleId);
	}

	public static MDRRule updateImpl(MDRRule mdrRule) {
		return getPersistence().updateImpl(mdrRule);
	}

	/**
	* Returns the m d r rule with the primary key or throws a {@link com.liferay.mobile.device.rules.NoSuchRuleException} if it could not be found.
	*
	* @param ruleId the primary key of the m d r rule
	* @return the m d r rule
	* @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	*/
	public static MDRRule findByPrimaryKey(long ruleId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleException {
		return getPersistence().findByPrimaryKey(ruleId);
	}

	/**
	* Returns the m d r rule with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ruleId the primary key of the m d r rule
	* @return the m d r rule, or <code>null</code> if a m d r rule with the primary key could not be found
	*/
	public static MDRRule fetchByPrimaryKey(long ruleId) {
		return getPersistence().fetchByPrimaryKey(ruleId);
	}

	public static java.util.Map<java.io.Serializable, MDRRule> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the m d r rules.
	*
	* @return the m d r rules
	*/
	public static List<MDRRule> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the m d r rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @return the range of m d r rules
	*/
	public static List<MDRRule> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the m d r rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of m d r rules
	*/
	public static List<MDRRule> findAll(int start, int end,
		OrderByComparator<MDRRule> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of m d r rules
	*/
	public static List<MDRRule> findAll(int start, int end,
		OrderByComparator<MDRRule> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the m d r rules from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of m d r rules.
	*
	* @return the number of m d r rules
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static MDRRulePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MDRRulePersistence, MDRRulePersistence> _serviceTracker =
		ServiceTrackerFactory.open(MDRRulePersistence.class);
}