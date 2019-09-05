/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.PasswordTracker;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PasswordTracker in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PasswordTrackerCacheModel
	implements CacheModel<PasswordTracker>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PasswordTrackerCacheModel)) {
			return false;
		}

		PasswordTrackerCacheModel passwordTrackerCacheModel =
			(PasswordTrackerCacheModel)obj;

		if ((passwordTrackerId ==
				passwordTrackerCacheModel.passwordTrackerId) &&
			(mvccVersion == passwordTrackerCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, passwordTrackerId);

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
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", passwordTrackerId=");
		sb.append(passwordTrackerId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", password=");
		sb.append(password);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PasswordTracker toEntityModel() {
		PasswordTrackerImpl passwordTrackerImpl = new PasswordTrackerImpl();

		passwordTrackerImpl.setMvccVersion(mvccVersion);
		passwordTrackerImpl.setPasswordTrackerId(passwordTrackerId);
		passwordTrackerImpl.setCompanyId(companyId);
		passwordTrackerImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			passwordTrackerImpl.setCreateDate(null);
		}
		else {
			passwordTrackerImpl.setCreateDate(new Date(createDate));
		}

		if (password == null) {
			passwordTrackerImpl.setPassword("");
		}
		else {
			passwordTrackerImpl.setPassword(password);
		}

		passwordTrackerImpl.resetOriginalValues();

		return passwordTrackerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		passwordTrackerId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		password = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(passwordTrackerId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);

		if (password == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(password);
		}
	}

	public long mvccVersion;
	public long passwordTrackerId;
	public long companyId;
	public long userId;
	public long createDate;
	public String password;

}