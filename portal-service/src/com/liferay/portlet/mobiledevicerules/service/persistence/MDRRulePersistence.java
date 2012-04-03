/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.mobiledevicerules.model.MDRRule;

/**
 * The persistence interface for the m d r rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRulePersistenceImpl
 * @see MDRRuleUtil
 * @generated
 */
public interface MDRRulePersistence extends BasePersistence<MDRRule> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRRuleUtil} to access the m d r rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the m d r rule in the entity cache if it is enabled.
	*
	* @param mdrRule the m d r rule
	*/
	public void cacheResult(
		com.liferay.portlet.mobiledevicerules.model.MDRRule mdrRule);

	/**
	* Caches the m d r rules in the entity cache if it is enabled.
	*
	* @param mdrRules the m d r rules
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> mdrRules);

	/**
	* Creates a new m d r rule with the primary key. Does not add the m d r rule to the database.
	*
	* @param ruleId the primary key for the new m d r rule
	* @return the new m d r rule
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule create(
		long ruleId);

	/**
	* Removes the m d r rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleId the primary key of the m d r rule
	* @return the m d r rule that was removed
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule remove(
		long ruleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	public com.liferay.portlet.mobiledevicerules.model.MDRRule updateImpl(
		com.liferay.portlet.mobiledevicerules.model.MDRRule mdrRule,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the m d r rule with the primary key or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleException} if it could not be found.
	*
	* @param ruleId the primary key of the m d r rule
	* @return the m d r rule
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule findByPrimaryKey(
		long ruleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	/**
	* Returns the m d r rule with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ruleId the primary key of the m d r rule
	* @return the m d r rule, or <code>null</code> if a m d r rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule fetchByPrimaryKey(
		long ruleId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the m d r rules where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the m d r rules where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @return the range of matching m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the m d r rules where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first m d r rule in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	/**
	* Returns the last m d r rule in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	/**
	* Returns the m d r rules before and after the current m d r rule in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleId the primary key of the current m d r rule
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule[] findByUuid_PrevAndNext(
		long ruleId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	/**
	* Returns the m d r rule where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	/**
	* Returns the m d r rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the m d r rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the m d r rules where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> findByRuleGroupId(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the m d r rules where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @return the range of matching m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> findByRuleGroupId(
		long ruleGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the m d r rules where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first m d r rule in the ordered set where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule findByRuleGroupId_First(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	/**
	* Returns the last m d r rule in the ordered set where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule findByRuleGroupId_Last(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	/**
	* Returns the m d r rules before and after the current m d r rule in the ordered set where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleId the primary key of the current m d r rule
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule[] findByRuleGroupId_PrevAndNext(
		long ruleId, long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	/**
	* Returns all the m d r rules.
	*
	* @return the m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the m d r rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @return the range of m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the m d r rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the m d r rules where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the m d r rule where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the m d r rule that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleException;

	/**
	* Removes all the m d r rules where ruleGroupId = &#63; from the database.
	*
	* @param ruleGroupId the rule group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByRuleGroupId(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the m d r rules from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of m d r rules where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of m d r rules where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of m d r rules where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the number of matching m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public int countByRuleGroupId(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of m d r rules.
	*
	* @return the number of m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}