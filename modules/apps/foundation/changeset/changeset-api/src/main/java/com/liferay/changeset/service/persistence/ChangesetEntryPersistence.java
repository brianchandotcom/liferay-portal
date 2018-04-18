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

package com.liferay.changeset.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.exception.NoSuchEntryException;
import com.liferay.changeset.model.ChangesetEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the changeset entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.changeset.service.persistence.impl.ChangesetEntryPersistenceImpl
 * @see ChangesetEntryUtil
 * @generated
 */
@ProviderType
public interface ChangesetEntryPersistence extends BasePersistence<ChangesetEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangesetEntryUtil} to access the changeset entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the changeset entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the changeset entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @return the range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the changeset entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the changeset entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first changeset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first changeset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the last changeset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last changeset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the changeset entries before and after the current changeset entry in the ordered set where groupId = &#63;.
	*
	* @param entryId the primary key of the current changeset entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset entry
	* @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	*/
	public ChangesetEntry[] findByGroupId_PrevAndNext(long entryId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the changeset entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of changeset entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching changeset entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the changeset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByCompanyId(long companyId);

	/**
	* Returns a range of all the changeset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @return the range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the changeset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the changeset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first changeset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first changeset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the last changeset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last changeset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the changeset entries before and after the current changeset entry in the ordered set where companyId = &#63;.
	*
	* @param entryId the primary key of the current changeset entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset entry
	* @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	*/
	public ChangesetEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the changeset entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of changeset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching changeset entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the changeset entries where changesetId = &#63;.
	*
	* @param changesetId the changeset ID
	* @return the matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByChangesetId(long changesetId);

	/**
	* Returns a range of all the changeset entries where changesetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param changesetId the changeset ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @return the range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByChangesetId(long changesetId,
		int start, int end);

	/**
	* Returns an ordered range of all the changeset entries where changesetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param changesetId the changeset ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByChangesetId(long changesetId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the changeset entries where changesetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param changesetId the changeset ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByChangesetId(long changesetId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first changeset entry in the ordered set where changesetId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByChangesetId_First(long changesetId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first changeset entry in the ordered set where changesetId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByChangesetId_First(long changesetId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the last changeset entry in the ordered set where changesetId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByChangesetId_Last(long changesetId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last changeset entry in the ordered set where changesetId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByChangesetId_Last(long changesetId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the changeset entries before and after the current changeset entry in the ordered set where changesetId = &#63;.
	*
	* @param entryId the primary key of the current changeset entry
	* @param changesetId the changeset ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset entry
	* @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	*/
	public ChangesetEntry[] findByChangesetId_PrevAndNext(long entryId,
		long changesetId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the changeset entries where changesetId = &#63; from the database.
	*
	* @param changesetId the changeset ID
	*/
	public void removeByChangesetId(long changesetId);

	/**
	* Returns the number of changeset entries where changesetId = &#63;.
	*
	* @param changesetId the changeset ID
	* @return the number of matching changeset entries
	*/
	public int countByChangesetId(long changesetId);

	/**
	* Returns all the changeset entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByG_C(long groupId,
		long classNameId);

	/**
	* Returns a range of all the changeset entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @return the range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByG_C(long groupId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the changeset entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the changeset entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the last changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the changeset entries before and after the current changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param entryId the primary key of the current changeset entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset entry
	* @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	*/
	public ChangesetEntry[] findByG_C_PrevAndNext(long entryId, long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the changeset entries where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public void removeByG_C(long groupId, long classNameId);

	/**
	* Returns the number of changeset entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching changeset entries
	*/
	public int countByG_C(long groupId, long classNameId);

	/**
	* Returns all the changeset entries where changesetId = &#63; and classNameId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @return the matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByC_C(long changesetId,
		long classNameId);

	/**
	* Returns a range of all the changeset entries where changesetId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @return the range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByC_C(long changesetId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the changeset entries where changesetId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByC_C(long changesetId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the changeset entries where changesetId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changeset entries
	*/
	public java.util.List<ChangesetEntry> findByC_C(long changesetId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first changeset entry in the ordered set where changesetId = &#63; and classNameId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByC_C_First(long changesetId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first changeset entry in the ordered set where changesetId = &#63; and classNameId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByC_C_First(long changesetId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the last changeset entry in the ordered set where changesetId = &#63; and classNameId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByC_C_Last(long changesetId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last changeset entry in the ordered set where changesetId = &#63; and classNameId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByC_C_Last(long changesetId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns the changeset entries before and after the current changeset entry in the ordered set where changesetId = &#63; and classNameId = &#63;.
	*
	* @param entryId the primary key of the current changeset entry
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset entry
	* @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	*/
	public ChangesetEntry[] findByC_C_PrevAndNext(long entryId,
		long changesetId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the changeset entries where changesetId = &#63; and classNameId = &#63; from the database.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	*/
	public void removeByC_C(long changesetId, long classNameId);

	/**
	* Returns the number of changeset entries where changesetId = &#63; and classNameId = &#63;.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @return the number of matching changeset entries
	*/
	public int countByC_C(long changesetId, long classNameId);

	/**
	* Returns the changeset entry where changesetId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching changeset entry
	* @throws NoSuchEntryException if a matching changeset entry could not be found
	*/
	public ChangesetEntry findByC_C_C(long changesetId, long classNameId,
		long classPK) throws NoSuchEntryException;

	/**
	* Returns the changeset entry where changesetId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByC_C_C(long changesetId, long classNameId,
		long classPK);

	/**
	* Returns the changeset entry where changesetId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	*/
	public ChangesetEntry fetchByC_C_C(long changesetId, long classNameId,
		long classPK, boolean retrieveFromCache);

	/**
	* Removes the changeset entry where changesetId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the changeset entry that was removed
	*/
	public ChangesetEntry removeByC_C_C(long changesetId, long classNameId,
		long classPK) throws NoSuchEntryException;

	/**
	* Returns the number of changeset entries where changesetId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param changesetId the changeset ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching changeset entries
	*/
	public int countByC_C_C(long changesetId, long classNameId, long classPK);

	/**
	* Caches the changeset entry in the entity cache if it is enabled.
	*
	* @param changesetEntry the changeset entry
	*/
	public void cacheResult(ChangesetEntry changesetEntry);

	/**
	* Caches the changeset entries in the entity cache if it is enabled.
	*
	* @param changesetEntries the changeset entries
	*/
	public void cacheResult(java.util.List<ChangesetEntry> changesetEntries);

	/**
	* Creates a new changeset entry with the primary key. Does not add the changeset entry to the database.
	*
	* @param entryId the primary key for the new changeset entry
	* @return the new changeset entry
	*/
	public ChangesetEntry create(long entryId);

	/**
	* Removes the changeset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the changeset entry
	* @return the changeset entry that was removed
	* @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	*/
	public ChangesetEntry remove(long entryId) throws NoSuchEntryException;

	public ChangesetEntry updateImpl(ChangesetEntry changesetEntry);

	/**
	* Returns the changeset entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the changeset entry
	* @return the changeset entry
	* @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	*/
	public ChangesetEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException;

	/**
	* Returns the changeset entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the changeset entry
	* @return the changeset entry, or <code>null</code> if a changeset entry with the primary key could not be found
	*/
	public ChangesetEntry fetchByPrimaryKey(long entryId);

	@Override
	public java.util.Map<java.io.Serializable, ChangesetEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the changeset entries.
	*
	* @return the changeset entries
	*/
	public java.util.List<ChangesetEntry> findAll();

	/**
	* Returns a range of all the changeset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @return the range of changeset entries
	*/
	public java.util.List<ChangesetEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the changeset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of changeset entries
	*/
	public java.util.List<ChangesetEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the changeset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of changeset entries
	*/
	public java.util.List<ChangesetEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the changeset entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of changeset entries.
	*
	* @return the number of changeset entries
	*/
	public int countAll();
}