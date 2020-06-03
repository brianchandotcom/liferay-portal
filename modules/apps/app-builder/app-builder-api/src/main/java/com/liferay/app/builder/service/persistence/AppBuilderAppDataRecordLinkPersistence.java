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

package com.liferay.app.builder.service.persistence;

import com.liferay.app.builder.exception.NoSuchAppDataRecordLinkException;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the app builder app data record link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDataRecordLinkUtil
 * @generated
 */
@ProviderType
public interface AppBuilderAppDataRecordLinkPersistence
	extends BasePersistence<AppBuilderAppDataRecordLink> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AppBuilderAppDataRecordLinkUtil} to access the app builder app data record link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder app data record links
	 */
	public java.util.List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId);

	/**
	 * Returns a range of all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of matching app builder app data record links
	 */
	public java.util.List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end);

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app data record links
	 */
	public java.util.List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDataRecordLink> orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app data record links
	 */
	public java.util.List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDataRecordLink> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	public AppBuilderAppDataRecordLink findByAppBuilderAppId_First(
			long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppDataRecordLink> orderByComparator)
		throws NoSuchAppDataRecordLinkException;

	/**
	 * Returns the first app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	public AppBuilderAppDataRecordLink fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDataRecordLink> orderByComparator);

	/**
	 * Returns the last app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	public AppBuilderAppDataRecordLink findByAppBuilderAppId_Last(
			long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppDataRecordLink> orderByComparator)
		throws NoSuchAppDataRecordLinkException;

	/**
	 * Returns the last app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	public AppBuilderAppDataRecordLink fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDataRecordLink> orderByComparator);

	/**
	 * Returns the app builder app data record links before and after the current app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the current app builder app data record link
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	public AppBuilderAppDataRecordLink[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderAppDataRecordLinkId, long appBuilderAppId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AppBuilderAppDataRecordLink> orderByComparator)
		throws NoSuchAppDataRecordLinkException;

	/**
	 * Removes all the app builder app data record links where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	public void removeByAppBuilderAppId(long appBuilderAppId);

	/**
	 * Returns the number of app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder app data record links
	 */
	public int countByAppBuilderAppId(long appBuilderAppId);

	/**
	 * Returns the app builder app data record link where ddlRecordId = &#63; or throws a <code>NoSuchAppDataRecordLinkException</code> if it could not be found.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	public AppBuilderAppDataRecordLink findByDDLRecordId(long ddlRecordId)
		throws NoSuchAppDataRecordLinkException;

	/**
	 * Returns the app builder app data record link where ddlRecordId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	public AppBuilderAppDataRecordLink fetchByDDLRecordId(long ddlRecordId);

	/**
	 * Returns the app builder app data record link where ddlRecordId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	public AppBuilderAppDataRecordLink fetchByDDLRecordId(
		long ddlRecordId, boolean useFinderCache);

	/**
	 * Removes the app builder app data record link where ddlRecordId = &#63; from the database.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the app builder app data record link that was removed
	 */
	public AppBuilderAppDataRecordLink removeByDDLRecordId(long ddlRecordId)
		throws NoSuchAppDataRecordLinkException;

	/**
	 * Returns the number of app builder app data record links where ddlRecordId = &#63;.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the number of matching app builder app data record links
	 */
	public int countByDDLRecordId(long ddlRecordId);

	/**
	 * Caches the app builder app data record link in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 */
	public void cacheResult(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink);

	/**
	 * Caches the app builder app data record links in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDataRecordLinks the app builder app data record links
	 */
	public void cacheResult(
		java.util.List<AppBuilderAppDataRecordLink>
			appBuilderAppDataRecordLinks);

	/**
	 * Creates a new app builder app data record link with the primary key. Does not add the app builder app data record link to the database.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key for the new app builder app data record link
	 * @return the new app builder app data record link
	 */
	public AppBuilderAppDataRecordLink create(
		long appBuilderAppDataRecordLinkId);

	/**
	 * Removes the app builder app data record link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link that was removed
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	public AppBuilderAppDataRecordLink remove(
			long appBuilderAppDataRecordLinkId)
		throws NoSuchAppDataRecordLinkException;

	public AppBuilderAppDataRecordLink updateImpl(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink);

	/**
	 * Returns the app builder app data record link with the primary key or throws a <code>NoSuchAppDataRecordLinkException</code> if it could not be found.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	public AppBuilderAppDataRecordLink findByPrimaryKey(
			long appBuilderAppDataRecordLinkId)
		throws NoSuchAppDataRecordLinkException;

	/**
	 * Returns the app builder app data record link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link, or <code>null</code> if a app builder app data record link with the primary key could not be found
	 */
	public AppBuilderAppDataRecordLink fetchByPrimaryKey(
		long appBuilderAppDataRecordLinkId);

	/**
	 * Returns all the app builder app data record links.
	 *
	 * @return the app builder app data record links
	 */
	public java.util.List<AppBuilderAppDataRecordLink> findAll();

	/**
	 * Returns a range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of app builder app data record links
	 */
	public java.util.List<AppBuilderAppDataRecordLink> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder app data record links
	 */
	public java.util.List<AppBuilderAppDataRecordLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDataRecordLink> orderByComparator);

	/**
	 * Returns an ordered range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder app data record links
	 */
	public java.util.List<AppBuilderAppDataRecordLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AppBuilderAppDataRecordLink> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the app builder app data record links from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of app builder app data record links.
	 *
	 * @return the number of app builder app data record links
	 */
	public int countAll();

}