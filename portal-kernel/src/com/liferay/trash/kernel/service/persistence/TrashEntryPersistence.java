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

package com.liferay.trash.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.trash.kernel.exception.NoSuchEntryException;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.Date;

/**
 * The persistence interface for the trash entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.trash.service.persistence.impl.TrashEntryPersistenceImpl
 * @see TrashEntryUtil
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.trash.model.impl.TrashEntryImpl}
 * @generated
 */
@Deprecated
@ProviderType
public interface TrashEntryPersistence extends BasePersistence<TrashEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TrashEntryUtil} to access the trash entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the trash entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching trash entries
	*/
	public java.util.List<TrashEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the trash entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @return the range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the trash entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns an ordered range of all the trash entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first trash entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash entry
	* @throws NoSuchEntryException if a matching trash entry could not be found
	*/
	public TrashEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first trash entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns the last trash entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash entry
	* @throws NoSuchEntryException if a matching trash entry could not be found
	*/
	public TrashEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last trash entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns the trash entries before and after the current trash entry in the ordered set where groupId = &#63;.
	*
	* @param entryId the primary key of the current trash entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash entry
	* @throws NoSuchEntryException if a trash entry with the primary key could not be found
	*/
	public TrashEntry[] findByGroupId_PrevAndNext(long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the trash entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of trash entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching trash entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the trash entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching trash entries
	*/
	public java.util.List<TrashEntry> findByCompanyId(long companyId);

	/**
	* Returns a range of all the trash entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @return the range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the trash entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns an ordered range of all the trash entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first trash entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash entry
	* @throws NoSuchEntryException if a matching trash entry could not be found
	*/
	public TrashEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first trash entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns the last trash entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash entry
	* @throws NoSuchEntryException if a matching trash entry could not be found
	*/
	public TrashEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last trash entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns the trash entries before and after the current trash entry in the ordered set where companyId = &#63;.
	*
	* @param entryId the primary key of the current trash entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash entry
	* @throws NoSuchEntryException if a trash entry with the primary key could not be found
	*/
	public TrashEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the trash entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of trash entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching trash entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the trash entries where groupId = &#63; and createDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param createDate the create date
	* @return the matching trash entries
	*/
	public java.util.List<TrashEntry> findByG_LtCD(long groupId, Date createDate);

	/**
	* Returns a range of all the trash entries where groupId = &#63; and createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param createDate the create date
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @return the range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByG_LtCD(long groupId,
		Date createDate, int start, int end);

	/**
	* Returns an ordered range of all the trash entries where groupId = &#63; and createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param createDate the create date
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByG_LtCD(long groupId,
		Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns an ordered range of all the trash entries where groupId = &#63; and createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param createDate the create date
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByG_LtCD(long groupId,
		Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first trash entry in the ordered set where groupId = &#63; and createDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash entry
	* @throws NoSuchEntryException if a matching trash entry could not be found
	*/
	public TrashEntry findByG_LtCD_First(long groupId, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first trash entry in the ordered set where groupId = &#63; and createDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByG_LtCD_First(long groupId, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns the last trash entry in the ordered set where groupId = &#63; and createDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash entry
	* @throws NoSuchEntryException if a matching trash entry could not be found
	*/
	public TrashEntry findByG_LtCD_Last(long groupId, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last trash entry in the ordered set where groupId = &#63; and createDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByG_LtCD_Last(long groupId, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns the trash entries before and after the current trash entry in the ordered set where groupId = &#63; and createDate &lt; &#63;.
	*
	* @param entryId the primary key of the current trash entry
	* @param groupId the group ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash entry
	* @throws NoSuchEntryException if a trash entry with the primary key could not be found
	*/
	public TrashEntry[] findByG_LtCD_PrevAndNext(long entryId, long groupId,
		Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the trash entries where groupId = &#63; and createDate &lt; &#63; from the database.
	*
	* @param groupId the group ID
	* @param createDate the create date
	*/
	public void removeByG_LtCD(long groupId, Date createDate);

	/**
	* Returns the number of trash entries where groupId = &#63; and createDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param createDate the create date
	* @return the number of matching trash entries
	*/
	public int countByG_LtCD(long groupId, Date createDate);

	/**
	* Returns all the trash entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching trash entries
	*/
	public java.util.List<TrashEntry> findByG_C(long groupId, long classNameId);

	/**
	* Returns a range of all the trash entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @return the range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByG_C(long groupId, long classNameId,
		int start, int end);

	/**
	* Returns an ordered range of all the trash entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByG_C(long groupId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns an ordered range of all the trash entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching trash entries
	*/
	public java.util.List<TrashEntry> findByG_C(long groupId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first trash entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash entry
	* @throws NoSuchEntryException if a matching trash entry could not be found
	*/
	public TrashEntry findByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first trash entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns the last trash entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash entry
	* @throws NoSuchEntryException if a matching trash entry could not be found
	*/
	public TrashEntry findByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last trash entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns the trash entries before and after the current trash entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param entryId the primary key of the current trash entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash entry
	* @throws NoSuchEntryException if a trash entry with the primary key could not be found
	*/
	public TrashEntry[] findByG_C_PrevAndNext(long entryId, long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the trash entries where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public void removeByG_C(long groupId, long classNameId);

	/**
	* Returns the number of trash entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching trash entries
	*/
	public int countByG_C(long groupId, long classNameId);

	/**
	* Returns the trash entry where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching trash entry
	* @throws NoSuchEntryException if a matching trash entry could not be found
	*/
	public TrashEntry findByC_C(long classNameId, long classPK)
		throws NoSuchEntryException;

	/**
	* Returns the trash entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByC_C(long classNameId, long classPK);

	/**
	* Returns the trash entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching trash entry, or <code>null</code> if a matching trash entry could not be found
	*/
	public TrashEntry fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the trash entry where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the trash entry that was removed
	*/
	public TrashEntry removeByC_C(long classNameId, long classPK)
		throws NoSuchEntryException;

	/**
	* Returns the number of trash entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching trash entries
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Caches the trash entry in the entity cache if it is enabled.
	*
	* @param trashEntry the trash entry
	*/
	public void cacheResult(TrashEntry trashEntry);

	/**
	* Caches the trash entries in the entity cache if it is enabled.
	*
	* @param trashEntries the trash entries
	*/
	public void cacheResult(java.util.List<TrashEntry> trashEntries);

	/**
	* Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	*
	* @param entryId the primary key for the new trash entry
	* @return the new trash entry
	*/
	public TrashEntry create(long entryId);

	/**
	* Removes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry that was removed
	* @throws NoSuchEntryException if a trash entry with the primary key could not be found
	*/
	public TrashEntry remove(long entryId) throws NoSuchEntryException;

	public TrashEntry updateImpl(TrashEntry trashEntry);

	/**
	* Returns the trash entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry
	* @throws NoSuchEntryException if a trash entry with the primary key could not be found
	*/
	public TrashEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException;

	/**
	* Returns the trash entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry, or <code>null</code> if a trash entry with the primary key could not be found
	*/
	public TrashEntry fetchByPrimaryKey(long entryId);

	@Override
	public java.util.Map<java.io.Serializable, TrashEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the trash entries.
	*
	* @return the trash entries
	*/
	public java.util.List<TrashEntry> findAll();

	/**
	* Returns a range of all the trash entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @return the range of trash entries
	*/
	public java.util.List<TrashEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the trash entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of trash entries
	*/
	public java.util.List<TrashEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator);

	/**
	* Returns an ordered range of all the trash entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of trash entries
	*/
	public java.util.List<TrashEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the trash entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of trash entries.
	*
	* @return the number of trash entries
	*/
	public int countAll();
}