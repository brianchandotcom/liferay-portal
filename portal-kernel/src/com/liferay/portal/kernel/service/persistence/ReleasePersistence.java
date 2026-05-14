/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchReleaseException;
import com.liferay.portal.kernel.model.Release;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the release service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReleaseUtil
 * @generated
 */
@ProviderType
public interface ReleasePersistence extends BasePersistence<Release> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ReleaseUtil} to access the release persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the release where servletContextName = &#63; or throws a <code>NoSuchReleaseException</code> if it could not be found.
	 *
	 * @param servletContextName the servlet context name
	 * @return the matching release
	 * @throws NoSuchReleaseException if a matching release could not be found
	 */
	public Release findByServletContextName(String servletContextName)
		throws NoSuchReleaseException;

	/**
	 * Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param servletContextName the servlet context name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching release, or <code>null</code> if a matching release could not be found
	 */
	public Release fetchByServletContextName(
		String servletContextName, boolean useFinderCache);

	/**
	 * Removes the release where servletContextName = &#63; from the database.
	 *
	 * @param servletContextName the servlet context name
	 * @return the release that was removed
	 */
	public Release removeByServletContextName(String servletContextName)
		throws NoSuchReleaseException;

	/**
	 * Returns the number of releases where servletContextName = &#63;.
	 *
	 * @param servletContextName the servlet context name
	 * @return the number of matching releases
	 */
	public int countByServletContextName(String servletContextName);

	/**
	 * Creates a new release with the primary key. Does not add the release to the database.
	 *
	 * @param releaseId the primary key for the new release
	 * @return the new release
	 */
	public Release create(long releaseId);

	/**
	 * Removes the release with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release that was removed
	 * @throws NoSuchReleaseException if a release with the primary key could not be found
	 */
	public Release remove(long releaseId) throws NoSuchReleaseException;

	public Release updateImpl(Release release);

	/**
	 * Returns the release with the primary key or throws a <code>NoSuchReleaseException</code> if it could not be found.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release
	 * @throws NoSuchReleaseException if a release with the primary key could not be found
	 */
	public Release findByPrimaryKey(long releaseId)
		throws NoSuchReleaseException;

	/**
	 * Returns the release with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release, or <code>null</code> if a release with the primary key could not be found
	 */
	public Release fetchByPrimaryKey(long releaseId);

	/**
	 * Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param servletContextName the servlet context name
	 * @return the matching release, or <code>null</code> if a matching release could not be found
	 */
	public default Release fetchByServletContextName(
		String servletContextName) {

		return fetchByServletContextName(servletContextName, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1574696641