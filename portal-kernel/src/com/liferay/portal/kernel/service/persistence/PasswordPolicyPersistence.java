/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchPasswordPolicyException;
import com.liferay.portal.kernel.model.PasswordPolicy;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the password policy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyUtil
 * @generated
 */
@ProviderType
public interface PasswordPolicyPersistence
	extends BasePersistence<PasswordPolicy> {

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
	public java.util.List<PasswordPolicy> findByUuid(String uuid);

	/**
	 * Returns a range of all the password policies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies
	 */
	public java.util.List<PasswordPolicy> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the password policies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies
	 */
	public java.util.List<PasswordPolicy> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns an ordered range of all the password policies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching password policies
	 */
	public java.util.List<PasswordPolicy> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first password policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy
	 * @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	public PasswordPolicy findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the first password policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns the last password policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy
	 * @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	public PasswordPolicy findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the last password policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns the password policies before and after the current password policy in the ordered set where uuid = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	public PasswordPolicy[] findByUuid_PrevAndNext(
			long passwordPolicyId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns all the password policies that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching password policies that the user has permission to view
	 */
	public java.util.List<PasswordPolicy> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the password policies that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies that the user has permission to view
	 */
	public java.util.List<PasswordPolicy> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the password policies that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies that the user has permission to view
	 */
	public java.util.List<PasswordPolicy> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where uuid = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	public PasswordPolicy[] filterFindByUuid_PrevAndNext(
			long passwordPolicyId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Removes all the password policies where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of password policies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching password policies
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of password policies that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching password policies that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the password policies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching password policies
	 */
	public java.util.List<PasswordPolicy> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the password policies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies
	 */
	public java.util.List<PasswordPolicy> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the password policies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies
	 */
	public java.util.List<PasswordPolicy> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns an ordered range of all the password policies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching password policies
	 */
	public java.util.List<PasswordPolicy> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy
	 * @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	public PasswordPolicy findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the first password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns the last password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy
	 * @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	public PasswordPolicy findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the last password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns the password policies before and after the current password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	public PasswordPolicy[] findByUuid_C_PrevAndNext(
			long passwordPolicyId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns all the password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching password policies that the user has permission to view
	 */
	public java.util.List<PasswordPolicy> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies that the user has permission to view
	 */
	public java.util.List<PasswordPolicy> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the password policies that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies that the user has permission to view
	 */
	public java.util.List<PasswordPolicy> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	public PasswordPolicy[] filterFindByUuid_C_PrevAndNext(
			long passwordPolicyId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Removes all the password policies where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of password policies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching password policies
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching password policies that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the password policies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching password policies
	 */
	public java.util.List<PasswordPolicy> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the password policies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies
	 */
	public java.util.List<PasswordPolicy> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the password policies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies
	 */
	public java.util.List<PasswordPolicy> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns an ordered range of all the password policies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching password policies
	 */
	public java.util.List<PasswordPolicy> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first password policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy
	 * @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	public PasswordPolicy findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the first password policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns the last password policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy
	 * @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	public PasswordPolicy findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the last password policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns the password policies before and after the current password policy in the ordered set where companyId = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	public PasswordPolicy[] findByCompanyId_PrevAndNext(
			long passwordPolicyId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns all the password policies that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching password policies that the user has permission to view
	 */
	public java.util.List<PasswordPolicy> filterFindByCompanyId(long companyId);

	/**
	 * Returns a range of all the password policies that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies that the user has permission to view
	 */
	public java.util.List<PasswordPolicy> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the password policies that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies that the user has permission to view
	 */
	public java.util.List<PasswordPolicy> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where companyId = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	public PasswordPolicy[] filterFindByCompanyId_PrevAndNext(
			long passwordPolicyId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
				orderByComparator)
		throws NoSuchPasswordPolicyException;

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
	 * Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or throws a <code>NoSuchPasswordPolicyException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the matching password policy
	 * @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	public PasswordPolicy findByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByC_DP(long companyId, boolean defaultPolicy);

	/**
	 * Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByC_DP(
		long companyId, boolean defaultPolicy, boolean useFinderCache);

	/**
	 * Removes the password policy where companyId = &#63; and defaultPolicy = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the password policy that was removed
	 */
	public PasswordPolicy removeByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the number of password policies where companyId = &#63; and defaultPolicy = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the number of matching password policies
	 */
	public int countByC_DP(long companyId, boolean defaultPolicy);

	/**
	 * Returns the password policy where companyId = &#63; and name = &#63; or throws a <code>NoSuchPasswordPolicyException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching password policy
	 * @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	public PasswordPolicy findByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByC_N(long companyId, String name);

	/**
	 * Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	public PasswordPolicy fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the password policy where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the password policy that was removed
	 */
	public PasswordPolicy removeByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the number of password policies where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching password policies
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Caches the password policy in the entity cache if it is enabled.
	 *
	 * @param passwordPolicy the password policy
	 */
	public void cacheResult(PasswordPolicy passwordPolicy);

	/**
	 * Caches the password policies in the entity cache if it is enabled.
	 *
	 * @param passwordPolicies the password policies
	 */
	public void cacheResult(java.util.List<PasswordPolicy> passwordPolicies);

	/**
	 * Creates a new password policy with the primary key. Does not add the password policy to the database.
	 *
	 * @param passwordPolicyId the primary key for the new password policy
	 * @return the new password policy
	 */
	public PasswordPolicy create(long passwordPolicyId);

	/**
	 * Removes the password policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @return the password policy that was removed
	 * @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	public PasswordPolicy remove(long passwordPolicyId)
		throws NoSuchPasswordPolicyException;

	public PasswordPolicy updateImpl(PasswordPolicy passwordPolicy);

	/**
	 * Returns the password policy with the primary key or throws a <code>NoSuchPasswordPolicyException</code> if it could not be found.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @return the password policy
	 * @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	public PasswordPolicy findByPrimaryKey(long passwordPolicyId)
		throws NoSuchPasswordPolicyException;

	/**
	 * Returns the password policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @return the password policy, or <code>null</code> if a password policy with the primary key could not be found
	 */
	public PasswordPolicy fetchByPrimaryKey(long passwordPolicyId);

	/**
	 * Returns all the password policies.
	 *
	 * @return the password policies
	 */
	public java.util.List<PasswordPolicy> findAll();

	/**
	 * Returns a range of all the password policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of password policies
	 */
	public java.util.List<PasswordPolicy> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the password policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of password policies
	 */
	public java.util.List<PasswordPolicy> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator);

	/**
	 * Returns an ordered range of all the password policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of password policies
	 */
	public java.util.List<PasswordPolicy> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordPolicy>
			orderByComparator,
		boolean useFinderCache);

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