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

package com.liferay.multi.factor.authentication.checker.email.otp.model.impl;

import com.liferay.multi.factor.authentication.checker.email.otp.model.EmailOTPEntry;
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
 * The cache model class for representing EmailOTPEntry in entity cache.
 *
 * @author Arthur Chan
 * @generated
 */
public class EmailOTPEntryCacheModel
	implements CacheModel<EmailOTPEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EmailOTPEntryCacheModel)) {
			return false;
		}

		EmailOTPEntryCacheModel emailOTPEntryCacheModel =
			(EmailOTPEntryCacheModel)obj;

		if ((entryId == emailOTPEntryCacheModel.entryId) &&
			(mvccVersion == emailOTPEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, entryId);

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
		sb.append(", entryId=");
		sb.append(entryId);
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
		sb.append(", lastSuccessDate=");
		sb.append(lastSuccessDate);
		sb.append(", lastSuccessIP=");
		sb.append(lastSuccessIP);
		sb.append(", lastFailDate=");
		sb.append(lastFailDate);
		sb.append(", lastFailIP=");
		sb.append(lastFailIP);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public EmailOTPEntry toEntityModel() {
		EmailOTPEntryImpl emailOTPEntryImpl = new EmailOTPEntryImpl();

		emailOTPEntryImpl.setMvccVersion(mvccVersion);
		emailOTPEntryImpl.setEntryId(entryId);
		emailOTPEntryImpl.setCompanyId(companyId);
		emailOTPEntryImpl.setUserId(userId);

		if (userName == null) {
			emailOTPEntryImpl.setUserName("");
		}
		else {
			emailOTPEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			emailOTPEntryImpl.setCreateDate(null);
		}
		else {
			emailOTPEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			emailOTPEntryImpl.setModifiedDate(null);
		}
		else {
			emailOTPEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		emailOTPEntryImpl.setFailedAttempts(failedAttempts);

		if (lastSuccessDate == Long.MIN_VALUE) {
			emailOTPEntryImpl.setLastSuccessDate(null);
		}
		else {
			emailOTPEntryImpl.setLastSuccessDate(new Date(lastSuccessDate));
		}

		if (lastSuccessIP == null) {
			emailOTPEntryImpl.setLastSuccessIP("");
		}
		else {
			emailOTPEntryImpl.setLastSuccessIP(lastSuccessIP);
		}

		if (lastFailDate == Long.MIN_VALUE) {
			emailOTPEntryImpl.setLastFailDate(null);
		}
		else {
			emailOTPEntryImpl.setLastFailDate(new Date(lastFailDate));
		}

		if (lastFailIP == null) {
			emailOTPEntryImpl.setLastFailIP("");
		}
		else {
			emailOTPEntryImpl.setLastFailIP(lastFailIP);
		}

		emailOTPEntryImpl.resetOriginalValues();

		return emailOTPEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		entryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		failedAttempts = objectInput.readInt();
		lastSuccessDate = objectInput.readLong();
		lastSuccessIP = objectInput.readUTF();
		lastFailDate = objectInput.readLong();
		lastFailIP = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(entryId);

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
		objectOutput.writeLong(lastSuccessDate);

		if (lastSuccessIP == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(lastSuccessIP);
		}

		objectOutput.writeLong(lastFailDate);

		if (lastFailIP == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(lastFailIP);
		}
	}

	public long mvccVersion;
	public long entryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int failedAttempts;
	public long lastSuccessDate;
	public String lastSuccessIP;
	public long lastFailDate;
	public String lastFailIP;

}