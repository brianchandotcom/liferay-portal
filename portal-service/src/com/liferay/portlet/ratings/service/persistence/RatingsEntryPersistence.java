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

package com.liferay.portlet.ratings.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.ratings.exception.NoSuchEntryException;
import com.liferay.portlet.ratings.model.RatingsEntry;

/**
 * The persistence interface for the ratings entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.ratings.service.persistence.impl.RatingsEntryPersistenceImpl
 * @see RatingsEntryUtil
 * @generated
 */
@ProviderType
public interface RatingsEntryPersistence extends BasePersistence<RatingsEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RatingsEntryUtil} to access the ratings entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the ratings entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the ratings entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the ratings entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the ratings entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ratings entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry
	* @throws NoSuchEntryException if a matching ratings entry could not be found
	*/
	public RatingsEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first ratings entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns the last ratings entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry
	* @throws NoSuchEntryException if a matching ratings entry could not be found
	*/
	public RatingsEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last ratings entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns the ratings entries before and after the current ratings entry in the ordered set where uuid = &#63;.
	*
	* @param entryId the primary key of the current ratings entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ratings entry
	* @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	*/
	public RatingsEntry[] findByUuid_PrevAndNext(long entryId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the ratings entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of ratings entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching ratings entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the ratings entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the ratings entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the ratings entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the ratings entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry
	* @throws NoSuchEntryException if a matching ratings entry could not be found
	*/
	public RatingsEntry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns the last ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry
	* @throws NoSuchEntryException if a matching ratings entry could not be found
	*/
	public RatingsEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns the ratings entries before and after the current ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param entryId the primary key of the current ratings entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ratings entry
	* @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	*/
	public RatingsEntry[] findByUuid_C_PrevAndNext(long entryId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the ratings entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of ratings entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching ratings entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByC_C(long classNameId, long classPK);

	/**
	* Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry
	* @throws NoSuchEntryException if a matching ratings entry could not be found
	*/
	public RatingsEntry findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry
	* @throws NoSuchEntryException if a matching ratings entry could not be found
	*/
	public RatingsEntry findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param entryId the primary key of the current ratings entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ratings entry
	* @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	*/
	public RatingsEntry[] findByC_C_PrevAndNext(long entryId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the ratings entries where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of ratings entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching ratings entries
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings entry
	* @throws NoSuchEntryException if a matching ratings entry could not be found
	*/
	public RatingsEntry findByU_C_C(long userId, long classNameId, long classPK)
		throws NoSuchEntryException;

	/**
	* Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByU_C_C(long userId, long classNameId, long classPK);

	/**
	* Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByU_C_C(long userId, long classNameId,
		long classPK, boolean retrieveFromCache);

	/**
	* Removes the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the ratings entry that was removed
	*/
	public RatingsEntry removeByU_C_C(long userId, long classNameId,
		long classPK) throws NoSuchEntryException;

	/**
	* Returns the number of ratings entries where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching ratings entries
	*/
	public int countByU_C_C(long userId, long classNameId, long classPK);

	/**
	* Returns all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @return the matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByC_C_S(long classNameId,
		long classPK, double score);

	/**
	* Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByC_C_S(long classNameId,
		long classPK, double score, int start, int end);

	/**
	* Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByC_C_S(long classNameId,
		long classPK, double score, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ratings entries
	*/
	public java.util.List<RatingsEntry> findByC_C_S(long classNameId,
		long classPK, double score, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry
	* @throws NoSuchEntryException if a matching ratings entry could not be found
	*/
	public RatingsEntry findByC_C_S_First(long classNameId, long classPK,
		double score,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByC_C_S_First(long classNameId, long classPK,
		double score,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry
	* @throws NoSuchEntryException if a matching ratings entry could not be found
	*/
	public RatingsEntry findByC_C_S_Last(long classNameId, long classPK,
		double score,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	*/
	public RatingsEntry fetchByC_C_S_Last(long classNameId, long classPK,
		double score,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param entryId the primary key of the current ratings entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ratings entry
	* @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	*/
	public RatingsEntry[] findByC_C_S_PrevAndNext(long entryId,
		long classNameId, long classPK, double score,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	*/
	public void removeByC_C_S(long classNameId, long classPK, double score);

	/**
	* Returns the number of ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param score the score
	* @return the number of matching ratings entries
	*/
	public int countByC_C_S(long classNameId, long classPK, double score);

	/**
	* Caches the ratings entry in the entity cache if it is enabled.
	*
	* @param ratingsEntry the ratings entry
	*/
	public void cacheResult(RatingsEntry ratingsEntry);

	/**
	* Caches the ratings entries in the entity cache if it is enabled.
	*
	* @param ratingsEntries the ratings entries
	*/
	public void cacheResult(java.util.List<RatingsEntry> ratingsEntries);

	/**
	* Creates a new ratings entry with the primary key. Does not add the ratings entry to the database.
	*
	* @param entryId the primary key for the new ratings entry
	* @return the new ratings entry
	*/
	public RatingsEntry create(long entryId);

	/**
	* Removes the ratings entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry that was removed
	* @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	*/
	public RatingsEntry remove(long entryId) throws NoSuchEntryException;

	public RatingsEntry updateImpl(RatingsEntry ratingsEntry);

	/**
	* Returns the ratings entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry
	* @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	*/
	public RatingsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException;

	/**
	* Returns the ratings entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry, or <code>null</code> if a ratings entry with the primary key could not be found
	*/
	public RatingsEntry fetchByPrimaryKey(long entryId);

	@Override
	public java.util.Map<java.io.Serializable, RatingsEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the ratings entries.
	*
	* @return the ratings entries
	*/
	public java.util.List<RatingsEntry> findAll();

	/**
	* Returns a range of all the ratings entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of ratings entries
	*/
	public java.util.List<RatingsEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the ratings entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ratings entries
	*/
	public java.util.List<RatingsEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the ratings entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of ratings entries
	*/
	public java.util.List<RatingsEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the ratings entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of ratings entries.
	*
	* @return the number of ratings entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}