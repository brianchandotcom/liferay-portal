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

package com.liferay.saml.persistence.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;

/**
 * The persistence interface for the saml sp idp connection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see com.liferay.saml.persistence.service.persistence.impl.SamlSpIdpConnectionPersistenceImpl
 * @see SamlSpIdpConnectionUtil
 * @generated
 */
@ProviderType
public interface SamlSpIdpConnectionPersistence extends BasePersistence<SamlSpIdpConnection> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SamlSpIdpConnectionUtil} to access the saml sp idp connection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the saml sp idp connections where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching saml sp idp connections
	*/
	public java.util.List<SamlSpIdpConnection> findByCompanyId(long companyId);

	/**
	* Returns a range of all the saml sp idp connections where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @return the range of matching saml sp idp connections
	*/
	public java.util.List<SamlSpIdpConnection> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the saml sp idp connections where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching saml sp idp connections
	*/
	public java.util.List<SamlSpIdpConnection> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpIdpConnection> orderByComparator);

	/**
	* Returns an ordered range of all the saml sp idp connections where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching saml sp idp connections
	*/
	public java.util.List<SamlSpIdpConnection> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpIdpConnection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a matching saml sp idp connection could not be found
	*/
	public SamlSpIdpConnection findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpIdpConnection> orderByComparator)
		throws NoSuchSpIdpConnectionException;

	/**
	* Returns the first saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	*/
	public SamlSpIdpConnection fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpIdpConnection> orderByComparator);

	/**
	* Returns the last saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a matching saml sp idp connection could not be found
	*/
	public SamlSpIdpConnection findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpIdpConnection> orderByComparator)
		throws NoSuchSpIdpConnectionException;

	/**
	* Returns the last saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	*/
	public SamlSpIdpConnection fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpIdpConnection> orderByComparator);

	/**
	* Returns the saml sp idp connections before and after the current saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param samlSpIdpConnectionId the primary key of the current saml sp idp connection
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	*/
	public SamlSpIdpConnection[] findByCompanyId_PrevAndNext(
		long samlSpIdpConnectionId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpIdpConnection> orderByComparator)
		throws NoSuchSpIdpConnectionException;

	/**
	* Removes all the saml sp idp connections where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of saml sp idp connections where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching saml sp idp connections
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; or throws a {@link NoSuchSpIdpConnectionException} if it could not be found.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @return the matching saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a matching saml sp idp connection could not be found
	*/
	public SamlSpIdpConnection findByC_SIEI(long companyId,
		String samlIdpEntityId) throws NoSuchSpIdpConnectionException;

	/**
	* Returns the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @return the matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	*/
	public SamlSpIdpConnection fetchByC_SIEI(long companyId,
		String samlIdpEntityId);

	/**
	* Returns the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	*/
	public SamlSpIdpConnection fetchByC_SIEI(long companyId,
		String samlIdpEntityId, boolean retrieveFromCache);

	/**
	* Removes the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @return the saml sp idp connection that was removed
	*/
	public SamlSpIdpConnection removeByC_SIEI(long companyId,
		String samlIdpEntityId) throws NoSuchSpIdpConnectionException;

	/**
	* Returns the number of saml sp idp connections where companyId = &#63; and samlIdpEntityId = &#63;.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @return the number of matching saml sp idp connections
	*/
	public int countByC_SIEI(long companyId, String samlIdpEntityId);

	/**
	* Caches the saml sp idp connection in the entity cache if it is enabled.
	*
	* @param samlSpIdpConnection the saml sp idp connection
	*/
	public void cacheResult(SamlSpIdpConnection samlSpIdpConnection);

	/**
	* Caches the saml sp idp connections in the entity cache if it is enabled.
	*
	* @param samlSpIdpConnections the saml sp idp connections
	*/
	public void cacheResult(
		java.util.List<SamlSpIdpConnection> samlSpIdpConnections);

	/**
	* Creates a new saml sp idp connection with the primary key. Does not add the saml sp idp connection to the database.
	*
	* @param samlSpIdpConnectionId the primary key for the new saml sp idp connection
	* @return the new saml sp idp connection
	*/
	public SamlSpIdpConnection create(long samlSpIdpConnectionId);

	/**
	* Removes the saml sp idp connection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection that was removed
	* @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	*/
	public SamlSpIdpConnection remove(long samlSpIdpConnectionId)
		throws NoSuchSpIdpConnectionException;

	public SamlSpIdpConnection updateImpl(
		SamlSpIdpConnection samlSpIdpConnection);

	/**
	* Returns the saml sp idp connection with the primary key or throws a {@link NoSuchSpIdpConnectionException} if it could not be found.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	*/
	public SamlSpIdpConnection findByPrimaryKey(long samlSpIdpConnectionId)
		throws NoSuchSpIdpConnectionException;

	/**
	* Returns the saml sp idp connection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection, or <code>null</code> if a saml sp idp connection with the primary key could not be found
	*/
	public SamlSpIdpConnection fetchByPrimaryKey(long samlSpIdpConnectionId);

	@Override
	public java.util.Map<java.io.Serializable, SamlSpIdpConnection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the saml sp idp connections.
	*
	* @return the saml sp idp connections
	*/
	public java.util.List<SamlSpIdpConnection> findAll();

	/**
	* Returns a range of all the saml sp idp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @return the range of saml sp idp connections
	*/
	public java.util.List<SamlSpIdpConnection> findAll(int start, int end);

	/**
	* Returns an ordered range of all the saml sp idp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of saml sp idp connections
	*/
	public java.util.List<SamlSpIdpConnection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpIdpConnection> orderByComparator);

	/**
	* Returns an ordered range of all the saml sp idp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of saml sp idp connections
	*/
	public java.util.List<SamlSpIdpConnection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpIdpConnection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the saml sp idp connections from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of saml sp idp connections.
	*
	* @return the number of saml sp idp connections
	*/
	public int countAll();
}