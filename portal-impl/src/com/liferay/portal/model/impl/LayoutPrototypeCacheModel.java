/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutPrototype in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutPrototypeCacheModel
	implements CacheModel<LayoutPrototype>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutPrototypeCacheModel)) {
			return false;
		}

		LayoutPrototypeCacheModel layoutPrototypeCacheModel =
			(LayoutPrototypeCacheModel)object;

		if ((layoutPrototypeId ==
				layoutPrototypeCacheModel.layoutPrototypeId) &&
			(mvccVersion == layoutPrototypeCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutPrototypeId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", layoutPrototypeId=");
		sb.append(layoutPrototypeId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutPrototype toEntityModel() {
		LayoutPrototypeImpl layoutPrototypeImpl = new LayoutPrototypeImpl();

		layoutPrototypeImpl.setMvccVersion(mvccVersion);
		layoutPrototypeImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			layoutPrototypeImpl.setUuid("");
		}
		else {
			layoutPrototypeImpl.setUuid(uuid);
		}

		layoutPrototypeImpl.setLayoutPrototypeId(layoutPrototypeId);
		layoutPrototypeImpl.setCompanyId(companyId);
		layoutPrototypeImpl.setUserId(userId);

		if (userName == null) {
			layoutPrototypeImpl.setUserName("");
		}
		else {
			layoutPrototypeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutPrototypeImpl.setCreateDate(null);
		}
		else {
			layoutPrototypeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutPrototypeImpl.setModifiedDate(null);
		}
		else {
			layoutPrototypeImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			layoutPrototypeImpl.setName("");
		}
		else {
			layoutPrototypeImpl.setName(name);
		}

		if (description == null) {
			layoutPrototypeImpl.setDescription("");
		}
		else {
			layoutPrototypeImpl.setDescription(description);
		}

		if (settings == null) {
			layoutPrototypeImpl.setSettings("");
		}
		else {
			layoutPrototypeImpl.setSettings(settings);
		}

		layoutPrototypeImpl.setActive(active);

		layoutPrototypeImpl.resetOriginalValues();

		return layoutPrototypeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		layoutPrototypeId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = (String)objectInput.readObject();
		description = (String)objectInput.readObject();
		settings = objectInput.readUTF();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(layoutPrototypeId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(name);
		}

		if (description == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(description);
		}

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}

		objectOutput.writeBoolean(active);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long layoutPrototypeId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String settings;
	public boolean active;

}