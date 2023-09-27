/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.site.exception.NoSuchFriendlyURLException;
import com.liferay.site.model.SiteFriendlyURL;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the site friendly url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURLUtil
 * @generated
 */
@ProviderType
public interface SiteFriendlyURLPersistence
	extends BasePersistence<SiteFriendlyURL> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteFriendlyURLUtil} to access the site friendly url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the site friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByUuid(String uuid);

	/**
	 * Returns a range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
				orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
				orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public SiteFriendlyURL[] findByUuid_PrevAndNext(
			long siteFriendlyURLId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
				orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	 * Removes all the site friendly urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of site friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching site friendly urls
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByUUID_G(String uuid, long groupId)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the site friendly url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the site friendly url that was removed
	 */
	public SiteFriendlyURL removeByUUID_G(String uuid, long groupId)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the number of site friendly urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching site friendly urls
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
				orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
				orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public SiteFriendlyURL[] findByUuid_C_PrevAndNext(
			long siteFriendlyURLId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
				orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	 * Removes all the site friendly urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching site friendly urls
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByG_C(
		long groupId, long companyId);

	/**
	 * Returns a range of all the site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByG_C(
		long groupId, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByG_C(
		long groupId, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns an ordered range of all the site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findByG_C(
		long groupId, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByG_C_First(
			long groupId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
				orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the first site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByG_C_First(
		long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns the last site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByG_C_Last(
			long groupId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
				orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the last site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByG_C_Last(
		long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public SiteFriendlyURL[] findByG_C_PrevAndNext(
			long siteFriendlyURLId, long groupId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
				orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	 * Removes all the site friendly urls where groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 */
	public void removeByG_C(long groupId, long companyId);

	/**
	 * Returns the number of site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching site friendly urls
	 */
	public int countByG_C(long groupId, long companyId);

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByC_F(long companyId, String friendlyURL)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByC_F(long companyId, String friendlyURL);

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByC_F(
		long companyId, String friendlyURL, boolean useFinderCache);

	/**
	 * Removes the site friendly url where companyId = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the site friendly url that was removed
	 */
	public SiteFriendlyURL removeByC_F(long companyId, String friendlyURL)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and friendlyURL = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the number of matching site friendly urls
	 */
	public int countByC_F(long companyId, String friendlyURL);

	/**
	 * Returns the site friendly url where groupId = &#63; and companyId = &#63; and languageId = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByG_C_L(
			long groupId, long companyId, String languageId)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the site friendly url where groupId = &#63; and companyId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByG_C_L(
		long groupId, long companyId, String languageId);

	/**
	 * Returns the site friendly url where groupId = &#63; and companyId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByG_C_L(
		long groupId, long companyId, String languageId,
		boolean useFinderCache);

	/**
	 * Removes the site friendly url where groupId = &#63; and companyId = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the site friendly url that was removed
	 */
	public SiteFriendlyURL removeByG_C_L(
			long groupId, long companyId, String languageId)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the number of site friendly urls where groupId = &#63; and companyId = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the number of matching site friendly urls
	 */
	public int countByG_C_L(long groupId, long companyId, String languageId);

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL findByC_F_L(
			long companyId, String friendlyURL, String languageId)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByC_F_L(
		long companyId, String friendlyURL, String languageId);

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public SiteFriendlyURL fetchByC_F_L(
		long companyId, String friendlyURL, String languageId,
		boolean useFinderCache);

	/**
	 * Removes the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the site friendly url that was removed
	 */
	public SiteFriendlyURL removeByC_F_L(
			long companyId, String friendlyURL, String languageId)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and friendlyURL = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the number of matching site friendly urls
	 */
	public int countByC_F_L(
		long companyId, String friendlyURL, String languageId);

	/**
	 * Caches the site friendly url in the entity cache if it is enabled.
	 *
	 * @param siteFriendlyURL the site friendly url
	 */
	public void cacheResult(SiteFriendlyURL siteFriendlyURL);

	/**
	 * Caches the site friendly urls in the entity cache if it is enabled.
	 *
	 * @param siteFriendlyURLs the site friendly urls
	 */
	public void cacheResult(java.util.List<SiteFriendlyURL> siteFriendlyURLs);

	/**
	 * Creates a new site friendly url with the primary key. Does not add the site friendly url to the database.
	 *
	 * @param siteFriendlyURLId the primary key for the new site friendly url
	 * @return the new site friendly url
	 */
	public SiteFriendlyURL create(long siteFriendlyURLId);

	/**
	 * Removes the site friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url that was removed
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public SiteFriendlyURL remove(long siteFriendlyURLId)
		throws NoSuchFriendlyURLException;

	public SiteFriendlyURL updateImpl(SiteFriendlyURL siteFriendlyURL);

	/**
	 * Returns the site friendly url with the primary key or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public SiteFriendlyURL findByPrimaryKey(long siteFriendlyURLId)
		throws NoSuchFriendlyURLException;

	/**
	 * Returns the site friendly url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url, or <code>null</code> if a site friendly url with the primary key could not be found
	 */
	public SiteFriendlyURL fetchByPrimaryKey(long siteFriendlyURLId);

	/**
	 * Returns all the site friendly urls.
	 *
	 * @return the site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findAll();

	/**
	 * Returns a range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator);

	/**
	 * Returns an ordered range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of site friendly urls
	 */
	public java.util.List<SiteFriendlyURL> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteFriendlyURL>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the site friendly urls from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of site friendly urls.
	 *
	 * @return the number of site friendly urls
	 */
	public int countAll();

}