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

package com.liferay.portlet.softwarecatalog.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

/**
 * The persistence interface for the s c product entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.persistence.impl.SCProductEntryPersistenceImpl
 * @see SCProductEntryUtil
 * @generated
 */
@ProviderType
public interface SCProductEntryPersistence extends BasePersistence<SCProductEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SCProductEntryUtil} to access the s c product entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the s c product entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the s c product entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the s c product entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns an ordered range of all the s c product entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to use the finder cache
	* @return the ordered range of matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first s c product entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry
	* @throws NoSuchProductEntryException if a matching s c product entry could not be found
	*/
	public SCProductEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns the first s c product entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	*/
	public SCProductEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns the last s c product entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry
	* @throws NoSuchProductEntryException if a matching s c product entry could not be found
	*/
	public SCProductEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns the last s c product entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	*/
	public SCProductEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set where groupId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws NoSuchProductEntryException if a s c product entry with the primary key could not be found
	*/
	public SCProductEntry[] findByGroupId_PrevAndNext(long productEntryId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns all the s c product entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c product entries that the user has permission to view
	*/
	public java.util.List<SCProductEntry> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the s c product entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries that the user has permission to view
	*/
	public java.util.List<SCProductEntry> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the s c product entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries that the user has permission to view
	*/
	public java.util.List<SCProductEntry> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set of s c product entries that the user has permission to view where groupId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws NoSuchProductEntryException if a s c product entry with the primary key could not be found
	*/
	public SCProductEntry[] filterFindByGroupId_PrevAndNext(
		long productEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Removes all the s c product entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of s c product entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c product entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of s c product entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c product entries that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the s c product entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByCompanyId(long companyId);

	/**
	* Returns a range of all the s c product entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the s c product entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns an ordered range of all the s c product entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to use the finder cache
	* @return the ordered range of matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first s c product entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry
	* @throws NoSuchProductEntryException if a matching s c product entry could not be found
	*/
	public SCProductEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns the first s c product entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	*/
	public SCProductEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns the last s c product entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry
	* @throws NoSuchProductEntryException if a matching s c product entry could not be found
	*/
	public SCProductEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns the last s c product entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	*/
	public SCProductEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set where companyId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws NoSuchProductEntryException if a s c product entry with the primary key could not be found
	*/
	public SCProductEntry[] findByCompanyId_PrevAndNext(long productEntryId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Removes all the s c product entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of s c product entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s c product entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the s c product entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByG_U(long groupId, long userId);

	/**
	* Returns a range of all the s c product entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByG_U(long groupId, long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the s c product entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByG_U(long groupId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns an ordered range of all the s c product entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to use the finder cache
	* @return the ordered range of matching s c product entries
	*/
	public java.util.List<SCProductEntry> findByG_U(long groupId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry
	* @throws NoSuchProductEntryException if a matching s c product entry could not be found
	*/
	public SCProductEntry findByG_U_First(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns the first s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	*/
	public SCProductEntry fetchByG_U_First(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns the last s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry
	* @throws NoSuchProductEntryException if a matching s c product entry could not be found
	*/
	public SCProductEntry findByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns the last s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	*/
	public SCProductEntry fetchByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws NoSuchProductEntryException if a s c product entry with the primary key could not be found
	*/
	public SCProductEntry[] findByG_U_PrevAndNext(long productEntryId,
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns all the s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching s c product entries that the user has permission to view
	*/
	public java.util.List<SCProductEntry> filterFindByG_U(long groupId,
		long userId);

	/**
	* Returns a range of all the s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of matching s c product entries that the user has permission to view
	*/
	public java.util.List<SCProductEntry> filterFindByG_U(long groupId,
		long userId, int start, int end);

	/**
	* Returns an ordered range of all the s c product entries that the user has permissions to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product entries that the user has permission to view
	*/
	public java.util.List<SCProductEntry> filterFindByG_U(long groupId,
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns the s c product entries before and after the current s c product entry in the ordered set of s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param productEntryId the primary key of the current s c product entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product entry
	* @throws NoSuchProductEntryException if a s c product entry with the primary key could not be found
	*/
	public SCProductEntry[] filterFindByG_U_PrevAndNext(long productEntryId,
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Removes all the s c product entries where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	*/
	public void removeByG_U(long groupId, long userId);

	/**
	* Returns the number of s c product entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching s c product entries
	*/
	public int countByG_U(long groupId, long userId);

	/**
	* Returns the number of s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching s c product entries that the user has permission to view
	*/
	public int filterCountByG_U(long groupId, long userId);

	/**
	* Returns the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; or throws a {@link NoSuchProductEntryException} if it could not be found.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @return the matching s c product entry
	* @throws NoSuchProductEntryException if a matching s c product entry could not be found
	*/
	public SCProductEntry findByRG_RA(java.lang.String repoGroupId,
		java.lang.String repoArtifactId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @return the matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	*/
	public SCProductEntry fetchByRG_RA(java.lang.String repoGroupId,
		java.lang.String repoArtifactId);

	/**
	* Returns the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	*/
	public SCProductEntry fetchByRG_RA(java.lang.String repoGroupId,
		java.lang.String repoArtifactId, boolean retrieveFromCache);

	/**
	* Removes the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; from the database.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @return the s c product entry that was removed
	*/
	public SCProductEntry removeByRG_RA(java.lang.String repoGroupId,
		java.lang.String repoArtifactId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns the number of s c product entries where repoGroupId = &#63; and repoArtifactId = &#63;.
	*
	* @param repoGroupId the repo group ID
	* @param repoArtifactId the repo artifact ID
	* @return the number of matching s c product entries
	*/
	public int countByRG_RA(java.lang.String repoGroupId,
		java.lang.String repoArtifactId);

	/**
	* Caches the s c product entry in the entity cache if it is enabled.
	*
	* @param scProductEntry the s c product entry
	*/
	public void cacheResult(SCProductEntry scProductEntry);

	/**
	* Caches the s c product entries in the entity cache if it is enabled.
	*
	* @param scProductEntries the s c product entries
	*/
	public void cacheResult(java.util.List<SCProductEntry> scProductEntries);

	/**
	* Creates a new s c product entry with the primary key. Does not add the s c product entry to the database.
	*
	* @param productEntryId the primary key for the new s c product entry
	* @return the new s c product entry
	*/
	public SCProductEntry create(long productEntryId);

	/**
	* Removes the s c product entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry that was removed
	* @throws NoSuchProductEntryException if a s c product entry with the primary key could not be found
	*/
	public SCProductEntry remove(long productEntryId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public SCProductEntry updateImpl(SCProductEntry scProductEntry);

	/**
	* Returns the s c product entry with the primary key or throws a {@link NoSuchProductEntryException} if it could not be found.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry
	* @throws NoSuchProductEntryException if a s c product entry with the primary key could not be found
	*/
	public SCProductEntry findByPrimaryKey(long productEntryId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	/**
	* Returns the s c product entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry, or <code>null</code> if a s c product entry with the primary key could not be found
	*/
	public SCProductEntry fetchByPrimaryKey(long productEntryId);

	@Override
	public java.util.Map<java.io.Serializable, SCProductEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the s c product entries.
	*
	* @return the s c product entries
	*/
	public java.util.List<SCProductEntry> findAll();

	/**
	* Returns a range of all the s c product entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of s c product entries
	*/
	public java.util.List<SCProductEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the s c product entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product entries
	*/
	public java.util.List<SCProductEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator);

	/**
	* Returns an ordered range of all the s c product entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to use the finder cache
	* @return the ordered range of s c product entries
	*/
	public java.util.List<SCProductEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the s c product entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of s c product entries.
	*
	* @return the number of s c product entries
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of s c licenses associated with the s c product entry.
	*
	* @param pk the primary key of the s c product entry
	* @return long[] of the primaryKeys of s c licenses associated with the s c product entry
	*/
	public long[] getSCLicensePrimaryKeys(long pk);

	/**
	* Returns all the s c licenses associated with the s c product entry.
	*
	* @param pk the primary key of the s c product entry
	* @return the s c licenses associated with the s c product entry
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk);

	/**
	* Returns a range of all the s c licenses associated with the s c product entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c product entry
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of s c licenses associated with the s c product entry
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the s c licenses associated with the s c product entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c product entry
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c licenses associated with the s c product entry
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCLicense> orderByComparator);

	/**
	* Returns the number of s c licenses associated with the s c product entry.
	*
	* @param pk the primary key of the s c product entry
	* @return the number of s c licenses associated with the s c product entry
	*/
	public int getSCLicensesSize(long pk);

	/**
	* Returns <code>true</code> if the s c license is associated with the s c product entry.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePK the primary key of the s c license
	* @return <code>true</code> if the s c license is associated with the s c product entry; <code>false</code> otherwise
	*/
	public boolean containsSCLicense(long pk, long scLicensePK);

	/**
	* Returns <code>true</code> if the s c product entry has any s c licenses associated with it.
	*
	* @param pk the primary key of the s c product entry to check for associations with s c licenses
	* @return <code>true</code> if the s c product entry has any s c licenses associated with it; <code>false</code> otherwise
	*/
	public boolean containsSCLicenses(long pk);

	/**
	* Adds an association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePK the primary key of the s c license
	*/
	public void addSCLicense(long pk, long scLicensePK);

	/**
	* Adds an association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicense the s c license
	*/
	public void addSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense);

	/**
	* Adds an association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePKs the primary keys of the s c licenses
	*/
	public void addSCLicenses(long pk, long[] scLicensePKs);

	/**
	* Adds an association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicenses the s c licenses
	*/
	public void addSCLicenses(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses);

	/**
	* Clears all associations between the s c product entry and its s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry to clear the associated s c licenses from
	*/
	public void clearSCLicenses(long pk);

	/**
	* Removes the association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePK the primary key of the s c license
	*/
	public void removeSCLicense(long pk, long scLicensePK);

	/**
	* Removes the association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicense the s c license
	*/
	public void removeSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense);

	/**
	* Removes the association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePKs the primary keys of the s c licenses
	*/
	public void removeSCLicenses(long pk, long[] scLicensePKs);

	/**
	* Removes the association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicenses the s c licenses
	*/
	public void removeSCLicenses(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses);

	/**
	* Sets the s c licenses associated with the s c product entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicensePKs the primary keys of the s c licenses to be associated with the s c product entry
	*/
	public void setSCLicenses(long pk, long[] scLicensePKs);

	/**
	* Sets the s c licenses associated with the s c product entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product entry
	* @param scLicenses the s c licenses to be associated with the s c product entry
	*/
	public void setSCLicenses(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses);

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}