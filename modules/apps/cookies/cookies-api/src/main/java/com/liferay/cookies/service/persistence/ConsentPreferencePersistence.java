/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service.persistence;

import com.liferay.cookies.exception.NoSuchConsentPreferenceException;
import com.liferay.cookies.model.ConsentPreference;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the consent preference service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ConsentPreferenceUtil
 * @generated
 */
@ProviderType
public interface ConsentPreferencePersistence
	extends BasePersistence<ConsentPreference> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ConsentPreferenceUtil} to access the consent preference persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the consent preferences where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByUserId(long userId);

	/**
	 * Returns a range of all the consent preferences where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @return the range of matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the consent preferences where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns an ordered range of all the consent preferences where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first consent preference in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	public ConsentPreference findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
				orderByComparator)
		throws NoSuchConsentPreferenceException;

	/**
	 * Returns the first consent preference in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	public ConsentPreference fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns the last consent preference in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	public ConsentPreference findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
				orderByComparator)
		throws NoSuchConsentPreferenceException;

	/**
	 * Returns the last consent preference in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	public ConsentPreference fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns the consent preferences before and after the current consent preference in the ordered set where userId = &#63;.
	 *
	 * @param consentPreferenceId the primary key of the current consent preference
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next consent preference
	 * @throws NoSuchConsentPreferenceException if a consent preference with the primary key could not be found
	 */
	public ConsentPreference[] findByUserId_PrevAndNext(
			long consentPreferenceId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
				orderByComparator)
		throws NoSuchConsentPreferenceException;

	/**
	 * Removes all the consent preferences where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of consent preferences where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching consent preferences
	 */
	public int countByUserId(long userId);

	/**
	 * Returns all the consent preferences where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByExpirationDate(
		Date expirationDate);

	/**
	 * Returns a range of all the consent preferences where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @return the range of matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByExpirationDate(
		Date expirationDate, int start, int end);

	/**
	 * Returns an ordered range of all the consent preferences where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByExpirationDate(
		Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns an ordered range of all the consent preferences where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByExpirationDate(
		Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first consent preference in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	public ConsentPreference findByExpirationDate_First(
			Date expirationDate,
			com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
				orderByComparator)
		throws NoSuchConsentPreferenceException;

	/**
	 * Returns the first consent preference in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	public ConsentPreference fetchByExpirationDate_First(
		Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns the last consent preference in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	public ConsentPreference findByExpirationDate_Last(
			Date expirationDate,
			com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
				orderByComparator)
		throws NoSuchConsentPreferenceException;

	/**
	 * Returns the last consent preference in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	public ConsentPreference fetchByExpirationDate_Last(
		Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns the consent preferences before and after the current consent preference in the ordered set where expirationDate = &#63;.
	 *
	 * @param consentPreferenceId the primary key of the current consent preference
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next consent preference
	 * @throws NoSuchConsentPreferenceException if a consent preference with the primary key could not be found
	 */
	public ConsentPreference[] findByExpirationDate_PrevAndNext(
			long consentPreferenceId, Date expirationDate,
			com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
				orderByComparator)
		throws NoSuchConsentPreferenceException;

	/**
	 * Removes all the consent preferences where expirationDate = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	public void removeByExpirationDate(Date expirationDate);

	/**
	 * Returns the number of consent preferences where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching consent preferences
	 */
	public int countByExpirationDate(Date expirationDate);

	/**
	 * Returns all the consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @return the matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByU_D(
		long userId, String domain);

	/**
	 * Returns a range of all the consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @return the range of matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByU_D(
		long userId, String domain, int start, int end);

	/**
	 * Returns an ordered range of all the consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByU_D(
		long userId, String domain, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns an ordered range of all the consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching consent preferences
	 */
	public java.util.List<ConsentPreference> findByU_D(
		long userId, String domain, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first consent preference in the ordered set where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	public ConsentPreference findByU_D_First(
			long userId, String domain,
			com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
				orderByComparator)
		throws NoSuchConsentPreferenceException;

	/**
	 * Returns the first consent preference in the ordered set where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	public ConsentPreference fetchByU_D_First(
		long userId, String domain,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns the last consent preference in the ordered set where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	public ConsentPreference findByU_D_Last(
			long userId, String domain,
			com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
				orderByComparator)
		throws NoSuchConsentPreferenceException;

	/**
	 * Returns the last consent preference in the ordered set where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	public ConsentPreference fetchByU_D_Last(
		long userId, String domain,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns the consent preferences before and after the current consent preference in the ordered set where userId = &#63; and domain = &#63;.
	 *
	 * @param consentPreferenceId the primary key of the current consent preference
	 * @param userId the user ID
	 * @param domain the domain
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next consent preference
	 * @throws NoSuchConsentPreferenceException if a consent preference with the primary key could not be found
	 */
	public ConsentPreference[] findByU_D_PrevAndNext(
			long consentPreferenceId, long userId, String domain,
			com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
				orderByComparator)
		throws NoSuchConsentPreferenceException;

	/**
	 * Removes all the consent preferences where userId = &#63; and domain = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 */
	public void removeByU_D(long userId, String domain);

	/**
	 * Returns the number of consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @return the number of matching consent preferences
	 */
	public int countByU_D(long userId, String domain);

	/**
	 * Returns the consent preference where userId = &#63; and domain = &#63; and name = &#63; or throws a <code>NoSuchConsentPreferenceException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @return the matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	public ConsentPreference findByU_D_N(
			long userId, String domain, String name)
		throws NoSuchConsentPreferenceException;

	/**
	 * Returns the consent preference where userId = &#63; and domain = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @return the matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	public ConsentPreference fetchByU_D_N(
		long userId, String domain, String name);

	/**
	 * Returns the consent preference where userId = &#63; and domain = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	public ConsentPreference fetchByU_D_N(
		long userId, String domain, String name, boolean useFinderCache);

	/**
	 * Removes the consent preference where userId = &#63; and domain = &#63; and name = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @return the consent preference that was removed
	 */
	public ConsentPreference removeByU_D_N(
			long userId, String domain, String name)
		throws NoSuchConsentPreferenceException;

	/**
	 * Returns the number of consent preferences where userId = &#63; and domain = &#63; and name = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @return the number of matching consent preferences
	 */
	public int countByU_D_N(long userId, String domain, String name);

	/**
	 * Caches the consent preference in the entity cache if it is enabled.
	 *
	 * @param consentPreference the consent preference
	 */
	public void cacheResult(ConsentPreference consentPreference);

	/**
	 * Caches the consent preferences in the entity cache if it is enabled.
	 *
	 * @param consentPreferences the consent preferences
	 */
	public void cacheResult(
		java.util.List<ConsentPreference> consentPreferences);

	/**
	 * Creates a new consent preference with the primary key. Does not add the consent preference to the database.
	 *
	 * @param consentPreferenceId the primary key for the new consent preference
	 * @return the new consent preference
	 */
	public ConsentPreference create(long consentPreferenceId);

	/**
	 * Removes the consent preference with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param consentPreferenceId the primary key of the consent preference
	 * @return the consent preference that was removed
	 * @throws NoSuchConsentPreferenceException if a consent preference with the primary key could not be found
	 */
	public ConsentPreference remove(long consentPreferenceId)
		throws NoSuchConsentPreferenceException;

	public ConsentPreference updateImpl(ConsentPreference consentPreference);

	/**
	 * Returns the consent preference with the primary key or throws a <code>NoSuchConsentPreferenceException</code> if it could not be found.
	 *
	 * @param consentPreferenceId the primary key of the consent preference
	 * @return the consent preference
	 * @throws NoSuchConsentPreferenceException if a consent preference with the primary key could not be found
	 */
	public ConsentPreference findByPrimaryKey(long consentPreferenceId)
		throws NoSuchConsentPreferenceException;

	/**
	 * Returns the consent preference with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param consentPreferenceId the primary key of the consent preference
	 * @return the consent preference, or <code>null</code> if a consent preference with the primary key could not be found
	 */
	public ConsentPreference fetchByPrimaryKey(long consentPreferenceId);

	/**
	 * Returns all the consent preferences.
	 *
	 * @return the consent preferences
	 */
	public java.util.List<ConsentPreference> findAll();

	/**
	 * Returns a range of all the consent preferences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @return the range of consent preferences
	 */
	public java.util.List<ConsentPreference> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the consent preferences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of consent preferences
	 */
	public java.util.List<ConsentPreference> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator);

	/**
	 * Returns an ordered range of all the consent preferences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of consent preferences
	 */
	public java.util.List<ConsentPreference> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConsentPreference>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the consent preferences from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of consent preferences.
	 *
	 * @return the number of consent preferences
	 */
	public int countAll();

}
// LIFERAY-SERVICE-BUILDER-HASH:-2147092528