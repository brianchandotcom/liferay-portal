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
import com.liferay.portal.model.UserTrackerPath;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing UserTrackerPath in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPath
 * @generated
 */
public class UserTrackerPathCacheModel implements CacheModel<UserTrackerPath>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{userTrackerPathId=");
		sb.append(userTrackerPathId);
		sb.append(", userTrackerId=");
		sb.append(userTrackerId);
		sb.append(", path=");
		sb.append(path);
		sb.append(", pathDate=");
		sb.append(pathDate);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserTrackerPath toEntityModel() {
		UserTrackerPathImpl userTrackerPathImpl = new UserTrackerPathImpl();

		userTrackerPathImpl.setUserTrackerPathId(userTrackerPathId);
		userTrackerPathImpl.setUserTrackerId(userTrackerId);

		if (path == null) {
			userTrackerPathImpl.setPath(StringPool.BLANK);
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

		userTrackerPathImpl.setMvccVersion(mvccVersion);

		userTrackerPathImpl.resetOriginalValues();

		return userTrackerPathImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		userTrackerPathId = objectInput.readLong();
		userTrackerId = objectInput.readLong();
		path = objectInput.readUTF();
		pathDate = objectInput.readLong();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(userTrackerPathId);
		objectOutput.writeLong(userTrackerId);

		if (path == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(path);
		}

		objectOutput.writeLong(pathDate);
		objectOutput.writeLong(mvccVersion);
	}

	public long userTrackerPathId;
	public long userTrackerId;
	public String path;
	public long pathDate;
	public long mvccVersion;
}