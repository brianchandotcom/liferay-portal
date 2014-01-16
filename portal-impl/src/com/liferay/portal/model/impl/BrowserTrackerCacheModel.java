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
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing BrowserTracker in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see BrowserTracker
 * @generated
 */
public class BrowserTrackerCacheModel implements CacheModel<BrowserTracker>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{browserTrackerId=");
		sb.append(browserTrackerId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", browserKey=");
		sb.append(browserKey);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BrowserTracker toEntityModel() {
		BrowserTrackerImpl browserTrackerImpl = new BrowserTrackerImpl();

		browserTrackerImpl.setBrowserTrackerId(browserTrackerId);
		browserTrackerImpl.setUserId(userId);
		browserTrackerImpl.setBrowserKey(browserKey);
		browserTrackerImpl.setMvccVersion(mvccVersion);

		browserTrackerImpl.resetOriginalValues();

		return browserTrackerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		browserTrackerId = objectInput.readLong();
		userId = objectInput.readLong();
		browserKey = objectInput.readLong();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(browserTrackerId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(browserKey);
		objectOutput.writeLong(mvccVersion);
	}

	public long browserTrackerId;
	public long userId;
	public long browserKey;
	public long mvccVersion;
}