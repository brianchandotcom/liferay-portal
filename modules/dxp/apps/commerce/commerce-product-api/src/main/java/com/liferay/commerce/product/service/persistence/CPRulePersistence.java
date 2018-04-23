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

import com.liferay.commerce.product.exception.NoSuchCPRuleException;
import com.liferay.commerce.product.model.CPRule;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPRulePersistenceImpl
 * @see CPRuleUtil
 * @generated
 */
@ProviderType
public interface CPRulePersistence extends BasePersistence<CPRule> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPRuleUtil} to access the cp rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp rules where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp rules
	*/
	public java.util.List<CPRule> findByGroupId(long groupId);

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
	public java.util.List<CPRule> findByGroupId(long groupId, int start, int end);

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
	public java.util.List<CPRule> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator);

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
	public java.util.List<CPRule> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp rule in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule
	* @throws NoSuchCPRuleException if a matching cp rule could not be found
	*/
	public CPRule findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator)
		throws NoSuchCPRuleException;

	/**
	* Returns the first cp rule in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp rule, or <code>null</code> if a matching cp rule could not be found
	*/
	public CPRule fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator);

	/**
	* Returns the last cp rule in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule
	* @throws NoSuchCPRuleException if a matching cp rule could not be found
	*/
	public CPRule findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator)
		throws NoSuchCPRuleException;

	/**
	* Returns the last cp rule in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp rule, or <code>null</code> if a matching cp rule could not be found
	*/
	public CPRule fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator);

	/**
	* Returns the cp rules before and after the current cp rule in the ordered set where groupId = &#63;.
	*
	* @param CPRuleId the primary key of the current cp rule
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule
	* @throws NoSuchCPRuleException if a cp rule with the primary key could not be found
	*/
	public CPRule[] findByGroupId_PrevAndNext(long CPRuleId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator)
		throws NoSuchCPRuleException;

	/**
	* Returns all the cp rules that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp rules that the user has permission to view
	*/
	public java.util.List<CPRule> filterFindByGroupId(long groupId);

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
	public java.util.List<CPRule> filterFindByGroupId(long groupId, int start,
		int end);

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
	public java.util.List<CPRule> filterFindByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator);

	/**
	* Returns the cp rules before and after the current cp rule in the ordered set of cp rules that the user has permission to view where groupId = &#63;.
	*
	* @param CPRuleId the primary key of the current cp rule
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp rule
	* @throws NoSuchCPRuleException if a cp rule with the primary key could not be found
	*/
	public CPRule[] filterFindByGroupId_PrevAndNext(long CPRuleId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator)
		throws NoSuchCPRuleException;

	/**
	* Removes all the cp rules where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp rules where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp rules
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of cp rules that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp rules that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Caches the cp rule in the entity cache if it is enabled.
	*
	* @param cpRule the cp rule
	*/
	public void cacheResult(CPRule cpRule);

	/**
	* Caches the cp rules in the entity cache if it is enabled.
	*
	* @param cpRules the cp rules
	*/
	public void cacheResult(java.util.List<CPRule> cpRules);

	/**
	* Creates a new cp rule with the primary key. Does not add the cp rule to the database.
	*
	* @param CPRuleId the primary key for the new cp rule
	* @return the new cp rule
	*/
	public CPRule create(long CPRuleId);

	/**
	* Removes the cp rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleId the primary key of the cp rule
	* @return the cp rule that was removed
	* @throws NoSuchCPRuleException if a cp rule with the primary key could not be found
	*/
	public CPRule remove(long CPRuleId) throws NoSuchCPRuleException;

	public CPRule updateImpl(CPRule cpRule);

	/**
	* Returns the cp rule with the primary key or throws a {@link NoSuchCPRuleException} if it could not be found.
	*
	* @param CPRuleId the primary key of the cp rule
	* @return the cp rule
	* @throws NoSuchCPRuleException if a cp rule with the primary key could not be found
	*/
	public CPRule findByPrimaryKey(long CPRuleId) throws NoSuchCPRuleException;

	/**
	* Returns the cp rule with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPRuleId the primary key of the cp rule
	* @return the cp rule, or <code>null</code> if a cp rule with the primary key could not be found
	*/
	public CPRule fetchByPrimaryKey(long CPRuleId);

	@Override
	public java.util.Map<java.io.Serializable, CPRule> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp rules.
	*
	* @return the cp rules
	*/
	public java.util.List<CPRule> findAll();

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
	public java.util.List<CPRule> findAll(int start, int end);

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
	public java.util.List<CPRule> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator);

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
	public java.util.List<CPRule> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPRule> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp rules from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp rules.
	*
	* @return the number of cp rules
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}