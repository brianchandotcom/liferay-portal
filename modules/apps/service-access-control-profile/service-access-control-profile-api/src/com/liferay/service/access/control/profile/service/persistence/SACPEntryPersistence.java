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

package com.liferay.service.access.control.profile.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.service.access.control.profile.model.SACPEntry;

/**
 * The persistence interface for the s a c p entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.service.access.control.profile.service.persistence.impl.SACPEntryPersistenceImpl
 * @see SACPEntryUtil
 * @generated
 */
@ProviderType
public interface SACPEntryPersistence extends BasePersistence<SACPEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SACPEntryUtil} to access the s a c p entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the s a c p entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching s a c p entries
	*/
	public java.util.List<SACPEntry> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the s a c p entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries
	*/
	public java.util.List<SACPEntry> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the s a c p entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries
	*/
	public java.util.List<SACPEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the first s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public SACPEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns the first s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public SACPEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the last s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public SACPEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns the last s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public SACPEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public SACPEntry[] findByUuid_PrevAndNext(long sacpEntryId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns all the s a c p entries that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching s a c p entries that the user has permission to view
	*/
	public java.util.List<SACPEntry> filterFindByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the s a c p entries that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries that the user has permission to view
	*/
	public java.util.List<SACPEntry> filterFindByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the s a c p entries that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries that the user has permission to view
	*/
	public java.util.List<SACPEntry> filterFindByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set of s a c p entries that the user has permission to view where uuid = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public SACPEntry[] filterFindByUuid_PrevAndNext(long sacpEntryId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Removes all the s a c p entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of s a c p entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching s a c p entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the number of s a c p entries that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching s a c p entries that the user has permission to view
	*/
	public int filterCountByUuid(java.lang.String uuid);

	/**
	* Returns all the s a c p entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching s a c p entries
	*/
	public java.util.List<SACPEntry> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the s a c p entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries
	*/
	public java.util.List<SACPEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the s a c p entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries
	*/
	public java.util.List<SACPEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the first s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public SACPEntry findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns the first s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public SACPEntry fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the last s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public SACPEntry findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns the last s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public SACPEntry fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public SACPEntry[] findByUuid_C_PrevAndNext(long sacpEntryId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns all the s a c p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching s a c p entries that the user has permission to view
	*/
	public java.util.List<SACPEntry> filterFindByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the s a c p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries that the user has permission to view
	*/
	public java.util.List<SACPEntry> filterFindByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the s a c p entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries that the user has permission to view
	*/
	public java.util.List<SACPEntry> filterFindByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set of s a c p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public SACPEntry[] filterFindByUuid_C_PrevAndNext(long sacpEntryId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Removes all the s a c p entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of s a c p entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching s a c p entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of s a c p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching s a c p entries that the user has permission to view
	*/
	public int filterCountByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the s a c p entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s a c p entries
	*/
	public java.util.List<SACPEntry> findByCompanyId(long companyId);

	/**
	* Returns a range of all the s a c p entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries
	*/
	public java.util.List<SACPEntry> findByCompanyId(long companyId, int start,
		int end);

	/**
	* Returns an ordered range of all the s a c p entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries
	*/
	public java.util.List<SACPEntry> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the first s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public SACPEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns the first s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public SACPEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the last s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public SACPEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns the last s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public SACPEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public SACPEntry[] findByCompanyId_PrevAndNext(long sacpEntryId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns all the s a c p entries that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s a c p entries that the user has permission to view
	*/
	public java.util.List<SACPEntry> filterFindByCompanyId(long companyId);

	/**
	* Returns a range of all the s a c p entries that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries that the user has permission to view
	*/
	public java.util.List<SACPEntry> filterFindByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the s a c p entries that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries that the user has permission to view
	*/
	public java.util.List<SACPEntry> filterFindByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set of s a c p entries that the user has permission to view where companyId = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public SACPEntry[] filterFindByCompanyId_PrevAndNext(long sacpEntryId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Removes all the s a c p entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of s a c p entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s a c p entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the number of s a c p entries that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s a c p entries that the user has permission to view
	*/
	public int filterCountByCompanyId(long companyId);

	/**
	* Returns the s a c p entry where companyId = &#63; and name = &#63; or throws a {@link com.liferay.service.access.control.profile.NoSuchEntryException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public SACPEntry findByC_N(long companyId, java.lang.String name)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns the s a c p entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public SACPEntry fetchByC_N(long companyId, java.lang.String name);

	/**
	* Returns the s a c p entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public SACPEntry fetchByC_N(long companyId, java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the s a c p entry where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the s a c p entry that was removed
	*/
	public SACPEntry removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns the number of s a c p entries where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching s a c p entries
	*/
	public int countByC_N(long companyId, java.lang.String name);

	/**
	* Caches the s a c p entry in the entity cache if it is enabled.
	*
	* @param sacpEntry the s a c p entry
	*/
	public void cacheResult(SACPEntry sacpEntry);

	/**
	* Caches the s a c p entries in the entity cache if it is enabled.
	*
	* @param sacpEntries the s a c p entries
	*/
	public void cacheResult(java.util.List<SACPEntry> sacpEntries);

	/**
	* Creates a new s a c p entry with the primary key. Does not add the s a c p entry to the database.
	*
	* @param sacpEntryId the primary key for the new s a c p entry
	* @return the new s a c p entry
	*/
	public SACPEntry create(long sacpEntryId);

	/**
	* Removes the s a c p entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry that was removed
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public SACPEntry remove(long sacpEntryId)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	public SACPEntry updateImpl(SACPEntry sacpEntry);

	/**
	* Returns the s a c p entry with the primary key or throws a {@link com.liferay.service.access.control.profile.NoSuchEntryException} if it could not be found.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public SACPEntry findByPrimaryKey(long sacpEntryId)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException;

	/**
	* Returns the s a c p entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry, or <code>null</code> if a s a c p entry with the primary key could not be found
	*/
	public SACPEntry fetchByPrimaryKey(long sacpEntryId);

	@Override
	public java.util.Map<java.io.Serializable, SACPEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the s a c p entries.
	*
	* @return the s a c p entries
	*/
	public java.util.List<SACPEntry> findAll();

	/**
	* Returns a range of all the s a c p entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of s a c p entries
	*/
	public java.util.List<SACPEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the s a c p entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s a c p entries
	*/
	public java.util.List<SACPEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SACPEntry> orderByComparator);

	/**
	* Removes all the s a c p entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of s a c p entries.
	*
	* @return the number of s a c p entries
	*/
	public int countAll();
}