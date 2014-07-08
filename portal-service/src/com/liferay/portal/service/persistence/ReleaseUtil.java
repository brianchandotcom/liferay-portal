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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.Release;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the release service. This utility wraps {@link ReleasePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReleasePersistence
 * @see ReleasePersistenceImpl
 * @generated
 */
@ProviderType
public class ReleaseUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Release release) {
		getPersistence().clearCache(release);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Release> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Release> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Release> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Release update(Release release) {
		return getPersistence().update(release);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Release update(Release release, ServiceContext serviceContext) {
		return getPersistence().update(release, serviceContext);
	}

	/**
	* Returns the release where servletContextName = &#63; or throws a {@link com.liferay.portal.NoSuchReleaseException} if it could not be found.
	*
	* @param servletContextName the servlet context name
	* @return the matching release
	* @throws com.liferay.portal.NoSuchReleaseException if a matching release could not be found
	*/
	public static com.liferay.portal.model.Release findByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException {
		return getPersistence().findByServletContextName(servletContextName);
	}

	/**
	* Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param servletContextName the servlet context name
	* @return the matching release, or <code>null</code> if a matching release could not be found
	*/
	public static com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName) {
		return getPersistence().fetchByServletContextName(servletContextName);
	}

	/**
	* Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param servletContextName the servlet context name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching release, or <code>null</code> if a matching release could not be found
	*/
	public static com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByServletContextName(servletContextName,
			retrieveFromCache);
	}

	/**
	* Removes the release where servletContextName = &#63; from the database.
	*
	* @param servletContextName the servlet context name
	* @return the release that was removed
	*/
	public static com.liferay.portal.model.Release removeByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException {
		return getPersistence().removeByServletContextName(servletContextName);
	}

	/**
	* Returns the number of releases where servletContextName = &#63;.
	*
	* @param servletContextName the servlet context name
	* @return the number of matching releases
	*/
	public static int countByServletContextName(
		java.lang.String servletContextName) {
		return getPersistence().countByServletContextName(servletContextName);
	}

	/**
	* Caches the release in the entity cache if it is enabled.
	*
	* @param release the release
	*/
	public static void cacheResult(com.liferay.portal.model.Release release) {
		getPersistence().cacheResult(release);
	}

	/**
	* Caches the releases in the entity cache if it is enabled.
	*
	* @param releases the releases
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Release> releases) {
		getPersistence().cacheResult(releases);
	}

	/**
	* Creates a new release with the primary key. Does not add the release to the database.
	*
	* @param releaseId the primary key for the new release
	* @return the new release
	*/
	public static com.liferay.portal.model.Release create(long releaseId) {
		return getPersistence().create(releaseId);
	}

	/**
	* Removes the release with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param releaseId the primary key of the release
	* @return the release that was removed
	* @throws com.liferay.portal.NoSuchReleaseException if a release with the primary key could not be found
	*/
	public static com.liferay.portal.model.Release remove(long releaseId)
		throws com.liferay.portal.NoSuchReleaseException {
		return getPersistence().remove(releaseId);
	}

	public static com.liferay.portal.model.Release updateImpl(
		com.liferay.portal.model.Release release) {
		return getPersistence().updateImpl(release);
	}

	/**
	* Returns the release with the primary key or throws a {@link com.liferay.portal.NoSuchReleaseException} if it could not be found.
	*
	* @param releaseId the primary key of the release
	* @return the release
	* @throws com.liferay.portal.NoSuchReleaseException if a release with the primary key could not be found
	*/
	public static com.liferay.portal.model.Release findByPrimaryKey(
		long releaseId) throws com.liferay.portal.NoSuchReleaseException {
		return getPersistence().findByPrimaryKey(releaseId);
	}

	/**
	* Returns the release with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param releaseId the primary key of the release
	* @return the release, or <code>null</code> if a release with the primary key could not be found
	*/
	public static com.liferay.portal.model.Release fetchByPrimaryKey(
		long releaseId) {
		return getPersistence().fetchByPrimaryKey(releaseId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.Release> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the releases.
	*
	* @return the releases
	*/
	public static java.util.List<com.liferay.portal.model.Release> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the releases.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ReleaseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of releases
	* @param end the upper bound of the range of releases (not inclusive)
	* @return the range of releases
	*/
	public static java.util.List<com.liferay.portal.model.Release> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the releases.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ReleaseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of releases
	* @param end the upper bound of the range of releases (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of releases
	*/
	public static java.util.List<com.liferay.portal.model.Release> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the releases from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of releases.
	*
	* @return the number of releases
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ReleasePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ReleasePersistence)PortalBeanLocatorUtil.locate(ReleasePersistence.class.getName());

			ReferenceRegistry.registerReference(ReleaseUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ReleasePersistence persistence) {
	}

	private static ReleasePersistence _persistence;
}