/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationVersion;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the lv entry localization version service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryLocalizationVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationVersionPersistence
 * @generated
 */
public class LVEntryLocalizationVersionUtil {

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
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		getPersistence().clearCache(lvEntryLocalizationVersion);
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
	public static Map<Serializable, LVEntryLocalizationVersion>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LVEntryLocalizationVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LVEntryLocalizationVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LVEntryLocalizationVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LVEntryLocalizationVersion update(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		return getPersistence().update(lvEntryLocalizationVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LVEntryLocalizationVersion update(
		LVEntryLocalizationVersion lvEntryLocalizationVersion,
		ServiceContext serviceContext) {

		return getPersistence().update(
			lvEntryLocalizationVersion, serviceContext);
	}

	/**
	 * Returns all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @return the matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId) {

		return getPersistence().findByLvEntryLocalizationId(
			lvEntryLocalizationId);
	}

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId, int start, int end) {

		return getPersistence().findByLvEntryLocalizationId(
			lvEntryLocalizationId, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().findByLvEntryLocalizationId(
			lvEntryLocalizationId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLvEntryLocalizationId(
			lvEntryLocalizationId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion findByLvEntryLocalizationId_First(
			long lvEntryLocalizationId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryLocalizationId_First(
			lvEntryLocalizationId, orderByComparator);
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion fetchByLvEntryLocalizationId_First(
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryLocalizationId_First(
			lvEntryLocalizationId, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion findByLvEntryLocalizationId_Last(
			long lvEntryLocalizationId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryLocalizationId_Last(
			lvEntryLocalizationId, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Last(
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryLocalizationId_Last(
			lvEntryLocalizationId, orderByComparator);
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public static LVEntryLocalizationVersion[]
			findByLvEntryLocalizationId_PrevAndNext(
				long lvEntryLocalizationVersionId, long lvEntryLocalizationId,
				OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryLocalizationId_PrevAndNext(
			lvEntryLocalizationVersionId, lvEntryLocalizationId,
			orderByComparator);
	}

	/**
	 * Removes all the lv entry localization versions where lvEntryLocalizationId = &#63; from the database.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 */
	public static void removeByLvEntryLocalizationId(
		long lvEntryLocalizationId) {

		getPersistence().removeByLvEntryLocalizationId(lvEntryLocalizationId);
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @return the number of matching lv entry localization versions
	 */
	public static int countByLvEntryLocalizationId(long lvEntryLocalizationId) {
		return getPersistence().countByLvEntryLocalizationId(
			lvEntryLocalizationId);
	}

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryLocalizationVersionException</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion
			findByLvEntryLocalizationId_Version(
				long lvEntryLocalizationId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryLocalizationId_Version(
			lvEntryLocalizationId, version);
	}

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion
		fetchByLvEntryLocalizationId_Version(
			long lvEntryLocalizationId, int version) {

		return getPersistence().fetchByLvEntryLocalizationId_Version(
			lvEntryLocalizationId, version);
	}

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion
		fetchByLvEntryLocalizationId_Version(
			long lvEntryLocalizationId, int version, boolean useFinderCache) {

		return getPersistence().fetchByLvEntryLocalizationId_Version(
			lvEntryLocalizationId, version, useFinderCache);
	}

	/**
	 * Removes the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the lv entry localization version that was removed
	 */
	public static LVEntryLocalizationVersion
			removeByLvEntryLocalizationId_Version(
				long lvEntryLocalizationId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().removeByLvEntryLocalizationId_Version(
			lvEntryLocalizationId, version);
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryLocalizationId = &#63; and version = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	public static int countByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version) {

		return getPersistence().countByLvEntryLocalizationId_Version(
			lvEntryLocalizationId, version);
	}

	/**
	 * Returns all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId) {

		return getPersistence().findByLvEntryId(lvEntryId);
	}

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId, int start, int end) {

		return getPersistence().findByLvEntryId(lvEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().findByLvEntryId(
			lvEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLvEntryId(
			lvEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion findByLvEntryId_First(
			long lvEntryId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_First(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion fetchByLvEntryId_First(
		long lvEntryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryId_First(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion findByLvEntryId_Last(
			long lvEntryId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_Last(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion fetchByLvEntryId_Last(
		long lvEntryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryId_Last(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public static LVEntryLocalizationVersion[] findByLvEntryId_PrevAndNext(
			long lvEntryLocalizationVersionId, long lvEntryId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_PrevAndNext(
			lvEntryLocalizationVersionId, lvEntryId, orderByComparator);
	}

	/**
	 * Removes all the lv entry localization versions where lvEntryId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 */
	public static void removeByLvEntryId(long lvEntryId) {
		getPersistence().removeByLvEntryId(lvEntryId);
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the number of matching lv entry localization versions
	 */
	public static int countByLvEntryId(long lvEntryId) {
		return getPersistence().countByLvEntryId(lvEntryId);
	}

	/**
	 * Returns all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version) {

		return getPersistence().findByLvEntryId_Version(lvEntryId, version);
	}

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version, int start, int end) {

		return getPersistence().findByLvEntryId_Version(
			lvEntryId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().findByLvEntryId_Version(
			lvEntryId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLvEntryId_Version(
			lvEntryId, version, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion findByLvEntryId_Version_First(
			long lvEntryId, int version,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_Version_First(
			lvEntryId, version, orderByComparator);
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion fetchByLvEntryId_Version_First(
		long lvEntryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryId_Version_First(
			lvEntryId, version, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion findByLvEntryId_Version_Last(
			long lvEntryId, int version,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_Version_Last(
			lvEntryId, version, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion fetchByLvEntryId_Version_Last(
		long lvEntryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryId_Version_Last(
			lvEntryId, version, orderByComparator);
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public static LVEntryLocalizationVersion[]
			findByLvEntryId_Version_PrevAndNext(
				long lvEntryLocalizationVersionId, long lvEntryId, int version,
				OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_Version_PrevAndNext(
			lvEntryLocalizationVersionId, lvEntryId, version,
			orderByComparator);
	}

	/**
	 * Removes all the lv entry localization versions where lvEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 */
	public static void removeByLvEntryId_Version(long lvEntryId, int version) {
		getPersistence().removeByLvEntryId_Version(lvEntryId, version);
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	public static int countByLvEntryId_Version(long lvEntryId, int version) {
		return getPersistence().countByLvEntryId_Version(lvEntryId, version);
	}

	/**
	 * Returns all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId_LanguageId(
		long lvEntryId, String languageId) {

		return getPersistence().findByLvEntryId_LanguageId(
			lvEntryId, languageId);
	}

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId_LanguageId(
		long lvEntryId, String languageId, int start, int end) {

		return getPersistence().findByLvEntryId_LanguageId(
			lvEntryId, languageId, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId_LanguageId(
		long lvEntryId, String languageId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().findByLvEntryId_LanguageId(
			lvEntryId, languageId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findByLvEntryId_LanguageId(
		long lvEntryId, String languageId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLvEntryId_LanguageId(
			lvEntryId, languageId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion findByLvEntryId_LanguageId_First(
			long lvEntryId, String languageId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_LanguageId_First(
			lvEntryId, languageId, orderByComparator);
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_First(
		long lvEntryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryId_LanguageId_First(
			lvEntryId, languageId, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion findByLvEntryId_LanguageId_Last(
			long lvEntryId, String languageId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_LanguageId_Last(
			lvEntryId, languageId, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_Last(
		long lvEntryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryId_LanguageId_Last(
			lvEntryId, languageId, orderByComparator);
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public static LVEntryLocalizationVersion[]
			findByLvEntryId_LanguageId_PrevAndNext(
				long lvEntryLocalizationVersionId, long lvEntryId,
				String languageId,
				OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_LanguageId_PrevAndNext(
			lvEntryLocalizationVersionId, lvEntryId, languageId,
			orderByComparator);
	}

	/**
	 * Removes all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 */
	public static void removeByLvEntryId_LanguageId(
		long lvEntryId, String languageId) {

		getPersistence().removeByLvEntryId_LanguageId(lvEntryId, languageId);
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the number of matching lv entry localization versions
	 */
	public static int countByLvEntryId_LanguageId(
		long lvEntryId, String languageId) {

		return getPersistence().countByLvEntryId_LanguageId(
			lvEntryId, languageId);
	}

	/**
	 * Returns the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryLocalizationVersionException</code> if it could not be found.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion findByLvEntryId_LanguageId_Version(
			long lvEntryId, String languageId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByLvEntryId_LanguageId_Version(
			lvEntryId, languageId, version);
	}

	/**
	 * Returns the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion
		fetchByLvEntryId_LanguageId_Version(
			long lvEntryId, String languageId, int version) {

		return getPersistence().fetchByLvEntryId_LanguageId_Version(
			lvEntryId, languageId, version);
	}

	/**
	 * Returns the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	public static LVEntryLocalizationVersion
		fetchByLvEntryId_LanguageId_Version(
			long lvEntryId, String languageId, int version,
			boolean useFinderCache) {

		return getPersistence().fetchByLvEntryId_LanguageId_Version(
			lvEntryId, languageId, version, useFinderCache);
	}

	/**
	 * Removes the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the lv entry localization version that was removed
	 */
	public static LVEntryLocalizationVersion
			removeByLvEntryId_LanguageId_Version(
				long lvEntryId, String languageId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().removeByLvEntryId_LanguageId_Version(
			lvEntryId, languageId, version);
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63; and languageId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	public static int countByLvEntryId_LanguageId_Version(
		long lvEntryId, String languageId, int version) {

		return getPersistence().countByLvEntryId_LanguageId_Version(
			lvEntryId, languageId, version);
	}

	/**
	 * Caches the lv entry localization version in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizationVersion the lv entry localization version
	 */
	public static void cacheResult(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		getPersistence().cacheResult(lvEntryLocalizationVersion);
	}

	/**
	 * Caches the lv entry localization versions in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizationVersions the lv entry localization versions
	 */
	public static void cacheResult(
		List<LVEntryLocalizationVersion> lvEntryLocalizationVersions) {

		getPersistence().cacheResult(lvEntryLocalizationVersions);
	}

	/**
	 * Creates a new lv entry localization version with the primary key. Does not add the lv entry localization version to the database.
	 *
	 * @param lvEntryLocalizationVersionId the primary key for the new lv entry localization version
	 * @return the new lv entry localization version
	 */
	public static LVEntryLocalizationVersion create(
		long lvEntryLocalizationVersionId) {

		return getPersistence().create(lvEntryLocalizationVersionId);
	}

	/**
	 * Removes the lv entry localization version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version that was removed
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public static LVEntryLocalizationVersion remove(
			long lvEntryLocalizationVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().remove(lvEntryLocalizationVersionId);
	}

	public static LVEntryLocalizationVersion updateImpl(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		return getPersistence().updateImpl(lvEntryLocalizationVersion);
	}

	/**
	 * Returns the lv entry localization version with the primary key or throws a <code>NoSuchLVEntryLocalizationVersionException</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	public static LVEntryLocalizationVersion findByPrimaryKey(
			long lvEntryLocalizationVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationVersionException {

		return getPersistence().findByPrimaryKey(lvEntryLocalizationVersionId);
	}

	/**
	 * Returns the lv entry localization version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version, or <code>null</code> if a lv entry localization version with the primary key could not be found
	 */
	public static LVEntryLocalizationVersion fetchByPrimaryKey(
		long lvEntryLocalizationVersionId) {

		return getPersistence().fetchByPrimaryKey(lvEntryLocalizationVersionId);
	}

	/**
	 * Returns all the lv entry localization versions.
	 *
	 * @return the lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findAll(
		int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lv entry localization versions
	 */
	public static List<LVEntryLocalizationVersion> findAll(
		int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the lv entry localization versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of lv entry localization versions.
	 *
	 * @return the number of lv entry localization versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LVEntryLocalizationVersionPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		LVEntryLocalizationVersionPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile LVEntryLocalizationVersionPersistence _persistence;

}