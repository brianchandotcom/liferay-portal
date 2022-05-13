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

package com.liferay.oauth.client.persistence.service.persistence;

import com.liferay.oauth.client.persistence.exception.NoSuchAuthServerException;
import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the o auth client auth server service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientAuthServerUtil
 * @generated
 */
@ProviderType
public interface OAuthClientAuthServerPersistence
	extends BasePersistence<OAuthClientAuthServer> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuthClientAuthServerUtil} to access the o auth client auth server persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the o auth client auth servers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the o auth client auth servers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public OAuthClientAuthServer[] findByCompanyId_PrevAndNext(
			long oAuthClientAuthServerId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Returns all the o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client auth servers that the user has permission to view
	 */
	public java.util.List<OAuthClientAuthServer> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers that the user has permission to view
	 */
	public java.util.List<OAuthClientAuthServer> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client auth servers that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers that the user has permission to view
	 */
	public java.util.List<OAuthClientAuthServer> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set of o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public OAuthClientAuthServer[] filterFindByCompanyId_PrevAndNext(
			long oAuthClientAuthServerId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Removes all the o auth client auth servers where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of o auth client auth servers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client auth servers
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client auth servers that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the o auth client auth servers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByUserId(long userId);

	/**
	 * Returns a range of all the o auth client auth servers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client auth servers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth client auth servers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Returns the first o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns the last o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Returns the last o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public OAuthClientAuthServer[] findByUserId_PrevAndNext(
			long oAuthClientAuthServerId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Returns all the o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client auth servers that the user has permission to view
	 */
	public java.util.List<OAuthClientAuthServer> filterFindByUserId(
		long userId);

	/**
	 * Returns a range of all the o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers that the user has permission to view
	 */
	public java.util.List<OAuthClientAuthServer> filterFindByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client auth servers that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers that the user has permission to view
	 */
	public java.util.List<OAuthClientAuthServer> filterFindByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set of o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public OAuthClientAuthServer[] filterFindByUserId_PrevAndNext(
			long oAuthClientAuthServerId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Removes all the o auth client auth servers where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of o auth client auth servers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client auth servers
	 */
	public int countByUserId(long userId);

	/**
	 * Returns the number of o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client auth servers that the user has permission to view
	 */
	public int filterCountByUserId(long userId);

	/**
	 * Returns the o auth client auth server where companyId = &#63; and issuer = &#63; or throws a <code>NoSuchAuthServerException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer findByC_I(long companyId, String issuer)
		throws NoSuchAuthServerException;

	/**
	 * Returns the o auth client auth server where companyId = &#63; and issuer = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer fetchByC_I(long companyId, String issuer);

	/**
	 * Returns the o auth client auth server where companyId = &#63; and issuer = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer fetchByC_I(
		long companyId, String issuer, boolean useFinderCache);

	/**
	 * Removes the o auth client auth server where companyId = &#63; and issuer = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the o auth client auth server that was removed
	 */
	public OAuthClientAuthServer removeByC_I(long companyId, String issuer)
		throws NoSuchAuthServerException;

	/**
	 * Returns the number of o auth client auth servers where companyId = &#63; and issuer = &#63;.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the number of matching o auth client auth servers
	 */
	public int countByC_I(long companyId, String issuer);

	/**
	 * Returns all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByC_T(
		long companyId, String type);

	/**
	 * Returns a range of all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByC_T(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer findByC_T_First(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer fetchByC_T_First(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer findByC_T_Last(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public OAuthClientAuthServer fetchByC_T_Last(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public OAuthClientAuthServer[] findByC_T_PrevAndNext(
			long oAuthClientAuthServerId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Returns all the o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching o auth client auth servers that the user has permission to view
	 */
	public java.util.List<OAuthClientAuthServer> filterFindByC_T(
		long companyId, String type);

	/**
	 * Returns a range of all the o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers that the user has permission to view
	 */
	public java.util.List<OAuthClientAuthServer> filterFindByC_T(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client auth servers that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers that the user has permission to view
	 */
	public java.util.List<OAuthClientAuthServer> filterFindByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set of o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public OAuthClientAuthServer[] filterFindByC_T_PrevAndNext(
			long oAuthClientAuthServerId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException;

	/**
	 * Removes all the o auth client auth servers where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, String type);

	/**
	 * Returns the number of o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching o auth client auth servers
	 */
	public int countByC_T(long companyId, String type);

	/**
	 * Returns the number of o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching o auth client auth servers that the user has permission to view
	 */
	public int filterCountByC_T(long companyId, String type);

	/**
	 * Caches the o auth client auth server in the entity cache if it is enabled.
	 *
	 * @param oAuthClientAuthServer the o auth client auth server
	 */
	public void cacheResult(OAuthClientAuthServer oAuthClientAuthServer);

	/**
	 * Caches the o auth client auth servers in the entity cache if it is enabled.
	 *
	 * @param oAuthClientAuthServers the o auth client auth servers
	 */
	public void cacheResult(
		java.util.List<OAuthClientAuthServer> oAuthClientAuthServers);

	/**
	 * Creates a new o auth client auth server with the primary key. Does not add the o auth client auth server to the database.
	 *
	 * @param oAuthClientAuthServerId the primary key for the new o auth client auth server
	 * @return the new o auth client auth server
	 */
	public OAuthClientAuthServer create(long oAuthClientAuthServerId);

	/**
	 * Removes the o auth client auth server with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server that was removed
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public OAuthClientAuthServer remove(long oAuthClientAuthServerId)
		throws NoSuchAuthServerException;

	public OAuthClientAuthServer updateImpl(
		OAuthClientAuthServer oAuthClientAuthServer);

	/**
	 * Returns the o auth client auth server with the primary key or throws a <code>NoSuchAuthServerException</code> if it could not be found.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public OAuthClientAuthServer findByPrimaryKey(long oAuthClientAuthServerId)
		throws NoSuchAuthServerException;

	/**
	 * Returns the o auth client auth server with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server, or <code>null</code> if a o auth client auth server with the primary key could not be found
	 */
	public OAuthClientAuthServer fetchByPrimaryKey(
		long oAuthClientAuthServerId);

	/**
	 * Returns all the o auth client auth servers.
	 *
	 * @return the o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findAll();

	/**
	 * Returns a range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth client auth servers
	 */
	public java.util.List<OAuthClientAuthServer> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientAuthServer>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the o auth client auth servers from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of o auth client auth servers.
	 *
	 * @return the number of o auth client auth servers
	 */
	public int countAll();

}