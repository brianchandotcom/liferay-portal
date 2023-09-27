/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.credential.service.persistence;

import com.liferay.multi.factor.authentication.fido2.credential.exception.NoSuchMFAFIDO2CredentialEntryException;
import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the mfafido2 credential entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @see MFAFIDO2CredentialEntryUtil
 * @generated
 */
@ProviderType
public interface MFAFIDO2CredentialEntryPersistence
	extends BasePersistence<MFAFIDO2CredentialEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MFAFIDO2CredentialEntryUtil} to access the mfafido2 credential entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the mfafido2 credential entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching mfafido2 credential entries
	 */
	public java.util.List<MFAFIDO2CredentialEntry> findByUserId(long userId);

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
	public java.util.List<MFAFIDO2CredentialEntry> findByUserId(
		long userId, int start, int end);

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
	public java.util.List<MFAFIDO2CredentialEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator);

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
	public java.util.List<MFAFIDO2CredentialEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException;

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator);

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException;

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator);

	/**
	 * Returns the mfafido2 credential entries before and after the current mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the current mfafido2 credential entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public MFAFIDO2CredentialEntry[] findByUserId_PrevAndNext(
			long mfaFIDO2CredentialEntryId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException;

	/**
	 * Removes all the mfafido2 credential entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of mfafido2 credential entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfafido2 credential entries
	 */
	public int countByUserId(long userId);

	/**
	 * Returns all the mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @return the matching mfafido2 credential entries
	 */
	public java.util.List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash);

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
	public java.util.List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash, int start, int end);

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
	public java.util.List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator);

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
	public java.util.List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry findByCredentialKeyHash_First(
			long credentialKeyHash,
			com.liferay.portal.kernel.util.OrderByComparator
				<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException;

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry fetchByCredentialKeyHash_First(
		long credentialKeyHash,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator);

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry findByCredentialKeyHash_Last(
			long credentialKeyHash,
			com.liferay.portal.kernel.util.OrderByComparator
				<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException;

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry fetchByCredentialKeyHash_Last(
		long credentialKeyHash,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator);

	/**
	 * Returns the mfafido2 credential entries before and after the current mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the current mfafido2 credential entry
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public MFAFIDO2CredentialEntry[] findByCredentialKeyHash_PrevAndNext(
			long mfaFIDO2CredentialEntryId, long credentialKeyHash,
			com.liferay.portal.kernel.util.OrderByComparator
				<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException;

	/**
	 * Removes all the mfafido2 credential entries where credentialKeyHash = &#63; from the database.
	 *
	 * @param credentialKeyHash the credential key hash
	 */
	public void removeByCredentialKeyHash(long credentialKeyHash);

	/**
	 * Returns the number of mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @return the number of matching mfafido2 credential entries
	 */
	public int countByCredentialKeyHash(long credentialKeyHash);

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry findByU_C(
			long userId, long credentialKeyHash)
		throws NoSuchMFAFIDO2CredentialEntryException;

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry fetchByU_C(
		long userId, long credentialKeyHash);

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public MFAFIDO2CredentialEntry fetchByU_C(
		long userId, long credentialKeyHash, boolean useFinderCache);

	/**
	 * Removes the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the mfafido2 credential entry that was removed
	 */
	public MFAFIDO2CredentialEntry removeByU_C(
			long userId, long credentialKeyHash)
		throws NoSuchMFAFIDO2CredentialEntryException;

	/**
	 * Returns the number of mfafido2 credential entries where userId = &#63; and credentialKeyHash = &#63;.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the number of matching mfafido2 credential entries
	 */
	public int countByU_C(long userId, long credentialKeyHash);

	/**
	 * Caches the mfafido2 credential entry in the entity cache if it is enabled.
	 *
	 * @param mfaFIDO2CredentialEntry the mfafido2 credential entry
	 */
	public void cacheResult(MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry);

	/**
	 * Caches the mfafido2 credential entries in the entity cache if it is enabled.
	 *
	 * @param mfaFIDO2CredentialEntries the mfafido2 credential entries
	 */
	public void cacheResult(
		java.util.List<MFAFIDO2CredentialEntry> mfaFIDO2CredentialEntries);

	/**
	 * Creates a new mfafido2 credential entry with the primary key. Does not add the mfafido2 credential entry to the database.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key for the new mfafido2 credential entry
	 * @return the new mfafido2 credential entry
	 */
	public MFAFIDO2CredentialEntry create(long mfaFIDO2CredentialEntryId);

	/**
	 * Removes the mfafido2 credential entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was removed
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public MFAFIDO2CredentialEntry remove(long mfaFIDO2CredentialEntryId)
		throws NoSuchMFAFIDO2CredentialEntryException;

	public MFAFIDO2CredentialEntry updateImpl(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry);

	/**
	 * Returns the mfafido2 credential entry with the primary key or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public MFAFIDO2CredentialEntry findByPrimaryKey(
			long mfaFIDO2CredentialEntryId)
		throws NoSuchMFAFIDO2CredentialEntryException;

	/**
	 * Returns the mfafido2 credential entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry, or <code>null</code> if a mfafido2 credential entry with the primary key could not be found
	 */
	public MFAFIDO2CredentialEntry fetchByPrimaryKey(
		long mfaFIDO2CredentialEntryId);

	/**
	 * Returns all the mfafido2 credential entries.
	 *
	 * @return the mfafido2 credential entries
	 */
	public java.util.List<MFAFIDO2CredentialEntry> findAll();

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
	public java.util.List<MFAFIDO2CredentialEntry> findAll(int start, int end);

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
	public java.util.List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator);

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
	public java.util.List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<MFAFIDO2CredentialEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the mfafido2 credential entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of mfafido2 credential entries.
	 *
	 * @return the number of mfafido2 credential entries
	 */
	public int countAll();

}