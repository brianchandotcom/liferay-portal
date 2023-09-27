/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.email.otp.model.impl;

import com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry;
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
 * The cache model class for representing MFAEmailOTPEntry in entity cache.
 *
 * @author Arthur Chan
 * @generated
 */
public class MFAEmailOTPEntryCacheModel
	implements CacheModel<MFAEmailOTPEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MFAEmailOTPEntryCacheModel)) {
			return false;
		}

		MFAEmailOTPEntryCacheModel mfaEmailOTPEntryCacheModel =
			(MFAEmailOTPEntryCacheModel)object;

		if ((mfaEmailOTPEntryId ==
				mfaEmailOTPEntryCacheModel.mfaEmailOTPEntryId) &&
			(mvccVersion == mfaEmailOTPEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, mfaEmailOTPEntryId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", mfaEmailOTPEntryId=");
		sb.append(mfaEmailOTPEntryId);
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
		sb.append(", failedAttempts=");
		sb.append(failedAttempts);
		sb.append(", lastFailDate=");
		sb.append(lastFailDate);
		sb.append(", lastFailIP=");
		sb.append(lastFailIP);
		sb.append(", lastSuccessDate=");
		sb.append(lastSuccessDate);
		sb.append(", lastSuccessIP=");
		sb.append(lastSuccessIP);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MFAEmailOTPEntry toEntityModel() {
		MFAEmailOTPEntryImpl mfaEmailOTPEntryImpl = new MFAEmailOTPEntryImpl();

		mfaEmailOTPEntryImpl.setMvccVersion(mvccVersion);
		mfaEmailOTPEntryImpl.setMfaEmailOTPEntryId(mfaEmailOTPEntryId);
		mfaEmailOTPEntryImpl.setCompanyId(companyId);
		mfaEmailOTPEntryImpl.setUserId(userId);

		if (userName == null) {
			mfaEmailOTPEntryImpl.setUserName("");
		}
		else {
			mfaEmailOTPEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mfaEmailOTPEntryImpl.setCreateDate(null);
		}
		else {
			mfaEmailOTPEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mfaEmailOTPEntryImpl.setModifiedDate(null);
		}
		else {
			mfaEmailOTPEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		mfaEmailOTPEntryImpl.setFailedAttempts(failedAttempts);

		if (lastFailDate == Long.MIN_VALUE) {
			mfaEmailOTPEntryImpl.setLastFailDate(null);
		}
		else {
			mfaEmailOTPEntryImpl.setLastFailDate(new Date(lastFailDate));
		}

		if (lastFailIP == null) {
			mfaEmailOTPEntryImpl.setLastFailIP("");
		}
		else {
			mfaEmailOTPEntryImpl.setLastFailIP(lastFailIP);
		}

		if (lastSuccessDate == Long.MIN_VALUE) {
			mfaEmailOTPEntryImpl.setLastSuccessDate(null);
		}
		else {
			mfaEmailOTPEntryImpl.setLastSuccessDate(new Date(lastSuccessDate));
		}

		if (lastSuccessIP == null) {
			mfaEmailOTPEntryImpl.setLastSuccessIP("");
		}
		else {
			mfaEmailOTPEntryImpl.setLastSuccessIP(lastSuccessIP);
		}

		mfaEmailOTPEntryImpl.resetOriginalValues();

		return mfaEmailOTPEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		mfaEmailOTPEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		failedAttempts = objectInput.readInt();
		lastFailDate = objectInput.readLong();
		lastFailIP = objectInput.readUTF();
		lastSuccessDate = objectInput.readLong();
		lastSuccessIP = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(mfaEmailOTPEntryId);

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

		objectOutput.writeInt(failedAttempts);
		objectOutput.writeLong(lastFailDate);

		if (lastFailIP == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(lastFailIP);
		}

		objectOutput.writeLong(lastSuccessDate);

		if (lastSuccessIP == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(lastSuccessIP);
		}
	}

	public long mvccVersion;
	public long mfaEmailOTPEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int failedAttempts;
	public long lastFailDate;
	public String lastFailIP;
	public long lastSuccessDate;
	public String lastSuccessIP;

}