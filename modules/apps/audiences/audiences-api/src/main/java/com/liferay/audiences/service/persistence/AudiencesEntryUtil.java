/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.service.persistence;

import com.liferay.audiences.model.AudiencesEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the audiences entry service. This utility wraps <code>com.liferay.audiences.service.persistence.impl.AudiencesEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AudiencesEntryPersistence
 * @generated
 */
public class AudiencesEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(List<AudiencesEntry> audiencesEntries) {
		getPersistence().cacheResult(audiencesEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(AudiencesEntry audiencesEntry) {
		getPersistence().cacheResult(audiencesEntry);
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
	public static void clearCache(AudiencesEntry audiencesEntry) {
		getPersistence().clearCache(audiencesEntry);
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
	public static Map<Serializable, AudiencesEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AudiencesEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AudiencesEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AudiencesEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AudiencesEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AudiencesEntry update(AudiencesEntry audiencesEntry) {
		return getPersistence().update(audiencesEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AudiencesEntry update(
		AudiencesEntry audiencesEntry, ServiceContext serviceContext) {

		return getPersistence().update(audiencesEntry, serviceContext);
	}

	/**
	 * Returns an ordered range of all the audiences entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audiences entries
	 */
	public static List<AudiencesEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AudiencesEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first audiences entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audiences entry
	 * @throws NoSuchAudiencesEntryException if a matching audiences entry could not be found
	 */
	public static AudiencesEntry findByCompanyId_First(
			long companyId, OrderByComparator<AudiencesEntry> orderByComparator)
		throws com.liferay.audiences.exception.NoSuchAudiencesEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first audiences entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audiences entry, or <code>null</code> if a matching audiences entry could not be found
	 */
	public static AudiencesEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<AudiencesEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Removes all the audiences entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of audiences entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audiences entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching audiences entries
	 */
	public static List<AudiencesEntry> findByC_LikeN(
		long companyId, String name) {

		return getPersistence().findByC_LikeN(companyId, name);
	}

	/**
	 * Returns a range of all the audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @return the range of matching audiences entries
	 */
	public static List<AudiencesEntry> findByC_LikeN(
		long companyId, String name, int start, int end) {

		return getPersistence().findByC_LikeN(companyId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audiences entries
	 */
	public static List<AudiencesEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<AudiencesEntry> orderByComparator) {

		return getPersistence().findByC_LikeN(
			companyId, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audiences entries
	 */
	public static List<AudiencesEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<AudiencesEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_LikeN(
			companyId, name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first audiences entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audiences entry
	 * @throws NoSuchAudiencesEntryException if a matching audiences entry could not be found
	 */
	public static AudiencesEntry findByC_LikeN_First(
			long companyId, String name,
			OrderByComparator<AudiencesEntry> orderByComparator)
		throws com.liferay.audiences.exception.NoSuchAudiencesEntryException {

		return getPersistence().findByC_LikeN_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the first audiences entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audiences entry, or <code>null</code> if a matching audiences entry could not be found
	 */
	public static AudiencesEntry fetchByC_LikeN_First(
		long companyId, String name,
		OrderByComparator<AudiencesEntry> orderByComparator) {

		return getPersistence().fetchByC_LikeN_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Removes all the audiences entries where companyId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public static void removeByC_LikeN(long companyId, String name) {
		getPersistence().removeByC_LikeN(companyId, name);
	}

	/**
	 * Returns the number of audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching audiences entries
	 */
	public static int countByC_LikeN(long companyId, String name) {
		return getPersistence().countByC_LikeN(companyId, name);
	}

	/**
	 * Returns the audiences entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchAudiencesEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching audiences entry
	 * @throws NoSuchAudiencesEntryException if a matching audiences entry could not be found
	 */
	public static AudiencesEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.audiences.exception.NoSuchAudiencesEntryException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the audiences entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching audiences entry, or <code>null</code> if a matching audiences entry could not be found
	 */
	public static AudiencesEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the audiences entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the audiences entry that was removed
	 */
	public static AudiencesEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.audiences.exception.NoSuchAudiencesEntryException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of audiences entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching audiences entries
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Creates a new audiences entry with the primary key. Does not add the audiences entry to the database.
	 *
	 * @param audiencesEntryId the primary key for the new audiences entry
	 * @return the new audiences entry
	 */
	public static AudiencesEntry create(long audiencesEntryId) {
		return getPersistence().create(audiencesEntryId);
	}

	/**
	 * Removes the audiences entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param audiencesEntryId the primary key of the audiences entry
	 * @return the audiences entry that was removed
	 * @throws NoSuchAudiencesEntryException if a audiences entry with the primary key could not be found
	 */
	public static AudiencesEntry remove(long audiencesEntryId)
		throws com.liferay.audiences.exception.NoSuchAudiencesEntryException {

		return getPersistence().remove(audiencesEntryId);
	}

	public static AudiencesEntry updateImpl(AudiencesEntry audiencesEntry) {
		return getPersistence().updateImpl(audiencesEntry);
	}

	/**
	 * Returns the audiences entry with the primary key or throws a <code>NoSuchAudiencesEntryException</code> if it could not be found.
	 *
	 * @param audiencesEntryId the primary key of the audiences entry
	 * @return the audiences entry
	 * @throws NoSuchAudiencesEntryException if a audiences entry with the primary key could not be found
	 */
	public static AudiencesEntry findByPrimaryKey(long audiencesEntryId)
		throws com.liferay.audiences.exception.NoSuchAudiencesEntryException {

		return getPersistence().findByPrimaryKey(audiencesEntryId);
	}

	/**
	 * Returns the audiences entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param audiencesEntryId the primary key of the audiences entry
	 * @return the audiences entry, or <code>null</code> if a audiences entry with the primary key could not be found
	 */
	public static AudiencesEntry fetchByPrimaryKey(long audiencesEntryId) {
		return getPersistence().fetchByPrimaryKey(audiencesEntryId);
	}

	/**
	 * Returns the audiences entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching audiences entry, or <code>null</code> if a matching audiences entry could not be found
	 */
	public static AudiencesEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns all the audiences entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching audiences entries
	 */
	public static List<AudiencesEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the audiences entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @return the range of matching audiences entries
	 */
	public static List<AudiencesEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the audiences entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audiences entries
	 */
	public static List<AudiencesEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AudiencesEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	public static AudiencesEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(AudiencesEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile AudiencesEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-376595346