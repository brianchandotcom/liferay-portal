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

package com.liferay.multi.factor.authentication.timebased.otp.service.persistence;

import com.liferay.multi.factor.authentication.timebased.otp.model.MFATimeBasedOTPEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the mfa time based otp entry service. This utility wraps <code>com.liferay.multi.factor.authentication.timebased.otp.service.persistence.impl.MFATimeBasedOTPEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @see MFATimeBasedOTPEntryPersistence
 * @generated
 */
public class MFATimeBasedOTPEntryUtil {

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
	public static void clearCache(MFATimeBasedOTPEntry mfaTimeBasedOTPEntry) {
		getPersistence().clearCache(mfaTimeBasedOTPEntry);
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
	public static Map<Serializable, MFATimeBasedOTPEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MFATimeBasedOTPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MFATimeBasedOTPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MFATimeBasedOTPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MFATimeBasedOTPEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MFATimeBasedOTPEntry update(
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry) {

		return getPersistence().update(mfaTimeBasedOTPEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MFATimeBasedOTPEntry update(
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(mfaTimeBasedOTPEntry, serviceContext);
	}

	/**
	 * Returns the mfa time based otp entry where userId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching mfa time based otp entry
	 * @throws NoSuchEntryException if a matching mfa time based otp entry could not be found
	 */
	public static MFATimeBasedOTPEntry findByUserId(long userId)
		throws com.liferay.multi.factor.authentication.timebased.otp.exception.
			NoSuchEntryException {

		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns the mfa time based otp entry where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching mfa time based otp entry, or <code>null</code> if a matching mfa time based otp entry could not be found
	 */
	public static MFATimeBasedOTPEntry fetchByUserId(long userId) {
		return getPersistence().fetchByUserId(userId);
	}

	/**
	 * Returns the mfa time based otp entry where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfa time based otp entry, or <code>null</code> if a matching mfa time based otp entry could not be found
	 */
	public static MFATimeBasedOTPEntry fetchByUserId(
		long userId, boolean useFinderCache) {

		return getPersistence().fetchByUserId(userId, useFinderCache);
	}

	/**
	 * Removes the mfa time based otp entry where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the mfa time based otp entry that was removed
	 */
	public static MFATimeBasedOTPEntry removeByUserId(long userId)
		throws com.liferay.multi.factor.authentication.timebased.otp.exception.
			NoSuchEntryException {

		return getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of mfa time based otp entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfa time based otp entries
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Caches the mfa time based otp entry in the entity cache if it is enabled.
	 *
	 * @param mfaTimeBasedOTPEntry the mfa time based otp entry
	 */
	public static void cacheResult(MFATimeBasedOTPEntry mfaTimeBasedOTPEntry) {
		getPersistence().cacheResult(mfaTimeBasedOTPEntry);
	}

	/**
	 * Caches the mfa time based otp entries in the entity cache if it is enabled.
	 *
	 * @param mfaTimeBasedOTPEntries the mfa time based otp entries
	 */
	public static void cacheResult(
		List<MFATimeBasedOTPEntry> mfaTimeBasedOTPEntries) {

		getPersistence().cacheResult(mfaTimeBasedOTPEntries);
	}

	/**
	 * Creates a new mfa time based otp entry with the primary key. Does not add the mfa time based otp entry to the database.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key for the new mfa time based otp entry
	 * @return the new mfa time based otp entry
	 */
	public static MFATimeBasedOTPEntry create(long mfaTimeBasedOTPEntryId) {
		return getPersistence().create(mfaTimeBasedOTPEntryId);
	}

	/**
	 * Removes the mfa time based otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry that was removed
	 * @throws NoSuchEntryException if a mfa time based otp entry with the primary key could not be found
	 */
	public static MFATimeBasedOTPEntry remove(long mfaTimeBasedOTPEntryId)
		throws com.liferay.multi.factor.authentication.timebased.otp.exception.
			NoSuchEntryException {

		return getPersistence().remove(mfaTimeBasedOTPEntryId);
	}

	public static MFATimeBasedOTPEntry updateImpl(
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry) {

		return getPersistence().updateImpl(mfaTimeBasedOTPEntry);
	}

	/**
	 * Returns the mfa time based otp entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry
	 * @throws NoSuchEntryException if a mfa time based otp entry with the primary key could not be found
	 */
	public static MFATimeBasedOTPEntry findByPrimaryKey(
			long mfaTimeBasedOTPEntryId)
		throws com.liferay.multi.factor.authentication.timebased.otp.exception.
			NoSuchEntryException {

		return getPersistence().findByPrimaryKey(mfaTimeBasedOTPEntryId);
	}

	/**
	 * Returns the mfa time based otp entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry, or <code>null</code> if a mfa time based otp entry with the primary key could not be found
	 */
	public static MFATimeBasedOTPEntry fetchByPrimaryKey(
		long mfaTimeBasedOTPEntryId) {

		return getPersistence().fetchByPrimaryKey(mfaTimeBasedOTPEntryId);
	}

	/**
	 * Returns all the mfa time based otp entries.
	 *
	 * @return the mfa time based otp entries
	 */
	public static List<MFATimeBasedOTPEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the mfa time based otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFATimeBasedOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa time based otp entries
	 * @param end the upper bound of the range of mfa time based otp entries (not inclusive)
	 * @return the range of mfa time based otp entries
	 */
	public static List<MFATimeBasedOTPEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the mfa time based otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFATimeBasedOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa time based otp entries
	 * @param end the upper bound of the range of mfa time based otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mfa time based otp entries
	 */
	public static List<MFATimeBasedOTPEntry> findAll(
		int start, int end,
		OrderByComparator<MFATimeBasedOTPEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mfa time based otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFATimeBasedOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa time based otp entries
	 * @param end the upper bound of the range of mfa time based otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mfa time based otp entries
	 */
	public static List<MFATimeBasedOTPEntry> findAll(
		int start, int end,
		OrderByComparator<MFATimeBasedOTPEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the mfa time based otp entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of mfa time based otp entries.
	 *
	 * @return the number of mfa time based otp entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MFATimeBasedOTPEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		MFATimeBasedOTPEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile MFATimeBasedOTPEntryPersistence _persistence;

}