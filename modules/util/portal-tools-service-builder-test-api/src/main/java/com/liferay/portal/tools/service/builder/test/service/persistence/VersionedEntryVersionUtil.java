/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryVersion;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the versioned entry version service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.VersionedEntryVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryVersionPersistence
 * @generated
 */
public class VersionedEntryVersionUtil {

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
	public static void clearCache(VersionedEntryVersion versionedEntryVersion) {
		getPersistence().clearCache(versionedEntryVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, VersionedEntryVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<VersionedEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<VersionedEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<VersionedEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static VersionedEntryVersion update(
		VersionedEntryVersion versionedEntryVersion) {

		return getPersistence().update(versionedEntryVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static VersionedEntryVersion update(
		VersionedEntryVersion versionedEntryVersion,
		ServiceContext serviceContext) {

		return getPersistence().update(versionedEntryVersion, serviceContext);
	}

	/**
	 * Returns all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId) {

		return getPersistence().findByVersionedEntryId(versionedEntryId);
	}

	/**
	 * Returns a range of all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end) {

		return getPersistence().findByVersionedEntryId(
			versionedEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().findByVersionedEntryId(
			versionedEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByVersionedEntryId(
			versionedEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion findByVersionedEntryId_First(
			long versionedEntryId,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByVersionedEntryId_First(
			versionedEntryId, orderByComparator);
	}

	/**
	 * Returns the first versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion fetchByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByVersionedEntryId_First(
			versionedEntryId, orderByComparator);
	}

	/**
	 * Returns the last versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion findByVersionedEntryId_Last(
			long versionedEntryId,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByVersionedEntryId_Last(
			versionedEntryId, orderByComparator);
	}

	/**
	 * Returns the last versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion fetchByVersionedEntryId_Last(
		long versionedEntryId,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByVersionedEntryId_Last(
			versionedEntryId, orderByComparator);
	}

	/**
	 * Returns the versioned entry versions before and after the current versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryVersionId the primary key of the current versioned entry version
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public static VersionedEntryVersion[] findByVersionedEntryId_PrevAndNext(
			long versionedEntryVersionId, long versionedEntryId,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByVersionedEntryId_PrevAndNext(
			versionedEntryVersionId, versionedEntryId, orderByComparator);
	}

	/**
	 * Removes all the versioned entry versions where versionedEntryId = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 */
	public static void removeByVersionedEntryId(long versionedEntryId) {
		getPersistence().removeByVersionedEntryId(versionedEntryId);
	}

	/**
	 * Returns the number of versioned entry versions where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the number of matching versioned entry versions
	 */
	public static int countByVersionedEntryId(long versionedEntryId) {
		return getPersistence().countByVersionedEntryId(versionedEntryId);
	}

	/**
	 * Returns the versioned entry version where versionedEntryId = &#63; and version = &#63; or throws a <code>NoSuchVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion findByVersionedEntryId_Version(
			long versionedEntryId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByVersionedEntryId_Version(
			versionedEntryId, version);
	}

	/**
	 * Returns the versioned entry version where versionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion fetchByVersionedEntryId_Version(
		long versionedEntryId, int version) {

		return getPersistence().fetchByVersionedEntryId_Version(
			versionedEntryId, version);
	}

	/**
	 * Returns the versioned entry version where versionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion fetchByVersionedEntryId_Version(
		long versionedEntryId, int version, boolean useFinderCache) {

		return getPersistence().fetchByVersionedEntryId_Version(
			versionedEntryId, version, useFinderCache);
	}

	/**
	 * Removes the versioned entry version where versionedEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the versioned entry version that was removed
	 */
	public static VersionedEntryVersion removeByVersionedEntryId_Version(
			long versionedEntryId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().removeByVersionedEntryId_Version(
			versionedEntryId, version);
	}

	/**
	 * Returns the number of versioned entry versions where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the number of matching versioned entry versions
	 */
	public static int countByVersionedEntryId_Version(
		long versionedEntryId, int version) {

		return getPersistence().countByVersionedEntryId_Version(
			versionedEntryId, version);
	}

	/**
	 * Returns all the versioned entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the versioned entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion findByGroupId_First(
			long groupId,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion fetchByGroupId_First(
		long groupId,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion findByGroupId_Last(
			long groupId,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion fetchByGroupId_Last(
		long groupId,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the versioned entry versions before and after the current versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param versionedEntryVersionId the primary key of the current versioned entry version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public static VersionedEntryVersion[] findByGroupId_PrevAndNext(
			long versionedEntryVersionId, long groupId,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByGroupId_PrevAndNext(
			versionedEntryVersionId, groupId, orderByComparator);
	}

	/**
	 * Removes all the versioned entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of versioned entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching versioned entry versions
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version) {

		return getPersistence().findByGroupId_Version(groupId, version);
	}

	/**
	 * Returns a range of all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end) {

		return getPersistence().findByGroupId_Version(
			groupId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().findByGroupId_Version(
			groupId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entry versions
	 */
	public static List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId_Version(
			groupId, version, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion findByGroupId_Version_First(
			long groupId, int version,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByGroupId_Version_First(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion fetchByGroupId_Version_First(
		long groupId, int version,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Version_First(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the last versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion findByGroupId_Version_Last(
			long groupId, int version,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByGroupId_Version_Last(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the last versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public static VersionedEntryVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Version_Last(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the versioned entry versions before and after the current versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryVersionId the primary key of the current versioned entry version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public static VersionedEntryVersion[] findByGroupId_Version_PrevAndNext(
			long versionedEntryVersionId, long groupId, int version,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByGroupId_Version_PrevAndNext(
			versionedEntryVersionId, groupId, version, orderByComparator);
	}

	/**
	 * Removes all the versioned entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	public static void removeByGroupId_Version(long groupId, int version) {
		getPersistence().removeByGroupId_Version(groupId, version);
	}

	/**
	 * Returns the number of versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching versioned entry versions
	 */
	public static int countByGroupId_Version(long groupId, int version) {
		return getPersistence().countByGroupId_Version(groupId, version);
	}

	/**
	 * Caches the versioned entry version in the entity cache if it is enabled.
	 *
	 * @param versionedEntryVersion the versioned entry version
	 */
	public static void cacheResult(
		VersionedEntryVersion versionedEntryVersion) {

		getPersistence().cacheResult(versionedEntryVersion);
	}

	/**
	 * Caches the versioned entry versions in the entity cache if it is enabled.
	 *
	 * @param versionedEntryVersions the versioned entry versions
	 */
	public static void cacheResult(
		List<VersionedEntryVersion> versionedEntryVersions) {

		getPersistence().cacheResult(versionedEntryVersions);
	}

	/**
	 * Creates a new versioned entry version with the primary key. Does not add the versioned entry version to the database.
	 *
	 * @param versionedEntryVersionId the primary key for the new versioned entry version
	 * @return the new versioned entry version
	 */
	public static VersionedEntryVersion create(long versionedEntryVersionId) {
		return getPersistence().create(versionedEntryVersionId);
	}

	/**
	 * Removes the versioned entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntryVersionId the primary key of the versioned entry version
	 * @return the versioned entry version that was removed
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public static VersionedEntryVersion remove(long versionedEntryVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().remove(versionedEntryVersionId);
	}

	public static VersionedEntryVersion updateImpl(
		VersionedEntryVersion versionedEntryVersion) {

		return getPersistence().updateImpl(versionedEntryVersion);
	}

	/**
	 * Returns the versioned entry version with the primary key or throws a <code>NoSuchVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param versionedEntryVersionId the primary key of the versioned entry version
	 * @return the versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public static VersionedEntryVersion findByPrimaryKey(
			long versionedEntryVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryVersionException {

		return getPersistence().findByPrimaryKey(versionedEntryVersionId);
	}

	/**
	 * Returns the versioned entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param versionedEntryVersionId the primary key of the versioned entry version
	 * @return the versioned entry version, or <code>null</code> if a versioned entry version with the primary key could not be found
	 */
	public static VersionedEntryVersion fetchByPrimaryKey(
		long versionedEntryVersionId) {

		return getPersistence().fetchByPrimaryKey(versionedEntryVersionId);
	}

	/**
	 * Returns all the versioned entry versions.
	 *
	 * @return the versioned entry versions
	 */
	public static List<VersionedEntryVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of versioned entry versions
	 */
	public static List<VersionedEntryVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of versioned entry versions
	 */
	public static List<VersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of versioned entry versions
	 */
	public static List<VersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the versioned entry versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of versioned entry versions.
	 *
	 * @return the number of versioned entry versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static VersionedEntryVersionPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		VersionedEntryVersionPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile VersionedEntryVersionPersistence _persistence;

}