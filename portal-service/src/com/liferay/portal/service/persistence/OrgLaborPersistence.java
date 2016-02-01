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

import com.liferay.portal.exception.NoSuchOrgLaborException;
import com.liferay.portal.model.OrgLabor;

/**
 * The persistence interface for the org labor service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.OrgLaborPersistenceImpl
 * @see OrgLaborUtil
 * @generated
 */
@ProviderType
public interface OrgLaborPersistence extends BasePersistence<OrgLabor> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OrgLaborUtil} to access the org labor persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the org labors where organizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @return the matching org labors
	*/
	public java.util.List<OrgLabor> findByOrganizationId(long organizationId);

	/**
	* Returns a range of all the org labors where organizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgLaborModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param organizationId the organization ID
	* @param start the lower bound of the range of org labors
	* @param end the upper bound of the range of org labors (not inclusive)
	* @return the range of matching org labors
	*/
	public java.util.List<OrgLabor> findByOrganizationId(long organizationId,
		int start, int end);

	/**
	* Returns an ordered range of all the org labors where organizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgLaborModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param organizationId the organization ID
	* @param start the lower bound of the range of org labors
	* @param end the upper bound of the range of org labors (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching org labors
	*/
	public java.util.List<OrgLabor> findByOrganizationId(long organizationId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OrgLabor> orderByComparator);

	/**
	* Returns an ordered range of all the org labors where organizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgLaborModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param organizationId the organization ID
	* @param start the lower bound of the range of org labors
	* @param end the upper bound of the range of org labors (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching org labors
	*/
	public java.util.List<OrgLabor> findByOrganizationId(long organizationId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OrgLabor> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first org labor in the ordered set where organizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org labor
	* @throws NoSuchOrgLaborException if a matching org labor could not be found
	*/
	public OrgLabor findByOrganizationId_First(long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator<OrgLabor> orderByComparator)
		throws NoSuchOrgLaborException;

	/**
	* Returns the first org labor in the ordered set where organizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org labor, or <code>null</code> if a matching org labor could not be found
	*/
	public OrgLabor fetchByOrganizationId_First(long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator<OrgLabor> orderByComparator);

	/**
	* Returns the last org labor in the ordered set where organizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org labor
	* @throws NoSuchOrgLaborException if a matching org labor could not be found
	*/
	public OrgLabor findByOrganizationId_Last(long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator<OrgLabor> orderByComparator)
		throws NoSuchOrgLaborException;

	/**
	* Returns the last org labor in the ordered set where organizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org labor, or <code>null</code> if a matching org labor could not be found
	*/
	public OrgLabor fetchByOrganizationId_Last(long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator<OrgLabor> orderByComparator);

	/**
	* Returns the org labors before and after the current org labor in the ordered set where organizationId = &#63;.
	*
	* @param orgLaborId the primary key of the current org labor
	* @param organizationId the organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next org labor
	* @throws NoSuchOrgLaborException if a org labor with the primary key could not be found
	*/
	public OrgLabor[] findByOrganizationId_PrevAndNext(long orgLaborId,
		long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator<OrgLabor> orderByComparator)
		throws NoSuchOrgLaborException;

	/**
	* Removes all the org labors where organizationId = &#63; from the database.
	*
	* @param organizationId the organization ID
	*/
	public void removeByOrganizationId(long organizationId);

	/**
	* Returns the number of org labors where organizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @return the number of matching org labors
	*/
	public int countByOrganizationId(long organizationId);

	/**
	* Caches the org labor in the entity cache if it is enabled.
	*
	* @param orgLabor the org labor
	*/
	public void cacheResult(OrgLabor orgLabor);

	/**
	* Caches the org labors in the entity cache if it is enabled.
	*
	* @param orgLabors the org labors
	*/
	public void cacheResult(java.util.List<OrgLabor> orgLabors);

	/**
	* Creates a new org labor with the primary key. Does not add the org labor to the database.
	*
	* @param orgLaborId the primary key for the new org labor
	* @return the new org labor
	*/
	public OrgLabor create(long orgLaborId);

	/**
	* Removes the org labor with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orgLaborId the primary key of the org labor
	* @return the org labor that was removed
	* @throws NoSuchOrgLaborException if a org labor with the primary key could not be found
	*/
	public OrgLabor remove(long orgLaborId) throws NoSuchOrgLaborException;

	public OrgLabor updateImpl(OrgLabor orgLabor);

	/**
	* Returns the org labor with the primary key or throws a {@link NoSuchOrgLaborException} if it could not be found.
	*
	* @param orgLaborId the primary key of the org labor
	* @return the org labor
	* @throws NoSuchOrgLaborException if a org labor with the primary key could not be found
	*/
	public OrgLabor findByPrimaryKey(long orgLaborId)
		throws NoSuchOrgLaborException;

	/**
	* Returns the org labor with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param orgLaborId the primary key of the org labor
	* @return the org labor, or <code>null</code> if a org labor with the primary key could not be found
	*/
	public OrgLabor fetchByPrimaryKey(long orgLaborId);

	@Override
	public java.util.Map<java.io.Serializable, OrgLabor> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the org labors.
	*
	* @return the org labors
	*/
	public java.util.List<OrgLabor> findAll();

	/**
	* Returns a range of all the org labors.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgLaborModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of org labors
	* @param end the upper bound of the range of org labors (not inclusive)
	* @return the range of org labors
	*/
	public java.util.List<OrgLabor> findAll(int start, int end);

	/**
	* Returns an ordered range of all the org labors.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgLaborModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of org labors
	* @param end the upper bound of the range of org labors (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of org labors
	*/
	public java.util.List<OrgLabor> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OrgLabor> orderByComparator);

	/**
	* Returns an ordered range of all the org labors.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgLaborModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of org labors
	* @param end the upper bound of the range of org labors (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of org labors
	*/
	public java.util.List<OrgLabor> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OrgLabor> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the org labors from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of org labors.
	*
	* @return the number of org labors
	*/
	public int countAll();
}