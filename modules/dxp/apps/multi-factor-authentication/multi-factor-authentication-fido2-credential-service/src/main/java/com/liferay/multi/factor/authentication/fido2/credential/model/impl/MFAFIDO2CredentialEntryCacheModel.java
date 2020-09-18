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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", mfaFIDO2CredentialEntryId=");
		sb.append(mfaFIDO2CredentialEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", credentialId=");
		sb.append(credentialId);
		sb.append(", credentialType=");
		sb.append(credentialType);
		sb.append(", publicKeyCose=");
		sb.append(publicKeyCose);
		sb.append(", signatureCount=");
		sb.append(signatureCount);
		sb.append(", failedAttempts=");
		sb.append(failedAttempts);
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

		mfaFIDO2CredentialEntryImpl.setUserId(userId);

		if (credentialId == null) {
			mfaFIDO2CredentialEntryImpl.setCredentialId("");
		}
		else {
			mfaFIDO2CredentialEntryImpl.setCredentialId(credentialId);
		}

		mfaFIDO2CredentialEntryImpl.setCredentialType(credentialType);

		if (publicKeyCose == null) {
			mfaFIDO2CredentialEntryImpl.setPublicKeyCose("");
		}
		else {
			mfaFIDO2CredentialEntryImpl.setPublicKeyCose(publicKeyCose);
		}

		mfaFIDO2CredentialEntryImpl.setSignatureCount(signatureCount);
		mfaFIDO2CredentialEntryImpl.setFailedAttempts(failedAttempts);

		mfaFIDO2CredentialEntryImpl.resetOriginalValues();

		return mfaFIDO2CredentialEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		mfaFIDO2CredentialEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		userId = objectInput.readLong();
		credentialId = objectInput.readUTF();

		credentialType = objectInput.readInt();
		publicKeyCose = objectInput.readUTF();

		signatureCount = objectInput.readLong();

		failedAttempts = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(mfaFIDO2CredentialEntryId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(userId);

		if (credentialId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(credentialId);
		}

		objectOutput.writeInt(credentialType);

		if (publicKeyCose == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(publicKeyCose);
		}

		objectOutput.writeLong(signatureCount);

		objectOutput.writeInt(failedAttempts);
	}

	public long mvccVersion;
	public long mfaFIDO2CredentialEntryId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long userId;
	public String credentialId;
	public int credentialType;
	public String publicKeyCose;
	public long signatureCount;
	public int failedAttempts;

}