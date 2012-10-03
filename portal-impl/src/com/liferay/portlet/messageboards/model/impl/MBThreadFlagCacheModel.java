/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.messageboards.model.MBThreadFlag;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MBThreadFlag in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlag
 * @generated
 */
public class MBThreadFlagCacheModel implements CacheModel<MBThreadFlag>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{threadFlagId=");
		sb.append(threadFlagId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", threadId=");
		sb.append(threadId);
		sb.append("}");

		return sb.toString();
	}

	public MBThreadFlag toEntityModel() {
		MBThreadFlagImpl mbThreadFlagImpl = new MBThreadFlagImpl();

		mbThreadFlagImpl.setThreadFlagId(threadFlagId);
		mbThreadFlagImpl.setUserId(userId);

		if (modifiedDate == Long.MIN_VALUE) {
			mbThreadFlagImpl.setModifiedDate(null);
		}
		else {
			mbThreadFlagImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbThreadFlagImpl.setThreadId(threadId);

		mbThreadFlagImpl.resetOriginalValues();

		return mbThreadFlagImpl;
	}

	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		threadFlagId = objectInput.readLong();

		userId = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		threadId = objectInput.readLong();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(threadFlagId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(threadId);
	}

	public long threadFlagId;
	public long userId;
	public long modifiedDate;
	public long threadId;
}