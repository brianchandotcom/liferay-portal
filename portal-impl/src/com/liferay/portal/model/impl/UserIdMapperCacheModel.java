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
import com.liferay.portal.model.UserIdMapper;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UserIdMapper in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapper
 * @generated
 */
public class UserIdMapperCacheModel implements CacheModel<UserIdMapper>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{userIdMapperId=");
		sb.append(userIdMapperId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", description=");
		sb.append(description);
		sb.append(", externalUserId=");
		sb.append(externalUserId);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserIdMapper toEntityModel() {
		UserIdMapperImpl userIdMapperImpl = new UserIdMapperImpl();

		userIdMapperImpl.setUserIdMapperId(userIdMapperId);
		userIdMapperImpl.setUserId(userId);

		if (type == null) {
			userIdMapperImpl.setType(StringPool.BLANK);
		}
		else {
			userIdMapperImpl.setType(type);
		}

		if (description == null) {
			userIdMapperImpl.setDescription(StringPool.BLANK);
		}
		else {
			userIdMapperImpl.setDescription(description);
		}

		if (externalUserId == null) {
			userIdMapperImpl.setExternalUserId(StringPool.BLANK);
		}
		else {
			userIdMapperImpl.setExternalUserId(externalUserId);
		}

		userIdMapperImpl.setMvccVersion(mvccVersion);

		userIdMapperImpl.resetOriginalValues();

		return userIdMapperImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		userIdMapperId = objectInput.readLong();
		userId = objectInput.readLong();
		type = objectInput.readUTF();
		description = objectInput.readUTF();
		externalUserId = objectInput.readUTF();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(userIdMapperId);
		objectOutput.writeLong(userId);

		if (type == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (externalUserId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(externalUserId);
		}

		objectOutput.writeLong(mvccVersion);
	}

	public long userIdMapperId;
	public long userId;
	public String type;
	public String description;
	public String externalUserId;
	public long mvccVersion;
}