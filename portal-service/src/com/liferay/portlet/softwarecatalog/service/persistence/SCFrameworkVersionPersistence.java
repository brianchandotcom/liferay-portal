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

import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;

/**
 * The persistence interface for the s c framework version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.persistence.impl.SCFrameworkVersionPersistenceImpl
 * @see SCFrameworkVersionUtil
 * @generated
 */
@ProviderType
public interface SCFrameworkVersionPersistence extends BasePersistence<SCFrameworkVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SCFrameworkVersionUtil} to access the s c framework version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the s c framework versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findByGroupId(long groupId);

	/**
	* Returns a range of all the s c framework versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the s c framework versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the first s c framework version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version
	* @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Returns the first s c framework version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the last s c framework version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version
	* @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Returns the last s c framework version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set where groupId = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	*/
	public SCFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Returns all the s c framework versions that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c framework versions that the user has permission to view
	*/
	public java.util.List<SCFrameworkVersion> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the s c framework versions that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions that the user has permission to view
	*/
	public java.util.List<SCFrameworkVersion> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the s c framework versions that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions that the user has permission to view
	*/
	public java.util.List<SCFrameworkVersion> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set of s c framework versions that the user has permission to view where groupId = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	*/
	public SCFrameworkVersion[] filterFindByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Removes all the s c framework versions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of s c framework versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c framework versions
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of s c framework versions that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c framework versions that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the s c framework versions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findByCompanyId(long companyId);

	/**
	* Returns a range of all the s c framework versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the s c framework versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the first s c framework version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version
	* @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Returns the first s c framework version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the last s c framework version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version
	* @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Returns the last s c framework version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set where companyId = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	*/
	public SCFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Removes all the s c framework versions where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of s c framework versions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s c framework versions
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the s c framework versions where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findByG_A(long groupId,
		boolean active);

	/**
	* Returns a range of all the s c framework versions where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findByG_A(long groupId,
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the s c framework versions where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the first s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version
	* @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion findByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Returns the first s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion fetchByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the last s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version
	* @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion findByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Returns the last s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	*/
	public SCFrameworkVersion fetchByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	*/
	public SCFrameworkVersion[] findByG_A_PrevAndNext(long frameworkVersionId,
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Returns all the s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching s c framework versions that the user has permission to view
	*/
	public java.util.List<SCFrameworkVersion> filterFindByG_A(long groupId,
		boolean active);

	/**
	* Returns a range of all the s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions that the user has permission to view
	*/
	public java.util.List<SCFrameworkVersion> filterFindByG_A(long groupId,
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the s c framework versions that the user has permissions to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions that the user has permission to view
	*/
	public java.util.List<SCFrameworkVersion> filterFindByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set of s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	*/
	public SCFrameworkVersion[] filterFindByG_A_PrevAndNext(
		long frameworkVersionId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Removes all the s c framework versions where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public void removeByG_A(long groupId, boolean active);

	/**
	* Returns the number of s c framework versions where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching s c framework versions
	*/
	public int countByG_A(long groupId, boolean active);

	/**
	* Returns the number of s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching s c framework versions that the user has permission to view
	*/
	public int filterCountByG_A(long groupId, boolean active);

	/**
	* Caches the s c framework version in the entity cache if it is enabled.
	*
	* @param scFrameworkVersion the s c framework version
	*/
	public void cacheResult(SCFrameworkVersion scFrameworkVersion);

	/**
	* Caches the s c framework versions in the entity cache if it is enabled.
	*
	* @param scFrameworkVersions the s c framework versions
	*/
	public void cacheResult(
		java.util.List<SCFrameworkVersion> scFrameworkVersions);

	/**
	* Creates a new s c framework version with the primary key. Does not add the s c framework version to the database.
	*
	* @param frameworkVersionId the primary key for the new s c framework version
	* @return the new s c framework version
	*/
	public SCFrameworkVersion create(long frameworkVersionId);

	/**
	* Removes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version that was removed
	* @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	*/
	public SCFrameworkVersion remove(long frameworkVersionId)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public SCFrameworkVersion updateImpl(SCFrameworkVersion scFrameworkVersion);

	/**
	* Returns the s c framework version with the primary key or throws a {@link NoSuchFrameworkVersionException} if it could not be found.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version
	* @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	*/
	public SCFrameworkVersion findByPrimaryKey(long frameworkVersionId)
		throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	/**
	* Returns the s c framework version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version, or <code>null</code> if a s c framework version with the primary key could not be found
	*/
	public SCFrameworkVersion fetchByPrimaryKey(long frameworkVersionId);

	@Override
	public java.util.Map<java.io.Serializable, SCFrameworkVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the s c framework versions.
	*
	* @return the s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findAll();

	/**
	* Returns a range of all the s c framework versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the s c framework versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c framework versions
	*/
	public java.util.List<SCFrameworkVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCFrameworkVersion> orderByComparator);

	/**
	* Removes all the s c framework versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of s c framework versions.
	*
	* @return the number of s c framework versions
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of s c product versions associated with the s c framework version.
	*
	* @param pk the primary key of the s c framework version
	* @return long[] of the primaryKeys of s c product versions associated with the s c framework version
	*/
	public long[] getSCProductVersionPrimaryKeys(long pk);

	/**
	* Returns all the s c product versions associated with the s c framework version.
	*
	* @param pk the primary key of the s c framework version
	* @return the s c product versions associated with the s c framework version
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk);

	/**
	* Returns a range of all the s c product versions associated with the s c framework version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c framework version
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of s c product versions associated with the s c framework version
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the s c product versions associated with the s c framework version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c framework version
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product versions associated with the s c framework version
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCProductVersion> orderByComparator);

	/**
	* Returns the number of s c product versions associated with the s c framework version.
	*
	* @param pk the primary key of the s c framework version
	* @return the number of s c product versions associated with the s c framework version
	*/
	public int getSCProductVersionsSize(long pk);

	/**
	* Returns <code>true</code> if the s c product version is associated with the s c framework version.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPK the primary key of the s c product version
	* @return <code>true</code> if the s c product version is associated with the s c framework version; <code>false</code> otherwise
	*/
	public boolean containsSCProductVersion(long pk, long scProductVersionPK);

	/**
	* Returns <code>true</code> if the s c framework version has any s c product versions associated with it.
	*
	* @param pk the primary key of the s c framework version to check for associations with s c product versions
	* @return <code>true</code> if the s c framework version has any s c product versions associated with it; <code>false</code> otherwise
	*/
	public boolean containsSCProductVersions(long pk);

	/**
	* Adds an association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPK the primary key of the s c product version
	*/
	public void addSCProductVersion(long pk, long scProductVersionPK);

	/**
	* Adds an association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersion the s c product version
	*/
	public void addSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion);

	/**
	* Adds an association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPKs the primary keys of the s c product versions
	*/
	public void addSCProductVersions(long pk, long[] scProductVersionPKs);

	/**
	* Adds an association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersions the s c product versions
	*/
	public void addSCProductVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions);

	/**
	* Clears all associations between the s c framework version and its s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version to clear the associated s c product versions from
	*/
	public void clearSCProductVersions(long pk);

	/**
	* Removes the association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPK the primary key of the s c product version
	*/
	public void removeSCProductVersion(long pk, long scProductVersionPK);

	/**
	* Removes the association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersion the s c product version
	*/
	public void removeSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion);

	/**
	* Removes the association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPKs the primary keys of the s c product versions
	*/
	public void removeSCProductVersions(long pk, long[] scProductVersionPKs);

	/**
	* Removes the association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersions the s c product versions
	*/
	public void removeSCProductVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions);

	/**
	* Sets the s c product versions associated with the s c framework version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPKs the primary keys of the s c product versions to be associated with the s c framework version
	*/
	public void setSCProductVersions(long pk, long[] scProductVersionPKs);

	/**
	* Sets the s c product versions associated with the s c framework version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersions the s c product versions to be associated with the s c framework version
	*/
	public void setSCProductVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions);
}