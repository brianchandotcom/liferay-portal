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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContentVersion;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the versioned entry content version service. This utility wraps {@link com.liferay.portal.tools.service.builder.test.service.persistence.impl.VersionedEntryContentVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryContentVersionPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.VersionedEntryContentVersionPersistenceImpl
 * @generated
 */
@ProviderType
public class VersionedEntryContentVersionUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		getPersistence().clearCache(versionedEntryContentVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<VersionedEntryContentVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<VersionedEntryContentVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<VersionedEntryContentVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static VersionedEntryContentVersion update(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		return getPersistence().update(versionedEntryContentVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static VersionedEntryContentVersion update(
		VersionedEntryContentVersion versionedEntryContentVersion,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(versionedEntryContentVersion, serviceContext);
	}

	/**
	* Returns all the versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @return the matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId) {
		return getPersistence()
				   .findByVersionedEntryContentId(versionedEntryContentId);
	}

	/**
	* Returns a range of all the versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId, int start, int end) {
		return getPersistence()
				   .findByVersionedEntryContentId(versionedEntryContentId,
			start, end);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .findByVersionedEntryContentId(versionedEntryContentId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByVersionedEntryContentId(versionedEntryContentId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryContentId_First(
		long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryContentId_First(versionedEntryContentId,
			orderByComparator);
	}

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryContentId_First(
		long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryContentId_First(versionedEntryContentId,
			orderByComparator);
	}

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryContentId_Last(
		long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryContentId_Last(versionedEntryContentId,
			orderByComparator);
	}

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryContentId_Last(
		long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryContentId_Last(versionedEntryContentId,
			orderByComparator);
	}

	/**
	* Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public static VersionedEntryContentVersion[] findByVersionedEntryContentId_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryContentId_PrevAndNext(versionedEntryContentVersionId,
			versionedEntryContentId, orderByComparator);
	}

	/**
	* Removes all the versioned entry content versions where versionedEntryContentId = &#63; from the database.
	*
	* @param versionedEntryContentId the versioned entry content ID
	*/
	public static void removeByVersionedEntryContentId(
		long versionedEntryContentId) {
		getPersistence().removeByVersionedEntryContentId(versionedEntryContentId);
	}

	/**
	* Returns the number of versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @return the number of matching versioned entry content versions
	*/
	public static int countByVersionedEntryContentId(
		long versionedEntryContentId) {
		return getPersistence()
				   .countByVersionedEntryContentId(versionedEntryContentId);
	}

	/**
	* Returns the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; or throws a {@link NoSuchVersionedEntryContentVersionException} if it could not be found.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @return the matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryContentId_Version(versionedEntryContentId,
			version);
	}

	/**
	* Returns the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version) {
		return getPersistence()
				   .fetchByVersionedEntryContentId_Version(versionedEntryContentId,
			version);
	}

	/**
	* Returns the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByVersionedEntryContentId_Version(versionedEntryContentId,
			version, retrieveFromCache);
	}

	/**
	* Removes the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; from the database.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @return the versioned entry content version that was removed
	*/
	public static VersionedEntryContentVersion removeByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .removeByVersionedEntryContentId_Version(versionedEntryContentId,
			version);
	}

	/**
	* Returns the number of versioned entry content versions where versionedEntryContentId = &#63; and version = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @return the number of matching versioned entry content versions
	*/
	public static int countByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version) {
		return getPersistence()
				   .countByVersionedEntryContentId_Version(versionedEntryContentId,
			version);
	}

	/**
	* Returns all the versioned entry content versions where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @return the matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId) {
		return getPersistence().findByVersionedEntryId(versionedEntryId);
	}

	/**
	* Returns a range of all the versioned entry content versions where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end) {
		return getPersistence()
				   .findByVersionedEntryId(versionedEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .findByVersionedEntryId(versionedEntryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByVersionedEntryId(versionedEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_First(versionedEntryId,
			orderByComparator);
	}

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryId_First(versionedEntryId,
			orderByComparator);
	}

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryId_Last(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_Last(versionedEntryId,
			orderByComparator);
	}

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryId_Last(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryId_Last(versionedEntryId,
			orderByComparator);
	}

	/**
	* Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public static VersionedEntryContentVersion[] findByVersionedEntryId_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_PrevAndNext(versionedEntryContentVersionId,
			versionedEntryId, orderByComparator);
	}

	/**
	* Removes all the versioned entry content versions where versionedEntryId = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	*/
	public static void removeByVersionedEntryId(long versionedEntryId) {
		getPersistence().removeByVersionedEntryId(versionedEntryId);
	}

	/**
	* Returns the number of versioned entry content versions where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @return the number of matching versioned entry content versions
	*/
	public static int countByVersionedEntryId(long versionedEntryId) {
		return getPersistence().countByVersionedEntryId(versionedEntryId);
	}

	/**
	* Returns all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @return the matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version) {
		return getPersistence()
				   .findByVersionedEntryId_Version(versionedEntryId, version);
	}

	/**
	* Returns a range of all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version, int start, int end) {
		return getPersistence()
				   .findByVersionedEntryId_Version(versionedEntryId, version,
			start, end);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .findByVersionedEntryId_Version(versionedEntryId, version,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByVersionedEntryId_Version(versionedEntryId, version,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryId_Version_First(
		long versionedEntryId, int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_Version_First(versionedEntryId,
			version, orderByComparator);
	}

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryId_Version_First(
		long versionedEntryId, int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryId_Version_First(versionedEntryId,
			version, orderByComparator);
	}

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryId_Version_Last(
		long versionedEntryId, int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_Version_Last(versionedEntryId,
			version, orderByComparator);
	}

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryId_Version_Last(
		long versionedEntryId, int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryId_Version_Last(versionedEntryId,
			version, orderByComparator);
	}

	/**
	* Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public static VersionedEntryContentVersion[] findByVersionedEntryId_Version_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryId,
		int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_Version_PrevAndNext(versionedEntryContentVersionId,
			versionedEntryId, version, orderByComparator);
	}

	/**
	* Removes all the versioned entry content versions where versionedEntryId = &#63; and version = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	*/
	public static void removeByVersionedEntryId_Version(long versionedEntryId,
		int version) {
		getPersistence()
			.removeByVersionedEntryId_Version(versionedEntryId, version);
	}

	/**
	* Returns the number of versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @return the number of matching versioned entry content versions
	*/
	public static int countByVersionedEntryId_Version(long versionedEntryId,
		int version) {
		return getPersistence()
				   .countByVersionedEntryId_Version(versionedEntryId, version);
	}

	/**
	* Returns all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId) {
		return getPersistence()
				   .findByVersionedEntryId_LanguageId(versionedEntryId,
			languageId);
	}

	/**
	* Returns a range of all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId, int start, int end) {
		return getPersistence()
				   .findByVersionedEntryId_LanguageId(versionedEntryId,
			languageId, start, end);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .findByVersionedEntryId_LanguageId(versionedEntryId,
			languageId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByVersionedEntryId_LanguageId(versionedEntryId,
			languageId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryId_LanguageId_First(
		long versionedEntryId, java.lang.String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_LanguageId_First(versionedEntryId,
			languageId, orderByComparator);
	}

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_First(
		long versionedEntryId, java.lang.String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryId_LanguageId_First(versionedEntryId,
			languageId, orderByComparator);
	}

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryId_LanguageId_Last(
		long versionedEntryId, java.lang.String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_LanguageId_Last(versionedEntryId,
			languageId, orderByComparator);
	}

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_Last(
		long versionedEntryId, java.lang.String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence()
				   .fetchByVersionedEntryId_LanguageId_Last(versionedEntryId,
			languageId, orderByComparator);
	}

	/**
	* Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public static VersionedEntryContentVersion[] findByVersionedEntryId_LanguageId_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryId,
		java.lang.String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_LanguageId_PrevAndNext(versionedEntryContentVersionId,
			versionedEntryId, languageId, orderByComparator);
	}

	/**
	* Removes all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	*/
	public static void removeByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId) {
		getPersistence()
			.removeByVersionedEntryId_LanguageId(versionedEntryId, languageId);
	}

	/**
	* Returns the number of versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the number of matching versioned entry content versions
	*/
	public static int countByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId) {
		return getPersistence()
				   .countByVersionedEntryId_LanguageId(versionedEntryId,
			languageId);
	}

	/**
	* Returns the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; or throws a {@link NoSuchVersionedEntryContentVersionException} if it could not be found.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @return the matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion findByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .findByVersionedEntryId_LanguageId_Version(versionedEntryId,
			languageId, version);
	}

	/**
	* Returns the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version) {
		return getPersistence()
				   .fetchByVersionedEntryId_LanguageId_Version(versionedEntryId,
			languageId, version);
	}

	/**
	* Returns the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public static VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByVersionedEntryId_LanguageId_Version(versionedEntryId,
			languageId, version, retrieveFromCache);
	}

	/**
	* Removes the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @return the versioned entry content version that was removed
	*/
	public static VersionedEntryContentVersion removeByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence()
				   .removeByVersionedEntryId_LanguageId_Version(versionedEntryId,
			languageId, version);
	}

	/**
	* Returns the number of versioned entry content versions where versionedEntryId = &#63; and languageId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @return the number of matching versioned entry content versions
	*/
	public static int countByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version) {
		return getPersistence()
				   .countByVersionedEntryId_LanguageId_Version(versionedEntryId,
			languageId, version);
	}

	/**
	* Caches the versioned entry content version in the entity cache if it is enabled.
	*
	* @param versionedEntryContentVersion the versioned entry content version
	*/
	public static void cacheResult(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		getPersistence().cacheResult(versionedEntryContentVersion);
	}

	/**
	* Caches the versioned entry content versions in the entity cache if it is enabled.
	*
	* @param versionedEntryContentVersions the versioned entry content versions
	*/
	public static void cacheResult(
		List<VersionedEntryContentVersion> versionedEntryContentVersions) {
		getPersistence().cacheResult(versionedEntryContentVersions);
	}

	/**
	* Creates a new versioned entry content version with the primary key. Does not add the versioned entry content version to the database.
	*
	* @param versionedEntryContentVersionId the primary key for the new versioned entry content version
	* @return the new versioned entry content version
	*/
	public static VersionedEntryContentVersion create(
		long versionedEntryContentVersionId) {
		return getPersistence().create(versionedEntryContentVersionId);
	}

	/**
	* Removes the versioned entry content version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param versionedEntryContentVersionId the primary key of the versioned entry content version
	* @return the versioned entry content version that was removed
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public static VersionedEntryContentVersion remove(
		long versionedEntryContentVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence().remove(versionedEntryContentVersionId);
	}

	public static VersionedEntryContentVersion updateImpl(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		return getPersistence().updateImpl(versionedEntryContentVersion);
	}

	/**
	* Returns the versioned entry content version with the primary key or throws a {@link NoSuchVersionedEntryContentVersionException} if it could not be found.
	*
	* @param versionedEntryContentVersionId the primary key of the versioned entry content version
	* @return the versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public static VersionedEntryContentVersion findByPrimaryKey(
		long versionedEntryContentVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException {
		return getPersistence().findByPrimaryKey(versionedEntryContentVersionId);
	}

	/**
	* Returns the versioned entry content version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param versionedEntryContentVersionId the primary key of the versioned entry content version
	* @return the versioned entry content version, or <code>null</code> if a versioned entry content version with the primary key could not be found
	*/
	public static VersionedEntryContentVersion fetchByPrimaryKey(
		long versionedEntryContentVersionId) {
		return getPersistence().fetchByPrimaryKey(versionedEntryContentVersionId);
	}

	public static java.util.Map<java.io.Serializable, VersionedEntryContentVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the versioned entry content versions.
	*
	* @return the versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the versioned entry content versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findAll(int start,
		int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the versioned entry content versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of versioned entry content versions
	*/
	public static List<VersionedEntryContentVersion> findAll(int start,
		int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the versioned entry content versions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of versioned entry content versions.
	*
	* @return the number of versioned entry content versions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static VersionedEntryContentVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<VersionedEntryContentVersionPersistence, VersionedEntryContentVersionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(VersionedEntryContentVersionPersistence.class);

		ServiceTracker<VersionedEntryContentVersionPersistence, VersionedEntryContentVersionPersistence> serviceTracker =
			new ServiceTracker<VersionedEntryContentVersionPersistence, VersionedEntryContentVersionPersistence>(bundle.getBundleContext(),
				VersionedEntryContentVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}