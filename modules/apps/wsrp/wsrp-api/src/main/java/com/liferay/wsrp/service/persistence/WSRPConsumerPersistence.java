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

import com.liferay.wsrp.exception.NoSuchConsumerException;
import com.liferay.wsrp.model.WSRPConsumer;

/**
 * The persistence interface for the wsrp consumer service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.wsrp.service.persistence.impl.WSRPConsumerPersistenceImpl
 * @see WSRPConsumerUtil
 * @generated
 */
@ProviderType
public interface WSRPConsumerPersistence extends BasePersistence<WSRPConsumer> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WSRPConsumerUtil} to access the wsrp consumer persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the wsrp consumers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the wsrp consumers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @return the range of matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the wsrp consumers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns an ordered range of all the wsrp consumers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first wsrp consumer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer
	* @throws NoSuchConsumerException if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator)
		throws NoSuchConsumerException;

	/**
	* Returns the first wsrp consumer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer, or <code>null</code> if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns the last wsrp consumer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer
	* @throws NoSuchConsumerException if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator)
		throws NoSuchConsumerException;

	/**
	* Returns the last wsrp consumer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer, or <code>null</code> if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns the wsrp consumers before and after the current wsrp consumer in the ordered set where uuid = &#63;.
	*
	* @param wsrpConsumerId the primary key of the current wsrp consumer
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wsrp consumer
	* @throws NoSuchConsumerException if a wsrp consumer with the primary key could not be found
	*/
	public WSRPConsumer[] findByUuid_PrevAndNext(long wsrpConsumerId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator)
		throws NoSuchConsumerException;

	/**
	* Removes all the wsrp consumers where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of wsrp consumers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching wsrp consumers
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the wsrp consumers where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the wsrp consumers where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @return the range of matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the wsrp consumers where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns an ordered range of all the wsrp consumers where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first wsrp consumer in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer
	* @throws NoSuchConsumerException if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator)
		throws NoSuchConsumerException;

	/**
	* Returns the first wsrp consumer in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer, or <code>null</code> if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns the last wsrp consumer in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer
	* @throws NoSuchConsumerException if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator)
		throws NoSuchConsumerException;

	/**
	* Returns the last wsrp consumer in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer, or <code>null</code> if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns the wsrp consumers before and after the current wsrp consumer in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param wsrpConsumerId the primary key of the current wsrp consumer
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wsrp consumer
	* @throws NoSuchConsumerException if a wsrp consumer with the primary key could not be found
	*/
	public WSRPConsumer[] findByUuid_C_PrevAndNext(long wsrpConsumerId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator)
		throws NoSuchConsumerException;

	/**
	* Removes all the wsrp consumers where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of wsrp consumers where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching wsrp consumers
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the wsrp consumers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByCompanyId(long companyId);

	/**
	* Returns a range of all the wsrp consumers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @return the range of matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the wsrp consumers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns an ordered range of all the wsrp consumers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first wsrp consumer in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer
	* @throws NoSuchConsumerException if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator)
		throws NoSuchConsumerException;

	/**
	* Returns the first wsrp consumer in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer, or <code>null</code> if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns the last wsrp consumer in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer
	* @throws NoSuchConsumerException if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator)
		throws NoSuchConsumerException;

	/**
	* Returns the last wsrp consumer in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer, or <code>null</code> if a matching wsrp consumer could not be found
	*/
	public WSRPConsumer fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns the wsrp consumers before and after the current wsrp consumer in the ordered set where companyId = &#63;.
	*
	* @param wsrpConsumerId the primary key of the current wsrp consumer
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wsrp consumer
	* @throws NoSuchConsumerException if a wsrp consumer with the primary key could not be found
	*/
	public WSRPConsumer[] findByCompanyId_PrevAndNext(long wsrpConsumerId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator)
		throws NoSuchConsumerException;

	/**
	* Removes all the wsrp consumers where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of wsrp consumers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching wsrp consumers
	*/
	public int countByCompanyId(long companyId);

	/**
	* Caches the wsrp consumer in the entity cache if it is enabled.
	*
	* @param wsrpConsumer the wsrp consumer
	*/
	public void cacheResult(WSRPConsumer wsrpConsumer);

	/**
	* Caches the wsrp consumers in the entity cache if it is enabled.
	*
	* @param wsrpConsumers the wsrp consumers
	*/
	public void cacheResult(java.util.List<WSRPConsumer> wsrpConsumers);

	/**
	* Creates a new wsrp consumer with the primary key. Does not add the wsrp consumer to the database.
	*
	* @param wsrpConsumerId the primary key for the new wsrp consumer
	* @return the new wsrp consumer
	*/
	public WSRPConsumer create(long wsrpConsumerId);

	/**
	* Removes the wsrp consumer with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpConsumerId the primary key of the wsrp consumer
	* @return the wsrp consumer that was removed
	* @throws NoSuchConsumerException if a wsrp consumer with the primary key could not be found
	*/
	public WSRPConsumer remove(long wsrpConsumerId)
		throws NoSuchConsumerException;

	public WSRPConsumer updateImpl(WSRPConsumer wsrpConsumer);

	/**
	* Returns the wsrp consumer with the primary key or throws a {@link NoSuchConsumerException} if it could not be found.
	*
	* @param wsrpConsumerId the primary key of the wsrp consumer
	* @return the wsrp consumer
	* @throws NoSuchConsumerException if a wsrp consumer with the primary key could not be found
	*/
	public WSRPConsumer findByPrimaryKey(long wsrpConsumerId)
		throws NoSuchConsumerException;

	/**
	* Returns the wsrp consumer with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param wsrpConsumerId the primary key of the wsrp consumer
	* @return the wsrp consumer, or <code>null</code> if a wsrp consumer with the primary key could not be found
	*/
	public WSRPConsumer fetchByPrimaryKey(long wsrpConsumerId);

	@Override
	public java.util.Map<java.io.Serializable, WSRPConsumer> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the wsrp consumers.
	*
	* @return the wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findAll();

	/**
	* Returns a range of all the wsrp consumers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @return the range of wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findAll(int start, int end);

	/**
	* Returns an ordered range of all the wsrp consumers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator);

	/**
	* Returns an ordered range of all the wsrp consumers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumers
	* @param end the upper bound of the range of wsrp consumers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of wsrp consumers
	*/
	public java.util.List<WSRPConsumer> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WSRPConsumer> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the wsrp consumers from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of wsrp consumers.
	*
	* @return the number of wsrp consumers
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}