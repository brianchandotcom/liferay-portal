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
import com.liferay.portal.kernel.model.UserTrackerPath;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing UserTrackerPath in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserTrackerPathCacheModel
	implements CacheModel<UserTrackerPath>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserTrackerPathCacheModel)) {
			return false;
		}

		UserTrackerPathCacheModel userTrackerPathCacheModel =
			(UserTrackerPathCacheModel)obj;

		if ((userTrackerPathId ==
				userTrackerPathCacheModel.userTrackerPathId) &&
			(mvccVersion == userTrackerPathCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, userTrackerPathId);

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
		sb.append(", userTrackerPathId=");
		sb.append(userTrackerPathId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userTrackerId=");
		sb.append(userTrackerId);
		sb.append(", path=");
		sb.append(path);
		sb.append(", pathDate=");
		sb.append(pathDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserTrackerPath toEntityModel() {
		UserTrackerPathImpl userTrackerPathImpl = new UserTrackerPathImpl();

		userTrackerPathImpl.setMvccVersion(mvccVersion);
		userTrackerPathImpl.setUserTrackerPathId(userTrackerPathId);
		userTrackerPathImpl.setCompanyId(companyId);
		userTrackerPathImpl.setUserTrackerId(userTrackerId);

		if (path == null) {
			userTrackerPathImpl.setPath("");
		}
		else {
			userTrackerPathImpl.setPath(path);
		}

		if (pathDate == Long.MIN_VALUE) {
			userTrackerPathImpl.setPathDate(null);
		}
		else {
			userTrackerPathImpl.setPathDate(new Date(pathDate));
		}

		userTrackerPathImpl.resetOriginalValues();

		return userTrackerPathImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		userTrackerPathId = objectInput.readLong();

		companyId = objectInput.readLong();

		userTrackerId = objectInput.readLong();
		path = objectInput.readUTF();
		pathDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(userTrackerPathId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userTrackerId);

		if (path == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(path);
		}

		objectOutput.writeLong(pathDate);
	}

	public long mvccVersion;
	public long userTrackerPathId;
	public long companyId;
	public long userTrackerId;
	public String path;
	public long pathDate;

}