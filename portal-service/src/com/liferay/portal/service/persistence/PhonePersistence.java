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

import com.liferay.portal.exception.NoSuchPhoneException;
import com.liferay.portal.model.Phone;

/**
 * The persistence interface for the phone service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.PhonePersistenceImpl
 * @see PhoneUtil
 * @generated
 */
@ProviderType
public interface PhonePersistence extends BasePersistence<Phone> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PhoneUtil} to access the phone persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the phones where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching phones
	*/
	public java.util.List<Phone> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the phones where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @return the range of matching phones
	*/
	public java.util.List<Phone> findByUuid(java.lang.String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the phones where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns an ordered range of all the phones where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first phone in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the first phone in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the last phone in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the last phone in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the phones before and after the current phone in the ordered set where uuid = &#63;.
	*
	* @param phoneId the primary key of the current phone
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next phone
	* @throws NoSuchPhoneException if a phone with the primary key could not be found
	*/
	public Phone[] findByUuid_PrevAndNext(long phoneId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Removes all the phones where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of phones where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching phones
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the phones where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching phones
	*/
	public java.util.List<Phone> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the phones where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @return the range of matching phones
	*/
	public java.util.List<Phone> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the phones where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns an ordered range of all the phones where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first phone in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the first phone in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the last phone in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the last phone in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the phones before and after the current phone in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param phoneId the primary key of the current phone
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next phone
	* @throws NoSuchPhoneException if a phone with the primary key could not be found
	*/
	public Phone[] findByUuid_C_PrevAndNext(long phoneId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Removes all the phones where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of phones where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching phones
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the phones where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching phones
	*/
	public java.util.List<Phone> findByCompanyId(long companyId);

	/**
	* Returns a range of all the phones where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @return the range of matching phones
	*/
	public java.util.List<Phone> findByCompanyId(long companyId, int start,
		int end);

	/**
	* Returns an ordered range of all the phones where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns an ordered range of all the phones where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first phone in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the first phone in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the last phone in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the last phone in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the phones before and after the current phone in the ordered set where companyId = &#63;.
	*
	* @param phoneId the primary key of the current phone
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next phone
	* @throws NoSuchPhoneException if a phone with the primary key could not be found
	*/
	public Phone[] findByCompanyId_PrevAndNext(long phoneId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Removes all the phones where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of phones where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching phones
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the phones where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching phones
	*/
	public java.util.List<Phone> findByUserId(long userId);

	/**
	* Returns a range of all the phones where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @return the range of matching phones
	*/
	public java.util.List<Phone> findByUserId(long userId, int start, int end);

	/**
	* Returns an ordered range of all the phones where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByUserId(long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns an ordered range of all the phones where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByUserId(long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first phone in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the first phone in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the last phone in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the last phone in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the phones before and after the current phone in the ordered set where userId = &#63;.
	*
	* @param phoneId the primary key of the current phone
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next phone
	* @throws NoSuchPhoneException if a phone with the primary key could not be found
	*/
	public Phone[] findByUserId_PrevAndNext(long phoneId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Removes all the phones where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of phones where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching phones
	*/
	public int countByUserId(long userId);

	/**
	* Returns all the phones where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching phones
	*/
	public java.util.List<Phone> findByC_C(long companyId, long classNameId);

	/**
	* Returns a range of all the phones where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @return the range of matching phones
	*/
	public java.util.List<Phone> findByC_C(long companyId, long classNameId,
		int start, int end);

	/**
	* Returns an ordered range of all the phones where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByC_C(long companyId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns an ordered range of all the phones where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByC_C(long companyId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first phone in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByC_C_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the first phone in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByC_C_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the last phone in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByC_C_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the last phone in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByC_C_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the phones before and after the current phone in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param phoneId the primary key of the current phone
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next phone
	* @throws NoSuchPhoneException if a phone with the primary key could not be found
	*/
	public Phone[] findByC_C_PrevAndNext(long phoneId, long companyId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Removes all the phones where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	*/
	public void removeByC_C(long companyId, long classNameId);

	/**
	* Returns the number of phones where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching phones
	*/
	public int countByC_C(long companyId, long classNameId);

	/**
	* Returns all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching phones
	*/
	public java.util.List<Phone> findByC_C_C(long companyId, long classNameId,
		long classPK);

	/**
	* Returns a range of all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @return the range of matching phones
	*/
	public java.util.List<Phone> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns an ordered range of all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByC_C_C_First(long companyId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the first phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByC_C_C_First(long companyId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the last phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByC_C_C_Last(long companyId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the last phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByC_C_C_Last(long companyId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the phones before and after the current phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param phoneId the primary key of the current phone
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next phone
	* @throws NoSuchPhoneException if a phone with the primary key could not be found
	*/
	public Phone[] findByC_C_C_PrevAndNext(long phoneId, long companyId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Removes all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C_C(long companyId, long classNameId, long classPK);

	/**
	* Returns the number of phones where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching phones
	*/
	public int countByC_C_C(long companyId, long classNameId, long classPK);

	/**
	* Returns all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @return the matching phones
	*/
	public java.util.List<Phone> findByC_C_C_P(long companyId,
		long classNameId, long classPK, boolean primary);

	/**
	* Returns a range of all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @return the range of matching phones
	*/
	public java.util.List<Phone> findByC_C_C_P(long companyId,
		long classNameId, long classPK, boolean primary, int start, int end);

	/**
	* Returns an ordered range of all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByC_C_C_P(long companyId,
		long classNameId, long classPK, boolean primary, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns an ordered range of all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching phones
	*/
	public java.util.List<Phone> findByC_C_C_P(long companyId,
		long classNameId, long classPK, boolean primary, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByC_C_C_P_First(long companyId, long classNameId,
		long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the first phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByC_C_C_P_First(long companyId, long classNameId,
		long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the last phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone
	* @throws NoSuchPhoneException if a matching phone could not be found
	*/
	public Phone findByC_C_C_P_Last(long companyId, long classNameId,
		long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Returns the last phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public Phone fetchByC_C_C_P_Last(long companyId, long classNameId,
		long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns the phones before and after the current phone in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* @param phoneId the primary key of the current phone
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next phone
	* @throws NoSuchPhoneException if a phone with the primary key could not be found
	*/
	public Phone[] findByC_C_C_P_PrevAndNext(long phoneId, long companyId,
		long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator)
		throws NoSuchPhoneException;

	/**
	* Removes all the phones where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	*/
	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary);

	/**
	* Returns the number of phones where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param primary the primary
	* @return the number of matching phones
	*/
	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary);

	/**
	* Caches the phone in the entity cache if it is enabled.
	*
	* @param phone the phone
	*/
	public void cacheResult(Phone phone);

	/**
	* Caches the phones in the entity cache if it is enabled.
	*
	* @param phones the phones
	*/
	public void cacheResult(java.util.List<Phone> phones);

	/**
	* Creates a new phone with the primary key. Does not add the phone to the database.
	*
	* @param phoneId the primary key for the new phone
	* @return the new phone
	*/
	public Phone create(long phoneId);

	/**
	* Removes the phone with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param phoneId the primary key of the phone
	* @return the phone that was removed
	* @throws NoSuchPhoneException if a phone with the primary key could not be found
	*/
	public Phone remove(long phoneId) throws NoSuchPhoneException;

	public Phone updateImpl(Phone phone);

	/**
	* Returns the phone with the primary key or throws a {@link NoSuchPhoneException} if it could not be found.
	*
	* @param phoneId the primary key of the phone
	* @return the phone
	* @throws NoSuchPhoneException if a phone with the primary key could not be found
	*/
	public Phone findByPrimaryKey(long phoneId) throws NoSuchPhoneException;

	/**
	* Returns the phone with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param phoneId the primary key of the phone
	* @return the phone, or <code>null</code> if a phone with the primary key could not be found
	*/
	public Phone fetchByPrimaryKey(long phoneId);

	@Override
	public java.util.Map<java.io.Serializable, Phone> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the phones.
	*
	* @return the phones
	*/
	public java.util.List<Phone> findAll();

	/**
	* Returns a range of all the phones.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @return the range of phones
	*/
	public java.util.List<Phone> findAll(int start, int end);

	/**
	* Returns an ordered range of all the phones.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of phones
	*/
	public java.util.List<Phone> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator);

	/**
	* Returns an ordered range of all the phones.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of phones
	*/
	public java.util.List<Phone> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Phone> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the phones from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of phones.
	*
	* @return the number of phones
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}