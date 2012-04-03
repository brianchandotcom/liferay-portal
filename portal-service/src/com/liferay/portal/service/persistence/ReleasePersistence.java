/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Release;

/**
 * The persistence interface for the release service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReleasePersistenceImpl
 * @see ReleaseUtil
 * @generated
 */
public interface ReleasePersistence extends BasePersistence<Release> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ReleaseUtil} to access the release persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the release in the entity cache if it is enabled.
	*
	* @param release the release
	*/
	public void cacheResult(com.liferay.portal.model.Release release);

	/**
	* Caches the releases in the entity cache if it is enabled.
	*
	* @param releases the releases
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Release> releases);

	/**
	* Creates a new release with the primary key. Does not add the release to the database.
	*
	* @param releaseId the primary key for the new release
	* @return the new release
	*/
	public com.liferay.portal.model.Release create(long releaseId);

	/**
	* Removes the release with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param releaseId the primary key of the release
	* @return the release that was removed
	* @throws com.liferay.portal.NoSuchReleaseException if a release with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release remove(long releaseId)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Release updateImpl(
		com.liferay.portal.model.Release release, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the release with the primary key or throws a {@link com.liferay.portal.NoSuchReleaseException} if it could not be found.
	*
	* @param releaseId the primary key of the release
	* @return the release
	* @throws com.liferay.portal.NoSuchReleaseException if a release with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release findByPrimaryKey(long releaseId)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the release with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param releaseId the primary key of the release
	* @return the release, or <code>null</code> if a release with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release fetchByPrimaryKey(long releaseId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the release where servletContextName = &#63; or throws a {@link com.liferay.portal.NoSuchReleaseException} if it could not be found.
	*
	* @param servletContextName the servlet context name
	* @return the matching release
	* @throws com.liferay.portal.NoSuchReleaseException if a matching release could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release findByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param servletContextName the servlet context name
	* @return the matching release, or <code>null</code> if a matching release could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param servletContextName the servlet context name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching release, or <code>null</code> if a matching release could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the releases.
	*
	* @return the releases
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Release> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the releases.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of releases
	* @param end the upper bound of the range of releases (not inclusive)
	* @return the range of releases
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Release> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the releases.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of releases
	* @param end the upper bound of the range of releases (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of releases
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Release> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the release where servletContextName = &#63; from the database.
	*
	* @param servletContextName the servlet context name
	* @return the release that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release removeByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the releases from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of releases where servletContextName = &#63;.
	*
	* @param servletContextName the servlet context name
	* @return the number of matching releases
	* @throws SystemException if a system exception occurred
	*/
	public int countByServletContextName(java.lang.String servletContextName)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of releases.
	*
	* @return the number of releases
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}