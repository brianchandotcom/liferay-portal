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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public interface PasswordPolicyPersistence extends BasePersistence<PasswordPolicy> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PasswordPolicyUtil} to access the password policy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the password policies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the password policies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the password policies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first password policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the first password policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last password policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the last password policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the password policies before and after the current password policy in the ordered set where uuid = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy[] findByUuid_PrevAndNext(
		long passwordPolicyId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns all the password policies that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching password policies that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> filterFindByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the password policies that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> filterFindByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the password policies that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> filterFindByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where uuid = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy[] filterFindByUuid_PrevAndNext(
		long passwordPolicyId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Removes all the password policies where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of password policies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching password policies
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the number of password policies that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching password policies that the user has permission to view
	*/
	public int filterCountByUuid(java.lang.String uuid);

	/**
	* Returns all the password policies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the password policies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the password policies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the first password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the last password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the password policies before and after the current password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy[] findByUuid_C_PrevAndNext(
		long passwordPolicyId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns all the password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching password policies that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the password policies that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy[] filterFindByUuid_C_PrevAndNext(
		long passwordPolicyId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Removes all the password policies where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of password policies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching password policies
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching password policies that the user has permission to view
	*/
	public int filterCountByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the password policies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the password policies where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the password policies where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first password policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the first password policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last password policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the last password policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the password policies before and after the current password policy in the ordered set where companyId = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy[] findByCompanyId_PrevAndNext(
		long passwordPolicyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns all the password policies that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching password policies that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> filterFindByCompanyId(
		long companyId);

	/**
	* Returns a range of all the password policies that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the password policies that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where companyId = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy[] filterFindByCompanyId_PrevAndNext(
		long passwordPolicyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Removes all the password policies where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of password policies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching password policies
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the number of password policies that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching password policies that the user has permission to view
	*/
	public int filterCountByCompanyId(long companyId);

	/**
	* Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy findByC_DP(long companyId,
		boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy);

	/**
	* Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy, boolean retrieveFromCache);

	/**
	* Removes the password policy where companyId = &#63; and defaultPolicy = &#63; from the database.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the password policy that was removed
	*/
	public com.liferay.portal.model.PasswordPolicy removeByC_DP(
		long companyId, boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the number of password policies where companyId = &#63; and defaultPolicy = &#63;.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the number of matching password policies
	*/
	public int countByC_DP(long companyId, boolean defaultPolicy);

	/**
	* Returns the password policy where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name);

	/**
	* Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the password policy where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the password policy that was removed
	*/
	public com.liferay.portal.model.PasswordPolicy removeByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the number of password policies where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching password policies
	*/
	public int countByC_N(long companyId, java.lang.String name);

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
	*/
	public com.liferay.portal.model.PasswordPolicy remove(long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	public com.liferay.portal.model.PasswordPolicy updateImpl(
		com.liferay.portal.model.PasswordPolicy passwordPolicy);

	/**
	* Returns the password policy with the primary key or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy
	* @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy findByPrimaryKey(
		long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException;

	/**
	* Returns the password policy with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy, or <code>null</code> if a password policy with the primary key could not be found
	*/
	public com.liferay.portal.model.PasswordPolicy fetchByPrimaryKey(
		long passwordPolicyId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.PasswordPolicy> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the password policies.
	*
	* @return the password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll();

	/**
	* Returns a range of all the password policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the password policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of password policies
	*/
	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the password policies from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of password policies.
	*
	* @return the number of password policies
	*/
	public int countAll();
}