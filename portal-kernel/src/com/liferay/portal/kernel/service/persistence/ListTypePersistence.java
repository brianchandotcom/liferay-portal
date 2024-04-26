/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.model.ListType;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the list type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ListTypeUtil
 * @generated
 */
@ProviderType
public interface ListTypePersistence extends BasePersistence<ListType> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ListTypeUtil} to access the list type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the list types where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching list types
	 */
	public java.util.List<ListType> findByUuid(String uuid);

	/**
	 * Returns a range of all the list types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of matching list types
	 */
	public java.util.List<ListType> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the list types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list types
	 */
	public java.util.List<ListType> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list types
	 */
	public java.util.List<ListType> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public ListType findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Returns the first list type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns the last list type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public ListType findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Returns the last list type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns the list types before and after the current list type in the ordered set where uuid = &#63;.
	 *
	 * @param listTypeId the primary key of the current list type
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public ListType[] findByUuid_PrevAndNext(
			long listTypeId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Removes all the list types where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of list types where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching list types
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the list types where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching list types
	 */
	public java.util.List<ListType> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the list types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of matching list types
	 */
	public java.util.List<ListType> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the list types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list types
	 */
	public java.util.List<ListType> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list types
	 */
	public java.util.List<ListType> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public ListType findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Returns the first list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns the last list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public ListType findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Returns the last list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns the list types before and after the current list type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listTypeId the primary key of the current list type
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public ListType[] findByUuid_C_PrevAndNext(
			long listTypeId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Removes all the list types where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of list types where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching list types
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the list types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching list types
	 */
	public java.util.List<ListType> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the list types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of matching list types
	 */
	public java.util.List<ListType> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the list types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list types
	 */
	public java.util.List<ListType> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list types
	 */
	public java.util.List<ListType> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public ListType findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Returns the first list type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns the last list type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public ListType findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Returns the last list type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns the list types before and after the current list type in the ordered set where companyId = &#63;.
	 *
	 * @param listTypeId the primary key of the current list type
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public ListType[] findByCompanyId_PrevAndNext(
			long listTypeId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Removes all the list types where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of list types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching list types
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the list types where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching list types
	 */
	public java.util.List<ListType> findByC_T(long companyId, String type);

	/**
	 * Returns a range of all the list types where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of matching list types
	 */
	public java.util.List<ListType> findByC_T(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the list types where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list types
	 */
	public java.util.List<ListType> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list types where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list types
	 */
	public java.util.List<ListType> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public ListType findByC_T_First(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Returns the first list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByC_T_First(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns the last list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public ListType findByC_T_Last(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Returns the last list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByC_T_Last(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns the list types before and after the current list type in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param listTypeId the primary key of the current list type
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public ListType[] findByC_T_PrevAndNext(
			long listTypeId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<ListType>
				orderByComparator)
		throws NoSuchListTypeException;

	/**
	 * Removes all the list types where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, String type);

	/**
	 * Returns the number of list types where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching list types
	 */
	public int countByC_T(long companyId, String type);

	/**
	 * Returns the list type where companyId = &#63; and name = &#63; and type = &#63; or throws a <code>NoSuchListTypeException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @return the matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	public ListType findByC_N_T(long companyId, String name, String type)
		throws NoSuchListTypeException;

	/**
	 * Returns the list type where companyId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @return the matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByC_N_T(long companyId, String name, String type);

	/**
	 * Returns the list type where companyId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching list type, or <code>null</code> if a matching list type could not be found
	 */
	public ListType fetchByC_N_T(
		long companyId, String name, String type, boolean useFinderCache);

	/**
	 * Removes the list type where companyId = &#63; and name = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @return the list type that was removed
	 */
	public ListType removeByC_N_T(long companyId, String name, String type)
		throws NoSuchListTypeException;

	/**
	 * Returns the number of list types where companyId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching list types
	 */
	public int countByC_N_T(long companyId, String name, String type);

	/**
	 * Caches the list type in the entity cache if it is enabled.
	 *
	 * @param listType the list type
	 */
	public void cacheResult(ListType listType);

	/**
	 * Caches the list types in the entity cache if it is enabled.
	 *
	 * @param listTypes the list types
	 */
	public void cacheResult(java.util.List<ListType> listTypes);

	/**
	 * Creates a new list type with the primary key. Does not add the list type to the database.
	 *
	 * @param listTypeId the primary key for the new list type
	 * @return the new list type
	 */
	public ListType create(long listTypeId);

	/**
	 * Removes the list type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type that was removed
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public ListType remove(long listTypeId) throws NoSuchListTypeException;

	public ListType updateImpl(ListType listType);

	/**
	 * Returns the list type with the primary key or throws a <code>NoSuchListTypeException</code> if it could not be found.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	public ListType findByPrimaryKey(long listTypeId)
		throws NoSuchListTypeException;

	/**
	 * Returns the list type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type, or <code>null</code> if a list type with the primary key could not be found
	 */
	public ListType fetchByPrimaryKey(long listTypeId);

	/**
	 * Returns all the list types.
	 *
	 * @return the list types
	 */
	public java.util.List<ListType> findAll();

	/**
	 * Returns a range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of list types
	 */
	public java.util.List<ListType> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of list types
	 */
	public java.util.List<ListType> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of list types
	 */
	public java.util.List<ListType> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the list types from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of list types.
	 *
	 * @return the number of list types
	 */
	public int countAll();

}