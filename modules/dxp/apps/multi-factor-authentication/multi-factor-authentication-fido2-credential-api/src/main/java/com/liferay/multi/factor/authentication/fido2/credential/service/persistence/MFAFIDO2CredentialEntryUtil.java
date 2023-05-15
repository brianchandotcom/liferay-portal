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

package com.liferay.multi.factor.authentication.fido2.credential.service.persistence;

import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the mfafido2 credential entry service. This utility wraps <code>com.liferay.multi.factor.authentication.fido2.credential.service.persistence.impl.MFAFIDO2CredentialEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @see MFAFIDO2CredentialEntryPersistence
 * @generated
 */
public class MFAFIDO2CredentialEntryUtil {

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
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		getPersistence().clearCache(mfaFIDO2CredentialEntry);
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
	public static Map<Serializable, MFAFIDO2CredentialEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MFAFIDO2CredentialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MFAFIDO2CredentialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MFAFIDO2CredentialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MFAFIDO2CredentialEntry update(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		return getPersistence().update(mfaFIDO2CredentialEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MFAFIDO2CredentialEntry update(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(mfaFIDO2CredentialEntry, serviceContext);
	}

	/**
	 * Returns all the mfafido2 credential entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the mfafido2 credential entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @return the range of matching mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry findByUserId_First(
			long userId,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByUserId_First(
		long userId,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry findByUserId_Last(
			long userId,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByUserId_Last(
		long userId,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the mfafido2 credential entries before and after the current mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the current mfafido2 credential entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry[] findByUserId_PrevAndNext(
			long mfaFIDO2CredentialEntryId, long userId,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByUserId_PrevAndNext(
			mfaFIDO2CredentialEntryId, userId, orderByComparator);
	}

	/**
	 * Removes all the mfafido2 credential entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of mfafido2 credential entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfafido2 credential entries
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns all the mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @return the matching mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash) {

		return getPersistence().findByCredentialKeyHash(credentialKeyHash);
	}

	/**
	 * Returns a range of all the mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @return the range of matching mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash, int start, int end) {

		return getPersistence().findByCredentialKeyHash(
			credentialKeyHash, start, end);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().findByCredentialKeyHash(
			credentialKeyHash, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCredentialKeyHash(
			credentialKeyHash, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry findByCredentialKeyHash_First(
			long credentialKeyHash,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByCredentialKeyHash_First(
			credentialKeyHash, orderByComparator);
	}

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByCredentialKeyHash_First(
		long credentialKeyHash,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().fetchByCredentialKeyHash_First(
			credentialKeyHash, orderByComparator);
	}

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry findByCredentialKeyHash_Last(
			long credentialKeyHash,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByCredentialKeyHash_Last(
			credentialKeyHash, orderByComparator);
	}

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByCredentialKeyHash_Last(
		long credentialKeyHash,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().fetchByCredentialKeyHash_Last(
			credentialKeyHash, orderByComparator);
	}

	/**
	 * Returns the mfafido2 credential entries before and after the current mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the current mfafido2 credential entry
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry[] findByCredentialKeyHash_PrevAndNext(
			long mfaFIDO2CredentialEntryId, long credentialKeyHash,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByCredentialKeyHash_PrevAndNext(
			mfaFIDO2CredentialEntryId, credentialKeyHash, orderByComparator);
	}

	/**
	 * Removes all the mfafido2 credential entries where credentialKeyHash = &#63; from the database.
	 *
	 * @param credentialKeyHash the credential key hash
	 */
	public static void removeByCredentialKeyHash(long credentialKeyHash) {
		getPersistence().removeByCredentialKeyHash(credentialKeyHash);
	}

	/**
	 * Returns the number of mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @return the number of matching mfafido2 credential entries
	 */
	public static int countByCredentialKeyHash(long credentialKeyHash) {
		return getPersistence().countByCredentialKeyHash(credentialKeyHash);
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry findByU_C(
			long userId, long credentialKeyHash)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByU_C(userId, credentialKeyHash);
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByU_C(
		long userId, long credentialKeyHash) {

		return getPersistence().fetchByU_C(userId, credentialKeyHash);
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByU_C(
		long userId, long credentialKeyHash, boolean useFinderCache) {

		return getPersistence().fetchByU_C(
			userId, credentialKeyHash, useFinderCache);
	}

	/**
	 * Removes the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the mfafido2 credential entry that was removed
	 */
	public static MFAFIDO2CredentialEntry removeByU_C(
			long userId, long credentialKeyHash)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().removeByU_C(userId, credentialKeyHash);
	}

	/**
	 * Returns the number of mfafido2 credential entries where userId = &#63; and credentialKeyHash = &#63;.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the number of matching mfafido2 credential entries
	 */
	public static int countByU_C(long userId, long credentialKeyHash) {
		return getPersistence().countByU_C(userId, credentialKeyHash);
	}

	/**
	 * Caches the mfafido2 credential entry in the entity cache if it is enabled.
	 *
	 * @param mfaFIDO2CredentialEntry the mfafido2 credential entry
	 */
	public static void cacheResult(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		getPersistence().cacheResult(mfaFIDO2CredentialEntry);
	}

	/**
	 * Caches the mfafido2 credential entries in the entity cache if it is enabled.
	 *
	 * @param mfaFIDO2CredentialEntries the mfafido2 credential entries
	 */
	public static void cacheResult(
		List<MFAFIDO2CredentialEntry> mfaFIDO2CredentialEntries) {

		getPersistence().cacheResult(mfaFIDO2CredentialEntries);
	}

	/**
	 * Creates a new mfafido2 credential entry with the primary key. Does not add the mfafido2 credential entry to the database.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key for the new mfafido2 credential entry
	 * @return the new mfafido2 credential entry
	 */
	public static MFAFIDO2CredentialEntry create(
		long mfaFIDO2CredentialEntryId) {

		return getPersistence().create(mfaFIDO2CredentialEntryId);
	}

	/**
	 * Removes the mfafido2 credential entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was removed
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry remove(long mfaFIDO2CredentialEntryId)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().remove(mfaFIDO2CredentialEntryId);
	}

	public static MFAFIDO2CredentialEntry updateImpl(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		return getPersistence().updateImpl(mfaFIDO2CredentialEntry);
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry findByPrimaryKey(
			long mfaFIDO2CredentialEntryId)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByPrimaryKey(mfaFIDO2CredentialEntryId);
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry, or <code>null</code> if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByPrimaryKey(
		long mfaFIDO2CredentialEntryId) {

		return getPersistence().fetchByPrimaryKey(mfaFIDO2CredentialEntryId);
	}

	/**
	 * Returns all the mfafido2 credential entries.
	 *
	 * @return the mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @return the range of mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the mfafido2 credential entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of mfafido2 credential entries.
	 *
	 * @return the number of mfafido2 credential entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MFAFIDO2CredentialEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		MFAFIDO2CredentialEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile MFAFIDO2CredentialEntryPersistence _persistence;

}