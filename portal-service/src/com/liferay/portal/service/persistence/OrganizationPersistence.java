/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Organization;

/**
 * The persistence interface for the organization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrganizationPersistenceImpl
 * @see OrganizationUtil
 * @generated
 */
public interface OrganizationPersistence extends BasePersistence<Organization> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OrganizationUtil} to access the organization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the organization in the entity cache if it is enabled.
	*
	* @param organization the organization
	*/
	public void cacheResult(com.liferay.portal.model.Organization organization);

	/**
	* Caches the organizations in the entity cache if it is enabled.
	*
	* @param organizations the organizations
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Organization> organizations);

	/**
	* Creates a new organization with the primary key. Does not add the organization to the database.
	*
	* @param organizationId the primary key for the new organization
	* @return the new organization
	*/
	public com.liferay.portal.model.Organization create(long organizationId);

	/**
	* Removes the organization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param organizationId the primary key of the organization
	* @return the organization that was removed
	* @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization remove(long organizationId)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization updateImpl(
		com.liferay.portal.model.Organization organization, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organization with the primary key or throws a {@link com.liferay.portal.NoSuchOrganizationException} if it could not be found.
	*
	* @param organizationId the primary key of the organization
	* @return the organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization findByPrimaryKey(
		long organizationId)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param organizationId the primary key of the organization
	* @return the organization, or <code>null</code> if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization fetchByPrimaryKey(
		long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the organizations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first organization in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last organization in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organizations before and after the current organization in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization[] findByCompanyId_PrevAndNext(
		long organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the organizations that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> filterFindByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the organizations that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> filterFindByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization[] filterFindByCompanyId_PrevAndNext(
		long organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the organizations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findByLocations(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findByLocations(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findByLocations(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first organization in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization findByLocations_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last organization in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization findByLocations_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organizations before and after the current organization in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization[] findByLocations_PrevAndNext(
		long organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the organizations that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> filterFindByLocations(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the organizations that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> filterFindByLocations(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> filterFindByLocations(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization[] filterFindByLocations_PrevAndNext(
		long organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findByC_P(
		long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findByC_P(
		long companyId, long parentOrganizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findByC_P(
		long companyId, long parentOrganizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization findByC_P_First(
		long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization findByC_P_Last(
		long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization[] findByC_P_PrevAndNext(
		long organizationId, long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> filterFindByC_P(
		long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> filterFindByC_P(
		long companyId, long parentOrganizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> filterFindByC_P(
		long companyId, long parentOrganizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization[] filterFindByC_P_PrevAndNext(
		long organizationId, long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organization where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchOrganizationException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching organization
	* @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organization where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching organization, or <code>null</code> if a matching organization could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization fetchByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the organization where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching organization, or <code>null</code> if a matching organization could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the organizations.
	*
	* @return the organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the organizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the organizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the organizations where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the organizations where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByLocations(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the organizations where companyId = &#63; and parentOrganizationId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_P(long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the organization where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the organizations from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of organizations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of organizations that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of organizations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public int countByLocations(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of organizations that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByLocations(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of organizations where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the number of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_P(long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the number of matching organizations that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByC_P(long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of organizations where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching organizations
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of organizations.
	*
	* @return the number of organizations
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the groups associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return the groups associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the groups associated with the organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the organization
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of groups associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the groups associated with the organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the organization
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of groups associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return the number of groups associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public int getGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the group is associated with the organization.
	*
	* @param pk the primary key of the organization
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the organization; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the organization has any groups associated with it.
	*
	* @param pk the primary key of the organization to check for associations with groups
	* @return <code>true</code> if the organization has any groups associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public void addGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the organization and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization to clear the associated groups from
	* @throws SystemException if a system exception occurred
	*/
	public void clearGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the groups associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPKs the primary keys of the groups to be associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the groups associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groups the groups to be associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the users associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return the users associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the users associated with the organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the organization
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of users associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the users associated with the organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the organization
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return the number of users associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public int getUsersSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the user is associated with the organization.
	*
	* @param pk the primary key of the organization
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the organization; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the organization has any users associated with it.
	*
	* @param pk the primary key of the organization to check for associations with users
	* @return <code>true</code> if the organization has any users associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPK the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public void addUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param user the user
	* @throws SystemException if a system exception occurred
	*/
	public void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPKs the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param users the users
	* @throws SystemException if a system exception occurred
	*/
	public void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the organization and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization to clear the associated users from
	* @throws SystemException if a system exception occurred
	*/
	public void clearUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPK the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public void removeUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param user the user
	* @throws SystemException if a system exception occurred
	*/
	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPKs the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param users the users
	* @throws SystemException if a system exception occurred
	*/
	public void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the users associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPKs the primary keys of the users to be associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the users associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param users the users to be associated with the organization
	* @throws SystemException if a system exception occurred
	*/
	public void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	public Organization remove(Organization organization)
		throws SystemException;
}