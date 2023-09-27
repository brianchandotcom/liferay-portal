/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.credential.service.impl;

import com.liferay.multi.factor.authentication.fido2.credential.exception.DuplicateMFAFIDO2CredentialEntryException;
import com.liferay.multi.factor.authentication.fido2.credential.exception.NoSuchMFAFIDO2CredentialEntryException;
import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.multi.factor.authentication.fido2.credential.service.base.MFAFIDO2CredentialEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	property = "model.class.name=com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry",
	service = AopService.class
)
public class MFAFIDO2CredentialEntryLocalServiceImpl
	extends MFAFIDO2CredentialEntryLocalServiceBaseImpl {

	@Override
	public MFAFIDO2CredentialEntry addMFAFIDO2CredentialEntry(
			long userId, String credentialKey, int credentialType,
			String publicKeyCOSE)
		throws PortalException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			mfaFIDO2CredentialEntryPersistence.fetchByU_C(
				userId, credentialKey.hashCode());

		if (mfaFIDO2CredentialEntry != null) {
			throw new DuplicateMFAFIDO2CredentialEntryException(
				StringBundler.concat(
					"{credentialKey=", credentialKey, ", userId=", userId,
					"}"));
		}

		mfaFIDO2CredentialEntry = mfaFIDO2CredentialEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUserById(userId);

		mfaFIDO2CredentialEntry.setCompanyId(user.getCompanyId());

		mfaFIDO2CredentialEntry.setUserId(userId);
		mfaFIDO2CredentialEntry.setUserName(user.getFullName());
		mfaFIDO2CredentialEntry.setCreateDate(new Date());
		mfaFIDO2CredentialEntry.setCredentialKey(credentialKey);
		mfaFIDO2CredentialEntry.setCredentialKeyHash(credentialKey.hashCode());
		mfaFIDO2CredentialEntry.setCredentialType(credentialType);
		mfaFIDO2CredentialEntry.setPublicKeyCOSE(publicKeyCOSE);
		mfaFIDO2CredentialEntry.setSignatureCount(0);

		return mfaFIDO2CredentialEntryPersistence.update(
			mfaFIDO2CredentialEntry);
	}

	@Override
	public MFAFIDO2CredentialEntry
		fetchMFAFIDO2CredentialEntryByUserIdAndCredentialKey(
			long userId, String credentialKey) {

		return mfaFIDO2CredentialEntryPersistence.fetchByU_C(
			userId, credentialKey.hashCode());
	}

	@Override
	public List<MFAFIDO2CredentialEntry>
		getMFAFIDO2CredentialEntriesByCredentialKey(String credentialKey) {

		return mfaFIDO2CredentialEntryPersistence.findByCredentialKeyHash(
			credentialKey.hashCode());
	}

	@Override
	public List<MFAFIDO2CredentialEntry> getMFAFIDO2CredentialEntriesByUserId(
		long userId) {

		return mfaFIDO2CredentialEntryPersistence.findByUserId(userId);
	}

	@Override
	public MFAFIDO2CredentialEntry updateAttempts(
			long userId, String credentialKey, long signatureCount)
		throws PortalException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			mfaFIDO2CredentialEntryPersistence.fetchByU_C(
				userId, credentialKey.hashCode());

		if (mfaFIDO2CredentialEntry == null) {
			throw new NoSuchMFAFIDO2CredentialEntryException(
				StringBundler.concat(
					"{credentialKey=", credentialKey, ", userId=", userId,
					"}"));
		}

		if (signatureCount < 1) {
			mfaFIDO2CredentialEntry.setFailedAttempts(
				mfaFIDO2CredentialEntry.getFailedAttempts() + 1);
			mfaFIDO2CredentialEntry.setSignatureCount(0);
		}
		else {
			mfaFIDO2CredentialEntry.setFailedAttempts(0);
			mfaFIDO2CredentialEntry.setSignatureCount(signatureCount);
		}

		return mfaFIDO2CredentialEntryPersistence.update(
			mfaFIDO2CredentialEntry);
	}

	@Reference
	private UserLocalService _userLocalService;

}