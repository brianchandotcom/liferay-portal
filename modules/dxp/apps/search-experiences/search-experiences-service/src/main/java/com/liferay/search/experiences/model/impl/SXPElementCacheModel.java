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
		sb.append(", description=");
		sb.append(description);
		sb.append(", elementDefinitionJSON=");
		sb.append(elementDefinitionJSON);
		sb.append(", hidden=");
		sb.append(hidden);
		sb.append(", readOnly=");
		sb.append(readOnly);
		sb.append(", schemaVersion=");
		sb.append(schemaVersion);
		sb.append(", title=");
		sb.append(title);
		sb.append(", type=");
		sb.append(type);
		sb.append(", status=");
		sb.append(status);
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

		if (description == null) {
			sxpElementImpl.setDescription("");
		}
		else {
			sxpElementImpl.setDescription(description);
		}

		if (elementDefinitionJSON == null) {
			sxpElementImpl.setElementDefinitionJSON("");
		}
		else {
			sxpElementImpl.setElementDefinitionJSON(elementDefinitionJSON);
		}

		sxpElementImpl.setHidden(hidden);
		sxpElementImpl.setReadOnly(readOnly);

		if (schemaVersion == null) {
			sxpElementImpl.setSchemaVersion("");
		}
		else {
			sxpElementImpl.setSchemaVersion(schemaVersion);
		}

		if (title == null) {
			sxpElementImpl.setTitle("");
		}
		else {
			sxpElementImpl.setTitle(title);
		}

		sxpElementImpl.setType(type);
		sxpElementImpl.setStatus(status);

		sxpElementImpl.resetOriginalValues();

		return sxpElementImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		sxpElementId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		description = objectInput.readUTF();
		elementDefinitionJSON = (String)objectInput.readObject();

		hidden = objectInput.readBoolean();

		readOnly = objectInput.readBoolean();
		schemaVersion = objectInput.readUTF();
		title = objectInput.readUTF();

		type = objectInput.readInt();

		status = objectInput.readInt();
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

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (elementDefinitionJSON == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(elementDefinitionJSON);
		}

		objectOutput.writeBoolean(hidden);

		objectOutput.writeBoolean(readOnly);

		if (schemaVersion == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(schemaVersion);
		}

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeInt(type);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public String uuid;
	public long sxpElementId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String description;
	public String elementDefinitionJSON;
	public boolean hidden;
	public boolean readOnly;
	public String schemaVersion;
	public String title;
	public int type;
	public int status;

}