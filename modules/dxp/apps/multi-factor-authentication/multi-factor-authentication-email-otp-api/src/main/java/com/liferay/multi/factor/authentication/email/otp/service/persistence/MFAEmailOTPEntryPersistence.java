/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.email.otp.service.persistence;

import com.liferay.multi.factor.authentication.email.otp.exception.NoSuchEntryException;
import com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the mfa email otp entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @see MFAEmailOTPEntryUtil
 * @generated
 */
@ProviderType
public interface MFAEmailOTPEntryPersistence
	extends BasePersistence<MFAEmailOTPEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MFAEmailOTPEntryUtil} to access the mfa email otp entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the mfa email otp entry where userId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching mfa email otp entry
	 * @throws NoSuchEntryException if a matching mfa email otp entry could not be found
	 */
	public MFAEmailOTPEntry findByUserId(long userId)
		throws NoSuchEntryException;

	/**
	 * Returns the mfa email otp entry where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching mfa email otp entry, or <code>null</code> if a matching mfa email otp entry could not be found
	 */
	public MFAEmailOTPEntry fetchByUserId(long userId);

	/**
	 * Returns the mfa email otp entry where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfa email otp entry, or <code>null</code> if a matching mfa email otp entry could not be found
	 */
	public MFAEmailOTPEntry fetchByUserId(long userId, boolean useFinderCache);

	/**
	 * Removes the mfa email otp entry where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the mfa email otp entry that was removed
	 */
	public MFAEmailOTPEntry removeByUserId(long userId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of mfa email otp entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfa email otp entries
	 */
	public int countByUserId(long userId);

	/**
	 * Caches the mfa email otp entry in the entity cache if it is enabled.
	 *
	 * @param mfaEmailOTPEntry the mfa email otp entry
	 */
	public void cacheResult(MFAEmailOTPEntry mfaEmailOTPEntry);

	/**
	 * Caches the mfa email otp entries in the entity cache if it is enabled.
	 *
	 * @param mfaEmailOTPEntries the mfa email otp entries
	 */
	public void cacheResult(
		java.util.List<MFAEmailOTPEntry> mfaEmailOTPEntries);

	/**
	 * Creates a new mfa email otp entry with the primary key. Does not add the mfa email otp entry to the database.
	 *
	 * @param mfaEmailOTPEntryId the primary key for the new mfa email otp entry
	 * @return the new mfa email otp entry
	 */
	public MFAEmailOTPEntry create(long mfaEmailOTPEntryId);

	/**
	 * Removes the mfa email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry that was removed
	 * @throws NoSuchEntryException if a mfa email otp entry with the primary key could not be found
	 */
	public MFAEmailOTPEntry remove(long mfaEmailOTPEntryId)
		throws NoSuchEntryException;

	public MFAEmailOTPEntry updateImpl(MFAEmailOTPEntry mfaEmailOTPEntry);

	/**
	 * Returns the mfa email otp entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry
	 * @throws NoSuchEntryException if a mfa email otp entry with the primary key could not be found
	 */
	public MFAEmailOTPEntry findByPrimaryKey(long mfaEmailOTPEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the mfa email otp entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry, or <code>null</code> if a mfa email otp entry with the primary key could not be found
	 */
	public MFAEmailOTPEntry fetchByPrimaryKey(long mfaEmailOTPEntryId);

	/**
	 * Returns all the mfa email otp entries.
	 *
	 * @return the mfa email otp entries
	 */
	public java.util.List<MFAEmailOTPEntry> findAll();

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
	public java.util.List<MFAEmailOTPEntry> findAll(int start, int end);

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
	public java.util.List<MFAEmailOTPEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MFAEmailOTPEntry>
			orderByComparator);

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
	public java.util.List<MFAEmailOTPEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MFAEmailOTPEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the mfa email otp entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of mfa email otp entries.
	 *
	 * @return the number of mfa email otp entries
	 */
	public int countAll();

}