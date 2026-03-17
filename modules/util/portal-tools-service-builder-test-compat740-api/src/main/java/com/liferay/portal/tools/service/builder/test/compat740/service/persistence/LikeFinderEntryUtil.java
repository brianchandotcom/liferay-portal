/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat740.model.LikeFinderEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the like finder entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl.LikeFinderEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LikeFinderEntryPersistence
 * @generated
 */
public class LikeFinderEntryUtil {

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
	public static void clearCache(LikeFinderEntry likeFinderEntry) {
		getPersistence().clearCache(likeFinderEntry);
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
	public static Map<Serializable, LikeFinderEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LikeFinderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LikeFinderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LikeFinderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LikeFinderEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LikeFinderEntry update(LikeFinderEntry likeFinderEntry) {
		return getPersistence().update(likeFinderEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LikeFinderEntry update(
		LikeFinderEntry likeFinderEntry, ServiceContext serviceContext) {

		return getPersistence().update(likeFinderEntry, serviceContext);
	}

	/**
	 * Returns the like finder entry where ownerId = &#63; and ownerType = &#63; and portletId = &#63; or throws a <code>NoSuchLikeFinderEntryException</code> if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching like finder entry
	 * @throws NoSuchLikeFinderEntryException if a matching like finder entry could not be found
	 */
	public static LikeFinderEntry findByO_O_P(
			long ownerId, int ownerType, String portletId)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchLikeFinderEntryException {

		return getPersistence().findByO_O_P(ownerId, ownerType, portletId);
	}

	/**
	 * Returns the like finder entry where ownerId = &#63; and ownerType = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching like finder entry, or <code>null</code> if a matching like finder entry could not be found
	 */
	public static LikeFinderEntry fetchByO_O_P(
		long ownerId, int ownerType, String portletId) {

		return getPersistence().fetchByO_O_P(ownerId, ownerType, portletId);
	}

	/**
	 * Returns the like finder entry where ownerId = &#63; and ownerType = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching like finder entry, or <code>null</code> if a matching like finder entry could not be found
	 */
	public static LikeFinderEntry fetchByO_O_P(
		long ownerId, int ownerType, String portletId, boolean useFinderCache) {

		return getPersistence().fetchByO_O_P(
			ownerId, ownerType, portletId, useFinderCache);
	}

	/**
	 * Removes the like finder entry where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the like finder entry that was removed
	 */
	public static LikeFinderEntry removeByO_O_P(
			long ownerId, int ownerType, String portletId)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchLikeFinderEntryException {

		return getPersistence().removeByO_O_P(ownerId, ownerType, portletId);
	}

	/**
	 * Returns the number of like finder entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching like finder entries
	 */
	public static int countByO_O_P(
		long ownerId, int ownerType, String portletId) {

		return getPersistence().countByO_O_P(ownerId, ownerType, portletId);
	}

	/**
	 * Returns all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching like finder entries
	 */
	public static List<LikeFinderEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

	/**
	 * Returns a range of all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @return the range of matching like finder entries
	 */
	public static List<LikeFinderEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching like finder entries
	 */
	public static List<LikeFinderEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		OrderByComparator<LikeFinderEntry> orderByComparator) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching like finder entries
	 */
	public static List<LikeFinderEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		OrderByComparator<LikeFinderEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching like finder entry
	 * @throws NoSuchLikeFinderEntryException if a matching like finder entry could not be found
	 */
	public static LikeFinderEntry findByC_O_O_LikeP_First(
			long companyId, long ownerId, int ownerType, String portletId,
			OrderByComparator<LikeFinderEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchLikeFinderEntryException {

		return getPersistence().findByC_O_O_LikeP_First(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the first like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching like finder entry, or <code>null</code> if a matching like finder entry could not be found
	 */
	public static LikeFinderEntry fetchByC_O_O_LikeP_First(
		long companyId, long ownerId, int ownerType, String portletId,
		OrderByComparator<LikeFinderEntry> orderByComparator) {

		return getPersistence().fetchByC_O_O_LikeP_First(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching like finder entry
	 * @throws NoSuchLikeFinderEntryException if a matching like finder entry could not be found
	 */
	public static LikeFinderEntry findByC_O_O_LikeP_Last(
			long companyId, long ownerId, int ownerType, String portletId,
			OrderByComparator<LikeFinderEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchLikeFinderEntryException {

		return getPersistence().findByC_O_O_LikeP_Last(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching like finder entry, or <code>null</code> if a matching like finder entry could not be found
	 */
	public static LikeFinderEntry fetchByC_O_O_LikeP_Last(
		long companyId, long ownerId, int ownerType, String portletId,
		OrderByComparator<LikeFinderEntry> orderByComparator) {

		return getPersistence().fetchByC_O_O_LikeP_Last(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the like finder entries before and after the current like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param likeFinderEntryId the primary key of the current like finder entry
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next like finder entry
	 * @throws NoSuchLikeFinderEntryException if a like finder entry with the primary key could not be found
	 */
	public static LikeFinderEntry[] findByC_O_O_LikeP_PrevAndNext(
			long likeFinderEntryId, long companyId, long ownerId, int ownerType,
			String portletId,
			OrderByComparator<LikeFinderEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchLikeFinderEntryException {

		return getPersistence().findByC_O_O_LikeP_PrevAndNext(
			likeFinderEntryId, companyId, ownerId, ownerType, portletId,
			orderByComparator);
	}

	/**
	 * Removes all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public static void removeByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		getPersistence().removeByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

	/**
	 * Returns the number of like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching like finder entries
	 */
	public static int countByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		return getPersistence().countByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

	/**
	 * Caches the like finder entry in the entity cache if it is enabled.
	 *
	 * @param likeFinderEntry the like finder entry
	 */
	public static void cacheResult(LikeFinderEntry likeFinderEntry) {
		getPersistence().cacheResult(likeFinderEntry);
	}

	/**
	 * Caches the like finder entries in the entity cache if it is enabled.
	 *
	 * @param likeFinderEntries the like finder entries
	 */
	public static void cacheResult(List<LikeFinderEntry> likeFinderEntries) {
		getPersistence().cacheResult(likeFinderEntries);
	}

	/**
	 * Creates a new like finder entry with the primary key. Does not add the like finder entry to the database.
	 *
	 * @param likeFinderEntryId the primary key for the new like finder entry
	 * @return the new like finder entry
	 */
	public static LikeFinderEntry create(long likeFinderEntryId) {
		return getPersistence().create(likeFinderEntryId);
	}

	/**
	 * Removes the like finder entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param likeFinderEntryId the primary key of the like finder entry
	 * @return the like finder entry that was removed
	 * @throws NoSuchLikeFinderEntryException if a like finder entry with the primary key could not be found
	 */
	public static LikeFinderEntry remove(long likeFinderEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchLikeFinderEntryException {

		return getPersistence().remove(likeFinderEntryId);
	}

	public static LikeFinderEntry updateImpl(LikeFinderEntry likeFinderEntry) {
		return getPersistence().updateImpl(likeFinderEntry);
	}

	/**
	 * Returns the like finder entry with the primary key or throws a <code>NoSuchLikeFinderEntryException</code> if it could not be found.
	 *
	 * @param likeFinderEntryId the primary key of the like finder entry
	 * @return the like finder entry
	 * @throws NoSuchLikeFinderEntryException if a like finder entry with the primary key could not be found
	 */
	public static LikeFinderEntry findByPrimaryKey(long likeFinderEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchLikeFinderEntryException {

		return getPersistence().findByPrimaryKey(likeFinderEntryId);
	}

	/**
	 * Returns the like finder entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param likeFinderEntryId the primary key of the like finder entry
	 * @return the like finder entry, or <code>null</code> if a like finder entry with the primary key could not be found
	 */
	public static LikeFinderEntry fetchByPrimaryKey(long likeFinderEntryId) {
		return getPersistence().fetchByPrimaryKey(likeFinderEntryId);
	}

	/**
	 * Returns all the like finder entries.
	 *
	 * @return the like finder entries
	 */
	public static List<LikeFinderEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the like finder entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @return the range of like finder entries
	 */
	public static List<LikeFinderEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the like finder entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of like finder entries
	 */
	public static List<LikeFinderEntry> findAll(
		int start, int end,
		OrderByComparator<LikeFinderEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the like finder entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of like finder entries
	 */
	public static List<LikeFinderEntry> findAll(
		int start, int end,
		OrderByComparator<LikeFinderEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the like finder entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of like finder entries.
	 *
	 * @return the number of like finder entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LikeFinderEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(LikeFinderEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile LikeFinderEntryPersistence _persistence;

}
// SB-Hash:1509784112