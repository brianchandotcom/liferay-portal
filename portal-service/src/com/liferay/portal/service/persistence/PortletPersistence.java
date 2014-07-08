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

import com.liferay.portal.model.Portlet;

/**
 * The persistence interface for the portlet service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPersistenceImpl
 * @see PortletUtil
 * @generated
 */
@ProviderType
public interface PortletPersistence extends BasePersistence<Portlet> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletUtil} to access the portlet persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the portlets where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching portlets
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the portlets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @return the range of matching portlets
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the portlets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlets
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet
	* @throws com.liferay.portal.NoSuchPortletException if a matching portlet could not be found
	*/
	public com.liferay.portal.model.Portlet findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletException;

	/**
	* Returns the first portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public com.liferay.portal.model.Portlet fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet
	* @throws com.liferay.portal.NoSuchPortletException if a matching portlet could not be found
	*/
	public com.liferay.portal.model.Portlet findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletException;

	/**
	* Returns the last portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public com.liferay.portal.model.Portlet fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the portlets before and after the current portlet in the ordered set where companyId = &#63;.
	*
	* @param id the primary key of the current portlet
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet
	* @throws com.liferay.portal.NoSuchPortletException if a portlet with the primary key could not be found
	*/
	public com.liferay.portal.model.Portlet[] findByCompanyId_PrevAndNext(
		long id, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletException;

	/**
	* Removes all the portlets where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of portlets where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching portlets
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the portlet where companyId = &#63; and portletId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletException} if it could not be found.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the matching portlet
	* @throws com.liferay.portal.NoSuchPortletException if a matching portlet could not be found
	*/
	public com.liferay.portal.model.Portlet findByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException;

	/**
	* Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId);

	/**
	* Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId, boolean retrieveFromCache);

	/**
	* Removes the portlet where companyId = &#63; and portletId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the portlet that was removed
	*/
	public com.liferay.portal.model.Portlet removeByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException;

	/**
	* Returns the number of portlets where companyId = &#63; and portletId = &#63;.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the number of matching portlets
	*/
	public int countByC_P(long companyId, java.lang.String portletId);

	/**
	* Caches the portlet in the entity cache if it is enabled.
	*
	* @param portlet the portlet
	*/
	public void cacheResult(com.liferay.portal.model.Portlet portlet);

	/**
	* Caches the portlets in the entity cache if it is enabled.
	*
	* @param portlets the portlets
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Portlet> portlets);

	/**
	* Creates a new portlet with the primary key. Does not add the portlet to the database.
	*
	* @param id the primary key for the new portlet
	* @return the new portlet
	*/
	public com.liferay.portal.model.Portlet create(long id);

	/**
	* Removes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the portlet
	* @return the portlet that was removed
	* @throws com.liferay.portal.NoSuchPortletException if a portlet with the primary key could not be found
	*/
	public com.liferay.portal.model.Portlet remove(long id)
		throws com.liferay.portal.NoSuchPortletException;

	public com.liferay.portal.model.Portlet updateImpl(
		com.liferay.portal.model.Portlet portlet);

	/**
	* Returns the portlet with the primary key or throws a {@link com.liferay.portal.NoSuchPortletException} if it could not be found.
	*
	* @param id the primary key of the portlet
	* @return the portlet
	* @throws com.liferay.portal.NoSuchPortletException if a portlet with the primary key could not be found
	*/
	public com.liferay.portal.model.Portlet findByPrimaryKey(long id)
		throws com.liferay.portal.NoSuchPortletException;

	/**
	* Returns the portlet with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the portlet
	* @return the portlet, or <code>null</code> if a portlet with the primary key could not be found
	*/
	public com.liferay.portal.model.Portlet fetchByPrimaryKey(long id);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.Portlet> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the portlets.
	*
	* @return the portlets
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findAll();

	/**
	* Returns a range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @return the range of portlets
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portlets
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the portlets from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of portlets.
	*
	* @return the number of portlets
	*/
	public int countAll();
}