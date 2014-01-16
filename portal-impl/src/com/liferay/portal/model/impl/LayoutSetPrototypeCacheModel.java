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
import com.liferay.portal.model.LayoutSetPrototype;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutSetPrototype in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototype
 * @generated
 */
public class LayoutSetPrototypeCacheModel implements CacheModel<LayoutSetPrototype>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", layoutSetPrototypeId=");
		sb.append(layoutSetPrototypeId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", active=");
		sb.append(active);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSetPrototype toEntityModel() {
		LayoutSetPrototypeImpl layoutSetPrototypeImpl = new LayoutSetPrototypeImpl();

		if (uuid == null) {
			layoutSetPrototypeImpl.setUuid(StringPool.BLANK);
		}
		else {
			layoutSetPrototypeImpl.setUuid(uuid);
		}

		layoutSetPrototypeImpl.setLayoutSetPrototypeId(layoutSetPrototypeId);
		layoutSetPrototypeImpl.setCompanyId(companyId);
		layoutSetPrototypeImpl.setUserId(userId);

		if (userName == null) {
			layoutSetPrototypeImpl.setUserName(StringPool.BLANK);
		}
		else {
			layoutSetPrototypeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutSetPrototypeImpl.setCreateDate(null);
		}
		else {
			layoutSetPrototypeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSetPrototypeImpl.setModifiedDate(null);
		}
		else {
			layoutSetPrototypeImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			layoutSetPrototypeImpl.setName(StringPool.BLANK);
		}
		else {
			layoutSetPrototypeImpl.setName(name);
		}

		if (description == null) {
			layoutSetPrototypeImpl.setDescription(StringPool.BLANK);
		}
		else {
			layoutSetPrototypeImpl.setDescription(description);
		}

		if (settings == null) {
			layoutSetPrototypeImpl.setSettings(StringPool.BLANK);
		}
		else {
			layoutSetPrototypeImpl.setSettings(settings);
		}

		layoutSetPrototypeImpl.setActive(active);
		layoutSetPrototypeImpl.setMvccVersion(mvccVersion);

		layoutSetPrototypeImpl.resetOriginalValues();

		return layoutSetPrototypeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		layoutSetPrototypeId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		settings = objectInput.readUTF();
		active = objectInput.readBoolean();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(layoutSetPrototypeId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (settings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(settings);
		}

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(mvccVersion);
	}

	public String uuid;
	public long layoutSetPrototypeId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String settings;
	public boolean active;
	public long mvccVersion;
}