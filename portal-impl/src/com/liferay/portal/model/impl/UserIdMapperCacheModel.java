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
import com.liferay.portal.kernel.model.UserIdMapper;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UserIdMapper in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserIdMapperCacheModel
	implements CacheModel<UserIdMapper>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserIdMapperCacheModel)) {
			return false;
		}

		UserIdMapperCacheModel userIdMapperCacheModel =
			(UserIdMapperCacheModel)obj;

		if ((userIdMapperId == userIdMapperCacheModel.userIdMapperId) &&
			(mvccVersion == userIdMapperCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, userIdMapperId);

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
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", userIdMapperId=");
		sb.append(userIdMapperId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", description=");
		sb.append(description);
		sb.append(", externalUserId=");
		sb.append(externalUserId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserIdMapper toEntityModel() {
		UserIdMapperImpl userIdMapperImpl = new UserIdMapperImpl();

		userIdMapperImpl.setMvccVersion(mvccVersion);
		userIdMapperImpl.setUserIdMapperId(userIdMapperId);
		userIdMapperImpl.setCompanyId(companyId);
		userIdMapperImpl.setUserId(userId);

		if (type == null) {
			userIdMapperImpl.setType("");
		}
		else {
			userIdMapperImpl.setType(type);
		}

		if (description == null) {
			userIdMapperImpl.setDescription("");
		}
		else {
			userIdMapperImpl.setDescription(description);
		}

		if (externalUserId == null) {
			userIdMapperImpl.setExternalUserId("");
		}
		else {
			userIdMapperImpl.setExternalUserId(externalUserId);
		}

		userIdMapperImpl.resetOriginalValues();

		return userIdMapperImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		userIdMapperId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		type = objectInput.readUTF();
		description = objectInput.readUTF();
		externalUserId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(userIdMapperId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (externalUserId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalUserId);
		}
	}

	public long mvccVersion;
	public long userIdMapperId;
	public long companyId;
	public long userId;
	public String type;
	public String description;
	public String externalUserId;

}