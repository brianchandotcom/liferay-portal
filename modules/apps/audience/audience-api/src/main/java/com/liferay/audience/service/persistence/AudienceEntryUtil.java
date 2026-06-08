/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service.persistence;

import com.liferay.audience.model.AudienceEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the audience entry service. This utility wraps <code>com.liferay.audience.service.persistence.impl.AudienceEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AudienceEntryPersistence
 * @generated
 */
public class AudienceEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(List<AudienceEntry> audienceEntries) {
		getPersistence().cacheResult(audienceEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(AudienceEntry audienceEntry) {
		getPersistence().cacheResult(audienceEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(AudienceEntry audienceEntry) {
		getPersistence().clearCache(audienceEntry);
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
	public static Map<Serializable, AudienceEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AudienceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AudienceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AudienceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AudienceEntry update(AudienceEntry audienceEntry) {
		return getPersistence().update(audienceEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AudienceEntry update(
		AudienceEntry audienceEntry, ServiceContext serviceContext) {

		return getPersistence().update(audienceEntry, serviceContext);
	}

	/**
	 * Returns an ordered range of all the audience entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	public static List<AudienceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first audience entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchEntryException if a matching audience entry could not be found
	 */
	public static AudienceEntry findByUuid_First(
			String uuid, OrderByComparator<AudienceEntry> orderByComparator)
		throws com.liferay.audience.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first audience entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public static AudienceEntry fetchByUuid_First(
		String uuid, OrderByComparator<AudienceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Removes all the audience entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of audience entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching audience entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns an ordered range of all the audience entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	public static List<AudienceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first audience entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchEntryException if a matching audience entry could not be found
	 */
	public static AudienceEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AudienceEntry> orderByComparator)
		throws com.liferay.audience.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first audience entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public static AudienceEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the audience entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of audience entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching audience entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	public static List<AudienceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchEntryException if a matching audience entry could not be found
	 */
	public static AudienceEntry findByCompanyId_First(
			long companyId, OrderByComparator<AudienceEntry> orderByComparator)
		throws com.liferay.audience.exception.NoSuchEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public static AudienceEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<AudienceEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Removes all the audience entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of audience entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audience entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching audience entries
	 */
	public static List<AudienceEntry> findByC_LikeN(
		long companyId, String name) {

		return getPersistence().findByC_LikeN(companyId, name);
	}

	/**
	 * Returns a range of all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries
	 */
	public static List<AudienceEntry> findByC_LikeN(
		long companyId, String name, int start, int end) {

		return getPersistence().findByC_LikeN(companyId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries
	 */
	public static List<AudienceEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return getPersistence().findByC_LikeN(
			companyId, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	public static List<AudienceEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_LikeN(
			companyId, name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchEntryException if a matching audience entry could not be found
	 */
	public static AudienceEntry findByC_LikeN_First(
			long companyId, String name,
			OrderByComparator<AudienceEntry> orderByComparator)
		throws com.liferay.audience.exception.NoSuchEntryException {

		return getPersistence().findByC_LikeN_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public static AudienceEntry fetchByC_LikeN_First(
		long companyId, String name,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return getPersistence().fetchByC_LikeN_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Removes all the audience entries where companyId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public static void removeByC_LikeN(long companyId, String name) {
		getPersistence().removeByC_LikeN(companyId, name);
	}

	/**
	 * Returns the number of audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching audience entries
	 */
	public static int countByC_LikeN(long companyId, String name) {
		return getPersistence().countByC_LikeN(companyId, name);
	}

	/**
	 * Returns the audience entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching audience entry
	 * @throws NoSuchEntryException if a matching audience entry could not be found
	 */
	public static AudienceEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.audience.exception.NoSuchEntryException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the audience entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public static AudienceEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the audience entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the audience entry that was removed
	 */
	public static AudienceEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.audience.exception.NoSuchEntryException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of audience entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching audience entries
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Creates a new audience entry with the primary key. Does not add the audience entry to the database.
	 *
	 * @param audienceEntryId the primary key for the new audience entry
	 * @return the new audience entry
	 */
	public static AudienceEntry create(long audienceEntryId) {
		return getPersistence().create(audienceEntryId);
	}

	/**
	 * Removes the audience entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry that was removed
	 * @throws NoSuchEntryException if a audience entry with the primary key could not be found
	 */
	public static AudienceEntry remove(long audienceEntryId)
		throws com.liferay.audience.exception.NoSuchEntryException {

		return getPersistence().remove(audienceEntryId);
	}

	public static AudienceEntry updateImpl(AudienceEntry audienceEntry) {
		return getPersistence().updateImpl(audienceEntry);
	}

	/**
	 * Returns the audience entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry
	 * @throws NoSuchEntryException if a audience entry with the primary key could not be found
	 */
	public static AudienceEntry findByPrimaryKey(long audienceEntryId)
		throws com.liferay.audience.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(audienceEntryId);
	}

	/**
	 * Returns the audience entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry, or <code>null</code> if a audience entry with the primary key could not be found
	 */
	public static AudienceEntry fetchByPrimaryKey(long audienceEntryId) {
		return getPersistence().fetchByPrimaryKey(audienceEntryId);
	}

	/**
	 * Returns the audience entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public static AudienceEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns all the audience entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching audience entries
	 */
	public static List<AudienceEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the audience entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries
	 */
	public static List<AudienceEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the audience entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries
	 */
	public static List<AudienceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns all the audience entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching audience entries
	 */
	public static List<AudienceEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the audience entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries
	 */
	public static List<AudienceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the audience entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries
	 */
	public static List<AudienceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns all the audience entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching audience entries
	 */
	public static List<AudienceEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the audience entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries
	 */
	public static List<AudienceEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries
	 */
	public static List<AudienceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	public static AudienceEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(AudienceEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile AudienceEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:274501255