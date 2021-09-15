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

package com.liferay.search.experiences.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.search.experiences.model.SXPElement;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SXPElement in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SXPElementCacheModel
	implements CacheModel<SXPElement>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SXPElementCacheModel)) {
			return false;
		}

		SXPElementCacheModel sxpElementCacheModel =
			(SXPElementCacheModel)object;

		if ((sxpElementId == sxpElementCacheModel.sxpElementId) &&
			(mvccVersion == sxpElementCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, sxpElementId);

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
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", sxpElementId=");
		sb.append(sxpElementId);
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
		sb.append(", configurationJSON=");
		sb.append(configurationJSON);
		sb.append(", hidden=");
		sb.append(hidden);
		sb.append(", readOnly=");
		sb.append(readOnly);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SXPElement toEntityModel() {
		SXPElementImpl sxpElementImpl = new SXPElementImpl();

		sxpElementImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			sxpElementImpl.setUuid("");
		}
		else {
			sxpElementImpl.setUuid(uuid);
		}

		sxpElementImpl.setSXPElementId(sxpElementId);
		sxpElementImpl.setGroupId(groupId);
		sxpElementImpl.setCompanyId(companyId);
		sxpElementImpl.setUserId(userId);

		if (userName == null) {
			sxpElementImpl.setUserName("");
		}
		else {
			sxpElementImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			sxpElementImpl.setCreateDate(null);
		}
		else {
			sxpElementImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			sxpElementImpl.setModifiedDate(null);
		}
		else {
			sxpElementImpl.setModifiedDate(new Date(modifiedDate));
		}

		sxpElementImpl.setStatus(status);

		if (title == null) {
			sxpElementImpl.setTitle("");
		}
		else {
			sxpElementImpl.setTitle(title);
		}

		if (description == null) {
			sxpElementImpl.setDescription("");
		}
		else {
			sxpElementImpl.setDescription(description);
		}

		if (configurationJSON == null) {
			sxpElementImpl.setConfigurationJSON("");
		}
		else {
			sxpElementImpl.setConfigurationJSON(configurationJSON);
		}

		sxpElementImpl.setHidden(hidden);
		sxpElementImpl.setReadOnly(readOnly);
		sxpElementImpl.setType(type);

		sxpElementImpl.resetOriginalValues();

		return sxpElementImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		sxpElementId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		status = objectInput.readInt();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		configurationJSON = objectInput.readUTF();

		hidden = objectInput.readBoolean();

		readOnly = objectInput.readBoolean();

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

		objectOutput.writeLong(sxpElementId);

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

		if (configurationJSON == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(configurationJSON);
		}

		objectOutput.writeBoolean(hidden);

		objectOutput.writeBoolean(readOnly);

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public String uuid;
	public long sxpElementId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int status;
	public String title;
	public String description;
	public String configurationJSON;
	public boolean hidden;
	public boolean readOnly;
	public int type;

}