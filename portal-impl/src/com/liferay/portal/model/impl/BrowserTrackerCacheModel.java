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
import com.liferay.portal.kernel.model.BrowserTracker;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing BrowserTracker in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BrowserTrackerCacheModel
	implements CacheModel<BrowserTracker>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BrowserTrackerCacheModel)) {
			return false;
		}

		BrowserTrackerCacheModel browserTrackerCacheModel =
			(BrowserTrackerCacheModel)obj;

		if ((browserTrackerId == browserTrackerCacheModel.browserTrackerId) &&
			(mvccVersion == browserTrackerCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, browserTrackerId);

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", browserTrackerId=");
		sb.append(browserTrackerId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", browserKey=");
		sb.append(browserKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BrowserTracker toEntityModel() {
		BrowserTrackerImpl browserTrackerImpl = new BrowserTrackerImpl();

		browserTrackerImpl.setMvccVersion(mvccVersion);
		browserTrackerImpl.setBrowserTrackerId(browserTrackerId);
		browserTrackerImpl.setCompanyId(companyId);
		browserTrackerImpl.setUserId(userId);
		browserTrackerImpl.setBrowserKey(browserKey);

		browserTrackerImpl.resetOriginalValues();

		return browserTrackerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		browserTrackerId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		browserKey = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(browserTrackerId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(browserKey);
	}

	public long mvccVersion;
	public long browserTrackerId;
	public long companyId;
	public long userId;
	public long browserKey;

}