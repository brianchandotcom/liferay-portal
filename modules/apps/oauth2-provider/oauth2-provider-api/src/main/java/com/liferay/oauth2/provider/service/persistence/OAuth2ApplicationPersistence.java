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

package com.liferay.oauth2.provider.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException;
import com.liferay.oauth2.provider.model.OAuth2Application;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the o auth2 application service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth2.provider.service.persistence.impl.OAuth2ApplicationPersistenceImpl
 * @see OAuth2ApplicationUtil
 * @generated
 */
@ProviderType
public interface OAuth2ApplicationPersistence extends BasePersistence<OAuth2Application> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2ApplicationUtil} to access the o auth2 application persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the o auth2 applications where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching o auth2 applications
	*/
	public java.util.List<OAuth2Application> findByC(long companyId);

	/**
	* Returns a range of all the o auth2 applications where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @return the range of matching o auth2 applications
	*/
	public java.util.List<OAuth2Application> findByC(long companyId, int start,
		int end);

	/**
	* Returns an ordered range of all the o auth2 applications where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 applications
	*/
	public java.util.List<OAuth2Application> findByC(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 applications where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 applications
	*/
	public java.util.List<OAuth2Application> findByC(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	*/
	public OAuth2Application findByC_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator)
		throws NoSuchOAuth2ApplicationException;

	/**
	* Returns the first o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	*/
	public OAuth2Application fetchByC_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator);

	/**
	* Returns the last o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	*/
	public OAuth2Application findByC_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator)
		throws NoSuchOAuth2ApplicationException;

	/**
	* Returns the last o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	*/
	public OAuth2Application fetchByC_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator);

	/**
	* Returns the o auth2 applications before and after the current o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param oAuth2ApplicationId the primary key of the current o auth2 application
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	*/
	public OAuth2Application[] findByC_PrevAndNext(long oAuth2ApplicationId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator)
		throws NoSuchOAuth2ApplicationException;

	/**
	* Returns all the o auth2 applications that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching o auth2 applications that the user has permission to view
	*/
	public java.util.List<OAuth2Application> filterFindByC(long companyId);

	/**
	* Returns a range of all the o auth2 applications that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @return the range of matching o auth2 applications that the user has permission to view
	*/
	public java.util.List<OAuth2Application> filterFindByC(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the o auth2 applications that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 applications that the user has permission to view
	*/
	public java.util.List<OAuth2Application> filterFindByC(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator);

	/**
	* Returns the o auth2 applications before and after the current o auth2 application in the ordered set of o auth2 applications that the user has permission to view where companyId = &#63;.
	*
	* @param oAuth2ApplicationId the primary key of the current o auth2 application
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	*/
	public OAuth2Application[] filterFindByC_PrevAndNext(
		long oAuth2ApplicationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator)
		throws NoSuchOAuth2ApplicationException;

	/**
	* Removes all the o auth2 applications where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByC(long companyId);

	/**
	* Returns the number of o auth2 applications where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching o auth2 applications
	*/
	public int countByC(long companyId);

	/**
	* Returns the number of o auth2 applications that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching o auth2 applications that the user has permission to view
	*/
	public int filterCountByC(long companyId);

	/**
	* Returns the o auth2 application where companyId = &#63; and clientId = &#63; or throws a {@link NoSuchOAuth2ApplicationException} if it could not be found.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @return the matching o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	*/
	public OAuth2Application findByC_C(long companyId, java.lang.String clientId)
		throws NoSuchOAuth2ApplicationException;

	/**
	* Returns the o auth2 application where companyId = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	*/
	public OAuth2Application fetchByC_C(long companyId,
		java.lang.String clientId);

	/**
	* Returns the o auth2 application where companyId = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	*/
	public OAuth2Application fetchByC_C(long companyId,
		java.lang.String clientId, boolean retrieveFromCache);

	/**
	* Removes the o auth2 application where companyId = &#63; and clientId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @return the o auth2 application that was removed
	*/
	public OAuth2Application removeByC_C(long companyId,
		java.lang.String clientId) throws NoSuchOAuth2ApplicationException;

	/**
	* Returns the number of o auth2 applications where companyId = &#63; and clientId = &#63;.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @return the number of matching o auth2 applications
	*/
	public int countByC_C(long companyId, java.lang.String clientId);

	/**
	* Caches the o auth2 application in the entity cache if it is enabled.
	*
	* @param oAuth2Application the o auth2 application
	*/
	public void cacheResult(OAuth2Application oAuth2Application);

	/**
	* Caches the o auth2 applications in the entity cache if it is enabled.
	*
	* @param oAuth2Applications the o auth2 applications
	*/
	public void cacheResult(
		java.util.List<OAuth2Application> oAuth2Applications);

	/**
	* Creates a new o auth2 application with the primary key. Does not add the o auth2 application to the database.
	*
	* @param oAuth2ApplicationId the primary key for the new o auth2 application
	* @return the new o auth2 application
	*/
	public OAuth2Application create(long oAuth2ApplicationId);

	/**
	* Removes the o auth2 application with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2ApplicationId the primary key of the o auth2 application
	* @return the o auth2 application that was removed
	* @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	*/
	public OAuth2Application remove(long oAuth2ApplicationId)
		throws NoSuchOAuth2ApplicationException;

	public OAuth2Application updateImpl(OAuth2Application oAuth2Application);

	/**
	* Returns the o auth2 application with the primary key or throws a {@link NoSuchOAuth2ApplicationException} if it could not be found.
	*
	* @param oAuth2ApplicationId the primary key of the o auth2 application
	* @return the o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	*/
	public OAuth2Application findByPrimaryKey(long oAuth2ApplicationId)
		throws NoSuchOAuth2ApplicationException;

	/**
	* Returns the o auth2 application with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oAuth2ApplicationId the primary key of the o auth2 application
	* @return the o auth2 application, or <code>null</code> if a o auth2 application with the primary key could not be found
	*/
	public OAuth2Application fetchByPrimaryKey(long oAuth2ApplicationId);

	@Override
	public java.util.Map<java.io.Serializable, OAuth2Application> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the o auth2 applications.
	*
	* @return the o auth2 applications
	*/
	public java.util.List<OAuth2Application> findAll();

	/**
	* Returns a range of all the o auth2 applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @return the range of o auth2 applications
	*/
	public java.util.List<OAuth2Application> findAll(int start, int end);

	/**
	* Returns an ordered range of all the o auth2 applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of o auth2 applications
	*/
	public java.util.List<OAuth2Application> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of o auth2 applications
	*/
	public java.util.List<OAuth2Application> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Application> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the o auth2 applications from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of o auth2 applications.
	*
	* @return the number of o auth2 applications
	*/
	public int countAll();
}