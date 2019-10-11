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

import com.liferay.mobile.device.rules.exception.NoSuchRuleException;
import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the mdr rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleUtil
 * @generated
 */
@ProviderType
public interface MDRRulePersistence extends BasePersistence<MDRRule> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRRuleUtil} to access the mdr rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the mdr rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching mdr rules
	 */
	public java.util.List<MDRRule> findByUuid(String uuid);

	/**
	 * Returns a range of all the mdr rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @return the range of matching mdr rules
	 */
	public java.util.List<MDRRule> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rules
	 */
	public java.util.List<MDRRule> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rules
	 */
	public java.util.List<MDRRule> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule
	 * @throws NoSuchRuleException if a matching mdr rule could not be found
	 */
	public MDRRule findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Returns the first mdr rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public MDRRule fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns the last mdr rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule
	 * @throws NoSuchRuleException if a matching mdr rule could not be found
	 */
	public MDRRule findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Returns the last mdr rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public MDRRule fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns the mdr rules before and after the current mdr rule in the ordered set where uuid = &#63;.
	 *
	 * @param ruleId the primary key of the current mdr rule
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule
	 * @throws NoSuchRuleException if a mdr rule with the primary key could not be found
	 */
	public MDRRule[] findByUuid_PrevAndNext(
			long ruleId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Removes all the mdr rules where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of mdr rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching mdr rules
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the mdr rule where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRuleException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr rule
	 * @throws NoSuchRuleException if a matching mdr rule could not be found
	 */
	public MDRRule findByUUID_G(String uuid, long groupId)
		throws NoSuchRuleException;

	/**
	 * Returns the mdr rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public MDRRule fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the mdr rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public MDRRule fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the mdr rule where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the mdr rule that was removed
	 */
	public MDRRule removeByUUID_G(String uuid, long groupId)
		throws NoSuchRuleException;

	/**
	 * Returns the number of mdr rules where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching mdr rules
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the mdr rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching mdr rules
	 */
	public java.util.List<MDRRule> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the mdr rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @return the range of matching mdr rules
	 */
	public java.util.List<MDRRule> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rules
	 */
	public java.util.List<MDRRule> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rules
	 */
	public java.util.List<MDRRule> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule
	 * @throws NoSuchRuleException if a matching mdr rule could not be found
	 */
	public MDRRule findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Returns the first mdr rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public MDRRule fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns the last mdr rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule
	 * @throws NoSuchRuleException if a matching mdr rule could not be found
	 */
	public MDRRule findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Returns the last mdr rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public MDRRule fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns the mdr rules before and after the current mdr rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ruleId the primary key of the current mdr rule
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule
	 * @throws NoSuchRuleException if a mdr rule with the primary key could not be found
	 */
	public MDRRule[] findByUuid_C_PrevAndNext(
			long ruleId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Removes all the mdr rules where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of mdr rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching mdr rules
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the mdr rules where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the matching mdr rules
	 */
	public java.util.List<MDRRule> findByRuleGroupId(long ruleGroupId);

	/**
	 * Returns a range of all the mdr rules where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @return the range of matching mdr rules
	 */
	public java.util.List<MDRRule> findByRuleGroupId(
		long ruleGroupId, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rules where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rules
	 */
	public java.util.List<MDRRule> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rules where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rules
	 */
	public java.util.List<MDRRule> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule
	 * @throws NoSuchRuleException if a matching mdr rule could not be found
	 */
	public MDRRule findByRuleGroupId_First(
			long ruleGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Returns the first mdr rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public MDRRule fetchByRuleGroupId_First(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns the last mdr rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule
	 * @throws NoSuchRuleException if a matching mdr rule could not be found
	 */
	public MDRRule findByRuleGroupId_Last(
			long ruleGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Returns the last mdr rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public MDRRule fetchByRuleGroupId_Last(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns the mdr rules before and after the current mdr rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleId the primary key of the current mdr rule
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule
	 * @throws NoSuchRuleException if a mdr rule with the primary key could not be found
	 */
	public MDRRule[] findByRuleGroupId_PrevAndNext(
			long ruleId, long ruleGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
				orderByComparator)
		throws NoSuchRuleException;

	/**
	 * Removes all the mdr rules where ruleGroupId = &#63; from the database.
	 *
	 * @param ruleGroupId the rule group ID
	 */
	public void removeByRuleGroupId(long ruleGroupId);

	/**
	 * Returns the number of mdr rules where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the number of matching mdr rules
	 */
	public int countByRuleGroupId(long ruleGroupId);

	/**
	 * Caches the mdr rule in the entity cache if it is enabled.
	 *
	 * @param mdrRule the mdr rule
	 */
	public void cacheResult(MDRRule mdrRule);

	/**
	 * Caches the mdr rules in the entity cache if it is enabled.
	 *
	 * @param mdrRules the mdr rules
	 */
	public void cacheResult(java.util.List<MDRRule> mdrRules);

	/**
	 * Creates a new mdr rule with the primary key. Does not add the mdr rule to the database.
	 *
	 * @param ruleId the primary key for the new mdr rule
	 * @return the new mdr rule
	 */
	public MDRRule create(long ruleId);

	/**
	 * Removes the mdr rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ruleId the primary key of the mdr rule
	 * @return the mdr rule that was removed
	 * @throws NoSuchRuleException if a mdr rule with the primary key could not be found
	 */
	public MDRRule remove(long ruleId) throws NoSuchRuleException;

	public MDRRule updateImpl(MDRRule mdrRule);

	/**
	 * Returns the mdr rule with the primary key or throws a <code>NoSuchRuleException</code> if it could not be found.
	 *
	 * @param ruleId the primary key of the mdr rule
	 * @return the mdr rule
	 * @throws NoSuchRuleException if a mdr rule with the primary key could not be found
	 */
	public MDRRule findByPrimaryKey(long ruleId) throws NoSuchRuleException;

	/**
	 * Returns the mdr rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ruleId the primary key of the mdr rule
	 * @return the mdr rule, or <code>null</code> if a mdr rule with the primary key could not be found
	 */
	public MDRRule fetchByPrimaryKey(long ruleId);

	/**
	 * Returns all the mdr rules.
	 *
	 * @return the mdr rules
	 */
	public java.util.List<MDRRule> findAll();

	/**
	 * Returns a range of all the mdr rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @return the range of mdr rules
	 */
	public java.util.List<MDRRule> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the mdr rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mdr rules
	 */
	public java.util.List<MDRRule> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mdr rules
	 */
	public java.util.List<MDRRule> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the mdr rules from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of mdr rules.
	 *
	 * @return the number of mdr rules
	 */
	public int countAll();

}