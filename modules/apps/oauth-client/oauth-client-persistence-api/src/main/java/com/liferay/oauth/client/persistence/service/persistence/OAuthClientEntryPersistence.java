/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service.persistence;

import com.liferay.oauth.client.persistence.exception.NoSuchOAuthClientEntryException;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the o auth client entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientEntryUtil
 * @generated
 */
@ProviderType
public interface OAuthClientEntryPersistence
	extends BasePersistence<OAuthClientEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuthClientEntryUtil} to access the o auth client entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the o auth client entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the o auth client entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns the last o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns the last o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public OAuthClientEntry[] findByCompanyId_PrevAndNext(
			long oAuthClientEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns all the o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client entries that the user has permission to view
	 */
	public java.util.List<OAuthClientEntry> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	public java.util.List<OAuthClientEntry> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	public java.util.List<OAuthClientEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set of o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public OAuthClientEntry[] filterFindByCompanyId_PrevAndNext(
			long oAuthClientEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Removes all the o auth client entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of o auth client entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the o auth client entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByUserId(long userId);

	/**
	 * Returns a range of all the o auth client entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth client entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns the first o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns the last o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns the last o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public OAuthClientEntry[] findByUserId_PrevAndNext(
			long oAuthClientEntryId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns all the o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client entries that the user has permission to view
	 */
	public java.util.List<OAuthClientEntry> filterFindByUserId(long userId);

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	public java.util.List<OAuthClientEntry> filterFindByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	public java.util.List<OAuthClientEntry> filterFindByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set of o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public OAuthClientEntry[] filterFindByUserId_PrevAndNext(
			long oAuthClientEntryId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Removes all the o auth client entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of o auth client entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client entries
	 */
	public int countByUserId(long userId);

	/**
	 * Returns the number of o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	public int filterCountByUserId(long userId);

	/**
	 * Returns all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI);

	/**
	 * Returns a range of all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry findByC_A_First(
			long companyId, String authServerWellKnownURI,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry fetchByC_A_First(
		long companyId, String authServerWellKnownURI,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns the last o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry findByC_A_Last(
			long companyId, String authServerWellKnownURI,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns the last o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry fetchByC_A_Last(
		long companyId, String authServerWellKnownURI,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public OAuthClientEntry[] findByC_A_PrevAndNext(
			long oAuthClientEntryId, long companyId,
			String authServerWellKnownURI,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns all the o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the matching o auth client entries that the user has permission to view
	 */
	public java.util.List<OAuthClientEntry> filterFindByC_A(
		long companyId, String authServerWellKnownURI);

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	public java.util.List<OAuthClientEntry> filterFindByC_A(
		long companyId, String authServerWellKnownURI, int start, int end);

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	public java.util.List<OAuthClientEntry> filterFindByC_A(
		long companyId, String authServerWellKnownURI, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set of o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public OAuthClientEntry[] filterFindByC_A_PrevAndNext(
			long oAuthClientEntryId, long companyId,
			String authServerWellKnownURI,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
				orderByComparator)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Removes all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 */
	public void removeByC_A(long companyId, String authServerWellKnownURI);

	/**
	 * Returns the number of o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the number of matching o auth client entries
	 */
	public int countByC_A(long companyId, String authServerWellKnownURI);

	/**
	 * Returns the number of o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	public int filterCountByC_A(long companyId, String authServerWellKnownURI);

	/**
	 * Returns the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; or throws a <code>NoSuchOAuthClientEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry findByC_A_C(
			long companyId, String authServerWellKnownURI, String clientId)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry fetchByC_A_C(
		long companyId, String authServerWellKnownURI, String clientId);

	/**
	 * Returns the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public OAuthClientEntry fetchByC_A_C(
		long companyId, String authServerWellKnownURI, String clientId,
		boolean useFinderCache);

	/**
	 * Removes the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the o auth client entry that was removed
	 */
	public OAuthClientEntry removeByC_A_C(
			long companyId, String authServerWellKnownURI, String clientId)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns the number of o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the number of matching o auth client entries
	 */
	public int countByC_A_C(
		long companyId, String authServerWellKnownURI, String clientId);

	/**
	 * Caches the o auth client entry in the entity cache if it is enabled.
	 *
	 * @param oAuthClientEntry the o auth client entry
	 */
	public void cacheResult(OAuthClientEntry oAuthClientEntry);

	/**
	 * Caches the o auth client entries in the entity cache if it is enabled.
	 *
	 * @param oAuthClientEntries the o auth client entries
	 */
	public void cacheResult(
		java.util.List<OAuthClientEntry> oAuthClientEntries);

	/**
	 * Creates a new o auth client entry with the primary key. Does not add the o auth client entry to the database.
	 *
	 * @param oAuthClientEntryId the primary key for the new o auth client entry
	 * @return the new o auth client entry
	 */
	public OAuthClientEntry create(long oAuthClientEntryId);

	/**
	 * Removes the o auth client entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthClientEntryId the primary key of the o auth client entry
	 * @return the o auth client entry that was removed
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public OAuthClientEntry remove(long oAuthClientEntryId)
		throws NoSuchOAuthClientEntryException;

	public OAuthClientEntry updateImpl(OAuthClientEntry oAuthClientEntry);

	/**
	 * Returns the o auth client entry with the primary key or throws a <code>NoSuchOAuthClientEntryException</code> if it could not be found.
	 *
	 * @param oAuthClientEntryId the primary key of the o auth client entry
	 * @return the o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public OAuthClientEntry findByPrimaryKey(long oAuthClientEntryId)
		throws NoSuchOAuthClientEntryException;

	/**
	 * Returns the o auth client entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthClientEntryId the primary key of the o auth client entry
	 * @return the o auth client entry, or <code>null</code> if a o auth client entry with the primary key could not be found
	 */
	public OAuthClientEntry fetchByPrimaryKey(long oAuthClientEntryId);

	/**
	 * Returns all the o auth client entries.
	 *
	 * @return the o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findAll();

	/**
	 * Returns a range of all the o auth client entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the o auth client entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth client entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth client entries
	 */
	public java.util.List<OAuthClientEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthClientEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the o auth client entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of o auth client entries.
	 *
	 * @return the number of o auth client entries
	 */
	public int countAll();

}