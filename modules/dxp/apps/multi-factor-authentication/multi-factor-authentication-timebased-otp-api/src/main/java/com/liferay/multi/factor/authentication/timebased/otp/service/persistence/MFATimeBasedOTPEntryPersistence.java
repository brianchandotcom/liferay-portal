/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.timebased.otp.service.persistence;

import com.liferay.multi.factor.authentication.timebased.otp.exception.NoSuchEntryException;
import com.liferay.multi.factor.authentication.timebased.otp.model.MFATimeBasedOTPEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the mfa time based otp entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @see MFATimeBasedOTPEntryUtil
 * @generated
 */
@ProviderType
public interface MFATimeBasedOTPEntryPersistence
	extends BasePersistence<MFATimeBasedOTPEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MFATimeBasedOTPEntryUtil} to access the mfa time based otp entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the mfa time based otp entry where userId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching mfa time based otp entry
	 * @throws NoSuchEntryException if a matching mfa time based otp entry could not be found
	 */
	public MFATimeBasedOTPEntry findByUserId(long userId)
		throws NoSuchEntryException;

	/**
	 * Returns the mfa time based otp entry where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfa time based otp entry, or <code>null</code> if a matching mfa time based otp entry could not be found
	 */
	public MFATimeBasedOTPEntry fetchByUserId(
		long userId, boolean useFinderCache);

	/**
	 * Removes the mfa time based otp entry where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the mfa time based otp entry that was removed
	 */
	public MFATimeBasedOTPEntry removeByUserId(long userId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of mfa time based otp entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfa time based otp entries
	 */
	public int countByUserId(long userId);

	/**
	 * Creates a new mfa time based otp entry with the primary key. Does not add the mfa time based otp entry to the database.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key for the new mfa time based otp entry
	 * @return the new mfa time based otp entry
	 */
	public MFATimeBasedOTPEntry create(long mfaTimeBasedOTPEntryId);

	/**
	 * Removes the mfa time based otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry that was removed
	 * @throws NoSuchEntryException if a mfa time based otp entry with the primary key could not be found
	 */
	public MFATimeBasedOTPEntry remove(long mfaTimeBasedOTPEntryId)
		throws NoSuchEntryException;

	public MFATimeBasedOTPEntry updateImpl(
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry);

	/**
	 * Returns the mfa time based otp entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry
	 * @throws NoSuchEntryException if a mfa time based otp entry with the primary key could not be found
	 */
	public MFATimeBasedOTPEntry findByPrimaryKey(long mfaTimeBasedOTPEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the mfa time based otp entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry, or <code>null</code> if a mfa time based otp entry with the primary key could not be found
	 */
	public MFATimeBasedOTPEntry fetchByPrimaryKey(long mfaTimeBasedOTPEntryId);

	/**
	 * Returns the mfa time based otp entry where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching mfa time based otp entry, or <code>null</code> if a matching mfa time based otp entry could not be found
	 */
	public default MFATimeBasedOTPEntry fetchByUserId(long userId) {
		return fetchByUserId(userId, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1524702656