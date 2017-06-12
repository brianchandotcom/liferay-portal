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

import com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException;
import com.liferay.commerce.product.model.CPTemplateLayoutEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp template layout entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPTemplateLayoutEntryPersistenceImpl
 * @see CPTemplateLayoutEntryUtil
 * @generated
 */
@ProviderType
public interface CPTemplateLayoutEntryPersistence extends BasePersistence<CPTemplateLayoutEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPTemplateLayoutEntryUtil} to access the cp template layout entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp template layout entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the cp template layout entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @return the range of matching cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the cp template layout entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator);

	/**
	* Returns an ordered range of all the cp template layout entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Returns the first cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator);

	/**
	* Returns the last cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Returns the last cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator);

	/**
	* Returns the cp template layout entries before and after the current cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param CPFriendlyUrlEntryId the primary key of the current cp template layout entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	*/
	public CPTemplateLayoutEntry[] findByUuid_PrevAndNext(
		long CPFriendlyUrlEntryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Removes all the cp template layout entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp template layout entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp template layout entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp template layout entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPTemplateLayoutEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry findByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Returns the cp template layout entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the cp template layout entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the cp template layout entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp template layout entry that was removed
	*/
	public CPTemplateLayoutEntry removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Returns the number of cp template layout entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp template layout entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @return the range of matching cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator);

	/**
	* Returns an ordered range of all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Returns the first cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator);

	/**
	* Returns the last cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Returns the last cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator);

	/**
	* Returns the cp template layout entries before and after the current cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPFriendlyUrlEntryId the primary key of the current cp template layout entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	*/
	public CPTemplateLayoutEntry[] findByUuid_C_PrevAndNext(
		long CPFriendlyUrlEntryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Removes all the cp template layout entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp template layout entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchCPTemplateLayoutEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry findByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Returns the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry fetchByG_C_C(long groupId, long classNameId,
		long classPK);

	/**
	* Returns the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public CPTemplateLayoutEntry fetchByG_C_C(long groupId, long classNameId,
		long classPK, boolean retrieveFromCache);

	/**
	* Removes the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the cp template layout entry that was removed
	*/
	public CPTemplateLayoutEntry removeByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Returns the number of cp template layout entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching cp template layout entries
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Caches the cp template layout entry in the entity cache if it is enabled.
	*
	* @param cpTemplateLayoutEntry the cp template layout entry
	*/
	public void cacheResult(CPTemplateLayoutEntry cpTemplateLayoutEntry);

	/**
	* Caches the cp template layout entries in the entity cache if it is enabled.
	*
	* @param cpTemplateLayoutEntries the cp template layout entries
	*/
	public void cacheResult(
		java.util.List<CPTemplateLayoutEntry> cpTemplateLayoutEntries);

	/**
	* Creates a new cp template layout entry with the primary key. Does not add the cp template layout entry to the database.
	*
	* @param CPFriendlyUrlEntryId the primary key for the new cp template layout entry
	* @return the new cp template layout entry
	*/
	public CPTemplateLayoutEntry create(long CPFriendlyUrlEntryId);

	/**
	* Removes the cp template layout entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPFriendlyUrlEntryId the primary key of the cp template layout entry
	* @return the cp template layout entry that was removed
	* @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	*/
	public CPTemplateLayoutEntry remove(long CPFriendlyUrlEntryId)
		throws NoSuchCPTemplateLayoutEntryException;

	public CPTemplateLayoutEntry updateImpl(
		CPTemplateLayoutEntry cpTemplateLayoutEntry);

	/**
	* Returns the cp template layout entry with the primary key or throws a {@link NoSuchCPTemplateLayoutEntryException} if it could not be found.
	*
	* @param CPFriendlyUrlEntryId the primary key of the cp template layout entry
	* @return the cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	*/
	public CPTemplateLayoutEntry findByPrimaryKey(long CPFriendlyUrlEntryId)
		throws NoSuchCPTemplateLayoutEntryException;

	/**
	* Returns the cp template layout entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPFriendlyUrlEntryId the primary key of the cp template layout entry
	* @return the cp template layout entry, or <code>null</code> if a cp template layout entry with the primary key could not be found
	*/
	public CPTemplateLayoutEntry fetchByPrimaryKey(long CPFriendlyUrlEntryId);

	@Override
	public java.util.Map<java.io.Serializable, CPTemplateLayoutEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp template layout entries.
	*
	* @return the cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findAll();

	/**
	* Returns a range of all the cp template layout entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @return the range of cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp template layout entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator);

	/**
	* Returns an ordered range of all the cp template layout entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp template layout entries
	*/
	public java.util.List<CPTemplateLayoutEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp template layout entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp template layout entries.
	*
	* @return the number of cp template layout entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}