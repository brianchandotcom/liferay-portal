/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.search.tuning.blueprints.model.Element;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Element in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ElementCacheModel
	implements CacheModel<Element>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ElementCacheModel)) {
			return false;
		}

		ElementCacheModel elementCacheModel = (ElementCacheModel)object;

		if ((elementId == elementCacheModel.elementId) &&
			(mvccVersion == elementCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, elementId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", elementId=");
		sb.append(elementId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", status=");
		sb.append(status);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", configuration=");
		sb.append(configuration);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Element toEntityModel() {
		ElementImpl elementImpl = new ElementImpl();

		elementImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			elementImpl.setUuid("");
		}
		else {
			elementImpl.setUuid(uuid);
		}

		elementImpl.setElementId(elementId);
		elementImpl.setGroupId(groupId);
		elementImpl.setCompanyId(companyId);
		elementImpl.setUserId(userId);

		if (userName == null) {
			elementImpl.setUserName("");
		}
		else {
			elementImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			elementImpl.setCreateDate(null);
		}
		else {
			elementImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			elementImpl.setModifiedDate(null);
		}
		else {
			elementImpl.setModifiedDate(new Date(modifiedDate));
		}

		elementImpl.setStatus(status);

		if (title == null) {
			elementImpl.setTitle("");
		}
		else {
			elementImpl.setTitle(title);
		}

		if (description == null) {
			elementImpl.setDescription("");
		}
		else {
			elementImpl.setDescription(description);
		}

		if (configuration == null) {
			elementImpl.setConfiguration("");
		}
		else {
			elementImpl.setConfiguration(configuration);
		}

		elementImpl.setType(type);

		elementImpl.resetOriginalValues();

		return elementImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		elementId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		status = objectInput.readInt();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		configuration = (String)objectInput.readObject();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(elementId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeInt(status);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (configuration == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(configuration);
		}

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public String uuid;
	public long elementId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int status;
	public String title;
	public String description;
	public String configuration;
	public int type;

}