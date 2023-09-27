/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.credential.model.impl;

import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MFAFIDO2CredentialEntry in entity cache.
 *
 * @author Arthur Chan
 * @generated
 */
public class MFAFIDO2CredentialEntryCacheModel
	implements CacheModel<MFAFIDO2CredentialEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MFAFIDO2CredentialEntryCacheModel)) {
			return false;
		}

		MFAFIDO2CredentialEntryCacheModel mfaFIDO2CredentialEntryCacheModel =
			(MFAFIDO2CredentialEntryCacheModel)object;

		if ((mfaFIDO2CredentialEntryId ==
				mfaFIDO2CredentialEntryCacheModel.mfaFIDO2CredentialEntryId) &&
			(mvccVersion == mfaFIDO2CredentialEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, mfaFIDO2CredentialEntryId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", mfaFIDO2CredentialEntryId=");
		sb.append(mfaFIDO2CredentialEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", credentialKey=");
		sb.append(credentialKey);
		sb.append(", credentialKeyHash=");
		sb.append(credentialKeyHash);
		sb.append(", credentialType=");
		sb.append(credentialType);
		sb.append(", failedAttempts=");
		sb.append(failedAttempts);
		sb.append(", publicKeyCOSE=");
		sb.append(publicKeyCOSE);
		sb.append(", signatureCount=");
		sb.append(signatureCount);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MFAFIDO2CredentialEntry toEntityModel() {
		MFAFIDO2CredentialEntryImpl mfaFIDO2CredentialEntryImpl =
			new MFAFIDO2CredentialEntryImpl();

		mfaFIDO2CredentialEntryImpl.setMvccVersion(mvccVersion);
		mfaFIDO2CredentialEntryImpl.setMfaFIDO2CredentialEntryId(
			mfaFIDO2CredentialEntryId);
		mfaFIDO2CredentialEntryImpl.setCompanyId(companyId);
		mfaFIDO2CredentialEntryImpl.setUserId(userId);

		if (userName == null) {
			mfaFIDO2CredentialEntryImpl.setUserName("");
		}
		else {
			mfaFIDO2CredentialEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mfaFIDO2CredentialEntryImpl.setCreateDate(null);
		}
		else {
			mfaFIDO2CredentialEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mfaFIDO2CredentialEntryImpl.setModifiedDate(null);
		}
		else {
			mfaFIDO2CredentialEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (credentialKey == null) {
			mfaFIDO2CredentialEntryImpl.setCredentialKey("");
		}
		else {
			mfaFIDO2CredentialEntryImpl.setCredentialKey(credentialKey);
		}

		mfaFIDO2CredentialEntryImpl.setCredentialKeyHash(credentialKeyHash);
		mfaFIDO2CredentialEntryImpl.setCredentialType(credentialType);
		mfaFIDO2CredentialEntryImpl.setFailedAttempts(failedAttempts);

		if (publicKeyCOSE == null) {
			mfaFIDO2CredentialEntryImpl.setPublicKeyCOSE("");
		}
		else {
			mfaFIDO2CredentialEntryImpl.setPublicKeyCOSE(publicKeyCOSE);
		}

		mfaFIDO2CredentialEntryImpl.setSignatureCount(signatureCount);

		mfaFIDO2CredentialEntryImpl.resetOriginalValues();

		return mfaFIDO2CredentialEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		mfaFIDO2CredentialEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		credentialKey = (String)objectInput.readObject();

		credentialKeyHash = objectInput.readLong();

		credentialType = objectInput.readInt();

		failedAttempts = objectInput.readInt();
		publicKeyCOSE = objectInput.readUTF();

		signatureCount = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(mfaFIDO2CredentialEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (credentialKey == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(credentialKey);
		}

		objectOutput.writeLong(credentialKeyHash);

		objectOutput.writeInt(credentialType);

		objectOutput.writeInt(failedAttempts);

		if (publicKeyCOSE == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(publicKeyCOSE);
		}

		objectOutput.writeLong(signatureCount);
	}

	public long mvccVersion;
	public long mfaFIDO2CredentialEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String credentialKey;
	public long credentialKeyHash;
	public int credentialType;
	public int failedAttempts;
	public String publicKeyCOSE;
	public long signatureCount;

}