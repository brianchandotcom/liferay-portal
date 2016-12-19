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

package com.liferay.wsrp.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.wsrp.exception.NoSuchConsumerPortletException;
import com.liferay.wsrp.model.WSRPConsumerPortlet;

/**
 * The persistence interface for the wsrp consumer portlet service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.wsrp.service.persistence.impl.WSRPConsumerPortletPersistenceImpl
 * @see WSRPConsumerPortletUtil
 * @generated
 */
@ProviderType
public interface WSRPConsumerPortletPersistence extends BasePersistence<WSRPConsumerPortlet> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WSRPConsumerPortletUtil} to access the wsrp consumer portlet persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the wsrp consumer portlets where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the wsrp consumer portlets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @return the range of matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the wsrp consumer portlets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns an ordered range of all the wsrp consumer portlets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws NoSuchConsumerPortletException;

	/**
	* Returns the first wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns the last wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws NoSuchConsumerPortletException;

	/**
	* Returns the last wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns the wsrp consumer portlets before and after the current wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param wsrpConsumerPortletId the primary key of the current wsrp consumer portlet
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public WSRPConsumerPortlet[] findByUuid_PrevAndNext(
		long wsrpConsumerPortletId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws NoSuchConsumerPortletException;

	/**
	* Removes all the wsrp consumer portlets where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of wsrp consumer portlets where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching wsrp consumer portlets
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @return the range of matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns an ordered range of all the wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws NoSuchConsumerPortletException;

	/**
	* Returns the first wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns the last wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws NoSuchConsumerPortletException;

	/**
	* Returns the last wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns the wsrp consumer portlets before and after the current wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param wsrpConsumerPortletId the primary key of the current wsrp consumer portlet
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public WSRPConsumerPortlet[] findByUuid_C_PrevAndNext(
		long wsrpConsumerPortletId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws NoSuchConsumerPortletException;

	/**
	* Removes all the wsrp consumer portlets where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching wsrp consumer portlets
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @return the matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByWsrpConsumerId(
		long wsrpConsumerId);

	/**
	* Returns a range of all the wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @return the range of matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByWsrpConsumerId(
		long wsrpConsumerId, int start, int end);

	/**
	* Returns an ordered range of all the wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByWsrpConsumerId(
		long wsrpConsumerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns an ordered range of all the wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findByWsrpConsumerId(
		long wsrpConsumerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet findByWsrpConsumerId_First(long wsrpConsumerId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws NoSuchConsumerPortletException;

	/**
	* Returns the first wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet fetchByWsrpConsumerId_First(
		long wsrpConsumerId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns the last wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet findByWsrpConsumerId_Last(long wsrpConsumerId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws NoSuchConsumerPortletException;

	/**
	* Returns the last wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet fetchByWsrpConsumerId_Last(long wsrpConsumerId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns the wsrp consumer portlets before and after the current wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerPortletId the primary key of the current wsrp consumer portlet
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public WSRPConsumerPortlet[] findByWsrpConsumerId_PrevAndNext(
		long wsrpConsumerPortletId, long wsrpConsumerId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws NoSuchConsumerPortletException;

	/**
	* Removes all the wsrp consumer portlets where wsrpConsumerId = &#63; from the database.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	*/
	public void removeByWsrpConsumerId(long wsrpConsumerId);

	/**
	* Returns the number of wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @return the number of matching wsrp consumer portlets
	*/
	public int countByWsrpConsumerId(long wsrpConsumerId);

	/**
	* Returns the wsrp consumer portlet where wsrpConsumerId = &#63; and portletHandle = &#63; or throws a {@link NoSuchConsumerPortletException} if it could not be found.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @return the matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet findByW_P(long wsrpConsumerId,
		java.lang.String portletHandle) throws NoSuchConsumerPortletException;

	/**
	* Returns the wsrp consumer portlet where wsrpConsumerId = &#63; and portletHandle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @return the matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet fetchByW_P(long wsrpConsumerId,
		java.lang.String portletHandle);

	/**
	* Returns the wsrp consumer portlet where wsrpConsumerId = &#63; and portletHandle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public WSRPConsumerPortlet fetchByW_P(long wsrpConsumerId,
		java.lang.String portletHandle, boolean retrieveFromCache);

	/**
	* Removes the wsrp consumer portlet where wsrpConsumerId = &#63; and portletHandle = &#63; from the database.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @return the wsrp consumer portlet that was removed
	*/
	public WSRPConsumerPortlet removeByW_P(long wsrpConsumerId,
		java.lang.String portletHandle) throws NoSuchConsumerPortletException;

	/**
	* Returns the number of wsrp consumer portlets where wsrpConsumerId = &#63; and portletHandle = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @return the number of matching wsrp consumer portlets
	*/
	public int countByW_P(long wsrpConsumerId, java.lang.String portletHandle);

	/**
	* Caches the wsrp consumer portlet in the entity cache if it is enabled.
	*
	* @param wsrpConsumerPortlet the wsrp consumer portlet
	*/
	public void cacheResult(WSRPConsumerPortlet wsrpConsumerPortlet);

	/**
	* Caches the wsrp consumer portlets in the entity cache if it is enabled.
	*
	* @param wsrpConsumerPortlets the wsrp consumer portlets
	*/
	public void cacheResult(
		java.util.List<WSRPConsumerPortlet> wsrpConsumerPortlets);

	/**
	* Creates a new wsrp consumer portlet with the primary key. Does not add the wsrp consumer portlet to the database.
	*
	* @param wsrpConsumerPortletId the primary key for the new wsrp consumer portlet
	* @return the new wsrp consumer portlet
	*/
	public WSRPConsumerPortlet create(long wsrpConsumerPortletId);

	/**
	* Removes the wsrp consumer portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpConsumerPortletId the primary key of the wsrp consumer portlet
	* @return the wsrp consumer portlet that was removed
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public WSRPConsumerPortlet remove(long wsrpConsumerPortletId)
		throws NoSuchConsumerPortletException;

	public WSRPConsumerPortlet updateImpl(
		WSRPConsumerPortlet wsrpConsumerPortlet);

	/**
	* Returns the wsrp consumer portlet with the primary key or throws a {@link NoSuchConsumerPortletException} if it could not be found.
	*
	* @param wsrpConsumerPortletId the primary key of the wsrp consumer portlet
	* @return the wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public WSRPConsumerPortlet findByPrimaryKey(long wsrpConsumerPortletId)
		throws NoSuchConsumerPortletException;

	/**
	* Returns the wsrp consumer portlet with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param wsrpConsumerPortletId the primary key of the wsrp consumer portlet
	* @return the wsrp consumer portlet, or <code>null</code> if a wsrp consumer portlet with the primary key could not be found
	*/
	public WSRPConsumerPortlet fetchByPrimaryKey(long wsrpConsumerPortletId);

	@Override
	public java.util.Map<java.io.Serializable, WSRPConsumerPortlet> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the wsrp consumer portlets.
	*
	* @return the wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findAll();

	/**
	* Returns a range of all the wsrp consumer portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @return the range of wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findAll(int start, int end);

	/**
	* Returns an ordered range of all the wsrp consumer portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator);

	/**
	* Returns an ordered range of all the wsrp consumer portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of wsrp consumer portlets
	*/
	public java.util.List<WSRPConsumerPortlet> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumerPortlet> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the wsrp consumer portlets from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of wsrp consumer portlets.
	*
	* @return the number of wsrp consumer portlets
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}