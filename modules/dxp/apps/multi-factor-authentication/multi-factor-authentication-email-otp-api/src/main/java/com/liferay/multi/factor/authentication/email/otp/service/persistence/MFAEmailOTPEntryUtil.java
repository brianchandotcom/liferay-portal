/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.email.otp.service.persistence;

import com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the mfa email otp entry service. This utility wraps <code>com.liferay.multi.factor.authentication.email.otp.service.persistence.impl.MFAEmailOTPEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @see MFAEmailOTPEntryPersistence
 * @generated
 */
public class MFAEmailOTPEntryUtil {

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
	public static void clearCache(MFAEmailOTPEntry mfaEmailOTPEntry) {
		getPersistence().clearCache(mfaEmailOTPEntry);
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
	public static Map<Serializable, MFAEmailOTPEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MFAEmailOTPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MFAEmailOTPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MFAEmailOTPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MFAEmailOTPEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MFAEmailOTPEntry update(MFAEmailOTPEntry mfaEmailOTPEntry) {
		return getPersistence().update(mfaEmailOTPEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MFAEmailOTPEntry update(
		MFAEmailOTPEntry mfaEmailOTPEntry, ServiceContext serviceContext) {

		return getPersistence().update(mfaEmailOTPEntry, serviceContext);
	}

	/**
	 * Returns the mfa email otp entry where userId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching mfa email otp entry
	 * @throws NoSuchEntryException if a matching mfa email otp entry could not be found
	 */
	public static MFAEmailOTPEntry findByUserId(long userId)
		throws com.liferay.multi.factor.authentication.email.otp.exception.
			NoSuchEntryException {

		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns the mfa email otp entry where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching mfa email otp entry, or <code>null</code> if a matching mfa email otp entry could not be found
	 */
	public static MFAEmailOTPEntry fetchByUserId(long userId) {
		return getPersistence().fetchByUserId(userId);
	}

	/**
	 * Returns the mfa email otp entry where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfa email otp entry, or <code>null</code> if a matching mfa email otp entry could not be found
	 */
	public static MFAEmailOTPEntry fetchByUserId(
		long userId, boolean useFinderCache) {

		return getPersistence().fetchByUserId(userId, useFinderCache);
	}

	/**
	 * Removes the mfa email otp entry where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the mfa email otp entry that was removed
	 */
	public static MFAEmailOTPEntry removeByUserId(long userId)
		throws com.liferay.multi.factor.authentication.email.otp.exception.
			NoSuchEntryException {

		return getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of mfa email otp entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfa email otp entries
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Caches the mfa email otp entry in the entity cache if it is enabled.
	 *
	 * @param mfaEmailOTPEntry the mfa email otp entry
	 */
	public static void cacheResult(MFAEmailOTPEntry mfaEmailOTPEntry) {
		getPersistence().cacheResult(mfaEmailOTPEntry);
	}

	/**
	 * Caches the mfa email otp entries in the entity cache if it is enabled.
	 *
	 * @param mfaEmailOTPEntries the mfa email otp entries
	 */
	public static void cacheResult(List<MFAEmailOTPEntry> mfaEmailOTPEntries) {
		getPersistence().cacheResult(mfaEmailOTPEntries);
	}

	/**
	 * Creates a new mfa email otp entry with the primary key. Does not add the mfa email otp entry to the database.
	 *
	 * @param mfaEmailOTPEntryId the primary key for the new mfa email otp entry
	 * @return the new mfa email otp entry
	 */
	public static MFAEmailOTPEntry create(long mfaEmailOTPEntryId) {
		return getPersistence().create(mfaEmailOTPEntryId);
	}

	/**
	 * Removes the mfa email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry that was removed
	 * @throws NoSuchEntryException if a mfa email otp entry with the primary key could not be found
	 */
	public static MFAEmailOTPEntry remove(long mfaEmailOTPEntryId)
		throws com.liferay.multi.factor.authentication.email.otp.exception.
			NoSuchEntryException {

		return getPersistence().remove(mfaEmailOTPEntryId);
	}

	public static MFAEmailOTPEntry updateImpl(
		MFAEmailOTPEntry mfaEmailOTPEntry) {

		return getPersistence().updateImpl(mfaEmailOTPEntry);
	}

	/**
	 * Returns the mfa email otp entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry
	 * @throws NoSuchEntryException if a mfa email otp entry with the primary key could not be found
	 */
	public static MFAEmailOTPEntry findByPrimaryKey(long mfaEmailOTPEntryId)
		throws com.liferay.multi.factor.authentication.email.otp.exception.
			NoSuchEntryException {

		return getPersistence().findByPrimaryKey(mfaEmailOTPEntryId);
	}

	/**
	 * Returns the mfa email otp entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry, or <code>null</code> if a mfa email otp entry with the primary key could not be found
	 */
	public static MFAEmailOTPEntry fetchByPrimaryKey(long mfaEmailOTPEntryId) {
		return getPersistence().fetchByPrimaryKey(mfaEmailOTPEntryId);
	}

	/**
	 * Returns all the mfa email otp entries.
	 *
	 * @return the mfa email otp entries
	 */
	public static List<MFAEmailOTPEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the mfa email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAEmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa email otp entries
	 * @param end the upper bound of the range of mfa email otp entries (not inclusive)
	 * @return the range of mfa email otp entries
	 */
	public static List<MFAEmailOTPEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the mfa email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAEmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa email otp entries
	 * @param end the upper bound of the range of mfa email otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mfa email otp entries
	 */
	public static List<MFAEmailOTPEntry> findAll(
		int start, int end,
		OrderByComparator<MFAEmailOTPEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mfa email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAEmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa email otp entries
	 * @param end the upper bound of the range of mfa email otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mfa email otp entries
	 */
	public static List<MFAEmailOTPEntry> findAll(
		int start, int end,
		OrderByComparator<MFAEmailOTPEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the mfa email otp entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of mfa email otp entries.
	 *
	 * @return the number of mfa email otp entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MFAEmailOTPEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(MFAEmailOTPEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile MFAEmailOTPEntryPersistence _persistence;

}