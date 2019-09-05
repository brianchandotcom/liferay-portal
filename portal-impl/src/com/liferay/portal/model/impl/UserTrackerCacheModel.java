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
import com.liferay.portal.kernel.model.UserTracker;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing UserTracker in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserTrackerCacheModel
	implements CacheModel<UserTracker>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserTrackerCacheModel)) {
			return false;
		}

		UserTrackerCacheModel userTrackerCacheModel =
			(UserTrackerCacheModel)obj;

		if ((userTrackerId == userTrackerCacheModel.userTrackerId) &&
			(mvccVersion == userTrackerCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, userTrackerId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", userTrackerId=");
		sb.append(userTrackerId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", sessionId=");
		sb.append(sessionId);
		sb.append(", remoteAddr=");
		sb.append(remoteAddr);
		sb.append(", remoteHost=");
		sb.append(remoteHost);
		sb.append(", userAgent=");
		sb.append(userAgent);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserTracker toEntityModel() {
		UserTrackerImpl userTrackerImpl = new UserTrackerImpl();

		userTrackerImpl.setMvccVersion(mvccVersion);
		userTrackerImpl.setUserTrackerId(userTrackerId);
		userTrackerImpl.setCompanyId(companyId);
		userTrackerImpl.setUserId(userId);

		if (modifiedDate == Long.MIN_VALUE) {
			userTrackerImpl.setModifiedDate(null);
		}
		else {
			userTrackerImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (sessionId == null) {
			userTrackerImpl.setSessionId("");
		}
		else {
			userTrackerImpl.setSessionId(sessionId);
		}

		if (remoteAddr == null) {
			userTrackerImpl.setRemoteAddr("");
		}
		else {
			userTrackerImpl.setRemoteAddr(remoteAddr);
		}

		if (remoteHost == null) {
			userTrackerImpl.setRemoteHost("");
		}
		else {
			userTrackerImpl.setRemoteHost(remoteHost);
		}

		if (userAgent == null) {
			userTrackerImpl.setUserAgent("");
		}
		else {
			userTrackerImpl.setUserAgent(userAgent);
		}

		userTrackerImpl.resetOriginalValues();

		return userTrackerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		userTrackerId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		sessionId = objectInput.readUTF();
		remoteAddr = objectInput.readUTF();
		remoteHost = objectInput.readUTF();
		userAgent = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(userTrackerId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(modifiedDate);

		if (sessionId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sessionId);
		}

		if (remoteAddr == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(remoteAddr);
		}

		if (remoteHost == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(remoteHost);
		}

		if (userAgent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userAgent);
		}
	}

	public long mvccVersion;
	public long userTrackerId;
	public long companyId;
	public long userId;
	public long modifiedDate;
	public String sessionId;
	public String remoteAddr;
	public String remoteHost;
	public String userAgent;

}