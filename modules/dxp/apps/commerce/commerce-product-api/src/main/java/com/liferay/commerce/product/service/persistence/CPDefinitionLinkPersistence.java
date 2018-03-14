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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException;
import com.liferay.commerce.product.model.CPDefinitionLink;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp definition link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionLinkPersistenceImpl
 * @see CPDefinitionLinkUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionLinkPersistence extends BasePersistence<CPDefinitionLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionLinkUtil} to access the cp definition link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp definition links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the cp definition links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the cp definition links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the first cp definition link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the last cp definition link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the last cp definition link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public CPDefinitionLink[] findByUuid_PrevAndNext(long CPDefinitionLinkId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Removes all the cp definition links where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp definition links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition links
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp definition link where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the cp definition link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the cp definition link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp definition link where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition link that was removed
	*/
	public CPDefinitionLink removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the number of cp definition links where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition links
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the first cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the last cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the last cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public CPDefinitionLink[] findByUuid_C_PrevAndNext(
		long CPDefinitionLinkId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Removes all the cp definition links where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition links
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the cp definition links where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @return the matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByCPDefinitionId1(
		long CPDefinitionId1);

	/**
	* Returns a range of all the cp definition links where CPDefinitionId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByCPDefinitionId1(
		long CPDefinitionId1, int start, int end);

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByCPDefinitionId1(
		long CPDefinitionId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByCPDefinitionId1(
		long CPDefinitionId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByCPDefinitionId1_First(long CPDefinitionId1,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByCPDefinitionId1_First(long CPDefinitionId1,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByCPDefinitionId1_Last(long CPDefinitionId1,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByCPDefinitionId1_Last(long CPDefinitionId1,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public CPDefinitionLink[] findByCPDefinitionId1_PrevAndNext(
		long CPDefinitionLinkId, long CPDefinitionId1,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Removes all the cp definition links where CPDefinitionId1 = &#63; from the database.
	*
	* @param CPDefinitionId1 the cp definition id1
	*/
	public void removeByCPDefinitionId1(long CPDefinitionId1);

	/**
	* Returns the number of cp definition links where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @return the number of matching cp definition links
	*/
	public int countByCPDefinitionId1(long CPDefinitionId1);

	/**
	* Returns all the cp definition links where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @return the matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByCPDefinitionId2(
		long CPDefinitionId2);

	/**
	* Returns a range of all the cp definition links where CPDefinitionId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByCPDefinitionId2(
		long CPDefinitionId2, int start, int end);

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByCPDefinitionId2(
		long CPDefinitionId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByCPDefinitionId2(
		long CPDefinitionId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByCPDefinitionId2_First(long CPDefinitionId2,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByCPDefinitionId2_First(long CPDefinitionId2,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByCPDefinitionId2_Last(long CPDefinitionId2,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByCPDefinitionId2_Last(long CPDefinitionId2,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public CPDefinitionLink[] findByCPDefinitionId2_PrevAndNext(
		long CPDefinitionLinkId, long CPDefinitionId2,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Removes all the cp definition links where CPDefinitionId2 = &#63; from the database.
	*
	* @param CPDefinitionId2 the cp definition id2
	*/
	public void removeByCPDefinitionId2(long CPDefinitionId2);

	/**
	* Returns the number of cp definition links where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @return the number of matching cp definition links
	*/
	public int countByCPDefinitionId2(long CPDefinitionId2);

	/**
	* Returns all the cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @return the matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByC1_T(long CPDefinitionId1,
		java.lang.String type);

	/**
	* Returns a range of all the cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByC1_T(long CPDefinitionId1,
		java.lang.String type, int start, int end);

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByC1_T(long CPDefinitionId1,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByC1_T(long CPDefinitionId1,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByC1_T_First(long CPDefinitionId1,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByC1_T_First(long CPDefinitionId1,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByC1_T_Last(long CPDefinitionId1,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByC1_T_Last(long CPDefinitionId1,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public CPDefinitionLink[] findByC1_T_PrevAndNext(long CPDefinitionLinkId,
		long CPDefinitionId1, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Removes all the cp definition links where CPDefinitionId1 = &#63; and type = &#63; from the database.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	*/
	public void removeByC1_T(long CPDefinitionId1, java.lang.String type);

	/**
	* Returns the number of cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @return the number of matching cp definition links
	*/
	public int countByC1_T(long CPDefinitionId1, java.lang.String type);

	/**
	* Returns all the cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByC2_T(long CPDefinitionId2,
		java.lang.String type);

	/**
	* Returns a range of all the cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByC2_T(long CPDefinitionId2,
		java.lang.String type, int start, int end);

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByC2_T(long CPDefinitionId2,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public java.util.List<CPDefinitionLink> findByC2_T(long CPDefinitionId2,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByC2_T_First(long CPDefinitionId2,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByC2_T_First(long CPDefinitionId2,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByC2_T_Last(long CPDefinitionId2,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByC2_T_Last(long CPDefinitionId2,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public CPDefinitionLink[] findByC2_T_PrevAndNext(long CPDefinitionLinkId,
		long CPDefinitionId2, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Removes all the cp definition links where CPDefinitionId2 = &#63; and type = &#63; from the database.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	*/
	public void removeByC2_T(long CPDefinitionId2, java.lang.String type);

	/**
	* Returns the number of cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the number of matching cp definition links
	*/
	public int countByC2_T(long CPDefinitionId2, java.lang.String type);

	/**
	* Returns the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public CPDefinitionLink findByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, java.lang.String type)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, java.lang.String type);

	/**
	* Returns the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public CPDefinitionLink fetchByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, java.lang.String type, boolean retrieveFromCache);

	/**
	* Removes the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; from the database.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the cp definition link that was removed
	*/
	public CPDefinitionLink removeByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, java.lang.String type)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the number of cp definition links where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the number of matching cp definition links
	*/
	public int countByC1_C2_T(long CPDefinitionId1, long CPDefinitionId2,
		java.lang.String type);

	/**
	* Caches the cp definition link in the entity cache if it is enabled.
	*
	* @param cpDefinitionLink the cp definition link
	*/
	public void cacheResult(CPDefinitionLink cpDefinitionLink);

	/**
	* Caches the cp definition links in the entity cache if it is enabled.
	*
	* @param cpDefinitionLinks the cp definition links
	*/
	public void cacheResult(java.util.List<CPDefinitionLink> cpDefinitionLinks);

	/**
	* Creates a new cp definition link with the primary key. Does not add the cp definition link to the database.
	*
	* @param CPDefinitionLinkId the primary key for the new cp definition link
	* @return the new cp definition link
	*/
	public CPDefinitionLink create(long CPDefinitionLinkId);

	/**
	* Removes the cp definition link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link that was removed
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public CPDefinitionLink remove(long CPDefinitionLinkId)
		throws NoSuchCPDefinitionLinkException;

	public CPDefinitionLink updateImpl(CPDefinitionLink cpDefinitionLink);

	/**
	* Returns the cp definition link with the primary key or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public CPDefinitionLink findByPrimaryKey(long CPDefinitionLinkId)
		throws NoSuchCPDefinitionLinkException;

	/**
	* Returns the cp definition link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link, or <code>null</code> if a cp definition link with the primary key could not be found
	*/
	public CPDefinitionLink fetchByPrimaryKey(long CPDefinitionLinkId);

	@Override
	public java.util.Map<java.io.Serializable, CPDefinitionLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp definition links.
	*
	* @return the cp definition links
	*/
	public java.util.List<CPDefinitionLink> findAll();

	/**
	* Returns a range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of cp definition links
	*/
	public java.util.List<CPDefinitionLink> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition links
	*/
	public java.util.List<CPDefinitionLink> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition links
	*/
	public java.util.List<CPDefinitionLink> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp definition links from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp definition links.
	*
	* @return the number of cp definition links
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}