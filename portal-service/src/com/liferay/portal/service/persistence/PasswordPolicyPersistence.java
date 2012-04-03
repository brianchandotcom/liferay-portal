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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.PasswordPolicy;

/**
 * The persistence interface for the password policy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyPersistenceImpl
 * @see PasswordPolicyUtil
 * @generated
 */
public interface PasswordPolicyPersistence extends BasePersistence<PasswordPolicy> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PasswordPolicyUtil} to access the password policy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the password policy in the entity cache if it is enabled.
	*
	* @param passwordPolicy the password policy
	*/
	public void cacheResult(
		com.liferay.portal.model.PasswordPolicy passwordPolicy);

	/**
	* Caches the password policies in the entity cache if it is enabled.
	*
	* @param passwordPolicies the password policies
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.PasswordPolicy> passwordPolicies);

	/**
	* Creates a new password policy with the primary key. Does not add the password policy to the database.
	*
	* @param passwordPolicyId the primary key for the new password policy
	* @return the new password policy
	*/
	public com.liferay.portal.model.PasswordPolicy create(long passwordPolicyId);

	/**
	* Removes the password policy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy that was removed
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy remove(long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy updateImpl(
		com.liferay.portal.model.PasswordPolicy passwordPolicy, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the password policy with the primary key or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy findByPrimaryKey(
		long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the password policy with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy, or <code>null</code> if a password policy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByPrimaryKey(
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy findByC_DP(long companyId,
		boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the password policy where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the password policies.
	*
	* @return the password policies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the password policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of password policies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the password policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of password policies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the password policy where companyId = &#63; and defaultPolicy = &#63; from the database.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the password policy that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy removeByC_DP(
		long companyId, boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the password policy where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the password policy that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordPolicy removeByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the password policies from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of password policies where companyId = &#63; and defaultPolicy = &#63;.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the number of matching password policies
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_DP(long companyId, boolean defaultPolicy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of password policies where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching password policies
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of password policies.
	*
	* @return the number of password policies
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}