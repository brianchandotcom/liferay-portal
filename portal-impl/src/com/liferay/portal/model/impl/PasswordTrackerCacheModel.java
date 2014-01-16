/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.PasswordTracker;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PasswordTracker in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTracker
 * @generated
 */
public class PasswordTrackerCacheModel implements CacheModel<PasswordTracker>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{passwordTrackerId=");
		sb.append(passwordTrackerId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", password=");
		sb.append(password);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PasswordTracker toEntityModel() {
		PasswordTrackerImpl passwordTrackerImpl = new PasswordTrackerImpl();

		passwordTrackerImpl.setPasswordTrackerId(passwordTrackerId);
		passwordTrackerImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			passwordTrackerImpl.setCreateDate(null);
		}
		else {
			passwordTrackerImpl.setCreateDate(new Date(createDate));
		}

		if (password == null) {
			passwordTrackerImpl.setPassword(StringPool.BLANK);
		}
		else {
			passwordTrackerImpl.setPassword(password);
		}

		passwordTrackerImpl.setMvccVersion(mvccVersion);

		passwordTrackerImpl.resetOriginalValues();

		return passwordTrackerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		passwordTrackerId = objectInput.readLong();
		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		password = objectInput.readUTF();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(passwordTrackerId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);

		if (password == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(password);
		}

		objectOutput.writeLong(mvccVersion);
	}

	public long passwordTrackerId;
	public long userId;
	public long createDate;
	public String password;
	public long mvccVersion;
}